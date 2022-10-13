package com.ah.cloud.permissions.test.jasypt;

import com.ah.cloud.permissions.biz.application.strategy.jasypt.AESAlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.RSAStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 14:31
 **/
public class JasyptTest {

    public static void main(String[] args) throws Exception {
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("286c7874e0d14fd89ffe041e61a1820c");
        System.out.println(encryptor.encrypt("123456"));
    }
}
