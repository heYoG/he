package com.dj.seal.sysmgr.dao.api;

import com.dj.seal.structure.dao.po.SysBackup;


/**
 * @title IBackupDao
 * @description 数据备份DAO接口
 * @author lzl
 * @since 2013-04-16
 * @version 1.0
 * 
 */
public interface IBackupDao {
	
	public SysBackup sysBackup(String sql);

	public void update(SysBackup sysbackup);

	public void insert(SysBackup tb);
}