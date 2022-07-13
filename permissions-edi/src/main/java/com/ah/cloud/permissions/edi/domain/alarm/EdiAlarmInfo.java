package com.ah.cloud.permissions.edi.domain.alarm;

import com.ah.cloud.permissions.enums.edi.EdiAlarmTypeEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-12 10:15
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdiAlarmInfo {

    /**
     * 标题
     */
    private String title;

    /**
     * edi记录总数
     */
    private Integer total;

    /**
     * 类型
     */
    private EdiAlarmTypeEnum ediAlarmTypeEnum;

    /**
     * 业务类型
     */
    private Integer bizType;

    /**
     * 业务名称
     */
    private String bizName;
}
