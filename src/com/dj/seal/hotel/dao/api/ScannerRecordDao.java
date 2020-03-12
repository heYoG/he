package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.ScannerRecordPO;
import com.dj.seal.util.exception.DAOException;

public interface ScannerRecordDao {

	/**
	 *新增单据记录 
	 */
	public void addScannerRecord(ScannerRecordPO scannerRecord);
	

	
	/**
	 * 通过sql语句获得recordpo对象
	 * @param sql
	 * @return
	 */
	public ScannerRecordPO getScannerRecordPoBySql(String sql) throws DAOException;
	
	public List<ScannerRecordPO> getAllScannerRecordPoBy(String sql) throws DAOException;
	
	public ScannerRecordPO getScannerRecordPOById(String id) throws DAOException;
	
	
	public int showCount(String sql);
	
	
	public int isScannerRecordExist(String fName);
	
}
