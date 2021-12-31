package com.ah.cloud.permissions.security.handler;

import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.ah.cloud.permissions.security.exception.ApiAuthorityErrorException;
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
        if (e instanceof ApiAuthorityErrorException) {
            ApiAuthorityErrorException exception = (ApiAuthorityErrorException) e;
            response.setStatus(exception.getErrorCodeEnum().getCode());
            response.getWriter()
                    .print(JsonUtils.toJSONString(
                            ResponseResult.error(exception.getErrorCodeEnum()
                                    ,exception.getSubCode()
                                    , exception.getSubMsg())
                    ));
        } else {
            response.setStatus(ErrorCodeEnum.UNKNOWN_PERMISSION.getCode());
            response.getWriter()
                    .print(JsonUtils.toJSONString(ResponseResult.error(ErrorCodeEnum.UNKNOWN_PERMISSION
                            ,"unknown permission"
                            , "未知权限异常")));
        }

        log.error("[权限端点异常处理] ==> [异常信息为:{}]", Throwables.getStackTraceAsString(e));
    }
}
