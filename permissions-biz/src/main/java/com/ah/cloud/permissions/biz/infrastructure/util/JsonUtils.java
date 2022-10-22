package com.ah.cloud.permissions.biz.infrastructure.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

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
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String toJsonString(T t) {
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
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String json, Class<T> tClass) {
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
     * json string转为map
     *
     * @param json
     * @param <T>
     * @return
     */
    public static Map<String, Object> stringToMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            log.info("jackson jsonString to map, params:{}", json);
            TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {};
            return OBJECT_MAPPER.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            log.error("jackson jsonString to object  error, params:{}, exception:{}", json, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * json string转为map
     *
     * @param json
     * @param <T>
     * @return
     */
    public static Map<String, String> stringToMap2(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            log.info("jackson jsonString to map, params:{}", json);
            TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};
            return OBJECT_MAPPER.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            log.error("jackson jsonString to object  error, params:{}, exception:{}", json, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * json字符串转成list
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, getCollectionType(List.class, cls));
        } catch (JsonProcessingException e) {
            String className = cls.getSimpleName();
            log.error(" parse json [{}] to class [{}] error：{}", jsonString, className, e);
        }
        return null;
    }

    /**
     * json字符串转成list
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> Set<T> jsonToSet(String jsonString, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, getCollectionType(Set.class, cls));
        } catch (JsonProcessingException e) {
            String className = cls.getSimpleName();
            log.error(" parse json [{}] to class [{}] error：{}", jsonString, className, e);
        }
        return null;
    }

    /**
     * 获取泛型的Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  实体bean
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * bytes convert bean
     *
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T byteToBean(byte[] bytes, Class<T> clazz) {
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

    /**
     * map转为bean
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    /**
     * byte转为JsonNode
     *
     * @param bytes
     * @return
     */
    public static JsonNode byteToReadTree(byte[] bytes) {
        try {
            return OBJECT_MAPPER.readTree(bytes);
        } catch (IOException e) {
            log.error("jackson bytes read tree convert JsonNode  error, bytes:{}, exception:{}", bytes, Throwables.getStackTraceAsString(e));
            return null;
        }
    }

    /**
     * byte转为JsonNode
     *
     * @param content
     * @return
     */
    public static JsonNode byteToReadTree(String content) {
        try {
            return OBJECT_MAPPER.readTree(content);
        } catch (IOException e) {
            log.error("jackson bytes read tree convert JsonNode  error, bytes:{}, exception:{}", content, Throwables.getStackTraceAsString(e));
            return null;
        }
    }
}
