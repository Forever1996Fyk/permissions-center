package com.ah.cloud.permissions.biz.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 12:17
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserInfo implements Serializable {
    /**
     * 用户id
     */
    private Long userId;
}
