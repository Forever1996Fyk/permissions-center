package com.ah.cloud.permissions.biz.infrastructure.sequence.enums;

import lombok.Getter;

/**
 * @program: permissions-center
 * @description: 生产方式类型枚举
 * @author: YuKai Fan
 * @create: 2022/9/16 15:46
 **/
@Getter
public enum ProducerTypeEnum {

    /**
     * 类型
     */
    DB(1, "数据库"),
    CACHE(2, "缓存"),
    ;

    private final int type;

    private final String desc;

    ProducerTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
