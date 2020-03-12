package com.dj.seal.sysmgr.util.timer;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.dj.seal.sysmgr.util.common.DateFormatUtils;
import com.dj.seal.sysmgr.util.db.BackupMysqlDatabase;
import com.dj.seal.sysmgr.util.db.BackupOracleDatabase;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

/**
 * @author lzl
 * 
 * 2013-4-16 
 */
public class QuartzJobOne extends StatefulMethodInvokingJob {
	static Logger logger = LogManager.getLogger(QuartzJobOne.class.getName());
	@Override
	public void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		String type = DJPropertyUtil.getPropertyValue("DBTYPE");
		if("mysql".equals(type)){
			String userName = DJPropertyUtil.getPropertyValue("USERNAME");
			String userPass = DJPropertyUtil.getPropertyValue("PASSWORD");
			String serverAddr = DJPropertyUtil.getPropertyValue("server_addr");
			String port = DJPropertyUtil.getPropertyValue("port");
			String dbName = DJPropertyUtil.getPropertyValue("db_name");
			String outFilePath = DJPropertyUtil.getPropertyValue("backup_path");
			String mysqlrun = DJPropertyUtil.getPropertyValue("mysqlrun");
			String filename = DateFormatUtils.formatDate(new Date(),"yyyyMMddHHmmss")+ ".sql";
			new BackupMysqlDatabase();
			BackupMysqlDatabase.backup(userName, userPass, serverAddr, port,dbName, filename, outFilePath, mysqlrun);
		}else if("oracle".equals(type)){
			String filename = DateFormatUtils.formatDate(new Date(),"yyyyMMddHHmmss")+ ".DMP";
			String userName = DJPropertyUtil.getPropertyValue("USERNAME");
			String userPass = DJPropertyUtil.getPropertyValue("PASSWORD");
			String serverAddr = DJPropertyUtil.getPropertyValue("server_addr");
			String port = DJPropertyUtil.getPropertyValue("port");
			String dbName = DJPropertyUtil.getPropertyValue("db_name");
			String outFilePath = DJPropertyUtil.getPropertyValue("backup_path");
			new BackupOracleDatabase(userName, userPass,serverAddr, port, dbName, outFilePath,filename ).doWork();
		}
	}
}
