package com.ah.cloud.permissions.workflow.domain.form.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-15 11:59
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormModelVo {

    /**
     * 表单id
     */
    private Long id;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单编码
     */
    private String code;

    /**
     * 表单项配置
     */
    private String config;

    /**
     * 表单内容
     */
    private List<String> fields;
}
