package com.ah.cloud.permissions.biz.infrastructure.mq.redis.message;

import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 09:55
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuartzJobChangeMessage implements Serializable {

    /**
     * 调度id
     */
    private Long jobId;

    /**
     * 变更类型
     */
    private QuartzJobChangeTypeEnum changeTypeEnum;
}
