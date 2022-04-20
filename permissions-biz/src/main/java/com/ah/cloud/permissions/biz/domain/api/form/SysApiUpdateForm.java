package com.ah.cloud.permissions.biz.domain.api.form;

import com.ah.cloud.permissions.biz.infrastructure.annotation.EnumValid;
import com.ah.cloud.permissions.enums.ReadOrWriteEnum;
import com.ah.cloud.permissions.enums.SysApiTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-04-02 16:10
 **/
@Data
public class SysApiUpdateForm {

    /**
     * 接口id
     */
    @NotNull(message = "主键不能为空")
    private Long id;

    /**
     * 接口编码
     */
    @NotEmpty(message = "接口编码不能为空")
    private String apiCode;

    /**
     * 接口名称
     */
    @NotEmpty(message = "接口名称不能为空")
    private String apiName;

    /**
     * 接口类型
     */
    @EnumValid(enumClass = SysApiTypeEnum.class, enumMethod = "isValid")
    private Integer apiType;

    /**
     * 接口描述
     */
    @NotEmpty(message = "接口描述不能为空")
    private String apiDesc;

    /**
     * 请求路径
     */
    @NotEmpty(message = "请求路径不能为空")
    private String apiUrl;

    /**
     * 是否认证
     */
    @NotNull(message = "请确认是否需要认证")
    private Integer auth;

    /**
     * 是否公开
     */
    @NotNull(message = "请确认是否公开")
    private Integer open;

    /**
     * 读写权限标识
     * @see ReadOrWriteEnum
     */
    @EnumValid(enumClass = ReadOrWriteEnum.class, enumMethod = "isValid")
    private Integer readOrWrite;

    /**
     * 备注
     */
    private String remark;
}
