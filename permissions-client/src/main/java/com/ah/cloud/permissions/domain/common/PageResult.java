package com.ah.cloud.permissions.domain.common;

import lombok.Data;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-06 15:55
 **/
@Data
public class PageResult<T> {
    /**
     * 当前页数
     */
    private int pageNum;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 查询总数
     */
    private long total;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 分页数据
     */
    private List<T> rows;
}
