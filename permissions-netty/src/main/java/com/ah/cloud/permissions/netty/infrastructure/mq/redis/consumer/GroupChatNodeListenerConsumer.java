package com.ah.cloud.permissions.netty.infrastructure.mq.redis.consumer;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.AbstractBaseRedisConsumer;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.netty.application.manager.SessionManager;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.ah.cloud.permissions.netty.domain.session.GroupSession;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
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
public class GroupChatNodeListenerConsumer extends AbstractBaseRedisConsumer<MessageNodeDTO> {

    @Value("${mq.redis.topic.groupChatListenerNodePattern.name}")
    private String groupChatListenerNodePattern;

    @Override
    protected Topic getTopic() {
        return new PatternTopic(groupChatListenerNodePattern);
    }

    @Override
    protected MessageListener getMessageListener() {
        return this;
    }

    @Override
    protected void doHandleMessage(MessageNodeDTO message) {
        GroupSessionService<GroupSession, GroupSessionKey> groupSessionService = SessionManager.getGroupSessionService();
        GroupSessionKey groupSessionKey = GroupSessionKey.builder()
                .sessionId(message.getToId())
                .build();
        GroupSession groupSession = groupSessionService.get(groupSessionKey);
        if (Objects.isNull(groupSession)) {
            log.error("GroupChatNodeListenerConsumer[doHandleMessage] group chat send msg failed, message is {}, reason is groupSession is empty", JsonUtils.toJSONString(message));
            return;
        }
    }

    @Override
    protected MessageNodeDTO convert(byte[] bytes) {
        return JsonUtils.byteToBean(bytes, MessageNodeDTO.class);
    }
}
