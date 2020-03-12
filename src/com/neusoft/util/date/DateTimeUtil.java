package com.neusoft.util.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateTimeUtil {

    // ********************************************************************
    // Change string to java.util.Date .
    // @strDate: your date string, only support such format as
    // "2005-8-8","2005-8-8 8:30","2005-8-8 8:30:59",
    // Return null when the any error occured
    public static java.util.Date toDateTime(String strDate) {
        java.util.Date datReturn = null;
        if (strDate == null) {
            return datReturn;
        }
        try {
            String strForm = "yyyy-MM-dd";
            if (strDate.indexOf(":") > 0) {
                strForm = "yyyy-MM-dd HH:mm";
            }
            if (strDate.indexOf(":") > 0
                && strDate.lastIndexOf(":") > strDate.indexOf(":")) {
                strForm = "yyyy-MM-dd HH:mm:ss";
            }
            
            java.text.SimpleDateFormat tmpsdf = new java.text.SimpleDateFormat(
                    strForm);
            datReturn = tmpsdf.parse(strDate);
        } catch (Exception ex) {
        }
        return datReturn;
    }

    // ********************************************************************

    // ********************************************************************
    // Get the year/month/day/week/hour/minute/second from your date string
    // NOTE: The first month of the year is JANUARY which is 0
    // The weekday SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, and
    // SATURDAY is 1,2,3,4,5,6,7
    // @mydate: your date,it must be java.util.Date type
    // @interval: Y/M/D/W/H/m/S means year/month/day/week/hour/minute/second.
    // Return -1 when the any error occured
    public static int get(java.util.Date mydate, String interval) {
        int intReturn = -1, intField = 0;
        if (mydate == null || interval == null) {
            return intReturn;
        }
        try {
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            cldr.setTime(mydate);
            if (interval.equals("Y")) {
                intField = java.util.Calendar.YEAR;
            } else if (interval.equals("M")) {
                intField = java.util.Calendar.MONTH;
            } else if (interval.equals("D")) {
                intField = java.util.Calendar.DAY_OF_MONTH;
            } else if (interval.equals("W")) {
                intField = java.util.Calendar.DAY_OF_WEEK;
            } else if (interval.equals("H")) {
                intField = java.util.Calendar.HOUR_OF_DAY;
            } else if (interval.equals("m")) {
                intField = java.util.Calendar.MINUTE;
            } else if (interval.equals("S")) {
                intField = java.util.Calendar.SECOND;
            }

            intReturn = cldr.get(intField);
        } catch (Exception ex) {
        }
        return intReturn;
    }

    // String version of get(java.util.Date mydate,String interval)
    public static int get(String strDate, String interval) {
        return get(toDateTime(strDate), interval);
    }

    // ********************************************************************

    // ********************************************************************
    // This function get the max day number of the given strDate.
    // @mydate: your date,it must be java.util.Date type
    // Return -1 when the any error occured.
    // eg: getMaxday("2006-2-1")== 28;
    public static int getMaxday(java.util.Date mydate) {
        int intReturn = -1;
        if (mydate == null) {
            return intReturn;
        }
        try {
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            cldr.setTime(mydate);
            intReturn = cldr.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        } catch (Exception ex) {
        }
        return intReturn;
    }

    // String version of getMaxday(java.util.Date mydate)
    public static int getMaxday(String strDate) {
        return getMaxday(toDateTime(strDate));
    }

    // ********************************************************************
    
    
    public static String formatDateTime(Date date) {
        return formatDateTime(date, "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String formatDate(Date date) {
        return formatDateTime(date, "yyyy-MM-dd");
    }

    // ********************************************************************
    // Format your date as the specify formation.
    // NOTE: Only Can Format SQL Server datetime/smalldatetime type.
    // @strDate: your date
    // @strFormat: the specify formation
    // Return blank string '' when the any error occured
    public static String formatDateTime(java.util.Date mydate, String strFormat) {
        String strReturn = "";
        if (mydate == null) {
            return strReturn;
        }
        if (strFormat == null) {
            strFormat = "yyyy-MM-dd";
        }
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    strFormat);
            strReturn = sdf.format(mydate);
        } catch (Exception ex) {
        }
        return strReturn;
    }

    // String version of getMaxday(java.util.Date mydate)
    public static String formatDateTime(String strDate, String strFormat) {
        return formatDateTime(toDateTime(strDate), strFormat);
    }

    // ********************************************************************

    // ********************************************************************
    // Get current date and time as the the specify formation.
   public static String getCurDateTime(String strFormat) {
        String strReturn = "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    strFormat);
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            strReturn = sdf.format(cldr.getTime());
        } catch (Exception ex) {
        }
        return strReturn;
    }
    
    public static String getCurDateTime() {
    	return getCurDateTime("yyyy-MM-dd HH:mm:ss");
    }

    // ********************************************************************

    // ********************************************************************
    // Get current date, default formation is 'yyyy-MM-dd'.
    public static String getCurDate() {
        String strReturn = "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM-dd");
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            strReturn = sdf.format(cldr.getTime());
        } catch (Exception ex) {
        }
        return strReturn;
    }

    // ********************************************************************
    
    // ********************************************************************
    // Get current date, default formation is 'yyyy-MM-dd'.
    public static String getCurYear() {
        String strReturn = "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "yyyy");
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            strReturn = sdf.format(cldr.getTime());
        } catch (Exception ex) {
        }
        return strReturn;
    }

    // ********************************************************************
    
    // ********************************************************************
    // Get current date, default formation is 'yyyy-MM-dd'.
    public static String getCurMonth() {
        String strReturn = "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "yyyy-MM");
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            strReturn = sdf.format(cldr.getTime());
        } catch (Exception ex) {
        }
        return strReturn;
    }

    // ********************************************************************

    // ********************************************************************
    // Get current time, default formation is 'HH:mm'.
    public static String getCurTime() {
        String strReturn = "";
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                    "HH:mm");
            java.util.Calendar cldr = java.util.Calendar.getInstance();
            strReturn = sdf.format(cldr.getTime());
        } catch (Exception ex) {
        }
        return strReturn;
    }

    // ********************************************************************

    // ********************************************************************
    // This function likes MS VB dateAdd().
    // NOTE: Only Can operate date string not include time part. Not support
    // week operation.
    // @strDate: your date ,such as "2005-8-9"
    // @interval: the interval to be added, it must be one of
    // "Y"、"M"、"D"、"H"、"m"、"S".
    // @number: the number to be added.
    // Return null when the any error occured.
    // eg: dateAdd("2004-2-29","Y",-1)== "2003-2-28";
    public static java.util.Date dateAdd(java.util.Date mydate,
                                         String interval, int number) {
        java.util.Date datReturn= null;
        int intInterval = Calendar.DAY_OF_YEAR;
        if (interval.equals("Y")) {
            intInterval = Calendar.YEAR;
        } else if (interval.equals("M")) {
            intInterval = Calendar.MONTH;
        } else if (interval.equals("W")) {
            intInterval = Calendar.WEEK_OF_MONTH;
        } else if (interval.equals("D")) {
            intInterval = Calendar.DAY_OF_YEAR;
        } else if (interval.equals("H")) {
            intInterval = Calendar.HOUR_OF_DAY;
        } else if (interval.equals("m")) {
            intInterval = Calendar.MINUTE;
        } else if (interval.equals("S")) {
            intInterval = Calendar.SECOND;
        }

        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        try {
            cal.setTime(mydate);
            cal.add(intInterval, number);
            datReturn = cal.getTime();
        } catch (Exception ex) {
        }
        return datReturn;
    }

    // String version of dateAdd(String strDate,String interval,int number)
    public static java.util.Date dateAdd(String strDate, String interval,
                                         int number) {
        return dateAdd(toDateTime(strDate), interval, number);
    }

    // ********************************************************************

    // ********************************************************************
    // This function likes MS VB DateDiff().
    // NOTE: Only Can operate date string not include time part. Not support
    // week operation.
    // @strDate1,@strDate2: your date ,such as "2004-3-9".
    // @interval: the interval to be Diff, it must be one of
    // "Y"、"M"、"D"、"H"、"m"、"S".
    // Return: the number (strDate2-strDate1)/interval,when errors occured will
    // return -1,000,000,000
    // eg: dateDiff("D","2004-1-1", "2004-2-2") == 32;
    public static int dateDiff(String interval, java.util.Date date1,
                               java.util.Date date2) {
        int intReturn = -1000000000;
        if (date1 == null || date2 == null || interval == null) {
            return intReturn;
        }
        try {
            java.util.Calendar cal1 = java.util.Calendar.getInstance();
            java.util.Calendar cal2 = java.util.Calendar.getInstance();

            // different date might have different offset
            cal1.setTime(date1);
            long ldate1 = date1.getTime()
                          + cal1.get(java.util.Calendar.ZONE_OFFSET)
                          + cal1.get(java.util.Calendar.DST_OFFSET);

            cal2.setTime(date2);
            long ldate2 = date2.getTime()
                          + cal2.get(java.util.Calendar.ZONE_OFFSET)
                          + cal2.get(java.util.Calendar.DST_OFFSET);

            // Use integer calculation, truncate the decimals
            int hr1 = (int) (ldate1 / 3600000);
            int hr2 = (int) (ldate2 / 3600000);

            int days1 = hr1 / 24;
            int days2 = hr2 / 24;

            int yearDiff = cal2.get(java.util.Calendar.YEAR)
                           - cal1.get(java.util.Calendar.YEAR);
            int monthDiff = yearDiff * 12 + cal2.get(java.util.Calendar.MONTH)
                            - cal1.get(java.util.Calendar.MONTH);
            int dateDiff = days2 - days1;
            int hourDiff = hr2 - hr1;
            int minuteDiff = (int) (ldate2 / 60000) - (int) (ldate1 / 60000) + 1;
            int secondDiff = (int) (ldate2 / 1000) - (int) (ldate1 / 1000);

            if (interval.equals("Y")) {
                intReturn = yearDiff;
            } else if (interval.equals("M")) {
                intReturn = monthDiff;
            } else if (interval.equals("D")) {
                intReturn = dateDiff;
            } else if (interval.equals("H")) {
                intReturn = hourDiff;
            } else if (interval.equals("m")) {
                intReturn = minuteDiff;
            } else if (interval.equals("S")) {
                intReturn = secondDiff;
            }
        } catch (Exception ex) {
        }
        return intReturn;
    }

    // String version of dateDiff(String interval,java.util.Date date1,
    // java.util.Date date2)
    public static int dateDiff(String interval, String strDate1,
                               String strDate2) {
        return dateDiff(interval, toDateTime(strDate1), toDateTime(strDate2));
    }
    // ********************************************************************

    
    public static String ressDateConvert(String tod) {
		String s = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
			Date d = sdf.parse(tod);
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			s = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(c.getTime());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
    
    public static Date getDate(String dateTime)  {
        if(null == dateTime || dateTime.length() == 0)
            return null;
        DateFormat df = null;
        if(dateTime.length() == "yyyy-MM-dd HH:mm:ss".length())
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
        if(dateTime.length() == "yyyy-MM-dd HH:mm".length())
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        else
        if(dateTime.length() == "yyyy-MM-dd".length())
            df = new SimpleDateFormat("yyyy-MM-dd");
        else
            return null;
        try {
            return df.parse(dateTime);
        }
        catch(ParseException pe) {
            return null;
        }
    }
    
    public static String getTimeStamp() {
        Date dt = new Date();
        long tm = dt.getTime();
        return Long.toString(tm);
    }
    
    public static String buildDateTime(String date, String hour, String minute) {
        if(null == date || date.length() == 0)
            return "";
        if(null == hour)
            return date;
        String hm = hour.length() != 1 ? hour : "0" + hour;
        if(null != minute)
        {
            hm = hm + ":";
            hm = hm + (minute.length() != 1 ? minute : "0" + minute);
        }
        return date + " " + hm;
    }
    

    public static String formatTimeStamp(String timeStamp, String format) {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);  
		return sdf.format(new Date(Long.parseLong(timeStamp)));
    }
    
    // 判定时间date1是否在时间date2之前
	// 时间格式 2008-9-6 16:16:34
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	// 判定当前时间是否在时间date2之前
	// 2008-9-6 16:16:34
	public static boolean isDateBefore(String date2) {
		try {
			Date date1 = new Date();
			DateFormat df = DateFormat.getDateTimeInstance();
			return date1.before(df.parse(date2));
		} catch (ParseException e) {
			System.out.print("[SYS] " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * 返回当前中文日期，如:2008年11月1日 星期六
	 */
	public static String getChineseDate() {
		String str = "";
		str += String.valueOf(Calendar.getInstance().get(Calendar.YEAR)) + "年";
		str += String.valueOf(Calendar.getInstance().get(Calendar.MONTH) + 1) + "月";
		str += String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) + "日";
		str += " 星期";

		switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
			case 1:
				str += "日";
				break;
			case 2:
				str += "一";
				break;
			case 3:
				str += "二";
				break;
			case 4:
				str += "三";
				break;
			case 5:
				str += "四";
				break;
			case 6:
				str += "五";
				break;
			case 7:
				str += "六";
				break;
			default:
				break;
		}

		return str;
	}
	
	public static String getPassDay(String d1, String d2) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date a = parser.parse(d1);
            Date b = parser.parse(d2);
            long aa = a.getTime();
            long bb = b.getTime();
            long diff = (Math.abs((aa - bb) / 86400000));

            return Long.toString(diff);
        }  catch(Exception e) {
            return "-1";
        }
    }
	
	public static String addDays(String d, int n) {
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date a = parser.parse(d);
            long aa = a.getTime();
            aa += (long)n * 86400000;
            java.sql.Date b = new java.sql.Date(aa);

            return (b.toString());
        } catch(Exception e) {
            return d;
        }
    }
	
	/**
	 * 计算时间差
	 */
	public static long getPassSecond(String startTime, String endTime) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = df.parse(startTime);
			Date d2 = df.parse(endTime);
			return d2.getTime() - d1.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 获取月份最后一天
	 */
	public static String getLastDay(String year, String month) {
        try {
			String ret = "0";
			int y = Integer.parseInt(year);
			int m = Integer.parseInt(month);
			switch (m){
				case 1:
					ret = "31";
					break;
				case 3:
					ret = "31";
					break;
				case 5:
					ret = "31";
					break;
				case 7:
					ret = "31";
					break;
				case 8:
					ret = "31";
					break;
				case 10:
					ret = "31";
					break;
				case 12:
					ret = "31";
					break;
				case 4:
					ret = "30";
					break;
				case 6:
					ret = "30";
					break;
				case 9:
					ret = "30";
					break;
				case 11:
					ret = "30";
					break;
				case 2:
					if ((y%4==0&&y%100!=0)||y%400==0)
						ret = "29";
					else
						ret = "28";
					break;
			}
			return ret;
        } catch(Exception e) {
            return e.toString();
        }
    }
	
	/**
	 * 获取一年前日期
	 * 时间格式：2008-01-01
	 */
	public static String getLastYearDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, -1);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		int iDate = calendar.get(Calendar.DATE);
		String month = String.valueOf(iMonth);
		String date = String.valueOf(iDate);
		if (iMonth < 10) {
			month = "0" + iMonth;
		}
		if (iDate < 10) {
			date = "0" + iDate;
		}
		
		return iYear + "-" + month + "-" + date;
	}
	
	/**
	 * 获取半年前日期
	 * 时间格式：2008-01-01
	 */
	public static String getLastHalfYearDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -6);
		int iYear = calendar.get(Calendar.YEAR);
		int iMonth = calendar.get(Calendar.MONTH) + 1;
		int iDate = calendar.get(Calendar.DATE);
		String month = String.valueOf(iMonth);
		String date = String.valueOf(iDate);
		if (iMonth < 10) {
			month = "0" + iMonth;
		}
		if (iDate < 10) {
			date = "0" + iDate;
		}
		
		return iYear + "-" + month + "-" + date;
	}
	
	public static String getAge(Date birthDay) { 
        Calendar cal = Calendar.getInstance(); 

        if (cal.before(birthDay)) { 
            return "";
        } 

        int yearNow = cal.get(Calendar.YEAR); 
        int monthNow = cal.get(Calendar.MONTH)+1; 
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); 
        
        cal.setTime(birthDay); 
        int yearBirth = cal.get(Calendar.YEAR); 
        int monthBirth = cal.get(Calendar.MONTH); 
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH); 

        int age = yearNow - yearBirth; 

        if (monthNow <= monthBirth) { 
            if (monthNow == monthBirth) { 
                //monthNow==monthBirth 
                if (dayOfMonthNow < dayOfMonthBirth) { 
                    age--; 
                } 
            } else { 
                //monthNow>monthBirth 
                age--; 
            } 
        } 

        return age + ""; 
    }
	
	public static void main(String[] args) {
//		Date date = DateTimeUtil.dateAdd(DateTimeUtil.getCurDate(), "D", -1);
//		System.out.println(DateTimeUtil.formatDateTime(date, "yyyy"));
//		System.out.println(DateTimeUtil.formatDateTime(date, "MM"));
//		System.out.println(DateTimeUtil.formatDateTime(date, "dd"));
		
		String dd = DateTimeUtil.formatDateTime(new Date(), "yyyyMM");
		System.out.println(dd);
		
	}
}
