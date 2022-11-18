package com.ah.cloud.permissions.biz.application.provider;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:25
 **/
public interface SendMode {

    /**
     * 发送方
     * @return String
     */
    String getSender();

    /**
     * 校验目标是否合法
     * @return boolean
     */
    boolean isLegal();

    /**
     * 生成验证码长度
     * @return Integer
     */
    default Integer generateCodeLength() {
        return PermissionsConstants.DEFAULT_GENERATE_CODE_LENGTH;
    }

    /**
     * 存储方式
     * @return RepositoryModeEnum
     */
    RepositoryModeEnum getRepositoryModeEnum();

    /**
     * 前缀类型
     *
     * @return String
     */
    String getCacheKey();
}
