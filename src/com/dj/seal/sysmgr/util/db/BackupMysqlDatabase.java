package com.dj.seal.sysmgr.util.db;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BackupMysqlDatabase {
	
	static Logger logger = LogManager.getLogger(BackupMysqlDatabase.class.getName());

	public static void backup(String userName, String userPass,
			String serverAddr,String port, String dbName, String outFileName,
			String sqlpath, String mysqlpaths) {

		try {

			File backupath = new File(sqlpath);

			if (!backupath.exists()) {

				backupath.mkdir();

			}

			StringBuffer sb = new StringBuffer();

			sb.append(mysqlpaths);

			sb.append("mysqldump ");

			sb.append("--opt ");

			sb.append("-h ");

			sb.append(serverAddr);

			sb.append(" ");
			
			sb.append("-P");
			
			sb.append(port);
			
			sb.append(" ");

			sb.append("--user=");

			sb.append(userName);

			sb.append(" ");

			sb.append("--password=");

			sb.append(userPass);

			sb.append(" ");

			sb.append("--lock-all-tables=true ");

			sb.append("--result-file=");

			sb.append(sqlpath);

			sb.append(outFileName);

			sb.append(" ");

			sb.append("--default-character-set=utf8 ");

			sb.append(dbName);

			logger.info("cmd指令 ：" + sb.toString());

			Runtime cmd = Runtime.getRuntime();

			try {

				Process p = cmd.exec(sb.toString());

			} catch (IOException e) {

				logger.error(e.getMessage());

			}

		} catch (Exception e1) {

			e1.printStackTrace();

		}

	}

	/*
	 * 
	 * 根据备份文件恢复数据库
	 * 
	 */

	public static void load(String addr,String port,String root, String pass, String dbName,
			String mysqlpaths, String sqlpath, String filename) {

		String filepath = sqlpath + filename;

		String stmt1 = mysqlpaths + "mysqladmin  -u " + root + " -p" + pass

		+ " create " + dbName; // -p后面加的是你的密码

		String stmt2 = mysqlpaths + "mysql -h "+ addr +" -P"+port+" -u " + root + " -p" + pass

		+ " " + dbName + " < " + filepath;

		String[] cmd = { "cmd", "/c", stmt2 };

		try {
			logger.info(stmt2);
			Runtime.getRuntime().exec(stmt1);

			Runtime.getRuntime().exec(cmd);

			logger.info("数据已从 " + filepath + " 导入到数据库中");

		} catch (IOException e) {

			logger.error(e.getMessage());

		}

	}

	/*
	 * 
	 * Test测试
	 * 
	 */

	public static void main(String[] args) throws IOException {

		// 数据库类型
		// 用户名
		// 密码
		// 服务器地址
		// 数据库名称
		// 数据库备份路径
		// 数据库备份路径

		//load(userName, password, dbName, mysqlrun, backupPath,"20130314094050.sql");

	}

}
