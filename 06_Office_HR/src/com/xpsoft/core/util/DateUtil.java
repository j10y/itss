/*    */ package com.xpsoft.core.util;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import org.apache.commons.lang.time.DateUtils;
/*    */ import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class DateUtil
/*    */ {
/* 17 */   private static final Log logger = LogFactory.getLog(DateUtil.class);
/*    */ 
/*    */   public static Calendar setStartDay(Calendar cal)
/*    */   {
/* 26 */     cal.set(11, 0);
/* 27 */     cal.set(12, 0);
/* 28 */     cal.set(13, 0);
/* 29 */     return cal;
/*    */   }
/*    */ 
/*    */   public static Calendar setEndDay(Calendar cal) {
/* 33 */     cal.set(11, 23);
/* 34 */     cal.set(12, 59);
/* 35 */     cal.set(13, 59);
/* 36 */     return cal;
/*    */   }
/*    */ 
/*    */   public static void copyYearMonthDay(Calendar destCal, Calendar sourceCal)
/*    */   {
/* 45 */     destCal.set(1, sourceCal.get(1));
/* 46 */     destCal.set(2, sourceCal.get(2));
/* 47 */     destCal.set(5, sourceCal.get(5));
/*    */   }
/*    */ 
/*    */   public static String formatEnDate(Date date)
/*    */   {
/* 56 */     SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
/*    */ 
/* 58 */     return sdf.format(date).replaceAll("上午", "AM").replaceAll("下午", "PM");
/*    */   }
/*    */ 
/*    */   public static Date parseDate(String dateString) {
/* 62 */     Date date = null;
/*    */     try {
/* 64 */       date = DateUtils.parseDate(dateString, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd" });
/*    */     } catch (Exception ex) {
/* 66 */       logger.error("Pase the Date(" + dateString + ") occur errors:" + 
/* 67 */         ex.getMessage());
/*    */     }
/* 69 */     return date;
/*    */   }
			public static String formatStringToDate(Date date)
/*    */   {
/* 56 */     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/*    */ 
/* 58 */     return sdf.format(date);
/*    */   }
			public static final String convertDateToString(Date aDate) {
		        return getDateTime("yyyy-MM-dd", aDate);
		    }
		    
		    public static final String convertDateToString(String smark,Date aDate) {
		        return getDateTime(smark, aDate);
		    }
			    
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
			
			public static Date addDays(Date date, int days) {
		        return add(date, days, Calendar.DATE);
		    }


		    public static Date addMonths(Date date, int months) {
		        return add(date, months, Calendar.MONTH);
		    }

		    private static Date add(Date date, int amount, int field) {
		        Calendar calendar = Calendar.getInstance();

		        calendar.setTime(date);
		        calendar.add(field, amount);

		        return calendar.getTime();
		    }
/*    */ }

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.core.util.DateUtil
 * JD-Core Version:    0.6.0
 */