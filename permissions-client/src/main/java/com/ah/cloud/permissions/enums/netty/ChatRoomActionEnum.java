package com.ah.cloud.permissions.enums.netty;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 15:06
 **/
public enum ChatRoomActionEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 加入聊天室
     */
    JOIN(1, "加入聊天室"),

    /**
     * 退出聊天室
     */
    EXIT(2, "退出聊天室"),

    /**
     * 发送消息
     */
    SEND(3, "发送消息"),
    ;

    private final int type;

    private final String desc;

    ChatRoomActionEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static ChatRoomActionEnum getByType(Integer type) {
        ChatRoomActionEnum[] values = values();
        for (ChatRoomActionEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }
}
