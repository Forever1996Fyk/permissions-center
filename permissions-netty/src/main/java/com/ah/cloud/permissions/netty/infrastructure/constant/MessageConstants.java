package com.ah.cloud.permissions.netty.infrastructure.constant;

/**
 * @program: permissions-center
 * @description:
 * @author: YuKai Fan
 * @create: 2022-05-12 10:21
 **/
public class MessageConstants {

    /**
     * 消息魔数
     */
    public final static short MAGIC_CODE = 0x2022;

    /**
     * 消息版本
     */
    public final static short VERSION = 0x01;

    /**
     * 消息长度
     *
     * 魔数short: 2字节
     * 版本short: 2字节
     * 数据长度int: 8字节
     */
    public final static int MESSAGE_LENGTH = 8;

}
