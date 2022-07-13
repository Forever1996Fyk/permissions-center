package com.ah.cloud.permissions.edi.domain.record.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRetryBizRecordDTO {

    /**
     * 业务单据号
     */
    private String bizNo;

    /**
     * 业务id
     */
    private Long bizId;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 系统来源
     */
    private String bizSource;

    /**
     * 执行参数
     */
    private String bizParams;

    /**
     * 扩展信息
     */
    private String ext;

    /**
     * 备注
     */
    private String remark;
}
