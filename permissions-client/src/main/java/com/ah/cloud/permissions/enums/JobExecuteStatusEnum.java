package com.ah.cloud.permissions.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-01 11:50
 **/
public enum JobExecuteStatusEnum {
    /**
     * 初始化
     */
    INIT(1, "初始化"),

    /**
     * 正在执行
     */
    EXECUTING(2, "正在执行"),

    /**
     * 执行成功
     */
    SUCCESS(3, "执行成功"),

    /**
     * 执行失败
     */
    FAILED(4, "执行失败"),
    ;

    private final int status;

    private final String desc;

    private final static List<JobExecuteStatusEnum> END_NODE = new ArrayList<JobExecuteStatusEnum>(){
        {
            add(SUCCESS);
            add(FAILED);
        }
    };

    JobExecuteStatusEnum(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 是否是最终节点
     * @return
     */
    public boolean isEndNode() {
        return END_NODE.contains(this);
    }
}
