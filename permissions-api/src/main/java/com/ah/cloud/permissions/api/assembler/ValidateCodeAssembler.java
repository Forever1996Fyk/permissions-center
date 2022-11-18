package com.ah.cloud.permissions.api.assembler;

import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.ReUtil;
import com.ah.cloud.permissions.biz.application.provider.SendMode;
import com.ah.cloud.permissions.biz.domain.code.LoginEmailCodeSendMode;
import com.ah.cloud.permissions.biz.domain.code.LoginSmsCodeSendMode;
import com.ah.cloud.permissions.biz.domain.code.form.LoginEmailCodeForm;
import com.ah.cloud.permissions.biz.domain.code.form.LoginSmsCodeForm;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 22:05
 **/
@Component
public class ValidateCodeAssembler {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public LoginSmsCodeSendMode convert(LoginSmsCodeForm form) {
        return LoginSmsCodeSendMode.builder()
                .sender(form.getPhone())
                .effectiveTime(form.getCurrentTime())
                .random(form.getRandom())
                .sign(form.getSign())
                .build();
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public LoginEmailCodeSendMode convert(LoginEmailCodeForm form) {
        return LoginEmailCodeSendMode.builder()
                .sender(form.getEmail())
                .build();
    }

    /**
     * 数据转换
     * @param sender
     * @return
     */
    public SendMode convert(String sender) {
        boolean match = ReUtil.isMatch(PermissionsConstants.PHONE_REGEX, sender);
        if (match) {
            return LoginSmsCodeSendMode.builder()
                    .sender(sender)
                    .build();
        } else {
            return LoginEmailCodeSendMode.builder()
                    .sender(sender)
                    .build();
        }
    }
}
