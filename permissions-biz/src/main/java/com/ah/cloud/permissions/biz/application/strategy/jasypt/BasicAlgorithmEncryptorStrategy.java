package com.ah.cloud.permissions.biz.application.strategy.jasypt;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.*;
import com.ah.cloud.permissions.biz.infrastructure.util.JasyptUtils;
import com.ah.cloud.permissions.enums.jasypt.AlgorithmTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:20
 **/
@Component
public class BasicAlgorithmEncryptorStrategy extends AbstractAlgorithmEncryptorStrategy {
    private final static String LOG_MARK = "BasicAlgorithmEncryptorStrategy";

    @Override
    protected DecryptResult doDecrypt(Decrypt decrypt) {
        DefaultDecrypt defaultDecrypt = (DefaultDecrypt) decrypt;
        return DecryptResult.builder()
                .result(JasyptUtils.decrypt(defaultDecrypt.getSalt(), defaultDecrypt.getCipher()))
                .build();
    }

    @Override
    protected EncryptResult doEncrypt(Encrypt encrypt) {
        DefaultEncrypt defaultEncrypt = (DefaultEncrypt) encrypt;
        return EncryptResult.builder()
                .result(JasyptUtils.encrypt(defaultEncrypt.getSalt(), defaultEncrypt.getOriginal()))
                .build();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public <E extends Encrypt, D extends Decrypt> boolean support(AlgorithmType<E, D> algorithmType) {
        return algorithmType.getClass().isAssignableFrom(DefaultAlgorithmType.class);
    }
}
