package com.ah.cloud.permissions.task.domain.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 15:05
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImportExportBizTypeVo {

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务名称
     */
    private String bizTypeName;
}
