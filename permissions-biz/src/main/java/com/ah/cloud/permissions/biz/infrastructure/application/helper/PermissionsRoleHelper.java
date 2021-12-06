package com.ah.cloud.permissions.biz.infrastructure.application.helper;

import com.ah.cloud.permissions.biz.domain.role.form.PermissionsRoleAddForm;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.PermissionsRole;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-03 16:05
 **/
@Component
public class PermissionsRoleHelper {

    @Mapper
    public interface PermissionsRoleConvert {
        PermissionsRoleConvert INSTANCE = Mappers.getMapper(PermissionsRoleConvert.class);

        PermissionsRole convert(PermissionsRoleAddForm request);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public PermissionsRole convert2Entity(PermissionsRoleAddForm form) {
        PermissionsRole permissionsRole = PermissionsRoleConvert.INSTANCE.convert(form);
        return permissionsRole;
    }
}
