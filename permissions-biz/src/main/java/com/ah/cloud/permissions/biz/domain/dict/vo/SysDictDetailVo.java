package com.ah.cloud.permissions.biz.domain.dict.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 17:00
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SysDictDetailVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典详情编码
     */
    private String dictDetailCode;

    /**
     * 字典详情名称
     */
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

    /**
     * 行记录创建者
     */
    private String creator;

    /**
     * 行记录最近更新人
     */
    private String modifier;

    /**
     * 行记录创建时间
     */
    private String createdTime;

    /**
     * 行记录最近修改时间
     */
    private String modifiedTime;

}
