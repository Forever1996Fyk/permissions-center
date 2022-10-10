package com.ah.cloud.permissions.biz.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 11:40
 **/
@Slf4j
public class JwtUtils {

    /**
     * 构建token
     * @param params
     * @param sign
     * @return
     */
    public static String createToken(Map<String, String> params, String sign) {
        return createToken(params, sign, null);
    }

    /**
     * 构建token
     * @param params
     * @param sign
     * @param expireTime
     * @return
     */
    public static String createToken(Map<String, String> params, String sign, Date expireTime) {
        JWTCreator.Builder builder = JWT.create();
        if (StringUtils.isBlank(sign)) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "token签名");
        }
        if (expireTime != null) {
            builder.withExpiresAt(expireTime);
        }
        if (CollectionUtils.isNotEmpty(params)) {
            params.forEach(builder::withClaim);
        }
        return builder.sign(Algorithm.HMAC256(sign));
    }

    /**
     * 验证token
     * @param token
     * @param sign
     * @return
     */
    public static Map<String, String> verify(String token, String sign) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(sign)).build();
        try {
            DecodedJWT decoded = verifier.verify(token);
            return JsonUtils.stringToMap2(decoded.getSubject());
        } catch (Exception e) {
            log.error("JwtUtils[verify] jwt verify token error, token is {}, sign is {}, reason is {}", token, sign, Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.TOKEN_EXCEPTION);
        }
    }
}
