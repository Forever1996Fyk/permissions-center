package com.ah.cloud.permissions.biz.infrastructure.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/15 09:07
 **/
public class PasswordUtils {

    private final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    /**
     * 密码加密
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * 校验密码
     * @param password
     * @param cipher
     * @return
     */
    public static boolean match(String password, String cipher) {
        return PASSWORD_ENCODER.matches(password, cipher);
    }
}
