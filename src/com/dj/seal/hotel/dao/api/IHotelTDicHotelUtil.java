package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.HotelTDic;
import com.dj.seal.util.exception.DAOException;

public interface IHotelTDicHotelUtil {
	
	/**
	 * 新增数据字典
	 * @param dic
	 * @throws Exception
	 */
	public void addHotelTDicHotelUtil(HotelTDic dic) throws Exception;
	
	/**
	 * 更新数据字典
	 * @param dic
	 * @throws DAOException
	 */
	public void updateHotelDicHotelUtil(HotelTDic dic) throws DAOException;
	
	/**
	 * 删除数据字典
	 * @param cid
	 * @throws DAOException
	 */
	public void delHotelDicHotelUtil(String cid) throws DAOException;
	
	/**
	 * 通过ID获得指定的数据字典
	 * @return HotelTDicHotelUtil
	 * @throws DAOException
	 */
	public HotelTDic getHotelDicHotelUtil(String cid) throws DAOException;
	
	/**
	 * 获得所有的数据字典
	 * @return
	 * @throws DAOException
	 */
	public List<HotelTDic> showAllHotelDicHotelUtil(String sql) throws DAOException;
	
	/**
	 * 得到SQL语句返回的记录数
	 * 
	 * @param sql
	 * @return
	 */
	public int showCount(String sql);
	
	/**
	 * 通过sql语句返回查询数据字典
	 * @return
	 * @throws DAOException
	 */
	public HotelTDic getHogelDicHotelUtilBy_sql(String sql) throws DAOException;
	
	/**
	 * 隐藏属性
	 */
	public void changeDicIsDisplay(String id);
	
	/**
	 * 显示属性
	 */
	public void changeDicIsBlock(String id);

}
