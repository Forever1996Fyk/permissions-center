package com.ah.cloud.permissions.biz.infrastructure.minio;

import com.ah.cloud.permissions.biz.infrastructure.config.MinioConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/16 10:41
 **/
@Slf4j
@Component
public class MinioServiceImpl extends AbstractMinioService {
    private final static String LOG_MARK = "MinioServiceImpl";

    @Autowired
    public MinioServiceImpl(MinioConfiguration minioConfiguration) {
        super(minioConfiguration);
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }
}
