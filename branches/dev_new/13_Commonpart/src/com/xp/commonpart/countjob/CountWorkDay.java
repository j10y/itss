package com.xp.commonpart.countjob;

import java.util.Calendar;
import java.util.Date;

public class CountWorkDay {
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
	 * 计算2个日期之间的工作日相隔天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getWorkingDay(Date dd1, Date dd2) {
		Calendar d1=Calendar.getInstance();
		d1.setTimeInMillis(dd1.getTime());
		Calendar d2=Calendar.getInstance();
		d2.setTimeInMillis(dd2.getTime());
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
