package com.ah.cloud.permissions.biz.application.provider;

import com.ah.cloud.permissions.biz.domain.code.SendResult;
import com.ah.cloud.permissions.biz.domain.code.ValidateResult;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:24
 **/
public interface ValidateCodeProvider {

    /**
     * 发送验证码
     * @param sendMode
     * @return
     */
    SendResult<Void> send(SendMode sendMode);

    /**
     * 存储验证码
     * @param sendMode
     * @param code
     * @return
     */
    boolean save(SendMode sendMode, String code);

    /**
     * 校验验证码
     * @param sendMode
     * @param sourceCode
     * @return
     */
    ValidateResult validate(SendMode sendMode, String sourceCode);

    /**
     * 获取验证码
     * @param sendMode
     * @return
     */
    String get(SendMode sendMode);

    /**
     * 删除验证码
     * @param sendMode
     * @return
     */
    boolean delete(SendMode sendMode);

    /**
     * 是否支持
     * @param sendMode
     * @return
     */
    boolean support(SendMode sendMode);
}
