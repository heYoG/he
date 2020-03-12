package com.dj.seal.util.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 时间帮助类
 * 
 * @version $Id: DateUtil.java,v 1.1 2008/05/28 04:29:52 linan Exp $
 * @author LiNan
 */
public class DateUtil {
	static Logger logger = LogManager.getLogger(DateUtil.class.getName());
	private Calendar calendar = Calendar.getInstance();

	/**
	 * dateStr必需满足“yyyy-MM-dd hh:mm:ss”格式
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long getTime(String dateStr) {
		return getTime(dateStr, "yyyy-MM-dd hh:mm:ss");
	}

	/**
	 * 
	 * @param dateStr
	 * @param fmtStr
	 * @return
	 */
	public static long getTime(String dateStr, String fmtStr) {
		SimpleDateFormat df = new SimpleDateFormat(fmtStr);
		try {
			Date date = df.parse(dateStr);
			return date.getTime();
		} catch (Exception ex) {
			logger.info(ex.getMessage());
			return 0;
		}
	}

	/**
	 * 得到当前的时间，时间格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public String getCurrentDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}

	/**
	 * 得到当前的时间,自定义时间格式 y 年 M 月 d 日 H 时 m 分 s 秒
	 * 
	 * @param dateFormat
	 *            输出显示的时间格式
	 * @return
	 */
	public String getCurrentDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}

	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 日期格式化，自定义输出日期格式
	 * 
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 返回当前日期的前一个时间日期，amount为正数 当前时间后的时间 为负数 当前时间前的时间 默认日期格式yyyy-MM-dd
	 * 
	 * @param field
	 *            日历字段 y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount
	 *            数量
	 * @return 一个日期
	 */
	public String getPreDate(String field, int amount) {
		calendar.setTime(new Date());
		if (field != null && !field.equals("")) {
			if (field.equals("y")) {
				calendar.add(Calendar.YEAR, amount);
			} else if (field.equals("M")) {
				calendar.add(Calendar.MONTH, amount);
			} else if (field.equals("d")) {
				calendar.add(Calendar.DAY_OF_MONTH, amount);
			} else if (field.equals("H")) {
				calendar.add(Calendar.HOUR, amount);
			}
		} else {
			return null;
		}
		return getFormatDate(calendar.getTime());
	}

	/**
	 * 某一个日期的前一个日期
	 * 
	 * @param d,某一个日期
	 * @param field
	 *            日历字段 y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount
	 *            数量
	 * @return 一个日期
	 */
	public String getPreDate(Date d, String field, int amount) {
		calendar.setTime(d);
		if (field != null && !field.equals("")) {
			if (field.equals("y")) {
				calendar.add(Calendar.YEAR, amount);
			} else if (field.equals("M")) {
				calendar.add(Calendar.MONTH, amount);
			} else if (field.equals("d")) {
				calendar.add(Calendar.DAY_OF_MONTH, amount);
			} else if (field.equals("H")) {
				calendar.add(Calendar.HOUR, amount);
			}
		} else {
			return null;
		}
		return getFormatDate(calendar.getTime());
	}

	/**
	 * 某一个时间的前一个时间
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public String getPreDate(String date) throws ParseException {
		Date d = new SimpleDateFormat().parse(date);
		String preD = getPreDate(d, "d", 1);
		Date preDate = new SimpleDateFormat().parse(preD);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(preDate);
	}

	public static Date toDate(String str, String fmt) throws Exception {
		if (fmt == null) {
			fmt = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.parse(str);
	}
	public static void main(String[] args)throws Exception {
		Timestamp t=new Timestamp(DateUtil.toDate("2011-05-12 10:52:32","yyyy-MM-dd HH:mm:ss").getTime());
		logger.info(t.toString());
	}
}
