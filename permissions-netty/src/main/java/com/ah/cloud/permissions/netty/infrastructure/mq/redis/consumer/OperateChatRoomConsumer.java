package com.ah.cloud.permissions.netty.infrastructure.mq.redis.consumer;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisConsumer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.message.OperateChatRoomMessage;
import com.ah.cloud.permissions.enums.ChatRoomStatusEnum;
import com.ah.cloud.permissions.netty.application.strategy.chatroom.operate.ChatRoomOperateHandler;
import com.ah.cloud.permissions.netty.application.strategy.selector.ChatRoomOperateHandleSelector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.Topic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 21:19
 **/
@Component
public class OperateChatRoomConsumer extends AbstractBaseRedisConsumer<OperateChatRoomMessage>  {
    /**
     * channel
     */
    @Value("${mq.redis.topic.operateChatRoom.name}")
    private String operateChatRoom;

    @Resource
    private ChatRoomOperateHandleSelector selector;

    @Override
    protected Topic getTopic() {
        return new ChannelTopic(operateChatRoom);
    }

    @Override
    protected MessageListener getMessageListener() {
        return this;
    }

    @Override
    protected void doHandleMessage(OperateChatRoomMessage message) {
        ChatRoomStatusEnum chatRoomStatusEnum = ChatRoomStatusEnum.getByType(message.getChatRoomStatus());
        ChatRoomOperateHandler chatRoomOperateHandler = selector.select(chatRoomStatusEnum);
        chatRoomOperateHandler.handle(message.getRoomId());
    }

    @Override
    protected OperateChatRoomMessage convert(byte[] bytes) {
        return JsonUtils.toBean(bytes, OperateChatRoomMessage.class);
    }
}
