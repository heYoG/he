package com.dj.seal.hotel.dao.api;

import java.util.List;

import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;
import com.dj.seal.util.exception.DAOException;

public interface RecordDao {

	/**
	 *新增单据记录 
	 */
	public void addRecord(RecordPO record);
	

	
	/**
	 * 更改单据的状态为禁用
	 * @param record
	 */
	public void updateRecordStatusIsOff(String rid) throws DAOException;
	
	/**
	 * 更改单据的状态为可用
	 * @param rid
	 * @throws DAOException
	 */
	public void updateRecordStatusIsOn(String rid) throws DAOException;
	
	/**
	 * 通过sql语句获得recordpo对象
	 * @param sql
	 * @return
	 */
	public RecordPO getRecordPoBySql(String sql) throws DAOException;
	
	public List<RecordPO> getAllRecordPoBy(String sql) throws DAOException;
	
	public RecordPO getRecordPOById(String cid) throws DAOException;
	
	public List<RecordAffiliatePO> getAllRecordAffiliatePOBySql(String sql) throws DAOException;
	
	
	public List<RecordAffiliatePO> getRecordAffiliatePOByRecordId(String recordId) throws DAOException;
	
	public int showCount(String sql);
	
	public void addRecordAffileate(RecordAffiliatePO recordAffiliate);
	
	public void addRecordContent(RecordContentPO rContent);
	
	/**
	 * 获取单据表名称
	 * @return
	 */
	public String getTableName(String tbName);
	
	public int isRecordExist(String fName);
	
}
