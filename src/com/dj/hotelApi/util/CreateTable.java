package com.dj.hotelApi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelTClientOperLogUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;
import com.neusoft.service.TableMonthConfigService;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;

public class CreateTable {
	static Logger logger = LogManager.getLogger(CreateTable.class.getName());

	public void createNewTable() {
		logger.info("createNewTable");
		Date nowdate = new Date();
		// SimpleDateFormat new_formatter = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		//createRecordTable(yuestr);// 创建单据月表
		//createRecordAffiliatedTable(yuestr);// 创建单据属性月表
		//createClientOperLogTable(yuestr);// 创建无纸化日志月表
		//createRecordContent(yuestr);// 创建无纸化单据内容信息表
		//TableNameUtil.updateTableName(yuestr);// 更新内存中的表名
		//refreshTableMonathConfig();
	}

	// 创建单据表 月表tablename+201403
	public boolean createRecordTable(String yuestr) {
		logger.info("createRecordTable");
		String tablename = HotelRecordUtil.TABLE_NAME;
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			sql = HotelRecordUtil.createSql4Oracle();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sql = HotelRecordUtil.createSql4MySql();
		}
		sql = sql.replace(tablename, tablename + yuestr);
		logger.info(sql);
		BaseDAOJDBC baseDao = (BaseDAOJDBC) Constants.getBean("IBaseDao");
		logger.info(baseDao);
		String sqlSelect = "select count(*) from " + tablename + yuestr;
		logger.info(sqlSelect);
		try {
			baseDao.select(sqlSelect);// 用select是否报错来判断数据库中是否存在表名，如果不存在会报错
		} catch (Exception e) {
			logger.error("createRecordTable:当月表不存在");
			baseDao.update(sql);
		}
		return true;
	}

	// 创建单据属性表 月表tablename+201403
	public boolean createRecordAffiliatedTable(String yuestr) {
		logger.info("createRecordAffiliatedTable");
		String tablename = HotelTRecordAffiliatedUtil.TABLE_NAME;
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			sql = HotelTRecordAffiliatedUtil.createSql4Oracle();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sql = HotelTRecordAffiliatedUtil.createSql4MySql();
		}
		sql = sql.replace(tablename, tablename + yuestr);
		BaseDAOJDBC baseDao = (BaseDAOJDBC) Constants.getBean("IBaseDao");
		String sqlSelect = "select count(*) from " + tablename + yuestr;
		try {
			baseDao.select(sqlSelect);
		} catch (Exception e) {
			logger.error("createRecordAffiliatedTable:当月表不存在");
			baseDao.update(sql);
		}
		return true;
	}

	// 创建无纸化日志月表 月表tablename+201403
	public boolean createClientOperLogTable(String yuestr) {
		logger.info("createClientOperLogTable");
		String tablename = HotelTClientOperLogUtil.TABLE_NAME;
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			sql = HotelTClientOperLogUtil.createSql4oracle();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sql = HotelTClientOperLogUtil.createSql();
		}
		sql = sql.replace(tablename, tablename + yuestr);
		BaseDAOJDBC baseDao = (BaseDAOJDBC) Constants.getBean("IBaseDao");
		String sqlSelect = "select count(*) from " + tablename + yuestr;
		try {
			baseDao.select(sqlSelect);
		} catch (Exception e) {
			logger.error("createClientOperLogTable:当月表不存在");
			baseDao.update(sql);
		}
		return true;
	}

	// 创建无纸化内容检索月表 月表tablename+201403
	public boolean createRecordContent(String yuestr) {
		logger.info("createRecordContent");
		String tablename = HotelRecordContent.TABLE_NAME;
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
			sql = HotelRecordContent.createSql4Oracle();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sql = HotelRecordContent.createSql4MySql();
		}
		sql = sql.replace(tablename, tablename + yuestr);
		BaseDAOJDBC baseDao = (BaseDAOJDBC) Constants.getBean("IBaseDao");
		String sqlSelect = "select count(*) from " + tablename + yuestr;
		try {
			baseDao.select(sqlSelect);
		} catch (Exception e) {
			logger.error("createRecordContentTable:当月表不存在");
			baseDao.update(sql);
		}
		return true;
	}

	// 创建房间单据合并表 月表tablename+201403
	public boolean createRecordRoomMergeTable(String yuestr) {
		logger.info("createRecordRoomMergeTable");
		String tablename = HotelRecordRoomMergeUtil.TABLE_NAME;
		String sql = "";
		if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
//			sql = HotelRecordRoomMergeUtil.createSql4Oracle();
		} else if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
			sql = HotelRecordRoomMergeUtil.createSql4MySql();
		}
		sql = sql.replace(tablename, tablename + yuestr);
		logger.info(sql);
		BaseDAOJDBC baseDao = (BaseDAOJDBC) Constants.getBean("IBaseDao");
		logger.info(baseDao);
		String sqlSelect = "select count(*) from " + tablename + yuestr;
		logger.info(sqlSelect);
		try {
			baseDao.select(sqlSelect);// 用select是否报错来判断数据库中是否存在表名，如果不存在会报错
		} catch (Exception e) {
			logger.error("createRecordRoomMergeTable:当月表不存在");
			baseDao.update(sql);
		}
		return true;
	}
	
	public void refreshTableMonathConfig() {
		TableMonthConfigService tableMonthConfigService = (TableMonthConfigService)Constants.getBean("tableMonthConfigService");
		tableMonthConfigService.refresh();
	}
	
}
