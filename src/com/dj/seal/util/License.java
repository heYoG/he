package com.dj.seal.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.sealTemplate.service.impl.SealTemplateServiceImpl;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.exception.DAOException;

public class License {
	private UserService user;
	private SealTemplateServiceImpl sealtemp;

	public SealTemplateServiceImpl getSealtemp() {
		return sealtemp;
	}

	public void setSealtemp(SealTemplateServiceImpl sealtemp) {
		this.sealtemp = sealtemp;
	}

	public UserService getUser() {
		return user;
	}

	public void setUser(UserService user) {
		this.user = user;
	}

	/**
	 * 获取系统有效期
	 * 
	 * @return 1:有效期已过，0是在有效期之内
	 * @throws Exception
	 * @throws DAOException
	 */
	public static String getAbleDate() throws Exception {
		DateFormat df = new SimpleDateFormat("yyy-MM-dd");
		DesUtils des = new DesUtils(DesUtils.strAvaliableDateKey);
		java.util.Date d1 = df.parse(des.decrypt(Constants
				.getLicenseProperty("AVAILABLE_DATA")));
		Date d2 = new Date();
		if (d1.getTime() <= d2.getTime()) {
			return "1";
		} else {
			return "0";
		}
	}

	/**
	 * 获取用户最大数量
	 * 
	 * @return false:已超出最大数量，true在数量之内
	 * @throws Exception
	 */
	public String selUserNum() throws Exception {
		return user.selUserNum();
	}

	/**
	 * 获取印章最大数量
	 * 
	 * @return false:已超出最大数量，true在数量之内
	 * @throws Exception
	 */
	public String selTempNum() throws Exception {
		return sealtemp.selTempNum();
	}
}
    
