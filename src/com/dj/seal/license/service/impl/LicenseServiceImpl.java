package com.dj.seal.license.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.license.dao.impl.LicenseDaoImpl;
import com.dj.seal.license.service.api.ILicenseService;
import com.dj.seal.structure.dao.po.LicenseInfo;
import com.dj.seal.util.Constants;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SysUserUtil;

public class LicenseServiceImpl implements ILicenseService {
	
	static Logger logger = LogManager.getLogger(LicenseServiceImpl.class.getName());
    
	private LicenseDaoImpl license_dao;
	
	public LicenseDaoImpl getLicense_dao() {
		return license_dao;
	}

	public void setLicense_dao(LicenseDaoImpl license_dao) {
		this.license_dao = license_dao;
	}

	@Override
	public LicenseInfo getSql()throws GeneralException{
		String sqlUser="select * from "+SysUserUtil.TABLE_NAME;
		int userUseNum=license_dao.getUseNum(sqlUser);
		String sqlSeal="select * from "+SealBodyUtil.TABLE_NAME+" where "+SealBodyUtil.SEAL_STATUS+"='"+Constants.IS_MAKED+"'";
	    int sealUseNum=license_dao.getUseNum(sqlSeal);
	    LicenseInfo obj=new LicenseInfo();
	    obj.setSealnumuse(String.valueOf(sealUseNum));
	    obj.setUsernumeuse(String.valueOf(userUseNum));
		try {
			DesUtils des = new DesUtils(DesUtils.strAvaliableDateKey);
		    DateFormat df = new SimpleDateFormat("yyy-MM-dd");
			java.util.Date d1 = df.parse(des.decrypt(Constants
					.getLicenseProperty("AVAILABLE_DATA")));
			obj.setAble_data(d1);
			DesUtils des1 = new DesUtils(DesUtils.strUserNumKey);
			obj.setUsernum(des1.decrypt(Constants
						.getLicenseProperty("MAX_USERNUM")));
			DesUtils des2 = new DesUtils(DesUtils.strSealNumKey);
			obj.setSealnum(des2.decrypt(Constants
						.getLicenseProperty("MAX_SEALNUM")));
			DesUtils des3 = new DesUtils(DesUtils.strUnitKey);
			obj.setDept(des3.decrypt(Constants
						.getLicenseProperty("UNIT")));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("查询信息出错"+e.toString());
		}
		return obj;
	}
}
