package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.domain.common.ImResult;
import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.application.strategy.chatroom.action.ChatRoomActionHandler;
import com.ah.cloud.permissions.netty.application.strategy.selector.ChatRoomActionHandleSelector;
import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.*;
import com.ah.cloud.permissions.netty.domain.session.key.ChatRoomSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description: 聊天室的消息发送 消息的实时性，准确性，时序性要求不是很高, 即使存在消息丢失也问题不大, 所以单独构建chatRoomSend方法, 用于聊天室的消息处理
 * @author: YuKai Fan
 * @create: 2022-05-22 21:03
 **/
@Slf4j
@Component
public class ChatRoomSendClientService extends AbstractSendClientService<ChatRoomSessionKey, ChatRoomSession> {
    private final static String LOG_MARK = "ChatRoomSendClientService";

    @Resource
    private ChatRoomActionHandleSelector selector;

    /**
     * 聊天室发送
     * @param pair
     * @param body
     * @param <T>
     */
    public <T> void chatRoomSend(ImmutablePair<ChatRoomSession, SingleSession> pair, MessageBody<ChatRoomMessage> body) {
        ChatRoomMessage chatRoomMessage = body.getData();
        ChatRoomActionEnum chatRoomActionEnum = ChatRoomActionEnum.getByType(chatRoomMessage.getAction());
        ChatRoomActionHandler chatRoomActionHandler = selector.select(chatRoomActionEnum);
        chatRoomActionHandler.handle(pair, body);
    }

    @Override
    public <T> void sendAndAck(ImmutableTriple<ChatRoomSessionKey, ChatRoomSession, Channel> triple, MessageBody<T> body, Consumer<MessageBody<T>> afterHandle) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void simpleSend(ImmutablePair<ChatRoomSessionKey, ChatRoomSession> pair, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void ack(AckDTO<T> ackDTO) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void ack(Channel channel, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void complexSendMessageBodyList(ImmutablePair<ChatRoomSessionKey, ChatRoomSession> pair, List<MessageBody<T>> messageBodies, Consumer<List<MessageBody<T>>> consumer) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    protected <T> ImResult<Void> doSend(ChatRoomSession session, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public <T> boolean dispatchNode(ChatRoomSessionKey sessionKey, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
