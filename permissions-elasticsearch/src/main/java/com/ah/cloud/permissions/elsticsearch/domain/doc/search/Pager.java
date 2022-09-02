package com.ah.cloud.permissions.elsticsearch.domain.doc.search;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-26 09:16
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Pager {

    /**
     * 默认分页大小
     */
    public static final int DEFAULT_PAGE_SIZE = 100;

    /**
     * 页数
     */
    private int page;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总数
     */
    private long total;

    /**
     * 是否结束
     */
    private boolean isEnd;

    public Pager(int page, int pageSize) {
        this.page = Math.max(page, 1);
        if (pageSize <= 0) {
            this.pageSize = DEFAULT_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public static Pager newPager() {
        return new Pager(1, DEFAULT_PAGE_SIZE);
    }


    public static Pager newPager(int pageSize) {
        return new Pager(1, pageSize);
    }

    public static Pager newPager(int page, int pageSize, boolean isEnd) {
        Pager pager = new Pager(page, pageSize);
        return Pager.builder()
                .isEnd(isEnd)
                .page(page)
                .pageSize(pageSize)
                .build();
    }

    public void setEnd(boolean end) {
        this.isEnd = end;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getCurrentPageSizeTotal() {
        return (long) this.page * this.pageSize;
    }

    public int getStart() {
        return (page - 1) * pageSize;
    }
}
