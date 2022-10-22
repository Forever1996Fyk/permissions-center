package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.provider.SendMode;
import com.ah.cloud.permissions.biz.application.provider.ValidateCodeProvider;
import com.ah.cloud.permissions.biz.domain.code.SendResult;
import com.ah.cloud.permissions.biz.domain.code.ValidateResult;
import com.ah.cloud.permissions.biz.infrastructure.function.VoidFunction;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @program: permissions-center
 * @description: 验证码管理器
 * @author: YuKai Fan
 * @create: 2022-01-09 16:48
 **/
@Slf4j
@Component
public class ValidateCodeManager implements InitializingBean {

    @Resource
    private List<ValidateCodeProvider> providers;

    /**
     * 发送验证码
     * @param sendMode
     * @return
     */
    public SendResult<Void> sendCode(SendMode sendMode) {
        SendResult<Void> sendResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            sendResult = provider.send(sendMode);
            if (!Objects.isNull(sendResult)) {
                break;
            }
        }
        return sendResult;
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @return
     */
    public ValidateResult<Void> validateCode(SendMode sendMode, String code) {
        ValidateResult<Void> validateResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            validateResult = provider.validate(sendMode, code);
            if (!Objects.isNull(validateResult)) {
                break;
            }
        }
        return validateResult;
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @param function
     * @return
     */
    public ValidateResult<Void> validateCode(SendMode sendMode, String code, VoidFunction function) {
        ValidateResult<Void> validateResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            validateResult = provider.validate(sendMode, code, function);
            if (!Objects.isNull(validateResult)) {
                break;
            }
        }
        return validateResult;
    }

    /**
     * 校验验证码
     *
     * @param sendMode
     * @param code
     * @param supplier
     * @return
     */
    public <R> ValidateResult<R> validateCode(SendMode sendMode, String code, Supplier<R> supplier) {
        ValidateResult<R> validateResult = null;
        int currentPosition = 0;
        int size = this.providers.size();
        for (ValidateCodeProvider provider : providers) {
            if (!provider.support(sendMode)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("ValidateCodeProvider request with %s (%d/%d)",
                        provider.getClass().getSimpleName(), ++currentPosition, size)));
            }

            validateResult = provider.validate(sendMode, code, supplier);
            if (!Objects.isNull(validateResult)) {
                break;
            }
        }
        return validateResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(providers)) {
            throw new RuntimeException("加载ValidateCodeProvider失败, 不存在验证码处理管理器");
        }
        log.info("初始化ValidateCodeProvider集合数据:{}", JsonUtils.toJsonString(providers));
    }
}
