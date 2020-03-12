package com.dj.seal.log.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.log.dao.impl.LogSysDaoImpl;
import com.dj.seal.log.service.api.ILogSysService;
import com.dj.seal.log.web.LogSealWriteVO;
import com.dj.seal.organise.dao.impl.SysRoleDaoImpl;
import com.dj.seal.sealBody.service.api.ISealBodyService;
import com.dj.seal.structure.dao.po.HuiZhiLog;
import com.dj.seal.structure.dao.po.LogSealWrite;
import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.SealLog;
import com.dj.seal.structure.dao.po.ServerSealLog;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.table.HuiZhiLogUtil;
import com.dj.seal.util.table.LogSealWriteUtil;
import com.dj.seal.util.table.LogSysUtil;
import com.dj.seal.util.table.SealLogUtil;
import com.dj.seal.util.table.ServerSealLogUtil;
import com.dj.seal.util.web.SearchForm;

public class LogSysServiceImpl implements ILogSysService {

	static Logger logger = LogManager.getLogger(LogSysServiceImpl.class
			.getName());

	private LogSysDaoImpl log_dao;
	private SysRoleDaoImpl role_dao;
	private ISealBodyService seal_body;

	public ISealBodyService getSeal_body() {
		return seal_body;
	}

	public void setSeal_body(ISealBodyService seal_body) {
		this.seal_body = seal_body;
	}

	public SysRoleDaoImpl getRole_dao() {
		return role_dao;
	}

	public void setRole_dao(SysRoleDaoImpl role_dao) {
		this.role_dao = role_dao;
	}

	public LogSysDaoImpl getLog_dao() {
		return log_dao;
	}

	public void setLog_dao(LogSysDaoImpl log_dao) {
		this.log_dao = log_dao;
	}

	@Override
	public void logAdd(String userid, String username, String ip,
			String viewMenu, String logdetail) throws GeneralException {
		// TODO Auto-generated method stub
		// logger.info("userid"+userid);
		// logger.info("username"+username);
		// logger.info("ip"+ip);
		// logger.info("viewMenu"+viewMenu);
		// logger.info("logdetail"+logdetail);

		logger.info("HERE!!!");
		LogSys logsys = new LogSys();
		logsys.setUser_id(userid);
		logsys.setUser_name(username);
		logsys.setOpr_type(viewMenu);
		logsys.setLog_remark(logdetail);
		logsys.setOpr_time(new Timestamp(new Date().getTime()));
		logsys.setOpr_ip(ip);
		log_dao.addLogSys(logsys);
	}

	@Override
	public PageSplit logSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException {
		String str = SearchUtil.logSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<LogSys> datas = log_dao.logSel(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}

	@Override
	public void logAddSealWrite(String username, String ip, String opr_num,
			String key_desc, String keysn, String seal_id)
			throws GeneralException {
		LogSealWrite logsealWrite = new LogSealWrite();
		logsealWrite.setUser_id("");
		logsealWrite.setUser_name(username);
		logsealWrite.setOpr_ip(ip);
		logsealWrite.setOpr_num(opr_num);
		logsealWrite.setOpr_time(new Timestamp(new Date().getTime()));
		logsealWrite.setOpr_sn(keysn);
		logsealWrite.setSeal_id(seal_id);
		logsealWrite.setKey_desc(key_desc);
		// logDelSealWrite(seal_id);
		log_dao.logAddSealWrite(logsealWrite);
	}

	public void logAddServerSeal(String userid, String serverid, String ip,
			String seal_status, String file_name, String opr_msg)
			throws GeneralException {
		ServerSealLog ssL = new ServerSealLog();
		ssL.setUser_id(userid);
		ssL.setServer_id(serverid);
		ssL.setOpr_ip(ip);
		ssL.setOpr_time(new Timestamp(new Date().getTime()));
		ssL.setSeal_status(seal_status);
		ssL.setFile_name(file_name);
		ssL.setOpr_msg(opr_msg);
		log_dao.logAddSealServer(ssL);
	}

	public void logDelSealWrite(String sealid) throws GeneralException {
		log_dao.delLogSys(sealid);
	}

	public void cleanData(String syslog, String seallog, String server_log,
			String client_log) throws GeneralException {
		if (syslog.equals("1")) {// 选中了系统日志
			String sql = "delete from " + LogSysUtil.TABLE_NAME;
			log_dao.delete(sql);
		}
		if (seallog.equals("2")) {// 选中了印章日志
			String sql = "delete from " + LogSealWriteUtil.TABLE_NAME;
			log_dao.delete(sql);
		}
		if (server_log.equals("3")) {// 选中了服务端盖章日志
			String sql = "delete from " + ServerSealLogUtil.TABLE_NAME;
			log_dao.delete(sql);
		}
		if (client_log.equals("4")) {// 选中了客户端盖章日志
			String sql = "delete from " + SealLogUtil.TABLE_NAME;
			log_dao.delete(sql);
		}
	}

	@Override
	public PageSplit logSealWriteSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException {
		String str = SearchUtil.logSealSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<LogSealWriteVO> listVO = new ArrayList<LogSealWriteVO>();
		List<LogSealWrite> datas = log_dao.logSealWrite(sql);
		if (datas.size() > 0) {
			for (LogSealWrite logseal : datas) {
				SealBody sealbody = seal_body.getSealBodys(Integer
						.parseInt(logseal.getSeal_id()));
				LogSealWriteVO vo = new LogSealWriteVO();
				try {
					BeanUtils.copyProperties(vo, logseal);
					vo.setSeal_name(sealbody.getSeal_name());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
				listVO.add(vo);
			}
		}
		PageSplit ps = new PageSplit();
		ps.setDatas(listVO);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}

	/**
	 * 查询客户端盖章等操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	@Override
	public PageSplit logSealOperLog(int pageIndex, int pageSize, SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(SealLogUtil.TABLE_NAME);
		sb.append(" where " + SealLogUtil.LOG_TYPE + " not in( '2' )");
		if (f != null && f.getSeal_name() != null
				&& !f.getSeal_name().equals("")) {
			sb.append(" and " + SealLogUtil.SEAL_NAME + " like '%"
					+ f.getSeal_name() + "%'");
		}
		if (f != null && f.getUser_name() != null
				&& !f.getUser_name().equals("")) {
			sb.append(" and " + SealLogUtil.USER_NAME + " like '%"
					+ f.getUser_name() + "%'");
		}
		if (f != null && f.getUser_ip() != null && !f.getUser_ip().equals("")) {
			sb.append(" and " + SealLogUtil.IP + " like '%" + f.getUser_ip()
					+ "%'");
		}
		if (f != null && f.getBegin_time() != null
				&& !f.getBegin_time().equals("")) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealLogUtil.CREATE_TIME + " > to_date('"
						+ f.getBegin_time() + "','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealLogUtil.CREATE_TIME + " > '"
						+ f.getBegin_time() + " 00:00:00'");
			}
		}
		if (f != null && f.getEnd_time() != null && !f.getEnd_time().equals("")) {
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append(" and " + SealLogUtil.CREATE_TIME + " < to_date('"
						+ f.getEnd_time() + "','yyyy-mm-dd hh24:mi:ss')");
			}
			if (Constants.DB_TYPE == DBTypeUtil.DT_MYSQL) {
				sb.append(" and " + SealLogUtil.CREATE_TIME + " < '"
						+ f.getEnd_time() + " 00:00:00'");
			}
		}
		sb.append(" order by ");
		sb.append(SealLogUtil.CREATE_TIME);
		sb.append(" desc");
		String sqlPageSplit = DBTypeUtil.splitSql(sb.toString(), pageIndex,
				pageSize, Constants.DB_TYPE);
		List<Map> lists = role_dao.select(sqlPageSplit);
		List<SealLog> logs = new ArrayList<SealLog>();
		for (int i = 0; i < lists.size(); i++) {
			Map map = lists.get(i);
			SealLog log = new SealLog();
			log = (SealLog) DaoUtil.setPo(log, map, new SealLogUtil());
			logs.add(log);
		}
		PageSplit ps = new PageSplit();
		ps.setDatas(logs);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(sb.toString());
		ps.setTotalCount(totalCount);
		return ps;
	}

	/**
	 * 查询客户端打印操作日志
	 * 
	 * @param logSeal
	 * @throws DAOException
	 */
	public PageSplit logPrintOperLog(int pageIndex, int pageSize, SearchForm f) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(SealLogUtil.TABLE_NAME);
		sb.append(" where " + SealLogUtil.LOG_TYPE + " = '2' ");
		sb.append(" order by ");
		sb.append(SealLogUtil.CREATE_TIME);
		sb.append(" desc");
		String sqlPageSplit = DBTypeUtil.splitSql(sb.toString(), pageIndex,
				pageSize, Constants.DB_TYPE);
		List<Map> lists = role_dao.select(sqlPageSplit);
		List<SealLog> logs = new ArrayList<SealLog>();
		for (int i = 0; i < lists.size(); i++) {
			Map map = lists.get(i);
			SealLog log = new SealLog();
			log = (SealLog) DaoUtil.setPo(log, map, new SealLogUtil());
			logs.add(log);
		}
		PageSplit ps = new PageSplit();
		ps.setDatas(logs);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(sb.toString());
		ps.setTotalCount(totalCount);
		return ps;
	}

	public int[] getCount(String startTime, String endTime) {
		int a = -1;
		int b = -1;
		int c = -1;
		int d = -1;
		int e = -1;
		int f = -1;
		int[] arrays = new int[5];
		String daysqlone = "select count(*) from "
				+ ServerSealLogUtil.TABLE_NAME + " where to_char("
				+ ServerSealLogUtil.OPR_TIME
				+ ",'dd')=to_char(sysdate,'dd') and "
				+ ServerSealLogUtil.SEAL_STATUS + "='1'";
		String daysqltwo = "select count(*) from "
				+ ServerSealLogUtil.TABLE_NAME + " where to_char("
				+ ServerSealLogUtil.OPR_TIME + ",'dd')=to_char(sysdate,'dd')";
		String monthsqlone = "select * from " + ServerSealLogUtil.TABLE_NAME
				+ " where to_char(" + ServerSealLogUtil.OPR_TIME
				+ ",'mm')=to_char(sysdate,'mm') and "
				+ ServerSealLogUtil.SEAL_STATUS + "='1'";
		String monthsqltwo = "select * from " + ServerSealLogUtil.TABLE_NAME
				+ " where to_char(" + ServerSealLogUtil.OPR_TIME
				+ ",'mm')=to_char(sysdate,'mm')";
		a = log_dao.getSeallogCount(daysqlone);
		b = log_dao.getSeallogCount(daysqltwo);
		c = log_dao.getSeallogCount(monthsqlone);
		d = log_dao.getSeallogCount(monthsqltwo);
		if (startTime != null && !startTime.equals("") && endTime != null
				&& !endTime.equals("")) {
			String starttime = startTime + " 00:00:00";
			String endtime = endTime + " 00:00:00";
			// System.out.println(starttime+"----------"+endtime);
			String zisqlone = "select * from " + ServerSealLogUtil.TABLE_NAME
					+ " where " + ServerSealLogUtil.OPR_TIME
					+ " between to_date('" + starttime
					+ "','yyyy-mm-dd hh24:mi:ss') and to_date('" + endtime
					+ "','yyyy-mm-dd hh24:mi:ss') and "
					+ ServerSealLogUtil.SEAL_STATUS + "='1'";
			;
			String zisqltwo = "select * from " + ServerSealLogUtil.TABLE_NAME
					+ " where " + ServerSealLogUtil.OPR_TIME
					+ " between to_date('" + starttime
					+ "','yyyy-mm-dd hh24:mi:ss') and to_date('" + endtime
					+ "','yyyy-mm-dd hh24:mi:ss')";
			// System.out.println("zisqlone:"+zisqlone);
			// System.out.println("zisqltwo:"+zisqltwo);
			e = log_dao.getSeallogCount(zisqlone);
			f = log_dao.getSeallogCount(zisqltwo);
		}
		arrays = new int[] { a, b, c, d, e, f };
		return arrays;
	}

	public int[] huizhigetCount(String startTime, String endTime) {
		int a = -1;
		int b = -1;
		int c = -1;
		int d = -1;
		int e = -1;
		int f = -1;
		int[] arrays = new int[5];
		String daysqlone = "select count(*) from "
				+ ServerSealLogUtil.TABLE_NAME + " where to_char("
				+ ServerSealLogUtil.OPR_TIME
				+ ",'dd')=to_char(sysdate,'dd') and "
				+ ServerSealLogUtil.SEAL_STATUS + "='1'";
		String daysqltwo = "select count(*) from "
				+ ServerSealLogUtil.TABLE_NAME + " where to_char("
				+ ServerSealLogUtil.OPR_TIME + ",'dd')=to_char(sysdate,'dd')";
		String monthsqlone = "select * from " + ServerSealLogUtil.TABLE_NAME
				+ " where to_char(" + ServerSealLogUtil.OPR_TIME
				+ ",'mm')=to_char(sysdate,'mm') and "
				+ ServerSealLogUtil.SEAL_STATUS + "='1'";
		String monthsqltwo = "select * from " + ServerSealLogUtil.TABLE_NAME
				+ " where to_char(" + ServerSealLogUtil.OPR_TIME
				+ ",'mm')=to_char(sysdate,'mm')";
		a = log_dao.getHuizhilogCount(daysqlone);
		b = log_dao.getHuizhilogCount(daysqltwo);
		c = log_dao.getHuizhilogCount(monthsqlone);
		d = log_dao.getHuizhilogCount(monthsqltwo);
		if (startTime != null && !startTime.equals("") && endTime != null
				&& !endTime.equals("")) {
			String starttime = startTime + " 00:00:00";
			String endtime = endTime + " 00:00:00";
			// System.out.println(starttime+"----------"+endtime);
			String zisqlone = "select * from " + HuiZhiLogUtil.TABLE_NAME
					+ " where " + HuiZhiLogUtil.OPR_TIME + " between to_date('"
					+ starttime + "','yyyy-mm-dd hh24:mi:ss') and to_date('"
					+ endtime + "','yyyy-mm-dd hh24:mi:ss') and "
					+ HuiZhiLogUtil.STATUS + "='1'";
			;
			String zisqltwo = "select * from " + HuiZhiLogUtil.TABLE_NAME
					+ " where " + HuiZhiLogUtil.OPR_TIME + " between to_date('"
					+ starttime + "','yyyy-mm-dd hh24:mi:ss') and to_date('"
					+ endtime + "','yyyy-mm-dd hh24:mi:ss')";
			// System.out.println("zisqlone:"+zisqlone);
			// System.out.println("zisqltwo:"+zisqltwo);
			e = log_dao.getHuizhilogCount(zisqlone);
			f = log_dao.getHuizhilogCount(zisqltwo);
		}
		arrays = new int[] { a, b, c, d, e, f };
		return arrays;
	}

	public PageSplit logSealServerSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(ServerSealLogUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		sb.append(" order by ");
		sb.append(ServerSealLogUtil.OPR_TIME);
		sb.append(" desc");
		String sql = DBTypeUtil.splitSql(sb.toString(), pageIndex, pageSize,
				Constants.DB_TYPE);
		List<ServerSealLog> datas = log_dao.logSealServer(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(sb.toString());
		ps.setTotalCount(totalCount);
		return ps;
	}

	public PageSplit huizhiLogSel(int pageIndex, int pageSize, SearchForm f)
			throws GeneralException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(HuiZhiLogUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		sb.append(" order by ");
		sb.append(HuiZhiLogUtil.OPR_TIME);
		sb.append(" desc");
		String sql = DBTypeUtil.splitSql(sb.toString(), pageIndex, pageSize,
				Constants.DB_TYPE);
		List<HuiZhiLog> datas = log_dao.logHuizhi(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = role_dao.count(sb.toString());
		ps.setTotalCount(totalCount);
		return ps;
	}

	public void deleteSealSys() {
		int savetime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("logData_savetime").toString().trim());
		SimpleDateFormat sdfFileName = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date nowTime = new Date(System.currentTimeMillis());
		String nowTimeStr = sdfFileName.format(nowTime);
		String sql = "delete from " + ServerSealLogUtil.TABLE_NAME
				+ " where (to_date('" + nowTimeStr
				+ "','yyyy-mm-dd hh24:mi:ss')-" + ServerSealLogUtil.OPR_TIME
				+ ")>" + savetime;
		logger.info("删除签章日志sql:" + sql);
		log_dao.delete(sql);
	}

	public void deleteLogSys() {
		int savetime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("logData_savetime").toString().trim());
		SimpleDateFormat sdfFileName = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date nowTime = new Date(System.currentTimeMillis());
		String nowTimeStr = sdfFileName.format(nowTime);
		String sql = "delete from " + LogSysUtil.TABLE_NAME
				+ " where (to_date('" + nowTimeStr
				+ "','yyyy-mm-dd hh24:mi:ss')-" + LogSysUtil.OPR_TIME + ")>"
				+ savetime;
		logger.info("删除系统操作日志sql:" + sql);
		log_dao.delete(sql);
	}

}
