package com.ah.cloud.permissions.biz.application.service.impl;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJobTask;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.QuartzJobTaskMapper;
import com.ah.cloud.permissions.biz.application.service.QuartzJobTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * quartz任务调度日志表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-04-29
 */
@Service
public class QuartzJobTaskServiceImpl extends ServiceImpl<QuartzJobTaskMapper, QuartzJobTask> implements QuartzJobTaskService {

}
