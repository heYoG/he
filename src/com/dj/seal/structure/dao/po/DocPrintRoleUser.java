package com.dj.seal.structure.dao.po;

/**
 * 
 * @description 文档打印设置表po
 * @author oyxy
 * @since 2009-11-2
 * 
 */
public class DocPrintRoleUser {
	private static final long serialVersionUID = 1L;
	private String doc_no; // 文档编号
	private int type;// 0 按用户分配，1 按角色分配
	private String user_id; // 用户id或者角色id
	private int printnum;// 总打印份数
	private int currnum;// 当前已打印份数
	public String getDoc_no() {
		return doc_no;
	}
	public void setDoc_no(String doc_no) {
		this.doc_no = doc_no;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public int getPrintnum() {
		return printnum;
	}
	public void setPrintnum(int printnum) {
		this.printnum = printnum;
	}
	public int getCurrnum() {
		return currnum;
	}
	public void setCurrnum(int currnum) {
		this.currnum = currnum;
	}
	

	

}
