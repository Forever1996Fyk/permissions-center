package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.enums.common.IMErrorCodeEnum;
import com.ah.cloud.permissions.enums.netty.ChatRoomActionEnum;
import com.ah.cloud.permissions.netty.application.strategy.chatroom.action.ChatRoomActionHandler;
import com.ah.cloud.permissions.netty.application.strategy.selector.ChatRoomActionHandleSelector;
import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import com.ah.cloud.permissions.netty.domain.message.ChatRoomMessage;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.exception.IMBizException;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-22 21:03
 **/
@Slf4j
@Component
public class ChatRoomSendClientService extends AbstractSendClientService<ChatRoomSession> {
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
    public <T> void simpleSend(ImmutablePair<SingleSessionKey, ServerSession> pair, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    protected <T> Future<Void> doSend(ChatRoomSession session, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    protected boolean needThirdPush() {
        return false;
    }

    @Override
    protected boolean needRecordOfflineList() {
        return false;
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public <T> void sendAndAck(ImmutableTriple<SingleSessionKey, ServerSession, ServerSession> triple, MessageBody<T> body, Consumer<MessageBody<T>> afterHandle) {
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
    public <T> void dispatchNode(SingleSessionKey singleSessionKey, MessageBody<T> body) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void complexSendMessageBodyList(ImmutablePair<SingleSessionKey, ChatRoomSession> pair, List<MessageBody<T>> messageBodies, Consumer<List<MessageBody<T>>> consumer) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }

    @Override
    public <T> void complexSendGroupMessage(ImmutablePair<Map<SingleSessionKey, ChatRoomSession>, ChatRoomSession> pair, MessageBody<T> body, Consumer<MessageBody<T>> consumer) {
        throw new IMBizException(IMErrorCodeEnum.CHAT_ROOM_NOT_SUPPORT_OPERATE);
    }
}
