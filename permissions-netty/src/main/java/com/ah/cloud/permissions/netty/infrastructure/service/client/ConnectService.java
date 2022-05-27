package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.session.ServerSession;

/**
 * @program: permissions-center
 * @description: connect service
 * @author: YuKai Fan
 * @create: 2022-05-20 15:21
 **/
public interface ConnectService {

    /**
     * 连接服务端
     * @param host ip
     * @param port 端口
     * @return
     */
    boolean connect(String host, Integer port);

    /**
     * 重新连接
     * @param session
     */
    void reconnect(ServerSession session);

    /**
     * 关闭连接
     */
    void disconnect();
}
