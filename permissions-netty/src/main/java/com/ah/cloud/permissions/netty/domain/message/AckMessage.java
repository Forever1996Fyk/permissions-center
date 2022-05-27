package com.ah.cloud.permissions.netty.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: ack消息
 * @author: YuKai Fan
 * @create: 2022-05-16 13:49
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AckMessage {
    /**
     * 消息序列id
     */
    private Integer sequenceId;
}
