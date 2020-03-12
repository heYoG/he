package com.dj.seal.hotel.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.NSHRecordDaoImpl;
import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.organise.dao.impl.SysUserDaoImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.QueryLog;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.NSHRecordUtil;
import com.dj.seal.util.web.SearchForm;

public class NSHRecordServiceImpl implements NSHRecordService {

	static Logger logger = LogManager.getLogger(NSHRecordServiceImpl.class
			.getName());

	private NSHRecordDaoImpl recordDao;
	private SysUserDaoImpl usersDao;
	private SysDeptService dept_srv;

	public NSHRecordDaoImpl getRecordDao() {
		return recordDao;
	}

	public void setRecordDao(NSHRecordDaoImpl recordDao) {
		this.recordDao = recordDao;
	}

	public SysUserDaoImpl getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(SysUserDaoImpl usersDao) {
		this.usersDao = usersDao;
	}

	/**
	 * 添加方法
	 */
	@Override
	public String addRecord(NSHRecordPo record) {
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = time.format(new Date()) + "-"
				+ UUID.randomUUID().toString();
		record.setCid(id);
		recordDao.addRecord(record);
		return id;
	}

	/**
	 * 添加方法 生成月表
	 */
	@Override
	public String addRecordNew(NSHRecordPo record) {
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = time.format(new Date()) + "-"
				+ UUID.randomUUID().toString();
		record.setCid(id);
		recordDao.addRecordNew(record);
		return id;
	}

	private static boolean chk(String s) {
		return s != null && !"".equals(s);
	}

	@SuppressWarnings("unused")
	private static boolean chk(Integer i) {
		return i != null && i != 0;
	}

	@SuppressWarnings("unused")
	private static boolean chk(Timestamp t) {
		return t != null && !"".equals(t);
	}

	@Override
	public List<NSHRecordPo> ShowRecord() {
		String sql = "";
		return recordDao.getAllRecordPoBy(sql);
	}

	// 加入月表功能，查询单据表
	public String getSqlByFormNew(SearchForm f) {
		StringBuffer sb = new StringBuffer();
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate) + " 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate) + "-01 00:00:00";
		if (chk(f.getDate_type())) {
			String dateType = f.getDate_type();
			logger.info("类型-----" + dateType);
			if (dateType.equals("1")) {// 当天
				sb.append("select *  from ");
				sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
				if (f.getKeyword() != null && f.getKeyword() != "") {
					sb.append(" INNER JOIN ")
							.append(HotelRecordContent.TABLE_NAME)
							.append(yuestr).append(" ON ")
							.append(NSHRecordUtil.TABLE_NAME).append(yuestr)
							.append(".").append(NSHRecordUtil.CID).append("=")
							.append(HotelRecordContent.TABLE_NAME)
							.append(yuestr).append(".")
							.append(HotelRecordContent.RID);
				}
				sb.append(" where ");
				sb.append(NSHRecordUtil.CUPLOADTIME).append(">");
				if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
					sb.append("to_date('").append(todaydate0)
							.append("','yyyy-mm-dd hh24:mi:ss')");
				} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
					sb.append("'").append(todaydate0).append("'");
				}
				addWhere(sb, f, yuestr);
				sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
						.append(" desc");
			} else if (dateType.equals("2")) {// 当月
				sb.append("select *  from ");
				sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
				if (f.getKeyword() != null && f.getKeyword() != "") {
					sb.append(" INNER JOIN ")
							.append(HotelRecordContent.TABLE_NAME)
							.append(yuestr).append(" ON ")
							.append(NSHRecordUtil.TABLE_NAME).append(yuestr)
							.append(".").append(NSHRecordUtil.CID).append("=")
							.append(HotelRecordContent.TABLE_NAME)
							.append(yuestr).append(".")
							.append(HotelRecordContent.RID);
				}
				sb.append(" where ");
				sb.append(NSHRecordUtil.CUPLOADTIME).append(">");
				if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
					sb.append("to_date('").append(todaydate0)
							.append("','yyyy-mm-dd hh24:mi:ss')");
				} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
					sb.append("'").append(todaydate0).append("'");
				}
				addWhere(sb, f, yuestr);
				sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
						.append(" desc");
			} else if (dateType.equals("7")) {// 自定义
				if (!chk(f.getBegin_time()) || !chk(f.getEnd_time())) {// 开始时间或结束时间为空

				} else {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date begin_date = null;
					Date end_date = null;
					try {
						begin_date = sdf.parse(f.getBegin_time() + " 00:00:00");
						end_date = sdf.parse(f.getEnd_time() + " 23:59:59");
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
					int begindateyear = begin_date.getYear() + 1900;
					int begindatemonth = begin_date.getMonth() + 1;
					// int begindatedate = begin_date.getDate();
					int enddateyear = end_date.getYear() + 1900;
					int enddatemonth = end_date.getMonth() + 1;
					// int enddatedate = end_date.getDate();
					int yue2 = enddatemonth + (enddateyear - begindateyear)
							* 12;
					int yuecha = yue2 - begindatemonth;
					if (yuecha == 0) {// 查询同一个月的数据
						String yueStr = "";
						if (begindatemonth < 10) {
							yueStr = enddateyear + "0"
									+ Integer.toString(begindatemonth);
						} else {
							yueStr = enddateyear
									+ Integer.toString(begindatemonth);
						}
						sb.append("select *  from ");
						sb.append(NSHRecordUtil.TABLE_NAME).append(yueStr);
						if (f.getKeyword() != null && f.getKeyword() != "") {
							sb.append(" INNER JOIN ")
									.append(HotelRecordContent.TABLE_NAME)
									.append(yuestr).append(" ON ")
									.append(NSHRecordUtil.TABLE_NAME)
									.append(yuestr).append(".")
									.append(NSHRecordUtil.CID).append("=")
									.append(HotelRecordContent.TABLE_NAME)
									.append(yuestr).append(".")
									.append(HotelRecordContent.RID);
						}
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType(
								NSHRecordUtil.CUPLOADTIME,
								sdf.format(begin_date), sdf.format(end_date)));
						addWhere(sb, f, yueStr);
						sb.append(" order by ")
								.append(NSHRecordUtil.CUPLOADTIME)
								.append(" desc");
					} else {// 查询多月的数据
						sb.append("select distinct a.*  from (");
						if (enddatemonth < 10) {
							yuestr = enddateyear + "0"
									+ Integer.toString(enddatemonth);
						} else {
							yuestr = enddateyear
									+ Integer.toString(enddatemonth);
						}
						sb.append("select *  from ");
						sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
						if (f.getKeyword() != null && f.getKeyword() != "") {
							sb.append(" INNER JOIN ")
									.append(HotelRecordContent.TABLE_NAME)
									.append(yuestr).append(" ON ")
									.append(NSHRecordUtil.TABLE_NAME)
									.append(yuestr).append(".")
									.append(NSHRecordUtil.CID).append("=")
									.append(HotelRecordContent.TABLE_NAME)
									.append(yuestr).append(".")
									.append(HotelRecordContent.RID);
						}
						sb.append(" where ");
						sb.append(DBTypeUtil.dateSqlByDBType(
								NSHRecordUtil.CUPLOADTIME,
								sdf.format(begin_date), sdf.format(end_date)));
						addWhere(sb, f, yuestr);
						int thisMonth = enddatemonth;
						for (int i = 0; i < yuecha; i++) {
							thisMonth = thisMonth - 1;
							if (thisMonth == 0) {
								thisMonth = 12;
								enddateyear = enddateyear - 1;
							}
							if (thisMonth < 10) {
								String newsss = enddateyear + "0"
										+ Integer.toString(thisMonth);
								yuestr = newsss;
							} else {
								String newsss = enddateyear
										+ Integer.toString(thisMonth);
								yuestr = newsss;
							}
							sb.append(" UNION ALL ");
							sb.append("select *  from ");
							sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
							if (f.getKeyword() != null && f.getKeyword() != "") {
								sb.append(" INNER JOIN ")
										.append(HotelRecordContent.TABLE_NAME)
										.append(yuestr).append(" ON ")
										.append(NSHRecordUtil.TABLE_NAME)
										.append(yuestr).append(".")
										.append(NSHRecordUtil.CID).append("=")
										.append(HotelRecordContent.TABLE_NAME)
										.append(yuestr).append(".")
										.append(HotelRecordContent.RID);
							}
							sb.append(" where ");
							sb.append(DBTypeUtil.dateSqlByDBType(
									NSHRecordUtil.CUPLOADTIME,
									sdf.format(begin_date),
									sdf.format(end_date)));
							addWhere(sb, f, yuestr);
						}
						sb.append(") a order by a.")
								.append(NSHRecordUtil.CUPLOADTIME)
								.append(" desc");
					}
				}
			}
		} else {
			// 如果查询时限类型为空，则默认查当天的
			sb.append("select *  from ");
			sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ");
			sb.append(NSHRecordUtil.CUPLOADTIME).append(">");
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append("to_date('").append(todaydate0)
						.append("','yyyy-mm-dd hh24:mi:ss')");
			} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
				sb.append("'").append(todaydate0).append("'");
			}
			addWhere(sb, f, yuestr);
			sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
					.append(" desc");
		}

		logger.info("多表：：：：" + sb.toString());
		return sb.toString();
	}

	public String addWhere(StringBuffer sb, SearchForm f, String yuestr) {
		// 按照流水号查询
		if (chk(f.getSerialNo())) {
			sb.append(" and ").append(NSHRecordUtil.CASESEQID)
					.append(" like '%").append(f.getSerialNo()).append("%'");
		}
		// 操作员账号查询
		if (chk(f.getTellerNo())) {
			sb.append(" and ").append(NSHRecordUtil.TELLERID)
					.append(" like '%").append(f.getTellerNo()).append("%'");
		}
		if (chk(f.getCeid())) {
			sb.append(" and ").append(NSHRecordUtil.CEID).append(" like '%")
					.append(f.getCeid()).append("%'");
		}
		// 机构号
		if (chk(f.getOrgunit())) {
			sb.append(" and ").append(NSHRecordUtil.ORGUNIT).append(" = '")
					.append(f.getOrgunit()).append("'");
		}
		return sb.toString();
	}

	public String getSqlByForm(SearchForm f) {
		Date nowdate = new Date();
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat yue_formatter = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		String todaydate0 = today_formatter.format(nowdate) + " 00:00:00";
		String yuedate0 = yue_formatter.format(nowdate) + "-01 00:00:00";
		StringBuffer sb = new StringBuffer();
		sb.append("select *  from ");
		sb.append(NSHRecordUtil.TABLE_NAME);
		sb.append(" where 1=1 ");
		if (chk(f.getDate_type())) {
			String dateType = f.getDate_type();
			// logger.info("类型-----"+dateType);
			if (dateType.equals("1")) {// 当天
				sb.append(" and ").append(NSHRecordUtil.CUPLOADTIME);
				sb.append(" > ");
				if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
					sb.append("to_date('").append(todaydate0)
							.append("','yyyy-mm-dd hh24:mi:ss')");
				} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
					sb.append("'").append(todaydate0).append("'");
				}
				addWhere(sb, f, yuestr);
				sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
						.append(" desc");
			} else if (dateType.equals("2")) {// 当月
				sb.append(" and ");
				sb.append(NSHRecordUtil.CUPLOADTIME).append(">");
				if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
					sb.append("to_date('").append(yuedate0)
							.append("','yyyy-mm-dd hh24:mi:ss')");
				} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
					sb.append("'").append(yuedate0).append("'");
				}
				addWhere(sb, f);
				sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
						.append(" desc");
			} else if (dateType.equals("7")) {// 自定义
				if (!chk(f.getBegin_time()) || !chk(f.getEnd_time())) {// 开始时间或结束时间为空

				} else {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					Date begin_date = null;
					Date end_date = null;
					try {
						begin_date = sdf.parse(f.getBegin_time() + " 00:00:00");
						end_date = sdf.parse(f.getEnd_time() + " 23:59:59");
					} catch (ParseException e) {
						logger.error(e.getMessage());
					}
					sb.append(" and ");
					sb.append(DBTypeUtil.dateSqlByDBType(
							NSHRecordUtil.CUPLOADTIME, sdf.format(begin_date),
							sdf.format(end_date)));
					addWhere(sb, f, yuestr);
					sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
							.append(" desc");
				}
			}
			// addWhere(sb,f);
		} else {
			// 如果查询时限类型为空，则默认查当天的
			sb.append(" and ");
			sb.append(NSHRecordUtil.CUPLOADTIME).append(">");
			if (Constants.DB_TYPE == DBTypeUtil.DT_ORCL) {
				sb.append("to_date('").append(todaydate0)
						.append("','yyyy-mm-dd hh24:mi:ss')");
			} else if (Constants.DB_TYPE == DBTypeUtil.DT_MSSQL) {
				sb.append("'").append(todaydate0).append("'");
			}
			addWhere(sb, f, yuestr);
			sb.append(" order by ").append(NSHRecordUtil.CUPLOADTIME)
					.append(" desc");
			logger.info("recordPoSql:" + sb.toString());
		}

		// 增加查询条件

		return sb.toString();
	}

	public String addWhere(StringBuffer sb, SearchForm f) {
		// 按照流水号查询
		if (chk(f.getSerialNo())) {
			sb.append(" and ").append(NSHRecordUtil.CASESEQID)
					.append(" like '%").append(f.getSerialNo()).append("%'");
		}
		// 操作员账号查询
		if (chk(f.getTellerNo())) {
			sb.append(" and ").append(NSHRecordUtil.TELLERID)
					.append(" like '%").append(f.getTellerNo()).append("%'");
		}
		if (chk(f.getCeid())) {
			sb.append(" and ").append(NSHRecordUtil.CEID).append(" like '%")
					.append(f.getCeid()).append("%'");
		}
		if (chk(f.getCip())) {
			sb.append(" and ").append(NSHRecordUtil.CIP).append(" like '%")
					.append(f.getCip()).append("%'");
		}
		// 机构号
		if (chk(f.getOrgunit())) {
			sb.append(" and ").append(NSHRecordUtil.ORGUNIT).append(" = '")
					.append(f.getOrgunit()).append("'");
		}
		// if (chk(f.getBegin_time())) {
		// sb.append(" and ").append(NSHRecordUtil.CUPLOADTIME);
		// sb.append(" > '");
		// sb.append(f.getBegin_time()).append(" 00:00:00'");
		// }
		// if (chk(f.getEnd_time())) {
		// sb.append(" and ").append(NSHRecordUtil.CUPLOADTIME);
		// sb.append(" < '");
		// sb.append(f.getEnd_time()).append(" 23:59:59'");
		// }
		return sb.toString();
	}

	// public PageSplit showRecordBySch(int pageIndex, int pageSize, SearchForm
	// f)
	// throws Exception {
	// PageSplit ps = new PageSplit();
	// String selectsql = getSqlByForm(f);
	// logger.info("selectsql:"+selectsql);
	// String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
	// Constants.DB_TYPE);
	// List<RecordVO> datas = listObjs(sql);
	// ps.setDatas(datas);
	// ps.setNowPage(pageIndex);
	// ps.setPageSize(pageSize);
	// int totalCount = recordDao.showCount(selectsql);
	// ps.setTotalCount(totalCount);
	// return ps;
	// }

	public PageSplit showRecordBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		PageSplit ps = new PageSplit();
		String selectsql = getSqlByForm(f);
		String sql = DBTypeUtil.splitSql(selectsql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<NSHRecordPo> datas = listObjs(sql);
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = recordDao.showCount(selectsql);
		ps.setTotalCount(totalCount);
		return ps;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<NSHRecordPo> listObjs(String sql) throws Exception {
		List<NSHRecordPo> list_obj = new ArrayList<NSHRecordPo>();
		List<Map> list = recordDao.select(sql);
		for (Map map : list) {
			NSHRecordPo obj = new NSHRecordPo();
			obj = (NSHRecordPo) DaoUtil.setPo(obj, map, new NSHRecordUtil());
			list_obj.add(obj);// 不查询关联的属性信息。
			}
		return list_obj;
	}

	public NSHRecordPo getRecord(String cid) throws Exception {
		return recordDao.getRecordPOById(cid);
	}

	@Override
	public List<NSHRecordPo> getRecordPos(String strs) {
		return recordDao.getRecordPos(strs);
	}

	public SysDeptService getDept_srv() {
		return dept_srv;
	}

	public void setDept_srv(SysDeptService dept_srv) {
		this.dept_srv = dept_srv;
	}

	@Override
	public List<NSHRecordPo> getRecordsBySql(String sql) throws Exception {
		return listObjs(sql);
	}

	@Override
	public int backupRecords() {
		return recordDao.backupRecords();
	}

	@Override
	public int delRecords() {
		return recordDao.delRecords();
	}

	@Override
	public List<NSHRecordPo> TradeData(String valcode, String date,
			String consumerSq) {// 网银查询接口
		return recordDao.TradeData(valcode, date, consumerSq);
	}

	@Override
	public String addRecordYZH(NSHRecordPo rdp) {// 保存有纸化请求
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = time.format(new Date()) + "-"
				+ UUID.randomUUID().toString();
		rdp.setCid(id);
		rdp.setStatus("1");
		recordDao.addRecordYZH(rdp);
		return id;

	}

	@Override
	public NSHRecordPo checkValcode(String valcode) {// 打印判断是否第一次请求
		return recordDao.checkValcode(valcode);
	}

	@Override
	public DeptForm getDeptNameByOrgUnit(String orgunit) {// 根据机构号查询对应部门
		return recordDao.getDeptNameByOrgUnit(orgunit);
	}

	@Override
	public int delRecordOfHistory() {// 删除>1年的单据历史表数据
		return recordDao.delRecordOfHistory();
	}

	@Override
	public NSHRecordPo getMaxRequired_printNum(String valcode) {// 获取请求次数
		return recordDao.getMaxRequired_printNum(valcode);
	}

	@Override
	public void updateCEID(String CEID, String voucherNo, String caseseqid) {// 无纸化接口旧三要素保存CEID
		recordDao.updateCEID(CEID, voucherNo, caseseqid);

	}

	@Override
	public void addQueryLog(QueryLog querylog) {//添加查询记录
		recordDao.addQueryLog(querylog);

	}

	@Override
	public int delServerSealLog() {//删除服务端盖章历史表日志
		return recordDao.delServerSealLog();
	}

	@Override
	public void updatePrintNum(String valcode, Number printNum, String caseseqid,Number requiredNum) {//更新打印份数
		recordDao.updatePrintNum(valcode, printNum, caseseqid,requiredNum);
	}

	@Override
	public void updateInfo_plus(String caseseqid, String info_plus,Number requiredNum) {//更新附件信息
		recordDao.updateInfo_plus(caseseqid, info_plus,requiredNum);
		
	}

}
