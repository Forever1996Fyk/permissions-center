package com.ah.cloud.permissions.biz.application.strategy.jasypt;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.aes.AESAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.DecryptResult;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.result.EncryptResult;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSAAlgorithmType;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.RSAStringEncryptor;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.StandardRSAStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description: AES算法策略，是使用AES256算法作为基础的对称加密算法, 对明文的加密和解密需要同一个密钥
 * @author: YuKai Fan
 * @create: 2022/10/12 14:39
 **/
@Component
public class RSAAlgorithmEncryptorStrategy extends AbstractAlgorithmEncryptorStrategy {
    private final static String LOG_MARK = "RSAAlgorithmEncryptorStrategy";

    /**
     * 解密私钥
     */
    private final static String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK9EGTB1x2/fClBqFWmt8OnpfJxSzZrBlPneEnKpBkM7sUZT+C3PCV0gI28xori329bbEEy1Z/wLuKVHF6f4fYsnGyV7KgLl3Hs77EXDtRxikvFIKuYtZf57RDrzOl2v5Xrwgk6BxW8ALzxCmyQtPwGmGhOk2gD0ZVT3mOmH3wutAgMBAAECgYEAnSD9QD/nbDlXIt7okZUa7mqgh/mIUR0KNbVNbrRwY+hpNzbiwhbiOzYr7m1eiCJ1mjtNcqgUMJhFkw+YesPBsHjWNV2t4Fy66HtK0eUp6YfaOOjMDkiKVg52Z9sYubjrfZz8J2wxt5/H6uCgJ/T5FqMULRoUXtEkEYPeqQDhbXUCQQDp0LrmAAc4Xu/qTGyrZiwBM9e7pnk9sXKkYd798t093gT2ZjGx43DqQ8RB1Tk7tAm6qwY/QekLqDGGZ7utPL/nAkEAv+U7KAkeFTfX2HzXq5ZKsv9+V08PSYB8PNIClEitzvkuLdROYGpD5ZoRJuFE2Mx/BwqB9elXGhilsnyKJug1SwJAD6TwrGz6DrKDePjOGJXIgfgXCi40VIVn90m5IuK6HOabDGWAE7f1GSeZnRIIN09vizbgPyFI14fUcHrGWuwCpwJAe85+0z7GrPPhi+hIEYme2kvjDJuShl6iSW9JgLg/g1jbRXBDvQrykXFMfaQ7khCmKDnMTYdlzg124mhdjeqlbwJBAM2CLUvOBAG60Gb3Pc5TPwZbSrHd+TITJtIBiiFRcD5hGATwD+QpOov4vfipuXqbfpdElidp1X66PO8iFLp3ftk=";

    /**
     * 加密公钥
     */
    private final static String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvRBkwdcdv3wpQahVprfDp6XycUs2awZT53hJyqQZDO7FGU/gtzwldICNvMaK4t9vW2xBMtWf8C7ilRxen+H2LJxsleyoC5dx7O+xFw7UcYpLxSCrmLWX+e0Q68zpdr+V68IJOgcVvAC88QpskLT8BphoTpNoA9GVU95jph98LrQIDAQAB";

    private final RSAStringEncryptor encryptor;

    public RSAAlgorithmEncryptorStrategy() {
        this.encryptor = new StandardRSAStringEncryptor(PUBLIC_KEY, PRIVATE_KEY);
    }

    @Override
    protected DecryptResult doDecrypt(Decrypt decrypt) {
        return DecryptResult.builder()
                .result(encryptor.decrypt(decrypt.getCipher()))
                .build();
    }

    @Override
    protected EncryptResult doEncrypt(Encrypt encrypt) {
        return EncryptResult.builder()
                .result(encryptor.encrypt(encrypt.getOriginal()))
                .build();
    }

    @Override
    protected String getLogMark() {
        return LOG_MARK;
    }

    @Override
    public <E extends Encrypt, D extends Decrypt> boolean support(AlgorithmType<E, D> algorithmType) {
        return RSAAlgorithmType.class.isAssignableFrom(algorithmType.getClass());
    }
}
