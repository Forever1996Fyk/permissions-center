package com.ah.cloud.permissions.netty.domain.message;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 绑定消息
 * @author: YuKai Fan
 * @create: 2022-05-12 14:32
 **/
@Data
public class BindMessage {

    /**
     * 登录token
     */
    private String token;

    /**
     * 连接ip
     */
    private String host;

    /**
     * 连接端口
     */
    private Integer port;
}
