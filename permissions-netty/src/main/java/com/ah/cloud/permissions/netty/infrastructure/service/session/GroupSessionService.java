package com.ah.cloud.permissions.netty.infrastructure.service.session;

import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.key.SessionKey;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 17:05
 **/
public interface GroupSessionService<T extends ServerSession, K extends SessionKey> extends SessionService<T, K> {

    /**
     * 获取当前群组在线人数
     * @param key
     * @return
     */
    int countOnlineNum(K key);

    /**
     * 关闭群组
     * @param key
     * @return
     */
    boolean closeGroup(K key);

    /**
     * 打开群组
     * @param key
     * @return
     */
    boolean openGroup(K key);

    /**
     * 获取群组成员集合
     * @param key
     * @return
     */
    Set<Long> getGroupMemberSet(K key);

}
