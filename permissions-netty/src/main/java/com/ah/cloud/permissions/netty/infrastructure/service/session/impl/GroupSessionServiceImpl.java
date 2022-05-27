package com.ah.cloud.permissions.netty.infrastructure.service.session.impl;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.netty.GroupTypeEnum;
import com.ah.cloud.permissions.netty.domain.session.GroupSession;
import com.ah.cloud.permissions.netty.domain.session.GroupSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.infrastructure.service.session.GroupSessionService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @program: permissions-center
 * @description: 群组session管理
 * @author: YuKai Fan
 * @create: 2022-05-26 17:16
 **/
@Slf4j
@Component
public class GroupSessionServiceImpl implements GroupSessionService<GroupSession, GroupSessionKey> {
    private final static Map<String, GroupSession> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public int countOnlineNum(GroupSessionKey key) {
        return 0;
    }

    @Override
    public boolean closeGroup(GroupSessionKey key) {
        return false;
    }

    @Override
    public boolean openGroup(GroupSessionKey key) {
        return false;
    }

    @Override
    public void save(GroupSession session) {
        String key = this.getKey(GroupTypeEnum.GROUP.getType(), session.getGroupId());
        SESSION_MAP.put(key, session);
    }

    @Override
    public GroupSession get(GroupSessionKey groupSessionKey) {
        String key = this.getKey(groupSessionKey.getGroupTypeEnum().getType(), groupSessionKey.getSessionId());
        return SESSION_MAP.get(key);
    }

    @Override
    public ImmutablePair<GroupSession, GroupSession> getPairSession(ImmutablePair<SingleSessionKey, SingleSessionKey> pair) {
        return null;
    }

    @Override
    public void remove(GroupSessionKey key) {

    }

    @Override
    public GroupSession bind(GroupSession session) {
        return null;
    }

    @Override
    public boolean exist(GroupSessionKey key) {
        return false;
    }

    @Override
    public Map<GroupSessionKey, GroupSession> listByKeys(Collection<GroupSessionKey> keys) {
        return null;
    }

    @Override
    public String getKey(String type, Long sessionId) {
        return type + PermissionsConstants.COLON_SEPARATOR + sessionId;
    }
}
