
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
  `result` double DEFAULT NULL COMMENT '考核最后得分'
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
  `modifyPerson` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of hr_pa_kpipbc2usercmp
-- ----------------------------

--2011-08-18

--修改档案管理表
ALTER TABLE emp_profile ADD COLUMN accessionTime datetime default null COMMENT '入职日期';
ALTER TABLE emp_profile ADD COLUMN departureTime datetime default null COMMENT '离职日期';
ALTER TABLE emp_profile ADD COLUMN perCoefficient decimal(18,2) DEFAULT NULL COMMENT '绩效系数';
ALTER TABLE emp_profile ADD COLUMN provident decimal(18,2) DEFAULT NULL COMMENT '公积金';
ALTER TABLE emp_profile ADD COLUMN insurance decimal(18,2) DEFAULT NULL COMMENT '社保';


drop table if exists hr_sr_rewardsPunishments;

--添加人员奖惩表、个税、个税条目表
create table hr_sr_rewardsPunishments
(
   id                   bigint not null AUTO_INCREMENT,
   userId               bigint comment '被奖惩人',
   rpType               bigint comment '奖惩类型 来源于字典表',
   rpTypeStr            varchar(32) comment '奖惩类型',
   amount               decimal,
   remark               varchar(512) comment '奖惩备注',
   profileId            bigint comment '人员档案ID',
   createDate datetime DEFAULT NULL comment '创建人',
   createPerson bigint(20) DEFAULT NULL comment '创建日期',
   primary key (id)
);

alter table hr_sr_rewardsPunishments comment '人员奖惩表';

alter table hr_sr_rewardsPunishments add constraint FK_Reference_30 foreign key (profileId) REFERENCES `emp_profile` (`profileId`);

insert into dictionary(itemName,itemValue) values("奖惩类型","奖励");
insert into dictionary(itemName,itemValue) values("奖惩类型","惩罚");


drop table if exists hr_sr_incomeTax;

/*==============================================================*/
/* Table: hr_sr_incomeTax                                       */
/*==============================================================*/
create table hr_sr_incomeTax
(
   id                   bigint not null AUTO_INCREMENT,
   name                 varchar(128) comment '名称',
   beginDate            datetime comment '开始执行时间',
   endDate              datetime comment '执行结束时间',
   basicAmount          decimal comment '个税起征点',
   publishDate          datetime comment '发布时间',
   publishPerson        bigint comment '发布人',
   remark               varchar(512) comment '备注',
   primary key (id)
);

alter table hr_sr_incomeTax comment '个人所得税基准表';

drop table if exists hr_sr_incomeTaxItem;

/*==============================================================*/
/* Table: hr_sr_incomeTaxItem                                   */
/*==============================================================*/
create table hr_sr_incomeTaxItem
(
   id                   bigint not null AUTO_INCREMENT,
   itId                 bigint comment '所得税模板',
   limitAmount          decimal comment '上限金额',
   lowerAmount          decimal comment '下限金额',
   taxValue             double comment '适用税率',
   primary key (id)
);

alter table hr_sr_incomeTaxItem add constraint FK_Reference_28 foreign key (itId)
      references hr_sr_incomeTax (id) on delete restrict on update restrict;
alter table hr_sr_incomeTaxItem add column deductValue decimal comment '速算扣除数';
      
-- 2011-08-19      
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `hr_pa_datadictionary`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_datadictionary`;
CREATE TABLE `hr_pa_datadictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL COMMENT '分类名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父类型ID',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_11` (`parentId`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`parentId`) REFERENCES `hr_pa_datadictionary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of hr_pa_datadictionary
-- ----------------------------
INSERT INTO hr_pa_datadictionary VALUES ('1', '数据字典基础字段', '1');
INSERT INTO hr_pa_datadictionary VALUES ('2', '考核项目类型', '1');
INSERT INTO hr_pa_datadictionary VALUES ('3', '考核频度', '1');
INSERT INTO hr_pa_datadictionary VALUES ('4', '考核方式', '1');
INSERT INTO hr_pa_datadictionary VALUES ('5', '销售类', '2');
INSERT INTO hr_pa_datadictionary VALUES ('6', '非销售类', '2');
INSERT INTO hr_pa_datadictionary VALUES ('7', '一个月', '3');
INSERT INTO hr_pa_datadictionary VALUES ('8', '两个月', '3');
INSERT INTO hr_pa_datadictionary VALUES ('9', '一个季度', '3');
INSERT INTO hr_pa_datadictionary VALUES ('10', '半年', '3');
INSERT INTO hr_pa_datadictionary VALUES ('11', '一年', '3');
INSERT INTO hr_pa_datadictionary VALUES ('12', '定性考核', '4');
INSERT INTO hr_pa_datadictionary VALUES ('13', '定量考核', '4');

-- ----------------------------
-- Table structure for `hr_pa_kpiitem2user`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpiitem2user`;
CREATE TABLE `hr_pa_kpiitem2user` (
  `id` bigint(20) NOT NULL,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_31` (`pbcId`),
  KEY `FK_Reference_32` (`piId`),
  CONSTRAINT `FK_Reference_31` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of hr_pa_kpiitem2user
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc2user`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc2user`;
CREATE TABLE `hr_pa_kpipbc2user` (
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