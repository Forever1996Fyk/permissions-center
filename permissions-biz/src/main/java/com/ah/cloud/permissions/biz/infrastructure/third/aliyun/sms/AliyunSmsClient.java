package com.ah.cloud.permissions.biz.infrastructure.third.aliyun.sms;

import com.ah.cloud.permissions.biz.domain.third.aliyun.sms.AliyunSmsRequest;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.third.aliyun.config.properties.AliyunSmsProperties;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.C;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/17 14:46
 **/
@Slf4j
@Component
@EnableConfigurationProperties(AliyunSmsProperties.class)
public class AliyunSmsClient {

    private final Client client;

    /**
     * 发送短信成功响应码
     */
    private final static String SEND_SMS_SUCCESS_CODE = "OK";

    public AliyunSmsClient(AliyunSmsProperties properties) {
        Config config = new Config()
                .setAccessKeyId(properties.getAccessKey())
                .setAccessKeySecret(properties.getAccessKeySecret())
                .setEndpoint(properties.getEndpoint());
        try {
            client = new Client(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送短信
     * @param request
     * @throws Exception
     */
    public void sendSms(AliyunSmsRequest request) throws Exception {
        SendSmsRequest smsRequest = new SendSmsRequest()
                .setTemplateParam(request.getTemplateParam())
                .setTemplateCode(request.getTemplateCode())
                .setPhoneNumbers(request.getPhoneNumbers())
                .setSignName(request.getSignName());
        SendSmsResponse response = client.sendSms(smsRequest);
        if (Objects.isNull(response) || Objects.isNull(response.getBody())) {
            throw new AliyunSmsException("阿里云短信无响应");
        }
        SendSmsResponseBody body = response.getBody();
        if (!StringUtils.equals(body.getCode(), SEND_SMS_SUCCESS_CODE)) {
            throw new AliyunSmsException(body.getMessage());
        }
    }
}
