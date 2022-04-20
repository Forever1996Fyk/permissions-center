package com.ah.cloud.permissions.biz.domain.api.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-04 17:46
 **/
@Data
public class SysApiQuery extends PageQuery {

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口类型
     */
    private List<Integer> apiType;

    /**
     * 账号状态(1:启用,2:停用)
     */
    private Integer status;

    /**
     * 是否认证
     */
    private Integer auth;

    /**
     * 是否公开
     */
    private Integer open;
}
