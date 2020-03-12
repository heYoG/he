package com.dj.seal.util.table;

/**
 * 
 * @description 印章备份表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class SealBodyBakUtil {
	public static String TABLE_NAME = "T_BB_BAK"; // 表名
	public static String SEAL_ID = "C_BBA"; // 印章ID
	public static String DEPT_NAME = "C_BBC"; // 外键 部门名称
	public static String SEAL_NAME = "C_BBD"; // 印章名，唯一
	public static String SEAL_TYPE = "C_BBE"; // 印章类型
	public static String SEAL_CREATOR = "C_BBI"; // 外键 制章人
	public static String CREATE_TIME = "C_BBJ"; // 制章时间
	public static String SEAL_CZID="SEAL_CZID";//财政sealID
	public static String SEAL_BASE64="SEAL_BASE64";//财政base64值
	
	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(SEAL_ID).append(" int,");
		sb.append(DEPT_NAME).append(" varchar(100),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(40),");
		sb.append(SEAL_CREATOR).append(" varchar(40),");
		sb.append(CREATE_TIME).append("  date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(SEAL_CZID).append(" varchar(50),");
		sb.append(SEAL_BASE64).append(" clob ");
		sb.append(")");
		System.out.println(sb.toString());
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
			+ DEPT_NAME
			+ " varchar(100) not null,"// foreign key references
			// "+SysDepartment.TABLE_NAME+"("+SysDepartment.DEPT_NO+"),"
			+ SEAL_NAME + " varchar(200) not null," + SEAL_TYPE
			+ " varchar(40) not null," 
			+ SEAL_CREATOR + " varchar(40) not null,"
			+ CREATE_TIME
			+ " datetime default '1970-01-01 08:00:00' not null,"
			+ SEAL_CZID + " varchar(50),"
			+ SEAL_BASE64 + " mediumtext)";
		System.out.println(create_sql+";");
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
	
		sb.append(DEPT_NAME).append(" varchar(100),");
		sb.append(SEAL_NAME).append(" varchar(200),");
		sb.append(SEAL_TYPE).append(" varchar(40),");
		sb.append(SEAL_CREATOR).append(" varchar(40),");
		sb.append(CREATE_TIME).append(" timestamp,");
		sb.append(SEAL_CZID).append(" varchar(50),");
		sb.append(SEAL_BASE64).append(" clob ");
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


	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getSEAL_ID() {
		return SEAL_ID;
	}



	public static String getSEAL_NAME() {
		return SEAL_NAME;
	}

	public static String getSEAL_TYPE() {
		return SEAL_TYPE;
	}

	public static String getSEAL_CREATOR() {
		return SEAL_CREATOR;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}
	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static void setSEAL_ID(String seal_id) {
		SEAL_ID = seal_id;
	}
	

	public static void setSEAL_NAME(String seal_name) {
		SEAL_NAME = seal_name;
	}

	public static void setSEAL_TYPE(String seal_type) {
		SEAL_TYPE = seal_type;
	}

	public static void setSEAL_CREATOR(String seal_creator) {
		SEAL_CREATOR = seal_creator;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}
	public static String getSEAL_CZID() {
		return SEAL_CZID;
	}

	public static void setSEAL_CZID(String seal_czid) {
		SEAL_CZID = seal_czid;
	}

	public static String getSEAL_BASE64() {
		return SEAL_BASE64;
	}

	public static void setSEAL_BASE64(String seal_base64) {
		SEAL_BASE64 = seal_base64;
	}

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static void setDEPT_NAME(String dept_name) {
		DEPT_NAME = dept_name;
	}
	
}
