package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: quartz执行计划枚举
 * @author: YuKai Fan
 * @create: 2022-04-30 10:31
 **/
public enum ExecutePolicyEnum {

    /**
     * 默认
     */
    EXECUTE_DEFAULT(0, "默认"),

    /**
     * 立即触发执行
     */
    EXECUTE_IMMEDIATELY(1, "立即触发执行"),

    /**
     * 触发一次执行
     */
    EXECUTE_ONCE(2, "触发一次执行"),

    /**
     * 不触发执行
     */
    EXECUTE_NOTHING(3, "不触发执行"),

    ;

    /**
     * 类型
     */
    private final int type;

    /**
     * 描述
     */
    private final String desc;

    ExecutePolicyEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isValid(Integer value) {
        ExecutePolicyEnum[] values = values();
        for (ExecutePolicyEnum executePolicyEnum : values) {
            if (Objects.equals(executePolicyEnum.getType(), value)) {
                return true;
            }
        }
        return false;
    }
}
