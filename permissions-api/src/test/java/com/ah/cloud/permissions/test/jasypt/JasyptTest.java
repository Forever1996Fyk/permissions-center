package com.ah.cloud.permissions.test.jasypt;

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
public class JasyptTest extends BaseTest {
    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    public void datasourceEncryptor() {
        String pw = "zky_prod123!@#";
        System.out.println("db pw: " + stringEncryptor.encrypt(pw));
    }
}
