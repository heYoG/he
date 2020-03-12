package com.dj.seal.hotel.dao.impl;

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

import com.dj.seal.hotel.dao.api.IHotelAdvertDao;
import com.dj.seal.hotel.po.AdvertImagePO;
import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.AdvertImagesUtil;
import com.dj.seal.util.table.HotelAdvertUtil;

public class HotelAdvertDaoImpl extends BaseDAOJDBC implements IHotelAdvertDao {

	static Logger logger = LogManager.getLogger(HotelAdvertDaoImpl.class
			.getName());

	private HotelAdvertUtil table = new HotelAdvertUtil();

	public HotelAdvertUtil getTable() {
		return table;
	}

	public void setTable(HotelAdvertUtil table) {
		this.table = table;
	}

	private AdvertImagesUtil adverttable = new AdvertImagesUtil();

	public AdvertImagesUtil getAdverttable() {
		return adverttable;
	}

	public void setAdverttable(AdvertImagesUtil adverttable) {
		this.adverttable = adverttable;
	}

	@SuppressWarnings("unchecked")
	public HotelAdvertPO form(String sql) {
		HotelAdvertPO obj = new HotelAdvertPO();
		try {
			List list = select(sql);// 调用父类方法
			if (list.size() != 0) {
				Map map = (Map) list.get(0);
				obj = (HotelAdvertPO) DaoUtil.setPo(obj, map, table);
				return obj;
			} else {
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return obj;

	}

	public List<HotelAdvertPO> formList(String sql) {
		List<HotelAdvertPO> listObj = new ArrayList<HotelAdvertPO>();
		List<Map> list = select(sql);
		for (Map map : list) {
			HotelAdvertPO form = new HotelAdvertPO();
			form = (HotelAdvertPO) DaoUtil.setPo(form, map, table);
			listObj.add(form);
		}
		return listObj;
	}

	@Override
	public void addAdvert(HotelAdvertPO advert) throws Exception {
		String sql = ConstructSql.composeInsertSql(advert, table);
		add(sql);
	}

	@Override
	public void addAdvertImage(AdvertImagePO advertimage) throws DAOException {
		// TODO Auto-generated method stub
		String sql=ConstructSql.composeInsertSql(advertimage, adverttable);
		String str1 = sql.substring(0,
				sql.indexOf(advertimage.getAi_imagedata()) - 1);
		String str2 = sql.substring(sql.indexOf(advertimage.getAi_imagedata())
				+ advertimage.getAi_imagedata().length() + 1, sql.length());
		// logger.info("--str3--" + str3);
		sql = str1 + "?" + str2;
		//logger.info("advertimagesql:"+sql);
		try {
			Connection conn = getDataSource().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			Reader clobReader = new StringReader(advertimage.getAi_imagedata()); // 将text转成流形式
			stmt.setCharacterStream(1, clobReader, advertimage.getAi_imagedata().length());// 替换sql语句中的？
			stmt.executeUpdate();// 执行SQL 
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}

	@Override
	public void deleteAdvert(String adId) throws DAOException {

		String sql = "delete from " + HotelAdvertUtil.TABLE_NAME + " where "
				+ HotelAdvertUtil.AD_ID + " = '" + adId + "'";
		delete(sql);

	}

	@Override
	public HotelAdvertPO getAdvert(String adId) throws DAOException {
		String sql = "select * from " + HotelAdvertUtil.TABLE_NAME + " where "
				+ HotelAdvertUtil.AD_ID + " = '" + adId + "'";
		return form(sql);
	}

	@Override
	public AdvertImagePO getAdvertImage(String filename) throws DAOException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(AdvertImagesUtil.TABLE_NAME);
		sb.append(" where ");
		/*sb.append(AdvertImagesUtil.getAD_ID() + " = '");
		sb.append(id);
		sb.append("' and ");*/
		sb.append(AdvertImagesUtil.getAI_IMAGENAME()+" = '");
		sb.append(filename).append("'");
		logger.info("sql==="+sb.toString());

		AdvertImagePO obj = new AdvertImagePO();
		try {
			List list = select(sb.toString());// 调用父类方法
			if (list.size() != 0) {
				Map map = (Map) list.get(0);
				obj = (AdvertImagePO) DaoUtil.setPo(obj, map, adverttable);
				return obj;
			} else {
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		return obj;
	}
	
	public List<AdvertImagePO> getAdvertImageById(String id) throws DAOException {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(AdvertImagesUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(AdvertImagesUtil.getAD_ID() + " = '");
		sb.append(id).append("'");
		logger.info("sql==="+sb.toString());

		List<AdvertImagePO> imagelist=new ArrayList<AdvertImagePO>();
		AdvertImagePO obj=null;
		try {
			List list = select(sb.toString());// 调用父类方法
			if (list.size() != 0) {
				for(int i=0;i<list.size();i++){
					obj = new AdvertImagePO();
					Map map = (Map) list.get(i);
					obj = (AdvertImagePO) DaoUtil.setPo(obj, map, adverttable);
					imagelist.add(obj);
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			logger.error(e.getMessage());
		}
		
		return imagelist;
	}
	
	public void deleteAdvertImage(String imagename,String id) throws DAOException{
		StringBuffer sb=new StringBuffer();
		sb.append("delete from ");
		sb.append(AdvertImagesUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(AdvertImagesUtil.getAD_ID() + " = '");
		sb.append(id);
		if(imagename==null||"".equals(imagename)){
			sb.append("'");
		}else{
			sb.append("' and ");
			sb.append(AdvertImagesUtil.getAI_IMAGENAME()+" = '");
			sb.append(imagename).append("'");
		}		
		logger.info("sql==="+sb.toString());
		delete(sb.toString());
	}
	
	@Override
	public HotelAdvertPO getHotelAdvertBy_sql(String sql) throws DAOException {
		return form(sql);
	}

	@Override
	public List<HotelAdvertPO> showAllAdverts(String sql) throws DAOException {
		return formList(sql);
	}

	@Override
	public int showCount(String sql) {
		return count(sql);
	}

	@Override
	public void updateAdvert(HotelAdvertPO advert) throws DAOException {
		String pastr = HotelAdvertUtil.getAD_ID() + " = '" + advert.getAd_id()
				+ "'";
		String sql = ConstructSql.composeUpdateSql(advert, table, pastr);
		System.out.println("updsql:" + sql);
		update(sql);
	}

	@Override
	public void approveAd(HotelAdvertPO advert, String id) {
		StringBuffer sb = new StringBuffer();
		// logger.info("---是否制章---"+temp.getIs_maked());
			String[] titles = id.split(",");
			for (int i = 0; i < titles.length; i++) {
				String title = titles[i];
				sb = new StringBuffer(HotelAdvertUtil.AD_TITLE + " = '");
				sb.append(title).append("'");
				String sql = ConstructSql.composeUpdateSql(advert, table,
						sb.toString());
				logger.info("广告审批：" + sql);
				update(sql);
			}
	}

	@Override
	public List getAdvertByDept(String deptNo) {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(HotelAdvertUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(HotelAdvertUtil.AD_DEPT + " in( ");
		sb.append(deptNo).append(" )");
		sb.append(" and ");
		sb.append(HotelAdvertUtil.AD_STATE);
		sb.append(" ='2'");
		logger.info(sb.toString());
		return select(sb.toString());
	}

}
