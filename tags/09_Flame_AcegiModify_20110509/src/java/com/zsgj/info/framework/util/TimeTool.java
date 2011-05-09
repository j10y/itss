package com.zsgj.info.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeTool {
//	---当前日期的年，月，日，时，分，秒
	public static Calendar now   = Calendar.getInstance();
	int    year = now.get( Calendar.YEAR );
	int    date = now.get( Calendar.DAY_OF_MONTH );
	int    month = now.get( Calendar.MONTH ) + 1;
	int    hour = now.get( Calendar.HOUR );
	int    min   = now.get( Calendar.MINUTE );
	int    sec   = now.get( Calendar.SECOND );

//	-------------------------------日期类型转换---------------------------------------------------------------------------
	/**
	* 字符型日期转化util.Date型日期
	* @Param:p_strDate 字符型日期 
	* @param p_format 格式:"yyyy-MM-dd" / "yyyy-MM-dd hh:mm:ss"
	* @Return:java.util.Date util.Date型日期
	* @Throws: ParseException
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.util.Date toUtilDateFromStrDateByFormat( String p_strDate, String p_format )
	    throws ParseException {
	   java.util.Date l_date = null;
	   java.text.DateFormat df = new java.text.SimpleDateFormat( p_format );
	   if ( p_strDate != null && ( !"".equals( p_strDate ) ) && p_format != null && ( !"".equals( p_format ) ) ) {
	    l_date = df.parse( p_strDate );
	   }
	   return l_date;
	}



	/**
	* 字符型日期转化成sql.Date型日期
	* @param p_strDate    字符型日期
	* @return java.sql.Date sql.Date型日期
	* @throws ParseException 
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.sql.Date toSqlDateFromStrDate( String p_strDate ) throws ParseException {
	   java.sql.Date returnDate = null;
	   java.text.DateFormat sdf = new java.text.SimpleDateFormat();
	   if ( p_strDate != null && ( !"".equals( p_strDate ) ) ) {
	    returnDate = new java.sql.Date( sdf.parse( p_strDate ).getTime() );
	   }
	   return returnDate;
	}
	  
	/** 
	* util.Date型日期转化指定格式的字符串型日期
	* @param   p_date    Date 
	* @param   p_format String 
	* 格式1:"yyyy-MM-dd" 
	* 格式2:"yyyy-MM-dd hh:mm:ss EE" 
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE" 
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	* @return String 
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static String toStrDateFromUtilDateByFormat( java.util.Date p_utilDate, String p_format ) {
	   String l_result = "";
	   if ( p_utilDate != null ) {
	    SimpleDateFormat sdf = new SimpleDateFormat( p_format );
	    l_result = sdf.format( p_utilDate );
	   }
	   return l_result;
	}

	/**
	* util.Date型日期转化转化成Calendar日期
	* @param p_utilDate Date
	* @return Calendar
	* @Author: zhuqx
	* @Date: 2006-10-31
	*/
	public static Calendar toCalendarFromUtilDate(java.util.Date p_utilDate) {
	   Calendar c = Calendar.getInstance();
	   c.setTime(p_utilDate);
	   return c;
	}

	/**
	* util.Date型日期转化sql.Date(年月日)型日期
	* @Param: p_utilDate util.Date型日期
	* @Return: java.sql.Date sql.Date型日期
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.sql.Date toSqlDateFromUtilDate( java.util.Date p_utilDate ) {
	   java.sql.Date returnDate = null;
	   if ( p_utilDate != null ) {
	    returnDate = new java.sql.Date( p_utilDate.getTime() );
	   }
	   return returnDate;
	}
	
	/**
	 * 获取sql类型的当前日期
	 * @Methods Name getCurrentSqlDate
	 * @Create In 2008-2-28 By peixf
	 * @return java.sql.Date
	 */
	public static java.sql.Date getCurrentSqlDate() {
		 return new java.sql.Date(DateUtil.getCurrentDate().getTime());
	}

	/**
	* util.Date型日期转化sql.Time(时分秒)型日期
	* @Param: p_utilDate util.Date型日期
	* @Return: java.sql.Time sql.Time型日期
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.sql.Time toSqlTimeFromUtilDate( java.util.Date p_utilDate ) {
	   java.sql.Time returnDate = null;
	   if ( p_utilDate != null ) {
	    returnDate = new java.sql.Time( p_utilDate.getTime() );
	   }
	   return returnDate;
	}

	/**
	* util.Date型日期转化sql.Date(时分秒)型日期
	* @Param: p_utilDate util.Date型日期
	* @Return: java.sql.Timestamp sql.Timestamp型日期
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.sql.Timestamp toSqlTimestampFromUtilDate( java.util.Date p_utilDate ) {
	   java.sql.Timestamp returnDate = null;
	   if ( p_utilDate != null ) {
	    returnDate = new java.sql.Timestamp( p_utilDate.getTime() );
	   }
	   return returnDate;
	}

	/**
	* sql.Date型日期转化util.Date型日期
	* @Param: sqlDate sql.Date型日期
	* @Return: java.util.Date util.Date型日期
	* @Author: zhuqx
	* @Date:   2006-10-31
	*/
	public static java.util.Date toUtilDateFromSqlDate( java.sql.Date p_sqlDate ) {
	   java.util.Date returnDate = null;
	   if ( p_sqlDate != null ) {
	    returnDate = new java.util.Date( p_sqlDate.getTime() );
	   }
	   return returnDate;
	}

//	-----------------获取指定日期的年份，月份，日份，小时，分，秒，毫秒----------------------------
	/** 
	* 获取指定日期的年份 
	* @param p_date util.Date日期 
	* @return int   年份 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getYearOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.YEAR );
	}
	  
	/** 
	* 获取指定日期的月份 
	* @param p_date util.Date日期 
	* @return int   月份 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getMonthOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.MONTH ) + 1;
	}

	/** 
	* 获取指定日期的日份 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getDayOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.DAY_OF_MONTH );
	}

	/** 
	* 获取指定日期的小时 
	* @param p_date util.Date日期 
	* @return int   日份 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getHourOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.HOUR_OF_DAY );
	}
	  
	/** 
	* 获取指定日期的分钟 
	* @param p_date util.Date日期 
	* @return int   分钟 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getMinuteOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.MINUTE );
	}
	  
	/** 
	* 获取指定日期的秒钟 
	* @param p_date util.Date日期 
	* @return int   秒钟 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static int getSecondOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.get( java.util.Calendar.SECOND ); 
	}
	
	/** 
	* 获取指定日期所在天的最后一秒时间
	* @param p_date util.Date日期 
	* @return int   秒钟 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static java.util.Date getEndOfDate( java.util.Date p_date ) {
		Calendar todayEnd = Calendar.getInstance(); 
		todayEnd.setTime(p_date); 
		todayEnd.set(Calendar.HOUR_OF_DAY,23); 
		todayEnd.set(Calendar.MINUTE,59); 
		todayEnd.set(Calendar.SECOND,59); 
		return todayEnd.getTime();  
	}
	
	public static String getEndOfDateAsString( java.util.Date p_date ) {
	   Date date = getEndOfDate(p_date);
	   return DateUtil.getDateTime(DateUtil.getTimePattern(), date);
	}
	
	/** 
	* 获取指定日期所在天的最后一秒时间
	* @param p_date util.Date日期 
	* @return int   秒钟 
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static java.util.Date getBeginOfDate( java.util.Date p_date ) {
		Calendar todayBegin = Calendar.getInstance(); 
		todayBegin.setTime(p_date); 
		todayBegin.set(Calendar.HOUR_OF_DAY,0); 
		todayBegin.set(Calendar.MINUTE,0); 
		todayBegin.set(Calendar.SECOND,0); 
		return todayBegin.getTime(); 
	}
	
	public static String getBeginOfDateAsString( java.util.Date p_date ) {
	   Date date = getBeginOfDate(p_date);
	   return DateUtil.getDateTime(DateUtil.getTimePattern(), date);
	}
	  
	/** 
	* 获取指定日期的毫秒   
	* @param p_date util.Date日期 
	* @return long   毫秒   
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static long getMillisOfDate( java.util.Date p_date ) {
	   java.util.Calendar c = java.util.Calendar.getInstance();
	   c.setTime( p_date );
	   return c.getTimeInMillis();
	}

//	-----------------获取当前/系统日期(指定日期格式)-----------------------------------------------------------------------------------
	/**
	* 获取指定日期格式当前日期的字符型日期
	* @param p_format 日期格式
	* 格式1:"yyyy-MM-dd" 
	* 格式2:"yyyy-MM-dd hh:mm:ss EE" 
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE" 
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	* @return String 当前时间字符串
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static String getNowOfDateByFormat( String p_format ) {
	   Date d = new Date();
	   SimpleDateFormat sdf = new SimpleDateFormat( p_format );
	   String dateStr = sdf.format( d );
	   return dateStr;
	}
	
	public static Date getCurrentDateByFormat( String p_format ) {
		   Date d = new Date();
		   SimpleDateFormat sdf = new SimpleDateFormat( p_format );
		   String dateStr = sdf.format( d ).substring(0,10);
		   Date dd = null;
		try {
			dd = toUtilDateFromStrDateByFormat(dateStr, p_format);
			System.out.println("dd is "+dd);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   return dd;
		}

	/**
	* 获取指定日期格式系统日期的字符型日期
	* @param p_format 日期格式
	* 格式1:"yyyy-MM-dd" 
	* 格式2:"yyyy-MM-dd hh:mm:ss EE" 
	* 格式3:"yyyy年MM月dd日 hh:mm:ss EE" 
	* 说明: 年-月-日 时:分:秒 星期 注意MM/mm大小写
	* @return String 系统时间字符串
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static String getSystemOfDateByFormat( String p_format ) {
	   long time = System.currentTimeMillis();
	   Date d2 = new Date();
	   Date d = new Date( time );
	   SimpleDateFormat sdf = new SimpleDateFormat( p_format );
	   String dateStr = sdf.format( d );
	   return dateStr;
	}

	/**
	* 获取字符日期一个月的天数
	* @param p_date
	* @return 天数
	* @author zhuqx
	*/
	public static long getDayOfMonth( Date p_date ) throws ParseException {
	   int year = getYearOfDate(p_date);
	   int month = getMonthOfDate( p_date )-1;
	   int day = getDayOfDate( p_date );
	   int hour = getHourOfDate( p_date );
	   int minute = getMinuteOfDate( p_date );
	   int second = getSecondOfDate( p_date );
	   Calendar l_calendar = new GregorianCalendar(year,month,day,hour,minute,second);
	   return l_calendar.getActualMaximum( l_calendar.DAY_OF_MONTH );
	}

//	 -----------------获取指定月份的第一天,最后一天 ---------------------------------------------------------------------------
	/** 
	* 获取指定月份的第一天 
	* @param p_strDate 指定月份
	* @param p_formate 日期格式
	* @return String 时间字符串
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static String getDateOfMonthBegin( String p_strDate, String p_format ) throws ParseException {
	   java.util.Date date = toUtilDateFromStrDateByFormat( p_strDate,p_format );
	   return toStrDateFromUtilDateByFormat( date,"yyyy-MM" ) + "-01";
	}
	  
	/** 
	* 获取指定月份的最后一天 
	* @param p_strDate 指定月份
	* @param p_formate 日期格式
	* @return String 时间字符串
	* @author zhuqx
	* @Date:   2006-10-31
	*/
	public static String getDateOfMonthEnd( String p_strDate, String p_format ) throws ParseException {
	   java.util.Date date = toUtilDateFromStrDateByFormat( getDateOfMonthBegin( p_strDate,p_format ),p_format );
	   Calendar calendar = Calendar.getInstance();
	   calendar.setTime( date );
	   calendar.add( Calendar.MONTH,1 );
	   calendar.add( Calendar.DAY_OF_YEAR,-1 );
	   return toStrDateFromUtilDateByFormat( calendar.getTime(),p_format );
	}

	public static String getBeginOfYear(){
	   Calendar calendar = null;
	   try {
			java.util.Date date = getCurrentDateByFormat(DateUtil.timePattern);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
		} catch (Exception e) {}
	   Date date = calendar.getTime();
	   return date.toString();
		
		//return toStrDateFromUtilDateByFormat( calendar.getTime(),DateUtil.timePattern );
	}
}
