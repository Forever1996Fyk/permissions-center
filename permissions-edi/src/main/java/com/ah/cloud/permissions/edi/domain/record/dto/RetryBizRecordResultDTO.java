package com.ah.cloud.permissions.edi.domain.record.dto;

import com.ah.cloud.permissions.edi.domain.config.dto.RetryBizConfigCacheDTO;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:04
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordResultDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 分片key
     */
    private String shardingKey;

    /**
     * edi重试配置
     */
    private RetryBizConfigCacheDTO retryBizConfig;
}
