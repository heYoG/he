package com.dj.hotelApi.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.dj.seal.util.table.HotelRecordContent;
import com.dj.seal.util.table.HotelRecordUtil;
import com.dj.seal.util.table.HotelTClientOperLogUtil;
import com.dj.seal.util.table.HotelTRecordAffiliatedUtil;
import com.neusoft.util.table.HotelRecordRoomMergeUtil;

public class TableNameUtil {

	public static String recordTableName="";//单据表表名
	public static String recordAffiliatedTableName="";//单据属性表表名
	public static String recordContentTableName="";//单据内容表表名
	public static String clientOperLogTableName="";//无纸化操作日志表名
	public static String recordRoomMergeTableName = ""; // 房间单据合并表名
	
	static{
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		recordTableName = HotelRecordUtil.TABLE_NAME+yuestr;
		recordContentTableName = HotelRecordContent.TABLE_NAME+yuestr;
		recordAffiliatedTableName = HotelTRecordAffiliatedUtil.TABLE_NAME+yuestr;
		clientOperLogTableName = HotelTClientOperLogUtil.TABLE_NAME+yuestr;
		recordRoomMergeTableName = HotelRecordRoomMergeUtil.TABLE_NAME + yuestr;
		System.out.println("recordTableName:"+recordTableName);
		System.out.println("recordAffiliatedTableName:"+recordAffiliatedTableName);
		System.out.println("clientOperLogTableName:"+clientOperLogTableName);
		System.out.println("recordContentTableName"+recordContentTableName);
	}
	
	
	public static void updateTableName(String yuestr){
		recordTableName = HotelRecordUtil.TABLE_NAME+yuestr;
		recordAffiliatedTableName = HotelTRecordAffiliatedUtil.TABLE_NAME+yuestr;
		clientOperLogTableName = HotelTClientOperLogUtil.TABLE_NAME+yuestr;
		recordContentTableName=HotelRecordContent.TABLE_NAME+yuestr;
		recordRoomMergeTableName = HotelRecordRoomMergeUtil.TABLE_NAME + yuestr;
	}
	
	public static void updateTableName(){
		Date nowdate = new Date();
		SimpleDateFormat new_formatter = new SimpleDateFormat("yyyyMM");
		String yuestr = new_formatter.format(nowdate);
		recordTableName = HotelRecordUtil.TABLE_NAME+yuestr;
		recordAffiliatedTableName = HotelTRecordAffiliatedUtil.TABLE_NAME+yuestr;
		clientOperLogTableName = HotelTClientOperLogUtil.TABLE_NAME+yuestr;
		recordRoomMergeTableName = HotelRecordRoomMergeUtil.TABLE_NAME + yuestr;
	}
}
