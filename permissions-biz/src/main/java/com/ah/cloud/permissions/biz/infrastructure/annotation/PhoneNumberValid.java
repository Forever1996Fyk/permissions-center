package com.ah.cloud.permissions.biz.infrastructure.annotation;


import com.ah.cloud.permissions.biz.infrastructure.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:21
 **/
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValid {

    String message() default "手机号格式不正确";

    Class[] groups() default {};

    Class[] payload() default {};
}
