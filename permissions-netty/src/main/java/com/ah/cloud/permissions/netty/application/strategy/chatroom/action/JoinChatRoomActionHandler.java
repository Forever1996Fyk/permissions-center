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
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:11
 **/
@Component
public class JoinChatRoomActionHandler extends AbstractChatRoomActionHandler {
    private final static String LOG_MARK = "JoinChatRoomActionHandler";

    @Resource
    private ChatRoomStoreManager chatRoomStoreManager;

    @Override
    public void doHandle(ChatRoomSession chatRoomSession, SingleSession singleSession, MessageBody<ChatRoomMessage> body) {
        ChatRoomMessage chatRoomMessage = body.getData();
        ChannelGroup channelGroup = chatRoomSession.getChannelGroup();
        Channel channel = singleSession.getChannel();
        Channel existedChannel = channelGroup.find(channel.id());
        // 判断是否在 群组set集合中
        if (Objects.isNull(existedChannel) && !chatRoomStoreManager.currentUserIsExistRoom(chatRoomMessage.getRoomId(), chatRoomMessage.getFromId())) {
            channelGroup.add(channel);
            chatRoomStoreManager.addUserToRoom(chatRoomMessage.getRoomId(), chatRoomMessage.getFromId(), chatRoomSession.getMaxSize());
        }

        chatRoomMessage.setContent(String.format("[%s]加入聊天室",  chatRoomMessage.getFromName()));
        sendMessage(channelGroup, body);
    }

    @Override
    public String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public ChatRoomActionEnum getChatRoomActionEnum() {
        return ChatRoomActionEnum.JOIN;
    }
}
