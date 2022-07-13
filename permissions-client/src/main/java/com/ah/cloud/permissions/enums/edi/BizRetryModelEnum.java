package com.ah.cloud.permissions.enums.edi;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 重试方式
 * @author: YuKai Fan
 * @create: 2022-07-05 10:26
 **/
public enum BizRetryModelEnum {
    /**
     * 系统重试
     */
    SYS(1, "系统重试"),

    /**
     * 手动重试
     */
    HAND(2, "手动重试"),
    ;

    private final int type;

    private final String desc;

    public static BizRetryModelEnum getByType(Integer type) {
        BizRetryModelEnum[] values = values();
        for (BizRetryModelEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }

    public static boolean isValid(Integer type) {
        BizRetryModelEnum retryModelEnum = getByType(type);
        return Objects.isNull(retryModelEnum);
    }

    BizRetryModelEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public boolean isHand() {
        return Objects.equals(this, HAND);
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
