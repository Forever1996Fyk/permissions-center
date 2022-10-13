package com.ah.cloud.permissions.biz.domain.jasypt.dto.aes;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 14:36
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AESEncrypt implements Encrypt {

    /**
     * 原文
     */
    private String original;

    /**
     * 盐
     */
    private String salt;
}
