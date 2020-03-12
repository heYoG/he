package com.dj.seal.util.table;

/**
 * 
 * @description 界面表
 * @author yc,oyxy
 * @since 2009-11-2
 * 
 */
public class ViewInterfaceUtil {
	public static String TABLE_NAME = "T_FB"; // 表名
	public static String IE_TITLE = "C_FBA"; // 浏览器标题
	public static String STATUS_TEXT = "C_FBB"; // 状态设置
	public static String BANNER_TEXT = "C_FBC"; // 顶部大标题
	public static String BANNER_FONT = "C_FBD"; // 标题字体
	public static String ATTACHMENT_ID = "C_FBE";
	public static String ATTACHMENT_NAME = "C_FBF"; // 顶部图标名
	public static String IMG_WIDTH = "C_FBG"; // 顶部图标宽度
	public static String IMG_HEIGHT = "C_FBH"; // 顶部图标高度
	public static String ATTACHMENT_ID1 = "C_FBI";
	public static String ATTACHMENT_NAME1 = "C_FBJ"; // 登录界面图片
	public static String AVATAR_UPLOAD = "C_FBK"; // 是否允许上传头像
	public static String AVATAR_WIDTH = "C_FBL"; // 头像最大宽度
	public static String AVATAR_HEIGHT = "C_FBM"; // 头像最大高度
	public static String THEME_SELECT = "C_FBN"; // 是否允许选择主题
	public static String VIEW_THEME = "C_FBO"; // 界面主题
	public static String VIEW_TEMPLATE = "C_FBP"; // 登录界面模板

	public static String createSql4oracle() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append(" (");// 表
		sb.append(IE_TITLE).append(" varchar(200),");
		sb.append(STATUS_TEXT).append(" varchar(255),");
		sb.append(BANNER_TEXT).append(" varchar(200),");
		sb.append(BANNER_FONT).append(" varchar(200),");
		sb.append(ATTACHMENT_ID).append(" varchar(200),");
		sb.append(ATTACHMENT_NAME).append(" varchar(200),");
		sb.append(IMG_WIDTH).append(" int default 110,");
		sb.append(IMG_HEIGHT).append(" int default 40,");
		sb.append(ATTACHMENT_ID1).append(" varchar(200),");
		sb.append(ATTACHMENT_NAME1).append(" varchar(200),");
		sb.append(AVATAR_UPLOAD).append(" char(1) default '1',");
		sb.append(AVATAR_WIDTH).append(" int default 20,");
		sb.append(AVATAR_HEIGHT).append(" int default 20,");
		sb.append(THEME_SELECT).append(" varchar(20) default '1',");
		sb.append(VIEW_THEME).append(" varchar(200) default '1',");
		sb.append(VIEW_TEMPLATE).append(" varchar(200)");
		sb.append(")");
		System.out.println(sb.toString()+";");
		return sb.toString();
	}

	/**
	 * @description 返回建界面表的SQL语句
	 * @return
	 */
	public static String createSql() {
		String create_sql = "create table " + TABLE_NAME + " (" + IE_TITLE
				+ " varchar(200) not null," + STATUS_TEXT
				+ " varchar(255) not null," + BANNER_TEXT
				+ " varchar(200) not null," + BANNER_FONT
				+ " varchar(200) not null," + ATTACHMENT_ID
				+ " varchar(200) not null," + ATTACHMENT_NAME
				+ " varchar(200) not null," + IMG_WIDTH
				+ " int(11) default 110 not null," + IMG_HEIGHT
				+ " int(11) default 40 not null," + ATTACHMENT_ID1
				+ " varchar(200) not null," + ATTACHMENT_NAME1
				+ " varchar(200) not null," + AVATAR_UPLOAD
				+ " char(1) default '1' not null," + AVATAR_WIDTH
				+ " int(11) default 20 not null," + AVATAR_HEIGHT
				+ " int(11) default 20 not null," + THEME_SELECT
				+ " varchar(20) default '1' not null," + VIEW_THEME
				+ " varchar(200) default '1' not null," + VIEW_TEMPLATE
				+ " varchar(200) not null"

				+ ")";
		System.out.println(create_sql+";");
		return create_sql;
	}

	/**
	 * @description 返回建界面表的SQL语句
	 * @return
	 */
	public static String createDB2Sql() {
		StringBuffer sb = new StringBuffer();
		sb.append("create table ").append(TABLE_NAME).append("(");
		sb.append(IE_TITLE).append(" varchar(200),");
		sb.append(STATUS_TEXT).append(" varchar(255),");
		sb.append(BANNER_TEXT).append(" varchar(200),");
		sb.append(BANNER_FONT).append(" varchar(200),");
		sb.append(ATTACHMENT_ID).append(" varchar(200),");
		sb.append(ATTACHMENT_NAME).append(" varchar(200),");
		sb.append(IMG_WIDTH).append(" int,");
		sb.append(IMG_HEIGHT).append(" int,");
		sb.append(ATTACHMENT_ID1).append(" varchar(200),");
		sb.append(ATTACHMENT_NAME1).append(" varchar(200),");
		sb.append(AVATAR_UPLOAD).append(" varchar(1),");
		sb.append(AVATAR_WIDTH).append(" int,");
		sb.append(AVATAR_HEIGHT).append(" int,");
		sb.append(THEME_SELECT).append(" varchar(20),");
		sb.append(VIEW_THEME).append(" varchar(200),");
		sb.append(VIEW_TEMPLATE).append(" varchar(200)");
		sb.append(")");
		return sb.toString();
	}
	public static String getTABLE_NAME() {
		return TABLE_NAME;
	}

	public static String getIE_TITLE() {
		return IE_TITLE;
	}

	public static String getSTATUS_TEXT() {
		return STATUS_TEXT;
	}

	public static String getBANNER_TEXT() {
		return BANNER_TEXT;
	}

	public static String getBANNER_FONT() {
		return BANNER_FONT;
	}

	public static String getATTACHMENT_ID() {
		return ATTACHMENT_ID;
	}

	public static String getATTACHMENT_NAME() {
		return ATTACHMENT_NAME;
	}

	public static String getIMG_WIDTH() {
		return IMG_WIDTH;
	}

	public static String getIMG_HEIGHT() {
		return IMG_HEIGHT;
	}

	public static String getATTACHMENT_ID1() {
		return ATTACHMENT_ID1;
	}

	public static String getATTACHMENT_NAME1() {
		return ATTACHMENT_NAME1;
	}

	public static String getAVATAR_UPLOAD() {
		return AVATAR_UPLOAD;
	}

	public static String getAVATAR_WIDTH() {
		return AVATAR_WIDTH;
	}

	public static String getAVATAR_HEIGHT() {
		return AVATAR_HEIGHT;
	}

	public static String getTHEME_SELECT() {
		return THEME_SELECT;
	}

	public static String getVIEW_THEME() {
		return VIEW_THEME;
	}

	public static String getVIEW_TEMPLATE() {
		return VIEW_TEMPLATE;
	}

	/**
	 * @description 返回删除界面表的SQL语句
	 * @return
	 */
	public static String dropSql() {
		return "drop table " + TABLE_NAME;
	}

}
