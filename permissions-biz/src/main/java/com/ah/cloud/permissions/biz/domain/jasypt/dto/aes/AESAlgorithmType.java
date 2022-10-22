package com.ah.cloud.permissions.biz.domain.jasypt.dto.aes;

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
public class AESAlgorithmType implements AlgorithmType<AESEncrypt, AESDecrypt> {

    /**
     * 加密入参
     */
    private AESEncrypt encrypt;

    /**
     * 解密入参
     */
    private AESDecrypt decrypt;
}
