package com.ah.cloud.permissions.biz.domain.user;

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
     * 用户名称
     */
    private String name;

    /**
     * 当前部门编码
     */
    private String deptCode;

    /**
     * 用户头像
     */
    private String avatar;
}
