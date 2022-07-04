package com.ah.cloud.permissions.biz.infrastructure.jasypt;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 11:00
 **/
public class PermissionPropertyDetector implements EncryptablePropertyDetector {

    private final String prefix = "p(";
    private final String suffix = ")";

    @Override
    public boolean isEncrypted(String property) {
        if (property == null) {
            return false;
        } else {
            String trimmedValue = property.trim();
            return trimmedValue.startsWith(prefix) && trimmedValue.endsWith(suffix);
        }
    }

    @Override
    public String unwrapEncryptedValue(String property) {
        return property.substring(prefix.length(), property.length() - suffix.length());
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }
}
