package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-06 21:36
 **/
public enum FileErrorCodeEnum implements ErrorCode {

    /**
     * 文件相关错误码
     */
    FILE_INPUT_STREAM_ERROR(2_10_0_001, "文件输入流错误"),
    FILE_SUFFIX_TYPE_ERROR(2_10_0_002, "文件后缀类型不正确"),
    FILE_UPLOAD_ERROR(2_10_0_003, "[%s]文件上传错误"),
    FILE_RESOURCE_ID_IS_NULL(2_10_0_004, "资源id为空"),
    FILE_RESOURCE_IS_EXPIRED(2_10_0_005, "当前资源[%s]已过期"),
    FILE_RESOURCE_DOWNLOAD_ERROR(2_10_0_006, "当前资源[%s]下载失败"),
    FILE_CONVERT_FROM_REQUEST_ERROR(2_10_0_007, "文件转换错误"),
    FILE_RESOURCE_NOT_EXISTED(2_10_0_007, "当前资源[%s]不存在"),
    FILE_RESOURCE_REAL_DELETE_FAILED(2_10_0_008, "当前资源[%s]真实文件删除失败"),

    /**
     * bucket
     */
    BUCKET_CREATE_FAILED(2_11_0_001, "文件Bucket创建失败"),
    ;


    private final int code;
    private final String msg;

    FileErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
