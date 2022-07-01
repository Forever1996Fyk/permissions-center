package com.ah.cloud.permissions.workflow.domain.business.vo;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-29 22:43
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BusinessVariablesFormVo {

    /**
     * 表单编码
     */
    private String formCode;

    /**
     * 表单域
     */
    private List<String> options;

    /**
     * 表单配置
     */
    private String config;

    /**
     * 变量
     */
    private Map<String, Object> variables;


}
