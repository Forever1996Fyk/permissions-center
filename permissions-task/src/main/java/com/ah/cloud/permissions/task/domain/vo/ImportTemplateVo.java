package com.ah.cloud.permissions.task.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 10:10
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImportTemplateVo {

    /**
     * 模板id
     */
    private Long id;

    /**
     * 业务类型
     */
    private String bizType;

    /**
     * 业务类型名称
     */
    private String bizTypeName;

    /**
     * 模板url
     */
    private String templateUrl;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifyTime;

    /**
     * 更新人
     */
    private String modifier;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态类型
     */
    private String statusName;
}
