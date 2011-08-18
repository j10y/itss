
CREATE TABLE `hr_task_scheduler` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `taskname` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `runtime` varchar(255) DEFAULT NULL COMMENT '运行周期',
  `cronTrigger` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `hr_pa_kpiitem2usercmp`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpiitem2usercmp`;
CREATE TABLE `hr_pa_kpiitem2usercmp` (
  `id` bigint(20) NOT NULL,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of hr_pa_kpiitem2usercmp
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc2usercmp`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc2usercmp`;
CREATE TABLE `hr_pa_kpipbc2usercmp` (
  `id` bigint(20) NOT NULL,
  `pbcName` varchar(150) DEFAULT NULL,
  `fromPBC` varchar(200) DEFAULT NULL COMMENT '记录来源于那些PBC\r\n            \r\n            内容为用‘,’分割的kbiPBC表的id字符串',
  `belongUser` bigint(20) DEFAULT NULL,
  `frequency` bigint(20) DEFAULT NULL COMMENT '频度',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` float DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';