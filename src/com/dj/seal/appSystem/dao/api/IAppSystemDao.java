package com.dj.seal.appSystem.dao.api;

import java.util.List;

import com.dj.seal.appSystem.web.form.AppSystemForm;
import com.dj.seal.util.exception.DAOException;

public interface IAppSystemDao {

	/**
	 * @depscription 添加应用系统
	 * @param appSystem
	 */
	public void addAppSystem(AppSystemForm app);

	/**
	 * 根据分页信息得到应用系统集合
	 * 
	 * @param pageIndex
	 * @param pageSize
	 * @param sql
	 * @return
	 * @throws DAOException
	 */
	public List<AppSystemForm> showAppSystemsByPageSplit(int pageIndex,
			int pageSize, String sql) throws DAOException;

	/**
	 * 检查应用系统编号是否存在
	 * 
	 * @param app_no
	 * @return false or true
	 */
	public boolean isExistAppSystem(String app_no);
	
	/**
	 *删除应用系统 
	 */
	public String delApp(String app_id);
	
	/**
	 *根据编号获取实体
	 */
	public AppSystemForm getAppSystem(String app_id);
	/**
	 *根据应用系统编号获取实体
	 */
	public AppSystemForm getAppSystemByAPP_NO(String app_no);
	/**
	 *判断请求ip是否在应用系统内
	 */
	public boolean getAppIp(String app_id,String clientIP);
	
	/**
	 *应用系统更新
	 **/
	public void upApp(String app_no, String app_name, String app_ip,
			String app_id, String app_pwd);

}
