package com.ah.cloud.permissions.biz.application.builder;

import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import org.springframework.beans.factory.InitializingBean;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 16:09
 **/
public interface AlgorithmTypeBuilder<E extends Encrypt, D extends Decrypt> extends InitializingBean {

    /**
     * 构建算法类型
     * @param params
     * @return
     */
    AlgorithmType<E, D> build(String params);

    /**
     * 加密类型
     * @return
     */
    EncryptTypeEnum getEncryptTypeEnum();
}
