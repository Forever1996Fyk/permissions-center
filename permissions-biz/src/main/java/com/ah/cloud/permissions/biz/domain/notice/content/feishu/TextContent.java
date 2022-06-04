package com.ah.cloud.permissions.biz.domain.notice.content.feishu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: permissions-center
 * @description: 文本消息
 * @author: YuKai Fan
 * @create: 2022-06-02 16:26
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TextContent implements FeishuContent {

    /**
     * 文本内容
     */
    private String text;
}
