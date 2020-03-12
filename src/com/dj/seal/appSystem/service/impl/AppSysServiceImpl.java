package com.dj.seal.appSystem.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.appSystem.dao.api.IAppSystemDao;
import com.dj.seal.appSystem.service.api.IAppSysService;
import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.table.AppSystemUtil;
import com.dj.seal.util.web.SearchForm;

public class AppSysServiceImpl implements IAppSysService {
	
	static Logger logger = LogManager.getLogger(AppSysServiceImpl.class.getName());

	private IAppSystemDao app_dao;
	private BaseDAOJDBC dao;
	private AppSystemUtil table = new AppSystemUtil();

	public IAppSystemDao getApp_dao() {
		return app_dao;
	}

	public void setApp_dao(IAppSystemDao app_dao) {
		this.app_dao = app_dao;
	}

	public BaseDAOJDBC getDao() {
		return dao;
	}

	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}

	@Override
	public boolean addAppSystem(AppSystemForm app) throws Exception {
		if (app.getApp_id() == null) {
			app.setApp_id(Integer.parseInt(dao.getMaxNo(AppSystemUtil.getTABLE_NAME(),
					AppSystemUtil.getAPP_ID())) + 1);
		}
		app_dao.addAppSystem(app);
		return true;
	}

	@Override
	public PageSplit showAppSystems(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException {
		String str = null;
		try {
			str = SearchUtil.appSch(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return showPageSplit(pageIndex, pageSize, str);
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
	public PageSplit showPageSplit(int pageIndex, int pageSize, String sql)
			throws GeneralException {
		PageSplit pageSplit = new PageSplit();
		List<AppSystemForm> sealBodys = app_dao.showAppSystemsByPageSplit(
				pageIndex, pageSize, sql);
		pageSplit.setDatas(sealBodys);
		pageSplit.setNowPage(pageIndex);
		pageSplit.setPageSize(pageSize);
		pageSplit.setTotalCount(dao.count(sql));
		return pageSplit;
	}

	/**
	 * 检查应用系统编号是否存在
	 * 
	 * @param app_no
	 * @return false or true
	 */

	@Override
	public boolean isExistServer(String app_no) {
		return app_dao.isExistAppSystem(app_no);
	}

	@Override
	public String delAppSystem(String app_id) {
		// TODO Auto-generated method stub
		return app_dao.delApp(app_id);
	}

	@Override
	public AppSystemForm getAppById(String app_id) {
		// TODO Auto-generated method stub
		return app_dao.getAppSystem(app_id);
	}
	
	@Override
	public AppSystemForm getAppSystemByAPP_NO(String app_no) {
		// TODO Auto-generated method stub
		return app_dao.getAppSystemByAPP_NO(app_no);
	}

	public boolean getAppIp(String app_id,String clientIP) {
		// TODO Auto-generated method stub
		return app_dao.getAppIp(app_id,clientIP);
	}
	@Override
	public void upAppSystem(String app_no, String app_name, String app_ip,
			String app_id, String app_pwd) {
		app_dao.upApp(app_no,app_name,app_ip,app_id,app_pwd);
	}
}
