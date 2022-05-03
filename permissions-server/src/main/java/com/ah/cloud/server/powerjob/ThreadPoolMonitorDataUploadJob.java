//package com.ah.cloud.server.powerjob;
//
//import com.ah.cloud.permissions.biz.application.manager.ThreadPoolConfigManager;
//import com.ah.cloud.permissions.biz.infrastructure.quartz.BasicProcessor;
//import com.google.common.base.Throwables;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * @program: permissions-center
// * @description: 定时任务执行线程池数据监控
// * @author: YuKai Fan
// * @create: 2022-04-28 18:10
// **/
//@Slf4j
//@Component
//public class ThreadPoolMonitorDataUploadJob implements BasicProcessor {
//    @Resource
//    private ThreadPoolConfigManager threadPoolConfigManager;
//    @Override
//    public ProcessResult process(TaskContext taskContext) throws Exception {
//        String errorMsg = "";
//        ProcessResult processResult = new ProcessResult(true);
//        try {
//            threadPoolConfigManager.uploadThreadPoolMonitorData();
//        } catch (Exception e) {
//            log.error("ThreadPoolMonitorDataUploadJob[process] powerjob handle exception:{}", Throwables.getStackTraceAsString(e));
//            errorMsg = Throwables.getStackTraceAsString(e);
//        }
//
//        if (StringUtils.isNotEmpty(errorMsg)) {
//            processResult.setMsg(errorMsg);
//            processResult.setSuccess(false);
//        }
//        return processResult;
//    }
//}
