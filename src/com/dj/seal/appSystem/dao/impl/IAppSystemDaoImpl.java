package com.dj.seal.appSystem.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.appSystem.dao.api.IAppSystemDao;
import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.AppSystemUtil;

public class IAppSystemDaoImpl extends BaseDAOJDBC implements IAppSystemDao {

	static Logger logger = LogManager.getLogger(IAppSystemDaoImpl.class.getName());
	
	private AppSystemUtil appTable = new AppSystemUtil();

	@Override
	public void addAppSystem(AppSystemForm app) {
		try {
			String sql = ConstructSql.composeInsertSql(app, appTable);
			add(sql);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	@Override
	public boolean getAppIp(String app_id,String clientIp)throws DAOException {
		String sql="select * from "+AppSystemUtil.TABLE_NAME+" where "+AppSystemUtil.APP_NO+"='"+app_id+"'";
		List<Map> list = select(sql);
		for (Map map : list) {
			AppSystemForm appSystem = new AppSystemForm();
			appSystem = (AppSystemForm) DaoUtil.setPo(appSystem, map, appTable);
			String app_ip=appSystem.getApp_ip();
			if(app_ip.indexOf(clientIp)!=-1){
				return true;
			}else{
				return false;
			}
		}
		return true;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<AppSystemForm> showAppSystemsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException {
		
		List<AppSystemForm> appSystems = new ArrayList<AppSystemForm>();
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,Constants.DB_TYPE);

		List<Map> list = select(str);
		for (Map map : list) {
			AppSystemForm appSystem = new AppSystemForm();
			appSystem = (AppSystemForm) DaoUtil.setPo(appSystem, map, appTable);
			appSystem.setCreatedate1(appSystem.getCreate_date().toString());
			appSystems.add(appSystem);
		}
		return appSystems;
	}

	@Override
	public boolean isExistAppSystem(String app_no) {
	    StringBuffer sb = new StringBuffer();
	    sb.append("select * from ").append(AppSystemUtil.getTABLE_NAME()).append(" where ").append(AppSystemUtil.getAPP_NO());
	    sb.append(" ='").append(app_no).append("'");
	    try {

	    	List<Map> list = getJdbcTemplate().queryForList(sb.toString());
			getJdbcTemplate().execute("commit");
			if (list.size() == 0) {
				return false;
			}
			StringBuffer sb1 = new StringBuffer();
			for (Map map : list) {
				String str =  map.get(AppSystemUtil.getAPP_ID()).toString();
				sb1.append(str).append(",");
			}
			sb1.deleteCharAt(sb1.lastIndexOf(","));
			if(sb1!=null){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return false;
	}

	@Override
	public String delApp(String app_id) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ").append(AppSystemUtil.TABLE_NAME);
		sb.append(" where ").append(AppSystemUtil.APP_ID).append("='"+app_id+"'");
		delete(sb.toString());
		return "0";
	}

	@Override
	public AppSystemForm getAppSystem(String app_id) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(AppSystemUtil.getTABLE_NAME()).append(" where ").append(AppSystemUtil.APP_ID);
	    sb.append(" ='").append(app_id).append("'");
		List<Map> lists = select(sb.toString());
		if(lists!=null&&lists.size()>0){
			AppSystemForm appSystem = new AppSystemForm();
			appSystem = (AppSystemForm) DaoUtil.setPo(appSystem, lists.get(0), appTable);	
			return appSystem;
		}else{
			return null;
		}
		
	}
	@Override
	public AppSystemForm getAppSystemByAPP_NO(String app_no) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(AppSystemUtil.getTABLE_NAME()).append(" where ").append(AppSystemUtil.APP_NO);
	    sb.append(" ='").append(app_no).append("'");
		List<Map> lists = select(sb.toString());
		if(lists!=null&&lists.size()>0){
			AppSystemForm appSystem = new AppSystemForm();
			appSystem = (AppSystemForm) DaoUtil.setPo(appSystem, lists.get(0), appTable);	
			return appSystem;
		}else{
			return null;
		}
		
	}

	@Override
	public void upApp(String app_no, String app_name, String app_ip,
			String app_id, String app_pwd) {
		AppSystemForm app = new AppSystemForm();
		app.setApp_no(app_no);
		app.setApp_id(Integer.parseInt(app_id));
		app.setApp_name(app_name);
		app.setApp_pwd(app_pwd);
		app.setApp_ip(app_ip);
		String sql = ConstructSql.composeUpdateSql(app, appTable, AppSystemUtil.APP_ID+"='"+app_id+"'");
		update(sql);
	}
}
