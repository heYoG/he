package com.dj.seal.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis.transport.http.HTTPConstants;
import org.apache.axis2.context.MessageContext;

import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class Constants {

	// public final static String basePath
	// =System.getProperty("user.dir").replaceAll("bin","webapps") +
	// "/Seal/";//tomcat
	/*用户当前工作目录*/
	public final static String basePath = System.getProperty("user.dir")
			+ "/autodeploy/Seal/";// weblogic
	// 设置单位默认的部门编号为“0000”
	public final static String UNIT_DEPT_NO = "0000";
	/*村镇银行虚拟汇总机构*/
	public final static String VILLAGE_DEPT_NO="-888";
	// public final static String
	// DEPT_KEY_STR="0123456789:@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_`abcdefghijklmnopqrstuvwxyz";
	public final static String DEPT_KEY_STR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	// 每页显示数
	public final static int PAGESIZE = 80;
	public final static int ISOCR = Integer.valueOf(Constants
			.getProperty("is_ocr"));
	// 数据库类型
	public final static int DB_TYPE = DBTypeUtil.DT_ORCL;
	// public final static int DB_TYPE = DBTypeUtil.DT_MYSQL;
	// public final static int DB_TYPE= DBTypeUtil.DT_DB2;
	// HttpSession中存放用户权限对象的名称
	public final static String SESSION_AUTH_TOKEN = "SESSION_AUTH_TOKEN";
	// oracle表空间名
	public final static String TABLESPACE = "tkrs";
	public final static String ORCL_USER = "tom";
	public final static String ORCL_PSD = "tom";
	// 用户身份状态
	public final static String USER_STATUS_ADMIN = "1";// 系统管理员
	public final static String USER_STATUS_LOGGER = "2";// 日志审计员
	public final static String USER_STATUS_NOMOR = "3";// 普通用户
	public final static String USER_NAME_ADMIN = "admin";// 内置超级管理员用户名
	public final static String USER_NAME_LOGGER = "logger";// 内置日志审核员用户名
	// 用户状态
	public final static String ZXUSER_STATUS = "4";// 注销状态
	public final static String USER_STATUS = "1";// 正常状态
	// 是否使用证书
	public final static String IS_USE = "1";// 使用
	public final static String NO_USE = "0";// 不使用
	// 证书状态
	public final static String ZCERT_STATE = "1"; // 注销证书
	public final static String UCERT_STATE = "2"; // 可用证书
	// HttpSession中存放当前用户对象的名称
	public final static String SESSION_CURRENT_USER = "current_user";
	public final static String USER_ACTIVE_NO = "0";// 用户禁止登录
	// 制章状态
	public final static String TEMP_STATUS_REG = "1";// 申请状态（编辑后未提交申请）
	public final static String TEMP_STATUS_OK = "2";// 审核通过
	public final static String TEMP_STATUS_FAIL = "3";// 审核未通过
	public final static String NO_MAKED = "4";// 未制章
	public final static String IS_MAKED = "5";// 已制章&正常
	public final static String SEAL_STATUS_STOP = "6";// 失效
	public final static String SEAL_STATUS_DISABLE = "7";// 注销

	// 用户审批状态
	public final static String IS_APPROVE_W = "0";// 待审批
	public final static String IS_APPROVE_Y = "1";// 审批通过
	public final static String IS_APPROVE_N = "2";// 审批未通过

	public static List<String> CHECKSTRS = new ArrayList<String>();
	static {
		CHECKSTRS.add("<");
		CHECKSTRS.add(">");
		CHECKSTRS.add("|");
		CHECKSTRS.add("*");
		CHECKSTRS.add("?");
		CHECKSTRS.add(":");
		CHECKSTRS.add("/");
		CHECKSTRS.add("\\");
		CHECKSTRS.add("\"");
	}

	public static String getProperty(String key) {
		InputStream inputStream = Constants.class.getClassLoader().getResourceAsStream("config.properties");
		Properties p = new Properties();
		String ret = "";
		try {
			p.load(inputStream);
			ret = new String(p.getProperty(key).getBytes("iso-8859-1"), "utf-8");
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public static String getLicenseProperty(String key) throws Exception {
		InputStream inputStream = Constants.class.getClassLoader()
				.getResourceAsStream("license.properties");
		Properties p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String ret = new String(p.getProperty(key).getBytes("iso-8859-1"),
				"utf-8");
		return ret;
	}

	public static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}

	public static String myTrim(String str) {
		str = str.replaceAll("<br>", "");
		str = str.trim();
		return str;
	}

	public static boolean checkStr(String str) {
		for (String s : CHECKSTRS) {
			if (str.indexOf(s) != -1) {
				return true;
			}
		}
		return false;
	}

	public static String getClientIp() {
		MessageContext mc = MessageContext.getCurrentMessageContext();
		if (mc == null) {
			return null;
		}
		HttpServletRequest request = (HttpServletRequest) mc
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String ip = request.getHeader("x-forwarded-for");
		System.out.println("ip:" + ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("ip1:" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("ip2:" + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("ip3:" + ip);
		}

		return ip;
	}
	
}
