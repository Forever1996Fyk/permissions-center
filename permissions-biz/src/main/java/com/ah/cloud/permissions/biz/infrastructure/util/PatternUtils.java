package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;

import java.util.regex.Pattern;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 17:12
 **/
public class PatternUtils {

    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     *
     * @param phone 移动、联通、电信运营商的号码段
     *              <p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *              、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *              <p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *              <p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isMobile(String phone) {
        Pattern p = Pattern.compile(PermissionsConstants.PHONE_REGEX);
        return p.matcher(phone).matches();
    }

    /**
     * 验证Email
     *
     * @param email email地址，格式：zhangsan@sina.com，zhangsan@xxx.com.cn，xxx代表邮件服务商
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean isEmail(String email) {
        Pattern p = Pattern.compile(PermissionsConstants.EMAIL_REGEX);
        return p.matcher(email).matches();
    }
}
