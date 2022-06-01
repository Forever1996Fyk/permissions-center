package com.ah.cloud.permissions.biz.application.checker;

import com.ah.cloud.permissions.biz.domain.msg.push.dto.MsgAppPushUserDTO;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @program: permissions-center
 * @description: 消息处理检查类
 * @author: YuKai Fan
 * @create: 2022-05-31 22:12
 **/
@Component
public class MsgHandleChecker {

    public void check(MsgAppPushUserDTO msgAppPushUserDTO) {
        if (CollectionUtils.isEmpty(msgAppPushUserDTO.getUserIdList())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "用户id集合");
        }
        if (StringUtils.isBlank(msgAppPushUserDTO.getContent())) {
            throw new BizException(ErrorCodeEnum.PARAM_MISS, "消息内容");
        }
    }
}
