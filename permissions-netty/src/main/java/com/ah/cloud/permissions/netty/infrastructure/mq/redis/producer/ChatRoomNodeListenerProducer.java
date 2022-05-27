package com.ah.cloud.permissions.netty.infrastructure.mq.redis.producer;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisProducer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ProducerResult;
import com.ah.cloud.permissions.netty.domain.message.mq.ChatRoomNodeMessage;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-24 14:18
 **/
@Slf4j
@Component
public class ChatRoomNodeListenerProducer extends AbstractBaseRedisProducer<ChatRoomNodeMessage> {
    @Value("${mq.redis.topic.chatRoomNodeListener.name}")
    private String chatRoomNodeListener;

    @Override
    public ProducerResult<Void> sendMessage(ChatRoomNodeMessage message) {
        ProducerResult<Void> result = new ProducerResult<>();
        try {
            redisCacheHandleStrategy.publishMessage(rebuildChannel(message.getRoomId()), message);
            log.info("{} message publish success, msg is [{}]", getChannel(), JsonUtils.toJSONString(message));
            result.setSuccess(true);
        } catch (Throwable throwable) {
            log.error("{} message publish failed, msg is [{}], reason is {}", getChannel(), JsonUtils.toJSONString(message), Throwables.getStackTraceAsString(throwable));
            result.setSuccess(false);
            result.setMsg(throwable.getMessage());
        }
        return result;
    }

    private String rebuildChannel(Long roomId) {
        return getChannel() + PermissionsConstants.COLON_SEPARATOR + roomId;
    }

    @Override
    protected String getChannel() {
        return chatRoomNodeListener;
    }
}
