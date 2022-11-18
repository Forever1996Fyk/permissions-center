package com.ah.cloud.permissions.biz.infrastructure.third.aliyun.sms;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 15:10
 **/
public class AliyunSmsException extends RuntimeException {

    public AliyunSmsException(String message) {
        super(message);
    }
}
