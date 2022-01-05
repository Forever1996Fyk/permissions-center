package com.ah.cloud.permissions.biz.domain.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-27 14:21
 **/
@Data
public class SysUserAddForm {
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    /**
     * 性别(1: 男, 2: 女)
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;
}
