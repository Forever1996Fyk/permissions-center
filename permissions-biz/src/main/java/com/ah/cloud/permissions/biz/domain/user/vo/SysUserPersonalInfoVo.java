package com.ah.cloud.permissions.biz.domain.user.vo;

import com.ah.cloud.permissions.biz.domain.user.intro.vo.SysUserIntroVo;
import lombok.*;

/**
 * @program: permissions-center
 * @description: 系统用户个人信息
 * @author: YuKai Fan
 * @create: 2022-08-21 14:03
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysUserPersonalInfoVo {

    /**
     * 用户基本信息
     */
    private BaseUserInfoVo baseUserInfo;

    /**
     * 用户简介
     */
    private SysUserIntroVo sysUserIntro;
}
