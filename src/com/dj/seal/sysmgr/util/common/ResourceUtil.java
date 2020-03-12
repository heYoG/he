package com.dj.seal.sysmgr.util.common;

import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 项目参数工具类
 * 
 * @author lzl
 * 
 */
public class ResourceUtil {
	
	static Logger logger = LogManager.getLogger(ResourceUtil.class.getName());

	private static final ResourceBundle bundle = java.util.ResourceBundle
			.getBundle("config");

	public static final String getResourceName(String name) {
		return bundle.getString(name);
	}

}
