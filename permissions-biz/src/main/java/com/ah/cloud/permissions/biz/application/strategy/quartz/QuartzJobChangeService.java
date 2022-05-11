package com.ah.cloud.permissions.biz.application.strategy.quartz;

import com.ah.cloud.permissions.biz.domain.quartz.dto.QuartzJobChangeDTO;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 11:30
 **/
public interface QuartzJobChangeService {

    /**
     * 处理变更
     * @param dto
     */
    void handleChange(QuartzJobChangeDTO dto);

    /**
     * 获取
     * @return
     */
    QuartzJobChangeTypeEnum getQuartzJobChangeTypeEnum();
}
