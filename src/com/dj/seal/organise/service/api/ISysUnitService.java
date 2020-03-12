package com.dj.seal.organise.service.api;

import com.dj.seal.organise.web.form.UnitForm;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.util.exception.GeneralException;

public interface ISysUnitService {

	/**
	 * 增加单位
	 * 
	 * @param sysUnit
	 * @throws GeneralException
	 */
	public void addSysUnit(SysUnit sysUnit) throws GeneralException;

	/**
	 * 修改单位的方法
	 * 
	 * @param unitForm
	 * @throws GeneralException
	 */
	public void upDateSysUnit(UnitForm unitForm) throws GeneralException;

	/**
	 * 查询单位信息
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public SysUnit showSysUnit() throws GeneralException;
	/**
	 * 授权单位信息
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public String LicenseUnit() throws Exception;

	/**
	 * 删除单位
	 * 
	 * @throws GeneralException
	 */
	public void deleteSysUnit() throws GeneralException;
}
