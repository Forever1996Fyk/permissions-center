package com.ah.cloud.permissions.biz.infrastructure.annotation;

import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;

import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description: 单个请求参数解密, 使用这个注解利用自定义AOP实现解密
 * @author: YuKai Fan
 * @create: 2022/10/12 11:08
 **/
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamDecrypt {

    /**
     * 解密类型
     * @return
     */
    EncryptTypeEnum type() default EncryptTypeEnum.AES_FOR_PARAM_DECRYPT;
}
