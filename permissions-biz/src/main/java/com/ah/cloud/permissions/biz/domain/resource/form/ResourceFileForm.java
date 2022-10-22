package com.ah.cloud.permissions.biz.domain.resource.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.ResourceBizTypeEnum;
import com.ah.cloud.permissions.enums.TimeTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 16:54
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFileForm {

    /**
     * 业务类型
     */
    @EnumValid(enumClass = ResourceBizTypeEnum.class, enumMethod = "isValid")
    private Integer bizType;

    /**
     * 是否公开
     */
    private Integer isPublic;

    /**
     * 过期时长
     */
    private Long expireTime;

    /**
     * 时间类型
     */
    private Integer timeUnit;

    /**
     * 所属标识
     */
    private String ownerId;

    /**
     * 获取默认表单
     * @return
     */
    public static ResourceFileForm defaultForm() {
        return ResourceFileForm.builder()
                .expireTime(-1L)
                .isPublic(YesOrNoEnum.YES.getType())
                .build();
    }

    public void check() {
        if (Objects.isNull(expireTime)) {
            this.expireTime = -1L;
        }
        if (Objects.isNull(isPublic)) {
            this.isPublic = YesOrNoEnum.YES.getType();
        }
        if (Objects.isNull(timeUnit)) {
            timeUnit = TimeTypeEnum.SECONDS.getType();
        }
    }
}
