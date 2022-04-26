CREATE TABLE `edi_biz_retry_record` (
                                        `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `biz_no` varchar(100) NOT NULL DEFAULT '' COMMENT '业务单据号',
                                        `biz_id` bigint(20) DEFAULT NULL COMMENT '业务id',
                                        `biz_type` int(11) NOT NULL COMMENT '业务类型',
                                        `biz_source` varchar(100) DEFAULT NULL COMMENT '系统来源',
                                        `error_message` varchar(500) DEFAULT NULL COMMENT '异常信息',
                                        `last_op_time` datetime DEFAULT NULL COMMENT '上次执行时间',
                                        `record_status` int(11) NOT NULL DEFAULT '0' COMMENT '失败 成功 重试中',
                                        `biz_params` longtext COMMENT '执行参数',
                                        `retry_times` int(11) DEFAULT '0' COMMENT '重试次数',
                                        `version` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
                                        `ext` longtext COMMENT '扩展信息',
                                        `remark` longtext COMMENT '备注',
                                        `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
                                        `modifier` varchar(100) DEFAULT NULL COMMENT '变更人',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                        `deleted` bigint(20) DEFAULT '0' COMMENT '删除标志位',
                                        `sharding_key` varchar(100) DEFAULT NULL,
                                        `env` varchar(100) DEFAULT NULL COMMENT '环境标字段',
                                        PRIMARY KEY (`id`),
                                        KEY `idx_record_status` (`record_status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '重试记录表';

CREATE TABLE `edi_tech_biz_retry_record` (
                                             `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                             `biz_no` varchar(100) NOT NULL DEFAULT '' COMMENT '业务单据号',
                                             `biz_id` bigint(20) DEFAULT NULL COMMENT '业务id',
                                             `biz_type` int(11) NOT NULL COMMENT '业务类型',
                                             `biz_source` varchar(100) DEFAULT NULL COMMENT '系统来源',
                                             `error_message` varchar(500) DEFAULT NULL COMMENT '异常信息',
                                             `last_op_time` datetime DEFAULT NULL COMMENT '上次执行时间',
                                             `record_status` int(11) NOT NULL DEFAULT '0' COMMENT '失败 成功 重试中',
                                             `biz_params` longtext COMMENT '执行参数',
                                             `retry_times` int(11) DEFAULT '0' COMMENT '重试次数',
                                             `version` int(11) DEFAULT '0' COMMENT '乐观锁版本号',
                                             `ext` longtext COMMENT '扩展信息',
                                             `remark` longtext COMMENT '备注',
                                             `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
                                             `modifier` varchar(100) DEFAULT NULL COMMENT '变更人',
                                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                             `modify_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                             `deleted` bigint(20) DEFAULT '0' COMMENT '删除标志位',
                                             `sharding_key` varchar(100) DEFAULT NULL,
                                             `env` varchar(100) DEFAULT NULL COMMENT '环境标字段',
                                             PRIMARY KEY (`id`),
                                             KEY `idx_record_status` (`record_status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '重试记录表';

CREATE TABLE `edi_cfg_biz_retry` (
                                     `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                     `biz_type` int(11) NOT NULL COMMENT '业务类型',
                                     `biz_name` varchar(30) DEFAULT NULL COMMENT '业务名称',
                                     `max_retry_times` int(11) DEFAULT '0' COMMENT '最大重试次数。0表示不重试，-1无限重试',
                                     `retry_interval` int(11) DEFAULT '10000' COMMENT '执行间隔，毫秒为单位',
                                     `retry_model` int(11) DEFAULT '0' COMMENT '0系统自动重试 1手动重试',
                                     `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                     `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
                                     `modifier` varchar(100) DEFAULT NULL COMMENT '变更人',
                                     `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                     `status` int(11) NOT NULL DEFAULT '2' COMMENT '停用启用',
                                     `deleted` bigint(20) DEFAULT '0' COMMENT '删除标志位',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `unique_biz` (`biz_type`, `deleted`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = '重试配置表';

CREATE TABLE `edi_cfg_tech_biz_retry` (
                                          `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
                                          `biz_type` int(11) NOT NULL COMMENT '业务类型',
                                          `biz_name` varchar(30) DEFAULT NULL COMMENT '业务名称',
                                          `max_retry_times` int(11) DEFAULT '0' COMMENT '最大重试次数。0表示不重试，-1无限重试',
                                          `retry_interval` int(11) DEFAULT '10000' COMMENT '执行间隔，毫秒为单位',
                                          `retry_model` int(11) DEFAULT '0' COMMENT '0系统自动重试 1手动重试',
                                          `remark` varchar(200) DEFAULT NULL COMMENT '备注',
                                          `creator` varchar(100) DEFAULT NULL COMMENT '创建人',
                                          `modifier` varchar(100) DEFAULT NULL COMMENT '变更人',
                                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                          `status` int(11) NOT NULL DEFAULT '2' COMMENT '停用启用',
                                          `deleted` bigint(20) DEFAULT '0' COMMENT '删除标志位',
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `unique_biz` (`biz_type`, `deleted`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'tech重试配置表';

create table edi_biz_retry_record_202205_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202205_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202205_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202205_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202206_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202206_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202206_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202206_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202207_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202207_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202207_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202207_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202208_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202208_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202208_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202208_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202209_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202209_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202209_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202209_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202210_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202210_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202210_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202210_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202211_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202211_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202211_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202211_3 like edi_biz_retry_record;

create table edi_biz_retry_record_202212_0 like edi_biz_retry_record;
create table edi_biz_retry_record_202212_1 like edi_biz_retry_record;
create table edi_biz_retry_record_202212_2 like edi_biz_retry_record;
create table edi_biz_retry_record_202212_3 like edi_biz_retry_record;

create table edi_tech_biz_retry_record_202205_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202205_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202205_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202205_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202206_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202206_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202206_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202206_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202207_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202207_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202207_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202207_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202208_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202208_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202208_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202208_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202209_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202209_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202209_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202209_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202210_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202210_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202210_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202210_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202211_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202211_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202211_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202211_3 like edi_tech_biz_retry_record;

create table edi_tech_biz_retry_record_202212_0 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202212_1 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202212_2 like edi_tech_biz_retry_record;
create table edi_tech_biz_retry_record_202212_3 like edi_tech_biz_retry_record;
