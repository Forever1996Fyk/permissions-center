package com.ah.cloud.permissions.security.loader;

import com.ah.cloud.permissions.biz.application.manager.SysApiManager;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
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
    @Autowired
    private SysApiManager sysApiManager;

    private Map<String, AuthorityApiDTO> uriAndApiCodeMap;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<AuthorityApiDTO> allAuthorityApis = sysApiManager.getAllAuthorityApis();
        uriAndApiCodeMap = allAuthorityApis.stream()
                .collect(Collectors.toMap(AuthorityApiDTO::getUri, authorityApiDTO -> authorityApiDTO));

    }

    /**
     * 获取当前apiCode集合
     * @return
     */
    public Map<String, AuthorityApiDTO> getUriAndApiCodeMap() {
        return CollectionUtils.isEmpty(uriAndApiCodeMap)?Maps.newHashMap():uriAndApiCodeMap;
    }
}
