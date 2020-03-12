package controllers.userController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.deptDao.impl.DeptDaoImpl;
import dao.userDao.impl.UserDaoImplHib;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import util.CommenClass;
import util.PageUtil;
import vo.deptVo.DeptVo;
import vo.userVo.AuthorityVo;
import vo.userVo.UserVo;

@Controller
@RequestMapping(value=("/user"))//父路径
public class UserController{
	static Logger log=LogManager.getLogger(UserController.class.getName());
	
	@Autowired
	private UserDaoImplHib<UserVo> userDaoImpl;
	@Autowired
	private UserVo userVo;
	@Autowired
	private DeptVo dv;
	@Autowired
	private AuthorityVo av;
	@Autowired
	private DeptDaoImpl<DeptVo> dt;
	@Autowired
	private CommenClass cc;//实例化工具类
	
	/*登录*/
	@RequestMapping("/login")//子路径,已在配置文件配置拦截.do后缀的请求，可不写.do
	public String login(HttpServletRequest request,UserVo userVo) throws InterruptedException{
		HttpSession session = request.getSession(false);
		/*通过实体类获取前端输入值*/
		String userNo = userVo.getUserNo();
		String userPWD=userVo.getPwd();
		List<UserVo> list = userDaoImpl.selectUser(userVo,1);
		if(list.isEmpty()){
			log.info("用户名不存在");
			request.setAttribute("errorUser", userNo);
			request.setAttribute("user", "user");
			return "redirect:/jsp/errorsPage.jsp";		
		}else{
			userVo = list.get(0);//重新设置userVo
			if(!userPWD.equals("")&&userPWD.equals(userVo.getPwd())){
				if(session.getAttribute(CommenClass.CURRENTUSERSESSION)!=null) {//重新设置session(可能有数据更新)
					session.removeAttribute(CommenClass.CURRENTUSERSESSION);
					session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
				}else {//服务已停或session已过期
					session.setAttribute(CommenClass.CURRENTUSERSESSION, userVo);
				}
//				session.setMaxInactiveInterval(30);//设置session有效性,单位s
				log.info(System.currentTimeMillis()+" 用户登录成功!");
				return "redirect:/jsp/mainPage.jsp";	
			}else{
				System.out.println(userNo+"用户密码错误");
				request.setAttribute("pwd", "password");
				return "redirect:/jsp/errorsPage.jsp";
			}			
		}			
	}
	
	/*用户信息列表*/
	@RequestMapping("/userList")
	public String userInfo(HttpServletRequest request){
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
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
		return "/userInfo/userInfo";
	}
	
	/*修改前回显,模型驱动带值到jsp时不能设置局部模型变量*/
	@RequestMapping("/toUpdateUser")
	public String updateUser(HttpServletRequest request){
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		List<UserVo> list=new ArrayList<UserVo>();
		String userNo=request.getParameter("userNo");//要修改用户
		String isAppro = request.getParameter("isAppro");//是否为审批模块的修改,不为空是
		userVo.setUserNo(userNo);
		list = userDaoImpl.selectUser(userVo);
		userVo=(UserVo)list.get(0);
		request.setAttribute("isAppro", isAppro);
		request.setAttribute("userVo", userVo);//设置了局部模板变量必需另外传值
		return "/userInfo/updateUser";
	}
	
	/*更新用户信息*/
	@RequestMapping("/updateUser")
	public String updateUserRet(HttpServletRequest request){//改事务
		HttpSession session = request.getSession(false);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(session==null) {//判断系统登录是否过期
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		String userNo=request.getParameter("userNo");
		String isAppro = request.getParameter("isAppro");//是否为审批模块的修改,不为空是
		userVo.setUserNo(userNo);
		userVo.setUserName(request.getParameter("userName"));
		userVo.setAge(Integer.parseInt(request.getParameter("userAge")));
		userVo.setPwd(request.getParameter("newPWDName"));
		int updateRet = userDaoImpl.updateUser(userVo);
		userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);//重新获取当前登录用户信息用于列表显示用户信息
		if(updateRet>0){//修改成功
			return approveUser(request);//返回审批模块
		}else{
			if(isAppro.equals("3")) {//在审批中修改失败
				request.setAttribute("updateInfo", "update_fail");
				return approveUser(request);
			}
			System.out.println("修改密码失败!");
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
	
	/*注销用户*/
	@RequestMapping("/deleteUserVirtual")
	public String delUser(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		int delUserRet = userDaoImpl.delUser(userVo, userID,1);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			System.out.println("删除用户成功,id:"+userID);
		else
			System.out.println("删除用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/userInfo";
	}
	
	/*彻底删除用户,连同关联外键记录也删除-文件*/
	@RequestMapping("/deleteUserCompletely")
	public String delComplete(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		int userID=Integer.parseInt(request.getParameter("id"));
		int delUserRet = userDaoImpl.delUser(userVo, userID,0);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			System.out.println("彻底删除用户成功,id:"+userID);
		else
			System.out.println("彻底删除用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/userInfo";
	}
	
	/*获取部门数据,用于回显*/
	@RequestMapping("/beforeAddUser")
	public String beforeAddUser(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		List<DeptVo> list=dt.getDeptInfos();//普通查询
		request.setAttribute("deptList", list);
		return "/userInfo/addUser";//返回到添加用户的addUser.jsp页面
		
	}
	
	/*添加用户*/
	@RequestMapping("/addUser")
	public String addUser(HttpServletRequest request,UserVo userVo) {
		HttpSession session = request.getSession(false);
		if(session==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		String deptName=request.getParameter("deptName");
		/*根据部门名称获取部门id*/
		List<DeptVo> deptInfo = dt.getDeptInfo(dv, deptName);
		for(DeptVo dpVo:deptInfo)//部门唯一，只会查出一条记录
			dv.setDeptID(dpVo.getDeptID());
		userVo.setDept(dv);
		av.setAuthVal(2);//默认为普通用户
		userVo.setAv(av);
		int i = userDaoImpl.addUser(userVo);
		if(i>0) {
			log.info("添加用户成功!");
		}else {
			log.info("添加用户失败,返回值:"+i);
		}
		userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);//重新获取当前登录用户信息用于列表显示用户信息
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);//用户列表
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/approveUser";//返回到用户审批页面
	}

	/*ajax判断用户是否已存在*/
	@RequestMapping("/ajaxReturn")
	public void ajaxReturn(HttpServletRequest request,HttpServletResponse response) {
		log.info("come to controller");
		try {
			/*中文乱码时设置响应或请求的编码*/
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String userNo = request.getParameter("userNo");
			userVo.setUserNo(userNo);
			List<UserVo> list = userDaoImpl.selectUser(userVo);
			JsonConfig jsonConfig = new JsonConfig();
			
			/*去除级联关系,String数组包含所有要去除的属性,不管是什么类型的属性;要写在一起，不能分开写多个setExcludes*/
			jsonConfig.setExcludes(new String[] {"av","dept","fileVo","ad","sv","ev"});
			String ret=null;
			if(list.size()>  0)
				ret=JSONArray.fromObject(list, jsonConfig).toString();//list是含级联关系pojo的结果集,转json字符串
			out.print(ret);//返回json到页面
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*激活用户、审批通过*/
	@RequestMapping("/activeUser")
	public String activeUser(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		int delUserRet = userDaoImpl.updateStatus(userVo, userID);
		String type = request.getParameter("type");
		type=type==null?"1":type;
		cc.setType(type);
		if(delUserRet>0)
			System.out.println("激活用户成功,id:"+userID);
		else
			System.out.println("激活用户失败,id:"+userID);
		long count = userDaoImpl.getCount(type);
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageSelectUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/userInfo";//mvc中间部分,前缀和后缀已在mvc的xml配置文件设置
	}
	
	/*回显待审批用户*/
	@RequestMapping("/approveUser")
	public String approveUser(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);//用户列表
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/approveUser";
	}
	
	/*审批不通过*/
	@RequestMapping("/refuseUser")
	public String refuseUser(HttpServletRequest request) {
		UserVo userVo=null;
		HttpSession session = request.getSession(false);
		if(session!=null) {//判断系统登录是否过期
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		int userID=Integer.parseInt(request.getParameter("userID"));
		userVo.setId(userID);
		int delUserRet = userDaoImpl.updateApproveUserStatus(userVo);
		if(delUserRet>0)
			System.out.println("审批通过,id:"+userID);
		else
			System.out.println("审批不通过,id:"+userID);
		long count = userDaoImpl.getApproveCount();
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<UserVo> list = userDaoImpl.pageApproveUser(userVo,cc);
		request.setAttribute("userList", list);
		request.setAttribute("pageData", cc);//分页列表
		return "/userInfo/approveUser";
	}

}
