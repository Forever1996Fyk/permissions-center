package com.ah.cloud.permissions.biz.domain.dict.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:55
 **/
@Data
public class SysDictDetailUpdateForm {

    /**
     * 主键id
     */
    @NotNull(message = "id不能为空")
    private Long id;

    /**
     * 字典详情编码
     */
    @NotEmpty(message = "字典详情编码不能为空")
    private String dictDetailCode;

    /**
     * 字典详情名称
     */
    @NotEmpty(message = "字典详情名称不能为空")
    private String dictDetailDesc;

    /**
     * 排序
     */
    private Integer detailOrder;

    /**
     * 是否可编辑
     */
    private Integer edit;

    /**
     * 备注
     */
    private String remark;
}
