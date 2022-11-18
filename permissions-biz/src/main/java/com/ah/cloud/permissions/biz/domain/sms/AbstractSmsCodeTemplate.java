package com.ah.cloud.permissions.biz.domain.sms;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Optional;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/16 17:09
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class AbstractSmsCodeTemplate extends AbstractTemplate {

    /**
     * 有效时间(秒)
     */
    private Long effectiveTime;

    /**
     * 过期时间(秒)
     */
    private Long expireTime;

    /**
     * 签名
     */
    private String sign;

    /**
     * 随机数
     */
    private String random;

    public Long getEffectiveTime() {
        return Optional.ofNullable(effectiveTime).orElse(0L);
    }

    public Long getExpireTime() {
        return Optional.ofNullable(expireTime).orElse(0L);
    }
}
