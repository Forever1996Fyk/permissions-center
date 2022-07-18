package com.ah.cloud.permissions.task.application.helper;

import com.ah.cloud.permissions.biz.domain.user.query.SysUserExportQuery;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.enums.SexEnum;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import com.ah.cloud.permissions.task.domain.dto.business.export.SysUserImportErrorExportDTO;
import com.ah.cloud.permissions.task.domain.dto.business.import2.SysUserImportDTO;
import com.ah.cloud.permissions.task.domain.dto.business.param.SysUserExportParamDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 14:20
 **/
@Component
public class SysUserImportExportHelper {

    /**
     * 数据转换
     * @param paramDTO
     * @return
     */
    public SysUserExportQuery convert(SysUserExportParamDTO paramDTO) {
        return Convert.INSTANCE.convert(paramDTO);
    }

    /**
     * 数据转换
     * @param sysUserImportDTO
     * @return
     */
    public SysUser convert(SysUserImportDTO sysUserImportDTO) {
        SysUser sysUser = Convert.INSTANCE.convert(sysUserImportDTO);
        sysUser.setStatus(UserStatusEnum.NORMAL.getStatus());
        sysUser.setSex(Objects.requireNonNull(SexEnum.getByDesc(sysUserImportDTO.getSexName())).getType());
        sysUser.setCreator(PermissionsConstants.OPERATOR_SYSTEM);
        sysUser.setModifier(PermissionsConstants.OPERATOR_SYSTEM);
        sysUser.setUserId(AppUtils.randomLongId());
        sysUser.setAvatar(PermissionsConstants.DEFAULT_AVATAR);
        return sysUser;
    }

    /**
     * 数据转换
     * @param sysUserImportDTO
     * @return
     */
    public SysUserImportErrorExportDTO convertToErrorExportDTO(SysUserImportDTO sysUserImportDTO) {
        return Convert.INSTANCE.convertToErrorExportDTO(sysUserImportDTO);
    }

    @Mapper
    public interface Convert {
        SysUserImportExportHelper.Convert INSTANCE = Mappers.getMapper(SysUserImportExportHelper.Convert.class);

        /**
         * 数据转换
         * @param paramDTO
         * @return
         */
        SysUserExportQuery convert(SysUserExportParamDTO paramDTO);

        /**
         * 数据转换
         * @param sysUserImportDTO
         * @return
         */
        SysUser convert(SysUserImportDTO sysUserImportDTO);

        /**
         * 数据转换
         * @param sysUserImportDTO
         * @return
         */
        SysUserImportErrorExportDTO convertToErrorExportDTO(SysUserImportDTO sysUserImportDTO);
    }
}
