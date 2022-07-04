package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.jasypt.AlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.application.strategy.jasypt.BasicAlgorithmEncryptorStrategy;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:51
 **/
@Component
public class AlgorithmEncryptorSelector {
    @Resource
    private List<AlgorithmEncryptorStrategy> list;
    @Resource
    private BasicAlgorithmEncryptorStrategy basicAlgorithmEncryptorStrategy;

    /**
     * 加密器
     * @param algorithmType
     * @param <E>
     * @param <D>
     * @return
     */
    public <E extends Encrypt, D extends Decrypt> EncryptResult encrypt(AlgorithmType<E, D> algorithmType) {
        AlgorithmEncryptorStrategy strategy = this.select(algorithmType);
        return strategy.encrypt(algorithmType);
    }

    /**
     * 解密器
     * @param algorithmType
     * @param <E>
     * @param <D>
     * @return
     */
    public <E extends Encrypt, D extends Decrypt> DecryptResult decrypt(AlgorithmType<E, D> algorithmType) {
        AlgorithmEncryptorStrategy strategy = this.select(algorithmType);
        return strategy.decrypt(algorithmType);
    }

    /**
     * 选择策略
     * @param algorithmType
     * @param <E>
     * @param <D>
     * @return
     */
    public <E extends Encrypt, D extends Decrypt> AlgorithmEncryptorStrategy select(AlgorithmType<E, D> algorithmType) {
        for (AlgorithmEncryptorStrategy algorithmEncryptorStrategy : list) {
            if (algorithmEncryptorStrategy.support(algorithmType)) {
                return algorithmEncryptorStrategy;
            }
        }
        return basicAlgorithmEncryptorStrategy;
    }
}
