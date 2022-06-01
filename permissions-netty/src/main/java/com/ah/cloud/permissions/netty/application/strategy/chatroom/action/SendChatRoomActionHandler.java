package com.ah.cloud.permissions.netty.application.strategy.chatroom.action;

import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.mq.redis.producer.ChatRoomNodeListenerProducer;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:25
 **/
@Slf4j
@Component
public class SendChatRoomActionHandler extends AbstractChatRoomActionHandler {
    private final static String LOG_MARK = "SendChatRoomActionHandler";

    @Override
    public void doHandle(ChatRoomSession chatRoomSession, SingleSession singleSession, MessageBody<ChatRoomMessage> body) {
        ChatRoomMessage chatRoomMessage = body.getData();
        ChannelGroup channelGroup = chatRoomSession.getChannelGroup();
        Channel channel = singleSession.getChannel();
        Channel existChannel = channelGroup.find(channel.id());
        // 判断当前用户是否在聊天室中
        if (Objects.isNull(existChannel)) {
            log.error("SendChatRoomActionHandler[doHandle] current user not exist chatroom cannot send message roomId is {}, userId is {}",
                    chatRoomSession.getRoomId(),
                    singleSession.getUserId());
            throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
        }
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
