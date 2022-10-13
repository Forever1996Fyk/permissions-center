package com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 14:35
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RSAAlgorithmType implements AlgorithmType<RSAEncrypt, RSADecrypt> {

    /**
     * 加密入参
     */
    private RSAEncrypt encrypt;

    /**
     * 解密入参
     */
    private RSADecrypt decrypt;
}
