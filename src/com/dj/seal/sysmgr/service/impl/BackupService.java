package com.dj.seal.sysmgr.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobDataMap;

import com.dj.seal.structure.dao.po.SysBackup;
import com.dj.seal.sysmgr.dao.api.IBackupDao;
import com.dj.seal.sysmgr.service.api.IBackupService;
import com.dj.seal.sysmgr.util.common.DateFormatUtils;
import com.dj.seal.sysmgr.util.db.BackupMysqlDatabase;
import com.dj.seal.sysmgr.util.db.BackupOracleDatabase;
import com.dj.seal.sysmgr.util.timer.CustomJob;
import com.dj.seal.sysmgr.util.timer.QuartzJobOne;
import com.dj.seal.sysmgr.util.timer.QuartzManager;
import com.dj.seal.util.properyUtil.DJPropertyUtil;

public class BackupService implements IBackupService {
	
	static Logger logger = LogManager.getLogger(BackupService.class.getName());

	private IBackupDao backup_dao;

	public IBackupDao getBackup_dao() {
		return backup_dao;
	}

	public void setBackup_dao(IBackupDao backup_dao) {
		this.backup_dao = backup_dao;
	}

	/**
	 * 判断自动备份是否已启动
	 * 
	 */
	@Override
	public void checkTask() {
		SysBackup t = getBackup();
		if (t != null) {
			if (t.getStatus()!=null&&t.getStatus().equals("0")) {
				CustomJob job = new CustomJob();
				job.setJobId("job1");
				job.setJobGroup("job1_group");
				job.setJobName("数据库备份定时器");
				job.setMemos("根据用户配置，自动备份数据库");
				job.setCronExpression(getTimeConfig(t.getFrequency(), t.getDate(), t.getTime()));// 定时任务表达式
				job.setStateFulljobExecuteClass(QuartzJobOne.class);
				JobDataMap paramsMap = new JobDataMap();
				QuartzManager.enableCronSchedule(job, paramsMap, true);
				System.out.println("check enable backup!");
			}
		}
	}
	
	public String getTimeConfig(String cfrequency, String cdate, String ctime) {
		String cronExpression = "";
		if (cfrequency.equals("0")) {// 每天
			cronExpression = "0 0 " + ctime + " * * ?";
		} else if (cfrequency.equals("1")) {// 每周第几天
			cronExpression = "0 0 " + ctime + " ? * " + cdate;
		} else if (cfrequency.equals("2")) {// 每月第几天
			cronExpression = "0 0 " + ctime + " " + cdate + " * ?";
		}
		System.out.println("定时任务时间表达式:" + cronExpression);
		return cronExpression;
	}

	
	@Override
	public SysBackup getBackup() {
		String date = DJPropertyUtil.getPropertyValue("DATE");
		String frequency = DJPropertyUtil.getPropertyValue("FREQUENCY");
		String status = DJPropertyUtil.getPropertyValue("STATUS");
		String time = DJPropertyUtil.getPropertyValue("TIME");
		SysBackup sysbackup = new SysBackup();
		sysbackup.setDate(date);
		sysbackup.setFrequency(frequency);
		sysbackup.setStatus(status);
		sysbackup.setTime(time);
		return sysbackup;
	}
	
	/**
	 * 保存备份计划
	 * 
	 * */
	@Override
	public String saveBackup(String status,String frequency,String date,String time) {
		DJPropertyUtil.setPropery("DATE", date);
		DJPropertyUtil.setPropery("FREQUENCY",frequency);
		DJPropertyUtil.setPropery("STATUS",status);
		DJPropertyUtil.setPropery("TIME",time);
		if (status.equals("0")) {
			// 启用
			CustomJob job = new CustomJob();
			job.setJobId("job1");
			job.setJobGroup("job1_group");
			job.setJobName("数据库备份定时器");
			job.setMemos("根据用户配置，自动备份数据库");
			job.setCronExpression(getTimeConfig(frequency, date, time));// 定时任务表达式
			job.setStateFulljobExecuteClass(QuartzJobOne.class);
			JobDataMap paramsMap = new JobDataMap();
			QuartzManager.enableCronSchedule(job, paramsMap, true);
		} else {
			// 停用
			QuartzManager.disableSchedule("job1", "job1_group");
		}
		return "配置成功";
	}

	
	/**
	 * 手动备份
	 * 
	 * */
	@Override
	public String backup() {
		String type = DJPropertyUtil.getPropertyValue("DBTYPE");
		if("mysql".equals(type)){
			String path = this.getClass().getResource("/").getPath().replace("WEB-INF/classes/", "")+"mysqlrun/";
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
		}else{
			return "备份失败！";
		}
		return "备份成功！";
	}

	/**
	 * 获取指定文件夹下数据库列表
	 * 
	 * */
	
	@Override
	public String databaseList(String backupPath) {
		List<String> list = this.getFileList(backupPath);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if((list.size()-1)!=i){
				sb.append(list.get(i)+",");
			}else{
				sb.append(list.get(i));
			}
		}
		return sb.toString();
	}
	public List<String> getFileList(String path) {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(path);
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				list.add(filelist[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 数据库还原
	 * 
	 * */
	@Override
	public String recoverys(String filepath,String backuppath){
		String type = DJPropertyUtil.getPropertyValue("DBTYPE");
		if("mysql".equals(type)){
			String userName = DJPropertyUtil.getPropertyValue("USERNAME");
			String userPass = DJPropertyUtil.getPropertyValue("PASSWORD");
			String serverAddr = DJPropertyUtil.getPropertyValue("server_addr");
			String port = DJPropertyUtil.getPropertyValue("port");
			String dbName = DJPropertyUtil.getPropertyValue("db_name");
			String outFilePath = DJPropertyUtil.getPropertyValue("backup_path");
			String mysqlrun = DJPropertyUtil.getPropertyValue("mysqlrun");
			new BackupMysqlDatabase();
			BackupMysqlDatabase.load(serverAddr,port,userName, userPass, dbName, mysqlrun, backuppath, filepath);
		}else if("oracle".equals(type)){
			//CreateTableForOracle ctfo = new CreateTableForOracle();
			//ctfo.dropTable();
			String userName = DJPropertyUtil.getPropertyValue("USERNAME");
			String userPass = DJPropertyUtil.getPropertyValue("PASSWORD");
			String serverAddr = DJPropertyUtil.getPropertyValue("server_addr");
			String port = DJPropertyUtil.getPropertyValue("port");
			String dbName = DJPropertyUtil.getPropertyValue("db_name");
			String outFilePath = DJPropertyUtil.getPropertyValue("backup_path");
			new BackupOracleDatabase(userName, userPass, serverAddr, port, dbName,
					backuppath, filepath).doRCWork();
		}
		return "还原成功";
	}
	/**
	 * 备份文件
	 */
	@Override
	public String backupFile(String status, String frequencysel,
			String datesel, String timeseal) {
		if("0".equals(status)){//启用
			DJPropertyUtil.setPropery("fileBakupStatus", status);//状态 ，0启用，1禁用
			DJPropertyUtil.setPropery("rate", frequencysel);// 0 每天，1每周，2每月
			DJPropertyUtil.setPropery("datesel", datesel);//日期
			DJPropertyUtil.setPropery("timesel", timeseal);//时间
			try {
				DJPropertyUtil.saveToFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "ok";
	}
	
	
	

}