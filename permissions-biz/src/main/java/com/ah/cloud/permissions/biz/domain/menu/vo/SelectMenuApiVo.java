package com.ah.cloud.permissions.biz.domain.menu.vo;

import lombok.*;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-12 13:56
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class SelectMenuApiVo {

    /**
     * 已分配接口集合
     */
    private Set<ApiInfoVo> allocatedApiSet;


    @Getter
    @Builder
    @ToString
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiInfoVo {

        /**
         * api id
         */
        private Long id;

        /**
         * api编码
         */
        private String apiCode;

        /**
         * api名称
         */
        private String apiName;
    }
}
