package com.ah.cloud.permissions.biz.domain.code.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EmailValid;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 23:03
 **/
@Data
public class LoginCodeForm {


    /**
     * 发送账号
     */
    @NotEmpty(message = "发送账号不能为空")
    private String sender;

    /**
     * 随机数
     */
    @NotEmpty(message = "随机数不能为空")
    private String random;

    /**
     * 签名
     */
    @NotEmpty(message = "签名不能为空")
    private String sign;

    /**
     * 当前时间(秒)
     */
    @NotNull(message = "当前时间不能为空")
    private Long currentTime;
}
