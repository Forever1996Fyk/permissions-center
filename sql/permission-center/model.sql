-- 可以按照 group_type + group_id % 4 进行分表
CREATE TABLE `chat_group`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_id`       bigint(20) NOT NULL DEFAULT 0 COMMENT '群组id',
    `group_name`     varchar(64)   NOT NULL DEFAULT '' COMMENT '群组名称',
    `group_type`     tinyint(3) NOT NULL DEFAULT 0 COMMENT '群组类型',
    `max_group_size` tinyint(3) NOT NULL DEFAULT 0 COMMENT '最大群组数量',
    `group_avatar`   varchar(255)  NOT NULL DEFAULT '' COMMENT '群组头像',
    `extension`      varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`         varchar(256)  NOT NULL DEFAULT '' COMMENT '描述',
    `version`        int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`        varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`    timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`       varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`    timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`        bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY group_uniq(`group_id`, `group_name`, `group_type`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '聊天群组表';

-- 可以按照 group_id % 4 进行分表
CREATE TABLE `chat_group_relation`
(
    `id`          bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `group_id`    bigint(20) NOT NULL DEFAULT 0 COMMENT '群组id',
    `user_id`     bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
    `group_owner` tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否群主',
    `extension`   varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`      varchar(256)  NOT NULL DEFAULT '' COMMENT '描述',
    `version`     int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`     varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`    varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`     bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY           group_idx(`group_id`),
    KEY           user_idx(`user_id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '聊天群组关系表';

-- 可以按照 room_id % 4 + owner_id % 4 进行分表
CREATE TABLE `chat_room`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `room_id`       bigint(20) NOT NULL DEFAULT 0 COMMENT '聊天室id',
    `room_name`     varchar(64)   NOT NULL DEFAULT '' COMMENT '聊天室名称',
    `max_room_size` bigint(20) NOT NULL DEFAULT 0 COMMENT '聊天室最大数量',
    `room_avatar`   varchar(255)  NOT NULL DEFAULT '' COMMENT '聊天室头像',
    `owner_id`      bigint(20) NOT NULL DEFAULT 0 COMMENT '聊天室所属人id',
    `status`        tinyint(3) NOT NULL DEFAULT 0 COMMENT '聊天室状态',
    `extension`     varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`        varchar(256)  NOT NULL DEFAULT '' COMMENT '描述',
    `version`       int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`       varchar(64)   NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`      varchar(64)   NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`       bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY room_uniq(`room_id`, `owner_id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '聊天室表';

