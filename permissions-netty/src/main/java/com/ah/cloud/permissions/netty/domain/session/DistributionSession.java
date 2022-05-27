package com.ah.cloud.permissions.netty.domain.session;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 21:47
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DistributionSession {
    /**
     * 本节点 ip
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 用户id
     */
    private Long userId;
}
