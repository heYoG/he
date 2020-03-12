package com.dj.seal.hotel.service.api;

import java.util.List;

import com.dj.seal.hotel.po.ScannerRecordPO;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.exception.DAOException;
import com.dj.seal.util.web.SearchForm;

public interface ScannerRecordService {
	
	public PageSplit showScannerRecordBySch(int pageIndex, int pageSize, SearchForm f)
	throws Exception;

	public String addScannerRecord(ScannerRecordPO scannerRecord);
	
	/**
	 * 通过sql语句获得recordpo对象
	 * @param sql
	 * @return
	 */
	public ScannerRecordPO getScannerRecordPoBySql(String sql) throws DAOException;
	
	public List<ScannerRecordPO> getAllScannerRecordPoBy(String sql) throws DAOException;
	
	public ScannerRecordPO getScannerRecordPOById(String id) throws DAOException;
	
	
	
	
}
