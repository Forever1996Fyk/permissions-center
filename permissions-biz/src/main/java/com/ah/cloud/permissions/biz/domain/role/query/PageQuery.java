package com.ah.cloud.permissions.biz.domain.role.query;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import lombok.Data;
import lombok.Setter;

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
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    public Integer getPageNum() {
        return Objects.isNull(pageNum) ? PermissionsConstants.DEFAULT_PAGE_NUM : pageNum;
    }

    public Integer getPageSize() {
        return Objects.isNull(pageSize) ? PermissionsConstants.DEFAULT_PAGE_SIZE : pageSize;
    }
}
