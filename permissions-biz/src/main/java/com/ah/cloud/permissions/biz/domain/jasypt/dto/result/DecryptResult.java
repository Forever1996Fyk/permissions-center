package com.ah.cloud.permissions.biz.domain.jasypt.dto.result;

import lombok.*;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-04 15:06
 **/
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DecryptResult implements HandleResult {

    /**
     * 解密结果
     */
    String result;

    /**
     * bytes 结果
     */
    byte[] resultBytes;
}
