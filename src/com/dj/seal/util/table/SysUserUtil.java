package com.dj.seal.util.table;

/**
 * 
 * @description 用户表 
 * @author oyxy
 * @since 2009-11-2
 *
 */
/**
 * @author LUY
 * 
 */
public class SysUserUtil {

	public static String TABLE_NAME = "T_AC"; // 表名
	public static String UNIQUE_ID = "C_AC0";// 唯一编号
	public static String USER_ID = "C_ACA"; // 用户名
	public static String USER_NAME = "C_ACB"; // 真实姓名
	public static String USEING_KEY = "C_ACC"; // 是否使用证书
	public static String USER_PSD = "C_ACD"; // 密码
	public static String INITIAL_PASSWORD = "INITIAL_PASSWORD"; // 初始密码
	public static String KEY_ID = "key_id";// 硬件设备key的序列号
	public static String KEY_SN = "C_ACE"; // 证书号
	public static String KEY_DN = "C_KEYDN";// 证书DN
	public static String KEY_CERT = "C_KEYCERT";// 证书公钥
	public static String USER_SEX = "C_ACF"; // 性别
	public static String USER_BIRTH = "C_ACG"; // 生日
	public static String LAST_VISIT_TIME = "C_ACH"; // 最后访问时间
	public static String ADD_HOME = "C_ACI"; // 家庭地址
	public static String POST_NO_HOME = "C_ACJ"; // 家庭邮编
	public static String TEL_NO_HOME = "C_ACK"; // 家庭电话
	public static String MOBIL_NO = "C_ACL"; // 手机号码
	public static String MOBILE_NO_HIDDEN = "C_ACM"; // 手机号是否隐藏
	public static String USER_EMAIL = "C_ACN"; // 邮箱
	public static String DEPT_NO = "C_ACO"; // 部门编号
	public static String TEL_NO_DEPT = "C_ACP"; // 部门电话
	public static String PRINT_ABLE = "C_ACQ"; // 是否允许打印
	public static String USER_FUN1 = "C_ACR"; // 权限组一的权限值
	public static String USER_FUN2 = "C_ACS"; // 权限组二的权限值
	public static String USER_FUN3 = "C_ACT"; // 权限组三的权限值
	public static String USER_FUN4 = "C_ACU"; // 权限组四的权限值
	public static String USER_FUN5 = "C_ACV"; // 权限组五的权限值
	public static String USER_THEME = "C_ACW"; // 界面主题
	public static String MYTABLE_LEFT = "C_ACX"; // 桌面左边模块
	public static String MYTABLE_RIGHT = "C_ACY"; // 桌面右边模块
	public static String ON_STATUS = "C_ACZ"; // 在线状态
	public static String USER_STATUS = "C_AC1"; // 用户身份状态
	public static String LAST_VISIT_IP = "C_AC2"; // 最后登录IP
	public static String USER_REMARK = "C_AC3"; // 备注
	public static String IS_APPROVE = "C_AC4"; // 是否批准
	public static String USER_TAB = "C_AC5"; // 用户排序号
	public static String IS_ACTIVE = "C_AC6"; // 是否禁止登陆
	public static String IS_FLOW = "C_AC7"; // 是否需要走印章使用申请流程
	public static String USER_TYPE = "C_AUT"; // 用户状态（4：注销用户）
	public static String USER_IP = "C_IP"; // 用户IP
	public static String CREATE_NAME = "C_NAME";// 创建人
	public static String CREATE_DATA = "C_DATA";// 创建时间
	public static String RANG_TYPE = "U_MANAGER";// 用户管理范围(新增)manage_range
	public static String IS_JUNIOR = "C_AHJ"; // 是否允许下级
	public static String XT_USER = "XT_ACA";// 应用系统用户名
	public static String XT_PWD = "XT_ACB";// 应用系统密码
	//public static String OPERATE_USER = "OPERATE_USER";// 操作人
	public static String OPERATE_TIME = "OPERATE_TIME";// 操作时间
	//public static String STATE = "STATE";// 审批内容
	public static String LOGINED="LOGINED";//是否第一次登录
	public static String PASSWORD1="PASSWORD1";//旧密码1
	public static String PASSWORD2="PASSWORD2";//旧密码2
	public static String PASSWORD1MD5="PASSWORD1MD5";//旧密码1的MD5加密
	public static String PASSWORD2MD5="PASSWORD2MD5";//旧密码2的MD5加密
	public static String CURRENTPASSWORD="CURRENTPASSWORD";//当前使用密码

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(UNIQUE_ID).append(" varchar(20),");
		sb.append(USER_ID).append(" varchar(20),");
		sb.append(USER_NAME).append(" varchar(200),");
		sb.append(USEING_KEY).append(" varchar(2),");
		sb.append(USER_PSD).append(" varchar(50),");
		sb.append(INITIAL_PASSWORD).append(" varchar(50),");
		sb.append(KEY_ID).append(" varchar(100),");
		sb.append(KEY_SN).append(" varchar(100),");
		sb.append(KEY_DN).append(" varchar(255),");
		sb.append(KEY_CERT).append(" clob,");
		sb.append(USER_SEX).append(" char(1),");
		sb.append(USER_BIRTH)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(LAST_VISIT_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(ADD_HOME).append(" varchar(200),");
		sb.append(POST_NO_HOME).append(" varchar(50),");
		sb.append(TEL_NO_HOME).append(" varchar(50),");
		sb.append(MOBIL_NO).append(" varchar(50),");
		sb.append(MOBILE_NO_HIDDEN).append(" varchar(20) default '0',");
		sb.append(USER_EMAIL).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(255),");
		sb.append(TEL_NO_DEPT).append(" varchar(50),");
		sb.append(PRINT_ABLE).append(" varchar(20) default '0',");
		sb.append(USER_FUN1).append(" int,");
		sb.append(USER_FUN2).append(" int,");
		sb.append(USER_FUN3).append(" int,");
		sb.append(USER_FUN4).append(" int,");
		sb.append(USER_FUN5).append(" int,");
		sb.append(USER_THEME).append(" varchar(10) default '1',");
		sb.append(MYTABLE_LEFT).append(" varchar(200),");
		sb.append(MYTABLE_RIGHT).append(" varchar(200),");
		sb.append(ON_STATUS).append(" varchar(20) default '0',");
		sb.append(USER_STATUS).append(" varchar(20) default '0',");
		sb.append(LAST_VISIT_IP).append(" varchar(100),");
		sb.append(USER_REMARK).append(" varchar(255),");
		sb.append(IS_APPROVE).append(" varchar(20),");
		sb.append(USER_TAB).append(" varchar(20),");
		sb.append(IS_ACTIVE).append(" varchar(20),");
		sb.append(IS_FLOW).append(" varchar(20),");
		sb.append(USER_TYPE).append(" varchar(50),");
		sb.append(USER_IP).append(" varchar(200),");
		sb.append(CREATE_NAME).append(" varchar(200),");
		sb.append(CREATE_DATA)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(RANG_TYPE).append(" varchar(20),");
		sb.append(IS_JUNIOR).append(" varchar(20),");
		sb.append(XT_USER).append(" varchar(20),");
		sb.append(XT_PWD).append(" varchar(20)");
//		sb.append(OPERATE_USER).append(" varchar(50)");
		sb.append(OPERATE_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
//		sb.append(STATUS).append(" varchar(50)");
//		sb.append(STATE).append(" varchar(50)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回建用户表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table "
				+ TABLE_NAME
				+ " ("
				+ UNIQUE_ID
				+ " varchar(20) ,"
				+ USER_ID
				+ " varchar(20) primary key,"
				+ USER_NAME
				+ " varchar(200) ,"// not null,"
				+ USEING_KEY
				+ " varchar(20) ,"// not null,"
				+ USER_PSD
				+ " varchar(50) ,"// not null,"
				+ INITIAL_PASSWORD
				+ " varchar(50) ,"// not null,"
				+ KEY_ID
				+ " varchar(100) ,"
				+ KEY_SN
				+ " varchar(100) ,"// not null,"
				+ KEY_DN
				+ " varchar(255) ,"// not null,"
				+ KEY_CERT
				+ " mediumtext ,"// not null,"
				+ USER_SEX
				+ " varchar(20) ,"// not null,"
				+ USER_BIRTH
				+ " datetime default '1970-01-01 08:00:00' ,"// not null,"
				+ LAST_VISIT_TIME
				+ " datetime default '1970-01-01 08:00:00' ,"// not null,"
				+ ADD_HOME
				+ " varchar(200) ,"// not null,"
				+ POST_NO_HOME
				+ " varchar(50) ,"// not null,"
				+ TEL_NO_HOME
				+ " varchar(50) ,"// not null,"
				+ MOBIL_NO
				+ " varchar(50) ,"// not null,"
				+ MOBILE_NO_HIDDEN
				+ " varchar(20) default '0' ,"// not null,"
				+ USER_EMAIL
				+ " varchar(50) ,"// not null,"
				+ DEPT_NO
				+ " varchar(255) ,"// not null,"//foreign key references
				// "+SysDepartment.TABLE_NAME+"("+SysDepartment.DEPT_NO+"),"
				+ TEL_NO_DEPT
				+ " varchar(50) ,"// not null,"
				+ PRINT_ABLE
				+ " varchar(20) default '0' ,"// not null,"
				+ USER_FUN1
				+ " int ,"// not null,"
				+ USER_FUN2
				+ " int ,"// not null,"
				+ USER_FUN3
				+ " int ,"// not null,"
				+ USER_FUN4
				+ " int ,"// not null,"
				+ USER_FUN5
				+ " int ,"// not null,"
				+ USER_THEME
				+ " varchar(10) default '1' ,"// not null,"
				+ MYTABLE_LEFT
				+ " varchar(200) ,"// not null,"
				+ MYTABLE_RIGHT
				+ " varchar(200) ,"// not null,"
				+ ON_STATUS
				+ " varchar(20) default '0' ,"// not null,"
				+ USER_STATUS
				+ " varchar(20) default '0' ,"// not null,"
				+ LAST_VISIT_IP
				+ " varchar(100) ,"// not null,"
				+ USER_REMARK + " varchar(255)," + IS_APPROVE + " varchar(20),"
				+ USER_TAB + " varchar(20)," + IS_ACTIVE + " varchar(20),"
				+ IS_FLOW + " varchar(20)," + USER_TYPE + " varchar(50),"
				+ USER_IP + " varchar(200)," + CREATE_NAME + " varchar(50),"
				+ CREATE_DATA + " datetime default '1970-01-01 08:00:00' ,"
				+ RANG_TYPE + " varchar(20)," + IS_JUNIOR
				+ " varchar(20) not null," + XT_USER + " varchar(20) ,"
				+ XT_PWD + " varchar(20) " + ")," + OPERATE_TIME
				+ " datetime default '1970-01-01 08:00:00' " ;
//				","+ STATE + " varchar(50) ";
		System.out.println(create_sql + ";");
		return create_sql;
	}

	/**
	 * @description 返回建用户表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(UNIQUE_ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(USER_ID).append(" varchar(50),");
		sb.append(USER_NAME).append(" varchar(50),");
		sb.append(USEING_KEY).append(" varchar(2),");
		sb.append(USER_PSD).append(" varchar(50),");
		sb.append(INITIAL_PASSWORD).append(" varchar(50),");
		sb.append(KEY_ID).append(" varchar(100),");
		sb.append(KEY_SN).append(" varchar(100),");
		sb.append(KEY_DN).append(" varchar(255),");
		sb.append(KEY_CERT).append(" clob,");
		sb.append(USER_SEX).append(" varchar(10),");
		sb.append(USER_BIRTH).append(" timestamp,");
		sb.append(LAST_VISIT_TIME).append(" timestamp,");
		sb.append(ADD_HOME).append(" varchar(200),");
		sb.append(POST_NO_HOME).append(" varchar(50),");
		sb.append(TEL_NO_HOME).append(" varchar(50),");
		sb.append(MOBIL_NO).append(" varchar(50),");
		sb.append(MOBILE_NO_HIDDEN).append(" varchar(20),");
		sb.append(USER_EMAIL).append(" varchar(50),");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(TEL_NO_DEPT).append(" varchar(50),");
		sb.append(PRINT_ABLE).append(" varchar(20),");
		sb.append(USER_FUN1).append(" int,");
		sb.append(USER_FUN2).append(" int,");
		sb.append(USER_FUN3).append(" int,");
		sb.append(USER_FUN4).append(" int,");
		sb.append(USER_FUN5).append(" int,");
		sb.append(USER_THEME).append(" varchar(10),");
		sb.append(MYTABLE_LEFT).append(" varchar(200),");
		sb.append(MYTABLE_RIGHT).append(" varchar(200),");
		sb.append(ON_STATUS).append(" varchar(20),");
		sb.append(USER_STATUS).append(" varchar(20),");
		sb.append(LAST_VISIT_IP).append(" varchar(100),");
		sb.append(USER_REMARK).append(" varchar(255),");
		sb.append(IS_APPROVE).append(" varchar(20),");
		sb.append(USER_TAB).append(" varchar(20),");
		sb.append(IS_ACTIVE).append(" varchar(20),");
		sb.append(IS_FLOW).append(" varchar(20),");
		sb.append(USER_TYPE).append(" varchar(50),");
		sb.append(USER_IP).append(" varchar(200),");
		sb.append(CREATE_NAME).append(" varchar(200),");
		sb.append(CREATE_DATA).append(" timestamp,");
		sb.append(RANG_TYPE).append(" varchar(20),");
		sb.append(IS_JUNIOR).append(" varchar(20),");
		sb.append(XT_USER).append(" varchar(20),");
		sb.append(XT_PWD).append(" varchar(20),");
//		sb.append(OPERATE_USER).append(" varchar(50),");
		sb.append(OPERATE_TIME).append(" timestamp,");
//		sb.append(STATUS).append(" varchar(50),");
//		sb.append(STATE).append(" varchar(50)");
		sb.append(")");
		return sb.toString();
	}

	public static String getXT_USER() {
		return XT_USER;
	}

	public static void setXT_USER(String xt_user) {
		XT_USER = xt_user;
	}

	public static String getXT_PWD() {
		return XT_PWD;
	}

	public static void setXT_PWD(String xt_pwd) {
		XT_PWD = xt_pwd;
	}

	public static String getIS_FLOW() {
		return IS_FLOW;
	}

	public static String getIS_ACTIVE() {
		return IS_ACTIVE;
	}

	public static String getUSER_TAB() {
		return USER_TAB;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getUSER_ID() {
		return USER_ID;
	}

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static String getUSEING_KEY() {
		return USEING_KEY;
	}

	public static String getUSER_PSD() {
		return USER_PSD;
	}

	public static String getINITIAL_PASSWORD() {
		return INITIAL_PASSWORD;
	}

	public static String getKEY_SN() {
		return KEY_SN;
	}

	public static String getUSER_SEX() {
		return USER_SEX;
	}

	public static String getUSER_BIRTH() {
		return USER_BIRTH;
	}

	public static String getLAST_VISIT_TIME() {
		return LAST_VISIT_TIME;
	}

	public static String getADD_HOME() {
		return ADD_HOME;
	}

	public static String getPOST_NO_HOME() {
		return POST_NO_HOME;
	}

	public static String getTEL_NO_HOME() {
		return TEL_NO_HOME;
	}

	public static String getMOBIL_NO() {
		return MOBIL_NO;
	}

	public static String getMOBILE_NO_HIDDEN() {
		return MOBILE_NO_HIDDEN;
	}

	public static String getUSER_EMAIL() {
		return USER_EMAIL;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static String getTEL_NO_DEPT() {
		return TEL_NO_DEPT;
	}

	public static String getPRINT_ABLE() {
		return PRINT_ABLE;
	}

	public static String getUSER_FUN1() {
		return USER_FUN1;
	}

	public static String getUSER_FUN2() {
		return USER_FUN2;
	}

	public static String getUSER_FUN3() {
		return USER_FUN3;
	}

	public static String getUSER_FUN4() {
		return USER_FUN4;
	}

	public static String getUSER_FUN5() {
		return USER_FUN5;
	}

	public static String getUSER_THEME() {
		return USER_THEME;
	}

	public static String getMYTABLE_LEFT() {
		return MYTABLE_LEFT;
	}

	public static String getMYTABLE_RIGHT() {
		return MYTABLE_RIGHT;
	}

	public static String getON_STATUS() {
		return ON_STATUS;
	}

	public static String getUSER_STATUS() {
		return USER_STATUS;
	}

	public static String getLAST_VISIT_IP() {
		return LAST_VISIT_IP;
	}

	public static String getUSER_REMARK() {
		return USER_REMARK;
	}

	public static String getIS_APPROVE() {
		return IS_APPROVE;
	}

	public static String getUSER_TYPE() {
		return USER_TYPE;
	}

	public static String getUSER_IP() {
		return USER_IP;
	}

	public static String getCREATE_NAME() {
		return CREATE_NAME;
	}

	public static String getCREATE_DATA() {
		return CREATE_DATA;
	}

	public static String getRANG_TYPE() {
		return RANG_TYPE;
	}

	public static String getIS_JUNIOR() {
		return IS_JUNIOR;
	}

	/**
	 * @description 返回删除用户表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getUNIQUE_ID() {
		return UNIQUE_ID;
	}

	public static void setUNIQUE_ID(String unique_id) {
		UNIQUE_ID = unique_id;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}

	public static void setUSER_NAME(String user_name) {
		USER_NAME = user_name;
	}

	public static void setUSEING_KEY(String useing_key) {
		USEING_KEY = useing_key;
	}

	public static void setUSER_PSD(String user_psd) {
		USER_PSD = user_psd;
	}

	public static void setINITIAL_PASSWORD(String initial_password) {
		INITIAL_PASSWORD = initial_password;
	}

	public static void setKEY_SN(String key_sn) {
		KEY_SN = key_sn;
	}

	public static void setUSER_SEX(String user_sex) {
		USER_SEX = user_sex;
	}

	public static void setUSER_BIRTH(String user_birth) {
		USER_BIRTH = user_birth;
	}

	public static void setLAST_VISIT_TIME(String last_visit_time) {
		LAST_VISIT_TIME = last_visit_time;
	}

	public static void setADD_HOME(String add_home) {
		ADD_HOME = add_home;
	}

	public static void setPOST_NO_HOME(String post_no_home) {
		POST_NO_HOME = post_no_home;
	}

	public static void setTEL_NO_HOME(String tel_no_home) {
		TEL_NO_HOME = tel_no_home;
	}

	public static void setMOBIL_NO(String mobil_no) {
		MOBIL_NO = mobil_no;
	}

	public static void setMOBILE_NO_HIDDEN(String mobile_no_hidden) {
		MOBILE_NO_HIDDEN = mobile_no_hidden;
	}

	public static void setUSER_EMAIL(String user_email) {
		USER_EMAIL = user_email;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static void setTEL_NO_DEPT(String tel_no_dept) {
		TEL_NO_DEPT = tel_no_dept;
	}

	public static void setPRINT_ABLE(String print_able) {
		PRINT_ABLE = print_able;
	}

	public static void setUSER_FUN1(String user_fun1) {
		USER_FUN1 = user_fun1;
	}

	public static void setUSER_FUN2(String user_fun2) {
		USER_FUN2 = user_fun2;
	}

	public static void setUSER_FUN3(String user_fun3) {
		USER_FUN3 = user_fun3;
	}

	public static void setUSER_FUN4(String user_fun4) {
		USER_FUN4 = user_fun4;
	}

	public static void setUSER_FUN5(String user_fun5) {
		USER_FUN5 = user_fun5;
	}

	public static void setUSER_THEME(String user_theme) {
		USER_THEME = user_theme;
	}

	public static void setMYTABLE_LEFT(String mytable_left) {
		MYTABLE_LEFT = mytable_left;
	}

	public static void setMYTABLE_RIGHT(String mytable_right) {
		MYTABLE_RIGHT = mytable_right;
	}

	public static void setON_STATUS(String on_status) {
		ON_STATUS = on_status;
	}

	public static void setUSER_STATUS(String user_status) {
		USER_STATUS = user_status;
	}

	public static void setLAST_VISIT_IP(String last_visit_ip) {
		LAST_VISIT_IP = last_visit_ip;
	}

	public static void setUSER_REMARK(String user_remark) {
		USER_REMARK = user_remark;
	}

	public static void setIS_APPROVE(String is_approve) {
		IS_APPROVE = is_approve;
	}

	public static void setUSER_TAB(String user_tab) {
		USER_TAB = user_tab;
	}

	public static void setIS_ACTIVE(String is_active) {
		IS_ACTIVE = is_active;
	}

	public static void setIS_FLOW(String is_flow) {
		IS_FLOW = is_flow;
	}

	public static void setUSER_TYPE(String user_type) {
		USER_TYPE = user_type;
	}

	public static void setUSER_IP(String user_ip) {
		USER_IP = user_ip;
	}

	public static void setCREATE_NAME(String create_name) {
		CREATE_NAME = create_name;
	}

	public static void setCREATE_DATA(String create_data) {
		CREATE_DATA = create_data;
	}

	public static void setRANG_TYPE(String rang_type) {
		RANG_TYPE = rang_type;
	}

	public static void setIS_JUNIOR(String is_junior) {
		IS_JUNIOR = is_junior;
	}

	public static String getKEY_DN() {
		return KEY_DN;
	}

	public static void setKEY_DN(String key_dn) {
		KEY_DN = key_dn;
	}

	public static String getKEY_CERT() {
		return KEY_CERT;
	}

	public static void setKEY_CERT(String key_cert) {
		KEY_CERT = key_cert;
	}

	public static String getKEY_ID() {
		return KEY_ID;
	}

	public static void setKEY_ID(String key_id) {
		KEY_ID = key_id;
	}

//	public static String getOPERATE_USER() {
//		return OPERATE_USER;
//	}
//
//	public static void setOPERATE_USER(String oPERATE_USER) {
//		OPERATE_USER = oPERATE_USER;
//	}

	public static String getOPERATE_TIME() {
		return OPERATE_TIME;
	}

	public static void setOPERATE_TIME(String oPERATE_TIME) {
		OPERATE_TIME = oPERATE_TIME;
	}

//	public static String getSTATE() {
//		return STATE;
//	}
//
//	public static void setSTATE(String sTATE) {
//		STATE = sTATE;
//	}

	public static String getLOGINED() {
		return LOGINED;
	}

	public static void setLOGINED(String lOGINED) {
		LOGINED = lOGINED;
	}

	public static String getPASSWORD1() {
		return PASSWORD1;
	}

	public static void setPASSWORD1(String pASSWORD1) {
		PASSWORD1 = pASSWORD1;
	}

	public static String getPASSWORD2() {
		return PASSWORD2;
	}

	public static void setPASSWORD2(String pASSWORD2) {
		PASSWORD2 = pASSWORD2;
	}

	public static String getPASSWORD1MD5() {
		return PASSWORD1MD5;
	}

	public static void setPASSWORD1MD5(String pASSWORD1MD5) {
		PASSWORD1MD5 = pASSWORD1MD5;
	}

	public static String getPASSWORD2MD5() {
		return PASSWORD2MD5;
	}

	public static void setPASSWORD2MD5(String pASSWORD2MD5) {
		PASSWORD2MD5 = pASSWORD2MD5;
	}

	public static String getCURRENTPASSWORD() {
		return CURRENTPASSWORD;
	}

	public static void setCURRENTPASSWORD(String cURRENTPASSWORD) {
		CURRENTPASSWORD = cURRENTPASSWORD;
	}
}
