package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.key.SessionKey;
import io.netty.channel.Channel;
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
public interface SendClientService<K extends SessionKey, S extends ServerSession> extends InitializingBean {

    /**
     * 消息发送
     * @param triple
     *       left: to-singleSessionKey,  接收者的sessionKey, 防止当前节点不存在 该用户的session, 用于分发节点
     *       middle: to-session,         接收者的session， 用于消息的分发
     *       right: from-session         发送者的session,  用于ack
     * @param body
     * @param <T>
     */
    <T> void sendAndAck(ImmutableTriple<K, S, Channel> triple, MessageBody<T> body, Consumer<MessageBody<T>> consumer);


    /**
     * simple消息发送
     * @param pair
     *        left: to-sessionKey,  接收者的sessionKey, 防止当前节点不存在 该用户的session, 用于分发节点
     *        right: to-session,    接收者的session， 用于消息的分发
     * @param body
     * @param <T>
     */
    <T> void simpleSend(ImmutablePair<K, S> pair, MessageBody<T> body);

    /**
     * 分发节点
     *
     * @param sessionKey 根据当前接收者的sessionKey 分发到其他节点
     * @param body
     * @param <T>
     */
    <T> boolean dispatchNode(K sessionKey, MessageBody<T> body);
}


