package com.dj.seal.organise.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysUserRoleDao;
import com.dj.seal.structure.dao.po.SysUserRole;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SysUserRoleUtil;

public class SysUserRoleDaoImpl extends BaseDAOJDBC implements ISysUserRoleDao {
	
	static Logger logger = LogManager.getLogger(SysUserRoleDaoImpl.class.getName());

	private SysUserRoleUtil table = new SysUserRoleUtil();

	@SuppressWarnings("unchecked")
	private List<SysUserRole> listSysUserRole(String sql) {
		List<SysUserRole> list_user_role = new ArrayList<SysUserRole>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SysUserRole user_role = new SysUserRole();
			user_role = (SysUserRole) DaoUtil.setPo(user_role, map, table);
			list_user_role.add(user_role);
		}
		return list_user_role;
	}

	@SuppressWarnings("unchecked")
	private SysUserRole sysUserRole(String sql) {
		SysUserRole user_role = new SysUserRole();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			user_role = (SysUserRole) DaoUtil.setPo(user_role, map, table);
			return user_role;
		} else {
			return null;
		}
	}

	@Override
	public void deleteUserRoleByRole_id(Integer role_id) throws DAOException {
		String sql = "delete from " + SysUserRoleUtil.TABLE_NAME + " where "
				+ SysUserRoleUtil.ROLE_ID + "='" + role_id + "'";
		delete(sql);

	}
	
	public SysUserRoleUtil getTable() {
		return table;
	}

	@Override
	public List<SysUserRole> showSysUserRolesByRole_id(Integer role_id)
			throws DAOException {
		String sql = "select * from " + SysUserRoleUtil.TABLE_NAME + " where "
				+ SysUserRoleUtil.ROLE_ID + "='" + role_id + "'";
		return listSysUserRole(sql);
	}
	@Override
	public List<SysUserRole> showSysUserRolesByRole_id2(String sql)
	    throws DAOException {
         return listSysUserRole(sql);
      }
	@Override
	public void addUserRole(String user_id, Integer role_id,
			String user_role_status) throws DAOException {
		SysUserRole userRole = new SysUserRole();
		userRole.setRole_id(role_id);
		userRole.setUser_id(user_id);
		userRole.setUser_role_status(user_role_status);
		String sql = ConstructSql.composeInsertSql(userRole, table);
		add(sql);
	}

	@Override
	public void updateUserRole(String user_id, Integer role_id,
			String user_role_status) throws DAOException {
		SysUserRole userRole = new SysUserRole();
		userRole.setRole_id(role_id);
		userRole.setUser_id(user_id);
		userRole.setUser_role_status(user_role_status);
		String condition = SysUserRoleUtil.USER_ID + "='" + user_id + "'";
		String sql = ConstructSql.composeUpdateSql(userRole, table, condition);
		update(sql);
	}

	@Override
	public SysUserRole showSysUserRoleByUser_id(String user_id)
			throws DAOException {
		String sql = "select * from " + SysUserRoleUtil.TABLE_NAME + " where "
				+ SysUserRoleUtil.USER_ID + "='" + user_id + "'";
		return sysUserRole(sql);
	}

	@Override
	public void deleteUserRoleByUser_id(String user_id) throws DAOException {
		String sql = "delete from " + SysUserRoleUtil.TABLE_NAME + " where "
		+ SysUserRoleUtil.USER_ID + "='" + user_id + "'";
        delete(sql);
	}
	
	@Override
	public List<SysUserRole> showSysUserRolesByUser_id(String user_id)
			throws DAOException {
		String sql = "select * from " + SysUserRoleUtil.TABLE_NAME + " where "
				+ SysUserRoleUtil.USER_ID + "='" + user_id + "'";
		return listSysUserRole(sql);
	}

}
