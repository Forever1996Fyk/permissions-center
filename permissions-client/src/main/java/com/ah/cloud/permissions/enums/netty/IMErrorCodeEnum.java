package com.ah.cloud.permissions.enums.netty;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Getter;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 18:24
 **/
@Getter
public enum IMErrorCodeEnum implements ErrorCode  {

    /**
     * 成功
     */
    SUCCESS(3_0, "成功"),


    MSG_SEND_FAILED(3_10_0_001, "消息发送失败"),
    MSG_SEND_FAILED_SESSION_NOT_EXISTED(3_10_0_002, "消息发送失败, 当前session不存在"),

    CLIENT_CONNECT_FAILED_SESSION_NULL(3_10_1_001, "客户端连接失败, session为空"),
    BIND_SERVER_FAILED_TOKEN_ERROR(3_10_1_002, "绑定服务端失败, 当前token错误"),
    USER_IM_NOT_BIND(3_10_1_003, "用户未绑定"),


    OFFLINE_MESSAGE_STORE_FAILED_BODY_IS_NULL(3_11_0_001, "离线消息存储失败，消息体为空"),
    OFFLINE_MESSAGE_STORE_FAILED_TOID_IS_NULL(3_11_0_001, "离线消息存储失败，接收者id为空"),


    CHAT_ROOM_NOT_SUPPORT_OPERATE(3_12_0_001, "聊天室不支持当前操作"),
    CHAT_ROOM_STATUE_DISABLED(3_12_0_002, "[%s]聊天室状态不可用"),
    CHAT_ROOM_NOT_EXISTED(3_12_0_003, "[%s]聊天室不存在"),
    CHAT_ROOM_MAX_SIZE(3_12_0_004, "[%s]聊天室已达到最大容量"),
    CHAT_ROOM_BANNED_CHAT(3_12_0_005, "[%s]聊天室已禁言"),


    GROUP_CHAT_FAILED_MEMBER_IS_EMPTY(3_13_0_001, "当前群里[%s]没有成员"),

    ;

    private final int code;
    private final String msg;

    IMErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
