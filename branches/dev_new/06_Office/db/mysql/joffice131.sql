-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.20a-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema joffice131
--

CREATE DATABASE IF NOT EXISTS joffice131;
USE joffice131;

--
-- Definition of table `app_function`
--

DROP TABLE IF EXISTS `app_function`;
CREATE TABLE `app_function` (
  `functionId` bigint(20) NOT NULL auto_increment,
  `funKey` varchar(64) NOT NULL COMMENT '权限Key',
  `funName` varchar(128) NOT NULL COMMENT '权限名称',
  PRIMARY KEY  (`functionId`),
  UNIQUE KEY `AK_UQ_RSKEY` (`funKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `app_function`
--

/*!40000 ALTER TABLE `app_function` DISABLE KEYS */;
INSERT INTO `app_function` (`functionId`,`funKey`,`funName`) VALUES 
 (1,'_AppUserQuery','查看员工'),
 (2,'_AppUserAdd','添加员工'),
 (3,'_AppUserEdit','编辑员工'),
 (4,'_AppUserDel','删除员工'),
 (5,'_AppRoleList','查看角色'),
 (6,'_AppRoleAdd','添加角色'),
 (7,'_AppRoleEdit','编辑角色'),
 (8,'_AppRoleDel','删除角色'),
 (9,'_AppRoleGrant','授权角色'),
 (10,'_DepartmentQuery','查看部门'),
 (11,'_DepartmentAdd','新建部门'),
 (12,'_DepartmentEdit','修改部门'),
 (13,'_DepartmentDel','删除部门'),
 (14,'_FileAttachQuery','查看附件'),
 (15,'_FileAttachEdit','编辑附件'),
 (16,'_FileAttachDel','删除附件'),
 (17,'_CompanyEdit','公司信息修改'),
 (18,'_FlowQuery','查看流程'),
 (19,'_ProTypeManage','流程类型'),
 (20,'_FlowAdd','发布流程'),
 (21,'_FlowEdit','编辑流程'),
 (22,'_FlowDel','删除流程'),
 (23,'_FlowCheck','查看'),
 (24,'_FlowSetting','人员设置'),
 (25,'_DocFolderSharedManage','公共文件夹管理'),
 (26,'_DocPrivilegeQuery','查看权限'),
 (27,'_DocPrivilegeAdd','添加权限'),
 (28,'_DocPrivilegeEdit','编辑权限'),
 (29,'_DocPrivilegeDel','删除权限'),
 (30,'_PlanTypeQuery','查看类型'),
 (31,'_PlanTypeAdd','添加类型'),
 (32,'_PlanTypeEdit','编辑类型'),
 (33,'_PlanTypeDel','删除类型'),
 (34,'_NewDepPlan','新建部门任务计划'),
 (35,'_NewsQuery','查看新闻'),
 (36,'_NewsAdd','添加新闻'),
 (37,'_NewsEdit','编辑新闻'),
 (38,'_NewsDel','删除新闻'),
 (39,'_NewsCommentQuery','查看评论'),
 (40,'_NewsCommentDel','删除评论'),
 (41,'_NewsTypeQuery','查看新闻类型'),
 (42,'_NewsTypeAdd','添加新闻类型'),
 (43,'_NewsTypeEdit','修改新闻类型'),
 (44,'_NewsTypeDel','删除新闻类型'),
 (45,'_NoticeQuery','查看公告'),
 (46,'_NoticeAdd','添加公告'),
 (47,'_NoticeEdit','编辑公告'),
 (48,'_NoticeDel','删除公告'),
 (49,'_HolidayRecordQuery','查看假期设置'),
 (50,'_HolidayRecordAdd','添加假期设置'),
 (51,'_HolidayRecordEdit','修改假期设置'),
 (52,'_HolidayRecordDel','删除假期设置'),
 (53,'_DutySectonQuery','查看班次定义'),
 (54,'_DutySectonAdd','添加班次定义'),
 (55,'_DutySectonEdit','修改班次定义'),
 (56,'_DutySectonDel','删除班次定义'),
 (57,'_DutySystemQuery','查看班制定义'),
 (58,'_DutySystemAdd','添加班制定义'),
 (59,'_DutySystemEdit','修改班制定义'),
 (60,'_DutySystemDel','删除班制定义'),
 (61,'_DutyQuery','查看排班'),
 (62,'_DutyAdd','添加排班'),
 (63,'_DutyEdit','修改排班'),
 (64,'_DutyDel','删除排班'),
 (65,'_DutyRegisterQuery','查看考勤信息'),
 (66,'_DutyRegisterAdd','补签'),
 (67,'_DutyRegisterDel','删除考勤信息'),
 (68,'_CustomerQuery','查看客户信息'),
 (69,'_CustomerAdd','添加客户信息'),
 (70,'_CustomerEdit','编辑客户信息'),
 (71,'_CustomerDel','删除客户信息'),
 (72,'_CusLinkmanQuery','查看联系人信息'),
 (73,'_CusLinkmanAdd','添加联系人'),
 (74,'_CusLinkmanEdit','编辑联系人'),
 (75,'_CusLinkmanDel','删除联系人'),
 (76,'_CusConnectionQuery','查看交往信息'),
 (77,'_CusConnectionAdd','添加交往信息'),
 (78,'_CusConnectionEdit','编辑交往信息'),
 (79,'_CusConnectionDel','删除交往信息'),
 (80,'_ProjectQuery','查看项目'),
 (81,'_ProjectAdd','添加项目'),
 (82,'_ProjectEdit','编辑项目'),
 (83,'_ProjectDel','删除项目'),
 (84,'_ContractQuery','查看合同'),
 (85,'_ContractAdd','添加合同'),
 (86,'_ContractEdit','编辑合同'),
 (87,'_ContractDel','删除合同'),
 (88,'_ProductQuery','查看产品'),
 (89,'_ProductAdd','添加产品'),
 (90,'_ProductEdit','编辑产品'),
 (91,'_ProductDel','删除产品'),
 (92,'_ProviderQuery','查看供应商'),
 (93,'_ProviderAdd','添加供应商'),
 (94,'_ProviderEdit','编辑供应商'),
 (95,'_ProviderDel','删除供应商'),
 (96,'_OfficeGoodsQuery','查看办公用品'),
 (97,'_OfficeGoodsTypeManage','用品类型管理'),
 (98,'_OfficeGoodsAdd','添加用品'),
 (99,'_OfficeGoodsEdit','编辑用品'),
 (100,'_OfficeGoodsDel','删除用品'),
 (101,'_InStockQuery','查看入库记录'),
 (102,'_InStockAdd','添加入库记录'),
 (103,'_InStockEdit','编辑入库记录'),
 (104,'_InStockDel','删除入库记录'),
 (105,'_GoodsApplyQuery','查看申请记录'),
 (106,'_GoodsApplyAdd','添加申请记录'),
 (107,'_GoodsApplyEdit','编辑申请记录'),
 (108,'_GoodsApplyDel','删除申请记录'),
 (109,'_CarQuery','查看车辆'),
 (110,'_CarAdd','添加车辆'),
 (111,'_CarEdit','编辑车辆'),
 (112,'_CarDel','删除车辆'),
 (113,'_CarRepairQuery','查看维修记录'),
 (114,'_CarRepairAdd','添加维修记录'),
 (115,'_CarRepairEdit','编辑维修记录'),
 (116,'_CarRepairDel','删除维修记录'),
 (117,'_CarApplyQuery','查看车辆申请记录'),
 (118,'_CarApplyAdd','添加申请记录'),
 (119,'_CarApplyEdit','编辑申请记录'),
 (120,'_CarApplyDel','删除申请记录'),
 (121,'_DepreTypeQuery','查看折算类型'),
 (122,'_DepreTypeAdd','添加类型'),
 (123,'_DepreTypeEdit','编辑类型'),
 (124,'_DepreTypeDel','删除类型'),
 (125,'_FixedAssetsQuery','查看固定资产'),
 (126,'_AssetsTypeManage','资产类型管理'),
 (127,'_FixedAssetsAdd','添加资产'),
 (128,'_FixedAssetsEdit','编辑资产'),
 (129,'_FixedAssetsDel','删除资产'),
 (130,'_Depreciate','进行折算'),
 (131,'_DepreRecordQuery','查看折算记录'),
 (132,'_BookTypeQuery','查看类型'),
 (133,'_BookTypeAdd','添加图书类别'),
 (134,'_BookTypeEdit','编辑图书类别'),
 (135,'_BookTypeDel','删除图书类别'),
 (136,'_BookQuery','查看图书'),
 (137,'_BookAdd','添加图书'),
 (138,'_BookEdit','编辑图书'),
 (139,'_BookDel','删除图书'),
 (140,'_BookBorrowQuery','查看记录'),
 (141,'_BookBorrowAdd','添加借阅记录'),
 (142,'_BookBorrowEdit','编辑借阅记录'),
 (143,'_BookReturn','归还'),
 (144,'_BookBorrowDel','删除借阅记录'),
 (145,'_BookReturnQuery','查看记录'),
 (146,'_BookReturnAdd','添加归还记录'),
 (147,'_BookReturnEdit','编辑归还记录'),
 (148,'_BookReturnDel','删除归还记录');
/*!40000 ALTER TABLE `app_function` ENABLE KEYS */;


--
-- Definition of table `app_role`
--

DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
  `roleId` bigint(20) NOT NULL auto_increment,
  `roleName` varchar(128) NOT NULL COMMENT '角色名称',
  `roleDesc` varchar(128) default NULL COMMENT '角色描述',
  `status` smallint(6) NOT NULL COMMENT '状态',
  `rights` text,
  `isDefaultIn` smallint(6) NOT NULL,
  PRIMARY KEY  (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

--
-- Dumping data for table `app_role`
--

/*!40000 ALTER TABLE `app_role` DISABLE KEYS */;
INSERT INTO `app_role` (`roleId`,`roleName`,`roleDesc`,`status`,`rights`,`isDefaultIn`) VALUES 
 (-1,'超级管理员','超级管理员,具有所有权限',1,'__ALL',0),
 (1,'[人事经理]','管理人事的经理',1,'SystemSetting,AppUserView,_AppUserQuery,_AppUserAdd,_AppUserEdit,_AppUserDel,DepartmentView,_DepartmentQuery,_DepartmentAdd,_DepartmentEdit,_DepartmentDel,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,DutyManager,Duty,HolidayRecordView,_HolidayRecordQuery,_HolidayRecordAdd,_HolidayRecordEdit,_HolidayRecordDel,DutySectionView,_DutySectonQuery,_DutySectonAdd,_DutySectonEdit,_DutySectonDel,DutySystemView,_DutySystemQuery,_DutySystemAdd,_DutySystemEdit,_DutySystemDel,DutyView,_DutyQuery,_DutyAdd,_DutyEdit,_DutyDel,DutyRegisterView,_DutyRegisterQuery,_DutyRegisterAdd,_DutyRegisterDel',1),
 (2,'[行政经理]','管理行政',1,'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,Administrator,GoodManeger,OfficeGoodsManageView,_OfficeGoodsQuery,_OfficeGoodsTypeManage,_OfficeGoodsAdd,_OfficeGoodsEdit,_OfficeGoodsDel,InStockView,_InStockQuery,_InStockAdd,_InStockEdit,_InStockDel,GoodsApplyView,_GoodsApplyQuery,_GoodsApplyAdd,_GoodsApplyEdit,_GoodsApplyDel,CarManeger,CarView,_CarQuery,_CarAdd,_CarEdit,_CarDel,CartRepairView,_CarRepairQuery,_CarRepairAdd,_CarRepairEdit,_CarRepairDel,CarApplyView,_CarApplyQuery,_CarApplyAdd,_CarApplyEdit,_CarApplyDel,FixedAssetsManage,DepreTypeView,_DepreTypeQuery,_DepreTypeAdd,_DepreTypeEdit,_DepreTypeDel,FixedAssetsManageView,_FixedAssetsQuery,_AssetsTypeManage,_FixedAssetsAdd,_FixedAssetsEdit,_FixedAssetsDel,_Depreciate,DepreRecordView,_DepreRecordQuery,BookManager,BookTypeView,_BookTypeQuery,_BookTypeAdd,_BookTypeEdit,_BookTypeDel,BookManageView,_BookQuery,_BookAdd,_BookEdit,_BookDel,BookBorrowView,_BookBorrowQuery,_BookBorrowAdd,_BookBorrowEdit,_BookReturn,_BookBorrowDel,BookReturnView,_BookReturnQuery,_BookReturnAdd,_BookReturnEdit,_BookReturnDel',1),
 (3,'[文档管理员]','管理文档',1,'SystemSetting,PublicDocument,NewPublicDocumentForm,DocFolderSharedView,_DocFolderSharedManage,_DocPrivilegeQuery,_DocPrivilegeAdd,_DocPrivilegeEdit,_DocPrivilegeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView',1),
 (4,'[信息管理员]','管理新闻公告等信息',1,'SystemSetting,Task,PlanTypeView,_PlanTypeQuery,_PlanTypeAdd,_PlanTypeEdit,_PlanTypeDel,NewWorkPlanForm,_NewDepPlan,Info,NewsView,_NewsQuery,_NewsAdd,_NewsEdit,_NewsDel,NewsCommentView,_NewsCommentQuery,_NewsCommentDel,NewsTypeView,_NewsTypeQuery,_NewsTypeAdd,_NewsTypeEdit,_NewsTypeDel,NoticeView,_NoticeQuery,_NoticeAdd,_NoticeEdit,_NoticeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView',1),
 (5,'[客户经理]','管理客户信息',1,'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,customer,CustomerView,_CustomerQuery,_CustomerAdd,_CustomerEdit,_CustomerDel,CusLinkmanView,_CusLinkmanQuery,_CusLinkmanAdd,_CusLinkmanEdit,_CusLinkmanDel,CusConnectionView,_CusConnectionQuery,_CusConnectionAdd,_CusConnectionEdit,_CusConnectionDel,ProjectView,_ProjectQuery,_ProjectAdd,_ProjectEdit,_ProjectDel,ContractView,_ContractQuery,_ContractAdd,_ContractEdit,_ContractDel,ProductView,_ProductQuery,_ProductAdd,_ProductEdit,_ProductDel,ProviderView,_ProviderQuery,_ProviderAdd,_ProviderEdit,_ProviderDel',1);
/*!40000 ALTER TABLE `app_role` ENABLE KEYS */;


--
-- Definition of table `app_tips`
--

DROP TABLE IF EXISTS `app_tips`;
CREATE TABLE `app_tips` (
  `tipsId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) NOT NULL,
  `tipsName` varchar(128) default NULL,
  `content` varchar(2048) default NULL,
  `disheight` int(11) default NULL,
  `diswidth` int(11) default NULL,
  `disleft` int(11) default NULL,
  `distop` int(11) default NULL,
  `dislevel` int(11) default NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY  (`tipsId`),
  KEY `FK_AT_R_AP` (`userId`),
  CONSTRAINT `FK_AT_R_AP` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户便签';

--
-- Dumping data for table `app_tips`
--

/*!40000 ALTER TABLE `app_tips` DISABLE KEYS */;
/*!40000 ALTER TABLE `app_tips` ENABLE KEYS */;


--
-- Definition of table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `userId` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `title` smallint(6) NOT NULL COMMENT '1=先生\r\n            0=女士\r\n            小姐',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(128) NOT NULL COMMENT '邮件',
  `depId` bigint(20) default NULL COMMENT '所属部门',
  `position` varchar(32) default NULL COMMENT '职位',
  `phone` varchar(32) default NULL COMMENT '电话',
  `mobile` varchar(32) default NULL COMMENT '手机',
  `fax` varchar(32) default NULL COMMENT '传真',
  `address` varchar(64) default NULL COMMENT '地址',
  `zip` varchar(32) default NULL COMMENT '邮编',
  `photo` varchar(128) default NULL COMMENT '相片',
  `accessionTime` datetime NOT NULL COMMENT '入职时间',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用\r\n            2=离职\r\n            ',
  `education` varchar(64) default NULL,
  `fullname` varchar(50) NOT NULL,
  `delFlag` smallint(6) NOT NULL COMMENT '0=未删除\r\n            1=删除',
  PRIMARY KEY  (`userId`),
  KEY `FK_AU_R_DPT` (`depId`),
  CONSTRAINT `FK_AU_R_DPT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='app_user\r\n用户表';

--
-- Dumping data for table `app_user`
--

/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` (`userId`,`username`,`title`,`password`,`email`,`depId`,`position`,`phone`,`mobile`,`fax`,`address`,`zip`,`photo`,`accessionTime`,`status`,`education`,`fullname`,`delFlag`) VALUES 
 (-1,'system',1,'0','152@163.com',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2009-12-18 00:00:00',0,NULL,'系统',1),
 (1,'admin',1,'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','csx@jee-soft.cn',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2009-12-18 00:00:00',1,NULL,'超级管理员',0),
 (2,'csx',1,'9uCh4qxBlFqap/+KiqoM68EqO8yYGpKa1c+BCgkOEa4=','111@hotmail.com',1,'','','','','','','','2010-05-03 00:00:00',1,NULL,'cwx',0);
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;


--
-- Definition of table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `appointId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) default NULL COMMENT '主键',
  `subject` varchar(128) NOT NULL COMMENT '主题',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime NOT NULL COMMENT '结束时间',
  `content` text NOT NULL COMMENT '约会内容',
  `notes` varchar(1000) default NULL COMMENT '备注',
  `location` varchar(150) NOT NULL COMMENT '地点',
  `inviteEmails` varchar(1000) default NULL,
  PRIMARY KEY  (`appointId`),
  KEY `FK_AP_R_AU` (`userId`),
  CONSTRAINT `FK_AP_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='约会管理';

--
-- Dumping data for table `appointment`
--

/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;


--
-- Definition of table `arch_dispatch`
--

DROP TABLE IF EXISTS `arch_dispatch`;
CREATE TABLE `arch_dispatch` (
  `dispatchId` bigint(20) NOT NULL auto_increment,
  `archivesId` bigint(20) default NULL,
  `dispatchTime` datetime NOT NULL,
  `userId` bigint(20) NOT NULL,
  `fullname` varchar(128) default NULL,
  `isRead` smallint(6) default NULL,
  `subject` varchar(256) default NULL,
  `readFeedback` varchar(1024) default NULL,
  `archUserType` smallint(6) NOT NULL default '0' COMMENT '0=阅读人员\r\n            1=承办人\r\n            2=分发负责人\r\n            ',
  `disRoleId` bigint(20) default NULL,
  `disRoleName` varchar(64) default NULL,
  PRIMARY KEY  (`dispatchId`),
  KEY `FK_AVDH_R_ARV` (`archivesId`),
  CONSTRAINT `FK_AVDH_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `arch_dispatch`
--

/*!40000 ALTER TABLE `arch_dispatch` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_dispatch` ENABLE KEYS */;


--
-- Definition of table `arch_flow_conf`
--

DROP TABLE IF EXISTS `arch_flow_conf`;
CREATE TABLE `arch_flow_conf` (
  `configId` bigint(20) NOT NULL auto_increment,
  `processName` varchar(128) NOT NULL,
  `processDefId` bigint(20) NOT NULL,
  `archType` smallint(6) NOT NULL COMMENT '0=发文\r\n            1=收文',
  PRIMARY KEY  (`configId`),
  KEY `FK_AFC_R_PD` (`processDefId`),
  CONSTRAINT `FK_AFC_R_PD` FOREIGN KEY (`processDefId`) REFERENCES `pro_definition` (`defId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文流程设置';

--
-- Dumping data for table `arch_flow_conf`
--

/*!40000 ALTER TABLE `arch_flow_conf` DISABLE KEYS */;
INSERT INTO `arch_flow_conf` (`configId`,`processName`,`processDefId`,`archType`) VALUES 
 (1,'发文流程',2,0),
 (2,'收文流程',3,1);
/*!40000 ALTER TABLE `arch_flow_conf` ENABLE KEYS */;


--
-- Definition of table `arch_hasten`
--

DROP TABLE IF EXISTS `arch_hasten`;
CREATE TABLE `arch_hasten` (
  `recordId` bigint(20) NOT NULL auto_increment,
  `archivesId` bigint(20) default NULL,
  `content` varchar(1024) default NULL COMMENT '催办内容',
  `createtime` datetime default NULL COMMENT '催办时间',
  `hastenFullname` varchar(64) default NULL COMMENT '催办人',
  `handlerFullname` varchar(64) default NULL COMMENT '承办人',
  `handlerUserId` bigint(20) default NULL COMMENT '承办人ID',
  PRIMARY KEY  (`recordId`),
  KEY `FK_ARHN_R_ARV` (`archivesId`),
  CONSTRAINT `FK_ARHN_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `arch_hasten`
--

/*!40000 ALTER TABLE `arch_hasten` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_hasten` ENABLE KEYS */;


--
-- Definition of table `arch_rec_type`
--

DROP TABLE IF EXISTS `arch_rec_type`;
CREATE TABLE `arch_rec_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  `depId` bigint(20) default NULL,
  PRIMARY KEY  (`typeId`),
  KEY `FK_ART_R_DPT` (`depId`),
  CONSTRAINT `FK_ART_R_DPT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `arch_rec_type`
--

/*!40000 ALTER TABLE `arch_rec_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_rec_type` ENABLE KEYS */;


--
-- Definition of table `arch_rec_user`
--

DROP TABLE IF EXISTS `arch_rec_user`;
CREATE TABLE `arch_rec_user` (
  `archRecId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) NOT NULL,
  `fullname` varchar(128) NOT NULL COMMENT '用户名',
  `depId` bigint(20) NOT NULL COMMENT '部门ID ',
  `depName` varchar(128) NOT NULL COMMENT '部门名称',
  PRIMARY KEY  (`archRecId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `arch_rec_user`
--

/*!40000 ALTER TABLE `arch_rec_user` DISABLE KEYS */;
INSERT INTO `arch_rec_user` (`archRecId`,`userId`,`fullname`,`depId`,`depName`) VALUES 
 (1,1,'超级管理员',1,'信息部门');
/*!40000 ALTER TABLE `arch_rec_user` ENABLE KEYS */;


--
-- Definition of table `arch_template`
--

DROP TABLE IF EXISTS `arch_template`;
CREATE TABLE `arch_template` (
  `templateId` bigint(20) NOT NULL auto_increment,
  `typeId` bigint(20) default NULL COMMENT '所属类型',
  `tempName` varchar(128) NOT NULL COMMENT '模板名称',
  `tempPath` varchar(256) NOT NULL COMMENT '路径',
  `fileId` bigint(20) NOT NULL COMMENT '文件ID',
  PRIMARY KEY  (`templateId`),
  KEY `FK_AHT_R_FA` (`fileId`),
  KEY `FK_ART_R_ARVT` (`typeId`),
  CONSTRAINT `FK_AHT_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_ART_R_ARVT` FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文模板';

--
-- Dumping data for table `arch_template`
--

/*!40000 ALTER TABLE `arch_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `arch_template` ENABLE KEYS */;


--
-- Definition of table `archives`
--

DROP TABLE IF EXISTS `archives`;
CREATE TABLE `archives` (
  `archivesId` bigint(20) NOT NULL auto_increment,
  `typeId` bigint(20) default NULL COMMENT '公文类型',
  `typeName` varchar(128) default NULL COMMENT '公文类型名称',
  `archivesNo` varchar(100) NOT NULL COMMENT '发文字号',
  `issueDep` varchar(128) default NULL COMMENT '发文机关或部门',
  `depId` bigint(20) default NULL COMMENT '发文部门ID',
  `arc_typeId` bigint(20) default NULL,
  `subject` varchar(256) NOT NULL COMMENT '文件标题',
  `createtime` datetime NOT NULL,
  `issueDate` datetime NOT NULL COMMENT '发布日期',
  `status` smallint(6) NOT NULL COMMENT '公文状态\r\n            0=拟稿、修改状态\r\n            1=发文状态\r\n            2=归档状态',
  `shortContent` varchar(1024) default NULL COMMENT '内容简介',
  `fileCounts` int(11) NOT NULL default '0' COMMENT '文件数',
  `privacyLevel` varchar(50) default '普通' COMMENT '秘密等级\r\n            普通\r\n            秘密\r\n            机密\r\n            绝密',
  `urgentLevel` varchar(50) default '普通' COMMENT '紧急程度\r\n            普通\r\n            紧急\r\n            特急\r\n            特提',
  `issuer` varchar(50) default NULL COMMENT '发文人',
  `issuerId` bigint(20) default NULL COMMENT '发文人ID',
  `keywords` varchar(256) default NULL COMMENT '主题词',
  `sources` varchar(50) default NULL COMMENT '公文来源\r\n            仅在收文中指定，发公文不需要指定\r\n            上级公文\r\n            下级公文',
  `archType` smallint(6) NOT NULL default '0' COMMENT '0=发文\r\n            1=收文',
  `recDepIds` varchar(2000) default NULL COMMENT '用于存储接收公文的部门ID,使用,进行分开',
  `recDepNames` varchar(2000) default NULL COMMENT '用于存储接收公文的部门的名称，使用,进行分开',
  `handlerUids` varchar(256) default NULL COMMENT '在收文中使用，多个用户ID用'',''分割',
  `handlerUnames` varchar(256) default NULL COMMENT '用于收文，存储多个拟办用户名，用‘，’分割',
  `orgArchivesId` bigint(20) default NULL COMMENT '用于收文时使用，指向原公文ID',
  `depSignNo` varchar(100) default NULL COMMENT '用于收文时，部门对自身的公文自编号',
  PRIMARY KEY  (`archivesId`),
  KEY `FK_ARV_R_ART` (`arc_typeId`),
  KEY `FK_ARV_R_ARVT` (`typeId`),
  KEY `FK_ARV_R_DPT` (`depId`),
  CONSTRAINT `FK_ARV_R_ART` FOREIGN KEY (`arc_typeId`) REFERENCES `arch_rec_type` (`typeId`),
  CONSTRAINT `FK_ARV_R_ARVT` FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`),
  CONSTRAINT `FK_ARV_R_DPT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收发公文';

--
-- Dumping data for table `archives`
--

/*!40000 ALTER TABLE `archives` DISABLE KEYS */;
/*!40000 ALTER TABLE `archives` ENABLE KEYS */;


--
-- Definition of table `archives_attend`
--

DROP TABLE IF EXISTS `archives_attend`;
CREATE TABLE `archives_attend` (
  `attendId` bigint(20) NOT NULL auto_increment,
  `archivesId` bigint(20) NOT NULL,
  `userID` bigint(20) NOT NULL COMMENT '用户ID',
  `fullname` varchar(128) NOT NULL COMMENT '姓名',
  `attendType` varchar(64) NOT NULL COMMENT '参与类型\r\n            1=校对人\r\n            2=审核人\r\n            3=缮印人\r\n            4=用印人',
  `executeTime` datetime default NULL COMMENT '执行时间',
  `memo` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`attendId`),
  KEY `FK_ARVA_R_ARV` (`archivesId`),
  CONSTRAINT `FK_ARVA_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发文拟稿参与人';

--
-- Dumping data for table `archives_attend`
--

/*!40000 ALTER TABLE `archives_attend` DISABLE KEYS */;
/*!40000 ALTER TABLE `archives_attend` ENABLE KEYS */;


--
-- Definition of table `archives_dep`
--

DROP TABLE IF EXISTS `archives_dep`;
CREATE TABLE `archives_dep` (
  `archDepId` bigint(20) NOT NULL auto_increment COMMENT '主键',
  `signNo` varchar(128) default NULL COMMENT '自编号',
  `depId` bigint(20) NOT NULL COMMENT '收文部门',
  `archivesId` bigint(20) NOT NULL COMMENT '所属公文',
  `subject` varchar(256) NOT NULL COMMENT '公文标题',
  `status` smallint(6) NOT NULL COMMENT '签收状态\r\n            0=未签收\r\n            1=已签收',
  `signTime` datetime default NULL COMMENT '签收日期',
  `signFullname` varchar(64) default NULL COMMENT '签收人',
  `signUserID` bigint(20) default NULL,
  `handleFeedback` varchar(1024) default NULL COMMENT '办理结果反馈',
  `isMain` smallint(6) NOT NULL default '1' COMMENT '主送、抄送\r\n            1=主送\r\n            0=抄送',
  PRIMARY KEY  (`archDepId`),
  KEY `FK_AVD_R_ARV` (`archivesId`),
  KEY `FK_AVD_R_DPT` (`depId`),
  CONSTRAINT `FK_AVD_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`),
  CONSTRAINT `FK_AVD_R_DPT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `archives_dep`
--

/*!40000 ALTER TABLE `archives_dep` DISABLE KEYS */;
/*!40000 ALTER TABLE `archives_dep` ENABLE KEYS */;


--
-- Definition of table `archives_doc`
--

DROP TABLE IF EXISTS `archives_doc`;
CREATE TABLE `archives_doc` (
  `docId` bigint(20) NOT NULL auto_increment,
  `fileId` bigint(20) default NULL,
  `archivesId` bigint(20) default NULL,
  `creator` varchar(64) default NULL COMMENT '拟稿人',
  `creatorId` bigint(20) default NULL COMMENT '拟稿人ID',
  `menderId` bigint(20) default NULL,
  `mender` varchar(64) default NULL COMMENT '修改人',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `docStatus` smallint(6) NOT NULL COMMENT '文档状态\r\n            0=修改中\r\n            1=修改完成',
  `curVersion` int(11) NOT NULL COMMENT '当前版本\r\n            取当前最新的版本',
  `docPath` varchar(128) NOT NULL COMMENT '文档路径',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY  (`docId`),
  KEY `FK_ARVC_R_FA` (`fileId`),
  KEY `FK_ARVD_R_ARV` (`archivesId`),
  CONSTRAINT `FK_ARVC_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_ARVD_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `archives_doc`
--

/*!40000 ALTER TABLE `archives_doc` DISABLE KEYS */;
/*!40000 ALTER TABLE `archives_doc` ENABLE KEYS */;


--
-- Definition of table `archives_handle`
--

DROP TABLE IF EXISTS `archives_handle`;
CREATE TABLE `archives_handle` (
  `handleId` bigint(20) NOT NULL auto_increment,
  `archivesId` bigint(20) NOT NULL,
  `handleOpinion` varchar(1024) default NULL,
  `userId` bigint(20) NOT NULL,
  `userFullname` varchar(128) NOT NULL,
  `createtime` datetime NOT NULL,
  `fillTime` datetime default NULL,
  `isPass` smallint(6) NOT NULL default '1' COMMENT '0=尚未审批\r\n            1=通过审批\r\n            ２=未通过审批',
  PRIMARY KEY  (`handleId`),
  KEY `FK_AVHD_R_ARV` (`archivesId`),
  CONSTRAINT `FK_AVHD_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文拟办人';

--
-- Dumping data for table `archives_handle`
--

/*!40000 ALTER TABLE `archives_handle` DISABLE KEYS */;
/*!40000 ALTER TABLE `archives_handle` ENABLE KEYS */;


--
-- Definition of table `archives_type`
--

DROP TABLE IF EXISTS `archives_type`;
CREATE TABLE `archives_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(256) default NULL COMMENT '类型描述',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文类型';

--
-- Dumping data for table `archives_type`
--

/*!40000 ALTER TABLE `archives_type` DISABLE KEYS */;
INSERT INTO `archives_type` (`typeId`,`typeName`,`typeDesc`) VALUES 
 (1,'文件内容',''),
 (2,'三星','三源');
/*!40000 ALTER TABLE `archives_type` ENABLE KEYS */;


--
-- Definition of table `assets_type`
--

DROP TABLE IF EXISTS `assets_type`;
CREATE TABLE `assets_type` (
  `assetsTypeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY  (`assetsTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assets_type`
--

/*!40000 ALTER TABLE `assets_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `assets_type` ENABLE KEYS */;


--
-- Definition of table `book`
--

DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookId` bigint(20) NOT NULL auto_increment,
  `typeId` bigint(20) default NULL,
  `bookName` varchar(128) NOT NULL,
  `author` varchar(128) NOT NULL,
  `isbn` varchar(64) NOT NULL,
  `publisher` varchar(128) default NULL,
  `price` decimal(10,0) NOT NULL,
  `location` varchar(128) NOT NULL,
  `department` varchar(64) NOT NULL,
  `amount` int(11) NOT NULL,
  `leftAmount` int(11) NOT NULL,
  PRIMARY KEY  (`bookId`),
  KEY `FK_BK_R_BT` (`typeId`),
  CONSTRAINT `FK_BK_R_BT` FOREIGN KEY (`typeId`) REFERENCES `book_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书';

--
-- Dumping data for table `book`
--

/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;


--
-- Definition of table `book_bor_ret`
--

DROP TABLE IF EXISTS `book_bor_ret`;
CREATE TABLE `book_bor_ret` (
  `recordId` bigint(20) NOT NULL auto_increment,
  `bookSnId` bigint(20) default NULL,
  `borrowTime` datetime NOT NULL,
  `returnTime` datetime NOT NULL,
  `lastReturnTime` datetime default NULL,
  `borrowIsbn` varchar(128) NOT NULL,
  `bookName` varchar(128) NOT NULL,
  `registerName` varchar(32) NOT NULL,
  `fullname` varchar(32) NOT NULL,
  PRIMARY KEY  (`recordId`),
  KEY `FK_BBR_R_BS` (`bookSnId`),
  CONSTRAINT `FK_BBR_R_BS` FOREIGN KEY (`bookSnId`) REFERENCES `book_sn` (`bookSnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书借还表';

--
-- Dumping data for table `book_bor_ret`
--

/*!40000 ALTER TABLE `book_bor_ret` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_bor_ret` ENABLE KEYS */;


--
-- Definition of table `book_sn`
--

DROP TABLE IF EXISTS `book_sn`;
CREATE TABLE `book_sn` (
  `bookSnId` bigint(20) NOT NULL auto_increment,
  `bookId` bigint(20) NOT NULL,
  `bookSN` varchar(128) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '借阅状态\r\n            0=未借出\r\n            1=借出\r\n            2=预订\r\n            3=注销',
  PRIMARY KEY  (`bookSnId`),
  KEY `FK_BS_R_BK` (`bookId`),
  CONSTRAINT `FK_BS_R_BK` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `book_sn`
--

/*!40000 ALTER TABLE `book_sn` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_sn` ENABLE KEYS */;


--
-- Definition of table `book_type`
--

DROP TABLE IF EXISTS `book_type`;
CREATE TABLE `book_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL,
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书类别';

--
-- Dumping data for table `book_type`
--

/*!40000 ALTER TABLE `book_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `book_type` ENABLE KEYS */;


--
-- Definition of table `cal_file`
--

DROP TABLE IF EXISTS `cal_file`;
CREATE TABLE `cal_file` (
  `fileId` bigint(20) NOT NULL,
  `planId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`planId`),
  KEY `FK_CF_R_CP` (`planId`),
  CONSTRAINT `FK_CF_R_CP` FOREIGN KEY (`planId`) REFERENCES `calendar_plan` (`planId`),
  CONSTRAINT `FK_CF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cal_file`
--

/*!40000 ALTER TABLE `cal_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `cal_file` ENABLE KEYS */;


--
-- Definition of table `calendar_plan`
--

DROP TABLE IF EXISTS `calendar_plan`;
CREATE TABLE `calendar_plan` (
  `planId` bigint(20) NOT NULL auto_increment,
  `startTime` datetime default NULL COMMENT '开始时间',
  `endTime` datetime default NULL COMMENT '结束时间',
  `urgent` smallint(6) NOT NULL COMMENT '紧急程度\r\n            0=一般\r\n            1=重要\r\n            2=紧急',
  `content` varchar(1200) NOT NULL COMMENT '内容',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            0=未完成\r\n            1=完成',
  `userId` bigint(20) NOT NULL COMMENT '员工ID',
  `fullname` varchar(32) default NULL COMMENT '员工名',
  `assignerId` bigint(20) NOT NULL COMMENT '分配人',
  `assignerName` varchar(32) default NULL COMMENT '分配人名',
  `feedback` varchar(500) default NULL COMMENT '反馈意见',
  `showStyle` smallint(6) NOT NULL COMMENT '显示方式\r\n            1=仅在任务中显示\r\n            2=在日程与任务中显示',
  `taskType` smallint(6) NOT NULL COMMENT '任务类型\r\n            1=限期任务\r\n            2=非限期任务',
  PRIMARY KEY  (`planId`),
  KEY `FK_CA_R_AU` (`userId`),
  KEY `FK_CP_R_AUAS` (`assignerId`),
  CONSTRAINT `FK_CA_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_CP_R_AUAS` FOREIGN KEY (`assignerId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程安排';

--
-- Dumping data for table `calendar_plan`
--

/*!40000 ALTER TABLE `calendar_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `calendar_plan` ENABLE KEYS */;


--
-- Definition of table `car`
--

DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `carId` bigint(20) NOT NULL auto_increment,
  `carNo` varchar(128) NOT NULL,
  `carType` varchar(64) NOT NULL COMMENT '轿车\r\n            货车\r\n            商务车\r\n            ',
  `engineNo` varchar(128) default NULL,
  `buyInsureTime` datetime default NULL COMMENT '购买保险时间',
  `auditTime` datetime default NULL COMMENT '年审时间',
  `notes` varchar(500) default NULL,
  `factoryModel` varchar(64) NOT NULL,
  `driver` varchar(32) NOT NULL,
  `buyDate` date NOT NULL COMMENT '购置日期',
  `status` smallint(6) NOT NULL COMMENT '当前状态\r\n            1=可用\r\n            2=维修中\r\n            0=报废',
  `cartImage` varchar(128) default NULL,
  PRIMARY KEY  (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆信息';

--
-- Dumping data for table `car`
--

/*!40000 ALTER TABLE `car` DISABLE KEYS */;
/*!40000 ALTER TABLE `car` ENABLE KEYS */;


--
-- Definition of table `car_apply`
--

DROP TABLE IF EXISTS `car_apply`;
CREATE TABLE `car_apply` (
  `applyId` bigint(20) NOT NULL auto_increment,
  `carId` bigint(20) NOT NULL,
  `department` varchar(64) NOT NULL,
  `userFullname` varchar(32) NOT NULL,
  `applyDate` date NOT NULL,
  `reason` varchar(512) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime default NULL,
  `userId` bigint(20) NOT NULL,
  `proposer` varchar(32) NOT NULL,
  `mileage` decimal(18,2) default NULL,
  `oilUse` decimal(18,2) default NULL,
  `notes` varchar(128) default NULL,
  `approvalStatus` smallint(6) NOT NULL,
  PRIMARY KEY  (`applyId`),
  KEY `FK_CRA_R_CR` (`carId`),
  CONSTRAINT `FK_CRA_R_CR` FOREIGN KEY (`carId`) REFERENCES `car` (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆申请';

--
-- Dumping data for table `car_apply`
--

/*!40000 ALTER TABLE `car_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `car_apply` ENABLE KEYS */;


--
-- Definition of table `cart_repair`
--

DROP TABLE IF EXISTS `cart_repair`;
CREATE TABLE `cart_repair` (
  `repairId` bigint(20) NOT NULL auto_increment,
  `carId` bigint(20) default NULL,
  `repairDate` datetime NOT NULL COMMENT '维护日期',
  `reason` varchar(128) NOT NULL COMMENT '维护原因',
  `executant` varchar(128) NOT NULL COMMENT '经办人',
  `notes` varchar(128) default NULL COMMENT '备注',
  `repairType` varchar(128) NOT NULL COMMENT '维修类型\r\n            保养\r\n            维修',
  `fee` decimal(18,2) default NULL COMMENT '费用',
  PRIMARY KEY  (`repairId`),
  KEY `FK_CRR_R_CR` (`carId`),
  CONSTRAINT `FK_CRR_R_CR` FOREIGN KEY (`carId`) REFERENCES `car` (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cart_repair`
--

/*!40000 ALTER TABLE `cart_repair` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_repair` ENABLE KEYS */;


--
-- Definition of table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `companyId` bigint(20) NOT NULL auto_increment,
  `companyNo` varchar(128) default NULL,
  `companyName` varchar(128) NOT NULL,
  `companyDesc` varchar(4000) default NULL,
  `legalPerson` varchar(32) default NULL,
  `setup` datetime default NULL,
  `phone` varchar(32) default NULL,
  `fax` varchar(32) default NULL,
  `site` varchar(128) default NULL,
  `logo` varchar(128) default NULL,
  PRIMARY KEY  (`companyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司信息';

--
-- Dumping data for table `company`
--

/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`companyId`,`companyNo`,`companyName`,`companyDesc`,`legalPerson`,`setup`,`phone`,`fax`,`site`,`logo`) VALUES 
 (1,'','广州宏天软件有限公司','<br>​','',NULL,'','','','');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;


--
-- Definition of table `contract`
--

DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contractId` bigint(20) NOT NULL auto_increment,
  `contractNo` varchar(64) NOT NULL COMMENT '合同编号',
  `subject` varchar(128) NOT NULL COMMENT '合同标题',
  `contractAmount` decimal(10,0) NOT NULL COMMENT '合同金额',
  `mainItem` varchar(4000) default NULL COMMENT '主要条款',
  `salesAfterItem` varchar(4000) default NULL COMMENT '售后条款',
  `validDate` datetime NOT NULL COMMENT '生效日期',
  `expireDate` datetime NOT NULL COMMENT '有效期',
  `serviceDep` varchar(64) default NULL COMMENT '维修部门',
  `serviceMan` varchar(64) default NULL COMMENT '维修负责人',
  `signupUser` varchar(64) NOT NULL COMMENT '签约人',
  `signupTime` datetime NOT NULL COMMENT '签约时间',
  `creator` varchar(32) NOT NULL COMMENT '录入人',
  `createtime` datetime NOT NULL COMMENT '录入时间',
  `projectId` bigint(20) default NULL COMMENT '所属项目',
  `consignAddress` varchar(128) default NULL COMMENT '收货地址',
  `consignee` varchar(32) default NULL COMMENT '收货人',
  PRIMARY KEY  (`contractId`),
  KEY `FK_CT_R_PT` (`projectId`),
  CONSTRAINT `FK_CT_R_PT` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `contract`
--

/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;


--
-- Definition of table `contract_config`
--

DROP TABLE IF EXISTS `contract_config`;
CREATE TABLE `contract_config` (
  `configId` bigint(20) NOT NULL auto_increment,
  `itemName` varchar(128) NOT NULL COMMENT '设备名称',
  `contractId` bigint(20) default NULL,
  `itemSpec` varchar(128) NOT NULL COMMENT '设置规格',
  `amount` decimal(18,2) NOT NULL COMMENT '数量',
  `notes` varchar(200) default NULL COMMENT '备注',
  PRIMARY KEY  (`configId`),
  KEY `FK_CC_R_CT` (`contractId`),
  CONSTRAINT `FK_CC_R_CT` FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同配置单';

--
-- Dumping data for table `contract_config`
--

/*!40000 ALTER TABLE `contract_config` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_config` ENABLE KEYS */;


--
-- Definition of table `contract_file`
--

DROP TABLE IF EXISTS `contract_file`;
CREATE TABLE `contract_file` (
  `fileId` bigint(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`contractId`),
  KEY `FK_CTF_R_CT` (`contractId`),
  CONSTRAINT `FK_CTF_R_CT` FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`),
  CONSTRAINT `FK_CTF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同附件';

--
-- Dumping data for table `contract_file`
--

/*!40000 ALTER TABLE `contract_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `contract_file` ENABLE KEYS */;


--
-- Definition of table `cus_connection`
--

DROP TABLE IF EXISTS `cus_connection`;
CREATE TABLE `cus_connection` (
  `connId` bigint(20) NOT NULL auto_increment,
  `customerId` bigint(20) NOT NULL,
  `contactor` varchar(32) NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `content` varchar(512) NOT NULL,
  `notes` varchar(1000) default NULL,
  `creator` varchar(32) default NULL,
  PRIMARY KEY  (`connId`),
  KEY `FK_CC_R_CS` (`customerId`),
  CONSTRAINT `FK_CC_R_CS` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='business connection ';

--
-- Dumping data for table `cus_connection`
--

/*!40000 ALTER TABLE `cus_connection` DISABLE KEYS */;
/*!40000 ALTER TABLE `cus_connection` ENABLE KEYS */;


--
-- Definition of table `cus_linkman`
--

DROP TABLE IF EXISTS `cus_linkman`;
CREATE TABLE `cus_linkman` (
  `linkmanId` bigint(20) NOT NULL auto_increment,
  `customerId` bigint(20) NOT NULL COMMENT '所属客户',
  `fullname` varchar(32) NOT NULL COMMENT '姓名',
  `sex` smallint(6) NOT NULL COMMENT '性别',
  `position` varchar(32) default NULL COMMENT '职位',
  `phone` varchar(32) default NULL COMMENT '电话',
  `mobile` varchar(32) NOT NULL COMMENT '手机',
  `fax` varchar(32) default NULL,
  `email` varchar(100) default NULL COMMENT 'Email',
  `msn` varchar(100) default NULL COMMENT 'MSN',
  `qq` varchar(64) default NULL COMMENT 'QQ',
  `birthday` date default NULL COMMENT '生日',
  `homeAddress` varchar(128) default NULL COMMENT '家庭住址',
  `homeZip` varchar(32) default NULL COMMENT '邮编',
  `homePhone` varchar(32) default NULL COMMENT '家庭电话',
  `hobby` varchar(100) default NULL COMMENT '爱好',
  `isPrimary` smallint(6) NOT NULL COMMENT '是否为主要联系人',
  `notes` varchar(500) default NULL COMMENT '备注',
  PRIMARY KEY  (`linkmanId`),
  KEY `FK_CLM_R_CS` (`customerId`),
  CONSTRAINT `FK_CLM_R_CS` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系人';

--
-- Dumping data for table `cus_linkman`
--

/*!40000 ALTER TABLE `cus_linkman` DISABLE KEYS */;
/*!40000 ALTER TABLE `cus_linkman` ENABLE KEYS */;


--
-- Definition of table `customer`
--

DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customerId` bigint(20) NOT NULL auto_increment,
  `customerNo` varchar(64) NOT NULL COMMENT '客户号\r\n            自动生成',
  `industryType` varchar(64) NOT NULL COMMENT '所属行业\r\n            有缺省的选择，也可以输入',
  `customerSource` varchar(64) default NULL COMMENT '客户来源\r\n            可编辑，可添加\r\n            \r\n            电话访问\r\n            网络\r\n            客户或朋友介绍',
  `customerType` smallint(6) NOT NULL COMMENT '1=正式客户\r\n            2=重要客户\r\n            3＝潜在客户\r\n            4＝无效客户',
  `companyScale` int(11) default NULL COMMENT '1=1-20人\r\n            2=20-50人\r\n            3=50-100人\r\n            4=100-200人\r\n            5=200-500人\r\n            6=500-1000 人\r\n            7=1000人以上\r\n            ',
  `customerName` varchar(128) NOT NULL COMMENT '客户名称\r\n            一般为公司名称',
  `customerManager` varchar(32) NOT NULL COMMENT '负责该客户的经理',
  `phone` varchar(32) NOT NULL COMMENT '电话',
  `fax` varchar(32) default NULL,
  `site` varchar(128) default NULL,
  `email` varchar(128) default NULL,
  `state` varchar(32) default NULL,
  `city` varchar(32) default NULL,
  `zip` varchar(32) default NULL,
  `address` varchar(100) default NULL,
  `registerFun` decimal(10,0) default NULL,
  `turnOver` decimal(10,0) default NULL,
  `currencyUnit` varchar(32) default NULL COMMENT '注册资金及年营业额的货币单位\r\n            可选可编辑\r\n            人民币（默认）\r\n            美元\r\n            ',
  `otherDesc` varchar(800) default NULL,
  `principal` varchar(32) default NULL,
  `openBank` varchar(64) default NULL,
  `accountsNo` varchar(64) default NULL,
  `taxNo` varchar(64) default NULL,
  `notes` varchar(500) default NULL,
  `rights` smallint(6) NOT NULL COMMENT '1=客户经理及上级经理有权查看\r\n            2=公开\r\n            3=共享人员有权查看',
  PRIMARY KEY  (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息';

--
-- Dumping data for table `customer`
--

/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;


--
-- Definition of table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `depId` bigint(20) NOT NULL auto_increment,
  `depName` varchar(128) NOT NULL COMMENT '部门名称',
  `depDesc` varchar(256) default NULL COMMENT '部门描述',
  `depLevel` int(11) NOT NULL COMMENT '层次',
  `parentId` bigint(20) default NULL,
  `path` varchar(128) default NULL COMMENT '路径',
  `phone` varchar(64) default NULL,
  `fax` varchar(64) default NULL,
  PRIMARY KEY  (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` (`depId`,`depName`,`depDesc`,`depLevel`,`parentId`,`path`,`phone`,`fax`) VALUES 
 (1,'信息部门','维护系统',2,0,'0.1.',NULL,NULL);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;


--
-- Definition of table `depre_record`
--

DROP TABLE IF EXISTS `depre_record`;
CREATE TABLE `depre_record` (
  `recordId` bigint(20) NOT NULL auto_increment,
  `assetsId` bigint(20) NOT NULL,
  `workCapacity` decimal(18,2) default NULL,
  `workGrossUnit` varchar(128) default NULL,
  `depreAmount` decimal(18,4) NOT NULL,
  `calTime` datetime NOT NULL,
  PRIMARY KEY  (`recordId`),
  KEY `FK_DR_R_FA` (`assetsId`),
  CONSTRAINT `FK_DR_R_FA` FOREIGN KEY (`assetsId`) REFERENCES `fixed_assets` (`assetsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `depre_record`
--

/*!40000 ALTER TABLE `depre_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `depre_record` ENABLE KEYS */;


--
-- Definition of table `depre_type`
--

DROP TABLE IF EXISTS `depre_type`;
CREATE TABLE `depre_type` (
  `depreTypeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(64) NOT NULL,
  `deprePeriod` int(11) NOT NULL COMMENT '单位为月',
  `typeDesc` varchar(500) default NULL,
  `calMethod` smallint(6) NOT NULL COMMENT '1=平均年限法\r\n            2=工作量法\r\n            加速折旧法\r\n            3=双倍余额递减法\r\n            4=年数总和法',
  PRIMARY KEY  (`depreTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='depreciation type';

--
-- Dumping data for table `depre_type`
--

/*!40000 ALTER TABLE `depre_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `depre_type` ENABLE KEYS */;


--
-- Definition of table `diary`
--

DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary` (
  `diaryId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) default NULL COMMENT '主键',
  `dayTime` date NOT NULL,
  `content` text NOT NULL,
  `diaryType` smallint(6) NOT NULL COMMENT '1=工作日志\r\n            0=个人日志',
  PRIMARY KEY  (`diaryId`),
  KEY `FK_DY_R_AU` (`userId`),
  CONSTRAINT `FK_DY_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `diary`
--

/*!40000 ALTER TABLE `diary` DISABLE KEYS */;
/*!40000 ALTER TABLE `diary` ENABLE KEYS */;


--
-- Definition of table `dictionary`
--

DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `dicId` bigint(20) NOT NULL auto_increment,
  `itemName` varchar(64) NOT NULL,
  `itemValue` varchar(128) NOT NULL,
  `descp` varchar(256) default NULL,
  PRIMARY KEY  (`dicId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

--
-- Dumping data for table `dictionary`
--

/*!40000 ALTER TABLE `dictionary` DISABLE KEYS */;
INSERT INTO `dictionary` (`dicId`,`itemName`,`itemValue`,`descp`) VALUES 
 (1,'宗教信仰','佛教',NULL),
 (2,'宗教信仰','道教',NULL),
 (3,'宗教信仰','基督宗教',NULL),
 (4,'宗教信仰','天主教',NULL),
 (5,'宗教信仰','伊斯兰教',NULL),
 (6,'宗教信仰','犹太教',NULL),
 (7,'宗教信仰','孔教',NULL),
 (8,'宗教信仰','神道教',NULL),
 (9,'宗教信仰','耆那教',NULL),
 (10,'宗教信仰','印度教',NULL),
 (11,'宗教信仰','东正教',NULL),
 (12,'宗教信仰','新教',NULL),
 (13,'宗教信仰','锡克教',NULL),
 (14,'宗教信仰','琐罗亚斯德教',NULL),
 (15,'宗教信仰','巴哈伊教',NULL),
 (16,'宗教信仰','其它',NULL),
 (17,'民族','汉族',NULL),
 (18,'民族','阿昌族',NULL),
 (19,'民族','白族',NULL),
 (20,'民族','保安族',NULL),
 (21,'民族','布朗族',NULL),
 (22,'民族','布依族',NULL),
 (23,'民族','朝鲜族',NULL),
 (24,'民族','达斡族',NULL),
 (25,'民族','傣族',NULL),
 (26,'民族','德昂族',NULL),
 (27,'民族','侗族',NULL),
 (28,'民族','东乡族',NULL),
 (29,'民族','独龙族',NULL),
 (30,'民族','鄂伦春族',NULL),
 (31,'民族','俄罗斯族',NULL),
 (32,'民族','鄂温克族',NULL),
 (33,'民族','高山族',NULL),
 (34,'民族','仡佬族',NULL),
 (35,'民族','哈尼族',NULL),
 (36,'民族','啥萨克族',NULL),
 (37,'民族','赫哲族',NULL),
 (38,'民族','回族',NULL),
 (39,'民族','基诺族',NULL),
 (40,'民族','京族',NULL),
 (41,'民族','景颇族',NULL),
 (42,'民族','柯尔克孜族',NULL),
 (43,'民族','拉祜族',NULL),
 (44,'民族','黎族',NULL),
 (45,'民族','僳僳族',NULL),
 (46,'民族','珞巴族',NULL),
 (47,'民族','满族',NULL),
 (48,'民族','毛南族',NULL),
 (49,'民族','门巴族',NULL),
 (50,'民族','蒙古族',NULL),
 (51,'民族','苗族',NULL),
 (52,'民族','仫佬族',NULL),
 (53,'民族','纳西族',NULL),
 (54,'民族','怒族',NULL),
 (55,'民族','普米族',NULL),
 (56,'民族','羌族',NULL),
 (57,'民族','撒拉族',NULL),
 (58,'民族','畲族',NULL),
 (59,'民族','水族',NULL),
 (60,'民族','塔吉克族',NULL),
 (61,'民族','塔塔尔族',NULL),
 (62,'民族','土族',NULL),
 (63,'民族','土家族',NULL),
 (64,'民族','佤族',NULL),
 (65,'民族','维吾尔族',NULL),
 (66,'民族','乌孜别克族',NULL),
 (67,'民族','锡伯族',NULL),
 (68,'民族','瑶族',NULL),
 (69,'民族','彝族',NULL),
 (70,'民族','藏族',NULL),
 (71,'民族','壮族',NULL),
 (72,'学历','博士',NULL),
 (73,'学历','研究生',NULL),
 (74,'学历','本科',NULL),
 (75,'学历','大专',NULL),
 (76,'学历','中专',NULL),
 (77,'学历','初中',NULL),
 (78,'学历','小学',NULL),
 (79,'学历','其它',NULL),
 (80,'政治面貌','群众',NULL),
 (81,'政治面貌','共青团员',NULL),
 (82,'政治面貌','中共党员',NULL),
 (83,'国籍','中华人民共和国',NULL),
 (84,'国籍','美国',NULL),
 (85,'国籍','俄罗斯',NULL),
 (86,'国籍','日本',NULL),
 (87,'国籍','韩国',NULL),
 (88,'国籍','新加波',NULL),
 (89,'国籍','马来西亚',NULL),
 (90,'国籍','英国',NULL),
 (91,'国籍','德国',NULL),
 (92,'国籍','意大利',NULL),
 (93,'国籍','澳大利亚',NULL),
 (94,'国籍','巴西',NULL),
 (95,'专业','管理科学',NULL),
 (96,'专业','信息管理和信息系统',NULL),
 (97,'专业','工业工程',NULL),
 (98,'专业','工程管理',NULL),
 (99,'专业','农业经理管理',NULL),
 (100,'专业','工商管理',NULL),
 (101,'专业','市场营销',NULL),
 (102,'专业','会计学',NULL),
 (103,'专业','涉外会计',NULL),
 (104,'专业','会计电算化',NULL),
 (105,'专业','财政金融',NULL),
 (106,'专业','财务管理',NULL),
 (107,'专业','技术经济',NULL),
 (108,'专业','文秘',NULL),
 (109,'专业','国际商务',NULL),
 (110,'专业','物流管理',NULL),
 (111,'专业','行政管理',NULL),
 (112,'专业','公共事业管理',NULL),
 (113,'专业','旅游管理',NULL),
 (114,'专业','宾馆/酒店管理',NULL),
 (115,'专业','人力资源管理',NULL),
 (116,'专业','公共关系学',NULL),
 (117,'专业','物业管理',NULL),
 (118,'专业','房地产经营管理',NULL),
 (119,'专业','劳动与社会保障',NULL),
 (120,'专业','土地资源管理',NULL),
 (121,'专业','图书档案学',NULL),
 (122,'专业','计算机科学与技术',NULL),
 (123,'专业','计算机应用',NULL),
 (124,'专业','计算机信息管理',NULL),
 (125,'专业','计算机网络',NULL),
 (126,'专业','电子商务',NULL),
 (127,'专业','通信工程',NULL),
 (128,'专业','电气工程及其自动化',NULL),
 (129,'专业','软件工程',NULL),
 (130,'专业','自动化',NULL),
 (131,'专业','电子信息工程',NULL),
 (132,'专业','电子科学与技术',NULL),
 (133,'专业','电子信息科学与技术',NULL),
 (134,'专业','微电子学',NULL),
 (135,'专业','光信息科学与技术',NULL),
 (136,'专业','机械设计制造及其自动化',NULL),
 (137,'专业','材料成型及控制工程',NULL),
 (138,'专业','工业设计',NULL),
 (139,'专业','过程装备与控制工程',NULL),
 (140,'专业','机械电子工程/机电一体化',NULL),
 (141,'专业','模具设计与制造',NULL),
 (142,'专业','机械制造工艺与设备',NULL),
 (143,'专业','测控技术与仪器',NULL),
 (144,'专业','热能与动力工程',NULL),
 (145,'专业','核工程与核技术',NULL),
 (146,'专业','电力系统及自动化',NULL),
 (147,'专业','制冷与低温技术',NULL),
 (148,'专业','冶金工程',NULL),
 (149,'专业','金属材料工程',NULL),
 (150,'专业','无机非金属料工程',NULL),
 (151,'专业','高分子材料与工程',NULL),
 (152,'专业','材料物理',NULL),
 (153,'专业','材料化学',NULL),
 (154,'专业','材料科学与工程',NULL),
 (155,'专业','食品科学与工程',NULL),
 (156,'专业','轻化工程',NULL),
 (157,'专业','包装工程',NULL),
 (158,'专业','印刷工程',NULL),
 (159,'专业','纺织工程',NULL),
 (160,'专业','服装设计与工程',NULL),
 (161,'专业','建筑学',NULL),
 (162,'专业','城市规划',NULL),
 (163,'专业','园林规划与设计',NULL),
 (164,'专业','土木工程',NULL),
 (165,'专业','道路与桥梁',NULL),
 (166,'专业','建设环境与设备工程',NULL),
 (167,'专业','给水排水工程',NULL),
 (168,'专业','供热通风与空调工程',NULL),
 (169,'专业','工业与民用建筑',NULL),
 (170,'专业','室内装潢设计',NULL),
 (171,'专业','建筑工程',NULL),
 (172,'专业','工程造价管理',NULL),
 (173,'专业','力学',NULL),
 (174,'专业','应用力学',NULL),
 (175,'专业','环境科学',NULL),
 (176,'专业','生态学',NULL),
 (177,'专业','环境工程',NULL),
 (178,'专业','安全工程',NULL),
 (179,'专业','制药工程',NULL),
 (180,'专业','交通运输',NULL),
 (181,'专业','交通工程',NULL),
 (182,'专业','油气储运工程',NULL),
 (183,'专业','飞行技术',NULL),
 (184,'专业','航海技术',NULL),
 (185,'专业','轮机工程',NULL),
 (186,'专业','汽车工程',NULL),
 (187,'专业','飞行器设计与工程',NULL),
 (188,'专业','飞行器动力工程',NULL),
 (189,'专业','飞行器制造工程',NULL),
 (190,'专业','飞行器环境与生命保障工程',NULL),
 (191,'专业','船舶与海洋工程',NULL),
 (192,'专业','水利水电工程',NULL),
 (193,'专业','水文与水资源工程',NULL),
 (194,'专业','港口航道与海岸工程',NULL),
 (195,'专业','测绘工程',NULL),
 (196,'专业','公安技术',NULL),
 (197,'专业','武器系统与发射工程',NULL),
 (198,'专业','探测制导与控制技术',NULL),
 (199,'专业','弹药工程与爆炸技术',NULL),
 (200,'专业','数学与应用数学',NULL),
 (201,'专业','信息与计算科学',NULL),
 (202,'专业','物理学',NULL),
 (203,'专业','应用物理学',NULL),
 (204,'专业','化学',NULL),
 (205,'专业','应用化学',NULL),
 (206,'专业','化学工程与工艺',NULL),
 (207,'专业','精细化工',NULL),
 (208,'专业','化工设备与机械',NULL),
 (209,'专业','生物工程',NULL),
 (210,'专业','生物医学工程',NULL),
 (211,'专业','生物科学,技术',NULL),
 (212,'专业','天文学',NULL),
 (213,'专业','地质学',NULL),
 (214,'专业','宝石鉴定与加工',NULL),
 (215,'专业','地理科学',NULL),
 (216,'专业','地球物理学',NULL),
 (217,'专业','大气科学',NULL),
 (218,'专业','海洋科学',NULL),
 (219,'专业','地矿',NULL),
 (220,'专业','石油工程',NULL),
 (221,'专业','经济学',NULL),
 (222,'专业','国际经济与贸易',NULL),
 (223,'专业','财政学',NULL),
 (224,'专业','金融学',NULL),
 (225,'专业','经济管理',NULL),
 (226,'专业','经济信息管理',NULL),
 (227,'专业','工业外贸',NULL),
 (228,'专业','国际金融',NULL),
 (229,'专业','投资经济管理',NULL),
 (230,'专业','统计学',NULL),
 (231,'专业','审计学',NULL),
 (232,'专业','中国语言文学',NULL),
 (233,'专业','英语',NULL),
 (234,'专业','俄语',NULL),
 (235,'专业','德语',NULL),
 (236,'专业','法语',NULL),
 (237,'专业','日语',NULL),
 (238,'专业','西班牙语',NULL),
 (239,'专业','阿拉伯语',NULL),
 (240,'专业','朝鲜语',NULL),
 (241,'专业','其它外语',NULL),
 (242,'专业','新闻学',NULL),
 (243,'专业','广播电视新闻',NULL),
 (244,'专业','广告学',NULL),
 (245,'专业','编辑出版学',NULL),
 (246,'专业','外贸英语',NULL),
 (247,'专业','商务英语',NULL),
 (248,'专业','音乐,舞蹈,作曲',NULL),
 (249,'专业','绘画,艺术设计',NULL),
 (250,'专业','戏剧,表演',NULL),
 (251,'专业','导演,广播电视编导',NULL),
 (252,'专业','戏剧影视文学',NULL),
 (253,'专业','戏剧影视美术设计',NULL),
 (254,'专业','摄影,动画',NULL),
 (255,'专业','播音,主持,录音',NULL),
 (256,'专业','服装设计',NULL),
 (257,'专业','法学',NULL),
 (258,'专业','马克思主义理论',NULL),
 (259,'专业','社会学',NULL),
 (260,'专业','政治学与行政学',NULL),
 (261,'专业','国际政治',NULL),
 (262,'专业','外交学',NULL),
 (263,'专业','思想政治教育',NULL),
 (264,'专业','公安学',NULL),
 (265,'专业','经济法',NULL),
 (266,'专业','国际经济法',NULL),
 (267,'专业','哲学',NULL),
 (268,'专业','逻辑学',NULL),
 (269,'专业','宗教学',NULL),
 (270,'专业','教育学',NULL),
 (271,'专业','学前教育',NULL),
 (272,'专业','体育学',NULL),
 (273,'专业','基础医学',NULL),
 (274,'专业','预防医学',NULL),
 (275,'专业','临床医学与医学技术',NULL),
 (276,'专业','口腔医学',NULL),
 (277,'专业','中医学',NULL),
 (278,'专业','法医学',NULL),
 (279,'专业','护理学',NULL),
 (280,'专业','药学',NULL),
 (281,'专业','心理学',NULL),
 (282,'专业','医学检验',NULL),
 (283,'专业','植物生产',NULL),
 (284,'专业','农学',NULL),
 (285,'专业','园艺',NULL),
 (286,'专业','植物保护学',NULL),
 (287,'专业','茶学',NULL),
 (288,'专业','草业科学',NULL),
 (289,'专业','森林资源',NULL),
 (290,'专业','环境生态',NULL),
 (291,'专业','园林',NULL),
 (292,'专业','动物生产',NULL),
 (293,'专业','动物医学',NULL),
 (294,'专业','水产类',NULL),
 (295,'专业','农业工程',NULL),
 (296,'专业','林业工程',NULL),
 (297,'专业','历史学',NULL),
 (298,'专业','考古学',NULL),
 (299,'专业','博物馆学',NULL);
/*!40000 ALTER TABLE `dictionary` ENABLE KEYS */;


--
-- Definition of table `doc_file`
--

DROP TABLE IF EXISTS `doc_file`;
CREATE TABLE `doc_file` (
  `fileId` bigint(20) NOT NULL,
  `docId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`docId`),
  KEY `FK_DF_F_DT` (`docId`),
  CONSTRAINT `FK_DF_F_DT` FOREIGN KEY (`docId`) REFERENCES `document` (`docId`),
  CONSTRAINT `FK_DF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doc_file`
--

/*!40000 ALTER TABLE `doc_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_file` ENABLE KEYS */;


--
-- Definition of table `doc_folder`
--

DROP TABLE IF EXISTS `doc_folder`;
CREATE TABLE `doc_folder` (
  `folderId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) default NULL COMMENT '主键',
  `folderName` varchar(128) NOT NULL COMMENT '目录名称',
  `parentId` bigint(20) default NULL COMMENT '父目录',
  `path` varchar(128) default NULL COMMENT '路径\r\n            为当前路径的＋上级路径\r\n            如当前ID为3，上级目录的路径为1.2，\r\n            则当前的路径为1.2.3.',
  `isShared` smallint(6) NOT NULL,
  PRIMARY KEY  (`folderId`),
  KEY `FK_DF_R_AU` (`userId`),
  CONSTRAINT `FK_DF_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doc_folder`
--

/*!40000 ALTER TABLE `doc_folder` DISABLE KEYS */;
INSERT INTO `doc_folder` (`folderId`,`userId`,`folderName`,`parentId`,`path`,`isShared`) VALUES 
 (1,1,'用户操作管理',0,'1.',0),
 (2,1,'管理及限制',0,'2.',0),
 (3,2,'成文档',0,'3.',0),
 (4,2,'强类型',0,'4.',0);
/*!40000 ALTER TABLE `doc_folder` ENABLE KEYS */;


--
-- Definition of table `doc_history`
--

DROP TABLE IF EXISTS `doc_history`;
CREATE TABLE `doc_history` (
  `historyId` bigint(20) NOT NULL auto_increment,
  `docId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL COMMENT '附件ID',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `path` varchar(128) NOT NULL COMMENT '路径',
  `version` int(11) NOT NULL COMMENT '版本',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  `mender` varchar(64) NOT NULL COMMENT '修改人',
  PRIMARY KEY  (`historyId`),
  KEY `FK_DHY_R_ARVD` (`docId`),
  KEY `FK_DH_R_FA` (`fileId`),
  CONSTRAINT `FK_DHY_R_ARVD` FOREIGN KEY (`docId`) REFERENCES `archives_doc` (`docId`),
  CONSTRAINT `FK_DH_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `doc_history`
--

/*!40000 ALTER TABLE `doc_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_history` ENABLE KEYS */;


--
-- Definition of table `doc_privilege`
--

DROP TABLE IF EXISTS `doc_privilege`;
CREATE TABLE `doc_privilege` (
  `privilegeId` bigint(20) NOT NULL auto_increment,
  `folderId` bigint(20) default NULL,
  `docId` bigint(20) default NULL,
  `rights` int(11) NOT NULL COMMENT '权限\r\n            文档或目录的读写修改权限\r\n            1=读\r\n            2=修改\r\n            4=删除\r\n            \r\n            权限值可以为上面的值之和\r\n            如：3则代表进行读，修改的操作\r\n            \r\n            \r\n            ',
  `udrId` int(11) default NULL,
  `udrName` varchar(128) default NULL,
  `flag` smallint(6) NOT NULL COMMENT '1=user\r\n            2=deparment\r\n            3=role',
  `fdFlag` smallint(6) NOT NULL COMMENT '缺省为文件夹权限\r\n            1=文档权限\r\n            0=文件夹权限',
  PRIMARY KEY  (`privilegeId`),
  KEY `FK_DP_R_DF` (`folderId`),
  KEY `FK_DP_R_DT` (`docId`),
  CONSTRAINT `FK_DP_R_DF` FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`),
  CONSTRAINT `FK_DP_R_DT` FOREIGN KEY (`docId`) REFERENCES `document` (`docId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档或目录的权限，只要是针对公共目录下的';

--
-- Dumping data for table `doc_privilege`
--

/*!40000 ALTER TABLE `doc_privilege` DISABLE KEYS */;
/*!40000 ALTER TABLE `doc_privilege` ENABLE KEYS */;


--
-- Definition of table `document`
--

DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `docId` bigint(20) NOT NULL auto_increment,
  `docName` varchar(100) NOT NULL,
  `content` text COMMENT '内容',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime default NULL,
  `folderId` bigint(20) default NULL,
  `userId` bigint(20) default NULL COMMENT '主键',
  `fullname` varchar(32) NOT NULL,
  `haveAttach` smallint(6) default NULL,
  `sharedUserIds` varchar(1000) default NULL COMMENT '共享员工ID',
  `sharedUserNames` varchar(1000) default NULL,
  `sharedDepIds` varchar(1000) default NULL COMMENT '共享部门ID',
  `sharedDepNames` varchar(1000) default NULL,
  `sharedRoleIds` varchar(1000) default NULL COMMENT '共享角色ID',
  `sharedRoleNames` varchar(1000) default NULL,
  `isShared` smallint(6) NOT NULL COMMENT '是否共享',
  PRIMARY KEY  (`docId`),
  KEY `FK_DT_R_AU` (`userId`),
  KEY `FK_DT_R_DF` (`folderId`),
  CONSTRAINT `FK_DT_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_DT_R_DF` FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档';

--
-- Dumping data for table `document`
--

/*!40000 ALTER TABLE `document` DISABLE KEYS */;
/*!40000 ALTER TABLE `document` ENABLE KEYS */;


--
-- Definition of table `duty`
--

DROP TABLE IF EXISTS `duty`;
CREATE TABLE `duty` (
  `dutyId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) NOT NULL COMMENT '员工ID',
  `fullname` varchar(32) NOT NULL COMMENT '员工姓名',
  `systemId` bigint(20) NOT NULL COMMENT '班制ID',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime default NULL COMMENT '结束时间',
  PRIMARY KEY  (`dutyId`),
  KEY `FK_DUY_R_AU` (`userId`),
  KEY `FK_DUY_R_DS` (`systemId`),
  CONSTRAINT `FK_DUY_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_DUY_R_DS` FOREIGN KEY (`systemId`) REFERENCES `duty_system` (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='排班';

--
-- Dumping data for table `duty`
--

/*!40000 ALTER TABLE `duty` DISABLE KEYS */;
/*!40000 ALTER TABLE `duty` ENABLE KEYS */;


--
-- Definition of table `duty_register`
--

DROP TABLE IF EXISTS `duty_register`;
CREATE TABLE `duty_register` (
  `registerId` bigint(20) NOT NULL auto_increment,
  `registerDate` datetime NOT NULL COMMENT '登记时间',
  `userId` bigint(20) NOT NULL COMMENT '登记人',
  `fullname` varchar(32) NOT NULL,
  `regFlag` smallint(6) NOT NULL COMMENT '登记标识\r\n            1=正常登记（上班，下班）\r\n            2＝迟到\r\n            3=早退\r\n            4＝休息\r\n            5＝旷工\r\n            6=放假\r\n            \r\n            ',
  `regMins` int(11) NOT NULL COMMENT '迟到或早退分钟\r\n            正常上班时为0',
  `reason` varchar(128) default NULL COMMENT '迟到原因',
  `dayOfWeek` int(11) NOT NULL COMMENT '周几\r\n            1=星期日\r\n            ..\r\n            7=日期六',
  `inOffFlag` smallint(6) NOT NULL COMMENT '上下班标识\r\n            1=签到\r\n            2=签退',
  `sectionId` bigint(20) NOT NULL,
  PRIMARY KEY  (`registerId`),
  KEY `FK_DR_R_AU` (`userId`),
  KEY `FK_DR_R_DS` (`sectionId`),
  CONSTRAINT `FK_DR_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_DR_R_DS` FOREIGN KEY (`sectionId`) REFERENCES `duty_section` (`sectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `duty_register`
--

/*!40000 ALTER TABLE `duty_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `duty_register` ENABLE KEYS */;


--
-- Definition of table `duty_section`
--

DROP TABLE IF EXISTS `duty_section`;
CREATE TABLE `duty_section` (
  `sectionId` bigint(20) NOT NULL auto_increment,
  `sectionName` varchar(64) NOT NULL,
  `startSignin` datetime NOT NULL COMMENT 'å¼å§ç­¾å°',
  `dutyStartTime` datetime NOT NULL COMMENT 'ä¸ç­æ¶é´',
  `endSignin` datetime NOT NULL COMMENT 'ç­¾å°ç»ææ¶é´',
  `earlyOffTime` datetime NOT NULL COMMENT 'æ©éè®¡æ¶',
  `dutyEndTime` datetime NOT NULL COMMENT 'ä¸ç­æ¶é´',
  `signOutTime` datetime NOT NULL COMMENT 'ç­¾éç»æ',
  PRIMARY KEY  (`sectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ç­æ¬¡\r\nä¹ç§°ä¸ºç­æ®µï¼ä¸å¤©å¯ä»¥åä¸ºä¸¤ä¸ªæä¸ä¸ªç';

--
-- Dumping data for table `duty_section`
--

/*!40000 ALTER TABLE `duty_section` DISABLE KEYS */;
/*!40000 ALTER TABLE `duty_section` ENABLE KEYS */;


--
-- Definition of table `duty_system`
--

DROP TABLE IF EXISTS `duty_system`;
CREATE TABLE `duty_system` (
  `systemId` bigint(20) NOT NULL auto_increment,
  `systemName` varchar(128) NOT NULL COMMENT 'ç­å¶åç§°',
  `systemSetting` varchar(128) NOT NULL COMMENT 'ç­æ¬¡\r\n            ç­æ¬¡çæ°æ®ç»æä¸ºï¼\r\n            å¦ä¸åå·¥å¨ä¸è³å¨äºä¸ç­ï¼ä¸å9ï¼00- 12ï¼00 ï¼ä¸å 13ï¼30 -18:00\r\n            \r\n            åºæ°æ®ç»æä¸ºï¼\r\n            1|2,1|2,1|2,1|2,1|2,-,-\r\n            -ä»£è¡¨æ­£å¸¸ä¼æ¯æ¥\r\n            1|2ä»£è¡¨ä¸ºä¸å¤©ä¸ä¸¤ä¸ªç­æ¬¡ï¼1ä»£è¡¨ä¸ºç­æ¬¡è¡¨1çè®°å½\r\n            ',
  `systemDesc` varchar(256) NOT NULL COMMENT 'ç­æ¬¡æè¿°',
  `isDefault` smallint(6) NOT NULL COMMENT 'æ¯å¦ç¼ºç\r\n            1ï¼ç¼ºç\r\n            0ï¼éç¼ºç',
  PRIMARY KEY  (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='è½®ç­å¶\r\nä¸ºå¬å¸è®¾ç½®ä¸ç­çæ¶é´è§å®\r\nå¦è§å®å';

--
-- Dumping data for table `duty_system`
--

/*!40000 ALTER TABLE `duty_system` DISABLE KEYS */;
/*!40000 ALTER TABLE `duty_system` ENABLE KEYS */;


--
-- Definition of table `emp_profile`
--

DROP TABLE IF EXISTS `emp_profile`;
CREATE TABLE `emp_profile` (
  `profileId` bigint(20) NOT NULL auto_increment,
  `profileNo` varchar(100) NOT NULL COMMENT '档案编号',
  `userId` bigint(20) NOT NULL,
  `fullname` varchar(64) NOT NULL COMMENT '员工姓名',
  `address` varchar(128) default NULL COMMENT '家庭地址',
  `birthday` datetime default NULL COMMENT '出生日期',
  `homeZip` varchar(32) default NULL COMMENT '家庭邮编',
  `sex` varchar(32) default NULL,
  `marriage` varchar(32) default NULL COMMENT '婚姻状况\r\n            已婚\r\n            未婚',
  `designation` varchar(64) default NULL,
  `jobId` bigint(20) NOT NULL,
  `position` varchar(128) NOT NULL,
  `phone` varchar(64) default NULL COMMENT '电话号码',
  `mobile` varchar(64) default NULL COMMENT '手机号码',
  `openBank` varchar(100) default NULL COMMENT '开户银行',
  `bankNo` varchar(100) default NULL COMMENT '银行账号',
  `qq` varchar(64) default NULL COMMENT 'QQ号码',
  `email` varchar(128) default NULL COMMENT '电子邮箱',
  `hobby` varchar(300) default NULL COMMENT '爱好',
  `religion` varchar(100) default NULL COMMENT '宗教信仰',
  `party` varchar(100) default NULL COMMENT '政治面貌',
  `nationality` varchar(100) default NULL COMMENT '国籍',
  `race` varchar(100) default NULL COMMENT '民族',
  `birthPlace` varchar(128) default NULL COMMENT '出生地',
  `eduDegree` varchar(100) default NULL COMMENT '学历',
  `eduMajor` varchar(100) default NULL COMMENT '专业',
  `eduCollege` varchar(128) default NULL COMMENT '毕业院校',
  `startWorkDate` datetime default NULL COMMENT '参加工作时间',
  `eduCase` varchar(2048) default NULL COMMENT '教育背景',
  `awardPunishCase` varchar(2048) default NULL COMMENT '奖惩情况',
  `trainingCase` varchar(2048) default NULL COMMENT '培训情况',
  `workCase` varchar(2048) default NULL COMMENT '工作经历',
  `idCard` varchar(64) default NULL COMMENT '身份证号',
  `photo` varchar(128) default NULL COMMENT '照片',
  `standardMiNo` varchar(100) default NULL COMMENT '薪酬标准编号',
  `standardMoney` decimal(18,2) default NULL COMMENT '薪酬标准金额',
  `standardName` varchar(128) default NULL COMMENT '薪酬标准单名称',
  `standardId` bigint(20) default NULL COMMENT '薪酬标准单编号',
  `creator` varchar(64) default NULL COMMENT '建档人',
  `createtime` datetime default NULL COMMENT '建档时间',
  `checkName` varchar(128) default NULL COMMENT '审核人',
  `checktime` datetime default NULL COMMENT '审核时间',
  `opprovalOpinion` varchar(1024) default NULL,
  `approvalStatus` smallint(6) default '0' COMMENT '核审状态\r\n            0=未审批\r\n            1=通过审核\r\n            2=未通过审核',
  `memo` varchar(1024) default NULL COMMENT '备注',
  `depName` varchar(100) default NULL COMMENT '所属部门或公司',
  `depId` bigint(20) default NULL COMMENT '所属部门Id',
  `delFlag` smallint(6) NOT NULL default '0' COMMENT '删除状态\r\n            0=未删除\r\n            1=删除',
  PRIMARY KEY  (`profileId`),
  KEY `FK_EPF_R_AU` (`userId`),
  KEY `FK_EP_R_JB` (`jobId`),
  KEY `FK_SD_R_SY` (`standardId`),
  CONSTRAINT `FK_EPF_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_EP_R_JB` FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`),
  CONSTRAINT `FK_SD_R_SY` FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工档案';

--
-- Dumping data for table `emp_profile`
--

/*!40000 ALTER TABLE `emp_profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `emp_profile` ENABLE KEYS */;


--
-- Definition of table `errands_register`
--

DROP TABLE IF EXISTS `errands_register`;
CREATE TABLE `errands_register` (
  `dateId` bigint(20) NOT NULL auto_increment,
  `userId` bigint(20) NOT NULL COMMENT '外出/加班人员',
  `descp` varchar(500) NOT NULL COMMENT '描述',
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `approvalId` bigint(20) NOT NULL COMMENT '审批人ID',
  `status` smallint(6) NOT NULL COMMENT '审批状态\r\n            0=未审批\r\n            1=通过审批\r\n            2=不通过审批',
  `approvalOption` varchar(500) default NULL COMMENT '审批意见',
  `approvalName` varchar(128) NOT NULL COMMENT '审批人',
  `flag` smallint(6) default NULL COMMENT '标识\r\n            0=加班\r\n            1=请假\r\n            2=外出',
  PRIMARY KEY  (`dateId`),
  KEY `FK_ERP_R_AU` (`approvalId`),
  KEY `FK_ER_R_AU` (`userId`),
  CONSTRAINT `FK_ERP_R_AU` FOREIGN KEY (`approvalId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_ER_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假、加班、外出登记';

--
-- Dumping data for table `errands_register`
--

/*!40000 ALTER TABLE `errands_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `errands_register` ENABLE KEYS */;


--
-- Definition of table `file_attach`
--

DROP TABLE IF EXISTS `file_attach`;
CREATE TABLE `file_attach` (
  `fileId` bigint(20) NOT NULL auto_increment,
  `fileName` varchar(128) NOT NULL COMMENT '文件名',
  `filePath` varchar(128) NOT NULL COMMENT '文件路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `ext` varchar(32) default NULL COMMENT '扩展名',
  `fileType` varchar(32) NOT NULL COMMENT '附件类型\r\n            如：邮件附件',
  `note` varchar(1024) default NULL COMMENT '说明',
  `creator` varchar(32) NOT NULL COMMENT '上传者',
  PRIMARY KEY  (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件';

--
-- Dumping data for table `file_attach`
--

/*!40000 ALTER TABLE `file_attach` DISABLE KEYS */;
INSERT INTO `file_attach` (`fileId`,`fileName`,`filePath`,`createtime`,`ext`,`fileType`,`note`,`creator`) VALUES 
 (1,'webQQ截图.docx','document/201005/2f1094cdfd6843b39f3e3e9ae3e18d7e.docx','2010-05-22 14:10:04','docx','document','137923 bytes','超级管理员'),
 (2,'webQQ截图.docx','document/201005/185bc295267b45e7a6d467b44ab72ebe.docx','2010-05-22 14:15:24','docx','document','137923 bytes','超级管理员'),
 (3,'webQQ截图.docx','document/201005/acb51b7f7fd6417384ddc694abef3881.docx','2010-05-22 14:19:05','docx','document','137923 bytes','超级管理员');
/*!40000 ALTER TABLE `file_attach` ENABLE KEYS */;


--
-- Definition of table `fixed_assets`
--

DROP TABLE IF EXISTS `fixed_assets`;
CREATE TABLE `fixed_assets` (
  `assetsId` bigint(20) NOT NULL auto_increment,
  `assetsNo` varchar(128) default NULL,
  `assetsName` varchar(128) NOT NULL,
  `model` varchar(64) default NULL,
  `assetsTypeId` bigint(20) NOT NULL,
  `manufacturer` varchar(64) default NULL,
  `manuDate` datetime default NULL,
  `buyDate` datetime NOT NULL,
  `beDep` varchar(64) NOT NULL,
  `custodian` varchar(32) default NULL,
  `notes` varchar(500) default NULL,
  `remainValRate` decimal(18,6) NOT NULL,
  `depreTypeId` bigint(20) NOT NULL,
  `startDepre` datetime default NULL,
  `intendTerm` decimal(18,2) default NULL,
  `intendWorkGross` decimal(18,2) default NULL COMMENT '当折旧的方法选择用工作量法进行计算时，才需要填写',
  `workGrossUnit` varchar(128) default NULL,
  `assetValue` decimal(18,4) NOT NULL,
  `assetCurValue` decimal(18,4) NOT NULL,
  `depreRate` decimal(18,2) default NULL,
  `defPerWorkGross` decimal(18,2) default NULL,
  PRIMARY KEY  (`assetsId`),
  KEY `FK_FA_R_AT` (`assetsTypeId`),
  KEY `FK_FA_R_DT` (`depreTypeId`),
  CONSTRAINT `FK_FA_R_AT` FOREIGN KEY (`assetsTypeId`) REFERENCES `assets_type` (`assetsTypeId`),
  CONSTRAINT `FK_FA_R_DT` FOREIGN KEY (`depreTypeId`) REFERENCES `depre_type` (`depreTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fixed_assets`
--

/*!40000 ALTER TABLE `fixed_assets` DISABLE KEYS */;
/*!40000 ALTER TABLE `fixed_assets` ENABLE KEYS */;


--
-- Definition of table `form_data`
--

DROP TABLE IF EXISTS `form_data`;
CREATE TABLE `form_data` (
  `dataId` bigint(20) NOT NULL auto_increment,
  `formId` bigint(20) NOT NULL COMMENT '所属表单',
  `fieldLabel` varchar(128) NOT NULL COMMENT '字段标签',
  `fieldName` varchar(64) NOT NULL COMMENT '字段名称',
  `intValue` int(11) default NULL COMMENT '整数值',
  `longValue` bigint(20) default NULL COMMENT '长整值',
  `decValue` decimal(18,4) default NULL COMMENT '精度值',
  `dateValue` datetime default NULL COMMENT '日期值',
  `strValue` varchar(4000) default NULL COMMENT '字符值',
  `boolValue` smallint(6) default NULL COMMENT '布尔值',
  `blobValue` blob COMMENT '对象值',
  `isShowed` smallint(6) NOT NULL COMMENT '是否显示\r\n            1=显示\r\n            0=不显示',
  `textValue` text,
  `fieldType` varchar(32) NOT NULL,
  PRIMARY KEY  (`dataId`),
  KEY `FK_FD_R_PF` (`formId`),
  CONSTRAINT `FK_FD_R_PF` FOREIGN KEY (`formId`) REFERENCES `process_form` (`formId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `form_data`
--

/*!40000 ALTER TABLE `form_data` DISABLE KEYS */;
INSERT INTO `form_data` (`dataId`,`formId`,`fieldLabel`,`fieldName`,`intValue`,`longValue`,`decValue`,`dateValue`,`strValue`,`boolValue`,`blobValue`,`isShowed`,`textValue`,`fieldType`) VALUES 
 (1,1,'备注','note',NULL,NULL,NULL,NULL,'测试开发',NULL,NULL,1,NULL,'varchar'),
 (2,2,'备注','note',NULL,NULL,NULL,NULL,'测试及开发\r\n',NULL,NULL,1,NULL,'varchar'),
 (3,3,'备注','note',NULL,NULL,NULL,NULL,'薄情及开发',NULL,NULL,1,NULL,'varchar'),
 (4,4,'备注','note',NULL,NULL,NULL,NULL,'伤心及开发',NULL,NULL,1,NULL,'varchar'),
 (5,5,'备注','note',NULL,NULL,NULL,NULL,'送还垂发',NULL,NULL,1,NULL,'varchar');
/*!40000 ALTER TABLE `form_data` ENABLE KEYS */;


--
-- Definition of table `form_def`
--

DROP TABLE IF EXISTS `form_def`;
CREATE TABLE `form_def` (
  `formDefId` bigint(20) NOT NULL auto_increment,
  `formName` varchar(128) NOT NULL COMMENT '表单名称',
  `columns` int(11) NOT NULL COMMENT '总列数',
  `isEnabled` smallint(6) NOT NULL default '1' COMMENT '是否可用',
  `activityName` varchar(128) NOT NULL COMMENT '节点名称',
  `deployId` varchar(128) NOT NULL COMMENT 'Jbpm流程发布ID',
  `extDef` text COMMENT '表单定义',
  `formView` varchar(128) default NULL COMMENT '流程定义View',
  PRIMARY KEY  (`formDefId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `form_def`
--

/*!40000 ALTER TABLE `form_def` DISABLE KEYS */;
/*!40000 ALTER TABLE `form_def` ENABLE KEYS */;


--
-- Definition of table `fun_url`
--

DROP TABLE IF EXISTS `fun_url`;
CREATE TABLE `fun_url` (
  `urlId` bigint(20) NOT NULL auto_increment,
  `functionId` bigint(20) NOT NULL,
  `urlPath` varchar(128) NOT NULL,
  PRIMARY KEY  (`urlId`),
  KEY `FK_FU_R_AFN` (`functionId`),
  CONSTRAINT `FK_FU_R_AFN` FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fun_url`
--

/*!40000 ALTER TABLE `fun_url` DISABLE KEYS */;
INSERT INTO `fun_url` (`urlId`,`functionId`,`urlPath`) VALUES 
 (1,1,'/system/listAppUser.do'),
 (2,2,'/system/listAppUser.do'),
 (3,2,'/system/chooseRolesAppUser.do'),
 (4,2,'/system/selectedRolesAppUser.do'),
 (5,2,'/system/listDepartment.do'),
 (6,3,'/system/listAppUser.do'),
 (7,3,'/system/chooseRolesAppUser.do'),
 (8,3,'/system/selectedRolesAppUser.do'),
 (9,3,'/system/listDepartment.do'),
 (10,4,'/system/listAppUser.do'),
 (11,4,'/system/multiDelAppUser.do'),
 (12,5,'/system/listAppRole.do'),
 (13,6,'/system/listAppRole.do'),
 (14,6,'/system/saveAppRole.do'),
 (15,7,'/system/listAppRole.do'),
 (16,7,'/system/saveAppRole.do'),
 (17,7,'/system/getAppRole.do'),
 (18,8,'/system/listAppRole.do'),
 (19,8,'/system/mulDelAppRole.do'),
 (20,9,'/system/listAppRole.do'),
 (21,9,'/system/grantXmlAppRole.do'),
 (22,9,'/system/getAppRole.do'),
 (23,9,'/system/grantAppRole.do'),
 (24,10,'/system/listDepartment.do'),
 (25,10,'/system/selectAppUser.do'),
 (26,11,'/system/listDepartment.do'),
 (27,11,'/system/addDepartment.do'),
 (28,11,'/system/selectAppUser.do'),
 (29,11,'/system/saveAppUser.do'),
 (30,12,'/system/listDepartment.do'),
 (31,12,'/system/addDepartment.do'),
 (32,12,'/system/detailDepartment.do'),
 (33,12,'/system/selectAppUser.do'),
 (34,12,'/system/saveAppUser.do'),
 (35,13,'/system/listDepartment.do'),
 (36,13,'/system/removeDepartment.do'),
 (37,13,'/system/selectAppUser.do'),
 (38,13,'/system/saveAppUser.do'),
 (39,14,'/system/listFileAttach.do'),
 (40,15,'/system/saveFileAttach.do'),
 (41,15,'/system/listFileAttach.do'),
 (42,15,'/system/getFileAttach.do'),
 (43,16,'/system/listFileAttach.do'),
 (44,16,'/system/multiDelFileAttach.do'),
 (45,17,'/system/listCompany.do'),
 (46,17,'/system/addCompany.do'),
 (47,18,'/flow/rootProType.do'),
 (48,18,'/flow/listProDefinition.do'),
 (49,18,'/flow/processDetail.do'),
 (50,19,'/flow/rootProType.do'),
 (51,19,'/flow/saveProType.do'),
 (52,19,'/flow/removeProType.do'),
 (53,19,'/flow/getProType.do'),
 (54,20,'/flow/rootProType.do'),
 (55,20,'/flow/listProDefinition.do'),
 (56,20,'/flow/saveProDefinition.do'),
 (57,20,'/flow/listProType.do'),
 (58,20,'/flow/getProDefinition.do'),
 (59,21,'/flow/rootProType.do'),
 (60,21,'/flow/listProDefinition.do'),
 (61,21,'/flow/saveProDefinition.do'),
 (62,21,'/flow/listProType.do'),
 (63,21,'/flow/getProDefinition.do'),
 (64,22,'/flow/rootProType.do'),
 (65,22,'/flow/listProDefinition.do'),
 (66,22,'/flow/multiDelProDefinition.do'),
 (67,23,'/flow/processDetail.do'),
 (68,24,'/flow/saveProUserAssign.do'),
 (69,24,'/flow/listProUserAssign.do'),
 (70,25,'/document/saveDocFolder.do'),
 (71,25,'/document/getDocFolder.do'),
 (72,25,'/document/removeDocFolder.do'),
 (73,26,'/document/listDocPrivilege.do'),
 (74,27,'/document/listDocPrivilege.do'),
 (75,27,'/document/addDocPrivilege.do'),
 (76,28,'/document/listDocPrivilege.do'),
 (77,28,'/document/changeDocPrivilege.do'),
 (78,29,'/document/listDocPrivilege.do'),
 (79,29,'/document/multiDelDocPrivilege.do'),
 (80,30,'/task/listPlanType.do'),
 (81,31,'/task/listPlanType.do'),
 (82,31,'/task/savePlanType.do'),
 (83,32,'/task/listPlanType.do'),
 (84,32,'/task/savePlanType.do'),
 (85,32,'/task/getPlanType.do'),
 (86,33,'/task/listPlanType.do'),
 (87,33,'/task/multiDelPlanType.do'),
 (88,35,'/info/categoryNews.do'),
 (89,35,'/info/treeNewsType.do'),
 (90,36,'/info/categoryNews.do'),
 (91,36,'/info/treeNewsType.do'),
 (92,36,'/info/saveNews.do'),
 (93,37,'/info/categoryNews.do'),
 (94,37,'/info/treeNewsType.do'),
 (95,37,'/info/saveNews.do'),
 (96,38,'/info/categoryNews.do'),
 (97,38,'/info/treeNewsType.do'),
 (98,38,'/info/multiDelNews.do'),
 (99,40,'/info/multiDelNewsComment.do'),
 (100,41,'/info/listNewsType.do'),
 (101,42,'/info/listNewsType.do'),
 (102,42,'/info/addNewsType.do'),
 (103,43,'/info/listNewsType.do'),
 (104,43,'/info/addNewsType.do'),
 (105,43,'/info/detailNewsType.do'),
 (106,43,'/info/sortNewsType.do'),
 (107,44,'/info/listNewsType.do'),
 (108,44,'/info/removeNewsType.do'),
 (109,46,'/info/saveNotice.do'),
 (110,47,'/info/saveNotice.do'),
 (111,48,'/info/multiDelNotice.do'),
 (112,49,'/personal/listHolidayRecord.do'),
 (113,50,'/personal/listHolidayRecord.do'),
 (114,50,'/personal/saveHolidayRecord.do'),
 (115,51,'/personal/listHolidayRecord.do'),
 (116,51,'/personal/getHolidayRecord.do'),
 (117,51,'/personal/saveHolidayRecord.do'),
 (118,52,'/personal/listHolidayRecord.do'),
 (119,52,'/personal/multiDelHolidayRecord.do'),
 (120,53,'/personal/listDutySection.do'),
 (121,54,'/personal/listDutySection.do'),
 (122,54,'/personal/saveDutySection.do'),
 (123,55,'/personal/listDutySection.do'),
 (124,55,'/personal/saveDutySection.do'),
 (125,55,'/personal/getDutySection.do'),
 (126,56,'/personal/listDutySection.do'),
 (127,56,'/personal/multiDelDutySection.do'),
 (128,57,'/personal/listDutySystem.do'),
 (129,58,'/personal/listDutySystem.do'),
 (130,58,'/personal/settingDutySystem.do'),
 (131,58,'/personal/saveDutySystem.do'),
 (132,59,'/personal/listDutySystem.do'),
 (133,59,'/personal/getDutySystem.do'),
 (134,59,'/personal/saveDutySystem.do'),
 (135,60,'/personal/listDutySystem.do'),
 (136,60,'/personal/multiDelDutySystem.do'),
 (137,61,'/personal/listDuty.do'),
 (138,62,'/personal/listDuty.do'),
 (139,62,'/personal/saveDuty.do'),
 (140,62,'/personal/comboDutySystem.do'),
 (141,63,'/personal/listDuty.do'),
 (142,63,'/personal/saveDuty.do'),
 (143,63,'/personal/comboDutySystem.do'),
 (144,63,'/personal/getDuty.do'),
 (145,64,'/personal/listDuty.do'),
 (146,64,'/personal/multiDelDuty.do'),
 (147,65,'/personal/listDutyRegister.do'),
 (148,66,'/personal/listDutyRegister.do'),
 (149,66,'/personal/saveDutyRegister.do'),
 (150,66,'/personal/comboDutySection.do'),
 (151,67,'/personal/listDutyRegister.do'),
 (152,67,'/personal/multiDelDutyRegister.do'),
 (153,68,'/customer/listCustomer.do'),
 (154,69,'/customer/listCustomer.do'),
 (155,69,'/customer/saveCustomer.do'),
 (156,69,'/customer/checkCustomer.do'),
 (157,69,'/customer/numberCustomer.do'),
 (158,69,'/system/listRegion.do'),
 (159,70,'/customer/listCustomer.do'),
 (160,70,'/customer/saveCustomer.do'),
 (161,70,'/customer/checkCustomer.do'),
 (162,70,'/customer/numberCustomer.do'),
 (163,70,'/system/listRegion.do'),
 (164,70,'/customer/getCustomer.do'),
 (165,71,'/customer/listCustomer.do'),
 (166,71,'/customer/multiDelCustomer.do'),
 (167,72,'/customer/listCusLinkman.do'),
 (168,73,'/customer/listCusLinkman.do'),
 (169,73,'/customer/saveCusLinkman.do'),
 (170,74,'/customer/listCusLinkman.do'),
 (171,74,'/customer/saveCusLinkman.do'),
 (172,74,'/customer/getCusLinkman.do'),
 (173,75,'/customer/listCusLinkman.do'),
 (174,75,'/customer/multiDelCusLinkman.do'),
 (175,76,'/customer/listCusConnection.do'),
 (176,77,'/customer/listCusConnection.do'),
 (177,77,'/customer/saveCusConnection.do'),
 (178,77,'/customer/findCusLinkman.do'),
 (179,78,'/customer/listCusConnection.do'),
 (180,78,'/customer/saveCusConnection.do'),
 (181,78,'/customer/findCusLinkman.do'),
 (182,78,'/customer/getCusConnection.do'),
 (183,79,'/customer/listCusConnection.do'),
 (184,79,'/customer/multiDelCusConnection.do'),
 (185,80,'/customer/listProject.do'),
 (186,81,'/customer/listProject.do'),
 (187,81,'/customer/saveProject.do'),
 (188,81,'/customer/numberProject.do'),
 (189,81,'/customer/findCusLinkman.do'),
 (190,82,'/customer/listProject.do'),
 (191,82,'/customer/saveProject.do'),
 (192,82,'/customer/numberProject.do'),
 (193,82,'/customer/findCusLinkman.do'),
 (194,82,'/customer/getProject.do'),
 (195,83,'/customer/listProject.do'),
 (196,83,'/customer/multiDelProject.do'),
 (197,84,'/customer/listContract.do'),
 (198,85,'/customer/listContract.do'),
 (199,85,'/customer/saveContract.do'),
 (200,85,'/customer/getProject.do'),
 (201,85,'/customer/removeFileContract.do'),
 (202,85,'/customer/listContractConfig.do'),
 (203,85,'/customer/multiDelContractConfig.do'),
 (204,86,'/customer/listContract.do'),
 (205,86,'/customer/saveContract.do'),
 (206,86,'/customer/getProject.do'),
 (207,86,'/customer/removeFileContract.do'),
 (208,86,'/customer/listContractConfig.do'),
 (209,86,'/customer/multiDelContractConfig.do'),
 (210,86,'/customer/getContract.do'),
 (211,87,'/customer/listContract.do'),
 (212,87,'/customer/multiDelContract.do'),
 (213,88,'/customer/listProduct.do'),
 (214,89,'/customer/saveProduct.do'),
 (215,89,'/customer/listProvider.do'),
 (216,90,'/customer/listProduct.do'),
 (217,90,'/customer/getProduct.do'),
 (218,90,'/customer/saveProduct.do'),
 (219,91,'/customer/listProduct.do'),
 (220,91,'/customer/multiDelProduct.do'),
 (221,92,'/customer/listProvider.do'),
 (222,93,'/customer/saveProvider.do'),
 (223,94,'/customer/listProvider.do'),
 (224,94,'/customer/getProvider.do'),
 (225,94,'/customer/saveProvider.do'),
 (226,95,'/customer/listProvider.do'),
 (227,95,'/customer/multiDelProvider.do'),
 (228,96,'/admin/listOfficeGoods.do'),
 (229,96,'/admin/treeOfficeGoodsType.do'),
 (230,97,'/admin/listOfficeGoods.do'),
 (231,97,'/admin/treeOfficeGoodsType.do'),
 (232,97,'/admin/multiDelOfficeGoodsType.do'),
 (233,97,'/admin/saveOfficeGoodsType.do'),
 (234,97,'/admin/getOfficeGoodsType.do'),
 (235,98,'/admin/listOfficeGoods.do'),
 (236,98,'/admin/saveOfficeGoods.do'),
 (237,98,'/admin/treeOfficeGoodsType.do'),
 (238,99,'/admin/listOfficeGoods.do'),
 (239,99,'/admin/saveOfficeGoods.do'),
 (240,99,'/admin/treeOfficeGoodsType.do'),
 (241,99,'/admin/getOfficeGoods.do'),
 (242,100,'/admin/listOfficeGoods.do'),
 (243,100,'/admin/multiDelOfficeGoods.do'),
 (244,101,'/admin/listInStock.do'),
 (245,102,'/admin/listInStock.do'),
 (246,102,'/admin/listOfficeGoods.do'),
 (247,102,'/admin/saveInStock.do'),
 (248,102,'/admin/treeOfficeGoodsType.do'),
 (249,103,'/admin/listInStock.do'),
 (250,103,'/admin/listOfficeGoods.do'),
 (251,103,'/admin/saveInStock.do'),
 (252,103,'/admin/treeOfficeGoodsType.do'),
 (253,103,'/admin/getInStock.do'),
 (254,104,'/admin/listInStock.do'),
 (255,104,'/admin/multiDelInStock.do'),
 (256,105,'/admin/listGoodsApply.do'),
 (257,106,'/admin/listGoodsApply.do'),
 (258,106,'/admin/saveGoodsApply.do'),
 (259,106,'/admin/listOfficeGoods.do'),
 (260,106,'/admin/treeOfficeGoodsType.do'),
 (261,107,'/admin/listGoodsApply.do'),
 (262,107,'/admin/saveGoodsApply.do'),
 (263,107,'/admin/listOfficeGoods.do'),
 (264,107,'/admin/treeOfficeGoodsType.do'),
 (265,107,'/admin/getGoodsApply.do'),
 (266,108,'/admin/listGoodsApply.do'),
 (267,108,'/admin/multiDelGoodsApply.do'),
 (268,109,'/admin/listCar.do'),
 (269,110,'/admin/listCar.do'),
 (270,110,'/admin/saveCar.do'),
 (271,111,'/admin/listCar.do'),
 (272,111,'/admin/saveCar.do'),
 (273,111,'/admin/getCar.do'),
 (274,112,'/admin/listCar.do'),
 (275,112,'/admin/multiDelCar.do'),
 (276,113,'/admin/listCartRepair.do'),
 (277,114,'/admin/listCartRepair.do'),
 (278,114,'/admin/saveCartRepair.do'),
 (279,115,'/admin/listCartRepair.do'),
 (280,115,'/admin/saveCartRepair.do'),
 (281,115,'/admin/getCartRepair.do'),
 (282,116,'/admin/listCartRepair.do'),
 (283,116,'/admin/multiDelCartRepair.do'),
 (284,117,'/admin/listCarApply.do'),
 (285,118,'/admin/listCarApply.do'),
 (286,118,'/admin/saveCarApply.do'),
 (287,119,'/admin/listCarApply.do'),
 (288,119,'/admin/saveCarApply.do'),
 (289,119,'/admin/getCarApply.do'),
 (290,120,'/admin/listCarApply.do'),
 (291,120,'/admin/multiDelCarApply.do'),
 (292,121,'/admin/listDepreType.do'),
 (293,122,'/admin/listDepreType.do'),
 (294,122,'/admin/saveDepreType.do'),
 (295,123,'/admin/listDepreType.do'),
 (296,123,'/admin/saveDepreType.do'),
 (297,123,'/admin/getDepreType.do'),
 (298,124,'/admin/listDepreType.do'),
 (299,124,'/admin/multiDelDepreType.do'),
 (300,125,'/admin/listFixedAssets.do'),
 (301,125,'/admin/treeAssetsType.do'),
 (302,126,'/admin/treeAssetsType.do'),
 (303,126,'/admin/multiDelAssetsType.do'),
 (304,126,'/admin/saveAssetsType.do'),
 (305,126,'/admin/getAssetsType.do'),
 (306,127,'/admin/listFixedAssets.do'),
 (307,127,'/system/selectDepartment.do'),
 (308,127,'/admin/treeAssetsType.do'),
 (309,127,'/admin/saveFixedAssets.do'),
 (310,127,'/admin/comboxAssetsType.do'),
 (311,127,'/admin/comboxDepreType.do'),
 (312,128,'/admin/listFixedAssets.do'),
 (313,128,'/admin/treeAssetsType.do'),
 (314,128,'/system/selectDepartment.do'),
 (315,128,'/admin/saveFixedAssets.do'),
 (316,128,'/admin/comboxAssetsType.do'),
 (317,128,'/admin/comboxDepreType.do'),
 (318,128,'/admin/getFixedAssets.do'),
 (319,129,'/admin/listFixedAssets.do'),
 (320,129,'/admin/treeAssetsType.do'),
 (321,129,'/admin/multiDelFixedAssets.do'),
 (322,130,'/admin/depreciateDepreRecord.do'),
 (323,130,'/admin/workDepreRecord.do'),
 (324,131,'/admin/listDepreRecord.do'),
 (325,132,'/admin/listBookType.do'),
 (326,133,'/admin/listBookType.do'),
 (327,133,'/admin/saveBookType.do'),
 (328,134,'/admin/listBookType.do'),
 (329,134,'/admin/saveBookType.do'),
 (330,134,'/admin/getBookType.do'),
 (331,135,'/admin/listBookType.do'),
 (332,135,'/admin/multiDelBookType.do'),
 (333,136,'/admin/listBook.do'),
 (334,136,'/admin/treeBookType.do'),
 (335,137,'/admin/listBook.do'),
 (336,137,'/admin/treeBookType.do'),
 (337,137,'/admin/saveBook.do'),
 (338,137,'/admin/treeBook.do'),
 (339,138,'/admin/listBookType.do'),
 (340,138,'/admin/treeBookType.do'),
 (341,138,'/admin/saveBookType.do'),
 (342,138,'/admin/treeBook.do'),
 (343,138,'/admin/getBook.do'),
 (344,139,'/admin/listBook.do'),
 (345,139,'/admin/treeBookType.do'),
 (346,139,'/admin/multiDelBook.do'),
 (347,140,'/admin/listBorrowBookBorRet.do'),
 (348,141,'/admin/listBorrowBookBorRet.do'),
 (349,141,'/admin/saveBorrowBookBorRet.do'),
 (350,141,'/admin/listBook.do'),
 (351,141,'/admin/treeBookType.do'),
 (352,141,'/admin/getBook.do'),
 (353,141,'/admin/getBorrowSnBookSn.do'),
 (354,142,'/admin/listBorrowBookBorRet.do'),
 (355,142,'/admin/saveBorrowBookBorRet.do'),
 (356,142,'/admin/listBook.do'),
 (357,142,'/admin/treeBookType.do'),
 (358,142,'/admin/getBook.do'),
 (359,142,'/admin/getBorrowSnBookSn.do'),
 (360,142,'/admin/getBookBorRet.do'),
 (361,143,'/admin/listBook.do'),
 (362,143,'/admin/treeBookType.do'),
 (363,143,'/admin/getReturnSnBookSn.do'),
 (364,143,'/admin/getBorRetTimeBookBorRet.do'),
 (365,143,'/admin/listReturnBookBorRet.do'),
 (366,143,'/admin/saveReturnBookBorRet.do'),
 (367,143,'/admin/getBookBorRet.do'),
 (368,144,'/admin/listBorrowBookBorRet.do'),
 (369,144,'/admin/multiDelBookBorRet.do'),
 (370,145,'/admin/listReturnBookBorRet.do'),
 (371,146,'/admin/listBook.do'),
 (372,146,'/admin/treeBookType.do'),
 (373,146,'/admin/getReturnSnBookSn.do'),
 (374,146,'/admin/getBorRetTimeBookBorRet.do'),
 (375,146,'/admin/listReturnBookBorRet.do'),
 (376,146,'/admin/saveReturnBookBorRet.do'),
 (377,147,'/admin/listBook.do'),
 (378,147,'/admin/treeBookType.do'),
 (379,147,'/admin/getReturnSnBookSn.do'),
 (380,147,'/admin/getBorRetTimeBookBorRet.do'),
 (381,147,'/admin/listReturnBookBorRet.do'),
 (382,147,'/admin/saveReturnBookBorRet.do'),
 (383,147,'/admin/getBookBorRet.do'),
 (384,148,'/admin/listReturnBookBorRet.do'),
 (385,148,'/admin/multiDelBookBorRet.do');
/*!40000 ALTER TABLE `fun_url` ENABLE KEYS */;


--
-- Definition of table `goods_apply`
--

DROP TABLE IF EXISTS `goods_apply`;
CREATE TABLE `goods_apply` (
  `applyId` bigint(20) NOT NULL auto_increment,
  `goodsId` bigint(20) NOT NULL,
  `applyDate` datetime NOT NULL,
  `applyNo` varchar(128) NOT NULL COMMENT '申请号,按系统时间产生，如GA20091002-0001',
  `useCounts` int(11) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `proposer` varchar(32) NOT NULL,
  `notes` varchar(500) default NULL,
  `approvalStatus` smallint(6) NOT NULL COMMENT '审批状态\r\n            1=通过审批\r\n            0=未审批\r\n            ',
  PRIMARY KEY  (`applyId`),
  KEY `FK_GA_R_OG` (`goodsId`),
  CONSTRAINT `FK_GA_R_OG` FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品出库';

--
-- Dumping data for table `goods_apply`
--

/*!40000 ALTER TABLE `goods_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `goods_apply` ENABLE KEYS */;


--
-- Definition of table `hire_issue`
--

DROP TABLE IF EXISTS `hire_issue`;
CREATE TABLE `hire_issue` (
  `hireId` bigint(20) NOT NULL auto_increment,
  `title` varchar(128) NOT NULL COMMENT '招聘信息标题',
  `startDate` datetime NOT NULL COMMENT '开始时间',
  `endDate` datetime NOT NULL COMMENT '结束时间',
  `hireCount` int(11) NOT NULL COMMENT '招聘人数',
  `jobName` varchar(128) NOT NULL COMMENT '职位名称',
  `jobCondition` varchar(1024) default NULL COMMENT '招聘要求(条件)',
  `regFullname` varchar(128) NOT NULL COMMENT '登记人姓名',
  `regDate` datetime NOT NULL COMMENT '登记时间',
  `modifyFullname` varchar(32) default NULL COMMENT '变更人姓名',
  `modifyDate` datetime default NULL COMMENT '变更时间',
  `checkFullname` varchar(32) default NULL COMMENT '审核人姓名',
  `checkOpinion` varchar(512) default NULL COMMENT '审核意见',
  `checkDate` datetime default NULL COMMENT '审批时间',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=通过审核\r\n            0=未审核\r\n            2=审核不通过',
  `memo` varchar(1024) default NULL COMMENT '备注',
  PRIMARY KEY  (`hireId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='招聘发布';

--
-- Dumping data for table `hire_issue`
--

/*!40000 ALTER TABLE `hire_issue` DISABLE KEYS */;
/*!40000 ALTER TABLE `hire_issue` ENABLE KEYS */;


--
-- Definition of table `holiday_record`
--

DROP TABLE IF EXISTS `holiday_record`;
CREATE TABLE `holiday_record` (
  `recordId` bigint(20) NOT NULL auto_increment,
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `descp` varchar(512) default NULL COMMENT '假期描述',
  `fullname` varchar(32) default NULL COMMENT '用户名',
  `userId` bigint(20) default NULL COMMENT '所属用户\r\n            若为某员工的假期，则为员工自己的id',
  `isAll` smallint(6) NOT NULL COMMENT '是否为全公司假期\r\n            1=是\r\n            0=否',
  PRIMARY KEY  (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='放假记录';

--
-- Dumping data for table `holiday_record`
--

/*!40000 ALTER TABLE `holiday_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `holiday_record` ENABLE KEYS */;


--
-- Definition of table `in_message`
--

DROP TABLE IF EXISTS `in_message`;
CREATE TABLE `in_message` (
  `receiveId` bigint(20) NOT NULL auto_increment,
  `messageId` bigint(20) default NULL,
  `userId` bigint(20) default NULL COMMENT '主键',
  `readFlag` smallint(6) NOT NULL COMMENT '1=has red\r\n            0=unread',
  `delFlag` smallint(6) NOT NULL,
  `userFullname` varchar(32) NOT NULL,
  PRIMARY KEY  (`receiveId`),
  KEY `FK_IM_R_AU` (`userId`),
  KEY `FK_IM_R_SM` (`messageId`),
  CONSTRAINT `FK_IM_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_IM_R_SM` FOREIGN KEY (`messageId`) REFERENCES `short_message` (`messageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `in_message`
--

/*!40000 ALTER TABLE `in_message` DISABLE KEYS */;
INSERT INTO `in_message` (`receiveId`,`messageId`,`userId`,`readFlag`,`delFlag`,`userFullname`) VALUES 
 (1,1,2,1,0,'cwx'),
 (2,2,1,1,1,'超级管理员'),
 (3,3,2,1,0,'cwx'),
 (4,4,2,1,0,'cwx');
/*!40000 ALTER TABLE `in_message` ENABLE KEYS */;


--
-- Definition of table `in_stock`
--

DROP TABLE IF EXISTS `in_stock`;
CREATE TABLE `in_stock` (
  `buyId` bigint(20) NOT NULL auto_increment,
  `goodsId` bigint(20) NOT NULL,
  `providerName` varchar(128) default NULL,
  `stockNo` varchar(128) NOT NULL,
  `price` decimal(18,2) default NULL,
  `inCounts` int(11) default NULL,
  `amount` decimal(18,2) NOT NULL,
  `inDate` datetime NOT NULL,
  `buyer` varchar(128) default NULL,
  PRIMARY KEY  (`buyId`),
  KEY `FK_IS_R_OG` (`goodsId`),
  CONSTRAINT `FK_IS_R_OG` FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品入库需要同时更新办公用品表的库存';

--
-- Dumping data for table `in_stock`
--

/*!40000 ALTER TABLE `in_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `in_stock` ENABLE KEYS */;


--
-- Definition of table `index_display`
--

DROP TABLE IF EXISTS `index_display`;
CREATE TABLE `index_display` (
  `indexId` bigint(20) NOT NULL auto_increment,
  `portalId` varchar(64) NOT NULL COMMENT 'Portal ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `colNum` int(11) NOT NULL COMMENT '列号',
  `rowNum` int(11) NOT NULL COMMENT '行号',
  PRIMARY KEY  (`indexId`),
  KEY `FK_ID_R_AU` (`userId`),
  CONSTRAINT `FK_ID_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个员工可以设置自己的显示方式';

--
-- Dumping data for table `index_display`
--

/*!40000 ALTER TABLE `index_display` DISABLE KEYS */;
/*!40000 ALTER TABLE `index_display` ENABLE KEYS */;


--
-- Definition of table `jbpm4_deployment`
--

DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `NAME_` longtext,
  `TIMESTAMP_` bigint(20) default NULL,
  `STATE_` varchar(255) default NULL,
  PRIMARY KEY  (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_deployment`
--

/*!40000 ALTER TABLE `jbpm4_deployment` DISABLE KEYS */;
INSERT INTO `jbpm4_deployment` (`DBID_`,`NAME_`,`TIMESTAMP_`,`STATE_`) VALUES 
 (1,NULL,0,'active'),
 (2,NULL,0,'active'),
 (3,NULL,0,'active');
/*!40000 ALTER TABLE `jbpm4_deployment` ENABLE KEYS */;


--
-- Definition of table `jbpm4_deployprop`
--

DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DEPLOYMENT_` bigint(20) default NULL,
  `OBJNAME_` varchar(255) default NULL,
  `KEY_` varchar(255) default NULL,
  `STRINGVAL_` varchar(255) default NULL,
  `LONGVAL_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  KEY `FK_DEPLPROP_DEPL` (`DEPLOYMENT_`),
  CONSTRAINT `FK_DEPLPROP_DEPL` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_deployprop`
--

/*!40000 ALTER TABLE `jbpm4_deployprop` DISABLE KEYS */;
INSERT INTO `jbpm4_deployprop` (`DBID_`,`DEPLOYMENT_`,`OBJNAME_`,`KEY_`,`STRINGVAL_`,`LONGVAL_`) VALUES 
 (1,1,'pd5246674244169262478','pdversion',NULL,1),
 (2,1,'pd5246674244169262478','pdkey','pd5246674244169262478',NULL),
 (3,1,'pd5246674244169262478','pdid','pd5246674244169262478-1',NULL),
 (4,2,'ArchiveSendFlow','pdversion',NULL,1),
 (5,2,'ArchiveSendFlow','pdid','ArchiveSendFlow-1',NULL),
 (6,2,'ArchiveSendFlow','pdkey','ArchiveSendFlow',NULL),
 (7,3,'ArchivesRecFlow','pdkey','ArchivesRecFlow',NULL),
 (8,3,'ArchivesRecFlow','pdid','ArchivesRecFlow-1',NULL),
 (9,3,'ArchivesRecFlow','pdversion',NULL,1);
/*!40000 ALTER TABLE `jbpm4_deployprop` ENABLE KEYS */;


--
-- Definition of table `jbpm4_execution`
--

DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ACTIVITYNAME_` varchar(255) default NULL,
  `PROCDEFID_` varchar(255) default NULL,
  `HASVARS_` bit(1) default NULL,
  `NAME_` varchar(255) default NULL,
  `KEY_` varchar(255) default NULL,
  `ID_` varchar(255) default NULL,
  `STATE_` varchar(255) default NULL,
  `SUSPHISTSTATE_` varchar(255) default NULL,
  `PRIORITY_` int(11) default NULL,
  `HISACTINST_` bigint(20) default NULL,
  `PARENT_` bigint(20) default NULL,
  `INSTANCE_` bigint(20) default NULL,
  `SUPEREXEC_` bigint(20) default NULL,
  `SUBPROCINST_` bigint(20) default NULL,
  `PARENT_IDX_` int(11) default NULL,
  PRIMARY KEY  (`DBID_`),
  UNIQUE KEY `ID_` (`ID_`),
  KEY `IDX_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  KEY `IDX_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `IDX_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `IDX_EXEC_PARENT` (`PARENT_`),
  KEY `FK_EXEC_PARENT` (`PARENT_`),
  KEY `FK_EXEC_SUBPI` (`SUBPROCINST_`),
  KEY `FK_EXEC_INSTANCE` (`INSTANCE_`),
  KEY `FK_EXEC_SUPEREXEC` (`SUPEREXEC_`),
  CONSTRAINT `FK_EXEC_INSTANCE` FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUBPI` FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_EXEC_SUPEREXEC` FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_execution`
--

/*!40000 ALTER TABLE `jbpm4_execution` DISABLE KEYS */;
INSERT INTO `jbpm4_execution` (`DBID_`,`CLASS_`,`DBVERSION_`,`ACTIVITYNAME_`,`PROCDEFID_`,`HASVARS_`,`NAME_`,`KEY_`,`ID_`,`STATE_`,`SUSPHISTSTATE_`,`PRIORITY_`,`HISACTINST_`,`PARENT_`,`INSTANCE_`,`SUPEREXEC_`,`SUBPROCINST_`,`PARENT_IDX_`) VALUES 
 (1,'pvm',2,'测试计划','pd5246674244169262478-1',0x01,NULL,NULL,'pd5246674244169262478.1','active-root',NULL,0,1,NULL,1,NULL,NULL,NULL),
 (2,'pvm',2,'测试计划','pd5246674244169262478-1',0x01,NULL,NULL,'pd5246674244169262478.2','active-root',NULL,0,2,NULL,2,NULL,NULL,NULL),
 (3,'pvm',2,'测试计划','pd5246674244169262478-1',0x01,NULL,NULL,'pd5246674244169262478.3','active-root',NULL,0,3,NULL,3,NULL,NULL,NULL),
 (4,'pvm',2,'测试计划','pd5246674244169262478-1',0x01,NULL,NULL,'pd5246674244169262478.4','active-root',NULL,0,4,NULL,4,NULL,NULL,NULL),
 (5,'pvm',2,'测试计划','pd5246674244169262478-1',0x01,NULL,NULL,'pd5246674244169262478.5','active-root',NULL,0,5,NULL,5,NULL,NULL,NULL);
/*!40000 ALTER TABLE `jbpm4_execution` ENABLE KEYS */;


--
-- Definition of table `jbpm4_hist_actinst`
--

DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `HPROCI_` bigint(20) default NULL,
  `TYPE_` varchar(255) default NULL,
  `EXECUTION_` varchar(255) default NULL,
  `ACTIVITY_NAME_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `TRANSITION_` varchar(255) default NULL,
  `NEXTIDX_` int(11) default NULL,
  `HTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_HACTI_HPROCI` (`HPROCI_`),
  KEY `IDX_HTI_HTASK` (`HTASK_`),
  KEY `FK_HACTI_HPROCI` (`HPROCI_`),
  KEY `FK_HTI_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HACTI_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HTI_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_hist_actinst`
--

/*!40000 ALTER TABLE `jbpm4_hist_actinst` DISABLE KEYS */;
INSERT INTO `jbpm4_hist_actinst` (`DBID_`,`CLASS_`,`DBVERSION_`,`HPROCI_`,`TYPE_`,`EXECUTION_`,`ACTIVITY_NAME_`,`START_`,`END_`,`DURATION_`,`TRANSITION_`,`NEXTIDX_`,`HTASK_`) VALUES 
 (1,'task',1,1,'task','pd5246674244169262478.1','测试计划','2010-05-15 11:36:53',NULL,0,NULL,1,1),
 (2,'task',1,2,'task','pd5246674244169262478.2','测试计划','2010-05-15 12:07:31',NULL,0,NULL,1,2),
 (3,'task',1,3,'task','pd5246674244169262478.3','测试计划','2010-05-15 12:07:40',NULL,0,NULL,1,3),
 (4,'task',1,4,'task','pd5246674244169262478.4','测试计划','2010-05-15 12:07:48',NULL,0,NULL,1,4),
 (5,'task',1,5,'task','pd5246674244169262478.5','测试计划','2010-05-15 12:08:00',NULL,0,NULL,1,5);
/*!40000 ALTER TABLE `jbpm4_hist_actinst` ENABLE KEYS */;


--
-- Definition of table `jbpm4_hist_detail`
--

DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USERID_` varchar(255) default NULL,
  `TIME_` datetime default NULL,
  `HPROCI_` bigint(20) default NULL,
  `HPROCIIDX_` int(11) default NULL,
  `HACTI_` bigint(20) default NULL,
  `HACTIIDX_` int(11) default NULL,
  `HTASK_` bigint(20) default NULL,
  `HTASKIDX_` int(11) default NULL,
  `HVAR_` bigint(20) default NULL,
  `HVARIDX_` int(11) default NULL,
  `MESSAGE_` longtext,
  `OLD_INT_` int(11) default NULL,
  `NEW_INT_` int(11) default NULL,
  `OLD_STR_` varchar(255) default NULL,
  `NEW_STR_` varchar(255) default NULL,
  `OLD_TIME_` datetime default NULL,
  `NEW_TIME_` datetime default NULL,
  `PARENT_` bigint(20) default NULL,
  `PARENT_IDX_` int(11) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_HDET_HACTI` (`HACTI_`),
  KEY `IDX_HDET_HPROCI` (`HPROCI_`),
  KEY `IDX_HDETAIL_HACTI` (`HACTI_`),
  KEY `IDX_HDETAIL_HVAR` (`HVAR_`),
  KEY `IDX_HDETAIL_HTASK` (`HTASK_`),
  KEY `IDX_HDETAIL_HPROCI` (`HPROCI_`),
  KEY `IDX_HDET_HVAR` (`HVAR_`),
  KEY `IDX_HDET_HTASK` (`HTASK_`),
  KEY `FK_HDETAIL_HPROCI` (`HPROCI_`),
  KEY `FK_HDETAIL_HACTI` (`HACTI_`),
  KEY `FK_HDETAIL_HTASK` (`HTASK_`),
  KEY `FK_HDETAIL_HVAR` (`HVAR_`),
  CONSTRAINT `FK_HDETAIL_HACTI` FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `FK_HDETAIL_HVAR` FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_hist_detail`
--

/*!40000 ALTER TABLE `jbpm4_hist_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_hist_detail` ENABLE KEYS */;


--
-- Definition of table `jbpm4_hist_procinst`
--

DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) default NULL,
  `PROCDEFID_` varchar(255) default NULL,
  `KEY_` varchar(255) default NULL,
  `START_` datetime default NULL,
  `END_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `STATE_` varchar(255) default NULL,
  `ENDACTIVITY_` varchar(255) default NULL,
  `NEXTIDX_` int(11) default NULL,
  PRIMARY KEY  (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_hist_procinst`
--

/*!40000 ALTER TABLE `jbpm4_hist_procinst` DISABLE KEYS */;
INSERT INTO `jbpm4_hist_procinst` (`DBID_`,`DBVERSION_`,`ID_`,`PROCDEFID_`,`KEY_`,`START_`,`END_`,`DURATION_`,`STATE_`,`ENDACTIVITY_`,`NEXTIDX_`) VALUES 
 (1,0,'pd5246674244169262478.1','pd5246674244169262478-1',NULL,'2010-05-15 11:36:53',NULL,NULL,'active',NULL,1),
 (2,0,'pd5246674244169262478.2','pd5246674244169262478-1',NULL,'2010-05-15 12:07:31',NULL,NULL,'active',NULL,1),
 (3,0,'pd5246674244169262478.3','pd5246674244169262478-1',NULL,'2010-05-15 12:07:40',NULL,NULL,'active',NULL,1),
 (4,0,'pd5246674244169262478.4','pd5246674244169262478-1',NULL,'2010-05-15 12:07:48',NULL,NULL,'active',NULL,1),
 (5,0,'pd5246674244169262478.5','pd5246674244169262478-1',NULL,'2010-05-15 12:08:00',NULL,NULL,'active',NULL,1);
/*!40000 ALTER TABLE `jbpm4_hist_procinst` ENABLE KEYS */;


--
-- Definition of table `jbpm4_hist_task`
--

DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `EXECUTION_` varchar(255) default NULL,
  `OUTCOME_` varchar(255) default NULL,
  `ASSIGNEE_` varchar(255) default NULL,
  `PRIORITY_` int(11) default NULL,
  `STATE_` varchar(255) default NULL,
  `CREATE_` datetime default NULL,
  `END_` datetime default NULL,
  `DURATION_` bigint(20) default NULL,
  `NEXTIDX_` int(11) default NULL,
  `SUPERTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `FK_HSUPERT_SUB` (`SUPERTASK_`),
  CONSTRAINT `FK_HSUPERT_SUB` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_hist_task`
--

/*!40000 ALTER TABLE `jbpm4_hist_task` DISABLE KEYS */;
INSERT INTO `jbpm4_hist_task` (`DBID_`,`DBVERSION_`,`EXECUTION_`,`OUTCOME_`,`ASSIGNEE_`,`PRIORITY_`,`STATE_`,`CREATE_`,`END_`,`DURATION_`,`NEXTIDX_`,`SUPERTASK_`) VALUES 
 (1,4,'pd5246674244169262478.1',NULL,'2',0,NULL,'2010-05-15 11:36:53',NULL,0,1,NULL),
 (2,1,'pd5246674244169262478.2',NULL,'1',0,NULL,'2010-05-15 12:07:31',NULL,0,1,NULL),
 (3,1,'pd5246674244169262478.3',NULL,'1',0,NULL,'2010-05-15 12:07:40',NULL,0,1,NULL),
 (4,1,'pd5246674244169262478.4',NULL,'1',0,NULL,'2010-05-15 12:07:48',NULL,0,1,NULL),
 (5,2,'pd5246674244169262478.5',NULL,'2',0,NULL,'2010-05-15 12:08:00',NULL,0,1,NULL);
/*!40000 ALTER TABLE `jbpm4_hist_task` ENABLE KEYS */;


--
-- Definition of table `jbpm4_hist_var`
--

DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `PROCINSTID_` varchar(255) default NULL,
  `EXECUTIONID_` varchar(255) default NULL,
  `VARNAME_` varchar(255) default NULL,
  `VALUE_` varchar(255) default NULL,
  `HPROCI_` bigint(20) default NULL,
  `HTASK_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_HVAR_HPROCI` (`HPROCI_`),
  KEY `IDX_HVAR_HTASK` (`HTASK_`),
  KEY `FK_HVAR_HPROCI` (`HPROCI_`),
  KEY `FK_HVAR_HTASK` (`HTASK_`),
  CONSTRAINT `FK_HVAR_HPROCI` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `FK_HVAR_HTASK` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_hist_var`
--

/*!40000 ALTER TABLE `jbpm4_hist_var` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_hist_var` ENABLE KEYS */;


--
-- Definition of table `jbpm4_id_group`
--

DROP TABLE IF EXISTS `jbpm4_id_group`;
CREATE TABLE `jbpm4_id_group` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) default NULL,
  `NAME_` varchar(255) default NULL,
  `TYPE_` varchar(255) default NULL,
  `PARENT_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_GROUP_PARENT` (`PARENT_`),
  KEY `FK_GROUP_PARENT` (`PARENT_`),
  CONSTRAINT `FK_GROUP_PARENT` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_id_group` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_id_group`
--

/*!40000 ALTER TABLE `jbpm4_id_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_id_group` ENABLE KEYS */;


--
-- Definition of table `jbpm4_id_membership`
--

DROP TABLE IF EXISTS `jbpm4_id_membership`;
CREATE TABLE `jbpm4_id_membership` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `USER_` bigint(20) default NULL,
  `GROUP_` bigint(20) default NULL,
  `NAME_` varchar(255) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_MEM_USER` (`USER_`),
  KEY `IDX_MEM_GROUP` (`GROUP_`),
  KEY `FK_MEM_GROUP` (`GROUP_`),
  KEY `FK_MEM_USER` (`USER_`),
  CONSTRAINT `FK_MEM_GROUP` FOREIGN KEY (`GROUP_`) REFERENCES `jbpm4_id_group` (`DBID_`),
  CONSTRAINT `FK_MEM_USER` FOREIGN KEY (`USER_`) REFERENCES `jbpm4_id_user` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_id_membership`
--

/*!40000 ALTER TABLE `jbpm4_id_membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_id_membership` ENABLE KEYS */;


--
-- Definition of table `jbpm4_id_user`
--

DROP TABLE IF EXISTS `jbpm4_id_user`;
CREATE TABLE `jbpm4_id_user` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) default NULL,
  `PASSWORD_` varchar(255) default NULL,
  `GIVENNAME_` varchar(255) default NULL,
  `FAMILYNAME_` varchar(255) default NULL,
  `BUSINESSEMAIL_` varchar(255) default NULL,
  PRIMARY KEY  (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_id_user`
--

/*!40000 ALTER TABLE `jbpm4_id_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_id_user` ENABLE KEYS */;


--
-- Definition of table `jbpm4_job`
--

DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime default NULL,
  `STATE_` varchar(255) default NULL,
  `ISEXCLUSIVE_` bit(1) default NULL,
  `LOCKOWNER_` varchar(255) default NULL,
  `LOCKEXPTIME_` datetime default NULL,
  `EXCEPTION_` longtext,
  `RETRIES_` int(11) default NULL,
  `PROCESSINSTANCE_` bigint(20) default NULL,
  `EXECUTION_` bigint(20) default NULL,
  `CFG_` bigint(20) default NULL,
  `SIGNAL_` varchar(255) default NULL,
  `EVENT_` varchar(255) default NULL,
  `REPEAT_` varchar(255) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_JOBRETRIES` (`RETRIES_`),
  KEY `IDX_JOB_CFG` (`CFG_`),
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`),
  KEY `IDX_JOB_EXE` (`EXECUTION_`),
  KEY `IDX_JOBLOCKEXP` (`LOCKEXPTIME_`),
  KEY `IDX_JOBDUEDATE` (`DUEDATE_`),
  KEY `FK_JOB_CFG` (`CFG_`),
  CONSTRAINT `FK_JOB_CFG` FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_job`
--

/*!40000 ALTER TABLE `jbpm4_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_job` ENABLE KEYS */;


--
-- Definition of table `jbpm4_lob`
--

DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `BLOB_VALUE_` longblob,
  `DEPLOYMENT_` bigint(20) default NULL,
  `NAME_` longtext,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  KEY `FK_LOB_DEPLOYMENT` (`DEPLOYMENT_`),
  CONSTRAINT `FK_LOB_DEPLOYMENT` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_lob`
--

/*!40000 ALTER TABLE `jbpm4_lob` DISABLE KEYS */;
INSERT INTO `jbpm4_lob` (`DBID_`,`DBVERSION_`,`BLOB_VALUE_`,`DEPLOYMENT_`,`NAME_`) VALUES 
 (1,0,0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706435323436363734323434313639323632343738223E3C7374617274206E616D653D22E5BC80E5A78B2220673D223230392C34302C35382C3538223E3C7472616E736974696F6E206E616D653D22E8BF9EE7BABF312220746F3D22E6B58BE8AF95E8AEA1E588922220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22E6B58BE8AF95E8AEA1E588922220673D223138302C3135332C3131342C3533223E3C7472616E736974696F6E206E616D653D22E8BF9EE7BABF322220746F3D22E5BC80E5A78BE6B58BE8AF952220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22E5BC80E5A78BE6B58BE8AF952220673D223137382C3235382C3131362C3537223E3C7472616E736974696F6E206E616D653D22E8BF9EE7BABF332220746F3D22E7BB93E69D9F312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22E7BB93E69D9F312220673D223230362C3336352C35382C3538222F3E3C2F70726F636573733E,1,'process.jpdl.xml'),
 (2,0,0xACED000573720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724EA1C822C86CD16D0200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F6874736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C396940C2EA600CBC0200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E747F2A123D44058D160200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C658915988CB6F698900200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70,NULL,NULL),
 (3,0,0xACED000573720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724EA1C822C86CD16D0200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F6874736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C396940C2EA600CBC0200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E747F2A123D44058D160200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C658915988CB6F698900200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70,NULL,NULL),
 (4,0,0xACED000573720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724EA1C822C86CD16D0200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F6874736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C396940C2EA600CBC0200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E747F2A123D44058D160200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C658915988CB6F698900200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70,NULL,NULL),
 (5,0,0xACED000573720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724EA1C822C86CD16D0200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F6874736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C396940C2EA600CBC0200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E747F2A123D44058D160200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C658915988CB6F698900200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70,NULL,NULL),
 (6,0,0xACED000573720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724EA1C822C86CD16D0200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F6874736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C396940C2EA600CBC0200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E6874736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E747F2A123D44058D160200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C658915988CB6F698900200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E6874736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70,NULL,NULL),
 (7,0,0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D225554462D38223F3E0A0A3C70726F63657373206E616D653D224172636869766553656E64466C6F772220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C223E0A2020203C7374617274206E616D653D22E5BC80E5A78B2220673D2235352C3130382C34382C3438223E0A2020202020203C7472616E736974696F6E206E616D653D22E6B581E7A88BE590AFE58AA82220746F3D22E58F91E69687E6A0B8E7A8BF2220673D222D32312C2D3133222F3E0A2020203C2F73746172743E0A2020203C7461736B206E616D653D22E58F91E69687E6A0B8E7A8BF2220673D223138372C3130332C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E6A0B8E7A8BF2220746F3D22E7A791E5AEA4E5AEA1E6A0B82220673D222D31332C2D3134222F3E0A2020203C2F7461736B3E0A2020203C7461736B206E616D653D22E7A791E5AEA4E5AEA1E6A0B82220673D223336332C3130332C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E5AEA1E6A0B82220746F3D22E4B8BBE7AEA1E9A286E5AFBCE5AEA1E689B92220673D222D32382C2D3132222F3E0A2020203C2F7461736B3E0A2020203C7461736B206E616D653D22E4B8BBE7AEA1E9A286E5AFBCE5AEA1E689B92220673D223336352C3233342C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E5AEA1E689B92220746F3D22E58886E7AEA1E9A286E5AFBCE7ADBEE58F912220673D222D33342C2D3134222F3E0A2020203C2F7461736B3E0A2020203C7461736B206E616D653D22E58886E7AEA1E9A286E5AFBCE7ADBEE58F912220673D223336352C3335372C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E7ADBEE58F912220746F3D22E58F91E69687E6A0A1E5AFB92220673D222D342C2D3133222F3E0A2020203C2F7461736B3E0A2020203C7461736B206E616D653D22E58F91E69687E6A0A1E5AFB92220673D223138312C3335372C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E6A0A1E5AFB92220746F3D22E58F91E69687E58886E58F912220673D222D32392C30222F3E0A2020203C2F7461736B3E0A2020203C7461736B206E616D653D22E58F91E69687E58886E58F912220673D223138322C3233352C39322C3532223E0A2020202020203C7472616E736974696F6E206E616D653D22E58886E58F912220746F3D22E5BD92E6A1A32220673D222D31332C2D3133222F3E0A2020203C2F7461736B3E0A2020203C656E64206E616D653D22E5BD92E6A1A32220673D2236302C3234312C34382C3438222F3E0A3C2F70726F636573733E,2,'process.jpdl.xml'),
 (8,0,0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A0A3C70726F63657373206E616D653D224172636869766573526563466C6F772220786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C223E0A2020203C737461727420673D223132312C34392C34382C343822206E616D653D22E5BC80E5A78B223E0A2020202020203C7472616E736974696F6E20673D222D37312C2D313722206E616D653D22E68F90E4BAA4E68B9FE58A9E2220746F3D22E799BBE8AEB0E68B9FE58A9E222F3E0A2020203C2F73746172743E0A2020203C7461736B20673D2239382C3135392C39322C353222206E616D653D22E799BBE8AEB0E68B9FE58A9E223E0A2020202020203C7472616E736974696F6E20673D222D37312C2D313722206E616D653D22E68F90E4BAA4E9A286E5AFBCE689B9E7A4BA2220746F3D22E9A286E5AFBCE689B9E7A4BA222F3E0A2020203C2F7461736B3E0A2020203C7461736B20673D2239392C3236312C39322C353222206E616D653D22E9A286E5AFBCE689B9E7A4BA223E0A2020202020203C7472616E736974696F6E20673D222D37312C2D313722206E616D653D22E68F90E4BAA4E585ACE69687E58886E58F912220746F3D22E585ACE69687E58886E58F91222F3E0A2020203C2F7461736B3E0A2020203C7461736B20673D223332382C3236322C39322C353222206E616D653D22E585ACE69687E58886E58F91223E0A2020202020203C7472616E736974696F6E20673D222D37312C2D313722206E616D653D22E68F90E4BAA4E689BFE58A9EE4BCA0E998852220746F3D22E689BFE58A9EE4BCA0E99885222F3E0A2020203C2F7461736B3E0A2020203C7461736B20673D223332382C3135312C39322C353222206E616D653D22E689BFE58A9EE4BCA0E99885223E0A2020202020203C7472616E736974696F6E20673D222D34372C2D313722206E616D653D22E7BB93E69D9F2220746F3D22E7BB93E69D9F222F3E0A2020203C2F7461736B3E0A2020203C656E6420673D223334392C35332C34382C343822206E616D653D22E7BB93E69D9F222F3E0A3C2F70726F636573733E,3,'process.jpdl.xml');
/*!40000 ALTER TABLE `jbpm4_lob` ENABLE KEYS */;


--
-- Definition of table `jbpm4_participation`
--

DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `GROUPID_` varchar(255) default NULL,
  `USERID_` varchar(255) default NULL,
  `TYPE_` varchar(255) default NULL,
  `TASK_` bigint(20) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_PART_TASK` (`TASK_`),
  KEY `FK_PART_SWIMLANE` (`SWIMLANE_`),
  KEY `FK_PART_TASK` (`TASK_`),
  CONSTRAINT `FK_PART_SWIMLANE` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `FK_PART_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_participation`
--

/*!40000 ALTER TABLE `jbpm4_participation` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_participation` ENABLE KEYS */;


--
-- Definition of table `jbpm4_swimlane`
--

DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `ASSIGNEE_` varchar(255) default NULL,
  `EXECUTION_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_SWIMLANE_EXEC` (`EXECUTION_`),
  KEY `FK_SWIMLANE_EXEC` (`EXECUTION_`),
  CONSTRAINT `FK_SWIMLANE_EXEC` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_swimlane`
--

/*!40000 ALTER TABLE `jbpm4_swimlane` DISABLE KEYS */;
/*!40000 ALTER TABLE `jbpm4_swimlane` ENABLE KEYS */;


--
-- Definition of table `jbpm4_task`
--

DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` char(1) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) default NULL,
  `DESCR_` longtext,
  `STATE_` varchar(255) default NULL,
  `SUSPHISTSTATE_` varchar(255) default NULL,
  `ASSIGNEE_` varchar(255) default NULL,
  `FORM_` varchar(255) default NULL,
  `PRIORITY_` int(11) default NULL,
  `CREATE_` datetime default NULL,
  `DUEDATE_` datetime default NULL,
  `PROGRESS_` int(11) default NULL,
  `SIGNALLING_` bit(1) default NULL,
  `EXECUTION_ID_` varchar(255) default NULL,
  `ACTIVITY_NAME_` varchar(255) default NULL,
  `HASVARS_` bit(1) default NULL,
  `SUPERTASK_` bigint(20) default NULL,
  `EXECUTION_` bigint(20) default NULL,
  `PROCINST_` bigint(20) default NULL,
  `SWIMLANE_` bigint(20) default NULL,
  `TASKDEFNAME_` varchar(255) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_TASK_SUPERTASK` (`SUPERTASK_`),
  KEY `FK_TASK_SWIML` (`SWIMLANE_`),
  KEY `FK_TASK_SUPERTASK` (`SUPERTASK_`),
  CONSTRAINT `FK_TASK_SUPERTASK` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`),
  CONSTRAINT `FK_TASK_SWIML` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_task`
--

/*!40000 ALTER TABLE `jbpm4_task` DISABLE KEYS */;
INSERT INTO `jbpm4_task` (`DBID_`,`CLASS_`,`DBVERSION_`,`NAME_`,`DESCR_`,`STATE_`,`SUSPHISTSTATE_`,`ASSIGNEE_`,`FORM_`,`PRIORITY_`,`CREATE_`,`DUEDATE_`,`PROGRESS_`,`SIGNALLING_`,`EXECUTION_ID_`,`ACTIVITY_NAME_`,`HASVARS_`,`SUPERTASK_`,`EXECUTION_`,`PROCINST_`,`SWIMLANE_`,`TASKDEFNAME_`) VALUES 
 (1,'T',4,'测试计划',NULL,'open',NULL,'2',NULL,0,'2010-05-15 11:36:53',NULL,NULL,0x01,'pd5246674244169262478.1','测试计划',0x00,NULL,1,1,NULL,'测试计划'),
 (2,'T',1,'测试计划',NULL,'open',NULL,'1',NULL,0,'2010-05-15 12:07:31',NULL,NULL,0x01,'pd5246674244169262478.2','测试计划',0x00,NULL,2,2,NULL,'测试计划'),
 (3,'T',1,'测试计划',NULL,'open',NULL,'1',NULL,0,'2010-05-15 12:07:40',NULL,NULL,0x01,'pd5246674244169262478.3','测试计划',0x00,NULL,3,3,NULL,'测试计划'),
 (4,'T',1,'测试计划',NULL,'open',NULL,'1',NULL,0,'2010-05-15 12:07:48',NULL,NULL,0x01,'pd5246674244169262478.4','测试计划',0x00,NULL,4,4,NULL,'测试计划'),
 (5,'T',2,'测试计划',NULL,'open',NULL,'2',NULL,0,'2010-05-15 12:08:00',NULL,NULL,0x01,'pd5246674244169262478.5','测试计划',0x00,NULL,5,5,NULL,'测试计划');
/*!40000 ALTER TABLE `jbpm4_task` ENABLE KEYS */;


--
-- Definition of table `jbpm4_variable`
--

DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
  `DBID_` bigint(20) NOT NULL auto_increment,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) default NULL,
  `CONVERTER_` varchar(255) default NULL,
  `HIST_` bit(1) default NULL,
  `EXECUTION_` bigint(20) default NULL,
  `TASK_` bigint(20) default NULL,
  `DATE_VALUE_` datetime default NULL,
  `DOUBLE_VALUE_` double default NULL,
  `LONG_VALUE_` bigint(20) default NULL,
  `STRING_VALUE_` varchar(255) default NULL,
  `TEXT_VALUE_` longtext,
  `LOB_` bigint(20) default NULL,
  `EXESYS_` bigint(20) default NULL,
  PRIMARY KEY  (`DBID_`),
  KEY `IDX_VAR_EXESYS` (`EXESYS_`),
  KEY `IDX_VAR_TASK` (`TASK_`),
  KEY `IDX_VAR_EXECUTION` (`EXECUTION_`),
  KEY `IDX_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_LOB` (`LOB_`),
  KEY `FK_VAR_EXECUTION` (`EXECUTION_`),
  KEY `FK_VAR_EXESYS` (`EXESYS_`),
  KEY `FK_VAR_TASK` (`TASK_`),
  CONSTRAINT `FK_VAR_EXECUTION` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_EXESYS` FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `FK_VAR_LOB` FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`),
  CONSTRAINT `FK_VAR_TASK` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `jbpm4_variable`
--

/*!40000 ALTER TABLE `jbpm4_variable` DISABLE KEYS */;
INSERT INTO `jbpm4_variable` (`DBID_`,`CLASS_`,`DBVERSION_`,`KEY_`,`CONVERTER_`,`HIST_`,`EXECUTION_`,`TASK_`,`DATE_VALUE_`,`DOUBLE_VALUE_`,`LONG_VALUE_`,`STRING_VALUE_`,`TEXT_VALUE_`,`LOB_`,`EXESYS_`) VALUES 
 (1,'blob',0,'flowStartUser','ser-bytes',0x00,1,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL),
 (2,'string',0,'processName',NULL,0x00,1,NULL,NULL,NULL,NULL,'开发测试',NULL,NULL,NULL),
 (3,'string',0,'note',NULL,0x00,1,NULL,NULL,NULL,NULL,'测试开发',NULL,NULL,NULL),
 (4,'blob',0,'flowStartUser','ser-bytes',0x00,2,NULL,NULL,NULL,NULL,NULL,NULL,3,NULL),
 (5,'string',0,'processName',NULL,0x00,2,NULL,NULL,NULL,NULL,'开发测试',NULL,NULL,NULL),
 (6,'string',0,'note',NULL,0x00,2,NULL,NULL,NULL,NULL,'测试及开发\r\n',NULL,NULL,NULL),
 (7,'blob',0,'flowStartUser','ser-bytes',0x00,3,NULL,NULL,NULL,NULL,NULL,NULL,4,NULL),
 (8,'string',0,'processName',NULL,0x00,3,NULL,NULL,NULL,NULL,'开发测试',NULL,NULL,NULL),
 (9,'string',0,'note',NULL,0x00,3,NULL,NULL,NULL,NULL,'薄情及开发',NULL,NULL,NULL),
 (10,'blob',0,'flowStartUser','ser-bytes',0x00,4,NULL,NULL,NULL,NULL,NULL,NULL,5,NULL),
 (11,'string',0,'processName',NULL,0x00,4,NULL,NULL,NULL,NULL,'开发测试',NULL,NULL,NULL),
 (12,'string',0,'note',NULL,0x00,4,NULL,NULL,NULL,NULL,'伤心及开发',NULL,NULL,NULL),
 (13,'blob',0,'flowStartUser','ser-bytes',0x00,5,NULL,NULL,NULL,NULL,NULL,NULL,6,NULL),
 (14,'string',0,'processName',NULL,0x00,5,NULL,NULL,NULL,NULL,'开发测试',NULL,NULL,NULL),
 (15,'string',0,'note',NULL,0x00,5,NULL,NULL,NULL,NULL,'送还垂发',NULL,NULL,NULL);
/*!40000 ALTER TABLE `jbpm4_variable` ENABLE KEYS */;


--
-- Definition of table `job`
--

DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `jobId` bigint(20) NOT NULL auto_increment,
  `jobName` varchar(128) NOT NULL COMMENT '职位名称',
  `depId` bigint(20) NOT NULL COMMENT '所属部门',
  `memo` varchar(512) default NULL COMMENT '备注',
  `delFlag` smallint(6) NOT NULL default '0' COMMENT '0=未删除\r\n            1=删除',
  PRIMARY KEY  (`jobId`),
  KEY `FK_JB_R_DPT` (`depId`),
  CONSTRAINT `FK_JB_R_DPT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job`
--

/*!40000 ALTER TABLE `job` DISABLE KEYS */;
/*!40000 ALTER TABLE `job` ENABLE KEYS */;


--
-- Definition of table `job_change`
--

DROP TABLE IF EXISTS `job_change`;
CREATE TABLE `job_change` (
  `changeId` bigint(20) NOT NULL auto_increment,
  `profileId` bigint(20) NOT NULL,
  `profileNo` varchar(64) NOT NULL,
  `userName` varchar(64) default NULL,
  `orgJobId` bigint(20) NOT NULL,
  `orgJobName` varchar(64) NOT NULL,
  `newJobId` bigint(20) NOT NULL,
  `newJobName` varchar(64) NOT NULL,
  `orgStandardId` bigint(20) default NULL COMMENT '原薪酬标准单ID',
  `orgStandardNo` varchar(64) default NULL,
  `orgStandardName` varchar(64) default NULL,
  `orgDepId` bigint(20) default NULL,
  `orgDepName` varchar(128) default NULL,
  `orgTotalMoney` decimal(18,2) default NULL,
  `newStandardId` bigint(20) default NULL,
  `newStandardNo` varchar(64) default NULL,
  `newStandardName` varchar(64) default NULL,
  `newDepId` bigint(20) default NULL,
  `newDepName` varchar(128) default NULL,
  `newTotalMoney` decimal(18,2) default NULL,
  `changeReason` varchar(1024) default NULL,
  `regTime` datetime default NULL,
  `regName` varchar(64) default NULL,
  `checkName` varchar(64) default NULL,
  `checkTime` datetime default NULL,
  `checkOpinion` varchar(512) default NULL,
  `status` smallint(6) default NULL COMMENT '状态\r\n            \r\n            -1=草稿\r\n            0=提交审批\r\n            1=通过审批\r\n            2=未通过审批\r\n            ',
  `memo` varchar(1024) default NULL,
  PRIMARY KEY  (`changeId`),
  KEY `FK_JBC_R_JBN` (`newJobId`),
  KEY `FK_JBC_R_JBO` (`orgJobId`),
  CONSTRAINT `FK_JBC_R_JBN` FOREIGN KEY (`newJobId`) REFERENCES `job` (`jobId`),
  CONSTRAINT `FK_JBC_R_JBO` FOREIGN KEY (`orgJobId`) REFERENCES `job` (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `job_change`
--

/*!40000 ALTER TABLE `job_change` DISABLE KEYS */;
/*!40000 ALTER TABLE `job_change` ENABLE KEYS */;


--
-- Definition of table `leader_read`
--

DROP TABLE IF EXISTS `leader_read`;
CREATE TABLE `leader_read` (
  `readId` bigint(20) NOT NULL auto_increment,
  `leaderName` varchar(64) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `leaderOpinion` varchar(128) default NULL,
  `createtime` datetime NOT NULL,
  `archivesId` bigint(20) default NULL,
  `depLevel` int(11) default NULL,
  `depName` varchar(128) default NULL,
  `isPass` smallint(6) NOT NULL COMMENT '0=尚未批\r\n            1=审批通过\r\n            2=审批未通过',
  `checkName` varchar(128) default NULL,
  PRIMARY KEY  (`readId`),
  KEY `FK_LDR_R_ARV` (`archivesId`),
  CONSTRAINT `FK_LDR_R_ARV` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='领导批示';

--
-- Dumping data for table `leader_read`
--

/*!40000 ALTER TABLE `leader_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `leader_read` ENABLE KEYS */;


--
-- Definition of table `mail`
--

DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `mailId` bigint(20) NOT NULL auto_increment,
  `sender` varchar(32) NOT NULL,
  `senderId` bigint(20) NOT NULL,
  `importantFlag` smallint(6) NOT NULL COMMENT '1=一般\r\n            2=重要\r\n            3=非常重要',
  `sendTime` datetime NOT NULL,
  `content` text NOT NULL COMMENT '邮件内容',
  `subject` varchar(256) NOT NULL COMMENT '邮件标题',
  `copyToNames` varchar(1000) default NULL COMMENT '抄送人姓名列表',
  `copyToIDs` varchar(1000) default NULL COMMENT '抄送人ID列表\r\n            用'',''分开',
  `recipientNames` varchar(1000) NOT NULL COMMENT '收件人姓名列表',
  `recipientIDs` varchar(1000) NOT NULL COMMENT '收件人ID列表\r\n            用'',''分隔',
  `mailStatus` smallint(6) NOT NULL COMMENT '邮件状态\r\n            1=正式邮件\r\n            0=草稿邮件',
  `fileIds` varchar(500) default NULL COMMENT '附件Ids，多个附件的ID，通过,分割',
  `filenames` varchar(500) default NULL COMMENT '附件名称列表，通过,进行分割',
  PRIMARY KEY  (`mailId`),
  KEY `FK_ML_R_AU` (`senderId`),
  CONSTRAINT `FK_ML_R_AU` FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件';

--
-- Dumping data for table `mail`
--

/*!40000 ALTER TABLE `mail` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail` ENABLE KEYS */;


--
-- Definition of table `mail_attach`
--

DROP TABLE IF EXISTS `mail_attach`;
CREATE TABLE `mail_attach` (
  `mailId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY  (`mailId`,`fileId`),
  KEY `FK_MA_R_FA` (`fileId`),
  CONSTRAINT `FK_MA_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_MA_R_ML` FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mail_attach`
--

/*!40000 ALTER TABLE `mail_attach` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_attach` ENABLE KEYS */;


--
-- Definition of table `mail_box`
--

DROP TABLE IF EXISTS `mail_box`;
CREATE TABLE `mail_box` (
  `boxId` bigint(20) NOT NULL auto_increment,
  `mailId` bigint(20) NOT NULL,
  `folderId` bigint(20) NOT NULL,
  `userId` bigint(20) default NULL COMMENT '主键',
  `sendTime` datetime NOT NULL,
  `delFlag` smallint(6) NOT NULL COMMENT 'del=1则代表删除',
  `readFlag` smallint(6) NOT NULL,
  `replyFlag` smallint(6) NOT NULL,
  `note` varchar(256) default NULL COMMENT 'note',
  PRIMARY KEY  (`boxId`),
  KEY `FK_MB_R_AU` (`userId`),
  KEY `FK_MB_R_FD` (`folderId`),
  KEY `FK_MB_R_ML` (`mailId`),
  CONSTRAINT `FK_MB_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_MB_R_FD` FOREIGN KEY (`folderId`) REFERENCES `mail_folder` (`folderId`),
  CONSTRAINT `FK_MB_R_ML` FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收件箱';

--
-- Dumping data for table `mail_box`
--

/*!40000 ALTER TABLE `mail_box` DISABLE KEYS */;
/*!40000 ALTER TABLE `mail_box` ENABLE KEYS */;


--
-- Definition of table `mail_folder`
--

DROP TABLE IF EXISTS `mail_folder`;
CREATE TABLE `mail_folder` (
  `folderId` bigint(20) NOT NULL auto_increment COMMENT '文件夹编号',
  `userId` bigint(20) default NULL COMMENT '主键',
  `folderName` varchar(128) NOT NULL COMMENT '文件夹名称',
  `parentId` bigint(20) default NULL COMMENT '父目录',
  `depLevel` int(11) NOT NULL COMMENT '目录层',
  `path` varchar(256) default NULL,
  `isPublic` smallint(6) NOT NULL COMMENT '1=表示共享，则所有的员工均可以使用该文件夹\r\n            0=私人文件夹',
  `folderType` smallint(6) NOT NULL COMMENT '文件夹类型\r\n            1=收信箱\r\n            2=发信箱\r\n            3=草稿箱\r\n            4=删除箱\r\n            10=其他',
  PRIMARY KEY  (`folderId`),
  KEY `FK_FD_R_AU` (`userId`),
  CONSTRAINT `FK_FD_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mail_folder`
--

/*!40000 ALTER TABLE `mail_folder` DISABLE KEYS */;
INSERT INTO `mail_folder` (`folderId`,`userId`,`folderName`,`parentId`,`depLevel`,`path`,`isPublic`,`folderType`) VALUES 
 (1,NULL,'收件箱',0,1,'0.1.',1,1),
 (2,NULL,'发件箱',0,1,'0.2.',1,2),
 (3,NULL,'草稿箱',0,1,'0.3.',1,3),
 (4,NULL,'垃圾箱',0,1,'0.4.',1,4);
/*!40000 ALTER TABLE `mail_folder` ENABLE KEYS */;


--
-- Definition of table `meeting`
--

DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
  `mettingId` bigint(20) NOT NULL auto_increment,
  `holdTime` datetime default NULL,
  `holdLocation` varchar(128) default NULL,
  `meetingName` varchar(128) default NULL,
  `attendUsers` varchar(128) default NULL,
  `holdDep` varchar(128) default NULL,
  `holdDepId` bigint(20) default NULL,
  `shortDesc` varchar(256) default NULL,
  `isFeedback` smallint(6) NOT NULL,
  `summary` text,
  PRIMARY KEY  (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `meeting`
--

/*!40000 ALTER TABLE `meeting` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting` ENABLE KEYS */;


--
-- Definition of table `meeting_attend`
--

DROP TABLE IF EXISTS `meeting_attend`;
CREATE TABLE `meeting_attend` (
  `attendId` int(11) NOT NULL auto_increment,
  `mettingId` bigint(20) NOT NULL,
  `userName` varchar(64) default NULL,
  `userId` bigint(20) default NULL,
  `depName` varchar(100) default NULL,
  `depId` bigint(20) default NULL,
  `attendType` smallint(6) NOT NULL default '0' COMMENT '参与类型\r\n            0=user\r\n            1=department',
  `feedback` varchar(1024) default NULL,
  `signTime` datetime default NULL,
  `signName` varchar(32) NOT NULL,
  PRIMARY KEY  (`attendId`),
  KEY `FK_MTA_R_MT` (`mettingId`),
  CONSTRAINT `FK_MTA_R_MT` FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议参与部门或人员';

--
-- Dumping data for table `meeting_attend`
--

/*!40000 ALTER TABLE `meeting_attend` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting_attend` ENABLE KEYS */;


--
-- Definition of table `meeting_file`
--

DROP TABLE IF EXISTS `meeting_file`;
CREATE TABLE `meeting_file` (
  `mettingId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY  (`mettingId`,`fileId`),
  KEY `FK_MF_R_FA` (`fileId`),
  CONSTRAINT `FK_MF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_MF_R_MT` FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `meeting_file`
--

/*!40000 ALTER TABLE `meeting_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `meeting_file` ENABLE KEYS */;


--
-- Definition of table `news`
--

DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `typeId` bigint(20) NOT NULL,
  `subjectIcon` varchar(128) default NULL,
  `subject` varchar(128) NOT NULL COMMENT '新闻标题',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `replyCounts` int(11) default NULL,
  `viewCounts` int(11) default NULL COMMENT '浏览数',
  `issuer` varchar(32) NOT NULL,
  `content` text NOT NULL COMMENT '内容',
  `updateTime` datetime NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '\r\n            0=待审核\r\n            1=审核通过',
  `isDeskImage` smallint(6) default NULL COMMENT '是否为图片新闻',
  PRIMARY KEY  (`newsId`),
  KEY `FK_NS_R_NT` (`typeId`),
  CONSTRAINT `FK_NS_R_NT` FOREIGN KEY (`typeId`) REFERENCES `news_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻';

--
-- Dumping data for table `news`
--

/*!40000 ALTER TABLE `news` DISABLE KEYS */;
/*!40000 ALTER TABLE `news` ENABLE KEYS */;


--
-- Definition of table `news_comment`
--

DROP TABLE IF EXISTS `news_comment`;
CREATE TABLE `news_comment` (
  `commentId` bigint(20) NOT NULL auto_increment,
  `newsId` bigint(20) NOT NULL,
  `content` varchar(500) NOT NULL,
  `createtime` datetime NOT NULL,
  `fullname` varchar(32) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY  (`commentId`),
  KEY `FK_NC_R_AU` (`userId`),
  KEY `FK_NC_R_NS` (`newsId`),
  CONSTRAINT `FK_NC_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_NC_R_NS` FOREIGN KEY (`newsId`) REFERENCES `news` (`newsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `news_comment`
--

/*!40000 ALTER TABLE `news_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_comment` ENABLE KEYS */;


--
-- Definition of table `news_type`
--

DROP TABLE IF EXISTS `news_type`;
CREATE TABLE `news_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL,
  `sn` int(11) NOT NULL,
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻类型';

--
-- Dumping data for table `news_type`
--

/*!40000 ALTER TABLE `news_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `news_type` ENABLE KEYS */;


--
-- Definition of table `notice`
--

DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeId` bigint(20) NOT NULL auto_increment,
  `postName` varchar(128) NOT NULL,
  `noticeTitle` varchar(128) NOT NULL,
  `noticeContent` varchar(3000) default NULL,
  `effectiveDate` date default NULL,
  `expirationDate` date default NULL,
  `state` smallint(6) NOT NULL,
  PRIMARY KEY  (`noticeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告';

--
-- Dumping data for table `notice`
--

/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;


--
-- Definition of table `office_goods`
--

DROP TABLE IF EXISTS `office_goods`;
CREATE TABLE `office_goods` (
  `goodsId` bigint(20) NOT NULL auto_increment,
  `typeId` bigint(20) NOT NULL COMMENT '所属分类',
  `goodsName` varchar(128) NOT NULL COMMENT '物品名称',
  `goodsNo` varchar(128) NOT NULL COMMENT '编号',
  `specifications` varchar(256) NOT NULL COMMENT '规格',
  `unit` varchar(64) NOT NULL COMMENT '计量单位',
  `isWarning` smallint(6) NOT NULL COMMENT '是否启用库存警示',
  `notes` varchar(500) default NULL COMMENT '备注',
  `stockCounts` int(11) NOT NULL COMMENT '库存总数',
  `warnCounts` int(11) NOT NULL COMMENT '最低库存数',
  PRIMARY KEY  (`goodsId`),
  KEY `FK_OG_R_OGT` (`typeId`),
  CONSTRAINT `FK_OG_R_OGT` FOREIGN KEY (`typeId`) REFERENCES `office_goods_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品';

--
-- Dumping data for table `office_goods`
--

/*!40000 ALTER TABLE `office_goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `office_goods` ENABLE KEYS */;


--
-- Definition of table `office_goods_type`
--

DROP TABLE IF EXISTS `office_goods_type`;
CREATE TABLE `office_goods_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品类型';

--
-- Dumping data for table `office_goods_type`
--

/*!40000 ALTER TABLE `office_goods_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `office_goods_type` ENABLE KEYS */;


--
-- Definition of table `phone_book`
--

DROP TABLE IF EXISTS `phone_book`;
CREATE TABLE `phone_book` (
  `phoneId` bigint(20) NOT NULL auto_increment,
  `fullname` varchar(128) NOT NULL,
  `title` varchar(32) NOT NULL COMMENT '先生\r\n            女士\r\n            小姐',
  `birthday` datetime default NULL,
  `nickName` varchar(32) default NULL,
  `duty` varchar(50) default NULL,
  `spouse` varchar(32) default NULL,
  `childs` varchar(40) default NULL,
  `companyName` varchar(100) default NULL,
  `companyAddress` varchar(128) default NULL,
  `companyPhone` varchar(32) default NULL,
  `companyFax` varchar(32) default NULL,
  `homeAddress` varchar(128) default NULL,
  `homeZip` varchar(12) default NULL,
  `mobile` varchar(32) default NULL,
  `phone` varchar(32) default NULL,
  `email` varchar(32) default NULL,
  `QQ` varchar(64) default NULL,
  `MSN` varchar(128) default NULL,
  `note` varchar(500) default NULL,
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) default NULL,
  `isShared` smallint(6) NOT NULL,
  PRIMARY KEY  (`phoneId`),
  KEY `FK_PB_R_AU` (`userId`),
  KEY `FK_PB_R_PG` (`groupId`),
  CONSTRAINT `FK_PB_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_PB_R_PG` FOREIGN KEY (`groupId`) REFERENCES `phone_group` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通讯簿';

--
-- Dumping data for table `phone_book`
--

/*!40000 ALTER TABLE `phone_book` DISABLE KEYS */;
/*!40000 ALTER TABLE `phone_book` ENABLE KEYS */;


--
-- Definition of table `phone_group`
--

DROP TABLE IF EXISTS `phone_group`;
CREATE TABLE `phone_group` (
  `groupId` bigint(20) NOT NULL auto_increment,
  `groupName` varchar(128) NOT NULL COMMENT '分组名称',
  `isShared` smallint(6) NOT NULL COMMENT '1=共享\r\n            0=私有',
  `SN` int(11) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY  (`groupId`),
  KEY `FK_PG_R_AU` (`userId`),
  CONSTRAINT `FK_PG_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `phone_group`
--

/*!40000 ALTER TABLE `phone_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `phone_group` ENABLE KEYS */;


--
-- Definition of table `plan_attend`
--

DROP TABLE IF EXISTS `plan_attend`;
CREATE TABLE `plan_attend` (
  `attendId` bigint(20) NOT NULL auto_increment,
  `depId` bigint(20) default NULL,
  `userId` bigint(20) default NULL,
  `planId` bigint(20) NOT NULL,
  `isDep` smallint(6) NOT NULL COMMENT '是否为部门',
  `isPrimary` smallint(6) default NULL COMMENT '是否负责人',
  PRIMARY KEY  (`attendId`),
  KEY `FK_PAD_R_DT` (`depId`),
  KEY `FK_PAD_R_UA` (`userId`),
  KEY `FK_PAD_R_WP` (`planId`),
  CONSTRAINT `FK_PAD_R_DT` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`),
  CONSTRAINT `FK_PAD_R_UA` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_PAD_R_WP` FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `plan_attend`
--

/*!40000 ALTER TABLE `plan_attend` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_attend` ENABLE KEYS */;


--
-- Definition of table `plan_file`
--

DROP TABLE IF EXISTS `plan_file`;
CREATE TABLE `plan_file` (
  `fileId` bigint(20) NOT NULL,
  `planId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`planId`),
  KEY `FK_PA_R_WP` (`planId`),
  CONSTRAINT `FK_PA_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_PA_R_WP` FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `plan_file`
--

/*!40000 ALTER TABLE `plan_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_file` ENABLE KEYS */;


--
-- Definition of table `plan_type`
--

DROP TABLE IF EXISTS `plan_type`;
CREATE TABLE `plan_type` (
  `typeId` bigint(20) NOT NULL auto_increment,
  `typeName` varchar(128) NOT NULL COMMENT '类别名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计划类型';

--
-- Dumping data for table `plan_type`
--

/*!40000 ALTER TABLE `plan_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `plan_type` ENABLE KEYS */;


--
-- Definition of table `pro_definition`
--

DROP TABLE IF EXISTS `pro_definition`;
CREATE TABLE `pro_definition` (
  `defId` bigint(20) NOT NULL auto_increment,
  `typeId` bigint(20) default NULL COMMENT '分类ID',
  `name` varchar(256) NOT NULL COMMENT '流程的名称',
  `description` varchar(1024) default NULL COMMENT '描述',
  `createtime` datetime default NULL COMMENT '创建时间',
  `deployId` varchar(64) NOT NULL COMMENT 'Jbpm 工作流id',
  `defXml` text COMMENT '流程定义XML',
  `drawDefXml` text,
  `isDefault` smallint(6) NOT NULL default '0' COMMENT '是否缺省\r\n            1=是\r\n            0=否',
  PRIMARY KEY  (`defId`),
  KEY `FK_PD_R_PT` (`typeId`),
  CONSTRAINT `FK_PD_R_PT` FOREIGN KEY (`typeId`) REFERENCES `pro_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程定义';

--
-- Dumping data for table `pro_definition`
--

/*!40000 ALTER TABLE `pro_definition` DISABLE KEYS */;
INSERT INTO `pro_definition` (`defId`,`typeId`,`name`,`description`,`createtime`,`deployId`,`defXml`,`drawDefXml`,`isDefault`) VALUES 
 (1,3,'开发测试','开发测试','2010-05-15 11:36:31','1','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd5246674244169262478\"><start name=\"开始\" g=\"209,40,58,58\"><transition name=\"连线1\" to=\"测试计划\" g=\"-24,-16\"/></start><task name=\"测试计划\" g=\"180,153,114,53\"><transition name=\"连线2\" to=\"开始测试\" g=\"-24,-16\"/></task><task name=\"开始测试\" g=\"178,258,116,57\"><transition name=\"连线3\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"206,365,58,58\"/></process>','<drawing id=\"0\"><figures><start id=\"1\" x=\"209\" y=\"40\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x6d4b;&#x8bd5;&#x8ba1;&#x5212;\" x=\"180\" y=\"153\" w=\"104\" h=\"43\"><a><text><string>&#x6d4b;&#x8bd5;&#x8ba1;&#x5212;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x5f00;&#x59cb;&#x6d4b;&#x8bd5;\" x=\"178\" y=\"258\" w=\"106\" h=\"47\"><a><text><string>&#x5f00;&#x59cb;&#x6d4b;&#x8bd5;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"4\" x=\"206\" y=\"365\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"5\" g=\"-24,-16\" name=\"&#x8fde;&#x7ebf;1\"><points><p colinear=\"true\" x=\"232.77737556561087\" y=\"88.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"232.2\" y=\"152.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"6\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"7\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"8\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"9\" g=\"-24,-16\" name=\"&#x8fde;&#x7ebf;2\"><points><p colinear=\"true\" x=\"231.79345794392523\" y=\"196.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"231.22523364485983\" y=\"257.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"a\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"b\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"c\" g=\"-24,-16\" name=\"&#x8fde;&#x7ebf;3\"><points><p colinear=\"true\" x=\"230.77581395348838\" y=\"305.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"230.22883720930233\" y=\"364.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><end ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>',0),
 (2,1,'发文流程','发文流程','2010-05-21 09:53:49','2','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n<process name=\"ArchiveSendFlow\" xmlns=\"http://jbpm.org/4.0/jpdl\">\n   <start name=\"开始\" g=\"55,108,48,48\">\n      <transition name=\"流程启动\" to=\"发文核稿\" g=\"-21,-13\"/>\n   </start>\n   <task name=\"发文核稿\" g=\"187,103,92,52\">\n      <transition name=\"核稿\" to=\"科室审核\" g=\"-13,-14\"/>\n   </task>\n   <task name=\"科室审核\" g=\"363,103,92,52\">\n      <transition name=\"审核\" to=\"主管领导审批\" g=\"-28,-12\"/>\n   </task>\n   <task name=\"主管领导审批\" g=\"365,234,92,52\">\n      <transition name=\"审批\" to=\"分管领导签发\" g=\"-34,-14\"/>\n   </task>\n   <task name=\"分管领导签发\" g=\"365,357,92,52\">\n      <transition name=\"签发\" to=\"发文校对\" g=\"-4,-13\"/>\n   </task>\n   <task name=\"发文校对\" g=\"181,357,92,52\">\n      <transition name=\"校对\" to=\"发文分发\" g=\"-29,0\"/>\n   </task>\n   <task name=\"发文分发\" g=\"182,235,92,52\">\n      <transition name=\"分发\" to=\"归档\" g=\"-13,-13\"/>\n   </task>\n   <end name=\"归档\" g=\"60,241,48,48\"/>\n</process>',NULL,0),
 (3,1,'收文流程','收文流程','2010-05-21 09:54:21','3','<?xml version=\"1.0\" encoding=\"GBK\"?>\n\n<process name=\"ArchivesRecFlow\" xmlns=\"http://jbpm.org/4.0/jpdl\">\n   <start g=\"121,49,48,48\" name=\"开始\">\n      <transition g=\"-71,-17\" name=\"提交拟办\" to=\"登记拟办\"/>\n   </start>\n   <task g=\"98,159,92,52\" name=\"登记拟办\">\n      <transition g=\"-71,-17\" name=\"提交领导批示\" to=\"领导批示\"/>\n   </task>\n   <task g=\"99,261,92,52\" name=\"领导批示\">\n      <transition g=\"-71,-17\" name=\"提交公文分发\" to=\"公文分发\"/>\n   </task>\n   <task g=\"328,262,92,52\" name=\"公文分发\">\n      <transition g=\"-71,-17\" name=\"提交承办传阅\" to=\"承办传阅\"/>\n   </task>\n   <task g=\"328,151,92,52\" name=\"承办传阅\">\n      <transition g=\"-47,-17\" name=\"结束\" to=\"结束\"/>\n   </task>\n   <end g=\"349,53,48,48\" name=\"结束\"/>\n</process>',NULL,0);
/*!40000 ALTER TABLE `pro_definition` ENABLE KEYS */;


--
-- Definition of table `pro_type`
--

DROP TABLE IF EXISTS `pro_type`;
CREATE TABLE `pro_type` (
  `typeId` bigint(20) NOT NULL auto_increment COMMENT '类别ID',
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY  (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程分类';

--
-- Dumping data for table `pro_type`
--

/*!40000 ALTER TABLE `pro_type` DISABLE KEYS */;
INSERT INTO `pro_type` (`typeId`,`typeName`) VALUES 
 (1,'公文'),
 (2,'行政'),
 (3,'日常');
/*!40000 ALTER TABLE `pro_type` ENABLE KEYS */;


--
-- Definition of table `pro_user_assign`
--

DROP TABLE IF EXISTS `pro_user_assign`;
CREATE TABLE `pro_user_assign` (
  `assignId` bigint(20) NOT NULL auto_increment COMMENT '授权ID',
  `deployId` varchar(128) NOT NULL COMMENT 'jbpm流程定义的id',
  `activityName` varchar(128) NOT NULL COMMENT '流程节点名称',
  `roleId` varchar(128) default NULL COMMENT '角色Id',
  `roleName` varchar(256) default NULL,
  `userId` varchar(128) default NULL COMMENT '用户ID',
  `username` varchar(256) default NULL,
  `isSigned` smallint(6) default '0' COMMENT '1=是会签任务\r\n            0=非会签任务\r\n            \r\n            若为会签任务，则需要为该会签添加会签的决策方式的设置\r\n            ',
  PRIMARY KEY  (`assignId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程过程中各个任务节点及启动流程时的角色';

--
-- Dumping data for table `pro_user_assign`
--

/*!40000 ALTER TABLE `pro_user_assign` DISABLE KEYS */;
/*!40000 ALTER TABLE `pro_user_assign` ENABLE KEYS */;


--
-- Definition of table `process_form`
--

DROP TABLE IF EXISTS `process_form`;
CREATE TABLE `process_form` (
  `formId` bigint(20) NOT NULL auto_increment,
  `runId` bigint(20) NOT NULL COMMENT '所属运行流程',
  `activityName` varchar(128) NOT NULL COMMENT '活动或任务名称',
  `sn` int(11) NOT NULL default '1' COMMENT '表单序号代表该流程任务执行经过的次数,如第一次经过时为1,第二次再次经过时变为2,\r\n            主要用于标识某一任务在流程中可能被不断回退.',
  `createtime` datetime NOT NULL,
  `creatorId` bigint(20) NOT NULL,
  `creatorName` varchar(64) NOT NULL,
  PRIMARY KEY  (`formId`),
  KEY `FK_PF_R_PR` (`runId`),
  CONSTRAINT `FK_PF_R_PR` FOREIGN KEY (`runId`) REFERENCES `process_run` (`runId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程表单\r\n存储保存在运行中的流程表单数据';

--
-- Dumping data for table `process_form`
--

/*!40000 ALTER TABLE `process_form` DISABLE KEYS */;
INSERT INTO `process_form` (`formId`,`runId`,`activityName`,`sn`,`createtime`,`creatorId`,`creatorName`) VALUES 
 (1,1,'开始',1,'2010-05-15 11:36:53',1,'超级管理员'),
 (2,2,'开始',1,'2010-05-15 12:07:31',1,'超级管理员'),
 (3,3,'开始',1,'2010-05-15 12:07:40',1,'超级管理员'),
 (4,4,'开始',1,'2010-05-15 12:07:48',1,'超级管理员'),
 (5,5,'开始',1,'2010-05-15 12:08:00',1,'超级管理员');
/*!40000 ALTER TABLE `process_form` ENABLE KEYS */;


--
-- Definition of table `process_run`
--

DROP TABLE IF EXISTS `process_run`;
CREATE TABLE `process_run` (
  `runId` bigint(20) NOT NULL auto_increment,
  `subject` varchar(256) NOT NULL COMMENT '标题\r\n            一般为流程名称＋格式化的时间',
  `creator` varchar(128) default NULL COMMENT '创建人',
  `userId` bigint(20) NOT NULL COMMENT '所属用户',
  `defId` bigint(20) NOT NULL COMMENT '所属流程定义',
  `piId` varchar(64) default NULL COMMENT '流程实例ID',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `runStatus` smallint(6) NOT NULL COMMENT '0=尚未启动\r\n            1=已经启动流程\r\n            2=运行结束',
  PRIMARY KEY  (`runId`),
  KEY `FK_PR_R_AU` (`userId`),
  KEY `FK_PR_R_PD` (`defId`),
  CONSTRAINT `FK_PR_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_PR_R_PD` FOREIGN KEY (`defId`) REFERENCES `pro_definition` (`defId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行中的流程';

--
-- Dumping data for table `process_run`
--

/*!40000 ALTER TABLE `process_run` DISABLE KEYS */;
INSERT INTO `process_run` (`runId`,`subject`,`creator`,`userId`,`defId`,`piId`,`createtime`,`runStatus`) VALUES 
 (1,'开发测试20100515-113652(超级管理员)','超级管理员',1,1,'pd5246674244169262478.1','2010-05-15 11:36:52',1),
 (2,'开发测试20100515-120731(超级管理员)','超级管理员',1,1,'pd5246674244169262478.2','2010-05-15 12:07:31',1),
 (3,'开发测试20100515-120740(超级管理员)','超级管理员',1,1,'pd5246674244169262478.3','2010-05-15 12:07:40',1),
 (4,'开发测试20100515-120748(超级管理员)','超级管理员',1,1,'pd5246674244169262478.4','2010-05-15 12:07:48',1),
 (5,'开发测试20100515-120800(超级管理员)','超级管理员',1,1,'pd5246674244169262478.5','2010-05-15 12:08:00',1);
/*!40000 ALTER TABLE `process_run` ENABLE KEYS */;


--
-- Definition of table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `productId` bigint(20) NOT NULL auto_increment,
  `productName` varchar(128) NOT NULL COMMENT '产品名称',
  `productModel` varchar(128) default NULL COMMENT '产品型号',
  `unit` varchar(128) default NULL COMMENT '计量单位',
  `costPrice` decimal(18,2) default NULL COMMENT '成本价',
  `salesPrice` decimal(18,2) default NULL COMMENT '出售价',
  `productDesc` varchar(512) default NULL COMMENT '产品描述',
  `providerId` bigint(20) NOT NULL COMMENT '所属供应商',
  `createtime` datetime NOT NULL COMMENT '收录时间',
  `updatetime` datetime NOT NULL,
  PRIMARY KEY  (`productId`),
  KEY `FK_PD_R_PUT` (`providerId`),
  CONSTRAINT `FK_PD_R_PUT` FOREIGN KEY (`providerId`) REFERENCES `provider` (`providerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商产品';

--
-- Dumping data for table `product`
--

/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;


--
-- Definition of table `project`
--

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `projectId` bigint(20) NOT NULL auto_increment,
  `projectName` varchar(128) NOT NULL COMMENT '项目名称',
  `projectNo` varchar(64) NOT NULL COMMENT '项目编号',
  `reqDesc` text COMMENT '需求描述',
  `isContract` smallint(6) NOT NULL COMMENT '是否签订合同',
  `fullname` varchar(32) NOT NULL COMMENT '联系人姓名',
  `mobile` varchar(32) default NULL COMMENT '手机',
  `phone` varchar(32) default NULL COMMENT '电话',
  `fax` varchar(32) default NULL COMMENT '传真',
  `otherContacts` varchar(128) default NULL COMMENT '其他联系方式',
  `customerId` bigint(20) NOT NULL COMMENT '所属客户',
  `userId` bigint(20) NOT NULL COMMENT '业务人员',
  PRIMARY KEY  (`projectId`),
  KEY `FK_PR_R_CS` (`customerId`),
  KEY `FK_PT_R_AU` (`userId`),
  CONSTRAINT `FK_PR_R_CS` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `FK_PT_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息';

--
-- Dumping data for table `project`
--

/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;


--
-- Definition of table `project_file`
--

DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
  `fileId` bigint(20) NOT NULL,
  `projectId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`projectId`),
  KEY `FK_PF_R_PT` (`projectId`),
  CONSTRAINT `FK_PF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_PF_R_PT` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目附件';

--
-- Dumping data for table `project_file`
--

/*!40000 ALTER TABLE `project_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_file` ENABLE KEYS */;


--
-- Definition of table `provider`
--

DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
  `providerId` bigint(20) NOT NULL auto_increment,
  `providerName` varchar(128) NOT NULL COMMENT '供应商名称',
  `contactor` varchar(128) NOT NULL COMMENT '联系人',
  `phone` varchar(32) NOT NULL COMMENT '电话',
  `fax` varchar(32) default NULL COMMENT '传真',
  `site` varchar(128) default NULL COMMENT '网址',
  `email` varchar(128) default NULL COMMENT '邮件',
  `address` varchar(128) NOT NULL COMMENT '地址',
  `zip` varchar(32) default NULL COMMENT '邮编',
  `openBank` varchar(128) default NULL COMMENT '开户行',
  `account` varchar(64) default NULL COMMENT '帐号',
  `notes` varchar(500) default NULL COMMENT '备注',
  `rank` int(11) default NULL COMMENT '供应商等级\r\n            1=一级供应商\r\n            2＝二级供应商\r\n            3＝三级供应商\r\n            4＝四级供应商\r\n            ',
  PRIMARY KEY  (`providerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商';

--
-- Dumping data for table `provider`
--

/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;


--
-- Definition of table `region`
--

DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `regionId` bigint(20) NOT NULL auto_increment,
  `regionName` varchar(128) NOT NULL COMMENT '地区名称',
  `regionType` smallint(6) NOT NULL COMMENT '地区类型\r\n            1=省份\r\n            2=市',
  `parentId` bigint(20) default NULL COMMENT '上级地区',
  PRIMARY KEY  (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区管理';

--
-- Dumping data for table `region`
--

/*!40000 ALTER TABLE `region` DISABLE KEYS */;
INSERT INTO `region` (`regionId`,`regionName`,`regionType`,`parentId`) VALUES 
 (1,'北京',2,0),
 (2,'上海',2,0),
 (3,'天津',2,0),
 (4,'重庆',2,0),
 (5,'河北',1,0),
 (6,'山西',1,0),
 (7,'内蒙古',1,0),
 (8,'辽宁',1,0),
 (9,'吉林',1,0),
 (10,'黑龙江',1,0),
 (11,'江苏',1,0),
 (12,'浙江',1,0),
 (13,'安徽',1,0),
 (14,'福建',1,0),
 (15,'江西',1,0),
 (16,'山东',1,0),
 (17,'河南',1,0),
 (18,'湖北',1,0),
 (19,'湖南',1,0),
 (20,'广东',1,0),
 (21,'广西',1,0),
 (22,'海南',1,0),
 (23,'四川',1,0),
 (24,'贵州',1,0),
 (25,'云南',1,0),
 (26,'西藏',1,0),
 (27,'陕西',1,0),
 (28,'甘肃',1,0),
 (29,'青海',1,0),
 (30,'宁夏',1,0),
 (31,'新疆',1,0),
 (32,'台湾',1,0),
 (33,'港澳',1,0),
 (34,'石家庄',2,5),
 (35,'唐山',2,5),
 (36,'秦皇岛',2,5),
 (37,'邯郸',2,5),
 (38,'邢台',2,5),
 (39,'保定',2,5),
 (40,'张家口',2,5),
 (41,'承德',2,5),
 (42,'沧州',2,5),
 (43,'廊坊',2,5),
 (44,'衡水',2,5),
 (45,'太原',2,6),
 (46,'大同',2,6),
 (47,'阳泉',2,6),
 (48,'长治',2,6),
 (49,'晋城',2,6),
 (50,'朔州',2,6),
 (51,'晋中',2,6),
 (52,'运城',2,6),
 (53,'忻州',2,6),
 (54,'临汾',2,6),
 (55,'吕梁',2,6),
 (56,'呼和浩特',2,7),
 (57,'包头',2,7),
 (58,'乌海',2,7),
 (59,'赤峰',2,7),
 (60,'通辽',2,7),
 (61,'鄂尔多斯',2,7),
 (62,'呼伦贝尔',2,7),
 (63,'巴彦淖尔',2,7),
 (64,'乌兰察布',2,7),
 (65,'兴安',2,7),
 (66,'锡林郭勒',2,7),
 (67,'阿拉善',2,7),
 (68,'沈阳',2,8),
 (69,'大连',2,8),
 (70,'鞍山',2,8),
 (71,'抚顺',2,8),
 (72,'本溪',2,8),
 (73,'丹东',2,8),
 (74,'锦州',2,8),
 (75,'营口',2,8),
 (76,'阜新',2,8),
 (77,'辽阳',2,8),
 (78,'盘锦',2,8),
 (79,'铁岭',2,8),
 (80,'朝阳',2,8),
 (81,'葫芦岛',2,8),
 (82,'长春',2,9),
 (83,'吉林',2,9),
 (84,'四平',2,9),
 (85,'辽源',2,9),
 (86,'通化',2,9),
 (87,'白山',2,9),
 (88,'松原',2,9),
 (89,'白城',2,9),
 (90,'延边',2,9),
 (91,'哈尔滨',2,10),
 (92,'齐齐哈尔',2,10),
 (93,'鸡西',2,10),
 (94,'鹤岗',2,10),
 (95,'双鸭山',2,10),
 (96,'大庆',2,10),
 (97,'伊春',2,10),
 (98,'佳木斯',2,10),
 (99,'七台河',2,10),
 (100,'牡丹江',2,10),
 (101,'黑河',2,10),
 (102,'绥化',2,10),
 (103,'大兴安岭',2,10),
 (104,'南京',2,11),
 (105,'无锡',2,11),
 (106,'徐州',2,11),
 (107,'常州',2,11),
 (108,'苏州',2,11),
 (109,'南通',2,11),
 (110,'连云港',2,11),
 (111,'淮安',2,11),
 (112,'盐城',2,11),
 (113,'扬州',2,11),
 (114,'镇江',2,11),
 (115,'泰州',2,11),
 (116,'宿迁',2,11),
 (117,'杭州',2,12),
 (118,'宁波',2,12),
 (119,'温州',2,12),
 (120,'嘉兴',2,12),
 (121,'湖州',2,12),
 (122,'绍兴',2,12),
 (123,'金华',2,12),
 (124,'衢州',2,12),
 (125,'舟山',2,12),
 (126,'台州',2,12),
 (127,'丽水',2,12),
 (128,'合肥',2,13),
 (129,'芜湖',2,13),
 (130,'蚌埠',2,13),
 (131,'淮南',2,13),
 (132,'马鞍山',2,13),
 (133,'淮北',2,13),
 (134,'铜陵',2,13),
 (135,'安庆',2,13),
 (136,'黄山',2,13),
 (137,'滁州',2,13),
 (138,'阜阳',2,13),
 (139,'宿州',2,13),
 (140,'巢湖',2,13),
 (141,'六安',2,13),
 (142,'亳州',2,13),
 (143,'池州',2,13),
 (144,'宣城',2,13),
 (145,'福州',2,14),
 (146,'厦门',2,14),
 (147,'莆田',2,14),
 (148,'三明',2,14),
 (149,'泉州',2,14),
 (150,'漳州',2,14),
 (151,'南平',2,14),
 (152,'龙岩',2,14),
 (153,'宁德',2,14),
 (154,'南昌',2,15),
 (155,'景德镇',2,15),
 (156,'萍乡',2,15),
 (157,'九江',2,15),
 (158,'新余',2,15),
 (159,'鹰潭',2,15),
 (160,'赣州',2,15),
 (161,'吉安',2,15),
 (162,'宜春',2,15),
 (163,'抚州',2,15),
 (164,'上饶',2,15),
 (165,'济南',2,16),
 (166,'青岛',2,16),
 (167,'淄博',2,16),
 (168,'枣庄',2,16),
 (169,'东营',2,16),
 (170,'烟台',2,16),
 (171,'潍坊',2,16),
 (172,'济宁',2,16),
 (173,'泰安',2,16),
 (174,'日照',2,16),
 (175,'莱芜',2,16),
 (176,'临沂',2,16),
 (177,'德州',2,16),
 (178,'聊城',2,16),
 (179,'滨州',2,16),
 (180,'菏泽',2,16),
 (181,'郑州',2,17),
 (182,'开封',2,17),
 (183,'洛阳',2,17),
 (184,'平顶山',2,17),
 (185,'焦作',2,17),
 (186,'鹤壁',2,17),
 (187,'新乡',2,17),
 (188,'安阳',2,17),
 (189,'濮阳',2,17),
 (190,'许昌',2,17),
 (191,'渭河',2,17),
 (192,'三门峡',2,17),
 (193,'南阳',2,17),
 (194,'商丘',2,17),
 (195,'信阳',2,17),
 (196,'周口',2,17),
 (197,'驻马店',2,17),
 (198,'武汉',2,18),
 (199,'黄石',2,18),
 (200,'襄樊',2,18),
 (201,'十堰',2,18),
 (202,'荆州',2,18),
 (203,'宜昌',2,18),
 (204,'荆门',2,18),
 (205,'鄂州',2,18),
 (206,'孝感',2,18),
 (207,'黄冈',2,18),
 (208,'咸宁',2,18),
 (209,'随州',2,18),
 (210,'恩施',2,18),
 (211,'长沙',2,19),
 (212,'株洲',2,19),
 (213,'湘潭',2,19),
 (214,'衡阳',2,19),
 (215,'邵阳',2,19),
 (216,'岳阳',2,19),
 (217,'常德',2,19),
 (218,'张家界',2,19),
 (219,'溢阳',2,19),
 (220,'郴州',2,19),
 (221,'永州',2,19),
 (222,'怀化',2,19),
 (223,'娄底',2,19),
 (224,'湘西',2,19),
 (225,'广州',2,20),
 (226,'深圳',2,20),
 (227,'珠海',2,20),
 (228,'汕头',2,20),
 (229,'韶关',2,20),
 (230,'佛山',2,20),
 (231,'江门',2,20),
 (232,'湛江',2,20),
 (233,'茂名',2,20),
 (234,'肇庆',2,20),
 (235,'惠州',2,20),
 (236,'梅州',2,20),
 (237,'汕尾',2,20),
 (238,'河源',2,20),
 (239,'阳江',2,20),
 (240,'清远',2,20),
 (241,'东莞',2,20),
 (242,'中山',2,20),
 (243,'潮州',2,20),
 (244,'揭阳',2,20),
 (245,'云浮',2,20),
 (246,'南宁',2,21),
 (247,'柳州',2,21),
 (248,'桂林',2,21),
 (249,'梧州',2,21),
 (250,'北海',2,21),
 (251,'防城港',2,21),
 (252,'钦州',2,21),
 (253,'贵港',2,21),
 (254,'玉林',2,21),
 (255,'百色',2,21),
 (256,'贺州',2,21),
 (257,'河池',2,21),
 (258,'来宾',2,21),
 (259,'崇左',2,21),
 (260,'白沙黎族自治县',2,22),
 (261,'西沙群岛',2,22),
 (262,'儋州',2,22),
 (263,'屯昌县',2,22),
 (264,'安定县',2,22),
 (265,'琼中黎族苗族自治县',2,22),
 (266,'昌江黎族自治县',2,22),
 (267,'东方',2,22),
 (268,'三亚',2,22),
 (269,'中沙群岛的岛礁及其海域',2,22),
 (270,'琼海',2,22),
 (271,'澄迈县',2,22),
 (272,'五指山',2,22),
 (273,'海口',2,22),
 (274,'文昌',2,22),
 (275,'陵水黎族自治县',2,22),
 (276,'保亭黎族苗族自治县',2,22),
 (277,'南沙群岛',2,22),
 (278,'乐东黎族自治县',2,22),
 (279,'临高县',2,22),
 (280,'万宁',2,22),
 (281,'成都',2,23),
 (282,'自贡',2,23),
 (283,'攀枝花',2,23),
 (284,'泸州',2,23),
 (285,'德阳',2,23),
 (286,'绵阳',2,23),
 (287,'广元',2,23),
 (288,'遂宁',2,23),
 (289,'内江',2,23),
 (290,'乐山',2,23),
 (291,'南充',2,23),
 (292,'宜宾',2,23),
 (293,'广安',2,23),
 (294,'达州',2,23),
 (295,'眉山',2,23),
 (296,'雅安',2,23),
 (297,'巴中',2,23),
 (298,'资阳',2,23),
 (299,'阿坝',2,23),
 (300,'甘孜',2,23),
 (301,'凉山',2,23),
 (302,'贵阳',2,24),
 (303,'六盘水',2,24),
 (304,'遵义',2,24),
 (305,'安顺',2,24),
 (306,'铜仁',2,24),
 (307,'毕节',2,24),
 (308,'黔西南',2,24),
 (309,'黔东南',2,24),
 (310,'黔南',2,24),
 (311,'昆明',2,25),
 (312,'曲靖',2,25),
 (313,'玉溪',2,25),
 (314,'保山',2,25),
 (315,'昭通',2,25),
 (316,'丽江',2,25),
 (317,'普洱',2,25),
 (318,'临沧',2,25),
 (319,'文山',2,25),
 (320,'红河',2,25),
 (321,'西双版纳',2,25),
 (322,'楚雄',2,25),
 (323,'大理',2,25),
 (324,'德宏',2,25),
 (325,'怒江',2,25),
 (326,'迪庆',2,25),
 (327,'拉萨',2,26),
 (328,'昌都',2,26),
 (329,'山南',2,26),
 (330,'日喀则',2,26),
 (331,'那曲',2,26),
 (332,'阿里',2,26),
 (333,'林芝',2,26),
 (334,'西安',2,27),
 (335,'铜川',2,27),
 (336,'宝鸡',2,27),
 (337,'咸阳',2,27),
 (338,'渭南',2,27),
 (339,'延安',2,27),
 (340,'汉中',2,27),
 (341,'榆林',2,27),
 (342,'安康',2,27),
 (343,'商洛',2,27),
 (344,'兰州',2,28),
 (345,'嘉峪关',2,28),
 (346,'金昌',2,28),
 (347,'白银',2,28),
 (348,'天水',2,28),
 (349,'武威',2,28),
 (350,'张掖',2,28),
 (351,'平凉',2,28),
 (352,'酒泉',2,28),
 (353,'庆阳',2,28),
 (354,'定西',2,28),
 (355,'陇南',2,28),
 (356,'临夏',2,28),
 (357,'甘南',2,28),
 (358,'西宁',2,29),
 (359,'海东',2,29),
 (360,'海北',2,29),
 (361,'黄南',2,29),
 (362,'海南',2,29),
 (363,'果洛',2,29),
 (364,'玉树',2,29),
 (365,'海西',2,29),
 (366,'银川',2,30),
 (367,'石嘴山',2,30),
 (368,'吴忠',2,30),
 (369,'固原',2,30),
 (370,'中卫',2,30),
 (371,'乌鲁木齐',2,31),
 (372,'克拉玛依',2,31),
 (373,'吐鲁番',2,31),
 (374,'哈密',2,31),
 (375,'和田',2,31),
 (376,'阿克苏',2,31),
 (377,'喀什',2,31),
 (378,'克孜勒苏柯尔克孜',2,31),
 (379,'巴音郭楞蒙古',2,31),
 (380,'昌吉',2,31),
 (381,'博尔塔拉蒙古',2,31),
 (382,'伊犁哈萨克',2,31),
 (383,'塔城',2,31),
 (384,'阿勒泰',2,31),
 (385,'台北',2,32),
 (386,'高雄',2,32),
 (387,'基隆',2,32),
 (388,'台中',2,32),
 (389,'台南',2,32),
 (390,'新竹',2,32),
 (391,'香港',2,33),
 (392,'澳门',2,33);
/*!40000 ALTER TABLE `region` ENABLE KEYS */;


--
-- Definition of table `report_param`
--

DROP TABLE IF EXISTS `report_param`;
CREATE TABLE `report_param` (
  `paramId` bigint(20) NOT NULL auto_increment,
  `reportId` bigint(20) NOT NULL COMMENT '所属报表',
  `paramName` varchar(64) NOT NULL COMMENT '参数名称',
  `paramKey` varchar(64) NOT NULL COMMENT '参数Key',
  `defaultVal` varchar(128) NOT NULL COMMENT '缺省值',
  `paramType` varchar(32) NOT NULL COMMENT '类型\r\n            字符类型--varchar\r\n            整型--int\r\n            精度型--decimal\r\n            日期型--date\r\n            日期时间型--datetime\r\n            ',
  `sn` int(11) NOT NULL COMMENT '系列号',
  PRIMARY KEY  (`paramId`),
  KEY `FK_RP_R_RPT` (`reportId`),
  CONSTRAINT `FK_RP_R_RPT` FOREIGN KEY (`reportId`) REFERENCES `report_template` (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表参数';

--
-- Dumping data for table `report_param`
--

/*!40000 ALTER TABLE `report_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_param` ENABLE KEYS */;


--
-- Definition of table `report_template`
--

DROP TABLE IF EXISTS `report_template`;
CREATE TABLE `report_template` (
  `reportId` bigint(20) NOT NULL auto_increment,
  `title` varchar(128) NOT NULL COMMENT '标题',
  `descp` varchar(500) NOT NULL COMMENT '描述',
  `reportLocation` varchar(128) NOT NULL COMMENT '报表模块的jasper文件的路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY  (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表模板\r\nreport_template';

--
-- Dumping data for table `report_template`
--

/*!40000 ALTER TABLE `report_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `report_template` ENABLE KEYS */;


--
-- Definition of table `resume`
--

DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume` (
  `resumeId` bigint(20) NOT NULL auto_increment,
  `fullname` varchar(64) NOT NULL,
  `age` int(11) default NULL,
  `birthday` datetime default NULL,
  `address` varchar(128) default NULL,
  `zip` varchar(32) default NULL,
  `sex` varchar(32) default NULL,
  `position` varchar(64) default NULL,
  `phone` varchar(64) default NULL,
  `mobile` varchar(64) default NULL,
  `email` varchar(128) default NULL,
  `hobby` varchar(256) default NULL,
  `religion` varchar(128) default NULL,
  `party` varchar(128) default NULL,
  `nationality` varchar(32) default NULL,
  `race` varchar(32) default NULL,
  `birthPlace` varchar(128) default NULL,
  `eduCollege` varchar(128) default NULL,
  `eduDegree` varchar(128) default NULL,
  `eduMajor` varchar(128) default NULL,
  `startWorkDate` datetime default NULL,
  `idNo` varchar(64) default NULL,
  `photo` varchar(128) default NULL,
  `status` varchar(64) default NULL COMMENT '状态\r\n            \r\n            通过\r\n            未通过\r\n            准备安排面试\r\n            面试通过\r\n            \r\n            ',
  `memo` varchar(1024) default NULL,
  `registor` varchar(64) default NULL,
  `regTime` datetime default NULL,
  `workCase` text,
  `trainCase` text,
  `projectCase` text,
  PRIMARY KEY  (`resumeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历管理';

--
-- Dumping data for table `resume`
--

/*!40000 ALTER TABLE `resume` DISABLE KEYS */;
/*!40000 ALTER TABLE `resume` ENABLE KEYS */;


--
-- Definition of table `resume_file`
--

DROP TABLE IF EXISTS `resume_file`;
CREATE TABLE `resume_file` (
  `fileId` bigint(20) NOT NULL,
  `resumeId` bigint(20) NOT NULL,
  PRIMARY KEY  (`fileId`,`resumeId`),
  KEY `FK_RMF_R_RM` (`resumeId`),
  CONSTRAINT `FK_RMF_R_FA` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `FK_RMF_R_RM` FOREIGN KEY (`resumeId`) REFERENCES `resume` (`resumeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `resume_file`
--

/*!40000 ALTER TABLE `resume_file` DISABLE KEYS */;
/*!40000 ALTER TABLE `resume_file` ENABLE KEYS */;


--
-- Definition of table `role_fun`
--

DROP TABLE IF EXISTS `role_fun`;
CREATE TABLE `role_fun` (
  `roleId` bigint(20) NOT NULL,
  `functionId` bigint(20) NOT NULL,
  PRIMARY KEY  (`roleId`,`functionId`),
  KEY `FK_RF_R_AFN` (`functionId`),
  CONSTRAINT `FK_RF_R_AFN` FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`),
  CONSTRAINT `FK_RF_R_AR` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `role_fun`
--

/*!40000 ALTER TABLE `role_fun` DISABLE KEYS */;
INSERT INTO `role_fun` (`roleId`,`functionId`) VALUES 
 (1,1),
 (1,2),
 (1,3),
 (1,4),
 (1,10),
 (1,11),
 (1,12),
 (1,13),
 (3,25),
 (3,26),
 (3,27),
 (3,28),
 (3,29),
 (4,30),
 (4,31),
 (4,32),
 (4,33),
 (1,34),
 (2,34),
 (4,34),
 (5,34),
 (4,35),
 (4,36),
 (4,37),
 (4,38),
 (4,39),
 (4,40),
 (4,41),
 (4,42),
 (4,43),
 (4,44),
 (4,45),
 (4,46),
 (4,47),
 (4,48),
 (1,49),
 (1,50),
 (1,51),
 (1,52),
 (1,53),
 (1,54),
 (1,55),
 (1,56),
 (1,57),
 (1,58),
 (1,59),
 (1,60),
 (1,61),
 (1,62),
 (1,63),
 (1,64),
 (1,65),
 (1,66),
 (1,67),
 (5,68),
 (5,69),
 (5,70),
 (5,71),
 (5,72),
 (5,73),
 (5,74),
 (5,75),
 (5,76),
 (5,77),
 (5,78),
 (5,79),
 (5,80),
 (5,81),
 (5,82),
 (5,83),
 (5,84),
 (5,85),
 (5,86),
 (5,87),
 (5,88),
 (5,89),
 (5,90),
 (5,91),
 (5,92),
 (5,93),
 (5,94),
 (5,95),
 (2,96),
 (2,97),
 (2,98),
 (2,99),
 (2,100),
 (2,101),
 (2,102),
 (2,103),
 (2,104),
 (2,105),
 (2,106),
 (2,107),
 (2,108),
 (2,109),
 (2,110),
 (2,111),
 (2,112),
 (2,113),
 (2,114),
 (2,115),
 (2,116),
 (2,117),
 (2,118),
 (2,119),
 (2,120),
 (2,121),
 (2,122),
 (2,123),
 (2,124),
 (2,125),
 (2,126),
 (2,127),
 (2,128),
 (2,129),
 (2,130),
 (2,131),
 (2,132),
 (2,133),
 (2,134),
 (2,135),
 (2,136),
 (2,137),
 (2,138),
 (2,139),
 (2,140),
 (2,141),
 (2,142),
 (2,143),
 (2,144),
 (2,145),
 (2,146),
 (2,147),
 (2,148);
/*!40000 ALTER TABLE `role_fun` ENABLE KEYS */;


--
-- Definition of table `salary_item`
--

DROP TABLE IF EXISTS `salary_item`;
CREATE TABLE `salary_item` (
  `salaryItemId` bigint(20) NOT NULL auto_increment,
  `itemName` varchar(128) NOT NULL COMMENT '项目名称',
  `defaultVal` decimal(18,2) NOT NULL COMMENT '缺省值',
  PRIMARY KEY  (`salaryItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='薪酬组成项目';

--
-- Dumping data for table `salary_item`
--

/*!40000 ALTER TABLE `salary_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `salary_item` ENABLE KEYS */;


--
-- Definition of table `salary_payoff`
--

DROP TABLE IF EXISTS `salary_payoff`;
CREATE TABLE `salary_payoff` (
  `recordId` bigint(20) NOT NULL auto_increment,
  `fullname` varchar(64) NOT NULL COMMENT '员工姓名',
  `userId` bigint(20) NOT NULL COMMENT '所属员工',
  `profileNo` varchar(128) default NULL COMMENT '档案编号',
  `standardId` bigint(20) NOT NULL,
  `idNo` varchar(128) default NULL COMMENT '身份证号',
  `standAmount` decimal(18,2) NOT NULL default '0.00' COMMENT '薪标金额',
  `encourageAmount` decimal(18,2) NOT NULL default '0.00' COMMENT '奖励金额',
  `deductAmount` decimal(18,2) NOT NULL default '0.00' COMMENT '扣除工资',
  `achieveAmount` decimal(18,2) default '0.00' COMMENT '效绩工资',
  `encourageDesc` varchar(512) default NULL COMMENT '奖励描述',
  `deductDesc` varchar(512) default NULL COMMENT '扣除描述',
  `memo` varchar(512) default NULL COMMENT '备注描述',
  `acutalAmount` decimal(18,2) default NULL COMMENT '实发金额',
  `regTime` datetime NOT NULL COMMENT '登记时间',
  `register` varchar(64) default NULL COMMENT '登记人',
  `checkOpinion` varchar(1024) default NULL,
  `checkName` varchar(64) default NULL COMMENT '审批人',
  `checkTime` datetime default NULL COMMENT '审批时间',
  `checkStatus` smallint(6) default NULL COMMENT '审批状态\r\n            0=草稿\r\n            1=通过审批\r\n            2=未通过审批\r\n            ',
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  PRIMARY KEY  (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `salary_payoff`
--

/*!40000 ALTER TABLE `salary_payoff` DISABLE KEYS */;
/*!40000 ALTER TABLE `salary_payoff` ENABLE KEYS */;


--
-- Definition of table `short_message`
--

DROP TABLE IF EXISTS `short_message`;
CREATE TABLE `short_message` (
  `messageId` bigint(20) NOT NULL auto_increment,
  `senderId` bigint(20) default NULL COMMENT '主键',
  `content` varchar(256) NOT NULL,
  `sender` varchar(64) NOT NULL,
  `msgType` smallint(6) NOT NULL COMMENT '1=个人信息\r\n            2=日程安排\r\n            3=计划任务\r\n            ',
  `sendTime` datetime NOT NULL,
  PRIMARY KEY  (`messageId`),
  KEY `FK_SM_R_AU` (`senderId`),
  CONSTRAINT `FK_SM_R_AU` FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信消息';

--
-- Dumping data for table `short_message`
--

/*!40000 ALTER TABLE `short_message` DISABLE KEYS */;
INSERT INTO `short_message` (`messageId`,`senderId`,`content`,`sender`,`msgType`,`sendTime`) VALUES 
 (1,-1,'cwx','系统',4,'2010-05-15 11:37:15'),
 (2,-1,'tesw','系统',4,'2010-05-15 11:38:37'),
 (3,-1,'admin','系统',4,'2010-05-15 12:06:56'),
 (4,-1,'测试及开发','系统',4,'2010-05-15 12:11:34');
/*!40000 ALTER TABLE `short_message` ENABLE KEYS */;


--
-- Definition of table `stand_salary`
--

DROP TABLE IF EXISTS `stand_salary`;
CREATE TABLE `stand_salary` (
  `standardId` bigint(20) NOT NULL auto_increment,
  `standardNo` varchar(128) NOT NULL COMMENT '薪酬标准编号\r\n            惟一',
  `standardName` varchar(128) NOT NULL COMMENT '标准名称',
  `totalMoney` decimal(18,2) NOT NULL default '0.00',
  `framer` varchar(64) default NULL,
  `setdownTime` datetime default NULL,
  `checkName` varchar(64) default NULL,
  `checkTime` datetime default NULL,
  `modifyName` varchar(64) default NULL,
  `modifyTime` datetime default NULL,
  `checkOpinion` varchar(512) default NULL,
  `status` smallint(6) NOT NULL COMMENT '0=草稿\r\n            1=审批\r\n            2=未通过审批',
  `memo` varchar(512) default NULL,
  PRIMARY KEY  (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stand_salary`
--

/*!40000 ALTER TABLE `stand_salary` DISABLE KEYS */;
/*!40000 ALTER TABLE `stand_salary` ENABLE KEYS */;


--
-- Definition of table `stand_salary_item`
--

DROP TABLE IF EXISTS `stand_salary_item`;
CREATE TABLE `stand_salary_item` (
  `itemId` bigint(20) NOT NULL auto_increment,
  `standardId` bigint(20) NOT NULL,
  `itemName` varchar(64) NOT NULL,
  `amount` decimal(18,2) NOT NULL,
  `salaryItemId` bigint(20) default NULL COMMENT '所属工资组成ID\r\n            外键，但不需要在数据库层建立外键',
  PRIMARY KEY  (`itemId`),
  KEY `FK_SSI_R_SSY` (`standardId`),
  CONSTRAINT `FK_SSI_R_SSY` FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='薪酬标准明细';

--
-- Dumping data for table `stand_salary_item`
--

/*!40000 ALTER TABLE `stand_salary_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `stand_salary_item` ENABLE KEYS */;


--
-- Definition of table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `configId` bigint(20) NOT NULL auto_increment,
  `configKey` varchar(64) NOT NULL COMMENT 'Key',
  `configName` varchar(64) NOT NULL COMMENT '配置名称',
  `configDesc` varchar(256) default NULL COMMENT '配置描述',
  `typeName` varchar(32) NOT NULL COMMENT '所属分类名称',
  `dataType` smallint(6) NOT NULL COMMENT '数据类型\r\n            1=varchar\r\n            2=intger\r\n            3=decimal\r\n            4=datetime\r\n            5=time\r\n            ',
  `dataValue` varchar(64) default NULL,
  PRIMARY KEY  (`configId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置\r\n\r\n用于系统的全局配置\r\n如邮件服务';

--
-- Dumping data for table `sys_config`
--

/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` (`configId`,`configKey`,`configName`,`configDesc`,`typeName`,`dataType`,`dataValue`) VALUES 
 (1,'host','主机Host','主机IP','系统邮件配置',1,'192.168.1.11'),
 (2,'username','用户名','邮件发送的邮箱用户名','系统邮件配置',1,'lyy'),
 (3,'password','密码','邮件发送的邮箱密码','系统邮件配置',1,'111111'),
 (4,'from','来自','发送人的邮箱或用户名','系统邮件配置',1,'lyy'),
 (5,'goodsStockUser','用户帐号','当库存产生警报时，接收消息的人员','行政管理配置',1,'admin'),
 (6,'codeConfig','验证码','登录时是否需要验证码','验证码配置',2,'1');
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;


--
-- Definition of table `system_log`
--

DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `logId` bigint(20) NOT NULL auto_increment,
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `createtime` datetime NOT NULL COMMENT '执行时间',
  `exeOperation` varchar(512) NOT NULL COMMENT '执行操作',
  PRIMARY KEY  (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `system_log`
--

/*!40000 ALTER TABLE `system_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `system_log` ENABLE KEYS */;


--
-- Definition of table `task_sign`
--

DROP TABLE IF EXISTS `task_sign`;
CREATE TABLE `task_sign` (
  `signId` bigint(20) NOT NULL,
  `assignId` bigint(20) NOT NULL COMMENT '所属流程设置',
  `voteCounts` int(11) default NULL COMMENT '绝对票数',
  `votePercents` int(11) default NULL COMMENT '百分比票数',
  `decideType` smallint(6) NOT NULL COMMENT '1=pass 通过\r\n            2=reject 拒绝',
  PRIMARY KEY  (`signId`),
  KEY `FK_TS_R_PUA` (`assignId`),
  CONSTRAINT `FK_TS_R_PUA` FOREIGN KEY (`assignId`) REFERENCES `pro_user_assign` (`assignId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `task_sign`
--

/*!40000 ALTER TABLE `task_sign` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_sign` ENABLE KEYS */;


--
-- Definition of table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userId` bigint(20) NOT NULL COMMENT '主键',
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY  (`userId`,`roleId`),
  KEY `FK_UR_R_AR` (`roleId`),
  CONSTRAINT `FK_UR_R_AR` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`),
  CONSTRAINT `FK_UR_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user_role`
--

/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` (`userId`,`roleId`) VALUES 
 (1,-1),
 (2,-1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;


--
-- Definition of table `user_sub`
--

DROP TABLE IF EXISTS `user_sub`;
CREATE TABLE `user_sub` (
  `subId` bigint(20) NOT NULL auto_increment,
  `subUserId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY  (`subId`),
  KEY `FK_USB_R_AU` (`subUserId`),
  KEY `FK_US_R_AU` (`userId`),
  CONSTRAINT `FK_USB_R_AU` FOREIGN KEY (`subUserId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_US_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='subordinate';

--
-- Dumping data for table `user_sub`
--

/*!40000 ALTER TABLE `user_sub` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_sub` ENABLE KEYS */;


--
-- Definition of table `work_plan`
--

DROP TABLE IF EXISTS `work_plan`;
CREATE TABLE `work_plan` (
  `planId` bigint(20) NOT NULL auto_increment,
  `planName` varchar(128) NOT NULL COMMENT '计划名称',
  `planContent` text COMMENT '计划内容',
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `typeId` bigint(20) NOT NULL COMMENT '计划类型',
  `userId` bigint(20) default NULL COMMENT '员工ID',
  `issueScope` varchar(2000) default NULL COMMENT '发布范围\r\n            0则代表全部部门\r\n            存放所有的参与部门ID\r\n            ',
  `participants` varchar(2000) default NULL COMMENT '参与人\r\n            0则代表全部参与\r\n            参与人,即员工ID列表',
  `principal` varchar(256) NOT NULL COMMENT '负责人',
  `note` varchar(500) default NULL COMMENT '备注',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用',
  `isPersonal` smallint(6) NOT NULL COMMENT '是否为个人计划\r\n            1=则为个人工作计划，这时发布范围，参与人均为空，负责人为当前用户\r\n            0=则代表为其他任务',
  `icon` varchar(128) default NULL COMMENT '图标',
  PRIMARY KEY  (`planId`),
  KEY `FK_WP_R_AU` (`userId`),
  KEY `FK_WP_R_PT` (`typeId`),
  CONSTRAINT `FK_WP_R_AU` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `FK_WP_R_PT` FOREIGN KEY (`typeId`) REFERENCES `plan_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作计划';

--
-- Dumping data for table `work_plan`
--

/*!40000 ALTER TABLE `work_plan` DISABLE KEYS */;
/*!40000 ALTER TABLE `work_plan` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
