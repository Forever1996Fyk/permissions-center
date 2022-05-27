package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 16:59
 **/
public enum ChatRoomErrorCodeEnum implements ErrorCode {

    /**
     * chatroom
     */
    OPERATE_FAILED_CHATROOM_NOT_EXISTED(4_10_0_001, "操作失败, 当前聊天室[%s]不存在"),
    CHATROOM_ID_NOT_NULL(4_10_0_001, "聊天室id不能为空"),
    CHATROOM_ACTION_ILLEGAL(4_10_0_002, "聊天室操作非法"),
    ;

    private final int code;
    private final String msg;


    ChatRoomErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
