package com.ah.cloud.permissions.biz.infrastructure.validator;


import com.ah.cloud.permissions.biz.infrastructure.annotation.PhoneNumberValid;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-23 22:23
 **/
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        Pattern p = Pattern.compile(PermissionsConstants.PHONE_REGEX);
        return p.matcher(s).matches();
    }
}
