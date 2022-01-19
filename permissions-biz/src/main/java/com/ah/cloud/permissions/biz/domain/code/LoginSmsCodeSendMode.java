package com.ah.cloud.permissions.biz.domain.code;

import cn.hutool.core.util.ReUtil;
import com.ah.cloud.permissions.biz.application.provider.SendMode;
import com.ah.cloud.permissions.biz.infrastructure.constant.CacheConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.enums.RepositoryModeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 15:27
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginSmsCodeSendMode implements SendMode {

    /**
     * 手机号
     */
    private String sender;

    @Override
    public boolean isLegal() {
        return ReUtil.isMatch(PermissionsConstants.PHONE_REGEX, this.sender);
    }

    @Override
    public RepositoryModeEnum getRepositoryModeEnum() {
        return RepositoryModeEnum.REDIS;
    }

    @Override
    public String getCacheKey() {
        return CacheConstants.LOGIN_SMS_CODE_PREFIX + this.sender;
    }
}
