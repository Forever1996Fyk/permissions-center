package com.ah.cloud.permissions.netty.application.manager;

import com.ah.cloud.permissions.netty.domain.session.*;
import com.ah.cloud.permissions.netty.domain.session.key.ChatRoomSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.constant.SessionConstants;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import com.ah.cloud.permissions.netty.infrastructure.service.session.impl.ChatRoomSessionServiceImpl;
import com.ah.cloud.permissions.netty.infrastructure.service.session.impl.GroupSessionServiceImpl;
import com.ah.cloud.permissions.netty.infrastructure.service.session.impl.SingleSessionServiceImpl;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.Timeout;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 16:03
 **/
@Slf4j
public class SessionManager {
    /**
     * 单聊session 静态变量
     */
    private final static SessionService<SingleSession, SingleSessionKey> SINGLE_SESSION_SERVICE = new SingleSessionServiceImpl();

    /**
     * 聊天室session 静态变量
     */
    private final static GroupSessionService<ChatRoomSession, ChatRoomSessionKey> CHAT_ROOM_SESSION_SERVICE = new ChatRoomSessionServiceImpl();

    /**
     * 群组session 静态变量
     */
    private final static GroupSessionService<GroupSession, GroupSessionKey> GROUP_SESSION_SERVICE = new GroupSessionServiceImpl();


    /**
     * ACK重试队列
     */
    private final static Map<Integer, Timeout> ACK_MSG_TIMEOUT_LIST = Maps.newConcurrentMap();

    /**
     * 获取SingeSessionService
     *
     * @return
     */
    public static SessionService<SingleSession, SingleSessionKey> getSingleSessionService() {
        return SINGLE_SESSION_SERVICE;
    }

    /**
     * 获取ChatRoomSessionService
     *
     * @return
     */
    public static GroupSessionService<ChatRoomSession, ChatRoomSessionKey> getChatRoomSessionService() {
        return CHAT_ROOM_SESSION_SERVICE;
    }

    /**
     * 获取GroupSessionService
     *
     * @return
     */
    public static GroupSessionService<GroupSession, GroupSessionKey> getGroupSessionService() {
        return GROUP_SESSION_SERVICE;
    }

    /**
     * 获取 ACK重试队列
     *
     * @return
     */
    public static Map<Integer, Timeout> getAckMsgTimeoutList() {
        return ACK_MSG_TIMEOUT_LIST;
    }

    /**
     * 移除session
     *
     * @param channel
     */
    public static void removeSession(Channel channel) {
        log.info("SessionManager[removeSession] remove session close channel");
        Attribute<SingleSession> attribute = channel.attr(SessionConstants.SINGLE_SESSION_ATTRIBUTE_KEY);
        SingleSession singleSession = attribute.get();
        SessionService<SingleSession, SingleSessionKey> singleSessionService = getSingleSessionService();
        singleSessionService.remove(SingleSessionKey.builder()
                .sessionId(singleSession.getUserId())
                .msgSourceEnum(singleSession.getMsgSourceEnum())
                .build());
    }
}
