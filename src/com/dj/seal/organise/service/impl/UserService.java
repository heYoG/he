package com.dj.seal.organise.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.dj.seal.structure.dao.po.*;
import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.organise.dao.api.ISysUnitDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.dao.api.ISysUserDeptDao;
import com.dj.seal.organise.dao.api.ISysUserRoleDao;
import com.dj.seal.organise.dao.impl.SysRoleDaoImpl;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.form.LoginForm;
import com.dj.seal.organise.web.form.UserForm;
import com.dj.seal.personInfo.web.form.PersonForm;
import com.dj.seal.personInfo.web.form.PsdForm;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.encrypt.MD5Util;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.CertUtil;
import com.dj.seal.util.table.PsdLastTimeUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.SysUserDeptUtil;
import com.dj.seal.util.table.SysUserRoleUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.table.UserCertUtil;
import com.dj.seal.util.table.UserSealUtil;


public class UserService implements IUserService {
	
	static Logger logger = LogManager.getLogger();

	private ISysUserDao su_dao;
	private ISysDepartmentDao dept_dao;
	private ISysUnitDao unit_dao;
	private ISysRoleDao role_dao;
	private ISysUserDeptDao user_dept_dao;
	private ISysUserRoleDao user_role_dao;
    private BaseDAOJDBC dao;
   
	public BaseDAOJDBC getDao() {
		return dao;
	}

	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}

	public ISysUserRoleDao getUser_role_dao() {
		return user_role_dao;
	}

	public void setUser_role_dao(ISysUserRoleDao user_role_dao) {
		this.user_role_dao = user_role_dao;
	}

	public ISysUserDeptDao getUser_dept_dao() {
		return user_dept_dao;
	}

	public void setUser_dept_dao(ISysUserDeptDao user_dept_dao) {
		this.user_dept_dao = user_dept_dao;
	}

	public ISysUserDao getSu_dao() {
		return su_dao;
	}

	public void setSu_dao(ISysUserDao su_dao) {
		this.su_dao = su_dao;
	}

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	public ISysUnitDao getUnit_dao() {
		return unit_dao;
	}

	public void setUnit_dao(ISysUnitDao unit_dao) {
		this.unit_dao = unit_dao;
	}

	public ISysRoleDao getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(ISysRoleDao role_dao) {
		this.role_dao = role_dao;
	}

	
	@Override
	public String piLiangImportUser(String gonghao,String xingming,String yingyeting ,String juese,String create_userid) throws Exception{
		SysUser user = showSysUserByUser_id(gonghao);
		if(user!=null){
			return "失败:用户工号已存在:"+gonghao;
		}
		SysDepartment dept = dept_dao.showSysDepartmentByDept_name(yingyeting);
		if(dept==null){
			return "失败:未找到营业厅信息:"+yingyeting;
		}
		UserForm userForm = new UserForm();
		userForm.setUser_id(gonghao);//用户ID  工号
		userForm.setUser_name(xingming);//用户姓名
		userForm.setDept_no(dept.getDept_no());//所属部门
		userForm.setRole_nos(juese);//角色
		userForm.setCreate_name(create_userid);//创建人
		userForm.setUser_psd("123456");//密码
		userForm.setIs_active("1");//
		userForm.setUser_type(Constants.USER_STATUS);//默认用户状态是正常
		userForm.setUseing_key("0");//是否使用key
		userForm.setRang_type("0");//管理范围
		userForm.setIs_junior("1");//是否包含下级
		userForm.setUser_theme("2");//主题
		userForm.setUser_sex("1");//性别 0男 1女
		AddUser(userForm, create_userid);
		return "成功";
	}
	
	
	
	@Override
	public SysUser userLogin(LoginForm loginForm, String ip)
			throws GeneralException {
		SysUser user = su_dao.showSysUserByUser_id(loginForm.getUser_no());
		// 判断输入是否正确并且是否被批准
		if (user != null && user.getUser_psd().equals(loginForm.getUser_psd())) {
			// 获得用户角色和所属部门
			List<SysUser> list = new ArrayList<SysUser>();
			list.add(user);
			//List<SysUser> list1 = change(list);
			return list.get(0);
		}
		return null;
	}

	// 将用户列表重新包装
	public List<SysUser> change1(List<SysUser> list) throws GeneralException {
		List<SysUser> list1 = new ArrayList<SysUser>();// 重新包装的list
		for (Iterator it = list.iterator(); it.hasNext();) {
			SysUser sysUser = (SysUser) it.next();
			String user_sta = sysUser.getUser_status();// 用户身份
			// 根据部门id查部门名
			SysDepartment dept = dept_dao.showSysDepartmentByDept_no(sysUser
					.getDept_no());
			if (dept != null) {
				sysUser.setDept_name(dept.getDept_name());
			} else {
				if (Constants.UNIT_DEPT_NO.equals(sysUser.getDept_no())) {
					SysUnit unit = unit_dao.showSysUnit();
					sysUser.setDept_name(unit.getUnit_name());
				}
			}
			List<SysRole> roles = role_dao.showSysRolesByUser_id(sysUser
					.getUser_id());
			SysRole role = null;
			if (roles.size() > 0) {
				role = roles.get(0);
			}
			if (role != null) {
				sysUser.setRole_name(role.getRole_name());
			} else {
				if ("1".equals(user_sta)) {
					sysUser.setRole_name("系统管理员");
				}
				if ("2".equals(user_sta)) {
					sysUser.setRole_name("日志审核员");
				}
			}
			// 根据用户id查询所管理的部门
			String dept_list = getDeptName(sysUser.getUser_id());
			sysUser.setManage_range(dept_list);
			if ("1".equals(user_sta)) {
				sysUser.setManage_range("全体部门");
			}
			if (Constants.UNIT_DEPT_NO.equals(sysUser.getDept_no())) {
				sysUser.setManage_range(unit_dao.showSysUnit().getUnit_name());
			}
			list1.add(sysUser);
		}
		return list1;
	}

	// 根据用户名获得所管理部门名称的拼凑语句
	public String getDeptName(String user_id) throws GeneralException {
		List<SysDepartment> dept_list = dept_dao
				.showSysDepartmentsByUser_id(user_id);
		StringBuffer sb = new StringBuffer();
		if (dept_list.size() > 0) {

			for (int i = 0; i < dept_list.size(); i++) {
				SysDepartment dept = dept_dao
						.showSysDepartmentByDept_no(dept_list.get(i)
								.getDept_no());

				if (i == (dept_list.size() - 1)) {
					sb.append(dept.getDept_name());

				} else {
					if (dept != null) {
						sb.append(dept.getDept_name() + ",");
					}
				}
			}
		}
		return sb.toString();
	}

	@Override
	public void updateFuncsValue(String user_id) throws GeneralException {
		// 根据用户名称得到现有角色列表
		List<SysRole> roles = role_dao.showSysRolesByUser_id(user_id);
		// 计算得出用户现有权限值
		SysUser model = getModelUserByRoles(roles);
		// 更新用户记录
		SysUser user = su_dao.showSysUserByUser_id(user_id);
		su_dao.updateSysUser(setNewFuncs(user, model));
	}

	public SysUser getModelUserByRoles(List<SysRole> roles)
			throws GeneralException {
		SysUser user = new SysUser();
		int[] funcs = { 0, 0, 0, 0, 0 };
		for (SysRole ro : roles) {
			funcs[0] |= ro.getRole_fun1();
			funcs[1] |= ro.getRole_fun2();
			funcs[2] |= ro.getRole_fun3();
			funcs[3] |= ro.getRole_fun4();
			funcs[4] |= ro.getRole_fun5();
		}
		user.setUser_fun1(funcs[0]);
		user.setUser_fun2(funcs[1]);
		user.setUser_fun3(funcs[2]);
		user.setUser_fun4(funcs[3]);
		user.setUser_fun5(funcs[4]);
		return user;
	}

	/**
	 * 返回设置好新权限的用户
	 * 
	 * @param user
	 *            需要更新的对象
	 * @param model
	 *            存有新权限值的对象
	 * @return
	 */
	private SysUser setNewFuncs(SysUser user, SysUser model) {
		user.setUser_fun1(model.getUser_fun1());
		user.setUser_fun2(model.getUser_fun2());
		user.setUser_fun3(model.getUser_fun3());
		user.setUser_fun4(model.getUser_fun4());
		user.setUser_fun5(model.getUser_fun5());
		return user;
	}

	public List<SysUser> showUsersByNos(String sel) throws GeneralException {

		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysUserUtil.TABLE_NAME);
		sb.append(" where ");
		String[] users = sel.split(",");
		sb.append(SysUserUtil.USER_ID+" in (");
		for (String no : users) {
			sb.append("'").append(no).append("',");
		}
		sb.deleteCharAt(sb.lastIndexOf(","));
		sb.append(")");
		sb.append(" and "+SysUserUtil.USER_TYPE+"='"+Constants.USER_STATUS+"'");
		sb.append(" and "+SysUserUtil.USER_ID+" !='"+Constants.USER_NAME_LOGGER+"'");
		
		//logger.info(sb.toString());
		return showSysUsersBySql(sb.toString());
	}

	@Override
	public List<SysUser> showSysUsersBySql(String sql) throws GeneralException {

		List<SysUser> list = su_dao.showSysUsersBySql(sql);
		//return change(list);
		return list;
	}

	@Override
	public String AddUser(UserForm userForm, String userid)
			throws GeneralException {
		try {
			SysUser user = new SysUser();
			// 设置管理部门信息（用户部门表）
			addUserDept(userForm);
			if (userForm.getUser_birth() != null&& userForm.getUser_birth().length() > 0) {
				userForm.setUser_birth(userForm.getUser_birth() + " 00:00:00");
			} else {
				userForm.setUser_birth("1970-08-01 00:00:00");
			}
			userForm.setCreate_data((new Timestamp(new java.util.Date()
					.getTime()).toString()));
			userForm.setCreate_name(userid);
			userForm.setCreate_user(userid);
			userForm.setOperate_time((new Timestamp(new java.util.Date().getTime()).toString()));

			BeanUtils.copyProperties(user, userForm);
//			// 判断是否使用证书
//			try {
//				if (user.getKey_sn() == null) {
//					user.setUseing_key(Constants.NO_USE);
//				} else {
//					user.setUseing_key(Constants.IS_USE);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error(e.getMessage());
//			}
			// 为用户桌面显示类型为默认
			user.setMytable_left("3,");
			user.setMytable_right("4,");
			user.setOn_status("0");// 设置为“非在线”
			user.setUser_status("3");// 设置默认用户身份状态为“普通用户”
			user.setLast_visit_ip("0.0.0.0");// 设置最后登录IP
			user.setLast_visit_time(new Timestamp(new java.util.Date().getTime()));// 设置最后登录时间
			user.setLogined(0);//新增用户默认为0-未登录过
			
			// 设置角色，权限信息（用户角色表，用户表的权限值）
			user_role_dao.deleteUserRoleByUser_id(userForm.getUser_id());
			String role_nos = userForm.getRole_nos();
			String[] roles = role_nos.split(",");
			for (String role_no : roles) {
				Integer role_id = Integer.valueOf(role_no);
				user_role_dao.addUserRole(userForm.getUser_id(), role_id, "1");
			}
			setUserRole(user, roles);
			su_dao.AddUser(user);// 保存到用户表
			return "success";
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 根据表单数据为用户部门表添加记录
	 * 
	 * @param userForm
	 * @throws GeneralException
	 */
	private void addUserDept(UserForm userForm) throws GeneralException {
		user_dept_dao.deleteSysUserDeptByUser_id(userForm.getUser_id());
		if ("2".equals(userForm.getRang_type())) {
			user_dept_dao.addSysUserDept(userForm.getUser_id(), userForm
					.getManage_range());
		}
		// else if ("1".equals(userForm.getRang_type())) {
		// List<SysDepartment> list = dept_dao
		// .showSubSysDepartmentsByDept_no(Constants.UNIT_DEPT_NO);
		// for (SysDepartment dept : list) {
		// user_dept_dao.addSysUserDept(userForm.getUser_id(), dept
		// .getDept_no(),userForm.getIs_junior());
		// }
		// }
		// else {
		// String manage_rang = userForm.getManage_range();
		// if (!"".equals(manage_rang)) {
		// String[] depts = manage_rang.split(",");
		// for (String no : depts) {
		// user_dept_dao.addSysUserDept(userForm.getUser_id(),
		// no,userForm.getIs_junior());
		// }
		// }
	}

	// 查询用户信息
	@Override
	public PageSplit showTempsByDeptManageUser(int pageIndex, int pageSize,
			String user_id) throws GeneralException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.CREATE_NAME + " ='" + user_id + "' and "+SysUserUtil.USER_ID+"!='logger' "+"order by "
				+ SysUserUtil.CREATE_DATA + " desc";
		return showPageSplit(pageIndex, pageSize, sql);
	}
	
	// 查询用户信息
	@Override
	public PageSplit showUserByUserId(int pageIndex, int pageSize,
			String user_id) throws GeneralException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.USER_ID+ " ='" + user_id + "' order by "
				+ SysUserUtil.CREATE_DATA + " desc";
		return showPageSplit(pageIndex, pageSize, sql);
	}

	/**
	 * 根据分页信息返回满足条件的记录
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws GeneralException
	 */
	private PageSplit showPageSplit(int pageIndex, int pageSize, String sql)
			throws GeneralException {	
		SysRoleDaoImpl sdi=new SysRoleDaoImpl();
		PageSplit pageSplit = new PageSplit();
		List<SysUser> list = su_dao.showSysUserByPageSplit(pageIndex, pageSize,
				sql);
		List<SysUser> listv = new ArrayList<SysUser>();
		if(list.size()>0)
		for (SysUser objUser : list) {
			int roleNum = su_dao.getUserRoleNum(objUser.getUser_id());
			String dept_name = su_dao.getDeptName(objUser.getDept_no());
			SysUser sysuser = su_dao.showSysUserByUser_id(objUser.getUser_id());//根据用户名获取用户信息
			objUser.setCreate_user(sysuser.getCreate_name());//创建人
			objUser.setDept_name(dept_name);//部门    
			objUser.setRoleNum(roleNum);// 得到角色数量
		    
		    listv.add(objUser);
			pageSplit.setDatas(listv);
			pageSplit.setNowPage(pageIndex);
			pageSplit.setPageSize(pageSize);
			pageSplit.setTotalCount(su_dao.showCount(sql));
		}
		return pageSplit;
	}

	/**
	 * 返回为user设置了role_id的权限的SysUser
	 * 
	 * @param user
	 * @param role_id
	 * @return
	 * @throws GeneralException
	 */
	private SysUser setUserRole(SysUser user, Integer role_id)
			throws GeneralException {
		SysRole role = role_dao.showSysRoleByRole_id(role_id);
		user.setUser_fun1(role.getRole_fun1());
		user.setUser_fun2(role.getRole_fun2());
		user.setUser_fun3(role.getRole_fun3());
		user.setUser_fun4(role.getRole_fun4());
		user.setUser_fun5(role.getRole_fun5());
		return user;
	}

	@SuppressWarnings("unchecked")
	private SysUser setUserRole(SysUser user, String[] roles)
			throws GeneralException {
		Integer fun1 = 0;
		Integer fun2 = 0;
		Integer fun3 = 0;
		Integer fun4 = 0;
		Integer fun5 = 0;
		for (String role_no : roles) {
			Integer fun1_in = role_dao.showSysRoleByRole_id(
					Integer.valueOf(role_no)).getRole_fun1();
			fun1 = DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil.showFuncs(fun1),
					DaoUtil.showFuncs(fun1_in)));
			Integer fun2_in = role_dao.showSysRoleByRole_id(
					Integer.valueOf(role_no)).getRole_fun2();
			fun2 = DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil.showFuncs(fun2),
					DaoUtil.showFuncs(fun2_in)));
			Integer fun3_in = role_dao.showSysRoleByRole_id(
					Integer.valueOf(role_no)).getRole_fun3();
			fun3 = DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil.showFuncs(fun3),
					DaoUtil.showFuncs(fun3_in)));
			Integer fun4_in = role_dao.showSysRoleByRole_id(
					Integer.valueOf(role_no)).getRole_fun4();
			fun4 = DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil.showFuncs(fun4),
					DaoUtil.showFuncs(fun4_in)));
			Integer fun5_in = role_dao.showSysRoleByRole_id(
					Integer.valueOf(role_no)).getRole_fun5();
			fun5 = DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil.showFuncs(fun5),
					DaoUtil.showFuncs(fun5_in)));
		}
		user.setUser_fun1(fun1);
		user.setUser_fun2(fun2);
		user.setUser_fun3(fun3);
		user.setUser_fun4(fun4);
		user.setUser_fun5(fun5);
		return user;
	}

	@Override
	public boolean isSuperManager(String user_id) throws GeneralException {
		if (Constants.USER_NAME_ADMIN.equals(user_id)) {
			return true;
		} else if (Constants.USER_NAME_LOGGER.equals(user_id)) {
			return true;
		}
		return false;
	}

	public int isExistUser(String user_id) throws GeneralException {
		String str = DaoUtil.quotes(user_id);
		if (su_dao.isExistUser(str)) {
			return 1;
		} else
			return 0;
	}
	@Override
	public int isActive(LoginForm loginForm)throws GeneralException{
		/*String sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"' and "+SysUserUtil.USER_PSD+"='"+loginForm.getUser_psd()+"' and "+SysUserUtil.USER_TYPE+"='"+Constants.ZXUSER_STATUS+"' and "+SysUserUtil.IS_APPROVE+"='1'";
	    int num=su_dao.showCount(sql);
	    if(num>=1){
	    	return 1;//用户注销(已通过审批)
	    }else{
	    	if(loginForm.getUser_psd().equals("") || loginForm.getUser_psd()==null){
	    		return 0;
//	    		sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"'";
	    	}else{//通过审批且未注销
	    		 sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"' and "+SysUserUtil.USER_PSD+"='"+loginForm.getUser_psd()+"' and "+SysUserUtil.IS_APPROVE+"='1'";
	    	}	    	
	    	//logger.info("sql:"+sql);
	    	num=su_dao.showCount(sql);
	    	if(num<=0){
	    		return 0;//用户名/密码错误
	    	}  	
	    }*/
		
	    if(loginForm.getUser_psd().equals("") || loginForm.getUser_psd()==null){//输入为空
	    	return 0;
	    }else{
	    	String sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"' and "+SysUserUtil.USER_PSD+"='"+loginForm.getUser_psd()+"' and "+SysUserUtil.USER_TYPE+"='"+Constants.ZXUSER_STATUS+"' and "+SysUserUtil.IS_APPROVE+"='1'";
	    	int num1=su_dao.showCount(sql);
	    	if(num1>=1){
	    		return 1;//用户注销(已通过审批)	    		
	    	}else{
	    		sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"' and "+SysUserUtil.USER_PSD+"='"+loginForm.getUser_psd()+"' and "+SysUserUtil.IS_APPROVE+"<>'1'";	    		
	    		int num2=su_dao.showCount(sql);
	    		if(num2>=1){
	    			return 0;//用户未通过审批	    			
	    		}else{
	    			sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+loginForm.getUser_no()+"' and "+SysUserUtil.USER_PSD+"='"+loginForm.getUser_psd()+"' and "+SysUserUtil.IS_APPROVE+"='1'";
	    			int num3=su_dao.showCount(sql);
	    			if(num3<=0)
	    			return 0;	
	    		}
	    	}
	    }	    
	    return 2;//正确
	}
	public int isExistServer(String sys_id) throws GeneralException {
		//String str = DaoUtil.quotes(user_id);
		if (su_dao.isExistServer(sys_id)) {
			return 1;
		} else
			return 0;
	}
	public int isExistUserorPwd(String user_id,String user_pwd) throws GeneralException {
		//String str = DaoUtil.quotes(user_id);
		if (su_dao.isExistorPwd(user_id, user_pwd)) {
			return 1;
		} else
			return 0;
	}
	// 根据查询界面的条件查询
	@Override
	public PageSplit showTempsByDeptManage(int pageIndex, int pageSize,
			UserForm userForm,int status) throws GeneralException {
		String userid = userForm.getUser_id();
		String username = userForm.getUser_name();
		String dept_no = userForm.getDept_no();
		String create_name = userForm.getCreate_name();
		String begin_time = userForm.getQstart_time();
		String end_time = userForm.getQend_time();
		StringBuffer sb = new StringBuffer("select * from "
				+ SysUserUtil.TABLE_NAME + " where 1=1 ");
		if (userid != null && !"".equals(userid)) {
			sb.append(" and " + SysUserUtil.USER_ID + " like '%" + userid
					+ "%'");
		}
		if (username != null && !"".equals(username)) {
			sb.append(" and " + SysUserUtil.USER_NAME + " like '%" + username
					+ "%'");
		}
		if (dept_no != null && !"".equals(dept_no)) {
			if (userForm.getIs_junior().equals("1")) {
				sb.append(" and " + SysUserUtil.DEPT_NO + " like '%" + dept_no
						+ "%'");
			}
			if (userForm.getIs_junior().equals("0")) {
				sb.append(" and " + SysUserUtil.DEPT_NO + " = '" + dept_no
						+ "'");
			}
		}
		if (create_name != null && !"".equals(create_name)) {
			sb.append(" and " + SysUserUtil.CREATE_NAME + " like '%"
					+ create_name + "%'");
		}
		if (begin_time != null && !"".equals(begin_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " > to_date('"
						+ begin_time + " 00:00:00','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " > '"
						+ begin_time + " 00:00:00'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " > '"
						+ begin_time + " 00:00:00'");
			}
		}
		if (end_time != null && !"".equals(end_time)) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " < to_date('"
						+ end_time + " 23:59:59','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " < '" + end_time
						+ " 23:59:59'");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_DB2) {
				sb.append(" and " + SysUserUtil.CREATE_DATA + " < '" + end_time
						+ " 23:59:59'");
			}
		}
		if(status==0){
			sb.append(" and "+SysUserUtil.USER_ID+" != '"+Constants.USER_NAME_LOGGER+"' and "+SysUserUtil.IS_APPROVE+"<>'1'");			
		}else{
			sb.append(" and "+SysUserUtil.USER_ID+" != '"+Constants.USER_NAME_LOGGER+"' and "+SysUserUtil.IS_APPROVE+"='1'");
		}
		sb.append(" order by " + SysUserUtil.CREATE_DATA + " desc");
		return showPageSplit(pageIndex, pageSize, sb.toString());
	}



	// 根据部门id查询
	@Override
	public PageSplit showTempsByDeptTree(int pageIndex, int pageSize,
			String dept_no,int status) throws GeneralException {
		String sql=""; 			
		if(status==0){//用户审批模块sql
			sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
					+ SysUserUtil.DEPT_NO + "='" + dept_no + "' and "+SysUserUtil.IS_APPROVE+"<>'1' order by "
					+ SysUserUtil.CREATE_DATA + " desc";	
		}else{//用户查询或用户管理模块sql
			sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
					+ SysUserUtil.DEPT_NO + "='" + dept_no + "' and "+SysUserUtil.IS_APPROVE+"='1' order by "
					+ SysUserUtil.CREATE_DATA + " desc";	
		}	
		return showPageSplit(pageIndex, pageSize, sql);
	}

	@Override
	public List<SysUser> showUsersByRole(Integer role_id) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select t1.* from ").append(SysUserUtil.TABLE_NAME);
		sb.append(" t1,").append(SysRoleUtil.TABLE_NAME).append(" t2,");
		sb.append(SysUserRoleUtil.TABLE_NAME).append(" t3 where t1.");
		sb.append(SysUserUtil.USER_ID).append("=t3.");
		sb.append(SysUserRoleUtil.USER_ID).append(" and t2.");
		sb.append(SysRoleUtil.ROLE_ID).append("=t3.");
		sb.append(SysUserRoleUtil.ROLE_ID).append(" and t2.");
		sb.append(SysRoleUtil.ROLE_ID).append("='").append(role_id);
		sb.append("'");
		List<SysUser> list = su_dao.showSysUsersBySql(sb.toString());
		return list;
	}
	@Override
	public String getUserIdBy_key(String key_sn) throws GeneralException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.KEY_SN + "='" + key_sn + "'";
		List<SysUser> list = su_dao.showSysUsersBySql(sql);
		if (list.size()>0) {
			return "1";
		}
		return "0";
	}
	/**
	 * 根据证书DN判断是否注册过用户
	 * @param key_dn 证书dn值
	 * @return
	 * @throws GeneralException
	 */
	@Override
	public String getUserIdBy_keyDN(String key_dn) throws GeneralException {
		String sql = "select * from " + SysUserUtil.TABLE_NAME + " where "
				+ SysUserUtil.KEY_DN + "='" + key_dn + "'";
		List<SysUser> list = su_dao.showSysUsersBySql(sql);
		if (list.size()>0) {
			return "1";
		}
		return "0";
	}
	/**
	 * 根据证书DN判断是否注册过用户
	 * @param key_dn 证书dn值
	 * @return
	 * @throws GeneralException
	 */
	@Override
	public SysUser getUserBy_keyDN(String key_dn){
		SysUser user=su_dao.getUserBy_keyDN(key_dn);
		return user;
	}
	/**
	 * 根据证书SN判断是否注册过用户
	 * @param key_sn 证书Sn值
	 * @return
	 * @throws GeneralException
	 */
	@Override
	public SysUser getUserBy_keySN(String key_sn){
		SysUser user=su_dao.getUserBy_keySN(key_sn);
		return user;
	}
	@Override
	public void deleteSysUser(String user_id) {
		SysUser user=su_dao.showSysUserByUser_id(user_id);
		su_dao.deleteSysUser(user_id);
		String sqlUserCert="delete from "+UserCertUtil.TABLE_NAME+" where "+UserCertUtil.USER_ID+"='"+user_id+"'";
		su_dao.AddSysUserDEPT(sqlUserCert);
		String sqlCert="";
		if(user.getKey_sn()!=""){
			 sqlCert="delete from "+CertUtil.TABLE_NAME+" where "+CertUtil.CERT_NAME+"='"+user.getKey_sn()+"'";
		}else{
			 sqlCert="delete from "+CertUtil.TABLE_NAME+" where "+CertUtil.CERT_NAME+"='"+user.getKey_sn()+"'";
		}
		su_dao.AddSysUserDEPT(sqlCert);
		
		String sqlUserdept="delete from "+SysUserDeptUtil.TABLE_NAME+" where "+SysUserDeptUtil.USER_ID+"='"+user_id+"'";
		su_dao.AddSysUserDEPT(sqlUserdept);
	    String sqlUserRole="delete from "+SysUserRoleUtil.TABLE_NAME+" where "+SysUserRoleUtil.USER_ID+"='"+user_id+"'";
	    su_dao.AddSysUserDEPT(sqlUserRole);
        String sqlUserSeal="delete from "+UserSealUtil.TABLE_NAME+" where "+UserSealUtil.USER_ID+"='"+user_id+"'";
        su_dao.AddSysUserDEPT(sqlUserSeal);
	}

	@Override
	public String zhuxiaoList(String user_id) {
		//logger.info("user_id:"+user_id);
		String sql="";
		sql="select * from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+"='"+user_id+"'";	
		List<SysUser> user_list=new ArrayList<SysUser>();
		user_list=su_dao.showSysUsersBySql(sql);
		int length=user_list.size();
		//logger.info("user_list:"+user_list.size());
		String name="";
		if(user_list.size()>0){
			for(SysUser sysuser:user_list){
				//logger.info("user_type:"+sysuser.getUser_type());
				if(!sysuser.getUser_type().equals("4")){//正常状态
					//logger.info("length:"+length);
					name=sysuser.getUser_name().split("_")[0]+"_已注销_"+length;
					//logger.info("name:"+name);
				}
			}
		}
		//logger.info("name--:"+name);
		String upsql="update "+SysUserUtil.TABLE_NAME+" set "+SysUserUtil.USER_TYPE+"='"+Constants.ZXUSER_STATUS+"' , "+SysUserUtil.USER_NAME+"='"+name+"' ,"+SysUserUtil.IS_ACTIVE+"='"+Constants.USER_ACTIVE_NO+"' where "+SysUserUtil.USER_ID+"='"+user_id+"' and " +SysUserUtil.USER_TYPE+"='"+Constants.USER_STATUS+"'";
		//logger.info("upsql:"+upsql);
		boolean bl=su_dao.zhuxiaoList(upsql);
		if(bl){
			return "1";
		}else{
			return "0";
		}
	}
	
	public String zhuxiaoSimple(String user_id) {
		String upsql="update "+SysUserUtil.TABLE_NAME+" set "+SysUserUtil.USER_TYPE+"='"+Constants.ZXUSER_STATUS+"' where "+SysUserUtil.USER_ID+"='"+user_id+"' and " +SysUserUtil.USER_TYPE+"='"+Constants.USER_STATUS+"'";
		//logger.info("upsql:"+upsql);
		boolean bl=su_dao.zhuxiaoList(upsql);
		if(bl){
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 获取用户最大数量
	 * @param path
	 * @return false:已超出最大数量，true在数量之内
	 * @throws Exception
	 */
	@Override
	public String selUserNum()throws Exception{
		String sql="";
		if(Constants.DB_TYPE == DBTypeUtil.DT_ORCL){
			sql="select "+SysUserUtil.USER_ID+" from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+" not in '"+Constants.USER_NAME_LOGGER+"'";
		}else if(Constants.DB_TYPE == DBTypeUtil.DT_MYSQL){
			sql="select "+SysUserUtil.USER_ID+" from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+" != '"+Constants.USER_NAME_LOGGER+"'";
		}else if(Constants.DB_TYPE == DBTypeUtil.DT_DB2){
			sql="select "+SysUserUtil.USER_ID+" from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.USER_ID+" != '"+Constants.USER_NAME_LOGGER+"'";
		}
		int user_num =su_dao.showCount(sql);
//		logger.info("size:"+user_num);
		DesUtils des = new DesUtils(DesUtils.strUserNumKey);
		if(user_num>=Integer.parseInt(des.decrypt(Constants.getLicenseProperty("MAX_USERNUM")))){
			return "false";
		}else{
			return "true";
		}
	}
	@Override
	public void quzhuxiaoList(String user_id) {
		su_dao.quzhuxiaoList(user_id);
	}

	@Override
	public String serSysUserRole(String user_id) throws GeneralException {

		List<SysRole> roles = role_dao.showSysRolesByUser_id(user_id);
		String rolenos = "";

		for (SysRole role : roles) {
			int roleno = role.getRole_id();
			rolenos += roleno + ",";
		}
		return rolenos;
	}

	@Override
	public SysUser editSysUser(String user_id) {
		return su_dao.editSysUser(user_id);
	}
	@Override
	public SysUser editSysUserJH(String user_id) {
		return su_dao.showSysUserByUser_id_JH(user_id);
	}
	@Override
	public String serSysUnitUtil(String deptno) {
		String unitname = "";
		
		if (deptno.equals(Constants.UNIT_DEPT_NO)) {

			SysUnit unit = unit_dao.showSysUnit();
			unitname = unit.getUnit_name();
		} else {
			SysDepartment department = dept_dao
					.showSysDepartmentByDept_no(deptno);
			unitname = department.getDept_name();
		}
		return unitname;
	}

//	public void updateSysUser(UserForm form, String usertype2, String is_key)
//			throws GeneralException {
//
//		try {
//
//			form.setCreate_data((new Timestamp(new java.util.Date().getTime())
//					.toString()));
//			form.setUser_birth((new Timestamp(new java.util.Date().getTime())
//					.toString()));
//			SysUser sysuser = new SysUser();
//			BeanUtils.copyProperties(sysuser, form);
//			String role_nos = form.getRole_nos();
//			// 判断是否使用证书
//			if (is_key.equals("2")) {
//				// logger.info("不使用key");
//				sysuser.setUseing_key(Constants.NO_USE);
//				sysuser.setKey_sn("");
//			}
//			if (is_key.equals("3")) {
//				// logger.info("使用key");
//				sysuser.setUseing_key(Constants.IS_USE);
//			}
//			user_role_dao.deleteUserRoleByUser_id(form.getUser_id());// 更新之前先删除用户角色表信息
//			logger.info("role_nos" + role_nos);
//			String[] roles = role_nos.split(",");
//			for (String role_no : roles) {
//				Integer role_id = Integer.valueOf(role_no);
//				user_role_dao.addUserRole(form.getUser_id(), role_id, "1");
//			}
//			setUserRole(sysuser, roles);
//			su_dao.updateSysUser(sysuser, usertype2);
//		} catch (IllegalAccessException e) {
//			logger.error(e.getMessage());
//		} catch (InvocationTargetException e) {
//			logger.error(e.getMessage());
//		}
//
//	}
	
	@Override
	public void updateSysUser(UserForm form,String dept_no,String user_remark2)throws GeneralException {
	try {
		form.setDept_no(dept_no);
		form.setUser_remark(user_remark2);
		form.setCreate_data((new Timestamp(new java.util.Date().getTime())
				.toString()));
		form.setUser_birth((new Timestamp(new java.util.Date().getTime())
				.toString()));
		form.setOperate_time((new Timestamp(new java.util.Date().getTime())
		.toString()));
		
		SysUser sysuser = new SysUser();
		BeanUtils.copyProperties(sysuser, form);
        if(form.getUser_type().equals("1")){
        	sysuser.setUser_type("1");//激活状态
		}
		String role_nos = form.getRole_nos();
		user_role_dao.deleteUserRoleByUser_id(form.getUser_id());// 更新之前先删除用户角色表信息
		String[] roles = role_nos.split(",");
		for (String role_no : roles) {
			Integer role_id = Integer.valueOf(role_no);
			user_role_dao.addUserRole(form.getUser_id(), role_id, "1");
		}
		setUserRole(sysuser, roles);
		
//		
//		String sql="select "+SysUserUtil.KEY_SN+" from "+SysUserUtil.TABLE_NAME+" where "+SysUserUtil.KEY_SN+"='"+form.getKey_sn()+"'";
//		List list=dao.select(sql);
//		if(list.size()==0){
			//更新证书表信息
//			DateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
//			Timestamp begtime=null,endtime=null;
//			logger.info(form.getBegin_time());
//			try {
//				begtime = new Timestamp(df.parse(form.getBegin_time()).getTime());
//				endtime = new Timestamp(df.parse(form.getEnd_time()).getTime());
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			logger.info("begtime:"+begtime);
//			Cert obj=new Cert();
//			obj.setCert_name(form.getKey_sn());
//			obj.setCert_dn(form.getKey_dn());
//			obj.setCert_user(form.getCert_user());
//			obj.setBegin_time(begtime);
//			obj.setBegin_time(endtime);
//			obj.setReg_user(form.getCreate_user());
//			obj.setReg_time(new Timestamp(new Date().getTime()));
//	   	    form.setKey_cert(form.getKey_cert());
//			String parasStr = CertUtil.CERT_USER + "='" + form.getUser_id() + "'";
////			logger.info("sql:"+ConstructSql.composeUpdateSql(obj, new CertUtil(),
////					parasStr));
//			dao.update(ConstructSql.composeUpdateSql(obj, new CertUtil(),
//							parasStr));
//		}
		su_dao.updateSysUser(sysuser);
		
	} catch (IllegalAccessException e) {
		logger.error(e.getMessage());
	} catch (InvocationTargetException e) {
		logger.error(e.getMessage());
	}
	
	}
	@Override
	public void updateSysUser(UserForm form)throws GeneralException {
		try {
			form.setUser_birth((Timestamp.valueOf(form.getUser_birth().toString()+" "+"00:00:00")).toString());
			form.setOperate_time((new Timestamp(new java.util.Date().getTime()).toString()));			
			SysUser sysuser = new SysUser();
			BeanUtils.copyProperties(sysuser, form);
			//if(!sysuser.getUser_status().equals("1")){//管理员修改后为激活、通过审批，其它身份修改后注销、待审批
				if(!form.getUser_type().equals("1")){
					sysuser.setUser_type("1");
				}
				//sysuser.setIs_approve("0");//待审批
			//}
			String role_nos = form.getRole_nos();
			user_role_dao.deleteUserRoleByUser_id(form.getUser_id());// 更新之前先删除用户角色表信息
			String[] roles = role_nos.split(",");
			for (String role_no : roles) {
				Integer role_id = Integer.valueOf(role_no);
				user_role_dao.addUserRole(form.getUser_id(), role_id, "1");
			}
			setUserRole(sysuser, roles);
			su_dao.updateSysUser(sysuser);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		
		}
	@Override
	public void jihuoSysUser(UserForm form,String user_id_old,String user_name_old,String dept_no,String user_remark2)throws GeneralException {
		//logger.info("user_id_old:"+user_id_old);
		try {
			if(form.getUseing_key().equals("false")){
				form.setUseing_key("0");
			}else{
				form.setUseing_key("1");
			}
			form.setDept_no(dept_no);
			form.setUser_remark(user_remark2);
			form.setCreate_data((new Timestamp(new java.util.Date().getTime())
					.toString()));
			form.setUser_birth((new Timestamp(new java.util.Date().getTime())
					.toString()));
			
			SysUser sysuser = new SysUser();
			BeanUtils.copyProperties(sysuser, form);
	        if(form.getUser_type().equals("1")){
	        	sysuser.setUser_type("1");//激活状态
			}
			String role_nos = form.getRole_nos();
			user_role_dao.deleteUserRoleByUser_id(form.getUser_id());// 更新之前先删除用户角色表信息
			String[] roles = role_nos.split(",");
			for (String role_no : roles) {
				Integer role_id = Integer.valueOf(role_no);
				user_role_dao.addUserRole(form.getUser_id(), role_id, "1");
			}
			setUserRole(sysuser, roles);
			su_dao.updateSysUser(user_id_old,user_name_old,sysuser);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
		
		}
	
	public String  jihuoSysUserSimple(String user_id)throws GeneralException {
		String upsql="update "+SysUserUtil.TABLE_NAME+" set "+SysUserUtil.USER_TYPE+"='"+Constants.USER_STATUS+"' where "+SysUserUtil.USER_ID+"='"+user_id+"' and " +SysUserUtil.USER_TYPE+"='"+Constants.ZXUSER_STATUS+"'";
		//logger.info("upsql:"+upsql);
		boolean bl=su_dao.zhuxiaoList(upsql);
		if(bl){
			return "1";
		}else{
			return "0";
		}
		}
	
	@Override
	public void updateSysUserDEPT(String user_id, String managedept)
			throws GeneralException {
		String managedept1[] = managedept.split(",");
		String sql = "delete from T_AH where C_AHA='" + user_id + "'";
		su_dao.deleteUserDetp(sql);
		for (int i = 0; i < managedept1.length; i++) {
			if (!managedept1[i].equals("")) {
				String insertsql = "insert into T_AH values('" + user_id
						+ "','" + managedept1[i] + "')";
				su_dao.AddSysUserDEPT(insertsql);
			}
		}
	}

	@Override
	public void updateSysUserPsd(PsdForm psdForm) throws GeneralException {
		SysUser sysuser = su_dao.showSysUserByUser_id(psdForm.getUser_id());
		if (sysuser.getUser_psd().equals(MD5Util.MD5(psdForm.getOld_psd()))) {
			logger.info("新密码："+psdForm.getNew_psd());
			sysuser.setUser_psd(MD5Util.MD5(psdForm.getNew_psd()));
			sysuser.setCurrentpassword(psdForm.getNew_psd());
			sysuser.setOperate_time(new Timestamp(new java.util.Date().getTime()));
			sysuser.setLogined(sysuser.getLogined()+1);
			sysuser.setPassword1(psdForm.getPassword1());
			sysuser.setPassword1md5(psdForm.getPassword1md5());
			sysuser.setPassword2(psdForm.getPassword2());
			sysuser.setPassword2md5(psdForm.getPassword2md5());
			su_dao.updateSysUser(sysuser);
			logger.info("密码修改成功！");
		} else {
			logger.info("密码不正确");
		}

	}

	@Override
	public void updateSysPerson(PersonForm personForm) throws GeneralException {
		
		try {
			SysUser sysUser = su_dao.showSysUserByUser_id(personForm
					.getUser_id());
			BeanUtils.copyProperties(sysUser, personForm);
			su_dao.updateSysUser(sysUser);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}
	@Override
	public SysUser showSysUserByUser_id(String user_id){

		return su_dao.showSysUserByUser_id(user_id);
	}
	public String regUser(String key_sn,String begin_time,String key_dn,String user_id,String user_name,String user_pwd,String role_id,String dept_no,String mark_text){
		String sql="";
		return "";
	}
	// 根据用户名获得所管理部门编号的拼凑语句s
	@Override
	public String getAllDept(String user_id) throws GeneralException {
		return dept_dao.manageRangeOfUser(user_id);
	}	
	@Override
	public String checkPsd(String user_id,String oldPsd) throws GeneralException {
		// TODO Auto-generated method stub
		SysUser sysUser=su_dao.showSysUserByUser_id(user_id);
		oldPsd=MD5Util.MD5(oldPsd);
		if(oldPsd.equals(sysUser.getUser_psd())){
			return "true";
		}
		return "false";
	}
	
	public boolean retPWD(String userID,String initial_pwd){
		String pwd=MD5Util.MD5(initial_pwd);
		String sql="update "+SysUserUtil.TABLE_NAME+" set "+SysUserUtil.USER_PSD+"='"+pwd+"'"+" where "+SysUserUtil.USER_ID+"='"+userID+"'";
		su_dao.updPWDByID(sql);
		return true;
	}

	@Override
	public int approveUserByUniId(String id, String flag,String state,String current_user) {//用户审批
		return su_dao.approveUserByUniId(id, flag,state,current_user);
	}

	@Override
	public String loginFirstTime(String userId) {//未使用
		SysUser sysUser=su_dao.showSysUserByUser_id(userId);
		if(sysUser.getLogined()==0)
			return "true";
		else
			return "false";
	}

	@Override
	public String isEffectivePassword(String userId) {//未使用
		SysUser sysUser=su_dao.showSysUserByUser_id(userId);
		long existTime=(System.currentTimeMillis()-sysUser.getOperate_time().getTime())/(1000*60*60*24);//密码已使用时长(天)
		if(existTime-30>0)
			return "true";
		else
			return "false";
	}

	@Override
	public String getPasswordLastTime() {
		String sql="select CANSSJ from "+PsdLastTimeUtil.TABLE_NAME+" where CANSHZ='PASSWORDLASTTIME'";
		//logger.info("sql:"+sql);
		return su_dao.getPasswordLastTime(sql);
	}
}