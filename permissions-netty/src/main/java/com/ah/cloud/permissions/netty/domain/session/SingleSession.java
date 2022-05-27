package com.ah.cloud.permissions.netty.domain.session;

import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.netty.infrastructure.constant.SessionConstants;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 16:25
 **/
@Getter
@Builder
public class SingleSession implements ServerSession {

    /**
     * 通道
     */
    private transient Channel channel;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 消息来源
     */
    private MsgSourceEnum msgSourceEnum;

    /**
     * 连接ip
     */
    private String host;

    /**
     * 连接端口
     */
    private Integer port;

    /**
     * 设备id
     */
    private String deviceId;
}
