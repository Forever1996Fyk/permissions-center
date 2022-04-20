package com.ah.cloud.permissions.biz.domain.role.form;

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
public class SysRoleApiAddForm {

    /**
     * 角色id
     */
    @NotEmpty(message = "角色编码不能为空")
    private String roleCode;

    /**
     * apiCode列表
     */
    private List<String> apiCodeList;
}
