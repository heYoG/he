package com.dj.seal.hotel.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.HotelTClientOperLogDao;
import com.dj.seal.hotel.po.HotelTClientOperLogPO;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTClientOperLogUtil;

public class HotelTClientOperLogDaoImpl extends BaseDAOJDBC implements
		HotelTClientOperLogDao {
	
	static Logger logger = LogManager.getLogger(HotelTClientOperLogDaoImpl.class.getName());

	private HotelTClientOperLogUtil table = new HotelTClientOperLogUtil();

	public HotelTClientOperLogUtil getTable() {
		return table;
	}


	public void setTable(HotelTClientOperLogUtil table) {
		this.table = table;
	}
	
	@SuppressWarnings("unchecked")
	
	public HotelTClientOperLogPO form(String sql){
		
		HotelTClientOperLogPO obj = new HotelTClientOperLogPO();
		
		List list = select(sql);//调用父类方法
		
		if(list.size()!=0){
			Map map = (Map)list.get(0);
			obj = (HotelTClientOperLogPO) DaoUtil.setPo(obj, map, table);
			return obj;
		}else{
			return null;
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	public List<HotelTClientOperLogPO> formList(String sql){
		List<HotelTClientOperLogPO>  listObj = new ArrayList<HotelTClientOperLogPO>();
		List<Map> list = select(sql);
		for(Map map : list){
			HotelTClientOperLogPO form = new HotelTClientOperLogPO();
			form = (HotelTClientOperLogPO) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}


	@Override
	public void addTClientOperLog(HotelTClientOperLogPO clientOperLog)
			throws DAOException {
		String sql = ConstructSql.composeInsertSql(clientOperLog, table);
		add(sql);
	}

	
	//添加日志  月表
	public void addTClientOperLogNew(HotelTClientOperLogPO clientOperLog)
		throws DAOException {
		String sql = ConstructSql.composeInsertSqlNew(clientOperLog, table,getTableName(HotelTClientOperLogUtil.TABLE_NAME));
		add(sql);
	}
	

	@Override
	public HotelTClientOperLogPO getClientOperLogById(String cid)
			throws DAOException {
		String sql = "select * from " + HotelTClientOperLogUtil.getTABLE_NAME() +" where " 
		+ HotelTClientOperLogUtil.getCID() + " = " + cid;
		return form(sql);
	}

	@Override
	public HotelTClientOperLogPO getHotelTClientOperLogPOBySql(String sql) {

		return form(sql);
	}


	@Override
	public List<HotelTClientOperLogPO> showAllClientOperLogsBySql(String sql)
			throws DAOException {

		return formList(sql);
	}


	@Override
	public int showCount(String sql) {
		return count(sql);
	}
	
	public String getTableName(String tbName) {
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		return tbName+yuestr;
	}

}
