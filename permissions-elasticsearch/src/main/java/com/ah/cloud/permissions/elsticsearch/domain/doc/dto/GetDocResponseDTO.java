
package com.ah.cloud.permissions.elsticsearch.domain.doc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-25 17:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDocResponseDTO<T> {

    /**
     * 文档映射
     */
    private T sourceObject;
}
