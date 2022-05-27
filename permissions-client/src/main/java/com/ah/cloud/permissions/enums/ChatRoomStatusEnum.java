package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 聊天室状态
 * @author: YuKai Fan
 * @create: 2022-05-23 18:05
 **/
public enum ChatRoomStatusEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 开放
     */
    OPEN(1, "开放"),

    /**
     * 关闭
     */
    CLOSE(2, "关闭"),
    ;

    private final int status;

    private final String desc;

    ChatRoomStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public static ChatRoomStatusEnum getByType(Integer type) {
        ChatRoomStatusEnum[] values = values();
        for (ChatRoomStatusEnum value : values) {
            if (Objects.equals(value.getStatus(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
