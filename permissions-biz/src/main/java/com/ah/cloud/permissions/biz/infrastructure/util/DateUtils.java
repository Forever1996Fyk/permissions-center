package com.ah.cloud.permissions.biz.infrastructure.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @program: permissions-center
 * @description: 日期工具类
 * @author: YuKai Fan
 * @create: 2022-04-27 16:29
 **/
@Slf4j
public class DateUtils {

    public static String pattern0 = "yyyy-MM-dd HH:mm:ss";

    public static String pattern1 ="yyyy年MM月dd日HH时mm分ss秒";

    public static String pattern3 = "yyyyMMdd";

    public static String pattern4 = "yyyy-MM-dd";

    public static String pattern6 = "yyyy/MM/dd HH:mm:ss";

    public static String pattern5 = "yyyyMM";

    /**
     * 字符串转为date
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(date);
        } catch (ParseException e) {
            log.error("date:" + date + " format error");
            return null;
        }
    }

    /**
     * 字符串转为date
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString) {
        if (StringUtils.isEmpty(dateString)) {
            return null;
        }
        return parse(dateString, pattern0);
    }

    /**
     * 获取当前时间 字符串
     *
     * @return
     */
    public static String getStrCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern0));
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, pattern0);
    }

    /**
     * 日期格式化
     * @param date
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 将时间字串转为 LocalDateTime，时间字串的格式请用 pattern 指定
     */
    public static LocalDateTime str2LocalDateTime(String datetimeStr, String pattern) {
        return LocalDateTime.parse(datetimeStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 将时间字串转为 LocalDate，时间字串的格式请用 pattern 指定
     */
    public static LocalDate str2LocalDate(String datetimeStr, String pattern) {
        return LocalDate.parse(datetimeStr, DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 将时间字串转为 LocalDate，时间字串的格式请用 pattern 指定
     */
    public static LocalDate str2LocalDate(String datetimeStr) {
        LocalDate localDate = str2LocalDate(datetimeStr, pattern0);
        if (localDate == null) {
            localDate = str2LocalDate(datetimeStr, pattern4);
        }
        return localDate;
    }


    /**
     * 将 java.time.LocalDateTime 转为指定格式的时间字串
     */
    public static String localDate2Str(LocalDate localDate, String pattern) {
        if (localDate == null || StringUtils.isBlank(pattern)) {
            return "";
        }
        return DateTimeFormatter.ofPattern(pattern).format(localDate);
    }

    /**
     * 将 java.time.LocalDateTime 转为指定格式的时间字串
     */
    public static String localDateTime2Str(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null || pattern == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    /**
     * 校验时间日期是否跨月
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean checkDateTimeIsAcrossMonth(String startTime, String endTime) {
        LocalDateTime startLocalDateTime = DateUtils.str2LocalDateTime(startTime, DateUtils.pattern0);
        LocalDateTime endLocalDateTime = DateUtils.str2LocalDateTime(endTime, DateUtils.pattern0);

        String start = DateUtils.localDateTime2Str(startLocalDateTime, DateUtils.pattern5);
        String end = DateUtils.localDateTime2Str(endLocalDateTime, DateUtils.pattern5);
        return StringUtils.equals(start,end);
    }

    /**
     * 校验时间日期是否跨月, 如果不跨月则返回当前日期(yyyyMM)
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static Integer checkDateTimeIsAcrossMonthAndReturnDate(String startTime, String endTime) {
        LocalDateTime startLocalDateTime = DateUtils.str2LocalDateTime(startTime, DateUtils.pattern0);
        LocalDateTime endLocalDateTime = DateUtils.str2LocalDateTime(endTime, DateUtils.pattern0);

        String start = DateUtils.localDateTime2Str(startLocalDateTime, DateUtils.pattern5);
        String end = DateUtils.localDateTime2Str(endLocalDateTime, DateUtils.pattern5);
        if (StringUtils.equals(start,end)) {
            return null;
        }
        return Integer.valueOf(start);
    }

    /**
     * instant转为date
     * @param instant
     * @return
     */
    public static Date instantToDate(Instant instant) {
        return Date.from(instant);
    }

    /**
     * 当前日期是否在目标时间之前
     *
     * @param expireTime
     * @return
     */
    public static boolean beforeDate(Date expireTime) {
        Date date = new Date();
        return date.before(expireTime);
    }

    /**
     * 当前日期是否在目标时间之后
     *
     * @param expireTime
     * @return
     */
    public static boolean afterDate(Date expireTime) {
        Date date = new Date();
        return date.after(expireTime);
    }

    /**
     * 获取当前时间戳 (秒)
     * @return
     */
    public static Long getCurrentSeconds() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    /**
     * 获取当前date日期
     * @return
     */
    public static Date getCurrentDateTime() {
        return new Date();
    }

    /**
     * 获取当前LocalDateTime
     * @return
     */
    public static LocalDateTime getCurrentLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取上月日期
     * @param date
     * @return
     */
    public static Date getLastMonthDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, -1);
        return c.getTime();
    }

    /**
     * 当前时间 前多少分钟 的时间
     * @param minutes
     * @return
     */
    public static String getDateBeforeMin(int minutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minutesBefore = now.minusMinutes(minutes);
        return localDateTime2Str(minutesBefore, pattern0);
    }

    /**
     * 基于当前时间 添加固定天数, 并返回Instant对象
     *
     * @param days
     * @return Instant
     */
    public static Instant plusDayLocalDateToInstant(long days) {
        return LocalDate.now().plusDays(days).atStartOfDay().toInstant(ZoneOffset.UTC);
    }
}
