package com.ah.cloud.permissions.biz.domain.third.aliyun.sms;

import lombok.*;

import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/16 17:42
 **/
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AliyunSmsRequest {

    /**
     * 手机号
     */
    private String phoneNumbers;

    /**
     * 签名
     */
    private String signName;

    /**
     * 模版编码
     */
    private String templateCode;

    /**
     * 模版参数
     */
    private String templateParam;
}
