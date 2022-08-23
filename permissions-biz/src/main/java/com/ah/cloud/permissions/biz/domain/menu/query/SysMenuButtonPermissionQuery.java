package com.ah.cloud.permissions.biz.domain.menu.query;

import lombok.Data;

import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-16 15:33
 **/
@Data
public class SysMenuButtonPermissionQuery {

    /**
     * 按钮编码
     */
    private Set<String> buttonCodes;
}
