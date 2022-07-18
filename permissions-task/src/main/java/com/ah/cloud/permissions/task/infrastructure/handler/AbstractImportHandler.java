package com.ah.cloud.permissions.task.infrastructure.handler;

import cn.hutool.core.date.StopWatch;
import com.ah.cloud.permissions.task.domain.dto.ImportBaseDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 15:46
 **/
@Slf4j
@Component
public abstract class AbstractImportHandler<T extends ImportBaseDTO> implements ImportHandler {

    /**
     * 这里需要加事务注解，因为如果是分批读取数据保存，需要保证所有的导出数据全部成功，或者全部失败
     * @param task 导入任务
     * @param inputStream 文件流
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ImmutableTriple<String, Long, ImportExportTaskStatusEnum> executeImport(SysImportExportTask task, InputStream inputStream) {
        StopWatch stopWatch = new StopWatch(getLogMark().concat("执行导入耗时"));
        ImportExportBizTypeEnum bizTypeEnum = ImportExportBizTypeEnum.getByCode(task.getBizType());
        log.info("{}[executeImport] Import processData start,  taskNo is {}, bizType is {}", getLogMark(), task.getTaskNo(), bizTypeEnum.getDesc());
        stopWatch.start();
        // 如果数据量较小 < 1000, 可以使用下面方法, 如果数据量超过1000，可以使用 easyexcel 分批读取数据，每次读取1000条然后批量保存
        if (needBatchImport()) {
            EasyExcel.read(inputStream, getClazz(), getListener(task)).sheet().doRead();
            stopWatch.stop();
            return null;
        } else {
            List<T> list = EasyExcel.read(inputStream).head(getClazz()).sheet().doReadSync();
            ImmutableTriple<String, Long, ImportExportTaskStatusEnum> result = processData(list, task);
            stopWatch.stop();
            return result;
        }
    }

    /**
     * 是否需要批量导入
     * @return
     */
    protected abstract boolean needBatchImport();

    /**
     * 获取监听器
     *
     * @param task
     * @return
     */
    protected abstract AnalysisEventListener<T> getListener(SysImportExportTask task);

    /**
     * 获取日志标识
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 获取导入文件实体class
     * @return
     */
    protected abstract Class<T> getClazz();

    /**
     * 处理数据
     * @param dataList
     * @param task
     * @return
     */
    public abstract ImmutableTriple<String, Long, ImportExportTaskStatusEnum> processData(List<T> dataList, SysImportExportTask task);
}
