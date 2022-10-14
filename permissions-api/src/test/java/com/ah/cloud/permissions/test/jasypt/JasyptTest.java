package com.ah.cloud.permissions.test.jasypt;

import com.ah.cloud.permissions.biz.application.strategy.jasypt.AESAlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.RSAStringEncryptor;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.StandardRSAStringEncryptor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.jasypt.util.text.AES256TextEncryptor;

import java.security.Key;
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
        StandardRSAStringEncryptor standardRSAStringEncryptor = new StandardRSAStringEncryptor("", "");
        Map<String, Object> stringObjectMap = standardRSAStringEncryptor.genKeyPair();
        System.out.println("pubKey: + " + getPublicKey(stringObjectMap));
        System.out.println("priKey:" + getPrivateKey(stringObjectMap));
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
