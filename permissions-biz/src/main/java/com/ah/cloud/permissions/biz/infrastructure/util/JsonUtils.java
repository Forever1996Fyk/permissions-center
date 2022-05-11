package com.ah.cloud.permissions.biz.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: Json转换工具类
 * @author: YuKai Fan
 * @create: 2021-12-06 15:42
 **/
@Slf4j
public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * 转为json string
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJSONString(T t) {
        if (Objects.isNull(t)) {
            return null;
        }
        try {
            log.info("jackson object to jsonString, params:{}", t);
            String s = OBJECT_MAPPER.writeValueAsString(t);
            return s;
        } catch (JsonProcessingException e) {
            log.error("jackson object to jsonString error, params:{}, exception:{}", t, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * json string转为bean
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, Class<T> tClass) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            log.info("jackson jsonString to object, params:{}", json);
            return OBJECT_MAPPER.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            log.error("jackson jsonString to object  error, params:{}, exception:{}", json, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * bytes convert bean
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T toBean(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            log.info("jackson bytes to object, bytes:{}, className:{}", bytes, clazz.getName());
            return OBJECT_MAPPER.readValue(bytes, clazz);
        } catch (IOException e) {
            log.error("jackson bytes to object  error, bytes:{}, className:{}, exception:{}", bytes, clazz.getName(), Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
