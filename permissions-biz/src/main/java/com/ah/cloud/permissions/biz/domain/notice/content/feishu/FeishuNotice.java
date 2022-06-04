package com.ah.cloud.permissions.biz.domain.notice.content.feishu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 16:17
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeishuNotice<T extends FeishuContent> {

    /**
     * 消息类型
     */
    private String msg_type;

    /**
     * 接收者id
     */
    private String receive_id;

    /**
     * 内容
     */
    private T content;

}
