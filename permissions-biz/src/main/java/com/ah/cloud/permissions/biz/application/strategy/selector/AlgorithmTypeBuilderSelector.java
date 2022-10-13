package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.builder.AlgorithmTypeBuilder;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 16:29
 **/
public class AlgorithmTypeBuilderSelector {

    private final static List<AlgorithmTypeBuilder<? extends Encrypt, ? extends Decrypt>> ALGORITHM_TYPE_BUILDER_LIST = Lists.newArrayList();

    /**
     * 注册算法类型构造器
     *
     * @param algorithmTypeBuilder
     * @param <E>
     * @param <D>
     */
    public static <E extends Encrypt, D extends Decrypt> void registerAlgorithmTypeBuilder(AlgorithmTypeBuilder<E, D> algorithmTypeBuilder) {
        ALGORITHM_TYPE_BUILDER_LIST.add(algorithmTypeBuilder);
    }

    /**
     * 根据加密类型获取构造器
     * @param encryptTypeEnum
     * @return
     */
    public static AlgorithmTypeBuilder<? extends Encrypt, ? extends Decrypt> select(EncryptTypeEnum encryptTypeEnum) {
        return ALGORITHM_TYPE_BUILDER_LIST.stream()
                .filter(algorithmTypeBuilder -> Objects.equals(algorithmTypeBuilder.getEncryptTypeEnum(), encryptTypeEnum))
                .findFirst()
                .orElse(null);
    }
}
