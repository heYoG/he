package com.dj.seal.organise.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import com.dj.seal.organise.dao.api.ISysDepartmentDao;
import com.dj.seal.organise.dao.api.ISysUnitDao;
import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.organise.dao.api.ISysUserRoleDao;
import com.dj.seal.organise.dao.impl.SysRoleDaoImpl;
import com.dj.seal.organise.service.api.ISysRoleService;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.web.SysRoleVo;
import com.dj.seal.organise.web.form.RoleForm;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.SysUserRole;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.struts.RoleDetail;
import com.dj.seal.util.table.RoleRuleUtil;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.SysUserRoleUtil;
import com.dj.seal.util.web.SearchForm;

public class SysRoleService implements ISysRoleService {
	
	static Logger logger = LogManager.getLogger(SysRoleService.class.getName());

	private SysRoleDaoImpl role_dao;
	private ISysUserRoleDao user_role_dao;
	private ISysDepartmentDao dept_dao;
	private ISysUnitDao unit_dao;
	private ISysUserDao user_dao;
	private IUserService user_srv;
	private Scheduler sche;

	public Scheduler getSche() {
		return sche;
	}

	public void setSche(Scheduler sche) {
		this.sche = sche;
	}
	@Override
	public List<SysUserRole> showRoleList(String sql) throws GeneralException{
		
		return user_role_dao.showSysUserRolesByRole_id2(sql);
	}
	@Override
	public void addSysRole2(String role_nos,String seal_id) throws GeneralException {
		String role_on[]=role_nos.split(",");
		for(int i=0;i<role_on.length;i++){
			if(!role_on[i].equals("")){
				String sql2="select * from t_rbd where C_RDC='"+role_on[i]+"' and C_RDB='"+seal_id+"'";
				List list=role_dao.select(sql2);
				if(list.size()<=0){
				  String sql="insert into T_RBD values('"+seal_id+"','"+role_on[i]+"')";
				  role_dao.add(sql);
				}	
//				if(list.size()<=0){
//				 String sql="delete from "+RoleSealUtil.TABLE_NAME+" where "+RoleSealUtil.USER_ID+"='"+role_on[i]+"'";
//				 role_dao.delete(sql);
//				 String insql="insert into T_RBD values('"+seal_id+"','"+role_on[i]+"')";
//   			     role_dao.add(insql);
//				}
			}
		}
	}
	
	public void objDel(String no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(SysRoleUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(SysRoleUtil.ROLE_ID).append("='");
		sb.append(no).append("'");
		role_dao.delete(sb.toString());
	}

	public void plDel(String selStr) throws Exception {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(SysRoleUtil.TABLE_NAME);
		sb.append(" where ").append(SysRoleUtil.ROLE_STATUS);
		sb.append("!='0' and (1=2 ");
		for (String str : r_nos) {
			sb.append(" or ").append(SysRoleUtil.ROLE_ID);
			sb.append("='").append(str).append("'");
		}
		sb.append(")");
		role_dao.delete(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public PageSplit showObjBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		String str = SearchUtil.roleSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<SysRoleVo> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}

	@SuppressWarnings("unchecked")
	private List<SysRoleVo> listObjs(String sql) throws Exception {
		List<SysRoleVo> list_obj = new ArrayList<SysRoleVo>();
		List<Map> list = role_dao.select(sql);
		for (Map map : list) {
			SysRole obj = new SysRole();
			obj = (SysRole) DaoUtil.setPo(obj, map, new SysRoleUtil());
			SysRoleVo vo = new SysRoleVo();
			BeanUtils.copyProperties(vo, obj);
			list_obj.add(resetVo(vo));
			
		}
		return list_obj;
	}
	private SysRoleVo resetVo(SysRoleVo vo) throws Exception {
		vo.setFunc_v(vo.getRole_fun1().toString());
		vo.setFunc_v2(vo.getRole_fun2().toString());
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(RoleRuleUtil.TABLE_NAME);
		sb.append(" where ").append(RoleRuleUtil.ROLE_ID);
		sb.append("='").append(vo.getRole_id()).append("'");
		int rule_num = role_dao.count(sb.toString());
		String sel_rules = role_dao.getStr(sb.toString(), RoleRuleUtil.RULE_NO);
		vo.setRule_num(rule_num);
		vo.setSel_rules(sel_rules);
		StringBuffer sb2 = new StringBuffer();
		sb2.append("select * from ").append(SysUserRoleUtil.TABLE_NAME);
		sb2.append(" where ").append(SysUserRoleUtil.ROLE_ID);
		sb2.append("='").append(vo.getRole_id()).append("'");
		int user_num = role_dao.count(sb2.toString());
		vo.setUser_num(user_num);
		String sel_users = role_dao.getStr(sb2.toString(),
				SysUserRoleUtil.USER_ID);
		vo.setSel_users(sel_users);
		return vo;
	}
	

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

	public ISysUnitDao getUnit_dao() {
		return unit_dao;
	}

	public void setUnit_dao(ISysUnitDao unit_dao) {
		this.unit_dao = unit_dao;
	}

	public ISysDepartmentDao getDept_dao() {
		return dept_dao;
	}

	public void setDept_dao(ISysDepartmentDao dept_dao) {
		this.dept_dao = dept_dao;
	}

	public ISysUserRoleDao getUser_role_dao() {
		return user_role_dao;
	}

	public void setUser_role_dao(ISysUserRoleDao user_role_dao) {
		this.user_role_dao = user_role_dao;
	}

	public SysRoleDaoImpl getRole_dao() {
		return role_dao;
	}

	@Override
	public PageSplit showSysRolesByPageSplit(int pageIndex, int pageSize)
			throws GeneralException {
		PageSplit pageSplit = new PageSplit();
		String sql = "select * from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_STATUS + "<>'0' order by "
				+ SysRoleUtil.ROLE_TAB;
		List<SysRole> list = role_dao.showSysRolesByPageSplit(pageIndex,
				pageSize, sql);
		pageSplit.setDatas(list);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(role_dao.showCount(sql));
		return pageSplit;
	}

	@Override
	public void addSysRole(RoleForm roleForm) throws GeneralException {
		try {
			SysRole sysRole = new SysRole();
			BeanUtils.copyProperties(sysRole, roleForm);
			sysRole.setDept_no(Constants.UNIT_DEPT_NO);
			sysRole.setRole_fun1(0);
			sysRole.setRole_fun2(0);
			sysRole.setRole_fun3(0);
			sysRole.setRole_fun4(0);
			sysRole.setRole_fun5(0);
			sysRole.setRole_status("1");
			role_dao.addSysRole(sysRole);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}

	public void addRole(RoleForm form) throws Exception {
		SysRole obj = new SysRole();
		BeanUtils.copyProperties(obj, form);
		obj.setDept_no(Constants.UNIT_DEPT_NO);
		obj.setRole_fun1(Integer.valueOf(form.getFunc_v()));
		obj.setRole_fun2(Integer.valueOf(form.getFunc_v2()));
		obj.setRole_status("1");
		String role_id = role_dao.getNo(SysRoleUtil.TABLE_NAME,
					SysRoleUtil.ROLE_ID);
		obj.setRole_id(Integer.valueOf(role_id));
		form.setRole_id(Integer.valueOf(role_id));
		role_dao.addSysRole(obj);
		SysRoleVo vo = new SysRoleVo();
		BeanUtils.copyProperties(vo, form);
	}
   
	@Override
	public int isExistRole(String role_name) throws GeneralException {
		if (role_dao.isExistRole(role_name)) {
			return 1;
		} else
			return 0;
	}
	@Override
	public int isExistRoleno(String role_no) throws GeneralException {
		   int role_noInt=Integer.parseInt(role_no);
		if (role_dao.isExistRoleno(role_noInt)) {
			return 1;
		} else
			return 0;
	}
	
	public int isExistRolename(String role_name) throws GeneralException {
		if (role_dao.isExistRolename(role_name)) {
			return 1;
		} else
			return 0;
	}

	@Override
	public SysRole showSysRoleByRole_id(Integer role_id)
			throws GeneralException {
		return role_dao.showSysRoleByRole_id(role_id);
	}
	
	@Override
	public void setRoleFunc(RoleForm roleForm) throws GeneralException {
		try {
			SysRole sysRole = role_dao.showSysRoleByRole_id(roleForm
					.getRole_id());
			BeanUtils.copyProperties(sysRole, roleForm);
			updateRoleFunc(sysRole);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}

	@Override
	public void updateSysRole(RoleForm roleForm) throws GeneralException {
		SysRole role = role_dao.showSysRoleByRole_id(roleForm.getRole_id());
		role.setRole_name(roleForm.getRole_name());
		role.setRole_tab(roleForm.getRole_tab());
		updateRoleFunc(role);
	}

	@Override
	public void cloneRole(Integer role_id, String role_tab, String role_name)
			throws GeneralException {
		SysRole role = role_dao.showSysRoleByRole_id(role_id);
		role.setRole_id(null);
		role.setRole_name(role_name);
		role.setRole_tab(role_tab);
		role_dao.addSysRole(role);
	}

	@Override
	public void deleteSysRole(Integer role_id) throws GeneralException {
		// 根据角色ID得到用户名称列表
		List<SysUserRole> u_rs = user_role_dao
				.showSysUserRolesByRole_id(role_id);
		// 删除用户角色表记录
		user_role_dao.deleteUserRoleByRole_id(role_id);
		// 更新用户的权限值（与该角色有关的用户）
		for (SysUserRole u_r : u_rs) {
			user_srv.updateFuncsValue(u_r.getUser_id());
		}
		SysRole sysRole = role_dao.showSysRoleByRole_id(role_id);
		role_dao.deleteRole(sysRole);// 删除角色表记录
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RoleDetail> showRoleDetail(Integer role_id)
			throws GeneralException {
		Map map = new HashMap<String, RoleDetail>();
		List<SysUserRole> list_user_role = user_role_dao
				.showSysUserRolesByRole_id(role_id);
		for (SysUserRole sysUserRole : list_user_role) {
			String user_id = sysUserRole.getUser_id();
			SysDepartment dept = dept_dao.showSysDepartmentByUser_id(user_id);
			if (dept == null) {
				dept = new SysDepartment();
				dept.setDept_name(unit_dao.showSysUnit().getUnit_name());
			}

			if (map.get(dept.getDept_name()) != null) {
				RoleDetail detail = (RoleDetail) map.get(dept.getDept_name());
				if (sysUserRole.getUser_role_status().equals("1")) {
					detail.setCount1(detail.getCount1() + 1);
					detail.setUsers1(detail.getUsers1() + "," + user_id);
				} else {
					detail.setCount0(detail.getCount0() + 1);
					detail.setUsers0(detail.getUsers0() + "," + user_id);
				}
			} else {
				RoleDetail detail = new RoleDetail();
				detail.setDept_name(dept.getDept_name());
				if (sysUserRole.getUser_role_status().equals("1")) {
					detail.setCount1(1);
					detail.setUsers1(user_id);
					detail.setCount0(0);
				} else {
					detail.setCount0(1);
					detail.setCount1(0);
					detail.setUsers0(user_id);
				}
				map.put(dept.getDept_name(), detail);
			}
		}
		Collection<RoleDetail> collc = map.values();
		List<RoleDetail> list = new ArrayList<RoleDetail>();
		for (RoleDetail roleDetail : collc) {
			list.add(roleDetail);
		}
		return list;
	}
	@Override
	public List<SysRole> showRolesByTab(String user_status)
	   throws GeneralException {
       StringBuffer sb = new StringBuffer("select * from "
		+ SysRoleUtil.TABLE_NAME);
         sb.append(" order by " + SysRoleUtil.ROLE_TAB);
       return role_dao.showSysRolesBySql(sb.toString());
     }
	@Override
	public void sortRoleTab(String role_ids, String role_tabs)
			throws GeneralException {
		String[] ids = role_ids.split(",");
		String[] tabs = role_tabs.split(",");
		for (int i = 0; i < ids.length; i++) {
			String sql = "update " + SysRoleUtil.TABLE_NAME + " set "
					+ SysRoleUtil.ROLE_TAB + "='" + tabs[i] + "' where "
					+ SysRoleUtil.ROLE_ID + "='" + ids[i] + "'";
			role_dao.updateSysRoleBySql(sql);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addFunc(String role_ids, RoleForm roleForm)
			throws GeneralException {
		String[] ids = role_ids.split(",");
		for (String id : ids) {
			SysRole role = role_dao.showSysRoleByRole_id(Integer.parseInt(id));
			// 权限组一
			Set set10 = DaoUtil.showFuncs(role.getRole_fun1());
			Set set11 = DaoUtil.showFuncs(roleForm.getRole_fun1());
			role.setRole_fun1(DaoUtil.sumOfSet(DaoUtil.bingJi(set10, set11)));
			// 权限组二
			role.setRole_fun2(DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil
					.showFuncs(role.getRole_fun2()), DaoUtil.showFuncs(roleForm
					.getRole_fun2()))));
			// 权限组三
			role.setRole_fun3(DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil
					.showFuncs(role.getRole_fun3()), DaoUtil.showFuncs(roleForm
					.getRole_fun3()))));
			// 权限组四
			role.setRole_fun4(DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil
					.showFuncs(role.getRole_fun4()), DaoUtil.showFuncs(roleForm
					.getRole_fun4()))));
			// 权限组五
			role.setRole_fun5(DaoUtil.sumOfSet(DaoUtil.bingJi(DaoUtil
					.showFuncs(role.getRole_fun5()), DaoUtil.showFuncs(roleForm
					.getRole_fun5()))));
			updateRoleFunc(role);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public void deleteFunc(String role_ids, RoleForm roleForm)
			throws GeneralException {
		String[] ids = role_ids.split(",");
		for (String id : ids) {
			SysRole role = role_dao.showSysRoleByRole_id(Integer.parseInt(id));
			// 权限组一
			Set set10 = DaoUtil.showFuncs(role.getRole_fun1());
			Set set11 = DaoUtil.showFuncs(roleForm.getRole_fun1());
			role.setRole_fun1(DaoUtil.sumOfSet(DaoUtil.buJi(set10, set11)));
			// 权限组二
			role.setRole_fun2(DaoUtil.sumOfSet(DaoUtil.buJi(DaoUtil
					.showFuncs(role.getRole_fun2()), DaoUtil.showFuncs(roleForm
					.getRole_fun2()))));
			// 权限组三
			role.setRole_fun3(DaoUtil.sumOfSet(DaoUtil.buJi(DaoUtil
					.showFuncs(role.getRole_fun3()), DaoUtil.showFuncs(roleForm
					.getRole_fun3()))));
			// 权限组四
			role.setRole_fun4(DaoUtil.sumOfSet(DaoUtil.buJi(DaoUtil
					.showFuncs(role.getRole_fun4()), DaoUtil.showFuncs(roleForm
					.getRole_fun4()))));
			// 权限组五
			role.setRole_fun5(DaoUtil.sumOfSet(DaoUtil.buJi(DaoUtil
					.showFuncs(role.getRole_fun5()), DaoUtil.showFuncs(roleForm
					.getRole_fun5()))));
			updateRoleFunc(role);
		}
	}

	@Override
	public void updateRoleFunc(SysRole role) throws GeneralException {
		// 更新自身权限值
		role_dao.updateSysRole(role);
		// 更新属于这个角色的用户的权限值
		List<SysUser> users = user_dao.showSysUserByRole_id(role.getRole_id());
		for (SysUser user : users) {
			// user_srv.updateFuncsValue(user.getUser_id());//与下面这句话作用等效
			updateUserFunc(role, user);
		}
	}

	/**
	 * 为user更新角色role
	 * 
	 * @param role
	 * @param user
	 * @throws GeneralException
	 */
	private void updateUserFunc(SysRole role, SysUser user)
			throws GeneralException {
		List<SysRole> list = role_dao.showSysRolesByUser_id(user.getUser_id());
		int[] funcs = { 0, 0, 0, 0, 0 };
		for (SysRole ro : list) {
			if (ro.getRole_id() != role.getRole_id()) {
				funcs[0] |= ro.getRole_fun1();
				funcs[1] |= ro.getRole_fun2();
				funcs[2] |= ro.getRole_fun3();
				funcs[3] |= ro.getRole_fun4();
				funcs[4] |= ro.getRole_fun5();
			}
		}
		user.setUser_fun1(funcs[0] | role.getRole_fun1());
		user.setUser_fun2(funcs[1] | role.getRole_fun2());
		user.setUser_fun3(funcs[2] | role.getRole_fun3());
		user.setUser_fun4(funcs[3] | role.getRole_fun4());
		user.setUser_fun5(funcs[4] | role.getRole_fun5());
		user_dao.updateSysUser(user);
	}

	@Override
	public SysRole showSysRoleByUser_id(String user_id) throws GeneralException {
		List<SysRole> roles = role_dao.showSysRolesByUser_id(user_id);
		if (roles.size() > 0) {
			return roles.get(0);
		}
		return null;
	}

	public List<SysRole> showSysRoleByRole_ids(String sel)
			throws GeneralException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysRoleUtil.TABLE_NAME);
		sb.append(" where 1=2 ");
		if (!"".equals(sel) && sel != null&&!"null".equals(sel) ) {
			String[] strs = sel.split(",");
			for (String str : strs) {
				sb.append(" or ").append(SysRoleUtil.ROLE_ID);
				sb.append("='").append(str).append("'");
			}
		}
		//logger.info("sb.toString():"+sb.toString());
		List<SysRole> roles = role_dao.showSysRolesBySql(sb.toString());
		return roles;
	}

	public List<SysRole> showSysRoleByRule_no(String rule_no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(RoleRuleUtil.ROLE_ID);
		sb.append(" from ").append(RoleRuleUtil.TABLE_NAME);
		sb.append(" where ").append(RoleRuleUtil.RULE_NO).append("='");
		sb.append(rule_no).append("'");
		String sel = role_dao.getStrOfInt(sb.toString(), RoleRuleUtil.ROLE_ID);
		return showSysRoleByRole_ids(sel);
	}
	
	public List<SysRole> showRoles() throws Exception {
		String sql = "select * from " + SysRoleUtil.TABLE_NAME;
		return role_dao.showSysRolesBySql(sql);
	}

	@Override
	public String getUserRoleStatus(String user_id) throws GeneralException {
		SysUserRole userRole = user_role_dao.showSysUserRoleByUser_id(user_id);
		if (userRole != null) {
			return userRole.getUser_role_status();
		}
		return null;
	}
	public SysRoleVo getObj(Integer role_id) throws Exception {
		SysRoleVo vo = new SysRoleVo();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(SysRoleUtil.TABLE_NAME);
		sb.append(" where ").append(SysRoleUtil.ROLE_ID);
		sb.append("='").append(role_id).append("'");
		List list = role_dao.select(sb.toString());// 调用父类方法
		SysRole obj = new SysRole();
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (SysRole) DaoUtil.setPo(obj, map, new SysRoleUtil());
			BeanUtils.copyProperties(vo, obj);
			return resetVo(vo);
		} else {
			return null;
		}
	}
	public void updObj(SysRoleVo vo) throws Exception {
		SysRole obj = new SysRole();
		BeanUtils.copyProperties(obj, vo);
		obj.setRole_fun1(Integer.valueOf(vo.getFunc_v()));
		obj.setRole_fun2(Integer.valueOf(vo.getFunc_v2()));
		String parasStr = SysRoleUtil.ROLE_ID + "='" + vo.getRole_id() + "'";
		role_dao.update(ConstructSql.composeUpdateSql(obj, new SysRoleUtil(),
				parasStr));
		otherChange(vo);// 其他关系表
	}
	public void otherChange(SysRoleVo vo) throws Exception {
		// 角色规则表
//		StringBuffer sb0 = new StringBuffer();
//		sb0.append("delete from ").append(RoleRuleUtil.TABLE_NAME);
//		sb0.append(" where ").append(RoleRuleUtil.ROLE_ID).append("='");
//		sb0.append(vo.getRole_id()).append("'");
//		role_dao.delete(sb0.toString());
//		if (!"".equals(vo.getSel_rules()) && vo.getSel_rules() != null) {
//			String[] rules = vo.getSel_rules().split(",");
//			for (String str : rules) {
//				RoleRule rr = new RoleRule();
//				rr.setRule_no(str);
//				rr.setRole_id(vo.getRole_id());
//				StringBuffer sb = new StringBuffer();
//				sb.append("select * from ").append(GaiZhangRuleUtil.TABLE_NAME);
//				sb.append(" where ").append(GaiZhangRuleUtil.RULE_NO).append(
//						"='");
//				sb.append(str).append("'");
//				String busi_no = (String) role_dao.getObject(sb.toString(),
//						GaiZhangRuleUtil.BUSI_NO, false);
//				rr.setBusi_no(busi_no);
//				role_dao.add(ConstructSql.composeInsertSql(rr,
//						new RoleRuleUtil()));
//			}
//		}
		// 用户角色表,更新用户表中权限
		List<SysUser> users=user_srv.showUsersByRole(vo.getRole_id());
		for (SysUser sysUser : users) {
			user_srv.updateFuncsValue(sysUser.getUser_id());
		}
	}
	public String getRoleNoMax()throws Exception{
		String sql="select * from "+SysRoleUtil.TABLE_NAME+" order by "+SysRoleUtil.ROLE_ID+" desc ";
		SysRole objRole=role_dao.showSysRoleBySql(sql);
		return objRole.getRole_id()+","+objRole.getRole_name();
	}
	@Override
	public List<SysRole> showSysRolesByUser_id(String user_id)
			throws GeneralException {
		return role_dao.showSysRolesByUser_id(user_id);
	}

	public void setRole_dao(SysRoleDaoImpl role_dao) {
		this.role_dao = role_dao;
	}

	@Override
	public String getRoleName(String userId) {//获取角色名称
		return role_dao.getRoleName(userId);
	}
}
