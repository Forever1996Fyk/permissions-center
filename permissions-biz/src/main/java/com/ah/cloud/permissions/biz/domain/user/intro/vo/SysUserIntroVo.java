package com.ah.cloud.permissions.biz.domain.user.intro.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-08-19 19:02
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SysUserIntroVo {

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 标签
     */
    private String tags;

    /**
     * 位置
     */
    private String location;

    /**
     * 生日
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthDay;

    /**
     * 技能
     */
    private String skills;

    /**
     * 爱好
     */
    private String hobby;
}
