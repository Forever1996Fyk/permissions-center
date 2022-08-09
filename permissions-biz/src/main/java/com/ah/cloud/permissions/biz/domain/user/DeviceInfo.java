package com.ah.cloud.permissions.biz.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description: 设备信息
 * @author: YuKai Fan
 * @create: 2022-05-18 11:37
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfo implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;
}
