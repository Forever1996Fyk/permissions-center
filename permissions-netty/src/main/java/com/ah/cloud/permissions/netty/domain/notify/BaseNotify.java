package com.ah.cloud.permissions.netty.domain.notify;

import com.ah.cloud.permissions.netty.domain.session.ServerSession;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 10:34
 **/
public interface BaseNotify {

    /**
     * session key
     * @return
     */
    SingleSessionKey getSessionKey();

    /**
     * session
     * @return
     */
    ServerSession getSession();
}
