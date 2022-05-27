package com.ah.cloud.permissions.netty.infrastructure.service.session.impl;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.netty.domain.session.ChatRoomSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
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
public class ChatRoomSessionServiceImpl implements GroupSessionService<ChatRoomSession, GroupSessionKey> {
    private final static Map<String, ChatRoomSession> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public int countOnlineNum(GroupSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return 0;
        }
        return chatRoomSession.getChannelGroup().size();
    }

    @Override
    public boolean closeGroup(GroupSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return false;
        }
        chatRoomSession.closeGroupSession();
        return true;
    }

    @Override
    public boolean openGroup(GroupSessionKey key) {
        ChatRoomSession chatRoomSession = SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
        if (Objects.isNull(chatRoomSession)) {
            return false;
        }
        chatRoomSession.openGroupSession();
        return true;
    }

    @Override
    public void save(ChatRoomSession session) {
        SESSION_MAP.put(getKey(GroupTypeEnum.GROUP.getType(), session.getRoomId()), session);
    }

    @Override
    public ChatRoomSession get(GroupSessionKey key) {
        return SESSION_MAP.get(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public ImmutablePair<ChatRoomSession, ChatRoomSession> getPairSession(ImmutablePair<SingleSessionKey, SingleSessionKey> pair) {
        return null;
    }

    @Override
    public void remove(GroupSessionKey key) {
        SESSION_MAP.remove(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public ChatRoomSession bind(ChatRoomSession session) {
        return session;
    }

    @Override
    public boolean exist(GroupSessionKey key) {
        return SESSION_MAP.containsKey(this.getKey(key.getGroupTypeEnum().getType(), key.getSessionId()));
    }

    @Override
    public Map<GroupSessionKey, ChatRoomSession> listByKeys(Collection<GroupSessionKey> keys) {
        Map<GroupSessionKey, ChatRoomSession> map = Maps.newHashMap();
        for (GroupSessionKey groupSessionKey : keys) {
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
