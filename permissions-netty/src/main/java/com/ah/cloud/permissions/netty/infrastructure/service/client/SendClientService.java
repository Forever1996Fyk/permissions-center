package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.beans.factory.InitializingBean;

import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 10:30
 **/
public interface SendClientService extends InitializingBean {

    /**
     * 消息发送
     * @param triple left: to-singleSessionKey, middle: to-session, right: from-session
     * @param body
     * @param <T>
     */
    <T> void sendAndAck(ImmutableTriple<SingleSessionKey, ServerSession, ServerSession> triple, MessageBody<T> body, Consumer<MessageBody<T>> consumer);


    /**
     * simple消息发送
     * @param pair
     * @param body
     * @param <T>
     */
    <T> void simpleSend(ImmutablePair<SingleSessionKey, ServerSession> pair, MessageBody<T> body);

    /**
     * 分发节点
     * @param singleSessionKey
     * @param body
     * @param <T>
     */
    <T> void dispatchNode(SingleSessionKey singleSessionKey, MessageBody<T> body);
}


