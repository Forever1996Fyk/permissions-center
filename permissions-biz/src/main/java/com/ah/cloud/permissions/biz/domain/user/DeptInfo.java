package com.ah.cloud.permissions.biz.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 部门信息
 * @author: YuKai Fan
 * @create: 2022-06-17 20:40
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptInfo {

    /**
     * 用户部门id
     */
    private Long deptId;
}
