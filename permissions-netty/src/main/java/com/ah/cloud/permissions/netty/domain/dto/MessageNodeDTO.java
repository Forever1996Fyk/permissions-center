package com.ah.cloud.permissions.netty.domain.dto;

import com.ah.cloud.permissions.enums.netty.FormatEnum;
import com.ah.cloud.permissions.enums.netty.MsgSourceEnum;
import com.ah.cloud.permissions.enums.netty.MsgTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-17 18:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageNodeDTO implements Serializable {

    /**
     * 消息id uuid
     */
    private Long msgId;

    /**
     * 消息格式
     */
    private Integer formatType;

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息来源
     */
    private Integer msgSource;

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
    private Long fromId;

    /**
     * 接收id
     */
    private Long toId;
}
