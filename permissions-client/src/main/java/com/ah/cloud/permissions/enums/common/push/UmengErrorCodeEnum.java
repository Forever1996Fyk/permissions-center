package com.ah.cloud.permissions.enums.common.push;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 15:29
 **/
public enum UmengErrorCodeEnum implements PushErrorCode {

    /**
     * 成功
     */
    SUCCESS(0, "成功"),

    /**
     * 消息推送失败
     */
    MSG_APP_PUSH_FAIL(4_1_10_0_001, "消息推送失败"),
    MSG_APP_PUSH_FAIL_USER_ASSOCIATED_DEVICE_IS_EMPTY(4_1_10_0_002, "消息推送失败, 用户关联的设备为空"),

    /**
     * 构建推送实体失败
     */
    MSG_APP_PUSH_BUILDER_CAST_FAILED(4_1_11_0_001, "构建推送实体失败"),

    ;

    private final int code;

    private final String msg;

    UmengErrorCodeEnum(int code, String msg) {
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
