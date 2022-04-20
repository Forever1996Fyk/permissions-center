package com.ah.cloud.permissions.biz.infrastructure.annotation;

import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-07 18:26
 **/
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiMethodLog {
}
