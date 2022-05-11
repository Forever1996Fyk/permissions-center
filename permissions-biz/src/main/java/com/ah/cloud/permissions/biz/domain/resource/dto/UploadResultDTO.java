package com.ah.cloud.permissions.biz.domain.resource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 上传结果
 * @author: YuKai Fan
 * @create: 2022-05-07 07:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultDTO {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 消息
     */
    private String message;

    /**
     * 处理结果数据
     */
    private ResultData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResultData {
        /**
         * 资源地址
         */
        private String resourceUrl;

        /**
         * 资源路径
         */
        private String resourcePath;

        /**
         * 资源名称
         */
        private String resourceName;
    }


    public boolean isFailed() {
        return !success;
    }
}
