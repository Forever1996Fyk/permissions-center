package com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.jasypt.encryption.StringEncryptor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 16:21
 **/
public class Md5StringEncryptor implements StringEncryptor {

    @Override
    public String encrypt(String message) {
        return DigestUtils.md5Hex(message);
    }

    @Override
    public String decrypt(String encryptedMessage) {
        return null;
    }
}
