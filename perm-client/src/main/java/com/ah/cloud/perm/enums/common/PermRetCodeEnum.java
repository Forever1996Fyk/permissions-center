package com.ah.cloud.perm.enums.common;

/**
 * @program: permissions-center
 * @description: 通用返回状态码
 * @author: YuKai Fan
 * @create: 2021-12-03 10:36
 **/
public enum PermRetCodeEnum {

    SUCCESS(0, "成功"),

    /**
     * 通用参数异常
     * 参数异常统一用这个，不要再重复定义
     */
    PARAM_IS_NULL(100_1_000, "[%s] params is null"),
    PARAM_IS_ERROR(100_1_001, "[%s] 参数非法"),
    ERROR_PARAM(100_1_002, "参数异常"),
    CREATE_TIME_GREATER_THAN_END_TIME(100_1_003, "起始时间不能大于结束时间"),
    /*[%s]可自己定义*/
    DATA_IS_NULL(100_1_004, "[%s] is null"),
    RECORD_MORE_THAN_ONE(100_1_005, "记录不唯一"),

    ;

    /**
     * 编码
     */
    private Integer code;

    /**
     * 信息
     */
    private String msg;

    PermRetCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
