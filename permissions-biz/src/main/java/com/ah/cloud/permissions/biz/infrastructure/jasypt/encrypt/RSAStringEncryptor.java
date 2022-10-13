package com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt;

import org.jasypt.encryption.StringEncryptor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 11:18
 **/
public interface RSAStringEncryptor extends StringEncryptor {

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     * @return
     * @throws Exception
     */
    Map<String, Object> genKeyPair() throws Exception;

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密的数据
     * @param privateKey  私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    String sign(byte[] data, String privateKey) throws Exception;

    /**
     * 校验数据签名
     *
     * @param data 已加密的数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * @return
     * @throws Exception
     */
    boolean verify(byte[] data, String publicKey, String sign) throws Exception;

    /**
     * 私钥解密
     *
     * @param data 已加密的数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception;

    /**
     * 公钥解密
     *
     * @param data 已加密的数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception;

    /**
     * 公钥加密
     * @param data 源数据
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception;

    /**
     * 私钥加密
     * @param data 源数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception;
}
