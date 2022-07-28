package com.ah.cloud.permissions.task.domain.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 10:17
 **/
@Data
public class ImportTemplateQuery extends PageQuery {

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 状态
     */
    private Integer status;
}
