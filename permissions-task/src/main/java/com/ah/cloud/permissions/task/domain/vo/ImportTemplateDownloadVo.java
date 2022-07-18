package com.ah.cloud.permissions.task.domain.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 10:23
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImportTemplateDownloadVo {

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板下载地址
     */
    private String templateUrl;
}
