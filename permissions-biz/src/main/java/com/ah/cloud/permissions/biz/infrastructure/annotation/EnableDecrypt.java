package com.ah.cloud.permissions.biz.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description: 开启参数解密
 * @author: YuKai Fan
 * @create: 2022/10/12 17:41
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDecrypt {
}
