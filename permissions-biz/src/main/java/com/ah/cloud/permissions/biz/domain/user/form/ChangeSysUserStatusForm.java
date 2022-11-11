package com.ah.cloud.permissions.biz.domain.user.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.UserStatusEnum;
import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/9 17:25
 **/
@Data
public class ChangeSysUserStatusForm {

    /**
     * 唯一标识
     */
    private Long id;

    /**
     * 用户状态
     */
    @EnumValid(enumClass = UserStatusEnum.class, enumMethod = "isValid")
    private Integer status;
}
