package com.ah.cloud.permissions.edi.domain.record.dto;

import com.ah.cloud.permissions.enums.edi.BizRetryStatusEnum;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 16:31
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStatusDTO {

    /**
     *
     */
    private Long id;

    private BizRetryStatusEnum bizRetryStatusEnum;
}
