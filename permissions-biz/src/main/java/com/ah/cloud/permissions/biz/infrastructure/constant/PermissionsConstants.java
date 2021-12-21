package com.ah.cloud.permissions.biz.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 15:28
 **/
public class PermissionsConstants {

    /**
     * 默认操作人: 系统
     */
    public static final String OPERATOR_SYSTEM = "SYSTEM";

    /**
     * redis token key prefix
     */
    public static final String TOKEN_KEY_PREFIX = "ACCESS:";

    /**
     * token 类型
     */
    public static final String TOKEN_TYPE = "Bearer";

    /**
     * 认证请求头
     */
    public static final String TOKEN_HEAD = "Authorization";
}
