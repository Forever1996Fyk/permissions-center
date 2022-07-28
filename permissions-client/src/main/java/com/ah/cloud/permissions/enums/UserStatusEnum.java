package com.ah.cloud.permissions.enums;

import lombok.Getter;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 用户状态枚举
 * @author: YuKai Fan
 * @create: 2021-12-23 14:31
 **/
@Getter
public enum UserStatusEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),
    /**
     * 正常
     */
    NORMAL(1, "正常"),
    /**
     * 禁用
     */
    DISABLED(2, "禁用"),
    /**
     * 注销
     */
    LOG_OFF(3, "注销"),
    ;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String desc;

    UserStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    /**
     * 根据value找到对应的枚举
     *
     * @param value value值
     * @return 枚举值
     */
    public static UserStatusEnum valueOf(Integer value) {
        for (UserStatusEnum e : UserStatusEnum.values()) {
            if (Objects.equals(e.status, value)) {
                return e;
            }
        }
        return UNKNOWN;
    }
}
