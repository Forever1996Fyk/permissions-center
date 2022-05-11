package com.ah.cloud.permissions.enums;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 17:34
 **/
public enum TimeTypeEnum {

    /**
     * 秒
     */
    SECONDS(1, "seconds"),

    /**
     * 分钟
     */
    MINUTES(2, "minutes"),

    /**
     * 小时
     */
    HOURS(3, "hour"),

    /**
     * 天
     */
    DAYS(4, "day"),

    /**
     * 星期
     */
    WEEK(5, "week"),

    /**
     * 月
     */
    MONTH(6, "month"),

    /**
     * 年
     */
    YEAR(7, "year"),
    ;

    private final int type;

    private final String desc;

    TimeTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ChronoUnit convertToChronoUnit(Integer timeUnit) {
        TimeTypeEnum timeTypeEnum = getByType(timeUnit);
        switch (timeTypeEnum) {
            case SECONDS:
                return ChronoUnit.SECONDS;
            case MINUTES:
                return ChronoUnit.MINUTES;
            case HOURS:
                return ChronoUnit.HOURS;
            case DAYS:
                return ChronoUnit.DAYS;
            case YEAR:
                return ChronoUnit.YEARS;
            case WEEK:
                return ChronoUnit.WEEKS;
            default:
                return ChronoUnit.SECONDS;
        }
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
    
    public static TimeTypeEnum getByType(Integer type) {
        TimeTypeEnum[] values = values();
        for (TimeTypeEnum value : values) {
            if (Objects.equals(value, type)) {
                return value;
            }
        }
        return SECONDS;
    }

    public TimeUnit convertTimeUnit() {
        switch (this) {
            case SECONDS:
                return TimeUnit.SECONDS;
            case MINUTES:
                return TimeUnit.MINUTES;
            case HOURS:
                return TimeUnit.HOURS;
            case DAYS:
                return TimeUnit.DAYS;
            default:
                return TimeUnit.SECONDS;
        }
    }
}
