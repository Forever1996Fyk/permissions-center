package com.ah.cloud.permissions.netty.application.manager;

import com.ah.cloud.permissions.biz.application.service.MessageOfflineService;
import com.ah.cloud.permissions.biz.application.service.MessageRecordService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.MessageOffline;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.MessageRecord;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.netty.application.helper.MessageStoreHelper;
import com.ah.cloud.permissions.netty.domain.message.body.MessageBody;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: permissions-center
 * @description: 消息存储
 * @author: YuKai Fan
 * @create: 2022-05-18 10:19
 **/
@Component
public class MessageStoreManager {
    @Resource
    private MessageStoreHelper messageStoreHelper;
    @Resource
    private MessageRecordService messageRecordService;
    @Resource
    private MessageOfflineService messageOfflineService;

    /**
     * 保存聊天记录
     *
     * @param body
     * @param <T>
     */
    public <T> void saveMessageRecord(MessageBody<T> body) {
        MessageRecord messageRecord = messageStoreHelper.convert(body);
        messageRecordService.save(messageRecord);
    }

    /**
     * 保存离线消息
     *
     * @param body
     * @param <T>
     */
    public <T> void offlineMessageStore(MessageBody<T> body) {
        MessageOffline messageOffline = messageStoreHelper.convertToOffline(body);
        messageOfflineService.save(messageOffline);
    }

    /**
     * 获取离线消息列表
     *
     * @param userId
     * @return
     */
    public List<MessageBody<String>> listOfflineMessage(Long userId) {
        List<MessageOffline> messageOfflineList = messageOfflineService.list(
                new QueryWrapper<MessageOffline>().lambda()
                        .eq(MessageOffline::getToId, userId)
                        .eq(MessageOffline::getHasRead, YesOrNoEnum.NO.getType())
        );
        return messageStoreHelper.convertToBody(messageOfflineList);
    }

    /**
     * 清除离线消息
     *
     * @param userId
     */
    public void clearOfflineMessage(Long userId) {
        MessageOffline updateMessageOffline = new MessageOffline();
        updateMessageOffline.setHasRead(YesOrNoEnum.YES.getType());
        messageOfflineService.update(
                updateMessageOffline,
                new UpdateWrapper<MessageOffline>().lambda()
                        .eq(MessageOffline::getToId, userId)
                        .eq(MessageOffline::getHasRead, YesOrNoEnum.NO.getType())
        );
    }
}
