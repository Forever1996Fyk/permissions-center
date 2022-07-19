package com.ah.cloud.permissions.biz.infrastructure.config.mp;

import com.ah.cloud.permissions.biz.infrastructure.annotation.DataScope;

/**
 * @author yinjinbiao
 * @version 1.0
 * @description
 * @create 2022/5/30 14:28
 */
public class DataScopeContextHolder {

    private static final ThreadLocal<DataScope> CONTEXT_HOLDER = new ThreadLocal<>();

    public static DataScope getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void setContext(DataScope context) {
        CONTEXT_HOLDER.set(context);
    }

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }


}
