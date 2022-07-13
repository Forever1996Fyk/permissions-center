package com.ah.cloud.permissions.edi.application.checker;

import com.ah.cloud.permissions.edi.domain.record.dto.AddRetryBizRecordDTO;
import com.ah.cloud.permissions.edi.infrastructure.exceprion.EdiException;
import com.ah.cloud.permissions.enums.edi.EdiErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 17:37
 **/
@Slf4j
@Component
public class RetryRecordChecker {

    /**
     * 校验参数
     * @param addDTO
     */
    public void check(AddRetryBizRecordDTO addDTO) {
        if (Objects.isNull(addDTO)) {
            log.error("RetryBizRecordAdapterServiceImpl[createRetryRecord] create retry record failed, reason is AddRetryBizRecordDTO null");
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_RECORD_IS_NULL);
        }

        if (addDTO.getBizType() == null) {
            throw new EdiException(EdiErrorCodeEnum.RETRY_BIZ_TYPE_IS_NULL);
        }
    }
}
