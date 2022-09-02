package com.ah.cloud.permissions.netty.application.strategy.chatroom.action;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.IMErrorCodeEnum;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.message.mq.ChatRoomNodeMessage;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import com.ah.cloud.permissions.netty.infrastructure.mq.redis.producer.ChatRoomNodeListenerProducer;
import com.google.common.base.Throwables;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-26 15:12
 **/
@Slf4j
@Component
public abstract class AbstractChatRoomActionHandler implements ChatRoomActionHandler {
    @Resource
    protected ChatRoomNodeListenerProducer chatRoomNodeListenerProducer;

    @Override
    public void handle(ImmutablePair<ChatRoomSession, SingleSession> pair, MessageBody<ChatRoomMessage> body) {
        ChatRoomSession chatRoomSession = pair.getLeft();
        SingleSession singleSession = pair.getRight();

        if (chatRoomSession.getGroupStatus().isDisabled()) {
            log.error("{}[handle] current chatroom is disabled cannot send, roomId is {}, body is {}",
                    getLogMark(),
                    chatRoomSession.getRoomId(),
                    JsonUtils.toJsonString(body));
            throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_STATUE_DISABLED);
        }

        if (chatRoomSession.getIsBannedChat().isNo()) {
            log.error("{}[handle] current chatroom is banned cannot send, roomId is {}, body is {}",
                    getLogMark(),
                    chatRoomSession.getRoomId(),
                    JsonUtils.toJsonString(body));
            throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_BANNED_CHAT);
        }

        try {
            log.info("{}[handle] start chatroom message, params is {}", getLogMark(), JsonUtils.toJsonString(body));
            doHandle(chatRoomSession, singleSession, body);
        } catch (IMBizException imBizException) {
            log.error("{}[handle] handle chatroom message error with IMBizException, params is {}, reason is {}",
                    getLogMark(),
                    JsonUtils.toJsonString(body),
                    Throwables.getStackTraceAsString(imBizException));
            throw imBizException;
        } catch (Throwable throwable) {
            log.error("{}[handle] handle chatroom message error with throwable, params is {}, reason is {}",
                    getLogMark(),
                    JsonUtils.toJsonString(body),
                    Throwables.getStackTraceAsString(throwable));
            throw throwable;
        }
    }

    /**
     * 发送消息
     * @param channelGroup
     * @param body
     */
    protected void sendMessage(ChannelGroup channelGroup, MessageBody<ChatRoomMessage> body) {
        ChannelGroupFuture channelFutures = channelGroup.writeAndFlush(body);
        if (channelFutures.isSuccess()) {
            ChatRoomMessage chatRoomMessage = body.getData();
            /*
             利用mq 通知其他节点 当前聊天室 需要发送的消息。这里不用redis的list作为消息队列和发布订阅实现, 原因如下:
             1. redis list 只能保证一个节点被消费，其他节点会无法收到这个消息, 则无法发送消息;
             2. redis发布订阅，并没有队列功能。而每次发布消息的key无法通过当前roomId进行动态控制, 在很多聊天室同时开启的情况下, 并发量太大, 无法支持。
             (但是目前暂且使用模式匹配的通道方式通知)
             */
            ChatRoomNodeMessage chatRoomNodeMessage = ChatRoomNodeMessage.builder()
                    .content(chatRoomMessage.getContent())
                    .roomId(chatRoomMessage.getRoomId())
                    .fromId(chatRoomMessage.getFromId())
                    .msgId(body.getMsgId())
                    .timestamp(body.getTimestamp())
                    .formatType(body.getFormatEnum().getType())
                    .msgSource(body.getMsgSourceEnum().getType())
                    .action(chatRoomMessage.getAction())
                    .build();
            chatRoomNodeListenerProducer.sendMessage(chatRoomNodeMessage);
        }
    }

    /**
     * 逻辑处理
     * @param chatRoomSession
     * @param singleSession
     * @param body
     */
    public abstract void doHandle(ChatRoomSession chatRoomSession, SingleSession singleSession, MessageBody<ChatRoomMessage> body);

    /**
     * 日志标记
     * @return
     */
    public abstract String getLogMark();
}
