package com.ah.cloud.permissions.elsticsearch.domain.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前台类目
 *
 * @author by luman on 2020-07-28 14:44.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FrontCategory {
    /**
     * 类目名称
     */
    private String frontCateId;
}
