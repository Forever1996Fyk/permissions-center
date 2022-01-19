package com.ah.cloud.permissions.biz.infrastructure.validator;


import com.ah.cloud.permissions.biz.infrastructure.annotation.PasswordValid;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:23
 **/
public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }

        return s.length() >= 6 && s.length() <= 20;
    }
}
