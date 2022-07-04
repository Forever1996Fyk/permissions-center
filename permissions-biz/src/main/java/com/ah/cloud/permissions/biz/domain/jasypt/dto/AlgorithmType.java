package com.ah.cloud.permissions.biz.domain.jasypt.dto;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:19
 **/
public interface AlgorithmType<E extends Encrypt, D extends Decrypt> {

    /**
     * 获取加密算法
     * @return
     */
    E getEncrypt();

    /**
     * 获取解密算法
     * @return
     */
    D getDecrypt();
}
