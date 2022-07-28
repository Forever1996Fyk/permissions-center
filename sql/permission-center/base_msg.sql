CREATE TABLE `msg_info`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `msg_code`       varchar(32) NOT NULL COMMENT '消息编号',
    `msg_title`      varchar(64) NOT NULL COMMENT '消息标题',
    `msg_type`       tinyint(2) NOT NULL COMMENT '消息类型',
    `push_type`      tinyint(2) NOT NULL COMMENT '推送方式',
    `push_time`      datetime             DEFAULT CURRENT_TIMESTAMP COMMENT '推送时间',
    `push_time_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '//推送时间类型 1:立即 2：自定义\n ',
    `msg_status`     tinyint(2) NOT NULL COMMENT '消息状态',
    `top_flag`       tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否app置顶',
    `reach_all_flag` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否推送全部 1否 2是',
    `content`        longtext COMMENT '消息内容',
    `res_ids`        varchar(255)         DEFAULT NULL COMMENT '资源ids',
    `deleted`        bigint(20) DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`        varchar(100)         DEFAULT '' COMMENT '创建人',
    `modifier`       varchar(100)         DEFAULT '' COMMENT '修改人',
    `version`        int(11) DEFAULT '1' COMMENT '版本号',
    `create_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_msg_no` (`msg_code`) USING BTREE COMMENT '消息编码唯一'
) DEFAULT CHARSET = utf8mb4 COMMENT = '消息主表';

CREATE TABLE `msg_account_device`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `device_no`   varchar(64) NOT NULL COMMENT '设备编号',
    `user_id`     bigint(20) NOT NULL COMMENT '接受人id',
    `device_type` tinyint(2) NOT NULL DEFAULT '1' COMMENT '设备类型：1:安卓 2：ios',
    `phone`       varchar(20) NOT NULL COMMENT '手机号码',
    `deleted`     bigint(20) DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time` timestamp   NOT NULL               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`     varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建人',
    `modifier`    varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '修改人',
    `version`     int(11) DEFAULT '1' COMMENT '版本号',
    `create_time` timestamp   NOT NULL               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_account_device` (`device_no`, `user_id`, `deleted`) USING BTREE COMMENT '用户id唯一',
    KEY           `idx_user_id` (`user_id`) USING BTREE
) DEFAULT CHARSET = utf8 COMMENT = '设备用户关系表'

CREATE TABLE `msg_app_push_log`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `source_code`    varchar(32) NOT NULL COMMENT '来源编号',
    `source_type`    varchar(6)  NOT NULL COMMENT '来源类型：MG/AP',
    `succeed`        tinyint(2) NOT NULL DEFAULT '1' COMMENT '友盟返回：成功失败',
    `result`         varchar(64) NOT NULL DEFAULT '' COMMENT '友盟返回：成功失败',
    `device_no_list` text        NOT NULL COMMENT '推送设备集合',
    `device_type`    tinyint(2) NOT NULL COMMENT '设备类型：1:安卓 2：ios',
    `deleted`        bigint(20) DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`        varchar(100)         DEFAULT '' COMMENT '创建人',
    `modifier`       varchar(100)         DEFAULT '' COMMENT '修改人',
    `version`        int(11) DEFAULT '1' COMMENT '版本号',
    `create_time`    timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY              `index_source` (`source_code`, `succeed`, `deleted`)
) DEFAULT CHARSET = utf8mb4 COMMENT = 'app push记录表';

CREATE TABLE `msg_event`
(
    `id`             bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `event_code`     varchar(32)  NOT NULL COMMENT '事件编号',
    `event_name`     varchar(64)  NOT NULL COMMENT '事件名称',
    `source_system`  varchar(32)  NOT NULL COMMENT '所属系统',
    `jump_url`       varchar(255)                       DEFAULT NULL COMMENT '跳转页面',
    `push_type`      tinyint(2) NOT NULL COMMENT '推送类型',
    `top_flag`       tinyint(2) NOT NULL COMMENT '是否app置顶',
    `secret_key`     varchar(256) NOT NULL COMMENT '密钥',
    `reach_all_flag` tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否推送全部 1否 2是',
    `voice_code`     varchar(255)                       DEFAULT NULL COMMENT '推送语音code',
    `deleted`        bigint(20) DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time`    timestamp    NOT NULL              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`        varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建人',
    `modifier`       varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '修改人',
    `version`        int(11) DEFAULT '1' COMMENT '版本号',
    `create_time`    timestamp    NOT NULL              DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_msg_event` (`event_code`) USING BTREE COMMENT '消息编码唯一'
) DEFAULT CHARSET = utf8 COMMENT = '消息事件表';

CREATE TABLE `msg_reach`
(
    `id`                 bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `reach_type`         tinyint(2) NOT NULL COMMENT '目标类型',
    `reach_value`        varchar(64) NOT NULL COMMENT '目标值',
    `source_id`          bigint(20) NOT NULL COMMENT '来源编号来源id(msg_info，mgs_event)',
    `source_id_type`     tinyint(2) NOT NULL COMMENT '来源id类型 1 msg_info 2 msg_event',
    `reach_name`         varchar(64) NOT NULL COMMENT '对象名称：角色名，用户名等',
    `group_id`           bigint(20) DEFAULT NULL COMMENT '范围类型id 对应租户id 运营组id 角色id等',
    `superior_check_all` tinyint(2) NOT NULL DEFAULT '1' COMMENT '上级是否全选 1否 2是',
    `group_name`         varchar(255)                       DEFAULT NULL COMMENT '范围名称',
    `deleted`            bigint(20) DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time`        timestamp   NOT NULL               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`            varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '创建人',
    `modifier`           varchar(100) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '修改人',
    `version`            int(11) DEFAULT '1' COMMENT '版本号',
    `create_time`        timestamp   NOT NULL               DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_msg_reach` (`source_id`, `reach_type`, `reach_value`, `deleted`) USING BTREE COMMENT '目标来源唯一'
) DEFAULT CHARSET = utf8 COMMENT = '消息目标表';

CREATE TABLE `msg_target`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `receiver_account` varchar(32)  NOT NULL COMMENT '接受人账号',
    `user_id`          bigint(20) NOT NULL COMMENT '接受人id',
    `msg_code`         varchar(32)  NOT NULL COMMENT '消息编码',
    `read_flag`        tinyint(3) NOT NULL DEFAULT '0' COMMENT '读取标识 0未读 1已读',
    `remind`           tinyint(3) NOT NULL DEFAULT '0' COMMENT '不在提醒 0no 1 yes',
    `msg_type`         tinyint(2) NOT NULL COMMENT '消息类型',
    `real_name`        varchar(32)  NOT NULL COMMENT '真实姓名',
    `top_flag`         tinyint(2) NOT NULL DEFAULT '1' COMMENT '是否app置顶',
    `msg_title`        varchar(64)  NOT NULL COMMENT '消息标题',
    `deleted`          bigint(20) NOT NULL DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
    `modify_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `creator`          varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
    `modifier`         varchar(100) NOT NULL DEFAULT '' COMMENT '修改人',
    `version`          int(11) NOT NULL DEFAULT '1' COMMENT '版本号',
    `create_time`      timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_msg_target` (`user_id`, `msg_code`, `deleted`) USING BTREE COMMENT '消息编码唯一'
) DEFAULT CHARSET = utf8mb4 COMMENT = '消息对象表'
