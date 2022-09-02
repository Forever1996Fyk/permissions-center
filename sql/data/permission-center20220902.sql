-- MySQL dump 10.13  Distrib 8.0.29, for macos12.2 (x86_64)
--
-- Host: 127.0.0.1    Database: permission-center
-- ------------------------------------------------------
-- Server version	8.0.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cfg_encrypt`
--

DROP TABLE IF EXISTS `cfg_encrypt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cfg_encrypt` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `cipher_text` varchar(255) NOT NULL DEFAULT '' COMMENT '密文',
  `private_key` varchar(2048) NOT NULL DEFAULT '' COMMENT '私钥',
  `public_key` varchar(2048) NOT NULL DEFAULT '' COMMENT '公钥',
  `original` varchar(255) NOT NULL DEFAULT '' COMMENT '原文',
  `type` varchar(32) NOT NULL DEFAULT '' COMMENT '加密方式',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_cipher` (`cipher_text`,`original`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='加密数据配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cfg_encrypt`
--

LOCK TABLES `cfg_encrypt` WRITE;
/*!40000 ALTER TABLE `cfg_encrypt` DISABLE KEYS */;
/*!40000 ALTER TABLE `cfg_encrypt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cfg_thread_pool`
--

DROP TABLE IF EXISTS `cfg_thread_pool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cfg_thread_pool` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `keep_alive_time` bigint NOT NULL DEFAULT '0' COMMENT '存活时间',
  `queue_type` varchar(64) NOT NULL DEFAULT '' COMMENT '队列类型',
  `work_queue_size` int NOT NULL DEFAULT '0' COMMENT '存放带执行任务的队列大小',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cfg_thread_pool`
--

LOCK TABLES `cfg_thread_pool` WRITE;
/*!40000 ALTER TABLE `cfg_thread_pool` DISABLE KEYS */;
/*!40000 ALTER TABLE `cfg_thread_pool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_offline`
--

DROP TABLE IF EXISTS `message_offline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_offline` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg_id` bigint NOT NULL DEFAULT '0' COMMENT '消息id',
  `to_id` bigint NOT NULL DEFAULT '0' COMMENT '接收id',
  `msg_type` tinyint NOT NULL DEFAULT '0' COMMENT '消息类型',
  `msg_source` tinyint NOT NULL DEFAULT '0' COMMENT '消息来源',
  `format` tinyint NOT NULL DEFAULT '0' COMMENT '消息格式',
  `content` varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
  `msg_time` datetime NOT NULL COMMENT '消息时间',
  `has_read` tinyint NOT NULL DEFAULT '0' COMMENT '是否已读',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `to_id_idx` (`to_id`,`msg_type`,`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='离线消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_offline`
--

LOCK TABLES `message_offline` WRITE;
/*!40000 ALTER TABLE `message_offline` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_offline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_record`
--

DROP TABLE IF EXISTS `message_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message_record` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg_id` bigint NOT NULL DEFAULT '0' COMMENT '消息id',
  `from_id` bigint NOT NULL DEFAULT '0' COMMENT '发送id',
  `to_id` bigint NOT NULL DEFAULT '0' COMMENT '接收id',
  `format` tinyint NOT NULL DEFAULT '0' COMMENT '消息格式',
  `msg_type` tinyint NOT NULL DEFAULT '0' COMMENT '消息类型',
  `msg_source` tinyint NOT NULL DEFAULT '0' COMMENT '消息来源',
  `content` varchar(2048) NOT NULL DEFAULT '' COMMENT '消息内容',
  `msg_time` datetime NOT NULL COMMENT '消息时间',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `msg_uniq` (`from_id`,`msg_type`,`msg_source`,`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message_record`
--

LOCK TABLES `message_record` WRITE;
/*!40000 ALTER TABLE `message_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_account_device`
--

DROP TABLE IF EXISTS `msg_account_device`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_account_device` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `device_no` varchar(64) NOT NULL COMMENT '设备编号',
  `user_id` bigint NOT NULL COMMENT '接受人id',
  `device_type` tinyint NOT NULL DEFAULT '1' COMMENT '设备类型：1:安卓 2：ios',
  `phone` varchar(20) NOT NULL COMMENT '手机号码',
  `deleted` bigint DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '修改人',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_account_device` (`device_no`,`user_id`,`deleted`) USING BTREE COMMENT '用户id唯一',
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='设备用户关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_account_device`
--

LOCK TABLES `msg_account_device` WRITE;
/*!40000 ALTER TABLE `msg_account_device` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_account_device` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_app_push_log`
--

DROP TABLE IF EXISTS `msg_app_push_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_app_push_log` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_code` varchar(32) NOT NULL COMMENT '来源编号',
  `source_type` varchar(6) NOT NULL COMMENT '来源类型：MG/AP',
  `succeed` tinyint NOT NULL DEFAULT '1' COMMENT '友盟返回：成功失败',
  `result` varchar(64) NOT NULL DEFAULT '' COMMENT '友盟返回：成功失败',
  `device_no_list` text NOT NULL COMMENT '推送设备集合',
  `device_type` tinyint NOT NULL COMMENT '设备类型：1:安卓 2：ios',
  `deleted` bigint DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) DEFAULT '' COMMENT '修改人',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_source` (`source_code`,`succeed`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='app push记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_app_push_log`
--

LOCK TABLES `msg_app_push_log` WRITE;
/*!40000 ALTER TABLE `msg_app_push_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_app_push_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_event`
--

DROP TABLE IF EXISTS `msg_event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_event` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `event_code` varchar(32) NOT NULL COMMENT '事件编号',
  `event_name` varchar(64) NOT NULL COMMENT '事件名称',
  `source_system` varchar(32) NOT NULL COMMENT '所属系统',
  `jump_url` varchar(255) DEFAULT NULL COMMENT '跳转页面',
  `push_type` tinyint NOT NULL COMMENT '推送类型',
  `top_flag` tinyint NOT NULL COMMENT '是否app置顶',
  `secret_key` varchar(256) NOT NULL COMMENT '密钥',
  `reach_all_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否推送全部 1否 2是',
  `voice_code` varchar(255) DEFAULT NULL COMMENT '推送语音code',
  `deleted` bigint DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '修改人',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_msg_event` (`event_code`) USING BTREE COMMENT '消息编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='消息事件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_event`
--

LOCK TABLES `msg_event` WRITE;
/*!40000 ALTER TABLE `msg_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_info`
--

DROP TABLE IF EXISTS `msg_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `msg_code` varchar(32) NOT NULL COMMENT '消息编号',
  `msg_title` varchar(64) NOT NULL COMMENT '消息标题',
  `msg_type` tinyint NOT NULL COMMENT '消息类型',
  `push_type` tinyint NOT NULL COMMENT '推送方式',
  `push_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '推送时间',
  `push_time_type` tinyint NOT NULL DEFAULT '1' COMMENT '//推送时间类型 1:立即 2：自定义\n ',
  `msg_status` tinyint NOT NULL COMMENT '消息状态',
  `top_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否app置顶',
  `reach_all_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否推送全部 1否 2是',
  `content` longtext COMMENT '消息内容',
  `res_ids` varchar(255) DEFAULT NULL COMMENT '资源ids',
  `deleted` bigint DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) DEFAULT '' COMMENT '修改人',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_msg_no` (`msg_code`) USING BTREE COMMENT '消息编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_info`
--

LOCK TABLES `msg_info` WRITE;
/*!40000 ALTER TABLE `msg_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_reach`
--

DROP TABLE IF EXISTS `msg_reach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_reach` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `reach_type` tinyint NOT NULL COMMENT '目标类型',
  `reach_value` varchar(64) NOT NULL COMMENT '目标值',
  `source_id` bigint NOT NULL COMMENT '来源编号来源id(msg_info，mgs_event)',
  `source_id_type` tinyint NOT NULL COMMENT '来源id类型 1 msg_info 2 msg_event',
  `reach_name` varchar(64) NOT NULL COMMENT '对象名称：角色名，用户名等',
  `group_id` bigint DEFAULT NULL COMMENT '范围类型id 对应租户id 运营组id 角色id等',
  `superior_check_all` tinyint NOT NULL DEFAULT '1' COMMENT '上级是否全选 1否 2是',
  `group_name` varchar(255) DEFAULT NULL COMMENT '范围名称',
  `deleted` bigint DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '修改人',
  `version` int DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_msg_reach` (`source_id`,`reach_type`,`reach_value`,`deleted`) USING BTREE COMMENT '目标来源唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='消息目标表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_reach`
--

LOCK TABLES `msg_reach` WRITE;
/*!40000 ALTER TABLE `msg_reach` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_reach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `msg_target`
--

DROP TABLE IF EXISTS `msg_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `msg_target` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `receiver_account` varchar(32) NOT NULL COMMENT '接受人账号',
  `user_id` bigint NOT NULL COMMENT '接受人id',
  `msg_code` varchar(32) NOT NULL COMMENT '消息编码',
  `read_flag` tinyint NOT NULL DEFAULT '0' COMMENT '读取标识 0未读 1已读',
  `remind` tinyint NOT NULL DEFAULT '0' COMMENT '不在提醒 0no 1 yes',
  `msg_type` tinyint NOT NULL COMMENT '消息类型',
  `real_name` varchar(32) NOT NULL COMMENT '真实姓名',
  `top_flag` tinyint NOT NULL DEFAULT '1' COMMENT '是否app置顶',
  `msg_title` varchar(64) NOT NULL COMMENT '消息标题',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '删除标识 0:否 其他的删除',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(100) NOT NULL DEFAULT '' COMMENT '创建人',
  `modifier` varchar(100) NOT NULL DEFAULT '' COMMENT '修改人',
  `version` int NOT NULL DEFAULT '1' COMMENT '版本号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uniq_msg_target` (`user_id`,`msg_code`,`deleted`) USING BTREE COMMENT '消息编码唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息对象表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `msg_target`
--

LOCK TABLES `msg_target` WRITE;
/*!40000 ALTER TABLE `msg_target` DISABLE KEYS */;
/*!40000 ALTER TABLE `msg_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_BLOB_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Blob类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_BLOB_TRIGGERS`
--

LOCK TABLES `QRTZ_BLOB_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_BLOB_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CALENDARS`
--

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='日历信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CALENDARS`
--

LOCK TABLES `QRTZ_CALENDARS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CALENDARS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_CRON_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='Cron类型的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_CRON_TRIGGERS`
--

LOCK TABLES `QRTZ_CRON_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_CRON_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_FIRED_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) NOT NULL COMMENT '状态',
  `job_name` varchar(200) DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='已触发的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_FIRED_TRIGGERS`
--

LOCK TABLES `QRTZ_FIRED_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_FIRED_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_JOB_DETAILS`
--

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) NOT NULL COMMENT '任务组名',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='任务详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_JOB_DETAILS`
--

LOCK TABLES `QRTZ_JOB_DETAILS` WRITE;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_JOB_DETAILS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_LOCKS`
--

DROP TABLE IF EXISTS `QRTZ_LOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='存储的悲观锁信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_LOCKS`
--

LOCK TABLES `QRTZ_LOCKS` WRITE;
/*!40000 ALTER TABLE `QRTZ_LOCKS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_LOCKS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='暂停的触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_PAUSED_TRIGGER_GRPS`
--

LOCK TABLES `QRTZ_PAUSED_TRIGGER_GRPS` WRITE;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_PAUSED_TRIGGER_GRPS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SCHEDULER_STATE`
--

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='调度器状态表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SCHEDULER_STATE`
--

LOCK TABLES `QRTZ_SCHEDULER_STATE` WRITE;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SCHEDULER_STATE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPLE_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='简单触发器的信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPLE_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPLE_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPLE_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_SIMPROP_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='同步机制的行锁表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_SIMPROP_TRIGGERS`
--

LOCK TABLES `QRTZ_SIMPROP_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_SIMPROP_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `QRTZ_TRIGGERS`
--

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`),
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='触发器详细信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `QRTZ_TRIGGERS`
--

LOCK TABLES `QRTZ_TRIGGERS` WRITE;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` DISABLE KEYS */;
/*!40000 ALTER TABLE `QRTZ_TRIGGERS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_job`
--

DROP TABLE IF EXISTS `quartz_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quartz_job` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT '' COMMENT '任务组名',
  `job_params` varchar(1024) NOT NULL DEFAULT '' COMMENT '任务参数',
  `job_class_name` varchar(255) NOT NULL COMMENT '任务类路径',
  `cron_expression` varchar(64) NOT NULL COMMENT 'cron表达式',
  `execute_policy` tinyint NOT NULL DEFAULT '0' COMMENT '执行计划策略',
  `is_concurrent` tinyint NOT NULL DEFAULT '0' COMMENT '是否支持并发',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '任务状态',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='quartz任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_job`
--

LOCK TABLES `quartz_job` WRITE;
/*!40000 ALTER TABLE `quartz_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quartz_job_task`
--

DROP TABLE IF EXISTS `quartz_job_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quartz_job_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务id主键',
  `job_id` bigint NOT NULL COMMENT '定时调度id',
  `error_message` varchar(2048) NOT NULL DEFAULT '' COMMENT '错误信息',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(1: 执行成功, 2: 执行中, 3: 执行失败)',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='quartz任务调度任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quartz_job_task`
--

LOCK TABLES `quartz_job_task` WRITE;
/*!40000 ALTER TABLE `quartz_job_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `quartz_job_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_file`
--

DROP TABLE IF EXISTS `resource_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `res_id` bigint NOT NULL COMMENT '资源id',
  `owner_id` bigint NOT NULL DEFAULT '0' COMMENT '所属id',
  `position_type` tinyint NOT NULL COMMENT '资源位置类型',
  `is_public` tinyint NOT NULL DEFAULT '1' COMMENT '是否公开',
  `resource_biz_type` tinyint NOT NULL COMMENT '资源类型',
  `file_type` tinyint NOT NULL COMMENT '文件类型',
  `expire_time` timestamp NOT NULL COMMENT '过期时间',
  `resource_name` varchar(64) NOT NULL DEFAULT '' COMMENT '资源名称',
  `resource_url` varchar(500) NOT NULL DEFAULT '' COMMENT '资源地址',
  `resource_path` varchar(500) NOT NULL DEFAULT '' COMMENT '资源路径',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源文件表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_file`
--

LOCK TABLES `resource_file` WRITE;
/*!40000 ALTER TABLE `resource_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_meta_data`
--

DROP TABLE IF EXISTS `resource_meta_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `resource_meta_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `res_id` bigint NOT NULL COMMENT '资源id',
  `owner_id` bigint NOT NULL DEFAULT '0' COMMENT '所属id',
  `file_md5` varchar(255) NOT NULL DEFAULT '' COMMENT '文件md5',
  `file_sha1` varchar(255) NOT NULL DEFAULT '' COMMENT '文件sha1',
  `file_size` bigint NOT NULL DEFAULT '0' COMMENT '文件大小',
  `file_suffix` varchar(10) NOT NULL DEFAULT '' COMMENT '文件后缀',
  `origin_title` varchar(64) NOT NULL DEFAULT '' COMMENT '资源原标题',
  `resource_attr` varchar(1024) NOT NULL DEFAULT '' COMMENT '资源属性',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资源元数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_meta_data`
--

LOCK TABLES `resource_meta_data` WRITE;
/*!40000 ALTER TABLE `resource_meta_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource_meta_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_api`
--

DROP TABLE IF EXISTS `sys_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `api_code` varchar(32) NOT NULL DEFAULT '' COMMENT '接口编码',
  `api_name` varchar(50) NOT NULL DEFAULT '' COMMENT '接口名称',
  `api_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '接口类型',
  `read_or_write` tinyint NOT NULL DEFAULT '0' COMMENT '读写类型',
  `api_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '接口描述',
  `api_url` varchar(100) NOT NULL DEFAULT '' COMMENT '请求路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '账号状态(1:启用,2:停用)',
  `is_auth` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否认证',
  `is_open` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '是否公开',
  `changeable` tinyint NOT NULL DEFAULT '1' COMMENT '是否可变',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_api_code` (`api_code`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统接口表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_api`
--

LOCK TABLES `sys_api` WRITE;
/*!40000 ALTER TABLE `sys_api` DISABLE KEYS */;
INSERT INTO `sys_api` VALUES (1,'userAdd','新增用户',1,2,'新增用户','/user/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-07-28 16:14:33','2022-08-08 10:14:08',0,'',0),(2,'getUserInfo','获取当前登录人信息',1,1,'获取当前登录人信息','/user/getUserInfoVo',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-01 14:55:51','2022-08-08 10:14:08',0,'',0),(3,'apiAdd','新增api',1,2,'新增api','/api/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-02 08:16:27','2022-08-08 10:14:08',0,'',0),(4,'listUserRouter','获取用户菜单路由',1,1,'获取用户菜单路由','/router/listRouterVoByUserId',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-02 08:40:38','2022-08-08 10:14:08',0,'',0),(5,'userUpdate','更新用户',1,2,'新增用户','/user/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-02 08:51:01','2022-08-08 10:14:08',0,'',0),(6,'selectDictLabelList','选择字典标签集合',1,1,'选择字典标签集合','/dictDetail/selectDictLabelList/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-04 06:03:20','2022-08-08 10:14:08',0,'',0),(8,'findUserById','根据id查询用户信息',1,1,'根据id查询用户信息','/user/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-04 09:06:41','2022-08-08 10:14:08',0,'',0),(10,'deleteUser','删除用户',1,2,'删除用户','/user/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-06 03:40:17','2022-08-08 10:14:08',0,'',0),(11,'pageApiList','分页查询接口列表',1,1,'分页查询接口列表','/api/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 02:05:42','2022-08-08 10:14:08',0,'',0),(12,'apiUpdate','更新接口',1,2,'更新接口','/api/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 02:05:42','2022-08-08 10:14:08',0,'',0),(13,'apiDelete','删除接口',1,2,'删除接口','/api/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 02:05:42','2022-08-08 10:14:08',0,'',0),(14,'findApiById','根据id查询接口',1,1,'根据id查询接口','/api/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 02:05:42','2022-08-08 10:14:08',0,'',0),(15,'pageUserList','分页查询用户列表',1,1,'分页查询用户列表','/user/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 02:06:45','2022-08-09 01:41:18',8,'',0),(16,'testApi','测试接口',1,1,'测试接口','/test/api',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-08 06:06:52','2022-08-08 10:14:08',6,'',16),(17,'changeApiStatus','变更api状态',1,2,'变更api状态','/api/changeApiStatus/**',1,1,1,0,'','SYSTEM','SYSTEM','2022-08-08 08:12:30','2022-08-08 10:21:20',4,'',0),(18,'changeApiAuth','变更api权限',1,2,'变更api状态','/api/changeApiAuth/**',1,1,1,0,'','SYSTEM','SYSTEM','2022-08-08 08:12:30','2022-08-09 01:37:14',3,'',0),(19,'changeApiOpen','变更api开放类型',1,2,'变更api开放类型','/api/changeApiOpen/**',1,1,1,0,'','SYSTEM','SYSTEM','2022-08-08 08:12:30','2022-08-09 01:37:20',1,'',0),(20,'menuAdd','新增菜单',1,2,'新增菜单','/menu/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:19','2022-08-09 06:04:19',0,'',0),(21,'menuUpdate','更新菜单',1,2,'更新菜单','/menu/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:19','2022-08-09 06:04:19',0,'',0),(22,'menuDelete','删除菜单',1,2,'删除菜单','/menu/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:19','2022-08-09 06:04:19',0,'',0),(23,'findMenuById','根据id查询菜单信息',1,1,'根据id查询菜单信息','/menu/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:19','2022-08-09 06:04:19',0,'',0),(24,'listSysMenuTree','获取菜单树集合',1,1,'分页查询菜单','/menu/listSysMenuTree',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:19','2022-08-09 06:04:19',0,'',0),(25,'listMenuSelectTree','获取菜单选择树集合',1,1,'获取菜单选择树集合','/menu/listMenuSelectTree',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-23 08:46:43',0,'',0),(26,'roleAdd','新增角色',1,2,'新增角色','/role/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 09:08:37','2022-08-23 08:45:40',0,'',0),(27,'roleUpdate','更新角色',1,2,'更新角色','/role/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 09:08:37','2022-08-23 08:45:40',0,'',0),(28,'roleDelete','删除角色',1,2,'删除角色','/role/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 09:08:37','2022-08-23 08:45:40',0,'',0),(29,'findRoleById','根据id查询角色信息',1,1,'根据id查询角色信息','/role/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 09:08:37','2022-08-23 08:45:40',0,'',0),(30,'pageRoleList','分页查询角色列表',1,1,'分页查询角色列表','/role/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-09 09:08:37','2022-08-23 08:35:47',0,'',0),(31,'setUserMenu','设置用户菜单',1,2,'设置用户菜单','/user/setSysMenuForUser',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-11 08:40:12','2022-08-23 08:45:40',0,'',0),(32,'setUserRole','设置用户角色',1,2,'设置用户角色','/user/setSysUserRole',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-12 07:14:22','2022-08-23 08:50:55',0,'',0),(33,'listSelectUserRole','查询选择用户已有角色',1,1,'查询选择用户已有角色','/user/listSelectUserRole/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-12 07:14:22','2022-08-23 08:45:40',0,'',0),(34,'pageSelectUserRole','根据用户id分页获取用户的角色信息',1,1,'根据用户id分页获取用户的角色信息','/user/pageSelectUserRole',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 04:05:39','2022-08-23 08:45:40',0,'',0),(35,'setSysApiForUser','设置用户接口',1,2,'设置用户接口','/user/setSysApiForUser',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 04:08:23','2022-08-23 08:48:20',0,'',0),(36,'pageSelectUserApi','根据用户id分页获取用户的接口信息',1,1,'根据用户id分页获取用户的接口信息','/user/pageSelectUserApi',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 04:15:49','2022-08-23 08:45:40',0,'',0),(37,'cancelSysApiForUser','取消用户接口权限',1,2,'取消用户接口权限','/user/cancelSysApiForUser',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 06:08:01','2022-08-23 08:45:40',0,'',0),(38,'setSysMenuForRole','设置角色菜单',1,2,'设置角色菜单','/role/setSysMenuForRole',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 07:38:32','2022-08-23 08:45:40',0,'',0),(39,'setSysApiForRole','设置角色api',1,2,'设置角色api','/role/setSysApiForRole',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 07:38:32','2022-08-23 08:45:40',0,'',0),(40,'cancelSysApiForRole','取消角色api',1,2,'取消角色api','/role/cancelSysApiForRole',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 07:38:32','2022-08-23 08:45:40',0,'',0),(41,'pageSelectRoleApi','根据角色编码分页获取角色的api信息',1,1,'根据角色编码分页获取角色的api信息','/role/pageSelectRoleApi',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 08:06:05','2022-08-23 08:45:40',0,'',0),(42,'setSysApiForMenu','设置菜单api',1,2,'设置菜单api','/menu/setSysApiForMenu',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 10:06:52','2022-08-23 08:45:40',0,'',0),(43,'cancelSysApiForMenu','取消菜单api',1,2,'取消菜单api','/menu/cancelSysApiForMenu',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 10:06:52','2022-08-23 08:45:40',0,'',0),(44,'pageSelectMenuApi','根据菜单编码分页获取菜单的api信息',1,1,'根据菜单编码分页获取菜单的api信息','/menu/pageSelectMenuApi',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-15 10:06:52','2022-08-23 08:45:40',0,'',0),(45,'pageSysApiList','分页查询系统api集合',1,1,'分页查询系统api集合','/api/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-16 02:44:26','2022-08-16 03:44:33',0,'',0),(46,'listSysMenuButtonPermissionVo','查询按钮权限集合',1,1,'查询按钮权限集合','/menu/listSysMenuButtonPermissionVo',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-16 08:09:27','2022-08-23 08:34:19',0,'',0),(47,'cs','测试',1,1,'测试','cs',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-16 09:40:30','2022-08-17 07:18:02',1,'',47),(48,'listSysDeptTree','查询部门树列表',1,1,'查询菜单树列表','/dept/listSysDeptTree',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:24:52','2022-08-18 07:25:28',1,'',0),(49,'deptAdd','添加部门',1,2,'添加部门','/dept/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:25:18','2022-08-18 07:25:18',0,'',0),(50,'deptUpdate','更新部门',1,2,'更新部门','/dept/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:25:53','2022-08-18 07:25:53',0,'',0),(51,'deptDelete','删除部门',1,2,'删除部门','/dept/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:26:18','2022-08-18 07:26:18',0,'',0),(52,'findDeptById','根据id查询部门',1,1,'根据id查询部门','/dept/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:26:54','2022-08-18 07:26:54',0,'',0),(53,'listDeptSelectTree','获取部门树形选择结构列表',1,1,'获取部门树形选择结构列表','/dept/listDeptSelectTree',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 07:31:19','2022-08-18 07:31:19',0,'',0),(54,'dictAdd','添加字典',1,2,'添加字典','/dict/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 10:34:24','2022-08-23 08:45:40',1,'',0),(55,'dictUpdate','更新字典',1,2,'更新字典','/dict/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 10:34:48','2022-08-18 10:34:48',0,'',0),(56,'dictDelete','删除字典',1,2,'删除字典','/dict/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 10:35:30','2022-08-18 10:35:30',0,'',0),(57,'findDictById','根据id获取字典',1,1,'根据id获取字典','/dict/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 10:35:56','2022-08-18 10:35:56',0,'',0),(58,'pageSysDictList','分页查询字典',1,1,'分页查询字典','/dict/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-18 10:36:20','2022-08-18 10:36:20',0,'',0),(59,'pageDictDetailList','分页查询字典详情',1,1,'分页查询字典详情','/dictDetail/page',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-19 10:18:51','2022-08-19 10:18:51',0,'',0),(60,'dictDetailAdd','添加字典详情',1,2,'添加字典详情','/dictDetail/add',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-19 10:19:27','2022-08-19 10:19:27',0,'',0),(61,'dictDetailUpdate','更新字典详情',1,2,'更新字典详情','/dictDetail/update',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-19 10:20:36','2022-08-19 10:20:36',0,'',0),(62,'findDictDetailById','根据id查询字典详情',1,1,'根据id查询字典详情','/dictDetail/findById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-19 10:22:14','2022-08-19 10:22:14',0,'',0),(63,'dictDetailDelete','删除字典详情',1,2,'删除字典详情','/dictDetail/deleteById/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-19 10:22:43','2022-08-19 10:22:43',0,'',0),(64,'findUserPersonalInfo','获取用户个人信息',1,1,'获取用户个人信息','/user/findUserPersonalInfo/*',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-21 08:53:57','2022-08-21 08:53:57',0,'',0),(65,'updateSysUserIntro','更新用户个人简介',1,2,'更新用户个人简介','/intro/updateSysUserIntro',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-22 03:11:54','2022-08-22 03:11:54',0,'',0),(66,'updatePersonalBaseUserInfo','更新个人中心基本信息',1,2,'更新个人中心基本信息','/user/updatePersonalBaseUserInfo',1,1,1,1,'','SYSTEM','SYSTEM','2022-08-22 03:12:25','2022-08-22 03:12:25',0,'',0),(67,'uploadUserAvatar','上传用户头像',1,2,'上传用户头像','/user/uploadUserAvatar',1,0,1,1,'','SYSTEM','SYSTEM','2022-08-22 07:17:22','2022-08-22 09:39:38',1,'',0);
/*!40000 ALTER TABLE `sys_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dept_code` varchar(16) NOT NULL DEFAULT '' COMMENT '部门编码',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父id',
  `parent_dept_code` varchar(16) NOT NULL DEFAULT '' COMMENT '父部门编码',
  `name` varchar(32) NOT NULL DEFAULT '' COMMENT '权部门名称',
  `dept_order` tinyint NOT NULL DEFAULT '0' COMMENT '部门序号',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_dept_code` (`dept_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
INSERT INTO `sys_dept` VALUES (2,'master',0,'','总公司',1,'','SYSTEM','SYSTEM','2022-08-18 07:46:27','2022-08-18 07:46:27',0,'',0),(3,'hefeiBranch',2,'','合肥分公司',1,'','SYSTEM','SYSTEM','2022-08-18 07:49:01','2022-08-18 07:49:01',0,'',0),(4,'hefeiTechnical',3,'','技术部',1,'','SYSTEM','SYSTEM','2022-08-18 07:51:44','2022-08-18 07:51:44',0,'',0);
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept_relation`
--

DROP TABLE IF EXISTS `sys_dept_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept_relation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ancestor_code` varchar(16) NOT NULL DEFAULT '' COMMENT '父部门编码',
  `descendant_code` varchar(16) NOT NULL DEFAULT '' COMMENT '子部门编码',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_code` (`ancestor_code`,`descendant_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统部门关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept_relation`
--

LOCK TABLES `sys_dept_relation` WRITE;
/*!40000 ALTER TABLE `sys_dept_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict`
--

DROP TABLE IF EXISTS `sys_dict`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dict_code` varchar(16) NOT NULL DEFAULT '' COMMENT '字典编码',
  `dict_name` varchar(64) NOT NULL DEFAULT '' COMMENT '字典名称',
  `type` tinyint NOT NULL DEFAULT '1' COMMENT '类型:系统/业务',
  `is_change` tinyint NOT NULL DEFAULT '1' COMMENT '是否可变',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_code` (`dict_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict`
--

LOCK TABLES `sys_dict` WRITE;
/*!40000 ALTER TABLE `sys_dict` DISABLE KEYS */;
INSERT INTO `sys_dict` VALUES (1,'sex','性别',1,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(2,'userStatus','用户状态',1,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(3,'readOrWrite','读写枚举',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:49','2022-08-08 03:32:49',0),(4,'apiType','接口类型',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:49','2022-08-08 03:32:49',0),(5,'menuType','菜单类型',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:48','2022-08-09 06:11:48',0),(6,'openType','菜单打开类型',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:48','2022-08-09 06:11:48',0),(7,'yesOrNo','是否类型',1,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:00','2022-08-15 06:46:00',0),(8,'dataScope','数据权限',1,0,'','SYSTEM','SYSTEM','2022-08-18 08:43:13','2022-08-18 08:43:13',0),(9,'test','测试',1,1,'','SYSTEM','SYSTEM','2022-08-18 10:47:04','2022-08-19 03:11:09',9);
/*!40000 ALTER TABLE `sys_dict` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_detail`
--

DROP TABLE IF EXISTS `sys_dict_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `dict_code` varchar(16) NOT NULL DEFAULT '' COMMENT '字典编码',
  `dict_detail_code` varchar(32) NOT NULL DEFAULT '' COMMENT '字典详情编码',
  `dict_detail_desc` varchar(64) NOT NULL DEFAULT '' COMMENT '字典详情名称',
  `detail_order` tinyint NOT NULL DEFAULT '0' COMMENT '排序',
  `is_edit` tinyint NOT NULL DEFAULT '1' COMMENT '是否可编辑',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_code` (`dict_code`,`dict_detail_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统字典详情表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_detail`
--

LOCK TABLES `sys_dict_detail` WRITE;
/*!40000 ALTER TABLE `sys_dict_detail` DISABLE KEYS */;
INSERT INTO `sys_dict_detail` VALUES (1,'sex','1','男',1,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-19 10:43:49',0),(2,'sex','2','女',2,0,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(3,'userStatus','1',' 正常',1,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(4,'userStatus','2',' 禁用',2,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(5,'userStatus','3',' 注销',3,1,'','SYSTEM','SYSTEM','2022-08-04 06:25:04','2022-08-04 06:25:04',0),(6,'apiType','1','web端系统类型',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0),(7,'apiType','2','web端业务类型',2,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0),(8,'apiType','3','移动端系统类型',3,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0),(9,'apiType','4','移动端业务类型',4,0,'','SYSTEM','SYSTEM','2022-08-08 03:32:53','2022-08-08 03:32:53',0),(10,'readOrWrite','1','读',1,0,'','SYSTEM','SYSTEM','2022-08-08 03:33:01','2022-08-08 03:33:01',0),(11,'readOrWrite','2','写',2,0,'','SYSTEM','SYSTEM','2022-08-08 03:33:01','2022-08-08 03:33:01',0),(12,'menuType','1','目录',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0),(13,'menuType','2','菜单',2,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0),(14,'menuType','3','按钮',3,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0),(15,'openType','1','页面内',1,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0),(16,'openType','2','外部链接',2,0,'','SYSTEM','SYSTEM','2022-08-09 06:11:53','2022-08-09 06:11:53',0),(17,'yesOrNo','1','是',1,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:23','2022-08-15 06:46:23',0),(18,'yesOrNo','0','否',2,0,'','SYSTEM','SYSTEM','2022-08-15 06:46:23','2022-08-15 06:46:23',0),(19,'dataScope','1','全部',1,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0),(20,'dataScope','2','本级',2,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0),(21,'dataScope','3','下级',3,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0),(22,'dataScope','4','本级及下级',4,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0),(23,'dataScope','5','本人',5,0,'','SYSTEM','SYSTEM','2022-08-18 08:44:45','2022-08-18 08:44:45',0),(24,'sex','3','人妖',3,1,'','SYSTEM','SYSTEM','2022-08-19 10:44:53','2022-08-19 10:44:58',24);
/*!40000 ALTER TABLE `sys_dict_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_import_export_task`
--

DROP TABLE IF EXISTS `sys_import_export_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_import_export_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `task_no` varchar(64) NOT NULL DEFAULT '' COMMENT '任务号',
  `status` int NOT NULL COMMENT '任务状态 1 - 待处理 2 - 处理中 3 - 处理成功 4 - 处理失败 5 - 部分成功',
  `param` mediumtext COMMENT '参数',
  `file_name` varchar(256) DEFAULT NULL COMMENT '文件名称',
  `file_url` varchar(256) DEFAULT NULL COMMENT '文件地址',
  `ref_file_name` varchar(256) DEFAULT NULL COMMENT '关联文件名称',
  `ref_file_url` varchar(256) DEFAULT NULL COMMENT '关联文件地址',
  `task_type` int NOT NULL COMMENT '任务类型 1 - 导入， 2 - 导出',
  `biz_type` varchar(64) NOT NULL COMMENT '详细业务类型',
  `error_reason` text COMMENT '错误原因',
  `begin_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `finish_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `env` varchar(64) NOT NULL DEFAULT '' COMMENT '环境标示',
  `creator` varchar(64) NOT NULL COMMENT '操作人',
  `creator_id` bigint NOT NULL COMMENT '操作人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  PRIMARY KEY (`id`),
  KEY `idx_created_time` (`create_time`),
  KEY `idx_task_no` (`task_no`),
  KEY `idx_task_type` (`task_type`,`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='导入导出任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_import_export_task`
--

LOCK TABLES `sys_import_export_task` WRITE;
/*!40000 ALTER TABLE `sys_import_export_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_import_export_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_import_template_info`
--

DROP TABLE IF EXISTS `sys_import_template_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_import_template_info` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `template_name` varchar(64) NOT NULL DEFAULT '' COMMENT '模板名称',
  `template_url` varchar(64) NOT NULL DEFAULT '' COMMENT '模板路径',
  `biz_type` varchar(64) NOT NULL COMMENT '详细业务类型',
  `status` int NOT NULL DEFAULT '1' COMMENT '模板状态 1 - 启用 2 - 禁用',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_biz_type` (`biz_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='导入模板信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_import_template_info`
--

LOCK TABLES `sys_import_template_info` WRITE;
/*!40000 ALTER TABLE `sys_import_template_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_import_template_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单id',
  `menu_code` varchar(16) NOT NULL DEFAULT '' COMMENT '菜单编码',
  `parent_menu_code` varchar(16) NOT NULL DEFAULT '' COMMENT '父菜单编码',
  `menu_name` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `menu_type` tinyint NOT NULL DEFAULT '1' COMMENT '菜单类型',
  `menu_order` tinyint NOT NULL DEFAULT '0' COMMENT '菜单序号',
  `component` varchar(100) NOT NULL DEFAULT '' COMMENT '组件路径',
  `menu_path` varchar(100) NOT NULL DEFAULT '' COMMENT '菜单url',
  `open_type` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '打开方式(1:页面内,2:外链)',
  `active_menu` varchar(500) NOT NULL DEFAULT '' COMMENT '激活当前页面的菜单路径',
  `dynamic_new_tab` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否动态创建新的tab页',
  `hidden` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '是否隐藏',
  `menu_icon` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单图标',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_menu_code` (`menu_code`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (1,0,'BaseData','','基础数据',1,1,'Layout','/baseData',1,'',1,0,'settings-line','','SYSTEM','SYSTEM','2022-08-02 09:09:08','2022-08-09 08:52:24',5,'',0),(2,1,'Permission','','权限中心',1,1,'','permission',1,'',1,0,'shield-keyhole-line','','SYSTEM','SYSTEM','2022-08-02 09:09:08','2022-08-18 03:15:09',6,'',0),(3,2,'SysUser','','用户管理',2,1,'@/views/baseData/permission/sysUser/index','sysUser',1,'',1,0,'user-line','','SYSTEM','SYSTEM','2022-08-02 09:09:08','2022-08-15 09:11:05',1,'',0),(4,2,'SysApi','','接口管理',2,2,'@/views/baseData/permission/sysApi/index','sysApi',1,'',1,0,'flashlight-line','','SYSTEM','SYSTEM','2022-08-08 02:05:36','2022-08-15 09:11:10',1,'',0),(5,2,'SysRole','','角色管理',2,3,'@/views/baseData/permission/sysRole/index','sysRole',1,'',1,0,'account-pin-circle-line','','SYSTEM','SYSTEM','2022-08-08 02:05:36','2022-08-15 09:11:13',1,'',0),(6,2,'SysMenu','','菜单管理',2,4,'@/views/baseData/permission/sysMenu/index','sysMenu',1,'',1,0,'menu-fill','','SYSTEM','SYSTEM','2022-08-08 02:05:36','2022-08-15 09:11:18',1,'',0),(7,0,'Workflow','','工作流',1,1,'Layout','/workflow',1,'',1,0,'settings-line','','SYSTEM','SYSTEM','2022-08-09 02:47:00','2022-08-18 03:15:21',1,'',7),(8,1,'Log','','日志管理',1,1,'Layout','log',1,'',1,0,'settings-line','','SYSTEM','SYSTEM','2022-08-09 02:47:55','2022-08-09 06:47:05',1,'',8),(9,7,'ProcessModel','','流程模型管理',2,1,'Layout','processModel',1,'',1,0,'function-line','','SYSTEM','SYSTEM','2022-08-09 06:27:52','2022-08-09 06:36:03',1,'',9),(11,2,'AuthDetail','','权限详情页',2,1,'@/views/baseData/permission/sysUser/authDetail','sysUser/authDetail',1,'/baseData/permission/sysUser/index',1,1,'user-line','','SYSTEM','SYSTEM','2022-08-11 03:20:33','2022-08-16 03:41:49',1,'',0),(12,2,'RoleAutDetail','','角色权限详情',2,6,'@/views/baseData/permission/sysRole/authDetail','sysRole/authDetail',1,'/baseData/permission/sysRole/index',1,1,'account-circle-line','','SYSTEM','SYSTEM','2022-08-15 06:50:46','2022-08-15 06:51:25',0,'',0),(13,2,'MenuAuthDetail','','菜单权限详情',2,7,'@/views/baseData/permission/sysMenu/authDetail','sysMenu/authDetail',1,'/baseData/permission/sysMenu/index',1,1,'menu-2-fill','','SYSTEM','SYSTEM','2022-08-15 09:21:05','2022-08-15 09:21:19',0,'',0),(14,3,'userAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:21:31','2022-08-16 02:34:23',1,'',0),(15,3,'userEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:21:53','2022-08-16 02:34:23',1,'',0),(16,3,'userDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:23:03','2022-08-16 02:34:23',1,'',0),(17,3,'isDisable','','是否禁用',3,4,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:33:33','2022-08-16 02:34:23',0,'',0),(18,4,'apiAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:36:24','2022-08-16 02:36:24',0,'',0),(19,4,'apiEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:37:06','2022-08-16 02:37:06',0,'',0),(20,4,'apiDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:37:24','2022-08-16 02:37:24',0,'',0),(21,4,'isOpen','','是否公开',3,4,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:37:48','2022-08-16 02:37:48',0,'',0),(22,4,'isAuth','','是否认证',3,5,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:38:01','2022-08-16 02:38:01',0,'',0),(23,4,'isDisabled','','是否禁用',3,6,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:38:24','2022-08-16 02:38:24',0,'',0),(24,5,'roleAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:47:38','2022-08-16 02:47:38',0,'',0),(25,5,'roleEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:47:54','2022-08-16 02:47:54',0,'',0),(26,5,'roleDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:48:09','2022-08-16 02:48:09',0,'',0),(27,5,'setRoleAuth','','设置权限',3,4,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:48:36','2022-08-16 02:48:36',0,'',0),(28,6,'menuAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:51:06','2022-08-16 02:51:06',0,'',0),(29,6,'menuEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:51:22','2022-08-16 02:51:22',0,'',0),(30,6,'menuDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:51:35','2022-08-16 02:51:35',0,'',0),(31,6,'setMenuAuth','','设置权限',3,4,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 02:51:50','2022-08-16 02:51:50',0,'',0),(32,3,'setUserAuth','','设置权限',3,5,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-16 03:12:29','2022-08-16 03:25:03',0,'',0),(33,1,'Tenant','','租户中心',1,2,'Layout','/tenant',1,'',0,0,'admin-line','','SYSTEM','SYSTEM','2022-08-18 03:18:06','2022-08-18 03:18:45',1,'',0),(34,2,'SysDept','','部门管理',2,5,'@/views/baseData/permission/sysDept/index','sysDept',1,'',0,0,'account-box-line','','SYSTEM','SYSTEM','2022-08-18 06:03:27','2022-08-18 06:03:27',0,'',0),(35,34,'deptAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 07:23:47','2022-08-18 07:23:47',0,'',0),(36,34,'deptEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 07:24:00','2022-08-18 07:24:00',0,'',0),(37,34,'deptDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 07:24:13','2022-08-18 07:24:13',0,'',0),(38,3,'userProfile','','个人中心',2,6,'@/views/baseData/permission/sysUser/userProfile','sysUser/userProfile',1,'',1,1,'file-user-line','','SYSTEM','SYSTEM','2022-08-18 09:11:25','2022-08-18 09:15:28',1,'',38),(39,1,'personalCenter','','个人中心',2,3,'@/views/baseData/personalCenter/index','personalCenter',1,'',1,1,'file-user-line','','SYSTEM','SYSTEM','2022-08-18 09:18:08','2022-08-18 09:18:08',0,'',0),(40,1,'SysDict','','字典管理',2,4,'','dict',1,'',0,0,'book-2-line','','SYSTEM','SYSTEM','2022-08-18 09:25:39','2022-08-19 09:55:53',3,'',0),(41,46,'dictAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 09:25:56','2022-08-19 09:53:10',0,'',0),(42,46,'dictEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 09:28:00','2022-08-19 09:53:10',0,'',0),(43,46,'dictDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 09:28:13','2022-08-19 09:53:10',0,'',0),(44,46,'dictDetail','','详情',3,4,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-18 09:28:38','2022-08-19 09:53:10',0,'',0),(45,40,'SysDictDetail','','字典详情',2,5,'@/views/baseData/dict/detail','detail',1,'/baseData/dict/index',1,1,'book-2-line','','SYSTEM','SYSTEM','2022-08-18 10:09:06','2022-08-19 09:56:55',3,'',0),(46,40,'dictList','','字典列表',2,1,'@/views/baseData/dict/index','index',1,'',0,0,'book-3-line','','SYSTEM','SYSTEM','2022-08-19 09:51:20','2022-08-19 09:56:17',2,'',0),(47,45,'dictDetailAdd','','添加',3,1,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-19 10:17:41','2022-08-19 10:17:41',0,'',0),(48,45,'dictDetailEdit','','编辑',3,2,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-19 10:17:58','2022-08-19 10:17:58',0,'',0),(49,45,'dictDetailDelete','','删除',3,3,'','',1,'',0,1,'','','SYSTEM','SYSTEM','2022-08-19 10:18:09','2022-08-19 10:18:09',0,'',0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_api`
--

DROP TABLE IF EXISTS `sys_menu_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `menu_id` bigint NOT NULL DEFAULT '0' COMMENT '菜单id',
  `menu_code` varchar(32) NOT NULL DEFAULT '' COMMENT '菜单编码',
  `api_code` varchar(32) NOT NULL COMMENT '接口编码',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_menu_api` (`menu_id`,`api_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_api`
--

LOCK TABLES `sys_menu_api` WRITE;
/*!40000 ALTER TABLE `sys_menu_api` DISABLE KEYS */;
INSERT INTO `sys_menu_api` VALUES (1,13,'MenuAuthDetail','pageSelectMenuApi',1,'SYSTEM','SYSTEM','2022-08-15 10:26:02','2022-08-16 08:11:23',0,'',1),(2,13,'MenuAuthDetail','pageSelectMenuApi',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(3,13,'MenuAuthDetail','setSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(4,13,'MenuAuthDetail','cancelSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(5,13,'MenuAuthDetail','pageSelectRoleApi',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(6,13,'MenuAuthDetail','setSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(7,13,'MenuAuthDetail','cancelSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(8,13,'MenuAuthDetail','setSysMenuForRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(9,13,'MenuAuthDetail','cancelSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(10,13,'MenuAuthDetail','pageSelectUserApi',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(11,13,'MenuAuthDetail','setSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 10:26:13','2022-08-16 08:11:23',0,'',0),(12,13,'MenuAuthDetail','pageSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',12),(13,13,'MenuAuthDetail','setUserRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',13),(14,13,'MenuAuthDetail','listSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',14),(15,13,'MenuAuthDetail','setUserMenu',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',15),(16,13,'MenuAuthDetail','roleAdd',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',16),(17,13,'MenuAuthDetail','roleUpdate',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',17),(18,13,'MenuAuthDetail','roleDelete',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',18),(19,13,'MenuAuthDetail','findRoleById',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',19),(20,13,'MenuAuthDetail','pageRoleList',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',20),(21,13,'MenuAuthDetail','listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-15 10:26:19','2022-08-16 08:11:23',0,'',21),(22,3,'SysUser','pageUserList',1,'SYSTEM','SYSTEM','2022-08-16 02:25:11','2022-08-16 08:11:23',0,'',0),(23,3,'SysUser','userUpdate',1,'SYSTEM','SYSTEM','2022-08-16 02:25:35','2022-08-16 08:11:23',0,'',23),(24,3,'SysUser','userAdd',1,'SYSTEM','SYSTEM','2022-08-16 02:25:35','2022-08-16 08:11:23',0,'',24),(25,14,'userAdd','userAdd',1,'SYSTEM','SYSTEM','2022-08-16 02:26:29','2022-08-16 08:11:23',0,'',0),(26,14,'userAdd','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:26:36','2022-08-16 08:11:23',0,'',0),(27,15,'userEdit','userUpdate',1,'SYSTEM','SYSTEM','2022-08-16 02:26:48','2022-08-16 08:11:23',0,'',0),(28,15,'userEdit','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:26:54','2022-08-16 08:11:23',0,'',0),(29,4,'SysApi','pageSysApiList',1,'SYSTEM','SYSTEM','2022-08-16 02:44:40','2022-08-16 08:11:23',0,'',0),(30,18,'apiAdd','findApiById',1,'SYSTEM','SYSTEM','2022-08-16 02:45:11','2022-08-16 08:11:23',0,'',30),(31,18,'apiAdd','apiAdd',1,'SYSTEM','SYSTEM','2022-08-16 02:45:25','2022-08-16 08:11:23',0,'',0),(32,18,'apiAdd','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:45:34','2022-08-16 08:11:23',0,'',0),(33,19,'apiEdit','apiUpdate',1,'SYSTEM','SYSTEM','2022-08-16 02:46:05','2022-08-16 08:11:23',0,'',0),(34,19,'apiEdit','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:46:16','2022-08-16 08:11:23',0,'',0),(35,20,'apiDelete','apiDelete',1,'SYSTEM','SYSTEM','2022-08-16 02:46:31','2022-08-16 08:11:23',0,'',0),(36,21,'isOpen','changeApiOpen',1,'SYSTEM','SYSTEM','2022-08-16 02:46:49','2022-08-16 08:11:23',0,'',0),(37,22,'isAuth','changeApiAuth',1,'SYSTEM','SYSTEM','2022-08-16 02:47:11','2022-08-16 08:11:23',0,'',0),(38,23,'isDisabled','changeApiStatus',1,'SYSTEM','SYSTEM','2022-08-16 02:47:22','2022-08-16 08:11:23',0,'',0),(39,5,'SysRole','pageRoleList',1,'SYSTEM','SYSTEM','2022-08-16 02:48:53','2022-08-16 08:11:23',0,'',0),(40,24,'roleAdd','roleAdd',1,'SYSTEM','SYSTEM','2022-08-16 02:49:10','2022-08-16 08:11:23',0,'',0),(41,25,'roleEdit','roleUpdate',1,'SYSTEM','SYSTEM','2022-08-16 02:49:20','2022-08-16 08:11:23',0,'',0),(42,26,'roleDelete','roleDelete',1,'SYSTEM','SYSTEM','2022-08-16 02:49:30','2022-08-16 08:11:23',0,'',0),(43,27,'setRoleAuth','setSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-16 02:49:53','2022-08-16 08:11:23',0,'',0),(44,27,'setRoleAuth','cancelSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-16 02:49:56','2022-08-16 08:11:23',0,'',0),(45,27,'setRoleAuth','pageSelectRoleApi',1,'SYSTEM','SYSTEM','2022-08-16 02:50:05','2022-08-16 08:11:23',0,'',0),(46,27,'setRoleAuth','setSysMenuForRole',1,'SYSTEM','SYSTEM','2022-08-16 02:50:06','2022-08-16 08:11:23',0,'',0),(47,25,'roleEdit','findRoleById',1,'SYSTEM','SYSTEM','2022-08-16 02:50:50','2022-08-16 08:11:23',0,'',0),(48,6,'SysMenu','listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-16 02:52:09','2022-08-16 08:11:23',0,'',48),(49,6,'SysMenu','listSysMenuTree',1,'SYSTEM','SYSTEM','2022-08-16 02:53:00','2022-08-16 08:11:23',0,'',0),(50,28,'menuAdd','listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-16 02:53:28','2022-08-16 08:11:23',0,'',0),(51,28,'menuAdd','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:53:33','2022-08-16 08:11:23',0,'',0),(52,28,'menuAdd','menuAdd',1,'SYSTEM','SYSTEM','2022-08-16 02:53:39','2022-08-16 08:11:23',0,'',0),(53,29,'menuEdit','menuUpdate',1,'SYSTEM','SYSTEM','2022-08-16 02:53:56','2022-08-16 08:11:23',0,'',0),(54,29,'menuEdit','listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-16 02:53:58','2022-08-16 08:11:23',0,'',0),(55,29,'menuEdit','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 02:54:06','2022-08-16 08:11:23',0,'',0),(56,30,'menuDelete','menuDelete',1,'SYSTEM','SYSTEM','2022-08-16 02:54:17','2022-08-16 08:11:23',0,'',0),(57,31,'setMenuAuth','pageSelectMenuApi',1,'SYSTEM','SYSTEM','2022-08-16 02:54:41','2022-08-16 08:11:23',0,'',0),(58,31,'setMenuAuth','setSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-16 02:54:44','2022-08-16 08:11:23',0,'',0),(59,31,'setMenuAuth','cancelSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-16 02:54:46','2022-08-16 08:11:23',0,'',0),(60,4,'SysApi','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-16 03:00:57','2022-08-16 08:11:23',0,'',0),(61,28,'menuAdd','listSysMenuButtonPermissionVo',1,'SYSTEM','SYSTEM','2022-08-16 08:18:08','2022-08-16 08:19:07',0,'',61),(62,32,'setUserAuth','cancelSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-16 09:28:20','2022-08-16 09:28:20',0,'',0),(63,32,'setUserAuth','pageSelectUserApi',1,'SYSTEM','SYSTEM','2022-08-16 09:28:22','2022-08-16 09:28:22',0,'',0),(64,32,'setUserAuth','setSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-16 09:28:45','2022-08-16 09:28:45',0,'',0),(65,32,'setUserAuth','pageSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-16 09:28:45','2022-08-16 09:28:45',0,'',0),(66,32,'setUserAuth','setUserRole',1,'SYSTEM','SYSTEM','2022-08-16 09:28:45','2022-08-16 09:28:45',0,'',0),(67,32,'setUserAuth','listSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-16 09:28:45','2022-08-16 09:28:45',0,'',0),(68,32,'setUserAuth','setUserMenu',1,'SYSTEM','SYSTEM','2022-08-16 09:28:45','2022-08-16 09:28:45',0,'',0),(69,16,'userDelete','deleteUser',1,'SYSTEM','SYSTEM','2022-08-16 09:40:43','2022-08-16 09:40:43',0,'',0),(70,16,'userDelete','cs',1,'SYSTEM','SYSTEM','2022-08-16 09:40:47','2022-08-16 10:50:56',0,'',70),(71,21,'isOpen','cs',1,'SYSTEM','SYSTEM','2022-08-16 11:41:55','2022-08-16 11:42:12',0,'',71),(72,34,'SysDept','listSysDeptTree',1,'SYSTEM','SYSTEM','2022-08-18 07:31:32','2022-08-18 07:31:32',0,'',0),(73,35,'deptAdd','deptAdd',1,'SYSTEM','SYSTEM','2022-08-18 07:31:52','2022-08-18 07:31:52',0,'',0),(74,35,'deptAdd','listDeptSelectTree',1,'SYSTEM','SYSTEM','2022-08-18 07:31:52','2022-08-18 07:31:52',0,'',0),(75,36,'deptEdit','deptUpdate',1,'SYSTEM','SYSTEM','2022-08-18 07:32:05','2022-08-18 07:32:05',0,'',0),(76,36,'deptEdit','listDeptSelectTree',1,'SYSTEM','SYSTEM','2022-08-18 07:32:05','2022-08-18 07:32:05',0,'',0),(77,36,'deptEdit','findDeptById',1,'SYSTEM','SYSTEM','2022-08-18 07:32:10','2022-08-18 07:32:10',0,'',0),(78,37,'deptDelete','deptDelete',1,'SYSTEM','SYSTEM','2022-08-18 07:32:18','2022-08-18 07:32:18',0,'',0),(79,40,'SysDict','pageSysDictList',1,'SYSTEM','SYSTEM','2022-08-18 10:36:50','2022-08-18 10:36:50',0,'',0),(80,41,'dictAdd','dictAdd',1,'SYSTEM','SYSTEM','2022-08-18 10:37:02','2022-08-18 10:37:02',0,'',0),(81,42,'dictEdit','dictUpdate',1,'SYSTEM','SYSTEM','2022-08-18 10:37:11','2022-08-18 10:37:11',0,'',0),(82,43,'dictDelete','dictDelete',1,'SYSTEM','SYSTEM','2022-08-18 10:37:22','2022-08-18 10:37:22',0,'',0),(83,42,'dictEdit','findDictById',1,'SYSTEM','SYSTEM','2022-08-18 11:02:08','2022-08-18 11:02:08',0,'',0),(84,45,'SysDictDetail','pageDictDetailList',1,'SYSTEM','SYSTEM','2022-08-19 10:23:05','2022-08-19 10:23:05',0,'',0),(85,47,'dictDetailAdd','dictDetailAdd',1,'SYSTEM','SYSTEM','2022-08-19 10:23:17','2022-08-19 10:23:17',0,'',0),(86,47,'dictDetailAdd','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-19 10:23:28','2022-08-19 10:23:28',0,'',0),(87,48,'dictDetailEdit','dictDetailUpdate',1,'SYSTEM','SYSTEM','2022-08-19 10:23:44','2022-08-19 10:23:44',0,'',0),(88,48,'dictDetailEdit','findDictDetailById',1,'SYSTEM','SYSTEM','2022-08-19 10:23:46','2022-08-19 10:23:46',0,'',0),(89,48,'dictDetailEdit','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-19 10:23:51','2022-08-19 10:23:51',0,'',0),(90,49,'dictDetailDelete','dictDetailDelete',1,'SYSTEM','SYSTEM','2022-08-19 10:24:00','2022-08-19 10:24:00',0,'',0);
/*!40000 ALTER TABLE `sys_menu_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_code` varchar(16) NOT NULL DEFAULT '' COMMENT '角色编码',
  `role_name` varchar(32) NOT NULL DEFAULT '' COMMENT '角色名称',
  `data_scope` tinyint NOT NULL DEFAULT '0' COMMENT '数据权限类型',
  `remark` varchar(200) NOT NULL DEFAULT '' COMMENT '备注',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'super_admin','超级管理员',0,'','SYSTEM','SYSTEM','2022-08-09 09:19:36','2022-08-09 09:23:22',2,'',0),(2,'test_admin','测试管理员',0,'','SYSTEM','SYSTEM','2022-08-16 02:56:36','2022-08-16 02:56:36',0,'',0);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_api`
--

DROP TABLE IF EXISTS `sys_role_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_code` varchar(32) NOT NULL DEFAULT '' COMMENT '角色编码',
  `api_code` varchar(32) NOT NULL COMMENT '接口编码',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_api` (`role_code`,`api_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_api`
--

LOCK TABLES `sys_role_api` WRITE;
/*!40000 ALTER TABLE `sys_role_api` DISABLE KEYS */;
INSERT INTO `sys_role_api` VALUES (44,'super_admin','pageSelectRoleApi',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(45,'super_admin','setSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(46,'super_admin','cancelSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(47,'super_admin','setSysMenuForRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(48,'super_admin','cancelSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(49,'super_admin','pageSelectUserApi',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(50,'super_admin','setSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(51,'super_admin','pageSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(52,'super_admin','setUserRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(53,'super_admin','listSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-15 08:21:42','2022-08-15 08:21:42',0,'',0),(54,'super_admin','setUserMenu',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(55,'super_admin','roleAdd',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(56,'super_admin','roleUpdate',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(57,'super_admin','roleDelete',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(58,'super_admin','findRoleById',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(59,'super_admin','pageRoleList',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(60,'super_admin','listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(61,'super_admin','menuUpdate',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(62,'super_admin','menuDelete',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(63,'super_admin','findMenuById',1,'SYSTEM','SYSTEM','2022-08-15 08:21:48','2022-08-15 08:21:48',0,'',0),(64,'super_admin','listSysMenuTree',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(65,'super_admin','menuAdd',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(66,'super_admin','changeApiOpen',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(67,'super_admin','changeApiAuth',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(68,'super_admin','changeApiStatus',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(69,'super_admin','pageUserList',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(70,'super_admin','findApiById',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(71,'super_admin','apiDelete',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(72,'super_admin','apiUpdate',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(73,'super_admin','pageApiList',1,'SYSTEM','SYSTEM','2022-08-15 08:21:55','2022-08-15 08:21:55',0,'',0),(74,'super_admin','deleteUser',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(75,'super_admin','findUserById',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(76,'super_admin','selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(77,'super_admin','userUpdate',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(78,'super_admin','listUserRouter',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(79,'super_admin','apiAdd',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:22:06',0,'',79),(80,'super_admin','getUserInfo',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(81,'super_admin','userAdd',1,'SYSTEM','SYSTEM','2022-08-15 08:21:59','2022-08-15 08:21:59',0,'',0),(82,'super_admin','apiAdd',1,'SYSTEM','SYSTEM','2022-08-15 08:22:30','2022-08-15 08:22:30',0,'',0),(83,'test_admin','listUserRouter',1,'SYSTEM','SYSTEM','2022-08-16 02:57:35','2022-08-16 02:57:35',0,'',0);
/*!40000 ALTER TABLE `sys_role_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `role_code` varchar(32) NOT NULL DEFAULT '' COMMENT '角色编码',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_role_menu` (`role_code`,`menu_id`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统角色菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (132,'test_admin',1,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(133,'test_admin',2,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(134,'test_admin',3,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(135,'test_admin',4,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(136,'test_admin',5,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(137,'test_admin',6,1,'SYSTEM','SYSTEM','2022-08-16 02:57:13','2022-08-16 02:57:13',0,'',0),(138,'super_admin',1,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(139,'super_admin',2,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(140,'super_admin',3,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(141,'super_admin',14,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(142,'super_admin',15,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(143,'super_admin',17,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(144,'super_admin',32,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(145,'super_admin',4,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(146,'super_admin',18,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(147,'super_admin',19,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(148,'super_admin',20,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(149,'super_admin',21,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(150,'super_admin',22,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(151,'super_admin',23,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(152,'super_admin',5,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(153,'super_admin',24,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(154,'super_admin',25,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(155,'super_admin',26,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(156,'super_admin',27,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(157,'super_admin',6,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(158,'super_admin',28,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(159,'super_admin',29,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(160,'super_admin',30,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(161,'super_admin',31,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(162,'super_admin',11,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(163,'super_admin',12,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(164,'super_admin',13,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0),(165,'super_admin',7,1,'SYSTEM','SYSTEM','2022-08-16 09:46:46','2022-08-16 09:46:46',0,'',0);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `name` varchar(32) NOT NULL COMMENT '用户姓名',
  `account` varchar(20) NOT NULL DEFAULT '' COMMENT '登录账号',
  `nick_name` varchar(16) NOT NULL DEFAULT '' COMMENT '昵称',
  `tenant_id` varchar(64) NOT NULL DEFAULT '' COMMENT '租户id',
  `post_code` varchar(16) NOT NULL DEFAULT '' COMMENT '岗位编码',
  `dept_code` varchar(16) NOT NULL DEFAULT '' COMMENT '部门编码',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  `phone` varchar(11) NOT NULL DEFAULT '' COMMENT '手机号',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `sex` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '性别(1: 男, 2: 女)',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用于头像路径',
  `status` tinyint unsigned NOT NULL DEFAULT '1' COMMENT '账号状态(1:正常,2:停用,3:注销)',
  `data_scope` tinyint NOT NULL DEFAULT '0' COMMENT '数据权限类型',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_id` (`user_id`,`deleted`) COMMENT '账号唯一索引',
  UNIQUE KEY `unique_account` (`account`,`deleted`) COMMENT '账号唯一索引',
  UNIQUE KEY `unique_email` (`email`,`deleted`) COMMENT '邮箱唯一索引',
  UNIQUE KEY `unique_phone` (`phone`,`deleted`) COMMENT '手机号唯一索引'
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,100000,'admin','17856941755','超级管理员','','','master','$2a$10$40OlRZfuPEFnE1HuoszQ4udPU069O4QI6KNmRltQzST6tXzrzxY5W','17856941755','michaelkai@aliyun.com',1,'',1,1,'SYSTEM','SYSTEM','2022-07-28 16:00:25','2022-08-22 03:38:37',0,'',0);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_api`
--

DROP TABLE IF EXISTS `sys_user_api`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
  `api_code` varchar(32) NOT NULL COMMENT '接口编码',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_api` (`user_id`,`api_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_api`
--

LOCK TABLES `sys_user_api` WRITE;
/*!40000 ALTER TABLE `sys_user_api` DISABLE KEYS */;
INSERT INTO `sys_user_api` VALUES (1,100000,'userAdd',1,'SYSTEM','SYSTEM','2022-07-28 16:25:23','2022-08-15 06:38:34',0,'',1),(2,100000,'getUserInfo',1,'SYSTEM','SYSTEM','2022-08-01 14:55:04','2022-08-02 09:10:43',0,'',0),(4,100000,'apiAdd',1,'SYSTEM','SYSTEM','2022-08-02 08:44:09','2022-08-15 06:16:37',0,'',4),(5,100000,'userUpdate',1,'SYSTEM','SYSTEM','2022-08-02 08:51:08','2022-08-02 09:10:43',0,'',0),(6,100000,'listUserRouter',1,'SYSTEM','SYSTEM','2022-08-02 08:52:42','2022-08-02 09:10:43',0,'',0),(7,100000,'selectDictLabelList',1,'SYSTEM','SYSTEM','2022-08-04 06:05:14','2022-08-04 06:05:14',0,'',0),(8,100000,'findUserById',1,'SYSTEM','SYSTEM','2022-08-04 09:07:48','2022-08-04 09:07:48',0,'',0),(10,100000,'deleteUser',1,'SYSTEM','SYSTEM','2022-08-06 03:40:30','2022-08-15 06:33:34',0,'',10),(11,100000,'pageUserList',1,'SYSTEM','SYSTEM','2022-08-08 02:07:04','2022-08-08 02:07:04',0,'',0),(12,100000,'pageApiList',1,'SYSTEM','SYSTEM','2022-08-08 02:07:04','2022-08-08 02:07:04',0,'',0),(13,100000,'apiUpdate',1,'SYSTEM','SYSTEM','2022-08-08 02:07:04','2022-08-08 02:07:04',0,'',0),(14,100000,'apiDelete',1,'SYSTEM','SYSTEM','2022-08-08 02:07:04','2022-08-08 02:07:04',0,'',0),(15,100000,'findApiById',1,'SYSTEM','SYSTEM','2022-08-08 02:07:04','2022-08-08 02:07:04',0,'',0),(16,100000,'changeApiStatus',1,'SYSTEM','SYSTEM','2022-08-08 08:12:57','2022-08-08 08:12:57',0,'',0),(17,100000,'changeApiAuth',1,'SYSTEM','SYSTEM','2022-08-08 08:12:57','2022-08-08 08:12:57',0,'',0),(18,100000,'changeApiOpen',1,'SYSTEM','SYSTEM','2022-08-08 08:12:57','2022-08-08 08:12:57',0,'',0),(19,100000,'menuAdd',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(20,100000,'menuUpdate',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(21,100000,'menuDelete',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(22,100000,'findMenuById',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(23,100000,'listSysMenuTree',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(24,100000,'listMenuSelectTree',1,'SYSTEM','SYSTEM','2022-08-09 06:04:20','2022-08-09 06:04:20',0,'',0),(25,100000,'roleAdd',1,'SYSTEM','SYSTEM','2022-08-09 09:09:08','2022-08-09 09:09:08',0,'',0),(26,100000,'roleUpdate',1,'SYSTEM','SYSTEM','2022-08-09 09:09:08','2022-08-09 09:09:08',0,'',0),(27,100000,'roleDelete',1,'SYSTEM','SYSTEM','2022-08-09 09:09:08','2022-08-09 09:09:08',0,'',0),(28,100000,'findRoleById',1,'SYSTEM','SYSTEM','2022-08-09 09:09:08','2022-08-09 09:09:08',0,'',0),(29,100000,'pageRoleList',1,'SYSTEM','SYSTEM','2022-08-09 09:09:08','2022-08-09 09:09:08',0,'',0),(30,100000,'setUserMenu',1,'SYSTEM','SYSTEM','2022-08-11 08:40:37','2022-08-11 08:40:37',0,'',0),(31,100000,'setUserRole',1,'SYSTEM','SYSTEM','2022-08-12 07:15:58','2022-08-12 07:15:58',0,'',0),(32,100000,'listSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-12 07:15:58','2022-08-12 07:15:58',0,'',0),(33,100000,'pageSelectUserRole',1,'SYSTEM','SYSTEM','2022-08-15 04:05:56','2022-08-15 04:05:56',0,'',0),(39,100000,'setSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 05:54:04','2022-08-15 05:54:04',0,'',0),(40,100000,'pageSelectUserApi',1,'SYSTEM','SYSTEM','2022-08-15 05:54:04','2022-08-15 05:54:04',0,'',0),(41,100000,'cancelSysApiForUser',1,'SYSTEM','SYSTEM','2022-08-15 06:08:23','2022-08-15 06:08:23',0,'',0),(42,100000,'apiAdd',1,'SYSTEM','SYSTEM','2022-08-15 06:16:51','2022-08-15 06:38:12',0,'',42),(43,100000,'deleteUser',1,'SYSTEM','SYSTEM','2022-08-15 06:33:44','2022-08-15 06:33:44',0,'',0),(44,100000,'apiAdd',1,'SYSTEM','SYSTEM','2022-08-15 06:38:21','2022-08-15 06:38:21',0,'',0),(45,100000,'userAdd',1,'SYSTEM','SYSTEM','2022-08-15 06:38:41','2022-08-15 06:38:41',0,'',0),(46,100000,'setSysMenuForRole',1,'SYSTEM','SYSTEM','2022-08-15 07:38:53','2022-08-15 07:38:53',0,'',0),(47,100000,'setSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 07:38:53','2022-08-15 07:38:53',0,'',0),(48,100000,'cancelSysApiForRole',1,'SYSTEM','SYSTEM','2022-08-15 07:38:53','2022-08-15 07:38:53',0,'',0),(49,100000,'pageSelectRoleApi',1,'SYSTEM','SYSTEM','2022-08-15 08:06:19','2022-08-15 08:06:19',0,'',0),(50,100000,'setSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-15 10:07:36','2022-08-15 10:07:36',0,'',0),(51,100000,'cancelSysApiForMenu',1,'SYSTEM','SYSTEM','2022-08-15 10:07:36','2022-08-15 10:07:36',0,'',0),(52,100000,'pageSelectMenuApi',1,'SYSTEM','SYSTEM','2022-08-15 10:07:36','2022-08-15 10:07:36',0,'',0),(53,100000,'listSysMenuButtonPermissionVo',1,'SYSTEM','SYSTEM','2022-08-16 08:09:44','2022-08-16 08:09:44',0,'',0),(59,100000,'listSysDeptTree',1,'SYSTEM','SYSTEM','2022-08-18 07:32:46','2022-08-18 07:32:46',0,'',0),(60,100000,'dictUpdate',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(61,100000,'listDeptSelectTree',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(62,100000,'deptDelete',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(63,100000,'deptAdd',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(64,100000,'dictDelete',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(65,100000,'findDeptById',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(66,100000,'deptUpdate',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(67,100000,'pageSysDictList',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(68,100000,'findDictById',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(69,100000,'dictAdd',1,'SYSTEM','SYSTEM','2022-08-18 11:04:11','2022-08-18 11:04:11',0,'',0),(70,100000,'findUserPersonalInfo',1,'SYSTEM','SYSTEM','2022-08-21 08:54:27','2022-08-21 08:54:27',0,'',0),(71,100000,'updatePersonalBaseUserInfo',1,'SYSTEM','SYSTEM','2022-08-22 03:12:34','2022-08-22 03:12:34',0,'',0),(72,100000,'updateSysUserIntro',1,'SYSTEM','SYSTEM','2022-08-22 03:12:34','2022-08-22 03:12:34',0,'',0),(74,100000,'uploadUserAvatar',1,'SYSTEM','SYSTEM','2022-08-22 10:21:56','2022-08-22 10:21:56',0,'',0);
/*!40000 ALTER TABLE `sys_user_api` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_intro`
--

DROP TABLE IF EXISTS `sys_user_intro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_intro` (
  `id` bigint NOT NULL COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `introduction` varchar(500) NOT NULL DEFAULT '' COMMENT '个人简介',
  `tags` varchar(500) NOT NULL DEFAULT '' COMMENT '标签',
  `location` varchar(500) NOT NULL DEFAULT '' COMMENT '位置',
  `birth_day` datetime DEFAULT NULL COMMENT '生日',
  `skills` varchar(500) NOT NULL DEFAULT '' COMMENT '技能',
  `hobby` varchar(500) NOT NULL DEFAULT '' COMMENT '爱好',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_id` (`user_id`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户简介表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_intro`
--

LOCK TABLES `sys_user_intro` WRITE;
/*!40000 ALTER TABLE `sys_user_intro` DISABLE KEYS */;
INSERT INTO `sys_user_intro` VALUES (1561557054483681282,100000,'富在术数，不在劳身；利在势居，不在力耕。','帅气,阳光','中国 • 安徽省 • 合肥市','1992-12-22 06:06:06','Java、Spring、SpringBoot、MyBatis、MySQL','看电影、听音乐、LOL、周杰伦','SYSTEM','SYSTEM','2022-08-22 03:33:08','2022-08-22 05:43:52',10,'',0);
/*!40000 ALTER TABLE `sys_user_intro` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_menu`
--

DROP TABLE IF EXISTS `sys_user_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
  `menu_id` bigint NOT NULL COMMENT '菜单id',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_menu` (`user_id`,`menu_id`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=924 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_menu`
--

LOCK TABLES `sys_user_menu` WRITE;
/*!40000 ALTER TABLE `sys_user_menu` DISABLE KEYS */;
INSERT INTO `sys_user_menu` VALUES (114,100001,1,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(115,100001,2,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(116,100001,3,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(117,100001,32,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(118,100001,4,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(119,100001,5,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(120,100001,6,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(121,100001,11,1,'SYSTEM','SYSTEM','2022-08-16 03:13:12','2022-08-16 03:13:12',0,'',0),(882,100000,1,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(883,100000,2,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(884,100000,3,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(885,100000,14,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(886,100000,15,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(887,100000,17,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(888,100000,32,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(889,100000,4,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(890,100000,18,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(891,100000,19,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(892,100000,20,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(893,100000,21,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(894,100000,22,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(895,100000,23,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(896,100000,5,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(897,100000,24,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(898,100000,25,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(899,100000,26,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(900,100000,27,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(901,100000,6,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(902,100000,28,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(903,100000,29,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(904,100000,30,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(905,100000,31,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(906,100000,11,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(907,100000,12,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(908,100000,13,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(909,100000,34,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(910,100000,35,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(911,100000,36,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(912,100000,37,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(913,100000,39,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(914,100000,40,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(915,100000,45,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(916,100000,47,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(917,100000,48,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(918,100000,49,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(919,100000,46,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(920,100000,41,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(921,100000,42,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(922,100000,43,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0),(923,100000,44,1,'SYSTEM','SYSTEM','2022-08-23 08:42:07','2022-08-23 08:42:07',0,'',0);
/*!40000 ALTER TABLE `sys_user_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL DEFAULT '0' COMMENT '用户id',
  `role_code` varchar(32) NOT NULL COMMENT '角色编码',
  `is_available` tinyint NOT NULL DEFAULT '1' COMMENT '是否可用',
  `creator` varchar(64) NOT NULL COMMENT '行记录创建者',
  `modifier` varchar(64) NOT NULL COMMENT '行记录最近更新人',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '行记录创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '行记录最近修改时间',
  `version` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '行版本号',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '拓展字段',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_user_role` (`user_id`,`role_code`,`deleted`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统用户角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (5,100000,'super_admin',1,'SYSTEM','SYSTEM','2022-08-15 06:39:12','2022-08-15 06:39:12',0,'',0),(6,1555116980267163600,'test_admin',1,'SYSTEM','SYSTEM','2022-08-16 02:57:45','2022-08-16 02:57:45',0,'',0),(7,100001,'test_admin',1,'SYSTEM','SYSTEM','2022-08-16 02:58:54','2022-08-16 02:58:54',0,'',0);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data`
--

DROP TABLE IF EXISTS `thread_pool_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data`
--

LOCK TABLES `thread_pool_data` WRITE;
/*!40000 ALTER TABLE `thread_pool_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202205`
--

DROP TABLE IF EXISTS `thread_pool_data_202205`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202205` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202205`
--

LOCK TABLES `thread_pool_data_202205` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202205` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202205` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202206`
--

DROP TABLE IF EXISTS `thread_pool_data_202206`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202206` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202206`
--

LOCK TABLES `thread_pool_data_202206` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202206` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202206` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202207`
--

DROP TABLE IF EXISTS `thread_pool_data_202207`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202207` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202207`
--

LOCK TABLES `thread_pool_data_202207` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202207` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202207` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202208`
--

DROP TABLE IF EXISTS `thread_pool_data_202208`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202208` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202208`
--

LOCK TABLES `thread_pool_data_202208` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202208` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202208` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202209`
--

DROP TABLE IF EXISTS `thread_pool_data_202209`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202209` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202209`
--

LOCK TABLES `thread_pool_data_202209` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202209` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202209` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202210`
--

DROP TABLE IF EXISTS `thread_pool_data_202210`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202210` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202210`
--

LOCK TABLES `thread_pool_data_202210` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202210` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202210` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202211`
--

DROP TABLE IF EXISTS `thread_pool_data_202211`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202211` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202211`
--

LOCK TABLES `thread_pool_data_202211` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202211` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202211` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thread_pool_data_202212`
--

DROP TABLE IF EXISTS `thread_pool_data_202212`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `thread_pool_data_202212` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) NOT NULL DEFAULT '' COMMENT '线程池名称',
  `client` varchar(64) NOT NULL DEFAULT '' COMMENT '客户端',
  `core_pool_size` int NOT NULL DEFAULT '0' COMMENT '核心线程数',
  `maximum_pool_size` int NOT NULL DEFAULT '0' COMMENT '最大线程数',
  `active_count` int NOT NULL DEFAULT '0' COMMENT '正在执行任务的大致线程数',
  `pool_size` int NOT NULL DEFAULT '0' COMMENT '当前线程数',
  `largest_pool_size` int NOT NULL DEFAULT '0' COMMENT '历史最大线程数',
  `task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务总数',
  `completed_task_count` bigint NOT NULL DEFAULT '0' COMMENT '任务完成数',
  `queue_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列总容量【总容量=已使用容量+剩余容量】',
  `queue_size` int NOT NULL DEFAULT '0' COMMENT '阻塞队列已使用容量',
  `queue_remaining_capacity` int NOT NULL DEFAULT '0' COMMENT '阻塞队列剩余容量',
  `sharding_key` int NOT NULL DEFAULT '0' COMMENT '分表字段【年月，例如202103】',
  `extension` varchar(2048) NOT NULL DEFAULT '' COMMENT '扩展字段',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '描述',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `creator` varchar(64) NOT NULL DEFAULT '' COMMENT '创建人',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` varchar(64) NOT NULL DEFAULT '' COMMENT '更新人',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `deleted` bigint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `idx_name_create_time_client` (`name`,`create_time`,`client`,`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='线程池监控数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thread_pool_data_202212`
--

LOCK TABLES `thread_pool_data_202212` WRITE;
/*!40000 ALTER TABLE `thread_pool_data_202212` DISABLE KEYS */;
/*!40000 ALTER TABLE `thread_pool_data_202212` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-02 16:05:09
