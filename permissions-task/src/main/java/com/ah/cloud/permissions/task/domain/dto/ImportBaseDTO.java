package com.ah.cloud.permissions.task.domain.dto;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 17:09
 **/
public interface ImportBaseDTO extends Serializable {

    /**
     * 是否成功
     * @return
     */
    boolean isSuccess();

    /**
     * 错误信息
     * @return
     */
    String getErrorMsg();
}
