package com.dj.seal.util.table;

/**
 * 
 * @description 印模表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class SealTemplateUtil {

	public static String TABLE_NAME = "T_BA"; // 表名
	public static String TEMP_ID = "C_BAA"; // 印模ID
	public static String TEMP_NAME = "C_BAB"; // 印模名称，唯一
	public static String TEMP_DATA = "C_BAC"; // 印模数据
	public static String SEAL_TYPE = "C_BAD"; // 印章类型
	public static String DEPT_NO = "C_BAE"; // 外键 部门编号
	public static String IMAGE_WIDTH = "C_BAF"; // 图片宽度
	public static String IMAGE_HEIGHT = "C_BAG"; // 图片高度
	public static String SEAL_WIDTH = "C_BAH"; // 印章宽度
	public static String SEAL_HEIGHT = "C_BAI"; // 印章高度
	public static String SEAL_BIT = "C_BAJ"; // 印章大小
	public static String TEMP_STATUS = "C_BAK"; // 印模状态
	public static String IS_MAKED = "C_BAL"; // 是否已制章
	public static String TEMP_CREATOR = "C_BAM"; // 外键 创建人
	public static String CREATE_TIME = "C_BAN"; // 申请时间
	public static String TEMP_REMARK = "C_BAO"; // 备注
	public static String APPROVE_USER = "C_BAP"; // 外键 审批人
	public static String APPROVE_TIME = "C_BAQ"; // 审批时间
	public static String APPROVE_TEXT = "C_BAR"; // 审批意见
	public static String CLIENT_SYSTEM = "C_BAS"; // 客户端软件
	public static String USE_STATUS = "C_BAT"; // 印模使用状态
	public static String USER_APPLY = "U_APPLY"; // 印模申请人
	public static String USER_APPLYER = "U_APPLYER"; // 申请印章制作的人（申请）
	public static String SEAL_CREATOR = "S_CREATOR"; // 制章人
	public static String PREVIEW_IMG = "C_PREIMG";//印章缩略图数据
	
	public static String ABLE_BTIME = "C_ABLEBTIME";//印章有效期开始时间
	public static String ABLE_ETIME = "C_ABLEETIME";//印章有效期结束时间
	

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

	public static String getPREVIEW_IMG() {
		return PREVIEW_IMG;
	}

	public static void setPREVIEW_IMG(String preview_img) {
		PREVIEW_IMG = preview_img;
	}

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(TEMP_ID).append(" int,");
		sb.append(TEMP_NAME).append(" varchar(200),");
		sb.append(TEMP_DATA).append(" clob,");
		sb.append(SEAL_TYPE).append(" varchar(20),");
		sb.append(DEPT_NO).append(" varchar(100),");
		sb.append(IMAGE_WIDTH).append(" varchar(20),");
		sb.append(IMAGE_HEIGHT).append(" varchar(20),");
		sb.append(SEAL_WIDTH).append(" varchar(20),");
		sb.append(SEAL_HEIGHT).append(" varchar(20),");
		sb.append(SEAL_BIT).append(" int default 0,");
		sb.append(TEMP_STATUS).append(" char(1),");
		sb.append(IS_MAKED).append(" char(1) default '0',");
		sb.append(TEMP_CREATOR).append(" varchar(40),");
		sb
				.append(CREATE_TIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(TEMP_REMARK).append(" varchar(255),");
		sb.append(APPROVE_USER).append(" varchar(40),");
		sb
				.append(APPROVE_TIME)
				.append(
						" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(APPROVE_TEXT).append(" varchar(255),");
		sb.append(CLIENT_SYSTEM).append(" varchar(255),");
		sb.append(USE_STATUS).append(" char(1),");
		sb.append(USER_APPLY).append(" varchar(255),");
		sb.append(USER_APPLYER).append(" varchar(255),");
		sb.append(SEAL_CREATOR).append(" varchar(50),");
		sb.append(PREVIEW_IMG).append(" clob");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建印模表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + TEMP_ID
				+ " int(11) auto_increment primary key," + TEMP_NAME
				+ " varchar(200) not null unique," + TEMP_DATA
				+ " mediumtext ,"// not
				// null,"
				+ SEAL_TYPE + " varchar(20) ,"// not null,"
				+ DEPT_NO + " varchar(100) ,"// not null,"//foreign key
				// references
				// "+SysDepartment.TABLE_NAME+"("+SysDepartment.DEPT_NO+"),"
				+ IMAGE_WIDTH + " varchar(20) ,"// not null,"
				+ IMAGE_HEIGHT + " varchar(20) ,"// not null,"
				+ SEAL_WIDTH + " varchar(20) ,"// not null,"
				+ SEAL_HEIGHT + " varchar(20) ,"// not null,"
				+ SEAL_BIT + " int(4) default 0 ,"// not null,"
				+ TEMP_STATUS + " char(1) ,"// not null,"
				+ IS_MAKED + " char(1) default '0' ,"// not null,"
				+ TEMP_CREATOR + " varchar(40) ,"// not null,"//foreign key
				// references
				// "+SysUser.TABLE_NAME+"("+SysUser.USER_ID+"),"
				+ CREATE_TIME + " datetime default '1970-01-01 08:00:00' ,"// not
				// null,"
				+ TEMP_REMARK + " varchar(255) ,"// not null,"
				+ APPROVE_USER + " varchar(40) ,"// not null,"//foreign key
				// references
				// "+SysUser.TABLE_NAME+"("+SysUser.USER_ID+"),"
				+ APPROVE_TIME + " datetime default '1970-01-01 08:00:00' ,"// not
				// null,"
				+ APPROVE_TEXT + " varchar(255) ,"// not null,"
				+ CLIENT_SYSTEM + " varchar(255) ,"// not null"
				+ USE_STATUS + " char(1)," 
				+ USER_APPLY + " varchar(255)," 
				+ USER_APPLYER + " varchar(255),"
				+ SEAL_CREATOR + " varchar(50),"
				+ PREVIEW_IMG + " mediumtext)"; 
		System.out.println(create_sql+";");
		return create_sql;
	}
	/**
	 * @description 返回建印模表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(TEMP_ID).append(
				" int GENERATED ALWAYS AS IDENTITY primary key,");
		sb.append(TEMP_NAME).append(" varchar(200),");
		sb.append(TEMP_DATA).append(" clob,");
		sb.append(SEAL_TYPE).append(" varchar(20),");
		sb.append(DEPT_NO).append(" varchar(200),");
		sb.append(IMAGE_WIDTH).append(" varchar(20),");
		sb.append(IMAGE_HEIGHT).append(" varchar(20),");
		sb.append(SEAL_WIDTH).append(" varchar(20),");
		sb.append(SEAL_HEIGHT).append(" varchar(20),");
		sb.append(SEAL_BIT).append(" int,");
		sb.append(TEMP_STATUS).append(" varchar(1) ,");
		sb.append(IS_MAKED).append(" varchar(1),");
		sb.append(TEMP_CREATOR).append(" varchar(40) ,");
		sb.append(CREATE_TIME).append(" timestamp,");
		sb.append(TEMP_REMARK).append(" varchar(255) ,");
		sb.append(APPROVE_USER).append(" varchar(40) ,");
		sb.append(APPROVE_TIME).append(" timestamp,");
		sb.append(APPROVE_TEXT).append(" varchar(255) ,");
		sb.append(CLIENT_SYSTEM).append(" varchar(255) ,");
		sb.append(USE_STATUS).append(" varchar(1),");
		sb.append(USER_APPLY).append(" varchar(255) ,");
		sb.append(USER_APPLYER).append(" varchar(255) ,");
		sb.append(SEAL_CREATOR).append(" varchar(50) ,");
		sb.append(PREVIEW_IMG).append(" clob ");
		sb.append(")");
		return sb.toString();
	}

	/**
	 * @description 返回删除印模表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static void setUSE_STATUS(String use_status) {
		USE_STATUS = use_status;
	}

	public static String getUSE_STATUS() {
		return USE_STATUS;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getTEMP_ID() {
		return TEMP_ID;
	}

	public static void setTEMP_ID(String temp_id) {
		TEMP_ID = temp_id;
	}

	public static String getTEMP_NAME() {
		return TEMP_NAME;
	}

	public static void setTEMP_NAME(String temp_name) {
		TEMP_NAME = temp_name;
	}

	public static String getTEMP_DATA() {
		return TEMP_DATA;
	}

	public static void setTEMP_DATA(String temp_data) {
		TEMP_DATA = temp_data;
	}

	public static String getSEAL_TYPE() {
		return SEAL_TYPE;
	}

	public static void setSEAL_TYPE(String seal_type) {
		SEAL_TYPE = seal_type;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static String getIMAGE_WIDTH() {
		return IMAGE_WIDTH;
	}

	public static void setIMAGE_WIDTH(String image_width) {
		IMAGE_WIDTH = image_width;
	}

	public static String getIMAGE_HEIGHT() {
		return IMAGE_HEIGHT;
	}

	public static void setIMAGE_HEIGHT(String image_height) {
		IMAGE_HEIGHT = image_height;
	}

	public static String getSEAL_WIDTH() {
		return SEAL_WIDTH;
	}

	public static void setSEAL_WIDTH(String seal_width) {
		SEAL_WIDTH = seal_width;
	}

	public static String getSEAL_HEIGHT() {
		return SEAL_HEIGHT;
	}

	public static void setSEAL_HEIGHT(String seal_height) {
		SEAL_HEIGHT = seal_height;
	}

	public static String getSEAL_BIT() {
		return SEAL_BIT;
	}

	public static void setSEAL_BIT(String seal_bit) {
		SEAL_BIT = seal_bit;
	}

	public static String getTEMP_STATUS() {
		return TEMP_STATUS;
	}

	public static void setTEMP_STATUS(String temp_status) {
		TEMP_STATUS = temp_status;
	}

	public static String getIS_MAKED() {
		return IS_MAKED;
	}

	public static void setIS_MAKED(String is_maked) {
		IS_MAKED = is_maked;
	}

	public static String getTEMP_CREATOR() {
		return TEMP_CREATOR;
	}

	public static void setTEMP_CREATOR(String temp_creator) {
		TEMP_CREATOR = temp_creator;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}

	public static String getTEMP_REMARK() {
		return TEMP_REMARK;
	}

	public static void setTEMP_REMARK(String temp_remark) {
		TEMP_REMARK = temp_remark;
	}

	public static String getAPPROVE_USER() {
		return APPROVE_USER;
	}

	public static void setAPPROVE_USER(String approve_user) {
		APPROVE_USER = approve_user;
	}

	public static String getAPPROVE_TIME() {
		return APPROVE_TIME;
	}

	public static void setAPPROVE_TIME(String approve_time) {
		APPROVE_TIME = approve_time;
	}

	public static String getAPPROVE_TEXT() {
		return APPROVE_TEXT;
	}

	public static void setAPPROVE_TEXT(String approve_text) {
		APPROVE_TEXT = approve_text;
	}

	public static String getCLIENT_SYSTEM() {
		return CLIENT_SYSTEM;
	}

	public static void setCLIENT_SYSTEM(String client_system) {
		CLIENT_SYSTEM = client_system;
	}

	public static String getUSER_APPLY() {
		return USER_APPLY;
	}

	public static void setUSER_APPLY(String user_apply) {
		USER_APPLY = user_apply;
	}

	public static String getUSER_APPLYER() {
		return USER_APPLYER;
	}

	public static void setUSER_APPLYER(String user_applyer) {
		USER_APPLYER = user_applyer;
	}

	public static String getSEAL_CREATOR() {
		return SEAL_CREATOR;
	}

	public static void setSEAL_CREATOR(String seal_creator) {
		SEAL_CREATOR = seal_creator;
	}
}
