alter table job add column band bigint(20) default null comment 'band';
alter table job add column seq bigint(20) default null comment '序列';
alter table job add column race bigint(20) default null comment '族群';

alter table emp_profile add column empType bigint(20) default null comment '用工形式 来源于字典表';
alter table emp_profile add column practiceRecord varchar(1024) default null comment '实习记录';
alter table emp_profile add column contractBeginDate datetime default null comment '合同签署日期';
alter table emp_profile add column contractEndDate datetime default null comment '合同截止日期';

alter table stand_salary add column baseMoney decimal default null comment '固定工资';


alter table emp_profile add column isDepartFiled int default null comment '是否签订离职手续';
alter table emp_profile add column providentDate datetime default null comment '社会保险起交日期';
alter table emp_profile add column pbcSingedDate datetime default null comment 'PBC签订日期';
alter table emp_profile add column pbcExecuteDate datetime default null comment 'PBC执行日期';


DROP TABLE IF EXISTS `emp_profile_hist`;
CREATE TABLE `emp_profile_hist` (
  `profileHistId` bigint(20) NOT NULL AUTO_INCREMENT,
  `profileId` bigint(20) NOT NULL,
  `profileNo` varchar(100) NOT NULL COMMENT '档案编号',
  `userId` bigint(20) NOT NULL,
  `fullname` varchar(64) NOT NULL COMMENT '员工姓名',
  `address` varchar(128) DEFAULT NULL COMMENT '家庭地址',
  `birthday` datetime DEFAULT NULL COMMENT '出生日期',
  `homeZip` varchar(32) DEFAULT NULL COMMENT '家庭邮编',
  `sex` varchar(32) DEFAULT NULL,
  `marriage` varchar(32) DEFAULT NULL COMMENT '婚姻状况\r\n            已婚\r\n            未婚',
  `designation` varchar(64) DEFAULT NULL,
  `jobId` bigint(20) NOT NULL,
  `position` varchar(128) NOT NULL,
  `phone` varchar(64) DEFAULT NULL COMMENT '电话号码',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号码',
  `openBank` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `bankNo` varchar(100) DEFAULT NULL COMMENT '银行账号',
  `qq` varchar(64) DEFAULT NULL COMMENT 'QQ号码',
  `email` varchar(128) DEFAULT NULL COMMENT '电子邮箱',
  `hobby` varchar(300) DEFAULT NULL COMMENT '爱好',
  `religion` varchar(100) DEFAULT NULL COMMENT '宗教信仰',
  `party` varchar(100) DEFAULT NULL COMMENT '政治面貌',
  `nationality` varchar(100) DEFAULT NULL COMMENT '国籍',
  `race` varchar(100) DEFAULT NULL COMMENT '民族',
  `birthPlace` varchar(128) DEFAULT NULL COMMENT '出生地',
  `eduDegree` varchar(100) DEFAULT NULL COMMENT '学历',
  `eduMajor` varchar(100) DEFAULT NULL COMMENT '专业',
  `eduCollege` varchar(128) DEFAULT NULL COMMENT '毕业院校',
  `startWorkDate` datetime DEFAULT NULL COMMENT '参加工作时间',
  `eduCase` varchar(2048) DEFAULT NULL COMMENT '教育背景',
  `awardPunishCase` varchar(2048) DEFAULT NULL COMMENT '奖惩情况',
  `trainingCase` varchar(2048) DEFAULT NULL COMMENT '培训情况',
  `workCase` varchar(2048) DEFAULT NULL COMMENT '工作经历',
  `idCard` varchar(64) DEFAULT NULL COMMENT '身份证号',
  `photo` varchar(128) DEFAULT NULL COMMENT '照片',
  `standardMiNo` varchar(100) DEFAULT NULL COMMENT '薪酬标准编号',
  `standardMoney` decimal(18,2) DEFAULT NULL COMMENT '薪酬标准金额',
  `standardName` varchar(128) DEFAULT NULL COMMENT '薪酬标准单名称',
  `standardId` bigint(20) DEFAULT NULL COMMENT '薪酬标准单编号',
  `creator` varchar(64) DEFAULT NULL COMMENT '建档人',
  `createtime` datetime DEFAULT NULL COMMENT '建档时间',
  `checkName` varchar(128) DEFAULT NULL COMMENT '审核人',
  `checktime` datetime DEFAULT NULL COMMENT '审核时间',
  `opprovalOpinion` varchar(1024) DEFAULT NULL,
  `approvalStatus` smallint(6) DEFAULT '0' COMMENT '核审状态\r\n            0=未审批\r\n            1=通过审核\r\n            2=未通过审核',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  `depName` varchar(100) DEFAULT NULL COMMENT '所属部门或公司',
  `depId` bigint(20) DEFAULT NULL COMMENT '所属部门Id',
  `delFlag` smallint(6) NOT NULL DEFAULT '0' COMMENT '删除状态\r\n            0=未删除\r\n            1=删除',
  `accessionTime` datetime DEFAULT NULL COMMENT '入职日期',
  `departureTime` datetime DEFAULT NULL COMMENT '离职日期',
  `perCoefficient` decimal(18,2) DEFAULT NULL COMMENT '绩效基数',
  `provident` decimal(18,2) DEFAULT NULL COMMENT '公积金',
  `insurance` decimal(18,2) DEFAULT NULL COMMENT '社保',
  `isDepart` int(11) DEFAULT NULL COMMENT '是否离职0 否1 是',
  `positiveTime` datetime DEFAULT NULL COMMENT '转正日期',
  `organization` int(11) DEFAULT NULL,
  `realPositiveTime` datetime DEFAULT NULL COMMENT '实际转正时间',
  `empType` bigint(20) DEFAULT NULL COMMENT '用工形式',
  `contractBeginDate` datetime DEFAULT NULL COMMENT '合同签署日期',
  `contractEndDate` datetime DEFAULT NULL COMMENT '合同结束日期',
  `practiceRecord` varchar(1024) DEFAULT NULL COMMENT '实习记录',
  `providentDate` datetime DEFAULT NULL COMMENT '社保起交日期',
  `pbcExecuteDate` datetime DEFAULT NULL COMMENT 'PBC执行日期',
  `pbcSingedDate` datetime DEFAULT NULL COMMENT 'PBC签订日期',
  `isDepartFiled` int(11) DEFAULT NULL COMMENT '是否签订离职手续',
  `modifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `modifiedUser` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`profileHistId`),
  KEY `FK_EPF_R_AU` (`userId`) USING BTREE,
  KEY `FK_EP_R_JB` (`jobId`) USING BTREE,
  KEY `FK_SD_R_SY` (`standardId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='员工档案历史表';

