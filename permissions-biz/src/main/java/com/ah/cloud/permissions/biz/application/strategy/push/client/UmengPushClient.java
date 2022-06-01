package com.ah.cloud.permissions.biz.application.strategy.push.client;

import com.ah.cloud.permissions.biz.domain.msg.push.umeng.UmengNotification;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.http.httpclient.HttpClientUtils;
import com.ah.cloud.permissions.biz.infrastructure.http.httpclient.HttpClientResult;
import com.ah.cloud.permissions.enums.common.push.UmengErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 17:20
 **/
@Slf4j
@Component
public class UmengPushClient implements PushClient<UmengNotification, String> {
    /**
     * USER_AGENT
     */
    protected static final String USER_AGENT = "Mozilla/5.0";

    /**
     * The host
     */
    protected static final String UMENG_HOST = "http://msg.umeng.com";

    /**
     * The post path
     */
    protected static final String POST_PATH = "/api/send";

    @Resource
    private HttpClientUtils httpClientUtils;

    @Override
    public String send(UmengNotification msg) throws Exception {
        String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
        msg.setPredefinedKeyValue("timestamp", timestamp);
        String url = UMENG_HOST + POST_PATH;
        String postBody = msg.getPostBody();
        String sign = DigestUtils.md5Hex(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes(StandardCharsets.UTF_8));
        url = url + "?sign=" + sign;
        HttpPost post = new HttpPost(url);
        Map<String, String> header  = new HashMap<>(4);
        header.put("User-Agent", USER_AGENT);
        StringEntity se = new StringEntity(postBody, "UTF-8");
        post.setEntity(se);
        HttpClientResult httpClientResult = httpClientUtils.doPost(url, header, postBody);
        int status = httpClientResult.getCode();
        log.info("PushClient [send]  Response Code:{}",status);
        String result = httpClientResult.getContent();
        if (status != HttpStatus.OK.value()) {
            log.warn("PushClient [send]  Response result:{}",result);
            throw new BizException(UmengErrorCodeEnum.MSG_APP_PUSH_FAIL);
        }
        return  result;
    }
}
