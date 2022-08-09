package com.ah.cloud.permissions.api.controller;

import com.ah.cloud.permissions.biz.application.manager.SysUserManager;
import com.ah.cloud.permissions.biz.domain.menu.vo.RouterVo;
import com.ah.cloud.permissions.biz.infrastructure.annotation.ApiMethodLog;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-02 14:14
 **/
@RestController
@RequestMapping("/router")
public class SysRouterController {
    @Resource
    private SysUserManager sysUserManager;

    /**
     * 根据当前登录人id获取菜单路由信息
     * @return
     */
    @ApiMethodLog
    @GetMapping("/listRouterVoByUserId")
    public ResponseResult<List<RouterVo>> listRouterVoByUserId() {
        Long currentUserId = SecurityUtils.getUserIdBySession();
        return ResponseResult.ok(sysUserManager.listRouterVoByUserId(currentUserId));
    }
}
