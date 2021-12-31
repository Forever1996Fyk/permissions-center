package com.ah.cloud.permissions.biz.domain.api.dto;

import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 权限api
 * @author: YuKai Fan
 * @create: 2021-12-24 15:28
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityApiDTO {

    /**
     * api编码
     */
    private String apiCode;

    /**
     * 请求路径
     */
    private String uri;


    /**
     * api状态
     */
    private ApiStatusEnum apiStatusEnum;

    /**
     * 是否认证
     */
    private Boolean auth;

    /**
     * 是否公开
     */
    private Boolean open;

}
