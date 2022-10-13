package com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
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
public class RSADecrypt implements Decrypt {
    /**
     * 密文
     */
    private String cipher;

    /**
     * 盐
     */
    private String salt;
}
