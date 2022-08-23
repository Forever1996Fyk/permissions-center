package com.ah.cloud.permissions.biz.domain.dict.vo;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-19 16:16
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SysDictVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 字典编码
     */
    private String dictCode;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 类型:系统/业务
     */
    private Integer type;

    /**
     * 是否可变
     */
    private Integer change;

    /**
     * 是否可变
     */
    private String changeName;
}
