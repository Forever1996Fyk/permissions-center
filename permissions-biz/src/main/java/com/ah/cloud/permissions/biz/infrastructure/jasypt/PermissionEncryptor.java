package com.ah.cloud.permissions.biz.infrastructure.jasypt;

import com.ah.cloud.permissions.biz.application.strategy.jasypt.AlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmEncryptorSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.DefaultAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.DefaultDecrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.DefaultEncrypt;
import com.ah.cloud.permissions.biz.infrastructure.util.JasyptUtils;
import com.ulisesbocchio.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import org.jasypt.encryption.StringEncryptor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 11:39
 **/
public class PermissionEncryptor implements StringEncryptor {

    private final AlgorithmEncryptorSelector selector;
    private final JasyptEncryptorConfigurationProperties properties;

    public PermissionEncryptor(AlgorithmEncryptorSelector selector, JasyptEncryptorConfigurationProperties properties) {
        this.selector = selector;
        this.properties = properties;
    }

    @Override
    public String encrypt(String s) {
        DefaultEncrypt defaultEncrypt = DefaultEncrypt.builder().original(s).salt(properties.getPassword()).build();
        DefaultAlgorithmType algorithmType = DefaultAlgorithmType.builder().encrypt(defaultEncrypt).build();
        return selector.encrypt(algorithmType).getResult();
    }

    @Override
    public String decrypt(String s) {
        DefaultDecrypt defaultDecrypt = DefaultDecrypt.builder().cipher(s).salt(properties.getPassword()).build();
        DefaultAlgorithmType algorithmType = DefaultAlgorithmType.builder().decrypt(defaultDecrypt).build();
        return selector.decrypt(algorithmType).getResult();
    }
}
