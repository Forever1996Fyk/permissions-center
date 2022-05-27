package com.ah.cloud.permissions.netty.domain.message;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-13 17:50
 **/
@Data
public class SingleMessage {

    /**
     * 发送者id
     */
    private Long fromUserId;

    /**
     * 接收者id
     */
    private Long toUserId;

    /**
     * 内容
     */
    private String content;
}
