package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.user.UserAuthorityDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-22 18:14
 **/
@Component
public class UserAuthorityHelper {

    /**
     * 数据转换
     * @param sysUser
     * @return
     */
    public UserAuthorityDTO convertDTO(SysUser sysUser) {
        return UserAuthorityConvert.INSTANCE.convert(sysUser);
    }

    @Mapper
    public interface UserAuthorityConvert {
        UserAuthorityHelper.UserAuthorityConvert INSTANCE = Mappers.getMapper(UserAuthorityHelper.UserAuthorityConvert.class);

        @Mapping(target = "userId", source = "id")
        UserAuthorityDTO convert(SysUser sysUser);
    }
}
