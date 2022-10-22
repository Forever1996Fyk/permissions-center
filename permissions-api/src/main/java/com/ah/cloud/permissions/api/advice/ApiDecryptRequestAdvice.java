package com.ah.cloud.permissions.api.advice;

import com.ah.cloud.permissions.biz.application.builder.AlgorithmTypeBuilder;
import com.ah.cloud.permissions.biz.application.strategy.selector.AlgorithmTypeBuilderSelector;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.AlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Decrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.Encrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSAAlgorithmType;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSADecrypt;
import com.ah.cloud.permissions.biz.domain.jasypt.dto.rsa.RSAEncrypt;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.controller.AbstractDecryptRequestAdvice;
import com.ah.cloud.permissions.biz.infrastructure.jasypt.encrypt.EncryptTypeEnum;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

import static javax.crypto.Cipher.PRIVATE_KEY;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/10/12 14:59
 **/
@ControllerAdvice
public class ApiDecryptRequestAdvice extends RequestBodyAdviceAdapter {
    @Resource
    private AbstractDecryptRequestAdvice<RSAEncrypt, RSADecrypt> decryptRequestAdvice;


    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return decryptRequestAdvice.support(methodParameter, targetType, converterType);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        try {
            return new InnerHttpInputMessage(inputMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    class InnerHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public InnerHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            String content = easpString(IOUtils.toString(inputMessage.getBody(), StandardCharsets.UTF_8));
            AlgorithmTypeBuilder<? extends Encrypt, ? extends Decrypt> algorithmTypeBuilder = AlgorithmTypeBuilderSelector.select(EncryptTypeEnum.RSA);

            RSAAlgorithmType algorithmType = (RSAAlgorithmType) algorithmTypeBuilder.build(content);
            String decrypt = decryptRequestAdvice.decrypt(algorithmType);
            this.body = IOUtils.toInputStream(decrypt, StandardCharsets.UTF_8);
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public String easpString(String requestData){
            if(requestData != null && !requestData.equals("")){
                String s = "{\"requestData\":\"";
                if(!requestData.startsWith(s)){
                    throw new RuntimeException("参数【requestData】缺失异常！");
                }else{
                    int closeLen = requestData.length()-2;
                    int openLen = s.length();
                    return StringUtils.substring(requestData, openLen, closeLen);
                }
            }
            return "";
        }
    }

}
