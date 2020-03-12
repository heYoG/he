package com.dj.seal.sealLog.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.sealLog.dao.api.ISealLogDao;
import com.dj.seal.structure.dao.po.CZSealLog;
import com.dj.seal.structure.dao.po.ServerSealLog;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.table.CZSealLogUtil;
import com.dj.seal.util.table.ServerSealLogUtil;

public class SealLogDao extends BaseDAOJDBC implements ISealLogDao {
	static Logger logger = LogManager.getLogger(SealLogDao.class.getName());

	/**
	 * 新增盖章、打印日志记录
	 * 
	 * @param log
	 *            日志对象
	 */
	@Override
	public void addSealLog(ServerSealLog log) {
		// System.out.println("流水号："+log.getCaseseqid());
		String sql = ConstructSql.composeInsertSql(log, new ServerSealLogUtil());
		add(sql);
	}

	/**
	 * 新增盖章、打印日志记录
	 * 
	 * @param log
	 *            日志对象
	 */
	@Override
	public void addSealCZLog(CZSealLog log) {
		try {
			String no = getNo(CZSealLogUtil.TABLE_NAME, CZSealLogUtil.ID);
			log.setId(Integer.valueOf(no));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
		}
		String sql = ConstructSql.composeInsertSql(log, new CZSealLogUtil());
		add(sql);
	}

	@Override
	public int backupSealLogs() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("SealLog_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "insert into " + ServerSealLogUtil.TABLE_NAME
				+ "_history select*from "+ServerSealLogUtil.TABLE_NAME+" where (to_date('" + nowTime
				+ "','yyyy-mm-dd hh24:mi:ss')-" + ServerSealLogUtil.OPR_TIME
				+ ")>" + saveTime;
		//logger.info("备份服务端盖章日志sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}

	@Override
	public int delSealLogs() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("SealLog_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "delete from " + ServerSealLogUtil.TABLE_NAME
				+ " where (to_date('" + nowTime + "','yyyy-mm-dd hh24:mi:ss')-"
				+ ServerSealLogUtil.OPR_TIME + ")>" + saveTime;
		//logger.info("删除原表已备份日志sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}
}
