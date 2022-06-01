package com.ah.cloud.permissions.biz.application.strategy.push;

/**
 * @program: permissions-center
 * @description: 消息实体构建
 * @author: YuKai Fan
 * @create: 2022-05-31 18:15
 **/
public interface MsgEntityBuilder<T, R> {

    /**
     * 消息构建
     * @return
     */
    R build(T t);
}
