package com.ah.cloud.permissions.netty.infrastructure.constant;

import com.ah.cloud.permissions.netty.domain.session.SingleSession;
import io.netty.util.AttributeKey;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-16 17:52
 **/
public class SessionConstants {

    /**
     * netty channel session key
     */
    public static final AttributeKey<SingleSession> SINGLE_SESSION_ATTRIBUTE_KEY = AttributeKey.valueOf("SINGLE_SESSION_ATTRIBUTE_KEY");
}
