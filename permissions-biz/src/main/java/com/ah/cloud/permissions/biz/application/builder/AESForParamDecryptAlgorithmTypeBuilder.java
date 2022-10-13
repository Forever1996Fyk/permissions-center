package com.ah.cloud.permissions.biz.application.builder;

import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmTypeBuilderSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESDecrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESEncrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 16:12
 **/
@Component
public class AESForParamDecryptAlgorithmTypeBuilder implements AlgorithmTypeBuilder<AESEncrypt, AESDecrypt> {


    @Override
    public AlgorithmType<AESEncrypt, AESDecrypt> build(String params) {
        return AESAlgorithmType.builder()
                .decrypt(AESDecrypt.builder().cipher(params).build())
                .build();
    }

    @Override
    public EncryptTypeEnum getEncryptTypeEnum() {
        return EncryptTypeEnum.AES_FOR_PARAM_DECRYPT;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AlgorithmTypeBuilderSelector.registerAlgorithmTypeBuilder(this);
    }
}
