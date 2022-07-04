package com.ah.cloud.permissions.biz.domain.jasypt.dto;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:35
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DefaultAlgorithmType implements AlgorithmType<DefaultEncrypt, DefaultDecrypt> {

    /**
     * 加密入参
     */
    private DefaultEncrypt encrypt;

    /**
     * 解密入参
     */
    private DefaultDecrypt decrypt;
}
