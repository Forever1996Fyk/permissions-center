package com.ah.cloud.permissions.task.domain.dto.business.param;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 14:05
 **/
@Data
public class SysUserExportParamDTO {

    /**
     * 登录账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别(1: 男, 2: 女)
     */
    private Integer sex;

    /**
     * 部门编码
     */
    private String deptCode;

    /**
     * 岗位编码
     */
    private String postCode;

    /**
     * 账号状态(1:正常,2:停用,3:注销)
     */
    private Integer status;
}
