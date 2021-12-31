package com.ah.cloud.permissions.biz.domain.user;

import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 权限用户实体
 * @author: YuKai Fan
 * @create: 2021-12-22 18:11
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorityDTO {
    /**
     * 主键id
     */
    private Long userId;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 账号状态枚举
     */
    private UserStatusEnum userStatusEnum;


    /**
     * 用户权限集合
     */
    private Set<String> authorities;
}
