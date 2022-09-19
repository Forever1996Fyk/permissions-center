package com.ah.cloud.permissions.biz.infrastructure.sequence.model;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:10
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BizSequence {

    /**
     * 序列id
     */
    private long sequenceId;

    public boolean isValid() {
        return this.getSequenceId() != 0;
    }
}
