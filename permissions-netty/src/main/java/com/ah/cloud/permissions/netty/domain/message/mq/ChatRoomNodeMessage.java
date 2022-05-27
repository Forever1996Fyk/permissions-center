package com.ah.cloud.permissions.netty.domain.message.mq;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-24 14:24
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomNodeMessage {

    /**
     * 发送者id
     */
    private Long fromId;

    /**
     * 房间id
     */
    private Long roomId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 消息id
     */
    private Long msgId;

    /**
     * 消息来源
     */
    private Integer msgSource;

    /**
     * 消息格式
     */
    private Integer formatType;

    /**
     * 操作类型
     */
    private Integer action;
}
