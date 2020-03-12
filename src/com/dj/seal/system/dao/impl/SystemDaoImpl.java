package com.dj.seal.system.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.ViewInterface;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.dao.api.ISystemDao;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.LogSysUtil;
import com.dj.seal.util.table.ViewInterfaceUtil;
import com.dj.seal.util.table.ViewMenuUtil;
import com.dj.seal.util.table.ViewTableModuleUtil;

public class SystemDaoImpl extends BaseDAOJDBC implements ISystemDao {
	
	static Logger logger = LogManager.getLogger(SystemDaoImpl.class.getName());
	
	LogSysUtil table_log = new LogSysUtil();

	@Override
	public void addLogSys(LogSys logSys) throws DAOException {

		add(ConstructSql.composeInsertSql(logSys, new LogSysUtil()));
	}

	@Override
	public void addViewInterface(ViewInterface view) throws DAOException {

		add(ConstructSql.composeInsertSql(view, new ViewInterfaceUtil()));
	}

	@Override
	public void addViewMenu(ViewMenu viewMenu) throws DAOException {

		add(ConstructSql.composeInsertSql(viewMenu, new ViewMenuUtil()));
	}

	@Override
	public void addViewTableModule(ViewTableModule viewTableModule)
			throws DAOException {

		add(ConstructSql.composeInsertSql(viewTableModule,
				new ViewTableModuleUtil()));
	}

	@Override
	public void deleteLogSys(String str) throws DAOException {
		delete(str);

	}

	@Override
	public void deleteViewMenu(String str) throws DAOException {
		delete(str);

	}

	@Override
	public List<ViewMenu> selectViewMenu(String str) throws DAOException {

		List<ViewMenu> list1 = new ArrayList<ViewMenu>();
		List<Map> list = select(str);
		if (list.size() > 0) {
			for (Map map : list) {
				ViewMenu viewMenu = new ViewMenu();
				viewMenu = (ViewMenu) DaoUtil.setPo(viewMenu, map,
						new ViewMenuUtil());
				list1.add(viewMenu);
			}
		}
		return list1;
	}

	@Override
	public List<LogSys> showLogSys(PageSplit pageSplit, String[] sql)
			throws DAOException {

		List<LogSys> list_log = new ArrayList<LogSys>();
		List<Map> list = selectByPageSplit1(pageSplit, sql);
		for (Map map : list) {
			LogSys logSys = new LogSys();
			logSys = (LogSys) DaoUtil.setPo(logSys, map, table_log);
			list_log.add(logSys);
		}
		return list_log;
	}

	@Override
	public ViewMenu showViewMenu(String str) throws DAOException {
		ViewMenu viewMenu = new ViewMenu();
		List list = select(str);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			viewMenu = (ViewMenu) DaoUtil.setPo(viewMenu, map,
					new ViewMenuUtil());
		}
		return viewMenu;
	}

	@Override
	public ViewTableModule showViewTableModule(String str) throws DAOException {
		ViewTableModule viewTableModule = new ViewTableModule();
		List list = select(str);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			viewTableModule = (ViewTableModule) DaoUtil.setPo(viewTableModule,
					map, new ViewMenuUtil());
		}
		return viewTableModule;
	}

	@Override
	public void updateViewMenu(ViewMenu viewMenu) throws DAOException {

		update(ConstructSql.composeUpdateSql(viewMenu, new ViewMenuUtil()));
	}

	@Override
	public void updateViewTableModule(ViewTableModule viewTableModule)
			throws DAOException {

		update(ConstructSql.composeUpdateSql(viewTableModule,
				new ViewTableModuleUtil()));
	}

	@Override
	public void updateViewInterface(ViewInterface view) throws DAOException {
		String sql = "1=1";
		update(ConstructSql
				.composeUpdateSql(view, new ViewInterfaceUtil(), sql));

	}

	@Override
	public ViewInterface selectViewInterface() throws DAOException {
		String sql = "select * from " + ViewInterfaceUtil.TABLE_NAME;
		ViewInterface view = new ViewInterface();
		List list = select(sql);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			view = (ViewInterface) DaoUtil.setPo(view, map,
					new ViewInterfaceUtil());
		}
		return view;
	}
}
