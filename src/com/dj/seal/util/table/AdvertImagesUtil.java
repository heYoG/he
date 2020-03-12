package com.dj.seal.util.table;

/**
 * 广告图片表
 * @author WB000520
 *
 */
public class AdvertImagesUtil {
	public static String TABLE_NAME = "T_ADVERTIMAGES";// 表名
	public static String AI_ID = "AI_ID"; // 主键ID
	public static String AI_IMAGENAME = "AI_IMAGENAME";// 广告图片名
	public static String AI_IMAGEDATA = "AI_IMAGEDATA";// 广告图片数据
	public static String AD_ID = "AD_ID";// 广告标题

	/**
	 * @description 返回oracle建表语句
	 */
	public static String createSql4Oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(AI_ID).append(" varchar(255) ,");
		sb.append(AI_IMAGENAME).append(" varchar(255) ,");
		sb.append(AI_IMAGEDATA).append(" clob ,");
		sb.append(AD_ID).append(" varchar(255) ,");
		sb.append(" )");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回mysql建表语句
	 */
	public static String createSql4MySql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(AI_ID).append(" varchar(255) ,");
		sb.append(AI_IMAGENAME).append(" varchar(255) ,");
		sb.append(AI_IMAGEDATA).append(" clob ,");
		sb.append(AD_ID).append(" varchar(255) ,");
		sb.append(" )");
		System.out.println(sb.toString() + ";");
		return sb.toString();
	}

	/**
	 * @description 返回删除表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

	/**
	 * @description 返回DB2建表语句
	 */
	public static String createSql4DB2() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");
		sb.append(AI_ID).append(" varchar(255) ,");
		sb.append(AI_IMAGENAME).append(" varchar(255) ,");
		sb.append(AI_IMAGEDATA).append(" clob ,");
		sb.append(AD_ID).append(" varchar(255) ,");
		sb.append(" )");
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(createSql4Oracle());
	}

	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static void setTABLE_NAME(String tABLE_NAME) {
		TABLE_NAME = tABLE_NAME;
	}

	public static String getAI_ID() {
		return AI_ID;
	}

	public static void setAI_ID(String aI_ID) {
		AI_ID = aI_ID;
	}

	public static String getAI_IMAGENAME() {
		return AI_IMAGENAME;
	}

	public static void setAI_IMAGENAME(String aI_IMAGENAME) {
		AI_IMAGENAME = aI_IMAGENAME;
	}

	public static String getAI_IMAGEDATA() {
		return AI_IMAGEDATA;
	}

	public static void setAI_IMAGEDATA(String aI_IMAGEDATA) {
		AI_IMAGEDATA = aI_IMAGEDATA;
	}

	public static String getAD_ID() {
		return AD_ID;
	}

	public static void setAD_ID(String aD_ID) {
		AD_ID = aD_ID;
	}

}
