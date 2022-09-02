package com.ah.cloud.permissions.netty.application.helper;

import com.ah.cloud.permissions.biz.domain.user.DeviceInfo;
import com.ah.cloud.permissions.biz.infrastructure.util.IpUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.netty.domain.dto.MessageNodeDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 17:40
 **/
@Component
public class SessionHelper {

    /**
     * 构建节点消息
     * @param body
     * @param <T>
     * @return
     */
    public <T> MessageNodeDTO buildMessageNodeDTO(MessageBody<T> body) {
        return MessageNodeDTO.builder()
                .formatType(body.getFormatEnum().getType())
                .msgId(body.getMsgId())
                .msgType(body.getMsgTypeEnum().getType())
                .timestamp(body.getTimestamp())
                .content(JsonUtils.toJsonString(body.getData()))
                .fromId(body.getFromId())
                .toId(body.getToId())
                .build();
    }

    /**
     * 构建SingleSession
     * @param channel
     * @return
     */
    public SingleSession buildSingSession(Channel channel, Long userId, MsgSourceEnum msgSourceEnum, Integer port, DeviceInfo deviceInfo) {
        return SingleSession.builder()
                .channel(channel)
                .userId(userId)
                .msgSourceEnum(msgSourceEnum)
                .host(IpUtils.getHost())
                .port(port)
                .deviceId(deviceInfo.getDeviceId())
                .build();
    }
}
