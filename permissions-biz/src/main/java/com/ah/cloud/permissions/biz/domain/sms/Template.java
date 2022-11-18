package com.ah.cloud.permissions.biz.domain.sms;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @program: permissions-center
 * @description: 短信模版
 * @author: YuKai Fan
 * @create: 2022/11/16 16:51
 **/
public interface Template {

    /**
     * 模版编码
     *
     * @return String
     */
    String getTemplateCode();

    /**
     * 手机号
     * @return String
     */
    List<String> getPhoneNumbers();

    /**
     * 签名名称
     * @return String
     */
    String getSignName();

    /**
     * 模版参数
     *
     * @return p
     */
    Object getTemplateParam();

    /**
     * 扩展字段
     * @return
     */
    Map<String, String> getExtend();
}
