package com.dj.seal.hotel.service.api;

import java.util.List;

import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;

public interface RecordService {

	/**
	 * 添加单据记录
	 * */
	public String addRecord(RecordPO record);
	
	public List<RecordPO> ShowRecord();
	
	public List<RecordAffiliatePO> shwoRecordAffiliate();
	
	public List<RecordAffiliatePO> getRecordAffiliatePOByRid(String rid);
	
	public int addRecordAffiliatePo(RecordAffiliatePO recordAffiliate);
	
	public int updateRecordStatusIsOff(String rid);
	
	public int updateRecordStatusIsOn(String rid);
	
	public List<RecordPO> getRecordPos(String strs);
	/**
	 * 插入单据文档内容
	 * @param rContent
	 * @return
	 */
	public int addRecordContent(RecordContentPO rContent);
	
	
}
