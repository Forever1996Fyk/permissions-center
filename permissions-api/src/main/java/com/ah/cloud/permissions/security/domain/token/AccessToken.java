package com.ah.cloud.permissions.security.domain.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class AccessToken {

    /**
     * token
     */
    private String access_token;

    /**
     * 过期时间(秒)
     */
    private Long expires_in;
}
