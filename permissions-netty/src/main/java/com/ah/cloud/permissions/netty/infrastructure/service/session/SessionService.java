package com.ah.cloud.permissions.netty.infrastructure.service.session;

import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.key.SessionKey;
import com.ah.cloud.permissions.netty.domain.session.key.SingleSessionKey;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Collection;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 16:13
 **/
public interface SessionService<T extends ServerSession, K extends SessionKey> {

    /**
     * 保存session
     * @param session
     */
    void save(T session);

    /**
     * 获取session
     * @param key
     * @return
     */
    T get(K key);

    /**
     * 获取session
     * @param pair left: to-singleSessionKey, right: from-singleSessionKey
     * @return pair left: to-session, right: from-session
     */
    ImmutablePair<T, T> getPairSession(ImmutablePair<SingleSessionKey, SingleSessionKey> pair);

    /**
     * 移除session
     * @param key
     */
    void remove(K key);

    /**
     * 绑定session
     * @param session
     * @return
     */
    T bind(T session);

    /**
     * session是否存在
     * @param key
     * @return
     */
    boolean exist(K key);

    /**
     * 根据key集合获取session集合
     * @param keys
     * @return
     */
    Map<K, T> listByKeys(Collection<K> keys);

    /**
     * 获取key
     * @param type
     * @param sessionId
     * @return
     */
    String getKey(String type, Long sessionId);
}
