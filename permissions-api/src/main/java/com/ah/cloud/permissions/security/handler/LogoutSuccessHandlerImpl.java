package com.ah.cloud.permissions.security.handler;

import com.ah.cloud.permissions.domain.common.ResponseResult;
import com.ah.cloud.permissions.security.domain.user.LocalUser;
import net.minidev.json.JSONUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {


    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
//        LocalUser localUser = tokenService.getLocalUser(request);
//        if (ObjectUtils.isNotEmpty(localUser)) {
//            // 删除用户缓存记录
//            tokenService.delLocalUser(localUser.getAccessToken());
//        }
//	    response.setStatus(HttpStatus.UNAUTHORIZED.value());
//	    response.setContentType("application/json");
//	    response.setCharacterEncoding("utf-8");
//	    response.getWriter().print(JSONUtil.toJsonStr(ResponseResult.ok()));
    }
}
