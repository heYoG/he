package com.dj.seal.sysmgr.service.api;

import com.dj.seal.structure.dao.po.SysBackup;

/**
 * @title IBackupService
 * @description 数据备份Service接口
 * @author lzl
 * @since 2013-04-16
 * @version 1.0
 * 
 */
public interface IBackupService {

	public void checkTask();

	public SysBackup getBackup();
	
	public String backup();
	
	public String saveBackup(String status,String frequency,String date,String time);
	
	public String databaseList(String backupPath);
	
	public String recoverys(String filepath,String backuppath);
	
	public String backupFile(String status,String frequencysel,String datesel,String timeseal);
}
