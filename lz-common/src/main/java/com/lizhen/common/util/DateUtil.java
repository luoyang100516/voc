package com.lizhen.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换类 转换一个 java.util.Date 对象到一个字符串以及 一个字符串到一个 java.util.Date 对象.
 *
 * @author zhangtengda
 */
public class DateUtil {
    public static final long SECOND = 1000;

    public static final long MINUTE = SECOND * 60;

    public static final long HOUR = MINUTE * 60;

    public static final long DAY = HOUR * 24;

    public static final long WEEK = DAY * 7;

    public static final long MONTH = DAY * 30;

    public static final long YEAR = DAY * 365;

    public static final String TYPE_DATE = "date";

    public static final String TYPE_TIME = "time";

    public static final String TYPE_DATETIME = "datetime";

    /**
     * 模式:yyyy-MM-dd HH:mm
     */
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm";

    /**
     * 模式:yyyy-MM-dd
     */
    public static final String PATTERN_DATE = ":yyyy-MM-dd";

    private static final String DEFAULT_PATTERN = PATTERN_DATETIME;

    public static final String[] TYPE_ALL = {TYPE_DATE, TYPE_DATETIME, TYPE_TIME};

    /**
     * 将字符串转换为Date类型
     *
     * @param strDate
     * @param pattern 格式
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate, String pattern) {

        if (StringUtils.isBlank(strDate)) {
            return null;
        }

        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 将Date转换为字符串
     *
     * @param aDate
     * @param pattern 格式
     * @return
     */
    public static String convertDateToString(Date aDate, String pattern) {
        if (aDate == null) {
            return "";
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = DEFAULT_PATTERN;
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(aDate);
    }

    /**
     * 将Date转换为字符串
     *
     * @param aDate
     * @param type
     *            见本类中的TYPE_*常量
     * @param locale
     * @return
     *//*
     * public static String convertDateToString(Date aDate, String type,
     * Locale locale) { if (aDate == null) return null; if (type == null)
     * type = TYPE_DATETIME; String pattern =
     * ResourceUtil.getString("date.format." + type, locale); return
     * convertDateToString(aDate, pattern); }
     */

    /**
     * 将日期、时间合并成长整型数据
     *
     * @param date 日期
     * @param time 时间
     * @return
     */
    public static long getDateTimeNumber(Date date, Date time) {
        Calendar dateCal = getCalendar(date);
        Calendar timeCal = getCalendar(time);
        dateCal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        dateCal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        dateCal.set(Calendar.SECOND, timeCal.get(Calendar.SECOND));
        dateCal.set(Calendar.MILLISECOND, timeCal.get(Calendar.MILLISECOND));
        return dateCal.getTimeInMillis();
    }

    /**
     * 将日期的时间部分清除后，转换成long类型
     *
     * @param date
     * @return
     */
    public static long getDateNumber(Date date) {
        return removeTime(date).getTimeInMillis();
    }

    /**
     * 获取日期(获取当天日期getDate(0))
     *
     * @param day
     * @return
     */
    public static Date getDate(int day) {
        Calendar cal = getCalendar(new Date());
        cal.add(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * 将一个不包含日期的时间量，转换为Date类型，其中的日期为当天
     *
     * @param l
     * @return
     */
    public static Date getTimeByNubmer(long l) {
        return new Date(getDateNumber(new Date()) + l);
    }

    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return cal;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Calendar removeTime(Date date) {
        if (date == null) {
            return null;
        }

        Calendar cal = getCalendar(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * 计算两个日期的月份差值
     * @param date1
     * @param date2
     */
    public static int getDifferenceValueOfMonth(Date date1,Date date2){
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        int result = calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH);
        int month = (calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR)) * 12;
        return month + result;
    }

    /**
     * 本周一0点
     * @return
     */
    public static Date getTimesWeekStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 本周日24点
      * @return
     */
    public static Date getTimesWeekEnd() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekStart());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }


}
