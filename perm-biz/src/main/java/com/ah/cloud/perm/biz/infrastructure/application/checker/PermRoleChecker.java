package com.ah.cloud.perm.biz.infrastructure.application.checker;

import com.ah.cloud.perm.biz.domain.role.request.PermRoleRequest;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class PermRoleChecker {

    /**
     * 检查type是否合法
     * @param request
     */
    public void checkRoleType(PermRoleRequest request) {
        if (request.getRoleType() != 1) {
            // 自定义异常
            throw new RuntimeException();
        }
    }
}
