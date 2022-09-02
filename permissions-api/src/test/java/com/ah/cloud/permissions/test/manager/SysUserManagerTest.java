package com.ah.cloud.permissions.test.manager;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-02 18:24
 **/
public class SysUserManagerTest extends BaseTest {
    @Resource
    private SysUserManager sysUserManager;

    @Test
    public void testListRouterVoByUserId() {
        List<RouterVo> routerVoList =
                sysUserManager.listRouterVoByUserId(100000L);
        System.out.println("routerList : " + JsonUtils.toJsonString(routerVoList));
    }
}
