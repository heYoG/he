package com.dj.seal.sealBody.web;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class SealBodyVO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer seal_id; // 印章id
	private Integer temp_id; // 外键 印模id
	private String dept_no; // 外键 部门编号
	private String seal_name; // 印章名，唯一
	private String seal_type; // 印章类型
	private String seal_data; // 印章数据
	private String role_list; // 印章授权角色列表
	private String user_list; // 印章授权用户列表
	private String seal_creator; // 外键 制章人
	private Timestamp create_time; // 制章时间
	private String seal_status; // 印章状态
	private String client_system; // 客户端软件
	private String dept_name; // 部门名称(新增)
	private String type_name; // 印章类型名（新增）
	private String create_name; // 制章人用户真实姓名
	public String user_apply; // 待审批用户列表
	public String is_apply; // 用户是否申请印章(新增)
	public String is_flow; // 是否需要走印章使用申请流程
    public String seal_usestate; //印章使用状态
    public Timestamp approve_begintime; // 申请起始时间
    public Timestamp approve_endtime; // 申请结束时间
    public String key_sn; //证书号
    public String key_name; //证书
    public String apply_user; //  申请人
    public String apply_name; //  申请姓名
    public Timestamp seal_applytime; // 申请时间
    public String approve_user; //  审批人(新增)
    public String approve_name; //  审批姓名(新增) 
    public Timestamp seal_approvetime; // 申请时间
    public String temp_remark;//备注
    public String user_id;//用户id
    public String user_name;//用户姓名
    public String user_roleid;//用户角色id
    public String user_role;//用户角色
    public String user_no;//用户数量
    public String role_no;//角色数量
    public String seal_applystate; //印章申请状态
    public int seal_no;//使用印章的人数
    public String sealid_no;//使用印章的id拼接
    public String rule_no; //规则号
    public String rule_num;//规则数量
    public String preview_img;//印章缩略图 
    
	public String getPreview_img() {
		return preview_img;
	}
	public void setPreview_img(String preview_img) {
		this.preview_img = preview_img;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	public String getRule_no() {
		return rule_no;
	}

	public void setRule_no(String rule_no) {
		this.rule_no = rule_no;
	}

	public String getRule_num() {
		return rule_num;
	}

	public void setRule_num(String rule_num) {
		this.rule_num = rule_num;
	}

	public String getApply_name() {
		return apply_name;
	}

	public void setApply_name(String apply_name) {
		this.apply_name = apply_name;
	}
	public String getSeal_usestate() {
		return seal_usestate;
	}

	public void setSeal_usestate(String seal_usestate) {
		this.seal_usestate = seal_usestate;
	}

	public String getIs_flow() {
		return is_flow;
	}

	public void setIs_flow(String is_flow) {
		this.is_flow = is_flow;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SealBodyVO other = (SealBodyVO) obj;
		if (seal_id == null) {
			if (other.seal_id != null)
				return false;
		} else if (!seal_id.equals(other.seal_id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((seal_id == null) ? 0 : seal_id.hashCode());
		return result;
	}

	public String getIs_apply() {
		return is_apply;
	}

	public void setIs_apply(String is_apply) {
		this.is_apply = is_apply;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getUser_apply() {
		return user_apply;
	}

	public void setUser_apply(String user_apply) {
		this.user_apply = user_apply;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public Integer getSeal_id() {
		return seal_id;
	}

	public void setSeal_id(Integer seal_id) {
		this.seal_id = seal_id;
	}

	public Integer getTemp_id() {
		return temp_id;
	}

	public void setTemp_id(Integer temp_id) {
		this.temp_id = temp_id;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getSeal_name() {
		return seal_name;
	}

	public void setSeal_name(String seal_name) {
		this.seal_name = seal_name;
	}

	public String getSeal_type() {
		return seal_type;
	}

	public void setSeal_type(String seal_type) {
		this.seal_type = seal_type;
	}

	public String getSeal_data() {
		return seal_data;
	}

	public void setSeal_data(String seal_data) {
		this.seal_data = seal_data;
	}

	public String getRole_list() {
		return role_list;
	}

	public void setRole_list(String role_list) {
		this.role_list = role_list;
	}

	public String getUser_list() {
		return user_list;
	}

	public void setUser_list(String user_list) {
		this.user_list = user_list;
	}

	public String getSeal_creator() {
		return seal_creator;
	}

	public void setSeal_creator(String seal_creator) {
		this.seal_creator = seal_creator;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public String getSeal_status() {
		return seal_status;
	}

	public void setSeal_status(String seal_status) {
		this.seal_status = seal_status;
	}

	public String getClient_system() {
		return client_system;
	}

	public void setClient_system(String client_system) {
		this.client_system = client_system;
	}

	public Timestamp getApprove_begintime() {
		return approve_begintime;
	}

	public void setApprove_begintime(Timestamp approve_begintime) {
		this.approve_begintime = approve_begintime;
	}

	public Timestamp getApprove_endtime() {
		return approve_endtime;
	}

	public void setApprove_endtime(Timestamp approve_endtime) {
		this.approve_endtime = approve_endtime;
	}

	public String getKey_sn() {
		return key_sn;
	}

	public void setKey_sn(String key_sn) {
		this.key_sn = key_sn;
	}

	public String getApply_user() {
		return apply_user;
	}

	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}

	public Timestamp getSeal_applytime() {
		return seal_applytime;
	}

	public void setSeal_applytime(Timestamp seal_applytime) {
		this.seal_applytime = seal_applytime;
	}

	public String getTemp_remark() {
		return temp_remark;
	}

	public void setTemp_remark(String temp_remark) {
		this.temp_remark = temp_remark;
	}

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

	public String getUser_roleid() {
		return user_roleid;
	}

	public void setUser_roleid(String user_roleid) {
		this.user_roleid = user_roleid;
	}

	public String getUser_role() {
		return user_role;
	}

	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getUser_no() {
		return user_no;
	}

	public void setUser_no(String user_no) {
		this.user_no = user_no;
	}

	public String getRole_no() {
		return role_no;
	}

	public void setRole_no(String role_no) {
		this.role_no = role_no;
	}

	public String getSeal_applystate() {
		return seal_applystate;
	}

	public void setSeal_applystate(String seal_applystate) {
		this.seal_applystate = seal_applystate;
	}

	public String getApprove_user() {
		return approve_user;
	}

	public void setApprove_user(String approve_user) {
		this.approve_user = approve_user;
	}

	public String getApprove_name() {
		return approve_name;
	}

	public void setApprove_name(String approve_name) {
		this.approve_name = approve_name;
	}

	public Timestamp getSeal_approvetime() {
		return seal_approvetime;
	}

	public void setSeal_approvetime(Timestamp seal_approvetime) {
		this.seal_approvetime = seal_approvetime;
	}


	public int getSeal_no() {
		return seal_no;
	}

	public void setSeal_no(int seal_no) {
		this.seal_no = seal_no;
	}

	public String getSealid_no() {
		return sealid_no;
	}

	public void setSealid_no(String sealid_no) {
		this.sealid_no = sealid_no;
	}

}
