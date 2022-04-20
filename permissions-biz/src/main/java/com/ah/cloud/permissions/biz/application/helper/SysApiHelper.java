package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiAddForm;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiUpdateForm;
import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.github.pagehelper.PageInfo;
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
     *
     * @param list
     * @return
     */
    public List<AuthorityApiDTO> convertToDTO(List<SysApi> list) {
        return SysApiHelperConvert.INSTANCE.convert(list);
    }

    public SysApi convertToEntity(SysApiAddForm form) {
        SysApi sysApi = SysApiHelperConvert.INSTANCE.convert(form);
        sysApi.setIsOpen(form.getOpen());
        sysApi.setIsAuth(form.getAuth());
        return sysApi;
    }

    public SysApi convertToEntity(SysApiUpdateForm form) {
        SysApi sysApi = SysApiHelperConvert.INSTANCE.convert(form);
        if (form.getOpen() != null) {
            sysApi.setIsOpen(form.getOpen());
        }
        if (form.getAuth() != null) {
            sysApi.setIsAuth(form.getAuth());
        }
        return sysApi;
    }

    public SysApiVo convertToVo(SysApi sysApi) {
        SysApiVo sysApiVo = SysApiHelperConvert.INSTANCE.convertToVO(sysApi);
        sysApiVo.setAuth(sysApi.getIsAuth());
        sysApiVo.setOpen(sysApi.getIsOpen());
        return sysApiVo;
    }

    public PageResult<SysApiVo> convertToPageResult(PageInfo<SysApi> pageInfo) {
        PageResult<SysApiVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysApiHelperConvert.INSTANCE.convertToVO(pageInfo.getList()));
        result.setPageSize(pageInfo.getSize());
        result.setPages(pageInfo.getPages());
        return result;
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

        SysApi convert(SysApiAddForm form);

        SysApi convert(SysApiUpdateForm form);

        SysApiVo convertToVO(SysApi sysApi);

        List<SysApiVo> convertToVO(List<SysApi> sysApiList);
    }
}
