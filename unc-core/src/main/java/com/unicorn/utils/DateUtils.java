package com.unicorn.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 */
public final class DateUtils {
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";
    /**
     * 英文全称 如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 中文简写 如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文全称 如：2010年12月01日 23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日 HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日 HH时mm分ss秒SSS毫秒";

    private static String[] otherDateSpilt = new String[]{"/", "_", ".", ""};

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 根据预设格式返回当前日期
     *
     * @return
     */
    public static String getNow() {
        return format(new Date());
    }

    /**
     * 根据用户格式返回当前日期
     *
     * @param format
     * @return
     */
    public static String getNow(String format) {
        return format(new Date(), format);
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 强制转为日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date forceParse(String strDate) throws ParseException {

        for (String pattern : new String[]{
                FORMAT_FULL, FORMAT_LONG, "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", FORMAT_SHORT, "yyyy-MM"
                , FORMAT_FULL_CN, FORMAT_LONG_CN, "yyyy年MM月dd日 HH时mm分", "yyyy年MM月dd日 HH时", FORMAT_SHORT_CN, "yyyy年MM月", "yyyy年"}) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            try {
                return df.parse(strDate);
            } catch (ParseException e) {
                continue;
            }
        }

        for (String split : otherDateSpilt) {
            for (String pattern : new String[]{FORMAT_FULL, FORMAT_LONG, "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", FORMAT_SHORT, "yyyy-MM"}) {
                SimpleDateFormat df = new SimpleDateFormat(pattern.replaceAll("-", split));
                try {
                    return df.parse(strDate);
                } catch (ParseException e) {
                    continue;
                }
            }
        }
        if (strDate.length() == 4) {
            try {
                return new SimpleDateFormat("yyyy").parse(strDate);
            } catch (ParseException e) {
            }
        }
        throw new ParseException("无法将“" + strDate + "”转换为日期", 0);
    }

    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 判断日期是否在指定区间内
     *
     * @param date
     * @param startMonth
     * @param startDay
     * @param endMonth
     * @param endDay
     * @return
     */
    public static boolean dateBetween(Date date, Integer startMonth, Integer startDay, Integer endMonth, Integer endDay) {

        Calendar calendar = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);

        startDate.set(year, startMonth - 1, startDay, 0, 0, 0);
        endDate.set(year, endMonth - 1, endDay + 1, 0, 0, 0);

        if (startDate.after(endDate)) {
            if (startDate.after(calendar)) {
                startDate.set(Calendar.YEAR, startDate.get(Calendar.YEAR) - 1);
            } else {
                endDate.set(Calendar.YEAR, endDate.get(Calendar.YEAR) + 1);
            }
        }
        return calendar.after(startDate) && calendar.before(endDate);
    }

    /**
     * 判断时间是否在指定区间内
     *
     * @param time
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute
     * @return
     */
    public static boolean timeBetween(Date time, Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {

        Calendar calendar = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        calendar.setTime(time);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);

        startDate.set(year, month, date, startHour, startMinute, 0);
        endDate.set(year, month, date, endHour, endMinute + 1, 0);

        if (startDate.after(endDate)) {
            if (startDate.after(calendar)) {
                startDate.set(Calendar.DATE, startDate.get(Calendar.DATE) - 1);
            } else {
                endDate.set(Calendar.DATE, endDate.get(Calendar.DATE) + 1);
            }
        }
        return calendar.after(startDate) && calendar.before(endDate);
    }
}