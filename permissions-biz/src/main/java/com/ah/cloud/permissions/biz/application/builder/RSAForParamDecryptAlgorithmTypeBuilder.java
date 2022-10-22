package com.ah.cloud.permissions.biz.application.builder;

import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmTypeBuilderSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESDecrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESEncrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSAAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSADecrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSAEncrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 16:12
 **/
@Component
public class RSAForParamDecryptAlgorithmTypeBuilder implements AlgorithmTypeBuilder<RSAEncrypt, RSADecrypt> {


    @Override
    public AlgorithmType<RSAEncrypt, RSADecrypt> build(String params) {
        return RSAAlgorithmType.builder()
                .decrypt(RSADecrypt.builder().cipher(params).build())
                .build();
    }

    @Override
    public EncryptTypeEnum getEncryptTypeEnum() {
        return EncryptTypeEnum.RSA;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AlgorithmTypeBuilderSelector.registerAlgorithmTypeBuilder(this);
    }
}
