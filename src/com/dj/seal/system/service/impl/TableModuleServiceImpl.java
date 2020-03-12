package com.dj.seal.system.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysUserDao;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.dao.api.ITableModuleDao;
import com.dj.seal.system.service.api.ITableModuleService;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.ViewTableModuleUtil;

public class TableModuleServiceImpl implements ITableModuleService {
	static Logger logger = LogManager.getLogger(TableModuleServiceImpl.class.getName());
	private ITableModuleDao modu_dao;
	private ISysUserDao user_dao;

	/**
	 * 根据用户ID和左右类型得到用户桌面模块的字符串
	 * 
	 * @param user_id
	 * @param type
	 * @return
	 * @throws GeneralException
	 */
	private String getModuleListByUser(String user_id, String type)
			throws GeneralException {
		SysUser user = user_dao.showSysUserByUser_id(user_id);
		String[] strs = user.getMytable_left().split(",");
		StringBuffer buf = new StringBuffer();
		if ("left".equals(type)) {
			strs = user.getMytable_left().split(",");
		} else if ("right".equals(type)) {
			strs = user.getMytable_right().split(",");
		}
		if (strs.length == 0) {
			return "'0'";
		}
		for (String str : strs) {
			buf.append("'").append(str).append("',");
		}
		buf.deleteCharAt(buf.lastIndexOf(","));
		return buf.toString();
	}

	public ITableModuleDao getModu_dao() {
		return modu_dao;
	}

	public void setModu_dao(ITableModuleDao modu_dao) {
		this.modu_dao = modu_dao;
	}

	@Override
	public ViewTableModule getViewModuleByNo(String module_no)
			throws GeneralException {
		return modu_dao.getTableModuleByNo(module_no);
	}

	@Override
	public List<ViewTableModule> showLeftViewModulesByUser(String user_id)
			throws GeneralException {
		String sql = "select * from " + ViewTableModuleUtil.TABLE_NAME
				+ " where " + ViewTableModuleUtil.MODULE_NO + " in("
				+ getModuleListByUser(user_id, "left") + ")";
		return modu_dao.showModuleListBySql(sql);
	}

	@Override
	public List<ViewTableModule> showRightViewModulesByUser(String user_id)
			throws GeneralException {
		String sql = "select * from " + ViewTableModuleUtil.TABLE_NAME
				+ " where " + ViewTableModuleUtil.MODULE_NO + " in("
				+ getModuleListByUser(user_id, "right") + ")";
		return modu_dao.showModuleListBySql(sql);
	}

	public ISysUserDao getUser_dao() {
		return user_dao;
	}

	public void setUser_dao(ISysUserDao user_dao) {
		this.user_dao = user_dao;
	}

}
