package com.dj.seal.hotel.po;


import com.dj.seal.util.dao.BasePO;

/**
 * 单据附属表
 */
public class RecordContentPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rid;// 对应单据ID
	private String content;//单据内容
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
}