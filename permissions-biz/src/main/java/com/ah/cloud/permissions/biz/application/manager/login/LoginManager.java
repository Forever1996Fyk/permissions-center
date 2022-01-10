package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.baomidou.mybatisplus.extension.api.R;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 15:35
 **/
public interface LoginManager {

    /**
     * 获取认证token
     * @return
     */
    AccessToken getAccessToken(LoginForm loginForm);

    /**
     * 判断是否支持
     * @param loginForm
     * @return
     */
    boolean support(LoginForm loginForm);
}
