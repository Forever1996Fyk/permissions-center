package com.ah.cloud.permissions.netty.domain.message;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 11:16
 **/
public interface Transportable {

    /**
     * 消息字节数据
     * @return
     */
    byte[] getBody();

    /**
     * 数据长度
     * @return
     */
    int getLength();
}
