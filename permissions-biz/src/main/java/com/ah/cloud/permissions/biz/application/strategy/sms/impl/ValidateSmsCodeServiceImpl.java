package com.ah.cloud.permissions.biz.application.strategy.sms.impl;

import com.ah.cloud.permissions.biz.application.strategy.sms.AbstractSmsCodeService;
import com.ah.cloud.permissions.biz.domain.sms.Template;
import com.ah.cloud.permissions.biz.domain.sms.ValidateSmsCodeTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/18 09:10
 **/
@Component
public class ValidateSmsCodeServiceImpl extends AbstractSmsCodeService {
    private final static String LOG_MARK = "ValidateSmsCodeServiceImpl";

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public boolean support(Template template) {
        return ValidateSmsCodeTemplate.class.isAssignableFrom(template.getClass());
    }
}
