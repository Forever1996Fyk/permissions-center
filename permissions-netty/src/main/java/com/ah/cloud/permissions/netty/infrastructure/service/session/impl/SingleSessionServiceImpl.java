package com.ah.cloud.permissions.netty.infrastructure.service.session.impl;

import com.ah.cloud.permissions.biz.application.helper.RedisKeyHelper;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SpringUtils;
import com.ah.cloud.permissions.netty.domain.session.DistributionSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import com.ah.cloud.permissions.netty.infrastructure.constant.SessionConstants;
import com.ah.cloud.permissions.netty.infrastructure.service.session.SessionService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 16:53
 **/
@Slf4j
@Component
public class SingleSessionServiceImpl implements SessionService<SingleSession, SingleSessionKey> {
    private final static Map<String, SingleSession> SESSION_MAP = Maps.newConcurrentMap();

    @Override
    public void save(SingleSession session) {
        SESSION_MAP.put(getKey(session.getMsgSourceEnum().getPlatform(), session.getUserId()), session);
        session.getChannel().attr(SessionConstants.SINGLE_SESSION_ATTRIBUTE_KEY).set(session);

        // 构建分布式session
        RedisCacheHandleStrategy redisCacheHandleStrategy = SpringUtils.getBean(RedisCacheHandleStrategy.class);
        String sessionKey = RedisKeyHelper.getImDistributionSessionKey(session.getUserId());
        DistributionSession distributionSession = DistributionSession.builder()
                .userId(session.getUserId())
                .port(session.getPort())
                .host(session.getHost())
                .build();
        redisCacheHandleStrategy.setCacheObject(sessionKey, JsonUtils.toJSONString(distributionSession));
    }

    @Override
    public SingleSession get(SingleSessionKey key) {
        return SESSION_MAP.get(this.getKey(key.getMsgSourceEnum().getPlatform(), key.getSessionId()));
    }

    @Override
    public ImmutablePair<SingleSession, SingleSession> getPairSession(ImmutablePair<SingleSessionKey, SingleSessionKey> pair) {
        return ImmutablePair.of(this.get(pair.getLeft()), this.get(pair.getRight()));
    }

    @Override
    public void remove(SingleSessionKey key) {
        SingleSession session = SESSION_MAP.remove(this.getKey(key.getMsgSourceEnum().getPlatform(), key.getSessionId()));
        // 关闭通道
        session.getChannel().close();
        // 构建分布式session
        RedisCacheHandleStrategy redisCacheHandleStrategy = SpringUtils.getBean(RedisCacheHandleStrategy.class);
        String sessionKey = RedisKeyHelper.getImDistributionSessionKey(session.getUserId());
        redisCacheHandleStrategy.deleteObject(sessionKey);
    }

    @Override
    public SingleSession bind(SingleSession session) {
        this.save(session);
        return session;
    }

    @Override
    public boolean exist(SingleSessionKey key) {
        return SESSION_MAP.containsKey(this.getKey(key.getMsgSourceEnum().getPlatform(), key.getSessionId()));
    }

    @Override
    public Map<SingleSessionKey, SingleSession> listByKeys(Collection<SingleSessionKey> keys) {
        Map<SingleSessionKey, SingleSession> map = Maps.newHashMap();
        for (SingleSessionKey singleSessionKey : keys) {
            SingleSession singleSession = get(singleSessionKey);
            map.put(singleSessionKey, singleSession);
        }
        return map;
    }

    @Override
    public String getKey(String type, Long sessionId) {
        return sessionId + PermissionsConstants.COLON_SEPARATOR + type;
    }
}
