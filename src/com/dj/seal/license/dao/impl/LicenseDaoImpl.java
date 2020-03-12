package com.dj.seal.license.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.license.dao.api.ILicenseDao;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.exception.DAOException;

public class LicenseDaoImpl extends BaseDAOJDBC implements ILicenseDao {
	
	static Logger logger = LogManager.getLogger(LicenseDaoImpl.class.getName());

	@Override
	public int getUseNum(String sql)throws DAOException{
		return count(sql);
	}
}
