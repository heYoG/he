package com.dj.seal.organise.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.organise.dao.api.ISysUnitDao;
import com.dj.seal.structure.dao.po.SysUnit;
import com.dj.seal.util.Constants;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.table.SysUnitUtil;

public class SysUnitDaoImpl extends BaseDAOJDBC implements ISysUnitDao {
	
	static Logger logger = LogManager.getLogger(SysUnitDaoImpl.class.getName());

	@Override
	public void addSysUnit(SysUnit sysUnit) throws DAOException {
		SysUnitUtil sysUnitUtil = new SysUnitUtil();
		sysUnit.setDept_no(Constants.UNIT_DEPT_NO);// 默认设置为"0000"
		String sql = ConstructSql.composeInsertSql(sysUnit, sysUnitUtil);
		add(sql);
	}

	@Override
	@SuppressWarnings("unchecked")
	public SysUnit showSysUnit() throws DAOException {
		SysUnit sysUnit = new SysUnit();
		String sql = "select * from " + SysUnitUtil.TABLE_NAME;
		List list = select(sql);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sysUnit = (SysUnit) DaoUtil.setPo(sysUnit, map, new SysUnitUtil());
		}
		return sysUnit;
	}
	@Override
	public String LicenseUnit() throws Exception{
		SysUnit sysUnit = new SysUnit();
		String sql = "select * from " + SysUnitUtil.TABLE_NAME;
		List list = select(sql);
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			sysUnit = (SysUnit) DaoUtil.setPo(sysUnit, map, new SysUnitUtil());
		}
		DesUtils des = new DesUtils(DesUtils.strUnitKey);
		String unit_name=des.decrypt(Constants.getLicenseProperty("UNIT"));
		if(!sysUnit.getUnit_name().equals(unit_name)){
			String sqlup="update "+SysUnitUtil.TABLE_NAME+" set "+SysUnitUtil.UNIT_NAME+"='"+unit_name+"'";
			update(sqlup);
		}
		return unit_name;
	}
	@Override
	public void upDateSysUnit(SysUnit sysUnit, String old_name)
			throws DAOException {
		SysUnitUtil sysUnitUtil = new SysUnitUtil();
		String parasStr = SysUnitUtil.UNIT_NAME + "='" + old_name + "'";
		String sql = ConstructSql.composeUpdateSql(sysUnit, sysUnitUtil,
				parasStr);
		update(sql);
	}

	@Override
	public void deleteSysUnit() throws DAOException {
		String sql = "delete from " + SysUnitUtil.TABLE_NAME;
		delete(sql);

	}

}
