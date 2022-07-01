package com.ah.cloud.permissions.biz.domain.user.vo;

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
     * 用于头像路径
     */
    private String avatar;

    /**
     * 账号状态(1:正常,2:停用,3:注销)
     */
    private Integer status;

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private Date createdTime;

    /**
     * 行记录最近修改时间
     */
    private Date modifiedTime;
}
