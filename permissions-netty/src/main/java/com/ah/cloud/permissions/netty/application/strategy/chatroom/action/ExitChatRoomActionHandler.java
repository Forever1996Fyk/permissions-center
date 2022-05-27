package com.ah.cloud.permissions.netty.application.strategy.chatroom.action;

import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.application.manager.ChatRoomStoreManager;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:25
 **/
@Component
public class ExitChatRoomActionHandler extends AbstractChatRoomActionHandler {
    private final static String LOG_MARK = "ExitChatRoomActionHandler";

    @Resource
    private ChatRoomStoreManager chatRoomStoreManager;

    @Override
    public void doHandle(ChatRoomSession chatRoomSession, SingleSession singleSession, MessageBody<ChatRoomMessage> body) {
        ChatRoomMessage chatRoomMessage = body.getData();
        ChannelGroup channelGroup = chatRoomSession.getChannelGroup();
        Channel channel = singleSession.getChannel();
        channelGroup.remove(channel);
        chatRoomStoreManager.removeUserFromRoom(chatRoomMessage.getRoomId(), chatRoomMessage.getFromId());
        chatRoomMessage.setContent(String.format("[%s]离开聊天室",  chatRoomMessage.getFromName()));
        sendMessage(channelGroup, body);
    }

    @Override
    public String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public ChatRoomActionEnum getChatRoomActionEnum() {
        return ChatRoomActionEnum.EXIT;
    }
}
