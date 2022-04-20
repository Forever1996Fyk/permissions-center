package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.menu.query.SysMenuTreeSelectQuery;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.MenuQueryTypeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-03-30 11:29
 **/
@Component
public class SysMenuChecker {

    public void checkSysMenuQueryType(SysMenuTreeSelectQuery query) {
        if (query.getSysMenuQueryType() == null) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "菜单树查询类型");
        }

        if (MenuQueryTypeEnum.getByType(query.getSysMenuQueryType()).equals(MenuQueryTypeEnum.ROLE_MENU)
                && StringUtils.isBlank(query.getRoleCode())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "角色编码");
        }

    }
}
