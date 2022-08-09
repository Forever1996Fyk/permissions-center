package com.ah.cloud.permissions.biz.domain.api.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-04 15:58
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysApiVo {

    /**
     * 主键id
     */
    private Long id;

    /**
     * 接口编码
     */
    private String apiCode;

    /**
     * 接口名称
     */
    private String apiName;

    /**
     * 接口类型
     */
    private Integer apiType;

    /**
     * 接口类型名称
     */
    private String apiTypeName;

    /**
     * 接口描述
     */
    private String apiDesc;

    /**
     * 请求路径
     */
    private String apiUrl;

    /**
     * 账号状态(1:启用,2:停用)
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 是否认证
     */
    private Integer auth;

    /**
     * 是否公开
     */
    private Integer open;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    /**
     * 读写类型
     */
    private Integer readOrWrite;

    /**
     * 是否可变
     */
    private Integer changeable;
}
