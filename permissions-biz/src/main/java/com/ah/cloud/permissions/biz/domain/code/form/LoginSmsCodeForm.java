package com.ah.cloud.permissions.biz.domain.code.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.PhoneNumberValid;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 21:41
 **/
@Data
public class LoginSmsCodeForm {

    /**
     * 手机号
     */
    @PhoneNumberValid
    private String phone;
}
