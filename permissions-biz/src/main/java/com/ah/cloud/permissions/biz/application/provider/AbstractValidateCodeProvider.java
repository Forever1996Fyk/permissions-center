package com.ah.cloud.permissions.biz.application.provider;

import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.application.strategy.cache.CacheHandleStrategy;
import com.ah.cloud.permissions.biz.application.strategy.facroty.CacheHandleStrategyFactory;
import com.ah.cloud.permissions.biz.application.strategy.sms.SmsException;
import com.ah.cloud.permissions.biz.domain.code.SendResult;
import com.ah.cloud.permissions.biz.domain.code.ValidateResult;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.function.VoidFunction;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.extension.api.R;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Supplier;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 16:15
 **/
@Slf4j
public abstract class AbstractValidateCodeProvider implements ValidateCodeProvider {

    @Override
    public SendResult<Void> send(SendMode sendMode) {
        SendResult<Void> sendResult = SendResult.buildSuccessResult();
        try {
            if (!sendMode.isLegal()) {
                // 校验不合法
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_PHONE_OR_EMAIL_FORMAT_ERROR);
            }

            /*
            生成 随机验证码
             */
            String code = RandomUtil.randomNumbers(sendMode.generateCodeLength());

            /*
            发送验证码操作
             */
            doSend(code, sendMode);

            /*
            保存验证码
             */
            boolean result = save(sendMode, code);
            if (!result) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_SAVE_ERROR, sendMode.getSender());
            }
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[send] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (SmsException e) {
            log.error("AbstractValidateCodeProvider[send] 验证码发送失败 SmsException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[send] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return sendResult;
    }

    @Override
    public boolean save(SendMode sendMode, String code) {
        CacheHandleStrategy cacheHandleStrategy = CacheHandleStrategyFactory.getStrategy(sendMode.getRepositoryModeEnum());
        return cacheHandleStrategy.setCacheObject(sendMode.getCacheKey(), code);
    }

    @Override
    public ValidateResult<Void> validate(SendMode sendMode, String sourceCode) {
        ValidateResult<Void> validateResult = ValidateResult.buildSuccessResult();
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return validateResult;
    }

    @Override
    public ValidateResult<Void> validate(SendMode sendMode, String sourceCode, VoidFunction function) {
        ValidateResult<Void> validateResult = ValidateResult.buildSuccessResult();
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            function.apply();
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return validateResult;
    }

    @Override
    public <R> ValidateResult<R> validate(SendMode sendMode, String sourceCode, Supplier<R> supplier) {
        ValidateResult<R> validateResult = ValidateResult.buildSuccessResult();
        try {
            String code = get(sendMode);
            if (StringUtils.isEmpty(code) || StringUtils.isEmpty(sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_INVALID);
            }

            if (StringUtils.equals(code, sourceCode)) {
                throw new BizException(ErrorCodeEnum.VALIDATE_CODE_DISCORD);
            }
            validateResult.setData(supplier.get());
            delete(sendMode);
        } catch (BizException e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 BizException:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw e;
        } catch (Exception e) {
            log.error("AbstractValidateCodeProvider[validate] 验证码发送失败 Exception:{}, param:{}",
                    Throwables.getStackTraceAsString(e),
                    JsonUtils.toJsonString(sendMode));
            throw new BizException(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return validateResult;
    }

    @Override
    public String get(SendMode sendMode) {
        CacheHandleStrategy cacheHandleStrategy = CacheHandleStrategyFactory.getStrategy(sendMode.getRepositoryModeEnum());
        return cacheHandleStrategy.getCacheObject(sendMode.getCacheKey());
    }

    @Override
    public boolean delete(SendMode sendMode) {
        CacheHandleStrategy cacheHandleStrategy = CacheHandleStrategyFactory.getStrategy(sendMode.getRepositoryModeEnum());
        return cacheHandleStrategy.deleteObject(sendMode.getCacheKey());
    }

    /**
     * 执行验证码发送
     * @param code 验证码
     * @param sendMode 发送方
     * @return
     */
    public abstract void doSend(String code, SendMode sendMode);

}
