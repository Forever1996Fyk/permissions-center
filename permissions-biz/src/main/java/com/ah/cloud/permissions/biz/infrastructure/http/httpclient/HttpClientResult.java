package com.ah.cloud.permissions.biz.infrastructure.http.httpclient;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-31 15:56
 **/
@Data
@AllArgsConstructor
public class HttpClientResult implements Serializable {
    private static final long serialVersionUID = 7072196141045535564L;

    /**
     * 响应吗
     */
    private int code;

    /**
     * 响应内容
     */
    private String content;

    public HttpClientResult(int code) {
        this.code = code;
    }

    public HttpClientResult(String content) {
        this.content = content;
    }
}
