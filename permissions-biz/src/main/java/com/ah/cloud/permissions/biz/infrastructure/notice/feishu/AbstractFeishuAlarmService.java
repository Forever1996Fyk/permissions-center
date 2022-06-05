package com.ah.cloud.permissions.biz.infrastructure.notice.feishu;

import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.notice.AbstractAlarmService;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-02 21:39
 **/
@Slf4j
public abstract class AbstractFeishuAlarmService extends AbstractAlarmService {


    /**
     * 飞书 签名生成
     * @param secret
     * @param timestamp
     * @return
     */
    protected String genSign(String secret, long timestamp) {
        try {
            //把timestamp+"\n"+密钥当做签名字符串
            String stringToSign = timestamp + "\n" + secret;

            //使用HmacSHA256算法计算签名
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(new byte[]{});
            return new String(Base64.encodeBase64(signData));
        } catch (Exception e) {
            log.error("{}[genSign] generate sign error, secret is {}, timestamp is {}, reason is {}", getLogMark(), secret, timestamp, Throwables.getStackTraceAsString(e));
            throw new BizException(ErrorCodeEnum.FEISHU_BOT_SECRET_ERROR, secret);
        }
    }
}
