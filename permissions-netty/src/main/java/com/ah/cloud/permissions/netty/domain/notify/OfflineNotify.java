package com.ah.cloud.permissions.netty.domain.notify;

import com.ah.cloud.permissions.enums.netty.OfflineReasonEnum;
import com.ah.cloud.permissions.netty.domain.session.SingleSessionKey;
import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 10:41
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfflineNotify implements BaseNotify {

    /**
     * session map
     */
    private SingleSessionKey singleSessionKey;

    /**
     * session
     */
    private SingleSession session;

    /**
     * 下线原因类型
     */
    private OfflineReasonEnum offlineReasonEnum;

    /**
     * 下线内容
     */
    private String content;
}
