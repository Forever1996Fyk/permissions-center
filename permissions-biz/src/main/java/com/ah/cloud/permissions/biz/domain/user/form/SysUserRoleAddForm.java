package com.ah.cloud.permissions.biz.domain.user.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 设置用户角色form
 * @author: YuKai Fan
 * @create: 2022-03-21 22:11
 **/
@Data
public class SysUserRoleAddForm {

    /**
     * 用户昵称
     */
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 角色编码列表
     */
    private List<String> roleCodeList;

    /**
     * 角色id列表
     */
    private List<String> roleIdList;
}
