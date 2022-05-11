package com.ah.cloud.permissions.biz.domain.resource.form;

import com.ah.cloud.permissions.enums.YesOrNoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public Integer bizType;

    /**
     * 是否公开
     */
    public Integer isPublic;

    /**
     * 过期时长
     */
    public Long expireTime;

    /**
     * 时间类型
     */
    public Integer timeUnit;

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
}
