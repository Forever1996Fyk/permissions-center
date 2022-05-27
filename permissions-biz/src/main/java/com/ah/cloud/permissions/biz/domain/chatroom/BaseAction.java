package com.ah.cloud.permissions.biz.domain.chatroom;

import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 16:24
 **/
public interface BaseAction {

    /**
     * 获取聊天室id
     * @return
     */
    Long getRoomId();

    /**
     * 获取聊天室操作类型
     * @return
     */
    ChatRoomActionEnum getChatRoomActionEnum();
}
