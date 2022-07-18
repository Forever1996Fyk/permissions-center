package com.ah.cloud.permissions.task.domain.dto.business.import2;

import com.ah.cloud.permissions.task.domain.dto.ImportBaseDTO;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 15:17
 **/
@Data
public class SysUserImportDTO implements ImportBaseDTO {

    /**
     * 登录账号
     */
    @ExcelProperty(value = "账号", index = 0)
    private String account;

    /**
     * 昵称
     */
    @ExcelProperty(value = "昵称", index = 1)
    private String nickName;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号", index = 2)
    private String phone;

    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱", index = 3)
    private String email;

    /**
     * 性别(1: 男, 2: 女)
     */
    @ExcelProperty(value = "性别", index = 4)
    private String sexName;

    /**
     * 部门名称
     */
    @ExcelProperty(value = "部门编码", index = 5)
    private String deptCode;

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位编码", index = 6)
    private String postCode;

    /**
     * 错误信息
     */
    @ExcelIgnore
    private String errorMsg;

    /**
     * 导入是否成功
     */
    @ExcelIgnore
    private boolean success;
}
