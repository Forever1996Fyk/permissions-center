package com.ah.cloud.permissions.biz.application.helper;

import com.ah.cloud.permissions.biz.domain.user.intro.form.SysUserIntroAddForm;
import com.ah.cloud.permissions.biz.domain.user.intro.form.SysUserIntroUpdateForm;
import com.ah.cloud.permissions.biz.domain.user.intro.vo.SysUserIntroVo;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysDept;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserIntro;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 19:07
 **/
@Component
public class SysUserIntroHelper {

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUserIntro convert(SysUserIntroAddForm form) {
        SysUserIntro sysUserIntro = Convert.INSTANCE.convert(form);
        sysUserIntro.setUserId(SecurityUtils.getUserIdBySession());
        sysUserIntro.setCreator(SecurityUtils.getUserNameBySession());
        sysUserIntro.setModifier(SecurityUtils.getUserNameBySession());
        return sysUserIntro;
    }

    /**
     * 数据转换
     * @param form
     * @return
     */
    public SysUserIntro convert(SysUserIntroUpdateForm form) {
        return Convert.INSTANCE.convert(form);
    }

    /**
     * 数据转换
     * @param sysUserIntro
     * @param sysDept
     * @param sysUser
     * @return
     */
    public SysUserIntroVo convertToVo(SysUserIntro sysUserIntro) {
        return Convert.INSTANCE.convertToVo(sysUserIntro);
    }

    @Mapper
    public interface Convert {
        SysUserIntroHelper.Convert INSTANCE = Mappers.getMapper(SysUserIntroHelper.Convert.class);

        /**
         * 数据转换
         * @param form
         * @return
         */
        SysUserIntro convert(SysUserIntroAddForm form);

        /**
         * 数据转换
         * @param form
         * @return
         */
        @Mappings({})
        @Mapping(target = "birthDay", expression = "java(com.ah.cloud.permissions.biz.infrastructure.util.DateUtils.parse(form.getBirthDay()))")
        SysUserIntro convert(SysUserIntroUpdateForm form);

        /**
         * 数据转换
         * @param sysUserIntro
         * @return
         */
        SysUserIntroVo convertToVo(SysUserIntro sysUserIntro);
    }
}
