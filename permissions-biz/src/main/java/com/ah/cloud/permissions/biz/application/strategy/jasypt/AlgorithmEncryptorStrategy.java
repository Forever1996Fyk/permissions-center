package com.ah.cloud.permissions.biz.application.strategy.jasypt;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.*;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.DecryptResult;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.EncryptResult;

/**
 * @program: permissions-center
 * @description: 算法加密策略
 * @author: YuKai Fan
 * @create: 2022-07-04 14:59
 **/
public interface AlgorithmEncryptorStrategy {

    /**
     * 加密算法
     * @param algorithmType
     * @return
     */
    <E extends Encrypt, D extends Decrypt> EncryptResult encrypt(AlgorithmType<E, D> algorithmType);

    /**
     * 解密算法
     * @param algorithmType
     * @return
     */
    <E extends Encrypt, D extends Decrypt> DecryptResult decrypt(AlgorithmType<E, D> algorithmType);

    /**
     * 是否支持算法
     *
     * @param algorithmType
     * @param <E>
     * @param <D>
     * @return
     */
    <E extends Encrypt, D extends Decrypt> boolean support(AlgorithmType<E, D> algorithmType);
}
