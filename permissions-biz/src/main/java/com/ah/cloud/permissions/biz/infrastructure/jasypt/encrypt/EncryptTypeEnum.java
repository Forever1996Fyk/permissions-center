package com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt;

/**
 * @program: permissions-center
 * @description: 加密类型
 * @author: YuKai Fan
 * @create: 2022/10/12 11:10
 **/
public enum EncryptTypeEnum {

    /**
     * 加密类型
     */
    RSA,

    /**
     * 为@ParamDecrypt注解使用
     * @see com.ah.cloud.permissions.biz.infrastructure.annotation.ParamDecrypt
     */
    AES_FOR_PARAM_DECRYPT,
    ;
}
