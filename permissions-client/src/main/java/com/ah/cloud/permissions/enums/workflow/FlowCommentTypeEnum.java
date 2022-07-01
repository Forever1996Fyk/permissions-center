package com.ah.cloud.permissions.enums.workflow;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-06-19 17:57
 **/
public enum FlowCommentTypeEnum {
    /**
     * 说明
     */
    UNKNOWN("-1", "未知"),
    NORMAL("1", "正常意见"),
    RETURN("2", "退回意见"),
    REJECT("3", "驳回意见"),
    DELEGATE("4", "委派意见"),
    ASSIGN("5", "转办意见"),
    STOP("6", "终止流程");

    private final String type;

    private final String desc;

    public static boolean isValid(String type) {
        FlowCommentTypeEnum value = getByType(type);
        return !Objects.equals(value.getType(), type);
    }

    public static FlowCommentTypeEnum getByType(String type) {
        FlowCommentTypeEnum[] values = values();
        for (FlowCommentTypeEnum value : values) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return UNKNOWN;
    }


    FlowCommentTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
