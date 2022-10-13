package com.ah.cloud.permissions.biz.application.strategy.jasypt;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.DecryptResult;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.EncryptResult;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: AES算法策略，是使用AES256算法作为基础的对称加密算法, 对明文的加密和解密需要同一个密钥
 * @author: YuKai Fan
 * @create: 2022/10/12 14:39
 **/
@Component
public class AESAlgorithmEncryptorStrategy extends AbstractAlgorithmEncryptorStrategy {
    private final static String LOG_MARK = "AESAlgorithmEncryptorStrategy";

    private final static String DEFAULT_PUBLIC_KEY = "286c7874e0d14fd89ffe041e61a1820c";

    private final AES256TextEncryptor encryptor;

    public AESAlgorithmEncryptorStrategy() {
        this.encryptor = new AES256TextEncryptor();
    }

    @Override
    protected DecryptResult doDecrypt(Decrypt decrypt) {
        encryptor.setPassword(StringUtils.isBlank(decrypt.getSalt()) ? DEFAULT_PUBLIC_KEY : decrypt.getSalt());
        return DecryptResult.builder()
                .result(encryptor.decrypt(decrypt.getCipher()))
                .build();
    }

    @Override
    protected EncryptResult doEncrypt(Encrypt encrypt) {
        encryptor.setPassword(StringUtils.isBlank(encrypt.getSalt()) ? DEFAULT_PUBLIC_KEY : encrypt.getSalt());
        return EncryptResult.builder()
                .result(encryptor.encrypt(encrypt.getOriginal()))
                .build();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public <E extends Encrypt, D extends Decrypt> boolean support(AlgorithmType<E, D> algorithmType) {
        return AESAlgorithmType.class.isAssignableFrom(algorithmType.getClass());
    }
}
