package com.ah.cloud.permissions.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    UNKNOWN("unknown", "未知"),

    /**
     * 网页端
     */
    WEB("web", "网页端"),

    /**
     * 移动端
     */
    MT("mt", "移动端"),
    /**
     * 安卓端
     */
    ANDROID("android", "安卓端"),
    /**
     * iOS端
     */
    IOS("ios", "iOS端"),
    /**
     * 微信小程序
     */
    WX_MINI_APP("wx_mini_app", "微信小程序"),
    /**
     * 支付宝小程序
     */
    ALIPAY_MINI("alipay_mini", "支付宝小程序"),
    /**
     * 安卓pad
     */
    ANDROID_PAD("android_pad", "安卓pad"),
    /**
     * ipad
     */
    IPAD("ipad", "ipad"),
    ;

    private final String type;

    private final String desc;

    RequestSourceEnum(String type, String desc) {
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

    public static RequestSourceEnum getByType(String type) {
        return Arrays.stream(values())
                .filter(requestSourceEnum -> Objects.equals(requestSourceEnum.getType(), type))
                .findAny()
                .orElse(UNKNOWN);
    }

    /**
     * 移动端来源
     * @return
     */
    public boolean isMT() {
        return MT_SOURCE_LIST.contains(this);
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
