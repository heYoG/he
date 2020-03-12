package com.dj.seal.util.table;

/**
 * 
 * @description 印章表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class SealBodyUtil {
	public static String TABLE_NAME = "T_BB"; // 表名
	public static String SEAL_ID = "C_BBA"; // 印章ID
	public static String TEMP_ID = "C_BBB"; // 外键 印模ID
	public static String DEPT_NO = "C_BBC"; // 外键 部门编号
	public static String SEAL_NAME = "C_BBD"; // 印章名，唯一
	public static String SEAL_TYPE = "C_BBE"; // 印章类型
	public static String SEAL_DATA = "C_BBF"; // 印章数据
	public static String ROLE_LIST = "C_BBG"; // 印章授权角色列表
	public static String USER_LIST = "C_BBH"; // 印章授权用户列表
	public static String SEAL_CREATOR = "C_BBI"; // 外键 制章人
	public static String CREATE_TIME = "C_BBJ"; // 制章时间
	public static String SEAL_STATUS = "C_BBK"; // 印章状态,4-待制章,5-已制章
	public static String CLIENT_SYSTEM = "C_BBL"; // 客户端软件
	public static String USER_APPLY = "C_BBQ"; // 待审批用户列表
	public static String IS_FLOW = "C_BBM"; // 是否需要走印章使用申请流程
	public static String APPROVE_BEGINTIME = "C_UDH"; // 申请起始时间
	public static String APPROVE_ENDTIME = "C_UDE"; // 申请结束时间
	public static String KEY_SN = "C_ACE"; // 证书号
	public static String APPLY_USER = "U_APPLY"; // 申请制作印章的人
	public static String SEAL_APPLYTIME = "C_BAN"; // 申请时间
	public static String TEMP_REMARK = "C_BAO"; // 备注
	public static String PREVIEW_IMG = "C_PREIMG";// 印章缩略图数据
	public static String KEY_WORDS = "C_KEYWORDS";// 关键字
	public static String ABLE_BTIME = "C_ABLEBTIME";// 印章生效时间
	public static String ABLE_ETIME = "C_ABLEETIME";// 印章失效时间
	

	public static String getABLE_BTIME() {
		return ABLE_BTIME;
	}

	public static void setABLE_BTIME(String aBLE_BTIME) {
		ABLE_BTIME = aBLE_BTIME;
	}

	public static String getABLE_ETIME() {
		return ABLE_ETIME;
	}

	public static void setABLE_ETIME(String aBLE_ETIME) {
		ABLE_ETIME = aBLE_ETIME;
	}

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(SEAL_ID).append(" int,");
		sb.append(TEMP_ID).append(" int,");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(40),");
		sb.append(SEAL_DATA).append(" clob,");
		sb.append(ROLE_LIST).append(" clob,");
		sb.append(USER_LIST).append(" clob,");
		sb.append(SEAL_CREATOR).append(" varchar(40),");
		sb.append(CREATE_TIME)
				.append("  date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(SEAL_STATUS).append(" varchar(20) default '1',");
		sb.append(CLIENT_SYSTEM).append(" varchar(255),");
		sb.append(USER_APPLY).append(" clob,");
		sb.append(IS_FLOW).append(" varchar(20),");
		sb.append(APPROVE_BEGINTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(APPROVE_ENDTIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(KEY_SN).append(" varchar(100),");
		sb.append(APPLY_USER).append(" varchar(50),");
		sb.append(SEAL_APPLYTIME)
				.append("  date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(TEMP_REMARK).append(" varchar(200),");
		sb.append(PREVIEW_IMG).append(" clob ,");
		sb.append(KEY_WORDS).append(" varchar(255)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table "
				+ TABLE_NAME
				+ " ("
				+ SEAL_ID
				+ " int(11) auto_increment primary key,"
				+ TEMP_ID
				+ " int(11) not null,"// //foreign key references
				// "+SealTemplate.TABLE_NAME+"("+SealTemplate.TEMP_ID+"),"
				+ DEPT_NO
				+ " varchar(100) not null,"// foreign key references
				// "+SysDepartment.TABLE_NAME+"("+SysDepartment.DEPT_NO+"),"
				+ SEAL_NAME + " varchar(200) not null," + SEAL_TYPE
				+ " varchar(40) not null," + SEAL_DATA
				+ " mediumtext not null," + ROLE_LIST + " text," + USER_LIST
				+ " text," + SEAL_CREATOR + " varchar(40) not null,"
				+ CREATE_TIME
				+ " datetime default '1970-01-01 08:00:00' not null,"
				+ SEAL_STATUS + " varchar(20) default '1' not null,"
				+ CLIENT_SYSTEM + " varchar(255)," + USER_APPLY + " text,"
				+ IS_FLOW + " varchar(20)," + APPROVE_BEGINTIME
				+ " datetime default '1970-01-01 08:00:00' not null,"
				+ APPROVE_ENDTIME
				+ " datetime default '1970-01-01 08:00:00' not null," + KEY_SN
				+ " varchar(100) ," + APPLY_USER + " varchar(100) not null,"
				+ SEAL_APPLYTIME
				+ " datetime default '1970-01-01 08:00:00' not null,"
				+ TEMP_REMARK + " varchar(200), " + PREVIEW_IMG
				+ " mediumtext not null ," + KEY_WORDS + " varchar(255))";
		System.out.println(create_sql + ";");
		return create_sql;
	}

	/**
	 * @description 返回建印章表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(SEAL_ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(TEMP_ID).append(" int,");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(40),");
		sb.append(SEAL_DATA).append(" clob,");
		sb.append(ROLE_LIST).append(" clob,");
		sb.append(USER_LIST).append(" clob,");
		sb.append(SEAL_CREATOR).append(" varchar(40),");
		sb.append(CREATE_TIME).append(" timestamp,");
		sb.append(SEAL_STATUS).append(" varchar(20),");
		sb.append(CLIENT_SYSTEM).append(" varchar(255),");
		sb.append(USER_APPLY).append(" clob,");
		sb.append(IS_FLOW).append(" varchar(20),");
		sb.append(APPROVE_BEGINTIME).append(" timestamp,");
		sb.append(APPROVE_ENDTIME).append(" timestamp,");
		sb.append(KEY_SN).append(" varchar(100),");
		sb.append(APPLY_USER).append(" varchar(100),");
		sb.append(SEAL_APPLYTIME).append(" timestamp,");
		sb.append(TEMP_REMARK).append(" varchar(200)");
		sb.append(PREVIEW_IMG).append(" clob, ");
		sb.append(KEY_WORDS).append(" varchar(255)");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @description 返回删除印章表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getIS_FLOW() {
		return IS_FLOW;
	}

	public static String getUSER_APPLY() {
		return USER_APPLY;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getSEAL_ID() {
		return SEAL_ID;
	}

	public static String getTEMP_ID() {
		return TEMP_ID;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static String getSEAL_TYPE() {
		return SEAL_TYPE;
	}

	public static String getSEAL_DATA() {
		return SEAL_DATA;
	}

	public static String getROLE_LIST() {
		return ROLE_LIST;
	}

	public static String getUSER_LIST() {
		return USER_LIST;
	}

	public static String getSEAL_CREATOR() {
		return SEAL_CREATOR;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public static String getSEAL_STATUS() {
		return SEAL_STATUS;
	}

	public static String getCLIENT_SYSTEM() {
		return CLIENT_SYSTEM;
	}

	public static String getAPPROVE_BEGINTIME() {
		return APPROVE_BEGINTIME;
	}

	public static String getAPPROVE_ENDTIME() {
		return APPROVE_ENDTIME;
	}

	public static String getKEY_SN() {
		return KEY_SN;
	}

	public static String getAPPLY_USER() {
		return APPLY_USER;
	}

	public static String getSEAL_APPLYTIME() {
		return SEAL_APPLYTIME;
	}

	public static String getTEMP_REMARK() {
		return TEMP_REMARK;
	}

	public static void setTEMP_REMARK(String temp_remark) {
		TEMP_REMARK = temp_remark;
	}

	public static String getPREVIEW_IMG() {
		return PREVIEW_IMG;
	}

	public static void setPREVIEW_IMG(String preview_img) {
		PREVIEW_IMG = preview_img;
	}

	public static String getKEY_WORDS() {
		return KEY_WORDS;
	}

	public static void setKEY_WORDS(String key_words) {
		KEY_WORDS = key_words;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static void setSEAL_ID(String seal_id) {
		SEAL_ID = seal_id;
	}

	public static void setTEMP_ID(String temp_id) {
		TEMP_ID = temp_id;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static void setSEAL_NAME(String seal_name) {
		SEAL_NAME = seal_name;
	}

	public static void setSEAL_TYPE(String seal_type) {
		SEAL_TYPE = seal_type;
	}

	public static void setSEAL_DATA(String seal_data) {
		SEAL_DATA = seal_data;
	}

	public static void setROLE_LIST(String role_list) {
		ROLE_LIST = role_list;
	}

	public static void setUSER_LIST(String user_list) {
		USER_LIST = user_list;
	}

	public static void setSEAL_CREATOR(String seal_creator) {
		SEAL_CREATOR = seal_creator;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}

	public static void setSEAL_STATUS(String seal_status) {
		SEAL_STATUS = seal_status;
	}

	public static void setCLIENT_SYSTEM(String client_system) {
		CLIENT_SYSTEM = client_system;
	}

	public static void setUSER_APPLY(String user_apply) {
		USER_APPLY = user_apply;
	}

	public static void setIS_FLOW(String is_flow) {
		IS_FLOW = is_flow;
	}

	public static void setAPPROVE_BEGINTIME(String approve_begintime) {
		APPROVE_BEGINTIME = approve_begintime;
	}

	public static void setAPPROVE_ENDTIME(String approve_endtime) {
		APPROVE_ENDTIME = approve_endtime;
	}

	public static void setKEY_SN(String key_sn) {
		KEY_SN = key_sn;
	}

	public static void setAPPLY_USER(String apply_user) {
		APPLY_USER = apply_user;
	}

	public static void setSEAL_APPLYTIME(String seal_applytime) {
		SEAL_APPLYTIME = seal_applytime;
	}
}
