package com.dj.seal.license.dao.api;
import com.dj.seal.util.exception.DAOException;

public interface ILicenseDao {

	public int getUseNum(String sql)throws DAOException;
}
