package com.ah.cloud.permissions.test.manager;

import com.ah.cloud.permissions.test.BaseTest;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowBusinessManager;
import com.ah.cloud.permissions.workflow.domain.business.form.WorkflowBusinessStartForm;
import com.google.common.collect.Maps;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 14:01
 **/
public class WorkflowBusinessManagerTest extends BaseTest {
    @Resource
    private WorkflowBusinessManager workflowBusinessManager;

    @Test
    public void testSubmitProcess() {
        WorkflowBusinessStartForm form = new WorkflowBusinessStartForm();
        form.setProcessId(5L);
        Map<String, Object> map = Maps.newHashMap();
        map.put("test", "test");
        form.setVariables(map);
        workflowBusinessManager.submitWorkflowBusiness(form);
    }
}
