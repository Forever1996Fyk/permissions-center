package com.ah.cloud.permissions.biz.infrastructure.sequence;

import com.ah.cloud.permissions.biz.infrastructure.sequence.repository.SequenceRepository;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/9/16 20:31
 **/
public class DefaultSyncSequenceLoader implements SyncSequenceLoader<String, Long> {

    private final SequenceRepository sequenceRepository;

    public DefaultSyncSequenceLoader(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @Override
    public Long load(String type) {
        return sequenceRepository.getBizSequence(type).getSequenceId();
    }
}
