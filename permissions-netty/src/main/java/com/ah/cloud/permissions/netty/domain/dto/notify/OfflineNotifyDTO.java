package com.ah.cloud.permissions.netty.domain.dto.notify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 14:00
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfflineNotifyDTO {

    /**
     * 下线原因类型
     */
    private Integer offlineReasonType;

    /**
     * 下线时间
     */
    private String offlineTime;

    /**
     * 下线通告
     */
    private String offlineNotice;
}
