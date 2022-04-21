package com.ah.cloud.permissions.biz.infrastructure.annotation;


import com.ah.cloud.permissions.biz.infrastructure.validator.EmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:21
 **/
@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValid {

    String message() default "邮箱格式不正确";

    Class[] groups() default {};

    Class[] payload() default {};
}
