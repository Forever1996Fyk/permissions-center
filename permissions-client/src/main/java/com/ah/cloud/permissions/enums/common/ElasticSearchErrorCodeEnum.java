package com.ah.cloud.permissions.enums.common;

import com.ah.cloud.permissions.exception.ErrorCode;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 15:15
 **/
public enum ElasticSearchErrorCodeEnum implements ErrorCode {


    /**
     * es通用错误码
     */
    ES_OPERATION_EXCEPTION(7_1_10_0_000, "ES操作index[%s]异常"),
    ES_INDEX_NAME_IS_NULL(7_1_10_0_001, "索引名称不能为空"),
    ES_INDEX_DOC_ID_IS_NULL(7_1_10_0_002, "文档不能为空"),

    /**
     * 创建索引失败
     */
    ES_CREATE_INDEX_FAILED_PARAM_IS_NULL(7_2_10_0_001, "创建索引失败, 参数实体为空"),

    /**
     * es查询
     */
    ES_SEARCH_PARAM_IS_NULL(7_3_10_0_001, "es查询参数为空"),
    ES_SEARCH_RESPONSE_STATUS_ERROR(7_3_10_0_002, "es返回状态异常[status=%s]"),
    ES_SEARCH_PAGE_FAILED(7_3_10_0_003, "es分页查询异常"),

    /**
     * es 文档操作
     */
    ES_OPERATE_DOC_FAILED(7_4_10_0_001, "%s文档失败"),
    ES_BULK_PARAMS_IS_NULL(7_4_10_0_002, "[%s]文档批处理失败, 参数为空"),
    ES_OPERATE_DOC_NOT_SUPPORT(7_4_10_0_003, "[%s]文档操作不支持"),

    /**
     * es文档响应处理
     */
    ES_DOC_RESPONSE_STATUS_HANDLER_IS_NULL(7_5_10_0_001, "文档响应状态处理器为空"),
    ES_DOC_RESPONSE_STATUS_IS_NULL(7_5_10_0_002, "文档响应状态为空"),

    ES_DOC_RESPONSE_RESULT_HANDLER_IS_NULL(7_5_10_1_001, "文档响应结果处理器为空"),
    ES_DOC_RESPONSE_RESULT_IS_NULL(7_5_10_1_002, "文档响应结果类型为空"),
    ;

    private final int code;
    private final String msg;

    ElasticSearchErrorCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return 0;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
