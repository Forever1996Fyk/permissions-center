package com.ah.cloud.permissions.biz.infrastructure.annotation;

import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;

import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description: 整个请求参数解密, 使用这个注解利用RequestBodyAdvice实现解密
 *               @see  com.ah.cloud.permissions.biz.infrastructure.jasypt.controller.AbstractDecryptRequestAdvice
 * @author: YuKai Fan
 * @create: 2022/10/12 11:08
 **/
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamsDecrypt {

    /**
     * 加密解密类型
     * @return
     */
    EncryptTypeEnum type() default EncryptTypeEnum.AES_FOR_PARAM_DECRYPT;

    /**
     * 解密作用域
     * @return
     */
    DecryptScope scope() default DecryptScope.FIELD;

    /**
     * 解密作用域
     */
    enum DecryptScope {
        /**
         * 整个参数都需解密
         */
        ALL,

        /**
         * 某个参数字段
         */
        FIELD,
        ;
    }
}
