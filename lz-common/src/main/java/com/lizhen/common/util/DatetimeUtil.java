package com.lizhen.common.util;

import com.google.common.collect.Lists;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;

public class DatetimeUtil {

    public final static String DATETIME_PATTERN = "yyyyMMddHHmmss";

    public final static String TIME_STAMP_PATTERN = "yyyyMMddHHmmssSSS";

    public final static String DATE_PATTERN = "yyyyMMdd";

    public final static String TIME_PATTERN = "HHmmss";

    public final static String STANDARD_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String STANDARD_DATETIME_PATTERN_HM = "yyyy-MM-dd HH:mm";

    public final static String STANDARD_DATE_PATTERN = "yyyy-MM-dd";

    public final static String STANDARD_OF_DATE = "yyyy年MM月dd日";

    public final static String STANDARD_TIME_PATTERN = "HH:mm:ss";

    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS = "yyyy/MM/dd HH:mm:ss";

    public final static String STANDARD_DATETIME_PATTERN_SOLIDUS_HM = "yyyy/MM/dd HH:mm";

    public final static String STANDARD_DATE_PATTERN_SOLIDUS = "yyyy/MM/dd";

    private DatetimeUtil() {
        super();
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static String currentDatetime() {
        return formatDate(new Date());
    }

    public static Timestamp parseDate(String dateStr, String pattern) throws ParseException {
        Date d = DatetimeUtil.parse(dateStr, pattern);
        return new Timestamp(d.getTime());
    }

    public static Timestamp parseDate(String dateStr) throws ParseException {
        return parseDate(dateStr, STANDARD_DATE_PATTERN);
    }

    public static java.sql.Date parseSQLDate(String dateStr, String pattern) throws ParseException {
        Date d = parse(dateStr, pattern);
        return new java.sql.Date(d.getTime());
    }

    public static java.sql.Date parseSQLDate(String dateStr) throws ParseException {
        Date d = parse(dateStr, STANDARD_DATE_PATTERN);
        return new java.sql.Date(d.getTime());
    }

    public static Timestamp getFutureTime(int month) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, month);
        return new Timestamp(c.getTimeInMillis());
    }

    /**
     * 显示今天时间
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String today() {
        return formatDateTime(new Date());
    }

    public static String formatDate(Timestamp t) {
        return formatDate(new Date(t.getTime()));
    }

    public static String formatDate(Timestamp t, String pattern) {
        return formatDate(new Date(t.getTime()), STANDARD_DATE_PATTERN);
    }

    public static String formatDateTime(Timestamp t, String pattern) {
        return formatDate(new Date(t.getTime()), STANDARD_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return formatDate(date, STANDARD_DATE_PATTERN);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yy年MM月dd日
     */
    public static String formatOfDate(Date date) {
        return formatDate(date, STANDARD_OF_DATE);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, STANDARD_DATETIME_PATTERN);
    }

    /**
     * 格式化日期
     *
     * @param date    日期
     * @param pattern 格式
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 解析日期
     *
     * @param dateStr yyyy-MM-dd
     * @return
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, STANDARD_DATE_PATTERN);
    }


    public static Date parse(String dateStr, String pattern) {

        try {
            DateFormat format = new SimpleDateFormat(pattern);
            return format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 解析str是否为pattern时间格式的字符串
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isValidDate(String str, String pattern) {
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            // 设置lenient为false.
            // 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 将yy-MM-dd 转为 yy年MM月dd日
     *
     * @param str
     * @return
     */
    public static Date dateFormatTransfer(String str) {
        Date parse = parse(formatOfDate(parse(str)), STANDARD_OF_DATE);
        return parse;
    }

    /**
     * 获取当月的 天数
     */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据 年、月 获取对应的月份 的 天数
     */
    public static int getDaysByYearMonth(int year, int month) {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 根据日期 找到对应日期的 星期几
     */
    public static String getDayOfWeekByDate(String date) {
        String dayOfweek = "-1";
        try {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = myFormatter.parse(date);
            SimpleDateFormat formatter = new SimpleDateFormat("E");
            String str = formatter.format(myDate);
            dayOfweek = str;

        } catch (Exception e) {
            System.out.println("错误!");
        }
        return dayOfweek;
    }


    /**
     * 计算工作日
     *
     * @param currDateStr
     * @return
     * @throws ParseException
     */
    public static int getWorkDay(String currDateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = format.parse(currDateStr);
        LocalDateTime currDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        LocalDate currDate = currDateTime.toLocalDate();
        LocalDate now = LocalDate.now();

        if (now.getYear() < currDate.getYear()) {
            return 0;
        } else if (now.getYear() == currDate.getYear()) {
            if (now.getMonth().getValue() < currDate.getMonth().getValue()) {
                return 0;
            } else if (now.getMonth().getValue() == currDate.getMonth().getValue()) {
                LocalDate with = now.with(TemporalAdjusters.firstDayOfMonth());
                if (now.equals(with)) {
                    return 0;
                } else {
                    now = now.minusDays(1);
                }

            }
            return getDayByMonth(currDate.getMonth().getValue() == now.getMonth().getValue() ? now : currDate);
        } else {
            return getDayByMonth(currDate.with(TemporalAdjusters.lastDayOfMonth()));
        }


    }

    /**
     * 根据日期计算当月工作日
     *
     * @param currDate
     * @return
     */
    private static int getDayByMonth(LocalDate currDate) {
        int count;
        LocalDate firstDay = currDate.withDayOfMonth(1);
        if (firstDay.equals(currDate)) {
            return 0;
        }
        int weekOfMonth = currDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth());
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue();
        int lastDayOfWeek = currDate.getDayOfWeek().getValue();
        int firstWeekWorkDay = firstDayOfWeek < 6 ? (6 - firstDayOfWeek) : (firstDayOfWeek == 6 ? 0 : 5);
        int lastWeekWorkDay = 0;
        if (weekOfMonth >= 2) {
            lastWeekWorkDay = lastDayOfWeek == 7 ? 0 : (lastDayOfWeek) == 6 ? 5 : lastDayOfWeek;
        }
        int middleWorkDay = 0;
        if (weekOfMonth >= 3) {
            middleWorkDay = weekOfMonth - 2 > 0 ? (weekOfMonth - 2) * 5 : 0;
        }
        count = firstWeekWorkDay + lastWeekWorkDay + middleWorkDay;
        return count;
    }


    /**
     * 是否是工作日
     * @param entryDate
     * @return
     */
    public static boolean checkWorkDayStatus(LocalDate entryDate){
        if(entryDate.getDayOfWeek().getValue()<DayOfWeek.SATURDAY.getValue())
            return true;
        return false;
    }

    /**
     * 获取某月内所有日期，格式：yyyy-MM-dd
     * isPersonal:是否时个人;查询个人的，格式为YearMonth;团队统计的格式为:LocalDate
     *
     * @param entryDateStr
     */
    public static List<LocalDate> getDaysInterval(String entryDateStr, boolean isPersonal) {
        DateTimeFormatter formatter;
        YearMonth yearMonth;
        LocalDate firstDayOfMonth;
        if (isPersonal) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            yearMonth = YearMonth.parse(entryDateStr, formatter);
            firstDayOfMonth = LocalDate.of(yearMonth.getYear(), yearMonth.getMonthValue(), 1);
        } else {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            firstDayOfMonth = LocalDate.parse(entryDateStr, formatter);
            yearMonth = YearMonth.of(firstDayOfMonth.getYear(), firstDayOfMonth.getMonthValue());
        }

        List<LocalDate> list = Lists.newArrayList();
        LocalDate now = LocalDate.now();
        YearMonth nowYearMonth = YearMonth.of(now.getYear(), now.getMonthValue());
        //当月或以前
        if (yearMonth.isAfter(nowYearMonth)) {
            return Collections.emptyList();
        }
        //当月
        if (yearMonth.equals(nowYearMonth)) {
            if (firstDayOfMonth.equals(now)) {
                return Collections.emptyList();
            }
            long daysInterval = ChronoUnit.DAYS.between(firstDayOfMonth, now);
            for (int i = 0; i <= daysInterval; i++) {
                LocalDate previousDay = now.minusDays(i);
                if (previousDay.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                    list.add(previousDay);
                }
            }
            return list;
        }

        int dayOfMonth = firstDayOfMonth.lengthOfMonth();
        for (int i = 0; i < dayOfMonth; i++) {
            LocalDate previousDay = firstDayOfMonth.plusDays(i);
            if (previousDay.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                list.add(previousDay);
            }
        }
        return list;
    }

    /**
     * 获取时间段内工作日
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static int getWorkDays(String beginTime, String endTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(beginTime, formatter);
        LocalDate endDate = LocalDate.parse(endTime, formatter);
        LocalDate currentDate = LocalDate.now();
        int workDays = 0;
        if (beginDate.isAfter(currentDate)) {
            return 0;
        }
        if (endDate.isAfter(currentDate)) {
            endDate = currentDate;
        }

        long durations = ChronoUnit.DAYS.between(beginDate, endDate);

        for (int i = 0; i <= durations; i++) {
            LocalDate previousDay = beginDate.plusDays(i);
            if (previousDay.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue()) {
                workDays++;
            }
        }
        return workDays;
    }

    public static void main(String[] args) {
        String beginTime = "2018-01-31 10:30:20";
        String formatDate = getFormatDate(beginTime);
        System.err.println(formatDate);

    }

    /**
     * 格式化日期
     * @param sendTime
     * @return
     */
    public static String getFormatDate(String sendTime){
        LocalDateTime send = LocalDateTime.parse(sendTime,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDate currentDay = LocalDate.now();
        if(currentDay.equals(send.toLocalDate())){
            return send.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        }

        Year sendYear = Year.of(send.getYear());
        Year currentYear = Year.now();
        if(sendYear.equals(currentYear)){
            return MonthDay.of(send.getMonthValue(),send.getDayOfMonth()).format(DateTimeFormatter.ofPattern("MM-dd"));
        }

        return send.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }



}
