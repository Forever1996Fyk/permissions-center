package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description: 复杂消息发送
 * @author: YuKai Fan
 * @create: 2022-05-20 15:21
 **/
public interface ComplexSendClientService<S extends ServerSession> {

    /**
     * 复杂消息发送
     * @param pair
     * @param messageBodyList
     * @param consumer
     */
    <T> void complexSendMessageBodyList(ImmutablePair<SingleSessionKey, S> pair, List<MessageBody<T>> messageBodyList, Consumer<List<MessageBody<T>>> consumer);
}
