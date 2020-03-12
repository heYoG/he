package com.dj.seal.sealTemplate.dao.impl;

import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.sealTemplate.dao.api.ISealTemplateDao;
import com.dj.seal.structure.dao.po.SealTemplate;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SealTemplateUtil;

public class SealTemplateDaoImpl extends BaseDAOJDBC implements
		ISealTemplateDao {
	static Logger logger = LogManager.getLogger(SealTemplateDaoImpl.class.getName());
	private SealTemplateUtil table = new SealTemplateUtil();

	@Override
	public List<SealTemplate> showTempList(String sql) throws DAOException {
		List<SealTemplate> list_sealtemp = new ArrayList<SealTemplate>();
		List<Map> list = select(sql);
		for (Map map : list) {
			SealTemplate sealtemp = new SealTemplate();
			sealtemp = (SealTemplate) DaoUtil.setPo(sealtemp, map, table);
			list_sealtemp.add(sealtemp);
		}
		return list_sealtemp;
	}

	@Override
	public void addSealTemp(SealTemplate sealtemp) {
		String sql = ConstructSql.composeInsertSql(sealtemp, table);
		//logger.info("sql:"+sql);
		if(sql.indexOf(sealtemp.getTemp_data())<sql.indexOf(sealtemp.getPreview_img())){
			String str1 = sql
			.substring(0, sql.indexOf(sealtemp.getTemp_data()) - 1);
	        String str2 = sql.substring(sql.indexOf(sealtemp.getTemp_data())
					+ sealtemp.getTemp_data().length() + 1, sql
					.indexOf(sealtemp.getPreview_img()) - 1);
			String str3 = sql.substring(sql.indexOf(sealtemp.getPreview_img())
					+ sealtemp.getPreview_img().length() + 1, sql.length());
			sql = str1 + "?" + str2 + "?" + str3;
	       try {
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader clobReader = new StringReader(sealtemp.getTemp_data()); // 将text转成流形式
				Reader clobReaderImg = new StringReader(sealtemp
						.getPreview_img());
				stmt.setCharacterStream(1, clobReader, sealtemp.getTemp_data()
						.length());// 替换sql语句中的？(替换第1个占位符)
				stmt.setCharacterStream(2, clobReaderImg, sealtemp
						.getPreview_img().length());//替换第2个占位符
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}else{
			String str1 = sql
			.substring(0, sql.indexOf(sealtemp.getPreview_img()) - 1);
	        String str2 = sql.substring(sql.indexOf(sealtemp.getPreview_img())
					+ sealtemp.getPreview_img().length() + 1, sql
					.indexOf(sealtemp.getTemp_data()) - 1);
			String str3 = sql.substring(sql.indexOf(sealtemp.getTemp_data())
					+ sealtemp.getTemp_data().length() + 1, sql.length());
			sql = str1 + "?" + str2 + "?" + str3;
	       try {
				Connection conn = getDataSource().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				Reader clobReader = new StringReader(sealtemp.getTemp_data()); // 将text转成流形式
				Reader clobReaderImg = new StringReader(sealtemp
						.getPreview_img());
				stmt.setCharacterStream(1, clobReaderImg, sealtemp.getPreview_img()
						.length());// 替换sql语句中的？
				stmt.setCharacterStream(2, clobReader, sealtemp
						.getTemp_data().length());
				stmt.executeUpdate();// 执行SQL
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean isExistTempName(String temp_name) throws DAOException {
		String sql = "select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_NAME + "='" + temp_name + "'";
		List list = select(sql);
		if (list.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void approveTemp(SealTemplate temp, String[] temps)
			throws DAOException {
		StringBuffer sb = new StringBuffer(SealTemplateUtil.TEMP_ID + " in (");
		temp.setIs_maked(Constants.NO_MAKED);
		//logger.info("---是否制章---"+temp.getIs_maked());
		for (String str : temps) {
			sb.append(str).append(",");
		}
		sb.deleteCharAt(sb.lastIndexOf(",")).append(")");
		String sql = ConstructSql.composeUpdateSql(temp, table, sb.toString());
		update(sql);
	}
	
	@Override
	public String showTempByTemp_id(String temp_id) throws DAOException {
		int lastIndex = temp_id.lastIndexOf(',');
		String temp_id2="";
		if (lastIndex > -1) {
			temp_id2= temp_id.substring(0, lastIndex) + temp_id.substring(lastIndex + 1, temp_id.length());
	    }
		String sql="select * from " + SealTemplateUtil.TABLE_NAME + " where "
				+ SealTemplateUtil.TEMP_ID + " in(" + temp_id2 + ")";
		List<SealTemplate> list_temp = new ArrayList<SealTemplate>();
		List<Map> list = select(sql);
		String temp_name="";
		for (Map map : list) {
			SealTemplate obj = new SealTemplate();
			obj = (SealTemplate) DaoUtil.setPo(obj, map, table);
			list_temp.add(obj);
		}
		for(SealTemplate st:list_temp){
			temp_name+=st.getTemp_name()+",";
		}
		return temp_name;
	}

	@Override
	public int getMaxId(String tableName, String colName) throws DAOException {
		int maxID = 0;
		try {
			 maxID = Integer.parseInt(getMaxNo(tableName, colName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		return maxID;
	}
	public int getId(String tableName, String colName) throws DAOException {
		int ID = 0;
		try {
			 ID = Integer.parseInt(getNo(tableName, colName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		
		return ID;
	}


	@Override
	public List<SealTemplate> showSealTempsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException {
		
		String str = DBTypeUtil.splitSql(sql, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<SealTemplate> list = listSealTemplate(str);

		return list;
	}
	@SuppressWarnings("unchecked")
	private List<SealTemplate> listSealTemplate(String sql) {
		List<SealTemplate> list_temp = new ArrayList<SealTemplate>();
		logger.info("sql:"+sql);
		List<Map> list = select(sql);
		for (Map map : list) {
			SealTemplate obj = new SealTemplate();
			obj = (SealTemplate) DaoUtil.setPo(obj, map, table);
			list_temp.add(obj);
		}
		return list_temp;
	}

	@Override
	public List<SealTemplate> getSealTempList(String sealId)throws DAOException{
		List<SealTemplate> list_temp = new ArrayList<SealTemplate>();
		if(sealId.indexOf(",")!=-1){
			 String sealStr[]=sealId.split(",");
			 StringBuilder tempid = new StringBuilder();
			 for(String str:sealStr){
				 tempid.append("'"+str+"',");
			 }
			 tempid.deleteCharAt(tempid.lastIndexOf(","));
			 String sql="select * from " + SealTemplateUtil.TABLE_NAME + " where "
			 +SealTemplateUtil.TEMP_ID + " in(" + tempid + ")";
	         List<Map> list = select(sql);
	         for (Map map : list) {
		     SealTemplate obj = new SealTemplate();
		     obj = (SealTemplate) DaoUtil.setPo(obj, map, table);
		     list_temp.add(obj);
	         }
		}else{
			 String sql="select * from " + SealTemplateUtil.TABLE_NAME + " where "
			 +SealTemplateUtil.TEMP_ID + "='"+sealId +"'";
	         List<Map> list = select(sql);
	         for (Map map : list) {
		     SealTemplate obj = new SealTemplate();
		     obj = (SealTemplate) DaoUtil.setPo(obj, map, table);
		     list_temp.add(obj);
	         }
		}
		return list_temp;
	}
	@Override
	public void updTemp(String tempid)throws DAOException{
		String sql="update "+SealTemplateUtil.TABLE_NAME+" set "+SealTemplateUtil.IS_MAKED+"='"+Constants.IS_MAKED+"'" +
				" where "+SealTemplateUtil.TEMP_ID+"='"+tempid+"'";
		update(sql);
	}

	@Override
	public void DeleteTeam(String tempID)throws DAOException{
		if(tempID.indexOf(",")!=-1){
			String sealStr[]=tempID.split(",");
			 for(String str:sealStr){
				// logger.info("str:"+str);
				 if(!str.equals("")||!str.equals(null)){
					 String sql="delete from "+SealTemplateUtil.TABLE_NAME+" where "+SealTemplateUtil.TEMP_ID+"='"+str+"'";
					 delete(sql);
				 }
			 }
		} else{
			String sql="delete from "+SealTemplateUtil.TABLE_NAME+" where "+SealTemplateUtil.TEMP_ID+"='"+tempID+"'";
			delete(sql);
		}
	}
	@Override
	public SealTemplate showTempByTemp(String tempID)throws DAOException{
		String sql="select * from "+SealTemplateUtil.TABLE_NAME+" where "+SealTemplateUtil.TEMP_ID+"='"+tempID+"'";
		List<Map> templist=select(sql);
		SealTemplate sealtemp = new SealTemplate();
		for (Map map : templist) {
			sealtemp = (SealTemplate) DaoUtil.setPo(sealtemp, map, table);
		}
		return sealtemp;
	}
	@Override
	public void updateTemp(String sql) throws DAOException{
		update(sql);
	}
	@Override
	public void deleteTemp(String sql) throws DAOException{
		delete(sql);	
	}
	@Override
	public String selTempNum(String sql)throws Exception{
		List list =select(sql);
		//logger.info("size:"+list.size());
		DesUtils des = new DesUtils(DesUtils.strSealNumKey);
		if(list.size()>=Integer.parseInt(des.decrypt(Constants.getLicenseProperty("MAX_SEALNUM")))){
			return "false";
		}else{
			return "true";
		}
	}
}
