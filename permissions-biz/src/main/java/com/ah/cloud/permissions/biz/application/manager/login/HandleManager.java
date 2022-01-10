package com.ah.cloud.permissions.biz.application.manager.login;

import com.ah.cloud.permissions.biz.domain.login.LoginForm;
import com.ah.cloud.permissions.biz.domain.token.AccessToken;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.log.LogMessage;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-09 16:00
 **/
@Slf4j
@Component
public class HandleManager implements InitializingBean {
    @Resource
    private List<LoginManager> loginManagerList;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(loginManagerList)) {
            throw new RuntimeException("加载LoginManager失败, 不存在登录管理器");
        }
        log.info("初始化LoginManager集合数据:{}", JsonUtils.toJSONString(loginManagerList));
    }

    /**
     * 获取当前LoginManager
     * @param loginForm
     * @return
     */
    public AccessToken getAccessToken(LoginForm loginForm) {
        AccessToken accessToken = null;
        int currentPosition = 0;
        int size = this.loginManagerList.size();
        for (LoginManager loginManager : loginManagerList) {
            if (!loginManager.support(loginForm)) {
                continue;
            }
            if (log.isTraceEnabled()) {
                log.trace(String.valueOf(LogMessage.format("HandleManager request with %s (%d/%d)",
                        loginManager.getClass().getSimpleName(), ++currentPosition, size)));
            }

            accessToken = loginManager.getAccessToken(loginForm);

            if (!Objects.isNull(accessToken)) {
                break;
            }
        }

        return accessToken;
    }

}
