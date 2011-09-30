/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : jxkh

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2011-09-29 11:03:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `appointment`
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
  `appointId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `subject` varchar(128) NOT NULL COMMENT '主题',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime NOT NULL COMMENT '结束时间',
  `content` text NOT NULL COMMENT '约会内容',
  `notes` varchar(1000) DEFAULT NULL COMMENT '备注',
  `location` varchar(150) NOT NULL COMMENT '地点',
  `inviteEmails` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`appointId`),
  KEY `FK_AP_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `appointment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='约会管理';

-- ----------------------------
-- Records of appointment
-- ----------------------------

-- ----------------------------
-- Table structure for `app_function`
-- ----------------------------
DROP TABLE IF EXISTS `app_function`;
CREATE TABLE `app_function` (
  `functionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `funKey` varchar(64) NOT NULL COMMENT '权限Key',
  `funName` varchar(128) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (`functionId`),
  UNIQUE KEY `AK_UQ_RSKEY` (`funKey`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app_function
-- ----------------------------
INSERT INTO app_function VALUES ('1', '_AppUserQuery', '查看员工');
INSERT INTO app_function VALUES ('2', '_AppUserAdd', '添加员工');
INSERT INTO app_function VALUES ('3', '_AppUserEdit', '编辑员工');
INSERT INTO app_function VALUES ('4', '_AppUserDel', '删除员工');
INSERT INTO app_function VALUES ('5', '_AppRoleList', '查看角色');
INSERT INTO app_function VALUES ('6', '_AppRoleAdd', '添加角色');
INSERT INTO app_function VALUES ('7', '_AppRoleEdit', '编辑角色');
INSERT INTO app_function VALUES ('8', '_AppRoleDel', '删除角色');
INSERT INTO app_function VALUES ('9', '_AppRoleGrant', '授权角色');
INSERT INTO app_function VALUES ('10', '_DepartmentQuery', '查看部门');
INSERT INTO app_function VALUES ('11', '_DepartmentAdd', '新建部门');
INSERT INTO app_function VALUES ('12', '_DepartmentEdit', '修改部门');
INSERT INTO app_function VALUES ('13', '_DepartmentDel', '删除部门');
INSERT INTO app_function VALUES ('14', '_FileAttachQuery', '查看附件');
INSERT INTO app_function VALUES ('15', '_FileAttachEdit', '编辑附件');
INSERT INTO app_function VALUES ('16', '_FileAttachDel', '删除附件');
INSERT INTO app_function VALUES ('17', '_CompanyEdit', '公司信息修改');
INSERT INTO app_function VALUES ('18', '_FlowQuery', '查看流程');
INSERT INTO app_function VALUES ('19', '_ProTypeManage', '流程类型');
INSERT INTO app_function VALUES ('20', '_FlowAdd', '发布流程');
INSERT INTO app_function VALUES ('21', '_FlowEdit', '编辑流程');
INSERT INTO app_function VALUES ('22', '_FlowDel', '删除流程');
INSERT INTO app_function VALUES ('23', '_FlowCheck', '查看');
INSERT INTO app_function VALUES ('24', '_FlowSetting', '人员设置');
INSERT INTO app_function VALUES ('25', '_DocFolderSharedManage', '公共文件夹管理');
INSERT INTO app_function VALUES ('26', '_DocPrivilegeQuery', '查看权限');
INSERT INTO app_function VALUES ('27', '_DocPrivilegeAdd', '添加权限');
INSERT INTO app_function VALUES ('28', '_DocPrivilegeEdit', '编辑权限');
INSERT INTO app_function VALUES ('29', '_DocPrivilegeDel', '删除权限');
INSERT INTO app_function VALUES ('30', '_PlanTypeQuery', '查看类型');
INSERT INTO app_function VALUES ('31', '_PlanTypeAdd', '添加类型');
INSERT INTO app_function VALUES ('32', '_PlanTypeEdit', '编辑类型');
INSERT INTO app_function VALUES ('33', '_PlanTypeDel', '删除类型');
INSERT INTO app_function VALUES ('34', '_NewDepPlan', '新建部门任务计划');
INSERT INTO app_function VALUES ('35', '_NewsQuery', '查看新闻');
INSERT INTO app_function VALUES ('36', '_NewsAdd', '添加新闻');
INSERT INTO app_function VALUES ('37', '_NewsEdit', '编辑新闻');
INSERT INTO app_function VALUES ('38', '_NewsDel', '删除新闻');
INSERT INTO app_function VALUES ('39', '_NewsCommentQuery', '查看评论');
INSERT INTO app_function VALUES ('40', '_NewsCommentDel', '删除评论');
INSERT INTO app_function VALUES ('41', '_NewsTypeQuery', '查看新闻类型');
INSERT INTO app_function VALUES ('42', '_NewsTypeAdd', '添加新闻类型');
INSERT INTO app_function VALUES ('43', '_NewsTypeEdit', '修改新闻类型');
INSERT INTO app_function VALUES ('44', '_NewsTypeDel', '删除新闻类型');
INSERT INTO app_function VALUES ('45', '_NoticeQuery', '查看公告');
INSERT INTO app_function VALUES ('46', '_NoticeAdd', '添加公告');
INSERT INTO app_function VALUES ('47', '_NoticeEdit', '编辑公告');
INSERT INTO app_function VALUES ('48', '_NoticeDel', '删除公告');
INSERT INTO app_function VALUES ('49', '_HolidayRecordQuery', '查看假期设置');
INSERT INTO app_function VALUES ('50', '_HolidayRecordAdd', '添加假期设置');
INSERT INTO app_function VALUES ('51', '_HolidayRecordEdit', '修改假期设置');
INSERT INTO app_function VALUES ('52', '_HolidayRecordDel', '删除假期设置');
INSERT INTO app_function VALUES ('53', '_DutySectonQuery', '查看班次定义');
INSERT INTO app_function VALUES ('54', '_DutySectonAdd', '添加班次定义');
INSERT INTO app_function VALUES ('55', '_DutySectonEdit', '修改班次定义');
INSERT INTO app_function VALUES ('56', '_DutySectonDel', '删除班次定义');
INSERT INTO app_function VALUES ('57', '_DutySystemQuery', '查看班制定义');
INSERT INTO app_function VALUES ('58', '_DutySystemAdd', '添加班制定义');
INSERT INTO app_function VALUES ('59', '_DutySystemEdit', '修改班制定义');
INSERT INTO app_function VALUES ('60', '_DutySystemDel', '删除班制定义');
INSERT INTO app_function VALUES ('61', '_DutyQuery', '查看排班');
INSERT INTO app_function VALUES ('62', '_DutyAdd', '添加排班');
INSERT INTO app_function VALUES ('63', '_DutyEdit', '修改排班');
INSERT INTO app_function VALUES ('64', '_DutyDel', '删除排班');
INSERT INTO app_function VALUES ('65', '_DutyRegisterQuery', '查看考勤信息');
INSERT INTO app_function VALUES ('66', '_DutyRegisterAdd', '补签');
INSERT INTO app_function VALUES ('67', '_DutyRegisterDel', '删除考勤信息');
INSERT INTO app_function VALUES ('68', '_CustomerQuery', '查看客户信息');
INSERT INTO app_function VALUES ('69', '_CustomerAdd', '添加客户信息');
INSERT INTO app_function VALUES ('70', '_CustomerEdit', '编辑客户信息');
INSERT INTO app_function VALUES ('71', '_CustomerDel', '删除客户信息');
INSERT INTO app_function VALUES ('72', '_CusLinkmanQuery', '查看联系人信息');
INSERT INTO app_function VALUES ('73', '_CusLinkmanAdd', '添加联系人');
INSERT INTO app_function VALUES ('74', '_CusLinkmanEdit', '编辑联系人');
INSERT INTO app_function VALUES ('75', '_CusLinkmanDel', '删除联系人');
INSERT INTO app_function VALUES ('76', '_CusConnectionQuery', '查看交往信息');
INSERT INTO app_function VALUES ('77', '_CusConnectionAdd', '添加交往信息');
INSERT INTO app_function VALUES ('78', '_CusConnectionEdit', '编辑交往信息');
INSERT INTO app_function VALUES ('79', '_CusConnectionDel', '删除交往信息');
INSERT INTO app_function VALUES ('80', '_ProjectQuery', '查看项目');
INSERT INTO app_function VALUES ('81', '_ProjectAdd', '添加项目');
INSERT INTO app_function VALUES ('82', '_ProjectEdit', '编辑项目');
INSERT INTO app_function VALUES ('83', '_ProjectDel', '删除项目');
INSERT INTO app_function VALUES ('84', '_ContractQuery', '查看合同');
INSERT INTO app_function VALUES ('85', '_ContractAdd', '添加合同');
INSERT INTO app_function VALUES ('86', '_ContractEdit', '编辑合同');
INSERT INTO app_function VALUES ('87', '_ContractDel', '删除合同');
INSERT INTO app_function VALUES ('88', '_ProductQuery', '查看产品');
INSERT INTO app_function VALUES ('89', '_ProductAdd', '添加产品');
INSERT INTO app_function VALUES ('90', '_ProductEdit', '编辑产品');
INSERT INTO app_function VALUES ('91', '_ProductDel', '删除产品');
INSERT INTO app_function VALUES ('92', '_ProviderQuery', '查看供应商');
INSERT INTO app_function VALUES ('93', '_ProviderAdd', '添加供应商');
INSERT INTO app_function VALUES ('94', '_ProviderEdit', '编辑供应商');
INSERT INTO app_function VALUES ('95', '_ProviderDel', '删除供应商');
INSERT INTO app_function VALUES ('96', '_OfficeGoodsQuery', '查看办公用品');
INSERT INTO app_function VALUES ('97', '_OfficeGoodsTypeManage', '用品类型管理');
INSERT INTO app_function VALUES ('98', '_OfficeGoodsAdd', '添加用品');
INSERT INTO app_function VALUES ('99', '_OfficeGoodsEdit', '编辑用品');
INSERT INTO app_function VALUES ('100', '_OfficeGoodsDel', '删除用品');
INSERT INTO app_function VALUES ('101', '_InStockQuery', '查看入库记录');
INSERT INTO app_function VALUES ('102', '_InStockAdd', '添加入库记录');
INSERT INTO app_function VALUES ('103', '_InStockEdit', '编辑入库记录');
INSERT INTO app_function VALUES ('104', '_InStockDel', '删除入库记录');
INSERT INTO app_function VALUES ('105', '_GoodsApplyQuery', '查看申请记录');
INSERT INTO app_function VALUES ('106', '_GoodsApplyAdd', '添加申请记录');
INSERT INTO app_function VALUES ('107', '_GoodsApplyEdit', '编辑申请记录');
INSERT INTO app_function VALUES ('108', '_GoodsApplyDel', '删除申请记录');
INSERT INTO app_function VALUES ('109', '_CarQuery', '查看车辆');
INSERT INTO app_function VALUES ('110', '_CarAdd', '添加车辆');
INSERT INTO app_function VALUES ('111', '_CarEdit', '编辑车辆');
INSERT INTO app_function VALUES ('112', '_CarDel', '删除车辆');
INSERT INTO app_function VALUES ('113', '_CarRepairQuery', '查看维修记录');
INSERT INTO app_function VALUES ('114', '_CarRepairAdd', '添加维修记录');
INSERT INTO app_function VALUES ('115', '_CarRepairEdit', '编辑维修记录');
INSERT INTO app_function VALUES ('116', '_CarRepairDel', '删除维修记录');
INSERT INTO app_function VALUES ('117', '_CarApplyQuery', '查看车辆申请记录');
INSERT INTO app_function VALUES ('118', '_CarApplyAdd', '添加申请记录');
INSERT INTO app_function VALUES ('119', '_CarApplyEdit', '编辑申请记录');
INSERT INTO app_function VALUES ('120', '_CarApplyDel', '删除申请记录');
INSERT INTO app_function VALUES ('121', '_DepreTypeQuery', '查看折算类型');
INSERT INTO app_function VALUES ('122', '_DepreTypeAdd', '添加类型');
INSERT INTO app_function VALUES ('123', '_DepreTypeEdit', '编辑类型');
INSERT INTO app_function VALUES ('124', '_DepreTypeDel', '删除类型');
INSERT INTO app_function VALUES ('125', '_FixedAssetsQuery', '查看固定资产');
INSERT INTO app_function VALUES ('126', '_AssetsTypeManage', '资产类型管理');
INSERT INTO app_function VALUES ('127', '_FixedAssetsAdd', '添加资产');
INSERT INTO app_function VALUES ('128', '_FixedAssetsEdit', '编辑资产');
INSERT INTO app_function VALUES ('129', '_FixedAssetsDel', '删除资产');
INSERT INTO app_function VALUES ('130', '_Depreciate', '进行折算');
INSERT INTO app_function VALUES ('131', '_DepreRecordQuery', '查看折算记录');
INSERT INTO app_function VALUES ('132', '_BookTypeQuery', '查看类型');
INSERT INTO app_function VALUES ('133', '_BookTypeAdd', '添加图书类别');
INSERT INTO app_function VALUES ('134', '_BookTypeEdit', '编辑图书类别');
INSERT INTO app_function VALUES ('135', '_BookTypeDel', '删除图书类别');
INSERT INTO app_function VALUES ('136', '_BookQuery', '查看图书');
INSERT INTO app_function VALUES ('137', '_BookAdd', '添加图书');
INSERT INTO app_function VALUES ('138', '_BookEdit', '编辑图书');
INSERT INTO app_function VALUES ('139', '_BookDel', '删除图书');
INSERT INTO app_function VALUES ('140', '_BookBorrowQuery', '查看记录');
INSERT INTO app_function VALUES ('141', '_BookBorrowAdd', '添加借阅记录');
INSERT INTO app_function VALUES ('142', '_BookBorrowEdit', '编辑借阅记录');
INSERT INTO app_function VALUES ('143', '_BookReturn', '归还');
INSERT INTO app_function VALUES ('144', '_BookBorrowDel', '删除借阅记录');
INSERT INTO app_function VALUES ('145', '_BookReturnQuery', '查看记录');
INSERT INTO app_function VALUES ('146', '_BookReturnAdd', '添加归还记录');
INSERT INTO app_function VALUES ('147', '_BookReturnEdit', '编辑归还记录');
INSERT INTO app_function VALUES ('148', '_BookReturnDel', '删除归还记录');

-- ----------------------------
-- Table structure for `app_role`
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(128) NOT NULL COMMENT '角色名称',
  `roleDesc` varchar(128) DEFAULT NULL COMMENT '角色描述',
  `status` smallint(6) NOT NULL COMMENT '状态',
  `rights` text,
  `isDefaultIn` smallint(6) NOT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of app_role
-- ----------------------------
INSERT INTO app_role VALUES ('-1', '超级管理员', '超级管理员,具有所有权限', '1', '__ALL', '0');
INSERT INTO app_role VALUES ('1', '[人事经理]', '管理人事的经理', '1', 'Hrm,HrmManage,JobView,_JobQuery,_JobEdit,_JobDel,_JobRec,EmpProfileForm,_EmpProfileReg,EmpProfileView,_EmpProfileQuery,_EmpProfileAdd,_EmpProfileEdit,_EmpProfileDel,_EmpProfileCheck,_EmpProfileRec,Budget,BudgetView,_BudgetQuery,_BudgetAdd,_BudgetEdit,_BudgetDel,BudgetAlarmView,_BudgetAlarmQuery,RealExecutionView,_RealExecutionQuery,_RealExecutionAdd,_RealExecutionDel,KpiManage,HrPaPerformanceindexView,_PaDel,_PaEdit,HrPaAssessmentcriteriaView,_AcDel,_AcEdit', '1');
INSERT INTO app_role VALUES ('2', '[行政经理]', '管理行政', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,Administrator,GoodManeger,OfficeGoodsManageView,_OfficeGoodsQuery,_OfficeGoodsTypeManage,_OfficeGoodsAdd,_OfficeGoodsEdit,_OfficeGoodsDel,InStockView,_InStockQuery,_InStockAdd,_InStockEdit,_InStockDel,GoodsApplyView,_GoodsApplyQuery,_GoodsApplyAdd,_GoodsApplyEdit,_GoodsApplyDel,CarManeger,CarView,_CarQuery,_CarAdd,_CarEdit,_CarDel,CartRepairView,_CarRepairQuery,_CarRepairAdd,_CarRepairEdit,_CarRepairDel,CarApplyView,_CarApplyQuery,_CarApplyAdd,_CarApplyEdit,_CarApplyDel,FixedAssetsManage,DepreTypeView,_DepreTypeQuery,_DepreTypeAdd,_DepreTypeEdit,_DepreTypeDel,FixedAssetsManageView,_FixedAssetsQuery,_AssetsTypeManage,_FixedAssetsAdd,_FixedAssetsEdit,_FixedAssetsDel,_Depreciate,DepreRecordView,_DepreRecordQuery,BookManager,BookTypeView,_BookTypeQuery,_BookTypeAdd,_BookTypeEdit,_BookTypeDel,BookManageView,_BookQuery,_BookAdd,_BookEdit,_BookDel,BookBorrowView,_BookBorrowQuery,_BookBorrowAdd,_BookBorrowEdit,_BookReturn,_BookBorrowDel,BookReturnView,_BookReturnQuery,_BookReturnAdd,_BookReturnEdit,_BookReturnDel', '1');
INSERT INTO app_role VALUES ('3', '[文档管理员]', '管理文档', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,DocFolderSharedView,_DocFolderSharedManage,_DocPrivilegeQuery,_DocPrivilegeAdd,_DocPrivilegeEdit,_DocPrivilegeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '1');
INSERT INTO app_role VALUES ('4', '[信息管理员]', '管理新闻公告等信息', '1', 'SystemSetting,Task,PlanTypeView,_PlanTypeQuery,_PlanTypeAdd,_PlanTypeEdit,_PlanTypeDel,NewWorkPlanForm,_NewDepPlan,Info,NewsView,_NewsQuery,_NewsAdd,_NewsEdit,_NewsDel,NewsCommentView,_NewsCommentQuery,_NewsCommentDel,NewsTypeView,_NewsTypeQuery,_NewsTypeAdd,_NewsTypeEdit,_NewsTypeDel,NoticeView,_NoticeQuery,_NoticeAdd,_NoticeEdit,_NoticeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '1');
INSERT INTO app_role VALUES ('5', '[客户经理]', '管理客户信息', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,customer,CustomerView,_CustomerQuery,_CustomerAdd,_CustomerEdit,_CustomerDel,CusLinkmanView,_CusLinkmanQuery,_CusLinkmanAdd,_CusLinkmanEdit,_CusLinkmanDel,CusConnectionView,_CusConnectionQuery,_CusConnectionAdd,_CusConnectionEdit,_CusConnectionDel,ProjectView,_ProjectQuery,_ProjectAdd,_ProjectEdit,_ProjectDel,ContractView,_ContractQuery,_ContractAdd,_ContractEdit,_ContractDel,ProductView,_ProductQuery,_ProductAdd,_ProductEdit,_ProductDel,ProviderView,_ProviderQuery,_ProviderAdd,_ProviderEdit,_ProviderDel', '1');
INSERT INTO app_role VALUES ('6', '一般用户', '一般用户', '1', 'MyDesktop,ProfileForm,PersonalSalaryView,SystemSetting,SysConfigView,_SysConfigEdit,AppUserView,_AppUserQuery,_AppUserAdd,_AppUserEdit,_AppUserDel,AppRoleView,_AppRoleList,_AppRoleAdd,_AppRoleEdit,_AppRoleDel,_AppRoleGrant,DepartmentView,_DepartmentQuery,_DepartmentAdd,_DepartmentEdit,_DepartmentDel,FileAttachView,_FileAttachQuery,_FileAttachEdit,_FileAttachDel,CompanyView,_CompanyEdit,FlowManagerView,_FlowQuery,_ProTypeManage,_FlowAdd,_FlowEdit,_FlowDel,_FlowCheck,_FlowSetting,Communication,InnerMail,PersonalMailBoxView,MessageManageView,Task,CalendarPlanView,Personal,PersonalDuty,SignInOffView,Flow,MyTaskView,Shop,ShopPaManage,SpPaPerformanceindexManage,DraftSpPaPerformanceindexView,PublishSpPaPerformanceindexView,_PaAdd,_PaDel,_PaEdit,SpPaKpipbcManage,DraftSpPaKpipbcView,PublishSpPaKpipbcView,_PbcAdd,_PbcDel,_PbcEdit,SpGradePbcScore,SpPaKpiPBC2UserView,SpGradeAuthorizePbcView,SpGradeTotalScoreView,SpResultTotalScoreView,SpReport,SpReportView', '0');
INSERT INTO app_role VALUES ('20', '门店打分', '用来为门店打分测试角色', '1', 'Shop,ShopPaManage,SpPaPerformanceindexManage,DraftSpPaPerformanceindexView,PublishSpPaPerformanceindexView,_PaAdd,_PaDel,_PaEdit,SpPaKpipbcManage,DraftSpPaKpipbcView,PublishSpPaKpipbcView,_PbcAdd,_PbcDel,_PbcEdit,SpGradePbcScore,SpPaKpiPBC2UserView,SpGradeAuthorizePbcView,SpGradeTotalScoreView,SpResultTotalScoreView,SpReport,SpReportView', '0');

-- ----------------------------
-- Table structure for `app_tips`
-- ----------------------------
DROP TABLE IF EXISTS `app_tips`;
CREATE TABLE `app_tips` (
  `tipsId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `tipsName` varchar(128) DEFAULT NULL,
  `content` varchar(2048) DEFAULT NULL,
  `disheight` int(11) DEFAULT NULL,
  `diswidth` int(11) DEFAULT NULL,
  `disleft` int(11) DEFAULT NULL,
  `distop` int(11) DEFAULT NULL,
  `dislevel` int(11) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  PRIMARY KEY (`tipsId`),
  KEY `FK_AT_R_AP` (`userId`) USING BTREE,
  CONSTRAINT `app_tips_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户便签';

-- ----------------------------
-- Records of app_tips
-- ----------------------------

-- ----------------------------
-- Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `title` smallint(6) NOT NULL COMMENT '1=先生\r\n            0=女士\r\n            小姐',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `email` varchar(128) NOT NULL COMMENT '邮件',
  `depId` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `fax` varchar(32) DEFAULT NULL COMMENT '传真',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `zip` varchar(32) DEFAULT NULL COMMENT '邮编',
  `photo` varchar(128) DEFAULT NULL COMMENT '相片',
  `accessionTime` datetime NOT NULL COMMENT '入职时间',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用\r\n            2=离职\r\n            ',
  `education` varchar(64) DEFAULT NULL,
  `fullname` varchar(50) NOT NULL,
  `delFlag` smallint(6) NOT NULL COMMENT '0=未删除\r\n            1=删除',
  PRIMARY KEY (`userId`),
  KEY `FK_AU_R_DPT` (`depId`) USING BTREE,
  CONSTRAINT `app_user_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='app_user\r\n用户表';

-- ----------------------------
-- Records of app_user
-- ----------------------------
INSERT INTO app_user VALUES ('-1', 'system', '1', '0', 'choupihao@163.com', '1', null, null, null, null, null, null, null, '2009-12-18 00:00:00', '0', null, '系统', '1');
INSERT INTO app_user VALUES ('1', 'admin', '1', 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'choupihao@163.com', '1', null, null, null, null, null, null, null, '2009-12-18 00:00:00', '1', null, '超级管理员', '0');
INSERT INTO app_user VALUES ('2', 'csx', '1', 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'choupihao@163.com', '1', '工程师', '888888', '888888', '', '', '', '', '2010-05-03 00:00:00', '1', null, 'cwx', '0');
INSERT INTO app_user VALUES ('3', 'hr', '1', 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'choupihao@163.com', '1', '', '', '', '', '', '', '', '2011-08-16 00:00:00', '1', null, '人事经理', '0');

-- ----------------------------
-- Table structure for `archives`
-- ----------------------------
DROP TABLE IF EXISTS `archives`;
CREATE TABLE `archives` (
  `archivesId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(20) DEFAULT NULL COMMENT '公文类型',
  `typeName` varchar(128) DEFAULT NULL COMMENT '公文类型名称',
  `archivesNo` varchar(100) NOT NULL COMMENT '发文字号',
  `issueDep` varchar(128) DEFAULT NULL COMMENT '发文机关或部门',
  `depId` bigint(20) DEFAULT NULL COMMENT '发文部门ID',
  `arc_typeId` bigint(20) DEFAULT NULL,
  `subject` varchar(256) NOT NULL COMMENT '文件标题',
  `createtime` datetime NOT NULL,
  `issueDate` datetime NOT NULL COMMENT '发布日期',
  `status` smallint(6) NOT NULL COMMENT '公文状态\r\n            0=拟稿、修改状态\r\n            1=发文状态\r\n            2=归档状态',
  `shortContent` varchar(1024) DEFAULT NULL COMMENT '内容简介',
  `fileCounts` int(11) NOT NULL DEFAULT '0' COMMENT '文件数',
  `privacyLevel` varchar(50) DEFAULT '普通' COMMENT '秘密等级\r\n            普通\r\n            秘密\r\n            机密\r\n            绝密',
  `urgentLevel` varchar(50) DEFAULT '普通' COMMENT '紧急程度\r\n            普通\r\n            紧急\r\n            特急\r\n            特提',
  `issuer` varchar(50) DEFAULT NULL COMMENT '发文人',
  `issuerId` bigint(20) DEFAULT NULL COMMENT '发文人ID',
  `keywords` varchar(256) DEFAULT NULL COMMENT '主题词',
  `sources` varchar(50) DEFAULT NULL COMMENT '公文来源\r\n            仅在收文中指定，发公文不需要指定\r\n            上级公文\r\n            下级公文',
  `archType` smallint(6) NOT NULL DEFAULT '0' COMMENT '0=发文\r\n            1=收文',
  `recDepIds` varchar(2000) DEFAULT NULL COMMENT '用于存储接收公文的部门ID,使用,进行分开',
  `recDepNames` varchar(2000) DEFAULT NULL COMMENT '用于存储接收公文的部门的名称，使用,进行分开',
  `handlerUids` varchar(256) DEFAULT NULL COMMENT '在收文中使用，多个用户ID用'',''分割',
  `handlerUnames` varchar(256) DEFAULT NULL COMMENT '用于收文，存储多个拟办用户名，用‘，’分割',
  `orgArchivesId` bigint(20) DEFAULT NULL COMMENT '用于收文时使用，指向原公文ID',
  `depSignNo` varchar(100) DEFAULT NULL COMMENT '用于收文时，部门对自身的公文自编号',
  PRIMARY KEY (`archivesId`),
  KEY `FK_ARV_R_ART` (`arc_typeId`) USING BTREE,
  KEY `FK_ARV_R_ARVT` (`typeId`) USING BTREE,
  KEY `FK_ARV_R_DPT` (`depId`) USING BTREE,
  CONSTRAINT `archives_ibfk_1` FOREIGN KEY (`arc_typeId`) REFERENCES `arch_rec_type` (`typeId`),
  CONSTRAINT `archives_ibfk_2` FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`),
  CONSTRAINT `archives_ibfk_3` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收发公文';

-- ----------------------------
-- Records of archives
-- ----------------------------

-- ----------------------------
-- Table structure for `archives_attend`
-- ----------------------------
DROP TABLE IF EXISTS `archives_attend`;
CREATE TABLE `archives_attend` (
  `attendId` bigint(20) NOT NULL AUTO_INCREMENT,
  `archivesId` bigint(20) NOT NULL,
  `userID` bigint(20) NOT NULL COMMENT '用户ID',
  `fullname` varchar(128) NOT NULL COMMENT '姓名',
  `attendType` varchar(64) NOT NULL COMMENT '参与类型\r\n            1=校对人\r\n            2=审核人\r\n            3=缮印人\r\n            4=用印人',
  `executeTime` datetime DEFAULT NULL COMMENT '执行时间',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`attendId`),
  KEY `FK_ARVA_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `archives_attend_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='发文拟稿参与人';

-- ----------------------------
-- Records of archives_attend
-- ----------------------------
INSERT INTO archives_attend VALUES ('1', '1', '1', '超级管理员', '2', '2011-07-26 15:22:10', '呵呵');
INSERT INTO archives_attend VALUES ('2', '3', '1', '超级管理员', '2', '2011-07-26 17:35:39', 'tongyi ');
INSERT INTO archives_attend VALUES ('3', '4', '1', '超级管理员', '2', '2011-07-29 10:27:29', '同意');
INSERT INTO archives_attend VALUES ('4', '1', '1', '超级管理员', '1', '2011-07-29 10:30:24', '同意');
INSERT INTO archives_attend VALUES ('5', '6', '1', '超级管理员', '2', '2011-08-01 11:55:07', '你好啊');
INSERT INTO archives_attend VALUES ('6', '6', '1', '超级管理员', '1', '2011-08-01 11:55:43', '你好啊');
INSERT INTO archives_attend VALUES ('7', '7', '1', '超级管理员', '2', '2011-08-01 13:14:55', '我同意了');
INSERT INTO archives_attend VALUES ('8', '7', '1', '超级管理员', '1', '2011-08-01 13:17:26', '编号吧');
INSERT INTO archives_attend VALUES ('9', '10', '1', '超级管理员', '2', '2011-08-03 14:54:06', '核稿');
INSERT INTO archives_attend VALUES ('10', '10', '1', '超级管理员', '1', '2011-08-03 14:55:28', '我承办');

-- ----------------------------
-- Table structure for `archives_dep`
-- ----------------------------
DROP TABLE IF EXISTS `archives_dep`;
CREATE TABLE `archives_dep` (
  `archDepId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `signNo` varchar(128) DEFAULT NULL COMMENT '自编号',
  `depId` bigint(20) NOT NULL COMMENT '收文部门',
  `archivesId` bigint(20) NOT NULL COMMENT '所属公文',
  `subject` varchar(256) NOT NULL COMMENT '公文标题',
  `status` smallint(6) NOT NULL COMMENT '签收状态\r\n            0=未签收\r\n            1=已签收',
  `signTime` datetime DEFAULT NULL COMMENT '签收日期',
  `signFullname` varchar(64) DEFAULT NULL COMMENT '签收人',
  `signUserID` bigint(20) DEFAULT NULL,
  `handleFeedback` varchar(1024) DEFAULT NULL COMMENT '办理结果反馈',
  `isMain` smallint(6) NOT NULL DEFAULT '1' COMMENT '主送、抄送\r\n            1=主送\r\n            0=抄送',
  PRIMARY KEY (`archDepId`),
  KEY `FK_AVD_R_ARV` (`archivesId`) USING BTREE,
  KEY `FK_AVD_R_DPT` (`depId`) USING BTREE,
  CONSTRAINT `archives_dep_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`),
  CONSTRAINT `archives_dep_ibfk_2` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of archives_dep
-- ----------------------------

-- ----------------------------
-- Table structure for `archives_doc`
-- ----------------------------
DROP TABLE IF EXISTS `archives_doc`;
CREATE TABLE `archives_doc` (
  `docId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileId` bigint(20) DEFAULT NULL,
  `archivesId` bigint(20) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL COMMENT '拟稿人',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '拟稿人ID',
  `menderId` bigint(20) DEFAULT NULL,
  `mender` varchar(64) DEFAULT NULL COMMENT '修改人',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `docStatus` smallint(6) NOT NULL COMMENT '文档状态\r\n            0=修改中\r\n            1=修改完成',
  `curVersion` int(11) NOT NULL COMMENT '当前版本\r\n            取当前最新的版本',
  `docPath` varchar(128) NOT NULL COMMENT '文档路径',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`docId`),
  KEY `FK_ARVC_R_FA` (`fileId`) USING BTREE,
  KEY `FK_ARVD_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `archives_doc_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `archives_doc_ibfk_2` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of archives_doc
-- ----------------------------

-- ----------------------------
-- Table structure for `archives_handle`
-- ----------------------------
DROP TABLE IF EXISTS `archives_handle`;
CREATE TABLE `archives_handle` (
  `handleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `archivesId` bigint(20) NOT NULL,
  `handleOpinion` varchar(1024) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `userFullname` varchar(128) NOT NULL,
  `createtime` datetime NOT NULL,
  `fillTime` datetime DEFAULT NULL,
  `isPass` smallint(6) NOT NULL DEFAULT '1' COMMENT '0=尚未审批\r\n            1=通过审批\r\n            ２=未通过审批',
  PRIMARY KEY (`handleId`),
  KEY `FK_AVHD_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `archives_handle_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文拟办人';

-- ----------------------------
-- Records of archives_handle
-- ----------------------------

-- ----------------------------
-- Table structure for `archives_type`
-- ----------------------------
DROP TABLE IF EXISTS `archives_type`;
CREATE TABLE `archives_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '类型名称',
  `typeDesc` varchar(256) DEFAULT NULL COMMENT '类型描述',
  `processDefId` bigint(20) DEFAULT NULL,
  `processDefName` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='公文类型';

-- ----------------------------
-- Records of archives_type
-- ----------------------------
INSERT INTO archives_type VALUES ('1', '一般发文', '一般发文', '4', '发文流程');
INSERT INTO archives_type VALUES ('2', '市局发文', '市局发文', '11', '发文流程-市局发文');
INSERT INTO archives_type VALUES ('3', '请示报告', '请示报告', '4', '发文流程');

-- ----------------------------
-- Table structure for `arch_dispatch`
-- ----------------------------
DROP TABLE IF EXISTS `arch_dispatch`;
CREATE TABLE `arch_dispatch` (
  `dispatchId` bigint(20) NOT NULL AUTO_INCREMENT,
  `archivesId` bigint(20) DEFAULT NULL,
  `dispatchTime` datetime NOT NULL,
  `userId` bigint(20) NOT NULL,
  `fullname` varchar(128) DEFAULT NULL,
  `isRead` smallint(6) DEFAULT NULL,
  `subject` varchar(256) DEFAULT NULL,
  `readFeedback` varchar(1024) DEFAULT NULL,
  `archUserType` smallint(6) NOT NULL DEFAULT '0' COMMENT '0=阅读人员\r\n            1=承办人\r\n            2=分发负责人\r\n            ',
  `disRoleId` bigint(20) DEFAULT NULL,
  `disRoleName` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`dispatchId`),
  KEY `FK_AVDH_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `arch_dispatch_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arch_dispatch
-- ----------------------------

-- ----------------------------
-- Table structure for `arch_flow_conf`
-- ----------------------------
DROP TABLE IF EXISTS `arch_flow_conf`;
CREATE TABLE `arch_flow_conf` (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT,
  `processName` varchar(128) NOT NULL,
  `processDefId` bigint(20) NOT NULL,
  `archType` smallint(6) NOT NULL COMMENT '0=发文\r\n            1=收文',
  PRIMARY KEY (`configId`),
  KEY `FK_AFC_R_PD` (`processDefId`) USING BTREE,
  CONSTRAINT `arch_flow_conf_ibfk_1` FOREIGN KEY (`processDefId`) REFERENCES `pro_definition` (`defId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='公文流程设置';

-- ----------------------------
-- Records of arch_flow_conf
-- ----------------------------
INSERT INTO arch_flow_conf VALUES ('1', '发文流程', '4', '0');
INSERT INTO arch_flow_conf VALUES ('2', '收文流程', '5', '1');

-- ----------------------------
-- Table structure for `arch_hasten`
-- ----------------------------
DROP TABLE IF EXISTS `arch_hasten`;
CREATE TABLE `arch_hasten` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `archivesId` bigint(20) DEFAULT NULL,
  `content` varchar(1024) DEFAULT NULL COMMENT '催办内容',
  `createtime` datetime DEFAULT NULL COMMENT '催办时间',
  `hastenFullname` varchar(64) DEFAULT NULL COMMENT '催办人',
  `handlerFullname` varchar(64) DEFAULT NULL COMMENT '承办人',
  `handlerUserId` bigint(20) DEFAULT NULL COMMENT '承办人ID',
  PRIMARY KEY (`recordId`),
  KEY `FK_ARHN_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `arch_hasten_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arch_hasten
-- ----------------------------

-- ----------------------------
-- Table structure for `arch_rec_filed_type`
-- ----------------------------
DROP TABLE IF EXISTS `arch_rec_filed_type`;
CREATE TABLE `arch_rec_filed_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arch_rec_filed_type
-- ----------------------------
INSERT INTO arch_rec_filed_type VALUES ('1', '人大文件');
INSERT INTO arch_rec_filed_type VALUES ('2', '省级文件');
INSERT INTO arch_rec_filed_type VALUES ('3', '市委文件');
INSERT INTO arch_rec_filed_type VALUES ('4', '市政府文件');
INSERT INTO arch_rec_filed_type VALUES ('5', '市直机关文件');
INSERT INTO arch_rec_filed_type VALUES ('6', '政协文件');
INSERT INTO arch_rec_filed_type VALUES ('7', '省局文件');

-- ----------------------------
-- Table structure for `arch_rec_type`
-- ----------------------------
DROP TABLE IF EXISTS `arch_rec_type`;
CREATE TABLE `arch_rec_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  `depId` bigint(20) DEFAULT NULL,
  `processDefId` bigint(20) DEFAULT NULL,
  `processDefName` varchar(256) DEFAULT '',
  PRIMARY KEY (`typeId`),
  KEY `FK_ART_R_DPT` (`depId`) USING BTREE,
  CONSTRAINT `arch_rec_type_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arch_rec_type
-- ----------------------------
INSERT INTO arch_rec_type VALUES ('1', '市局收文', '1', '15', '收文流程-市局收文');

-- ----------------------------
-- Table structure for `arch_rec_user`
-- ----------------------------
DROP TABLE IF EXISTS `arch_rec_user`;
CREATE TABLE `arch_rec_user` (
  `archRecId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `fullname` varchar(128) DEFAULT NULL COMMENT '用户名',
  `depId` bigint(20) DEFAULT NULL COMMENT '部门ID ',
  `depName` varchar(128) DEFAULT NULL COMMENT '部门名称',
  `leaderUserId` bigint(20) DEFAULT NULL,
  `leaderFullname` varchar(128) DEFAULT NULL,
  `deptUserId` bigint(20) DEFAULT NULL,
  `deptFullname` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`archRecId`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of arch_rec_user
-- ----------------------------
INSERT INTO arch_rec_user VALUES ('1', '1', '超级管理员', '1', '信息部门', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('2', '1', '超级管理员', '2', '办公室', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('3', '1', '超级管理员', '3', '人力资源部', '1', '超级管理员', '2', 'cwx');
INSERT INTO arch_rec_user VALUES ('4', '1', '超级管理员', '4', '财务部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('10', '1', '超级管理员', '10', '市场部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('11', '1', '超级管理员', '11', '合同部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('25', '8', '刘六', '19', '新闻中心', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('26', null, '', '26', '信息系统部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('27', null, '', '27', '商品经营中心', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('28', null, '', '28', '电子商务运营中心', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('29', null, '', '29', '亚运村', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('30', null, '', '30', '首体', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('31', null, '', '31', '王府井', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('32', null, '', '32', '五棵松', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('33', null, '', '33', '中关村', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('34', null, '', '34', '朝阳门', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('35', null, '', '35', '三里河', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('36', null, '', '36', '来广营', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('37', null, '', '37', '门店运营部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('38', null, '', '38', '防损管理部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('39', null, '', '39', '工程管理部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('40', null, '', '40', '总部财务', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('41', null, '', '41', '卖场财务', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('42', null, '', '42', '市场营销部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('43', null, '', '43', '商品规划部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('44', null, '', '44', '商品组织部', '1', '超级管理员', '1', '超级管理员');
INSERT INTO arch_rec_user VALUES ('45', null, '', '45', '商品经营中心数据服务小组', '1', '超级管理员', '1', '超级管理员');

-- ----------------------------
-- Table structure for `arch_template`
-- ----------------------------
DROP TABLE IF EXISTS `arch_template`;
CREATE TABLE `arch_template` (
  `templateId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(20) DEFAULT NULL COMMENT '所属类型',
  `tempName` varchar(128) NOT NULL COMMENT '模板名称',
  `tempPath` varchar(256) NOT NULL COMMENT '路径',
  `fileId` bigint(20) NOT NULL COMMENT '文件ID',
  PRIMARY KEY (`templateId`),
  KEY `FK_AHT_R_FA` (`fileId`) USING BTREE,
  KEY `FK_ART_R_ARVT` (`typeId`) USING BTREE,
  CONSTRAINT `arch_template_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `arch_template_ibfk_2` FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公文模板';

-- ----------------------------
-- Records of arch_template
-- ----------------------------

-- ----------------------------
-- Table structure for `assets_type`
-- ----------------------------
DROP TABLE IF EXISTS `assets_type`;
CREATE TABLE `assets_type` (
  `assetsTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`assetsTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of assets_type
-- ----------------------------

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bookId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(20) DEFAULT NULL,
  `bookName` varchar(128) NOT NULL,
  `author` varchar(128) NOT NULL,
  `isbn` varchar(64) NOT NULL,
  `publisher` varchar(128) DEFAULT NULL,
  `price` decimal(10,0) NOT NULL,
  `location` varchar(128) NOT NULL,
  `department` varchar(64) NOT NULL,
  `amount` int(11) NOT NULL,
  `leftAmount` int(11) NOT NULL,
  PRIMARY KEY (`bookId`),
  KEY `FK_BK_R_BT` (`typeId`) USING BTREE,
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `book_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书';

-- ----------------------------
-- Records of book
-- ----------------------------

-- ----------------------------
-- Table structure for `book_bor_ret`
-- ----------------------------
DROP TABLE IF EXISTS `book_bor_ret`;
CREATE TABLE `book_bor_ret` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `bookSnId` bigint(20) DEFAULT NULL,
  `borrowTime` datetime NOT NULL,
  `returnTime` datetime NOT NULL,
  `lastReturnTime` datetime DEFAULT NULL,
  `borrowIsbn` varchar(128) NOT NULL,
  `bookName` varchar(128) NOT NULL,
  `registerName` varchar(32) NOT NULL,
  `fullname` varchar(32) NOT NULL,
  PRIMARY KEY (`recordId`),
  KEY `FK_BBR_R_BS` (`bookSnId`) USING BTREE,
  CONSTRAINT `book_bor_ret_ibfk_1` FOREIGN KEY (`bookSnId`) REFERENCES `book_sn` (`bookSnId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书借还表';

-- ----------------------------
-- Records of book_bor_ret
-- ----------------------------

-- ----------------------------
-- Table structure for `book_sn`
-- ----------------------------
DROP TABLE IF EXISTS `book_sn`;
CREATE TABLE `book_sn` (
  `bookSnId` bigint(20) NOT NULL AUTO_INCREMENT,
  `bookId` bigint(20) NOT NULL,
  `bookSN` varchar(128) NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '借阅状态\r\n            0=未借出\r\n            1=借出\r\n            2=预订\r\n            3=注销',
  PRIMARY KEY (`bookSnId`),
  KEY `FK_BS_R_BK` (`bookId`) USING BTREE,
  CONSTRAINT `book_sn_ibfk_1` FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of book_sn
-- ----------------------------

-- ----------------------------
-- Table structure for `book_type`
-- ----------------------------
DROP TABLE IF EXISTS `book_type`;
CREATE TABLE `book_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图书类别';

-- ----------------------------
-- Records of book_type
-- ----------------------------

-- ----------------------------
-- Table structure for `calendar_plan`
-- ----------------------------
DROP TABLE IF EXISTS `calendar_plan`;
CREATE TABLE `calendar_plan` (
  `planId` bigint(20) NOT NULL AUTO_INCREMENT,
  `startTime` datetime DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  `urgent` smallint(6) NOT NULL COMMENT '紧急程度\r\n            0=一般\r\n            1=重要\r\n            2=紧急',
  `content` varchar(1200) NOT NULL COMMENT '内容',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            0=未完成\r\n            1=完成',
  `userId` bigint(20) NOT NULL COMMENT '员工ID',
  `fullname` varchar(32) DEFAULT NULL COMMENT '员工名',
  `assignerId` bigint(20) NOT NULL COMMENT '分配人',
  `assignerName` varchar(32) DEFAULT NULL COMMENT '分配人名',
  `feedback` varchar(500) DEFAULT NULL COMMENT '反馈意见',
  `showStyle` smallint(6) NOT NULL COMMENT '显示方式\r\n            1=仅在任务中显示\r\n            2=在日程与任务中显示',
  `taskType` smallint(6) NOT NULL COMMENT '任务类型\r\n            1=限期任务\r\n            2=非限期任务',
  PRIMARY KEY (`planId`),
  KEY `FK_CA_R_AU` (`userId`) USING BTREE,
  KEY `FK_CP_R_AUAS` (`assignerId`) USING BTREE,
  CONSTRAINT `calendar_plan_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `calendar_plan_ibfk_2` FOREIGN KEY (`assignerId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日程安排';

-- ----------------------------
-- Records of calendar_plan
-- ----------------------------

-- ----------------------------
-- Table structure for `cal_file`
-- ----------------------------
DROP TABLE IF EXISTS `cal_file`;
CREATE TABLE `cal_file` (
  `fileId` bigint(20) NOT NULL,
  `planId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`planId`),
  KEY `FK_CF_R_CP` (`planId`) USING BTREE,
  CONSTRAINT `cal_file_ibfk_1` FOREIGN KEY (`planId`) REFERENCES `calendar_plan` (`planId`),
  CONSTRAINT `cal_file_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cal_file
-- ----------------------------

-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `carId` bigint(20) NOT NULL AUTO_INCREMENT,
  `carNo` varchar(128) NOT NULL,
  `carType` varchar(64) NOT NULL COMMENT '轿车\r\n            货车\r\n            商务车\r\n            ',
  `engineNo` varchar(128) DEFAULT NULL,
  `buyInsureTime` datetime DEFAULT NULL COMMENT '购买保险时间',
  `auditTime` datetime DEFAULT NULL COMMENT '年审时间',
  `notes` varchar(500) DEFAULT NULL,
  `factoryModel` varchar(64) NOT NULL,
  `driver` varchar(32) NOT NULL,
  `buyDate` date NOT NULL COMMENT '购置日期',
  `status` smallint(6) NOT NULL COMMENT '当前状态\r\n            1=可用\r\n            2=维修中\r\n            0=报废',
  `cartImage` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆信息';

-- ----------------------------
-- Records of car
-- ----------------------------

-- ----------------------------
-- Table structure for `cart_repair`
-- ----------------------------
DROP TABLE IF EXISTS `cart_repair`;
CREATE TABLE `cart_repair` (
  `repairId` bigint(20) NOT NULL AUTO_INCREMENT,
  `carId` bigint(20) DEFAULT NULL,
  `repairDate` datetime NOT NULL COMMENT '维护日期',
  `reason` varchar(128) NOT NULL COMMENT '维护原因',
  `executant` varchar(128) NOT NULL COMMENT '经办人',
  `notes` varchar(128) DEFAULT NULL COMMENT '备注',
  `repairType` varchar(128) NOT NULL COMMENT '维修类型\r\n            保养\r\n            维修',
  `fee` decimal(18,2) DEFAULT NULL COMMENT '费用',
  PRIMARY KEY (`repairId`),
  KEY `FK_CRR_R_CR` (`carId`) USING BTREE,
  CONSTRAINT `cart_repair_ibfk_1` FOREIGN KEY (`carId`) REFERENCES `car` (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cart_repair
-- ----------------------------

-- ----------------------------
-- Table structure for `car_apply`
-- ----------------------------
DROP TABLE IF EXISTS `car_apply`;
CREATE TABLE `car_apply` (
  `applyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `carId` bigint(20) NOT NULL,
  `department` varchar(64) NOT NULL,
  `userFullname` varchar(32) NOT NULL,
  `applyDate` date NOT NULL,
  `reason` varchar(512) NOT NULL,
  `startTime` datetime NOT NULL,
  `endTime` datetime DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `proposer` varchar(32) NOT NULL,
  `mileage` decimal(18,2) DEFAULT NULL,
  `oilUse` decimal(18,2) DEFAULT NULL,
  `notes` varchar(128) DEFAULT NULL,
  `approvalStatus` smallint(6) NOT NULL,
  PRIMARY KEY (`applyId`),
  KEY `FK_CRA_R_CR` (`carId`) USING BTREE,
  CONSTRAINT `car_apply_ibfk_1` FOREIGN KEY (`carId`) REFERENCES `car` (`carId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆申请';

-- ----------------------------
-- Records of car_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
  `companyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `companyNo` varchar(128) DEFAULT NULL,
  `companyName` varchar(128) NOT NULL,
  `companyDesc` varchar(4000) DEFAULT NULL,
  `legalPerson` varchar(32) DEFAULT NULL,
  `setup` datetime DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `fax` varchar(32) DEFAULT NULL,
  `site` varchar(128) DEFAULT NULL,
  `logo` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`companyId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='公司信息';

-- ----------------------------
-- Records of company
-- ----------------------------
INSERT INTO company VALUES ('1', '', '上品折扣', '<br>​', '', null, '', '', '', '');

-- ----------------------------
-- Table structure for `contract`
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
  `contractId` bigint(20) NOT NULL AUTO_INCREMENT,
  `contractNo` varchar(64) NOT NULL COMMENT '合同编号',
  `subject` varchar(128) NOT NULL COMMENT '合同标题',
  `contractAmount` decimal(10,0) NOT NULL COMMENT '合同金额',
  `mainItem` varchar(4000) DEFAULT NULL COMMENT '主要条款',
  `salesAfterItem` varchar(4000) DEFAULT NULL COMMENT '售后条款',
  `validDate` datetime NOT NULL COMMENT '生效日期',
  `expireDate` datetime NOT NULL COMMENT '有效期',
  `serviceDep` varchar(64) DEFAULT NULL COMMENT '维修部门',
  `serviceMan` varchar(64) DEFAULT NULL COMMENT '维修负责人',
  `signupUser` varchar(64) NOT NULL COMMENT '签约人',
  `signupTime` datetime NOT NULL COMMENT '签约时间',
  `creator` varchar(32) NOT NULL COMMENT '录入人',
  `createtime` datetime NOT NULL COMMENT '录入时间',
  `projectId` bigint(20) DEFAULT NULL COMMENT '所属项目',
  `consignAddress` varchar(128) DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(32) DEFAULT NULL COMMENT '收货人',
  PRIMARY KEY (`contractId`),
  KEY `FK_CT_R_PT` (`projectId`) USING BTREE,
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contract
-- ----------------------------

-- ----------------------------
-- Table structure for `contract_config`
-- ----------------------------
DROP TABLE IF EXISTS `contract_config`;
CREATE TABLE `contract_config` (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(128) NOT NULL COMMENT '设备名称',
  `contractId` bigint(20) DEFAULT NULL,
  `itemSpec` varchar(128) NOT NULL COMMENT '设置规格',
  `amount` decimal(18,2) NOT NULL COMMENT '数量',
  `notes` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`configId`),
  KEY `FK_CC_R_CT` (`contractId`) USING BTREE,
  CONSTRAINT `contract_config_ibfk_1` FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同配置单';

-- ----------------------------
-- Records of contract_config
-- ----------------------------

-- ----------------------------
-- Table structure for `contract_file`
-- ----------------------------
DROP TABLE IF EXISTS `contract_file`;
CREATE TABLE `contract_file` (
  `fileId` bigint(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`contractId`),
  KEY `FK_CTF_R_CT` (`contractId`) USING BTREE,
  CONSTRAINT `contract_file_ibfk_1` FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`),
  CONSTRAINT `contract_file_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='合同附件';

-- ----------------------------
-- Records of contract_file
-- ----------------------------

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customerId` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerNo` varchar(64) NOT NULL COMMENT '客户号\r\n            自动生成',
  `industryType` varchar(64) NOT NULL COMMENT '所属行业\r\n            有缺省的选择，也可以输入',
  `customerSource` varchar(64) DEFAULT NULL COMMENT '客户来源\r\n            可编辑，可添加\r\n            \r\n            电话访问\r\n            网络\r\n            客户或朋友介绍',
  `customerType` smallint(6) NOT NULL COMMENT '1=正式客户\r\n            2=重要客户\r\n            3＝潜在客户\r\n            4＝无效客户',
  `companyScale` int(11) DEFAULT NULL COMMENT '1=1-20人\r\n            2=20-50人\r\n            3=50-100人\r\n            4=100-200人\r\n            5=200-500人\r\n            6=500-1000 人\r\n            7=1000人以上\r\n            ',
  `customerName` varchar(128) NOT NULL COMMENT '客户名称\r\n            一般为公司名称',
  `customerManager` varchar(32) NOT NULL COMMENT '负责该客户的经理',
  `phone` varchar(32) NOT NULL COMMENT '电话',
  `fax` varchar(32) DEFAULT NULL,
  `site` varchar(128) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `state` varchar(32) DEFAULT NULL,
  `city` varchar(32) DEFAULT NULL,
  `zip` varchar(32) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `registerFun` decimal(10,0) DEFAULT NULL,
  `turnOver` decimal(10,0) DEFAULT NULL,
  `currencyUnit` varchar(32) DEFAULT NULL COMMENT '注册资金及年营业额的货币单位\r\n            可选可编辑\r\n            人民币（默认）\r\n            美元\r\n            ',
  `otherDesc` varchar(800) DEFAULT NULL,
  `principal` varchar(32) DEFAULT NULL,
  `openBank` varchar(64) DEFAULT NULL,
  `accountsNo` varchar(64) DEFAULT NULL,
  `taxNo` varchar(64) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `rights` smallint(6) NOT NULL COMMENT '1=客户经理及上级经理有权查看\r\n            2=公开\r\n            3=共享人员有权查看',
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息';

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for `cus_connection`
-- ----------------------------
DROP TABLE IF EXISTS `cus_connection`;
CREATE TABLE `cus_connection` (
  `connId` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) NOT NULL,
  `contactor` varchar(32) NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `content` varchar(512) NOT NULL,
  `notes` varchar(1000) DEFAULT NULL,
  `creator` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`connId`),
  KEY `FK_CC_R_CS` (`customerId`) USING BTREE,
  CONSTRAINT `cus_connection_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='business connection ';

-- ----------------------------
-- Records of cus_connection
-- ----------------------------

-- ----------------------------
-- Table structure for `cus_linkman`
-- ----------------------------
DROP TABLE IF EXISTS `cus_linkman`;
CREATE TABLE `cus_linkman` (
  `linkmanId` bigint(20) NOT NULL AUTO_INCREMENT,
  `customerId` bigint(20) NOT NULL COMMENT '所属客户',
  `fullname` varchar(32) NOT NULL COMMENT '姓名',
  `sex` smallint(6) NOT NULL COMMENT '性别',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `mobile` varchar(32) NOT NULL COMMENT '手机',
  `fax` varchar(32) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL COMMENT 'Email',
  `msn` varchar(100) DEFAULT NULL COMMENT 'MSN',
  `qq` varchar(64) DEFAULT NULL COMMENT 'QQ',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `homeAddress` varchar(128) DEFAULT NULL COMMENT '家庭住址',
  `homeZip` varchar(32) DEFAULT NULL COMMENT '邮编',
  `homePhone` varchar(32) DEFAULT NULL COMMENT '家庭电话',
  `hobby` varchar(100) DEFAULT NULL COMMENT '爱好',
  `isPrimary` smallint(6) NOT NULL COMMENT '是否为主要联系人',
  `notes` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`linkmanId`),
  KEY `FK_CLM_R_CS` (`customerId`) USING BTREE,
  CONSTRAINT `cus_linkman_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户联系人';

-- ----------------------------
-- Records of cus_linkman
-- ----------------------------

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `depId` bigint(20) NOT NULL AUTO_INCREMENT,
  `depName` varchar(128) NOT NULL COMMENT '部门名称',
  `depDesc` varchar(256) DEFAULT NULL COMMENT '部门描述',
  `depLevel` int(11) NOT NULL COMMENT '层次',
  `parentId` bigint(20) DEFAULT NULL,
  `path` varchar(128) DEFAULT NULL COMMENT '路径',
  `phone` varchar(64) DEFAULT NULL,
  `fax` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`depId`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO department VALUES ('1', '总裁办公室', '总裁办公室', '2', '0', '0.1.', null, null);
INSERT INTO department VALUES ('2', '门店运营中心', '办公室', '2', '0', '0.2.', null, null);
INSERT INTO department VALUES ('3', '财务部', '财务部', '2', '0', '0.3.', null, null);
INSERT INTO department VALUES ('4', '企业发展部', '企业发展部', '2', '0', '0.4.', null, null);
INSERT INTO department VALUES ('10', '合同管理部', '合同管理部', '2', '0', '0.10.', null, null);
INSERT INTO department VALUES ('11', '人力资源部', '人力资源部', '2', '0', '0.11.', null, null);
INSERT INTO department VALUES ('19', '行政部', '行政部', '2', '0', '0.19.', null, null);
INSERT INTO department VALUES ('26', '信息系统部', '信息系统部', '2', '0', '0.26.', null, null);
INSERT INTO department VALUES ('27', '商品经营中心', '商品经营中心', '2', '0', '0.27.', null, null);
INSERT INTO department VALUES ('28', '电子商务运营中心', '电子商务运营中心', '2', '0', '0.28.', null, null);
INSERT INTO department VALUES ('29', '亚运村', '亚运村', '3', '2', '0.2.29.', null, null);
INSERT INTO department VALUES ('30', '首体', '首体', '3', '2', '0.2.30.', null, null);
INSERT INTO department VALUES ('31', '王府井', '王府井', '3', '2', '0.2.31.', null, null);
INSERT INTO department VALUES ('32', '五棵松', '五棵松', '3', '2', '0.2.32.', null, null);
INSERT INTO department VALUES ('33', '中关村', '中关村', '3', '2', '0.2.33.', null, null);
INSERT INTO department VALUES ('34', '朝阳门', '朝阳门', '3', '2', '0.2.34.', null, null);
INSERT INTO department VALUES ('35', '三里河', '三里河', '3', '2', '0.2.35.', null, null);
INSERT INTO department VALUES ('36', '来广营', '来广营', '3', '2', '0.2.36.', null, null);
INSERT INTO department VALUES ('37', '门店运营部', '门店运营部', '3', '2', '0.2.37.', null, null);
INSERT INTO department VALUES ('38', '防损管理部', '防损管理部', '3', '2', '0.2.38.', null, null);
INSERT INTO department VALUES ('39', '工程管理部', '工程管理部', '3', '2', '0.2.39.', null, null);
INSERT INTO department VALUES ('40', '总部财务', '总部财务', '3', '3', '0.3.40.', null, null);
INSERT INTO department VALUES ('41', '卖场财务', '卖场财务', '3', '3', '0.3.41.', null, null);
INSERT INTO department VALUES ('42', '市场营销部', '市场营销部', '3', '27', '0.27.42.', null, null);
INSERT INTO department VALUES ('43', '商品规划部', '商品规划部', '3', '27', '0.27.43.', null, null);
INSERT INTO department VALUES ('44', '商品组织部', '商品组织部', '3', '27', '0.27.44.', null, null);
INSERT INTO department VALUES ('45', '商品经营中心数据服务小组', '商品经营中心数据服务小组', '3', '27', '0.27.45.', null, null);
INSERT INTO department VALUES ('46', '未分配部门', '未分配部门', '2', '0', '0.46.', null, null);

-- ----------------------------
-- Table structure for `depre_record`
-- ----------------------------
DROP TABLE IF EXISTS `depre_record`;
CREATE TABLE `depre_record` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `assetsId` bigint(20) NOT NULL,
  `workCapacity` decimal(18,2) DEFAULT NULL,
  `workGrossUnit` varchar(128) DEFAULT NULL,
  `depreAmount` decimal(18,4) NOT NULL,
  `calTime` datetime NOT NULL,
  PRIMARY KEY (`recordId`),
  KEY `FK_DR_R_FA` (`assetsId`) USING BTREE,
  CONSTRAINT `depre_record_ibfk_1` FOREIGN KEY (`assetsId`) REFERENCES `fixed_assets` (`assetsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of depre_record
-- ----------------------------

-- ----------------------------
-- Table structure for `depre_type`
-- ----------------------------
DROP TABLE IF EXISTS `depre_type`;
CREATE TABLE `depre_type` (
  `depreTypeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(64) NOT NULL,
  `deprePeriod` int(11) NOT NULL COMMENT '单位为月',
  `typeDesc` varchar(500) DEFAULT NULL,
  `calMethod` smallint(6) NOT NULL COMMENT '1=平均年限法\r\n            2=工作量法\r\n            加速折旧法\r\n            3=双倍余额递减法\r\n            4=年数总和法',
  PRIMARY KEY (`depreTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='depreciation type';

-- ----------------------------
-- Records of depre_type
-- ----------------------------

-- ----------------------------
-- Table structure for `diary`
-- ----------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary` (
  `diaryId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `dayTime` date NOT NULL,
  `content` text NOT NULL,
  `diaryType` smallint(6) NOT NULL COMMENT '1=工作日志\r\n            0=个人日志',
  PRIMARY KEY (`diaryId`),
  KEY `FK_DY_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `diary_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diary
-- ----------------------------

-- ----------------------------
-- Table structure for `dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `dicId` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(64) NOT NULL,
  `itemValue` varchar(128) NOT NULL,
  `descp` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`dicId`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of dictionary
-- ----------------------------
INSERT INTO dictionary VALUES ('1', '宗教信仰', '佛教', null);
INSERT INTO dictionary VALUES ('2', '宗教信仰', '道教', null);
INSERT INTO dictionary VALUES ('3', '宗教信仰', '基督宗教', null);
INSERT INTO dictionary VALUES ('4', '宗教信仰', '天主教', null);
INSERT INTO dictionary VALUES ('5', '宗教信仰', '伊斯兰教', null);
INSERT INTO dictionary VALUES ('6', '宗教信仰', '犹太教', null);
INSERT INTO dictionary VALUES ('7', '宗教信仰', '孔教', null);
INSERT INTO dictionary VALUES ('8', '宗教信仰', '神道教', null);
INSERT INTO dictionary VALUES ('9', '宗教信仰', '耆那教', null);
INSERT INTO dictionary VALUES ('10', '宗教信仰', '印度教', null);
INSERT INTO dictionary VALUES ('11', '宗教信仰', '东正教', null);
INSERT INTO dictionary VALUES ('12', '宗教信仰', '新教', null);
INSERT INTO dictionary VALUES ('13', '宗教信仰', '锡克教', null);
INSERT INTO dictionary VALUES ('14', '宗教信仰', '琐罗亚斯德教', null);
INSERT INTO dictionary VALUES ('15', '宗教信仰', '巴哈伊教', null);
INSERT INTO dictionary VALUES ('16', '宗教信仰', '其它', null);
INSERT INTO dictionary VALUES ('17', '民族', '汉族', null);
INSERT INTO dictionary VALUES ('18', '民族', '阿昌族', null);
INSERT INTO dictionary VALUES ('19', '民族', '白族', null);
INSERT INTO dictionary VALUES ('20', '民族', '保安族', null);
INSERT INTO dictionary VALUES ('21', '民族', '布朗族', null);
INSERT INTO dictionary VALUES ('22', '民族', '布依族', null);
INSERT INTO dictionary VALUES ('23', '民族', '朝鲜族', null);
INSERT INTO dictionary VALUES ('24', '民族', '达斡族', null);
INSERT INTO dictionary VALUES ('25', '民族', '傣族', null);
INSERT INTO dictionary VALUES ('26', '民族', '德昂族', null);
INSERT INTO dictionary VALUES ('27', '民族', '侗族', null);
INSERT INTO dictionary VALUES ('28', '民族', '东乡族', null);
INSERT INTO dictionary VALUES ('29', '民族', '独龙族', null);
INSERT INTO dictionary VALUES ('30', '民族', '鄂伦春族', null);
INSERT INTO dictionary VALUES ('31', '民族', '俄罗斯族', null);
INSERT INTO dictionary VALUES ('32', '民族', '鄂温克族', null);
INSERT INTO dictionary VALUES ('33', '民族', '高山族', null);
INSERT INTO dictionary VALUES ('34', '民族', '仡佬族', null);
INSERT INTO dictionary VALUES ('35', '民族', '哈尼族', null);
INSERT INTO dictionary VALUES ('36', '民族', '啥萨克族', null);
INSERT INTO dictionary VALUES ('37', '民族', '赫哲族', null);
INSERT INTO dictionary VALUES ('38', '民族', '回族', null);
INSERT INTO dictionary VALUES ('39', '民族', '基诺族', null);
INSERT INTO dictionary VALUES ('40', '民族', '京族', null);
INSERT INTO dictionary VALUES ('41', '民族', '景颇族', null);
INSERT INTO dictionary VALUES ('42', '民族', '柯尔克孜族', null);
INSERT INTO dictionary VALUES ('43', '民族', '拉祜族', null);
INSERT INTO dictionary VALUES ('44', '民族', '黎族', null);
INSERT INTO dictionary VALUES ('45', '民族', '僳僳族', null);
INSERT INTO dictionary VALUES ('46', '民族', '珞巴族', null);
INSERT INTO dictionary VALUES ('47', '民族', '满族', null);
INSERT INTO dictionary VALUES ('48', '民族', '毛南族', null);
INSERT INTO dictionary VALUES ('49', '民族', '门巴族', null);
INSERT INTO dictionary VALUES ('50', '民族', '蒙古族', null);
INSERT INTO dictionary VALUES ('51', '民族', '苗族', null);
INSERT INTO dictionary VALUES ('52', '民族', '仫佬族', null);
INSERT INTO dictionary VALUES ('53', '民族', '纳西族', null);
INSERT INTO dictionary VALUES ('54', '民族', '怒族', null);
INSERT INTO dictionary VALUES ('55', '民族', '普米族', null);
INSERT INTO dictionary VALUES ('56', '民族', '羌族', null);
INSERT INTO dictionary VALUES ('57', '民族', '撒拉族', null);
INSERT INTO dictionary VALUES ('58', '民族', '畲族', null);
INSERT INTO dictionary VALUES ('59', '民族', '水族', null);
INSERT INTO dictionary VALUES ('60', '民族', '塔吉克族', null);
INSERT INTO dictionary VALUES ('61', '民族', '塔塔尔族', null);
INSERT INTO dictionary VALUES ('62', '民族', '土族', null);
INSERT INTO dictionary VALUES ('63', '民族', '土家族', null);
INSERT INTO dictionary VALUES ('64', '民族', '佤族', null);
INSERT INTO dictionary VALUES ('65', '民族', '维吾尔族', null);
INSERT INTO dictionary VALUES ('66', '民族', '乌孜别克族', null);
INSERT INTO dictionary VALUES ('67', '民族', '锡伯族', null);
INSERT INTO dictionary VALUES ('68', '民族', '瑶族', null);
INSERT INTO dictionary VALUES ('69', '民族', '彝族', null);
INSERT INTO dictionary VALUES ('70', '民族', '藏族', null);
INSERT INTO dictionary VALUES ('71', '民族', '壮族', null);
INSERT INTO dictionary VALUES ('72', '学历', '博士', null);
INSERT INTO dictionary VALUES ('73', '学历', '研究生', null);
INSERT INTO dictionary VALUES ('74', '学历', '本科', null);
INSERT INTO dictionary VALUES ('75', '学历', '大专', null);
INSERT INTO dictionary VALUES ('76', '学历', '中专', null);
INSERT INTO dictionary VALUES ('77', '学历', '初中', null);
INSERT INTO dictionary VALUES ('78', '学历', '小学', null);
INSERT INTO dictionary VALUES ('79', '学历', '其它', null);
INSERT INTO dictionary VALUES ('80', '政治面貌', '群众', null);
INSERT INTO dictionary VALUES ('81', '政治面貌', '共青团员', null);
INSERT INTO dictionary VALUES ('82', '政治面貌', '中共党员', null);
INSERT INTO dictionary VALUES ('83', '国籍', '中华人民共和国', null);
INSERT INTO dictionary VALUES ('84', '国籍', '美国', null);
INSERT INTO dictionary VALUES ('85', '国籍', '俄罗斯', null);
INSERT INTO dictionary VALUES ('86', '国籍', '日本', null);
INSERT INTO dictionary VALUES ('87', '国籍', '韩国', null);
INSERT INTO dictionary VALUES ('88', '国籍', '新加波', null);
INSERT INTO dictionary VALUES ('89', '国籍', '马来西亚', null);
INSERT INTO dictionary VALUES ('90', '国籍', '英国', null);
INSERT INTO dictionary VALUES ('91', '国籍', '德国', null);
INSERT INTO dictionary VALUES ('92', '国籍', '意大利', null);
INSERT INTO dictionary VALUES ('93', '国籍', '澳大利亚', null);
INSERT INTO dictionary VALUES ('94', '国籍', '巴西', null);
INSERT INTO dictionary VALUES ('95', '专业', '管理科学', null);
INSERT INTO dictionary VALUES ('96', '专业', '信息管理和信息系统', null);
INSERT INTO dictionary VALUES ('97', '专业', '工业工程', null);
INSERT INTO dictionary VALUES ('98', '专业', '工程管理', null);
INSERT INTO dictionary VALUES ('99', '专业', '农业经理管理', null);
INSERT INTO dictionary VALUES ('100', '专业', '工商管理', null);
INSERT INTO dictionary VALUES ('101', '专业', '市场营销', null);
INSERT INTO dictionary VALUES ('102', '专业', '会计学', null);
INSERT INTO dictionary VALUES ('103', '专业', '涉外会计', null);
INSERT INTO dictionary VALUES ('104', '专业', '会计电算化', null);
INSERT INTO dictionary VALUES ('105', '专业', '财政金融', null);
INSERT INTO dictionary VALUES ('106', '专业', '财务管理', null);
INSERT INTO dictionary VALUES ('107', '专业', '技术经济', null);
INSERT INTO dictionary VALUES ('108', '专业', '文秘', null);
INSERT INTO dictionary VALUES ('109', '专业', '国际商务', null);
INSERT INTO dictionary VALUES ('110', '专业', '物流管理', null);
INSERT INTO dictionary VALUES ('111', '专业', '行政管理', null);
INSERT INTO dictionary VALUES ('112', '专业', '公共事业管理', null);
INSERT INTO dictionary VALUES ('113', '专业', '旅游管理', null);
INSERT INTO dictionary VALUES ('114', '专业', '宾馆/酒店管理', null);
INSERT INTO dictionary VALUES ('115', '专业', '人力资源管理', null);
INSERT INTO dictionary VALUES ('116', '专业', '公共关系学', null);
INSERT INTO dictionary VALUES ('117', '专业', '物业管理', null);
INSERT INTO dictionary VALUES ('118', '专业', '房地产经营管理', null);
INSERT INTO dictionary VALUES ('119', '专业', '劳动与社会保障', null);
INSERT INTO dictionary VALUES ('120', '专业', '土地资源管理', null);
INSERT INTO dictionary VALUES ('121', '专业', '图书档案学', null);
INSERT INTO dictionary VALUES ('122', '专业', '计算机科学与技术', null);
INSERT INTO dictionary VALUES ('123', '专业', '计算机应用', null);
INSERT INTO dictionary VALUES ('124', '专业', '计算机信息管理', null);
INSERT INTO dictionary VALUES ('125', '专业', '计算机网络', null);
INSERT INTO dictionary VALUES ('126', '专业', '电子商务', null);
INSERT INTO dictionary VALUES ('127', '专业', '通信工程', null);
INSERT INTO dictionary VALUES ('128', '专业', '电气工程及其自动化', null);
INSERT INTO dictionary VALUES ('129', '专业', '软件工程', null);
INSERT INTO dictionary VALUES ('130', '专业', '自动化', null);
INSERT INTO dictionary VALUES ('131', '专业', '电子信息工程', null);
INSERT INTO dictionary VALUES ('132', '专业', '电子科学与技术', null);
INSERT INTO dictionary VALUES ('133', '专业', '电子信息科学与技术', null);
INSERT INTO dictionary VALUES ('134', '专业', '微电子学', null);
INSERT INTO dictionary VALUES ('135', '专业', '光信息科学与技术', null);
INSERT INTO dictionary VALUES ('136', '专业', '机械设计制造及其自动化', null);
INSERT INTO dictionary VALUES ('137', '专业', '材料成型及控制工程', null);
INSERT INTO dictionary VALUES ('138', '专业', '工业设计', null);
INSERT INTO dictionary VALUES ('139', '专业', '过程装备与控制工程', null);
INSERT INTO dictionary VALUES ('140', '专业', '机械电子工程/机电一体化', null);
INSERT INTO dictionary VALUES ('141', '专业', '模具设计与制造', null);
INSERT INTO dictionary VALUES ('142', '专业', '机械制造工艺与设备', null);
INSERT INTO dictionary VALUES ('143', '专业', '测控技术与仪器', null);
INSERT INTO dictionary VALUES ('144', '专业', '热能与动力工程', null);
INSERT INTO dictionary VALUES ('145', '专业', '核工程与核技术', null);
INSERT INTO dictionary VALUES ('146', '专业', '电力系统及自动化', null);
INSERT INTO dictionary VALUES ('147', '专业', '制冷与低温技术', null);
INSERT INTO dictionary VALUES ('148', '专业', '冶金工程', null);
INSERT INTO dictionary VALUES ('149', '专业', '金属材料工程', null);
INSERT INTO dictionary VALUES ('150', '专业', '无机非金属料工程', null);
INSERT INTO dictionary VALUES ('151', '专业', '高分子材料与工程', null);
INSERT INTO dictionary VALUES ('152', '专业', '材料物理', null);
INSERT INTO dictionary VALUES ('153', '专业', '材料化学', null);
INSERT INTO dictionary VALUES ('154', '专业', '材料科学与工程', null);
INSERT INTO dictionary VALUES ('155', '专业', '食品科学与工程', null);
INSERT INTO dictionary VALUES ('156', '专业', '轻化工程', null);
INSERT INTO dictionary VALUES ('157', '专业', '包装工程', null);
INSERT INTO dictionary VALUES ('158', '专业', '印刷工程', null);
INSERT INTO dictionary VALUES ('159', '专业', '纺织工程', null);
INSERT INTO dictionary VALUES ('160', '专业', '服装设计与工程', null);
INSERT INTO dictionary VALUES ('161', '专业', '建筑学', null);
INSERT INTO dictionary VALUES ('162', '专业', '城市规划', null);
INSERT INTO dictionary VALUES ('163', '专业', '园林规划与设计', null);
INSERT INTO dictionary VALUES ('164', '专业', '土木工程', null);
INSERT INTO dictionary VALUES ('165', '专业', '道路与桥梁', null);
INSERT INTO dictionary VALUES ('166', '专业', '建设环境与设备工程', null);
INSERT INTO dictionary VALUES ('167', '专业', '给水排水工程', null);
INSERT INTO dictionary VALUES ('168', '专业', '供热通风与空调工程', null);
INSERT INTO dictionary VALUES ('169', '专业', '工业与民用建筑', null);
INSERT INTO dictionary VALUES ('170', '专业', '室内装潢设计', null);
INSERT INTO dictionary VALUES ('171', '专业', '建筑工程', null);
INSERT INTO dictionary VALUES ('172', '专业', '工程造价管理', null);
INSERT INTO dictionary VALUES ('173', '专业', '力学', null);
INSERT INTO dictionary VALUES ('174', '专业', '应用力学', null);
INSERT INTO dictionary VALUES ('175', '专业', '环境科学', null);
INSERT INTO dictionary VALUES ('176', '专业', '生态学', null);
INSERT INTO dictionary VALUES ('177', '专业', '环境工程', null);
INSERT INTO dictionary VALUES ('178', '专业', '安全工程', null);
INSERT INTO dictionary VALUES ('179', '专业', '制药工程', null);
INSERT INTO dictionary VALUES ('180', '专业', '交通运输', null);
INSERT INTO dictionary VALUES ('181', '专业', '交通工程', null);
INSERT INTO dictionary VALUES ('182', '专业', '油气储运工程', null);
INSERT INTO dictionary VALUES ('183', '专业', '飞行技术', null);
INSERT INTO dictionary VALUES ('184', '专业', '航海技术', null);
INSERT INTO dictionary VALUES ('185', '专业', '轮机工程', null);
INSERT INTO dictionary VALUES ('186', '专业', '汽车工程', null);
INSERT INTO dictionary VALUES ('187', '专业', '飞行器设计与工程', null);
INSERT INTO dictionary VALUES ('188', '专业', '飞行器动力工程', null);
INSERT INTO dictionary VALUES ('189', '专业', '飞行器制造工程', null);
INSERT INTO dictionary VALUES ('190', '专业', '飞行器环境与生命保障工程', null);
INSERT INTO dictionary VALUES ('191', '专业', '船舶与海洋工程', null);
INSERT INTO dictionary VALUES ('192', '专业', '水利水电工程', null);
INSERT INTO dictionary VALUES ('193', '专业', '水文与水资源工程', null);
INSERT INTO dictionary VALUES ('194', '专业', '港口航道与海岸工程', null);
INSERT INTO dictionary VALUES ('195', '专业', '测绘工程', null);
INSERT INTO dictionary VALUES ('196', '专业', '公安技术', null);
INSERT INTO dictionary VALUES ('197', '专业', '武器系统与发射工程', null);
INSERT INTO dictionary VALUES ('198', '专业', '探测制导与控制技术', null);
INSERT INTO dictionary VALUES ('199', '专业', '弹药工程与爆炸技术', null);
INSERT INTO dictionary VALUES ('200', '专业', '数学与应用数学', null);
INSERT INTO dictionary VALUES ('201', '专业', '信息与计算科学', null);
INSERT INTO dictionary VALUES ('202', '专业', '物理学', null);
INSERT INTO dictionary VALUES ('203', '专业', '应用物理学', null);
INSERT INTO dictionary VALUES ('204', '专业', '化学', null);
INSERT INTO dictionary VALUES ('205', '专业', '应用化学', null);
INSERT INTO dictionary VALUES ('206', '专业', '化学工程与工艺', null);
INSERT INTO dictionary VALUES ('207', '专业', '精细化工', null);
INSERT INTO dictionary VALUES ('208', '专业', '化工设备与机械', null);
INSERT INTO dictionary VALUES ('209', '专业', '生物工程', null);
INSERT INTO dictionary VALUES ('210', '专业', '生物医学工程', null);
INSERT INTO dictionary VALUES ('211', '专业', '生物科学,技术', null);
INSERT INTO dictionary VALUES ('212', '专业', '天文学', null);
INSERT INTO dictionary VALUES ('213', '专业', '地质学', null);
INSERT INTO dictionary VALUES ('214', '专业', '宝石鉴定与加工', null);
INSERT INTO dictionary VALUES ('215', '专业', '地理科学', null);
INSERT INTO dictionary VALUES ('216', '专业', '地球物理学', null);
INSERT INTO dictionary VALUES ('217', '专业', '大气科学', null);
INSERT INTO dictionary VALUES ('218', '专业', '海洋科学', null);
INSERT INTO dictionary VALUES ('219', '专业', '地矿', null);
INSERT INTO dictionary VALUES ('220', '专业', '石油工程', null);
INSERT INTO dictionary VALUES ('221', '专业', '经济学', null);
INSERT INTO dictionary VALUES ('222', '专业', '国际经济与贸易', null);
INSERT INTO dictionary VALUES ('223', '专业', '财政学', null);
INSERT INTO dictionary VALUES ('224', '专业', '金融学', null);
INSERT INTO dictionary VALUES ('225', '专业', '经济管理', null);
INSERT INTO dictionary VALUES ('226', '专业', '经济信息管理', null);
INSERT INTO dictionary VALUES ('227', '专业', '工业外贸', null);
INSERT INTO dictionary VALUES ('228', '专业', '国际金融', null);
INSERT INTO dictionary VALUES ('229', '专业', '投资经济管理', null);
INSERT INTO dictionary VALUES ('230', '专业', '统计学', null);
INSERT INTO dictionary VALUES ('231', '专业', '审计学', null);
INSERT INTO dictionary VALUES ('232', '专业', '中国语言文学', null);
INSERT INTO dictionary VALUES ('233', '专业', '英语', null);
INSERT INTO dictionary VALUES ('234', '专业', '俄语', null);
INSERT INTO dictionary VALUES ('235', '专业', '德语', null);
INSERT INTO dictionary VALUES ('236', '专业', '法语', null);
INSERT INTO dictionary VALUES ('237', '专业', '日语', null);
INSERT INTO dictionary VALUES ('238', '专业', '西班牙语', null);
INSERT INTO dictionary VALUES ('239', '专业', '阿拉伯语', null);
INSERT INTO dictionary VALUES ('240', '专业', '朝鲜语', null);
INSERT INTO dictionary VALUES ('241', '专业', '其它外语', null);
INSERT INTO dictionary VALUES ('242', '专业', '新闻学', null);
INSERT INTO dictionary VALUES ('243', '专业', '广播电视新闻', null);
INSERT INTO dictionary VALUES ('244', '专业', '广告学', null);
INSERT INTO dictionary VALUES ('245', '专业', '编辑出版学', null);
INSERT INTO dictionary VALUES ('246', '专业', '外贸英语', null);
INSERT INTO dictionary VALUES ('247', '专业', '商务英语', null);
INSERT INTO dictionary VALUES ('248', '专业', '音乐,舞蹈,作曲', null);
INSERT INTO dictionary VALUES ('249', '专业', '绘画,艺术设计', null);
INSERT INTO dictionary VALUES ('250', '专业', '戏剧,表演', null);
INSERT INTO dictionary VALUES ('251', '专业', '导演,广播电视编导', null);
INSERT INTO dictionary VALUES ('252', '专业', '戏剧影视文学', null);
INSERT INTO dictionary VALUES ('253', '专业', '戏剧影视美术设计', null);
INSERT INTO dictionary VALUES ('254', '专业', '摄影,动画', null);
INSERT INTO dictionary VALUES ('255', '专业', '播音,主持,录音', null);
INSERT INTO dictionary VALUES ('256', '专业', '服装设计', null);
INSERT INTO dictionary VALUES ('257', '专业', '法学', null);
INSERT INTO dictionary VALUES ('258', '专业', '马克思主义理论', null);
INSERT INTO dictionary VALUES ('259', '专业', '社会学', null);
INSERT INTO dictionary VALUES ('260', '专业', '政治学与行政学', null);
INSERT INTO dictionary VALUES ('261', '专业', '国际政治', null);
INSERT INTO dictionary VALUES ('262', '专业', '外交学', null);
INSERT INTO dictionary VALUES ('263', '专业', '思想政治教育', null);
INSERT INTO dictionary VALUES ('264', '专业', '公安学', null);
INSERT INTO dictionary VALUES ('265', '专业', '经济法', null);
INSERT INTO dictionary VALUES ('266', '专业', '国际经济法', null);
INSERT INTO dictionary VALUES ('267', '专业', '哲学', null);
INSERT INTO dictionary VALUES ('268', '专业', '逻辑学', null);
INSERT INTO dictionary VALUES ('269', '专业', '宗教学', null);
INSERT INTO dictionary VALUES ('270', '专业', '教育学', null);
INSERT INTO dictionary VALUES ('271', '专业', '学前教育', null);
INSERT INTO dictionary VALUES ('272', '专业', '体育学', null);
INSERT INTO dictionary VALUES ('273', '专业', '基础医学', null);
INSERT INTO dictionary VALUES ('274', '专业', '预防医学', null);
INSERT INTO dictionary VALUES ('275', '专业', '临床医学与医学技术', null);
INSERT INTO dictionary VALUES ('276', '专业', '口腔医学', null);
INSERT INTO dictionary VALUES ('277', '专业', '中医学', null);
INSERT INTO dictionary VALUES ('278', '专业', '法医学', null);
INSERT INTO dictionary VALUES ('279', '专业', '护理学', null);
INSERT INTO dictionary VALUES ('280', '专业', '药学', null);
INSERT INTO dictionary VALUES ('281', '专业', '心理学', null);
INSERT INTO dictionary VALUES ('282', '专业', '医学检验', null);
INSERT INTO dictionary VALUES ('283', '专业', '植物生产', null);
INSERT INTO dictionary VALUES ('284', '专业', '农学', null);
INSERT INTO dictionary VALUES ('285', '专业', '园艺', null);
INSERT INTO dictionary VALUES ('286', '专业', '植物保护学', null);
INSERT INTO dictionary VALUES ('287', '专业', '茶学', null);
INSERT INTO dictionary VALUES ('288', '专业', '草业科学', null);
INSERT INTO dictionary VALUES ('289', '专业', '森林资源', null);
INSERT INTO dictionary VALUES ('290', '专业', '环境生态', null);
INSERT INTO dictionary VALUES ('291', '专业', '园林', null);
INSERT INTO dictionary VALUES ('292', '专业', '动物生产', null);
INSERT INTO dictionary VALUES ('293', '专业', '动物医学', null);
INSERT INTO dictionary VALUES ('294', '专业', '水产类', null);
INSERT INTO dictionary VALUES ('295', '专业', '农业工程', null);
INSERT INTO dictionary VALUES ('296', '专业', '林业工程', null);
INSERT INTO dictionary VALUES ('297', '专业', '历史学', null);
INSERT INTO dictionary VALUES ('298', '专业', '考古学', null);
INSERT INTO dictionary VALUES ('299', '专业', '博物馆学', null);
INSERT INTO dictionary VALUES ('300', '奖惩类型', '奖励', null);
INSERT INTO dictionary VALUES ('301', '奖惩类型', '惩罚', null);

-- ----------------------------
-- Table structure for `document`
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `docId` bigint(20) NOT NULL AUTO_INCREMENT,
  `docName` varchar(100) NOT NULL,
  `content` text COMMENT '内容',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime DEFAULT NULL,
  `folderId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `fullname` varchar(32) NOT NULL,
  `haveAttach` smallint(6) DEFAULT NULL,
  `sharedUserIds` varchar(1000) DEFAULT NULL COMMENT '共享员工ID',
  `sharedUserNames` varchar(1000) DEFAULT NULL,
  `sharedDepIds` varchar(1000) DEFAULT NULL COMMENT '共享部门ID',
  `sharedDepNames` varchar(1000) DEFAULT NULL,
  `sharedRoleIds` varchar(1000) DEFAULT NULL COMMENT '共享角色ID',
  `sharedRoleNames` varchar(1000) DEFAULT NULL,
  `isShared` smallint(6) NOT NULL COMMENT '是否共享',
  PRIMARY KEY (`docId`),
  KEY `FK_DT_R_AU` (`userId`) USING BTREE,
  KEY `FK_DT_R_DF` (`folderId`) USING BTREE,
  CONSTRAINT `document_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `document_ibfk_2` FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档';

-- ----------------------------
-- Records of document
-- ----------------------------

-- ----------------------------
-- Table structure for `doc_file`
-- ----------------------------
DROP TABLE IF EXISTS `doc_file`;
CREATE TABLE `doc_file` (
  `fileId` bigint(20) NOT NULL,
  `docId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`docId`),
  KEY `FK_DF_F_DT` (`docId`) USING BTREE,
  CONSTRAINT `doc_file_ibfk_1` FOREIGN KEY (`docId`) REFERENCES `document` (`docId`),
  CONSTRAINT `doc_file_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc_file
-- ----------------------------

-- ----------------------------
-- Table structure for `doc_folder`
-- ----------------------------
DROP TABLE IF EXISTS `doc_folder`;
CREATE TABLE `doc_folder` (
  `folderId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `folderName` varchar(128) NOT NULL COMMENT '目录名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父目录',
  `path` varchar(128) DEFAULT NULL COMMENT '路径\r\n            为当前路径的＋上级路径\r\n            如当前ID为3，上级目录的路径为1.2，\r\n            则当前的路径为1.2.3.',
  `isShared` smallint(6) NOT NULL,
  PRIMARY KEY (`folderId`),
  KEY `FK_DF_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `doc_folder_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc_folder
-- ----------------------------
INSERT INTO doc_folder VALUES ('1', '1', '用户操作管理', '0', '1.', '0');
INSERT INTO doc_folder VALUES ('2', '1', '管理及限制', '0', '2.', '0');
INSERT INTO doc_folder VALUES ('3', '2', '成文档', '0', '3.', '0');
INSERT INTO doc_folder VALUES ('4', '2', '强类型', '0', '4.', '0');

-- ----------------------------
-- Table structure for `doc_history`
-- ----------------------------
DROP TABLE IF EXISTS `doc_history`;
CREATE TABLE `doc_history` (
  `historyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `docId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL COMMENT '附件ID',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `path` varchar(128) NOT NULL COMMENT '路径',
  `version` int(11) NOT NULL COMMENT '版本',
  `updatetime` datetime NOT NULL COMMENT '更新时间',
  `mender` varchar(64) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`historyId`),
  KEY `FK_DHY_R_ARVD` (`docId`) USING BTREE,
  KEY `FK_DH_R_FA` (`fileId`) USING BTREE,
  CONSTRAINT `doc_history_ibfk_1` FOREIGN KEY (`docId`) REFERENCES `archives_doc` (`docId`),
  CONSTRAINT `doc_history_ibfk_2` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of doc_history
-- ----------------------------

-- ----------------------------
-- Table structure for `doc_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `doc_privilege`;
CREATE TABLE `doc_privilege` (
  `privilegeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `folderId` bigint(20) DEFAULT NULL,
  `docId` bigint(20) DEFAULT NULL,
  `rights` int(11) NOT NULL COMMENT '权限\r\n            文档或目录的读写修改权限\r\n            1=读\r\n            2=修改\r\n            4=删除\r\n            \r\n            权限值可以为上面的值之和\r\n            如：3则代表进行读，修改的操作\r\n            \r\n            \r\n            ',
  `udrId` int(11) DEFAULT NULL,
  `udrName` varchar(128) DEFAULT NULL,
  `flag` smallint(6) NOT NULL COMMENT '1=user\r\n            2=deparment\r\n            3=role',
  `fdFlag` smallint(6) NOT NULL COMMENT '缺省为文件夹权限\r\n            1=文档权限\r\n            0=文件夹权限',
  PRIMARY KEY (`privilegeId`),
  KEY `FK_DP_R_DF` (`folderId`) USING BTREE,
  KEY `FK_DP_R_DT` (`docId`) USING BTREE,
  CONSTRAINT `doc_privilege_ibfk_1` FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`),
  CONSTRAINT `doc_privilege_ibfk_2` FOREIGN KEY (`docId`) REFERENCES `document` (`docId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文档或目录的权限，只要是针对公共目录下的';

-- ----------------------------
-- Records of doc_privilege
-- ----------------------------

-- ----------------------------
-- Table structure for `duty`
-- ----------------------------
DROP TABLE IF EXISTS `duty`;
CREATE TABLE `duty` (
  `dutyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL COMMENT '员工ID',
  `fullname` varchar(32) NOT NULL COMMENT '员工姓名',
  `systemId` bigint(20) NOT NULL COMMENT '班制ID',
  `startTime` datetime NOT NULL COMMENT '开始时间',
  `endTime` datetime DEFAULT NULL COMMENT '结束时间',
  PRIMARY KEY (`dutyId`),
  KEY `FK_DUY_R_AU` (`userId`) USING BTREE,
  KEY `FK_DUY_R_DS` (`systemId`) USING BTREE,
  CONSTRAINT `duty_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `duty_ibfk_2` FOREIGN KEY (`systemId`) REFERENCES `duty_system` (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='排班';

-- ----------------------------
-- Records of duty
-- ----------------------------

-- ----------------------------
-- Table structure for `duty_register`
-- ----------------------------
DROP TABLE IF EXISTS `duty_register`;
CREATE TABLE `duty_register` (
  `registerId` bigint(20) NOT NULL AUTO_INCREMENT,
  `registerDate` datetime NOT NULL COMMENT '登记时间',
  `userId` bigint(20) NOT NULL COMMENT '登记人',
  `fullname` varchar(32) NOT NULL,
  `regFlag` smallint(6) NOT NULL COMMENT '登记标识\r\n            1=正常登记（上班，下班）\r\n            2＝迟到\r\n            3=早退\r\n            4＝休息\r\n            5＝旷工\r\n            6=放假\r\n            \r\n            ',
  `regMins` int(11) NOT NULL COMMENT '迟到或早退分钟\r\n            正常上班时为0',
  `reason` varchar(128) DEFAULT NULL COMMENT '迟到原因',
  `dayOfWeek` int(11) NOT NULL COMMENT '周几\r\n            1=星期日\r\n            ..\r\n            7=日期六',
  `inOffFlag` smallint(6) NOT NULL COMMENT '上下班标识\r\n            1=签到\r\n            2=签退',
  `sectionId` bigint(20) NOT NULL,
  PRIMARY KEY (`registerId`),
  KEY `FK_DR_R_AU` (`userId`) USING BTREE,
  KEY `FK_DR_R_DS` (`sectionId`) USING BTREE,
  CONSTRAINT `duty_register_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `duty_register_ibfk_2` FOREIGN KEY (`sectionId`) REFERENCES `duty_section` (`sectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duty_register
-- ----------------------------

-- ----------------------------
-- Table structure for `duty_section`
-- ----------------------------
DROP TABLE IF EXISTS `duty_section`;
CREATE TABLE `duty_section` (
  `sectionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sectionName` varchar(64) NOT NULL,
  `startSignin` datetime NOT NULL,
  `dutyStartTime` datetime NOT NULL,
  `endSignin` datetime NOT NULL,
  `earlyOffTime` datetime NOT NULL,
  `dutyEndTime` datetime NOT NULL,
  `signOutTime` datetime NOT NULL COMMENT '',
  PRIMARY KEY (`sectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of duty_section
-- ----------------------------

-- ----------------------------
-- Table structure for `duty_system`
-- ----------------------------
DROP TABLE IF EXISTS `duty_system`;
CREATE TABLE `duty_system` (
  `systemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `systemName` varchar(128) NOT NULL,
  `systemSetting` varchar(128) NOT NULL,
  `systemDesc` varchar(256) NOT NULL,
  `isDefault` smallint(6) NOT NULL COMMENT '',
  PRIMARY KEY (`systemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='';

-- ----------------------------
-- Records of duty_system
-- ----------------------------

-- ----------------------------
-- Table structure for `emp_profile`
-- ----------------------------
DROP TABLE IF EXISTS `emp_profile`;
CREATE TABLE `emp_profile` (
  `profileId` bigint(20) NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (`profileId`),
  KEY `FK_EPF_R_AU` (`userId`) USING BTREE,
  KEY `FK_EP_R_JB` (`jobId`) USING BTREE,
  KEY `FK_SD_R_SY` (`standardId`) USING BTREE,
  CONSTRAINT `emp_profile_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `emp_profile_ibfk_2` FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`),
  CONSTRAINT `emp_profile_ibfk_3` FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工档案';

-- ----------------------------
-- Records of emp_profile
-- ----------------------------

-- ----------------------------
-- Table structure for `errands_register`
-- ----------------------------
DROP TABLE IF EXISTS `errands_register`;
CREATE TABLE `errands_register` (
  `dateId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL COMMENT '外出/加班人员',
  `descp` varchar(500) NOT NULL COMMENT '描述',
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `approvalId` bigint(20) NOT NULL COMMENT '审批人ID',
  `status` smallint(6) NOT NULL COMMENT '审批状态\r\n            0=未审批\r\n            1=通过审批\r\n            2=不通过审批',
  `approvalOption` varchar(500) DEFAULT NULL COMMENT '审批意见',
  `approvalName` varchar(128) NOT NULL COMMENT '审批人',
  `flag` smallint(6) DEFAULT NULL COMMENT '标识\r\n            0=加班\r\n            1=请假\r\n            2=外出',
  `leaveTypeId` bigint(20) NOT NULL,
  `leaveTypeName` varchar(128) NOT NULL,
  PRIMARY KEY (`dateId`),
  KEY `FK_ERP_R_AU` (`approvalId`) USING BTREE,
  KEY `FK_ER_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `errands_register_ibfk_1` FOREIGN KEY (`approvalId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `errands_register_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假、加班、外出登记';

-- ----------------------------
-- Records of errands_register
-- ----------------------------

-- ----------------------------
-- Table structure for `file_attach`
-- ----------------------------
DROP TABLE IF EXISTS `file_attach`;
CREATE TABLE `file_attach` (
  `fileId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(128) NOT NULL COMMENT '文件名',
  `filePath` varchar(128) NOT NULL COMMENT '文件路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `ext` varchar(32) DEFAULT NULL COMMENT '扩展名',
  `fileType` varchar(32) NOT NULL COMMENT '附件类型\r\n            如：邮件附件',
  `note` varchar(1024) DEFAULT NULL COMMENT '说明',
  `creator` varchar(32) NOT NULL COMMENT '上传者',
  PRIMARY KEY (`fileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件';

-- ----------------------------
-- Records of file_attach
-- ----------------------------

-- ----------------------------
-- Table structure for `fixed_assets`
-- ----------------------------
DROP TABLE IF EXISTS `fixed_assets`;
CREATE TABLE `fixed_assets` (
  `assetsId` bigint(20) NOT NULL AUTO_INCREMENT,
  `assetsNo` varchar(128) DEFAULT NULL,
  `assetsName` varchar(128) NOT NULL,
  `model` varchar(64) DEFAULT NULL,
  `assetsTypeId` bigint(20) NOT NULL,
  `manufacturer` varchar(64) DEFAULT NULL,
  `manuDate` datetime DEFAULT NULL,
  `buyDate` datetime NOT NULL,
  `beDep` varchar(64) NOT NULL,
  `custodian` varchar(32) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `remainValRate` decimal(18,6) NOT NULL,
  `depreTypeId` bigint(20) NOT NULL,
  `startDepre` datetime DEFAULT NULL,
  `intendTerm` decimal(18,2) DEFAULT NULL,
  `intendWorkGross` decimal(18,2) DEFAULT NULL COMMENT '当折旧的方法选择用工作量法进行计算时，才需要填写',
  `workGrossUnit` varchar(128) DEFAULT NULL,
  `assetValue` decimal(18,4) NOT NULL,
  `assetCurValue` decimal(18,4) NOT NULL,
  `depreRate` decimal(18,2) DEFAULT NULL,
  `defPerWorkGross` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`assetsId`),
  KEY `FK_FA_R_AT` (`assetsTypeId`) USING BTREE,
  KEY `FK_FA_R_DT` (`depreTypeId`) USING BTREE,
  CONSTRAINT `fixed_assets_ibfk_1` FOREIGN KEY (`assetsTypeId`) REFERENCES `assets_type` (`assetsTypeId`),
  CONSTRAINT `fixed_assets_ibfk_2` FOREIGN KEY (`depreTypeId`) REFERENCES `depre_type` (`depreTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fixed_assets
-- ----------------------------

-- ----------------------------
-- Table structure for `form_data`
-- ----------------------------
DROP TABLE IF EXISTS `form_data`;
CREATE TABLE `form_data` (
  `dataId` bigint(20) NOT NULL AUTO_INCREMENT,
  `formId` bigint(20) NOT NULL COMMENT '所属表单',
  `fieldLabel` varchar(128) NOT NULL COMMENT '字段标签',
  `fieldName` varchar(64) NOT NULL COMMENT '字段名称',
  `intValue` int(11) DEFAULT NULL COMMENT '整数值',
  `longValue` bigint(20) DEFAULT NULL COMMENT '长整值',
  `decValue` decimal(18,4) DEFAULT NULL COMMENT '精度值',
  `dateValue` datetime DEFAULT NULL COMMENT '日期值',
  `strValue` varchar(4000) DEFAULT NULL COMMENT '字符值',
  `boolValue` smallint(6) DEFAULT NULL COMMENT '布尔值',
  `blobValue` blob COMMENT '对象值',
  `isShowed` smallint(6) NOT NULL COMMENT '是否显示\r\n            1=显示\r\n            0=不显示',
  `textValue` text,
  `fieldType` varchar(32) NOT NULL,
  PRIMARY KEY (`dataId`),
  KEY `FK_FD_R_PF` (`formId`) USING BTREE,
  CONSTRAINT `form_data_ibfk_1` FOREIGN KEY (`formId`) REFERENCES `process_form` (`formId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of form_data
-- ----------------------------

-- ----------------------------
-- Table structure for `form_def`
-- ----------------------------
DROP TABLE IF EXISTS `form_def`;
CREATE TABLE `form_def` (
  `formDefId` bigint(20) NOT NULL AUTO_INCREMENT,
  `formName` varchar(128) NOT NULL COMMENT '表单名称',
  `columns` int(11) NOT NULL COMMENT '总列数',
  `isEnabled` smallint(6) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `activityName` varchar(128) NOT NULL COMMENT '节点名称',
  `deployId` varchar(128) NOT NULL COMMENT 'Jbpm流程发布ID',
  `extDef` text COMMENT '表单定义',
  `formView` varchar(128) DEFAULT NULL COMMENT '流程定义View',
  PRIMARY KEY (`formDefId`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of form_def
-- ----------------------------
INSERT INTO form_def VALUES ('1', '申请-表单', '1', '1', '申请', '7', null, null);
INSERT INTO form_def VALUES ('2', '部门领导审阅-表单', '1', '1', '部门领导审阅', '7', null, null);
INSERT INTO form_def VALUES ('3', '分管领导盖章-表单', '1', '1', '分管领导盖章', '7', null, null);
INSERT INTO form_def VALUES ('4', '打印-表单', '1', '1', '打印', '7', null, null);
INSERT INTO form_def VALUES ('5', '财务处-表单', '1', '1', '财务处', '7', null, null);
INSERT INTO form_def VALUES ('6', '开始-表单', '1', '1', '开始', '8', null, null);
INSERT INTO form_def VALUES ('7', '上级审批-表单', '1', '1', '上级审批', '8', null, null);
INSERT INTO form_def VALUES ('8', '开始-表单', '1', '1', '开始', '5', null, null);
INSERT INTO form_def VALUES ('9', '登记拟办-表单', '1', '1', '登记拟办', '5', null, null);
INSERT INTO form_def VALUES ('10', '领导批示-表单', '1', '1', '领导批示', '5', null, null);
INSERT INTO form_def VALUES ('11', '公文分发-表单', '1', '1', '公文分发', '5', null, null);
INSERT INTO form_def VALUES ('12', '承办传阅-表单', '1', '1', '承办传阅', '5', null, null);
INSERT INTO form_def VALUES ('13', '开始-表单', '1', '1', '开始', '4', null, null);
INSERT INTO form_def VALUES ('14', '发文核稿-表单', '1', '1', '发文核稿', '4', null, null);
INSERT INTO form_def VALUES ('15', '科室审核-表单', '1', '1', '科室审核', '4', null, null);
INSERT INTO form_def VALUES ('16', '主管领导审批-表单', '1', '1', '主管领导审批', '4', null, null);
INSERT INTO form_def VALUES ('17', '发文分发-表单', '1', '1', '发文分发', '4', null, null);
INSERT INTO form_def VALUES ('18', '发文校对-表单', '1', '1', '发文校对', '4', null, null);
INSERT INTO form_def VALUES ('19', '分管领导签发-表单', '1', '1', '分管领导签发', '4', null, null);
INSERT INTO form_def VALUES ('20', '科室负责人核稿-表单', '1', '1', '科室负责人核稿', '12', null, null);
INSERT INTO form_def VALUES ('21', '分管或局领导签发-表单', '1', '1', '分管或局领导签发', '12', null, null);
INSERT INTO form_def VALUES ('22', '办公室主任承办-表单', '1', '1', '办公室主任承办', '12', null, null);
INSERT INTO form_def VALUES ('23', '编号盖章分发-表单', '1', '1', '编号盖章分发', '12', null, null);
INSERT INTO form_def VALUES ('24', '开始-表单', '1', '1', '开始', '12', null, null);
INSERT INTO form_def VALUES ('25', '开始-表单', '1', '1', '开始', '13', null, null);
INSERT INTO form_def VALUES ('26', '部门负责人审批-表单', '1', '1', '部门负责人审批', '13', null, null);
INSERT INTO form_def VALUES ('27', '人事登记-表单', '1', '1', '人事登记', '13', null, null);
INSERT INTO form_def VALUES ('28', '开始-表单', '1', '1', '开始', '15', null, null);
INSERT INTO form_def VALUES ('29', '部门负责人审批-表单', '1', '1', '部门负责人审批', '15', null, null);
INSERT INTO form_def VALUES ('30', '局长审批-表单', '1', '1', '局长审批', '15', null, null);
INSERT INTO form_def VALUES ('31', '人事登记-表单', '1', '1', '人事登记', '15', null, null);
INSERT INTO form_def VALUES ('32', '开始-表单', '1', '1', '开始', '14', null, null);
INSERT INTO form_def VALUES ('33', '部门负责人审批-表单', '1', '1', '部门负责人审批', '14', null, null);
INSERT INTO form_def VALUES ('34', '分管局长审批-表单', '1', '1', '分管局长审批', '14', null, null);
INSERT INTO form_def VALUES ('35', '局长审批-表单', '1', '1', '局长审批', '14', null, null);
INSERT INTO form_def VALUES ('36', '人事登记-表单', '1', '1', '人事登记', '14', null, null);
INSERT INTO form_def VALUES ('37', '开始-表单', '1', '1', '开始', '17', null, null);
INSERT INTO form_def VALUES ('38', '上报审批-表单', '1', '1', '上报审批', '17', null, null);
INSERT INTO form_def VALUES ('39', '人力资源核实并确认-表单', '1', '1', '人力资源核实并确认', '17', null, null);
INSERT INTO form_def VALUES ('40', '目标设定与面谈-表单', '1', '1', '目标设定与面谈', '17', null, null);
INSERT INTO form_def VALUES ('41', '考核期评估-表单', '1', '1', '考核期评估', '17', null, null);
INSERT INTO form_def VALUES ('42', '领导批准-表单', '1', '1', '领导批准', '17', null, null);
INSERT INTO form_def VALUES ('43', '人力资源确认-表单', '1', '1', '人力资源确认', '17', null, null);
INSERT INTO form_def VALUES ('44', '晋升面谈-表单', '1', '1', '晋升面谈', '17', null, null);
INSERT INTO form_def VALUES ('45', '任命发文-表单', '1', '1', '任命发文', '17', null, null);
INSERT INTO form_def VALUES ('46', '开始-表单', '1', '1', '开始', '18', null, null);
INSERT INTO form_def VALUES ('47', '人力资源复核确认-表单', '1', '1', '人力资源复核确认', '18', null, null);
INSERT INTO form_def VALUES ('48', '开始-表单', '1', '1', '开始', '20', null, null);
INSERT INTO form_def VALUES ('49', '直线经理审核-表单', '1', '1', '直线经理审核', '20', null, null);
INSERT INTO form_def VALUES ('50', '人力资源复核-表单', '1', '1', '人力资源复核', '20', null, null);
INSERT INTO form_def VALUES ('51', '分管副总裁确认-表单', '1', '1', '分管副总裁确认', '20', null, null);

-- ----------------------------
-- Table structure for `fun_url`
-- ----------------------------
DROP TABLE IF EXISTS `fun_url`;
CREATE TABLE `fun_url` (
  `urlId` bigint(20) NOT NULL AUTO_INCREMENT,
  `functionId` bigint(20) NOT NULL,
  `urlPath` varchar(128) NOT NULL,
  PRIMARY KEY (`urlId`),
  KEY `FK_FU_R_AFN` (`functionId`) USING BTREE,
  CONSTRAINT `fun_url_ibfk_1` FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`)
) ENGINE=InnoDB AUTO_INCREMENT=386 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fun_url
-- ----------------------------
INSERT INTO fun_url VALUES ('1', '1', '/system/listAppUser.do');
INSERT INTO fun_url VALUES ('2', '2', '/system/listAppUser.do');
INSERT INTO fun_url VALUES ('3', '2', '/system/chooseRolesAppUser.do');
INSERT INTO fun_url VALUES ('4', '2', '/system/selectedRolesAppUser.do');
INSERT INTO fun_url VALUES ('5', '2', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('6', '3', '/system/listAppUser.do');
INSERT INTO fun_url VALUES ('7', '3', '/system/chooseRolesAppUser.do');
INSERT INTO fun_url VALUES ('8', '3', '/system/selectedRolesAppUser.do');
INSERT INTO fun_url VALUES ('9', '3', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('10', '4', '/system/listAppUser.do');
INSERT INTO fun_url VALUES ('11', '4', '/system/multiDelAppUser.do');
INSERT INTO fun_url VALUES ('12', '5', '/system/listAppRole.do');
INSERT INTO fun_url VALUES ('13', '6', '/system/listAppRole.do');
INSERT INTO fun_url VALUES ('14', '6', '/system/saveAppRole.do');
INSERT INTO fun_url VALUES ('15', '7', '/system/listAppRole.do');
INSERT INTO fun_url VALUES ('16', '7', '/system/saveAppRole.do');
INSERT INTO fun_url VALUES ('17', '7', '/system/getAppRole.do');
INSERT INTO fun_url VALUES ('18', '8', '/system/listAppRole.do');
INSERT INTO fun_url VALUES ('19', '8', '/system/mulDelAppRole.do');
INSERT INTO fun_url VALUES ('20', '9', '/system/listAppRole.do');
INSERT INTO fun_url VALUES ('21', '9', '/system/grantXmlAppRole.do');
INSERT INTO fun_url VALUES ('22', '9', '/system/getAppRole.do');
INSERT INTO fun_url VALUES ('23', '9', '/system/grantAppRole.do');
INSERT INTO fun_url VALUES ('24', '10', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('25', '10', '/system/selectAppUser.do');
INSERT INTO fun_url VALUES ('26', '11', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('27', '11', '/system/addDepartment.do');
INSERT INTO fun_url VALUES ('28', '11', '/system/selectAppUser.do');
INSERT INTO fun_url VALUES ('29', '11', '/system/saveAppUser.do');
INSERT INTO fun_url VALUES ('30', '12', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('31', '12', '/system/addDepartment.do');
INSERT INTO fun_url VALUES ('32', '12', '/system/detailDepartment.do');
INSERT INTO fun_url VALUES ('33', '12', '/system/selectAppUser.do');
INSERT INTO fun_url VALUES ('34', '12', '/system/saveAppUser.do');
INSERT INTO fun_url VALUES ('35', '13', '/system/listDepartment.do');
INSERT INTO fun_url VALUES ('36', '13', '/system/removeDepartment.do');
INSERT INTO fun_url VALUES ('37', '13', '/system/selectAppUser.do');
INSERT INTO fun_url VALUES ('38', '13', '/system/saveAppUser.do');
INSERT INTO fun_url VALUES ('39', '14', '/system/listFileAttach.do');
INSERT INTO fun_url VALUES ('40', '15', '/system/saveFileAttach.do');
INSERT INTO fun_url VALUES ('41', '15', '/system/listFileAttach.do');
INSERT INTO fun_url VALUES ('42', '15', '/system/getFileAttach.do');
INSERT INTO fun_url VALUES ('43', '16', '/system/listFileAttach.do');
INSERT INTO fun_url VALUES ('44', '16', '/system/multiDelFileAttach.do');
INSERT INTO fun_url VALUES ('45', '17', '/system/listCompany.do');
INSERT INTO fun_url VALUES ('46', '17', '/system/addCompany.do');
INSERT INTO fun_url VALUES ('47', '18', '/flow/rootProType.do');
INSERT INTO fun_url VALUES ('48', '18', '/flow/listProDefinition.do');
INSERT INTO fun_url VALUES ('49', '18', '/flow/processDetail.do');
INSERT INTO fun_url VALUES ('50', '19', '/flow/rootProType.do');
INSERT INTO fun_url VALUES ('51', '19', '/flow/saveProType.do');
INSERT INTO fun_url VALUES ('52', '19', '/flow/removeProType.do');
INSERT INTO fun_url VALUES ('53', '19', '/flow/getProType.do');
INSERT INTO fun_url VALUES ('54', '20', '/flow/rootProType.do');
INSERT INTO fun_url VALUES ('55', '20', '/flow/listProDefinition.do');
INSERT INTO fun_url VALUES ('56', '20', '/flow/saveProDefinition.do');
INSERT INTO fun_url VALUES ('57', '20', '/flow/listProType.do');
INSERT INTO fun_url VALUES ('58', '20', '/flow/getProDefinition.do');
INSERT INTO fun_url VALUES ('59', '21', '/flow/rootProType.do');
INSERT INTO fun_url VALUES ('60', '21', '/flow/listProDefinition.do');
INSERT INTO fun_url VALUES ('61', '21', '/flow/saveProDefinition.do');
INSERT INTO fun_url VALUES ('62', '21', '/flow/listProType.do');
INSERT INTO fun_url VALUES ('63', '21', '/flow/getProDefinition.do');
INSERT INTO fun_url VALUES ('64', '22', '/flow/rootProType.do');
INSERT INTO fun_url VALUES ('65', '22', '/flow/listProDefinition.do');
INSERT INTO fun_url VALUES ('66', '22', '/flow/multiDelProDefinition.do');
INSERT INTO fun_url VALUES ('67', '23', '/flow/processDetail.do');
INSERT INTO fun_url VALUES ('68', '24', '/flow/saveProUserAssign.do');
INSERT INTO fun_url VALUES ('69', '24', '/flow/listProUserAssign.do');
INSERT INTO fun_url VALUES ('70', '25', '/document/saveDocFolder.do');
INSERT INTO fun_url VALUES ('71', '25', '/document/getDocFolder.do');
INSERT INTO fun_url VALUES ('72', '25', '/document/removeDocFolder.do');
INSERT INTO fun_url VALUES ('73', '26', '/document/listDocPrivilege.do');
INSERT INTO fun_url VALUES ('74', '27', '/document/listDocPrivilege.do');
INSERT INTO fun_url VALUES ('75', '27', '/document/addDocPrivilege.do');
INSERT INTO fun_url VALUES ('76', '28', '/document/listDocPrivilege.do');
INSERT INTO fun_url VALUES ('77', '28', '/document/changeDocPrivilege.do');
INSERT INTO fun_url VALUES ('78', '29', '/document/listDocPrivilege.do');
INSERT INTO fun_url VALUES ('79', '29', '/document/multiDelDocPrivilege.do');
INSERT INTO fun_url VALUES ('80', '30', '/task/listPlanType.do');
INSERT INTO fun_url VALUES ('81', '31', '/task/listPlanType.do');
INSERT INTO fun_url VALUES ('82', '31', '/task/savePlanType.do');
INSERT INTO fun_url VALUES ('83', '32', '/task/listPlanType.do');
INSERT INTO fun_url VALUES ('84', '32', '/task/savePlanType.do');
INSERT INTO fun_url VALUES ('85', '32', '/task/getPlanType.do');
INSERT INTO fun_url VALUES ('86', '33', '/task/listPlanType.do');
INSERT INTO fun_url VALUES ('87', '33', '/task/multiDelPlanType.do');
INSERT INTO fun_url VALUES ('88', '35', '/info/categoryNews.do');
INSERT INTO fun_url VALUES ('89', '35', '/info/treeNewsType.do');
INSERT INTO fun_url VALUES ('90', '36', '/info/categoryNews.do');
INSERT INTO fun_url VALUES ('91', '36', '/info/treeNewsType.do');
INSERT INTO fun_url VALUES ('92', '36', '/info/saveNews.do');
INSERT INTO fun_url VALUES ('93', '37', '/info/categoryNews.do');
INSERT INTO fun_url VALUES ('94', '37', '/info/treeNewsType.do');
INSERT INTO fun_url VALUES ('95', '37', '/info/saveNews.do');
INSERT INTO fun_url VALUES ('96', '38', '/info/categoryNews.do');
INSERT INTO fun_url VALUES ('97', '38', '/info/treeNewsType.do');
INSERT INTO fun_url VALUES ('98', '38', '/info/multiDelNews.do');
INSERT INTO fun_url VALUES ('99', '40', '/info/multiDelNewsComment.do');
INSERT INTO fun_url VALUES ('100', '41', '/info/listNewsType.do');
INSERT INTO fun_url VALUES ('101', '42', '/info/listNewsType.do');
INSERT INTO fun_url VALUES ('102', '42', '/info/addNewsType.do');
INSERT INTO fun_url VALUES ('103', '43', '/info/listNewsType.do');
INSERT INTO fun_url VALUES ('104', '43', '/info/addNewsType.do');
INSERT INTO fun_url VALUES ('105', '43', '/info/detailNewsType.do');
INSERT INTO fun_url VALUES ('106', '43', '/info/sortNewsType.do');
INSERT INTO fun_url VALUES ('107', '44', '/info/listNewsType.do');
INSERT INTO fun_url VALUES ('108', '44', '/info/removeNewsType.do');
INSERT INTO fun_url VALUES ('109', '46', '/info/saveNotice.do');
INSERT INTO fun_url VALUES ('110', '47', '/info/saveNotice.do');
INSERT INTO fun_url VALUES ('111', '48', '/info/multiDelNotice.do');
INSERT INTO fun_url VALUES ('112', '49', '/personal/listHolidayRecord.do');
INSERT INTO fun_url VALUES ('113', '50', '/personal/listHolidayRecord.do');
INSERT INTO fun_url VALUES ('114', '50', '/personal/saveHolidayRecord.do');
INSERT INTO fun_url VALUES ('115', '51', '/personal/listHolidayRecord.do');
INSERT INTO fun_url VALUES ('116', '51', '/personal/getHolidayRecord.do');
INSERT INTO fun_url VALUES ('117', '51', '/personal/saveHolidayRecord.do');
INSERT INTO fun_url VALUES ('118', '52', '/personal/listHolidayRecord.do');
INSERT INTO fun_url VALUES ('119', '52', '/personal/multiDelHolidayRecord.do');
INSERT INTO fun_url VALUES ('120', '53', '/personal/listDutySection.do');
INSERT INTO fun_url VALUES ('121', '54', '/personal/listDutySection.do');
INSERT INTO fun_url VALUES ('122', '54', '/personal/saveDutySection.do');
INSERT INTO fun_url VALUES ('123', '55', '/personal/listDutySection.do');
INSERT INTO fun_url VALUES ('124', '55', '/personal/saveDutySection.do');
INSERT INTO fun_url VALUES ('125', '55', '/personal/getDutySection.do');
INSERT INTO fun_url VALUES ('126', '56', '/personal/listDutySection.do');
INSERT INTO fun_url VALUES ('127', '56', '/personal/multiDelDutySection.do');
INSERT INTO fun_url VALUES ('128', '57', '/personal/listDutySystem.do');
INSERT INTO fun_url VALUES ('129', '58', '/personal/listDutySystem.do');
INSERT INTO fun_url VALUES ('130', '58', '/personal/settingDutySystem.do');
INSERT INTO fun_url VALUES ('131', '58', '/personal/saveDutySystem.do');
INSERT INTO fun_url VALUES ('132', '59', '/personal/listDutySystem.do');
INSERT INTO fun_url VALUES ('133', '59', '/personal/getDutySystem.do');
INSERT INTO fun_url VALUES ('134', '59', '/personal/saveDutySystem.do');
INSERT INTO fun_url VALUES ('135', '60', '/personal/listDutySystem.do');
INSERT INTO fun_url VALUES ('136', '60', '/personal/multiDelDutySystem.do');
INSERT INTO fun_url VALUES ('137', '61', '/personal/listDuty.do');
INSERT INTO fun_url VALUES ('138', '62', '/personal/listDuty.do');
INSERT INTO fun_url VALUES ('139', '62', '/personal/saveDuty.do');
INSERT INTO fun_url VALUES ('140', '62', '/personal/comboDutySystem.do');
INSERT INTO fun_url VALUES ('141', '63', '/personal/listDuty.do');
INSERT INTO fun_url VALUES ('142', '63', '/personal/saveDuty.do');
INSERT INTO fun_url VALUES ('143', '63', '/personal/comboDutySystem.do');
INSERT INTO fun_url VALUES ('144', '63', '/personal/getDuty.do');
INSERT INTO fun_url VALUES ('145', '64', '/personal/listDuty.do');
INSERT INTO fun_url VALUES ('146', '64', '/personal/multiDelDuty.do');
INSERT INTO fun_url VALUES ('147', '65', '/personal/listDutyRegister.do');
INSERT INTO fun_url VALUES ('148', '66', '/personal/listDutyRegister.do');
INSERT INTO fun_url VALUES ('149', '66', '/personal/saveDutyRegister.do');
INSERT INTO fun_url VALUES ('150', '66', '/personal/comboDutySection.do');
INSERT INTO fun_url VALUES ('151', '67', '/personal/listDutyRegister.do');
INSERT INTO fun_url VALUES ('152', '67', '/personal/multiDelDutyRegister.do');
INSERT INTO fun_url VALUES ('153', '68', '/customer/listCustomer.do');
INSERT INTO fun_url VALUES ('154', '69', '/customer/listCustomer.do');
INSERT INTO fun_url VALUES ('155', '69', '/customer/saveCustomer.do');
INSERT INTO fun_url VALUES ('156', '69', '/customer/checkCustomer.do');
INSERT INTO fun_url VALUES ('157', '69', '/customer/numberCustomer.do');
INSERT INTO fun_url VALUES ('158', '69', '/system/listRegion.do');
INSERT INTO fun_url VALUES ('159', '70', '/customer/listCustomer.do');
INSERT INTO fun_url VALUES ('160', '70', '/customer/saveCustomer.do');
INSERT INTO fun_url VALUES ('161', '70', '/customer/checkCustomer.do');
INSERT INTO fun_url VALUES ('162', '70', '/customer/numberCustomer.do');
INSERT INTO fun_url VALUES ('163', '70', '/system/listRegion.do');
INSERT INTO fun_url VALUES ('164', '70', '/customer/getCustomer.do');
INSERT INTO fun_url VALUES ('165', '71', '/customer/listCustomer.do');
INSERT INTO fun_url VALUES ('166', '71', '/customer/multiDelCustomer.do');
INSERT INTO fun_url VALUES ('167', '72', '/customer/listCusLinkman.do');
INSERT INTO fun_url VALUES ('168', '73', '/customer/listCusLinkman.do');
INSERT INTO fun_url VALUES ('169', '73', '/customer/saveCusLinkman.do');
INSERT INTO fun_url VALUES ('170', '74', '/customer/listCusLinkman.do');
INSERT INTO fun_url VALUES ('171', '74', '/customer/saveCusLinkman.do');
INSERT INTO fun_url VALUES ('172', '74', '/customer/getCusLinkman.do');
INSERT INTO fun_url VALUES ('173', '75', '/customer/listCusLinkman.do');
INSERT INTO fun_url VALUES ('174', '75', '/customer/multiDelCusLinkman.do');
INSERT INTO fun_url VALUES ('175', '76', '/customer/listCusConnection.do');
INSERT INTO fun_url VALUES ('176', '77', '/customer/listCusConnection.do');
INSERT INTO fun_url VALUES ('177', '77', '/customer/saveCusConnection.do');
INSERT INTO fun_url VALUES ('178', '77', '/customer/findCusLinkman.do');
INSERT INTO fun_url VALUES ('179', '78', '/customer/listCusConnection.do');
INSERT INTO fun_url VALUES ('180', '78', '/customer/saveCusConnection.do');
INSERT INTO fun_url VALUES ('181', '78', '/customer/findCusLinkman.do');
INSERT INTO fun_url VALUES ('182', '78', '/customer/getCusConnection.do');
INSERT INTO fun_url VALUES ('183', '79', '/customer/listCusConnection.do');
INSERT INTO fun_url VALUES ('184', '79', '/customer/multiDelCusConnection.do');
INSERT INTO fun_url VALUES ('185', '80', '/customer/listProject.do');
INSERT INTO fun_url VALUES ('186', '81', '/customer/listProject.do');
INSERT INTO fun_url VALUES ('187', '81', '/customer/saveProject.do');
INSERT INTO fun_url VALUES ('188', '81', '/customer/numberProject.do');
INSERT INTO fun_url VALUES ('189', '81', '/customer/findCusLinkman.do');
INSERT INTO fun_url VALUES ('190', '82', '/customer/listProject.do');
INSERT INTO fun_url VALUES ('191', '82', '/customer/saveProject.do');
INSERT INTO fun_url VALUES ('192', '82', '/customer/numberProject.do');
INSERT INTO fun_url VALUES ('193', '82', '/customer/findCusLinkman.do');
INSERT INTO fun_url VALUES ('194', '82', '/customer/getProject.do');
INSERT INTO fun_url VALUES ('195', '83', '/customer/listProject.do');
INSERT INTO fun_url VALUES ('196', '83', '/customer/multiDelProject.do');
INSERT INTO fun_url VALUES ('197', '84', '/customer/listContract.do');
INSERT INTO fun_url VALUES ('198', '85', '/customer/listContract.do');
INSERT INTO fun_url VALUES ('199', '85', '/customer/saveContract.do');
INSERT INTO fun_url VALUES ('200', '85', '/customer/getProject.do');
INSERT INTO fun_url VALUES ('201', '85', '/customer/removeFileContract.do');
INSERT INTO fun_url VALUES ('202', '85', '/customer/listContractConfig.do');
INSERT INTO fun_url VALUES ('203', '85', '/customer/multiDelContractConfig.do');
INSERT INTO fun_url VALUES ('204', '86', '/customer/listContract.do');
INSERT INTO fun_url VALUES ('205', '86', '/customer/saveContract.do');
INSERT INTO fun_url VALUES ('206', '86', '/customer/getProject.do');
INSERT INTO fun_url VALUES ('207', '86', '/customer/removeFileContract.do');
INSERT INTO fun_url VALUES ('208', '86', '/customer/listContractConfig.do');
INSERT INTO fun_url VALUES ('209', '86', '/customer/multiDelContractConfig.do');
INSERT INTO fun_url VALUES ('210', '86', '/customer/getContract.do');
INSERT INTO fun_url VALUES ('211', '87', '/customer/listContract.do');
INSERT INTO fun_url VALUES ('212', '87', '/customer/multiDelContract.do');
INSERT INTO fun_url VALUES ('213', '88', '/customer/listProduct.do');
INSERT INTO fun_url VALUES ('214', '89', '/customer/saveProduct.do');
INSERT INTO fun_url VALUES ('215', '89', '/customer/listProvider.do');
INSERT INTO fun_url VALUES ('216', '90', '/customer/listProduct.do');
INSERT INTO fun_url VALUES ('217', '90', '/customer/getProduct.do');
INSERT INTO fun_url VALUES ('218', '90', '/customer/saveProduct.do');
INSERT INTO fun_url VALUES ('219', '91', '/customer/listProduct.do');
INSERT INTO fun_url VALUES ('220', '91', '/customer/multiDelProduct.do');
INSERT INTO fun_url VALUES ('221', '92', '/customer/listProvider.do');
INSERT INTO fun_url VALUES ('222', '93', '/customer/saveProvider.do');
INSERT INTO fun_url VALUES ('223', '94', '/customer/listProvider.do');
INSERT INTO fun_url VALUES ('224', '94', '/customer/getProvider.do');
INSERT INTO fun_url VALUES ('225', '94', '/customer/saveProvider.do');
INSERT INTO fun_url VALUES ('226', '95', '/customer/listProvider.do');
INSERT INTO fun_url VALUES ('227', '95', '/customer/multiDelProvider.do');
INSERT INTO fun_url VALUES ('228', '96', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('229', '96', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('230', '97', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('231', '97', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('232', '97', '/admin/multiDelOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('233', '97', '/admin/saveOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('234', '97', '/admin/getOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('235', '98', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('236', '98', '/admin/saveOfficeGoods.do');
INSERT INTO fun_url VALUES ('237', '98', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('238', '99', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('239', '99', '/admin/saveOfficeGoods.do');
INSERT INTO fun_url VALUES ('240', '99', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('241', '99', '/admin/getOfficeGoods.do');
INSERT INTO fun_url VALUES ('242', '100', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('243', '100', '/admin/multiDelOfficeGoods.do');
INSERT INTO fun_url VALUES ('244', '101', '/admin/listInStock.do');
INSERT INTO fun_url VALUES ('245', '102', '/admin/listInStock.do');
INSERT INTO fun_url VALUES ('246', '102', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('247', '102', '/admin/saveInStock.do');
INSERT INTO fun_url VALUES ('248', '102', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('249', '103', '/admin/listInStock.do');
INSERT INTO fun_url VALUES ('250', '103', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('251', '103', '/admin/saveInStock.do');
INSERT INTO fun_url VALUES ('252', '103', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('253', '103', '/admin/getInStock.do');
INSERT INTO fun_url VALUES ('254', '104', '/admin/listInStock.do');
INSERT INTO fun_url VALUES ('255', '104', '/admin/multiDelInStock.do');
INSERT INTO fun_url VALUES ('256', '105', '/admin/listGoodsApply.do');
INSERT INTO fun_url VALUES ('257', '106', '/admin/listGoodsApply.do');
INSERT INTO fun_url VALUES ('258', '106', '/admin/saveGoodsApply.do');
INSERT INTO fun_url VALUES ('259', '106', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('260', '106', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('261', '107', '/admin/listGoodsApply.do');
INSERT INTO fun_url VALUES ('262', '107', '/admin/saveGoodsApply.do');
INSERT INTO fun_url VALUES ('263', '107', '/admin/listOfficeGoods.do');
INSERT INTO fun_url VALUES ('264', '107', '/admin/treeOfficeGoodsType.do');
INSERT INTO fun_url VALUES ('265', '107', '/admin/getGoodsApply.do');
INSERT INTO fun_url VALUES ('266', '108', '/admin/listGoodsApply.do');
INSERT INTO fun_url VALUES ('267', '108', '/admin/multiDelGoodsApply.do');
INSERT INTO fun_url VALUES ('268', '109', '/admin/listCar.do');
INSERT INTO fun_url VALUES ('269', '110', '/admin/listCar.do');
INSERT INTO fun_url VALUES ('270', '110', '/admin/saveCar.do');
INSERT INTO fun_url VALUES ('271', '111', '/admin/listCar.do');
INSERT INTO fun_url VALUES ('272', '111', '/admin/saveCar.do');
INSERT INTO fun_url VALUES ('273', '111', '/admin/getCar.do');
INSERT INTO fun_url VALUES ('274', '112', '/admin/listCar.do');
INSERT INTO fun_url VALUES ('275', '112', '/admin/multiDelCar.do');
INSERT INTO fun_url VALUES ('276', '113', '/admin/listCartRepair.do');
INSERT INTO fun_url VALUES ('277', '114', '/admin/listCartRepair.do');
INSERT INTO fun_url VALUES ('278', '114', '/admin/saveCartRepair.do');
INSERT INTO fun_url VALUES ('279', '115', '/admin/listCartRepair.do');
INSERT INTO fun_url VALUES ('280', '115', '/admin/saveCartRepair.do');
INSERT INTO fun_url VALUES ('281', '115', '/admin/getCartRepair.do');
INSERT INTO fun_url VALUES ('282', '116', '/admin/listCartRepair.do');
INSERT INTO fun_url VALUES ('283', '116', '/admin/multiDelCartRepair.do');
INSERT INTO fun_url VALUES ('284', '117', '/admin/listCarApply.do');
INSERT INTO fun_url VALUES ('285', '118', '/admin/listCarApply.do');
INSERT INTO fun_url VALUES ('286', '118', '/admin/saveCarApply.do');
INSERT INTO fun_url VALUES ('287', '119', '/admin/listCarApply.do');
INSERT INTO fun_url VALUES ('288', '119', '/admin/saveCarApply.do');
INSERT INTO fun_url VALUES ('289', '119', '/admin/getCarApply.do');
INSERT INTO fun_url VALUES ('290', '120', '/admin/listCarApply.do');
INSERT INTO fun_url VALUES ('291', '120', '/admin/multiDelCarApply.do');
INSERT INTO fun_url VALUES ('292', '121', '/admin/listDepreType.do');
INSERT INTO fun_url VALUES ('293', '122', '/admin/listDepreType.do');
INSERT INTO fun_url VALUES ('294', '122', '/admin/saveDepreType.do');
INSERT INTO fun_url VALUES ('295', '123', '/admin/listDepreType.do');
INSERT INTO fun_url VALUES ('296', '123', '/admin/saveDepreType.do');
INSERT INTO fun_url VALUES ('297', '123', '/admin/getDepreType.do');
INSERT INTO fun_url VALUES ('298', '124', '/admin/listDepreType.do');
INSERT INTO fun_url VALUES ('299', '124', '/admin/multiDelDepreType.do');
INSERT INTO fun_url VALUES ('300', '125', '/admin/listFixedAssets.do');
INSERT INTO fun_url VALUES ('301', '125', '/admin/treeAssetsType.do');
INSERT INTO fun_url VALUES ('302', '126', '/admin/treeAssetsType.do');
INSERT INTO fun_url VALUES ('303', '126', '/admin/multiDelAssetsType.do');
INSERT INTO fun_url VALUES ('304', '126', '/admin/saveAssetsType.do');
INSERT INTO fun_url VALUES ('305', '126', '/admin/getAssetsType.do');
INSERT INTO fun_url VALUES ('306', '127', '/admin/listFixedAssets.do');
INSERT INTO fun_url VALUES ('307', '127', '/system/selectDepartment.do');
INSERT INTO fun_url VALUES ('308', '127', '/admin/treeAssetsType.do');
INSERT INTO fun_url VALUES ('309', '127', '/admin/saveFixedAssets.do');
INSERT INTO fun_url VALUES ('310', '127', '/admin/comboxAssetsType.do');
INSERT INTO fun_url VALUES ('311', '127', '/admin/comboxDepreType.do');
INSERT INTO fun_url VALUES ('312', '128', '/admin/listFixedAssets.do');
INSERT INTO fun_url VALUES ('313', '128', '/admin/treeAssetsType.do');
INSERT INTO fun_url VALUES ('314', '128', '/system/selectDepartment.do');
INSERT INTO fun_url VALUES ('315', '128', '/admin/saveFixedAssets.do');
INSERT INTO fun_url VALUES ('316', '128', '/admin/comboxAssetsType.do');
INSERT INTO fun_url VALUES ('317', '128', '/admin/comboxDepreType.do');
INSERT INTO fun_url VALUES ('318', '128', '/admin/getFixedAssets.do');
INSERT INTO fun_url VALUES ('319', '129', '/admin/listFixedAssets.do');
INSERT INTO fun_url VALUES ('320', '129', '/admin/treeAssetsType.do');
INSERT INTO fun_url VALUES ('321', '129', '/admin/multiDelFixedAssets.do');
INSERT INTO fun_url VALUES ('322', '130', '/admin/depreciateDepreRecord.do');
INSERT INTO fun_url VALUES ('323', '130', '/admin/workDepreRecord.do');
INSERT INTO fun_url VALUES ('324', '131', '/admin/listDepreRecord.do');
INSERT INTO fun_url VALUES ('325', '132', '/admin/listBookType.do');
INSERT INTO fun_url VALUES ('326', '133', '/admin/listBookType.do');
INSERT INTO fun_url VALUES ('327', '133', '/admin/saveBookType.do');
INSERT INTO fun_url VALUES ('328', '134', '/admin/listBookType.do');
INSERT INTO fun_url VALUES ('329', '134', '/admin/saveBookType.do');
INSERT INTO fun_url VALUES ('330', '134', '/admin/getBookType.do');
INSERT INTO fun_url VALUES ('331', '135', '/admin/listBookType.do');
INSERT INTO fun_url VALUES ('332', '135', '/admin/multiDelBookType.do');
INSERT INTO fun_url VALUES ('333', '136', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('334', '136', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('335', '137', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('336', '137', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('337', '137', '/admin/saveBook.do');
INSERT INTO fun_url VALUES ('338', '137', '/admin/treeBook.do');
INSERT INTO fun_url VALUES ('339', '138', '/admin/listBookType.do');
INSERT INTO fun_url VALUES ('340', '138', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('341', '138', '/admin/saveBookType.do');
INSERT INTO fun_url VALUES ('342', '138', '/admin/treeBook.do');
INSERT INTO fun_url VALUES ('343', '138', '/admin/getBook.do');
INSERT INTO fun_url VALUES ('344', '139', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('345', '139', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('346', '139', '/admin/multiDelBook.do');
INSERT INTO fun_url VALUES ('347', '140', '/admin/listBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('348', '141', '/admin/listBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('349', '141', '/admin/saveBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('350', '141', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('351', '141', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('352', '141', '/admin/getBook.do');
INSERT INTO fun_url VALUES ('353', '141', '/admin/getBorrowSnBookSn.do');
INSERT INTO fun_url VALUES ('354', '142', '/admin/listBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('355', '142', '/admin/saveBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('356', '142', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('357', '142', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('358', '142', '/admin/getBook.do');
INSERT INTO fun_url VALUES ('359', '142', '/admin/getBorrowSnBookSn.do');
INSERT INTO fun_url VALUES ('360', '142', '/admin/getBookBorRet.do');
INSERT INTO fun_url VALUES ('361', '143', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('362', '143', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('363', '143', '/admin/getReturnSnBookSn.do');
INSERT INTO fun_url VALUES ('364', '143', '/admin/getBorRetTimeBookBorRet.do');
INSERT INTO fun_url VALUES ('365', '143', '/admin/listReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('366', '143', '/admin/saveReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('367', '143', '/admin/getBookBorRet.do');
INSERT INTO fun_url VALUES ('368', '144', '/admin/listBorrowBookBorRet.do');
INSERT INTO fun_url VALUES ('369', '144', '/admin/multiDelBookBorRet.do');
INSERT INTO fun_url VALUES ('370', '145', '/admin/listReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('371', '146', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('372', '146', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('373', '146', '/admin/getReturnSnBookSn.do');
INSERT INTO fun_url VALUES ('374', '146', '/admin/getBorRetTimeBookBorRet.do');
INSERT INTO fun_url VALUES ('375', '146', '/admin/listReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('376', '146', '/admin/saveReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('377', '147', '/admin/listBook.do');
INSERT INTO fun_url VALUES ('378', '147', '/admin/treeBookType.do');
INSERT INTO fun_url VALUES ('379', '147', '/admin/getReturnSnBookSn.do');
INSERT INTO fun_url VALUES ('380', '147', '/admin/getBorRetTimeBookBorRet.do');
INSERT INTO fun_url VALUES ('381', '147', '/admin/listReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('382', '147', '/admin/saveReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('383', '147', '/admin/getBookBorRet.do');
INSERT INTO fun_url VALUES ('384', '148', '/admin/listReturnBookBorRet.do');
INSERT INTO fun_url VALUES ('385', '148', '/admin/multiDelBookBorRet.do');

-- ----------------------------
-- Table structure for `goods_apply`
-- ----------------------------
DROP TABLE IF EXISTS `goods_apply`;
CREATE TABLE `goods_apply` (
  `applyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `goodsId` bigint(20) NOT NULL,
  `applyDate` datetime NOT NULL,
  `applyNo` varchar(128) NOT NULL COMMENT '申请号,按系统时间产生，如GA20091002-0001',
  `useCounts` int(11) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `proposer` varchar(32) NOT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `approvalStatus` smallint(6) NOT NULL COMMENT '审批状态\r\n            1=通过审批\r\n            0=未审批\r\n            ',
  PRIMARY KEY (`applyId`),
  KEY `FK_GA_R_OG` (`goodsId`) USING BTREE,
  CONSTRAINT `goods_apply_ibfk_1` FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品出库';

-- ----------------------------
-- Records of goods_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `hire_issue`
-- ----------------------------
DROP TABLE IF EXISTS `hire_issue`;
CREATE TABLE `hire_issue` (
  `hireId` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '招聘信息标题',
  `startDate` datetime NOT NULL COMMENT '开始时间',
  `endDate` datetime NOT NULL COMMENT '结束时间',
  `hireCount` int(11) NOT NULL COMMENT '招聘人数',
  `jobName` varchar(128) NOT NULL COMMENT '职位名称',
  `jobCondition` varchar(1024) DEFAULT NULL COMMENT '招聘要求(条件)',
  `regFullname` varchar(128) NOT NULL COMMENT '登记人姓名',
  `regDate` datetime NOT NULL COMMENT '登记时间',
  `modifyFullname` varchar(32) DEFAULT NULL COMMENT '变更人姓名',
  `modifyDate` datetime DEFAULT NULL COMMENT '变更时间',
  `checkFullname` varchar(32) DEFAULT NULL COMMENT '审核人姓名',
  `checkOpinion` varchar(512) DEFAULT NULL COMMENT '审核意见',
  `checkDate` datetime DEFAULT NULL COMMENT '审批时间',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=通过审核\r\n            0=未审核\r\n            2=审核不通过',
  `memo` varchar(1024) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`hireId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='招聘发布';

-- ----------------------------
-- Records of hire_issue
-- ----------------------------

-- ----------------------------
-- Table structure for `holiday_record`
-- ----------------------------
DROP TABLE IF EXISTS `holiday_record`;
CREATE TABLE `holiday_record` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `descp` varchar(512) DEFAULT NULL COMMENT '假期描述',
  `fullname` varchar(32) DEFAULT NULL COMMENT '用户名',
  `userId` bigint(20) DEFAULT NULL COMMENT '所属用户\r\n            若为某员工的假期，则为员工自己的id',
  `isAll` smallint(6) NOT NULL COMMENT '是否为全公司假期\r\n            1=是\r\n            0=否',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='放假记录';

-- ----------------------------
-- Records of holiday_record
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_bg_budget`
-- ----------------------------
DROP TABLE IF EXISTS `hr_bg_budget`;
CREATE TABLE `hr_bg_budget` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `belongDept` bigint(20) DEFAULT NULL,
  `beginDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `budgetType` int(11) DEFAULT NULL COMMENT '年度1 季度2 月度3',
  `belongBudget` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算表';

-- ----------------------------
-- Records of hr_bg_budget
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_bg_budgetitem`
-- ----------------------------
DROP TABLE IF EXISTS `hr_bg_budgetitem`;
CREATE TABLE `hr_bg_budgetitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bgId` bigint(20) DEFAULT NULL,
  `code` varchar(70) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `keyword` varchar(50) DEFAULT NULL,
  `budgetvalue` double DEFAULT NULL,
  `threshold` double DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `deleteFlag` int(11) DEFAULT NULL COMMENT '删除标记',
  `belongItem` bigint(20) DEFAULT NULL,
  `isDefault` smallint(6) NOT NULL DEFAULT '0' COMMENT '0为否 1为是',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_18` (`parentId`) USING BTREE,
  KEY `FK_Reference_19` (`bgId`) USING BTREE,
  KEY `FKF6A015BDBF15151` (`bgId`) USING BTREE,
  KEY `FKF6A015BDA601B729` (`parentId`) USING BTREE,
  CONSTRAINT `hr_bg_budgetitem_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `hr_bg_budgetitem` (`id`),
  CONSTRAINT `hr_bg_budgetitem_ibfk_2` FOREIGN KEY (`bgId`) REFERENCES `hr_bg_budget` (`id`),
  CONSTRAINT `hr_bg_budgetitem_ibfk_3` FOREIGN KEY (`parentId`) REFERENCES `hr_bg_budgetitem` (`id`),
  CONSTRAINT `hr_bg_budgetitem_ibfk_4` FOREIGN KEY (`bgId`) REFERENCES `hr_bg_budget` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算详细项目';

-- ----------------------------
-- Records of hr_bg_budgetitem
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_bg_realexecution`
-- ----------------------------
DROP TABLE IF EXISTS `hr_bg_realexecution`;
CREATE TABLE `hr_bg_realexecution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bgId` bigint(20) DEFAULT NULL COMMENT '预算ID',
  `bgiId` bigint(20) DEFAULT NULL COMMENT '预算科目ID',
  `realValue` double DEFAULT NULL,
  `month` int(11) DEFAULT NULL,
  `inputDate` datetime DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `deleteFlag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_20` (`bgId`) USING BTREE,
  KEY `FK_Reference_21` (`bgiId`) USING BTREE,
  KEY `FK47220F35BF15151` (`bgId`) USING BTREE,
  KEY `FK47220F35658B8CC3` (`bgiId`) USING BTREE,
  CONSTRAINT `hr_bg_realexecution_ibfk_1` FOREIGN KEY (`bgiId`) REFERENCES `hr_bg_budgetitem` (`id`),
  CONSTRAINT `hr_bg_realexecution_ibfk_2` FOREIGN KEY (`bgId`) REFERENCES `hr_bg_budget` (`id`),
  CONSTRAINT `hr_bg_realexecution_ibfk_3` FOREIGN KEY (`bgId`) REFERENCES `hr_bg_budget` (`id`),
  CONSTRAINT `hr_bg_realexecution_ibfk_4` FOREIGN KEY (`bgiId`) REFERENCES `hr_bg_budgetitem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='预算实际执行情况记录';

-- ----------------------------
-- Records of hr_bg_realexecution
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_exportsalary`
-- ----------------------------
DROP TABLE IF EXISTS `hr_exportsalary`;
CREATE TABLE `hr_exportsalary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `descr` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_exportsalary
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_exportsalaryitemorder`
-- ----------------------------
DROP TABLE IF EXISTS `hr_exportsalaryitemorder`;
CREATE TABLE `hr_exportsalaryitemorder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exportName` varchar(255) DEFAULT NULL,
  `fromTable` varchar(255) DEFAULT NULL,
  `fromTableType` bigint(20) DEFAULT NULL,
  `orderCol` int(11) DEFAULT NULL,
  `exportSalId` bigint(20) DEFAULT NULL,
  `isDefaut` int(11) DEFAULT NULL,
  `fromTableName` varchar(255) DEFAULT NULL,
  `fromTableTypeName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_exportsalaryitemorder
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_acreached`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_acreached`;
CREATE TABLE `hr_pa_acreached` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `result` double DEFAULT NULL COMMENT '考核标准达成结果',
  `inputDate` datetime DEFAULT NULL,
  `inputPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '模板ID',
  `deptId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_5` (`acId`) USING BTREE,
  KEY `FK_Reference_8` (`category`) USING BTREE,
  CONSTRAINT `hr_pa_acreached_ibfk_1` FOREIGN KEY (`acId`) REFERENCES `hr_pa_assessmentcriteria` (`id`),
  CONSTRAINT `hr_pa_acreached_ibfk_2` FOREIGN KEY (`category`) REFERENCES `hr_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标达成数据录入\r\n\r\n1. 提供销售达成导入结果记录\r\n2. 提供其他定量结果记录';

-- ----------------------------
-- Records of hr_pa_acreached
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_acreached_hist`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_acreached_hist`;
CREATE TABLE `hr_pa_acreached_hist` (
  `id` bigint(20) NOT NULL,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `result` double DEFAULT NULL COMMENT '考核标准达成结果',
  `inputDate` datetime DEFAULT NULL,
  `inputPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '模板ID',
  `deptId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标达成数据录入\r\n1. 提供销售达成导入结果记录\r\n2. 提供其他定量结果记录';

-- ----------------------------
-- Records of hr_pa_acreached_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_assessmentcriteria`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_assessmentcriteria`;
CREATE TABLE `hr_pa_assessmentcriteria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acName` varchar(100) DEFAULT NULL COMMENT '考核标准名称',
  `acKey` varchar(50) DEFAULT NULL COMMENT '考核标准关键字',
  `acDesc` varchar(200) DEFAULT NULL COMMENT '考核标准描述',
  `isSalesAC` int(11) DEFAULT NULL COMMENT '是否销售类定量考核标准',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态：\r\n            0：草稿\r\n            1：发布\r\n            2：删除',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `fromAc` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核标准表，记录定量考核的考核标准，以提供后期财务计算';

-- ----------------------------
-- Records of hr_pa_assessmentcriteria
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_assessmenttasksassigned`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_assessmenttasksassigned`;
CREATE TABLE `hr_pa_assessmenttasksassigned` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准关联',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `target` double DEFAULT NULL COMMENT '目标值',
  `publishDate` datetime DEFAULT NULL,
  `publishPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '模板ID',
  `deptId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_4` (`acId`) USING BTREE,
  KEY `FK_Reference_7` (`category`) USING BTREE,
  CONSTRAINT `hr_pa_assessmenttasksassigned_ibfk_1` FOREIGN KEY (`acId`) REFERENCES `hr_pa_assessmentcriteria` (`id`),
  CONSTRAINT `hr_pa_assessmenttasksassigned_ibfk_2` FOREIGN KEY (`category`) REFERENCES `hr_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核任务额度下达\r\n根据考核标准，下达考核标准对应的完成目标，这个目标值用来计算最后得分。';

-- ----------------------------
-- Records of hr_pa_assessmenttasksassigned
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_assessmenttasksassigned_hist`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_assessmenttasksassigned_hist`;
CREATE TABLE `hr_pa_assessmenttasksassigned_hist` (
  `id` bigint(20) NOT NULL,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准关联',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `target` double DEFAULT NULL COMMENT '目标值',
  `publishDate` datetime DEFAULT NULL,
  `publishPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '模板ID',
  `deptId` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核任务额度下达\r\n\r\n根据考核标准，下达考核标准对应的完成目标，这个目标值用来计算最后得分';

-- ----------------------------
-- Records of hr_pa_assessmenttasksassigned_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_authorizepbc`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_authorizepbc`;
CREATE TABLE `hr_pa_authorizepbc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '被授权人',
  `authDate` datetime DEFAULT NULL,
  `authPerson` bigint(20) DEFAULT NULL,
  `pbcId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_15` (`pbcId`) USING BTREE,
  CONSTRAINT `hr_pa_authorizepbc_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多人打分授权记录表';

-- ----------------------------
-- Records of hr_pa_authorizepbc
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_authpbcitem`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_authpbcitem`;
CREATE TABLE `hr_pa_authpbcitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apbcId` bigint(20) DEFAULT NULL COMMENT '被授权PBC',
  `akpiItem2uId` bigint(20) DEFAULT NULL,
  `result` double DEFAULT NULL COMMENT '考核得分',
  `weight` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_16` (`apbcId`) USING BTREE,
  KEY `FK_Reference_17` (`akpiItem2uId`) USING BTREE,
  CONSTRAINT `hr_pa_authpbcitem_ibfk_1` FOREIGN KEY (`apbcId`) REFERENCES `hr_pa_authorizepbc` (`id`),
  CONSTRAINT `hr_pa_authpbcitem_ibfk_2` FOREIGN KEY (`akpiItem2uId`) REFERENCES `hr_pa_kpiitem2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='由被授权PBC拷贝或来的定性考核项目';

-- ----------------------------
-- Records of hr_pa_authpbcitem
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_category`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_category`;
CREATE TABLE `hr_pa_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_6` (`parentId`) USING BTREE,
  CONSTRAINT `hr_pa_category_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `hr_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品类表\r\n来源于单品系统';

-- ----------------------------
-- Records of hr_pa_category
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of hr_pa_datadictionary
-- ----------------------------
INSERT INTO hr_pa_datadictionary VALUES ('1', '数据字典基础字段', '1');
INSERT INTO hr_pa_datadictionary VALUES ('2', '考核项目类型', '1');
INSERT INTO hr_pa_datadictionary VALUES ('3', '考核频度', '1');
INSERT INTO hr_pa_datadictionary VALUES ('4', '考核方式', '1');
INSERT INTO hr_pa_datadictionary VALUES ('5', '业务目标', '2');
INSERT INTO hr_pa_datadictionary VALUES ('6', '员工管理目标', '2');
INSERT INTO hr_pa_datadictionary VALUES ('7', '月度', '3');
INSERT INTO hr_pa_datadictionary VALUES ('8', '无效字段', null);
INSERT INTO hr_pa_datadictionary VALUES ('9', '季度', '3');
INSERT INTO hr_pa_datadictionary VALUES ('10', '半年', '3');
INSERT INTO hr_pa_datadictionary VALUES ('11', '年度', '3');
INSERT INTO hr_pa_datadictionary VALUES ('12', '定性考核', '4');
INSERT INTO hr_pa_datadictionary VALUES ('13', '定量考核', '4');
INSERT INTO hr_pa_datadictionary VALUES ('14', '个人发展目标', '2');

-- ----------------------------
-- Table structure for `hr_pa_kpiitem`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpiitem`;
CREATE TABLE `hr_pa_kpiitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_10` (`pbcId`) USING BTREE,
  KEY `FK_Reference_9` (`piId`) USING BTREE,
  KEY `FKF267ABE97779B1D` (`piId`) USING BTREE,
  KEY `FKF267ABEDBB50C80` (`pbcId`) USING BTREE,
  CONSTRAINT `hr_pa_kpiitem_ibfk_1` FOREIGN KEY (`piId`) REFERENCES `hr_pa_performanceindex` (`id`),
  CONSTRAINT `hr_pa_kpiitem_ibfk_2` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc` (`id`),
  CONSTRAINT `hr_pa_kpiitem_ibfk_3` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc` (`id`),
  CONSTRAINT `hr_pa_kpiitem_ibfk_4` FOREIGN KEY (`piId`) REFERENCES `hr_pa_performanceindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='KPI考核模板包含项目\r\n该表格中，考核结果只记录最终得分，评分过程记录于达成表';

-- ----------------------------
-- Records of hr_pa_kpiitem
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpiitem2user`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpiitem2user`;
CREATE TABLE `hr_pa_kpiitem2user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_31` (`pbcId`) USING BTREE,
  KEY `FK_Reference_32` (`piId`) USING BTREE,
  KEY `FKEF060EFF903CB655` (`pbcId`) USING BTREE,
  CONSTRAINT `hr_pa_kpiitem2user_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc2user` (`id`),
  CONSTRAINT `hr_pa_kpiitem2user_ibfk_2` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of hr_pa_kpiitem2user
-- ----------------------------

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
  `coefficient` double DEFAULT NULL COMMENT '绩效系数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of hr_pa_kpiitem2usercmp
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpiitem_hist`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpiitem_hist`;
CREATE TABLE `hr_pa_kpiitem_hist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_33` (`pbcId`) USING BTREE,
  CONSTRAINT `hr_pa_kpiitem_hist_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `hr_pa_kpipbc_hist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='KPI考核模板包含项目\r\n该表格中，考核结果只记录最终得分，评分过程记录于达成表';

-- ----------------------------
-- Records of hr_pa_kpiitem_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc`;
CREATE TABLE `hr_pa_kpipbc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcName` varchar(150) DEFAULT NULL COMMENT '模板名称',
  `belongDept` bigint(20) DEFAULT NULL COMMENT '隶属部门',
  `belongPost` bigint(20) DEFAULT NULL COMMENT '隶属岗位',
  `frequency` bigint(20) DEFAULT NULL COMMENT '考核频度',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` int(11) DEFAULT NULL COMMENT '总分，如果有小数位数则舍去',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `fromPbc` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FKADE88FA661E2FA83` (`frequency`) USING BTREE,
  CONSTRAINT `hr_pa_kpipbc_ibfk_1` FOREIGN KEY (`frequency`) REFERENCES `hr_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核模板管理\r\n';

-- ----------------------------
-- Records of hr_pa_kpipbc
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc2user`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc2user`;
CREATE TABLE `hr_pa_kpipbc2user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FKE654CB1761E2FA83` (`frequency`) USING BTREE,
  CONSTRAINT `hr_pa_kpipbc2user_ibfk_1` FOREIGN KEY (`frequency`) REFERENCES `hr_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of hr_pa_kpipbc2user
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc2useraudithis`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc2useraudithis`;
CREATE TABLE `hr_pa_kpipbc2useraudithis` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kpipbc2user` bigint(20) NOT NULL,
  `checkUser` bigint(20) DEFAULT NULL COMMENT '审批人',
  `checkTime` datetime DEFAULT NULL COMMENT '审批时间',
  `checkStatus` int(11) DEFAULT NULL COMMENT '审批结果0拒绝1同意',
  `checkRemark` varchar(200) DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_36` (`kpipbc2user`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC审批历史表';

-- ----------------------------
-- Records of hr_pa_kpipbc2useraudithis
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
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  KEY `FKE57F4ACF61E2FA83` (`frequency`) USING BTREE,
  CONSTRAINT `hr_pa_kpipbc2usercmp_ibfk_1` FOREIGN KEY (`frequency`) REFERENCES `hr_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of hr_pa_kpipbc2usercmp
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_kpipbc_hist`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_kpipbc_hist`;
CREATE TABLE `hr_pa_kpipbc_hist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcName` varchar(150) DEFAULT NULL COMMENT '模板名称',
  `belongDept` bigint(20) DEFAULT NULL COMMENT '隶属部门',
  `belongPost` bigint(20) DEFAULT NULL COMMENT '隶属岗位',
  `frequency` bigint(20) DEFAULT NULL COMMENT '考核频度',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` int(11) DEFAULT NULL COMMENT '总分，如果有小数位数则舍去',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `fromPbc` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核模板管理\r\n';

-- ----------------------------
-- Records of hr_pa_kpipbc_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_performanceindex`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_performanceindex`;
CREATE TABLE `hr_pa_performanceindex` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paName` varchar(150) DEFAULT NULL COMMENT '考核指标名称',
  `paType` bigint(20) DEFAULT NULL COMMENT '考核指标类型\r\n            1.销售类--定量\r\n            2.非销售类--定量',
  `paFrequency` bigint(20) DEFAULT NULL COMMENT '考核指标频度',
  `paMode` bigint(20) DEFAULT NULL COMMENT '考核方式：\r\n            1.定量考核--销售类\r\n            2.定性考核--职能类',
  `paIsOnlyNegative` int(11) DEFAULT NULL COMMENT '是否唯一否决条件\r\n            1：是\r\n            0：否',
  `paDesc` varchar(500) DEFAULT NULL COMMENT '考核指标描述',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `baseScore` double DEFAULT NULL,
  `finalScore` double DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `fromPi` bigint(20) DEFAULT NULL,
  `finalCoefficient` double(20,0) DEFAULT NULL,
  `belongDept` bigint(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_13` (`paMode`) USING BTREE,
  KEY `FK_Reference_14` (`paType`) USING BTREE,
  KEY `FKFBE53C7B2ABBB1D2` (`paType`) USING BTREE,
  KEY `FKFBE53C7B15C50BB2` (`paFrequency`) USING BTREE,
  KEY `FKFBE53C7B2AB85C3B` (`paMode`) USING BTREE,
  CONSTRAINT `hr_pa_performanceindex_ibfk_1` FOREIGN KEY (`paFrequency`) REFERENCES `hr_pa_datadictionary` (`id`),
  CONSTRAINT `hr_pa_performanceindex_ibfk_2` FOREIGN KEY (`paMode`) REFERENCES `hr_pa_datadictionary` (`id`),
  CONSTRAINT `hr_pa_performanceindex_ibfk_3` FOREIGN KEY (`paType`) REFERENCES `hr_pa_datadictionary` (`id`),
  CONSTRAINT `hr_pa_performanceindex_ibfk_4` FOREIGN KEY (`paMode`) REFERENCES `hr_pa_datadictionary` (`id`),
  CONSTRAINT `hr_pa_performanceindex_ibfk_5` FOREIGN KEY (`paType`) REFERENCES `hr_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核指标表';

-- ----------------------------
-- Records of hr_pa_performanceindex
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_performanceindexscore`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_performanceindexscore`;
CREATE TABLE `hr_pa_performanceindexscore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标名称',
  `pisScore` decimal(2,1) DEFAULT NULL,
  `pisType` bigint(20) DEFAULT NULL COMMENT '考核得分类型\r\n            该类型来源于考核指标，如果考核指标是定性，则该类型也是定性，如果考核指标是定量则该类型也是定量.\r\n            \r\n            如果是定量指标，则该得分需关联定量考核标准表并计算公式',
  `pisDesc` varchar(500) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1` (`piId`) USING BTREE,
  KEY `FK_Reference_12` (`pisType`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效指标得分';

-- ----------------------------
-- Records of hr_pa_performanceindexscore
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_pa_pisrule`
-- ----------------------------
DROP TABLE IF EXISTS `hr_pa_pisrule`;
CREATE TABLE `hr_pa_pisrule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pisId` bigint(20) DEFAULT NULL COMMENT '对应的考核得分',
  `pisAC` bigint(20) DEFAULT NULL COMMENT '评分标准，引用评分标准表',
  `formula` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_2` (`pisId`) USING BTREE,
  KEY `FK_Reference_3` (`pisAC`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_pa_pisrule
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_post_apply`
-- ----------------------------
DROP TABLE IF EXISTS `hr_post_apply`;
CREATE TABLE `hr_post_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applyUser` bigint(20) DEFAULT NULL COMMENT '转正申请人',
  `gender` varchar(32) DEFAULT NULL COMMENT '申请人性别',
  `age` int(11) DEFAULT NULL COMMENT '申请人年龄',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `deptName` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `postId` bigint(20) DEFAULT NULL COMMENT '职位ID',
  `postName` varchar(100) DEFAULT NULL COMMENT '职位名称',
  `accessionTime` datetime DEFAULT NULL COMMENT '入职日期',
  `proSummary` varchar(1000) DEFAULT NULL COMMENT '员工试用期总结',
  `postManagerId` bigint(20) DEFAULT NULL COMMENT '直属上级ID',
  `postManagerName` varchar(100) DEFAULT NULL COMMENT '直属上级姓名',
  `postManagerAuditDate` datetime DEFAULT NULL COMMENT '直属上级确认日期',
  `userManagerAuditDate` datetime DEFAULT NULL COMMENT '本人确认日期',
  `publishStatus` int(11) DEFAULT NULL COMMENT '状态：\r\n            0 草稿\r\n            1 审核中\r\n            2 退回\r\n            3 已确认',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='转正申请表';

-- ----------------------------
-- Records of hr_post_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_post_assessment`
-- ----------------------------
DROP TABLE IF EXISTS `hr_post_assessment`;
CREATE TABLE `hr_post_assessment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applyId` bigint(20) DEFAULT NULL,
  `actualReportDate` datetime DEFAULT NULL COMMENT '实际报到日期',
  `applyPostDate` datetime DEFAULT NULL COMMENT '拟定转正日期',
  `actualPostDate` datetime DEFAULT NULL COMMENT '实际转正日期',
  `proKnowledge` varchar(100) DEFAULT NULL COMMENT '专业知识',
  `commEffect` varchar(100) DEFAULT NULL COMMENT '沟通影响',
  `solveAbility` varchar(100) DEFAULT NULL COMMENT '问题解决能力',
  `difficultyManage` varchar(100) DEFAULT NULL COMMENT '工作难度管理幅度',
  `businessFieldEffect` varchar(100) DEFAULT NULL COMMENT '业务领域影响',
  `ratingResult` varchar(100) DEFAULT NULL COMMENT '评级结果',
  `standardPostId` bigint(20) DEFAULT NULL COMMENT '被评估者标准岗位ID',
  `standardPostName` varchar(100) DEFAULT NULL COMMENT '被评估者标准岗位名称',
  `postBand` varchar(100) DEFAULT NULL COMMENT '被评估者岗位层级（Band）',
  `postGrade` varchar(100) DEFAULT NULL COMMENT '被评估者岗位层级（档）',
  `newSalaryLevelId` varchar(100) DEFAULT NULL COMMENT '被评估者薪资标准ID',
  `newSalaryLevelName` varchar(100) DEFAULT NULL COMMENT '被评估者薪资标准名称',
  `oldSalaryLevelId` bigint(20) DEFAULT NULL COMMENT '转正前薪酬级别ID',
  `oldSalaryLevelName` varchar(100) DEFAULT NULL COMMENT '转正前薪酬级别名称',
  `oldSalary` decimal(10,0) DEFAULT NULL COMMENT '转正前薪酬金额',
  `newFixedSalary` decimal(10,0) DEFAULT NULL COMMENT '转正后固定工资金额',
  `newFloatSalary` decimal(10,0) DEFAULT NULL COMMENT '转正后浮动工资金额',
  `yearEndBonusCoefficient` decimal(10,0) DEFAULT NULL COMMENT '年终绩效基数金额',
  `totalYearSalary` decimal(10,0) DEFAULT NULL COMMENT '年度总薪酬金额',
  `proPerformance` varchar(500) DEFAULT NULL COMMENT '员工试用期间工作表现评价',
  `postManagerId` bigint(20) DEFAULT NULL COMMENT '直属经理ID',
  `postManagerName` varchar(100) DEFAULT NULL COMMENT '直属经理姓名',
  `postManagerAuditDate` datetime DEFAULT NULL COMMENT '直属经理确认日期',
  `postOpinion` varchar(500) DEFAULT NULL COMMENT '本部门/门店意见',
  `deptOpinion` varchar(500) DEFAULT NULL COMMENT '主管部门意见（无主管部门可不填写）',
  `hrOpinion` varchar(500) DEFAULT NULL COMMENT '人力资源部意见',
  `bossOpinion` varchar(500) DEFAULT NULL COMMENT '总经理意见',
  `publishStatus` int(11) DEFAULT NULL COMMENT '状态：\r\n            0 草稿\r\n            1 审核中\r\n            2 退回\r\n            3 已确认',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_361` (`applyId`) USING BTREE,
  CONSTRAINT `hr_post_assessment_ibfk_1` FOREIGN KEY (`applyId`) REFERENCES `hr_post_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='试用期评估表';

-- ----------------------------
-- Records of hr_post_assessment
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_prom_apply`
-- ----------------------------
DROP TABLE IF EXISTS `hr_prom_apply`;
CREATE TABLE `hr_prom_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applyUser` bigint(20) DEFAULT NULL COMMENT '晋升申请人',
  `accessionTime` datetime DEFAULT NULL,
  `depId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `depName` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `nowPositionId` bigint(20) DEFAULT NULL COMMENT '现职位ID',
  `nowPositionName` varchar(100) DEFAULT NULL COMMENT '现部门名称',
  `workYear` varchar(100) DEFAULT NULL COMMENT '工作年限',
  `workHereYear` varchar(100) DEFAULT NULL COMMENT '本单位工作年限',
  `applyPositionId` bigint(20) DEFAULT NULL COMMENT '现职位ID',
  `applyPositionName` varchar(100) DEFAULT NULL COMMENT '现职位名称',
  `applyDate` datetime DEFAULT NULL COMMENT '拟晋升时间',
  `applyReason` varchar(500) DEFAULT NULL COMMENT '拟晋升原因',
  `target1` varchar(500) DEFAULT NULL COMMENT '工作目标1',
  `target2` varchar(500) DEFAULT NULL COMMENT '工作目标2',
  `target3` varchar(500) DEFAULT NULL COMMENT '工作目标3',
  `intRecord` varchar(500) DEFAULT NULL,
  `postManagerId` bigint(20) DEFAULT NULL COMMENT '直线经理userId',
  `postManagerName` varchar(100) DEFAULT NULL COMMENT '直线经理姓名',
  `publishStatus` int(11) DEFAULT NULL COMMENT '状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审批通过\r\n            4：删除标记',
  `createPerson` bigint(20) DEFAULT NULL COMMENT '直线经理',
  `createDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `postManagerAuditDate` datetime DEFAULT NULL COMMENT '直线领导签字时间',
  `userManagerAuditDate` datetime DEFAULT NULL COMMENT '本人签字时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='晋升申请表';

-- ----------------------------
-- Records of hr_prom_apply
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_prom_assessment`
-- ----------------------------
DROP TABLE IF EXISTS `hr_prom_assessment`;
CREATE TABLE `hr_prom_assessment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `applyId` bigint(20) DEFAULT NULL COMMENT '关联晋升申请表',
  `reached1` varchar(500) DEFAULT NULL COMMENT '工作目标1完成情况',
  `reached2` varchar(500) DEFAULT NULL COMMENT '工作目标2完成情况',
  `reached3` varchar(500) DEFAULT NULL COMMENT '工作目标3完成情况',
  `performResult` varchar(500) DEFAULT NULL COMMENT '工作业绩-绩效结果',
  `proKnowledge` varchar(100) DEFAULT NULL COMMENT '专业知识',
  `commEffect` varchar(100) DEFAULT NULL COMMENT '沟通影响',
  `solveAbility` varchar(100) DEFAULT NULL COMMENT '问题解决能力',
  `difficultyManage` varchar(100) DEFAULT NULL COMMENT '工作难度管理幅度',
  `businessFieldEffect` varchar(100) DEFAULT NULL COMMENT '业务领域影响',
  `ratingResult` varchar(100) DEFAULT NULL COMMENT '评级结果',
  `postManagerId` bigint(20) DEFAULT NULL COMMENT '直接经理userId',
  `postManagerName` varchar(100) DEFAULT NULL COMMENT '直线经理姓名',
  `postManagerAuditDate` datetime DEFAULT NULL COMMENT '直线领导签字时间',
  `deptManagerId` bigint(20) DEFAULT NULL COMMENT '部门主管userId',
  `deptManagerName` varchar(100) DEFAULT NULL COMMENT '部门主管姓名',
  `deptManagerAuditDate` datetime DEFAULT NULL COMMENT '部门领导签字时间',
  `appointDate` datetime DEFAULT NULL COMMENT '正式任命时间',
  `postRank` varchar(50) DEFAULT NULL COMMENT '岗位职级',
  `salaryLevelId` bigint(20) DEFAULT NULL COMMENT '薪资等级ID',
  `salaryLevelName` varchar(100) DEFAULT NULL COMMENT '薪资等级名称',
  `promIntRecord` varchar(500) DEFAULT NULL COMMENT '晋升面谈',
  `auditHrId` bigint(20) DEFAULT NULL COMMENT '人力资源部审核人userId',
  `auditHrName` varchar(100) DEFAULT NULL COMMENT '人力资源审核人姓名',
  `hrManagerAuditDate` datetime DEFAULT NULL COMMENT '人力资源审核人员签字时间',
  `publishStatus` int(11) DEFAULT NULL COMMENT '状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审批通过\r\n            4：删除标记',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `userManagerAuditDate` datetime DEFAULT NULL COMMENT '本人签字时间',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_34` (`applyId`) USING BTREE,
  CONSTRAINT `hr_prom_assessment_ibfk_1` FOREIGN KEY (`applyId`) REFERENCES `hr_prom_apply` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='晋升评估表';

-- ----------------------------
-- Records of hr_prom_assessment
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_factoritem`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_factoritem`;
CREATE TABLE `hr_sr_factoritem` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT,
  `belongPF` bigint(20) DEFAULT NULL,
  `factorScore` decimal(10,2) DEFAULT NULL,
  `factorValue` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_32` (`belongPF`) USING BTREE,
  CONSTRAINT `hr_sr_factoritem_ibfk_1` FOREIGN KEY (`belongPF`) REFERENCES `hr_sr_performancefactor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效系数对应';

-- ----------------------------
-- Records of hr_sr_factoritem
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_incometax`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_incometax`;
CREATE TABLE `hr_sr_incometax` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL COMMENT '名称',
  `beginDate` datetime DEFAULT NULL COMMENT '开始执行时间',
  `endDate` datetime DEFAULT NULL COMMENT '执行结束时间',
  `basicAmount` decimal(10,0) DEFAULT NULL COMMENT '个税起征点',
  `publishDate` datetime DEFAULT NULL COMMENT '发布时间',
  `publishPerson` bigint(20) DEFAULT NULL COMMENT '发布人',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人所得税基准表';

-- ----------------------------
-- Records of hr_sr_incometax
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_incometaxitem`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_incometaxitem`;
CREATE TABLE `hr_sr_incometaxitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `itId` bigint(20) DEFAULT NULL COMMENT '所得税模板',
  `limitAmount` decimal(20,0) DEFAULT NULL COMMENT '上限金额',
  `lowerAmount` decimal(20,0) DEFAULT NULL COMMENT '下限金额',
  `taxValue` double DEFAULT NULL COMMENT '适用税率',
  `deductValue` decimal(10,0) DEFAULT NULL COMMENT '速算扣除数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_28` (`itId`) USING BTREE,
  CONSTRAINT `hr_sr_incometaxitem_ibfk_1` FOREIGN KEY (`itId`) REFERENCES `hr_sr_incometax` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_sr_incometaxitem
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_performancefactor`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_performancefactor`;
CREATE TABLE `hr_sr_performancefactor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `factorName` varchar(150) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `descp` varchar(200) DEFAULT NULL,
  `belongDept` bigint(20) DEFAULT NULL COMMENT '归属部门',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态0：草稿1：审核中2：审核完毕',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效得分与系数对应关系表';

-- ----------------------------
-- Records of hr_sr_performancefactor
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_rewardspunishments`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_rewardspunishments`;
CREATE TABLE `hr_sr_rewardspunishments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '被奖惩人',
  `rpType` bigint(20) DEFAULT NULL COMMENT '奖惩类型 来源于字典表',
  `rpTypeStr` varchar(32) DEFAULT NULL COMMENT '奖惩类型',
  `amount` decimal(10,0) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL COMMENT '奖惩备注',
  `profileId` bigint(20) DEFAULT NULL COMMENT '人员档案ID',
  `createDate` datetime DEFAULT NULL COMMENT '创建人',
  `createPerson` bigint(20) DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_30` (`profileId`) USING BTREE,
  CONSTRAINT `hr_sr_rewardspunishments_ibfk_1` FOREIGN KEY (`profileId`) REFERENCES `emp_profile` (`profileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员奖惩表';

-- ----------------------------
-- Records of hr_sr_rewardspunishments
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_sr_rewardspunishmentstype`
-- ----------------------------
DROP TABLE IF EXISTS `hr_sr_rewardspunishmentstype`;
CREATE TABLE `hr_sr_rewardspunishmentstype` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  `typeDesc` varchar(128) DEFAULT NULL COMMENT '分类描述',
  `operation` smallint(6) NOT NULL COMMENT '操作符 1+ /  0-',
  `deleteFlag` smallint(6) NOT NULL COMMENT '删除标记 0未删除 1删除',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_sr_rewardspunishmentstype
-- ----------------------------

-- ----------------------------
-- Table structure for `hr_task_scheduler`
-- ----------------------------
DROP TABLE IF EXISTS `hr_task_scheduler`;
CREATE TABLE `hr_task_scheduler` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `taskname` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `runtime` varchar(255) DEFAULT NULL COMMENT '运行周期',
  `cronTrigger` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hr_task_scheduler
-- ----------------------------

-- ----------------------------
-- Table structure for `index_display`
-- ----------------------------
DROP TABLE IF EXISTS `index_display`;
CREATE TABLE `index_display` (
  `indexId` bigint(20) NOT NULL AUTO_INCREMENT,
  `portalId` varchar(64) NOT NULL COMMENT 'Portal ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `colNum` int(11) NOT NULL COMMENT '列号',
  `rowNum` int(11) NOT NULL COMMENT '行号',
  PRIMARY KEY (`indexId`),
  KEY `FK_ID_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `index_display_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个员工可以设置自己的显示方式';

-- ----------------------------
-- Records of index_display
-- ----------------------------

-- ----------------------------
-- Table structure for `in_message`
-- ----------------------------
DROP TABLE IF EXISTS `in_message`;
CREATE TABLE `in_message` (
  `receiveId` bigint(20) NOT NULL AUTO_INCREMENT,
  `messageId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `readFlag` smallint(6) NOT NULL COMMENT '1=has red\r\n            0=unread',
  `delFlag` smallint(6) NOT NULL,
  `userFullname` varchar(32) NOT NULL,
  PRIMARY KEY (`receiveId`),
  KEY `FK_IM_R_AU` (`userId`) USING BTREE,
  KEY `FK_IM_R_SM` (`messageId`) USING BTREE,
  CONSTRAINT `in_message_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `in_message_ibfk_2` FOREIGN KEY (`messageId`) REFERENCES `short_message` (`messageId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of in_message
-- ----------------------------

-- ----------------------------
-- Table structure for `in_stock`
-- ----------------------------
DROP TABLE IF EXISTS `in_stock`;
CREATE TABLE `in_stock` (
  `buyId` bigint(20) NOT NULL AUTO_INCREMENT,
  `goodsId` bigint(20) NOT NULL,
  `providerName` varchar(128) DEFAULT NULL,
  `stockNo` varchar(128) NOT NULL,
  `price` decimal(18,2) DEFAULT NULL,
  `inCounts` int(11) DEFAULT NULL,
  `amount` decimal(18,2) NOT NULL,
  `inDate` datetime NOT NULL,
  `buyer` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`buyId`),
  KEY `FK_IS_R_OG` (`goodsId`) USING BTREE,
  CONSTRAINT `in_stock_ibfk_1` FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品入库需要同时更新办公用品表的库存';

-- ----------------------------
-- Records of in_stock
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_deployment`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME_` longtext,
  `TIMESTAMP_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_deployment
-- ----------------------------
INSERT INTO jbpm4_deployment VALUES ('4', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('5', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('6', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('7', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('8', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('9', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('10', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('12', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('13', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('14', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('15', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('16', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('17', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('18', null, '0', 'active');
INSERT INTO jbpm4_deployment VALUES ('20', null, '0', 'active');

-- ----------------------------
-- Table structure for `jbpm4_deployprop`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `OBJNAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `STRINGVAL_` varchar(255) DEFAULT NULL,
  `LONGVAL_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_DEPLPROP_DEPL` (`DEPLOYMENT_`) USING BTREE,
  KEY `FK_DEPLPROP_DEPL` (`DEPLOYMENT_`) USING BTREE,
  CONSTRAINT `jbpm4_deployprop_ibfk_1` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_deployprop
-- ----------------------------
INSERT INTO jbpm4_deployprop VALUES ('10', '4', 'pd6426584402782899404', 'pdkey', 'pd6426584402782899404', null);
INSERT INTO jbpm4_deployprop VALUES ('11', '4', 'pd6426584402782899404', 'pdid', 'pd6426584402782899404-1', null);
INSERT INTO jbpm4_deployprop VALUES ('12', '4', 'pd6426584402782899404', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('13', '5', 'pd4993365972583614546', 'pdkey', 'pd4993365972583614546', null);
INSERT INTO jbpm4_deployprop VALUES ('14', '5', 'pd4993365972583614546', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('15', '5', 'pd4993365972583614546', 'pdid', 'pd4993365972583614546-1', null);
INSERT INTO jbpm4_deployprop VALUES ('16', '6', 'pd6578157620277996804', 'pdid', 'pd6578157620277996804-1', null);
INSERT INTO jbpm4_deployprop VALUES ('17', '6', 'pd6578157620277996804', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('18', '6', 'pd6578157620277996804', 'pdkey', 'pd6578157620277996804', null);
INSERT INTO jbpm4_deployprop VALUES ('19', '7', 'pd8252642282251937144', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('20', '7', 'pd8252642282251937144', 'pdkey', 'pd8252642282251937144', null);
INSERT INTO jbpm4_deployprop VALUES ('21', '7', 'pd8252642282251937144', 'pdid', 'pd8252642282251937144-1', null);
INSERT INTO jbpm4_deployprop VALUES ('22', '8', 'pd6212082814169152003', 'pdid', 'pd6212082814169152003-1', null);
INSERT INTO jbpm4_deployprop VALUES ('23', '8', 'pd6212082814169152003', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('24', '8', 'pd6212082814169152003', 'pdkey', 'pd6212082814169152003', null);
INSERT INTO jbpm4_deployprop VALUES ('25', '9', 'pd8498713057619136655', 'pdid', 'pd8498713057619136655-1', null);
INSERT INTO jbpm4_deployprop VALUES ('26', '9', 'pd8498713057619136655', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('27', '9', 'pd8498713057619136655', 'pdkey', 'pd8498713057619136655', null);
INSERT INTO jbpm4_deployprop VALUES ('28', '10', 'pd7096695693128483739', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('29', '10', 'pd7096695693128483739', 'pdkey', 'pd7096695693128483739', null);
INSERT INTO jbpm4_deployprop VALUES ('30', '10', 'pd7096695693128483739', 'pdid', 'pd7096695693128483739-1', null);
INSERT INTO jbpm4_deployprop VALUES ('34', '12', 'pd9102991709241803919', 'pdkey', 'pd9102991709241803919', null);
INSERT INTO jbpm4_deployprop VALUES ('35', '12', 'pd9102991709241803919', 'pdid', 'pd9102991709241803919-1', null);
INSERT INTO jbpm4_deployprop VALUES ('36', '12', 'pd9102991709241803919', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('37', '13', 'pd6698814834287015698', 'pdid', 'pd6698814834287015698-1', null);
INSERT INTO jbpm4_deployprop VALUES ('38', '13', 'pd6698814834287015698', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('39', '13', 'pd6698814834287015698', 'pdkey', 'pd6698814834287015698', null);
INSERT INTO jbpm4_deployprop VALUES ('40', '14', 'pd7680978182520578249', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('41', '14', 'pd7680978182520578249', 'pdkey', 'pd7680978182520578249', null);
INSERT INTO jbpm4_deployprop VALUES ('42', '14', 'pd7680978182520578249', 'pdid', 'pd7680978182520578249-1', null);
INSERT INTO jbpm4_deployprop VALUES ('43', '15', 'pd8705157447619894010', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('44', '15', 'pd8705157447619894010', 'pdid', 'pd8705157447619894010-1', null);
INSERT INTO jbpm4_deployprop VALUES ('45', '15', 'pd8705157447619894010', 'pdkey', 'pd8705157447619894010', null);
INSERT INTO jbpm4_deployprop VALUES ('46', '16', 'pd6899743696526177012', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('47', '16', 'pd6899743696526177012', 'pdkey', 'pd6899743696526177012', null);
INSERT INTO jbpm4_deployprop VALUES ('48', '16', 'pd6899743696526177012', 'pdid', 'pd6899743696526177012-1', null);
INSERT INTO jbpm4_deployprop VALUES ('49', '17', 'pd7302193779570997303', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('50', '17', 'pd7302193779570997303', 'pdkey', 'pd7302193779570997303', null);
INSERT INTO jbpm4_deployprop VALUES ('51', '17', 'pd7302193779570997303', 'pdid', 'pd7302193779570997303-1', null);
INSERT INTO jbpm4_deployprop VALUES ('52', '18', 'pd5701124732843989465', 'pdversion', null, '1');
INSERT INTO jbpm4_deployprop VALUES ('53', '18', 'pd5701124732843989465', 'pdkey', 'pd5701124732843989465', null);
INSERT INTO jbpm4_deployprop VALUES ('54', '18', 'pd5701124732843989465', 'pdid', 'pd5701124732843989465-1', null);
INSERT INTO jbpm4_deployprop VALUES ('58', '20', 'pd5348597956506307584', 'pdkey', 'pd5348597956506307584', null);
INSERT INTO jbpm4_deployprop VALUES ('59', '20', 'pd5348597956506307584', 'pdid', 'pd5348597956506307584-1', null);
INSERT INTO jbpm4_deployprop VALUES ('60', '20', 'pd5348597956506307584', 'pdversion', null, '1');

-- ----------------------------
-- Table structure for `jbpm4_execution`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ACTIVITYNAME_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `HISACTINST_` bigint(20) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `INSTANCE_` bigint(20) DEFAULT NULL,
  `SUPEREXEC_` bigint(20) DEFAULT NULL,
  `SUBPROCINST_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  UNIQUE KEY `ID_` (`ID_`) USING BTREE,
  KEY `IDX_EXEC_SUPEREXEC` (`SUPEREXEC_`) USING BTREE,
  KEY `IDX_EXEC_INSTANCE` (`INSTANCE_`) USING BTREE,
  KEY `IDX_EXEC_SUBPI` (`SUBPROCINST_`) USING BTREE,
  KEY `IDX_EXEC_PARENT` (`PARENT_`) USING BTREE,
  KEY `FK_EXEC_PARENT` (`PARENT_`) USING BTREE,
  KEY `FK_EXEC_SUBPI` (`SUBPROCINST_`) USING BTREE,
  KEY `FK_EXEC_INSTANCE` (`INSTANCE_`) USING BTREE,
  KEY `FK_EXEC_SUPEREXEC` (`SUPEREXEC_`) USING BTREE,
  CONSTRAINT `jbpm4_execution_ibfk_1` FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `jbpm4_execution_ibfk_2` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `jbpm4_execution_ibfk_3` FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `jbpm4_execution_ibfk_4` FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_execution
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_hist_actinst`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `TRANSITION_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HACTI_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `IDX_HTI_HTASK` (`HTASK_`) USING BTREE,
  KEY `FK_HACTI_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `FK_HTI_HTASK` (`HTASK_`) USING BTREE,
  CONSTRAINT `jbpm4_hist_actinst_ibfk_1` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `jbpm4_hist_actinst_ibfk_2` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_actinst
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_hist_detail`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TIME_` datetime DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HPROCIIDX_` int(11) DEFAULT NULL,
  `HACTI_` bigint(20) DEFAULT NULL,
  `HACTIIDX_` int(11) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  `HTASKIDX_` int(11) DEFAULT NULL,
  `HVAR_` bigint(20) DEFAULT NULL,
  `HVARIDX_` int(11) DEFAULT NULL,
  `MESSAGE_` longtext,
  `OLD_INT_` int(11) DEFAULT NULL,
  `NEW_INT_` int(11) DEFAULT NULL,
  `OLD_STR_` varchar(255) DEFAULT NULL,
  `NEW_STR_` varchar(255) DEFAULT NULL,
  `OLD_TIME_` datetime DEFAULT NULL,
  `NEW_TIME_` datetime DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  `PARENT_IDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HDET_HACTI` (`HACTI_`) USING BTREE,
  KEY `IDX_HDET_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `IDX_HDETAIL_HACTI` (`HACTI_`) USING BTREE,
  KEY `IDX_HDETAIL_HVAR` (`HVAR_`) USING BTREE,
  KEY `IDX_HDETAIL_HTASK` (`HTASK_`) USING BTREE,
  KEY `IDX_HDETAIL_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `IDX_HDET_HVAR` (`HVAR_`) USING BTREE,
  KEY `IDX_HDET_HTASK` (`HTASK_`) USING BTREE,
  KEY `FK_HDETAIL_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `FK_HDETAIL_HACTI` (`HACTI_`) USING BTREE,
  KEY `FK_HDETAIL_HTASK` (`HTASK_`) USING BTREE,
  KEY `FK_HDETAIL_HVAR` (`HVAR_`) USING BTREE,
  CONSTRAINT `jbpm4_hist_detail_ibfk_1` FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`),
  CONSTRAINT `jbpm4_hist_detail_ibfk_2` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `jbpm4_hist_detail_ibfk_3` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`),
  CONSTRAINT `jbpm4_hist_detail_ibfk_4` FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_hist_procinst`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PROCDEFID_` varchar(255) DEFAULT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `START_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ENDACTIVITY_` varchar(255) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_procinst
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_hist_task`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `EXECUTION_` varchar(255) DEFAULT NULL,
  `OUTCOME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `END_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `NEXTIDX_` int(11) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `FK_HSUPERT_SUB` (`SUPERTASK_`) USING BTREE,
  CONSTRAINT `jbpm4_hist_task_ibfk_1` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_task
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_hist_var`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
  `DBID_` bigint(20) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `PROCINSTID_` varchar(255) DEFAULT NULL,
  `EXECUTIONID_` varchar(255) DEFAULT NULL,
  `VARNAME_` varchar(255) DEFAULT NULL,
  `VALUE_` varchar(255) DEFAULT NULL,
  `HPROCI_` bigint(20) DEFAULT NULL,
  `HTASK_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_HVAR_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `IDX_HVAR_HTASK` (`HTASK_`) USING BTREE,
  KEY `FK_HVAR_HPROCI` (`HPROCI_`) USING BTREE,
  KEY `FK_HVAR_HTASK` (`HTASK_`) USING BTREE,
  CONSTRAINT `jbpm4_hist_var_ibfk_1` FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`),
  CONSTRAINT `jbpm4_hist_var_ibfk_2` FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_hist_var
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_id_group`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_group`;
CREATE TABLE `jbpm4_id_group` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `PARENT_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_GROUP_PARENT` (`PARENT_`) USING BTREE,
  KEY `FK_GROUP_PARENT` (`PARENT_`) USING BTREE,
  CONSTRAINT `jbpm4_id_group_ibfk_1` FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_id_group` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_id_group
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_id_membership`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_membership`;
CREATE TABLE `jbpm4_id_membership` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `USER_` bigint(20) DEFAULT NULL,
  `GROUP_` bigint(20) DEFAULT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_MEM_USER` (`USER_`) USING BTREE,
  KEY `IDX_MEM_GROUP` (`GROUP_`) USING BTREE,
  KEY `FK_MEM_GROUP` (`GROUP_`) USING BTREE,
  KEY `FK_MEM_USER` (`USER_`) USING BTREE,
  CONSTRAINT `jbpm4_id_membership_ibfk_1` FOREIGN KEY (`GROUP_`) REFERENCES `jbpm4_id_group` (`DBID_`),
  CONSTRAINT `jbpm4_id_membership_ibfk_2` FOREIGN KEY (`USER_`) REFERENCES `jbpm4_id_user` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_id_membership
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_id_user`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_user`;
CREATE TABLE `jbpm4_id_user` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `ID_` varchar(255) DEFAULT NULL,
  `PASSWORD_` varchar(255) DEFAULT NULL,
  `GIVENNAME_` varchar(255) DEFAULT NULL,
  `FAMILYNAME_` varchar(255) DEFAULT NULL,
  `BUSINESSEMAIL_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_id_user
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_job`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `STATE_` varchar(255) DEFAULT NULL,
  `ISEXCLUSIVE_` bit(1) DEFAULT NULL,
  `LOCKOWNER_` varchar(255) DEFAULT NULL,
  `LOCKEXPTIME_` datetime DEFAULT NULL,
  `EXCEPTION_` longtext,
  `RETRIES_` int(11) DEFAULT NULL,
  `PROCESSINSTANCE_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `CFG_` bigint(20) DEFAULT NULL,
  `SIGNAL_` varchar(255) DEFAULT NULL,
  `EVENT_` varchar(255) DEFAULT NULL,
  `REPEAT_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_JOBRETRIES` (`RETRIES_`) USING BTREE,
  KEY `IDX_JOB_CFG` (`CFG_`) USING BTREE,
  KEY `IDX_JOB_PRINST` (`PROCESSINSTANCE_`) USING BTREE,
  KEY `IDX_JOB_EXE` (`EXECUTION_`) USING BTREE,
  KEY `IDX_JOBLOCKEXP` (`LOCKEXPTIME_`) USING BTREE,
  KEY `IDX_JOBDUEDATE` (`DUEDATE_`) USING BTREE,
  KEY `FK_JOB_CFG` (`CFG_`) USING BTREE,
  CONSTRAINT `jbpm4_job_ibfk_1` FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_job
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_lob`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `BLOB_VALUE_` longblob,
  `DEPLOYMENT_` bigint(20) DEFAULT NULL,
  `NAME_` longtext,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_LOB_DEPLOYMENT` (`DEPLOYMENT_`) USING BTREE,
  KEY `FK_LOB_DEPLOYMENT` (`DEPLOYMENT_`) USING BTREE,
  CONSTRAINT `jbpm4_lob_ibfk_1` FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`)
) ENGINE=InnoDB AUTO_INCREMENT=154 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_lob
-- ----------------------------
INSERT INTO jbpm4_lob VALUES ('9', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436343236353834343032373832383939343034223E3C7374617274206E616D653D22BFAACABC2220673D223235312C342C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22B7A2CEC4BACBB8E52220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B7A2CEC4BACBB8E52220673D223233382C3130352C38392C3336223E3C7472616E736974696F6E206E616D653D22BACBB8E52220746F3D22BFC6CAD2C9F3BACB2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BFC6CAD2C9F3BACB2220673D223234302C3137352C38392C3336223E3C7472616E736974696F6E206E616D653D22C9F3BACB2220746F3D22D6F7B9DCC1ECB5BCC9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22D6F7B9DCC1ECB5BCC9F3C5FA2220673D223234302C3233352C39332C3336223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22B7D6B9DCC1ECB5BCC7A9B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7A2CEC4B7D6B7A22220673D223233392C3434312C38392C3336223E3C7472616E736974696F6E206E616D653D22B7D6B7A22220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7A2CEC4D0A3B6D42220673D223234312C3336382C38392C3336223E3C7472616E736974696F6E206E616D653D22D0A3B6D42220746F3D22B7A2CEC4B7D6B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCC1ECB5BCC7A9B7A22220673D223233392C3330332C39362C3336223E3C7472616E736974696F6E206E616D653D22C7A9B7A22220746F3D22B7A2CEC4D0A3B6D42220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223235342C3530352C35382C3538222F3E3C2F70726F636573733E, '4', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('10', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706434393933333635393732353833363134353436223E3C7374617274206E616D653D22BFAACABC2220673D223236312C31342C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC4E2B0EC2220746F3D22B5C7BCC7C4E2B0EC2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B5C7BCC7C4E2B0EC2220673D223233392C3130332C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC1ECB5BCC5FACABE2220746F3D22C1ECB5BCC5FACABE2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C1ECB5BCC5FACABE2220673D223233392C3138312C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB9ABCEC4B7D6B7A22220746F3D22B9ABCEC4B7D6B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B9ABCEC4B7D6B7A22220673D223234302C3235332C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB3D0B0ECB4ABD4C42220746F3D22B3D0B0ECB4ABD4C42220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B3D0B0ECB4ABD4C42220673D223234322C3333392C3130362C3433223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223236362C3432372C35382C3538222F3E3C2F70726F636573733E, '5', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('11', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436353738313537363230323737393936383034223E3C7374617274206E616D653D22BFAACABC2220673D223139312C32392C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC9F3C5FA2220746F3D22BEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22BEADC0EDC9F3C5FA2220673D223135352C3135382C3133372C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223139362C3237382C35382C3538222F3E3C2F70726F636573733E, '6', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('12', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706438323532363432323832323531393337313434223E3C7374617274206E616D653D22BFAACABC2220673D223232322C392C35382C3538223E3C7472616E736974696F6E206E616D653D22C9EAC7EB2220746F3D22B2BFC3C5C1ECB5BCC9F3D4C42220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B2BFC3C5C1ECB5BCC9F3D4C42220673D223230332C39382C39382C3436223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB7D6B9DCC1ECB5BC2220746F3D22B7D6B9DCC1ECB5BCB8C7D5C22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCC1ECB5BCB8C7D5C22220673D223230332C3137322C39392C3437223E3C7472616E736974696F6E206E616D653D22B4F0D3A62220746F3D22B4F2D3A12220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B4F2D3A12220673D223230342C3234382C39382C3435223E3C7472616E736974696F6E206E616D653D22B2C6CEF1B4A62220746F3D22B2C6CEF1B4A62220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B2C6CEF1B4A62220673D223230332C3332372C3130352C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223232362C3431362C35382C3538222F3E3C2F70726F636573733E, '7', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('13', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436323132303832383134313639313532303033223E3C7374617274206E616D653D22BFAACABC2220673D223135382C35392C35382C3538223E3C7472616E736974696F6E206E616D653D22BFAACABC2220746F3D22C9CFBCB6C9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22C9CFBCB6C9F3C5FA2220673D223134312C3136392C39372C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223136312C3237372C35382C3538222F3E3C2F70726F636573733E, '8', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('14', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706438343938373133303537363139313336363535223E3C7374617274206E616D653D22BFAACABC2220673D223139382C36322C35382C3538223E3C7472616E736974696F6E206E616D653D22C9EAC7EB2220746F3D22B1EDB5A52220673D222D32342C2D3136222F3E3C2F73746172743E3C656E64206E616D653D22BDE1CAF8312220673D223230342C3239382C35382C3538222F3E3C7461736B206E616D653D22B1EDB5A52220673D223138322C3138302C39372C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C2F70726F636573733E, '9', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('15', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706437303936363935363933313238343833373339223E3C7374617274206E616D653D22BFAACABC2220673D223136392C34352C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBB2220746F3D22B8B1BEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B8B1BEADC0EDC9F3C5FA2220673D223133322C3133372C3133352C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22D7DCBEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22D7DCBEADC0EDC9F3C5FA2220673D223133322C3232372C3133392C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BDE1CAF8322220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8322220673D223137362C3332392C35382C3538222F3E3C2F70726F636573733E, '10', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('18', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000CE4BFA1E681AFE983A8E997A87371007E00180000000000000000740004302E312E7074000F31313140686F746D61696C2E636F6D74000074000363777874000074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740000740000740000737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);
INSERT INTO jbpm4_lob VALUES ('19', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('20', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('22', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0D0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706439313032393931373039323431383033393139223E3C7461736B206E616D653D22BFC6CAD2B8BAD4F0C8CBBACBB8E52220673D223234322C3130382C3130382C3334223E3C7472616E736974696F6E206E616D653D22BACBB8E52220746F3D22B7D6B9DCBBF2BED6C1ECB5BCC7A9B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCBBF2BED6C1ECB5BCC7A9B7A22220673D223234312C3137392C3131322C3335223E3C7472616E736974696F6E206E616D653D22C7A9B7A22220746F3D22B0ECB9ABCAD2D6F7C8CEB3D0B0EC2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B0ECB9ABCAD2D6F7C8CEB3D0B0EC2220673D223234342C3235302C3130352C3335223E3C7472616E736974696F6E206E616D653D22B3D0B0EC2220746F3D22B1E0BAC5B8C7D5C2B7D6B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B1E0BAC5B8C7D5C2B7D6B7A22220673D223234392C3331332C39332C3332223E3C7472616E736974696F6E206E616D653D22B7D6B7A22220746F3D22BDE1CAF8332220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8332220673D223236372C3337392C35382C3538222F3E3C7374617274206E616D653D22BFAACABC2220673D223236372C31312C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22BFC6CAD2B8BAD4F0C8CBBACBB8E52220673D222D32342C2D3136222F3E3C2F73746172743E3C2F70726F636573733E, '12', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('28', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436363938383134383334323837303135363938223E3C7374617274206E616D653D22BFAACABC2220673D223236332C34382C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D223233382C3134342C3130342C3335223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22C8CBCAC2B5C7BCC72220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBCAC2B5C7BCC72220673D223235352C3231322C36392C3333223E3C7472616E736974696F6E206E616D653D22B5C7BCC72220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223235392C3237352C35382C3538222F3E3C2F70726F636573733E, '13', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('29', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706437363830393738313832353230353738323439223E3C7374617274206E616D653D22BFAACABC2220673D223238352C33332C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D223236312C3132372C3130362C3337223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22B7D6B9DCBED6B3A4C9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCBED6B3A4C9F3C5FA2220673D223236342C3139332C39392C3337223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BED6B3A4C9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BED6B3A4C9F3C5FA2220673D223237372C3236302C37322C3336223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22C8CBCAC2B5C7BCC72220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBCAC2B5C7BCC72220673D223237382C3332382C37312C3333223E3C7472616E736974696F6E206E616D653D22B5C7BCC72220746F3D22BDE1CAF8322220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8322220673D223238342C3338392C35382C3538222F3E3C2F70726F636573733E, '14', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('30', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706438373035313537343437363139383934303130223E3C7374617274206E616D653D22BFAACABC2220673D223238392C35392C35382C3538223E3C7472616E736974696F6E206E616D653D22C1ACCFDF392220746F3D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B2BFC3C5B8BAD4F0C8CBC9F3C5FA2220673D223236372C3134332C3130352C3333223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BED6B3A4C9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BED6B3A4C9F3C5FA2220673D223238322C3231332C37352C3334223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22C8CBCAC2B5C7BCC72220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBCAC2B5C7BCC72220673D223238332C3238332C37332C3333223E3C7472616E736974696F6E206E616D653D22B5C7BCC72220746F3D22BDE1CAF8332220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8332220673D223239312C3334352C35382C3538222F3E3C2F70726F636573733E, '15', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('34', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('41', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436383939373433363936353236313737303132223E3C7374617274206E616D653D22BFAACABC2220673D223236342C31342C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22B0ECB9ABCAD2B4ABD4C42220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B0ECB9ABCAD2B4ABD4C42220673D223235312C39342C38372C3336223E3C7472616E736974696F6E206E616D653D22B4ABD4C42220746F3D22B0ECB9ABCAD2D6F7C8CEC5FAD4C42220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B0ECB9ABCAD2D6F7C8CEC5FAD4C42220673D223234322C3135382C3130362C3334223E3C7472616E736974696F6E206E616D653D22C5FAD4C42220746F3D22B7D6B9DCBBF2D6F7B9DCC1ECB5BCC5FACABE2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCBBF2D6F7B9DCC1ECB5BCC5FACABE2220673D223233322C3232312C3132392C3430223E3C7472616E736974696F6E206E616D653D22C5FACABE2220746F3D22BFC6CAD2D6F7C8CEB4ABD4C42220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BFC6CAD2D6F7C8CEB4ABD4C42220673D223235302C3239302C39332C3337223E3C7472616E736974696F6E206E616D653D22B4ABD4C42220746F3D22B3D0B0ECB9E9B5B52220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B3D0B0ECB9E9B5B52220673D223236312C3335362C37312C3334223E3C7472616E736974696F6E206E616D653D22B9E9B5B52220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223236372C3431352C35382C3538222F3E3C2F70726F636573733E, '16', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('42', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706437333032313933373739353730393937333033223E3C7374617274206E616D653D22BFAACABC2220673D223236352C342C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22C9CFB1A8C9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22C9CFB1A8C9F3C5FA2220673D223236302C38372C37302C3337223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22C8CBC1A6D7CAD4B4BACBCAB5B2A2C8B7C8CF2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBC1A6D7CAD4B4BACBCAB5B2A2C8B7C8CF2220673D223232382C3134392C3133332C3336223E3C7472616E736974696F6E206E616D653D22BACBCAB5C8B7C8CF2220746F3D22C4BFB1EAC9E8B6A8D3EBC3E6CCB82220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C4BFB1EAC9E8B6A8D3EBC3E6CCB82220673D223234312C3231302C3130392C3334223E3C7472616E736974696F6E206E616D653D22C3E6CCB82220746F3D22BFBCBACBC6DAC6C0B9C02220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BFBCBACBC6DAC6C0B9C02220673D223235352C3236372C38342C3334223E3C7472616E736974696F6E206E616D653D22C6C0B9C02220746F3D22C1ECB5BCC5FAD7BC2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C1ECB5BCC5FAD7BC2220673D223236302C3332342C37352C3334223E3C7472616E736974696F6E206E616D653D22C5FAD7BC2220746F3D22C8CBC1A6D7CAD4B4C8B7C8CF2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBC1A6D7CAD4B4C8B7C8CF2220673D223235322C3338322C39322C3338223E3C7472616E736974696F6E206E616D653D22C8B7C8CF2220746F3D22BDFAC9FDC3E6CCB82220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BDFAC9FDC3E6CCB82220673D223236312C3434372C37312C3334223E3C7472616E736974696F6E206E616D653D22BDFAC9FDC3E6CCB82220746F3D22C8CEC3FCB7A2CEC42220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CEC3FCB7A2CEC42220673D223236312C3530342C37312C3335223E3C7472616E736974696F6E206E616D653D22B7A2CEC42220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223236312C3535362C37342C3734222F3E3C2F70726F636573733E, '17', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('63', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('64', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('65', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('67', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('69', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('71', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000CE4BFA1E681AFE983A8E997A87371007E00180000000000000000740004302E312E7074000F31313140686F746D61696C2E636F6D74000074000363777874000638383838383874002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740006383838383838740000740009E5B7A5E7A88BE5B888737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);
INSERT INTO jbpm4_lob VALUES ('72', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('74', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('75', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('76', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('77', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000CE7BBB4E68AA4E7B3BBE7BB9F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000CE4BFA1E681AFE983A8E997A87371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('78', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706435373031313234373332383433393839343635223E3C7374617274206E616D653D22BFAACABC2220673D223236352C33332C35382C3538223E3C7472616E736974696F6E206E616D653D22C9F3BACBC9CFB1A82220746F3D22C8CBC1A6D7CAD4B4B8B4BACBC8B7C8CF2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22C8CBC1A6D7CAD4B4B8B4BACBC8B7C8CF2220673D223233342C3134352C3132322C3339223E3C7472616E736974696F6E206E616D653D22BCA8D0A7B5BCB3F62220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223236372C3231392C35382C3538222F3E3C2F70726F636573733E, '18', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('80', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0D0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706435333438353937393536353036333037353834223E3C7374617274206E616D653D22BFAACABC2220673D223233392C33322C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC9EAC7EB2220746F3D22D6B1CFDFBEADC0EDC9F3BACB2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22D6B1CFDFBEADC0EDC9F3BACB2220673D223232352C3133372C3130392C3334223E3C7472616E736974696F6E206E616D653D22C9F3BACBC9CFB1A82220746F3D22C8CBC1A6D7CAD4B4B8B4BACB2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C8CBC1A6D7CAD4B4B8B4BACB2220673D223232382C3233302C3130352C3333223E3C7472616E736974696F6E206E616D653D22C8B7C8CFCDA8B9FD2220746F3D22B7D6B9DCB8B1D7DCB2C3C8B7C8CF2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCB8B1D7DCB2C3C8B7C8CF2220673D223232312C3330372C3131392C3333223E3C7472616E736974696F6E206E616D653D22C8B7C8CFCDA8B9FD2220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223234352C3338322C35382C3538222F3E3C2F70726F636573733E, '20', 'process.jpdl.xml');
INSERT INTO jbpm4_lob VALUES ('81', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('82', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('83', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('84', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('85', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('86', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('87', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('88', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('89', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('90', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00180000000000000000740004302E312E7074000F31313140686F746D61696C2E636F6D74000074000363777874000638383838383874002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740006383838383838740000740009E5B7A5E7A88BE5B888737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);
INSERT INTO jbpm4_lob VALUES ('91', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('92', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('93', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00180000000000000000740004302E312E7074000F31313140686F746D61696C2E636F6D74000074000363777874000638383838383874002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740006383838383838740000740009E5B7A5E7A88BE5B888737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);
INSERT INTO jbpm4_lob VALUES ('94', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('95', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('97', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('98', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('101', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('104', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('105', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('106', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('107', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('108', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074000F637378406A65652D736F66742E636E7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('124', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('127', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001323F77AC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE5B882E59CBAE890A5E99480E983A87372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E0013000000000000002A737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000374000FE5B882E59CBAE890A5E99480E983A87371007E0018000000000000001B740008302E32372E34322E7074001163686F75706968616F403136332E636F6D740000740006E58898E585AD74000074002C6B625452516F492F66534446384933326B534C65512F4E66425871596A5A595A39744D546849584A6F674D3D740000740000740012E585ACE585B3E5AA92E4BB8BE4B893E59198737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000002003F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000871007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000037708000000040000000273720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018000000000000000671007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007413FF417263686976652C41726368466C6F77436F6E66566965772C5F41726368466C6F77436F6E66456469742C4172636869766549737375652C417263686976655479706554656D70566965772C5F417263686976655479706554656D7051756572792C5F4172636869766573547970654164642C5F417263686976657354797065456469742C5F41726368697665735479706544656C2C5F417263686976657354656D704164642C5F417263686976657354656D70456469742C5F417263687669657354656D7044656C2C41726368697665734472616674566965772C5F41636869766573447261664164642C417263686976657344726166744D616E6167652C5F4172636869766573447261666D51756572792C5F4172636869766573447261666D456469742C5F4172636869766573447261666D44656C2C4172636869766573497373756541756469742C5F4172636869766573497373756551756572792C5F41726368697665734973737565456469742C417263686976657349737375654C6561642C5F417263686976657349737375654C65616451756572792C5F417263686976657349737375654C656164456469742C417263686976657349737375654368617267652C5F4172636869766573497373756543686172676551756572792C5F41726368697665734973737565436861726765456469742C4172636869766573497373756550726F6F662C5F4172636869766573497373756550726F6F6651756572792C5F4172636869766573497373756550726F6F66456469742C4172636869766573446F63756D656E742C5F4172636869766573446F63756D656E7451756572792C417263686976657349737375654D6F6E69746F722C5F417263686976657349737375654D6F6E69746F7251756572792C5F4172636869766573497373756548617374656E2C417263686976657349737375654D616E6167652C5F417263686976657349737375654D616E61676551756572792C417263686976657349737375655365617263682C5F4172636869766573497373756553656172636851756572792C446F63486973746F7279566965772C5F446F63486973746F727951756572792C5F446F63486973746F727944656C2C41726368697665526563656976652C41726368697665735369676E566965772C5F41726368697665735369676E51756572792C5F41726368697665735369676E55702C4172636869766573526563566965772C5F417263686976657352656351756572792C5F41726368697665735265634164642C5F4172636869766573526563456469742C5F417263686976657352656344656C2C417263686976657348616E646C65566965772C5F417263686976657348616E646C6551756572792C4C656164657252656164566965772C5F4C65616465725265616451756572792C417263684469737061746368566965772C5F41726368446973706174636851756572792C41726368556E64657274616B65566965772C5F41726368556E64657274616B6551756572792C41726368697665735265634374726C566965772C5F41726368697665735265634374726C51756572792C5F417263686976657352656348617374656E2C4172636852656164566965772C5F417263685265616451756572792C4172636852656354797065566965772C5F417263685265635479706551756572792C5F41726368526563547970654164642C5F4172636852656354797065456469742C5F417263685265635479706544656C2C53797374656D53657474696E672C41707055736572566965772C5F4170705573657251756572792C5F417070557365724164642C5F41707055736572456469742C5F4170705573657244656C2C417070526F6C65566965772C5F417070526F6C654C6973742C5F417070526F6C654164642C5F417070526F6C65456469742C5F417070526F6C6544656C2C5F417070526F6C654772616E742C4465706172746D656E74566965772C5F4465706172746D656E7451756572792C5F4465706172746D656E744164642C5F4465706172746D656E74456469742C5F4465706172746D656E7444656C2C46696C65417474616368566965772C5F46696C6541747461636851756572792C5F46696C65417474616368456469742C5F46696C6541747461636844656C2C436F6D70616E79566965772C5F436F6D70616E79456469742C466C6F774D616E61676572566965772C5F466C6F7751756572792C5F50726F547970654D616E6167652C5F466C6F774164642C5F466C6F77456469742C5F466C6F7744656C2C5F466C6F77436865636B2C5F466C6F7753657474696E672C5075626C6963446F63756D656E742C4E65775075626C6963446F63756D656E74466F726D2C446F63466F6C646572536861726564566965772C5F446F63466F6C6465725368617265644D616E6167652C5F446F6350726976696C65676551756572792C5F446F6350726976696C6567654164642C5F446F6350726976696C656765456469742C5F446F6350726976696C65676544656C2C5461736B2C506C616E54797065566965772C5F506C616E5479706551756572792C5F506C616E547970654164642C5F506C616E54797065456469742C5F506C616E5479706544656C2C4E6577576F726B506C616E466F726D2C5F4E6577446570506C616E2C44696374696F6E617279566965772C5F44696374696F6E61727951756572792C5F44696374696F6E6172794164642C5F44696374696F6E617279456469742C5F44696374696F6E61727944656C2C496E666F2C4E657773566965772C5F4E65777351756572792C5F4E6577734164642C5F4E657773456469742C5F4E65777344656C2C4E657773436F6D6D656E74566965772C5F4E657773436F6D6D656E7451756572792C5F4E657773436F6D6D656E7444656C2C4E65777354797065566965772C5F4E6577735479706551756572792C5F4E657773547970654164642C5F4E65777354797065456469742C5F4E6577735479706544656C2C4E6F74696365566965772C5F4E6F7469636551756572792C5F4E6F746963654164642C5F4E6F74696365456469742C5F4E6F7469636544656C2C506572736F6E616C2C506572736F6E616C447574792C5369676E496E4F6666566965772C457272616E64735265676973746572566965772C457272616E647352656769737465724F7574566965772C447574794D616E616765722C447574792C486F6C696461795265636F7264566965772C5F486F6C696461795265636F726451756572792C5F486F6C696461795265636F72644164642C5F486F6C696461795265636F7264456469742C5F486F6C696461795265636F726444656C2C4475747953656374696F6E566965772C5F44757479536563746F6E51756572792C5F44757479536563746F6E4164642C5F44757479536563746F6E456469742C5F44757479536563746F6E44656C2C4475747953797374656D566965772C5F4475747953797374656D51756572792C5F4475747953797374656D4164642C5F4475747953797374656D456469742C5F4475747953797374656D44656C2C44757479566965772C5F4475747951756572792C5F447574794164642C5F44757479456469742C5F4475747944656C2C447574795265676973746572566965772C5F44757479526567697374657251756572792C5F4475747952656769737465724164642C5F44757479526567697374657244656C2C637573746F6D65722C437573746F6D6572566965772C5F437573746F6D657251756572792C5F437573746F6D65724164642C5F437573746F6D6572456469742C5F437573746F6D657244656C2C5F437573746F6D657253656E644D61696C2C4375734C696E6B6D616E566965772C5F4375734C696E6B6D616E51756572792C5F4375734C696E6B6D616E4164642C5F4375734C696E6B6D616E456469742C5F4375734C696E6B6D616E44656C2C437573436F6E6E656374696F6E566965772C5F437573436F6E6E656374696F6E51756572792C5F437573436F6E6E656374696F6E4164642C5F437573436F6E6E656374696F6E456469742C5F437573436F6E6E656374696F6E44656C2C50726F6A656374566965772C5F50726F6A65637451756572792C5F50726F6A6563744164642C5F50726F6A656374456469742C5F50726F6A65637444656C2C436F6E7472616374566965772C5F436F6E747261637451756572792C5F436F6E74726163744164642C5F436F6E7472616374456469742C5F436F6E747261637444656C2C50726F64756374566965772C5F50726F6475637451756572792C5F50726F647563744164642C5F50726F64756374456469742C5F50726F6475637444656C2C50726F7669646572566965772C5F50726F766964657251756572792C5F50726F76696465724164642C5F50726F7669646572456469742C5F50726F766964657244656C2C41646D696E6973747261746F722C476F6F644D616E656765722C4F6666696365476F6F64734D616E616765566965772C5F4F6666696365476F6F647351756572792C5F4F6666696365476F6F6473547970654D616E6167652C5F4F6666696365476F6F64734164642C5F4F6666696365476F6F6473456469742C5F4F6666696365476F6F647344656C2C496E53746F636B566965772C5F496E53746F636B51756572792C5F496E53746F636B4164642C5F496E53746F636B456469742C5F496E53746F636B44656C2C476F6F64734170706C79566965772C5F476F6F64734170706C7951756572792C5F476F6F64734170706C794164642C5F476F6F64734170706C79456469742C5F476F6F64734170706C7944656C2C4361724D616E656765722C436172566965772C5F43617251756572792C5F4361724164642C5F436172456469742C5F43617244656C2C43617274526570616972566965772C5F43617252657061697251756572792C5F4361725265706169724164642C5F436172526570616972456469742C5F43617252657061697244656C2C4361724170706C79566965772C5F4361724170706C7951756572792C5F4361724170706C794164642C5F4361724170706C79456469742C5F4361724170706C7944656C2C46697865644173736574734D616E6167652C446570726554797065566965772C5F44657072655479706551756572792C5F4465707265547970654164642C5F446570726554797065456469742C5F44657072655479706544656C2C46697865644173736574734D616E616765566965772C5F466978656441737365747351756572792C5F417373657473547970654D616E6167652C5F46697865644173736574734164642C5F4669786564417373657473456469742C5F466978656441737365747344656C2C5F446570726563696174652C44657072655265636F7264566965772C5F44657072655265636F726451756572792C426F6F6B4D616E616765722C426F6F6B54797065566965772C5F426F6F6B5479706551756572792C5F426F6F6B547970654164642C5F426F6F6B54797065456469742C5F426F6F6B5479706544656C2C426F6F6B4D616E616765566965772C5F426F6F6B51756572792C5F426F6F6B4164642C5F426F6F6B456469742C5F426F6F6B44656C2C426F6F6B426F72726F77566965772C5F426F6F6B426F72726F7751756572792C5F426F6F6B426F72726F774164642C5F426F6F6B426F72726F77456469742C5F426F6F6B52657475726E2C5F426F6F6B426F72726F7744656C2C426F6F6B52657475726E566965772C5F426F6F6B52657475726E51756572792C5F426F6F6B52657475726E4164642C5F426F6F6B52657475726E456469742C5F426F6F6B52657475726E44656C2C48726D2C48726D4D616E6167652C4A6F62566965772C5F4A6F6251756572792C5F4A6F624164642C5F4A6F62456469742C5F4A6F6244656C2C5F4A6F625265632C456D7050726F66696C65466F726D2C5F456D7050726F66696C655265672C456D7050726F66696C65566965772C5F456D7050726F66696C6551756572792C5F456D7050726F66696C654164642C5F456D7050726F66696C65456469742C5F456D7050726F66696C6544656C2C5F456D7050726F66696C65436865636B2C5F456D7050726F66696C655265632C53616C6172794D616E6167652C53616C6172794974656D566965772C5F53616C6172794974656D51756572792C5F53616C6172794974656D4164642C5F53616C6172794974656D456469742C5F53616C6172794974656D44656C2C5374616E6453616C617279466F726D2C5F5374616E6453616C6172795265672C5374616E6453616C617279566965772C5F5374616E6453616C61727951756572792C5F5374616E6453616C6172794164642C5F5374616E6453616C617279456469742C5F5374616E6453616C61727944656C2C5F5374616E6453616C617279436865636B2C53616C6172795061796F6666466F726D2C5F53616C6172795061796F66665265672C53616C6172795061796F6666566965772C5F53616C6172795061796F666651756572792C5F53616C6172795061796F66664164642C5F53616C6172795061796F6666456469742C5F53616C6172795061796F666644656C2C5F53616C6172795061796F6666436865636B2C4A6F624368616E67652C4A6F624368616E6765466F726D2C5F4A6F624368616E67655265672C4A6F624368616E6765566965772C5F4A6F624368616E676551756572792C5F4A6F624368616E67654164642C5F4A6F624368616E6765456469742C5F4A6F624368616E676544656C2C5F4A6F624368616E6765436865636B2C486972654973737565566965772C5F48697265497373756551756572792C5F4869726549737375654164642C5F486972654973737565456469742C5F48697265497373756544656C2C5F486972654973737565436865636B2C526573756D65566965772C5F526573756D6551756572792C5F526573756D654164642C5F526573756D65456469742C5F526573756D6544656C74000CE4B880E888ACE794A8E688B771007E003674000CE4B880E888ACE794A8E688B77371007E0012000171007E00347371007E003371007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003F71007E003770707371007E002AFFFFFFFF000071007E004171007E003F71007E003970707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E004174000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E003F787371007E0027770C000000103F4000000000000271007E003471007E003F787371007E001200017371007E0012000171007E002F7400066C69756C6975740000, null, null);
INSERT INTO jbpm4_lob VALUES ('128', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('129', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('130', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('131', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('132', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('136', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('137', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('146', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('149', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657200000000000000010200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('151', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B59741903000078707708000001259D5DFC007800000000707372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00120000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00120000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00170000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D7074000FE8B685E7BAA7E7AEA1E79086E591987074002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D707070737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E00267870FFFFFFFF00017371007E0017000000000000000171007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E0024FFFFFFFF00007371007E0017FFFFFFFFFFFFFFFF71007E002E74002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E0024FFFFFFFF000071007E003071007E002E74002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001100007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003074000FE8B685E7BAA7E7AEA1E79086E591987371007E0011000171007E002E787371007E0021770C000000103F4000000000000171007E002E787371007E001100017371007E0011000171007E002974000561646D696E70, null, null);
INSERT INTO jbpm4_lob VALUES ('152', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00180000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D74000074000363777874000638383838383874002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740006383838383838740000740009E5B7A5E7A88BE5B888737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);
INSERT INTO jbpm4_lob VALUES ('153', '0', 0xACED000573720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365724390B5A70186AB150200144C000D616363657373696F6E54696D657400104C6A6176612F7574696C2F446174653B4C0007616464726573737400124C6A6176612F6C616E672F537472696E673B4C000764656C466C61677400114C6A6176612F6C616E672F53686F72743B4C000A6465706172746D656E747400274C636F6D2F7870736F66742F6F612F6D6F64656C2F73797374656D2F4465706172746D656E743B4C0009656475636174696F6E71007E00024C0005656D61696C71007E00024C000366617871007E00024C000866756C6C6E616D6571007E00024C00066D6F62696C6571007E00024C000870617373776F726471007E00024C000570686F6E6571007E00024C000570686F746F71007E00024C0008706F736974696F6E71007E00024C000672696768747374000F4C6A6176612F7574696C2F5365743B4C0005726F6C657371007E00054C000673746174757371007E00034C00057469746C6571007E00034C00067573657249647400104C6A6176612F6C616E672F4C6F6E673B4C0008757365726E616D6571007E00024C00037A697071007E00027872001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656CE28ABC6276FC1B300200024C00066C6F676765727400204C6F72672F6170616368652F636F6D6D6F6E732F6C6F6767696E672F4C6F673B4C000776657273696F6E7400134C6A6176612F6C616E672F496E74656765723B78707372002B6F72672E6170616368652E636F6D6D6F6E732E6C6F6767696E672E696D706C2E4C6F67344A4C6F6767657228ACFDE82AD5D2380200014C00046E616D6571007E0002787074001F636F6D2E7870736F66742E636F72652E6D6F64656C2E426173654D6F64656C70737200126A6176612E73716C2E54696D657374616D702618D5C80153BF650200014900056E616E6F737872000E6A6176612E7574696C2E44617465686A81014B597419030000787077080000012859BEDC0078000000007400007372000F6A6176612E6C616E672E53686F7274684D37133460DA5202000153000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000073720025636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E4465706172746D656E74A8DB6D3F366D66AC0200064C00076465704465736371007E00024C0005646570496471007E00064C00086465704C6576656C71007E00094C00076465704E616D6571007E00024C0008706172656E74496471007E00064C00047061746871007E00027871007E000771007E000C7074000FE680BBE8A381E58A9EE585ACE5AEA47372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E00130000000000000001737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E00130000000274000FE680BBE8A381E58A9EE585ACE5AEA47371007E00180000000000000000740004302E312E7074001163686F75706968616F403136332E636F6D74000074000363777874000638383838383874002C61346179632F38302F4F47646134424F2F316F2F56306574704F71694C78314A77423553336265485730733D740006383838383838740000740009E5B7A5E7A88BE5B888737200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000017400055F5F414C4C78737200266F72672E68696265726E6174652E636F6C6C656374696F6E2E50657273697374656E745365743770507425ED70B00200014C000373657471007E0005787200356F72672E68696265726E6174652E636F6C6C656374696F6E2E416273747261637450657273697374656E74436F6C6C656374696F6EB09154394BE626B302000749000A63616368656453697A655A000564697274795A000B696E697469616C697A65644C00036B65797400164C6A6176612F696F2F53657269616C697A61626C653B4C00056F776E65727400124C6A6176612F6C616E672F4F626A6563743B4C0004726F6C6571007E00024C000E73746F726564536E617073686F7471007E002C7870FFFFFFFF00017371007E0018000000000000000271007E000A740028636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070557365722E726F6C6573737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F400000000000017708000000020000000173720022636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C65212634B47083A23F0200084C0008617070557365727371007E00054C000966756E6374696F6E7371007E00054C000B697344656661756C74496E71007E00034C000672696768747371007E00024C0008726F6C654465736371007E00024C0006726F6C65496471007E00064C0008726F6C654E616D6571007E00024C000673746174757371007E00037871007E000771007E000C707371007E002AFFFFFFFF00007371007E0018FFFFFFFFFFFFFFFF71007E003474002B636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E617070557365727370707371007E002AFFFFFFFF000071007E003671007E003474002C636F6D2E7870736F66742E6F612E6D6F64656C2E73797374656D2E417070526F6C652E66756E6374696F6E7370707371007E001200007400055F5F414C4C740022E8B685E7BAA7E7AEA1E79086E591982CE585B7E69C89E68980E69C89E69D83E9999071007E003674000FE8B685E7BAA7E7AEA1E79086E591987371007E0012000171007E0034787371007E0027770C000000103F4000000000000171007E0034787371007E001200017371007E0012000171007E002F740003637378740000, null, null);

-- ----------------------------
-- Table structure for `jbpm4_participation`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `GROUPID_` varchar(255) DEFAULT NULL,
  `USERID_` varchar(255) DEFAULT NULL,
  `TYPE_` varchar(255) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_PART_TASK` (`TASK_`) USING BTREE,
  KEY `FK_PART_SWIMLANE` (`SWIMLANE_`) USING BTREE,
  KEY `FK_PART_TASK` (`TASK_`) USING BTREE,
  CONSTRAINT `jbpm4_participation_ibfk_1` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`),
  CONSTRAINT `jbpm4_participation_ibfk_2` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_participation
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_swimlane`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_SWIMLANE_EXEC` (`EXECUTION_`) USING BTREE,
  KEY `FK_SWIMLANE_EXEC` (`EXECUTION_`) USING BTREE,
  CONSTRAINT `jbpm4_swimlane_ibfk_1` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_swimlane
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_task`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` char(1) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `NAME_` varchar(255) DEFAULT NULL,
  `DESCR_` longtext,
  `STATE_` varchar(255) DEFAULT NULL,
  `SUSPHISTSTATE_` varchar(255) DEFAULT NULL,
  `ASSIGNEE_` varchar(255) DEFAULT NULL,
  `FORM_` varchar(255) DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_` datetime DEFAULT NULL,
  `DUEDATE_` datetime DEFAULT NULL,
  `PROGRESS_` int(11) DEFAULT NULL,
  `SIGNALLING_` bit(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(255) DEFAULT NULL,
  `ACTIVITY_NAME_` varchar(255) DEFAULT NULL,
  `HASVARS_` bit(1) DEFAULT NULL,
  `SUPERTASK_` bigint(20) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `PROCINST_` bigint(20) DEFAULT NULL,
  `SWIMLANE_` bigint(20) DEFAULT NULL,
  `TASKDEFNAME_` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_TASK_SUPERTASK` (`SUPERTASK_`) USING BTREE,
  KEY `FK_TASK_SWIML` (`SWIMLANE_`) USING BTREE,
  KEY `FK_TASK_SUPERTASK` (`SUPERTASK_`) USING BTREE,
  CONSTRAINT `jbpm4_task_ibfk_1` FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`),
  CONSTRAINT `jbpm4_task_ibfk_2` FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_task
-- ----------------------------

-- ----------------------------
-- Table structure for `jbpm4_variable`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
  `DBID_` bigint(20) NOT NULL AUTO_INCREMENT,
  `CLASS_` varchar(255) NOT NULL,
  `DBVERSION_` int(11) NOT NULL,
  `KEY_` varchar(255) DEFAULT NULL,
  `CONVERTER_` varchar(255) DEFAULT NULL,
  `HIST_` bit(1) DEFAULT NULL,
  `EXECUTION_` bigint(20) DEFAULT NULL,
  `TASK_` bigint(20) DEFAULT NULL,
  `DATE_VALUE_` datetime DEFAULT NULL,
  `DOUBLE_VALUE_` double DEFAULT NULL,
  `LONG_VALUE_` bigint(20) DEFAULT NULL,
  `STRING_VALUE_` varchar(255) DEFAULT NULL,
  `TEXT_VALUE_` longtext,
  `LOB_` bigint(20) DEFAULT NULL,
  `EXESYS_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`DBID_`),
  KEY `IDX_VAR_EXESYS` (`EXESYS_`) USING BTREE,
  KEY `IDX_VAR_TASK` (`TASK_`) USING BTREE,
  KEY `IDX_VAR_EXECUTION` (`EXECUTION_`) USING BTREE,
  KEY `IDX_VAR_LOB` (`LOB_`) USING BTREE,
  KEY `FK_VAR_LOB` (`LOB_`) USING BTREE,
  KEY `FK_VAR_EXECUTION` (`EXECUTION_`) USING BTREE,
  KEY `FK_VAR_EXESYS` (`EXESYS_`) USING BTREE,
  KEY `FK_VAR_TASK` (`TASK_`) USING BTREE,
  CONSTRAINT `jbpm4_variable_ibfk_1` FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `jbpm4_variable_ibfk_2` FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`),
  CONSTRAINT `jbpm4_variable_ibfk_3` FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`),
  CONSTRAINT `jbpm4_variable_ibfk_4` FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jbpm4_variable
-- ----------------------------

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
  `jobId` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobName` varchar(128) NOT NULL COMMENT '职位名称',
  `depId` bigint(20) NOT NULL COMMENT '所属部门',
  `memo` varchar(512) DEFAULT NULL COMMENT '备注',
  `delFlag` smallint(6) NOT NULL DEFAULT '0' COMMENT '0=未删除\r\n            1=删除',
  PRIMARY KEY (`jobId`),
  KEY `FK_JB_R_DPT` (`depId`) USING BTREE,
  CONSTRAINT `job_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job
-- ----------------------------

-- ----------------------------
-- Table structure for `job_change`
-- ----------------------------
DROP TABLE IF EXISTS `job_change`;
CREATE TABLE `job_change` (
  `changeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `profileId` bigint(20) NOT NULL,
  `profileNo` varchar(64) NOT NULL,
  `userName` varchar(64) DEFAULT NULL,
  `orgJobId` bigint(20) NOT NULL,
  `orgJobName` varchar(64) NOT NULL,
  `newJobId` bigint(20) NOT NULL,
  `newJobName` varchar(64) NOT NULL,
  `orgStandardId` bigint(20) DEFAULT NULL COMMENT '原薪酬标准单ID',
  `orgStandardNo` varchar(64) DEFAULT NULL,
  `orgStandardName` varchar(64) DEFAULT NULL,
  `orgDepId` bigint(20) DEFAULT NULL,
  `orgDepName` varchar(128) DEFAULT NULL,
  `orgTotalMoney` decimal(18,2) DEFAULT NULL,
  `newStandardId` bigint(20) DEFAULT NULL,
  `newStandardNo` varchar(64) DEFAULT NULL,
  `newStandardName` varchar(64) DEFAULT NULL,
  `newDepId` bigint(20) DEFAULT NULL,
  `newDepName` varchar(128) DEFAULT NULL,
  `newTotalMoney` decimal(18,2) DEFAULT NULL,
  `changeReason` varchar(1024) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  `regName` varchar(64) DEFAULT NULL,
  `checkName` varchar(64) DEFAULT NULL,
  `checkTime` datetime DEFAULT NULL,
  `checkOpinion` varchar(512) DEFAULT NULL,
  `status` smallint(6) DEFAULT NULL COMMENT '状态\r\n            \r\n            -1=草稿\r\n            0=提交审批\r\n            1=通过审批\r\n            2=未通过审批\r\n            ',
  `memo` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`changeId`),
  KEY `FK_JBC_R_JBN` (`newJobId`) USING BTREE,
  KEY `FK_JBC_R_JBO` (`orgJobId`) USING BTREE,
  CONSTRAINT `job_change_ibfk_1` FOREIGN KEY (`newJobId`) REFERENCES `job` (`jobId`),
  CONSTRAINT `job_change_ibfk_2` FOREIGN KEY (`orgJobId`) REFERENCES `job` (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job_change
-- ----------------------------

-- ----------------------------
-- Table structure for `job_salary_relation`
-- ----------------------------
DROP TABLE IF EXISTS `job_salary_relation`;
CREATE TABLE `job_salary_relation` (
  `relationId` bigint(20) NOT NULL AUTO_INCREMENT,
  `depId` bigint(20) NOT NULL COMMENT '所属部门',
  `jobId` bigint(20) NOT NULL COMMENT '所属岗位',
  `standardId` bigint(20) NOT NULL COMMENT '所属薪资标准',
  `empCount` int(6) DEFAULT '0' COMMENT '人数',
  `totalMoney` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '人数',
  `deleteFlag` smallint(6) NOT NULL DEFAULT '0' COMMENT '0=未删除\r\n            1=删除',
  `onEmpCount` int(6) DEFAULT NULL,
  PRIMARY KEY (`relationId`),
  KEY `FK_JSR_R_DPT` (`depId`) USING BTREE,
  KEY `jsr_ibfk_2` (`jobId`) USING BTREE,
  KEY `jsr_ibfk_3` (`standardId`) USING BTREE,
  CONSTRAINT `job_salary_relation_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`),
  CONSTRAINT `job_salary_relation_ibfk_2` FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`),
  CONSTRAINT `job_salary_relation_ibfk_3` FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of job_salary_relation
-- ----------------------------

-- ----------------------------
-- Table structure for `leader_read`
-- ----------------------------
DROP TABLE IF EXISTS `leader_read`;
CREATE TABLE `leader_read` (
  `readId` bigint(20) NOT NULL AUTO_INCREMENT,
  `leaderName` varchar(64) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `leaderOpinion` varchar(128) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `archivesId` bigint(20) DEFAULT NULL,
  `depLevel` int(11) DEFAULT NULL,
  `depName` varchar(128) DEFAULT NULL,
  `isPass` smallint(6) NOT NULL COMMENT '0=尚未批\r\n            1=审批通过\r\n            2=审批未通过',
  `checkName` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`readId`),
  KEY `FK_LDR_R_ARV` (`archivesId`) USING BTREE,
  CONSTRAINT `leader_read_ibfk_1` FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='领导批示';

-- ----------------------------
-- Records of leader_read
-- ----------------------------

-- ----------------------------
-- Table structure for `leave_leader_read`
-- ----------------------------
DROP TABLE IF EXISTS `leave_leader_read`;
CREATE TABLE `leave_leader_read` (
  `readId` bigint(20) NOT NULL AUTO_INCREMENT,
  `leaderName` varchar(64) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `leaderOpinion` varchar(128) DEFAULT NULL,
  `createtime` datetime NOT NULL,
  `dateId` bigint(20) DEFAULT NULL,
  `depLevel` int(11) DEFAULT NULL,
  `depName` varchar(128) DEFAULT NULL,
  `isPass` smallint(6) NOT NULL COMMENT '0=尚未批\r\n            1=审批通过\r\n            2=审批未通过',
  `checkName` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`readId`),
  KEY `FK_LLDR_R_ARV` (`dateId`) USING BTREE,
  CONSTRAINT `leave_leader_read_ibfk_1` FOREIGN KEY (`dateId`) REFERENCES `errands_register` (`dateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请假领导批示';

-- ----------------------------
-- Records of leave_leader_read
-- ----------------------------

-- ----------------------------
-- Table structure for `leave_type`
-- ----------------------------
DROP TABLE IF EXISTS `leave_type`;
CREATE TABLE `leave_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  `processDefId` bigint(20) DEFAULT NULL,
  `processDefName` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave_type
-- ----------------------------
INSERT INTO leave_type VALUES ('1', '一天内', '12', '请假-短');
INSERT INTO leave_type VALUES ('2', '1-3天', '13', '请假-中');
INSERT INTO leave_type VALUES ('3', '3天以上', '14', '请假-长');

-- ----------------------------
-- Table structure for `mail`
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
  `mailId` bigint(20) NOT NULL AUTO_INCREMENT,
  `sender` varchar(32) NOT NULL,
  `senderId` bigint(20) NOT NULL,
  `importantFlag` smallint(6) NOT NULL COMMENT '1=一般\r\n            2=重要\r\n            3=非常重要',
  `sendTime` datetime NOT NULL,
  `content` text NOT NULL COMMENT '邮件内容',
  `subject` varchar(256) NOT NULL COMMENT '邮件标题',
  `copyToNames` varchar(1000) DEFAULT NULL COMMENT '抄送人姓名列表',
  `copyToIDs` varchar(1000) DEFAULT NULL COMMENT '抄送人ID列表\r\n            用'',''分开',
  `recipientNames` varchar(1000) NOT NULL COMMENT '收件人姓名列表',
  `recipientIDs` varchar(1000) NOT NULL COMMENT '收件人ID列表\r\n            用'',''分隔',
  `mailStatus` smallint(6) NOT NULL COMMENT '邮件状态\r\n            1=正式邮件\r\n            0=草稿邮件',
  `fileIds` varchar(500) DEFAULT NULL COMMENT '附件Ids，多个附件的ID，通过,分割',
  `filenames` varchar(500) DEFAULT NULL COMMENT '附件名称列表，通过,进行分割',
  PRIMARY KEY (`mailId`),
  KEY `FK_ML_R_AU` (`senderId`) USING BTREE,
  CONSTRAINT `mail_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='邮件';

-- ----------------------------
-- Records of mail
-- ----------------------------

-- ----------------------------
-- Table structure for `mail_attach`
-- ----------------------------
DROP TABLE IF EXISTS `mail_attach`;
CREATE TABLE `mail_attach` (
  `mailId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY (`mailId`,`fileId`),
  KEY `FK_MA_R_FA` (`fileId`) USING BTREE,
  CONSTRAINT `mail_attach_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `mail_attach_ibfk_2` FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_attach
-- ----------------------------

-- ----------------------------
-- Table structure for `mail_box`
-- ----------------------------
DROP TABLE IF EXISTS `mail_box`;
CREATE TABLE `mail_box` (
  `boxId` bigint(20) NOT NULL AUTO_INCREMENT,
  `mailId` bigint(20) NOT NULL,
  `folderId` bigint(20) NOT NULL,
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `sendTime` datetime NOT NULL,
  `delFlag` smallint(6) NOT NULL COMMENT 'del=1则代表删除',
  `readFlag` smallint(6) NOT NULL,
  `replyFlag` smallint(6) NOT NULL,
  `note` varchar(256) DEFAULT NULL COMMENT 'note',
  PRIMARY KEY (`boxId`),
  KEY `FK_MB_R_AU` (`userId`) USING BTREE,
  KEY `FK_MB_R_FD` (`folderId`) USING BTREE,
  KEY `FK_MB_R_ML` (`mailId`) USING BTREE,
  CONSTRAINT `mail_box_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `mail_box_ibfk_2` FOREIGN KEY (`folderId`) REFERENCES `mail_folder` (`folderId`),
  CONSTRAINT `mail_box_ibfk_3` FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='收件箱';

-- ----------------------------
-- Records of mail_box
-- ----------------------------

-- ----------------------------
-- Table structure for `mail_folder`
-- ----------------------------
DROP TABLE IF EXISTS `mail_folder`;
CREATE TABLE `mail_folder` (
  `folderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件夹编号',
  `userId` bigint(20) DEFAULT NULL COMMENT '主键',
  `folderName` varchar(128) NOT NULL COMMENT '文件夹名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父目录',
  `depLevel` int(11) NOT NULL COMMENT '目录层',
  `path` varchar(256) DEFAULT NULL,
  `isPublic` smallint(6) NOT NULL COMMENT '1=表示共享，则所有的员工均可以使用该文件夹\r\n            0=私人文件夹',
  `folderType` smallint(6) NOT NULL COMMENT '文件夹类型\r\n            1=收信箱\r\n            2=发信箱\r\n            3=草稿箱\r\n            4=删除箱\r\n            10=其他',
  PRIMARY KEY (`folderId`),
  KEY `FK_FD_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `mail_folder_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mail_folder
-- ----------------------------
INSERT INTO mail_folder VALUES ('1', null, '收件箱', '0', '1', '0.1.', '1', '1');
INSERT INTO mail_folder VALUES ('2', null, '发件箱', '0', '1', '0.2.', '1', '2');
INSERT INTO mail_folder VALUES ('3', null, '草稿箱', '0', '1', '0.3.', '1', '3');
INSERT INTO mail_folder VALUES ('4', null, '垃圾箱', '0', '1', '0.4.', '1', '4');

-- ----------------------------
-- Table structure for `meeting`
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
  `mettingId` bigint(20) NOT NULL AUTO_INCREMENT,
  `holdTime` datetime DEFAULT NULL,
  `holdLocation` varchar(128) DEFAULT NULL,
  `meetingName` varchar(128) DEFAULT NULL,
  `attendUsers` varchar(128) DEFAULT NULL,
  `holdDep` varchar(128) DEFAULT NULL,
  `holdDepId` bigint(20) DEFAULT NULL,
  `shortDesc` varchar(256) DEFAULT NULL,
  `isFeedback` smallint(6) NOT NULL,
  `summary` text,
  PRIMARY KEY (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meeting
-- ----------------------------

-- ----------------------------
-- Table structure for `meeting_attend`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attend`;
CREATE TABLE `meeting_attend` (
  `attendId` int(11) NOT NULL AUTO_INCREMENT,
  `mettingId` bigint(20) NOT NULL,
  `userName` varchar(64) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `depName` varchar(100) DEFAULT NULL,
  `depId` bigint(20) DEFAULT NULL,
  `attendType` smallint(6) NOT NULL DEFAULT '0' COMMENT '参与类型\r\n            0=user\r\n            1=department',
  `feedback` varchar(1024) DEFAULT NULL,
  `signTime` datetime DEFAULT NULL,
  `signName` varchar(32) NOT NULL,
  PRIMARY KEY (`attendId`),
  KEY `FK_MTA_R_MT` (`mettingId`) USING BTREE,
  CONSTRAINT `meeting_attend_ibfk_1` FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会议参与部门或人员';

-- ----------------------------
-- Records of meeting_attend
-- ----------------------------

-- ----------------------------
-- Table structure for `meeting_file`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_file`;
CREATE TABLE `meeting_file` (
  `mettingId` bigint(20) NOT NULL,
  `fileId` bigint(20) NOT NULL,
  PRIMARY KEY (`mettingId`,`fileId`),
  KEY `FK_MF_R_FA` (`fileId`) USING BTREE,
  CONSTRAINT `meeting_file_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `meeting_file_ibfk_2` FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meeting_file
-- ----------------------------

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `newsId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `typeId` bigint(20) NOT NULL,
  `subjectIcon` varchar(128) DEFAULT NULL,
  `subject` varchar(128) NOT NULL COMMENT '新闻标题',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `replyCounts` int(11) DEFAULT NULL,
  `viewCounts` int(11) DEFAULT NULL COMMENT '浏览数',
  `issuer` varchar(32) NOT NULL,
  `content` text NOT NULL COMMENT '内容',
  `updateTime` datetime NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '\r\n            0=待审核\r\n            1=审核通过',
  `isDeskImage` smallint(6) DEFAULT NULL COMMENT '是否为图片新闻',
  PRIMARY KEY (`newsId`),
  KEY `FK_NS_R_NT` (`typeId`) USING BTREE,
  CONSTRAINT `news_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `news_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻';

-- ----------------------------
-- Records of news
-- ----------------------------

-- ----------------------------
-- Table structure for `news_comment`
-- ----------------------------
DROP TABLE IF EXISTS `news_comment`;
CREATE TABLE `news_comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `newsId` bigint(20) NOT NULL,
  `content` varchar(500) NOT NULL,
  `createtime` datetime NOT NULL,
  `fullname` varchar(32) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`commentId`),
  KEY `FK_NC_R_AU` (`userId`) USING BTREE,
  KEY `FK_NC_R_NS` (`newsId`) USING BTREE,
  CONSTRAINT `news_comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `news_comment_ibfk_2` FOREIGN KEY (`newsId`) REFERENCES `news` (`newsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of news_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `news_type`
-- ----------------------------
DROP TABLE IF EXISTS `news_type`;
CREATE TABLE `news_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL,
  `sn` int(11) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='新闻类型';

-- ----------------------------
-- Records of news_type
-- ----------------------------

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `noticeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `postName` varchar(128) NOT NULL,
  `noticeTitle` varchar(128) NOT NULL,
  `noticeContent` varchar(3000) DEFAULT NULL,
  `effectiveDate` date DEFAULT NULL,
  `expirationDate` date DEFAULT NULL,
  `state` smallint(6) NOT NULL,
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='公告';

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for `noticenews_doc`
-- ----------------------------
DROP TABLE IF EXISTS `noticenews_doc`;
CREATE TABLE `noticenews_doc` (
  `docId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fileId` bigint(20) DEFAULT NULL,
  `noticeNewsId` bigint(20) DEFAULT NULL,
  `creator` varchar(64) DEFAULT NULL COMMENT '拟稿人',
  `creatorId` bigint(20) DEFAULT NULL COMMENT '拟稿人ID',
  `docName` varchar(128) NOT NULL COMMENT '文档名称',
  `docPath` varchar(128) NOT NULL COMMENT '文档路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`docId`),
  KEY `FK_NNSC_R_FA` (`fileId`) USING BTREE,
  KEY `FK_NNSD_R_NNS` (`noticeNewsId`) USING BTREE,
  CONSTRAINT `noticenews_doc_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `noticenews_doc_ibfk_2` FOREIGN KEY (`noticeNewsId`) REFERENCES `notice_news` (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of noticenews_doc
-- ----------------------------
INSERT INTO noticenews_doc VALUES ('30', '26', '1', null, null, 'HibernateNote.txt', 'noticenew/201107/85cd51ee8db74d6f93968ff543a7f27e.txt', '2011-07-29 15:01:14', '2011-07-29 15:01:14');
INSERT INTO noticenews_doc VALUES ('31', '31', '2', null, null, 'HibernateNote.txt', 'noticenew/201108/8ffb052592fd4b2d8a1a9b8d0d597663.txt', '2011-08-01 14:05:14', '2011-08-01 14:05:14');
INSERT INTO noticenews_doc VALUES ('32', '36', '3', null, null, 'leave_type.sql', 'noticenew/201108/cfd4aecc365c465a8717ce86a9cf22c5.sql', '2011-08-02 13:48:36', '2011-08-02 13:48:36');

-- ----------------------------
-- Table structure for `notice_news`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news`;
CREATE TABLE `notice_news` (
  `newsId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `typeId` bigint(20) NOT NULL,
  `subjectIcon` varchar(128) DEFAULT NULL,
  `subject` varchar(128) NOT NULL COMMENT '新闻标题',
  `author` varchar(32) NOT NULL COMMENT '作者',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `replyCounts` int(11) DEFAULT NULL,
  `viewCounts` int(11) DEFAULT NULL COMMENT '浏览数',
  `issuer` varchar(32) NOT NULL,
  `content` text NOT NULL COMMENT '内容',
  `updateTime` datetime NOT NULL,
  `status` smallint(6) NOT NULL COMMENT '\r\n            0=待审核\r\n            1=审核通过',
  `isDeskImage` smallint(6) DEFAULT NULL COMMENT '是否为图片新闻',
  `isAll` smallint(6) DEFAULT '0' COMMENT '0非全部，1全部人员',
  `authorId` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  PRIMARY KEY (`newsId`),
  KEY `FK_NS_R_NT` (`typeId`) USING BTREE,
  CONSTRAINT `notice_news_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `notice_news_type` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='通知（新）';

-- ----------------------------
-- Records of notice_news
-- ----------------------------
INSERT INTO notice_news VALUES ('1', '1', 'info/noticeNews/201107/43d7fa4b20a94cd2b82544770e981cd2.jpg', '测试通知', '超级管理员', '2011-07-27 11:49:29', '3', '100', '超级管理员', '<p>你好啊，美女<a href=\"http:///userfiles/file/%E5%9F%BA%E4%BA%8E%E8%A7%92%E8%89%B2%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6%E7%9A%84%E7%90%86%E8%AE%BA%E4%B8%8E%E5%BA%94%E7%94%A8%E7%A0%94%E7%A9%B6.pdf\">/userfiles/file/%E5%9F%BA%E4%BA%8E%E8%A7%92%E8%89%B2%E8%AE%BF%E9%97%AE%E6%8E%A7%E5%88%B6%E7%9A%84%E7%90%86%E8%AE%BA%E4%B8%8E%E5%BA%94%E7%94%A8%E7%A0%94%E7%A9%B6.pdf</a></p>', '2011-07-29 15:01:14', '1', '1', '1', '1', '1');
INSERT INTO notice_news VALUES ('2', '2', '', '你好啊', '超级管理员', '2011-08-01 14:05:14', '0', '2', '超级管理员', '<p>啊沙发沙发</p>', '2011-08-01 14:05:14', '1', '0', '1', '1', '1');
INSERT INTO notice_news VALUES ('3', '1', '', '你好', '超级管理员', '2011-08-02 13:48:36', '0', '1', '超级管理员', '', '2011-08-02 13:48:36', '1', '0', '0', '1', '1');

-- ----------------------------
-- Table structure for `notice_news_comment`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news_comment`;
CREATE TABLE `notice_news_comment` (
  `commentId` bigint(20) NOT NULL AUTO_INCREMENT,
  `newsId` bigint(20) NOT NULL,
  `content` varchar(500) NOT NULL,
  `createtime` datetime NOT NULL,
  `fullname` varchar(32) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `flag` smallint(6) NOT NULL,
  PRIMARY KEY (`commentId`),
  KEY `FK_NC_R_AU` (`userId`) USING BTREE,
  KEY `FK_NC_R_NS` (`newsId`) USING BTREE,
  CONSTRAINT `notice_news_comment_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `notice_news_comment_ibfk_2` FOREIGN KEY (`newsId`) REFERENCES `notice_news` (`newsId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice_news_comment
-- ----------------------------
INSERT INTO notice_news_comment VALUES ('17', '1', 'nihao ', '2011-07-29 16:58:45', '超级管理员', '1', '1');
INSERT INTO notice_news_comment VALUES ('20', '1', '1', '2011-07-29 17:49:27', '超级管理员', '1', '2');
INSERT INTO notice_news_comment VALUES ('21', '2', '1', '2011-08-01 14:06:24', '超级管理员', '1', '2');
INSERT INTO notice_news_comment VALUES ('22', '3', '1', '2011-08-02 13:48:36', '超级管理员', '1', '2');

-- ----------------------------
-- Table structure for `notice_news_type`
-- ----------------------------
DROP TABLE IF EXISTS `notice_news_type`;
CREATE TABLE `notice_news_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL,
  `sn` int(11) NOT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='通知（新）类型';

-- ----------------------------
-- Records of notice_news_type
-- ----------------------------
INSERT INTO notice_news_type VALUES ('1', '测试类别', '1');
INSERT INTO notice_news_type VALUES ('2', '测试类别2', '2');

-- ----------------------------
-- Table structure for `office_goods`
-- ----------------------------
DROP TABLE IF EXISTS `office_goods`;
CREATE TABLE `office_goods` (
  `goodsId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(20) NOT NULL COMMENT '所属分类',
  `goodsName` varchar(128) NOT NULL COMMENT '物品名称',
  `goodsNo` varchar(128) NOT NULL COMMENT '编号',
  `specifications` varchar(256) NOT NULL COMMENT '规格',
  `unit` varchar(64) NOT NULL COMMENT '计量单位',
  `isWarning` smallint(6) NOT NULL COMMENT '是否启用库存警示',
  `notes` varchar(500) DEFAULT NULL COMMENT '备注',
  `stockCounts` int(11) NOT NULL COMMENT '库存总数',
  `warnCounts` int(11) NOT NULL COMMENT '最低库存数',
  PRIMARY KEY (`goodsId`),
  KEY `FK_OG_R_OGT` (`typeId`) USING BTREE,
  CONSTRAINT `office_goods_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `office_goods_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品';

-- ----------------------------
-- Records of office_goods
-- ----------------------------

-- ----------------------------
-- Table structure for `office_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `office_goods_type`;
CREATE TABLE `office_goods_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='办公用品类型';

-- ----------------------------
-- Records of office_goods_type
-- ----------------------------

-- ----------------------------
-- Table structure for `phone_book`
-- ----------------------------
DROP TABLE IF EXISTS `phone_book`;
CREATE TABLE `phone_book` (
  `phoneId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(128) NOT NULL,
  `title` varchar(32) NOT NULL COMMENT '先生\r\n            女士\r\n            小姐',
  `birthday` datetime DEFAULT NULL,
  `nickName` varchar(32) DEFAULT NULL,
  `duty` varchar(50) DEFAULT NULL,
  `spouse` varchar(32) DEFAULT NULL,
  `childs` varchar(40) DEFAULT NULL,
  `companyName` varchar(100) DEFAULT NULL,
  `companyAddress` varchar(128) DEFAULT NULL,
  `companyPhone` varchar(32) DEFAULT NULL,
  `companyFax` varchar(32) DEFAULT NULL,
  `homeAddress` varchar(128) DEFAULT NULL,
  `homeZip` varchar(12) DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `QQ` varchar(64) DEFAULT NULL,
  `MSN` varchar(128) DEFAULT NULL,
  `note` varchar(500) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `groupId` bigint(20) DEFAULT NULL,
  `isShared` smallint(6) NOT NULL,
  PRIMARY KEY (`phoneId`),
  KEY `FK_PB_R_AU` (`userId`) USING BTREE,
  KEY `FK_PB_R_PG` (`groupId`) USING BTREE,
  CONSTRAINT `phone_book_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `phone_book_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `phone_group` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通讯簿';

-- ----------------------------
-- Records of phone_book
-- ----------------------------

-- ----------------------------
-- Table structure for `phone_group`
-- ----------------------------
DROP TABLE IF EXISTS `phone_group`;
CREATE TABLE `phone_group` (
  `groupId` bigint(20) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(128) NOT NULL COMMENT '分组名称',
  `isShared` smallint(6) NOT NULL COMMENT '1=共享\r\n            0=私有',
  `SN` int(11) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`groupId`),
  KEY `FK_PG_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `phone_group_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of phone_group
-- ----------------------------

-- ----------------------------
-- Table structure for `plan_attend`
-- ----------------------------
DROP TABLE IF EXISTS `plan_attend`;
CREATE TABLE `plan_attend` (
  `attendId` bigint(20) NOT NULL AUTO_INCREMENT,
  `depId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `planId` bigint(20) NOT NULL,
  `isDep` smallint(6) NOT NULL COMMENT '是否为部门',
  `isPrimary` smallint(6) DEFAULT NULL COMMENT '是否负责人',
  PRIMARY KEY (`attendId`),
  KEY `FK_PAD_R_DT` (`depId`) USING BTREE,
  KEY `FK_PAD_R_UA` (`userId`) USING BTREE,
  KEY `FK_PAD_R_WP` (`planId`) USING BTREE,
  CONSTRAINT `plan_attend_ibfk_1` FOREIGN KEY (`depId`) REFERENCES `department` (`depId`),
  CONSTRAINT `plan_attend_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `plan_attend_ibfk_3` FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_attend
-- ----------------------------

-- ----------------------------
-- Table structure for `plan_file`
-- ----------------------------
DROP TABLE IF EXISTS `plan_file`;
CREATE TABLE `plan_file` (
  `fileId` bigint(20) NOT NULL,
  `planId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`planId`),
  KEY `FK_PA_R_WP` (`planId`) USING BTREE,
  CONSTRAINT `plan_file_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `plan_file_ibfk_2` FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of plan_file
-- ----------------------------

-- ----------------------------
-- Table structure for `plan_type`
-- ----------------------------
DROP TABLE IF EXISTS `plan_type`;
CREATE TABLE `plan_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(128) NOT NULL COMMENT '类别名称',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计划类型';

-- ----------------------------
-- Records of plan_type
-- ----------------------------

-- ----------------------------
-- Table structure for `process_form`
-- ----------------------------
DROP TABLE IF EXISTS `process_form`;
CREATE TABLE `process_form` (
  `formId` bigint(20) NOT NULL AUTO_INCREMENT,
  `runId` bigint(20) NOT NULL COMMENT '所属运行流程',
  `activityName` varchar(128) NOT NULL COMMENT '活动或任务名称',
  `sn` int(11) NOT NULL DEFAULT '1' COMMENT '表单序号代表该流程任务执行经过的次数,如第一次经过时为1,第二次再次经过时变为2,\r\n            主要用于标识某一任务在流程中可能被不断回退.',
  `createtime` datetime NOT NULL,
  `creatorId` bigint(20) NOT NULL,
  `creatorName` varchar(64) NOT NULL,
  PRIMARY KEY (`formId`),
  KEY `FK_PF_R_PR` (`runId`) USING BTREE,
  CONSTRAINT `process_form_ibfk_1` FOREIGN KEY (`runId`) REFERENCES `process_run` (`runId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流程表单\r\n存储保存在运行中的流程表单数据';

-- ----------------------------
-- Records of process_form
-- ----------------------------

-- ----------------------------
-- Table structure for `process_run`
-- ----------------------------
DROP TABLE IF EXISTS `process_run`;
CREATE TABLE `process_run` (
  `runId` bigint(20) NOT NULL AUTO_INCREMENT,
  `subject` varchar(256) NOT NULL COMMENT '标题\r\n            一般为流程名称＋格式化的时间',
  `creator` varchar(128) DEFAULT NULL COMMENT '创建人',
  `userId` bigint(20) NOT NULL COMMENT '所属用户',
  `defId` bigint(20) NOT NULL COMMENT '所属流程定义',
  `piId` varchar(64) DEFAULT NULL COMMENT '流程实例ID',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `runStatus` smallint(6) NOT NULL COMMENT '0=尚未启动\r\n            1=已经启动流程\r\n            2=运行结束',
  PRIMARY KEY (`runId`),
  KEY `FK_PR_R_AU` (`userId`) USING BTREE,
  KEY `FK_PR_R_PD` (`defId`) USING BTREE,
  CONSTRAINT `process_run_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `process_run_ibfk_2` FOREIGN KEY (`defId`) REFERENCES `pro_definition` (`defId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='运行中的流程';

-- ----------------------------
-- Records of process_run
-- ----------------------------

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `productId` bigint(20) NOT NULL AUTO_INCREMENT,
  `productName` varchar(128) NOT NULL COMMENT '产品名称',
  `productModel` varchar(128) DEFAULT NULL COMMENT '产品型号',
  `unit` varchar(128) DEFAULT NULL COMMENT '计量单位',
  `costPrice` decimal(18,2) DEFAULT NULL COMMENT '成本价',
  `salesPrice` decimal(18,2) DEFAULT NULL COMMENT '出售价',
  `productDesc` varchar(512) DEFAULT NULL COMMENT '产品描述',
  `providerId` bigint(20) NOT NULL COMMENT '所属供应商',
  `createtime` datetime NOT NULL COMMENT '收录时间',
  `updatetime` datetime NOT NULL,
  PRIMARY KEY (`productId`),
  KEY `FK_PD_R_PUT` (`providerId`) USING BTREE,
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`providerId`) REFERENCES `provider` (`providerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商产品';

-- ----------------------------
-- Records of product
-- ----------------------------

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `projectId` bigint(20) NOT NULL AUTO_INCREMENT,
  `projectName` varchar(128) NOT NULL COMMENT '项目名称',
  `projectNo` varchar(64) NOT NULL COMMENT '项目编号',
  `reqDesc` text COMMENT '需求描述',
  `isContract` smallint(6) NOT NULL COMMENT '是否签订合同',
  `fullname` varchar(32) NOT NULL COMMENT '联系人姓名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `fax` varchar(32) DEFAULT NULL COMMENT '传真',
  `otherContacts` varchar(128) DEFAULT NULL COMMENT '其他联系方式',
  `customerId` bigint(20) NOT NULL COMMENT '所属客户',
  `userId` bigint(20) NOT NULL COMMENT '业务人员',
  PRIMARY KEY (`projectId`),
  KEY `FK_PR_R_CS` (`customerId`) USING BTREE,
  KEY `FK_PT_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `project_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`),
  CONSTRAINT `project_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目信息';

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for `project_file`
-- ----------------------------
DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
  `fileId` bigint(20) NOT NULL,
  `projectId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`projectId`),
  KEY `FK_PF_R_PT` (`projectId`) USING BTREE,
  CONSTRAINT `project_file_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `project_file_ibfk_2` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目附件';

-- ----------------------------
-- Records of project_file
-- ----------------------------

-- ----------------------------
-- Table structure for `provider`
-- ----------------------------
DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
  `providerId` bigint(20) NOT NULL AUTO_INCREMENT,
  `providerName` varchar(128) NOT NULL COMMENT '供应商名称',
  `contactor` varchar(128) NOT NULL COMMENT '联系人',
  `phone` varchar(32) NOT NULL COMMENT '电话',
  `fax` varchar(32) DEFAULT NULL COMMENT '传真',
  `site` varchar(128) DEFAULT NULL COMMENT '网址',
  `email` varchar(128) DEFAULT NULL COMMENT '邮件',
  `address` varchar(128) NOT NULL COMMENT '地址',
  `zip` varchar(32) DEFAULT NULL COMMENT '邮编',
  `openBank` varchar(128) DEFAULT NULL COMMENT '开户行',
  `account` varchar(64) DEFAULT NULL COMMENT '帐号',
  `notes` varchar(500) DEFAULT NULL COMMENT '备注',
  `rank` int(11) DEFAULT NULL COMMENT '供应商等级\r\n            1=一级供应商\r\n            2＝二级供应商\r\n            3＝三级供应商\r\n            4＝四级供应商\r\n            ',
  PRIMARY KEY (`providerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商';

-- ----------------------------
-- Records of provider
-- ----------------------------

-- ----------------------------
-- Table structure for `pro_definition`
-- ----------------------------
DROP TABLE IF EXISTS `pro_definition`;
CREATE TABLE `pro_definition` (
  `defId` bigint(20) NOT NULL AUTO_INCREMENT,
  `typeId` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `name` varchar(256) NOT NULL COMMENT '流程的名称',
  `description` varchar(1024) DEFAULT NULL COMMENT '描述',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `deployId` varchar(64) NOT NULL COMMENT 'Jbpm 工作流id',
  `defXml` text COMMENT '流程定义XML',
  `drawDefXml` text,
  `isDefault` smallint(6) NOT NULL DEFAULT '0' COMMENT '是否缺省\r\n            1=是\r\n            0=否',
  PRIMARY KEY (`defId`),
  KEY `FK_PD_R_PT` (`typeId`) USING BTREE,
  CONSTRAINT `pro_definition_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `pro_type` (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='流程定义';

-- ----------------------------
-- Records of pro_definition
-- ----------------------------
INSERT INTO pro_definition VALUES ('4', '1', '发文流程', '发文流程', '2011-07-14 10:06:44', '4', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6426584402782899404\"><start name=\"开始\" g=\"251,4,58,58\"><transition name=\"流程启动\" to=\"发文核稿\" g=\"-24,-16\"/></start><task name=\"发文核稿\" g=\"238,105,89,36\"><transition name=\"核稿\" to=\"科室审核\" g=\"-24,-16\"/></task><task name=\"科室审核\" g=\"240,175,89,36\"><transition name=\"审核\" to=\"主管领导审批\" g=\"-24,-16\"/></task><task name=\"主管领导审批\" g=\"240,235,93,36\"><transition name=\"审批\" to=\"分管领导签发\" g=\"-24,-16\"/></task><task name=\"发文分发\" g=\"239,441,89,36\"><transition name=\"分发\" to=\"结束1\" g=\"-24,-16\"/></task><task name=\"发文校对\" g=\"241,368,89,36\"><transition name=\"校对\" to=\"发文分发\" g=\"-24,-16\"/></task><task name=\"分管领导签发\" g=\"239,303,96,36\"><transition name=\"签发\" to=\"发文校对\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"254,505,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"251\" y=\"4\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x53d1;&#x6587;&#x6838;&#x7a3f;\" x=\"238\" y=\"105\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x6838;&#x7a3f;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x79d1;&#x5ba4;&#x5ba1;&#x6838;\" x=\"240\" y=\"175\" w=\"79\" h=\"26\"><a><text><string>&#x79d1;&#x5ba4;&#x5ba1;&#x6838;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x5ba1;&#x6279;\" x=\"240\" y=\"235\" w=\"83\" h=\"26\"><a><text><string>&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x53d1;&#x6587;&#x5206;&#x53d1;\" x=\"239\" y=\"441\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x5206;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"6\" name=\"&#x53d1;&#x6587;&#x6821;&#x5bf9;\" x=\"241\" y=\"368\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x6821;&#x5bf9;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"7\" name=\"&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;\" x=\"239\" y=\"303\" w=\"86\" h=\"26\"><a><text><string>&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"8\" x=\"254\" y=\"505\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"9\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"275.68333333333334\" y=\"52.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"277.1222222222222\" y=\"104.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"a\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"b\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"c\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"d\" g=\"-24,-16\" name=\"&#x6838;&#x7a3f;\"><points><p colinear=\"true\" x=\"277.8885714285714\" y=\"131.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"279.1114285714286\" y=\"174.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"e\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"10\" g=\"-24,-16\" name=\"&#x5ba1;&#x6838;\"><points><p colinear=\"true\" x=\"279.9533333333333\" y=\"201.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"281.0466666666667\" y=\"234.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"13\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"281.6\" y=\"261.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"281.9\" y=\"302.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"14\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"15\"><Owner><task ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"16\" g=\"-24,-16\" name=\"&#x7b7e;&#x53d1;\"><points><p colinear=\"true\" x=\"281.68615384615384\" y=\"329.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"280.81384615384616\" y=\"367.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"17\"><Owner><task ref=\"7\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"18\"><Owner><task ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"19\" g=\"-24,-16\" name=\"&#x6821;&#x5bf9;\"><points><p colinear=\"true\" x=\"280.127397260274\" y=\"394.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"278.87260273972606\" y=\"440.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"1a\"><Owner><task ref=\"6\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1b\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"1c\" g=\"-24,-16\" name=\"&#x5206;&#x53d1;\"><points><p colinear=\"true\" x=\"278.40933333333334\" y=\"467.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"278.164\" y=\"504.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"1d\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1e\"><Owner><end ref=\"8\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('5', '1', '收文流程', '收文流程', '2011-07-14 10:15:22', '5', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd4993365972583614546\"><start name=\"开始\" g=\"261,14,58,58\"><transition name=\"提交拟办\" to=\"登记拟办\" g=\"-24,-16\"/></start><task name=\"登记拟办\" g=\"239,103,106,43\"><transition name=\"提交领导批示\" to=\"领导批示\" g=\"-24,-16\"/></task><task name=\"领导批示\" g=\"239,181,106,43\"><transition name=\"提交公文分发\" to=\"公文分发\" g=\"-24,-16\"/></task><task name=\"公文分发\" g=\"240,253,106,43\"><transition name=\"提交承办传阅\" to=\"承办传阅\" g=\"-24,-16\"/></task><task name=\"承办传阅\" g=\"242,339,106,43\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"266,427,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"261\" y=\"14\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x767b;&#x8bb0;&#x62df;&#x529e;\" x=\"239\" y=\"103\" w=\"96\" h=\"33\"><a><text><string>&#x767b;&#x8bb0;&#x62df;&#x529e;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x9886;&#x5bfc;&#x6279;&#x793a;\" x=\"239\" y=\"181\" w=\"96\" h=\"33\"><a><text><string>&#x9886;&#x5bfc;&#x6279;&#x793a;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x516c;&#x6587;&#x5206;&#x53d1;\" x=\"240\" y=\"253\" w=\"96\" h=\"33\"><a><text><string>&#x516c;&#x6587;&#x5206;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x627f;&#x529e;&#x4f20;&#x9605;\" x=\"242\" y=\"339\" w=\"96\" h=\"33\"><a><text><string>&#x627f;&#x529e;&#x4f20;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"6\" x=\"266\" y=\"427\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"7\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x62df;&#x529e;\"><points><p colinear=\"true\" x=\"285.60368098159506\" y=\"62.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"286.5803680981595\" y=\"102.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"8\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"a\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"b\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x9886;&#x5bfc;&#x6279;&#x793a;\"><points><p colinear=\"true\" x=\"287\" y=\"136.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"287\" y=\"180.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"c\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"e\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x516c;&#x6587;&#x5206;&#x53d1;\"><points><p colinear=\"true\" x=\"287.2375\" y=\"214.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"287.7625\" y=\"252.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"10\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"11\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x627f;&#x529e;&#x4f20;&#x9605;\"><points><p colinear=\"true\" x=\"288.39767441860465\" y=\"286.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"289.60232558139535\" y=\"338.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"13\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"14\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"290\" y=\"372.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"290\" y=\"426.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('6', '3', '出差申请', '出差申请', '2011-07-14 12:41:12', '6', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6578157620277996804\"><start name=\"开始\" g=\"191,29,58,58\"><transition name=\"提交审批\" to=\"经理审批\" g=\"-24,-16\"/></start><task name=\"经理审批\" g=\"155,158,137,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"196,278,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"191\" y=\"29\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"155\" y=\"158\" w=\"127\" h=\"40\"><a><text><string>&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"3\" x=\"196\" y=\"278\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"4\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"215.68880000000001\" y=\"77.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"217.92319999999998\" y=\"157.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"218.7491935483871\" y=\"198.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"219.7024193548387\" y=\"277.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('7', '3', '网上报账流程', '网上报账流程', '2011-07-14 13:32:31', '7', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd8252642282251937144\"><start name=\"申请\" g=\"222,9,58,58\"><transition name=\"申请\" to=\"部门领导审阅\" g=\"-24,-16\"/></start><task name=\"部门领导审阅\" g=\"203,98,98,46\"><transition name=\"提交分管领导\" to=\"分管领导盖章\" g=\"-24,-16\"/></task><task name=\"分管领导盖章\" g=\"203,172,99,47\"><transition name=\"答应\" to=\"打印\" g=\"-24,-16\"/></task><task name=\"打印\" g=\"204,248,98,45\"><transition name=\"财务处\" to=\"财务处\" g=\"-24,-16\"/></task><task name=\"财务处\" g=\"203,327,105,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"226,416,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"222\" y=\"9\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x90e8;&#x95e8;&#x9886;&#x5bfc;&#x5ba1;&#x9605;\" x=\"203\" y=\"98\" w=\"88\" h=\"36\"><a><text><string>&#x90e8;&#x95e8;&#x9886;&#x5bfc;&#x5ba1;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x76d6;&#x7ae0;\" x=\"203\" y=\"172\" w=\"89\" h=\"37\"><a><text><string>&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x76d6;&#x7ae0;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x6253;&#x5370;\" x=\"204\" y=\"248\" w=\"88\" h=\"35\"><a><text><string>&#x6253;&#x5370;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x8d22;&#x52a1;&#x5904;\" x=\"203\" y=\"327\" w=\"95\" h=\"40\"><a><text><string>&#x8d22;&#x52a1;&#x5904;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"6\" x=\"226\" y=\"416\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"7\" g=\"-24,-16\" name=\"&#x7533;&#x8bf7;\"><points><p colinear=\"true\" x=\"246.29638554216868\" y=\"57.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"246.77590361445783\" y=\"97.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"8\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"a\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"b\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x5206;&#x7ba1;&#x9886;&#x5bfc;\"><points><p colinear=\"true\" x=\"247.12483221476512\" y=\"134.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"247.37181208053693\" y=\"171.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"c\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"e\" g=\"-24,-16\" name=\"&#x7b54;&#x5e94;\"><points><p colinear=\"true\" x=\"247.62733333333333\" y=\"209.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"247.87933333333334\" y=\"247.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"10\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"11\" g=\"-24,-16\" name=\"&#x8d22;&#x52a1;&#x5904;\"><points><p colinear=\"true\" x=\"248.55521472392638\" y=\"283.6\" c1x=\"-0.8680981595092021\" c1y=\"-0.39999999999997726\" c2x=\"-0.8680981595092021\" c2y=\"-0.39999999999997726\"/><p colinear=\"true\" x=\"249.8680981595092\" y=\"326.4\" c1x=\"-0.8680981595092021\" c1y=\"-0.39999999999997726\" c2x=\"-0.8680981595092021\" c2y=\"-0.39999999999997726\"/></points><startConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"13\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"14\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"250.38924731182794\" y=\"367.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"250.13225806451612\" y=\"415.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('8', '3', '请假', '请假', '2011-07-14 13:48:06', '8', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6212082814169152003\"><start name=\"开始\" g=\"158,59,58,58\"><transition name=\"开始\" to=\"上级审批\" g=\"-24,-16\"/></start><task name=\"上级审批\" g=\"141,169,97,50\"><transition name=\"审批\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"161,277,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"158\" y=\"59\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x4e0a;&#x7ea7;&#x5ba1;&#x6279;\" x=\"141\" y=\"169\" w=\"87\" h=\"40\"><a><text><string>&#x4e0a;&#x7ea7;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"3\" x=\"161\" y=\"277\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"4\" g=\"-24,-16\" name=\"&#x5f00;&#x59cb;\"><points><p colinear=\"true\" x=\"182.58018867924528\" y=\"107.6\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/><p colinear=\"true\" x=\"184.01415094339623\" y=\"168.4\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"184.5919642857143\" y=\"209.60000000000002\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/><p colinear=\"true\" x=\"184.89017857142858\" y=\"276.4\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('9', '3', '通用', '通用', '2011-07-14 14:08:48', '9', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd8498713057619136655\"><start name=\"开始\" g=\"198,62,58,58\"><transition name=\"申请\" to=\"表单\" g=\"-24,-16\"/></start><end name=\"结束1\" g=\"204,298,58,58\"/><task name=\"表单\" g=\"182,180,97,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"198\" y=\"62\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><end id=\"2\" x=\"204\" y=\"298\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><task id=\"3\" name=\"&#x8868;&#x5355;\" x=\"182\" y=\"180\" w=\"87\" h=\"40\"><a><text><string>&#x8868;&#x5355;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"4\" g=\"-24,-16\" name=\"&#x7533;&#x8bf7;\"><points><p colinear=\"true\" x=\"222.75526315789475\" y=\"110.6\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/><p colinear=\"true\" x=\"224.86754385964912\" y=\"179.4\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"225.922131147541\" y=\"220.60000000000002\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/><p colinear=\"true\" x=\"227.49590163934425\" y=\"297.4\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('10', '2', '购车请款', '购车请款', '2011-07-14 14:18:50', '10', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd7096695693128483739\"><start name=\"输入购车预算金额\" g=\"169,45,58,58\"><transition name=\"提交\" to=\"副经理审批\" g=\"-24,-16\"/></start><task name=\"副经理审批\" g=\"132,137,135,50\"><transition name=\"审批\" to=\"总经理审批\" g=\"-24,-16\"/></task><task name=\"总经理审批\" g=\"132,227,139,50\"><transition name=\"审批\" to=\"结束2\" g=\"-24,-16\"/></task><end name=\"结束2\" g=\"176,329,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"169\" y=\"45\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x526f;&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"132\" y=\"137\" w=\"125\" h=\"40\"><a><text><string>&#x526f;&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x603b;&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"132\" y=\"227\" w=\"129\" h=\"40\"><a><text><string>&#x603b;&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"4\" x=\"176\" y=\"329\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;2\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"5\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;\"><points><p colinear=\"true\" x=\"193.4193181818182\" y=\"93.6\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"194.14886363636364\" y=\"136.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"6\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"7\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"8\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"9\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"194.95777777777778\" y=\"177.60000000000002\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"196.04222222222222\" y=\"226.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"a\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"b\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"c\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"197.1801886792453\" y=\"267.6\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"199.1877358490566\" y=\"328.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><end ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('11', '1', '发文流程-市局发文', '发文流程-市局发文', '2011-08-01 11:28:41', '12', '<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd9102991709241803919\"><task name=\"科室负责人核稿\" g=\"242,108,108,34\"><transition name=\"核稿\" to=\"分管或局领导签发\" g=\"-24,-16\"/></task><task name=\"分管或局领导签发\" g=\"241,179,112,35\"><transition name=\"签发\" to=\"办公室主任承办\" g=\"-24,-16\"/></task><task name=\"办公室主任承办\" g=\"244,250,105,35\"><transition name=\"承办\" to=\"编号盖章分发\" g=\"-24,-16\"/></task><task name=\"编号盖章分发\" g=\"249,313,93,32\"><transition name=\"分发\" to=\"结束3\" g=\"-24,-16\"/></task><end name=\"结束3\" g=\"267,379,58,58\"/><start name=\"开始\" g=\"267,11,58,58\"><transition name=\"流程启动\" to=\"科室负责人核稿\" g=\"-24,-16\"/></start></process>', '<drawing id=\"0\"><figures><task id=\"1\" name=\"&#x79d1;&#x5ba4;&#x8d1f;&#x8d23;&#x4eba;&#x6838;&#x7a3f;\" x=\"242\" y=\"108\" w=\"98\" h=\"24\"><a><text><string>&#x79d1;&#x5ba4;&#x8d1f;&#x8d23;&#x4eba;&#x6838;&#x7a3f;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"2\" name=\"&#x5206;&#x7ba1;\\&#x5c40;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;\" x=\"241\" y=\"179\" w=\"102\" h=\"25\"><a><text><string>&#x5206;&#x7ba1;\\&#x5c40;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x529e;&#x516c;&#x5ba4;&#x4e3b;&#x4efb;&#x627f;&#x529e;\" x=\"244\" y=\"250\" w=\"95\" h=\"25\"><a><text><string>&#x529e;&#x516c;&#x5ba4;&#x4e3b;&#x4efb;&#x627f;&#x529e;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x7f16;&#x53f7;&#x76d6;&#x7ae0;&#x5206;&#x53d1;\" x=\"249\" y=\"313\" w=\"83\" h=\"22\"><a><text><string>&#x7f16;&#x53f7;&#x76d6;&#x7ae0;&#x5206;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"5\" x=\"267\" y=\"379\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;3\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><start id=\"6\" x=\"267\" y=\"11\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><transition id=\"7\" g=\"-24,-16\" name=\"&#x6838;&#x7a3f;\"><points><p colinear=\"true\" x=\"291.17622377622376\" y=\"132.6\" c1x=\"69.94265734265741\" c1y=\"49.599999999999966\" c2x=\"69.94265734265741\" c2y=\"49.599999999999966\"/><p colinear=\"true\" x=\"291.8167832167832\" y=\"178.4\" c1x=\"69.94265734265741\" c1y=\"49.599999999999966\" c2x=\"69.94265734265741\" c2y=\"49.599999999999966\"/></points><startConnector><rConnector id=\"8\"><Owner><task ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"a\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"b\" g=\"-24,-16\" name=\"&#x7b7e;&#x53d1;\"><points><p colinear=\"true\" x=\"291.90774647887326\" y=\"204.6\" c1x=\"2.9317605633801804\" c1y=\"41.99999999999997\" c2x=\"2.9317605633801804\" c2y=\"41.99999999999997\"/><p colinear=\"true\" x=\"291.59225352112674\" y=\"249.4\" c1x=\"2.9317605633801804\" c1y=\"41.99999999999997\" c2x=\"2.9317605633801804\" c2y=\"41.99999999999997\"/></points><startConnector><rConnector id=\"c\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"e\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"291\" y=\"59.6\" c1x=\"39\" c1y=\"33.999999999999964\" c2x=\"39\" c2y=\"33.999999999999964\"/><p colinear=\"true\" x=\"291\" y=\"107.4\" c1x=\"39\" c1y=\"33.999999999999964\" c2x=\"39\" c2y=\"33.999999999999964\"/></points><startConnector><rConnector id=\"f\"><Owner><start ref=\"6\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"10\"><Owner><task ref=\"1\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"11\" g=\"-24,-16\" name=\"&#x627f;&#x529e;\"><points><p colinear=\"true\" x=\"291.28699186991867\" y=\"275.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"290.68861788617886\" y=\"312.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"12\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"13\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"14\" g=\"-24,-16\" name=\"&#x5206;&#x53d1;\"><points><p colinear=\"true\" x=\"290.573417721519\" y=\"335.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"290.84430379746834\" y=\"378.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('12', '3', '请假-短', '请假-短：1天内', '2011-08-02 14:58:39', '13', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6698814834287015698\"><start name=\"开始\" g=\"263,48,58,58\"><transition name=\"流程启动\" to=\"部门负责人审批\" g=\"-24,-16\"/></start><task name=\"部门负责人审批\" g=\"238,144,104,35\"><transition name=\"审批\" to=\"人事登记\" g=\"-24,-16\"/></task><task name=\"人事登记\" g=\"255,212,69,33\"><transition name=\"登记\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"259,275,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"263\" y=\"48\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;\" x=\"238\" y=\"144\" w=\"94\" h=\"25\"><a><text><string>&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"3\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"286.4177514792899\" y=\"96.6\" c1x=\"-29.844970414201157\" c1y=\"-1.4000000000000057\" c2x=\"-29.844970414201157\" c2y=\"-1.4000000000000057\"/><p colinear=\"true\" x=\"285.31005917159763\" y=\"143.4\" c1x=\"-29.844970414201157\" c1y=\"-1.4000000000000057\" c2x=\"-29.844970414201157\" c2y=\"-1.4000000000000057\"/></points><startConnector><rConnector id=\"4\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"5\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"6\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"7\" name=\"&#x4eba;&#x4e8b;&#x767b;&#x8bb0;\" x=\"255\" y=\"212\" w=\"59\" h=\"23\"><a><text><string>&#x4eba;&#x4e8b;&#x767b;&#x8bb0;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"8\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"284.90223880597017\" y=\"169.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"284.5902985074627\" y=\"211.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><task ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><end id=\"b\" x=\"259\" y=\"275\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"c\" g=\"-24,-16\" name=\"&#x767b;&#x8bb0;\"><points><p colinear=\"true\" x=\"284.25960264900664\" y=\"235.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"283.4887417218543\" y=\"274.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"7\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><end ref=\"b\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('13', '3', '请假-中', '请假-中：1-3天', '2011-08-02 15:03:09', '14', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd7680978182520578249\"><start name=\"开始\" g=\"285,33,58,58\"><transition name=\"流程启动\" to=\"部门负责人审批\" g=\"-24,-16\"/></start><task name=\"部门负责人审批\" g=\"261,127,106,37\"><transition name=\"审批\" to=\"分管局长审批\" g=\"-24,-16\"/></task><task name=\"分管局长审批\" g=\"264,193,99,37\"><transition name=\"审批\" to=\"局长审批\" g=\"-24,-16\"/></task><task name=\"局长审批\" g=\"277,260,72,36\"><transition name=\"审批\" to=\"人事登记\" g=\"-24,-16\"/></task><task name=\"人事登记\" g=\"278,328,71,33\"><transition name=\"登记\" to=\"结束2\" g=\"-24,-16\"/></task><end name=\"结束2\" g=\"284,389,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"285\" y=\"33\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;\" x=\"261\" y=\"127\" w=\"96\" h=\"27\"><a><text><string>&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x5206;&#x7ba1;&#x5c40;&#x957f;&#x5ba1;&#x6279;\" x=\"264\" y=\"193\" w=\"89\" h=\"27\"><a><text><string>&#x5206;&#x7ba1;&#x5c40;&#x957f;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x5c40;&#x957f;&#x5ba1;&#x6279;\" x=\"277\" y=\"260\" w=\"62\" h=\"26\"><a><text><string>&#x5c40;&#x957f;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x4eba;&#x4e8b;&#x767b;&#x8bb0;\" x=\"278\" y=\"328\" w=\"61\" h=\"23\"><a><text><string>&#x4eba;&#x4e8b;&#x767b;&#x8bb0;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"6\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"309\" y=\"81.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"309\" y=\"126.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"7\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"8\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"9\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"a\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"308.8931818181818\" y=\"154.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"308.60681818181814\" y=\"192.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"b\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"c\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"d\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"308.39398496240597\" y=\"220.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"308.10225563909773\" y=\"259.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"e\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"f\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"10\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"308.10225563909773\" y=\"286.59999999999997\" c1x=\"-0.10225563909773427\" c1y=\"-1.3999999999999773\" c2x=\"-0.10225563909773427\" c2y=\"-1.3999999999999773\"/><p colinear=\"true\" x=\"308.40902255639094\" y=\"327.4\" c1x=\"-0.10225563909773427\" c1y=\"-1.3999999999999773\" c2x=\"-0.10225563909773427\" c2y=\"-1.3999999999999773\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><end id=\"13\" x=\"284\" y=\"389\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;2\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"14\" g=\"-24,-16\" name=\"&#x767b;&#x8bb0;\"><points><p colinear=\"true\" x=\"308.4176870748299\" y=\"351.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"308.1673469387755\" y=\"388.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"13\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('14', '3', '请假-长', '请假-长：3天以上', '2011-08-02 15:06:26', '15', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd8705157447619894010\"><start name=\"开始\" g=\"289,59,58,58\"><transition name=\"连线9\" to=\"部门负责人审批\" g=\"-24,-16\"/></start><task name=\"部门负责人审批\" g=\"267,143,105,33\"><transition name=\"审批\" to=\"局长审批\" g=\"-24,-16\"/></task><task name=\"局长审批\" g=\"282,213,75,34\"><transition name=\"审批\" to=\"人事登记\" g=\"-24,-16\"/></task><task name=\"人事登记\" g=\"283,283,73,33\"><transition name=\"登记\" to=\"结束3\" g=\"-24,-16\"/></task><end name=\"结束3\" g=\"291,345,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"289\" y=\"59\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;\" x=\"267\" y=\"143\" w=\"95\" h=\"23\"><a><text><string>&#x90e8;&#x95e8;&#x8d1f;&#x8d23;&#x4eba;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x5c40;&#x957f;&#x5ba1;&#x6279;\" x=\"282\" y=\"213\" w=\"65\" h=\"24\"><a><text><string>&#x5c40;&#x957f;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x4eba;&#x4e8b;&#x767b;&#x8bb0;\" x=\"283\" y=\"283\" w=\"63\" h=\"23\"><a><text><string>&#x4eba;&#x4e8b;&#x767b;&#x8bb0;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"5\" x=\"291\" y=\"345\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;3\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"6\" g=\"-24,-16\" name=\"&#x8fde;&#x7ebf;9\"><points><p colinear=\"true\" x=\"313.5160839160839\" y=\"107.6\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/><p colinear=\"true\" x=\"314.2461538461538\" y=\"142.4\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/></points><startConnector><rConnector id=\"7\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"8\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"9\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"a\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"314.5\" y=\"166.6\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/><p colinear=\"true\" x=\"314.5\" y=\"212.4\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/></points><startConnector><rConnector id=\"b\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"c\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"d\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"314.5\" y=\"237.6\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/><p colinear=\"true\" x=\"314.5\" y=\"282.4\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/></points><startConnector><rConnector id=\"e\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"f\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"10\" g=\"-24,-16\" name=\"&#x767b;&#x8bb0;\"><points><p colinear=\"true\" x=\"314.5812080536913\" y=\"306.59999999999997\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/><p colinear=\"true\" x=\"314.834899328859\" y=\"344.4\" c1x=\"-2\" c1y=\"19\" c2x=\"-2\" c2y=\"19\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><end ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"9\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('15', '1', '收文流程-市局收文', '收文流程-市局收文', '2011-08-03 15:37:11', '16', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6899743696526177012\"><start name=\"开始\" g=\"264,14,58,58\"><transition name=\"流程启动\" to=\"办公室传阅\" g=\"-24,-16\"/></start><task name=\"办公室传阅\" g=\"251,94,87,36\"><transition name=\"传阅\" to=\"办公室主任批阅\" g=\"-24,-16\"/></task><task name=\"办公室主任批阅\" g=\"242,158,106,34\"><transition name=\"批阅\" to=\"分管或主管领导批示\" g=\"-24,-16\"/></task><task name=\"分管或主管领导批示\" g=\"232,221,129,40\"><transition name=\"批示\" to=\"科室主任传阅\" g=\"-24,-16\"/></task><task name=\"科室主任传阅\" g=\"250,290,93,37\"><transition name=\"传阅\" to=\"承办归档\" g=\"-24,-16\"/></task><task name=\"承办归档\" g=\"261,356,71,34\"><transition name=\"归档\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"267,415,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"264\" y=\"14\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x529e;&#x516c;&#x5ba4;&#x4f20;&#x9605;\" x=\"251\" y=\"94\" w=\"77\" h=\"26\"><a><text><string>&#x529e;&#x516c;&#x5ba4;&#x4f20;&#x9605;</string></text><fontSize><double>13</double></fontSize><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x529e;&#x516c;&#x5ba4;&#x4e3b;&#x4efb;&#x6279;&#x9605;\" x=\"242\" y=\"158\" w=\"96\" h=\"24\"><a><text><string>&#x529e;&#x516c;&#x5ba4;&#x4e3b;&#x4efb;&#x6279;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x5206;&#x7ba1;&#x6216;&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x6279;&#x793a;\" x=\"232\" y=\"221\" w=\"119\" h=\"30\"><a><text><string>&#x5206;&#x7ba1;&#x6216;&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x6279;&#x793a;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x79d1;&#x5ba4;&#x4e3b;&#x4efb;&#x4f20;&#x9605;\" x=\"250\" y=\"290\" w=\"83\" h=\"27\"><a><text><string>&#x79d1;&#x5ba4;&#x4e3b;&#x4efb;&#x4f20;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"6\" name=\"&#x627f;&#x529e;&#x5f52;&#x6863;\" x=\"261\" y=\"356\" w=\"61\" h=\"24\"><a><text><string>&#x627f;&#x529e;&#x5f52;&#x6863;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"7\" x=\"267\" y=\"415\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"8\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"288.53478260869565\" y=\"62.6\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"289.204347826087\" y=\"93.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"9\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"b\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"c\" g=\"-24,-16\" name=\"&#x4f20;&#x9605;\"><points><p colinear=\"true\" x=\"289.60793650793653\" y=\"120.60000000000001\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"289.9\" y=\"157.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"b\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"f\" g=\"-24,-16\" name=\"&#x6279;&#x9605;\"><points><p colinear=\"true\" x=\"290.28636363636366\" y=\"182.6\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"291.1454545454545\" y=\"220.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"10\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"11\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"b\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"12\" g=\"-24,-16\" name=\"&#x6279;&#x793a;\"><points><p colinear=\"true\" x=\"291.5\" y=\"251.6\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"291.5\" y=\"289.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"13\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"14\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"b\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"15\" g=\"-24,-16\" name=\"&#x4f20;&#x9605;\"><points><p colinear=\"true\" x=\"291.5\" y=\"317.59999999999997\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"291.5\" y=\"355.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"16\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"17\"><Owner><task ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"b\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"18\" g=\"-24,-16\" name=\"&#x5f52;&#x6863;\"><points><p colinear=\"true\" x=\"291.4112676056338\" y=\"380.59999999999997\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/><p colinear=\"true\" x=\"291.1732394366197\" y=\"414.4\" c1x=\"1\" c1y=\"-5\" c2x=\"1\" c2y=\"-5\"/></points><startConnector><rConnector id=\"19\"><Owner><task ref=\"6\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1a\"><Owner><end ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"b\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('16', '4', '人员晋升', '人员晋升', '2011-09-07 14:14:45', '17', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd7302193779570997303\"><start name=\"开始\" g=\"265,4,58,58\"><transition name=\"流程启动\" to=\"上报审批\" g=\"-24,-16\"/></start><task name=\"上报审批\" g=\"260,87,70,37\"><transition name=\"审批\" to=\"人力资源核实并确认\" g=\"-24,-16\"/></task><task name=\"人力资源核实并确认\" g=\"228,149,133,36\"><transition name=\"核实确认\" to=\"目标设定与面谈\" g=\"-24,-16\"/></task><task name=\"目标设定与面谈\" g=\"241,210,109,34\"><transition name=\"面谈\" to=\"考核期评估\" g=\"-24,-16\"/></task><task name=\"考核期评估\" g=\"255,267,84,34\"><transition name=\"评估\" to=\"领导批准\" g=\"-24,-16\"/></task><task name=\"领导批准\" g=\"260,324,75,34\"><transition name=\"批准\" to=\"人力资源确认\" g=\"-24,-16\"/></task><task name=\"人力资源确认\" g=\"252,382,92,38\"><transition name=\"确认\" to=\"晋升面谈\" g=\"-24,-16\"/></task><task name=\"晋升面谈\" g=\"261,447,71,34\"><transition name=\"晋升面谈\" to=\"任命发文\" g=\"-24,-16\"/></task><task name=\"任命发文\" g=\"261,504,71,35\"><transition name=\"发文\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"261,556,74,74\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"265\" y=\"4\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x4e0a;&#x62a5;&#x5ba1;&#x6279;\" x=\"260\" y=\"87\" w=\"60\" h=\"27\"><a><text><string>&#x4e0a;&#x62a5;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"3\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"289.3393103448276\" y=\"52.6\" c1x=\"-2\" c1y=\"-21\" c2x=\"-2\" c2y=\"-21\"/><p colinear=\"true\" x=\"289.8055172413793\" y=\"86.4\" c1x=\"-2\" c1y=\"-21\" c2x=\"-2\" c2y=\"-21\"/></points><startConnector><rConnector id=\"4\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"5\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"6\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"7\" name=\"&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x6838;&#x5b9e;&#x5e76;&#x786e;&#x8ba4;\" x=\"228\" y=\"149\" w=\"123\" h=\"26\"><a><text><string>&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x6838;&#x5b9e;&#x5e76;&#x786e;&#x8ba4;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"8\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"289.8853658536585\" y=\"114.60000000000001\" c1x=\"-2\" c1y=\"-21\" c2x=\"-2\" c2y=\"-21\"/><p colinear=\"true\" x=\"289.61056910569107\" y=\"148.4\" c1x=\"-2\" c1y=\"-21\" c2x=\"-2\" c2y=\"-21\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><task ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"b\" name=\"&#x76ee;&#x6807;&#x8bbe;&#x5b9a;&#x4e0e;&#x9762;&#x8c08;\" x=\"241\" y=\"210\" w=\"99\" h=\"24\"><a><text><string>&#x76ee;&#x6807;&#x8bbe;&#x5b9a;&#x4e0e;&#x9762;&#x8c08;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"c\" g=\"-24,-16\" name=\"&#x6838;&#x5b9e;&#x786e;&#x8ba4;\"><points><p colinear=\"true\" x=\"289.7266666666667\" y=\"175.6\" c1x=\"-1\" c1y=\"-21\" c2x=\"-1\" c2y=\"-21\"/><p colinear=\"true\" x=\"290.29\" y=\"209.4\" c1x=\"-1\" c1y=\"-21\" c2x=\"-1\" c2y=\"-21\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"7\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><task ref=\"b\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"f\" name=\"&#x8003;&#x6838;&#x671f;&#x8bc4;&#x4f30;\" x=\"255\" y=\"267\" w=\"74\" h=\"24\"><a><text><string>&#x8003;&#x6838;&#x671f;&#x8bc4;&#x4f30;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"10\" g=\"-24,-16\" name=\"&#x9762;&#x8c08;\"><points><p colinear=\"true\" x=\"290.8315789473684\" y=\"234.6\" c1x=\"-2\" c1y=\"-24\" c2x=\"-2\" c2y=\"-24\"/><p colinear=\"true\" x=\"291.6684210526316\" y=\"266.4\" c1x=\"-2\" c1y=\"-24\" c2x=\"-2\" c2y=\"-24\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"b\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><task ref=\"f\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"13\" name=\"&#x9886;&#x5bfc;&#x6279;&#x51c6;\" x=\"260\" y=\"324\" w=\"65\" h=\"24\"><a><text><string>&#x9886;&#x5bfc;&#x6279;&#x51c6;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"14\" g=\"-24,-16\" name=\"&#x8bc4;&#x4f30;\"><points><p colinear=\"true\" x=\"292.11052631578946\" y=\"291.59999999999997\" c1x=\"0.525000000000091\" c1y=\"18.40000000000009\" c2x=\"0.525000000000091\" c2y=\"18.40000000000009\"/><p colinear=\"true\" x=\"292.3894736842105\" y=\"323.4\" c1x=\"0.525000000000091\" c1y=\"18.40000000000009\" c2x=\"0.525000000000091\" c2y=\"18.40000000000009\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"f\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><task ref=\"13\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"17\" name=\"&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x786e;&#x8ba4;\" x=\"252\" y=\"382\" w=\"82\" h=\"28\"><a><text><string>&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x786e;&#x8ba4;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"18\" g=\"-24,-16\" name=\"&#x6279;&#x51c6;\"><points><p colinear=\"true\" x=\"292.60499999999996\" y=\"348.59999999999997\" c1x=\"0\" c1y=\"4\" c2x=\"0\" c2y=\"4\"/><p colinear=\"true\" x=\"292.87833333333333\" y=\"381.4\" c1x=\"0\" c1y=\"4\" c2x=\"0\" c2y=\"4\"/></points><startConnector><rConnector id=\"19\"><Owner><task ref=\"13\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1a\"><Owner><task ref=\"17\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"1b\" name=\"&#x664b;&#x5347;&#x9762;&#x8c08;\" x=\"261\" y=\"447\" w=\"61\" h=\"24\"><a><text><string>&#x664b;&#x5347;&#x9762;&#x8c08;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"1c\" g=\"-24,-16\" name=\"&#x786e;&#x8ba4;\"><points><p colinear=\"true\" x=\"292.65238095238095\" y=\"410.59999999999997\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/><p colinear=\"true\" x=\"291.79999999999995\" y=\"446.4\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/></points><startConnector><rConnector id=\"1d\"><Owner><task ref=\"17\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1e\"><Owner><task ref=\"1b\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"1f\" name=\"&#x4efb;&#x547d;&#x53d1;&#x6587;\" x=\"261\" y=\"504\" w=\"61\" h=\"25\"><a><text><string>&#x4efb;&#x547d;&#x53d1;&#x6587;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"20\" g=\"-24,-16\" name=\"&#x664b;&#x5347;&#x9762;&#x8c08;\"><points><p colinear=\"true\" x=\"291.5\" y=\"471.59999999999997\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/><p colinear=\"true\" x=\"291.5\" y=\"503.4\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/></points><startConnector><rConnector id=\"21\"><Owner><task ref=\"1b\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"22\"><Owner><task ref=\"1f\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><end id=\"23\" x=\"261\" y=\"556\" w=\"64\" h=\"64\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"24\" g=\"-24,-16\" name=\"&#x53d1;&#x6587;\"><points><p colinear=\"true\" x=\"291.77482517482514\" y=\"529.6\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/><p colinear=\"true\" x=\"292.31608391608387\" y=\"555.4\" c1x=\"1\" c1y=\"0\" c2x=\"1\" c2y=\"0\"/></points><startConnector><rConnector id=\"25\"><Owner><task ref=\"1f\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"26\"><Owner><end ref=\"23\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('17', '4', '考核流程', '考核流程', '2011-09-21 16:35:19', '18', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd5701124732843989465\"><start name=\"开始\" g=\"265,33,58,58\"><transition name=\"审核上报\" to=\"人力资源复核确认\" g=\"-24,-16\"/></start><task name=\"人力资源复核确认\" g=\"234,145,122,39\"><transition name=\"绩效导出\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"267,219,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"265\" y=\"33\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x590d;&#x6838;&#x786e;&#x8ba4;\" x=\"234\" y=\"145\" w=\"112\" h=\"29\"><a><text><string>&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x590d;&#x6838;&#x786e;&#x8ba4;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"3\" x=\"267\" y=\"219\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"4\" g=\"-24,-16\" name=\"&#x5ba1;&#x6838;&#x4e0a;&#x62a5;\"><points><p colinear=\"true\" x=\"289.24\" y=\"81.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"289.8526829268293\" y=\"144.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x7ee9;&#x6548;&#x5bfc;&#x51fa;\"><points><p colinear=\"true\" x=\"290.1808383233533\" y=\"174.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"290.70538922155686\" y=\"218.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
INSERT INTO pro_definition VALUES ('18', '4', '人员转正', '人员转正', '2011-09-23 14:12:27', '20', '<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd5348597956506307584\"><start name=\"开始\" g=\"239,32,58,58\"><transition name=\"提交申请\" to=\"直线经理审核\" g=\"-24,-16\"/></start><task name=\"直线经理审核\" g=\"225,137,109,34\"><transition name=\"审核上报\" to=\"人力资源复核\" g=\"-24,-16\"/></task><task name=\"人力资源复核\" g=\"228,230,105,33\"><transition name=\"确认通过\" to=\"分管副总裁确认\" g=\"-24,-16\"/></task><task name=\"分管副总裁确认\" g=\"221,307,119,33\"><transition name=\"确认通过\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"245,382,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"239\" y=\"32\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x76f4;&#x7ebf;&#x7ecf;&#x7406;&#x5ba1;&#x6838;\" x=\"225\" y=\"137\" w=\"99\" h=\"24\"><a><text><string>&#x76f4;&#x7ebf;&#x7ecf;&#x7406;&#x5ba1;&#x6838;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"3\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x7533;&#x8bf7;\"><points><p colinear=\"true\" x=\"266.041935483871\" y=\"80.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"272.94193548387096\" y=\"136.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"4\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"5\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"6\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"7\" name=\"&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x590d;&#x6838;\" x=\"228\" y=\"230\" w=\"95\" h=\"23\"><a><text><string>&#x4eba;&#x529b;&#x8d44;&#x6e90;&#x590d;&#x6838;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"8\" g=\"-24,-16\" name=\"&#x5ba1;&#x6838;&#x4e0a;&#x62a5;\"><points><p colinear=\"true\" x=\"274.6362162162162\" y=\"161.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"275.36918918918917\" y=\"229.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><task ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><task id=\"b\" name=\"&#x5206;&#x7ba1;&#x526f;&#x603b;&#x88c1;&#x786e;&#x8ba4;\" x=\"221\" y=\"307\" w=\"109\" h=\"23\"><a><text><string>&#x5206;&#x7ba1;&#x526f;&#x603b;&#x88c1;&#x786e;&#x8ba4;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"c\" g=\"-24,-16\" name=\"&#x786e;&#x8ba4;&#x901a;&#x8fc7;\"><points><p colinear=\"true\" x=\"275.5\" y=\"253.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"275.5\" y=\"306.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"7\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><task ref=\"b\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><end id=\"f\" x=\"245\" y=\"382\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"10\" g=\"-24,-16\" name=\"&#x786e;&#x8ba4;&#x901a;&#x8fc7;\"><points><p colinear=\"true\" x=\"274.60114285714286\" y=\"330.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"270.8274285714286\" y=\"381.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"b\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><end ref=\"f\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"6\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');

-- ----------------------------
-- Table structure for `pro_type`
-- ----------------------------
DROP TABLE IF EXISTS `pro_type`;
CREATE TABLE `pro_type` (
  `typeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID',
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='流程分类';

-- ----------------------------
-- Records of pro_type
-- ----------------------------
INSERT INTO pro_type VALUES ('1', '公文');
INSERT INTO pro_type VALUES ('2', '行政');
INSERT INTO pro_type VALUES ('3', '日常');
INSERT INTO pro_type VALUES ('4', '人力');

-- ----------------------------
-- Table structure for `pro_user_assign`
-- ----------------------------
DROP TABLE IF EXISTS `pro_user_assign`;
CREATE TABLE `pro_user_assign` (
  `assignId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '授权ID',
  `deployId` varchar(128) NOT NULL COMMENT 'jbpm流程定义的id',
  `activityName` varchar(128) NOT NULL COMMENT '流程节点名称',
  `roleId` varchar(128) DEFAULT NULL COMMENT '角色Id',
  `roleName` varchar(256) DEFAULT NULL,
  `userId` varchar(128) DEFAULT NULL COMMENT '用户ID',
  `username` varchar(256) DEFAULT NULL,
  `isSigned` smallint(6) DEFAULT '0' COMMENT '1=是会签任务\r\n            0=非会签任务\r\n            \r\n            若为会签任务，则需要为该会签添加会签的决策方式的设置\r\n            ',
  PRIMARY KEY (`assignId`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='流程过程中各个任务节点及启动流程时的角色';

-- ----------------------------
-- Records of pro_user_assign
-- ----------------------------
INSERT INTO pro_user_assign VALUES ('1', '4', '发文核稿', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('2', '4', '科室审核', '', '', '__super', '[上级]', null);
INSERT INTO pro_user_assign VALUES ('3', '4', '主管领导审批', '8', '副局长', '', '', null);
INSERT INTO pro_user_assign VALUES ('4', '4', '发文分发', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('5', '4', '发文校对', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('6', '4', '分管领导签发', '7', '局长', '', '', null);
INSERT INTO pro_user_assign VALUES ('7', '5', '登记拟办', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('8', '5', '领导批示', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('9', '5', '公文分发', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('10', '5', '承办传阅', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('11', '12', '科室负责人核稿', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('12', '12', '分管或局领导签发', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('13', '12', '办公室主任承办', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('14', '12', '编号盖章分发', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('15', '13', '部门负责人审批', '', '', '', '', null);
INSERT INTO pro_user_assign VALUES ('16', '13', '人事登记', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('17', '14', '部门负责人审批', '', '', '', '', null);
INSERT INTO pro_user_assign VALUES ('18', '14', '分管局长审批', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('19', '14', '局长审批', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('20', '14', '人事登记', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('21', '15', '部门负责人审批', '', '', '', '', null);
INSERT INTO pro_user_assign VALUES ('22', '15', '局长审批', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('23', '15', '人事登记', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('24', '17', '上报审批', '', '', '', '', null);
INSERT INTO pro_user_assign VALUES ('25', '17', '人力资源核实并确认', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('26', '17', '目标设定与面谈', '', '', '__start', '[发起人]', null);
INSERT INTO pro_user_assign VALUES ('27', '17', '考核期评估', '', '', '__start', '[发起人]', null);
INSERT INTO pro_user_assign VALUES ('28', '17', '领导批准', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('29', '17', '人力资源确认', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('30', '17', '晋升面谈', '', '', '__start', '[发起人]', null);
INSERT INTO pro_user_assign VALUES ('31', '17', '任命发文', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('32', '20', '直线经理审核', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('33', '20', '人力资源复核', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('34', '20', '分管副总裁确认', '', '', '1', '超级管理员', null);
INSERT INTO pro_user_assign VALUES ('35', '18', '人力资源复核确认', '', '', '1,2,3', '超级管理员,cwx,人事经理', null);

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `regionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `regionName` varchar(128) NOT NULL COMMENT '地区名称',
  `regionType` smallint(6) NOT NULL COMMENT '地区类型\r\n            1=省份\r\n            2=市',
  `parentId` bigint(20) DEFAULT NULL COMMENT '上级地区',
  PRIMARY KEY (`regionId`)
) ENGINE=InnoDB AUTO_INCREMENT=393 DEFAULT CHARSET=utf8 COMMENT='地区管理';

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO region VALUES ('1', '北京', '2', '0');
INSERT INTO region VALUES ('2', '上海', '2', '0');
INSERT INTO region VALUES ('3', '天津', '2', '0');
INSERT INTO region VALUES ('4', '重庆', '2', '0');
INSERT INTO region VALUES ('5', '河北', '1', '0');
INSERT INTO region VALUES ('6', '山西', '1', '0');
INSERT INTO region VALUES ('7', '内蒙古', '1', '0');
INSERT INTO region VALUES ('8', '辽宁', '1', '0');
INSERT INTO region VALUES ('9', '吉林', '1', '0');
INSERT INTO region VALUES ('10', '黑龙江', '1', '0');
INSERT INTO region VALUES ('11', '江苏', '1', '0');
INSERT INTO region VALUES ('12', '浙江', '1', '0');
INSERT INTO region VALUES ('13', '安徽', '1', '0');
INSERT INTO region VALUES ('14', '福建', '1', '0');
INSERT INTO region VALUES ('15', '江西', '1', '0');
INSERT INTO region VALUES ('16', '山东', '1', '0');
INSERT INTO region VALUES ('17', '河南', '1', '0');
INSERT INTO region VALUES ('18', '湖北', '1', '0');
INSERT INTO region VALUES ('19', '湖南', '1', '0');
INSERT INTO region VALUES ('20', '广东', '1', '0');
INSERT INTO region VALUES ('21', '广西', '1', '0');
INSERT INTO region VALUES ('22', '海南', '1', '0');
INSERT INTO region VALUES ('23', '四川', '1', '0');
INSERT INTO region VALUES ('24', '贵州', '1', '0');
INSERT INTO region VALUES ('25', '云南', '1', '0');
INSERT INTO region VALUES ('26', '西藏', '1', '0');
INSERT INTO region VALUES ('27', '陕西', '1', '0');
INSERT INTO region VALUES ('28', '甘肃', '1', '0');
INSERT INTO region VALUES ('29', '青海', '1', '0');
INSERT INTO region VALUES ('30', '宁夏', '1', '0');
INSERT INTO region VALUES ('31', '新疆', '1', '0');
INSERT INTO region VALUES ('32', '台湾', '1', '0');
INSERT INTO region VALUES ('33', '港澳', '1', '0');
INSERT INTO region VALUES ('34', '石家庄', '2', '5');
INSERT INTO region VALUES ('35', '唐山', '2', '5');
INSERT INTO region VALUES ('36', '秦皇岛', '2', '5');
INSERT INTO region VALUES ('37', '邯郸', '2', '5');
INSERT INTO region VALUES ('38', '邢台', '2', '5');
INSERT INTO region VALUES ('39', '保定', '2', '5');
INSERT INTO region VALUES ('40', '张家口', '2', '5');
INSERT INTO region VALUES ('41', '承德', '2', '5');
INSERT INTO region VALUES ('42', '沧州', '2', '5');
INSERT INTO region VALUES ('43', '廊坊', '2', '5');
INSERT INTO region VALUES ('44', '衡水', '2', '5');
INSERT INTO region VALUES ('45', '太原', '2', '6');
INSERT INTO region VALUES ('46', '大同', '2', '6');
INSERT INTO region VALUES ('47', '阳泉', '2', '6');
INSERT INTO region VALUES ('48', '长治', '2', '6');
INSERT INTO region VALUES ('49', '晋城', '2', '6');
INSERT INTO region VALUES ('50', '朔州', '2', '6');
INSERT INTO region VALUES ('51', '晋中', '2', '6');
INSERT INTO region VALUES ('52', '运城', '2', '6');
INSERT INTO region VALUES ('53', '忻州', '2', '6');
INSERT INTO region VALUES ('54', '临汾', '2', '6');
INSERT INTO region VALUES ('55', '吕梁', '2', '6');
INSERT INTO region VALUES ('56', '呼和浩特', '2', '7');
INSERT INTO region VALUES ('57', '包头', '2', '7');
INSERT INTO region VALUES ('58', '乌海', '2', '7');
INSERT INTO region VALUES ('59', '赤峰', '2', '7');
INSERT INTO region VALUES ('60', '通辽', '2', '7');
INSERT INTO region VALUES ('61', '鄂尔多斯', '2', '7');
INSERT INTO region VALUES ('62', '呼伦贝尔', '2', '7');
INSERT INTO region VALUES ('63', '巴彦淖尔', '2', '7');
INSERT INTO region VALUES ('64', '乌兰察布', '2', '7');
INSERT INTO region VALUES ('65', '兴安', '2', '7');
INSERT INTO region VALUES ('66', '锡林郭勒', '2', '7');
INSERT INTO region VALUES ('67', '阿拉善', '2', '7');
INSERT INTO region VALUES ('68', '沈阳', '2', '8');
INSERT INTO region VALUES ('69', '大连', '2', '8');
INSERT INTO region VALUES ('70', '鞍山', '2', '8');
INSERT INTO region VALUES ('71', '抚顺', '2', '8');
INSERT INTO region VALUES ('72', '本溪', '2', '8');
INSERT INTO region VALUES ('73', '丹东', '2', '8');
INSERT INTO region VALUES ('74', '锦州', '2', '8');
INSERT INTO region VALUES ('75', '营口', '2', '8');
INSERT INTO region VALUES ('76', '阜新', '2', '8');
INSERT INTO region VALUES ('77', '辽阳', '2', '8');
INSERT INTO region VALUES ('78', '盘锦', '2', '8');
INSERT INTO region VALUES ('79', '铁岭', '2', '8');
INSERT INTO region VALUES ('80', '朝阳', '2', '8');
INSERT INTO region VALUES ('81', '葫芦岛', '2', '8');
INSERT INTO region VALUES ('82', '长春', '2', '9');
INSERT INTO region VALUES ('83', '吉林', '2', '9');
INSERT INTO region VALUES ('84', '四平', '2', '9');
INSERT INTO region VALUES ('85', '辽源', '2', '9');
INSERT INTO region VALUES ('86', '通化', '2', '9');
INSERT INTO region VALUES ('87', '白山', '2', '9');
INSERT INTO region VALUES ('88', '松原', '2', '9');
INSERT INTO region VALUES ('89', '白城', '2', '9');
INSERT INTO region VALUES ('90', '延边', '2', '9');
INSERT INTO region VALUES ('91', '哈尔滨', '2', '10');
INSERT INTO region VALUES ('92', '齐齐哈尔', '2', '10');
INSERT INTO region VALUES ('93', '鸡西', '2', '10');
INSERT INTO region VALUES ('94', '鹤岗', '2', '10');
INSERT INTO region VALUES ('95', '双鸭山', '2', '10');
INSERT INTO region VALUES ('96', '大庆', '2', '10');
INSERT INTO region VALUES ('97', '伊春', '2', '10');
INSERT INTO region VALUES ('98', '佳木斯', '2', '10');
INSERT INTO region VALUES ('99', '七台河', '2', '10');
INSERT INTO region VALUES ('100', '牡丹江', '2', '10');
INSERT INTO region VALUES ('101', '黑河', '2', '10');
INSERT INTO region VALUES ('102', '绥化', '2', '10');
INSERT INTO region VALUES ('103', '大兴安岭', '2', '10');
INSERT INTO region VALUES ('104', '南京', '2', '11');
INSERT INTO region VALUES ('105', '无锡', '2', '11');
INSERT INTO region VALUES ('106', '徐州', '2', '11');
INSERT INTO region VALUES ('107', '常州', '2', '11');
INSERT INTO region VALUES ('108', '苏州', '2', '11');
INSERT INTO region VALUES ('109', '南通', '2', '11');
INSERT INTO region VALUES ('110', '连云港', '2', '11');
INSERT INTO region VALUES ('111', '淮安', '2', '11');
INSERT INTO region VALUES ('112', '盐城', '2', '11');
INSERT INTO region VALUES ('113', '扬州', '2', '11');
INSERT INTO region VALUES ('114', '镇江', '2', '11');
INSERT INTO region VALUES ('115', '泰州', '2', '11');
INSERT INTO region VALUES ('116', '宿迁', '2', '11');
INSERT INTO region VALUES ('117', '杭州', '2', '12');
INSERT INTO region VALUES ('118', '宁波', '2', '12');
INSERT INTO region VALUES ('119', '温州', '2', '12');
INSERT INTO region VALUES ('120', '嘉兴', '2', '12');
INSERT INTO region VALUES ('121', '湖州', '2', '12');
INSERT INTO region VALUES ('122', '绍兴', '2', '12');
INSERT INTO region VALUES ('123', '金华', '2', '12');
INSERT INTO region VALUES ('124', '衢州', '2', '12');
INSERT INTO region VALUES ('125', '舟山', '2', '12');
INSERT INTO region VALUES ('126', '台州', '2', '12');
INSERT INTO region VALUES ('127', '丽水', '2', '12');
INSERT INTO region VALUES ('128', '合肥', '2', '13');
INSERT INTO region VALUES ('129', '芜湖', '2', '13');
INSERT INTO region VALUES ('130', '蚌埠', '2', '13');
INSERT INTO region VALUES ('131', '淮南', '2', '13');
INSERT INTO region VALUES ('132', '马鞍山', '2', '13');
INSERT INTO region VALUES ('133', '淮北', '2', '13');
INSERT INTO region VALUES ('134', '铜陵', '2', '13');
INSERT INTO region VALUES ('135', '安庆', '2', '13');
INSERT INTO region VALUES ('136', '黄山', '2', '13');
INSERT INTO region VALUES ('137', '滁州', '2', '13');
INSERT INTO region VALUES ('138', '阜阳', '2', '13');
INSERT INTO region VALUES ('139', '宿州', '2', '13');
INSERT INTO region VALUES ('140', '巢湖', '2', '13');
INSERT INTO region VALUES ('141', '六安', '2', '13');
INSERT INTO region VALUES ('142', '亳州', '2', '13');
INSERT INTO region VALUES ('143', '池州', '2', '13');
INSERT INTO region VALUES ('144', '宣城', '2', '13');
INSERT INTO region VALUES ('145', '福州', '2', '14');
INSERT INTO region VALUES ('146', '厦门', '2', '14');
INSERT INTO region VALUES ('147', '莆田', '2', '14');
INSERT INTO region VALUES ('148', '三明', '2', '14');
INSERT INTO region VALUES ('149', '泉州', '2', '14');
INSERT INTO region VALUES ('150', '漳州', '2', '14');
INSERT INTO region VALUES ('151', '南平', '2', '14');
INSERT INTO region VALUES ('152', '龙岩', '2', '14');
INSERT INTO region VALUES ('153', '宁德', '2', '14');
INSERT INTO region VALUES ('154', '南昌', '2', '15');
INSERT INTO region VALUES ('155', '景德镇', '2', '15');
INSERT INTO region VALUES ('156', '萍乡', '2', '15');
INSERT INTO region VALUES ('157', '九江', '2', '15');
INSERT INTO region VALUES ('158', '新余', '2', '15');
INSERT INTO region VALUES ('159', '鹰潭', '2', '15');
INSERT INTO region VALUES ('160', '赣州', '2', '15');
INSERT INTO region VALUES ('161', '吉安', '2', '15');
INSERT INTO region VALUES ('162', '宜春', '2', '15');
INSERT INTO region VALUES ('163', '抚州', '2', '15');
INSERT INTO region VALUES ('164', '上饶', '2', '15');
INSERT INTO region VALUES ('165', '济南', '2', '16');
INSERT INTO region VALUES ('166', '青岛', '2', '16');
INSERT INTO region VALUES ('167', '淄博', '2', '16');
INSERT INTO region VALUES ('168', '枣庄', '2', '16');
INSERT INTO region VALUES ('169', '东营', '2', '16');
INSERT INTO region VALUES ('170', '烟台', '2', '16');
INSERT INTO region VALUES ('171', '潍坊', '2', '16');
INSERT INTO region VALUES ('172', '济宁', '2', '16');
INSERT INTO region VALUES ('173', '泰安', '2', '16');
INSERT INTO region VALUES ('174', '日照', '2', '16');
INSERT INTO region VALUES ('175', '莱芜', '2', '16');
INSERT INTO region VALUES ('176', '临沂', '2', '16');
INSERT INTO region VALUES ('177', '德州', '2', '16');
INSERT INTO region VALUES ('178', '聊城', '2', '16');
INSERT INTO region VALUES ('179', '滨州', '2', '16');
INSERT INTO region VALUES ('180', '菏泽', '2', '16');
INSERT INTO region VALUES ('181', '郑州', '2', '17');
INSERT INTO region VALUES ('182', '开封', '2', '17');
INSERT INTO region VALUES ('183', '洛阳', '2', '17');
INSERT INTO region VALUES ('184', '平顶山', '2', '17');
INSERT INTO region VALUES ('185', '焦作', '2', '17');
INSERT INTO region VALUES ('186', '鹤壁', '2', '17');
INSERT INTO region VALUES ('187', '新乡', '2', '17');
INSERT INTO region VALUES ('188', '安阳', '2', '17');
INSERT INTO region VALUES ('189', '濮阳', '2', '17');
INSERT INTO region VALUES ('190', '许昌', '2', '17');
INSERT INTO region VALUES ('191', '渭河', '2', '17');
INSERT INTO region VALUES ('192', '三门峡', '2', '17');
INSERT INTO region VALUES ('193', '南阳', '2', '17');
INSERT INTO region VALUES ('194', '商丘', '2', '17');
INSERT INTO region VALUES ('195', '信阳', '2', '17');
INSERT INTO region VALUES ('196', '周口', '2', '17');
INSERT INTO region VALUES ('197', '驻马店', '2', '17');
INSERT INTO region VALUES ('198', '武汉', '2', '18');
INSERT INTO region VALUES ('199', '黄石', '2', '18');
INSERT INTO region VALUES ('200', '襄樊', '2', '18');
INSERT INTO region VALUES ('201', '十堰', '2', '18');
INSERT INTO region VALUES ('202', '荆州', '2', '18');
INSERT INTO region VALUES ('203', '宜昌', '2', '18');
INSERT INTO region VALUES ('204', '荆门', '2', '18');
INSERT INTO region VALUES ('205', '鄂州', '2', '18');
INSERT INTO region VALUES ('206', '孝感', '2', '18');
INSERT INTO region VALUES ('207', '黄冈', '2', '18');
INSERT INTO region VALUES ('208', '咸宁', '2', '18');
INSERT INTO region VALUES ('209', '随州', '2', '18');
INSERT INTO region VALUES ('210', '恩施', '2', '18');
INSERT INTO region VALUES ('211', '长沙', '2', '19');
INSERT INTO region VALUES ('212', '株洲', '2', '19');
INSERT INTO region VALUES ('213', '湘潭', '2', '19');
INSERT INTO region VALUES ('214', '衡阳', '2', '19');
INSERT INTO region VALUES ('215', '邵阳', '2', '19');
INSERT INTO region VALUES ('216', '岳阳', '2', '19');
INSERT INTO region VALUES ('217', '常德', '2', '19');
INSERT INTO region VALUES ('218', '张家界', '2', '19');
INSERT INTO region VALUES ('219', '溢阳', '2', '19');
INSERT INTO region VALUES ('220', '郴州', '2', '19');
INSERT INTO region VALUES ('221', '永州', '2', '19');
INSERT INTO region VALUES ('222', '怀化', '2', '19');
INSERT INTO region VALUES ('223', '娄底', '2', '19');
INSERT INTO region VALUES ('224', '湘西', '2', '19');
INSERT INTO region VALUES ('225', '广州', '2', '20');
INSERT INTO region VALUES ('226', '深圳', '2', '20');
INSERT INTO region VALUES ('227', '珠海', '2', '20');
INSERT INTO region VALUES ('228', '汕头', '2', '20');
INSERT INTO region VALUES ('229', '韶关', '2', '20');
INSERT INTO region VALUES ('230', '佛山', '2', '20');
INSERT INTO region VALUES ('231', '江门', '2', '20');
INSERT INTO region VALUES ('232', '湛江', '2', '20');
INSERT INTO region VALUES ('233', '茂名', '2', '20');
INSERT INTO region VALUES ('234', '肇庆', '2', '20');
INSERT INTO region VALUES ('235', '惠州', '2', '20');
INSERT INTO region VALUES ('236', '梅州', '2', '20');
INSERT INTO region VALUES ('237', '汕尾', '2', '20');
INSERT INTO region VALUES ('238', '河源', '2', '20');
INSERT INTO region VALUES ('239', '阳江', '2', '20');
INSERT INTO region VALUES ('240', '清远', '2', '20');
INSERT INTO region VALUES ('241', '东莞', '2', '20');
INSERT INTO region VALUES ('242', '中山', '2', '20');
INSERT INTO region VALUES ('243', '潮州', '2', '20');
INSERT INTO region VALUES ('244', '揭阳', '2', '20');
INSERT INTO region VALUES ('245', '云浮', '2', '20');
INSERT INTO region VALUES ('246', '南宁', '2', '21');
INSERT INTO region VALUES ('247', '柳州', '2', '21');
INSERT INTO region VALUES ('248', '桂林', '2', '21');
INSERT INTO region VALUES ('249', '梧州', '2', '21');
INSERT INTO region VALUES ('250', '北海', '2', '21');
INSERT INTO region VALUES ('251', '防城港', '2', '21');
INSERT INTO region VALUES ('252', '钦州', '2', '21');
INSERT INTO region VALUES ('253', '贵港', '2', '21');
INSERT INTO region VALUES ('254', '玉林', '2', '21');
INSERT INTO region VALUES ('255', '百色', '2', '21');
INSERT INTO region VALUES ('256', '贺州', '2', '21');
INSERT INTO region VALUES ('257', '河池', '2', '21');
INSERT INTO region VALUES ('258', '来宾', '2', '21');
INSERT INTO region VALUES ('259', '崇左', '2', '21');
INSERT INTO region VALUES ('260', '白沙黎族自治县', '2', '22');
INSERT INTO region VALUES ('261', '西沙群岛', '2', '22');
INSERT INTO region VALUES ('262', '儋州', '2', '22');
INSERT INTO region VALUES ('263', '屯昌县', '2', '22');
INSERT INTO region VALUES ('264', '安定县', '2', '22');
INSERT INTO region VALUES ('265', '琼中黎族苗族自治县', '2', '22');
INSERT INTO region VALUES ('266', '昌江黎族自治县', '2', '22');
INSERT INTO region VALUES ('267', '东方', '2', '22');
INSERT INTO region VALUES ('268', '三亚', '2', '22');
INSERT INTO region VALUES ('269', '中沙群岛的岛礁及其海域', '2', '22');
INSERT INTO region VALUES ('270', '琼海', '2', '22');
INSERT INTO region VALUES ('271', '澄迈县', '2', '22');
INSERT INTO region VALUES ('272', '五指山', '2', '22');
INSERT INTO region VALUES ('273', '海口', '2', '22');
INSERT INTO region VALUES ('274', '文昌', '2', '22');
INSERT INTO region VALUES ('275', '陵水黎族自治县', '2', '22');
INSERT INTO region VALUES ('276', '保亭黎族苗族自治县', '2', '22');
INSERT INTO region VALUES ('277', '南沙群岛', '2', '22');
INSERT INTO region VALUES ('278', '乐东黎族自治县', '2', '22');
INSERT INTO region VALUES ('279', '临高县', '2', '22');
INSERT INTO region VALUES ('280', '万宁', '2', '22');
INSERT INTO region VALUES ('281', '成都', '2', '23');
INSERT INTO region VALUES ('282', '自贡', '2', '23');
INSERT INTO region VALUES ('283', '攀枝花', '2', '23');
INSERT INTO region VALUES ('284', '泸州', '2', '23');
INSERT INTO region VALUES ('285', '德阳', '2', '23');
INSERT INTO region VALUES ('286', '绵阳', '2', '23');
INSERT INTO region VALUES ('287', '广元', '2', '23');
INSERT INTO region VALUES ('288', '遂宁', '2', '23');
INSERT INTO region VALUES ('289', '内江', '2', '23');
INSERT INTO region VALUES ('290', '乐山', '2', '23');
INSERT INTO region VALUES ('291', '南充', '2', '23');
INSERT INTO region VALUES ('292', '宜宾', '2', '23');
INSERT INTO region VALUES ('293', '广安', '2', '23');
INSERT INTO region VALUES ('294', '达州', '2', '23');
INSERT INTO region VALUES ('295', '眉山', '2', '23');
INSERT INTO region VALUES ('296', '雅安', '2', '23');
INSERT INTO region VALUES ('297', '巴中', '2', '23');
INSERT INTO region VALUES ('298', '资阳', '2', '23');
INSERT INTO region VALUES ('299', '阿坝', '2', '23');
INSERT INTO region VALUES ('300', '甘孜', '2', '23');
INSERT INTO region VALUES ('301', '凉山', '2', '23');
INSERT INTO region VALUES ('302', '贵阳', '2', '24');
INSERT INTO region VALUES ('303', '六盘水', '2', '24');
INSERT INTO region VALUES ('304', '遵义', '2', '24');
INSERT INTO region VALUES ('305', '安顺', '2', '24');
INSERT INTO region VALUES ('306', '铜仁', '2', '24');
INSERT INTO region VALUES ('307', '毕节', '2', '24');
INSERT INTO region VALUES ('308', '黔西南', '2', '24');
INSERT INTO region VALUES ('309', '黔东南', '2', '24');
INSERT INTO region VALUES ('310', '黔南', '2', '24');
INSERT INTO region VALUES ('311', '昆明', '2', '25');
INSERT INTO region VALUES ('312', '曲靖', '2', '25');
INSERT INTO region VALUES ('313', '玉溪', '2', '25');
INSERT INTO region VALUES ('314', '保山', '2', '25');
INSERT INTO region VALUES ('315', '昭通', '2', '25');
INSERT INTO region VALUES ('316', '丽江', '2', '25');
INSERT INTO region VALUES ('317', '普洱', '2', '25');
INSERT INTO region VALUES ('318', '临沧', '2', '25');
INSERT INTO region VALUES ('319', '文山', '2', '25');
INSERT INTO region VALUES ('320', '红河', '2', '25');
INSERT INTO region VALUES ('321', '西双版纳', '2', '25');
INSERT INTO region VALUES ('322', '楚雄', '2', '25');
INSERT INTO region VALUES ('323', '大理', '2', '25');
INSERT INTO region VALUES ('324', '德宏', '2', '25');
INSERT INTO region VALUES ('325', '怒江', '2', '25');
INSERT INTO region VALUES ('326', '迪庆', '2', '25');
INSERT INTO region VALUES ('327', '拉萨', '2', '26');
INSERT INTO region VALUES ('328', '昌都', '2', '26');
INSERT INTO region VALUES ('329', '山南', '2', '26');
INSERT INTO region VALUES ('330', '日喀则', '2', '26');
INSERT INTO region VALUES ('331', '那曲', '2', '26');
INSERT INTO region VALUES ('332', '阿里', '2', '26');
INSERT INTO region VALUES ('333', '林芝', '2', '26');
INSERT INTO region VALUES ('334', '西安', '2', '27');
INSERT INTO region VALUES ('335', '铜川', '2', '27');
INSERT INTO region VALUES ('336', '宝鸡', '2', '27');
INSERT INTO region VALUES ('337', '咸阳', '2', '27');
INSERT INTO region VALUES ('338', '渭南', '2', '27');
INSERT INTO region VALUES ('339', '延安', '2', '27');
INSERT INTO region VALUES ('340', '汉中', '2', '27');
INSERT INTO region VALUES ('341', '榆林', '2', '27');
INSERT INTO region VALUES ('342', '安康', '2', '27');
INSERT INTO region VALUES ('343', '商洛', '2', '27');
INSERT INTO region VALUES ('344', '兰州', '2', '28');
INSERT INTO region VALUES ('345', '嘉峪关', '2', '28');
INSERT INTO region VALUES ('346', '金昌', '2', '28');
INSERT INTO region VALUES ('347', '白银', '2', '28');
INSERT INTO region VALUES ('348', '天水', '2', '28');
INSERT INTO region VALUES ('349', '武威', '2', '28');
INSERT INTO region VALUES ('350', '张掖', '2', '28');
INSERT INTO region VALUES ('351', '平凉', '2', '28');
INSERT INTO region VALUES ('352', '酒泉', '2', '28');
INSERT INTO region VALUES ('353', '庆阳', '2', '28');
INSERT INTO region VALUES ('354', '定西', '2', '28');
INSERT INTO region VALUES ('355', '陇南', '2', '28');
INSERT INTO region VALUES ('356', '临夏', '2', '28');
INSERT INTO region VALUES ('357', '甘南', '2', '28');
INSERT INTO region VALUES ('358', '西宁', '2', '29');
INSERT INTO region VALUES ('359', '海东', '2', '29');
INSERT INTO region VALUES ('360', '海北', '2', '29');
INSERT INTO region VALUES ('361', '黄南', '2', '29');
INSERT INTO region VALUES ('362', '海南', '2', '29');
INSERT INTO region VALUES ('363', '果洛', '2', '29');
INSERT INTO region VALUES ('364', '玉树', '2', '29');
INSERT INTO region VALUES ('365', '海西', '2', '29');
INSERT INTO region VALUES ('366', '银川', '2', '30');
INSERT INTO region VALUES ('367', '石嘴山', '2', '30');
INSERT INTO region VALUES ('368', '吴忠', '2', '30');
INSERT INTO region VALUES ('369', '固原', '2', '30');
INSERT INTO region VALUES ('370', '中卫', '2', '30');
INSERT INTO region VALUES ('371', '乌鲁木齐', '2', '31');
INSERT INTO region VALUES ('372', '克拉玛依', '2', '31');
INSERT INTO region VALUES ('373', '吐鲁番', '2', '31');
INSERT INTO region VALUES ('374', '哈密', '2', '31');
INSERT INTO region VALUES ('375', '和田', '2', '31');
INSERT INTO region VALUES ('376', '阿克苏', '2', '31');
INSERT INTO region VALUES ('377', '喀什', '2', '31');
INSERT INTO region VALUES ('378', '克孜勒苏柯尔克孜', '2', '31');
INSERT INTO region VALUES ('379', '巴音郭楞蒙古', '2', '31');
INSERT INTO region VALUES ('380', '昌吉', '2', '31');
INSERT INTO region VALUES ('381', '博尔塔拉蒙古', '2', '31');
INSERT INTO region VALUES ('382', '伊犁哈萨克', '2', '31');
INSERT INTO region VALUES ('383', '塔城', '2', '31');
INSERT INTO region VALUES ('384', '阿勒泰', '2', '31');
INSERT INTO region VALUES ('385', '台北', '2', '32');
INSERT INTO region VALUES ('386', '高雄', '2', '32');
INSERT INTO region VALUES ('387', '基隆', '2', '32');
INSERT INTO region VALUES ('388', '台中', '2', '32');
INSERT INTO region VALUES ('389', '台南', '2', '32');
INSERT INTO region VALUES ('390', '新竹', '2', '32');
INSERT INTO region VALUES ('391', '香港', '2', '33');
INSERT INTO region VALUES ('392', '澳门', '2', '33');

-- ----------------------------
-- Table structure for `report_param`
-- ----------------------------
DROP TABLE IF EXISTS `report_param`;
CREATE TABLE `report_param` (
  `paramId` bigint(20) NOT NULL AUTO_INCREMENT,
  `reportId` bigint(20) NOT NULL COMMENT '所属报表',
  `paramName` varchar(64) NOT NULL COMMENT '参数名称',
  `paramKey` varchar(64) NOT NULL COMMENT '参数Key',
  `defaultVal` varchar(128) NOT NULL COMMENT '缺省值',
  `paramType` varchar(32) NOT NULL COMMENT '类型\r\n            字符类型--varchar\r\n            整型--int\r\n            精度型--decimal\r\n            日期型--date\r\n            日期时间型--datetime\r\n            ',
  `sn` int(11) NOT NULL COMMENT '系列号',
  PRIMARY KEY (`paramId`),
  KEY `FK_RP_R_RPT` (`reportId`) USING BTREE,
  CONSTRAINT `report_param_ibfk_1` FOREIGN KEY (`reportId`) REFERENCES `report_template` (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表参数';

-- ----------------------------
-- Records of report_param
-- ----------------------------

-- ----------------------------
-- Table structure for `report_template`
-- ----------------------------
DROP TABLE IF EXISTS `report_template`;
CREATE TABLE `report_template` (
  `reportId` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) NOT NULL COMMENT '标题',
  `descp` varchar(500) NOT NULL COMMENT '描述',
  `reportLocation` varchar(128) NOT NULL COMMENT '报表模块的jasper文件的路径',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  `updatetime` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`reportId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='报表模板\r\nreport_template';

-- ----------------------------
-- Records of report_template
-- ----------------------------

-- ----------------------------
-- Table structure for `resume`
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume` (
  `resumeId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(64) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `address` varchar(128) DEFAULT NULL,
  `zip` varchar(32) DEFAULT NULL,
  `sex` varchar(32) DEFAULT NULL,
  `position` varchar(64) DEFAULT NULL,
  `phone` varchar(64) DEFAULT NULL,
  `mobile` varchar(64) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `hobby` varchar(256) DEFAULT NULL,
  `religion` varchar(128) DEFAULT NULL,
  `party` varchar(128) DEFAULT NULL,
  `nationality` varchar(32) DEFAULT NULL,
  `race` varchar(32) DEFAULT NULL,
  `birthPlace` varchar(128) DEFAULT NULL,
  `eduCollege` varchar(128) DEFAULT NULL,
  `eduDegree` varchar(128) DEFAULT NULL,
  `eduMajor` varchar(128) DEFAULT NULL,
  `startWorkDate` datetime DEFAULT NULL,
  `idNo` varchar(64) DEFAULT NULL,
  `photo` varchar(128) DEFAULT NULL,
  `status` varchar(64) DEFAULT NULL COMMENT '状态\r\n            \r\n            通过\r\n            未通过\r\n            准备安排面试\r\n            面试通过\r\n            \r\n            ',
  `memo` varchar(1024) DEFAULT NULL,
  `registor` varchar(64) DEFAULT NULL,
  `regTime` datetime DEFAULT NULL,
  `workCase` text,
  `trainCase` text,
  `projectCase` text,
  PRIMARY KEY (`resumeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='简历管理';

-- ----------------------------
-- Records of resume
-- ----------------------------

-- ----------------------------
-- Table structure for `resume_file`
-- ----------------------------
DROP TABLE IF EXISTS `resume_file`;
CREATE TABLE `resume_file` (
  `fileId` bigint(20) NOT NULL,
  `resumeId` bigint(20) NOT NULL,
  PRIMARY KEY (`fileId`,`resumeId`),
  KEY `FK_RMF_R_RM` (`resumeId`) USING BTREE,
  CONSTRAINT `resume_file_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`),
  CONSTRAINT `resume_file_ibfk_2` FOREIGN KEY (`resumeId`) REFERENCES `resume` (`resumeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resume_file
-- ----------------------------

-- ----------------------------
-- Table structure for `role_fun`
-- ----------------------------
DROP TABLE IF EXISTS `role_fun`;
CREATE TABLE `role_fun` (
  `roleId` bigint(20) NOT NULL,
  `functionId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`functionId`),
  KEY `FK_RF_R_AFN` (`functionId`) USING BTREE,
  CONSTRAINT `role_fun_ibfk_1` FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`),
  CONSTRAINT `role_fun_ibfk_2` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_fun
-- ----------------------------
INSERT INTO role_fun VALUES ('6', '1');
INSERT INTO role_fun VALUES ('6', '2');
INSERT INTO role_fun VALUES ('6', '3');
INSERT INTO role_fun VALUES ('6', '4');
INSERT INTO role_fun VALUES ('6', '5');
INSERT INTO role_fun VALUES ('6', '6');
INSERT INTO role_fun VALUES ('6', '7');
INSERT INTO role_fun VALUES ('6', '8');
INSERT INTO role_fun VALUES ('6', '9');
INSERT INTO role_fun VALUES ('6', '10');
INSERT INTO role_fun VALUES ('6', '11');
INSERT INTO role_fun VALUES ('6', '12');
INSERT INTO role_fun VALUES ('6', '13');
INSERT INTO role_fun VALUES ('6', '14');
INSERT INTO role_fun VALUES ('6', '15');
INSERT INTO role_fun VALUES ('6', '16');
INSERT INTO role_fun VALUES ('6', '17');
INSERT INTO role_fun VALUES ('6', '18');
INSERT INTO role_fun VALUES ('6', '19');
INSERT INTO role_fun VALUES ('6', '20');
INSERT INTO role_fun VALUES ('6', '21');
INSERT INTO role_fun VALUES ('6', '22');
INSERT INTO role_fun VALUES ('6', '23');
INSERT INTO role_fun VALUES ('6', '24');
INSERT INTO role_fun VALUES ('3', '25');
INSERT INTO role_fun VALUES ('3', '26');
INSERT INTO role_fun VALUES ('3', '27');
INSERT INTO role_fun VALUES ('3', '28');
INSERT INTO role_fun VALUES ('3', '29');
INSERT INTO role_fun VALUES ('4', '30');
INSERT INTO role_fun VALUES ('4', '31');
INSERT INTO role_fun VALUES ('4', '32');
INSERT INTO role_fun VALUES ('4', '33');
INSERT INTO role_fun VALUES ('2', '34');
INSERT INTO role_fun VALUES ('4', '34');
INSERT INTO role_fun VALUES ('5', '34');
INSERT INTO role_fun VALUES ('4', '35');
INSERT INTO role_fun VALUES ('4', '36');
INSERT INTO role_fun VALUES ('4', '37');
INSERT INTO role_fun VALUES ('4', '38');
INSERT INTO role_fun VALUES ('4', '39');
INSERT INTO role_fun VALUES ('4', '40');
INSERT INTO role_fun VALUES ('4', '41');
INSERT INTO role_fun VALUES ('4', '42');
INSERT INTO role_fun VALUES ('4', '43');
INSERT INTO role_fun VALUES ('4', '44');
INSERT INTO role_fun VALUES ('4', '45');
INSERT INTO role_fun VALUES ('4', '46');
INSERT INTO role_fun VALUES ('4', '47');
INSERT INTO role_fun VALUES ('4', '48');
INSERT INTO role_fun VALUES ('5', '68');
INSERT INTO role_fun VALUES ('5', '69');
INSERT INTO role_fun VALUES ('5', '70');
INSERT INTO role_fun VALUES ('5', '71');
INSERT INTO role_fun VALUES ('5', '72');
INSERT INTO role_fun VALUES ('5', '73');
INSERT INTO role_fun VALUES ('5', '74');
INSERT INTO role_fun VALUES ('5', '75');
INSERT INTO role_fun VALUES ('5', '76');
INSERT INTO role_fun VALUES ('5', '77');
INSERT INTO role_fun VALUES ('5', '78');
INSERT INTO role_fun VALUES ('5', '79');
INSERT INTO role_fun VALUES ('5', '80');
INSERT INTO role_fun VALUES ('5', '81');
INSERT INTO role_fun VALUES ('5', '82');
INSERT INTO role_fun VALUES ('5', '83');
INSERT INTO role_fun VALUES ('5', '84');
INSERT INTO role_fun VALUES ('5', '85');
INSERT INTO role_fun VALUES ('5', '86');
INSERT INTO role_fun VALUES ('5', '87');
INSERT INTO role_fun VALUES ('5', '88');
INSERT INTO role_fun VALUES ('5', '89');
INSERT INTO role_fun VALUES ('5', '90');
INSERT INTO role_fun VALUES ('5', '91');
INSERT INTO role_fun VALUES ('5', '92');
INSERT INTO role_fun VALUES ('5', '93');
INSERT INTO role_fun VALUES ('5', '94');
INSERT INTO role_fun VALUES ('5', '95');
INSERT INTO role_fun VALUES ('2', '96');
INSERT INTO role_fun VALUES ('2', '97');
INSERT INTO role_fun VALUES ('2', '98');
INSERT INTO role_fun VALUES ('2', '99');
INSERT INTO role_fun VALUES ('2', '100');
INSERT INTO role_fun VALUES ('2', '101');
INSERT INTO role_fun VALUES ('2', '102');
INSERT INTO role_fun VALUES ('2', '103');
INSERT INTO role_fun VALUES ('2', '104');
INSERT INTO role_fun VALUES ('2', '105');
INSERT INTO role_fun VALUES ('2', '106');
INSERT INTO role_fun VALUES ('2', '107');
INSERT INTO role_fun VALUES ('2', '108');
INSERT INTO role_fun VALUES ('2', '109');
INSERT INTO role_fun VALUES ('2', '110');
INSERT INTO role_fun VALUES ('2', '111');
INSERT INTO role_fun VALUES ('2', '112');
INSERT INTO role_fun VALUES ('2', '113');
INSERT INTO role_fun VALUES ('2', '114');
INSERT INTO role_fun VALUES ('2', '115');
INSERT INTO role_fun VALUES ('2', '116');
INSERT INTO role_fun VALUES ('2', '117');
INSERT INTO role_fun VALUES ('2', '118');
INSERT INTO role_fun VALUES ('2', '119');
INSERT INTO role_fun VALUES ('2', '120');
INSERT INTO role_fun VALUES ('2', '121');
INSERT INTO role_fun VALUES ('2', '122');
INSERT INTO role_fun VALUES ('2', '123');
INSERT INTO role_fun VALUES ('2', '124');
INSERT INTO role_fun VALUES ('2', '125');
INSERT INTO role_fun VALUES ('2', '126');
INSERT INTO role_fun VALUES ('2', '127');
INSERT INTO role_fun VALUES ('2', '128');
INSERT INTO role_fun VALUES ('2', '129');
INSERT INTO role_fun VALUES ('2', '130');
INSERT INTO role_fun VALUES ('2', '131');
INSERT INTO role_fun VALUES ('2', '132');
INSERT INTO role_fun VALUES ('2', '133');
INSERT INTO role_fun VALUES ('2', '134');
INSERT INTO role_fun VALUES ('2', '135');
INSERT INTO role_fun VALUES ('2', '136');
INSERT INTO role_fun VALUES ('2', '137');
INSERT INTO role_fun VALUES ('2', '138');
INSERT INTO role_fun VALUES ('2', '139');
INSERT INTO role_fun VALUES ('2', '140');
INSERT INTO role_fun VALUES ('2', '141');
INSERT INTO role_fun VALUES ('2', '142');
INSERT INTO role_fun VALUES ('2', '143');
INSERT INTO role_fun VALUES ('2', '144');
INSERT INTO role_fun VALUES ('2', '145');
INSERT INTO role_fun VALUES ('2', '146');
INSERT INTO role_fun VALUES ('2', '147');
INSERT INTO role_fun VALUES ('2', '148');

-- ----------------------------
-- Table structure for `salary_item`
-- ----------------------------
DROP TABLE IF EXISTS `salary_item`;
CREATE TABLE `salary_item` (
  `salaryItemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(128) NOT NULL COMMENT '项目名称',
  `defaultVal` decimal(18,2) NOT NULL COMMENT '缺省值',
  PRIMARY KEY (`salaryItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='薪酬组成项目';

-- ----------------------------
-- Records of salary_item
-- ----------------------------

-- ----------------------------
-- Table structure for `salary_payoff`
-- ----------------------------
DROP TABLE IF EXISTS `salary_payoff`;
CREATE TABLE `salary_payoff` (
  `recordId` bigint(20) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(64) NOT NULL COMMENT '员工姓名',
  `userId` bigint(20) NOT NULL COMMENT '所属员工',
  `profileNo` varchar(128) DEFAULT NULL COMMENT '档案编号',
  `standardId` bigint(20) NOT NULL,
  `idNo` varchar(128) DEFAULT NULL COMMENT '身份证号',
  `standAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '薪标金额',
  `encourageAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '奖励金额',
  `deductAmount` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '扣除工资',
  `achieveAmount` decimal(18,2) DEFAULT '0.00' COMMENT '效绩工资',
  `encourageDesc` varchar(512) DEFAULT NULL COMMENT '奖励描述',
  `deductDesc` varchar(512) DEFAULT NULL COMMENT '扣除描述',
  `memo` varchar(512) DEFAULT NULL COMMENT '备注描述',
  `acutalAmount` decimal(18,2) DEFAULT NULL COMMENT '实发金额',
  `regTime` datetime NOT NULL COMMENT '登记时间',
  `register` varchar(64) DEFAULT NULL COMMENT '登记人',
  `checkOpinion` varchar(1024) DEFAULT NULL,
  `checkName` varchar(64) DEFAULT NULL COMMENT '审批人',
  `checkTime` datetime DEFAULT NULL COMMENT '审批时间',
  `checkStatus` smallint(6) DEFAULT NULL COMMENT '审批状态\r\n            0=草稿\r\n            1=通过审批\r\n            2=未通过审批\r\n            ',
  `startTime` datetime NOT NULL,
  `provident` decimal(18,2) DEFAULT NULL,
  `endTime` datetime NOT NULL,
  `insurance` decimal(18,2) DEFAULT NULL,
  `selftax` decimal(18,2) DEFAULT NULL,
  `perCoefficient` decimal(18,2) DEFAULT NULL COMMENT '绩效系数',
  `issuedAmount` decimal(18,2) DEFAULT NULL,
  `taxableAmount` decimal(18,2) DEFAULT NULL,
  `perNumber` decimal(18,2) DEFAULT NULL COMMENT '绩效基数',
  PRIMARY KEY (`recordId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of salary_payoff
-- ----------------------------

-- ----------------------------
-- Table structure for `short_message`
-- ----------------------------
DROP TABLE IF EXISTS `short_message`;
CREATE TABLE `short_message` (
  `messageId` bigint(20) NOT NULL AUTO_INCREMENT,
  `senderId` bigint(20) DEFAULT NULL COMMENT '主键',
  `content` varchar(256) NOT NULL,
  `sender` varchar(64) NOT NULL,
  `msgType` smallint(6) NOT NULL COMMENT '1=个人信息\r\n            2=日程安排\r\n            3=计划任务\r\n            ',
  `sendTime` datetime NOT NULL,
  PRIMARY KEY (`messageId`),
  KEY `FK_SM_R_AU` (`senderId`) USING BTREE,
  CONSTRAINT `short_message_ibfk_1` FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='短信消息';

-- ----------------------------
-- Records of short_message
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_acreached`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_acreached`;
CREATE TABLE `sp_pa_acreached` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `result` double DEFAULT NULL COMMENT '考核标准达成结果',
  `inputDate` datetime DEFAULT NULL,
  `inputPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '导入模板ID',
  `deptId` bigint(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_50` (`acId`) USING BTREE,
  KEY `FK_Reference_80` (`category`) USING BTREE,
  CONSTRAINT `sp_pa_acreached_ibfk_1` FOREIGN KEY (`acId`) REFERENCES `sp_pa_assessmentcriteria` (`id`),
  CONSTRAINT `sp_pa_acreached_ibfk_2` FOREIGN KEY (`category`) REFERENCES `sp_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标达成数据录入\r\n\r\n1. 提供销售达成导入结果记录\r\n2. 提供其他定量结果记录';

-- ----------------------------
-- Records of sp_pa_acreached
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_acreached_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_acreached_hist`;
CREATE TABLE `sp_pa_acreached_hist` (
  `id` bigint(20) NOT NULL,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `result` double DEFAULT NULL COMMENT '考核标准达成结果',
  `inputDate` datetime DEFAULT NULL,
  `inputPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='指标达成数据录入\r\n\r\n1. 提供销售达成导入结果记录\r\n2. 提供其他定量结果记录';

-- ----------------------------
-- Records of sp_pa_acreached_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_assessmentcriteria`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_assessmentcriteria`;
CREATE TABLE `sp_pa_assessmentcriteria` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acName` varchar(100) DEFAULT NULL COMMENT '考核标准名称',
  `acKey` varchar(50) DEFAULT NULL COMMENT '考核标准关键字',
  `acDesc` varchar(200) DEFAULT NULL COMMENT '考核标准描述',
  `isSalesAC` int(11) DEFAULT NULL COMMENT '是否销售类定量考核标准',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态：\r\n            0：草稿\r\n            1：发布\r\n            2：删除',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核标准表，记录定量考核的考核标准，以提供后期财务计算';

-- ----------------------------
-- Records of sp_pa_assessmentcriteria
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_assessmenttasksassigned`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_assessmenttasksassigned`;
CREATE TABLE `sp_pa_assessmenttasksassigned` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准关联',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `target` double DEFAULT NULL COMMENT '目标值',
  `publishDate` datetime DEFAULT NULL,
  `publishPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `templateId` varchar(100) DEFAULT NULL COMMENT '导入模板ID',
  `deptId` bigint(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_40` (`acId`) USING BTREE,
  KEY `FK_Reference_70` (`category`) USING BTREE,
  CONSTRAINT `sp_pa_assessmenttasksassigned_ibfk_1` FOREIGN KEY (`acId`) REFERENCES `sp_pa_assessmentcriteria` (`id`),
  CONSTRAINT `sp_pa_assessmenttasksassigned_ibfk_2` FOREIGN KEY (`category`) REFERENCES `sp_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核任务额度下达\r\n\r\n根据考核标准，下达考核标准对应的完成目标，这个目标值用来计算最后得分。\r\n                                                  -';

-- ----------------------------
-- Records of sp_pa_assessmenttasksassigned
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_assessmenttasksassigned_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_assessmenttasksassigned_hist`;
CREATE TABLE `sp_pa_assessmenttasksassigned_hist` (
  `id` bigint(20) NOT NULL,
  `acId` bigint(20) DEFAULT NULL COMMENT '考核标准关联',
  `category` bigint(20) DEFAULT NULL COMMENT '品类',
  `target` double DEFAULT NULL COMMENT '目标值',
  `publishDate` datetime DEFAULT NULL,
  `publishPerson` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='考核任务额度下达\r\n\r\n根据考核标准，下达考核标准对应的完成目标，这个目标值用来计算最后得分。\r\n                                                       -';

-- ----------------------------
-- Records of sp_pa_assessmenttasksassigned_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_authorizepbc`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_authorizepbc`;
CREATE TABLE `sp_pa_authorizepbc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '被授权人',
  `authDate` datetime DEFAULT NULL,
  `authPerson` bigint(20) DEFAULT NULL,
  `pbcId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_150` (`pbcId`) USING BTREE,
  CONSTRAINT `sp_pa_authorizepbc_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='多人打分授权记录表';

-- ----------------------------
-- Records of sp_pa_authorizepbc
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_authpbcitem`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_authpbcitem`;
CREATE TABLE `sp_pa_authpbcitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apbcId` bigint(20) DEFAULT NULL COMMENT '被授权PBC',
  `akpiItem2uId` bigint(20) DEFAULT NULL,
  `result` double DEFAULT NULL COMMENT '考核得分',
  `weight` double DEFAULT NULL COMMENT '权重',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_160` (`apbcId`) USING BTREE,
  KEY `FK_Reference_170` (`akpiItem2uId`) USING BTREE,
  CONSTRAINT `sp_pa_authpbcitem_ibfk_1` FOREIGN KEY (`apbcId`) REFERENCES `sp_pa_authorizepbc` (`id`),
  CONSTRAINT `sp_pa_authpbcitem_ibfk_2` FOREIGN KEY (`akpiItem2uId`) REFERENCES `sp_pa_kpiitem2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='由被授权PBC拷贝或来的定性考核项目';

-- ----------------------------
-- Records of sp_pa_authpbcitem
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_category`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_category`;
CREATE TABLE `sp_pa_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_60` (`parentId`) USING BTREE,
  CONSTRAINT `sp_pa_category_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `sp_pa_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品类表\r\n来源于单品系统';

-- ----------------------------
-- Records of sp_pa_category
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_datadictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_datadictionary`;
CREATE TABLE `sp_pa_datadictionary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) DEFAULT NULL COMMENT '分类名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父类型ID',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_110` (`parentId`) USING BTREE,
  CONSTRAINT `sp_pa_datadictionary_ibfk_1` FOREIGN KEY (`parentId`) REFERENCES `sp_pa_datadictionary` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='数据字典表';

-- ----------------------------
-- Records of sp_pa_datadictionary
-- ----------------------------
INSERT INTO sp_pa_datadictionary VALUES ('1', '数据字典基础字段', '1');
INSERT INTO sp_pa_datadictionary VALUES ('2', '考核指标类型', '1');
INSERT INTO sp_pa_datadictionary VALUES ('3', '考核频度', '1');
INSERT INTO sp_pa_datadictionary VALUES ('4', '考核方式', '1');
INSERT INTO sp_pa_datadictionary VALUES ('5', '业务目标', '2');
INSERT INTO sp_pa_datadictionary VALUES ('6', '员工管理目标', '2');
INSERT INTO sp_pa_datadictionary VALUES ('7', '月度', '3');
INSERT INTO sp_pa_datadictionary VALUES ('8', '无效字段', '1');
INSERT INTO sp_pa_datadictionary VALUES ('9', '季度', '3');
INSERT INTO sp_pa_datadictionary VALUES ('10', '半年', '3');
INSERT INTO sp_pa_datadictionary VALUES ('11', '年度', '3');
INSERT INTO sp_pa_datadictionary VALUES ('12', '定性考核', '4');
INSERT INTO sp_pa_datadictionary VALUES ('13', '定量考核', '4');
INSERT INTO sp_pa_datadictionary VALUES ('14', '个人发展目标', '2');
INSERT INTO sp_pa_datadictionary VALUES ('15', 'PBC类型', '1');
INSERT INTO sp_pa_datadictionary VALUES ('16', '本部门巡店', '15');
INSERT INTO sp_pa_datadictionary VALUES ('17', '跨部门巡店', '15');
INSERT INTO sp_pa_datadictionary VALUES ('18', '防损体系(安全成效)', '15');
INSERT INTO sp_pa_datadictionary VALUES ('19', '客服体系', '15');
INSERT INTO sp_pa_datadictionary VALUES ('20', '防损体系(标准化执行)', '15');

-- ----------------------------
-- Table structure for `sp_pa_kpiitem`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpiitem`;
CREATE TABLE `sp_pa_kpiitem` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1001` (`pbcId`) USING BTREE,
  KEY `FK_Reference_901` (`piId`) USING BTREE,
  CONSTRAINT `sp_pa_kpiitem_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc` (`id`),
  CONSTRAINT `sp_pa_kpiitem_ibfk_2` FOREIGN KEY (`piId`) REFERENCES `sp_pa_performanceindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='KPI考核模板包含项目\r\n该表格中，考核结果只记录最终得分，评分过程记录于达成表';

-- ----------------------------
-- Records of sp_pa_kpiitem
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpiitem2user`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpiitem2user`;
CREATE TABLE `sp_pa_kpiitem2user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_310` (`pbcId`) USING BTREE,
  CONSTRAINT `sp_pa_kpiitem2user_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc2user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of sp_pa_kpiitem2user
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpiitem2usercmp`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpiitem2usercmp`;
CREATE TABLE `sp_pa_kpiitem2usercmp` (
  `id` bigint(20) NOT NULL,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_3103` (`pbcId`) USING BTREE,
  CONSTRAINT `sp_pa_kpiitem2usercmp_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc2usercmp` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of sp_pa_kpiitem2usercmp
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpiitem2user_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpiitem2user_hist`;
CREATE TABLE `sp_pa_kpiitem2user_hist` (
  `id` bigint(20) NOT NULL,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_3101` (`pbcId`) USING BTREE,
  CONSTRAINT `sp_pa_kpiitem2user_hist_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc2user_hist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的KPIPBC的内容，来源于KPIItem表，是该表的数据拷贝。\r\n\r\n不同岗位所属的PB';

-- ----------------------------
-- Records of sp_pa_kpiitem2user_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpiitem_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpiitem_hist`;
CREATE TABLE `sp_pa_kpiitem_hist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcId` bigint(20) DEFAULT NULL COMMENT '考核模板ID',
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标ID',
  `weight` double DEFAULT NULL COMMENT '权值',
  `result` double DEFAULT NULL COMMENT '考核最后得分',
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_1002` (`pbcId`) USING BTREE,
  CONSTRAINT `sp_pa_kpiitem_hist_ibfk_1` FOREIGN KEY (`pbcId`) REFERENCES `sp_pa_kpipbc_hist` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='KPI考核模板包含项目\r\n该表格中，考核结果只记录最终得分，评分过程记录于达成表';

-- ----------------------------
-- Records of sp_pa_kpiitem_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpipbc`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpipbc`;
CREATE TABLE `sp_pa_kpipbc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcName` varchar(150) DEFAULT NULL COMMENT '模板名称',
  `belongDept` bigint(20) DEFAULT NULL COMMENT '隶属部门',
  `belongPost` bigint(20) DEFAULT NULL COMMENT '隶属岗位',
  `frequency` bigint(20) DEFAULT NULL COMMENT '考核频度',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` int(11) DEFAULT NULL COMMENT '总分，如果有小数位数则舍去',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `fromPbc` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  `fromPi` bigint(20) DEFAULT NULL COMMENT '来自HR绩效考核考核项ID',
  `pbcType` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核模板管理\r\n';

-- ----------------------------
-- Records of sp_pa_kpipbc
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpipbc2user`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpipbc2user`;
CREATE TABLE `sp_pa_kpipbc2user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcName` varchar(200) DEFAULT NULL,
  `fromPBC` varchar(200) DEFAULT NULL COMMENT '记录来源于那些PBC\r\n            \r\n            内容为用‘,’分割的kbiPBC表的id字符串',
  `belongUser` bigint(20) DEFAULT NULL,
  `frequency` bigint(20) DEFAULT NULL COMMENT '频度',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：未加权值\r\n            1：已加权值',
  `totalScore` float DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  `fromPi` bigint(20) DEFAULT NULL,
  `pbcType` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of sp_pa_kpipbc2user
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpipbc2usercmp`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpipbc2usercmp`;
CREATE TABLE `sp_pa_kpipbc2usercmp` (
  `id` bigint(20) NOT NULL,
  `pbcName` varchar(200) DEFAULT NULL,
  `fromPBC` varchar(200) DEFAULT NULL COMMENT '记录来源于那些PBC\r\n            \r\n            内容为用‘,’分割的kbiPBC表的id字符串',
  `belongUser` bigint(20) DEFAULT NULL,
  `frequency` bigint(20) DEFAULT NULL COMMENT '频度',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` float DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of sp_pa_kpipbc2usercmp
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpipbc2user_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpipbc2user_hist`;
CREATE TABLE `sp_pa_kpipbc2user_hist` (
  `id` bigint(20) NOT NULL,
  `pbcName` varchar(200) DEFAULT NULL,
  `fromPBC` varchar(200) DEFAULT NULL COMMENT '记录来源于那些PBC\r\n            \r\n            内容为用‘,’分割的kbiPBC表的id字符串',
  `belongUser` bigint(20) DEFAULT NULL,
  `frequency` bigint(20) DEFAULT NULL COMMENT '频度',
  `createPerson` bigint(20) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` float DEFAULT NULL,
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='每个人所属的最终PBC\r\n\r\n该表内容来源于根据每个人所属的不同的岗位的PBC合并后的结果。';

-- ----------------------------
-- Records of sp_pa_kpipbc2user_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_kpipbc_hist`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_kpipbc_hist`;
CREATE TABLE `sp_pa_kpipbc_hist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pbcName` varchar(150) DEFAULT NULL COMMENT '模板名称',
  `belongDept` bigint(20) DEFAULT NULL COMMENT '隶属部门',
  `belongPost` bigint(20) DEFAULT NULL COMMENT '隶属岗位',
  `frequency` bigint(20) DEFAULT NULL COMMENT '考核频度',
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `totalScore` int(11) DEFAULT NULL COMMENT '总分，如果有小数位数则舍去',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  `fromPi` bigint(20) DEFAULT NULL,
  `fromPbc` bigint(20) DEFAULT NULL,
  `pbcType` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核模板管理\r\n';

-- ----------------------------
-- Records of sp_pa_kpipbc_hist
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_performanceindex`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_performanceindex`;
CREATE TABLE `sp_pa_performanceindex` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paName` varchar(200) DEFAULT NULL COMMENT '考核指标名称',
  `paType` bigint(20) DEFAULT NULL COMMENT '考核指标类型\r\n            1.销售类--定量\r\n            2.非销售类--定量',
  `paFrequency` bigint(20) DEFAULT NULL COMMENT '考核指标频度',
  `paMode` bigint(20) DEFAULT NULL COMMENT '考核方式：\r\n            1.定量考核--销售类\r\n            2.定性考核--职能类',
  `paIsOnlyNegative` int(11) DEFAULT NULL COMMENT '是否唯一否决条件\r\n            1：是\r\n            0：否',
  `paDesc` varchar(500) DEFAULT NULL COMMENT '考核指标描述',
  `remark` varchar(200) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `createPerson` bigint(20) DEFAULT NULL,
  `publishStatus` int(11) DEFAULT NULL COMMENT '发布状态\r\n            0：草稿\r\n            1：审核中\r\n            2：退回\r\n            3：审核完毕，发布\r\n            4：删除标记',
  `modifyDate` datetime DEFAULT NULL,
  `modifyPerson` bigint(20) DEFAULT NULL,
  `baseScore` double DEFAULT NULL,
  `finalScore` double DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `fromPi` bigint(20) DEFAULT NULL,
  `finalCoefficient` double DEFAULT NULL,
  `belongDept` bigint(20) DEFAULT NULL COMMENT '所属部门',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_130` (`paMode`) USING BTREE,
  KEY `FK_Reference_140` (`paType`) USING BTREE,
  CONSTRAINT `sp_pa_performanceindex_ibfk_1` FOREIGN KEY (`paMode`) REFERENCES `sp_pa_datadictionary` (`id`),
  CONSTRAINT `sp_pa_performanceindex_ibfk_2` FOREIGN KEY (`paType`) REFERENCES `sp_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效考核指标表';

-- ----------------------------
-- Records of sp_pa_performanceindex
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_performanceindexscore`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_performanceindexscore`;
CREATE TABLE `sp_pa_performanceindexscore` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `piId` bigint(20) DEFAULT NULL COMMENT '考核指标名称',
  `pisScore` decimal(2,1) DEFAULT NULL,
  `pisType` bigint(20) DEFAULT NULL COMMENT '考核得分类型\r\n            该类型来源于考核指标，如果考核指标是定性，则该类型也是定性，如果考核指标是定量则该类型也是定量.\r\n            \r\n            如果是定量指标，则该得分需关联定量考核标准表并计算公式',
  `pisDesc` varchar(500) DEFAULT NULL,
  `coefficient` double DEFAULT NULL COMMENT '绩效系数',
  PRIMARY KEY (`id`),
  KEY `FK_Reference_100` (`piId`) USING BTREE,
  KEY `FK_Reference_120` (`pisType`) USING BTREE,
  CONSTRAINT `sp_pa_performanceindexscore_ibfk_1` FOREIGN KEY (`piId`) REFERENCES `sp_pa_performanceindex` (`id`),
  CONSTRAINT `sp_pa_performanceindexscore_ibfk_2` FOREIGN KEY (`pisType`) REFERENCES `sp_pa_datadictionary` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='绩效指标得分';

-- ----------------------------
-- Records of sp_pa_performanceindexscore
-- ----------------------------

-- ----------------------------
-- Table structure for `sp_pa_pisrule`
-- ----------------------------
DROP TABLE IF EXISTS `sp_pa_pisrule`;
CREATE TABLE `sp_pa_pisrule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pisId` bigint(20) DEFAULT NULL COMMENT '对应的考核得分',
  `pisAC` bigint(20) DEFAULT NULL COMMENT '评分标准，引用评分标准表',
  `formula` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_200` (`pisId`) USING BTREE,
  KEY `FK_Reference_301` (`pisAC`) USING BTREE,
  CONSTRAINT `sp_pa_pisrule_ibfk_1` FOREIGN KEY (`pisId`) REFERENCES `sp_pa_performanceindexscore` (`id`),
  CONSTRAINT `sp_pa_pisrule_ibfk_2` FOREIGN KEY (`pisAC`) REFERENCES `sp_pa_assessmentcriteria` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sp_pa_pisrule
-- ----------------------------

-- ----------------------------
-- Table structure for `stand_salary`
-- ----------------------------
DROP TABLE IF EXISTS `stand_salary`;
CREATE TABLE `stand_salary` (
  `standardId` bigint(20) NOT NULL AUTO_INCREMENT,
  `standardNo` varchar(128) NOT NULL COMMENT '薪酬标准编号\r\n            惟一',
  `standardName` varchar(128) NOT NULL COMMENT '标准名称',
  `totalMoney` decimal(18,2) NOT NULL DEFAULT '0.00',
  `framer` varchar(64) DEFAULT NULL,
  `setdownTime` datetime DEFAULT NULL,
  `checkName` varchar(64) DEFAULT NULL,
  `checkTime` datetime DEFAULT NULL,
  `modifyName` varchar(64) DEFAULT NULL,
  `modifyTime` datetime DEFAULT NULL,
  `checkOpinion` varchar(512) DEFAULT NULL,
  `status` smallint(6) NOT NULL COMMENT '0=草稿\r\n            1=审批\r\n            2=未通过审批',
  `memo` varchar(512) DEFAULT NULL,
  `perCoefficient` decimal(18,2) DEFAULT NULL COMMENT '绩效系数',
  `yearEndBonusCoefficient` decimal(18,2) DEFAULT NULL,
  `yearTotalMoney` decimal(18,2) DEFAULT NULL,
  PRIMARY KEY (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of stand_salary
-- ----------------------------

-- ----------------------------
-- Table structure for `stand_salary_item`
-- ----------------------------
DROP TABLE IF EXISTS `stand_salary_item`;
CREATE TABLE `stand_salary_item` (
  `itemId` bigint(20) NOT NULL AUTO_INCREMENT,
  `standardId` bigint(20) NOT NULL,
  `itemName` varchar(64) NOT NULL,
  `amount` decimal(18,2) NOT NULL,
  `salaryItemId` bigint(20) DEFAULT NULL COMMENT '所属工资组成ID\r\n            外键，但不需要在数据库层建立外键',
  PRIMARY KEY (`itemId`),
  KEY `FK_SSI_R_SSY` (`standardId`) USING BTREE,
  CONSTRAINT `stand_salary_item_ibfk_1` FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='薪酬标准明细';

-- ----------------------------
-- Records of stand_salary_item
-- ----------------------------

-- ----------------------------
-- Table structure for `system_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(128) NOT NULL COMMENT '用户名',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `createtime` datetime NOT NULL COMMENT '执行时间',
  `exeOperation` varchar(512) NOT NULL COMMENT '执行操作',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT,
  `configKey` varchar(64) NOT NULL COMMENT 'Key',
  `configName` varchar(64) NOT NULL COMMENT '配置名称',
  `configDesc` varchar(256) DEFAULT NULL COMMENT '配置描述',
  `typeName` varchar(32) NOT NULL COMMENT '所属分类名称',
  `dataType` smallint(6) NOT NULL COMMENT '数据类型\r\n            1=varchar\r\n            2=intger\r\n            3=decimal\r\n            4=datetime\r\n            5=time\r\n            ',
  `dataValue` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='系统配置\r\n\r\n用于系统的全局配置\r\n如邮件服务';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO sys_config VALUES ('1', 'host', '主机Host', '主机IP', '系统邮件配置', '1', 'smtp.shopin.cn');
INSERT INTO sys_config VALUES ('2', 'username', '用户名', '邮件发送的邮箱用户名', '系统邮件配置', '1', 'zhangpeng@shopin.cn');
INSERT INTO sys_config VALUES ('3', 'password', '密码', '邮件发送的邮箱密码', '系统邮件配置', '1', 'zhang1982peng');
INSERT INTO sys_config VALUES ('4', 'from', '来自', '发送人的邮箱或用户名', '系统邮件配置', '1', 'zhangpeng@shopin.cn');
INSERT INTO sys_config VALUES ('5', 'goodsStockUser', '用户帐号', '当库存产生警报时，接收消息的人员', '行政管理配置', '1', 'admin');
INSERT INTO sys_config VALUES ('6', 'codeConfig', '验证码', '登录时是否需要验证码', '验证码配置', '2', '1');
INSERT INTO sys_config VALUES ('7', 'displayName', '显示名称', '邮件发送显示的名称', '系统邮件配置', '1', '上品折扣办公系统');
INSERT INTO sys_config VALUES ('8', 'smtpAuth', '是否发件验证', '邮件发送Smtp验证', '系统邮件配置', '1', 'true');
INSERT INTO sys_config VALUES ('9', 'replyTo', '回复地址', '邮件回复时的地址', '系统以邮件配置', '1', 'zhangpeng@shopin.cn');

-- ----------------------------
-- Table structure for `task_sign`
-- ----------------------------
DROP TABLE IF EXISTS `task_sign`;
CREATE TABLE `task_sign` (
  `signId` bigint(20) NOT NULL,
  `assignId` bigint(20) NOT NULL COMMENT '所属流程设置',
  `voteCounts` int(11) DEFAULT NULL COMMENT '绝对票数',
  `votePercents` int(11) DEFAULT NULL COMMENT '百分比票数',
  `decideType` smallint(6) NOT NULL COMMENT '1=pass 通过\r\n            2=reject 拒绝',
  PRIMARY KEY (`signId`),
  KEY `FK_TS_R_PUA` (`assignId`) USING BTREE,
  CONSTRAINT `task_sign_ibfk_1` FOREIGN KEY (`assignId`) REFERENCES `pro_user_assign` (`assignId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_sign
-- ----------------------------

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userId` bigint(20) NOT NULL COMMENT '主键',
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FK_UR_R_AR` (`roleId`) USING BTREE,
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO user_role VALUES ('1', '-1');
INSERT INTO user_role VALUES ('2', '-1');
INSERT INTO user_role VALUES ('5', '-1');
INSERT INTO user_role VALUES ('6', '-1');
INSERT INTO user_role VALUES ('7', '-1');
INSERT INTO user_role VALUES ('8', '-1');
INSERT INTO user_role VALUES ('9', '-1');
INSERT INTO user_role VALUES ('3', '1');
INSERT INTO user_role VALUES ('5', '6');
INSERT INTO user_role VALUES ('6', '6');
INSERT INTO user_role VALUES ('7', '6');
INSERT INTO user_role VALUES ('8', '6');
INSERT INTO user_role VALUES ('9', '6');
INSERT INTO user_role VALUES ('11', '6');
INSERT INTO user_role VALUES ('10', '20');

-- ----------------------------
-- Table structure for `user_sub`
-- ----------------------------
DROP TABLE IF EXISTS `user_sub`;
CREATE TABLE `user_sub` (
  `subId` bigint(20) NOT NULL AUTO_INCREMENT,
  `subUserId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  PRIMARY KEY (`subId`),
  KEY `FK_USB_R_AU` (`subUserId`) USING BTREE,
  KEY `FK_US_R_AU` (`userId`) USING BTREE,
  CONSTRAINT `user_sub_ibfk_1` FOREIGN KEY (`subUserId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `user_sub_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='subordinate';

-- ----------------------------
-- Records of user_sub
-- ----------------------------

-- ----------------------------
-- Table structure for `work_plan`
-- ----------------------------
DROP TABLE IF EXISTS `work_plan`;
CREATE TABLE `work_plan` (
  `planId` bigint(20) NOT NULL AUTO_INCREMENT,
  `planName` varchar(128) NOT NULL COMMENT '计划名称',
  `planContent` text COMMENT '计划内容',
  `startTime` datetime NOT NULL COMMENT '开始日期',
  `endTime` datetime NOT NULL COMMENT '结束日期',
  `typeId` bigint(20) NOT NULL COMMENT '计划类型',
  `userId` bigint(20) DEFAULT NULL COMMENT '员工ID',
  `issueScope` varchar(2000) DEFAULT NULL COMMENT '发布范围\r\n            0则代表全部部门\r\n            存放所有的参与部门ID\r\n            ',
  `participants` varchar(2000) DEFAULT NULL COMMENT '参与人\r\n            0则代表全部参与\r\n            参与人,即员工ID列表',
  `principal` varchar(256) NOT NULL COMMENT '负责人',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用',
  `isPersonal` smallint(6) NOT NULL COMMENT '是否为个人计划\r\n            1=则为个人工作计划，这时发布范围，参与人均为空，负责人为当前用户\r\n            0=则代表为其他任务',
  `icon` varchar(128) DEFAULT NULL COMMENT '图标',
  PRIMARY KEY (`planId`),
  KEY `FK_WP_R_AU` (`userId`) USING BTREE,
  KEY `FK_WP_R_PT` (`typeId`) USING BTREE,
  CONSTRAINT `work_plan_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`),
  CONSTRAINT `work_plan_ibfk_2` FOREIGN KEY (`typeId`) REFERENCES `plan_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作计划';

-- ----------------------------
-- Records of work_plan
-- ----------------------------
