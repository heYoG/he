package com.dj.seal.organise.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysFuncDao;
import com.dj.seal.structure.dao.po.SysFunc;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SysFuncUtil;
import com.dj.seal.util.table.SysRoleFuncUtil;

public class SysFuncDaoImpl extends BaseDAOJDBC implements ISysFuncDao {
	
	static Logger logger = LogManager.getLogger(SysFuncDaoImpl.class.getName());

	private SysFuncUtil table = new SysFuncUtil();

	@SuppressWarnings("unchecked")
	private List<SysFunc> listSysFunc(String sql) {
		List<SysFunc> list_func = new ArrayList<SysFunc>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SysFunc func = new SysFunc();
			func = (SysFunc) DaoUtil.setPo(func, map, table);
			list_func.add(func);
		}
		return list_func;
	}

	@SuppressWarnings("unchecked")
	private SysFunc sysFunc(String sql) {
		SysFunc sysFunc = new SysFunc();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sysFunc = (SysFunc) DaoUtil.setPo(sysFunc, map, table);
			return sysFunc;
		} else {
			return null;
		}
	}

	@Override
	public void addSysFunc(SysFunc sysFunc) throws DAOException {
		String sql = ConstructSql.composeInsertSql(sysFunc, table);
		add(sql);
	}

	@Override
	public void deleteSysFunc(SysFunc sysFunc) throws DAOException {
		String sql = "delete from " + SysFuncUtil.TABLE_NAME + " where "
				+ SysFuncUtil.FUNC_ID + "='" + sysFunc.getFunc_id() + "'";
		delete(sql);
	}

	@Override
	public void deleteSysFuncBySql(String sql) throws DAOException {
		delete(sql);
	}

	@Override
	public SysFunc showSysFuncByFunc_id(Integer func_id) throws DAOException {
		String sql = "select * from " + SysFuncUtil.TABLE_NAME + " where "
				+ SysFuncUtil.FUNC_ID + "='" + func_id + "'";
		return sysFunc(sql);
	}

	@Override
	public SysFunc showSysFuncBySql(String sql) throws DAOException {
		return sysFunc(sql);
	}

	@Override
	public List<SysFunc> showSysFuncsByMenu_no(String menu_no)
			throws DAOException {
		String sql = "select * from " + SysFuncUtil.TABLE_NAME + " where "
				+ SysFuncUtil.MENU_NO + "='" + menu_no + "'";
		return listSysFunc(sql);
	}

	@Override
	public List<SysFunc> showSysFuncsByRole_id(Integer role_id)
			throws DAOException {
		String sql = "select " + DaoUtil.allFields(table) + " from "
				+ SysRoleFuncUtil.TABLE_NAME + "," + SysFuncUtil.TABLE_NAME
				+ " where " + SysRoleFuncUtil.FUNC_ID + "="
				+ SysFuncUtil.FUNC_ID + " and " + SysRoleFuncUtil.ROLE_ID
				+ "='" + role_id + "'";
		return listSysFunc(sql);
	}

	@Override
	public List<SysFunc> showSysFuncsBySql(String sql) throws DAOException {
		return listSysFunc(sql);
	}

	@Override
	public List<SysFunc> showSysFuncsByUser_id(String user_id)
			throws DAOException {
		String sql = "";
		return listSysFunc(sql);
	}

	@Override
	public void updateSysFunc(SysFunc sysFunc) throws DAOException {
		String parasStr = SysFuncUtil.FUNC_ID + "='" + sysFunc.getFunc_id()
				+ "'";
		String sql = ConstructSql.composeUpdateSql(sysFunc, table, parasStr);
		update(sql);
	}

	@Override
	public void updateSysFuncBySql(String sql) throws DAOException {
		update(sql);
	}

	@Override
	public SysFunc showSysFuncByFunc_value(Integer func_value,
			String value_proup) throws DAOException {
		String sql = "select * from " + SysFuncUtil.TABLE_NAME + " where "
				+ SysFuncUtil.FUNC_VALUE + "='" + func_value + "' and "
				+ SysFuncUtil.FUNC_GROUP + "='" + value_proup + "'";
		return sysFunc(sql);
	}

}
