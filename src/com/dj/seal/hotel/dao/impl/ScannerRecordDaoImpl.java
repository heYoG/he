package com.dj.seal.hotel.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.ScannerRecordDao;
import com.dj.seal.hotel.po.ScannerRecordPO;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelScannerRecordUtil;

public class ScannerRecordDaoImpl extends BaseDAOJDBC implements ScannerRecordDao {
	
	static Logger logger = LogManager.getLogger(ScannerRecordDaoImpl.class.getName());
	
	private HotelScannerRecordUtil table = new HotelScannerRecordUtil();
	
	


	@Override
	public void addScannerRecord(ScannerRecordPO record) {
		String sql = ConstructSql.composeInsertSql(record, table);
		add(sql);
	}


	
	@SuppressWarnings("unchecked")
	private ScannerRecordPO form(String sql) {
		ScannerRecordPO obj = new ScannerRecordPO();
		List list = select(sql);// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (ScannerRecordPO) DaoUtil.setPo(obj, map, table);
			return obj;
		} else {
			return null;
		}
	}
	

	@SuppressWarnings("unchecked")
	private List<ScannerRecordPO> formList(String sql) {
		List<ScannerRecordPO> listObj = new ArrayList<ScannerRecordPO>();
		List<Map> list = select(sql);
		//System.out.println("size:"+list.size());
		for (Map map : list) {
			ScannerRecordPO form = new ScannerRecordPO();
			form = (ScannerRecordPO) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}
	
	
	
	
	public List<ScannerRecordPO> getScannerRecordPos(String selStr){
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		int sizeno = r_nos.length;
		for(int i=0;i<sizeno;i++){
			if(i!=0){
				sb.append(" UNION ");
			}
			String recordid = r_nos[i];
			sb.append("select ");
			sb.append(HotelScannerRecordUtil.getID());
			sb.append(", ");
			sb.append(HotelScannerRecordUtil.getCREATEUSERID());
			sb.append(", ");
			sb.append(HotelScannerRecordUtil.getCREATETIME());
			sb.append(", ");
			sb.append(HotelScannerRecordUtil.getDEPTNO());
			sb.append(", ");
			sb.append(HotelScannerRecordUtil.getIP());
			sb.append(", ");
			sb.append(HotelScannerRecordUtil.getTABLE_NAME());
			sb.append(" where ").append(HotelScannerRecordUtil.getID()).append(" ='");
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


	@Override
	public int showCount(String sql) {
		return count(sql);
	}

	@Override
	public ScannerRecordPO getScannerRecordPOById(String cid) throws DAOException {
		String sql = "select * from " + HotelScannerRecordUtil.getTABLE_NAME()+" where " + HotelScannerRecordUtil.getID() + " = '" + cid +"'";
		return form(sql);
	}


	@Override
	public int isScannerRecordExist(String fName) {
		String sql = "SELECT count(*) FROM "+ HotelScannerRecordUtil.getTABLE_NAME() + " WHERE "+ HotelScannerRecordUtil.getFILENAME() +" = '"+fName+"'";
		int i = getJdbcTemplate().queryForInt(sql);
		return i;
	}


	@Override
	public ScannerRecordPO getScannerRecordPoBySql(String sql)
			throws DAOException {
		return form(sql);
	}


	@Override
	public List<ScannerRecordPO> getAllScannerRecordPoBy(String sql)
			throws DAOException {
		return formList(sql);
	}


}
