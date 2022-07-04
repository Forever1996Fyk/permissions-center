package com.ah.cloud.permissions.biz.domain.jasypt.dto;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:01
 **/
public interface Encrypt {

    /**
     * 原文
     * @return
     */
    String getOriginal();

    /**
     * 盐
     * @return
     */
    String getSalt();
}
