package com.ah.cloud.permissions.netty.infrastructure.service.session.impl;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.key.ChatRoomSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 17:07
 **/
@Slf4j
@Component
public class ChatRoomSessionServiceImpl implements GroupSessionService<ChatRoomSession, ChatRoomSessionKey> {
    private final static Map<String, ChatRoomSession> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public int countOnlineNum(ChatRoomSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return 0;
        }
        return chatRoomSession.getChannelGroup().size();
    }

    @Override
    public boolean closeGroup(ChatRoomSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return false;
        }
        chatRoomSession.closeGroupSession();
        return true;
    }

    @Override
    public boolean openGroup(ChatRoomSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return false;
        }
        chatRoomSession.openGroupSession();
        return true;
    }

    @Override
    public Set<Long> getGroupMemberSet(ChatRoomSessionKey key) {
        return null;
    }

    @Override
    public void save(ChatRoomSession session) {
        SESSION_MAP.put(getKey(GroupTypeEnum.GROUP.getType(), session.getRoomId()), session);
    }

    @Override
    public ChatRoomSession get(ChatRoomSessionKey key) {
        return SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public ImmutablePair<ChatRoomSession, ChatRoomSession> getPairSession(ImmutablePair<SingleSessionKey, SingleSessionKey> pair) {
        return null;
    }

    @Override
    public void remove(ChatRoomSessionKey key) {
        SESSION_MAP.remove(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public ChatRoomSession bind(ChatRoomSession session) {
        return session;
    }

    @Override
    public boolean exist(ChatRoomSessionKey key) {
        return SESSION_MAP.containsKey(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public Map<ChatRoomSessionKey, ChatRoomSession> listByKeys(Collection<ChatRoomSessionKey> keys) {
        Map<ChatRoomSessionKey, ChatRoomSession> map = Maps.newHashMap();
        for (ChatRoomSessionKey groupSessionKey : keys) {
            ChatRoomSession chatRoomSession = get(groupSessionKey);
            map.put(groupSessionKey, chatRoomSession);
        }
        return map;
    }

    @Override
    public String getKey(String type, Long sessionId) {
        return type + PermissionsConstants.COLON_SEPARATOR + sessionId;
    }
}
