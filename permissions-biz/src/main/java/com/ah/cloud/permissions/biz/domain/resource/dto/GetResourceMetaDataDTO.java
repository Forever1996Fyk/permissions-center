package com.ah.cloud.permissions.biz.domain.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-06 16:22
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResourceMetaDataDTO {

    /**
     * 资源id
     */
    private Long resId;
}
