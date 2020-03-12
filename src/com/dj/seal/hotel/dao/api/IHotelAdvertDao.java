package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.AdvertImagePO;
import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.util.exception.DAOException;

public interface IHotelAdvertDao {

	/**
	 * 新增广告
	 * 
	 * @param advert
	 */
	public void addAdvert(HotelAdvertPO advert) throws Exception;

	/**
	 * 修改广告信息
	 * 
	 * @param advert
	 * @throws DAOException
	 */
	public void updateAdvert(HotelAdvertPO advert) throws DAOException;

	/**
	 * 删除指定的广告信息
	 * 
	 * @param ad_id
	 */
	public void deleteAdvert(String ad_id) throws DAOException;

	/**
	 * 获取所有的广告信息
	 * 
	 * @return
	 */
	public List<HotelAdvertPO> showAllAdverts(String sql) throws DAOException;

	/**
	 * 获得指定的广告信息
	 * 
	 * @param ad_id
	 * @return
	 * @throws DAOException
	 */
	public HotelAdvertPO getAdvert(String ad_id) throws DAOException;

	/**
	 * 通过sql语句返回查询广告信息
	 * 
	 * @return
	 * @throws DAOException
	 */
	public HotelAdvertPO getHotelAdvertBy_sql(String sql) throws DAOException;

	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);

	/**
	 * 广告审批
	 * 
	 * @param advert
	 * @param id
	 */
	public void approveAd(HotelAdvertPO advert, String id);

	/**
	 * 获得部门下的广告信息
	 * 
	 * @param deptNo
	 * @return
	 */
	public List<HotelAdvertPO> getAdvertByDept(String deptNo);

	/**
	 * 向广告图片表中添加记录
	 * 
	 * @param
	 * @return
	 */
	public void addAdvertImage(AdvertImagePO advertimage) throws DAOException;

	/**
	 * 通过广告名称查询查询广告图片表
	 * 
	 * @param id
	 * @return
	 */
	public AdvertImagePO getAdvertImage(String filename) throws DAOException;
	
}
