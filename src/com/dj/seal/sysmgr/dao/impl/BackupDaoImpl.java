package com.dj.seal.sysmgr.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.structure.dao.po.SysBackup;
import com.dj.seal.sysmgr.dao.api.IBackupDao;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.table.SysBackupUtil;

public class BackupDaoImpl extends BaseDAOJDBC implements IBackupDao {
	
	static Logger logger = LogManager.getLogger(BackupDaoImpl.class.getName());

	private SysBackupUtil table = new SysBackupUtil();
	
	@Override
	@SuppressWarnings("unchecked")
	public SysBackup sysBackup(String sql) {
		List<Map> list = select(sql);
		SysBackup sysback = new SysBackup();
		if(list!=null&&list.size()>0){
			sysback = (SysBackup) DaoUtil.setPo(sysback, list.get(0), table);
		}else{
			return null;
		}
		return sysback;
	}

	
	@Override
	public void update(SysBackup sysbackup) {
		String parasStr = SysBackupUtil.ID + "=" + sysbackup.getId();
		String sql = ConstructSql.composeUpdateSql(sysbackup, table, parasStr);
		update(sql);
	}

	
	@Override
	public void insert(SysBackup tb) {
		String sql = ConstructSql.composeInsertSql(tb, table);
		add(sql);		
	}
}
