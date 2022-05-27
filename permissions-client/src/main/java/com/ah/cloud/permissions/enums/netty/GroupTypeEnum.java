package com.ah.cloud.permissions.enums.netty;

/**
 * @program: permissions-center
 * @description: 群组类型
 * @author: YuKai Fan
 * @create: 2022-05-26 17:17
 **/
public enum GroupTypeEnum {

    /**
     * 普通群聊
     */
    GROUP("group", "普通群聊"),

    /**
     * 聊天室
     */
    CHAT_ROOM("chat_room", "聊天室"),
    ;

    private final String type;

    private final String desc;

    GroupTypeEnum(String type, String desc) {
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
