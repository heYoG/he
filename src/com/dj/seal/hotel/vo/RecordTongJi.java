package com.dj.seal.hotel.vo;


import com.dj.seal.util.dao.BasePO;

/**
 * ����ͳ���ñ�
 */
public class RecordTongJi extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6954630474162941534L;
	private String user_id;//ӪҵԱID
	private String user_name;//ӪҵԱ����
	private String dept_name;//Ӫҵ������
	private String model_name;// ģ������  ����
	private int number;// ����
	
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	public String getModel_name() {
		return model_name;
	}
	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
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
	
	
}