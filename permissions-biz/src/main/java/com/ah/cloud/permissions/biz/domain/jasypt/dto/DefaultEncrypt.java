package com.ah.cloud.permissions.biz.domain.jasypt.dto;

import com.ah.cloud.permissions.enums.jasypt.AlgorithmTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:04
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefaultEncrypt implements Encrypt {

    /**
     * 原文
     */
    private String original;

    /**
     * 盐
     */
    private String salt;
}
