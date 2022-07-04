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
        String url = "jdbc:mysql://49.234.219.109:3306/permission-center?serverTimezone=GMT%2B8&autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false";
        String username = "root";
        String password = "permission_2022..";
        System.out.println("db url: " + stringEncryptor.encrypt(url));
        System.out.println("db username: " + stringEncryptor.encrypt(username));
        System.out.println("db password: " + stringEncryptor.encrypt(password));
    }
}
