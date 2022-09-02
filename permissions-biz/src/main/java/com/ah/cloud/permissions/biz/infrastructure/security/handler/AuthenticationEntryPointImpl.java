package com.ah.cloud.permissions.biz.infrastructure.security.handler;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @description 认证异常处理类
 * @author yinjinbiao
 * @create 2021/12/16 10:40
 * @version 1.0
 */
@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {

	    response.setContentType("application/json");
	    response.setCharacterEncoding("utf-8");
		ErrorCodeEnum errorCodeEnum = SecurityExceptionHandler.extractErrorCodeEnum(e);
		response.setStatus(errorCodeEnum.getCode());
		response.getWriter()
				.print(JsonUtils.toJsonString(
						ResponseResult.newResponse(errorCodeEnum)
				));

	    log.error("[Token端点异常处理] ==> [异常信息为:{}]", Throwables.getStackTraceAsString(e));
    }
}
