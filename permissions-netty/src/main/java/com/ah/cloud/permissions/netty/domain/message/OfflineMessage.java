package com.ah.cloud.permissions.netty.domain.message;

import com.ah.cloud.permissions.enums.netty.OfflineReasonEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-19 16:03
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfflineMessage {

    /**
     * 下线 userId
     */
    private Long toUserId;

    /**
     * 下线原因
     */
    private OfflineReasonEnum offlineReasonEnum;

}
