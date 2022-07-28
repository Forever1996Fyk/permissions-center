package com.ah.cloud.permissions.task.infrastructure.external;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 11:15
 **/
public interface ResourceService {

    /**
     * 创建文件获取资源id
     * @param fileName
     * @param filePath
     * @return
     */
    Long uploadFile(String fileName, String filePath);
}
