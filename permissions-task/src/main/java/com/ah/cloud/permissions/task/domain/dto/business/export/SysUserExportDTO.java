package com.ah.cloud.permissions.task.domain.dto.business.export;

import com.ah.cloud.permissions.task.domain.dto.ExportBaseDTO;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 13:47
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserExportDTO implements ExportBaseDTO {

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
    @ExcelProperty(value = "部门名称", index = 5)
    private String deptName;

    /**
     * 岗位名称
     */
    @ExcelProperty(value = "岗位名称", index = 6)
    private String postName;

    /**
     * 账号状态(1:正常,2:停用,3:注销)
     */
    @ExcelProperty(value = "账号状态", index = 7)
    private String userStatusName;
}
