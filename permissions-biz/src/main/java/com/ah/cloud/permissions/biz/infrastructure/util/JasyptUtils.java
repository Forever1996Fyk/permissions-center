package com.ah.cloud.permissions.biz.infrastructure.util;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 09:55
 **/
public class JasyptUtils {

    /**
     * 加密方法
     * @param salt 盐
     * @param targetString 待加密字符串
     * @return
     */
    public static String encrypt(String salt, String targetString) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(salt);
        return basicTextEncryptor.encrypt(targetString);
    }

    /**
     * 解密方法
     * @param salt 盐
     * @param targetString 待解密字符串
     * @return
     */
    public static String decrypt(String salt, String targetString) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(salt);
        return basicTextEncryptor.decrypt(targetString);
    }
}
