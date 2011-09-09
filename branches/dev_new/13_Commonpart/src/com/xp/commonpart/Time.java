package com.xp.commonpart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * 时间处理类，计算日期时间、转换时间表示类型
 *
 * @author Administrator
 * @version 1.0
 */
public class Time extends BaseTime {
    /**
     * 将一个日期对象转换成一个标准格式的日期字符串
     *
     * @param date
     * @return
     */
    public static String getString(Date date) {
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(chinaFormat);
        return bartDateFormat.format(date);
    }

    public static String getString(long longTime) {
        return getString(new Date(longTime));
    }

    /**
     * 将格式化的字符串转换为一个连续的字符串 <br>
     * [yyyy-mm-dd hh:mu:ss] ---> [yyyymmddhhmuss]
     *
     * @param formatTime
     * @return 连续的字符串
     */
    public static String getTimeToSeries(String formatTime) {
        if (formatTime == null)
            return null;
        formatTime = formatTime.trim();
        if (formatTime.length() < 19)
            return formatTime;
        String seriesTime = "";
        seriesTime = formatTime.substring(0, 4);
        int incept = 5;
        while (incept < 19) {
            seriesTime += formatTime.substring(incept, incept + 2);
            incept += 3;
        }
        return seriesTime;
    }
    
    /**
     *将毫秒转换成 MM/dd/yyyy HH:mm
     * @param timeMills
     * @return
     */
     public static String getTimeString(long timeMills)
     {
     	SimpleDateFormat dateTime = new SimpleDateFormat(dateTimeString);
     	return dateTime.format(new Date(timeMills));
     	
     }

    /**
     * 格式化日期转换为连续字符串
     *
     * @param shortDate
     * @return yyyy-mm-dd ----——> yyyymmdd
     */
    public static String getDateToSeriess(String shortDate) {
        String date = shortDate + " 00:00:00";
        return getShortDateFromFormat(getTimeToSeries(date));
    }

    /**
     * 将用一个连续字符串表示的日期转换为一个格式化的日期长符串 <br>
     * [yyyymmddhhmuss] ---> [yyyy-mm-dd hh:mu:ss]
     *
     * @param seriesTime
     * @return 格式化的日期长符串
     */
    public static String getTimeToFormat(String seriesTime) {
        if (seriesTime == null)
            return null;
        seriesTime = seriesTime.trim();
        if (seriesTime.length() < 8)
            return seriesTime;
        if (seriesTime.length() <= 10)
            seriesTime = "0000" + seriesTime;
        if (seriesTime.length() <= 12)
            seriesTime += "00";
        String formatTime = "";
        formatTime = seriesTime.substring(0, 4) + "-"
                + seriesTime.substring(4, 6) + "-" + seriesTime.substring(6, 8)
                + " " + seriesTime.substring(8, 10) + ":"
                + seriesTime.substring(10, 12) + ":"
                + seriesTime.substring(12, 14);
        return formatTime;
    }

    /**
     * 连续日期转换为格式化字符串
     *
     * @param seriesDate
     * @return
     */
    static public String getDateToFormat(String seriesDate) {
        return seriesDate.substring(0, 4) + "-" + seriesDate.substring(4, 6)
                + "-" + seriesDate.substring(6, 8);
    }

    /**
     * 取没有时间的日期字段
     *
     * @param formatTime
     * @return 日期字段
     */
    public static String getShortDateFromFormat(String formatTime) {
        return getDateAndTimeFromFormat(formatTime)[0];
    }

    /**
     * 取没有日期的时间字段
     *
     * @param formatTime
     * @return 日期字段
     */
    public static String getShortTimeFromFormat(String formatTime) {
        return getDateAndTimeFromFormat(formatTime)[1];
    }

    /**
     * 将一个格式化的日期字符串分割成日期和时间数组 <br>
     * [yyyy-mm-dd hh:mu:ss] ---> {{mmdd},{hhmu}};
     *
     * @param formatTime
     * @return 日期和时间数组
     */
    public static String[] getDateAndTimeFromFormat(String formatTime) {
        if (formatTime == null || "".equals(formatTime))
            return new String[]{null, null};
        StringTokenizer tokFormat = new StringTokenizer(formatTime, " ");
        // 年-月-日
        String strDate[] = null;
        // 小时-分钟-秒
        String strTime[] = null;
        while (tokFormat.hasMoreTokens()) {
            String strFormat = tokFormat.nextToken().trim();
            if (strFormat == null || strFormat.length() == 0)
                return null;
            // 日期
            if (strFormat.indexOf("-") > 0) {
                strDate = strFormat.split("-");
            }
            if (strFormat.indexOf(":") > 0) {
                strTime = strFormat.split(":");
            }
        }
        for (int i = 0; i < strDate.length; i++) {
            // System.out.println(strDate[i]);
            if (i == 0) {
                if (strDate[i].length() == 2)
                    strDate[i] = "20" + strDate[i];
            } else {
                if (strDate[i].length() < 2)
                    strDate[i] = "0" + strDate[i];
            }
        }
        for (int i = 0; i < strTime.length; i++) {
            // System.out.println(strTime[i]);
            if (strDate[i].length() < 2)
                strDate[i] = "0" + strDate[i];
        }
        String mmdd = strDate[0] + "-" + strDate[1] + "-" + strDate[2];
        String hhmu = strTime[0] + ":" + strTime[1] + ":" + strTime[2];
        return new String[]{mmdd, hhmu};
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss字符串转换成Date对象
     *
     * @param time String
     * @return Date
     */
    public static Date getDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(chinaFormat);
        return sdf.parse(time, new ParsePosition(0));
    }

    /**
     * 把yyyy-MM-dd HH:mm:ss字符串转换成long时间
     *
     * @param time String
     * @return Date
     */
    public static long getLongTime(String time) {
        return getDate(time).getTime();
    }

    /**
     * 得到一天的起始日期和结束日期
     *
     * @param time
     * @return String[2]{起始日期,结束日期}
     */
    public static String[] getDayBeginAndEnd(String time) {
        String shortTime = getShortDateFromFormat(time);
        return new String[]{(shortTime + " 00:00:00"),
                (shortTime + " 23:59:59")};
    }

    /**
     * 得到长度为一天的起始日期和结束日期，并指定起始时间。
     *
     * @param nowTime   当前日期
     * @param startTime 起始时间
     * @return String[2]{起始日期（指定了起始时间）,结束日期}
     */
    public static String[] getDayBeginAndEnd(String nowTime, String startTime) {// TODO
        // refactor
        // this
        // method
        if ((startTime == null) || (0 == startTime.length())) {
            startTime = "00:00:00";
        }

        String shortDate = getShortDateFromFormat(nowTime);

        Date consultDate = getDate(shortDate + " " + startTime);
        Date nowDate = getDate(nowTime);

        String[] times = new String[2];
        // 当前日期大于等于参照起始时间，结束时间为第二天的起始时间
        if (nowDate.compareTo(consultDate) >= 0) {
            times[0] = shortDate + " " + startTime;
            times[1] = addDate(shortDate, 1, dateFormat) + " " + startTime;
            // times[2] = shortDate;
        } else {
            times[0] = addDate(shortDate, -1, dateFormat) + " " + startTime;
            times[1] = shortDate + " " + startTime;
            // times[2] = shortDate;
        }
        return times;
    }

    /**
     * 给某一时间加减一相对天数 time 格式必须为"yyyyMMdd"
     *
     * @param time    String
     * @param daynum_ int
     * @return String
     */
    public static String addDate(String time, int daynum_) {
        SimpleDateFormat sdf = new SimpleDateFormat(chinaFormat);
        Date dt = sdf.parse(time, new ParsePosition(0));
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DATE, daynum_);// 你要加减的日期
        Date dt1 = rightNow.getTime();
        return sdf.format(dt1);
    }

    /**
     * 给某一时间加减一相对天数 时间格式必须为"yyyyMMdd"
     *
     * @param time    String
     * @param daynum_ int
     * @param format  String
     * @return String
     */
    public static String addDate(String time, int daynum_, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date dt = sdf.parse(time, new ParsePosition(0));
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.DATE, daynum_);// 你要加减的日期
        Date dt1 = rightNow.getTime();
        return sdf.format(dt1);
    }

    /**
     * 获取在当前时间上加减一相对天数的时间
     *
     * @param daynum_
     * @return String
     */
    public static String getRelativeDate(int daynum_) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE, daynum_);// 你要加减的日期
        Date dt = rightNow.getTime();
        return sdf.format(dt);

    }

    /**
     * 获取在当前时间上加减一相对天数的日期
     *
     * @param daynum_ int
     * @return Date
     */
    public static Date returnRelativeDate(int daynum_) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DATE, daynum_);// 你要加减的日期
        Date dt = rightNow.getTime();
        return dt;
    }

    /**
     * 获取在当前时间上加减一相对月数的日期
     *
     * @param monthnum_ int
     * @return String
     */
    public static String getRelativeMonth(int monthnum_) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH, monthnum_);// 你要加减的日期
        Date dt1 = rightNow.getTime();
        return sdf.format(dt1);

    }

    /**
     * 获取在当前时间上加减一相对月数的日期
     *
     * @param monthnum_ int
     * @return Date
     */
    public static Date returnRelativeMonth(int monthnum_) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.MONTH, monthnum_);// 你要加减的日期
        Date dt = rightNow.getTime();
        return dt;

    }

    /**
     * 得到给定日期是本月的第几周星期
     *
     * @param time
     * @return int
     */
    public static int getWeekInMonth(String time) {
        Date currentDate = getDate(time);
        SimpleDateFormat formater = new SimpleDateFormat("FF");
        return Integer.parseInt(formater.format(currentDate));
    }

    /**
     * 得到给定日期是本年的第几周数
     *
     * @param time
     * @return int
     */
    public static int getWeekInYear(String time) {
        Date currentDate = getDate(time);
        SimpleDateFormat formater = new SimpleDateFormat("ww");
        return Integer.parseInt(formater.format(currentDate));
    }

    /**
     * 得到给定日期是本月的第几周星期数
     *
     * @param time
     * @return int
     */
    public static String getDayInWeek(String time) {
        Date currentDate = getDate(time);
        SimpleDateFormat formater = new SimpleDateFormat("EE");
        return formater.format(currentDate);
    }

    /**
     * 求给定 某年、某月的最大天数.例如getDaysOfMonth(2000,1)范围31,getDaysOfMonth(2000,2)返回28
     *
     * @param year  年
     * @param month 月
     * @return 给定年、月的最大天数(1月返回31,2月返回28或29,3月返回31,...,12月返回31)
     */
    static public int getDaysOfMonth(int year, int month) {
        return (int) ((Time.toLongTime(month == 12 ? (year + 1) : year,
                month == 12 ? 1 : (month + 1), 1) - Time.toLongTime(year,
                month, 1)) / (24 * 60 * 60 * 1000));
    }

    /**
     * @param date yyyy-mm-dd hh:mu:ss
     * @return ?年?月?日
     */
    public static String toChinese(String date) {
        String[] chChart = new String[]{"年", "月", "日"};
        if (date == null) {
            return "?年?月?日";
        }
        String shortDate = getShortDateFromFormat(date);
        if (shortDate == null || shortDate.indexOf("-") < 0) {
            return "?年?月?日";
        }
        String[] splicDate = shortDate.split("-");
        String chinaDate = "";
        for (int i = 0; i < splicDate.length && i < 3; i++) {
            chinaDate += splicDate[i] + chChart[i];
        }
        return chinaDate;
    }

    /**
     * 从给定的 year,mongth,day 得到时间的long值表示(a point in time that is <tt>time</tt>
     * milliseconds after January 1, 1970 00:00:00 GMT).
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return 给定的 year,mongth,day 得到时间的long值表示
     */
    public static long toLongTime(int year, int month, int day) {
        // return toDate(year,month,day).getTime();
        return toLongTime(year, month, day, 0, 0, 0);
    }

    /**
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @return 时间的long值表示
     */
    public static long toLongTime(int year, int month, int day, int hour,
                                  int min, int sec) {
        Calendar staticCal = null;
        if (staticCal == null)
            staticCal = Calendar.getInstance();// new GregorianCalendar();
        staticCal.clear();
        staticCal.set(Calendar.YEAR, year);
        staticCal.set(Calendar.MONTH, month - 1);
        staticCal.set(Calendar.DAY_OF_MONTH, day); // day-1??
        staticCal.set(Calendar.HOUR_OF_DAY, hour);
        staticCal.set(Calendar.MINUTE, min);
        staticCal.set(Calendar.SECOND, sec);
        return staticCal.getTime().getTime();
        // return toDate(year,month,day).getTime();
    }

    /**
     * @param year
     * @param month
     * @return 该月双休日的天数
     */
    static public int getDefaultHolidays(int year, int month) {
        GregorianCalendar cal = new GregorianCalendar(year, month - 1, 1);
        // System.out.println("cal="+cal.);
        int x = 0;
        for (int d = 0; ; d++) {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
                x |= (1 << d);
            // System.out.println("d="+(d+1)+",dayOfWeek="+dayOfWeek);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            if (cal.get(Calendar.MONTH) + 1 != month)
                break;
        }
        return x;
    }

    /**
     * 得到当前日期所在月的第一天和最后一天时间
     *
     * @param time
     * @return 当前日期所在月的第一天和最后一天时间
     */
    public static String[] getMonthFirstAndLast(String time) {
        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        String fromDate = YMD[0] + "-" + YMD[1] + "-01 00:00:00";
        int maxDayInMonth = getDaysOfMonth(Integer.parseInt(YMD[0]), Integer
                .parseInt(YMD[1]));
        String toDate = YMD[0] + "-" + YMD[1] + "-" + maxDayInMonth
                + " 23:59:59";
        return new String[]{fromDate, toDate};
    }

    /**
     * 得到当前日期所在月的第一天和最后一天时间
     *
     * @param time
     * @param monthBegin
     * @param beginTime
     * @return String[2]{起始日期（指定了起始时间）,结束日期}
     */
    public static String[] getMonthBeginAndEnd(String time, String monthBegin,
                                               String beginTime) {// TODO refactor this method
        int iMonBegin = Integer.parseInt(monthBegin);

        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        int year = Integer.parseInt(YMD[0]);
        int month = Integer.parseInt(YMD[1]);
        int day = Integer.parseInt(YMD[2]);

        Date consultDate = getDate(getFormatDate(year, month, iMonBegin) + " "
                + beginTime);
        Date nowDate = getDate(time);

        String[] times = new String[2];

        // 当前日期正好是查询起始日期，根据时间判断是本月还是上月数据
        if (day == iMonBegin) {
            // 向后一个月
            if (nowDate.compareTo(consultDate) >= 0) {
                times[0] = shortTime + " " + beginTime;
                // 如果当前月是12月，结束时间为下一年一月
                if (month == 12) {
                    year += 1;
                    month = 1;
                } else {
                    month++;
                }
                int toMaxDayInMonth = getDaysOfMonth(year, month);
                // 如果开始日期大于本月的最大天数，取其为最大天数
                if (iMonBegin > toMaxDayInMonth) {
                    iMonBegin = toMaxDayInMonth;
                }
                times[1] = getFormatDate(year, month, iMonBegin) + " "
                        + beginTime;
            }
            // 向前一个月
            else {
                // 如果是一月，向前一个为前年12月
                if (month == 1) {
                    year -= 1;
                    month = 12;
                } else
                    month--;
                int toMaxDayInMonth = getDaysOfMonth(year, month);
                if (iMonBegin > toMaxDayInMonth) {
                    iMonBegin = toMaxDayInMonth;
                }
                times[0] = getFormatDate(year, month, iMonBegin) + " "
                        + beginTime;
                times[1] = shortTime + " " + beginTime;
            }
        } else if (day > iMonBegin) {
            times[0] = getFormatDate(year, month, iMonBegin) + " " + beginTime;
            // 如果当前月是12月，结束时间为下一年一月
            if (month == 12) {
                year += 1;
                month = 1;
            } else
                month++;
            int toMaxDayInMonth = getDaysOfMonth(year, month);
            // 如果开始日期大于本月的最大天数，取其为最大天数
            if (iMonBegin > toMaxDayInMonth) {
                iMonBegin = toMaxDayInMonth;
            }
            times[1] = getFormatDate(year, month, iMonBegin) + " " + beginTime;
        } else {
            times[1] = getFormatDate(year, month, iMonBegin) + " " + beginTime;

            if (month == 1) {
                year -= 1;
                month = 12;
            } else {
                month--;
            }
            int toMaxDayInMonth = getDaysOfMonth(year, month);
            if (iMonBegin > toMaxDayInMonth) {
                iMonBegin = toMaxDayInMonth;
            }
            times[0] = getFormatDate(year, month, iMonBegin) + " " + beginTime;
        }
        return times;
    }

    /**
     * 得到当有日期所在季度的起始日期和结束日期
     *
     * @param time
     * @param quarterBegin
     * @param beginTime
     * @return 所在季度的起始日期和结束日期
     */
    public static String[] getQuarterBeginAndEnd(String time,
                                                 String quarterBegin, String beginTime) {
        String[] consult = quarterBegin.split(",");
        // 一个季度的第几个月
        int conMonth = Integer.parseInt(consult[0]);
        int conDay = Integer.parseInt(consult[1]);

        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        int year = Integer.parseInt(YMD[0]);
        int month = Integer.parseInt(YMD[1]);
        // int day = Integer.parseInt(YMD[2]);

        // 当前所在日期所属季度的起始月份
        int startMonth = getQuarterStartMonth(month);

        // 参考日期的月份
        int beginMonth = startMonth + conMonth - 1;

        // 参考日期的日数
        int beginDay = conDay;
        int conMaxDayInMonth = getDaysOfMonth(year, beginMonth);
        if (beginDay > conMaxDayInMonth) {
            beginDay = conMaxDayInMonth;
        }

        String strConsult = getFormatDate(year, beginMonth, beginDay);
        // 参考时间
        Date consultDate = getDate(strConsult + " " + beginTime);
        // 当前时间
        Date nowDate = getDate(time);

        String[] times = new String[2];

        // 如果当前时间大于参考时间
        if (nowDate.compareTo(consultDate) >= 0) {
            times[0] = strConsult + " " + beginTime;
            int toYear = year;
            int toMonth = beginMonth + 3;
            int toDay = conDay;
            int diff = toMonth - 12;
            if (diff > 0) {
                toYear += 1;
                toMonth = diff;
            }
            int toMaxDayInMonth = getDaysOfMonth(toYear, toMonth);
            if (toDay > toMaxDayInMonth) {
                toDay = toMaxDayInMonth;
            }
            times[1] = getFormatDate(toYear, toMonth, toDay) + " " + beginTime;
        } else {
            times[1] = strConsult + " " + beginTime;
            int fromYear = year;
            int fromMonth = beginMonth - 3;
            int fromDay = conDay;
            if (fromMonth < 1) {
                fromYear -= 1;
                fromMonth = 12 + fromMonth;
            }
            int fromMaxDayInMonth = getDaysOfMonth(fromYear, fromMonth);
            if (fromDay > fromMaxDayInMonth) {
                fromDay = fromMaxDayInMonth;
            }

            times[0] = getFormatDate(fromYear, fromMonth, fromDay) + " "
                    + beginTime;
        }
        return times;
    }

    /**
     * 得到一个月份所在季度的起始月份
     *
     * @param month
     * @return 所在季度的起始月份
     */
    public static int getQuarterStartMonth(int month) {
        if (month >= 10)
            return 10;
        else if (month >= 7)
            return 7;
        else if (month >= 4)
            return 4;
        else
            return 1;
    }

    /**
     * 得到当前日期所在半年的第一天和最后一天时间
     *
     * @param time
     * @return 当前日期所在半年的第一天和最后一天时间
     */
    public static String[] getHalfYearFirstAndLast(String time) {
        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        String fromDate = null;
        String toDate = null;
        if (6 >= Integer.parseInt(YMD[1])) {
            fromDate = YMD[0] + "-" + "01-01 00:00:00";
            toDate = YMD[0] + "-" + "06-30 23:59:59";
        } else {
            fromDate = YMD[0] + "-" + "07-01 00:00:00";
            toDate = YMD[0] + "-" + "12-31 23:59:59";
        }
        return new String[]{fromDate, toDate};
    }

    /**
     * 得到当前日期所在半年的第一天和最后一天时间
     *
     * @param time
     * @param halfYearBegin
     * @param beginTime
     * @return 所在半年的第一天和最后一天时间
     */
    public static String[] getHalfYearBeginAndEnd(String time,
                                                  String halfYearBegin, String beginTime) {
        String[] consult = halfYearBegin.split(",");
        // 一个半年的第几个月
        int conMonth = Integer.parseInt(consult[0]);
        int conDay = Integer.parseInt(consult[1]);

        // 当前日期
        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        int year = Integer.parseInt(YMD[0]);
        int month = Integer.parseInt(YMD[1]);
        // int day = Integer.parseInt(YMD[2]);

        // 当前所在日期所属半年的起始月份
        int startMonth = 1;
        if (month >= 7)
            startMonth = 7;

        // 参考日期的月份
        int beginMonth = startMonth + conMonth - 1;

        // 参考日期的日数
        int beginDay = conDay;
        int conMaxDayInMonth = getDaysOfMonth(year, beginMonth);
        if (beginDay > conMaxDayInMonth) {
            beginDay = conMaxDayInMonth;
        }

        String strConsult = getFormatDate(year, beginMonth, beginDay);
        // 参考时间
        Date consultDate = getDate(strConsult + " " + beginTime);
        // 当前时间
        Date nowDate = getDate(time);

        String[] times = new String[2];

        // 如果当前时间大于参考时间
        if (nowDate.compareTo(consultDate) >= 0) {
            times[0] = strConsult + " " + beginTime;
            int toYear = year;
            int toMonth = beginMonth + 6;
            int toDay = conDay;
            int diff = toMonth - 12;
            if (diff > 0) {
                toYear += 1;
                toMonth = diff;
            }
            int toMaxDayInMonth = getDaysOfMonth(toYear, toMonth);
            if (toDay > toMaxDayInMonth) {
                toDay = toMaxDayInMonth;
            }
            times[1] = getFormatDate(toYear, toMonth, toDay) + " " + beginTime;
        } else {
            times[1] = strConsult + " " + beginTime;
            int fromYear = year;
            int fromMonth = beginMonth - 6;
            int fromDay = conDay;
            if (fromMonth < 1) {
                fromYear -= 1;
                fromMonth = 12 + fromMonth;
            }
            int fromMaxDayInMonth = getDaysOfMonth(fromYear, fromMonth);
            if (fromDay > fromMaxDayInMonth) {
                fromDay = fromMaxDayInMonth;
            }

            times[0] = getFormatDate(fromYear, fromMonth, fromDay) + " "
                    + beginTime;
        }
        return times;
    }

    /**
     * 得到当前日期所在年的第一天和最后一天时间
     *
     * @param time
     * @return 当前日期所在年的第一天和最后一天时间
     */
    public static String[] getYearFirstAndLast(String time) {
        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        String fromDate = YMD[0] + "-" + "01-01 00:00:00";
        String toDate = YMD[0] + "-" + "12-31 23:59:59";
        // System.err.println("fromDate -->" + fromDate);
        // System.err.println("toDate -->" + toDate);
        return new String[]{fromDate, toDate};
    }

    /**
     * 得到当前日期所在年的第一天和最后一天时间
     *
     * @param time
     * @param yearBegin
     * @param beginTime
     * @return 所在年的第一天和最后一天时间
     */
    public static String[] getYearBeginAndEnd(String time, String yearBegin,
                                              String beginTime) {
        String[] consult = yearBegin.split(",");
        // 一个半年的第几个月
        int conMonth = Integer.parseInt(consult[0]);
        int conDay = Integer.parseInt(consult[1]);

        // 当前日期
        String shortTime = getShortDateFromFormat(time);
        String[] YMD = shortTime.split("-");
        int year = Integer.parseInt(YMD[0]);
        // int month = Integer.parseInt(YMD[1]);
        // int day = Integer.parseInt(YMD[2]);

        // 参考日期的月份
        int beginMonth = conMonth;

        // 参考日期的日数
        int beginDay = conDay;
        int conMaxDayInMonth = getDaysOfMonth(year, beginMonth);
        if (beginDay > conMaxDayInMonth) {
            beginDay = conMaxDayInMonth;
        }

        String strConsult = getFormatDate(year, beginMonth, beginDay);
        // 参考时间
        Date consultDate = getDate(strConsult + " " + beginTime);
        // 当前时间
        Date nowDate = getDate(time);

        String[] times = new String[2];

        // 如果当前时间大于参考时间
        if (nowDate.compareTo(consultDate) >= 0) {
            times[0] = strConsult + " " + beginTime;
            int toYear = year;
            int toMonth = beginMonth + 12;
            int toDay = conDay;
            int diff = toMonth - 12;
            if (diff > 0) {
                toYear += 1;
                toMonth = diff;
            }
            int toMaxDayInMonth = getDaysOfMonth(toYear, toMonth);
            if (toDay > toMaxDayInMonth) {
                toDay = toMaxDayInMonth;
            }
            times[1] = getFormatDate(toYear, toMonth, toDay) + " " + beginTime;
        } else {
            times[1] = strConsult + " " + beginTime;
            int fromYear = year;
            int fromMonth = beginMonth - 12;
            int fromDay = conDay;
            if (fromMonth < 1) {
                fromYear -= 1;
                fromMonth = 12 + fromMonth;
            }
            int fromMaxDayInMonth = getDaysOfMonth(fromYear, fromMonth);
            if (fromDay > fromMaxDayInMonth) {
                fromDay = fromMaxDayInMonth;
            }

            times[0] = getFormatDate(fromYear, fromMonth, fromDay) + " "
                    + beginTime;
        }
        return times;
    }

    /**
     * 得到传入日期所在的周的起始日期和终止日期
     *
     * @param time
     * @return 传入日期所在的周的起始日期和终止日期
     */
    public static String[] getWeekFirstAndLast(String time) {
        String fromatTime = getShortDateFromFormat(time) + " 00:00:00";
        String dayForWeek = getDayInWeek(fromatTime);
        int from = 0;
        int to = 0;
        if ("星期日".equals(dayForWeek)) {
            from = 0;
            to = 7;
        } else if ("星期一".equals(dayForWeek)) {
            from = 1;
            to = 6;
        } else if ("星期二".equals(dayForWeek)) {
            from = 2;
            to = 5;
        } else if ("星期三".equals(dayForWeek)) {
            from = 3;
            to = 4;
        } else if ("星期四".equals(dayForWeek)) {
            from = 4;
            to = 3;
        } else if ("星期五".equals(dayForWeek)) {
            from = 5;
            to = 2;
        } else if ("星期六".equals(dayForWeek)) {
            from = 6;
            to = 1;
        }
        String fromDate = addDate(fromatTime, (-1 * from));
        String toDate = addDate(fromatTime, to);
        return new String[]{fromDate,
                (getShortDateFromFormat(toDate) + " 23:59:59")};
    }

    /**
     * 得到当前传入日期所在的周的起始日期 和终止日期
     *
     * @param time
     * @param weekBegin 起始日
     * @param beginTime 起始时间
     * @return String[2]{起始日期（指定了起始时间）,结束日期}
     */
    public static String[] getWeekBeginAndEnd(String time, String weekBegin,
                                              String beginTime) {// TODO refactor this method
        String shortDate = getShortDateFromFormat(time);
        Date consultDate = getDate(shortDate + " " + beginTime);
        Date nowDate = getDate(time);
        int begin = Integer.parseInt(weekBegin);
        String dayForWeek = getDayInWeek(time);

        int from = 0;
        int to = 0;
        if ("星期日".equals(dayForWeek)) {
            from = 0;
            to = 7;
        } else if ("星期一".equals(dayForWeek)) {
            from = 1;
            to = 6;
        } else if ("星期二".equals(dayForWeek)) {
            from = 2;
            to = 5;
        } else if ("星期三".equals(dayForWeek)) {
            from = 3;
            to = 4;
        } else if ("星期四".equals(dayForWeek)) {
            from = 4;
            to = 3;
        } else if ("星期五".equals(dayForWeek)) {
            from = 5;
            to = 2;
        } else if ("星期六".equals(dayForWeek)) {
            from = 6;
            to = 1;
        }

        // 起始日期正好是当前日期，根据时间来判断所需要查询数据是哪一周数据
        if (from == begin) {
            // 如果当前精确时间大于参考时间，表示从当前天开始向后一周为统计段
            if (nowDate.compareTo(consultDate) >= 0) {
                from = 0;
                to = 7;
            } else {
                from = 7;
                to = 0;
            }
        } else if (from > begin) {
            from = from - begin;
            to = 7 - from;
        } else {
            to = begin - from;
            from = 7 - to;
        }

        String fromDate = addDate(shortDate, (-1 * from), dateFormat);
        String toDate = addDate(shortDate, to, dateFormat);
        return new String[]{fromDate + " " + beginTime,
                toDate + " " + beginTime};
    }

    /**
     * 得到日期格式的字符串
     *
     * @param year
     * @param month
     * @param day
     * @return 日期格式的字符串
     */
    private static String getFormatDate(int year, int month, int day) {
        String strYear = String.valueOf(year);
        String strMonth = String.valueOf(month);
        String strDay = String.valueOf(day);
        if (strYear.length() == 2)
            strYear = "20" + strYear;
        if (strMonth.length() < 2)
            strMonth = "0" + strMonth;
        if (strDay.length() < 2)
            strDay = "0" + strDay;
        return strYear + "-" + strMonth + "-" + strDay;
    }

    /**
     * 得到当前月的第一天
     *
     * @return 当前月的第一天
     */
    public static String getTheMonthFirstDay() {
        String strMonth = null;
        int month = getMonth();
        if (month < 10)
            strMonth = "0" + month;
        else
            strMonth = "" + month;
        return getYear() + "-" + strMonth + "-" + "01 00:00:00";
    }

    /**
     * 得到两个日期相差小时数
     *
     * @param endDate
     * @param beginDate
     * @return 两个日期相差小时数
     */
    public static long diffHour(Date endDate, Date beginDate) {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);

        long diff = endCalendar.getTimeInMillis()
                - beginCalendar.getTimeInMillis();
        return diff / HOUR;

    }

    /**
     * @param formatDate yyyy-mm-dd hh:mu:ss
     * @return String
     */
    public static String getChinaDate(String formatDate) {
        String[] chChart = new String[]{"年", "月", "日"};
        if (formatDate == null)
            return "?年?月?日";
        String shortDate = getShortDateFromFormat(formatDate);
        if (shortDate == null || shortDate.indexOf("-") < 0)
            return "?年?月?日";
        String[] splicDate = shortDate.split("-");
        String chinaDate = "";
        for (int i = 0; i < splicDate.length && i < 3; i++) {
            chinaDate += splicDate[i] + chChart[i];
        }
        return chinaDate;
    }

    /**
     * 得到两个日期之间的年月信息
     *
     * @param beginDate
     * @param endDate
     * @return String[]
     */
    public static String[] getYearAndMonthCount(String beginDate, String endDate) {
        if (beginDate == null)
            beginDate = getTime();
        if (endDate == null)
            endDate = getTime();
        int[] beginDates = getYYMMDDHHMISS(beginDate);
        int[] endDates = getYYMMDDHHMISS(endDate);
        int year = beginDates[0];
        int month = beginDates[1];
        int monthCount = (endDates[0] - year) * 12 + (endDates[1] - month);

        if (monthCount <= 0)
            return new String[]{getYear() + "" + getMonth()};

        String[] yearAndMonths = new String[monthCount + 1];

        for (int i = 0; i <= monthCount; i++) {
            if (month > 12) {
                month = 1;
                year++;
            }
            yearAndMonths[i] = year + "" + month;
            month++;
        }
        return yearAndMonths;
    }

    /**
     * 得到指定日期的年，月，日，小时，分钟，秒
     *
     * @param date 字符串可以是(yyyy-MM-dd hh:mm:ss)或（yyyy-MM-dd)
     * @return int
     */
    public static int[] getYYMMDDHHMISS(String date) {
        int[] iYYMMDDHHMISS = new int[]{0, 0, 0, 0, 0, 0};
        String[] dates = date.trim().split(" ");
        String[] days = dates[0].split("-");
        for (int i = 0; i < days.length; i++) {
            iYYMMDDHHMISS[i] = Integer.parseInt(days[i]);
        }
        if (dates.length == 2) {
            String[] times = dates[1].split(":");
            for (int i = 0; i < times.length; i++) {
                iYYMMDDHHMISS[i + 3] = Integer.parseInt(times[i]);
            }
        }
        return iYYMMDDHHMISS;
    }

    /**
     * 时间格式转换
     *
     * @param timebase yyyyMM形式的时间
     * @return 时间的毫秒表示
     *  
     */
    public static long getMillByyyyyMM(String timebase) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
        Date date;
        try {
            date = formater.parse(timebase);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;

    }

    /**
     * 时间格式转换
     *
     * @param fullTime 毫秒形式的时间
     * @return 去除了分钟(包含)及其以下时间的毫秒形式时间.如果解析出错,返回0L
     */
    public static long getNoMinuteMillByMill(long fullTime) {
        long hourTimeInMill = 0L;
        DateFormat hourFormat = new SimpleDateFormat("yyyyMMdd HH");
        try {
            hourTimeInMill = (hourFormat.parse(hourFormat.format(new Date(
                    fullTime)))).getTime();
        } catch (ParseException e) {
        }
        return hourTimeInMill;
    }

    /**
     * 时间格式转换
     *
     * @param timeInMill 时间的毫秒表示
     * @return yyyyMM形式的时间
     *  
     */
    public static String getyyyyMMByMill(long timeInMill) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMM");
        Date date = new Date(timeInMill);
        return formater.format(date);
    }

    private static DateFormat getyyyyMMddFormat() {
        return new SimpleDateFormat("yyyyMMdd");
    }

    /**
     * 时间格式转换
     *
     * @param timebase yyyyMM形式的时间
     * @return 时间的毫秒表示
     *  
     */
    public static long getMillByyyyyMMdd(String timebase) {
        DateFormat formater = getyyyyMMddFormat();
        Date date;
        try {
            date = formater.parse(timebase);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;

    }

    /**
     * 时间格式转换
     *
     * @param timeInMill 时间的毫秒表示
     * @return yyyyMMdd形式的时间
     *  
     */
    public static String getyyyyMMddByMill(long timeInMill) {
        DateFormat formater = getyyyyMMddFormat();
        Date date = new Date(timeInMill);
        return formater.format(date);
    }

    private static DateFormat getyyyyMMddHHmmssFormat() {
        return new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     * 时间格式转换
     *
     * @param timebase yyyyMMddHHmmss形式的时间
     * @return 时间的毫秒表示
     *  
     */
    public static long getMillByyyyyMMddHHmmss(String timebase) {
        DateFormat formater = getyyyyMMddHHmmssFormat();
        Date date;
        try {
            date = formater.parse(timebase);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;

    }

    /**
     * 时间格式转换
     *
     * @param timeInMill 时间的毫秒表示
     * @return yyyyMMddHHmmss形式的时间
     *  
     */
    public static String getyyyyMMddHHmmFormatByMill(long timeInMill) {
        DateFormat formater = getyyyyMMddHHmmFormatFormat();
        Date date = new Date(timeInMill);
        return formater.format(date);
    }

    /**
        * 时间格式转换
        *
        * @param timeInMill 时间的毫秒表示
        * @return yyyyMMddHHmmss形式的时间
        *  
        */
       public static String getddHHmmFormatByMill(long timeInMill) {
           DateFormat formater = getddHHmmFormatFormat();
           Date date = new Date(timeInMill);
           return formater.format(date);
       }

    private static DateFormat getddHHmmFormatFormat() {
            return new SimpleDateFormat("dd HH:mm");
        }

    private static DateFormat getyyyyMMddHHmmFormatFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }


    public static String getDateTime(Date date,String formate){
    	SimpleDateFormat fromate = new SimpleDateFormat(formate);
    	return fromate.format(date);
    }
    
    /**
     * 时间格式转换
     *
     * @param timeInMill 时间的毫秒表示
     * @return yyyyMMddHHmmss形式的时间
     *  
     */
    public static String getyyyyMMddHHmmssByMill(long timeInMill) {
        DateFormat formater = getyyyyMMddHHmmssFormat();
        Date date = new Date(timeInMill);
        return formater.format(date);
	}
	


    private static DateFormat getyyyyMMddHHmmFormat() {
		return new SimpleDateFormat("yyyyMMddHHmm");
	}

	/**
     * 时间格式转换
     *
     * @param timebase yyyyMMddHHmm形式的时间
     * @return 时间的毫秒表示
     *  
     */
	public static long getMillByyyyyMMddHHmm(String timebase) {
		DateFormat formater = getyyyyMMddHHmmFormat();
		Date date;
		try {
			date = formater.parse(timebase);
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0L;

	}

	/**
     * 时间格式转换
     *
     * @param timeInMill 时间的毫秒表示
     * @return yyyyMMddHHmm形式的时间
     *  
     */
	public static String getyyyyMMddHHmmByMill(long timeInMill) {
		DateFormat formater = getyyyyMMddHHmmFormat();
		Date date = new Date(timeInMill);
		return formater.format(date);
	}
}