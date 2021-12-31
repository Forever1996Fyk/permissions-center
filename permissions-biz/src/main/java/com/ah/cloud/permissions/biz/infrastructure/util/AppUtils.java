package com.ah.cloud.permissions.biz.infrastructure.util;

/**
 * @program: zbgx_system
 * @description:
 * @author: YuKai Fan
 * @create: 2021-01-22 13:35
 **/
public class AppUtils {

    /**
     * 获取随机全局唯一id
     * @return
     */
    public static String randomStrId() {
        return String.valueOf(IdWorker.idWorker().createId());
    }

    /**
     * 获取随机全局唯一id
     * @return
     */
    public static Long randomLongId() {
        return IdWorker.idWorker().createId();
    }
}
