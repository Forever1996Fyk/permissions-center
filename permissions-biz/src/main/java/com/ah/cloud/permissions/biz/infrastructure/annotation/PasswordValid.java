package com.ah.cloud.permissions.biz.infrastructure.annotation;


import com.ah.cloud.permissions.biz.infrastructure.validator.PasswordValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:21
 **/
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {

    String message() default "密码不能为空";

    Class[] groups() default {};

    Class[] payload() default {};
}
