package com.ah.cloud.permissions.biz.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 15:28
 **/
public class PermissionsConstants {

    /**
     * 超级管理员id
     */
    public static final Long SUPER_ADMIN = 1L;

    /**
     * 默认操作人: 系统
     */
    public static final String OPERATOR_SYSTEM = "SYSTEM";

    /**
     * redis token key prefix
     */
    public static final String TOKEN_KEY_PREFIX = "ACCESS:";

    /**
     * redis token key
     */
    public static final String TOKEN_USER_KEY = "ACCESS_USER_AUTHORITY";

    /**
     * token 类型
     */
    public static final String TOKEN_TYPE = "Bearer";

    /**
     * 认证请求头
     */
    public static final String TOKEN_HEAD = "Authorization";

    /**
     * 用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 默认生成验证码长度
     */
    public static final Integer DEFAULT_GENERATE_CODE_LENGTH = 6;

    /**
     * 手机号正则
     */
    public static final String PHONE_REGEX = "^(1[3-9]\\d{9}$)";

    /**
     * 邮箱正则
     */
    public static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 逗号分隔符
     */
    public static final String COMMA_SEPARATOR = ",";

    /**
     * 点分隔符
     */
    public static final String DOT_SEPARATOR = ".";


    /**
     * Long  0
     */
    public static final Long ZERO = 0L;

    /**
     * Integer 0
     */
    public static final Integer ZERO_INT = 0;

    /**
     * String  0
     */
    public static final String ZERO_STR = "0";

    /**
     * String "
     */
    public static final String STR_EMPTY = "";

    /**
     * -1
     */
    public static final Integer NEGATIVE_ONE_INT = -1;

    /**
     * -1L
     */
    public static final Long NEGATIVE_ONE_LONG = -1L;
}
