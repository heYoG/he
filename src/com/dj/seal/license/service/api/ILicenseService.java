package com.dj.seal.license.service.api;

import com.dj.seal.structure.dao.po.LicenseInfo;
import com.dj.seal.util.exception.GeneralException;

public interface ILicenseService {

	/**
	 * 查询信息
	 * 
	 * @param 
	 * @throws GeneralException
	 */ 
	public LicenseInfo getSql()throws GeneralException;
}
