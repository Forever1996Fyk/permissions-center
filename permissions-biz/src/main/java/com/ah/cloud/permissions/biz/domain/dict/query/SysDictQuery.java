package com.ah.cloud.permissions.biz.domain.dict.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:15
 **/
@Data
public class SysDictQuery extends PageQuery {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;
}
