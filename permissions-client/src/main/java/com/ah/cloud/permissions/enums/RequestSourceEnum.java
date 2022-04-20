package com.ah.cloud.permissions.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 请求来源
 * @author: YuKai Fan
 * @create: 2022-04-07 17:06
 **/
public enum RequestSourceEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),
    /**
     * 网页端
     */
    WEB(1, "网页端"),
    /**
     * 安卓端
     */
    ANDROID(2, "安卓端"),
    /**
     * iOS端
     */
    IOS(3, "iOS端"),
    /**
     * 微信小程序
     */
    WX_MINI_APP(4, "微信小程序"),
    /**
     * 支付宝小程序
     */
    ALIPAY_MINI(5, "支付宝小程序"),
    /**
     * 安卓pad
     */
    ANDROID_PAD(6, "安卓pad"),
    /**
     * ipad
     */
    IPAD(7, "ipad"),
    ;

    private final int type;

    private final String desc;

    RequestSourceEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 移动端枚举初始化
     */
    private final static List<RequestSourceEnum> MT_SOURCE_LIST = new ArrayList<RequestSourceEnum>() {
        {
            add(ANDROID);
            add(IOS);
            add(ALIPAY_MINI);
            add(WX_MINI_APP);
            add(ANDROID_PAD);
            add(IPAD);
        }
    };

    /**
     * 移动端来源
     * @return
     */
    public boolean isMT() {
        return MT_SOURCE_LIST.contains(this);
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
