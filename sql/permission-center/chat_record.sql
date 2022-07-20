-- 可以按照 msg_type + from_id % 4 进行分表
CREATE TABLE `message_record`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `msg_id`      bigint(20) NOT NULL DEFAULT 0 COMMENT '消息id',
    `from_id`     bigint(20) NOT NULL DEFAULT 0 COMMENT '发送id',
    `to_id`       bigint(20) NOT NULL DEFAULT 0 COMMENT '接收id',
    `format`      tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息格式',
    `msg_type`    tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息类型',
    `msg_source`  tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息来源',
    `content`     varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
    `msg_time`    datetime      NOT NULL COMMENT '消息时间',
    `extension`   varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`      varchar(256)  NOT NULL DEFAULT '' COMMENT '描述',
    `version`     int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`     varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`    varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`     bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY msg_uniq(`from_id`, `msg_type`, `msg_source`, `msg_id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '消息记录表';

-- 可以按照 msg_type + to_id % 4 进行分表
CREATE TABLE `message_offline`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `msg_id`      bigint(20) NOT NULL DEFAULT 0 COMMENT '消息id',
    `to_id`       bigint(20) NOT NULL DEFAULT 0 COMMENT '接收id',
    `msg_type`    tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息类型',
    `msg_source`  tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息来源',
    `format`      tinyint(3) NOT NULL DEFAULT 0 COMMENT '消息格式',
    `content`     varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
    `msg_time`    datetime      NOT NULL COMMENT '消息时间',
    `has_read`    tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否已读',
    `extension`   varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`      varchar(256)  NOT NULL DEFAULT '' COMMENT '描述',
    `version`     int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`     varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`    varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`     bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY to_id_idx(`to_id`, `msg_type`, `msg_id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '离线消息表';
