package com.ah.cloud.permissions.biz.domain.notice.alarm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 15:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmParam {

    /**
     * webHook
     */
    private String webHook;

    /**
     * 获取内容
     */
    private AlarmMsg content;

    /**
     * 额外参数
     */
    private Map<String, String> extraParams;
}
