package com.dj.seal.hotel.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.RecordDao;
import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;

public class RecordDaoImpl extends BaseDAOJDBC implements RecordDao {
	
	static Logger logger = LogManager.getLogger(RecordDaoImpl.class.getName());
	
	private HotelRecordUtil table = new HotelRecordUtil();
	
	private HotelTRecordAffiliatedUtil table2 = new HotelTRecordAffiliatedUtil();
	
	private HotelRecordContent table3 = new HotelRecordContent();
	
	public HotelRecordContent getTable3() {
		return table3;
	}

	public void setTable3(HotelRecordContent table3) {
		this.table3 = table3;
	}

	public HotelTRecordAffiliatedUtil getTable2() {
		return table2;
	}

	public void setTable2(HotelTRecordAffiliatedUtil table2) {
		this.table2 = table2;
	}

	private HotelTRecordAffiliatedUtil tableV = new HotelTRecordAffiliatedUtil();
	
	public HotelRecordUtil getTable() {
		return table;
	}

	public void setTable(HotelRecordUtil table) {
		this.table = table;
	}

	public HotelTRecordAffiliatedUtil getTableV() {
		return tableV;
	}

	public void setTableV(HotelTRecordAffiliatedUtil tableV) {
		this.tableV = tableV;
	}

	@Override
	public void addRecord(RecordPO record) {
		String sql = ConstructSql.composeInsertSql(record, table);
		add(sql);
	}

	//添加单据月表
	public void addRecordNew(RecordPO record) {
		String sql = ConstructSql.composeInsertSqlNew(record, table,getTableName(HotelRecordUtil.TABLE_NAME));
		add(sql);
	}

	@Override
	public List<RecordPO> getAllRecordPoBy(String sql) throws DAOException {
		return formList(sql);
	}


	@Override
	public RecordPO getRecordPoBySql(String sql) throws DAOException {
		return form(sql);
	}

	
	@Override
	public List<RecordAffiliatePO> getAllRecordAffiliatePOBySql(String sql)
			throws DAOException {
		return voFormList(sql);
	}
	
	
	@SuppressWarnings("unchecked")
	private RecordPO form(String sql) {
		RecordPO obj = new RecordPO();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (RecordPO) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	private List<RecordPO> formList(String sql) {
		List<RecordPO> listObj = new ArrayList<RecordPO>();
		List<Map> list = select(sql);
		//logger.info("size:"+list.size());
		for (Map map : list) {
			RecordPO form = new RecordPO();
			form = (RecordPO) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}
	
	
	@SuppressWarnings({ "unused", "unchecked" })
	private RecordAffiliatePO voForm(String sql) {
		RecordAffiliatePO obj = new RecordAffiliatePO();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (RecordAffiliatePO) DaoUtil.setPo(obj, map, tableV);
			return obj;
		} else {
			return null;
		}
	}

	
	
	@SuppressWarnings("unchecked")
	public List<RecordAffiliatePO> voFormList(String sql){
		List<RecordAffiliatePO> listObj = new ArrayList<RecordAffiliatePO>();
		List<Map> list = select(sql);
		//logger.info("size:"+list.size());
		for (Map map : list) {
			RecordAffiliatePO form = new RecordAffiliatePO();
			form = (RecordAffiliatePO) DaoUtil.setPo(form, map, tableV);
			listObj.add(form);
		}
		return listObj;
	}

	@Override
	public void updateRecordStatusIsOff(String rid) throws DAOException {
		String yueStr = rid.substring(0, 6);
		String sql = "update " + HotelRecordUtil.TABLE_NAME + yueStr +" set " +HotelRecordUtil.CSTATUS+"='0' where "+HotelRecordUtil.CID+" = '"+rid+"'";
		logger.info(sql);
		update(sql);
	}

	public void updateRecordCheckStatus(String rid,String user_no,String status,String reason) throws DAOException {
		String yueStr = rid.substring(0, 6);
		SimpleDateFormat today_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update " + HotelRecordUtil.TABLE_NAME + yueStr +" set " +HotelRecordUtil.CHECKSTATUS+"='" +status +"', "
					+HotelRecordUtil.JIHEUSER+"='"+user_no+"',"
					+HotelRecordUtil.JIHETIME+"='"+today_formatter.format(new Date())+"',"
					+HotelRecordUtil.JIHEREASON+"='"+reason+"'"
					+" where "+HotelRecordUtil.CID+" = '"+rid+"'";
		update(sql);
	}
	
	@Override
	public void updateRecordStatusIsOn(String rid) throws DAOException {
		String yueStr = rid.substring(0, 6);
		String sql = "update " + HotelRecordUtil.TABLE_NAME + yueStr +" set " +HotelRecordUtil.CSTATUS+"='1' where "+HotelRecordUtil.CID+" = '"+rid+"'";
		update(sql);
	}
	
	public void updateRecordHasDone(String rid,String hasdone) throws DAOException {
		String sql = "update " + HotelRecordUtil.TABLE_NAME +" set " +HotelRecordUtil.HASDONE+"='"+hasdone+"' where "+HotelRecordUtil.CID+" = '"+rid+"'";
		update(sql);
	}
	
	public void deleteRecord(String rid) throws DAOException {
		String sql = "delete from " + HotelRecordUtil.TABLE_NAME +" where "+HotelRecordUtil.CID+" = '"+rid+"'";
		delete(sql);
		String sql22 = "delete from " + HotelTRecordAffiliatedUtil.TABLE_NAME +" where "+HotelTRecordAffiliatedUtil.RECORDID+" = '"+rid+"'";
		delete(sql22);
	}
	
	public List<RecordPO> getRecordPos(String selStr){
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		int sizeno = r_nos.length;
		for(int i=0;i<sizeno;i++){
			if(i!=0){
				sb.append(" UNION ");
			}
			String recordid = r_nos[i];
			String yuestr = recordid.substring(0, 6);
			sb.append("select ");
			sb.append(HotelRecordUtil.AGREEMENTID);
			sb.append(", ");
			sb.append(HotelRecordUtil.CCREATETIME);
			sb.append(", ");
			sb.append(HotelRecordUtil.CDATA);
			sb.append(", ");
			sb.append(HotelRecordUtil.CFILEFILENAME);
			sb.append(", ");
			sb.append(HotelRecordUtil.CHECKSTATUS);
			sb.append(", ");
			sb.append(HotelRecordUtil.CID);
			sb.append(", ");
			sb.append(HotelRecordUtil.CIP);
			sb.append(", ");
			sb.append(HotelRecordUtil.CREATEUSERID);
			sb.append(", ");
			sb.append(HotelRecordUtil.CSTATUS);
			sb.append(", ");
			sb.append(HotelRecordUtil.CUPLOADTIME);
			sb.append(", ");
			sb.append(HotelRecordUtil.DEPTNO);
			sb.append(", ");
			sb.append(HotelRecordUtil.HASDONE);
			sb.append(", ");
			sb.append(HotelRecordUtil.HAVEATTACH);
			sb.append(", ");
			sb.append(HotelRecordUtil.HAVEIDCARD);
			sb.append(", ");
			sb.append(HotelRecordUtil.UPLOADUSERID);
			sb.append("  from ");
			sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr);
			sb.append(" where ").append(HotelRecordUtil.CID).append(" ='");
			sb.append(recordid).append("'");
		}
//		String[] r_nos = selStr.split(",");
//		StringBuffer sb = new StringBuffer();
//		String recordid = r_nos[0];
//		String yuestr = recordid.substring(0, 6);
//		sb.append(" select * from ");
//		sb.append(HotelRecordUtil.TABLE_NAME).append(yuestr).append(" where 1=1 ");
//		sb.append(" and ").append(HotelRecordUtil.CID).append(" in('");
//		for (String str : r_nos) {
//			sb.append(str).append("','");
//		}
//		sb.append("')");
		return formList(sb.toString());
	}


	
	public List<RecordAffiliatePO> getRecordAffiliatePOByRecordIdNew(String recordid)
		throws DAOException {
			String yueStr = recordid.substring(0, 6);
			StringBuffer sb = new StringBuffer();
			sb.append(" select * from ");
			sb.append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(yueStr).append(" where ");
			sb.append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append("='").append(recordid).append("'");
			return voFormList(sb.toString());
	}
	
	

	@Override
	public List<RecordAffiliatePO> getRecordAffiliatePOByRecordId(String selStr)
			throws DAOException {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from ");
		sb.append(HotelTRecordAffiliatedUtil.TABLE_NAME).append(" where 1=1 ");
		for (String str : r_nos) {
			sb.append(" and ").append(HotelTRecordAffiliatedUtil.RECORDID);
			sb.append("='").append(str).append("'");
		}
		return voFormList(sb.toString());
	}

	@Override
	public int showCount(String sql) {
		return count(sql);
	}

	@Override
	public RecordPO getRecordPOById(String cid) throws DAOException {
		String sql = "select * from " + HotelRecordUtil.TABLE_NAME+" where " + HotelRecordUtil.CID + " = '" + cid +"'";
		return form(sql);
	}

	@Override
	public void addRecordAffileate(RecordAffiliatePO recordAffiliate) {
		String sql = ConstructSql.composeInsertSql(recordAffiliate, table2);
		add(sql);
	}
	
	//月表
	public void addRecordAffileateNew(RecordAffiliatePO recordAffiliate) {
		String sql = ConstructSql.composeInsertSqlNew(recordAffiliate, table2,getTableName(HotelTRecordAffiliatedUtil.TABLE_NAME));
		add(sql);
	}

	@Override
	public void addRecordContent(RecordContentPO rContent) {
		// TODO Auto-generated method stub
		String sql = ConstructSql.composeInsertSqlNew(rContent,table3,getTableName(HotelRecordContent.TABLE_NAME));
		add(sql);
	}

	@Override
	public String getTableName(String tbName) {
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		return tbName+yuestr;
	}

	@Override
	public int isRecordExist(String fName) {
		// TODO Auto-generated method stub
		String tableName = getTableName("T_HOTELRECORD");
		String sql = "SELECT count(*) FROM "+ tableName + " WHERE "+ tableName +".cfileFileName = '"+fName+"'";
		int i = getJdbcTemplate().queryForInt(sql);
		return i;
	}

}
