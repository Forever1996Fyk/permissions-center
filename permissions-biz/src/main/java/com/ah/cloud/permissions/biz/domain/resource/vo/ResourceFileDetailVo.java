package com.ah.cloud.permissions.biz.domain.resource.vo;

import com.ah.cloud.permissions.biz.domain.resource.meta.vo.ResourceMetaDataVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-07 18:24
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceFileDetailVo {

    /**
     * 文件信息
     */
    private ResourceFileVo fileInfo;

    /**
     * 文件元数据
     */
    private ResourceMetaDataVo meta;
}
