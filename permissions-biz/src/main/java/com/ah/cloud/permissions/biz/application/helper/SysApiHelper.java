package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysApi;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2021-12-24 15:56
 **/
@Component
public class SysApiHelper {

    /**
     * 数据转换
     * @param list
     * @return
     */
    public List<AuthorityApiDTO> convertToDTO(List<SysApi> list) {
        return SysApiHelperConvert.INSTANCE.convert(list);
    }

    @Mapper(uses = ApiStatusEnum.class)
    public interface SysApiHelperConvert {
        SysApiHelper.SysApiHelperConvert INSTANCE = Mappers.getMapper(SysApiHelper.SysApiHelperConvert.class);

        @Mappings({
                @Mapping(target = "apiStatusEnum", source = "status"),
                @Mapping(target = "uri", source = "apiUrl"),
        })
        AuthorityApiDTO convert(SysApi sysApi);

        List<AuthorityApiDTO> convert(List<SysApi> sysApis);
    }
}
