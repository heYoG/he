package com.dj.seal.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.dao.api.ITableModuleDao;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.ViewTableModuleUtil;

public class TableModuleDaoImpl extends BaseDAOJDBC implements ITableModuleDao {
	
	static Logger logger = LogManager.getLogger(TableModuleDaoImpl.class.getName());

	private ViewTableModuleUtil table = new ViewTableModuleUtil();

	@SuppressWarnings("unchecked")
	private ViewTableModule tableModule(String sql) throws DAOException {
		ViewTableModule obj = new ViewTableModule();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (ViewTableModule) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<ViewTableModule> list(String sql) throws DAOException {
		List<ViewTableModule> list_obj = new ArrayList<ViewTableModule>();
		List<Map> list = select(sql);
		for (Map map : list) {
			ViewTableModule obj = new ViewTableModule();
			obj = (ViewTableModule) DaoUtil.setPo(obj, map, table);
			list_obj.add(obj);
		}
		return list_obj;
	}

	@Override
	public ViewTableModule getTableModuleByNo(String module_no)
			throws DAOException {
		String sql = "select * from " + ViewTableModuleUtil.TABLE_NAME
				+ " where " + ViewTableModuleUtil.MODULE_NO + "='" + module_no
				+ "'";
		return tableModule(sql);
	}

	@Override
	public ViewTableModule getTableModuleBySql(String sql) throws DAOException {
		return tableModule(sql);
	}

	@Override
	public List<ViewTableModule> showModuleListBySql(String sql)
			throws DAOException {
		return list(sql);
	}

	@Override
	public void updateModule(ViewTableModule module) throws DAOException {
		String parasStr = ViewTableModuleUtil.MODULE_NO + "='"
				+ module.getModule_no() + "'";
		String sql = ConstructSql.composeUpdateSql(module, table, parasStr);
		update(sql);
	}

}
