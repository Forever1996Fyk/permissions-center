package com.ah.cloud.permissions.netty.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-14 14:14
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMessage {

    /**
     * 聊天室id
     */
    private Long roomId;

    /**
     * 发送者名称
     */
    private String fromName;

    /**
     * 发送者id
     */
    private Long fromId;

    /**
     * 聊天室动作
     */
    private Integer action;

    /**
     * 发送内容
     */
    private String content;
}
