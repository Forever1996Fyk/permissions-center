package com.ah.cloud.permissions.biz.infrastructure.minio;

import cn.hutool.core.io.FastByteArrayOutputStream;
import com.ah.cloud.permissions.biz.domain.minio.MinioResult;
import com.ah.cloud.permissions.biz.infrastructure.config.MinioConfiguration;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.common.FileErrorCodeEnum;
import com.google.common.base.Throwables;
import io.minio.*;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/16 10:13
 **/
@Slf4j
public abstract class AbstractMinioService implements MinioService {
    private final MinioClient minioClient;
    private final MinioConfiguration minioConfiguration;

    public AbstractMinioService(MinioConfiguration minioConfiguration)  {
        this.minioConfiguration = minioConfiguration;
        minioClient = MinioClient.builder()
                .credentials(minioConfiguration.getUsername(), minioConfiguration.getPassword())
                .endpoint(minioConfiguration.getEndpoint())
                .build();
    }

    @Override
    public MinioResult<String> upload(String bucketName, String fileName, YesOrNoEnum isPublic,  InputStream inputStream) {
        createBucket(bucketName, isPublic);
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(args);
            return MinioResult
                    .ofSuccess(buildAccessUrl(bucketName, fileName));
        } catch (Exception e) {
            log.error("{}[upload] upload file to minio failed, bucketName is {}, fileName is {}, reason is {}"
                    , getLogMark()
                    , bucketName
                    , fileName
                    , Throwables.getStackTraceAsString(e)
            );
            throw new BizException(FileErrorCodeEnum.FILE_UPLOAD_ERROR, fileName);
        }
    }

    @Override
    public void createBucket(String bucketName, YesOrNoEnum policy) {
        try {
            boolean exists = minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            if (exists) {
                bucketExisted(bucketName);
            } else {
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(bucketName)
                                .build()
                );
                minioClient.setBucketPolicy(
                        SetBucketPolicyArgs.builder()
                                .bucket(bucketName)
                                .config(buildBucketPolicy(bucketName, policy))
                                .build()
                );
            }
        } catch (Exception e) {
            log.error("{}[createBucket] minio create bucket failed, bucketName is {}, reason is {}", getLogMark(), bucketName, Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.BUCKET_CREATE_FAILED);
        }
    }

    @Override
    public void download(String bucketName, String fileName, OutputStream outputStream) {
        GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .build();
        try (GetObjectResponse response = minioClient.getObject(getObjectArgs)) {
            byte[] buf = new byte[1024];
            int len;
            try (FastByteArrayOutputStream os = new FastByteArrayOutputStream()){
                while ((len = response.read(buf)) != -1) {
                    os.write(buf, 0, len);
                }
                os.flush();
                byte[] bytes = os.toByteArray();
                outputStream.write(bytes);
                outputStream.flush();
            }
        } catch (Exception e) {
            log.error("{}[download] download resource from minio failed, bucketName is {}, reason is {}", getLogMark(), bucketName, Throwables.getStackTraceAsString(e));
            throw new BizException(FileErrorCodeEnum.BUCKET_CREATE_FAILED);
        }
    }

    /**
     * 日志标记
     *
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 存在bucketName的后续操作
     * @param bucketName
     */
    protected void bucketExisted(String bucketName) {}

    /**
     * 构建访问地址
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    private String buildAccessUrl(String bucketName, String fileName) {
        return minioConfiguration.getAccessUri() + "/" + bucketName + "/" + fileName;
    }

    protected String buildBucketPolicy(String bucketName, YesOrNoEnum policy) {
        return  "{\"Version\":\"2012-10-17\"," +
                "\"Statement\":[{\"Effect\":\"Allow\"," +
                "\"Principal\":{\"AWS\":[\"*\"]}," +
                "\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\"]," +
                "\"Resource\":[\"arn:aws:s3:::avatar\"]}," +
                "{\"Effect\":\"Allow\"," +
                "\"Principal\":{\"AWS\":[\"*\"]}," +
                "\"Action\":[\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\"]," +
                "\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
    }
}
