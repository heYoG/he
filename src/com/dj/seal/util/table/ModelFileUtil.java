package com.dj.seal.util.table;

/**
 * 
 * @description 模板表
 * @author zyl
 * @since 2013-05-09
 * 
 */

public class ModelFileUtil {

	public static String TABLE_NAME = "T_IC"; // 表名
	public static String MODEL_ID = "C_IA";// 模板ID
	public static String MODEL_NAME = "C_IB";// 模板名称
	public static String CONTENT_DATA = "C_IC";// 模板base64值
	public static String FIELD_DATA = "C_ID";// 模板里区域的base64值
	public static String CREATE_TIME = "C_IE";// 创建时间
	public static String MODIFY_TIME = "C_IF";// 最后修改时间
	public static String CREATE_USER = "C_IH";// 创建人
	public static String MODEL_STATE = "C_II";// 模板状态 1激活 2注销
	public static String ISHOTEL = "C_ISHOTEL";// 是否无纸化系统模板 0或空为否 1为是
	public static String MULTIPART = "C_MULTIPART";// 是否套打 0 否 1 是
	public static String PRINTOREDIT = "C_PRINTOREDIT";// 模版用户打印或者编辑，也可都用于（1
														// 全包含，2打印，3编辑）
	public static String ISFLOW = "ISFLOW";// 是否需要进行二次签字 0或空为否 1为是
	public static String PAGE_NO = "PAGE_NO";// 二次签字位置页码
	public static String X_POSITION = "X_POSITION";// 二次签字位置X坐标
	public static String Y_POSITION = "Y_POSITION";// 二次签字位置Y坐标
	public static String AREA_WIDTH = "AREA_WIDTH";// 二次签字位置宽度
	public static String AREA_HEIGHT = "AREA_HEIGHT";// 二次签字位置高度
	public static String AREA_NAME = "AREA_NAME";// 二次签字区域名称
	public static String DEPT_NO = "dept_no";// 模版所属部门
	public static String DEPT_NAME = "dept_name";//部门名称
	public static String IDENTITYCARD = "IDENTITYCARD";// 身份证是否是必填项，1 必填
	public static String APPROVER = "APPROVER";// 审批人
	public static String APPROVE_STATUS = "approve_status";// 审批状态
	public static String APPROVE_TIME = "approve_time";// 审批时间
	public static String APPROVE_REASON = "APPROVE_REASON";// 审批原因
	public static String USE_RANGE = "use_range";// 模版使用范围（保留字段）
	public static String MODELORXIEYI = "modelorxieyi";// 模版还是协议 0是模板 1是协议

	public static String MODELWIDTH = "modelwidth";// 模板宽度mm计算
	public static String MODELHEIGHT = "modelheight";// 模板高度mm计算

	public static String MODELCHANGEX = "modelchagex";// 打印内容左偏移量
	public static String MODELCHANGEY = "modelchagey";// 打印内容右偏移量

	public static String PRINTSIZE_WIDTH = "printsize_width";// 打印纸张的宽（范围值）
	public static String PRINTSIZE_HEIGHT = "printsize_height";// 打印纸张的高（范围值）

	public static String MODEL_XTYPE = "model_xtype";// x轴偏移方式
	public static String MODEL_YTYPE = "model_ytype";// y轴偏移方式
	public static String MODEL_VERSION = "model_version"; // 模板版本

	public static String getPRINTSIZE_HEIGHT() {
		return PRINTSIZE_HEIGHT;
	}

	public static void setPRINTSIZE_HEIGHT(String pRINTSIZE_HEIGHT) {
		PRINTSIZE_HEIGHT = pRINTSIZE_HEIGHT;
	}

	public static String getPRINTSIZE_WIDTH() {
		return PRINTSIZE_WIDTH;
	}

	public static void setPRINTSIZE_WIDTH(String pRINTSIZE_WIDTH) {
		PRINTSIZE_WIDTH = pRINTSIZE_WIDTH;
	}

	public static String getMODEL_XTYPE() {
		return MODEL_XTYPE;
	}

	public static void setMODEL_XTYPE(String mODEL_XTYPE) {
		MODEL_XTYPE = mODEL_XTYPE;
	}

	public static String getMODEL_YTYPE() {
		return MODEL_YTYPE;
	}

	public static void setMODEL_YTYPE(String mODEL_YTYPE) {
		MODEL_YTYPE = mODEL_YTYPE;
	}

	public static String getMODELCHANGEX() {
		return MODELCHANGEX;
	}

	public static void setMODELCHANGEX(String mODELCHANGEX) {
		MODELCHANGEX = mODELCHANGEX;
	}

	public static String getMODELCHANGEY() {
		return MODELCHANGEY;
	}

	public static void setMODELCHANGEY(String mODELCHANGEY) {
		MODELCHANGEY = mODELCHANGEY;
	}

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int,");
		sb.append(MODEL_NAME).append(" varchar(200),");
		sb.append(CONTENT_DATA).append("  clob,");
		sb.append(FIELD_DATA).append("  clob,");
		sb.append(CREATE_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(MODIFY_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(CREATE_USER).append(" varchar(50),");
		sb.append(MODEL_STATE).append(" varchar(20),");
		sb.append(ISHOTEL).append(" varchar(20),");
		sb.append(MULTIPART).append(" varchar(20),");
		sb.append(PRINTOREDIT).append(" varchar(20),");
		sb.append(ISFLOW).append(" varchar(20),");
		sb.append(PAGE_NO).append(" varchar(20),");
		sb.append(X_POSITION).append(" varchar(20),");
		sb.append(Y_POSITION).append(" varchar(20),");
		sb.append(AREA_WIDTH).append(" varchar(20),");
		sb.append(AREA_HEIGHT).append(" varchar(20),");
		sb.append(AREA_NAME).append(" varchar(30),");
		sb.append(DEPT_NO).append(" varchar(200),");
		sb.append(DEPT_NAME).append(" varchar(200),");
		sb.append(IDENTITYCARD).append(" varchar(1),");
		sb.append(APPROVER).append(" varchar(20),");
		sb.append(APPROVE_STATUS).append(" varchar(1),");
		sb.append(APPROVE_TIME)
				.append(" date default to_date('1970-01-01 08:00:00','yyyy-mm-dd hh24:mi:ss'),");
		sb.append(APPROVE_REASON).append(" varchar(200),");
		sb.append(USE_RANGE).append(" varchar(1),");
		sb.append(MODELORXIEYI).append(" varchar(1),");
		sb.append(MODELWIDTH).append(" int(11),");
		sb.append(MODELHEIGHT).append(" int(11),");
		sb.append(MODELCHANGEX).append(" varchar(11),");
		sb.append(MODELCHANGEY).append(" varchar(11),");
		sb.append(PRINTSIZE_HEIGHT).append(" varchar(20),");
		sb.append(PRINTSIZE_WIDTH).append(" varchar(20)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	public static String createSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表名
		sb.append(MODEL_ID).append(" int(11),");
		sb.append(MODEL_NAME).append(" varchar(200),");
		sb.append(CONTENT_DATA).append("  mediumText,");
		sb.append(FIELD_DATA).append("  mediumText,");
		sb.append(CREATE_TIME).append(
				" datetime default '1970-01-01 08:00:00',");
		sb.append(MODIFY_TIME).append(
				" datetime default '1970-01-01 08:00:00',");
		sb.append(CREATE_USER).append(" varchar(50),");
		sb.append(MODEL_STATE).append(" varchar(20),");
		sb.append(ISHOTEL).append(" varchar(20),");
		sb.append(MULTIPART).append(" varchar(20),");
		sb.append(PRINTOREDIT).append(" varchar(20),");
		sb.append(ISFLOW).append(" varchar(20),");
		sb.append(PAGE_NO).append(" varchar(20),");
		sb.append(X_POSITION).append(" varchar(20),");
		sb.append(Y_POSITION).append(" varchar(20),");
		sb.append(AREA_WIDTH).append(" varchar(20),");
		sb.append(AREA_HEIGHT).append(" varchar(20),");
		sb.append(AREA_NAME).append(" varchar(30),");

		sb.append(DEPT_NO).append(" varchar(200),");
		sb.append(DEPT_NAME).append(" varchar(200),");
		sb.append(IDENTITYCARD).append(" varchar(1),");
		sb.append(APPROVER).append(" varchar(20),");
		sb.append(APPROVE_STATUS).append(" varchar(1),");
		sb.append(APPROVE_TIME).append(
				" datetime default '1970-01-01 08:00:00',");
		sb.append(APPROVE_REASON).append(" varchar(200),");
		sb.append(USE_RANGE).append(" varchar(1),");
		sb.append(MODELORXIEYI).append(" varchar(1),");
		sb.append(MODELWIDTH).append(" int(11),");
		sb.append(MODELHEIGHT).append(" int(11),");
		sb.append(MODELCHANGEX).append(" varchar(11),");
		sb.append(MODELCHANGEY).append(" varchar(11),");
		sb.append(PRINTSIZE_HEIGHT).append(" varchar(20),");
		sb.append(PRINTSIZE_WIDTH).append(" varchar(20),");
		sb.append(MODEL_XTYPE).append(" varchar(10),");
		sb.append(MODEL_YTYPE).append(" varchar(10)");
		sb.append(")");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String table_name) {
		TABLE_NAME = table_name;
	}

	public static String getMODEL_ID() {
		return MODEL_ID;
	}

	public static void setMODEL_ID(String model_id) {
		MODEL_ID = model_id;
	}

	public static String getMODEL_NAME() {
		return MODEL_NAME;
	}

	public static void setMODEL_NAME(String model_name) {
		MODEL_NAME = model_name;
	}

	public static String getCONTENT_DATA() {
		return CONTENT_DATA;
	}

	public static void setCONTENT_DATA(String content_data) {
		CONTENT_DATA = content_data;
	}

	public static String getFIELD_DATA() {
		return FIELD_DATA;
	}

	public static void setFIELD_DATA(String field_data) {
		FIELD_DATA = field_data;
	}

	public static String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public static void setCREATE_TIME(String create_time) {
		CREATE_TIME = create_time;
	}

	public static String getMODIFY_TIME() {
		return MODIFY_TIME;
	}

	public static void setMODIFY_TIME(String modify_time) {
		MODIFY_TIME = modify_time;
	}

	public static String getCREATE_USER() {
		return CREATE_USER;
	}

	public static void setCREATE_USER(String create_user) {
		CREATE_USER = create_user;
	}

	public static String getMODEL_STATE() {
		return MODEL_STATE;
	}

	public static void setMODEL_STATE(String model_state) {
		MODEL_STATE = model_state;
	}

	public static String getMULTIPART() {
		return MULTIPART;
	}

	public static void setMULTIPART(String multipart) {
		MULTIPART = multipart;
	}

	public static String getPRINTOREDIT() {
		return PRINTOREDIT;
	}

	public static void setPRINTOREDIT(String printoredit) {
		PRINTOREDIT = printoredit;
	}

	public static String getISHOTEL() {
		return ISHOTEL;
	}

	public static void setISHOTEL(String ishotel) {
		ISHOTEL = ishotel;
	}

	public static String getISFLOW() {
		return ISFLOW;
	}

	public static void setISFLOW(String isflow) {
		ISFLOW = isflow;
	}

	public static String getPAGE_NO() {
		return PAGE_NO;
	}

	public static void setPAGE_NO(String page_no) {
		PAGE_NO = page_no;
	}

	public static String getX_POSITION() {
		return X_POSITION;
	}

	public static void setX_POSITION(String x_position) {
		X_POSITION = x_position;
	}

	public static String getY_POSITION() {
		return Y_POSITION;
	}

	public static void setY_POSITION(String y_position) {
		Y_POSITION = y_position;
	}

	public static String getAREA_WIDTH() {
		return AREA_WIDTH;
	}

	public static void setAREA_WIDTH(String area_width) {
		AREA_WIDTH = area_width;
	}

	public static String getAREA_HEIGHT() {
		return AREA_HEIGHT;
	}

	public static void setAREA_HEIGHT(String area_height) {
		AREA_HEIGHT = area_height;
	}

	public static String getAREA_NAME() {
		return AREA_NAME;
	}

	public static void setAREA_NAME(String area_name) {
		AREA_NAME = area_name;
	}

	public static String getDEPT_NO() {
		return DEPT_NO;
	}

	public static void setDEPT_NO(String dept_no) {
		DEPT_NO = dept_no;
	}

	public static String getIDENTITYCARD() {
		return IDENTITYCARD;
	}

	public static void setIDENTITYCARD(String identitycard) {
		IDENTITYCARD = identitycard;
	}

	public static String getAPPROVER() {
		return APPROVER;
	}

	public static void setAPPROVER(String approver) {
		APPROVER = approver;
	}

	public static String getAPPROVE_STATUS() {
		return APPROVE_STATUS;
	}

	public static void setAPPROVE_STATUS(String approve_status) {
		APPROVE_STATUS = approve_status;
	}

	public static String getAPPROVE_TIME() {
		return APPROVE_TIME;
	}

	public static void setAPPROVE_TIME(String approve_time) {
		APPROVE_TIME = approve_time;
	}

	public static String getAPPROVE_REASON() {
		return APPROVE_REASON;
	}

	public static void setAPPROVE_REASON(String aPPROVE_REASON) {
		APPROVE_REASON = aPPROVE_REASON;
	}

	public static String getUSE_RANGE() {
		return USE_RANGE;
	}

	public static void setUSE_RANGE(String use_range) {
		USE_RANGE = use_range;
	}

	public static String getMODELORXIEYI() {
		return MODELORXIEYI;
	}

	public static void setMODELORXIEYI(String modelorxieyi) {
		MODELORXIEYI = modelorxieyi;
	}

	public static String getDEPT_NAME() {
		return DEPT_NAME;
	}

	public static void setDEPT_NAME(String dept_name) {
		DEPT_NAME = dept_name;
	}

	public static String getMODELHEIGHT() {
		return MODELHEIGHT;
	}

	public static void setMODELHEIGHT(String mODELHEIGHT) {
		MODELHEIGHT = mODELHEIGHT;
	}

	public static String getMODELWIDTH() {
		return MODELWIDTH;
	}

	public static void setMODELWIDTH(String mODELWIDTH) {
		MODELWIDTH = mODELWIDTH;
	}

	public static String getMODEL_VERSION() {
		return MODEL_VERSION;
	}

	public static void setMODEL_VERSION(String mODELVERSION) {
		MODEL_VERSION = mODELVERSION;
	}

}
