package com.ah.cloud.permissions.task.domain.dto.business;

import com.ah.cloud.permissions.biz.infrastructure.util.CollectionUtils;
import com.ah.cloud.permissions.task.domain.dto.ImportBaseDTO;
import com.ah.cloud.permissions.task.domain.enums.ImportExportTaskStatusEnum;
import com.ah.cloud.permissions.task.infrastructure.repository.bean.SysImportExportTask;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-15 16:41
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportBo<T extends ImportBaseDTO> {

    /**
     * 数据集合
     */
    private List<T> dataList;

    /**
     * 任务
     */
    private SysImportExportTask task;

    /**
     * 错误计数
     */
    private Long errorCount;

    /**
     * 错误信息
     */
    private Map<String, String> errorMsgMap = Maps.newHashMap();


    /**
     * left是fileName，
     * middle是url
     * right是errorReason
     */
    private Triple<String, String, String> triple;

    /**
     * 判断是否需要生成错误报告日志
     *
     * @return 需要生成错误报告的信息
     */
    public List<T> needGenerateErrorFile() {
        return dataList.stream().filter(importDTO -> !importDTO.isSuccess()).collect(Collectors.toList());
    }

    /**
     * 获取校验成功数据
     *
     * @return 成功数据
     */
    public List<T> successValidData() {
        return dataList.stream().filter(ImportBaseDTO::isSuccess).collect(Collectors.toList());
    }

    public void addOne() {
        this.errorCount++;
    }

    /**
     * 获取处理状态
     *
     * @return 操作结果
     */
    public ImportExportTaskStatusEnum getProcessStatus() {
        //是否包含操作失败的
        List<T> successDTOList = dataList.stream().filter(T::isSuccess).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(successDTOList)) {
            return ImportExportTaskStatusEnum.FAILED;
        }
        if (successDTOList.size() == dataList.size()) {
            return ImportExportTaskStatusEnum.SUCCESS;
        } else {
            return ImportExportTaskStatusEnum.PART_SUCCESS;
        }
    }
}
