package com.dj.seal.hotel.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.api.IHotelTDicHotelUtil;
import com.dj.seal.hotel.po.HotelTDic;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.HotelTDicHotelUtil;

public class HotelTDicHotelUtilImpl extends BaseDAOJDBC implements IHotelTDicHotelUtil {
	
	static Logger logger = LogManager.getLogger(HotelTDicHotelUtilImpl.class.getName());
	
	private HotelTDicHotelUtil table = new HotelTDicHotelUtil();
	
	public HotelTDicHotelUtil getTable() {
		return table;
	}


	public void setTable(HotelTDicHotelUtil table) {
		this.table = table;
	}



	
	@SuppressWarnings("unchecked")
	public HotelTDic form(String sql){
		
		HotelTDic obj = new HotelTDic();
		
		List list = select(sql);//调用父类方法
		
		if(list.size()!=0){
			Map map = (Map)list.get(0);
			obj = (HotelTDic) DaoUtil.setPo(obj, map, table);
			return obj;
		}else{
			return null;
		}
		
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<HotelTDic> formList(String sql){
		List<HotelTDic>  listObj = new ArrayList<HotelTDic>();
		List<Map> list = select(sql);
		for(Map map : list){
			HotelTDic form = new HotelTDic();
			form = (HotelTDic) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}
	

	@Override
	public void addHotelTDicHotelUtil(HotelTDic dic) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = ConstructSql.composeInsertSql(dic, table);
//		System.out.println("sql:"+sql);
		add(sql);

	}
	

	@Override
	public void delHotelDicHotelUtil(String cid) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "delete from " + HotelTDicHotelUtil.getTABLE_NAME() + " where "
				+ HotelTDicHotelUtil.getCID() + "=" + cid;
		
		delete(sql);

	}
	
	
	@Override
	public HotelTDic getHogelDicHotelUtilBy_sql(String sql) throws DAOException {
		// TODO Auto-generated method stub
		
		return form(sql);
	}

	@Override
	public HotelTDic getHotelDicHotelUtil(String cid) throws DAOException {
		// TODO Auto-generated method stub
		String sql = "select * from " + HotelTDicHotelUtil.getTABLE_NAME() +" where " 
				+ HotelTDicHotelUtil.getCID() + " = " + cid;
				
		return form(sql);
	}


	@Override
	public List<HotelTDic> showAllHotelDicHotelUtil(String sql)
			throws DAOException {
		// TODO Auto-generated method stub
		return formList(sql);
	}


	@Override
	public int showCount(String sql) {
		// TODO Auto-generated method stub
		return count(sql);
	}
	

	
	@Override
	public void updateHotelDicHotelUtil(HotelTDic dic)
			throws DAOException {
		// TODO Auto-generated method stub
		String pastr = HotelTDicHotelUtil.getCID() + " = " + dic.getCid();
		String sql = ConstructSql.composeUpdateSql(dic,table,pastr);
		update(sql);
	}


	@Override
	public void changeDicIsBlock(String id) {
		
		String sql ="update " + HotelTDicHotelUtil.TABLE_NAME + " set " 
		+ HotelTDicHotelUtil.C_STATUS+"='0' where "+ HotelTDicHotelUtil.CID + "= '"+id+"'";
		
		update(sql);
		
	}


	@Override
	public void changeDicIsDisplay(String id) {
		String sql ="update " + HotelTDicHotelUtil.TABLE_NAME + " set " 
		+ HotelTDicHotelUtil.C_STATUS+"='1' where "+ HotelTDicHotelUtil.CID + "= '"+id+"'";
		
		update(sql);
	}


	

}
