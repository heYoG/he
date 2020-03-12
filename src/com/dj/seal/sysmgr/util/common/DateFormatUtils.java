package com.dj.seal.sysmgr.util.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DateFormatUtils {
	static Logger logger = LogManager.getLogger(DateFormatUtils.class.getName());
	private DateFormatUtils() {
	}

	public static String formatDate(Date date, String tmpl) {
		SimpleDateFormat sdf = new SimpleDateFormat(tmpl);
		return sdf.format(date);
	}
}
