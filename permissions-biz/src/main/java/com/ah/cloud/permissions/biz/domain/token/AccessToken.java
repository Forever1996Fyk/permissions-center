package com.ah.cloud.permissions.biz.domain.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-21 18:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessToken implements Token, Serializable {

    /**
     * token
     */
    private String accessToken;

    /**
     * 过期时间(秒)
     */
    private Long expiresIn;
}
