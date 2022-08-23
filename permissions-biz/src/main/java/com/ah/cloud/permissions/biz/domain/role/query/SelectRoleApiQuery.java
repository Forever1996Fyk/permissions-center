package com.ah.cloud.permissions.biz.domain.role.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-15 10:06
 **/
@Data
public class SelectRoleApiQuery extends PageQuery {

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 接口编码
     */
    private String apiCode;
}
