CREATE TABLE `leaf_alloc` (
                              `biz_tag` varchar(128) NOT NULL DEFAULT '' COMMENT '主键id',
                              `max_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '已申请最大id',
                              `step` int(11) NOT NULL COMMENT '步长',
                              `description` varchar(256) DEFAULT NULL COMMENT '描述',
                              `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              PRIMARY KEY (`biz_tag`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COMMENT = 'leaf'
