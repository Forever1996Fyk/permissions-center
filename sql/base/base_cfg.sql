CREATE TABLE `cfg_thread_pool`
(
    `id`                bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`              varchar(64)  NOT NULL DEFAULT '' COMMENT '线程池名称',
    `core_pool_size`    int(11) NOT NULL DEFAULT '0' COMMENT '核心线程数',
    `maximum_pool_size` int(11) NOT NULL DEFAULT '0' COMMENT '最大线程数',
    `keep_alive_time`   bigint(20) NOT NULL DEFAULT '0' COMMENT '存活时间',
    `queue_type`        varchar(64)  NOT NULL DEFAULT '' COMMENT '队列类型',
    `work_queue_size`   int(11) NOT NULL DEFAULT '0' COMMENT '存放带执行任务的队列大小',
    `ext`               varchar(64)  NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`            varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `version`           int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`           varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`          varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`           bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`, `deleted`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '线程池配置'

CREATE TABLE `thread_pool_data`
(
    `id`                       bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`                     varchar(64)  NOT NULL DEFAULT '' COMMENT '线程池名称',
    `client`                   varchar(64)  NOT NULL DEFAULT '' COMMENT '客户端',
    `core_pool_size`           int(11) NOT NULL DEFAULT '0' COMMENT '核心线程数',
    `maximum_pool_size`        int(11) NOT NULL DEFAULT '0' COMMENT '最大线程数',
    `active_count`             int(11) NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
    `pool_size`                int(11) NOT NULL DEFAULT '0' COMMENT '当前线程数',
    `largest_pool_size`        int(11) NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
    `task_count`               bigint(20) NOT NULL DEFAULT '0' COMMENT '任务总数',
    `completed_task_count`     bigint(20) NOT NULL DEFAULT '0' COMMENT '任务完成数',
    `queue_capacity`           int(11) NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
    `queue_size`               int(11) NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
    `queue_remaining_capacity` int(11) NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
    `sharding_key`             int(11) NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
    `ext`                      varchar(64)  NOT NULL DEFAULT '' COMMENT '扩展字段',
    `remark`                   varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
    `version`                  int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
    `creator`                  varchar(64)  NOT NULL DEFAULT '' COMMENT '创建人',
    `create_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifier`                 varchar(64)  NOT NULL DEFAULT '' COMMENT '更新人',
    `modify_time`              timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `deleted`                  bigint(20) NOT NULL DEFAULT '0' COMMENT '是否删除',
    PRIMARY KEY (`id`),
    KEY                        `idx_name_create_time_client` (`name`, `create_time`, `client`, `deleted`)
) DEFAULT CHARSET = utf8mb4 COMMENT = '线程池监控数据';

create table thread_pool_data_202205 like thread_pool_data;
create table thread_pool_data_202206 like thread_pool_data;
create table thread_pool_data_202207 like thread_pool_data;
create table thread_pool_data_202208 like thread_pool_data;
create table thread_pool_data_202209 like thread_pool_data;
create table thread_pool_data_202210 like thread_pool_data;
create table thread_pool_data_202211 like thread_pool_data;
create table thread_pool_data_202212 like thread_pool_data;
