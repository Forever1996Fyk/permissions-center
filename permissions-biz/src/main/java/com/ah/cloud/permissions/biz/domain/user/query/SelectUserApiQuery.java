package com.ah.cloud.permissions.biz.domain.user.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-15 10:06
 **/
@Data
public class SelectUserApiQuery extends PageQuery {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 接口编码
     */
    private String apiCode;
}
