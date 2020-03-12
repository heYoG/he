package com.dj.hotelApi;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.po.RecordAffiliatePO;
import com.dj.seal.hotel.po.RecordContentPO;
import com.dj.seal.hotel.po.RecordPO;

public class RecordAndAffiliated {
	static Logger log = LogManager.getLogger(RecordAndAffiliated.class.getName());

	private RecordPO record;
	private RecordContentPO rcontent;
	private List<RecordAffiliatePO> recordAffiliatedList;
	
	public RecordContentPO getRcontent() {
		return rcontent;
	}
	public void setRcontent(RecordContentPO rcontent) {
		this.rcontent = rcontent;
	}
	public RecordPO getRecord() {
		return record;
	}
	public void setRecord(RecordPO record) {
		this.record = record;
	}
	public List<RecordAffiliatePO> getRecordAffiliatedList() {
		return recordAffiliatedList;
	}
	public void setRecordAffiliatedList(List<RecordAffiliatePO> recordAffiliatedList) {
		this.recordAffiliatedList = recordAffiliatedList;
	}
	
}
