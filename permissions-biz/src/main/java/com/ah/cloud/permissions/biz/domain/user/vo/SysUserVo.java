package com.ah.cloud.permissions.biz.domain.user.vo;

import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-04 17:24
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserVo {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

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
     * 性别名称
     */
    private String sexName;

    /**
     * 用于头像路径
     */
    private String avatar;

    /**
     * 账号状态(1:正常,2:停用,3:注销)
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 行记录创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    /**
     * 部门编码
     */
    private String deptCode;


    /**
     * 数据权限
     */
    private Integer dataScope;
}
