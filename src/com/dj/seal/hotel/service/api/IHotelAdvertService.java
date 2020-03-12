package com.dj.seal.hotel.service.api;

import java.util.List;

import com.dj.seal.hotel.form.HotelAdvertForm;
import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.util.exception.DAOException;

public interface IHotelAdvertService {
	
	/**
	 * 
	 * @param advert
	 * @return 0成功 1失败
	 */
	public int addAdvert(HotelAdvertPO advert) throws DAOException;
	
	/**
	 * 删除广告信息
	 * @param adId
	 * @return 0成功 1 失败
	 * @throws DAOException
	 */
	public int deleteAdvert(String adId) throws DAOException;
	
	/**
	 * 修改广告信息
	 * @return 0成功 1 失败
	 * @throws DAOException
	 */
	public int updateAdvert(HotelAdvertForm advertForm) throws DAOException;
	
	/**
	 * 获得指定广告信息
	 * @param adId
	 * @return HOtelAdvertPO
	 * @throws DAOException
	 */
	public HotelAdvertPO getHotelAdvertById(String adId) throws DAOException;
	
	/**
	 * 获得所有的广告信息
	 * @return
	 * @throws DAOException
	 */
	public List<HotelAdvertPO> showAllAdverts() throws DAOException;
	
	/**
	 * from添加广告信息
	 * @param advertForm
	 * @return 0成功 1失败
	 * @throws DAOException
	 */
	public int addHotelAdvert(HotelAdvertForm advertForm) throws Exception;
	
	/**
	 * 广告审批
	 * @param tempForm
	 * @param temps
	 */
	public void approveAd(HotelAdvertForm adForm, String ID);
	
	/**
	 * 根据部门获得此部门下广告数据（向上查找）
	 * @param deptno
	 * @return
	 */
	public List<HotelAdvertPO> getDeptAdverts(String deptno);
	/**
	 * 根据银行部门获得此部门下广告数据（向上查找）
	 * @param deptno
	 * @return
	 */
	public List<HotelAdvertPO> getBankDeptAdverts(String deptno);
	
	/**
	 * 当前部门和上级部门没有广告或广告文件失效时，获取默认广告文件作为当前部门的广告
	 * @return
	 */
	public List<HotelAdvertPO> getDefaultAdvert();

}
