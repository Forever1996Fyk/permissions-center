package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysApiHelper;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.infrastructure.application.service.SysApiService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysApi;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: permissions-center
 * @description: 系统api管理器
 * @author: YuKai Fan
 * @create: 2021-12-24 15:32
 **/
@Slf4j
@Component
public class SysApiManager {
    @Autowired
    private SysApiHelper sysApiHelper;
    @Autowired
    private SysApiService sysApiService;

    /**
     * 获取所有权限api信息
     * @return
     */
    public List<AuthorityApiDTO> getAllAuthorityApis() {
        List<SysApi> sysApis = sysApiService.list(new QueryWrapper<SysApi>().lambda()
                .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        return sysApiHelper.convertToDTO(sysApis);
    }
}
