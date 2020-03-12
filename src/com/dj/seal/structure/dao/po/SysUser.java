package com.dj.seal.structure.dao.po;

import java.sql.Timestamp;

import com.dj.seal.util.dao.BasePO;

public class SysUser extends BasePO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String unique_id;// 唯一ID
	private String user_id;// 用户名
	private String user_name;// 真实姓名
	private String useing_key;// 是否使用证书 0,不绑定，1 key证书，2 pfx文件证书 ，3 ie证书
	private String user_psd;// 密码
	private String initial_password;// 初始密码
	private String key_id;// 硬件设备key的序列号
	private String key_sn;// 证书序列号
	private String key_dn;// 证书DN
	private String key_cert;// 证书公钥
	private String user_sex;// 性别
	private Timestamp user_birth;// 生日
	private String mobil_no;// 手机
	private String user_remark;// 备注
	private String user_email;// 电子邮箱
	private Timestamp last_visit_time; // 最后访问时间
	private String add_home; // 家庭地址
	private String post_no_home; // 家庭邮政编码
	private String tel_no_home; // 家庭电话
	private Integer user_fun1; // 权限值一
	private Integer user_fun2; // 权限值二
	private Integer user_fun3; // 权限值三
	private Integer user_fun4; // 权限值四
	private Integer user_fun5; // 权限值五
	private String mytable_left; // 左边模块
	private String mytable_right; // 右边模块
	private String on_status; // 在线状态
	private String user_status; // 用户身份
	private String last_visit_ip; // 最后访问IP
	private String is_approve;// 是否批准
	private String user_tab;// 用户排序号
	private String is_active;// 用户是否允许登录系统
	private String is_flow;// 用户使用印章必需走申请流程
	private String user_type;// 用户状态（4：注销用户）
	private String user_ip;// 用户IP
	private String create_name;// 创建人
	private Timestamp create_data;// 创建时间
	private String rang_type;// 范围类型
	private String is_junior;// 是否允许下级
	private String xt_user;// 应用系统用户名
	private String xt_pwd;// 应用系统密码
	private String create_user;// 创建人(姓名)
	private String dept_name;// 部门名称（新增）
	private String role_no;// 角色ID（新增）
	private String dept_no;
	private int roleNum;// 角色数量
	private String manage_range;// 管理范围（新增）
	private String user_theme; // 用户主题
	private String role_name;// 角色名
	private String tel_no_dept;// 部门电话
	private String mobile_no_hidden;// 手机号是否隐藏
	private String print_able;// 是否能打印文档
	private String manage_fw; // 管理范围部门
	private String manage_froleid;// 管理范围部门id
	//private String operate_user;//操作人
	private Timestamp operate_time;//操作时间
	//private String state;//审批内容
	private Integer logined;//是否第一次登录 0:是,1:否
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

	public Timestamp getLast_visit_time() {
		return last_visit_time;
	}

	public void setLast_visit_time(Timestamp last_visit_time) {
		this.last_visit_time = last_visit_time;
	}

	public String getAdd_home() {
		return add_home;
	}

	public void setAdd_home(String add_home) {
		this.add_home = add_home;
	}

	public String getPost_no_home() {
		return post_no_home;
	}

	public void setPost_no_home(String post_no_home) {
		this.post_no_home = post_no_home;
	}

	public String getTel_no_home() {
		return tel_no_home;
	}

	public void setTel_no_home(String tel_no_home) {
		this.tel_no_home = tel_no_home;
	}

	public Integer getUser_fun1() {
		return user_fun1;
	}

	public void setUser_fun1(Integer user_fun1) {
		this.user_fun1 = user_fun1;
	}

	public Integer getUser_fun2() {
		return user_fun2;
	}

	public void setUser_fun2(Integer user_fun2) {
		this.user_fun2 = user_fun2;
	}

	public Integer getUser_fun3() {
		return user_fun3;
	}

	public void setUser_fun3(Integer user_fun3) {
		this.user_fun3 = user_fun3;
	}

	public Integer getUser_fun4() {
		return user_fun4;
	}

	public void setUser_fun4(Integer user_fun4) {
		this.user_fun4 = user_fun4;
	}

	public Integer getUser_fun5() {
		return user_fun5;
	}

	public void setUser_fun5(Integer user_fun5) {
		this.user_fun5 = user_fun5;
	}

	public String getMytable_left() {
		return mytable_left;
	}

	public void setMytable_left(String mytable_left) {
		this.mytable_left = mytable_left;
	}

	public String getMytable_right() {
		return mytable_right;
	}

	public void setMytable_right(String mytable_right) {
		this.mytable_right = mytable_right;
	}

	public String getOn_status() {
		return on_status;
	}

	public void setOn_status(String on_status) {
		this.on_status = on_status;
	}

	public String getUser_status() {
		return user_status;
	}

	public void setUser_status(String user_status) {
		this.user_status = user_status;
	}

	public String getLast_visit_ip() {
		return last_visit_ip;
	}

	public void setLast_visit_ip(String last_visit_ip) {
		this.last_visit_ip = last_visit_ip;
	}

	public String getIs_approve() {
		return is_approve;
	}

	public void setIs_approve(String is_approve) {
		this.is_approve = is_approve;
	}

	public String getUser_tab() {
		return user_tab;
	}

	public void setUser_tab(String user_tab) {
		this.user_tab = user_tab;
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

	public String getInitial_password() {
		return initial_password;
	}

	public void setInitial_password(String initial_password) {
		this.initial_password = initial_password;
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

	public String getUseing_key() {
		return useing_key;
	}

	public void setUseing_key(String useing_key) {
		this.useing_key = useing_key;
	}

	public String getRang_type() {
		return rang_type;
	}

	public void setRang_type(String rang_type) {
		this.rang_type = rang_type;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getIs_flow() {
		return is_flow;
	}

	public void setIs_flow(String is_flow) {
		this.is_flow = is_flow;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getCreate_name() {
		return create_name;
	}

	public String getIs_junior() {
		return is_junior;
	}

	public void setIs_junior(String is_junior) {
		this.is_junior = is_junior;
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

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getRole_no() {
		return role_no;
	}

	public void setRole_no(String role_no) {
		this.role_no = role_no;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public int getRoleNum() {
		return roleNum;
	}

	public void setRoleNum(int roleNum) {
		this.roleNum = roleNum;
	}

	public String getManage_range() {
		return manage_range;
	}

	public void setManage_range(String manage_range) {
		this.manage_range = manage_range;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getUser_theme() {
		return user_theme;
	}

	public void setUser_theme(String user_theme) {
		this.user_theme = user_theme;
	}

	public String getMobil_no() {
		return mobil_no;
	}

	public void setMobil_no(String mobil_no) {
		this.mobil_no = mobil_no;
	}

	public String getUser_remark() {
		return user_remark;
	}

	public void setUser_remark(String user_remark) {
		this.user_remark = user_remark;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public Timestamp getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(Timestamp user_birth) {
		this.user_birth = user_birth;
	}

	public Timestamp getCreate_data() {
		return create_data;
	}

	public void setCreate_data(Timestamp create_data) {
		this.create_data = create_data;
	}

	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}

	public String getTel_no_dept() {
		return tel_no_dept;
	}

	public void setTel_no_dept(String tel_no_dept) {
		this.tel_no_dept = tel_no_dept;
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

	public String getManage_fw() {
		return manage_fw;
	}

	public void setManage_fw(String manage_fw) {
		this.manage_fw = manage_fw;
	}

	public String getManage_froleid() {
		return manage_froleid;
	}

	public void setManage_froleid(String manage_froleid) {
		this.manage_froleid = manage_froleid;
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

//	public String getOperate_user() {
//		return operate_user;
//	}
//
//	public void setOperate_user(String operate_user) {
//		this.operate_user = operate_user;
//	}

	public Timestamp getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Timestamp operate_time) {
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
