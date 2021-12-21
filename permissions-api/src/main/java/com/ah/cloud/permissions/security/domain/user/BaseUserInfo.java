package com.ah.cloud.permissions.security.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description: 用户基本信息
 * @author: YuKai Fan
 * @create: 2021-12-17 17:16
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserInfo implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;
}
