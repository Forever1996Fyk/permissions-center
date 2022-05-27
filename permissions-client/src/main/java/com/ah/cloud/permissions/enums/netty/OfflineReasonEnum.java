package com.ah.cloud.permissions.enums.netty;

/**
 * @program: permissions-center
 * @description: kick原因类型
 * @author: YuKai Fan
 * @create: 2022-05-19 16:04
 **/
public enum OfflineReasonEnum {

    /**
     * 其他设备登录
     */
    OTHER_DEVICE_LOGIN_FORCE_OFFLINE(1, "[%s]设备登录, 当前设备强制下线"),
    ;

    private final int type;

    private final String desc;

    OfflineReasonEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getDesc(String... args) {
        return String.format(this.getDesc(), args);
    }
}
