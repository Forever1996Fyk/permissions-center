package com.ah.cloud.permissions.test.jasypt;

import com.ah.cloud.permissions.biz.application.strategy.jasypt.AESAlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.RSAStringEncryptor;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.StandardRSAStringEncryptor;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jasypt.util.text.AES256TextEncryptor;

import java.security.Key;
import java.text.MessageFormat;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 14:31
 **/
public class JasyptTest {

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static void main(String[] args) throws Exception {
        System.out.println(buildBucketPolicy("image", YesOrNoEnum.NO));
    }

    private static String buildBucketPolicy(String bucketName, YesOrNoEnum policy) {
        String defaultPolicy = "{\n" +

                "  \"Version\": \"2012-10-17\",\n" +

                "  \"Statement\": [\n" +

                "    {\n" +

                "      \"Effect\": \"Allow\",\n" +

                "      \"Action\": [\n" +

                "                \"s3:ListAllMyBuckets\",\n" +

                "                \"s3:ListBucket\",\n" +

                "                \"s3:GetBucketLocation\",\n" +

                "                \"s3:GetObject\",\n" +

                "                \"s3:PutObject\",\n" +

                "                \"s3:DeleteObject\"\n" +

                "      ],\n" +

                "      \"Principal\":\"*\",\n" +

                "      \"Resource\": [\n" +

                "        \"arn:aws:s3:::"+bucketName+"/*\"\n" +

                "      ]\n" +

                "    }\n" +

                "  ]\n" +

                "}";
        return defaultPolicy;
    }

    /** */
    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /** */
    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }


}
