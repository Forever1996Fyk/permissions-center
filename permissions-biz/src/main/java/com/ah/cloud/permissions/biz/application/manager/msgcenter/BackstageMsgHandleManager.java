package com.ah.cloud.permissions.biz.application.manager.msgcenter;

import com.ah.cloud.permissions.biz.application.checker.MsgHandleChecker;
import com.ah.cloud.permissions.biz.application.service.MsgAccountDeviceService;
import com.ah.cloud.permissions.biz.application.strategy.selector.MsgPushServiceSelector;
import com.ah.cloud.permissions.biz.domain.msg.push.UmengMsgPush;
import com.ah.cloud.permissions.biz.domain.msg.push.dto.MsgAppPushUserDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.MsgAccountDevice;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.push.UmengErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 后台消息处理管理
 * @author: YuKai Fan
 * @create: 2022-05-31 13:59
 **/
@Slf4j
@Component
public class BackstageMsgHandleManager {
    @Resource
    private MsgPushServiceSelector selector;
    @Resource
    private MsgHandleChecker msgHandleChecker;
    @Resource
    private MsgAccountDeviceService msgAccountDeviceService;

    /**
     * 发送app push
     * @param msgAppPushUserDTO
     */
    public void sendAppPushMsg(MsgAppPushUserDTO msgAppPushUserDTO) {
        msgHandleChecker.check(msgAppPushUserDTO);
        List<MsgAccountDevice> msgAccountDeviceList = msgAccountDeviceService.list(
                new QueryWrapper<MsgAccountDevice>().lambda()
                        .in(MsgAccountDevice::getUserId, msgAppPushUserDTO.getUserIdList())
                        .eq(MsgAccountDevice::getDeleted, DeletedEnum.NO.value)
        );
        if (CollectionUtils.isEmpty(msgAccountDeviceList)) {
            throw new BizException(UmengErrorCodeEnum.MSG_APP_PUSH_FAIL_USER_ASSOCIATED_DEVICE_IS_EMPTY);
        }
        String deviceNos = msgAccountDeviceList.stream().map(MsgAccountDevice::getDeviceNo).collect(Collectors.joining(PermissionsConstants.COMMA_SEPARATOR));
        UmengMsgPush umengMsgPush = UmengMsgPush.builder()
                .pushMsgModeEnum(msgAppPushUserDTO.getPushMsgModeEnum())
                .msgSourceEnum(msgAppPushUserDTO.getMsgSourceEnum())
                .content(msgAppPushUserDTO.getContent())
                .title(msgAppPushUserDTO.getTitle())
                .jumpUrl(msgAppPushUserDTO.getJumpUrl())
                .voiceCode(msgAppPushUserDTO.getVoiceCode())
                .deviceNo(deviceNos)
                .build();
        selector.sendPushMsg(umengMsgPush);
    }


}
