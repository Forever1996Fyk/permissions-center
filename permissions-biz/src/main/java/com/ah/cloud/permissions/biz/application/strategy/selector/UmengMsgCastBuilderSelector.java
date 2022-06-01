package com.ah.cloud.permissions.biz.application.strategy.selector;

import com.ah.cloud.permissions.biz.application.strategy.push.umeng.UmengMsgCastBuilder;
import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 21:19
 **/
@Component
public class UmengMsgCastBuilderSelector {
    @Resource
    private List<UmengMsgCastBuilder> list;

    public UmengNotification build(UmengMsgPush msgPush) {
        for (UmengMsgCastBuilder umengMsgCastBuilder : list) {
            if (Objects.equals(umengMsgCastBuilder.getMsgSourceEnum(), msgPush.getMsgSourceEnum())
                    && Objects.equals(umengMsgCastBuilder.getPushMsgModeEnum(), msgPush.getPushMsgModeEnum())) {
                return umengMsgCastBuilder.build(msgPush);
            }
        }
        throw new BizException(ErrorCodeEnum.SELECTOR_NOT_EXISTED, "UmengMsgCastBuilderSelector");
    }
}
