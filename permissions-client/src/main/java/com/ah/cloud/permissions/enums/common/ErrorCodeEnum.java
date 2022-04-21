package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;
import lombok.Getter;

/**
 * @Description 错误码
 * @Author yin.jinbiao
 * @Date 2021/9/27 14:31
 * @Version 1.0
 */
@Getter
public enum ErrorCodeEnum implements ErrorCode {

    /**
     * 公共系统响应码
     */
    SUCCESS(0,"成功"),
    UNKNOWN_ERROR(100_0_001,"未知错误"),
    SYSTEM_ERROR(100_0_002,"系统异常"),

    /**
     * 公共参数响应码
     */
    PARAM_MISS(100_1_001,"缺少必要参数[%s]"),
    PARAM_ILLEGAL(100_1_002,"参数非法"),

    /**
     * 公共业务异常
     */
    BUSINESS_FAIL(100_2_001,"业务处理异常"),

    /**
     * 公共操作异常
     */
    CALL_LIMITED(100_3_001,"调用次数过多"),
    TOKEN_EXCEPTION(100_3_002, "Token验证异常"),
    VERSION_ERROR(100_3_003, "数据版本异常，请重试"),

    /**
     * 授权相关响应码
     */
    UNKNOWN_PERMISSION(200_0_001,"未知的权限错误, 请联系管理员"),
    PERMISSION_DENY(200_0_002,"权限不足, 拒绝访问"),
    AUTHORITY_API_DISABLED(200_1_001, "当前接口已被禁用,无法访问"),
    AUTHORITY_API_NOT_OPEN(200_1_002, "当前接口未公开, 无法访问"),
    VALIDATE_CODE_USER_IS_NULL(500_0_001, "短信认证失败, 用户不存在"),

    /**
     * 认证相关响应码
     */
    AUTHENTICATION_ACCOUNT_ERROR(300_0_001, "账号错误, 请重新输入"),
    AUTHENTICATION_ACCOUNT_DISABLED(300_0_002, "当前账号已被禁用"),
    AUTHENTICATION_ACCOUNT_LOG_OFF(300_0_003, "当前账号已被注销"),
    AUTHENTICATION_LOGIN_PROCESS_EXCEPTION(300_0_004, "登录流程异常, 请联系管理员"),
    AUTHENTICATION_USER_ID_ERROR(300_0_005, "用户id[%s]错误"),

    /**
     * 用户相关响应码
     */
    USER_PHONE_IS_EXISTED(400_0_001, "当前用户手机号[%s]已存在, 无需重复新增"),
    USER_NOT_EXIST(400_0_002, "用户不存在"),
    CURRENT_USER_IS_NOT_EXIST(400_0_003, "当前登录用户不存在"),
    ANONYMOUS_USER_HAS_NO_INFO(400_0_004, "当前匿名用户无具体信息"),

    /**
     * 验证码相关错误
     */
    VALIDATE_CODE_SAVE_ERROR(500_0_001, "[%s]存储验证码失败"),
    VALIDATE_CODE_INVALID(500_0_002, "验证码已过期, 请重新发送"),
    VALIDATE_CODE_DISCORD(500_0_003, "验证码错误, 请重新输入"),
    VALIDATE_CODE_PHONE_OR_EMAIL_FORMAT_ERROR(500_0_004, "手机或邮箱格式错误"),

    /**
     * 角色相关错误码
     */
    ROLE_CODE_IS_EXISTED(600_0_001, "当前角色编码[%s]已存在, 新增失败"),
    CURRENT_ROLE_IS_NOT_EXISTED_UPDATE_FAILED(600_0_002, "当前角色[%s]已存在, 更新失败"),
    CURRENT_ROLE_ASSOCIATION_USER_DELETE_FAILED(600_0_003, "当前角色[%s]关联用户信息, 无法被删除"),
    ROLE_CODE_CANNOT_CHANGE_UPDATE_FAILED(600_0_004, "角色编码禁止变更"),

    /**
     * 菜单相关错误码
     */
    MENU_CODE_IS_EXISTED(700_0_001, "当前菜单编码[%s]已存在, 新增失败"),
    MENU_INFO_IS_NOT_EXISTED_UPDATE_FAILED(700_0_002, "当前菜单信息不存在, 更新失败"),
    MENU_CODE_CANNOT_CHANGE_UPDATE_FAILED(700_0_003, "菜单编码禁止变更"),

    /**
     * 系统api相关错误码
     */
    API_CODE_IS_EXISTED(800_0_001, "当前接口编码[%s]已存在, 新增失败"),
    API_INFO_IS_NOT_EXISTED_UPDATE_FAILED(800_0_002, "当前接口信息不存在, 更新失败"),
    API_CODE_CANNOT_CHANGE_UPDATE_FAILED(800_0_003, "菜单编码禁止变更"),
    API_INFO_IS_NOT_EXISTED(800_0_004, "当前接口信息不存在"),
    ;

    private int code;
    private String msg;

    ErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
