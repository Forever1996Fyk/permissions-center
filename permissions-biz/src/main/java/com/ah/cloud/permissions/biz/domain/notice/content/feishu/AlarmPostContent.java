package com.ah.cloud.permissions.biz.domain.notice.content.feishu;

import lombok.Data;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 17:44
 **/
@Data
public class AlarmPostContent implements FeishuContent {

    /**
     * 富文本内容
     */
    private PostContent post;
}
