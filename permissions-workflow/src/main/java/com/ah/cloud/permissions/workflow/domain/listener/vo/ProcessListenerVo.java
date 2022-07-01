package com.ah.cloud.permissions.workflow.domain.listener.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-16 11:06
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessListenerVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 监听器名称
     */
    private String listenerName;

    /**
     * 监听器类型
     */
    private Integer listenerType;

    /**
     * 事件
     */
    private Integer event;

    /**
     * 执行类型
     */
    private Integer executeType;

    /**
     * 执行内容
     */
    private String executeContent;

    /**
     * 租户id
     */
    private String tenantId;
}
