package com.ah.cloud.permissions.enums.edi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-07-05 10:32
 **/
public enum BizRetryStatusEnum {

    /**
     * edi状态
     */
    RETRY_INIT(0, "初始状态"),
    RETRY_ING(1, "重试中"),
    RETRY_SUCCESS(2, "成功"),
    RETRY_FAIL(3, "失败"),
    RETRY_STOP(4, "达到最大重试次数"),
    RETRY_CLOSE(5, "关闭"),
    ;

    private final int type;

    private final String desc;

    /**
     * 获取正常状态集合
     * @return
     */
    public static List<Integer> getNormalStatusList() {
        return new ArrayList<Integer>() {
            {
                add(RETRY_INIT.getType());
                add(RETRY_ING.getType());
                add(RETRY_SUCCESS.getType());
                add(RETRY_FAIL.getType());
            }
        };
    }

    BizRetryStatusEnum(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isSuccess() {
        return Objects.equals(this, RETRY_SUCCESS);
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public boolean isOver() {
        return !isSuccess();
    }
}
