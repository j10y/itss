package com.xp.commonpart.countjob;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParTest {
	private Set getPram(String formula){
		Set set=new HashSet();
		String regex="\\{[^\\{\\}]+\\}";//匹配{}
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(formula);
		while(matcher.find()){
			String	group=matcher.group();
			set.add(group);
		}
		return set;
	}
//	/**
//	 * 获取两个时间之内的工作日时间（只去掉两个日期之间的周末时间，法定节假日未去掉）
//	 * @param start -起始时间，共有3个重载方法，可以传入long型，Long型，与Date型
//	 * @param end -结束时间，共有3个重载方法，可以传入long型，Long型，与Date型
//	 * @return Long型时间差对象
//	 */
//	public Long getWorkdayTimeInMillis(long start, long end){
//		//如果起始时间大于结束时间，将二者交换
//		if(start>end){
//			long temp = start;
//			start = end;
//			end = temp;
//		}
//		//根据参数获取起始时间与结束时间的日历类型对象
//		Calendar sdate = Calendar.getInstance();
//		Calendar edate = Calendar.getInstance();
//		sdate.setTimeInMillis(start);
//		edate.setTimeInMillis(end);
//		//如果两个时间在同一周并且都不是周末日期，则直接返回时间差，增加执行效率
//		if(sdate.get(Calendar.YEAR)==edate.get(Calendar.YEAR)
//				&& sdate.get(Calendar.WEEK_OF_YEAR)==edate.get(Calendar.WEEK_OF_YEAR)
//				&& sdate.get(Calendar.DAY_OF_WEEK)!=1 && sdate.get(Calendar.DAY_OF_WEEK)!=7
//				&& edate.get(Calendar.DAY_OF_WEEK)!=1 && edate.get(Calendar.DAY_OF_WEEK)!=7){
//			return new Long(end-start);
//		}
//		//首先取得起始日期与结束日期的下个周一的日期
//		Calendar snextM = getNextMonday(sdate);
//		Calendar enextM = getNextMonday(edate);
//		//获取这两个周一之间的实际天数
//		int days = getDaysBetween(snextM, enextM);
//		//获取这两个周一之间的工作日数(两个周一之间的天数肯定能被7整除，并且工作日数量占其中的5/7)
//		int workdays = days/7*5;
//		//获取开始时间的偏移量
//		long scharge = 0;
//		if(sdate.get(Calendar.DAY_OF_WEEK)!=1 && sdate.get(Calendar.DAY_OF_WEEK)!=7){
//			//只有在开始时间为非周末的时候才计算偏移量
//			scharge += (7-sdate.get(Calendar.DAY_OF_WEEK))*24*3600000;
//			scharge -= sdate.get(Calendar.HOUR_OF_DAY)*3600000;
//			scharge -= sdate.get(Calendar.MINUTE)*60000;
//			scharge -= sdate.get(Calendar.SECOND)*1000;
//			scharge -= sdate.get(Calendar.MILLISECOND);
//		}
//		//获取结束时间的偏移量
//		long echarge = 0;
//		if(edate.get(Calendar.DAY_OF_WEEK)!=1 && edate.get(Calendar.DAY_OF_WEEK)!=7){
//			//只有在结束时间为非周末的时候才计算偏移量
//			echarge += (7-edate.get(Calendar.DAY_OF_WEEK))*24*3600000;
//			echarge -= edate.get(Calendar.HOUR_OF_DAY)*3600000;
//			echarge -= edate.get(Calendar.MINUTE)*60000;
//			echarge -= edate.get(Calendar.SECOND)*1000;
//			echarge -= edate.get(Calendar.MILLISECOND);
//		}
//		//计算最终结果，具体为：workdays加上开始时间的时间偏移量，减去结束时间的时间偏移量
//		return workdays*24*3600000+scharge-echarge;
//	}
//	public Long getWorkdayTimeInMillis(Long start, Long end){
//		return getWorkdayTimeInMillis(start.longValue(), end.longValue());
//	}
//	public Long getWorkdayTimeInMillis(Date start, Date end){
//		return getWorkdayTimeInMillis(start.getTime(), end.getTime());
//	}
//	public Long getWorkdayTimeInMillis(String start, String end, String format){
//		SimpleDateFormat sdf = new SimpleDateFormat(format);
//		Date sdate;
//		Date edate;
//		try {
//			sdate = sdf.parse(start);
//			edate = sdf.parse(end);
//			return getWorkdayTimeInMillis(sdate, edate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return new Long(0);
//		}
//	}
//	private Calendar getNextMonday(Calendar cal){
//		int addnum=9-cal.get(Calendar.DAY_OF_WEEK);
//		if(addnum==8)addnum=1;//周日的情况
//		cal.add(Calendar.DATE, addnum);
//		return cal;
//	}
//	/**
//	 * 获取两个日期之间的实际天数，支持跨年
//	 */
//	public int getDaysBetween(Calendar start, Calendar end){
//		if(start.after(end)){
//			Calendar swap = start;
//			start = end;
//			end = swap;
//		}
//		int days = end.get(Calendar.DAY_OF_YEAR)- start.get(Calendar.DAY_OF_YEAR);
//		int y2 = end.get(Calendar.YEAR);
//		if (start.get(Calendar.YEAR) != y2) {
//			start = (Calendar) start.clone();
//			do {
//			    days += start.getActualMaximum(Calendar.DAY_OF_YEAR);
//			    start.add(Calendar.YEAR, 1);
//			}while(start.get(Calendar.YEAR) != y2);
//		}
//		return days;
//	}
	
	/////////////////////////////////////////
	/**
	 * 计算两个日期的相隔天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDaysBetween(Calendar d1, Calendar d2) {
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
		int y2 = d2.get(Calendar.YEAR);
		if (d1.get(Calendar.YEAR) != y2) {
			d1 = (Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
				d1.add(Calendar.YEAR, 1);
			} while (d1.get(Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 计算2个日期之间的工作日相隔天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getWorkingDay(Calendar d1, Calendar d2) {
		int result = -1;
		if (d1.after(d2)) {
			Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		
		//开始日期的日期偏移量
		int charge_start_date = 0;
		//结束日期的日期偏移量
		int charge_end_date = 0;
		// 日期不在同一个日期内
		int stmp;
		int etmp;
		stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
		etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
		// 开始日期为星期六和星期日时偏移量为0
		if (stmp != 0 && stmp != 6) {
			charge_start_date = stmp;
		}
		// 结束日期为星期六和星期日时偏移量为0
		if (etmp != 0 && etmp != 6) {
			charge_end_date = etmp - 1;
		}
		result = (getDaysBetween(getNextMonday(d1), getNextMonday(d2)) / 7)
			* 5 + charge_start_date - charge_end_date;
		return result;
	}
	
	/**
	 * 获得日期的下一个星期一的日期
	 * @param date
	 * @return
	 */
	public static Calendar getNextMonday(Calendar date) {
		Calendar result = null;
		result = date;
		do {
			result = (Calendar) result.clone();
			result.add(Calendar.DATE, 1);
		} while (result.get(Calendar.DAY_OF_WEEK) != 2);
		return result;
	} 
	
	/**
	 * 获取指定年-月(yyyy-mm)的工作日天数
	 * @param year_month
	 * @return
	 */
	public static int getWorkingDayOfMonth(String year_month){
		int year = Integer.parseInt(year_month.substring(0,year_month.indexOf("-")));
		int month = Integer.parseInt(year_month.substring(year_month.indexOf("-") + 1));
		Calendar start = Calendar.getInstance();
		start.clear();
		start.set(Calendar.YEAR, year);
		start.set(Calendar.MONTH, month - 1);
		start.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar end = Calendar.getInstance();
		end.clear();
		end = (Calendar) start.clone();
		end.add(Calendar.MONTH,1);
		end.add(Calendar.DAY_OF_MONTH,-1);
		
		return getWorkingDay(start,end);
	}
	public static void main(String [] args){
//		String s="{key8_r}>={key8_t}*0.7且{key8_r}<{key8_t}*0.8";
//		ParTest pt=new ParTest();
//		Set set=pt.getPram(s);
//		System.out.println();
		ParTest a = new ParTest();
		Calendar sdate = Calendar.getInstance();
		//sdate.set(2011, 10, 21);
		sdate.setTimeInMillis((new Date()).getTime());
		Calendar edate = Calendar.getInstance();
		edate.setTimeInMillis((new Date()).getTime());
		//edate.set(2011, 10, 30);
		//edate.set(2011, 9, 10);
//		Long b=a.getWorkdayTimeInMillis("2011-10-01", "2011-10-18", "yyyy-MM-dd");
		System.out.println(getWorkingDay(sdate,edate));
		System.out.println();
	}
}
