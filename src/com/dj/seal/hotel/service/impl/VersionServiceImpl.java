package com.dj.seal.hotel.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.dao.impl.VersionDaoImpl;
import com.dj.seal.hotel.po.VersionPO;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.table.HotelTversionUtil;

public class VersionServiceImpl {
	
	static Logger logger = LogManager.getLogger(VersionServiceImpl.class.getName());

	private VersionDaoImpl versionDao;
	
	public VersionPO getVersion(String masterplateid) {
		String sql = "select * from "+HotelTversionUtil.TABLE_NAME+" where "+HotelTversionUtil.MASTERPLATEID+"='"+masterplateid+"'";
		List<Map> list = versionDao.select(sql);
		if(list.size()==0){
			return null;
		}
		VersionPO obj = new VersionPO();
		obj = (VersionPO) DaoUtil.setPo(obj, list.get(0), new HotelTversionUtil());
		return obj;
	}
	
	/**
	 * in 批量查询模板版本 
	 * add by liuph
	 * 20171220
	 * @param masterplateids
	 * @return
	 */
	public List<VersionPO> getVersionRW(String masterplateids){
		String sql = "select cversionno,masterplateid from "+HotelTversionUtil.TABLE_NAME+" where "+HotelTversionUtil.MASTERPLATEID+" in("+masterplateids+")";
		List<Map> list = versionDao.select(sql);
		if(list.size()==0){
			return null;
		}
		List<VersionPO> versions = new ArrayList<VersionPO>();
		for(int i=0;i<list.size();i++){
			VersionPO obj = new VersionPO();
			obj = (VersionPO) DaoUtil.setPo(obj, list.get(i), new HotelTversionUtil());
			versions.add(obj);
		}
		return versions;
	}
	
	//添加版本号
	public void add(String masterplateid) {
		VersionPO vp = new VersionPO();
		vp.setCid(UUID.randomUUID().toString());
		Timestamp create_time = new Timestamp(new Date().getTime());
		vp.setCcreatetime(create_time);
		vp.setCversionno("1");
		vp.setMasterplateid(masterplateid);
		String sql = ConstructSql.composeInsertSql(vp, new HotelTversionUtil());
		versionDao.add(sql);
	}

	//根据模板id更新版本号，有则更新，没有则添加
	public void update(String masterplateid) {
		VersionPO vp = getVersion(masterplateid);
		if(vp==null){
			add(masterplateid);
		}else{
			String no = vp.getCversionno();
			int newno = Integer.parseInt(no)+1;
			vp.setCversionno(Integer.toString(newno));
			Timestamp create_time = new Timestamp(new Date().getTime());
			vp.setCcreatetime(create_time);
			vp.setMasterplateid(masterplateid);
			String wherestr = HotelTversionUtil.MASTERPLATEID + "='"+masterplateid+"'";
			String sql = ConstructSql.composeUpdateSql(vp, new HotelTversionUtil(), wherestr);
			versionDao.update(sql);
		}
	}
	
	
	public void deleteByMasterplateId(String masterplateids) {
		if (masterplateids != null) {
			for (String masterplateid : masterplateids.split(",")) {
					String sql = "delete from "+HotelTversionUtil.TABLE_NAME+" where "+HotelTversionUtil.MASTERPLATEID+"='"+masterplateid+"'";
					versionDao.delete(sql);
			}
		}

	}

	public VersionDaoImpl getVersionDao() {
		return versionDao;
	}

	public void setVersionDao(VersionDaoImpl versionDao) {
		this.versionDao = versionDao;
	}
	
}
