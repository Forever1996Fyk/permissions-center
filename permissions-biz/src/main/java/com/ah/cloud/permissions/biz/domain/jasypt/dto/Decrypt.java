package com.ah.cloud.permissions.biz.domain.jasypt.dto;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:03
 **/
public interface Decrypt {

    /**
     * 密文
     * @return
     */
    String getCipher();

    /**
     * 盐
     * @return
     */
    String getSalt();

}
