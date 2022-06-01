package com.ah.cloud.permissions.netty.infrastructure.mq.redis.producer;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisProducer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ProducerResult;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-27 16:04
 **/
@Slf4j
@Component
public class GroupChatNodeListenerProducer extends AbstractBaseRedisProducer<MessageNodeDTO> {
    @Value("${mq.redis.topic.groupChatListenerNode.name}")
    private String groupChatListenerNode;

    @Override
    public ProducerResult<Void> sendMessage(MessageNodeDTO message) {
        ProducerResult<Void> result = new ProducerResult<>();
        try {
            redisCacheHandleStrategy.publishMessage(rebuildChannel(message.getToId()), message);
            log.info("{} message publish success, msg is [{}]", getChannel(), JsonUtils.toJSONString(message));
            result.setSuccess(true);
        } catch (Throwable throwable) {
            log.error("{} message publish failed, msg is [{}], reason is {}", getChannel(), JsonUtils.toJSONString(message), Throwables.getStackTraceAsString(throwable));
            result.setSuccess(false);
            result.setMsg(throwable.getMessage());
        }
        return result;
    }

    @Override
    protected String getChannel() {
        return groupChatListenerNode;
    }

    private String rebuildChannel(Long groupId) {
        return getChannel() + PermissionsConstants.COLON_SEPARATOR + groupId;
    }
}
