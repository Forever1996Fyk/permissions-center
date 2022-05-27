package com.ah.cloud.permissions.netty.infrastructure.service.client;

import com.ah.cloud.permissions.netty.domain.dto.AckDTO;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import io.netty.channel.Channel;

/**
 * @program: permissions-center
 * @description: ack service
 * @author: YuKai Fan
 * @create: 2022-05-20 15:20
 **/
public interface AckClientService {

    /**
     * 消息ack
     * @param ackDTO
     * @param <T>
     */
    <T> void ack(AckDTO<T> ackDTO);


    /**
     * 消息ack
     * @param channel
     */
    <T> void ack(Channel channel, MessageBody<T> body);
}
