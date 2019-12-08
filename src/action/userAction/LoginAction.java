package action.userAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicSliderUI.ActionScroller;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.deptDao.impl.DeptDaoImpl;
import hibernate.dao.userDao.impl.UserDaoImplHib;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import util.InitializeSys;
import vo.deptVo.DeptVo;
import vo.fileVo.FileManageVo;
import vo.userVo.AuthorityVo;
import vo.userVo.UserVo;

@SuppressWarnings(value={"all"})
public class LoginAction extends ActionSupport implements ModelDriven<UserVo>{
	
	UserDaoImplHib<UserVo> userDaoImpl = new UserDaoImplHib<UserVo>();
	UserVo userVo =new UserVo();
	DeptVo dv=new DeptVo();
	AuthorityVo av=new AuthorityVo();
	DeptDaoImpl<DeptVo> dt=new DeptDaoImpl();
	HttpServletRequest request=ServletActionContext.getRequest();
	HttpSession session = request.getSession();
	
	public String execute() throws InterruptedException{
		/*通过模型驱动获取输入值*/
		String userNo = userVo.getUserNo();
		String userPWD=userVo.getPwd();
		List<UserVo> list = userDaoImpl.selectUser(userVo, userNo,0);
		userVo.setDept(dv);
//		Thread.sleep(2000);//延时2s模拟网络延时测试令牌
		if(list.isEmpty()){
			System.out.println("用户名不存在");
			request.setAttribute("errorUser", userNo);
			request.setAttribute("user", "user");
			return ERROR;		
		}else{
			for(UserVo uv:list){//用于设置session
				userVo.setId(uv.getId());
				userVo.setUserNo(uv.getUserNo());
				userVo.setUserName(uv.getUserName());
				userVo.getDept().setDeptID(uv.getDept().getDeptID());
				userVo.setStatus(uv.getStatus());
				userVo.setAv(uv.getAv());
				userVo.setPwd(uv.getPwd());
			}
			if(!userPWD.equals("")&&userPWD.equals(userVo.getPwd())){
				session.setAttribute("userVo", userVo);
//				session.setMaxInactiveInterval(1800);//单位s
				return SUCCESS;	
			}else{
				System.out.println(userNo+"用户密码错误");
				request.setAttribute("pwd", "password");
				return ERROR;
			}			
		}			
	}
	
	
	/*修改回显*/
	public String updateUser(){
		List<UserVo> list=new ArrayList<UserVo>();
		String userNo=request.getParameter("userNo");
		list = userDaoImpl.selectUser(userVo,userNo,0);
//		request.setAttribute("updateList", list);
		for(UserVo u:list) {
			userVo.setAge(u.getAge());
			userVo.setId(u.getId());
			userVo.setUserName(u.getUserName());
			userVo.setUserNo(u.getUserNo());
			userVo.setPwd(u.getPwd());
		}
		return "update";
	}
	
	public String userInfo(){
		List<UserVo> list = userDaoImpl.pageSelectUser(1, 10, 0, userVo);
		request.setAttribute("userList", list);
		return "userInfo";
	}
	
	public String updateUserRet(){//改事务
		String userNo=request.getParameter("userNo");
//		System.out.println("userNo:"+userNo);
		userVo.setUserNo(userNo);
		userVo.setUserName(request.getParameter("userName"));
		userVo.setAge(Integer.parseInt(request.getParameter("userAge")));
		userVo.setPwd(request.getParameter("newPWDName"));
		int updateRet = userDaoImpl.updateUser(userVo);
		if(updateRet>0){
			int c=userDaoImpl.getCount(userNo);
			List<UserVo> list = userDaoImpl.pageSelectUser(1, 10, c, userVo);
			request.setAttribute("userList", list);
		}else{
			System.out.println("修改密码失败!");
			request.setAttribute("updateInfo", "update_fail");
			int c=userDaoImpl.getCount(userNo);
			List<UserVo> list = userDaoImpl.pageSelectUser(1, 10, c, userVo);
			request.setAttribute("userList", list);
		}
		return "userInfo";
	}
	
	public String delUser() {
		int userID=Integer.parseInt(request.getParameter("id"));
		int delUserRet = userDaoImpl.delUser(userVo, userID);
		if(delUserRet>0)
			System.out.println("删除用户成功,id:"+userID);
		else
			System.out.println("删除用户失败,id:"+userID);
		List<UserVo> list = userDaoImpl.pageSelectUser(1, 10, 0, userVo);
		request.setAttribute("userList", list);
		return "userInfo";
	}
	
	public String beforeAddUser() {//获取部门数据
		List<DeptVo> list=dt.getDeptInfos(dv,0,0);//普通查询
		request.setAttribute("deptList", list);
		return "beforeAddUser";
		
	}
	
	public String addUser() {//添加用户
		String deptName=request.getParameter("deptName");
		/*根据部门名称获取部门id*/
		List<DeptVo> deptInfo = dt.getDeptInfo(dv, deptName);
		for(DeptVo dpVo:deptInfo)//部门唯一，只会查出一条记录
			dv.setDeptID(dpVo.getDeptID());
		userVo.setDept(dv);
		av.setAuthVal(2);//默认为普通用户
		userVo.setAv(av);
		int i = userDaoImpl.addUser(userVo);
//		System.out.println("添加用户返回值:"+i);
		List<UserVo> list = userDaoImpl.pageSelectUser(1, 10, 0, userVo);
		request.setAttribute("userList", list);
		return "userInfo";
	}

	public void ajaxReturn() {//返回json用于ajax判断用户名是否已存在
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			/*中文乱码时设置响应或请求的编码*/
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			String userNo = request.getParameter("userNo");
			List<UserVo> list = userDaoImpl.selectUser(userVo, userNo, 1);
			
			JsonConfig jsonConfig = new JsonConfig();
			
			/*去除级联关系,String数组包含所有要去除的属性,不管是什么类型的属性;要写在一起，不能分开写多个setExcludes*/
			jsonConfig.setExcludes(new String[] {"av","dept","fileVo"});
			
			JSONArray js = new JSONArray();
			String ret=null;
			if(list.size()>0)
				ret=js.fromObject(list, jsonConfig).toString();//list是含级联关系pojo的结果集,转json字符串
			out.print(ret);//返回json到页面
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*国际化*/
	public String i18n(){
		return "succ";
	}
	
	public String tokenTest() throws InterruptedException{
		System.out.println("token test");
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
		// TODO Auto-generated method stub
		return userVo;
	}

}
