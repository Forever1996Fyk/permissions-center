package com.ah.cloud.permissions.biz.domain.sms;

import com.ah.cloud.permissions.biz.domain.sms.Template;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022/11/16 17:09
 **/
@Data
public class AbstractTemplate implements Template {

    /**
     * 手机号集合
     */
    private List<String> phoneNumbers;

    /**
     *  签名名称
     */
    private String signName;

    /**
     * 模版编码
     */
    private String templateCode;

    /**
     * 默认参数
     */
    private Object templateParam;

    /**
     * 扩展字段
     */
    private Map<String, String> extend;
}
