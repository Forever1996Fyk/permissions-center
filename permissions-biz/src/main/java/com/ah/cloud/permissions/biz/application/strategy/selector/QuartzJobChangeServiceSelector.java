package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.quartz.QuartzJobChangeService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.QuartzJobChangeTypeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-11 14:18
 **/
@Component
public class QuartzJobChangeServiceSelector {
    @Resource
    private List<QuartzJobChangeService> list;

    public QuartzJobChangeService select(QuartzJobChangeTypeEnum quartzJobChangeTypeEnum) {
        for (QuartzJobChangeService quartzJobChangeService : list) {
            if (Objects.equals(quartzJobChangeService.getQuartzJobChangeTypeEnum(), quartzJobChangeTypeEnum)) {
                return quartzJobChangeService;
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "QuartzJobChangeService");
    }
}
