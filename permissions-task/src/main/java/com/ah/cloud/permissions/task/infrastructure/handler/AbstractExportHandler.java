package com.ah.cloud.permissions.task.infrastructure.handler;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.RandomUtil;
import com.ah.cloud.permissions.biz.infrastructure.constant.PermissionsConstants;
import com.ah.cloud.permissions.biz.infrastructure.util.DateUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.FileUtils;
import com.ah.cloud.permissions.biz.infrastructure.util.JsonUtils;
import com.ah.cloud.permissions.enums.FileSuffixTypeEnum;
import com.ah.cloud.permissions.enums.task.ImportExportErrorCodeEnum;
import com.ah.cloud.permissions.task.domain.dto.ExportBaseDTO;
import com.ah.cloud.permissions.task.domain.dto.ImportExportTaskAddDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportBizTypeEnum;
import com.ah.cloud.permissions.task.infrastructure.exception.ImportExportException;
import com.ah.cloud.permissions.task.infrastructure.external.ResourceService;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-14 10:25
 **/
@Slf4j
@Component
public abstract class AbstractExportHandler<T extends ExportBaseDTO> implements ExportHandler {
    @Resource
    private ResourceService resourceService;

    @Override
    public ImmutablePair<String, Long> executeExport(SysImportExportTask task) {
        ImportExportBizTypeEnum bizType = getBizType();
        // 本地文件名
        String sheetName = getFileName();
        // 完整文件名
        String fullLocalFileName = sheetName.concat(PermissionsConstants.DOT_SEPARATOR).concat(FileSuffixTypeEnum.XLSX_DOCX.getValue());
        String ossFullFileName = getOssFullFileName(task, fullLocalFileName);
        try {
            StopWatch stopWatch = new StopWatch(bizType.getDesc().concat("导出功能统计耗时"));
            stopWatch.start(getLogMark().concat("加载数据并写入文件"));
            // 加载数据并写入文件
            String filePath = loadDataAndWriteFile(task, sheetName, fullLocalFileName);
            stopWatch.stop();
            // 文件上传
            stopWatch.start(getLogMark().concat("文件上传"));
            Long resId = resourceService.uploadFile(ossFullFileName, filePath);
            stopWatch.stop();
            log.info("{}[executeExport] export service, taskNo is {}, bizType is {}, resId is {}", getLogMark(), task.getTaskNo(), bizType.getDesc(), resId);
            // 清除本地文件
            cleanLocalFile(filePath);
            log.info(stopWatch.prettyPrint());
            return ImmutablePair.of(task.getFileName(), resId);
        } catch (Exception e) {
            log.error("{}[executeExport] export service failed, reason is {}, task is {}, bizType is {}", getLogMark(), Throwables.getStackTraceAsString(e), JsonUtils.toJsonString(task), bizType.getDesc());
            throw new ImportExportException(ImportExportErrorCodeEnum.EXPORT_EXECUTE_FAILED, task.getTaskNo());
        }
    }

    /**
     * 导出数据并写入文件
     * @param task
     * @param sheetName
     * @param fullLocalFileName
     * @return
     */
    protected String loadDataAndWriteFile(SysImportExportTask task, String sheetName, String fullLocalFileName) {
        ImportExportBizTypeEnum bizTypeEnum = getBizType();
        ApplicationHome applicationHome = new ApplicationHome();
        String filePath = applicationHome.getDir() + "/" + fullLocalFileName;
        ExcelWriter excelWriter = EasyExcel.write(filePath, getClazz()).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        log.info("{}[loadDataAndWriteFile] taskNo is {}, bizType is {}", getLogMark(), task.getTaskNo(), bizTypeEnum.getDesc());
        loadDataAndWriteFile(task.getParam(), excelWriter, writeSheet);
        return filePath;
    }

//    /**
//     * 写入文件
//     * @param exportDataList
//     * @param sheetName
//     * @param fullLocalFileName
//     * @return
//     */
//    protected String writeFile(List<T> exportDataList, String sheetName, String fullLocalFileName) {
//        ApplicationHome applicationHome = new ApplicationHome();
//        String filePath = applicationHome.getDir() + "/" + fullLocalFileName;
//        ExcelWriter excelWriter = EasyExcel.write(filePath, getClazz()).build();
//        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
//        if (CollectionUtils.isNotEmpty(exportDataList)) {
//            excelWriter.write(exportDataList, writeSheet);
//            exportDataList.clear();
//        } else {
//            log.warn("{}[writeFile] data write excel failed, reason is data empty", getLogMark());
//            excelWriter.write(Lists.newArrayList(), writeSheet);
//        }
//        excelWriter.finish();
//        return filePath;
//    }

    @Override
    public void checkExportParam(ImportExportTaskAddDTO addDTO) {

    }

    /**
     * 获取当前处理任务导出实体class
     * @return
     */
    protected abstract Class<T> getClazz();

    /**
     * 加载数据
     *
     * @param param
     * @param excelWriter
     * @param writeSheet
     * @return
     */
    protected abstract void loadDataAndWriteFile(String param, ExcelWriter excelWriter, WriteSheet writeSheet);

    /**
     * 日志标识
     *
     * @return
     */
    protected abstract String getLogMark();

    /**
     * 获取完整的远程服务文件名
     *
     * @param task
     * @param fullLocalFileName
     * @return
     */
    private String getOssFullFileName(SysImportExportTask task, String fullLocalFileName) {
        return StringUtils.isNotEmpty(task.getFileName()) ? task.getFileName().concat(PermissionsConstants.DOT_SEPARATOR).concat(FileSuffixTypeEnum.XLSX_DOCX.getValue()) : fullLocalFileName;
    }

    /**
     * 本地文件名
     * <p>
     * 业务类型 + 年月日时分秒 + 5位随机数 防止重复
     *
     * @return
     */
    protected String getFileName() {
        return getBizType().getDesc().concat(DateUtils.localDateTime2Str(LocalDateTime.now(), DateUtils.pattern1)).concat(RandomUtil.randomString(5));
    }

    /**
     * 清除本地文件
     * @param filePath 文件路径
     */
    private void cleanLocalFile(String filePath){
        if(StringUtils.isEmpty(filePath)){
            return;
        }
        FileUtils.deleteFile(filePath);
    }
}
