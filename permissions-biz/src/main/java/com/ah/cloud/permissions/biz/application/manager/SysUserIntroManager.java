//package com.ah.cloud.permissions.biz.application.manager;
//
//import com.ah.cloud.permissions.biz.application.helper.SysUserIntroHelper;
//import com.ah.cloud.permissions.biz.application.service.ext.SysUserIntroExtService;
//import com.ah.cloud.permissions.biz.domain.user.intro.form.SysUserIntroUpdateForm;
//import com.ah.cloud.permissions.biz.domain.user.intro.vo.SysUserIntroVo;
//import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
//import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
//import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUserIntro;
//import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
//import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
///**
// * @program: permissions-center
// * @description:
// * @author: YuKai Fan
// * @create: 2022-08-19 18:57
// **/
//@Slf4j
//@Component
//public class SysUserIntroManager {
//    @Resource
//    private SysUserIntroHelper sysUserIntroHelper;
//    @Resource
//    private SysUserIntroExtService sysUserIntroExtService;
//
//    /**
//     * 更新用户信息
//     * @param form
//     */
//    public void updateIntro(SysUserIntroUpdateForm form) {
//        Long userId = SecurityUtils.getUserIdBySession();
//        SysUserIntro existSysUserIntro = sysUserIntroExtService.getOneByUserId(userId);
//        SysUserIntro sysUserIntro = sysUserIntroHelper.convert(form);
//        sysUserIntro.setUserId(userId);
//        // 这里无需担心重新请求问题, 因为userId是唯一索引，所以不会出现问题
//        if (Objects.isNull(existSysUserIntro)) {
//            String userNameBySession = SecurityUtils.getUserNameBySession();
//            sysUserIntro.setCreator(userNameBySession);
//            sysUserIntro.setModifier(userNameBySession);
//            sysUserIntroExtService.save(sysUserIntro);
//        } else {
//            sysUserIntro.setVersion(existSysUserIntro.getVersion());
//            boolean updateResult = sysUserIntroExtService.update(
//                    sysUserIntro,
//                    new UpdateWrapper<SysUserIntro>().lambda()
//                            .eq(SysUserIntro::getId, existSysUserIntro.getId())
//                            .eq(SysUserIntro::getVersion, existSysUserIntro.getVersion())
//            );
//            if (!updateResult) {
//                throw new BizException(ErrorCodeEnum.VERSION_ERROR);
//            }
//        }
//    }
//
//    /**
//     * 根据用户id查询用户简介
//     * @return
//     */
//    public SysUserIntroVo findSysUserIntroVoByUserId(Long userId) {
//        if (Objects.equals(userId, PermissionsConstants.ZERO) || userId == null) {
//            userId = SecurityUtils.getUserIdBySession();
//        }
//        SysUserIntro sysUserIntro = sysUserIntroExtService.getOneByUserId(userId);
//        return sysUserIntroHelper.convertToVo(sysUserIntro);
//    }
//}
