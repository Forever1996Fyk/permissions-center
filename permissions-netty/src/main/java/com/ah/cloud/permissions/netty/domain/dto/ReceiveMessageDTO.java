package com.ah.cloud.permissions.netty.domain.dto;

import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import com.ah.cloud.permissions.enums.netty.FormatEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 22:46
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveMessageDTO {
    /**
     * 消息id uuid
     */
    private Long msgId;

    /**
     * 消息格式
     */
    private FormatEnum formatEnum;

    /**
     * 消息类型
     */
    private MsgTypeEnum msgTypeEnum;

    /**
     * 消息来源
     */
    private MsgSourceEnum msgSourceEnum;

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送id
     */
    private Long  fromId;

    /**
     * 接收id
     */
    private Long toId;

}
