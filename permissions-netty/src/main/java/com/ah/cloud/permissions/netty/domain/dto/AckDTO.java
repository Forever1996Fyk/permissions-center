package com.ah.cloud.permissions.netty.domain.dto;

import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 10:56
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AckDTO<T> {
    /**
     * 通道
     */
    private transient Channel channel;

    /**
     * 序列id
     */
    private Integer sequenceId;

    /**
     * ack间隔时间
     */
    private Integer duration;

    /**
     * 重试次数
     */
    private Integer retry;

    /**
     * 消息体
     */
    private MessageBody<T> body;
}
