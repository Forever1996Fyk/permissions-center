package com.ah.cloud.permissions.biz.domain.resource.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:16
 **/
@Data
public class ResourceFileQuery extends PageQuery {

    /**
     * 所属id
     */
    private Long ownerId;

    /**
     * 资源id
     */
    private Long resId;

    /**
     * 资源名称
     */
    private String fileName;
}
