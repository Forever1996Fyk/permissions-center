package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiAddForm;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiUpdateForm;
import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
import com.ah.cloud.permissions.biz.domain.role.vo.SysRoleVO;
import com.ah.cloud.permissions.biz.domain.user.vo.SelectUserApiVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysApi;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysRole;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.ah.cloud.permissions.enums.ReadOrWriteEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
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
        sysApi.setChangeable(YesOrNoEnum.getByType(form.getChangeable()).getType());
        sysApi.setCreator(SecurityUtils.getUserNameBySession());
        sysApi.setModifier(SecurityUtils.getUserNameBySession());
        return sysApi;
    }

    public SysApi convertToEntity(SysApiUpdateForm form) {
        SysApi sysApi = SysApiHelperConvert.INSTANCE.convert(form);
        sysApi.setChangeable(YesOrNoEnum.getByType(form.getChangeable()).getType());
        return sysApi;
    }

    public SysApiVo convertToVo(SysApi sysApi) {
        return SysApiHelperConvert.INSTANCE.convertToVO(sysApi);
    }

    public PageResult<SysApiVo> convertToPageResult(PageInfo<SysApi> pageInfo) {
        PageResult<SysApiVo> result = new PageResult<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setRows(SysApiHelperConvert.INSTANCE.convertToVO(pageInfo.getList()));
        result.setPageSize(pageInfo.getPageSize());
        return result;
    }

    @Mapper(uses = {ApiStatusEnum.class, ReadOrWriteEnum.class})
    public interface SysApiHelperConvert {
        SysApiHelper.SysApiHelperConvert INSTANCE = Mappers.getMapper(SysApiHelper.SysApiHelperConvert.class);

        /**
         * 数据转换
         *
         * @param sysApi
         * @return
         */
        @Mappings({
                @Mapping(target = "apiStatusEnum", source = "status"),
                @Mapping(target = "readOrWriteEnum", source = "readOrWrite"),
                @Mapping(target = "uri", source = "apiUrl"),
                @Mapping(target = "auth", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.AppUtils.convertIntToBool(sysApi.getAuth()))"),
                @Mapping(target = "open", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.AppUtils.convertIntToBool(sysApi.getOpen()))")
        })
        AuthorityApiDTO convert(SysApi sysApi);

        /**
         * 数据转换
         *
         * @param sysApis
         * @return
         */
        List<AuthorityApiDTO> convert(List<SysApi> sysApis);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        SysApi convert(SysApiAddForm form);

        /**
         * 数据转换
         *
         * @param form
         * @return
         */
        SysApi convert(SysApiUpdateForm form);

        /**
         * 数据转换
         *
         * @param sysApi
         * @return
         */
        @Mappings({
                @Mapping(target = "statusName", expression = "java(com.ah.cloud.permissions.enums.ApiStatusEnum.valueOf(sysApi.getStatus()).getDesc())"),
                @Mapping(target = "apiTypeName", expression = "java(com.ah.cloud.permissions.enums.SysApiTypeEnum.getByType(sysApi.getApiType()).getDesc())")
        })
        SysApiVo convertToVO(SysApi sysApi);

        /**
         * 数据转换
         *
         * @param sysApiList
         * @return
         */
        List<SysApiVo> convertToVO(List<SysApi> sysApiList);
    }
}
