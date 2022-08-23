package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.application.service.SysUserService;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserAddForm;
import com.ah.cloud.permissions.biz.domain.user.form.SysUserUpdateForm;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.enums.DataScopeEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.alibaba.excel.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 系统用户检查类
 * @author: YuKai Fan
 * @create: 2022-01-04 15:40
 **/
@Slf4j
@Component
public class SysUserChecker {

    /**
     * 校验参数
     * @param form
     */
    public void checkSysUserAddForm(SysUserAddForm form) {
        if (form.getDataScope() == null) {
            form.setDataScope(DataScopeEnum.DATA_SCOPE_SELF.getType());
        } else if (!DataScopeEnum.isValid(form.getDataScope())){
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }

    /**
     * 校验参数
     * @param form
     */
    public void checkSysUserAddForm(SysUserUpdateForm form) {
        if (form.getDataScope() == null) {
            form.setDataScope(DataScopeEnum.DATA_SCOPE_SELF.getType());
        } else if (!DataScopeEnum.isValid(form.getDataScope())){
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL);
        }
    }
}
