package com.ah.cloud.permissions.netty.application.strategy.chatroom.operate;

import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 14:27
 **/
public interface ChatRoomOperateHandler {

    /**
     * 处理
     * @param roomId
     */
    void handle(Long roomId);

    /**
     * 操作类型
     * @return
     */
    ChatRoomStatusEnum getChatRoomStatusEnum();
}
