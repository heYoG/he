package com.dj.seal.organise.web.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author yan
 * 
 */
public class UserForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private String unique_id;// 唯一ID
	private String user_id; // 用户名
	private String user_name; // 真实姓名
	private String user_psd; // 密码
	private String initial_password; // 初始密码
	private String key_id; // 硬件设备key的序列号
	private String key_sn; // 证书序列号
	private String key_dn; // 证书DN
	private String key_cert; // 证书公钥
	private String cert_user; // 证书使用者（证书详细信息）
	private String cert_issue; // 证书颁发机构（证书详细信息）
	private String begin_time; // 证书有效期起始
	private String end_time; // 证书有效期结束
	private FormFile pfxContent; // 上传本地证书pfx文件
	private FormFile certContent; // 上传本地证书cert文件
	private String pfxPsw; // 上传本地证书pfx文件,证书密码
	private String user_sex; // 性别
	private String user_birth; // 生日
	private String mobil_no; // 手机
	private String user_remark; // 备注
	private String is_approve;// 是否批准
	private String user_email; // 电子邮箱
	private String dept_no; // 部门
	private String tel_no_dept; // 部门电话
	private String useing_key; // 是否使用证书
	private String mobile_no_hidden; // 手机号是否隐藏
	private String print_able; // 是否能打印文档
	private String user_theme; // 界面主题
	private String dept_name; // 部门名称（新增）
	private String role_no; // 角色ID（新增）
	private String role_nos; // 角色ID字符串
	private String role_names; // 角色名称字符串
	private String manage_range; // 管理范围（新增）
	private String rang_type; // 范围类型（新增）
	private String is_active; // 用户是否允许登录系统（新增）
	private String is_exits; // 用户证书是否已被使用
	private String is_flow; // 用户使用印章必需走申请流程
	private String user_type; // 用户状态（4：注销用户）
	private String user_ip; // 用户IP
	private String create_name; // 创建人
	private String create_user; // 创建人(姓名)
	private String create_data; // 创建时间
	private String is_junior; // 是否允许下级
	private String xt_user; // 应用系统用户名
	private String xt_pwd; // 应用系统密码
	private String qstart_time;// 用于查询
	private String qend_time;// 用于查询
	//private String operate_user;// 操作人
	private String operate_time;// 操作时间
	//private String state;// 审批内容
	private Integer logined;//是否第一次登录
	private String password1;//旧密码1
	private String password2;//旧密码2
	private String password1md5;//旧密码1的MD5加密
	private String password2md5;//旧密码2的MD5加密
	private String currentpassword;//当前使用密码
	

	public String getUnique_id() {
		return unique_id;
	}

	public void setUnique_id(String unique_id) {
		this.unique_id = unique_id;
	}

	public String getXt_user() {
		return xt_user;
	}

	public void setXt_user(String xt_user) {
		this.xt_user = xt_user;
	}

	public String getXt_pwd() {
		return xt_pwd;
	}

	public void setXt_pwd(String xt_pwd) {
		this.xt_pwd = xt_pwd;
	}

	public String getInitial_password() {
		return initial_password;
	}

	public void setInitial_password(String initial_password) {
		this.initial_password = initial_password;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getCert_user() {
		return cert_user;
	}

	public void setCert_user(String cert_user) {
		this.cert_user = cert_user;
	}

	public String getCert_issue() {
		return cert_issue;
	}

	public void setCert_issue(String cert_issue) {
		this.cert_issue = cert_issue;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getIs_junior() {
		return is_junior;
	}

	public void setIs_junior(String is_junior) {
		this.is_junior = is_junior;
	}

	public String getCreate_name() {
		return create_name;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getCreate_data() {
		return create_data;
	}

	public void setCreate_data(String create_data) {
		this.create_data = create_data;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getIs_exits() {
		return is_exits;
	}

	public void setIs_exits(String is_exits) {
		this.is_exits = is_exits;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getManage_range() {
		return manage_range;
	}

	public void setManage_range(String manage_range) {
		this.manage_range = manage_range;
	}

	public String getRole_no() {
		return role_no;
	}

	public void setRole_no(String role_no) {
		this.role_no = role_no;
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

	public String getUser_psd() {
		return user_psd;
	}

	public void setUser_psd(String user_psd) {
		this.user_psd = user_psd;
	}

	public String getKey_sn() {
		return key_sn;
	}

	public void setKey_sn(String key_sn) {
		this.key_sn = key_sn;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}

	public String getMobil_no() {
		return mobil_no;
	}

	public void setMobil_no(String mobil_no) {
		this.mobil_no = mobil_no;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getTel_no_dept() {
		return tel_no_dept;
	}

	public void setTel_no_dept(String tel_no_dept) {
		this.tel_no_dept = tel_no_dept;
	}

	public String getUser_remark() {
		return user_remark;
	}

	public void setUser_remark(String user_remark) {
		this.user_remark = user_remark;
	}

	public String getIs_approve() {
		return is_approve;
	}

	public void setIs_approve(String is_approve) {
		this.is_approve = is_approve;
	}

	public String getUseing_key() {
		return useing_key;
	}

	public void setUseing_key(String useing_key) {
		this.useing_key = useing_key;
	}

	public String getMobile_no_hidden() {
		return mobile_no_hidden;
	}

	public void setMobile_no_hidden(String mobile_no_hidden) {
		this.mobile_no_hidden = mobile_no_hidden;
	}

	public String getPrint_able() {
		return print_able;
	}

	public void setPrint_able(String print_able) {
		this.print_able = print_able;
	}

	public String getUser_theme() {
		return user_theme;
	}

	public void setUser_theme(String user_theme) {
		this.user_theme = user_theme;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRang_type() {
		return rang_type;
	}

	public void setRang_type(String rang_type) {
		this.rang_type = rang_type;
	}

	public String getRole_nos() {
		return role_nos;
	}

	public void setRole_nos(String role_nos) {
		this.role_nos = role_nos;
	}

	public String getRole_names() {
		return role_names;
	}

	public void setRole_names(String role_names) {
		this.role_names = role_names;
	}

	public String getIs_flow() {
		return is_flow;
	}

	public void setIs_flow(String is_flow) {
		this.is_flow = is_flow;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getKey_dn() {
		return key_dn;
	}

	public void setKey_dn(String key_dn) {
		this.key_dn = key_dn;
	}

	public String getKey_cert() {
		return key_cert;
	}

	public void setKey_cert(String key_cert) {
		this.key_cert = key_cert;
	}

	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public String getQstart_time() {
		return qstart_time;
	}

	public void setQstart_time(String qstart_time) {
		this.qstart_time = qstart_time;
	}

	public String getQend_time() {
		return qend_time;
	}

	public void setQend_time(String qend_time) {
		this.qend_time = qend_time;
	}

	public FormFile getPfxContent() {
		return pfxContent;
	}

	public void setPfxContent(FormFile pfxContent) {
		this.pfxContent = pfxContent;
	}

	public String getPfxPsw() {
		return pfxPsw;
	}

	public void setPfxPsw(String pfxPsw) {
		this.pfxPsw = pfxPsw;
	}

	public FormFile getCertContent() {
		return certContent;
	}

	public void setCertContent(FormFile certContent) {
		this.certContent = certContent;
	}

//	public String getOperate_user() {
//		return operate_user;
//	}
//
//	public void setOperate_user(String operate_user) {
//		this.operate_user = operate_user;
//	}

	public String getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}

//	public String getState() {
//		return state;
//	}
//
//	public void setState(String state) {
//		this.state = state;
//	}

	public Integer getLogined() {
		return logined;
	}

	public void setLogined(Integer logined) {
		this.logined = logined;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getPassword1md5() {
		return password1md5;
	}

	public void setPassword1md5(String password1md5) {
		this.password1md5 = password1md5;
	}

	public String getPassword2md5() {
		return password2md5;
	}

	public void setPassword2md5(String password2md5) {
		this.password2md5 = password2md5;
	}

	public String getCurrentpassword() {
		return currentpassword;
	}

	public void setCurrentpassword(String currentpassword) {
		this.currentpassword = currentpassword;
	}
}
