package com.ah.cloud.permissions.biz.application.strategy.jasypt;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.*;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.common.JasyptErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:25
 **/
@Slf4j
@Component
public abstract class AbstractAlgorithmEncryptorStrategy implements AlgorithmEncryptorStrategy {

    @Override
    public <E extends Encrypt, D extends Decrypt> EncryptResult encrypt(AlgorithmType<E, D> algorithmType) {
        E encrypt = algorithmType.getEncrypt();
        if (Objects.isNull(encrypt)) {
            log.error("{}[encrypt] data encrypt failed, reason is encryptor null", getLogMark());
            throw new BizException(JasyptErrorCodeEnum.JASYPT_ENCRYPT_IS_NULL);
        }
        EncryptResult encryptResult = doEncrypt(algorithmType.getEncrypt());
        log.info("{}[encrypt] result is {}", getLogMark(), JsonUtils.toJsonString(encryptResult));
        return encryptResult;
    }

    @Override
    public <E extends Encrypt, D extends Decrypt> DecryptResult decrypt(AlgorithmType<E, D> algorithmType) {
        D decrypt = algorithmType.getDecrypt();
        if (Objects.isNull(decrypt)) {
            log.error("{} decrypt data decrypt failed, reason is decrypt null", getLogMark());
            throw new BizException(JasyptErrorCodeEnum.JASYPT_DECRYPT_IS_NULL);
        }
        DecryptResult decryptResult = doDecrypt(algorithmType.getDecrypt());
        log.info("{}[decrypt] result is {}", getLogMark(), JsonUtils.toJsonString(decryptResult));
        return decryptResult;
    }

    /**
     * 解密算法
     * @param decrypt
     * @return
     */
    protected abstract DecryptResult doDecrypt(Decrypt decrypt);

    /**
     * 加密算法
     * @param encrypt
     * @return
     */
    protected abstract EncryptResult doEncrypt(Encrypt encrypt);

    /**
     * 日志标记
     * @return
     */
    protected abstract String getLogMark();
}
