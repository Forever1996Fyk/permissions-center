package com.ah.cloud.permissions.test.manager;

import com.ah.cloud.permissions.test.BaseTest;
import com.ah.cloud.permissions.workflow.application.manager.ProcessManager;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-08 14:59
 **/
public class ProcessManagerTest extends BaseTest {

    @Resource
    private ProcessManager processManager;

    @Test
    public void testDeploy() {
        processManager.deploy();
    }
}
