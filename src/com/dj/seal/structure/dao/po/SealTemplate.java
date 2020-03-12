package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class SealTemplate extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer temp_id; // 印模id
	private String temp_name; // 印模名称，唯一
	private String temp_data; // 印模数据(印章形式的数据)
	private String seal_type; // 印章类型
	private String dept_no; // 外键 部门编号
	private String image_width; // 图片宽度
	private String image_height; // 图片高度
	private String seal_width; // 印章宽度
	private String seal_height; // 印章高度
	private Integer seal_bit; // 印章大小
	private String temp_status; // 印模流程状态
	private String is_maked; // 是否已制章
	private String temp_creator; // 外键 创建人
	private Timestamp create_time; // 创建时间
	private String temp_remark; // 备注
	private String approve_user; //  审批人
	private Timestamp approve_time; // 审批时间
	private String approve_text; // 审批意见
	private String client_system; // 客户端软件
	private String use_status; // 印模使用状态
    private String user_apply;  // 印模申请人
    private String user_applyer;  // 申请印章制作的人（申请）
    private String user_name;  // 申请人姓名
	private String type_name; // 印模类型名称（新增）
	private String dept_name; // 部门名称（新增）
	private String bit_name; // 色彩位数名称（新增）
	private String status_name; // 状态名称（新增）
	private String use_status_name;// 使用状态名称（新增）
	private String seal_creator; // 外键 制章人
	private String preview_img;//印章缩略图 PREVIEW_IMG
	
	private Timestamp able_btime; // 印章有效期起始
	private Timestamp able_etime; // 印章有效期结束


	public Timestamp getAble_btime() {
		return able_btime;
	}

	public void setAble_btime(Timestamp able_btime) {
		this.able_btime = able_btime;
	}

	public Timestamp getAble_etime() {
		return able_etime;
	}

	public void setAble_etime(Timestamp able_etime) {
		this.able_etime = able_etime;
	}

	public String getPreview_img() {
		return preview_img;
	}

	public void setPreview_img(String preview_img) {
		this.preview_img = preview_img;
	}

	public String getUser_applyer() {
		return user_applyer;
	}

	public void setUser_applyer(String user_applyer) {
		this.user_applyer = user_applyer;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getSeal_creator() {
		return seal_creator;
	}

	public void setSeal_creator(String seal_creator) {
		this.seal_creator = seal_creator;
	}

	public String getUser_apply() {
		return user_apply;
	}

	public void setUser_apply(String user_apply) {
		this.user_apply = user_apply;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SealTemplate other = (SealTemplate) obj;
		if (temp_id == null) {
			if (other.temp_id != null)
				return false;
		} else if (!temp_id.equals(other.temp_id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((temp_id == null) ? 0 : temp_id.hashCode());
		return result;
	}

	public Integer getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(Integer temp_id) {
		this.temp_id = temp_id;
	}

	public String getTemp_name() {
		return temp_name;
	}

	public void setTemp_name(String temp_name) {
		this.temp_name = temp_name;
	}

	public String getTemp_data() {
		return temp_data;
	}

	public void setTemp_data(String temp_data) {
		this.temp_data = temp_data;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getImage_width() {
		return image_width;
	}

	public void setImage_width(String image_width) {
		this.image_width = image_width;
	}

	public String getImage_height() {
		return image_height;
	}

	public void setImage_height(String image_height) {
		this.image_height = image_height;
	}

	public String getSeal_width() {
		return seal_width;
	}

	public void setSeal_width(String seal_width) {
		this.seal_width = seal_width;
	}

	public String getSeal_height() {
		return seal_height;
	}

	public void setSeal_height(String seal_height) {
		this.seal_height = seal_height;
	}

	public Integer getSeal_bit() {
		return seal_bit;
	}

	public void setSeal_bit(Integer seal_bit) {
		this.seal_bit = seal_bit;
	}

	public String getTemp_status() {
		return temp_status;
	}

	public void setTemp_status(String temp_status) {
		this.temp_status = temp_status;
	}

	public String getIs_maked() {
		return is_maked;
	}

	public void setIs_maked(String is_maked) {
		this.is_maked = is_maked;
	}

	public String getTemp_creator() {
		return temp_creator;
	}

	public void setTemp_creator(String temp_creator) {
		this.temp_creator = temp_creator;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getTemp_remark() {
		return temp_remark;
	}

	public void setTemp_remark(String temp_remark) {
		this.temp_remark = temp_remark;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

	public Timestamp getApprove_time() {
		return approve_time;
	}

	public void setApprove_time(Timestamp approve_time) {
		this.approve_time = approve_time;
	}

	public String getApprove_text() {
		return approve_text;
	}

	public void setApprove_text(String approve_text) {
		this.approve_text = approve_text;
	}

	public String getClient_system() {
		return client_system;
	}

	public void setClient_system(String client_system) {
		this.client_system = client_system;
	}

	public String getBit_name() {
		return bit_name;
	}

	public void setBit_name(String bit_name) {
		this.bit_name = bit_name;
	}

	public String getStatus_name() {
		return status_name;
	}

	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}

	public String getUse_status() {
		return use_status;
	}

	public void setUse_status(String use_status) {
		this.use_status = use_status;
	}

	public String getUse_status_name() {
		return use_status_name;
	}

	public void setUse_status_name(String use_status_name) {
		this.use_status_name = use_status_name;
	}

}
