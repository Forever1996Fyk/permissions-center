package com.ah.cloud.permissions.enums.edi;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 10:43
 **/
public enum EdiErrorCodeEnum implements ErrorCode {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    RETRY_BIZ_CONFIG_ADD_FAIL(7_10_0_001, "%s配置插入失败"),
    RETRY_BIZ_CONFIG_UPDATE_FAIL(7_10_0_002, "%s配置更新失败"),
    RETRY_BIZ_CONFIG_IS_NULL(7_10_0_003, "重试配置为空"),
    RETRY_BIZ_CONFIG_START_FAILED(7_10_0_004, "%s重试配置启用失败"),
    RETRY_BIZ_CONFIG_STOP_FAILED(7_10_0_005, "%s重试配置停用失败"),


    RETRY_BIZ_RECORD_ADD_FAIL(7_10_1_001, "%s记录插入失败"),
    RETRY_BIZ_RECORD_UPDATE_FAIL(7_10_1_002, "EDI记录更新失败"),
    RETRY_BIZ_HANDLER_IS_NULL(7_10_1_003, "重试处理器为空"),
    RETRY_BIZ_TYPE_IS_NULL(7_10_1_004, "重试类型为空"),
    RETRY_BIZ_RECORD_IS_NULL(7_10_1_005, "重试记录为空"),
    RETRY_HANDLE_REPEAT(7_10_1_006, "重试处理器重复"),
    RETRY_HANDLE_REGISTERING(7_10_1_007, "重试处理器正在注册"),

    RETRY_EXECUTOR_NOT_EXISTED(7_10_2_001, "重试处理器不存在"),

    RETRY_RECORD_IS_RETRYING(7_10_2_001, "记录不能重试，正在重试中"),
    RETRY_RECORD_CONFIG_IS_STOP(7_10_2_002, "记录不能重试，重试配置停用"),
    RETRY_RECORD_CONFIG_IS_CANNOT_RETRY(7_10_2_003, "记录不能重试，重试配置不可重试"),
    RETRY_RECORD_RETRY_OVER_LIMIT(7_10_2_004, "记录不能重试，重试次数超过配置上限"),
    RETRY_RECORD_NOT_ARRIVE_TIME(7_10_2_005, "记录不能重试，没有达到执行时间"),

    RETRY_RECORD_BIZ_ERROR(7_10_2_006, "重试逻辑执行错误"),


    RETRY_ALARM_HANDLER_NOT_EXITED(8_10_1_001, "告警处理器不存在"),
    ;
    private final int code;
    private final String msg;

    EdiErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
