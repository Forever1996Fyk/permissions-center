package com.ah.cloud.permissions.netty.domain.session;


import io.netty.channel.Channel;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 15:33
 **/
public interface ServerSession {

    /**
     * 当前channel
     * @return
     */
    Channel getChannel();

    /**
     * 获取ip地址
     * @return
     */
    String getHost();

    /**
     * 获取端口号
     * @return
     */
    Integer getPort();
}
