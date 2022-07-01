package com.ah.cloud.permissions.workflow.infrastructure.util;

import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.workflow.infrastructure.constant.ProcessVariableConstants;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 10:34
 **/
public class FlowableUtils {

    /**
     * 获取BPMN流程中, 指定的元素集合
     * @param bpmnModel
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T extends FlowElement> List<T> getBpmnModelElements(BpmnModel bpmnModel, Class<T> clazz) {
        List<T> result = Lists.newArrayList();
        for (Process process : bpmnModel.getProcesses()) {
            Collection<FlowElement> flowElements = process.getFlowElements();
            for (FlowElement flowElement : flowElements) {
                result.add((T) flowElement);
            }
        }
        return result;
    }

    /**
     * 从流程变量获取流程名称, 申请人id, 申请人名称
     *
     * @param variables
     * @return left: 流程名称; middle: 申请人id; right: 申请人名称
     */
    public static ImmutableTriple<String, Long, String> getVariablesNameAndProposerId(Map<String, Object> variables) {
        String processName = PermissionsConstants.STR_EMPTY;
        String proposer = PermissionsConstants.STR_EMPTY;
        Long proposerId = PermissionsConstants.ZERO;
        if (!CollectionUtils.isEmpty(variables)) {
            Object processNameObj = variables.get(ProcessVariableConstants.PROCESS_NAME);
            if (Objects.nonNull(processNameObj)) {
                processName = String.valueOf(processNameObj);
            }
            Object proposerIdObj = variables.get(ProcessVariableConstants.PROPOSER_ID);
            if (Objects.nonNull(processNameObj)) {
                proposerId = Long.valueOf(proposerIdObj.toString());
            }

            Object proposerObj = variables.get(ProcessVariableConstants.PROPOSER);
            if (Objects.nonNull(processNameObj)) {
                proposer = String.valueOf(proposerObj);;
            }
        }
        return ImmutableTriple.of(processName, proposerId, proposer);
    }


    /**
     * 直接创建flow数据库
     *
     * @param args
     */
    public static void main(String[] args) {
        //1、创建ProcessEngineConfiguration实例,该实例可以配置与调整流程引擎的设置
        ProcessEngineConfiguration cfg=new StandaloneProcessEngineConfiguration()
                //2、通常采用xml配置文件创建ProcessEngineConfiguration，这里直接采用代码的方式
                //3、配置数据库相关参数
                .setJdbcUrl("jdbc:mysql://49.234.219.109:3306/workflow?serverTimezone=GMT%2B8&autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false&nullCatalogMeansCurrent=true")
                .setJdbcUsername("root")
                .setJdbcPassword("root")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //4、初始化ProcessEngine流程引擎实例
        ProcessEngine processEngine=cfg.buildProcessEngine();
    }
}
