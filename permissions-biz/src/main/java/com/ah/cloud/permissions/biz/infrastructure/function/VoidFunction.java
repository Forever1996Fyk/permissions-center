package com.ah.cloud.permissions.biz.infrastructure.function;

/**
 * @program: permissions-center
 * @description: 无入参无返回值的 函数式接口
 * @author: YuKai Fan
 * @create: 2022/10/9 16:43
 **/
@FunctionalInterface
public interface VoidFunction {

    /**
     * 执行逻辑
     *
     * 没有入参，没有返回值
     */
    void apply();
}
