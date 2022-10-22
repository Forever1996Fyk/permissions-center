package com.ah.cloud.permissions.biz.infrastructure.security.service;

import com.ah.cloud.permissions.biz.domain.token.Token;
import com.ah.cloud.permissions.biz.domain.user.BaseUserDetails;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.TokenConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JwtUtils;
import com.ah.cloud.permissions.enums.RequestSourceEnum;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/29 15:08
 **/
@Slf4j
public abstract class AbstractTokenService<T extends Token, U extends BaseUserDetails<T>> implements TokenService<T, U> {

    /**
     * 默认签名
     */
    private final static String DEFAULT_JWT_SIGN = "1234567890abcdefghijklmn";

    @Override
    public T createToken(U u) {
        String accessToken = generateActualToken(u);
        T token = buildToken(accessToken);
        u.setToken(token);
        return token;
    }


    @Override
    public Map<String, String> verifyToken(String token) {
        if (isJwt()) {
            return JwtUtils.verify(token, getSign());
        }
        Map<String, String> map = Maps.newHashMap();
        map.put(TokenConstants.TOKEN_MAP_KEY, token);
        return map;
    }

    @Override
    public void refreshToken(String token) {

    }

    @Override
    public Map<String, String> getToken(HttpServletRequest request) {
        String header = request.getHeader(PermissionsConstants.TOKEN_HEAD);
        if (StringUtils.isEmpty(header)) {
            return null;
        }
        // 如果以Bearer开头，则提取。
        if (header.toLowerCase().startsWith(PermissionsConstants.TOKEN_TYPE.toLowerCase())) {
            String authHeaderValue = header.substring(PermissionsConstants.TOKEN_TYPE.length()).trim();
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
                authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return verifyToken(authHeaderValue);
        }
        return null;
    }

    /**
     * 生成token
     * @param u
     * @return
     */
    protected String generateActualToken(U u) {
        return AppUtils.randomStrId();
    }

    /**
     * 是否需要jwt
     * @return
     */
    protected boolean isJwt() {
        return false;
    }

    /**
     * 默认签名
     * @return
     */
    protected String getSign() {
        return DEFAULT_JWT_SIGN;
    }

    /**
     * 获取日志标记
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 构建token
     *
     * @param token
     * @param jwtAccessToken
     * @return
     */
    protected abstract T buildToken(String token);
}
