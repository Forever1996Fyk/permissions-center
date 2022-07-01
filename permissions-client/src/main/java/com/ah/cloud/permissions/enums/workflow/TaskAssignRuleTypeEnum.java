package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-20 21:58
 **/
public enum TaskAssignRuleTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 角色
     */
    ROLE(10, "角色"),

    /**
     * 部门成员
     */
    DEPT_MEMBER(20, "部门成员"),

    /**
     * 部门负责人
     */
    DEPT_LEADER(30, "部门负责人"),

    /**
     * 岗位
     */
    POST(40, "岗位"),

    /**
     * 用户
     */
    USER(50, "用户"),

    /**
     * 当前申请人的领导
     *
     * 也就是当前申请人所在部门的负责人
     */
    PROPOSER_LEADER_L1(60, "自定义脚本"),
    ;

    private final int type;

    private final String desc;

    public static TaskAssignRuleTypeEnum getByType(Integer type) {
        TaskAssignRuleTypeEnum[] values = values();
        for (TaskAssignRuleTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }

        return UNKNOWN;
    }

    public static boolean isValid(Integer type) {
        TaskAssignRuleTypeEnum value = getByType(type);
        return !Objects.equals(type, value.getType());
    }

    TaskAssignRuleTypeEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
