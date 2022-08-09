package com.ah.cloud.permissions.domain.common;

import com.ah.cloud.permissions.constant.PageConstants;
import lombok.Data;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 16:37
 **/
@Data
public class PageQuery {

    /**
     * 当前页数
     */
    private Integer pageNo;

    /**
     * 每页数量
     */
    private Integer pageSize;

    public Integer getPageNo() {
        return Objects.isNull(pageNo) ? PageConstants.DEFAULT_PAGE_NUM : pageNo;
    }

    public Integer getPageSize() {
        return Objects.isNull(pageSize) ? PageConstants.DEFAULT_PAGE_SIZE : pageSize;
    }
}
