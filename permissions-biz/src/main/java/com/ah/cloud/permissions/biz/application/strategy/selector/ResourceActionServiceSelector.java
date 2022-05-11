package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.resource.ResourceActionService;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.PositionTypeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 15:03
 **/
@Component
public class ResourceActionServiceSelector {
    @Resource
    private List<ResourceActionService> resourceActionServiceList;

    /**
     * 根据位置类型获取处理器
     * @param positionTypeEnum
     * @return
     */
    public ResourceActionService select(PositionTypeEnum positionTypeEnum) {
        for (ResourceActionService resourceActionService : resourceActionServiceList) {
            if (Objects.equals(resourceActionService.getPositionTypeEnum(), positionTypeEnum)) {
                return resourceActionService;
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "ResourceActionService");
    }

    /**
     * 默认使用本地资源处理器
     * @return
     */
    public ResourceActionService select() {
        return select(PositionTypeEnum.LOCAL);
    }

}
