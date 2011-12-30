/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50509
Source Host           : localhost:3306
Source Database       : jxkh_25

Target Server Type    : MYSQL
Target Server Version : 50509
File Encoding         : 65001

Date: 2011-12-30 13:26:42
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `mrbs_area`
-- ----------------------------
DROP TABLE IF EXISTS `mrbs_area`;
CREATE TABLE `mrbs_area` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `area_name` varchar(30) DEFAULT '' COMMENT '名称',
  `linkman` varchar(255) DEFAULT NULL COMMENT '管理员联系方式',
  `descn` text,
  `shortdescn` text,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='区域';

-- ----------------------------
-- Records of mrbs_area
-- ----------------------------
INSERT INTO mrbs_area VALUES ('1', '来广营办公区', '在使用完会议室后将桌椅恢复原位 -- 如需长期预定请联系：郭宏雅', '', '', '1');

-- ----------------------------
-- Table structure for `mrbs_repeat`
-- ----------------------------
DROP TABLE IF EXISTS `mrbs_repeat`;
CREATE TABLE `mrbs_repeat` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `start_hour` varchar(2) NOT NULL COMMENT '开始时间,以小时计',
  `end_hour` varchar(2) NOT NULL COMMENT '结束时间',
  `start_mini` varchar(2) DEFAULT '00' COMMENT '开始分钟',
  `end_mini` varchar(2) NOT NULL DEFAULT '00' COMMENT '结束分钟',
  `room_id` bigint(11) NOT NULL COMMENT '会议室',
  `updatetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_by` bigint(80) NOT NULL COMMENT '添加者',
  `orderman` varchar(32) NOT NULL DEFAULT '' COMMENT '预定者,存个名字而已',
  `rep_opt` int(11) NOT NULL DEFAULT '0' COMMENT '重复情况',
  `description` text COMMENT '会议室描述',
  `start_date` datetime DEFAULT NULL COMMENT '开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '结束日期',
  `allday` int(11) DEFAULT '0' COMMENT '是否整天',
  `repeat_week_day` int(11) DEFAULT '1' COMMENT '重复星期',
  `week_span` int(11) DEFAULT '0' COMMENT '隔开几周',
  `repeat_day` int(11) DEFAULT '1' COMMENT '重复日',
  `orderman_email` varchar(128) DEFAULT NULL,
  `projector` int(11) DEFAULT '0',
  `conference_call` int(11) DEFAULT '0',
  `num` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_repeat_roo` (`room_id`) USING BTREE,
  CONSTRAINT `room_for` FOREIGN KEY (`room_id`) REFERENCES `mrbs_room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1140 DEFAULT CHARSET=utf8 COMMENT='会议预定规则';

-- ----------------------------
-- Table structure for `mrbs_room`
-- ----------------------------
DROP TABLE IF EXISTS `mrbs_room`;
CREATE TABLE `mrbs_room` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `area_id` bigint(11) DEFAULT NULL COMMENT '区域',
  `room_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `description` varchar(255) DEFAULT NULL COMMENT '简介',
  `capacity` int(11) DEFAULT '0' COMMENT '容纳人数',
  `room_admin_email` varchar(255) DEFAULT NULL COMMENT '联系方式',
  `status` varchar(32) DEFAULT NULL,
  `sortIndex` varchar(32) DEFAULT NULL,
  `virtualMap` varchar(255) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INDEX_ROMM_AREAID` (`area_id`) USING BTREE,
  CONSTRAINT `area_for` FOREIGN KEY (`area_id`) REFERENCES `mrbs_area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mrbs_room
-- ----------------------------
INSERT INTO mrbs_room VALUES ('1', '1', 'M1', '', '8', '6-8人 无投影', null, null, null, '1');
INSERT INTO mrbs_room VALUES ('2', '1', 'M2', null, '8', '6-8人  无投影', null, null, null, '1');
INSERT INTO mrbs_room VALUES ('3', '1', 'M3', '', '15', '10-15人有电视投影', null, null, null, '1');
INSERT INTO mrbs_room VALUES ('4', '1', 'M5', null, '9', '6-9人 无投影', null, null, null, '1');
INSERT INTO mrbs_room VALUES ('5', '1', 'M6', null, '30', '30人 有投影 白板 独立空调', null, null, null, '1');
INSERT INTO mrbs_room VALUES ('6', '1', 'M7', '', '20', '20人 有投影', null, null, null, '1');

-- ----------------------------
-- Table structure for `mrbs_schedule`
-- ----------------------------
DROP TABLE IF EXISTS `mrbs_schedule`;
CREATE TABLE `mrbs_schedule` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `room_id` bigint(11) NOT NULL COMMENT '会议室',
  `create_by` bigint(80) NOT NULL COMMENT '添加者',
  `preside` varchar(80) NOT NULL DEFAULT '' COMMENT '预定者，预定者主持会议',
  `repeat_id` bigint(11) DEFAULT NULL COMMENT '对应规则',
  `description` text COMMENT '会议室描述',
  `num` int(11) NOT NULL DEFAULT '0' COMMENT '与会人数',
  `title` varchar(255) DEFAULT NULL,
  `preside_email` varchar(128) DEFAULT NULL,
  `projector` int(11) DEFAULT '0',
  `conference_call` int(11) DEFAULT '0',
  `create_date` datetime DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `modify_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fk_schedule_repeat` (`repeat_id`) USING BTREE,
  KEY `INDEX_SCHEDULE_ROOMID` (`room_id`) USING BTREE,
  CONSTRAINT `sch_repeat_for` FOREIGN KEY (`repeat_id`) REFERENCES `mrbs_repeat` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sch_room_for` FOREIGN KEY (`room_id`) REFERENCES `mrbs_room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1354 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `mrbs_schedule_user`
-- ----------------------------
DROP TABLE IF EXISTS `mrbs_schedule_user`;
CREATE TABLE `mrbs_schedule_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(11) DEFAULT NULL,
  `scheduleid` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_shedule_key` (`scheduleid`) USING BTREE,
  KEY `FK_user_key` (`userid`) USING BTREE,
  CONSTRAINT `user_for` FOREIGN KEY (`userid`) REFERENCES `app_user` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sch_for` FOREIGN KEY (`scheduleid`) REFERENCES `mrbs_schedule` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='参加会议的用户';

