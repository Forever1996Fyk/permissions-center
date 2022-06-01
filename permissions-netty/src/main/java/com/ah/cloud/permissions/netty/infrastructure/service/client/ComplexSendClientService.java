package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.key.SessionKey;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description: 复杂消息发送
 * @author: YuKai Fan
 * @create: 2022-05-20 15:21
 **/
public interface ComplexSendClientService<K extends SessionKey, S extends ServerSession> {

    /**
     * 复杂消息发送
     *
     * 用于一次性发送List集合的消息体
     *
     * @param pair
     * @param messageBodyList
     * @param consumer
     */
    <T> void complexSendMessageBodyList(ImmutablePair<K, S> pair, List<MessageBody<T>> messageBodyList, Consumer<List<MessageBody<T>>> consumer);
}
