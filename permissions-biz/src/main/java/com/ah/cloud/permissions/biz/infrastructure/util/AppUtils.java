package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.exception.ErrorCode;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

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
     * 获取32位uuid
     * @return
     */
    public static String simpleUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) +
                uuid.substring(9, 13) +
                uuid.substring(14, 18) +
                uuid.substring(19, 23) +
                uuid.substring(24, 36);
    }

    /**
     * 获取随机全局唯一id
     * @return
     */
    public static Long randomLongId() {
        return IdWorker.idWorker().createId();
    }


    public static String getErrorMsg(ErrorCode errorCode, String... args) {
        String errorMsg = String.format(errorCode.getMsg(), args);
        // 说明StringFormat不启作用
        if (StringUtils.equals(errorMsg, errorCode.getMsg())) {
            StringBuffer sb = new StringBuffer();
            sb.append(errorCode.getMsg()).append("，详情：");
            if (args != null && args.length > 0) {
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }
            }
            errorMsg = sb.toString();
        }
        return errorMsg;
    }
}
