CREATE TABLE `quartz_job`
(
    `id`                       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `job_name`                 varchar(64)  NOT NULL COMMENT '任务名称',
    `job_group`                varchar(64)  NOT NULL DEFAULT '' COMMENT '任务组名',
    `job_params`               varchar(1024) NOT NULL DEFAULT '' COMMENT '任务参数',
    `job_class_name`           varchar(255) NOT NULL COMMENT '任务类路径',
    `cron_expression`          varchar(64) NOT NULL  COMMENT 'cron表达式',
    `execute_policy`           tinyint(3) NOT NULL DEFAULT 0 COMMENT '执行计划策略',
    `is_concurrent`            tinyint(3) NOT NULL DEFAULT 0 COMMENT '是否支持并发',
    `status`                   tinyint(3) NOT NULL DEFAULT 1 COMMENT '任务状态',
    `extension`                varchar(2048)  NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`                   varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `version`                  int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`                  varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`                 varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`                  bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = 'quartz任务调度表';

CREATE TABLE `quartz_job_log`
(
    `id`                       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `job_id`                   bigint(20) NOT NULL COMMENT '任务id',
    `error_message`            varchar(2048) NOT NULL default '' COMMENT '错误信息',
    `start_time`               datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time`                 datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
    `status`                   tinyint(3) NOT NULL DEFAULT 1 COMMENT '状态(1: 执行成功, 2: 执行中, 3: 执行失败)',
    `extension`                varchar(2048)  NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`                   varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `version`                  int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`                  varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`                 varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`                  bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`)
) DEFAULT CHARSET = utf8mb4 COMMENT = 'quartz任务调度日志表';


