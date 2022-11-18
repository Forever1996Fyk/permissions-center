package com.ah.cloud.permissions.biz.application.provider;

import com.ah.cloud.permissions.biz.application.strategy.selector.SmsServiceSelector;
import com.ah.cloud.permissions.biz.domain.code.LoginSmsCodeSendMode;
import com.ah.cloud.permissions.biz.domain.sms.AbstractSmsCodeTemplate;
import com.ah.cloud.permissions.biz.domain.sms.SmsResult;
import com.ah.cloud.permissions.biz.domain.sms.ValidateSmsCodeTemplate;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.google.common.collect.Lists;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 21:07
 **/
@Slf4j
@Component
public class LoginSmsValidateCodeProvider extends AbstractValidateCodeProvider {
    @Resource
    private SmsServiceSelector selector;

    private final static long LOGIN_VALIDATE_CODE_EXPIRE_TIME = 5 * 60;

    private final static String TEMPLATE_CODE = "SMS123456";

    @Override
    public void doSend(String code, SendMode sendMode) {
        LoginSmsCodeSendMode loginSmsCodeSendMode = (LoginSmsCodeSendMode) sendMode;
        log.info("LoginSmsValidateCodeProvider[doSend] 发送短信验证码 code:{}, sender:{}", code, loginSmsCodeSendMode);
        ValidateSmsCodeTemplate template = new ValidateSmsCodeTemplate();
        template.setEffectiveTime(loginSmsCodeSendMode.getEffectiveTime());
        template.setExpireTime(LOGIN_VALIDATE_CODE_EXPIRE_TIME);
        template.setSign(loginSmsCodeSendMode.getSign());
        template.setRandom(loginSmsCodeSendMode.getRandom());
        template.setPhoneNumbers(Lists.newArrayList(loginSmsCodeSendMode.getSender()));
        template.setTemplateCode(TEMPLATE_CODE);
        template.setTemplateParam(
                LoginSmsValidateCodeTemplateParam.builder()
                        .code(code)
                        .build()
        );
        SmsResult<Void> smsResult = selector.select(template).sendSms(template);
        if (smsResult.isFailed()) {
            throw new BizException(smsResult.getErrorCode());
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginSmsValidateCodeTemplateParam {
        /**
         * 验证码
         */
        private String code;
    }

    @Override
    public boolean support(SendMode sendMode) {
        return LoginSmsCodeSendMode.class.isAssignableFrom(sendMode.getClass());
    }
}
