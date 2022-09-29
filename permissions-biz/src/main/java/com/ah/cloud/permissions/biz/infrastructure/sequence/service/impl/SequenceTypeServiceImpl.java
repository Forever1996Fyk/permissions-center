package com.ah.cloud.permissions.biz.infrastructure.sequence.service.impl;

import com.ah.cloud.permissions.biz.infrastructure.sequence.service.SequenceTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 15:40
 **/
@Slf4j
@Service
public class SequenceTypeServiceImpl implements SequenceTypeService {

    @Override
    public List<String> getSequenceTypeConfig() {
        return null;
    }
}
