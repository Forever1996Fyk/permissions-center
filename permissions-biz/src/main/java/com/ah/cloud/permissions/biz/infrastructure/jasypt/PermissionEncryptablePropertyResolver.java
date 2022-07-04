package com.ah.cloud.permissions.biz.infrastructure.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 14:09
 **/
public class PermissionEncryptablePropertyResolver implements EncryptablePropertyResolver {

    private final PermissionPropertyDetector propertyDetector;

    public PermissionEncryptablePropertyResolver(PermissionPropertyDetector propertyDetector) {
        this.propertyDetector = propertyDetector;
    }

    @Override
    public String resolvePropertyValue(String s) {
        if (StringUtils.isNotBlank(s) && propertyDetector.isEncrypted(s)) {
            s = propertyDetector.unwrapEncryptedValue(s);
            //使用base64 解密 value带有前缀的值
            byte[] decoded = Base64.getDecoder().decode(s);
            return new String(decoded, StandardCharsets.UTF_8);
        }
        return s;
    }
}
