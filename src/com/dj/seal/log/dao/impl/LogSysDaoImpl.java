package com.dj.seal.log.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.log.dao.api.ILogSysDao;
import com.dj.seal.structure.dao.po.HuiZhiLog;
import com.dj.seal.structure.dao.po.LogSealWrite;
import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.ServerSealLog;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HuiZhiLogUtil;
import com.dj.seal.util.table.LogSealWriteUtil;
import com.dj.seal.util.table.LogSysUtil;
import com.dj.seal.util.table.ServerSealLogUtil;

public class LogSysDaoImpl extends BaseDAOJDBC implements ILogSysDao {

	static Logger logger = LogManager.getLogger(LogSysDaoImpl.class.getName());

	@Override
	public void addLogSys(LogSys logSys) throws DAOException {
		try {

			System.out.print("ININ!!!");
			String log_id = getNo(LogSysUtil.TABLE_NAME, LogSysUtil.LOG_ID);
			logSys.setLog_id(Integer.parseInt(log_id));
			String sql = ConstructSql.composeInsertSql(logSys, new LogSysUtil());
			add(sql);

		} catch (Exception e) {
			// logger.error(e.getMessage());
			logger.error(e.getMessage());
		}

	}

	public void delLogSys(String seal_id) throws DAOException {
		try {
			String sql = "delete  from " + LogSealWriteUtil.TABLE_NAME
					+ " where " + LogSealWriteUtil.SEAL_ID + "='" + seal_id
					+ "'";
			delete(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}

	}

	@Override
	public void logAddSealWrite(LogSealWrite logsealWrite) throws DAOException {
		String sql = "select * from " + LogSealWriteUtil.TABLE_NAME + " where "
				+ LogSealWriteUtil.SEAL_ID + "='" + logsealWrite.getSeal_id()
				+ "' and " + LogSealWriteUtil.OPR_SN + " = '"
				+ logsealWrite.getOpr_sn() + "'";
		List<Map> list = select(sql);
		if (list.size() > 0) {
			for (Map map : list) {
				LogSealWrite logsealwrite = new LogSealWrite();
				logsealwrite = (LogSealWrite) DaoUtil.setPo(logsealwrite, map,
						new LogSealWriteUtil());
				// String pastr = LogSealWriteUtil.USER_NAME +
				// "='"+logsealWrite.getUser_name()+"'";
				// logsealwrite.setOpr_num(String.valueOf(Integer.parseInt(logsealwrite.getOpr_num())+1));
				// sql = ConstructSql.composeUpdateSql(logsealwrite, new
				// LogSealWriteUtil(), pastr);
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				sql = "update "
						+ LogSealWriteUtil.TABLE_NAME
						+ " set "
						+ LogSealWriteUtil.OPR_NUM
						+ " = '"
						+ String.valueOf(Integer.parseInt(logsealwrite
								.getOpr_num()) + 1) + "',"
						+ LogSealWriteUtil.OPR_TIME + " = '"
						+ sdf.format(new Date()) + "' where "
						+ LogSealWriteUtil.USER_NAME + " = '"
						+ logsealWrite.getUser_name() + "' and "
						+ LogSealWriteUtil.SEAL_ID + "='"
						+ logsealWrite.getSeal_id() + "'";
				update(sql);
			}
		} else {
			try {
				String log_id = getNo(LogSealWriteUtil.TABLE_NAME,
						LogSealWriteUtil.LOG_ID);
				logsealWrite.setLog_id(Integer.parseInt(log_id));
				sql = ConstructSql.composeInsertSql(logsealWrite,
						new LogSealWriteUtil());
				add(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
	}

	public void logAddSealServer(ServerSealLog ssl) throws DAOException {
		// TODO Auto-generated method stub
		try {
			String sql = ConstructSql.composeInsertSql(ssl,
					new ServerSealLogUtil());
			add(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	@Override
	public List<LogSys> logSel(String sql) throws DAOException {
		List<LogSys> list_log = new ArrayList<LogSys>();
		List<Map> list = select(sql);
		for (Map map : list) {
			LogSys logsys = new LogSys();
			logsys = (LogSys) DaoUtil.setPo(logsys, map, new LogSysUtil());
			list_log.add(logsys);
		}
		return list_log;
	}

	public List<LogSealWrite> logSealWrite(String sql) throws DAOException {
		List<LogSealWrite> list_seallog = new ArrayList<LogSealWrite>();
		List<Map> list = select(sql);
		for (Map map : list) {
			LogSealWrite logsealwrite = new LogSealWrite();
			logsealwrite = (LogSealWrite) DaoUtil.setPo(logsealwrite, map,
					new LogSealWriteUtil());
			list_seallog.add(logsealwrite);
		}
		return list_seallog;
	}

	public int getSeallogCount(String sql) throws DAOException {
		ServerSealLog logsealserver = new ServerSealLog();
		int i = count(sql);
		return i;
	}

	public int getHuizhilogCount(String sql) throws DAOException {
		int i = count(sql);
		return i;
	}

	public List<ServerSealLog> logSealServer(String sql) throws DAOException {
		List<ServerSealLog> list_seallog = new ArrayList<ServerSealLog>();
		List<Map> list = select(sql);
		for (Map map : list) {
			ServerSealLog logsealserver = new ServerSealLog();
			logsealserver = (ServerSealLog) DaoUtil.setPo(logsealserver, map,
					new ServerSealLogUtil());
			list_seallog.add(logsealserver);
		}
		return list_seallog;
	}

	public List<HuiZhiLog> logHuizhi(String sql) throws DAOException {
		List<HuiZhiLog> list_huizhilog = new ArrayList<HuiZhiLog>();
		List<Map> list = select(sql);
		for (Map map : list) {
			HuiZhiLog loghuizhi = new HuiZhiLog();
			loghuizhi = (HuiZhiLog) DaoUtil.setPo(loghuizhi, map,
					new HuiZhiLogUtil());
			list_huizhilog.add(loghuizhi);
		}
		return list_huizhilog;
	}

}
