package com.dj.seal.system.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;

import com.dj.seal.structure.dao.po.LogSys;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.structure.dao.po.ViewInterface;
import com.dj.seal.structure.dao.po.ViewMenu;
import com.dj.seal.structure.dao.po.ViewTableModule;
import com.dj.seal.system.dao.api.ISystemDao;
import com.dj.seal.system.service.api.ISystemService;
import com.dj.seal.system.web.form.InterfaceForm;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.GeneralException;

public class SystemServiceImpl implements ISystemService {
	
	static Logger logger = LogManager.getLogger(SystemServiceImpl.class.getName());
	
	ISystemDao isy;

	public ISystemDao getIsy() {
		return isy;
	}

	public void setIsy(ISystemDao isy) {
		this.isy = isy;
	}

	@Override
	public void addLogSys(JoinPoint jp, SysUser sysUser)
			throws GeneralException {
		/*
		 * String anObject=jp.getSignature().getName(); Object
		 * obj=jp.getSignature();
		 * 
		 * LogSys logSys=new LogSys(); if("userLogin".equals(anObject)) {
		 * logSys.setLog_remark("登陆日志");
		 * logSys.setOpr_ip(sysUser.getLast_visit_ip());
		 * logSys.setOpr_type("1"); logSys.setUser_id(sysUser.getUser_id());
		 * logSys.setUser_status(sysUser.getUser_status());
		 * logSys.setOpr_time(new Timestamp(new java.util.Date().getTime()));
		 * isy.addLogSys(logSys); } if("updateSysUser".equals(anObject)) {
		 * logSys.setLog_remark("修改用户");
		 * logSys.setOpr_ip(sysUser.getLast_visit_ip());
		 * logSys.setOpr_type("1"); logSys.setUser_id(sysUser.getUser_id());
		 * logSys.setUser_status(sysUser.getUser_status());
		 * logSys.setOpr_time(new Timestamp(new java.util.Date().getTime()));
		 * isy.addLogSys(logSys); }
		 */
	}

	@Override
	public void addViewInterface(ViewInterface view) throws GeneralException {
		isy.addViewInterface(view);
	}

	@Override
	public void addViewMenu(ViewMenu viewMenu) throws GeneralException {
		isy.addViewMenu(viewMenu);

	}

	@Override
	public void addViewTableModule(ViewTableModule viewTableModule)
			throws GeneralException {
		isy.addViewTableModule(viewTableModule);

	}

	@Override
	public void deleteLogSys(String str) throws GeneralException {
		isy.deleteLogSys(str);

	}

	@Override
	public void deleteViewMenu(String str) throws GeneralException {
		isy.deleteViewMenu(str);

	}

	@Override
	public List<ViewMenu> selectVewMenu(String str) throws GeneralException {

		return isy.selectViewMenu(str);
	}

	@Override
	public List<LogSys> showLogSys(PageSplit pageSplit, String[] sql)
			throws GeneralException {

		return isy.showLogSys(pageSplit, sql);
	}

	@Override
	public ViewMenu showViewMenu(String str) throws GeneralException {

		return isy.showViewMenu(str);
	}

	@Override
	public ViewTableModule showViewTableModule(String str)
			throws GeneralException {

		return isy.showViewTableModule(str);
	}

	@Override
	public void updateViewInterface(ViewInterface view) throws GeneralException {

		isy.updateViewInterface(view);
	}

	@Override
	public void updateViewMenu(ViewMenu viewMenu) throws GeneralException {
		isy.updateViewMenu(viewMenu);

	}

	@Override
	public void updateViewTableModule(ViewTableModule viewTableModule)
			throws GeneralException {
		isy.updateViewTableModule(viewTableModule);

	}

	

	@Override
	public ViewInterface selectViewInterface() throws GeneralException {
		// TODO Auto-generated method stub
		ViewInterface viewInterface;
		viewInterface = isy.selectViewInterface();
		return viewInterface;
	}

	@Override
	public void updateInterface(InterfaceForm face_form)
			throws GeneralException {
		try {
			ViewInterface face = isy.selectViewInterface();
			BeanUtils.copyProperties(face, face_form);
			isy.updateViewInterface(face);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}
	}
}
