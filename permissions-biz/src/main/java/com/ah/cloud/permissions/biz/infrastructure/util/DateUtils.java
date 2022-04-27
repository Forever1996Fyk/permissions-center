package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @program: permissions-center
 * @description: 日期工具类
 * @author: YuKai Fan
 * @create: 2022-04-27 16:29
 **/
public class DateUtils {

    public static String pattern0 = "yyyy-MM-dd HH:mm:ss";

    public static String pattern1 ="yyyy年MM月dd日HH时mm分ss秒";

    public static String pattern3 = "yyyyMMdd";

    public static String pattern4 = "yyyy-MM-dd";

    public static String pattern6 = "yyyy/MM/dd HH:mm:ss";

    public static String pattern5 = "yyyyMM";

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
}
