package com.ah.cloud.permissions.biz.application.manager;

import com.ah.cloud.permissions.biz.application.helper.SysApiHelper;
import com.ah.cloud.permissions.biz.domain.api.dto.AuthorityApiDTO;
import com.ah.cloud.permissions.biz.application.service.SysApiService;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiAddForm;
import com.ah.cloud.permissions.biz.domain.api.form.SysApiUpdateForm;
import com.ah.cloud.permissions.biz.domain.api.query.SysApiQuery;
import com.ah.cloud.permissions.biz.domain.api.vo.SysApiVo;
import com.ah.cloud.permissions.biz.infrastructure.exception.BizException;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysApi;
import com.ah.cloud.permissions.biz.infrastructure.security.loader.ResourceLoader;
import com.ah.cloud.permissions.biz.infrastructure.util.AppUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.SecurityUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.ApiStatusEnum;
import com.ah.cloud.permissions.enums.SysApiTypeEnum;
import com.ah.cloud.permissions.enums.YesOrNoEnum;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.common.ErrorCodeEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 系统api管理器
 * @author: YuKai Fan
 * @create: 2021-12-24 15:32
 **/
@Slf4j
@Component
public class SysApiManager {
    @Resource
    private SysApiHelper sysApiHelper;
    @Resource
    private SysApiService sysApiService;
    @Resource
    private ResourceLoader resourceLoader;

    /**
     * 新增api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void addSysApi(SysApiAddForm form) {
        // 接口编码必须唯一
        List<SysApi> sysApiList = sysApiService.list(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getApiCode, form.getApiCode())
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (!CollectionUtils.isEmpty(sysApiList)) {
            throw new BizException(ErrorCodeEnum.API_CODE_IS_EXISTED, form.getApiCode());
        }
        SysApi sysApi = sysApiHelper.convertToEntity(form);
        boolean result = sysApiService.save(sysApi);
        if (result) {
            resourceLoader.updateResourceMap(
                    AuthorityApiDTO.builder()
                            .apiCode(form.getApiCode())
                            .apiStatusEnum(ApiStatusEnum.NORMAL)
                            .apiTypeEnum(SysApiTypeEnum.getByType(form.getApiType()))
                            .auth(Objects.equals(YesOrNoEnum.YES.getType(), form.getAuth()))
                            .open(Objects.equals(YesOrNoEnum.YES.getType(), form.getOpen()))
                            .uri(form.getApiUrl())
                            .build()
            );
        }
    }

    /**
     * 更新系统api
     *
     * @param form
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateSysApi(SysApiUpdateForm form) {
        SysApi existSysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, form.getId())
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existSysApi)) {
            throw new BizException(ErrorCodeEnum.API_INFO_IS_NOT_EXISTED_UPDATE_FAILED);
        }
        if (!Objects.equals(form.getApiCode(), existSysApi.getApiCode())) {
            throw new BizException(ErrorCodeEnum.API_CODE_CANNOT_CHANGE_UPDATE_FAILED);
        }
        SysApi updateSysApi = sysApiHelper.convertToEntity(form);
        updateSysApi.setVersion(existSysApi.getVersion());
        boolean updateResult = sysApiService.updateById(updateSysApi);
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        resourceLoader.updateResourceMap(
                AuthorityApiDTO.builder()
                        .apiCode(form.getApiCode())
                        .apiStatusEnum(ApiStatusEnum.NORMAL)
                        .apiTypeEnum(SysApiTypeEnum.getByType(form.getApiType()))
                        .auth(Objects.equals(YesOrNoEnum.YES.getType(), form.getAuth()))
                        .open(Objects.equals(YesOrNoEnum.YES.getType(), form.getOpen()))
                        .uri(form.getApiUrl())
                        .build()
        );

    }

    /**
     * 根据id删除系统api
     *
     * @param id
     */
    public void deleteSysApiById(Long id) {
        SysApi existSysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(existSysApi)) {
            return;
        }
        SysApi deleteSysApi = new SysApi();
        deleteSysApi.setVersion(existSysApi.getVersion());
        deleteSysApi.setDeleted(id);
        deleteSysApi.setId(id);
        deleteSysApi.setModifier(SecurityUtils.getUserNameBySession());
        boolean deleteResult = sysApiService.updateById(deleteSysApi);
        if (!deleteResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        resourceLoader.deleteResourceByUri(existSysApi.getApiUrl());
    }

    /**
     * 获取所有权限api信息
     *
     * @return
     */
    public List<AuthorityApiDTO> getAllAuthorityApis() {
        List<SysApi> sysApis = sysApiService.list(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        return sysApiHelper.convertToDTO(sysApis);
    }

    /**
     * 根据id查询接口信息
     *
     * @param id
     * @return
     */
    public SysApiVo findById(Long id) {
        SysApi sysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysApi)) {
            throw new BizException(ErrorCodeEnum.API_INFO_IS_NOT_EXISTED);
        }
        return sysApiHelper.convertToVo(sysApi);
    }

    /**
     * 分页查询用户信息
     *
     * @param query
     * @return
     */
    public PageResult<SysApiVo> pageSysApiList(SysApiQuery query) {
        PageInfo<SysApi> pageInfo = PageMethod.startPage(query.getPageNo(), query.getPageSize())
                .doSelectPageInfo(
                        () -> sysApiService.list(
                                new QueryWrapper<SysApi>().lambda()
                                        .orderByDesc(SysApi::getCreatedTime)
                                        .like(
                                                !StringUtils.isEmpty(query.getApiName()),
                                                SysApi::getApiName, query.getApiName())
                                        .like(
                                                StringUtils.isNotBlank(query.getApiUrl()),
                                                SysApi::getApiUrl, query.getApiUrl())
                                        .eq(
                                                !StringUtils.isEmpty(query.getApiCode()),
                                                SysApi::getApiCode, query.getApiCode())
                                        .in(
                                                !CollectionUtils.isEmpty(query.getApiType()),
                                                SysApi::getApiType, query.getApiType())
                                        .eq(
                                                query.getOpen() != null,
                                                SysApi::getOpen, query.getOpen()
                                        )
                                        .eq(
                                                query.getAuth() != null,
                                                SysApi::getAuth, query.getAuth()
                                        )
                                        .eq(
                                                query.getStatus() != null,
                                                SysApi::getStatus, query.getStatus()
                                        )
                                        .eq(
                                                SysApi::getDeleted,
                                                DeletedEnum.NO.value)
                        ));
        return sysApiHelper.convertToPageResult(pageInfo);
    }

    /**
     * 变更api状态
     *
     * @param id
     * @param status
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeApiStatus(Long id, Integer status) {
        ApiStatusEnum apiStatusEnum = ApiStatusEnum.valueOf(status);
        if (Objects.equals(apiStatusEnum, ApiStatusEnum.UNKNOWN)) {
            throw new BizException(ErrorCodeEnum.PARAM_ILLEGAL_FIELD, "status");
        }
        SysApi sysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysApi)) {
            throw new BizException(ErrorCodeEnum.API_INFO_IS_NOT_EXISTED);
        }
        if (YesOrNoEnum.getByType(sysApi.getChangeable()).isNo()) {
            throw new BizException(ErrorCodeEnum.API_STATUS_AUTH_OPEN_CANNOT_CHANGEABLE);
        }
        SysApi updateSysApi = new SysApi();
        updateSysApi.setId(id);
        updateSysApi.setStatus(apiStatusEnum.getStatus());
        updateSysApi.setVersion(sysApi.getVersion());
        boolean updateResult = sysApiService.update(
                updateSysApi,
                new UpdateWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getVersion, sysApi.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        resourceLoader.updateResourceMap(
                AuthorityApiDTO.builder()
                        .apiCode(sysApi.getApiCode())
                        .apiStatusEnum(apiStatusEnum)
                        .apiTypeEnum(SysApiTypeEnum.getByType(sysApi.getApiType()))
                        .auth(AppUtils.convertIntToBool(sysApi.getAuth()))
                        .open(AppUtils.convertIntToBool(sysApi.getOpen()))
                        .uri(sysApi.getApiUrl())
                        .build()
        );
    }

    /**
     * 变更api权限
     *
     * @param id
     * @param auth
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeApiAuth(Long id, Integer auth) {
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(auth);
        SysApi sysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysApi)) {
            throw new BizException(ErrorCodeEnum.API_INFO_IS_NOT_EXISTED);
        }
        if (YesOrNoEnum.getByType(sysApi.getChangeable()).isNo()) {
            throw new BizException(ErrorCodeEnum.API_STATUS_AUTH_OPEN_CANNOT_CHANGEABLE);
        }
        SysApi updateSysApi = new SysApi();
        updateSysApi.setId(id);
        updateSysApi.setAuth(yesOrNoEnum.getType());
        updateSysApi.setVersion(sysApi.getVersion());
        boolean updateResult = sysApiService.update(
                updateSysApi,
                new UpdateWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getVersion, sysApi.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        resourceLoader.updateResourceMap(
                AuthorityApiDTO.builder()
                        .apiCode(sysApi.getApiCode())
                        .apiStatusEnum(ApiStatusEnum.valueOf(sysApi.getStatus()))
                        .apiTypeEnum(SysApiTypeEnum.getByType(sysApi.getApiType()))
                        .auth(AppUtils.convertIntToBool(yesOrNoEnum.getType()))
                        .open(AppUtils.convertIntToBool(sysApi.getOpen()))
                        .uri(sysApi.getApiUrl())
                        .build()
        );
    }

    /**
     * 变更api开放类型
     *
     * @param id
     * @param open
     */
    @Transactional(rollbackFor = Exception.class)
    public void changeApiOpen(Long id, Integer open) {
        YesOrNoEnum yesOrNoEnum = YesOrNoEnum.getByType(open);
        SysApi sysApi = sysApiService.getOne(
                new QueryWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getDeleted, DeletedEnum.NO.value)
        );
        if (Objects.isNull(sysApi)) {
            throw new BizException(ErrorCodeEnum.API_INFO_IS_NOT_EXISTED);
        }
        if (YesOrNoEnum.getByType(sysApi.getChangeable()).isNo()) {
            throw new BizException(ErrorCodeEnum.API_STATUS_AUTH_OPEN_CANNOT_CHANGEABLE);
        }
        SysApi updateSysApi = new SysApi();
        updateSysApi.setId(id);
        updateSysApi.setOpen(yesOrNoEnum.getType());
        updateSysApi.setVersion(sysApi.getVersion());
        boolean updateResult = sysApiService.update(
                updateSysApi,
                new UpdateWrapper<SysApi>().lambda()
                        .eq(SysApi::getId, id)
                        .eq(SysApi::getVersion, sysApi.getVersion())
        );
        if (!updateResult) {
            throw new BizException(ErrorCodeEnum.VERSION_ERROR);
        }
        resourceLoader.updateResourceMap(
                AuthorityApiDTO.builder()
                        .apiCode(sysApi.getApiCode())
                        .apiStatusEnum(ApiStatusEnum.valueOf(sysApi.getStatus()))
                        .apiTypeEnum(SysApiTypeEnum.getByType(sysApi.getApiType()))
                        .auth(AppUtils.convertIntToBool(sysApi.getAuth()))
                        .open(AppUtils.convertIntToBool(yesOrNoEnum.getType()))
                        .uri(sysApi.getApiUrl())
                        .build()
        );
    }
}
