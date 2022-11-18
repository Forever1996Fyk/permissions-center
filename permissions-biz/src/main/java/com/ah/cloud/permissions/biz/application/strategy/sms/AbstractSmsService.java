package com.ah.cloud.permissions.biz.application.strategy.sms;

import com.ah.cloud.permissions.biz.domain.sms.SmsResult;
import com.ah.cloud.permissions.biz.domain.sms.Template;
import com.ah.cloud.permissions.biz.domain.third.aliyun.sms.AliyunSmsRequest;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.third.aliyun.sms.AliyunSmsClient;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.PatternUtils;
import com.ah.cloud.permissions.biz.infrastructure.validator.PhoneNumberValidator;
import com.ah.cloud.permissions.enums.common.SmsErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/16 17:17
 **/
@Slf4j
@Component
public abstract class AbstractSmsService implements SmsService {
    @Resource
    private AliyunSmsClient aliyunSmsClient;

    @Override
    public SmsResult<Void> sendSms(Template template) {
        log.info("{}[sendSms] send sms by template, param is {}", getLogMark(), template);
        /*
        校验参数
         */
        checkParam(template);

        /*
        前置处理
         */
        beforeHandle(template);
        AliyunSmsRequest request = this.buildSmsRequest(template);
        try {
            aliyunSmsClient.sendSms(request);
            log.error("{}[sendSms] send sms success by template, param is {}", getLogMark(), template);
            afterHandle(template);
            return SmsResult.ofSuccess();
        } catch (Exception e) {
            log.error("{}[sendSms] send sms by template with Exception, param is {}, reason is {}", getLogMark(), template, Throwables.getStackTraceAsString(e));
            return SmsResult.ofFailed(SmsErrorCodeEnum.SEND_SMS_FAILED);
        }
    }

    /**
     * 前置处理
     * @param template
     */
    protected void beforeHandle(Template template) {

    }

    /**
     * 后置处理
     * @param template
     */
    protected void afterHandle(Template template) {

    }

    protected AliyunSmsRequest buildSmsRequest(Template template) {
        return AliyunSmsRequest.builder()
                .phoneNumbers(
                        Joiner.on(PermissionsConstants.COMMA_SEPARATOR).join(template.getPhoneNumbers())
                )
                .signName(template.getSignName())
                .templateCode(template.getTemplateCode())
                .templateParam(JsonUtils.toJsonString(template.getTemplateParam()))
                .build();
    }

    /**
     * 验证模版参数
     * @param template
     * @return
     */
    protected void validationTemplate(Template template) {
    }

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 参数校验
     * @param template
     * @return
     */
    private void checkParam(Template template) {
        ErrorCode errorCode = null;
        String templateCode = template.getTemplateCode();
        if (StringUtils.isBlank(templateCode)) {
            errorCode =  SmsErrorCodeEnum.TEMPLATE_CODE_IS_NULL;
        }
        String signName = template.getSignName();
        if (StringUtils.isBlank(signName)) {
            errorCode =  SmsErrorCodeEnum.SIGN_NAME_IS_NULL;
        }
        Collection<String> phoneNumbers = template.getPhoneNumbers();
        if (CollectionUtils.isEmpty(phoneNumbers)) {
            errorCode =  SmsErrorCodeEnum.PHONE_NUMBER_IS_NULL;
        }
        if (phoneNumbers.size() > PermissionsConstants.ONE_THOUSAND_INT) {
            errorCode =  SmsErrorCodeEnum.PHONE_NUMBER_SIZE_MAX;
        }
        boolean notMatchPhoneNumber = phoneNumbers.stream()
                .anyMatch(phoneNumber -> !PatternUtils.isMobile(phoneNumber));
        if (notMatchPhoneNumber) {
            errorCode =  SmsErrorCodeEnum.PHONE_NUMBER_SIZE_MAX;
        }
        if (Objects.nonNull(errorCode)) {
            throw new SmsException(errorCode);
        }
    }
}
