package com.ah.cloud.permissions.biz.application.strategy.sms;

import com.ah.cloud.permissions.biz.domain.sms.SmsResult;
import com.ah.cloud.permissions.biz.domain.sms.Template;
import org.apache.poi.ss.formula.functions.T;

/**
 * @program: permissions-center
 * @description: 短息服务
 * @author: YuKai Fan
 * @create: 2022/11/16 16:43
 **/
public interface SmsService {

    /**
     * 发送短信
     *
     * @param template
     * @return
     */
    SmsResult<Void> sendSms(Template template);

    /**
     * 是否支持
     * @param template
     * @return
     */
    boolean support(Template template);
}
