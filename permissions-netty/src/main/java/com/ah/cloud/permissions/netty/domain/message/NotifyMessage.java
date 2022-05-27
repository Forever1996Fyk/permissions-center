package com.ah.cloud.permissions.netty.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-20 11:32
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyMessage<T> {

    /**
     * 通知类型
     *
     * @see com.ah.cloud.permissions.enums.netty.NotifyTypeEnum
     */
    private Integer notifyType;

    /**
     * 通知内容
     */
    private T content;
}
