package com.dj.seal.organise.service.api;

import java.util.List;

import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.struts.SysMenu;
public interface ISysFuncService {

	/**
	 * 返回用户的所有菜单列表（包括菜单下的子权限）
	 * 
	 * @param user_id
	 * @return
	 * @throws GeneralException
	 */
	public List<SysMenu> showMenusByUser_id(String user_id)
			throws GeneralException;
}
