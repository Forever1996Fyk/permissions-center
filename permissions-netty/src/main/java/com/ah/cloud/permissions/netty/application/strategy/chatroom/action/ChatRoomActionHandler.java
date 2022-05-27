package com.ah.cloud.permissions.netty.application.strategy.chatroom.action;

import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:09
 **/
public interface ChatRoomActionHandler {

    /**
     * 消息处理
     * @param pair
     * @param body
     */
    void handle(ImmutablePair<ChatRoomSession, SingleSession> pair, MessageBody<ChatRoomMessage> body);

    /**
     * 操作类型
     * @return
     */
    ChatRoomActionEnum getChatRoomActionEnum();
}
