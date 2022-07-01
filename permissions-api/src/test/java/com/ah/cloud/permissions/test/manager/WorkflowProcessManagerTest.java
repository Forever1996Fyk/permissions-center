package com.ah.cloud.permissions.test.manager;

import com.ah.cloud.permissions.biz.application.service.ext.WorkflowProcessExtService;
import com.ah.cloud.permissions.test.BaseTest;
import com.ah.cloud.permissions.workflow.application.manager.WorkflowProcessManager;
import com.ah.cloud.permissions.workflow.application.service.ProcessDefinitionService;

import javax.annotation.Resource;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-17 14:13
 **/
public class WorkflowProcessManagerTest extends BaseTest {
    @Resource
    private WorkflowProcessManager workflowProcessManager;
    @Resource
    private WorkflowProcessExtService workflowProcessExtService;
    @Resource
    private ProcessDefinitionService processDefinitionService;

    private final static String bpmnXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<bpmn2:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn2=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" id=\"diagram_process_1655444671232\" targetNamespace=\"http://activiti.org/bpmn\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd\">\n" +
            "  <bpmn2:process id=\"workflow1\" name=\"工作流1\" isExecutable=\"true\">\n" +
            "    <bpmn2:startEvent id=\"Event_1eubi65\" name=\"开始事件\">\n" +
            "      <bpmn2:outgoing>Flow_1pcnxjh</bpmn2:outgoing>\n" +
            "    </bpmn2:startEvent>\n" +
            "    <bpmn2:userTask id=\"Activity_1hs2mbb\" name=\"用户任务\">\n" +
            "      <bpmn2:incoming>Flow_1pcnxjh</bpmn2:incoming>\n" +
            "      <bpmn2:outgoing>Flow_0gu8tri</bpmn2:outgoing>\n" +
            "    </bpmn2:userTask>\n" +
            "    <bpmn2:sequenceFlow id=\"Flow_1pcnxjh\" sourceRef=\"Event_1eubi65\" targetRef=\"Activity_1hs2mbb\" />\n" +
            "    <bpmn2:userTask id=\"Activity_0i9scr5\" name=\"用户任务\">\n" +
            "      <bpmn2:incoming>Flow_0gu8tri</bpmn2:incoming>\n" +
            "      <bpmn2:outgoing>Flow_0b0csci</bpmn2:outgoing>\n" +
            "    </bpmn2:userTask>\n" +
            "    <bpmn2:sequenceFlow id=\"Flow_0gu8tri\" sourceRef=\"Activity_1hs2mbb\" targetRef=\"Activity_0i9scr5\" />\n" +
            "    <bpmn2:endEvent id=\"Event_1pidmir\" name=\"结束事件\">\n" +
            "      <bpmn2:incoming>Flow_0b0csci</bpmn2:incoming>\n" +
            "    </bpmn2:endEvent>\n" +
            "    <bpmn2:sequenceFlow id=\"Flow_0b0csci\" sourceRef=\"Activity_0i9scr5\" targetRef=\"Event_1pidmir\" />\n" +
            "  </bpmn2:process>\n" +
            "  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n" +
            "    <bpmndi:BPMNPlane id=\"BPMNPlane_1\" bpmnElement=\"process_1655444671232\">\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_1pcnxjh_di\" bpmnElement=\"Flow_1pcnxjh\">\n" +
            "        <di:waypoint x=\"328\" y=\"280\" />\n" +
            "        <di:waypoint x=\"380\" y=\"280\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0gu8tri_di\" bpmnElement=\"Flow_0gu8tri\">\n" +
            "        <di:waypoint x=\"480\" y=\"280\" />\n" +
            "        <di:waypoint x=\"540\" y=\"280\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNEdge id=\"Flow_0b0csci_di\" bpmnElement=\"Flow_0b0csci\">\n" +
            "        <di:waypoint x=\"640\" y=\"280\" />\n" +
            "        <di:waypoint x=\"702\" y=\"280\" />\n" +
            "      </bpmndi:BPMNEdge>\n" +
            "      <bpmndi:BPMNShape id=\"Event_1eubi65_di\" bpmnElement=\"Event_1eubi65\">\n" +
            "        <dc:Bounds x=\"292\" y=\"262\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"288\" y=\"305\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Activity_1hs2mbb_di\" bpmnElement=\"Activity_1hs2mbb\">\n" +
            "        <dc:Bounds x=\"380\" y=\"240\" width=\"100\" height=\"80\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Activity_0i9scr5_di\" bpmnElement=\"Activity_0i9scr5\">\n" +
            "        <dc:Bounds x=\"540\" y=\"240\" width=\"100\" height=\"80\" />\n" +
            "      </bpmndi:BPMNShape>\n" +
            "      <bpmndi:BPMNShape id=\"Event_1pidmir_di\" bpmnElement=\"Event_1pidmir\">\n" +
            "        <dc:Bounds x=\"702\" y=\"262\" width=\"36\" height=\"36\" />\n" +
            "        <bpmndi:BPMNLabel>\n" +
            "          <dc:Bounds x=\"698\" y=\"305\" width=\"44\" height=\"14\" />\n" +
            "        </bpmndi:BPMNLabel>\n" +
            "      </bpmndi:BPMNShape>\n" +
            "    </bpmndi:BPMNPlane>\n" +
            "  </bpmndi:BPMNDiagram>\n" +
            "</bpmn2:definitions>";
}
