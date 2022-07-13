package com.ah.cloud.permissions.edi.domain.record.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-07 11:12
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetryBizRecordTable {

    /**
     * 数据源
     */
    private String dataSource;

    /**
     * 逻辑表
     */
    private String logicTable;

    /**
     * 真实表
     */
    private String actualTable;
}
