package com.ah.cloud.permissions.netty.infrastructure.mq.redis.consumer;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisConsumer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.message.mq.ChatRoomNodeMessage;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-24 14:00
 **/
@Slf4j
@Component
public class ChatRoomNodeListenerConsumer extends AbstractBaseRedisConsumer<ChatRoomNodeMessage> {

    @Value("${mq.redis.topic.chatRoomNodeListenerPattern.name}")
    private String chatRoomNodeListenerPattern;

    @Override
    protected Topic getTopic() {
        return new PatternTopic(chatRoomNodeListenerPattern);
    }

    @Override
    protected MessageListener getMessageListener() {
        return this;
    }

    @Override
    protected void doHandleMessage(ChatRoomNodeMessage message) {
        GroupSessionService<ChatRoomSession, GroupSessionKey> groupSessionService = SessionManager.getChatRoomSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder()
                .groupTypeEnum(GroupTypeEnum.CHAT_ROOM)
                .sessionId(message.getRoomId())
                .build();
        ChatRoomSession chatRoomSession = groupSessionService.get(groupSessionKey);
        if (Objects.isNull(chatRoomSession)) {
            log.warn("ChatRoomNodeListenerConsumer[doHandleMessage] chatroom send msg failed, message is {}, reason is chatRoomSession is empty", JsonUtils.toJSONString(message));
            return;
        }
        ChatRoomMessage chatRoomMessage = ChatRoomMessage.builder()
                .roomId(message.getRoomId())
                .content(message.getContent())
                .fromId(message.getFromId())
                .build();
        MessageBody<ChatRoomMessage> messageBody = MessageBody.<ChatRoomMessage>builder()
                .toId(message.getFromId())
                .msgSourceEnum(MsgSourceEnum.getByType(message.getMsgSource()))
                .formatEnum(FormatEnum.getByType(message.getFormatType()))
                .msgId(message.getMsgId())
                .timestamp(message.getTimestamp())
                .data(chatRoomMessage)
                .build();
        chatRoomSession.getChannelGroup().writeAndFlush(messageBody);
    }

    @Override
    protected ChatRoomNodeMessage convert(byte[] bytes) {
        return JsonUtils.toBean(bytes, ChatRoomNodeMessage.class);
    }
}
