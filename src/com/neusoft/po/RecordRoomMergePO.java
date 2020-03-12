/**    
 * @{#} RecordRoomMergePO.java Create on 2015-7-19 下午10:40:56    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

/**    
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class RecordRoomMergePO extends BasePO {
	private static final long serialVersionUID = -6954630474162941534L;
	private String cid; // 通过UUID生成
	private String guestname; // 客户名称
	private String roomno; // 房间号
	private String mergedate; // 合并日期
	private String filename; // 合并文件名
	private String savefilename; // 合并文件相对路径
	private Timestamp createtime; // 合并文件创建时间
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getGuestname() {
		return guestname;
	}
	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}
	public String getRoomno() {
		return roomno;
	}
	public void setRoomno(String roomno) {
		this.roomno = roomno;
	}
	public String getMergedate() {
		return mergedate;
	}
	public void setMergedate(String mergedate) {
		this.mergedate = mergedate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSavefilename() {
		return savefilename;
	}
	public void setSavefilename(String savefilename) {
		this.savefilename = savefilename;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	
}

