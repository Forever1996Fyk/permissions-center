package com.ah.cloud.permissions.biz.domain.dict.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:07
 **/
@Data
public class SysDictAddForm {

    /**
     * 字典编码
     */
    @NotEmpty(message = "字典编码不能为空")
    private String dictCode;

    /**
     * 字典名称
     */
    @NotEmpty(message = "字典名称不能为空")
    private String dictName;

    /**
     * 字典类型
     */
    @NotNull(message = "字典类型不能为空")
    private Integer type;

    /**
     * 是否可变
     */
    private Integer change;
}
