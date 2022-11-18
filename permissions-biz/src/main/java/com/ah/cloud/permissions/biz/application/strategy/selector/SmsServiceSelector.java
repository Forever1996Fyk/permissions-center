package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.sms.SmsService;
import com.ah.cloud.permissions.biz.domain.sms.Template;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/18 08:37
 **/
@Component
public class SmsServiceSelector {

    @Resource
    private List<SmsService> smsServiceList;

    /**
     * 根据模版获取短信服务
     *
     * @param template
     * @return
     */
    public SmsService select(Template template) {
        return smsServiceList.stream()
                .filter(smsService -> smsService.support(template))
                .findFirst()
                .orElseThrow(() -> new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "SmsService"));
    }
}
