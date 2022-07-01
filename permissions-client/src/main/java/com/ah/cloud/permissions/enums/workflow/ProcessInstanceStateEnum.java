package com.ah.cloud.permissions.enums.workflow;

import com.ah.cloud.permissions.enums.SysApiTypeEnum;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-10 17:06
 **/
public enum ProcessInstanceStateEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 激活流程实例
     */
    ACTIVATE(1, "激活流程实例"),

    /**
     * 挂起流程实例
     */
    SUSPEND(2, "挂起流程实例"),

    /**
     * 结束流程实例
     */
    END(3, "结束流程实例"),
    ;

    private final int state;

    private final String desc;

    ProcessInstanceStateEnum(int state, String desc) {
        this.state = state;
        this.desc = desc;
    }

    public static boolean isValid(Integer state) {
        ProcessInstanceStateEnum processInstanceStateEnum = getByState(state);
        return !Objects.equals(processInstanceStateEnum, UNKNOWN);
    }

    public static ProcessInstanceStateEnum getByState(Integer state) {
        ProcessInstanceStateEnum[] processInstanceStateEnums = values();
        for (ProcessInstanceStateEnum processInstanceStateEnum : processInstanceStateEnums) {
            if (Objects.equals(processInstanceStateEnum.getState(), state)) {
                return processInstanceStateEnum;
            }
        }
        return UNKNOWN;
    }

    public int getState() {
        return state;
    }

    public String getDesc() {
        return desc;
    }
}
