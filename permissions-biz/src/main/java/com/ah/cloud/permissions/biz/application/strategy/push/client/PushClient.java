package com.ah.cloud.permissions.biz.application.strategy.push.client;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 17:03
 **/
public interface PushClient<T, R> {

    /**
     * 推送
     * @param t
     * @return
     */
    R send(T t) throws Exception;
}
