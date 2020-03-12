package com.dj.seal.hotel.service.api;

import java.util.List;

import com.dj.seal.hotel.form.HotelTDicForm;
import com.dj.seal.hotel.po.HotelTDic;
import com.dj.seal.util.exception.DAOException;

public interface IHotelTDicHotelUtilService {
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HotelTDic> showHotelDics() throws Exception;
	
	/**
	 * 
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public List<HotelTDic> shwoHotelDicById(String cid) throws DAOException;
	
	/**
	 * 
	 * @param cid
	 * @return
	 * @throws DAOException
	 */
	public HotelTDic getHotelDic(String cid) throws DAOException;
	
	/**
	 * 
	 * @param obj
	 * @return 1 成功 0 失败
	 * @throws DAOException
	 */
	public int addHotelDic(HotelTDic obj) throws DAOException;
	
	/**
	 * 1 成功 0 失败
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	public int delHotelDic(String cid ) throws DAOException;
	
	/**
	 *  1 成功 0 失败
	 * @param obj
	 * @return
	 * @throws DAOException
	 */
	public int updateHotelDic(HotelTDic obj) throws DAOException;
	
	public int addHotelTDic(HotelTDicForm f) throws Exception;
	
	/**
	 * 设置隐藏状态
	 * @param id
	 */
	public void changeStatusIsDisplay(String id);
	/**
	 * 设置显示状态
	 * @param id
	 */
	public void changeStatusIsBlock(String id);

}
