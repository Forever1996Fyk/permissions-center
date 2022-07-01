package com.ah.cloud.permissions.workflow.domain.form.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 12:00
 **/
@Data
public class FormModelQuery extends PageQuery {

    /**
     * 表单编码
     */
    private String code;

    /**
     * 表单名称
     */
    private String name;
}
