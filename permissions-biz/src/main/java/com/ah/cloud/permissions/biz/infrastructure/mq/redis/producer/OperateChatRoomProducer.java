package com.ah.cloud.permissions.biz.infrastructure.mq.redis.producer;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisProducer;
import com.ah.cloud.permissions.domain.message.OperateChatRoomMessage;
import org.springframework.beans.factory.annotation.Value;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-23 21:11
 **/
public class OperateChatRoomProducer extends AbstractBaseRedisProducer<OperateChatRoomMessage> {
    /**
     * channel
     */
    @Value("${mq.redis.topic.operateChatRoom.name}")
    private String operatorChatRoom;

    @Override
    protected String getChannel() {
        return operatorChatRoom;
    }
}
