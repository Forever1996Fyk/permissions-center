package com.ah.cloud.permissions.biz.infrastructure.security.loader;

import com.ah.cloud.permissions.biz.application.manager.SysApiManager;
import com.ah.cloud.permissions.biz.application.strategy.cache.impl.RedisCacheHandleStrategy;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.constant.CacheConstants;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description: 资源加载器
 * @author: YuKai Fan
 * @create: 2021-12-24 15:15
 **/
@Slf4j
@Component
public class ResourceLoader implements InitializingBean {
    @Resource
    private SysApiManager sysApiManager;
    @Resource
    private RedisCacheHandleStrategy redisCacheHandleStrategy;

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws Exception {
        List<AuthorityApiDTO> allAuthorityApis = sysApiManager.getAllAuthorityApis();
        if (CollectionUtils.isEmpty(allAuthorityApis)) {
            throw new RuntimeException("Project Run failed, cause api authority info list is empty");
        }
        // 重启时先清除缓存
        redisCacheHandleStrategy.removeCacheHashByKey(CacheConstants.AUTHORITY_API_PREFIX);
        for (AuthorityApiDTO authorityApiDTO : allAuthorityApis) {
            redisCacheHandleStrategy.setCacheHashValue(CacheConstants.AUTHORITY_API_PREFIX, authorityApiDTO.getUri(), authorityApiDTO);
        }
    }

    /**
     * 获取当前apiCode集合
     *
     * @return
     */
    public Map<String, AuthorityApiDTO> getUriAndApiCodeMap() {
        List<AuthorityApiDTO> allAuthorityApis = redisCacheHandleStrategy.getCacheHashValues(CacheConstants.AUTHORITY_API_PREFIX);
        return CollectionUtils.isEmpty(allAuthorityApis) ? Maps.newConcurrentMap() : allAuthorityApis.stream()
                .collect(Collectors.toConcurrentMap(AuthorityApiDTO::getUri, Function.identity()));
    }

    /**
     * 更新资源缓存
     *
     * @param authorityApiDTO
     */
    public void updateResourceMap(AuthorityApiDTO authorityApiDTO) {
        if (Objects.nonNull(authorityApiDTO) && StringUtils.isNotBlank(authorityApiDTO.getUri())) {
            redisCacheHandleStrategy.setCacheHashValue(CacheConstants.AUTHORITY_API_PREFIX, authorityApiDTO.getUri(), authorityApiDTO);
        }
    }

    /**
     * 根据uri获取当前权限api缓存信息
     *
     * @param uri
     * @return
     */
    public AuthorityApiDTO getCacheResourceByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey error, uri is empty");
            return null;
        }
        Map<String, AuthorityApiDTO> uriAndApiCodeMap = this.getUriAndApiCodeMap();
        String apiCodeKey = uriAndApiCodeMap
                .keySet()
                .stream()
                .filter(item -> PATH_MATCHER.match(item, uri))
                .findAny()
                .orElse(null);
        if (StringUtils.isBlank(apiCodeKey)) {
            log.error("ResourceLoader[getCacheResourceByUri] get apiCodeKey by uri result is empty, uri is {}, uriAndApiCodeMap is {}", uri, uriAndApiCodeMap);
            return null;
        }
        return uriAndApiCodeMap.get(apiCodeKey);
    }

    /**
     * 删除资源
     *
     * @param uri
     */
    public boolean deleteResourceByUri(String uri) {
        if (StringUtils.isBlank(uri)) {
            return false;
        }
        Long result = redisCacheHandleStrategy.deleteCacheHashByKey(CacheConstants.AUTHORITY_API_PREFIX, uri);
        return result != null && !Objects.equals(result, PermissionsConstants.ZERO);
    }
}
