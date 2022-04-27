package com.ah.cloud.permissions.enums;

import java.util.Objects;

/**
 * @program: permissions-center
 * @description: 线程池阻塞队列类型枚举
 * @author: YuKai Fan
 * @create: 2022-04-26 21:52
 **/
public enum BlockingQueueEnum {

    /**
     * 可调整无限队列
     */
    RESIZE_LINKED_BLOCKING_QUEUE("ResizeLinkedBlockingQueue"),
    ;

    private final String value;

    BlockingQueueEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isValid(String value) {
        BlockingQueueEnum[] values = values();
        for (BlockingQueueEnum blockingQueueEnum : values) {
            if (Objects.equals(blockingQueueEnum.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
