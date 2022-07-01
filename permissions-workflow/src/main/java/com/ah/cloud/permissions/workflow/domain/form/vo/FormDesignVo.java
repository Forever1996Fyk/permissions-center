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
 * @create: 2022-06-15 12:20
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormDesignVo {

    /**
     * 表单id
     */
    private Long id;

    /**
     * 表单项配置
     */
    private String config;

    /**
     * 表单内容
     */
    private List<String> fields;
}
