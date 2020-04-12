package action.userAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.deptDao.impl.DeptDaoImpl;
import hibernate.dao.userDao.impl.UserDaoImplHib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.CommenClass;
import util.PageUtil;
import vo.deptVo.DeptVo;
import vo.fileVo.FileManageVo;
import vo.userVo.AuthorityVo;
import vo.userVo.UserVo;

@SuppressWarnings(value={"all"})
public class LoginAction extends ActionSupport implements ModelDriven<UserVo>{
	private static Logger log=LogManager.getLogger(LoginAction.class.getName());
	HttpServletRequest request=ServletActionContext.getRequest();
	UserDaoImplHib<UserVo> userDaoImpl = new UserDaoImplHib<UserVo>();
	UserVo userVo=new UserVo();//为了登录时通过模型驱动取值,不能设置为null
	DeptVo dv=new DeptVo();
	AuthorityVo av=new AuthorityVo();
	DeptDaoImpl<DeptVo> dt=new DeptDaoImpl();
	CommenClass cc=new CommenClass();//实例化工具类
	Map map=new HashMap<String,String>();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/*登录*/
	public String execute() throws InterruptedException{
		HttpSession session = request.getSession();
		/*通过模型驱动获取输入值*/
		String userNo = userVo.getUserNo();
		String userPWD=userVo.getPwd();
		List<UserVo> list = userDaoImpl.selectUser(userVo,1);
//		Thread.sleep(2000);//延时2s模拟网络延时测试令牌
		if(list.isEmpty()){
			log.info("用户名不存在");
			request.setAttribute("errorUser", userNo);
			request.setAttribute("user", "user");
			return ERROR;		
		}else{
			userVo = list.get(0);//重新设置userVo
			if (!userPWD.equals("") && userPWD.equals(userVo.getPwd())) {
				if (session.getAttribute(CommenClass.LOGINEDMAP) != null) {
					map = (Map) session.getAttribute(CommenClass.LOGINEDMAP);
					String islogined = (String) map.get(userNo);
					if (islogined != null) {// 为空表示当前用户第一次登录
						log.info("该用户已登录，请稍后再试！");
						request.setAttribute("islogined", "logined");
						return ERROR;
					}
				}
				if (session.getAttribute(CommenClass.CURRENTUSERSESSION) != null) {// 重新设置session(可能有数据更新)
					session.removeAttribute(CommenClass.CURRENTUSERSESSION);
					session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
					map.put(userNo, "logined");// 设置当前用户已登录
					session.setAttribute(CommenClass.LOGINEDMAP, map);// 将用户登录状态改为已登录
				} else {// 服务已停或session已过期
					session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
					map.put(userNo, "logined");// 设置当前用户已登录
					session.setAttribute(CommenClass.LOGINEDMAP, map);// 将用户登录状态改为已登录
				}
//				session.setMaxInactiveInterval(30);//设置session有效性,单位s
				String date = sdf.format(System.currentTimeMillis());
				log.info(userNo + " 登录成功,登录时间:" + date);
				return SUCCESS;
			} else {
				log.info(userNo + "用户密码错误");
				request.setAttribute("pwd", "password");
				return ERROR;
			}
		}			
	}
	
	/*用户信息列表*/
	public String userInfo(){
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String type = request.getParameter("type");
		type=type==null?"1":type;
		long count = userDaoImpl.getCount(type);
		cc.setType(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);//用户信息
		request.setAttribute("pageData", cc);//分页数据
		return "userInfo";
	}
	
	/*修改前回显,模型驱动带值到jsp时不能设置局部模型变量*/
	public String updateUser(){
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		List<UserVo> list=new ArrayList<UserVo>();
		String userNo=request.getParameter("userNo");//要修改用户
		String isAppro = request.getParameter("isAppro");//是否为审批模块的修改,不为空是
		userVo.setUserNo(userNo);
		list = userDaoImpl.selectUser(userVo);
		userVo=(UserVo)list.get(0);
		request.setAttribute("isAppro", isAppro);
		request.setAttribute("userVo", userVo);//设置了局部模板变量必需另外传值
		return "update";
	}
	
	/*更新用户信息*/
	public String updateUserRet(){//改事务
		HttpSession session = request.getSession(false);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(session==null) {//判断系统登录是否过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String userNo=request.getParameter("userNo");
		String isAppro = request.getParameter("isAppro");//是否为审批模块的修改,不为空是
		userVo.setUserNo(userNo);
		userVo.setUserName(request.getParameter("userName"));
		userVo.setAge(Integer.parseInt(request.getParameter("userAge")));
		userVo.setPwd(request.getParameter("newPWDName"));
		int updateRet = userDaoImpl.updateUser(userVo);
		if(session!=null)
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);//重新获取当前登录用户信息用于列表显示用户信息
		else {
			request.setAttribute("user", "outtime");
			return ERROR;
		}

		if(updateRet>0){//修改成功
			return approveUser();//返回审批模块
		}else{
			if(isAppro.equals("3")) {//在审批中修改失败
				request.setAttribute("updateInfo", "update_fail");
				return approveUser();
			}
			log.info("修改密码失败!");
			request.setAttribute("updateInfo", "update_fail");
			long count = userDaoImpl.getCount(type);
			cc.setTotalCount((int)count);
			cc = PageUtil.pageMethod(cc, request);
			List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
			request.setAttribute("pageData", cc);//分页列表
			request.setAttribute("userList", list);
		}
		return "userInfo";//在用户管理中修改失败
	}
	
	/*已注销用户*/
	public String delUser() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		int delUserRet = userDaoImpl.delUser(userVo, userID,1);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			log.info("删除用户成功,id:"+userID);
		else
			log.info("删除用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "userInfo";
	}
	
	/*彻底删除用户,连同关联外键记录也删除-文件*/
	public String delComplete() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int userID=Integer.parseInt(request.getParameter("id"));
		int delUserRet = userDaoImpl.delUser(userVo, userID,0);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			log.info("彻底删除用户成功,id:"+userID);
		else
			log.info("彻底删除用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "userInfo";
	}
	
	/*获取部门数据,用于回显*/
	public String beforeAddUser() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		List<DeptVo> list=dt.getDeptInfos();//普通查询
		request.setAttribute("deptList", list);
		return "beforeAddUser";
		
	}
	
	/*添加用户*/
	public String addUser() {
		HttpSession session = request.getSession(false);
		if(session==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String deptName=request.getParameter("deptName");
		/*根据部门名称获取部门id*/
		List<DeptVo> deptInfo = dt.getDeptInfo(dv, deptName);
		for(DeptVo dpVo:deptInfo)//部门唯一，只会查出一条记录
			dv.setDeptID(dpVo.getDeptID());
		userVo.setDept(dv);
		av.setAuthVal(2);//默认为普通用户
		userVo.setAv(av);
		int i = userDaoImpl.addUser(userVo);//由模型驱动获取
		if(session!=null)
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);//重新获取当前登录用户信息用于列表显示用户信息
		else {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);//用户列表
		request.setAttribute("pageData", cc);//分页列表
		return "approveUser";
	}

	/*ajax判断用户是否已存在*/
	public void ajaxReturn() {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			/*中文乱码时设置响应或请求的编码*/
			response.setContentType("text/json;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String userNo = request.getParameter("userNo");
			userVo.setUserNo(userNo);
			List<UserVo> list = userDaoImpl.selectUser(userVo);
			JsonConfig jsonConfig = new JsonConfig();
			
			/*去除级联关系,String数组包含所有要去除的属性,不管是什么类型的属性;要写在一起，不能分开写多个setExcludes*/
			jsonConfig.setExcludes(new String[] {"av","dept","fileVo","ad","sv","ev"});
			JSONArray js = new JSONArray();
			String ret=null;
			if(list.size()>0)
				ret=js.fromObject(list, jsonConfig).toString();//list是含级联关系pojo的结果集,转json字符串
			out.print(ret);//返回json到页面
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*激活用户、审批通过*/
	public String activeUser() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		int delUserRet = userDaoImpl.updateStatus(userVo, userID);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			log.info("激活用户成功,id:"+userID);
		else
			log.info("激活用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "userInfo";
	}
	
	/*回显待审批用户*/
	public String approveUser() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);//用户列表
		request.setAttribute("pageData", cc);//分页列表
		return "approveUser";
	}
	
	/*审批不通过*/
	public String refuseUser() {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		userVo.setId(userID);
		int delUserRet = userDaoImpl.updateApproveUserStatus(userVo);
		if(delUserRet>0)
			log.info("审批通过,id:"+userID);
		else
			log.info("审批不通过,id:"+userID);
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "approveUser";
	}

	/*退出修改登录状态*/
	public void destroySession() {
		HttpSession session = request.getSession();
		String userNo = request.getParameter("userNo");
		map.remove(userNo);//移除登录状态
		session.setAttribute(CommenClass.LOGINEDMAP, map);//改变同一用户的登录状态
		String date = sdf.format(System.currentTimeMillis());
		log.info(userNo+"退出成功,退出时间："+date);
	}
	
	/*国际化*/
	public String i18n(){
		return "succ";
	}
	
	public String tokenTest() throws InterruptedException{
		log.info("token test");
		Thread.sleep(2000);//休眠2s
		return SUCCESS;
	}

	public UserDaoImplHib getUserDaoImpl() {
		return userDaoImpl;
	}


	public void setUserDaoImpl(UserDaoImplHib userDaoImpl) {
		this.userDaoImpl = userDaoImpl;
	}


	public UserVo getUserVo() {
		return userVo;
	}


	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}


	@Override
	public UserVo getModel() {
		return userVo;
	}

}
