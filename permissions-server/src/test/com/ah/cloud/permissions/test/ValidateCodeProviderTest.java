package com.ah.cloud.permissions.test;

import com.ah.cloud.permissions.api.assembler.ValidateCodeAssembler;
import com.ah.cloud.permissions.biz.application.manager.ValidateCodeManager;
import com.ah.cloud.permissions.biz.application.provider.SendMode;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-01-19 22:57
 **/
public class ValidateCodeProviderTest extends BaseTest {
    @Resource
    private ValidateCodeManager manager;
    @Resource
    private ValidateCodeAssembler assembler;

    @Test
    public void sendCodeTest() {
        SendMode sendMode = assembler.convert("17856941755");
        manager.sendCode(sendMode);
    }
}
