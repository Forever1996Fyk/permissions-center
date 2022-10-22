package com.ah.cloud.permissions.biz.infrastructure.minio;

import com.ah.cloud.permissions.biz.domain.minio.MinioResult;
import com.ah.cloud.permissions.enums.YesOrNoEnum;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/16 09:44
 **/
public interface MinioService {

    /**
     * 创建bucket
     * @param bucketName
     * @param isPublic
     */
    void createBucket(String bucketName, YesOrNoEnum isPublic);

    /**
     * 上传文件
     * @param bucketName
     * @param fileName
     * @param isPublic
     * @param inputStream
     * @return
     */
    MinioResult<String> upload(String bucketName, String fileName, YesOrNoEnum isPublic, InputStream inputStream);

    /**
     * 文件下载
     * @param bucketName
     * @param fileName
     * @param outputStream
     */
    void download(String bucketName, String fileName, OutputStream outputStream);
}
