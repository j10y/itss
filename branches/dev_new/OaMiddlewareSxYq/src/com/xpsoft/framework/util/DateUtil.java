package com.xpsoft.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    public static String datePattern = "yyyy-MM-dd";
    public static String timePattern = datePattern + " HH:mm:ss";


    /**
     * 
     * @Methods Name getDatePattern
     * @Create In Jul 23, 2010 By likang
     * @return String
     */
    public static String getDatePattern() {
        return datePattern;
    }

    public static String getTimePattern() {
        return timePattern;
    }

    /**
     * 
     * @Methods Name getDate
     * @Create In Jul 23, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 
     * @Methods Name convertStringToDate
     * @Create In Jul 23, 2010 By likang
     * @param aMask
     * @param strDate
     * @return date
     * @throws ParseException 
     * @throws ParseException Date
     */
    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException{
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        
            date = df.parse(strDate);
      
        return (date);
    }

    /**
     * 
     * @Methods Name getTimeNow
     * @Create In Jul 23, 2010 By likang
     * @param theTime
     * @return String
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * 
     * @Methods Name getToday
     * @Create In Jul 23, 2010 By likang
     * @return
     * @throws ParseException Calendar
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));

        return cal;
    }
    
    /**
     * 获取当前日期，日期格式为年月日
     * @Methods Name getCurrentDate
     * @Create In Jul 22, 2010 By likang
     * @return java.util.Date
     */
    public static java.util.Date getCurrentDate(){
    	 Date today = new Date();
         SimpleDateFormat df = new SimpleDateFormat(datePattern);
         String todayAsString = df.format(today);
         Calendar cal = new GregorianCalendar();
		 cal.setTime(convertStringToDate(todayAsString));
         return (java.util.Date)cal.getTime();
    }
    
    /**
     * 获取当前的日期，日期格式为年月日时分秒
     * @Methods Name getCurrentDateTime
     * @Create In Jul 23, 2010 By likang
     * @return java.util.Date
     */
    public static java.util.Date getCurrentDateTime(){
   	 	Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.timePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
        return (java.util.Date)cal.getTime();
   }
    
    
    /**
     * 获取当前的日期(字符串)，日期格式为年月日时分秒
     * @Methods Name getCurrentDateTimeString
     * @Create In Jul 23, 2010 By likang
     * @return java.util.Date
     */
    public static String getCurrentDateTimeString(){
   	 	Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(DateUtil.timePattern);
        return df.format(today);
   }
    
    /**
     * 
     * @Methods Name getCurrentSQLDate
     * @Create In Jul 23, 2010 By likang
     * @return java.sql.Date
     */
    public static java.sql.Date getCurrentSQLDate(){
   	 Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
        return (java.sql.Date)cal.getTime();
   }

    /**
     * 
     * @Methods Name getDateTime
     * @Create In Jul 23, 2010 By likang
     * @param aMask
     * @param aDate
     * @return String
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
           
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 
     * @Methods Name convertDateToString
     * @Create In Jul 23, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }
    
    /**
     * 将日期转成年月日 时分秒格式的字符串
     * @Methods Name convertDateTimeToString
     * @Create In Jul 22, 2010 By likang
     * @param aDate
     * @return String
     */
    public static final String convertDateTimeToString(Date aDate) {
        return getDateTime(timePattern, aDate);
    }
    

    /**
     * 
     * @Methods Name convertStringToDate
     * @Create In Jul 23, 2010 By likang
     * @param strDate
     * @return Date
     */
    public static Date convertStringToDate(String strDate) {
        Date aDate = null;
        try {
            String pattern = "\\d{4}[-|/]\\d{2}[-|/]\\d{2}[ ]\\d{2}[:]\\d{2}[:]\\d{2}";	
			if(strDate.matches(pattern)) {
				if(strDate.contains("/")) {
				    aDate = convertStringToDate("yyyy/MM/dd HH:mm:ss", strDate);
				} else {
				    aDate = convertStringToDate(timePattern, strDate);
				}
			} else {
				if(strDate.contains("/")) {
				    aDate = convertStringToDate("yyyy/MM/dd", strDate);
				} else {
				    aDate = convertStringToDate(datePattern, strDate);
				}
			}
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        return aDate;
    }

    /**
     * 日期 增加 或 减少（日）
     * @Methods Name addDays
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDays(Date date, int days) {
        return add(date, days, Calendar.DATE);
    }

    /**
     * 日期 增加 或 减少（月）
     * @Methods Name addMonths
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param months
     * @return Date
     */
    public static Date addMonths(Date date, int months) {
        return add(date, months, Calendar.MONTH);
    }
    
    
    /**
     * 日期 增加 或 减少（年）
     * @Methods Name addYears
     * @Create In Jul 27, 2010 By likang
     * @param date
     * @param months
     * @return Date
     */
    public static Date addYears(Date date, int months) {
        return add(date, months, Calendar.YEAR);
    }
    
    /**
     * 日期 增加 或 减少
     * @Methods Name add
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @param amount
     * @param field
     * 				Calendar.YEAR;
     *   			Calendar.DATE;
     *   			Calendar.MONTH;
     * @return Date
     */
    private static Date add(Date date, int amount, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }
    
    /**
     * 取得日期所在月的最后一天
     * @Methods Name add
     * @Create In Jul 23, 2010 By likang
     * @param date
     * @return Date
     */
    public static Date getLastMonthDay(Date date){
	    GregorianCalendar gc = new GregorianCalendar(Locale.CHINA);
	    int dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
	    int maxDaysOfMonth = gc.getActualMaximum(Calendar.DAY_OF_MONTH);
	    gc.add(Calendar.DAY_OF_MONTH, maxDaysOfMonth - dayOfMonth);
	    return gc.getTime();
    }
    
    /**
     * 是否是闰年
     * @Methods Name isLeapYear
     * @Create In Jul 23, 2010 By likang
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {   
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);   
    }  
    
    
    /**  
     * 获取某年某月的最后一天  
     * @Methods Name getLastDayOfMonth
     * @Create In Jul 23, 2010 By likang
     * @param year 年  
     * @param month 月  
     * @return 最后一天  
     */  
	  private int getLastDayOfMonth(int year, int month) {   
	         if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8  
	                   || month == 10 || month == 12) {   
	               return 31;   
	         }   
	         if (month == 4 || month == 6 || month == 9 || month == 11) {   
	               return 30;   
	         }   
	         if (month == 2) {   
	               if (isLeapYear(year)) {   
	                   return 29;   
	               } else {   
	                   return 28;   
	               }   
	         }   
	         return 0;   
	  } 
	  
	  /**
	   * 获得当前毫秒数
	   * @Methods Name getTimeMillis
	   * @Create In Sep 13, 2010 By Administrator
	   * @return long
	   */
	  public static long getTimeMillis() {
		return System.currentTimeMillis();
	  }
 
}
