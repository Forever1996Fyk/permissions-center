package com.ah.cloud.permissions.workflow.domain.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 18:02
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelVo {

    /**
     * 模型id
     */
    private String id;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型key
     */
    private String key;

    /**
     * 描述
     */
    private String desc;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后更新时间
     */
    private String lastUpdateTime;

    /**
     *
     */
    private String bpmnXml;

}
