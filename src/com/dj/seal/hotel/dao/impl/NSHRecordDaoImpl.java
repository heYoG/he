package com.dj.seal.hotel.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.NSHRecordDao;
import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.organise.web.form.DeptForm;
import com.dj.seal.structure.dao.po.QueryLog;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.NSHRecordUtil;
import com.dj.seal.util.table.QueryLogUtil;
import com.dj.seal.util.table.ServerSealLogUtil;
import com.dj.seal.util.table.SysDepartmentUtil;

public class NSHRecordDaoImpl extends BaseDAOJDBC implements NSHRecordDao {

	static Logger logger = LogManager.getLogger(NSHRecordDaoImpl.class
			.getName());

	private NSHRecordUtil table = new NSHRecordUtil();// 单据表
	private QueryLogUtil QU = new QueryLogUtil();// 查询记录表
	private SysDepartmentUtil table2 = new SysDepartmentUtil();

	public NSHRecordUtil getTable() {
		return table;
	}

	public void setTable(NSHRecordUtil table) {
		this.table = table;
	}

	@Override
	public void addRecord(NSHRecordPo record) {
		String sql = ConstructSql.composeInsertSql(record, table);
		//logger.info("无纸化保存单据sql:" + sql);
		add(sql);
	}

	// 添加单据月表
	@Override
	public void addRecordNew(NSHRecordPo record) {
		String sql = ConstructSql.composeInsertSqlNew(record, table,
				getTableName(NSHRecordUtil.TABLE_NAME));
		add(sql);
	}

	@Override
	public List<NSHRecordPo> getAllRecordPoBy(String sql) throws DAOException {
		return formList(sql);
	}

	@Override
	public NSHRecordPo getRecordPoBySql(String sql) throws DAOException {
		return form(sql);
	}

	private NSHRecordPo form(String sql) {
		NSHRecordPo obj = new NSHRecordPo();
		List list = select(sql);// 调用父类方法
		int a = list.size();
		if (a != 0) {
			Map map = (Map) list.get(0);
			obj = (NSHRecordPo) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<NSHRecordPo> formList(String sql) {
		List<NSHRecordPo> listObj = new ArrayList<NSHRecordPo>();
		List<Map> list = select(sql);
		for (Map map : list) {
			NSHRecordPo form = new NSHRecordPo();
			form = (NSHRecordPo) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}

	public List<NSHRecordPo> getRecordPos(String selStr) {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		int sizeno = r_nos.length;
		for (int i = 0; i < sizeno; i++) {
			if (i != 0) {
				sb.append(" UNION ");
			}
			String recordid = r_nos[i];
			String yuestr = recordid.substring(0, 6);
			sb.append("select ");
			sb.append(NSHRecordUtil.CEID);
			sb.append(", ");
			sb.append(NSHRecordUtil.CUPLOADTIME);
			sb.append(", ");
			sb.append(NSHRecordUtil.ORGUNIT);
			sb.append(", ");
			sb.append(NSHRecordUtil.REMARKS);
			sb.append(", ");
			sb.append(NSHRecordUtil.TELLERID);
			sb.append(", ");
			sb.append(NSHRecordUtil.CID);
			sb.append(", ");
			sb.append(NSHRecordUtil.CIP);
			sb.append(", ");
			sb.append(NSHRecordUtil.TRANCODE);
			sb.append(", ");
			sb.append(HotelRecordUtil.UPLOADUSERID);
			sb.append("  from ");
			sb.append(NSHRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(NSHRecordUtil.CID).append(" ='");
			sb.append(recordid).append("'");
		}
		// String[] r_nos = selStr.split(",");
		// StringBuffer sb = new StringBuffer();
		// String recordid = r_nos[0];
		// String yuestr = recordid.substring(0, 6);
		// sb.append(" select * from ");
		// sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(" where 1=1 ");
		// sb.append(" and ").append(HotelRecordUtil.CID).append(" in('");
		// for (String str : r_nos) {
		// sb.append(str).append("','");
		// }
		// sb.append("')");
		return formList(sb.toString());
	}

	@Override
	public int showCount(String sql) {
		return count(sql);
	}

	@Override
	public NSHRecordPo getRecordPOById(String cid) throws DAOException {
		String sql = "select * from " + NSHRecordUtil.TABLE_NAME + " where "
				+ NSHRecordUtil.CID + " = '" + cid + "'";
		return form(sql);
	}

	@Override
	public String getTableName(String tbName) {
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		return tbName + yuestr;
	}

	@Override
	public int isRecordExist(String fName) {
		// TODO Auto-generated method stub
		String tableName = getTableName("T_NSHRECORD");
		String sql = "SELECT count(*) FROM " + tableName + " WHERE "
				+ tableName + ".cfileFileName = '" + fName + "'";
		int i = getJdbcTemplate().queryForInt(sql);
		return i;
	}

	@Override
	public int backupRecords() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("Data_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "insert into " + NSHRecordUtil.TABLE_NAME
				+ "_history select*from " + NSHRecordUtil.TABLE_NAME
				+ " where (to_date('" + nowTime + "','yyyy-mm-dd hh24:mi:ss')-"
				+ NSHRecordUtil.CUPLOADTIME + ")>" + saveTime;

		//logger.info("备份单据数据sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}

	@Override
	public int delRecords() {
		int saveTime = Integer.parseInt(DJPropertyUtil
				.getPropertyValue("Data_savetime").toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "delete from " + NSHRecordUtil.TABLE_NAME
				+ " where (to_date('" + nowTime + "','yyyy-mm-dd hh24:mi:ss')-"
				+ NSHRecordUtil.CUPLOADTIME + ")>" + saveTime;
		//logger.info("删除原表已备份单据sql:" + sql);
		int a = getJdbcTemplate().update(sql);
		return a;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NSHRecordPo> TradeData(String valcode, String date,
			String consumerSq) {
		String sql = null;

		/* 在单据原始表查询 */
		if (date == null || "".equals(date)) {// 未传日期
			if (valcode == null || "".equals(valcode)) {// 柜面查询(按流水号)
				sql = "select*from " + NSHRecordUtil.TABLE_NAME
						+ " where caseseqid='" + consumerSq + "'";
			} else {// 微信查询(按验证码)
				sql = "select*from " + NSHRecordUtil.TABLE_NAME+ " where valcode='" + valcode + "'";
			}
		} else {// 传了日期
			StringBuffer sf = new StringBuffer(date);
			StringBuffer insert1 = sf.insert(4, "-");
			StringBuffer insert2 = insert1.insert(7, "-");
			if (valcode == null || "".equals(valcode)) {// 柜面查询(按流水号、日期)
				sql = "select*from " + NSHRecordUtil.TABLE_NAME
						+ " where caseseqid='" + consumerSq
						+ "' and d_trandt=to_date('" + insert2.toString()+ "','yyyy-mm-dd')";
			} else {// 微信查询(按验证码、日期)
				sql = "select*from " + NSHRecordUtil.TABLE_NAME
						+ " where valcode='" + valcode
						+ "' and d_trandt=to_date('" + insert2.toString()
						+ "','yyyy-mm-dd')";
			}
		}

		/* 在单据历史表查询 */
		if (formList_NSHR(sql) == null || "".equals(formList_NSHR(sql))) {
			if (date == null || "".equals(date)) {// 未传日期
				if (valcode == null || "".equals(valcode)) {// 柜面查询(按流水号)
					sql = "select*from " + NSHRecordUtil.TABLE_NAME
							+ "_HISTORY where caseseqid='" + consumerSq + "'";
				} else {// 微信查询(按验证码)
					sql = "select*from " + NSHRecordUtil.TABLE_NAME
							+ "_HISTORY where valcode='" + valcode + "'";
				}
			} else {// 传了日期
				StringBuffer sf = new StringBuffer(date);
				StringBuffer insert1 = sf.insert(4, "-");
				StringBuffer insert2 = insert1.insert(7, "-");
				if (valcode == null || "".equals(valcode)) {// 柜面查询(按流水号、日期)
					sql = "select*from " + NSHRecordUtil.TABLE_NAME
							+ "_HISTORY where caseseqid='" + consumerSq
							+ "' and d_trandt=to_date('" + insert2.toString()
							+ "','yyyy-mm-dd')";
				} else {// 微信查询(按验证码、日期)
					sql = "select*from " + NSHRecordUtil.TABLE_NAME
							+ "_HISTORY where valcode='" + valcode
							+ "' and d_trandt=to_date('" + insert2.toString()
							+ "','yyyy-mm-dd')";
				}
			}
		}
		logger.info("valcode:"+valcode+",consumerSq:"+consumerSq+"查询接口sql：" + sql);
		return formList_NSHR(sql);
	}

	@Override
	public void addRecordYZH(NSHRecordPo rdp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = new Date(System.currentTimeMillis());
		String tranDt = sdf2.format(rdp.getD_trandt());

		String nowTime = sdf.format(dt);
		String sql = "insert into "
				+ NSHRecordUtil.TABLE_NAME
				+ " (cid,cuploadtime,caseseqid,trancode,tellerid,orgunit,d_trandt,d_tranname,tranorgname,authtellerno,valcode,status,requirednum,bp1,bp2,bp3,info_plus,printnum) values ('"
				+ rdp.getCid() + "',to_date('" + nowTime
				+ "','yyyy-mm-dd hh24:mi:ss'),'" + rdp.getCaseseqid() + "','"
				+ rdp.getTrancode() + "','" + rdp.getTellerid() + "','"
				+ rdp.getOrgunit() + "',to_date('" + tranDt
				+ "','yyyy-mm-dd'),'" + rdp.getD_tranname() + "','"
				+ rdp.getTranorgname() + "','" + rdp.getAuthtellerno() + "','"
				+ rdp.getValcode() + "','" + rdp.getStatus() + "',"
				+ rdp.getRequirednum() + ",'" + rdp.getBp1() + "','" + rdp.getBp2() + "','"+rdp.getBp3()+"','"+rdp.getInfo_plus()+"',"+rdp.getPrintnum()+")";
		//logger.info("有纸化添加单据sql:" + sql);
		add(sql);

	}

	@Override
	public NSHRecordPo checkValcode(String valcode) {// 根据验证码判断单据是否存在
		String sql = "";
		sql = "select REQUIREDNUM from " + NSHRecordUtil.TABLE_NAME + " where valcode='"
				+ valcode + "'";
		//logger.info("验证是否为旧三要素sql:" + sql);
		return getRecordPoBySql(sql);
	}

	@Override
	public DeptForm getDeptFormBySql(String sql) throws DAOException {
		return form2(sql);
	}

	@SuppressWarnings("unchecked")
	private DeptForm form2(String sql) {
		DeptForm obj = new DeptForm();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (DeptForm) DaoUtil.setPo(obj, map, table2);
			return obj;
		} else {
			return null;
		}
	}

	@Override
	public DeptForm getDeptNameByOrgUnit(String orgunit) {
		String sql = "";
		sql = "select C_ABC from " + SysDepartmentUtil.TABLE_NAME
				+ " where C_HXDEPTID='" + orgunit + "'";
		//logger.info("根据机构号查询对应机构名称sql:" + sql);
		return getDeptFormBySql(sql);
	}

	@Override
	public int delRecordOfHistory() {
		int saveTime = Integer
				.parseInt(DJPropertyUtil
						.getPropertyValue("RecordOfHistory_savetime")
						.toString().trim());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dt = new Date(System.currentTimeMillis());
		String nowTime = sdf.format(dt);
		String sql = "delete from " + NSHRecordUtil.TABLE_NAME
				+ "_HISTORY where (to_date('" + nowTime
				+ "','yyyy-mm-dd hh24:mi:ss')-" + NSHRecordUtil.CUPLOADTIME
				+ ")>" + saveTime;
		//logger.info("删除单据历史表数据Sql:" + sql);
		return getJdbcTemplate().update(sql);
	}

	@Override
	public NSHRecordPo getMaxRequired_printNum(String valcode) {// 根据验证码查询最大请求次数、最大打印份数
		String sql = "";

		// 在原始表查
		sql = "select t1.requirednum,t1.printnum from "
				+ NSHRecordUtil.TABLE_NAME + " t1 where t1.requirednum =(select max(t2.requirednum) from "+NSHRecordUtil.TABLE_NAME +" t2 where t2.valcode='"+valcode+"') and t1.valcode='"+valcode+"'" ;
		if (getNum(sql) == null || "".equals(getNum(sql))) {// 在历史表查
			sql="select t1.requirednum,t1.printnum from "
				+ NSHRecordUtil.TABLE_NAME + "_HISTORY t1 where t1.requirednum =(select max(t2.requirednum) from "+NSHRecordUtil.TABLE_NAME +"_HISTORY t2 where t2.valcode='"+valcode+"') and t1.valcode='"+valcode+"'";
		}
		//logger.info("查询请求/打印最大数Sql:"+sql);
		return getNum(sql);

	}

	@SuppressWarnings("rawtypes")
	public NSHRecordPo getNum(String sql) {
		NSHRecordPo np = new NSHRecordPo();
		List list = select(sql);
		if (list.size() > 0) {
			Map map = (Map) list.get(0);
			np = (NSHRecordPo) DaoUtil.setPo(np, map, table);
			if (np.getRequirednum() != null)
				return np;
		}
		return null;
	}

	private List<NSHRecordPo> formList_NSHR(String sql) {
		List<NSHRecordPo> listObj = new ArrayList<NSHRecordPo>();
		List<Map> list = select(sql);
		if (list.size() > 0) {
			for (Map map : list) {
				NSHRecordPo form = new NSHRecordPo();
				form = (NSHRecordPo) DaoUtil.setPo(form, map, table);
				listObj.add(form);
			}
			return listObj;
		}
		return null;
	}

	@Override
	/* 无纸化接口旧三要素更新数据 */
	public void updateCEID(String CEID, String voucherNo, String caseseqid) {
		String sql = "update " + NSHRecordUtil.TABLE_NAME + " set CEID='"
				+ CEID + "',VOUCHERNO='" + voucherNo + "' where CASESEQID='"
				+ caseseqid + "'";
		//logger.info("旧三要素更新数据Sql:" + sql);
		update(sql);
	}

	@Override
	/* 添加查询记录 */
	public void addQueryLog(QueryLog querylog) {
		String sql = ConstructSql.composeInsertSql(querylog, QU);
		//logger.info("保存查询记录sql:" + sql);
		add(sql);
	}

	@Override
	/* 删除服务端盖章日志历史表数据 */
	public int delServerSealLog() {
		int saveTime = Integer.parseInt(DJPropertyUtil.getPropertyValue(
				"ServerSealLog_saveTime").trim());// 已保存天数
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String nowTime = df.format(date);// 当前时间
		String sql = "delete from " + ServerSealLogUtil.TABLE_NAME
				+ "_HISTORY where to_date('" + nowTime
				+ "','yyyy-MM-dd hh24:mi:ss')-" + ServerSealLogUtil.OPR_TIME
				+ ">" + saveTime;
		//logger.info("删除盖章日志Sql:" + sql);
		return getJdbcTemplate().update(sql);
	}

	@Override/*更新请求次数、打印份数*/
	public void updatePrintNum(String valcode,Number printNum,String caseseqid,Number requiredNum) {
		String sql = "update "+NSHRecordUtil.TABLE_NAME +" t set requirednum="+requiredNum+",printnum="+printNum+" where printnum =(select max(printnum) from "+NSHRecordUtil.TABLE_NAME +" where valcode='"+valcode+"') and t.valcode='"+valcode+"'";
		//logger.info(caseseqid+"更新打印份数sql:"+sql);
		update(sql);
	}

	@Override/*更新附件信息*/
	public void updateInfo_plus(String caseseqid,String info_plus,Number requiredNum) {
		String sql1="select caseseqid from "+NSHRecordUtil.TABLE_NAME +" where caseseqid='"+caseseqid+"'";//原始表查询
		String sql2="select caseseqid from "+NSHRecordUtil.TABLE_NAME +"_HISTORY where caseseqid='"+caseseqid+"'";
		String sql3=null;
		if(form(sql1)!=null){//更新原始表附件信息
		    sql3="update "+NSHRecordUtil.TABLE_NAME +" set requirednum="+requiredNum+",info_plus='"+info_plus+"' where caseseqid='"+caseseqid+"'";
		    update(sql3);//更新附件信息
		}else if(form(sql2)!=null){
		    sql3="update "+NSHRecordUtil.TABLE_NAME +"_HISTORY set requirednum="+requiredNum+", info_plus='"+info_plus+"' where caseseqid='"+caseseqid+"'";
		    update(sql3);//更新附件信息
		}else{
			logger.info(caseseqid+"流水号不存在！");
		}
		
	}
}
