package com.ah.cloud.permissions.workflow.application.helper;

import com.ah.cloud.permissions.biz.application.service.ext.SysUserExtService;
import com.ah.cloud.permissions.biz.infrastructure.repository.bean.SysUser;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.domain.common.PageResult;
import com.ah.cloud.permissions.enums.common.DeletedEnum;
import com.ah.cloud.permissions.enums.workflow.ProcessInstanceStateEnum;
import com.ah.cloud.permissions.workflow.domain.instance.query.InstanceQuery;
import com.ah.cloud.permissions.workflow.domain.instance.vo.ProcessInstanceVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceQuery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 18:06
 **/
@Component
public class ProcessInstanceHelper {
    @Resource
    private SysUserExtService sysUserExtService;

    /**
     * 构建流程实例查询条件
     * @param query
     * @return
     */
    public void buildProcessInstanceQuery(ProcessInstanceQuery processInstanceQuery, InstanceQuery query) {
        if (StringUtils.isNotEmpty(query.getStartTime())) {
            processInstanceQuery.startedAfter(DateUtils.instantToDate(Instant.parse(query.getStartTime())));
        }
        if (StringUtils.isNotEmpty(query.getEndTime())) {
            processInstanceQuery.startedBefore(DateUtils.instantToDate(Instant.parse(query.getEndTime())));
        }
        if (StringUtils.isNotEmpty(query.getName())) {
            processInstanceQuery.processInstanceNameLike(query.getName());
        }
    }

    public List<ProcessInstanceVo> convertToVoList(List<ProcessInstance> processInstanceList) {
        List<ProcessInstanceVo> list = Lists.newArrayList();
        Set<String> userIdsList = processInstanceList.stream()
                .map(ProcessInstance::getStartUserId)
                .collect(Collectors.toSet());
        List<SysUser> sysUserList = sysUserExtService.list(
                new QueryWrapper<SysUser>().lambda()
                        .in(SysUser::getUserId, userIdsList)
                        .eq(SysUser::getDeleted, DeletedEnum.NO.value)
        );
        Map<Long, String> map = sysUserList.stream().collect(Collectors.toMap(SysUser::getUserId, SysUser::getNickName, (k1, k2) -> k1));
        for (ProcessInstance processInstance : processInstanceList) {
            ProcessInstanceVo processInstanceVo = Convert.INSTANCE.convertToVo(processInstance);
            processInstanceVo.setStartUserName(map.get(Long.valueOf(processInstance.getStartUserId())));
            processInstanceVo.setProcessInstanceState(convertState(processInstance).getState());
        }
        return list;
    }

    public ProcessInstanceVo convertToVo(ProcessInstance processInstance) {
        ProcessInstanceVo processInstanceVo = Convert.INSTANCE.convertToVo(processInstance);
        // 根据用户id获取当前用户名称
        SysUser sysUser = sysUserExtService.getOneByUserId(Long.valueOf(processInstance.getStartUserId()));
        processInstanceVo.setStartUserName(sysUser.getNickName());
        processInstanceVo.setProcessInstanceState(convertState(processInstance).getState());
        return processInstanceVo;
    }

    /**
     * 流程实例状态转换
     * @param processInstance
     * @return
     */
    public ProcessInstanceStateEnum convertState(ProcessInstance processInstance) {
        if (processInstance.isSuspended()) {
            return ProcessInstanceStateEnum.SUSPEND;
        }
        if (processInstance.isEnded()) {
            return ProcessInstanceStateEnum.END;
        }
        return ProcessInstanceStateEnum.ACTIVATE;

    }

    /**
     * 数据转换
     * @param processInstanceList
     * @param count
     * @param query
     * @return
     */
    public PageResult<ProcessInstanceVo> convertToVoPageResult(List<ProcessInstance> processInstanceList, long count, InstanceQuery query) {
        PageResult<ProcessInstanceVo> pageResult = new PageResult<>();
        pageResult.setPageNum(query.getPageNo());
        pageResult.setPageSize(query.getPageSize());
        pageResult.setRows(this.convertToVoList(processInstanceList));
        pageResult.setTotal(count);
        return pageResult;
    }

    @Mapper
    public interface Convert {
        ProcessInstanceHelper.Convert INSTANCE = Mappers.getMapper(ProcessInstanceHelper.Convert.class);

        /**
         * 数据转换
         * @param processInstance
         * @return
         */
        @Mappings({
                @Mapping(target = "processInstanceId", source = "id"),
                @Mapping(target = "processInstanceName", source = "name"),
        })
        ProcessInstanceVo convertToVo(ProcessInstance processInstance);

        /**
         * 数据转换
         * @param processInstanceList
         * @return
         */
        List<ProcessInstanceVo> convertToVoList(List<ProcessInstance> processInstanceList);
    }

}
