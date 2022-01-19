package com.ah.cloud.permissions.biz.domain.code.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 21:41
 **/
@Data
public class LoginEmailCodeForm {

    /**
     * 邮箱
     */
    @EmailValid
    private String email;
}
