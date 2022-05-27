package com.ah.cloud.permissions.enums.netty;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 消息类型枚举
 * @author: YuKai Fan
 * @create: 2022-05-12 09:25
 **/
public enum MsgTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),
    /**
     * 绑定
     */
    BIND(1, "绑定"),

    /**
     * 单聊
     */
    SINGLE(2, "单聊"),

    /**
     * 群聊
     */
    GROUP(3, "群聊"),

    /**
     * 聊天室
     */
    CHAT_ROOM(4, "聊天室"),

    /**
     * 消息通知
     */
    NOTIFY(5, "通知"),

    /**
     * ack消息
     */
    ACK(6, "ack"),

    /**
     * 心跳ping消息
     */
    PING(7, "心跳ping消息"),

    /**
     * 心跳pong消息
     */
    PONG(8, "心跳pong消息"),

    /**
     * 下线 消息
     */
    OFFLINE(9, "下线"),
    ;

    private final int type;

    private final String desc;

    MsgTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static MsgTypeEnum getByType(Integer type) {
        MsgTypeEnum[] msgTypeEnums = values();
        for (MsgTypeEnum msgTypeEnum : msgTypeEnums) {
            if (Objects.equals(msgTypeEnum.getType(), type)) {
                return msgTypeEnum;
            }
        }
        return UNKNOWN;
    }
}
