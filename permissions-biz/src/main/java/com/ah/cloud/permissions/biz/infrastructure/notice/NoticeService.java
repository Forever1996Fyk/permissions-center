package com.ah.cloud.permissions.biz.infrastructure.notice;

import com.ah.cloud.permissions.domain.common.NoticeResult;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 通知service 最基础通知的接口, 这个接口的目的就是为了发送通知
 * @author: YuKai Fan
 * @create: 2022-06-02 14:15
 **/
public interface NoticeService<T> {

    /**
     * 消息通知
     * @param t
     * @return
     */
    void sendNotice(T t);

    /**
     * 批量消息通知
     * @param t
     * @return
     */
    void batchSendNotice(List<T> list);
}
