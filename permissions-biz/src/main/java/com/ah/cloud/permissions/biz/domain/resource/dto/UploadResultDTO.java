package com.ah.cloud.permissions.biz.domain.resource.dto;

import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
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

    public static UploadResultDTO ofSuccess(ResultData resultData) {
        return UploadResultDTO.builder()
                .success(true)
                .data(resultData)
                .message(ErrorCodeEnum.SUCCESS.getMsg())
                .build();
    }

    public static UploadResultDTO ofFailed(String message) {
        return UploadResultDTO.builder()
                .success(false)
                .message(message)
                .build();
    }


    public boolean isFailed() {
        return !success;
    }
}
