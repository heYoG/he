package com.dj.seal.sealBody.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dj.seal.organise.service.api.ISysDeptService;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.struts.BaseAction;
import com.dj.seal.util.table.SysUserUtil;

/**
 * 印章权限设置中跳转到显示用户列表的模式窗口时所用
 * @author yc
 * @since2009-12-01
 * 
 */
public class SealSetUserAction extends BaseAction{

	static Logger logger = LogManager.getLogger(SealSetUserAction.class.getName());

	private ISealBodyService seal_body;
	private IUserService user_srv;
	private ISysDeptService dept_srv;
	private ISysUnitService unit_srv;
	
	public ISysDeptService getDept_srv() {
		return dept_srv;
	}
	public void setDept_srv(ISysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}
	public ISysUnitService getUnit_srv() {
		return unit_srv;
	}
	public void setUnit_srv(ISysUnitService unit_srv) {
		this.unit_srv = unit_srv;
	}
	public ISealBodyService getSeal_body() {
		return seal_body;
	}
	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}
	public IUserService getUser_srv() {
		return user_srv;
	}
	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//判断用户是否登陆
		if (isLogin(request) == BaseAction.SYSTEM_USER_STATUS_NOLOGIN) {
			return mapping.findForward("no_login");
		}
		HttpSession session=request.getSession();
		SysUser user=(SysUser)session.getAttribute(Constants.SESSION_CURRENT_USER);
       if(user==null){
	     user=new SysUser();
	     user.setUser_id("admin");
	     user.setUser_status("1");
        }
		//根据用户名得到其可以管理的部门树
		List<SysDepartment> list_dept=dept_srv.deptTreeByUser(user.getUser_id());
		//如果是系统管理员，则可以管理所有部门
		if("admin".equals(user.getUser_id())){
			list_dept=dept_srv.showAllDepts();
		}
		//查询当前用户所管理的所有用户
		//查询所有用户
		String sql=null;
		if(Constants.USER_NAME_ADMIN.equals(user.getUser_id()) || Constants.USER_NAME_LOGGER.equals(user.getUser_id()))
		{
			 sql="select * from "+SysUserUtil.TABLE_NAME;
		}
		//得到当前用户所管理的部门id的拼凑语句
		else{
		 String dept_list=user_srv.getAllDept(user.getUser_id());
		 sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.DEPT_NO +" in ("+dept_list+")";
		}
		List<SysUser> list_user=user_srv.showSysUsersBySql(sql);
		request.setAttribute("list_user",list_user);	 
		request.setAttribute("depts", list_dept);
		SysUnit unit=unit_srv.showSysUnit();
		request.setAttribute("unit", unit);
		return mapping.findForward("success");
	}
	
}
