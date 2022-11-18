package com.ah.cloud.permissions.biz.application.strategy.sms;

import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.sms.AbstractSmsCodeTemplate;
import com.ah.cloud.permissions.biz.domain.sms.Template;
import com.ah.cloud.permissions.biz.infrastructure.constant.CacheConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.enums.common.SmsErrorCodeEnum;
import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 15:19
 **/
@Slf4j
@Component
public abstract class AbstractSmsCodeService extends AbstractSmsService {
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    @Override
    protected void validationTemplate(Template template) {
        AbstractSmsCodeTemplate smsCodeTemplate = (AbstractSmsCodeTemplate) template;
        ErrorCode errorCode = null;
        List<String> phoneNumbers = smsCodeTemplate.getPhoneNumbers();
        if (phoneNumbers.size() > 1) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_PHONE_NUMBER_SIZE_MUST_ONE;
        }
        Long effectiveTime = smsCodeTemplate.getEffectiveTime();
        Long expireTime = smsCodeTemplate.getExpireTime();
        if (expireTime == 0) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_EXPIRE_TIME_IS_ZERO;
        }
        if (effectiveTime == 0) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_EFFECTIVE_TIME_IS_ZERO;
        }
        // 判断有效时间是否已经超过过期时间 (秒)
        long restSeconds = DateUtils.getCurrentSeconds() - effectiveTime;
        if (restSeconds > expireTime) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_IN_PERIOD_OF_VALIDITY;
        }
        // 验证码生成签名，防止恶意调用 (MD5加密)
        String sign = generateSign(smsCodeTemplate);
        if (!StringUtils.equals(sign, smsCodeTemplate.getSign())) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_SIGN_ERROR;
        }
        if (redisCacheHandleStrategy.existByKey(getSignKey(sign))) {
            errorCode = SmsErrorCodeEnum.SEND_CODE_SIGN_USED;
        }
        if (Objects.nonNull(errorCode)) {
            throw new SmsException(errorCode);
        }
        redisCacheHandleStrategy.setCacheObjectByExpire(getSignKey(sign), sign, expireTime, TimeUnit.SECONDS);
    }

    private String getSignKey(String sign) {
        return CacheConstants.SMS_CODE_SIGN_PREFIX + sign;
    }

    /**
     * 生成md5签名
     * @param smsCodeTemplate
     * @return
     */
    protected String generateSign(AbstractSmsCodeTemplate smsCodeTemplate) {
        String phoneNumber = smsCodeTemplate.getPhoneNumbers().get(0);
        return DigestUtils.md5Hex(
                new StringBuilder(phoneNumber)
                        .append(smsCodeTemplate.getEffectiveTime())
                        .append(smsCodeTemplate.getRandom())
                        .toString()
        );

    }
}
