package com.dj.seal.organise.service.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysUnitDao;
import com.dj.seal.organise.service.api.ISysUnitService;
import com.dj.seal.organise.web.form.UnitForm;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;

public class SysUnitService implements ISysUnitService {
	
	static Logger logger = LogManager.getLogger(SysUnitService.class.getName());

	private ISysUnitDao isud;

	public ISysUnitDao getIsud() {
		return isud;
	}

	public void setIsud(ISysUnitDao isud) {
		this.isud = isud;
	}

	@Override
	public void addSysUnit(SysUnit sysUnit) throws GeneralException {
		isud.addSysUnit(sysUnit);
	}

	@Override
	public SysUnit showSysUnit() throws GeneralException {
		return isud.showSysUnit();
	}
	@Override
	public String LicenseUnit() throws Exception{
		return isud.LicenseUnit();
	}
	@Override
	public void upDateSysUnit(UnitForm unitForm) throws GeneralException {
		try {
			SysUnit sysUnit = new SysUnit();
			BeanUtils.copyProperties(sysUnit, unitForm);
			sysUnit.setDept_no(Constants.UNIT_DEPT_NO);
			isud.upDateSysUnit(sysUnit,unitForm.getOld_name());
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
		}

	}

	@Override
	public void deleteSysUnit() throws GeneralException {
		isud.deleteSysUnit();
	}

}
