package com.ah.cloud.permissions.test.jasypt;

import com.ah.cloud.permissions.biz.infrastructure.util.JasyptUtils;
import com.ah.cloud.permissions.test.BaseTest;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 14:31
 **/
public class JasyptTest {

    public static void main(String[] args) {
        String redisIp = "47.98.43.22";
        String pw = "zky_prod321#@!";
        String slat = "permission";
        System.out.println("redisIp: " + JasyptUtils.encrypt(slat, redisIp));
        System.out.println("pw: " + JasyptUtils.encrypt(slat, pw));
    }
}
