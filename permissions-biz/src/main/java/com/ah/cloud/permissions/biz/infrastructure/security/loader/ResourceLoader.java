package com.ah.cloud.permissions.biz.infrastructure.security.loader;

import com.ah.cloud.permissions.biz.application.manager.SysApiManager;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    private Map<String, AuthorityApiDTO> uriAndApiCodeMap;

    /**
     * 用户接口编码
     */
    private Map<Long, Set<String>> userApiCodeMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<AuthorityApiDTO> allAuthorityApis = sysApiManager.getAllAuthorityApis();
        uriAndApiCodeMap = allAuthorityApis.stream()
                .collect(Collectors.toConcurrentMap(AuthorityApiDTO::getUri, Function.identity()));

        userApiCodeMap = Maps.newConcurrentMap();

    }

    /**
     * 获取当前apiCode集合
     *
     * @return
     */
    public Map<String, AuthorityApiDTO> getUriAndApiCodeMap() {
        return CollectionUtils.isEmpty(uriAndApiCodeMap) ? Maps.newConcurrentMap() : uriAndApiCodeMap;
    }

    /**
     * 更新资源缓存
     * @param authorityApiDTO
     */
    public void updateResourceMap(AuthorityApiDTO authorityApiDTO) {
        if (CollectionUtils.isEmpty(uriAndApiCodeMap)) {
            uriAndApiCodeMap = Maps.newConcurrentMap();
        }
        uriAndApiCodeMap.put(authorityApiDTO.getUri(), authorityApiDTO);
    }

    /**
     * 删除资源
     * @param uri
     */
    public void deleteResourceByUri(String uri) {
        if (CollectionUtils.isEmpty(uriAndApiCodeMap)) {
            return;
        }
        uriAndApiCodeMap.remove(uri);
    }
}
