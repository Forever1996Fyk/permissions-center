package com.ah.cloud.permissions.biz.domain.resource.meta.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 08:05
 **/
@Data
public class ResourceMetaDataQuery extends PageQuery {

    /**
     * 资源id
     */
    private Long resId;
}
