package com.ah.cloud.permissions.biz.infrastructure.util;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 22:50
 **/
public class IpUtils {

    /**
     * 获取当前机器的ip
     * @return
     */
    public static String getHost() {
        String hostAddress = "";
        try {
            hostAddress = Inet4Address.getLocalHost().getHostAddress();
            return hostAddress;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }
}
