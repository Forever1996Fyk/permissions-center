package com.ah.cloud.permissions.biz.domain.token;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-14 18:04
 **/
public interface Token {

    /**
     * 获取访问token
     * @return
     */
    String getAccessToken();

    /**
     * 获取过期时间
     * @return
     */
    Long getExpiresIn();
}
