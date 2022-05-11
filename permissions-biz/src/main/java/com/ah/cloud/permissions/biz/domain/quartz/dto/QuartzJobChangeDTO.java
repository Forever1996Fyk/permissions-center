package com.ah.cloud.permissions.biz.domain.quartz.dto;

import com.ah.cloud.permissions.biz.infrastructure.mq.redis.message.QuartzJobChangeMessage;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:31
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuartzJobChangeDTO {

    /**
     * 调度id
     */
    private Long jobId;


    /**
     * 变更类型
     */
    private QuartzJobChangeTypeEnum changeTypeEnum;
}
