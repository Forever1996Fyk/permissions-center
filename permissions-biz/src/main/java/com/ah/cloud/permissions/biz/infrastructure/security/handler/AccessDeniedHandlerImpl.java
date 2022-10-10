package com.ah.cloud.permissions.biz.infrastructure.security.handler;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: permissions-center
 * @description: 权限异常处理类
 * 这个本身是处理 拒绝访问的异常，按理说是不需要 加入Spring Bean, 但是为了以后可能出现 其他需要Spring的需求，例如: 需要数据库记录日志等, 所以这里还是用的@Component注解
 * @author: YuKai Fan
 * @create: 2021-12-24 16:40
 **/
@Slf4j
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        ErrorCodeEnum errorCodeEnum = SecurityExceptionHandler.extractErrorCodeEnum(e);
        response.setStatus(errorCodeEnum.getCode());
        response.getWriter()
                .print(JsonUtils.toJsonString(
                        ResponseResult.newResponse(errorCodeEnum)
                ));
        log.error("[权限端点异常处理] ==> [请求地址为: {}, 异常信息为:{}]", request.getRequestURI(), Throwables.getStackTraceAsString(e));
    }
}
