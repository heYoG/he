package com.dj.hotelApi;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.lic.LicService;

public class LicenseDJ {
	static Logger log = LogManager.getLogger(LicenseDJ.class.getName());

	public static String licOper(String licNo,String newCount,String ip,String user,String mac,String flag,String lic,String token,String licText){
		LicService util=new LicService();
		String dbdriver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://192.168.1.95:3306/testpaperless?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String dbpsd = "123456";
		Connection conn = null;
		try {
			Class.forName(dbdriver);
			conn = DriverManager.getConnection(dburl, dbuser, dbpsd);
		} catch (Exception e) {
			log.info("checklicense数据库连接出错，错误信息如下：");
			log.error(e.getMessage());
		}
		String res = null;
		try {
			res = util.licOper(licNo,newCount,ip,user,mac,flag,lic,token,conn,licText);
		} catch (Exception e) {
			log.info("检查授权出错，错误信息如下：");
			log.error(e.getMessage());
		}
		return res;
	}
	
	
}
