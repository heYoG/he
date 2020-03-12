package com.dj.seal.organise.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysRoleDao;
import com.dj.seal.structure.dao.po.SysRole;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SysRoleUtil;
import com.dj.seal.util.table.SysUserRoleUtil;

public class SysRoleDaoImpl extends BaseDAOJDBC implements ISysRoleDao {
	
	static Logger logger = LogManager.getLogger(SysRoleDaoImpl.class.getName());

	private SysRoleUtil table = new SysRoleUtil();
	@SuppressWarnings("unchecked")
	public List<SysRole> listSysRole(String sql) {
		List<SysRole> list_role = new ArrayList<SysRole>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SysRole func = new SysRole();
			func = (SysRole) DaoUtil.setPo(func, map, table);
			list_role.add(func);
		}
		return list_role;
	}

	@SuppressWarnings("unchecked")
	private SysRole sysRole(String sql) {
		SysRole sysRole = new SysRole();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sysRole = (SysRole) DaoUtil.setPo(sysRole, map, table);
			return sysRole;
		} else {
			return null;
		}
	}

	@Override
	public void addSysRole(SysRole sysRole) throws DAOException {
		String sql = ConstructSql.composeInsertSql(sysRole, table);
		logger.info(sql);
		add(sql);

	}
	public void cleanLog(String sql) throws DAOException {
		delete(sql);
	}
	public void addLog(String sql) throws DAOException {
		update(sql);
	}
	public void delLog(String sql) throws DAOException {
		delete(sql);
	}
	@Override
	public void deleteRole(SysRole sysRole) throws DAOException {
		String sql = "delete from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_ID + "='" + sysRole.getRole_id() + "'";
		delete(sql);
	}

	@Override
	public SysRole showSysRoleByRole_id(Integer role_id) throws DAOException {
		String sql = "select * from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_ID + "='" + role_id + "'";
		return sysRole(sql);
	}

	@Override
	public SysRole showSysRoleBySql(String sql) throws DAOException {
		return sysRole(sql);
	}

	@Override
	public List<SysRole> showSysRolesByDept_no(String dept_no)
			throws DAOException {
		String sql = "";
		return listSysRole(sql);
	}

	@Override
	public List<SysRole> showSysRolesByFunc_id(Integer func_id)
			throws DAOException {
		String sql = "";
		return listSysRole(sql);
	}

	@Override
	public List<SysRole> showSysRolesBySql(String sql) throws DAOException {
		return listSysRole(sql);
	}

	@Override
	public List<SysRole> showSysRolesByUser_id(String user_id)
			throws DAOException {
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysRoleUtil.TABLE_NAME + "," + SysUserRoleUtil.TABLE_NAME
				+ " where " + SysUserRoleUtil.USER_ID + "='" + user_id
				+ "' and " + SysUserRoleUtil.ROLE_ID + "="
				+ SysRoleUtil.ROLE_ID;
		logger.info("sql:"+sql);
		return listSysRole(sql);
	}

	@Override
	public void updateSysRole(SysRole sysRole) throws DAOException {
		String parasStr = SysRoleUtil.ROLE_ID + "='" + sysRole.getRole_id()
				+ "'";
		String sql = ConstructSql.composeUpdateSql(sysRole, table, parasStr);
		update(sql);
	}

	@Override
	public void updateSysRoleBySql(String sql) throws DAOException {
		update(sql);

	}

	@Override
	public List<SysRole> showSysRolesByPageSplit(int pageIndex, int pageSize,
			String sql) throws DAOException {
		/*StringBuffer sb = new StringBuffer(sql);
		int left = (pageIndex - 1) * pageSize;
		sb.append(" limit ").append(left).append(",").append(pageSize);
		List<SysRole> list = listSysRole(sb.toString());*/
		
		String str=DBTypeUtil.splitSql(sql, pageIndex, pageSize, Constants.DB_TYPE);
		List<SysRole> list = listSysRole(str);
		
		// 为角色对象设置允许登录数和禁止登录数
		for (SysRole sysRole : list) {
			String sql1 = "select * from " + SysUserRoleUtil.TABLE_NAME
					+ " where " + SysUserRoleUtil.ROLE_ID + "='"
					+ sysRole.getRole_id() + "' and "
					+ SysUserRoleUtil.USER_ROLE_STATUS + "='1'";
			sysRole.setUsers1(showCount(sql1));
			String sql0 = "select * from " + SysUserRoleUtil.TABLE_NAME
					+ " where " + SysUserRoleUtil.ROLE_ID + "='"
					+ sysRole.getRole_id() + "' and "
					+ SysUserRoleUtil.USER_ROLE_STATUS + "='0'";
			sysRole.setUsers0(showCount(sql0));
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isExistRole(String role_name) throws DAOException {
		String sql = "select * from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_NAME + "='" + role_name + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	@SuppressWarnings("unchecked")
	public boolean isExistRoleno(int role_no) throws DAOException {
		String sql = "select * from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_TAB + "='" + role_no + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean isExistRolename(String role_name) throws DAOException {
		String sql = "select * from " + SysRoleUtil.TABLE_NAME + " where "
				+ SysRoleUtil.ROLE_NAME + "='" + role_name + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	@Override
	@SuppressWarnings("unchecked")
	public int showCount(String sql) throws DAOException {
		// String last_sql = "select count(*) "
		// + sql.substring(sql.indexOf("from"));
		// List list = select(last_sql);
		// Map map = (Map) list.get(0);
		// return (Integer)map.get("count(*)");
		return count(sql);
	}
	
	@Override
	public String getRoleName(String userId) {// 获取角色名称
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysRoleUtil.TABLE_NAME + "," + SysUserRoleUtil.TABLE_NAME
				+ " where " + SysUserRoleUtil.USER_ID + "='" + userId
				+ "' and " + SysUserRoleUtil.ROLE_ID + "="
				+ SysRoleUtil.ROLE_ID;

		List<Map> list = select(sql);
		String role_name = "";
		for (Map map : list) {
			SysRole func = new SysRole();
			func = (SysRole) DaoUtil.setPo(func, map, table);
			role_name = func.getRole_name();
		}
		return role_name;
	}
}
