package com.ah.cloud.permissions.biz.infrastructure.annotation;

import com.ah.cloud.permissions.biz.infrastructure.validator.EmailValidator;
import com.ah.cloud.permissions.biz.infrastructure.validator.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @program: permissions-center
 * @description: 枚举校验
 * @author: YuKai Fan
 * @create: 2022-04-03 15:21
 **/
@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {

    String message() default "无效的数据";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();

    String enumMethod();
}
