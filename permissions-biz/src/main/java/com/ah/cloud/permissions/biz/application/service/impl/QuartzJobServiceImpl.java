package com.ah.cloud.permissions.biz.application.service.impl;

import com.ah.cloud.permissions.biz.infrastructure.repository.bean.QuartzJob;
import com.ah.cloud.permissions.biz.infrastructure.repository.mapper.QuartzJobMapper;
import com.ah.cloud.permissions.biz.application.service.QuartzJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * quartz任务调度表 服务实现类
 * </p>
 *
 * @author auto_generation
 * @since 2022-04-29
 */
@Service
public class QuartzJobServiceImpl extends ServiceImpl<QuartzJobMapper, QuartzJob> implements QuartzJobService {

}
