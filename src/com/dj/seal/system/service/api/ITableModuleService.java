package com.dj.seal.system.service.api;

import java.util.List;

import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.util.exception.GeneralException;

/**
 * @title ITableModuleService
 * @description 桌面模块Service接口
 * @author oyxy
 * @since 2010-1-8
 * 
 */
public interface ITableModuleService {

	/**
	 * 根据模块编号得到桌面模块记录
	 * 
	 * @param module_no
	 * @return
	 * @throws GeneralException
	 */
	public ViewTableModule getViewModuleByNo(String module_no)
			throws GeneralException;

	/**
	 * 根据用户ID得到用户的左边桌面模块记录
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<ViewTableModule> showLeftViewModulesByUser(String user_id)
			throws GeneralException;

	/**
	 * 根据用户ID得到用户的右边桌面模块记录
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<ViewTableModule> showRightViewModulesByUser(String user_id)
			throws GeneralException;

}
