package com.ah.cloud.perm.biz.infrastructure.application.helper;

import com.ah.cloud.perm.biz.domain.role.form.PermRoleAddForm;
import com.ah.cloud.perm.biz.infrastructure.repository.bean.PermRole;
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
public class PermRoleHelper {

    @Mapper
    public interface PermRoleConvert {
        PermRoleConvert INSTANCE = Mappers.getMapper(PermRoleConvert.class);

        PermRole convert(PermRoleAddForm request);
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public PermRole convert2Entity(PermRoleAddForm form) {
        PermRole permRole = PermRoleConvert.INSTANCE.convert(form);
        return permRole;
    }
}
