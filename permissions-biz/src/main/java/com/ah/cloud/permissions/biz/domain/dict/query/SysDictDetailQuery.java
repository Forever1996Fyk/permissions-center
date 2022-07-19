package com.ah.cloud.permissions.biz.domain.dict.query;

import com.ah.cloud.permissions.domain.common.PageQuery;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:23
 **/
@Data
public class SysDictDetailQuery extends PageQuery {

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典详情编码
     */
    private String dictDetailCode;

    /**
     * 字典编码名称
     */
    private String dictDetailName;
}
