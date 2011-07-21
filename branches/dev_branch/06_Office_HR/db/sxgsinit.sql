/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50512
Source Host           : localhost:3306
Source Database       : sxgs

Target Server Type    : MYSQL
Target Server Version : 50512
File Encoding         : 65001

Date: 2011-07-15 18:41:13
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `app_function`
-- ----------------------------
DROP TABLE IF EXISTS `app_function`;
CREATE TABLE `app_function` (
`functionId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`funKey`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限Key' ,
`funName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限名称' ,
PRIMARY KEY (`functionId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=149

;

-- ----------------------------
-- Records of app_function
-- ----------------------------
BEGIN;
INSERT INTO app_function VALUES ('1', '_AppUserQuery', '查看员工'), ('2', '_AppUserAdd', '添加员工'), ('3', '_AppUserEdit', '编辑员工'), ('4', '_AppUserDel', '删除员工'), ('5', '_AppRoleList', '查看角色'), ('6', '_AppRoleAdd', '添加角色'), ('7', '_AppRoleEdit', '编辑角色'), ('8', '_AppRoleDel', '删除角色'), ('9', '_AppRoleGrant', '授权角色'), ('10', '_DepartmentQuery', '查看部门'), ('11', '_DepartmentAdd', '新建部门'), ('12', '_DepartmentEdit', '修改部门'), ('13', '_DepartmentDel', '删除部门'), ('14', '_FileAttachQuery', '查看附件'), ('15', '_FileAttachEdit', '编辑附件'), ('16', '_FileAttachDel', '删除附件'), ('17', '_CompanyEdit', '公司信息修改'), ('18', '_FlowQuery', '查看流程'), ('19', '_ProTypeManage', '流程类型'), ('20', '_FlowAdd', '发布流程'), ('21', '_FlowEdit', '编辑流程'), ('22', '_FlowDel', '删除流程'), ('23', '_FlowCheck', '查看'), ('24', '_FlowSetting', '人员设置'), ('25', '_DocFolderSharedManage', '公共文件夹管理'), ('26', '_DocPrivilegeQuery', '查看权限'), ('27', '_DocPrivilegeAdd', '添加权限'), ('28', '_DocPrivilegeEdit', '编辑权限'), ('29', '_DocPrivilegeDel', '删除权限'), ('30', '_PlanTypeQuery', '查看类型'), ('31', '_PlanTypeAdd', '添加类型'), ('32', '_PlanTypeEdit', '编辑类型'), ('33', '_PlanTypeDel', '删除类型'), ('34', '_NewDepPlan', '新建部门任务计划'), ('35', '_NewsQuery', '查看新闻'), ('36', '_NewsAdd', '添加新闻'), ('37', '_NewsEdit', '编辑新闻'), ('38', '_NewsDel', '删除新闻'), ('39', '_NewsCommentQuery', '查看评论'), ('40', '_NewsCommentDel', '删除评论'), ('41', '_NewsTypeQuery', '查看新闻类型'), ('42', '_NewsTypeAdd', '添加新闻类型'), ('43', '_NewsTypeEdit', '修改新闻类型'), ('44', '_NewsTypeDel', '删除新闻类型'), ('45', '_NoticeQuery', '查看公告'), ('46', '_NoticeAdd', '添加公告'), ('47', '_NoticeEdit', '编辑公告'), ('48', '_NoticeDel', '删除公告'), ('49', '_HolidayRecordQuery', '查看假期设置'), ('50', '_HolidayRecordAdd', '添加假期设置'), ('51', '_HolidayRecordEdit', '修改假期设置'), ('52', '_HolidayRecordDel', '删除假期设置'), ('53', '_DutySectonQuery', '查看班次定义'), ('54', '_DutySectonAdd', '添加班次定义'), ('55', '_DutySectonEdit', '修改班次定义'), ('56', '_DutySectonDel', '删除班次定义'), ('57', '_DutySystemQuery', '查看班制定义'), ('58', '_DutySystemAdd', '添加班制定义'), ('59', '_DutySystemEdit', '修改班制定义'), ('60', '_DutySystemDel', '删除班制定义'), ('61', '_DutyQuery', '查看排班'), ('62', '_DutyAdd', '添加排班'), ('63', '_DutyEdit', '修改排班'), ('64', '_DutyDel', '删除排班'), ('65', '_DutyRegisterQuery', '查看考勤信息'), ('66', '_DutyRegisterAdd', '补签'), ('67', '_DutyRegisterDel', '删除考勤信息'), ('68', '_CustomerQuery', '查看客户信息'), ('69', '_CustomerAdd', '添加客户信息'), ('70', '_CustomerEdit', '编辑客户信息'), ('71', '_CustomerDel', '删除客户信息'), ('72', '_CusLinkmanQuery', '查看联系人信息'), ('73', '_CusLinkmanAdd', '添加联系人'), ('74', '_CusLinkmanEdit', '编辑联系人'), ('75', '_CusLinkmanDel', '删除联系人'), ('76', '_CusConnectionQuery', '查看交往信息'), ('77', '_CusConnectionAdd', '添加交往信息'), ('78', '_CusConnectionEdit', '编辑交往信息'), ('79', '_CusConnectionDel', '删除交往信息'), ('80', '_ProjectQuery', '查看项目'), ('81', '_ProjectAdd', '添加项目'), ('82', '_ProjectEdit', '编辑项目'), ('83', '_ProjectDel', '删除项目'), ('84', '_ContractQuery', '查看合同'), ('85', '_ContractAdd', '添加合同'), ('86', '_ContractEdit', '编辑合同'), ('87', '_ContractDel', '删除合同'), ('88', '_ProductQuery', '查看产品'), ('89', '_ProductAdd', '添加产品'), ('90', '_ProductEdit', '编辑产品'), ('91', '_ProductDel', '删除产品'), ('92', '_ProviderQuery', '查看供应商'), ('93', '_ProviderAdd', '添加供应商'), ('94', '_ProviderEdit', '编辑供应商'), ('95', '_ProviderDel', '删除供应商'), ('96', '_OfficeGoodsQuery', '查看办公用品'), ('97', '_OfficeGoodsTypeManage', '用品类型管理'), ('98', '_OfficeGoodsAdd', '添加用品'), ('99', '_OfficeGoodsEdit', '编辑用品'), ('100', '_OfficeGoodsDel', '删除用品'), ('101', '_InStockQuery', '查看入库记录');
INSERT INTO app_function VALUES ('102', '_InStockAdd', '添加入库记录'), ('103', '_InStockEdit', '编辑入库记录'), ('104', '_InStockDel', '删除入库记录'), ('105', '_GoodsApplyQuery', '查看申请记录'), ('106', '_GoodsApplyAdd', '添加申请记录'), ('107', '_GoodsApplyEdit', '编辑申请记录'), ('108', '_GoodsApplyDel', '删除申请记录'), ('109', '_CarQuery', '查看车辆'), ('110', '_CarAdd', '添加车辆'), ('111', '_CarEdit', '编辑车辆'), ('112', '_CarDel', '删除车辆'), ('113', '_CarRepairQuery', '查看维修记录'), ('114', '_CarRepairAdd', '添加维修记录'), ('115', '_CarRepairEdit', '编辑维修记录'), ('116', '_CarRepairDel', '删除维修记录'), ('117', '_CarApplyQuery', '查看车辆申请记录'), ('118', '_CarApplyAdd', '添加申请记录'), ('119', '_CarApplyEdit', '编辑申请记录'), ('120', '_CarApplyDel', '删除申请记录'), ('121', '_DepreTypeQuery', '查看折算类型'), ('122', '_DepreTypeAdd', '添加类型'), ('123', '_DepreTypeEdit', '编辑类型'), ('124', '_DepreTypeDel', '删除类型'), ('125', '_FixedAssetsQuery', '查看固定资产'), ('126', '_AssetsTypeManage', '资产类型管理'), ('127', '_FixedAssetsAdd', '添加资产'), ('128', '_FixedAssetsEdit', '编辑资产'), ('129', '_FixedAssetsDel', '删除资产'), ('130', '_Depreciate', '进行折算'), ('131', '_DepreRecordQuery', '查看折算记录'), ('132', '_BookTypeQuery', '查看类型'), ('133', '_BookTypeAdd', '添加图书类别'), ('134', '_BookTypeEdit', '编辑图书类别'), ('135', '_BookTypeDel', '删除图书类别'), ('136', '_BookQuery', '查看图书'), ('137', '_BookAdd', '添加图书'), ('138', '_BookEdit', '编辑图书'), ('139', '_BookDel', '删除图书'), ('140', '_BookBorrowQuery', '查看记录'), ('141', '_BookBorrowAdd', '添加借阅记录'), ('142', '_BookBorrowEdit', '编辑借阅记录'), ('143', '_BookReturn', '归还'), ('144', '_BookBorrowDel', '删除借阅记录'), ('145', '_BookReturnQuery', '查看记录'), ('146', '_BookReturnAdd', '添加归还记录'), ('147', '_BookReturnEdit', '编辑归还记录'), ('148', '_BookReturnDel', '删除归还记录');
COMMIT;

-- ----------------------------
-- Table structure for `app_role`
-- ----------------------------
DROP TABLE IF EXISTS `app_role`;
CREATE TABLE `app_role` (
`roleId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`roleName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称' ,
`roleDesc`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述' ,
`status`  smallint(6) NOT NULL COMMENT '状态' ,
`rights`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isDefaultIn`  smallint(6) NOT NULL ,
PRIMARY KEY (`roleId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='角色表'
AUTO_INCREMENT=20

;

-- ----------------------------
-- Records of app_role
-- ----------------------------
BEGIN;
INSERT INTO app_role VALUES ('-1', '超级管理员', '超级管理员,具有所有权限', '1', '__ALL', '0'), ('1', '[人事经理]', '管理人事的经理', '1', 'SystemSetting,AppUserView,_AppUserQuery,_AppUserAdd,_AppUserEdit,_AppUserDel,DepartmentView,_DepartmentQuery,_DepartmentAdd,_DepartmentEdit,_DepartmentDel,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,DutyManager,Duty,HolidayRecordView,_HolidayRecordQuery,_HolidayRecordAdd,_HolidayRecordEdit,_HolidayRecordDel,DutySectionView,_DutySectonQuery,_DutySectonAdd,_DutySectonEdit,_DutySectonDel,DutySystemView,_DutySystemQuery,_DutySystemAdd,_DutySystemEdit,_DutySystemDel,DutyView,_DutyQuery,_DutyAdd,_DutyEdit,_DutyDel,DutyRegisterView,_DutyRegisterQuery,_DutyRegisterAdd,_DutyRegisterDel', '1'), ('2', '[行政经理]', '管理行政', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,Administrator,GoodManeger,OfficeGoodsManageView,_OfficeGoodsQuery,_OfficeGoodsTypeManage,_OfficeGoodsAdd,_OfficeGoodsEdit,_OfficeGoodsDel,InStockView,_InStockQuery,_InStockAdd,_InStockEdit,_InStockDel,GoodsApplyView,_GoodsApplyQuery,_GoodsApplyAdd,_GoodsApplyEdit,_GoodsApplyDel,CarManeger,CarView,_CarQuery,_CarAdd,_CarEdit,_CarDel,CartRepairView,_CarRepairQuery,_CarRepairAdd,_CarRepairEdit,_CarRepairDel,CarApplyView,_CarApplyQuery,_CarApplyAdd,_CarApplyEdit,_CarApplyDel,FixedAssetsManage,DepreTypeView,_DepreTypeQuery,_DepreTypeAdd,_DepreTypeEdit,_DepreTypeDel,FixedAssetsManageView,_FixedAssetsQuery,_AssetsTypeManage,_FixedAssetsAdd,_FixedAssetsEdit,_FixedAssetsDel,_Depreciate,DepreRecordView,_DepreRecordQuery,BookManager,BookTypeView,_BookTypeQuery,_BookTypeAdd,_BookTypeEdit,_BookTypeDel,BookManageView,_BookQuery,_BookAdd,_BookEdit,_BookDel,BookBorrowView,_BookBorrowQuery,_BookBorrowAdd,_BookBorrowEdit,_BookReturn,_BookBorrowDel,BookReturnView,_BookReturnQuery,_BookReturnAdd,_BookReturnEdit,_BookReturnDel', '1'), ('3', '[文档管理员]', '管理文档', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,DocFolderSharedView,_DocFolderSharedManage,_DocPrivilegeQuery,_DocPrivilegeAdd,_DocPrivilegeEdit,_DocPrivilegeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '1'), ('4', '[信息管理员]', '管理新闻公告等信息', '1', 'SystemSetting,Task,PlanTypeView,_PlanTypeQuery,_PlanTypeAdd,_PlanTypeEdit,_PlanTypeDel,NewWorkPlanForm,_NewDepPlan,Info,NewsView,_NewsQuery,_NewsAdd,_NewsEdit,_NewsDel,NewsCommentView,_NewsCommentQuery,_NewsCommentDel,NewsTypeView,_NewsTypeQuery,_NewsTypeAdd,_NewsTypeEdit,_NewsTypeDel,NoticeView,_NoticeQuery,_NoticeAdd,_NoticeEdit,_NoticeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '1'), ('5', '[客户经理]', '管理客户信息', '1', 'SystemSetting,PublicDocument,NewPublicDocumentForm,Task,NewWorkPlanForm,_NewDepPlan,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,customer,CustomerView,_CustomerQuery,_CustomerAdd,_CustomerEdit,_CustomerDel,CusLinkmanView,_CusLinkmanQuery,_CusLinkmanAdd,_CusLinkmanEdit,_CusLinkmanDel,CusConnectionView,_CusConnectionQuery,_CusConnectionAdd,_CusConnectionEdit,_CusConnectionDel,ProjectView,_ProjectQuery,_ProjectAdd,_ProjectEdit,_ProjectDel,ContractView,_ContractQuery,_ContractAdd,_ContractEdit,_ContractDel,ProductView,_ProductQuery,_ProductAdd,_ProductEdit,_ProductDel,ProviderView,_ProviderQuery,_ProviderAdd,_ProviderEdit,_ProviderDel', '1'), ('6', '一般用户', '一般用户', '1', 'Archive,ArchFlowConfView,_ArchFlowConfEdit,ArchiveIssue,ArchiveTypeTempView,_ArchiveTypeTempQuery,_ArchivesTypeAdd,_ArchivesTypeEdit,_ArchivesTypeDel,_ArchivesTempAdd,_ArchivesTempEdit,_ArchviesTempDel,ArchivesDraftView,_AchivesDrafAdd,ArchivesDraftManage,_ArchivesDrafmQuery,_ArchivesDrafmEdit,_ArchivesDrafmDel,ArchivesIssueAudit,_ArchivesIssueQuery,_ArchivesIssueEdit,ArchivesIssueLead,_ArchivesIssueLeadQuery,_ArchivesIssueLeadEdit,ArchivesIssueCharge,_ArchivesIssueChargeQuery,_ArchivesIssueChargeEdit,ArchivesIssueProof,_ArchivesIssueProofQuery,_ArchivesIssueProofEdit,ArchivesDocument,_ArchivesDocumentQuery,ArchivesIssueMonitor,_ArchivesIssueMonitorQuery,_ArchivesIssueHasten,ArchivesIssueManage,_ArchivesIssueManageQuery,ArchivesIssueSearch,_ArchivesIssueSearchQuery,DocHistoryView,_DocHistoryQuery,_DocHistoryDel,ArchiveReceive,ArchivesSignView,_ArchivesSignQuery,_ArchivesSignUp,ArchivesRecView,_ArchivesRecQuery,_ArchivesRecAdd,_ArchivesRecEdit,_ArchivesRecDel,ArchivesHandleView,_ArchivesHandleQuery,LeaderReadView,_LeaderReadQuery,ArchDispatchView,_ArchDispatchQuery,ArchUndertakeView,_ArchUndertakeQuery,ArchivesRecCtrlView,_ArchivesRecCtrlQuery,_ArchivesRecHasten,ArchReadView,_ArchReadQuery,ArchRecTypeView,_ArchRecTypeQuery,_ArchRecTypeAdd,_ArchRecTypeEdit,_ArchRecTypeDel,SystemSetting,AppUserView,_AppUserQuery,_AppUserAdd,_AppUserEdit,_AppUserDel,AppRoleView,_AppRoleList,_AppRoleAdd,_AppRoleEdit,_AppRoleDel,_AppRoleGrant,DepartmentView,_DepartmentQuery,_DepartmentAdd,_DepartmentEdit,_DepartmentDel,FileAttachView,_FileAttachQuery,_FileAttachEdit,_FileAttachDel,CompanyView,_CompanyEdit,FlowManagerView,_FlowQuery,_ProTypeManage,_FlowAdd,_FlowEdit,_FlowDel,_FlowCheck,_FlowSetting,PublicDocument,NewPublicDocumentForm,DocFolderSharedView,_DocFolderSharedManage,_DocPrivilegeQuery,_DocPrivilegeAdd,_DocPrivilegeEdit,_DocPrivilegeDel,Task,PlanTypeView,_PlanTypeQuery,_PlanTypeAdd,_PlanTypeEdit,_PlanTypeDel,NewWorkPlanForm,_NewDepPlan,DictionaryView,_DictionaryQuery,_DictionaryAdd,_DictionaryEdit,_DictionaryDel,Info,NewsView,_NewsQuery,_NewsAdd,_NewsEdit,_NewsDel,NewsCommentView,_NewsCommentQuery,_NewsCommentDel,NewsTypeView,_NewsTypeQuery,_NewsTypeAdd,_NewsTypeEdit,_NewsTypeDel,NoticeView,_NoticeQuery,_NoticeAdd,_NoticeEdit,_NoticeDel,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView,DutyManager,Duty,HolidayRecordView,_HolidayRecordQuery,_HolidayRecordAdd,_HolidayRecordEdit,_HolidayRecordDel,DutySectionView,_DutySectonQuery,_DutySectonAdd,_DutySectonEdit,_DutySectonDel,DutySystemView,_DutySystemQuery,_DutySystemAdd,_DutySystemEdit,_DutySystemDel,DutyView,_DutyQuery,_DutyAdd,_DutyEdit,_DutyDel,DutyRegisterView,_DutyRegisterQuery,_DutyRegisterAdd,_DutyRegisterDel,customer,CustomerView,_CustomerQuery,_CustomerAdd,_CustomerEdit,_CustomerDel,_CustomerSendMail,CusLinkmanView,_CusLinkmanQuery,_CusLinkmanAdd,_CusLinkmanEdit,_CusLinkmanDel,CusConnectionView,_CusConnectionQuery,_CusConnectionAdd,_CusConnectionEdit,_CusConnectionDel,ProjectView,_ProjectQuery,_ProjectAdd,_ProjectEdit,_ProjectDel,ContractView,_ContractQuery,_ContractAdd,_ContractEdit,_ContractDel,ProductView,_ProductQuery,_ProductAdd,_ProductEdit,_ProductDel,ProviderView,_ProviderQuery,_ProviderAdd,_ProviderEdit,_ProviderDel,Administrator,GoodManeger,OfficeGoodsManageView,_OfficeGoodsQuery,_OfficeGoodsTypeManage,_OfficeGoodsAdd,_OfficeGoodsEdit,_OfficeGoodsDel,InStockView,_InStockQuery,_InStockAdd,_InStockEdit,_InStockDel,GoodsApplyView,_GoodsApplyQuery,_GoodsApplyAdd,_GoodsApplyEdit,_GoodsApplyDel,CarManeger,CarView,_CarQuery,_CarAdd,_CarEdit,_CarDel,CartRepairView,_CarRepairQuery,_CarRepairAdd,_CarRepairEdit,_CarRepairDel,CarApplyView,_CarApplyQuery,_CarApplyAdd,_CarApplyEdit,_CarApplyDel,FixedAssetsManage,DepreTypeView,_DepreTypeQuery,_DepreTypeAdd,_DepreTypeEdit,_DepreTypeDel,FixedAssetsManageView,_FixedAssetsQuery,_AssetsTypeManage,_FixedAssetsAdd,_FixedAssetsEdit,_FixedAssetsDel,_Depreciate,DepreRecordView,_DepreRecordQuery,BookManager,BookTypeView,_BookTypeQuery,_BookTypeAdd,_BookTypeEdit,_BookTypeDel,BookManageView,_BookQuery,_BookAdd,_BookEdit,_BookDel,BookBorrowView,_BookBorrowQuery,_BookBorrowAdd,_BookBorrowEdit,_BookReturn,_BookBorrowDel,BookReturnView,_BookReturnQuery,_BookReturnAdd,_BookReturnEdit,_BookReturnDel,Hrm,HrmManage,JobView,_JobQuery,_JobAdd,_JobEdit,_JobDel,_JobRec,EmpProfileForm,_EmpProfileReg,EmpProfileView,_EmpProfileQuery,_EmpProfileAdd,_EmpProfileEdit,_EmpProfileDel,_EmpProfileCheck,_EmpProfileRec,SalaryManage,SalaryItemView,_SalaryItemQuery,_SalaryItemAdd,_SalaryItemEdit,_SalaryItemDel,StandSalaryForm,_StandSalaryReg,StandSalaryView,_StandSalaryQuery,_StandSalaryAdd,_StandSalaryEdit,_StandSalaryDel,_StandSalaryCheck,SalaryPayoffForm,_SalaryPayoffReg,SalaryPayoffView,_SalaryPayoffQuery,_SalaryPayoffAdd,_SalaryPayoffEdit,_SalaryPayoffDel,_SalaryPayoffCheck,JobChange,JobChangeForm,_JobChangeReg,JobChangeView,_JobChangeQuery,_JobChangeAdd,_JobChangeEdit,_JobChangeDel,_JobChangeCheck,HireIssueView,_HireIssueQuery,_HireIssueAdd,_HireIssueEdit,_HireIssueDel,_HireIssueCheck,ResumeView,_ResumeQuery,_ResumeAdd,_ResumeEdit,_ResumeDel', '0'), ('7', '局长', '', '1', 'Archive,ArchiveIssue,ArchivesIssueCharge,_ArchivesIssueChargeQuery,_ArchivesIssueChargeEdit,ArchiveReceive,LeaderReadView,_LeaderReadQuery,ArchivesRecCtrlView,_ArchivesRecCtrlQuery,_ArchivesRecHasten,SystemSetting,FlowManagerView,_FlowCheck,Info,NewsView,_NewsQuery,NewsCommentView,_NewsCommentQuery,NoticeView,_NoticeQuery,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '0'), ('8', '副局长', '', '1', 'Archive,ArchiveIssue,ArchivesIssueLead,_ArchivesIssueLeadQuery,_ArchivesIssueLeadEdit,ArchiveReceive,LeaderReadView,_LeaderReadQuery,SystemSetting,FlowManagerView,_FlowCheck,Personal,PersonalDuty,SignInOffView,ErrandsRegisterView,ErrandsRegisterOutView', '0'), ('9', '党组成员', '', '1', null, '0'), ('10', '纪检组长', '', '1', null, '0'), ('11', '主任', '', '1', null, '0'), ('12', '副主任', '', '1', null, '0'), ('13', '科长', '', '1', null, '0'), ('14', '副科长', '', '1', null, '0'), ('15', '主任科员', '', '1', null, '0'), ('16', '副主任科员', '', '1', null, '0'), ('17', '科员', '', '1', null, '0'), ('18', '秘书长', '', '1', null, '0'), ('19', '副秘书长', '', '1', null, '0');
COMMIT;

-- ----------------------------
-- Table structure for `app_tips`
-- ----------------------------
DROP TABLE IF EXISTS `app_tips`;
CREATE TABLE `app_tips` (
`tipsId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NOT NULL ,
`tipsName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`content`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`disheight`  int(11) NULL DEFAULT NULL ,
`diswidth`  int(11) NULL DEFAULT NULL ,
`disleft`  int(11) NULL DEFAULT NULL ,
`distop`  int(11) NULL DEFAULT NULL ,
`dislevel`  int(11) NULL DEFAULT NULL ,
`createtime`  datetime NOT NULL ,
PRIMARY KEY (`tipsId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='用户便签'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of app_tips
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `app_user`
-- ----------------------------
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
`userId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`username`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`title`  smallint(6) NOT NULL COMMENT '1=先生\r\n            0=女士\r\n            小姐' ,
`password`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码' ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件' ,
`depId`  bigint(20) NULL DEFAULT NULL COMMENT '所属部门' ,
`position`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位' ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话' ,
`mobile`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机' ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真' ,
`address`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址' ,
`zip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编' ,
`photo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '相片' ,
`accessionTime`  datetime NOT NULL COMMENT '入职时间' ,
`status`  smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用\r\n            2=离职\r\n            ' ,
`education`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fullname`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`delFlag`  smallint(6) NOT NULL COMMENT '0=未删除\r\n            1=删除' ,
PRIMARY KEY (`userId`),
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='app_user\r\n用户表'
AUTO_INCREMENT=33

;

-- ----------------------------
-- Records of app_user
-- ----------------------------
BEGIN;
INSERT INTO app_user VALUES ('-1', 'system', '1', '0', '152@163.com', '1', null, null, null, null, null, null, null, '2009-12-18 00:00:00', '0', null, '系统', '1'), ('1', 'admin', '1', 'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=', 'csx@jee-soft.cn', '1', null, null, null, null, null, null, null, '2009-12-18 00:00:00', '1', null, '超级管理员', '0'), ('2', 'csx', '1', '9uCh4qxBlFqap/+KiqoM68EqO8yYGpKa1c+BCgkOEa4=', '111@hotmail.com', '1', '', '', '', '', '', '', '', '2010-05-03 00:00:00', '1', null, 'cwx', '0'), ('3', 'jiaquan', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'jiaquan@office.com', '25', '局长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '贾权', '0'), ('4', 'zhangjishou', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'zhangjishou@office.com', '25', '副局长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '张继寿', '0'), ('5', 'hefusuo', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'hefusuo@office.com', '25', '副局长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '贺福锁', '0'), ('6', 'wangxili', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'wangxili@office.com', '25', '局长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '王希礼', '0'), ('7', 'jingzhonghai', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'jingzhonghai@office.com', '25', '党组成员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '荆忠海', '0'), ('8', 'yuanfengqing', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'yuanfengqing@office.com', '25', '党组成员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '袁丰庆', '0'), ('9', 'liyan', '0', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'liyan@office.com', '25', '纪检组长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李艳', '0'), ('10', 'duanzhanjun', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'duanzhanjun@office.com', '2', '办公室主任', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '段战军', '0'), ('11', 'mayudong', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'mayudong@office.com', '3', '副科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '马玉东', '0'), ('12', 'wangxiuping', '0', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'wangxiuping@office.com', '4', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '王秀萍', '0'), ('13', 'wanglijun', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'wanglijun@office.com', '5', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '王利军', '0'), ('14', 'guofengde', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'guofengde@office.com', '6', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '郭峰德', '0'), ('15', 'qiyongjun', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'qiyongjun@office.com', '7', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '齐拥军', '0'), ('16', 'liyuzhu', '0', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'liyuzhu@office.com', '8', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李玉珠', '0'), ('17', 'dengchunhai', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'dengchunhai@office.com', '9', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '邓春海', '0'), ('18', 'lishulin', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'lishulin@office.com', '10', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李树林', '0'), ('19', 'panyanfang', '0', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'panyanfang@office.com', '11', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '潘彦芳', '0'), ('20', 'zhuyingjie', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'zhuyingjie@office.com', '12', '副科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '朱应杰', '0'), ('21', 'yangxudong', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'yangxudong@office.com', '12', '副科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '杨旭东', '0'), ('22', 'liwei', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'liwei@office.com', '13', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李伟', '0'), ('23', 'yanqingshan', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'yanqingshan@office.com', '14', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '闫青山', '0'), ('24', 'zhangyanping', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'zhangyanping@office.com', '15', '主任', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '张彦萍', '0'), ('25', 'jingzhizhong', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'jingzhizhong@office.com', '16', '主任', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '荆志忠', '0'), ('26', 'zhouzhilan', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'zhouzhilan@office.com', '17', '科长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '周芝兰', '0'), ('27', 'wangfeng', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'wangfeng@office.com', '19', '副主任科员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '王枫', '0'), ('28', 'zhanghui', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'zhanghui@office.com', '20', '副主任科员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '张晖', '0'), ('29', 'jiaruihe', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'jiaruihe@office.com', '21', '副主任科员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '贾瑞河', '0'), ('30', 'lixiaohong', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'lixiaohong@office.com', '22', '主任科员', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李晓红', '0'), ('31', 'hujinyu', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'hujinyu@office.com', '23', '秘书长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '胡金玉', '0'), ('32', 'liwenyu', '1', 'kbTRQoI/fSDF8I32kSLeQ/NfBXqYjZYZ9tMThIXJogM=', 'liwenyu@office.com', '24', '秘书长', '', '', '', '', '', '', '2011-07-15 00:00:00', '1', null, '李文瑜', '0');
COMMIT;

-- ----------------------------
-- Table structure for `appointment`
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment` (
`appointId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`subject`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主题' ,
`startTime`  datetime NOT NULL COMMENT '开始时间' ,
`endTime`  datetime NOT NULL COMMENT '结束时间' ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '约会内容' ,
`notes`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`location`  varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地点' ,
`inviteEmails`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`appointId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='约会管理'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of appointment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `arch_dispatch`
-- ----------------------------
DROP TABLE IF EXISTS `arch_dispatch`;
CREATE TABLE `arch_dispatch` (
`dispatchId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`archivesId`  bigint(20) NULL DEFAULT NULL ,
`dispatchTime`  datetime NOT NULL ,
`userId`  bigint(20) NOT NULL ,
`fullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isRead`  smallint(6) NULL DEFAULT NULL ,
`subject`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`readFeedback`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`archUserType`  smallint(6) NOT NULL DEFAULT 0 COMMENT '0=阅读人员\r\n            1=承办人\r\n            2=分发负责人\r\n            ' ,
`disRoleId`  bigint(20) NULL DEFAULT NULL ,
`disRoleName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`dispatchId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of arch_dispatch
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `arch_flow_conf`
-- ----------------------------
DROP TABLE IF EXISTS `arch_flow_conf`;
CREATE TABLE `arch_flow_conf` (
`configId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`processName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`processDefId`  bigint(20) NOT NULL ,
`archType`  smallint(6) NOT NULL COMMENT '0=发文\r\n            1=收文' ,
PRIMARY KEY (`configId`),
FOREIGN KEY (`processDefId`) REFERENCES `pro_definition` (`defId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公文流程设置'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of arch_flow_conf
-- ----------------------------
BEGIN;
INSERT INTO arch_flow_conf VALUES ('1', '发文流程', '4', '0'), ('2', '收文流程', '5', '1');
COMMIT;

-- ----------------------------
-- Table structure for `arch_hasten`
-- ----------------------------
DROP TABLE IF EXISTS `arch_hasten`;
CREATE TABLE `arch_hasten` (
`recordId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`archivesId`  bigint(20) NULL DEFAULT NULL ,
`content`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '催办内容' ,
`createtime`  datetime NULL DEFAULT NULL COMMENT '催办时间' ,
`hastenFullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '催办人' ,
`handlerFullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '承办人' ,
`handlerUserId`  bigint(20) NULL DEFAULT NULL COMMENT '承办人ID' ,
PRIMARY KEY (`recordId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of arch_hasten
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `arch_rec_type`
-- ----------------------------
DROP TABLE IF EXISTS `arch_rec_type`;
CREATE TABLE `arch_rec_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称' ,
`depId`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`typeId`),
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of arch_rec_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `arch_rec_user`
-- ----------------------------
DROP TABLE IF EXISTS `arch_rec_user`;
CREATE TABLE `arch_rec_user` (
`archRecId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NOT NULL ,
`fullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`depId`  bigint(20) NOT NULL COMMENT '部门ID ' ,
`depName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称' ,
PRIMARY KEY (`archRecId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25

;

-- ----------------------------
-- Records of arch_rec_user
-- ----------------------------
BEGIN;
INSERT INTO arch_rec_user VALUES ('1', '1', '超级管理员', '1', '信息部门'), ('2', '10', '段战军', '2', '办公室'), ('3', '11', '马玉东', '3', '人教科'), ('4', '12', '王秀萍', '4', '财务科'), ('5', '13', '王利军', '5', '法制科'), ('6', '14', '郭峰德', '6', '注册科'), ('7', '15', '齐拥军', '7', '监督科'), ('8', '16', '李玉珠', '8', '经检科'), ('9', '17', '邓春海', '9', '经检支队'), ('10', '18', '李树林', '10', '市场科'), ('11', '19', '潘彦芳', '11', '合同科'), ('12', '20', '朱应杰', '12', '消保科'), ('13', '22', '李伟', '13', '商标科'), ('14', '23', '闫青山', '14', '广告科'), ('15', '24', '张彦萍', '15', '党办'), ('16', '25', '荆志忠', '16', '监察室'), ('17', '26', '周芝兰', '17', '老干部离退科'), ('18', '27', '王枫', '19', '新闻中心'), ('19', '28', '张晖', '20', '信息中心'), ('20', '29', '贾瑞河', '21', '后勤中心'), ('21', '30', '李晓红', '22', '消协'), ('22', '31', '胡金玉', '23', '私协'), ('23', '32', '李文瑜', '24', '个协'), ('24', '3', '贾权', '25', '局领导');
COMMIT;

-- ----------------------------
-- Table structure for `arch_template`
-- ----------------------------
DROP TABLE IF EXISTS `arch_template`;
CREATE TABLE `arch_template` (
`templateId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeId`  bigint(20) NULL DEFAULT NULL COMMENT '所属类型' ,
`tempName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模板名称' ,
`tempPath`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径' ,
`fileId`  bigint(20) NOT NULL COMMENT '文件ID' ,
PRIMARY KEY (`templateId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公文模板'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of arch_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives`
-- ----------------------------
DROP TABLE IF EXISTS `archives`;
CREATE TABLE `archives` (
`archivesId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeId`  bigint(20) NULL DEFAULT NULL COMMENT '公文类型' ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公文类型名称' ,
`archivesNo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发文字号' ,
`issueDep`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发文机关或部门' ,
`depId`  bigint(20) NULL DEFAULT NULL COMMENT '发文部门ID' ,
`arc_typeId`  bigint(20) NULL DEFAULT NULL ,
`subject`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件标题' ,
`createtime`  datetime NOT NULL ,
`issueDate`  datetime NOT NULL COMMENT '发布日期' ,
`status`  smallint(6) NOT NULL COMMENT '公文状态\r\n            0=拟稿、修改状态\r\n            1=发文状态\r\n            2=归档状态' ,
`shortContent`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容简介' ,
`fileCounts`  int(11) NOT NULL DEFAULT 0 COMMENT '文件数' ,
`privacyLevel`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '普通' COMMENT '秘密等级\r\n            普通\r\n            秘密\r\n            机密\r\n            绝密' ,
`urgentLevel`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '普通' COMMENT '紧急程度\r\n            普通\r\n            紧急\r\n            特急\r\n            特提' ,
`issuer`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发文人' ,
`issuerId`  bigint(20) NULL DEFAULT NULL COMMENT '发文人ID' ,
`keywords`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主题词' ,
`sources`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公文来源\r\n            仅在收文中指定，发公文不需要指定\r\n            上级公文\r\n            下级公文' ,
`archType`  smallint(6) NOT NULL DEFAULT 0 COMMENT '0=发文\r\n            1=收文' ,
`recDepIds`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于存储接收公文的部门ID,使用,进行分开' ,
`recDepNames`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于存储接收公文的部门的名称，使用,进行分开' ,
`handlerUids`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在收文中使用，多个用户ID用\',\'分割' ,
`handlerUnames`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于收文，存储多个拟办用户名，用‘，’分割' ,
`orgArchivesId`  bigint(20) NULL DEFAULT NULL COMMENT '用于收文时使用，指向原公文ID' ,
`depSignNo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用于收文时，部门对自身的公文自编号' ,
PRIMARY KEY (`archivesId`),
FOREIGN KEY (`arc_typeId`) REFERENCES `arch_rec_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`typeId`) REFERENCES `archives_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='收发公文'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of archives
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives_attend`
-- ----------------------------
DROP TABLE IF EXISTS `archives_attend`;
CREATE TABLE `archives_attend` (
`attendId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`archivesId`  bigint(20) NOT NULL ,
`userID`  bigint(20) NOT NULL COMMENT '用户ID' ,
`fullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名' ,
`attendType`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参与类型\r\n            1=校对人\r\n            2=审核人\r\n            3=缮印人\r\n            4=用印人' ,
`executeTime`  datetime NULL DEFAULT NULL COMMENT '执行时间' ,
`memo`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
PRIMARY KEY (`attendId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='发文拟稿参与人'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of archives_attend
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives_dep`
-- ----------------------------
DROP TABLE IF EXISTS `archives_dep`;
CREATE TABLE `archives_dep` (
`archDepId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`signNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '自编号' ,
`depId`  bigint(20) NOT NULL COMMENT '收文部门' ,
`archivesId`  bigint(20) NOT NULL COMMENT '所属公文' ,
`subject`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '公文标题' ,
`status`  smallint(6) NOT NULL COMMENT '签收状态\r\n            0=未签收\r\n            1=已签收' ,
`signTime`  datetime NULL DEFAULT NULL COMMENT '签收日期' ,
`signFullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收人' ,
`signUserID`  bigint(20) NULL DEFAULT NULL ,
`handleFeedback`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '办理结果反馈' ,
`isMain`  smallint(6) NOT NULL DEFAULT 1 COMMENT '主送、抄送\r\n            1=主送\r\n            0=抄送' ,
PRIMARY KEY (`archDepId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of archives_dep
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives_doc`
-- ----------------------------
DROP TABLE IF EXISTS `archives_doc`;
CREATE TABLE `archives_doc` (
`docId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`fileId`  bigint(20) NULL DEFAULT NULL ,
`archivesId`  bigint(20) NULL DEFAULT NULL ,
`creator`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拟稿人' ,
`creatorId`  bigint(20) NULL DEFAULT NULL COMMENT '拟稿人ID' ,
`menderId`  bigint(20) NULL DEFAULT NULL ,
`mender`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '修改人' ,
`docName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档名称' ,
`docStatus`  smallint(6) NOT NULL COMMENT '文档状态\r\n            0=修改中\r\n            1=修改完成' ,
`curVersion`  int(11) NOT NULL COMMENT '当前版本\r\n            取当前最新的版本' ,
`docPath`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档路径' ,
`updatetime`  datetime NOT NULL COMMENT '更新时间' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
PRIMARY KEY (`docId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of archives_doc
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives_handle`
-- ----------------------------
DROP TABLE IF EXISTS `archives_handle`;
CREATE TABLE `archives_handle` (
`handleId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`archivesId`  bigint(20) NOT NULL ,
`handleOpinion`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userId`  bigint(20) NOT NULL ,
`userFullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`createtime`  datetime NOT NULL ,
`fillTime`  datetime NULL DEFAULT NULL ,
`isPass`  smallint(6) NOT NULL DEFAULT 1 COMMENT '0=尚未审批\r\n            1=通过审批\r\n            ２=未通过审批' ,
PRIMARY KEY (`handleId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公文拟办人'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of archives_handle
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `archives_type`
-- ----------------------------
DROP TABLE IF EXISTS `archives_type`;
CREATE TABLE `archives_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型名称' ,
`typeDesc`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型描述' ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公文类型'
AUTO_INCREMENT=3

;

-- ----------------------------
-- Records of archives_type
-- ----------------------------
BEGIN;
INSERT INTO archives_type VALUES ('1', '文件内容', ''), ('2', '三星', '三源');
COMMIT;

-- ----------------------------
-- Table structure for `assets_type`
-- ----------------------------
DROP TABLE IF EXISTS `assets_type`;
CREATE TABLE `assets_type` (
`assetsTypeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称' ,
PRIMARY KEY (`assetsTypeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of assets_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
`bookId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeId`  bigint(20) NULL DEFAULT NULL ,
`bookName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`author`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`isbn`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`publisher`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`price`  decimal(10,0) NOT NULL ,
`location`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`department`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`amount`  int(11) NOT NULL ,
`leftAmount`  int(11) NOT NULL ,
PRIMARY KEY (`bookId`),
FOREIGN KEY (`typeId`) REFERENCES `book_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='图书'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of book
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `book_bor_ret`
-- ----------------------------
DROP TABLE IF EXISTS `book_bor_ret`;
CREATE TABLE `book_bor_ret` (
`recordId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`bookSnId`  bigint(20) NULL DEFAULT NULL ,
`borrowTime`  datetime NOT NULL ,
`returnTime`  datetime NOT NULL ,
`lastReturnTime`  datetime NULL DEFAULT NULL ,
`borrowIsbn`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`bookName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`registerName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`recordId`),
FOREIGN KEY (`bookSnId`) REFERENCES `book_sn` (`bookSnId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='图书借还表'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of book_bor_ret
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `book_sn`
-- ----------------------------
DROP TABLE IF EXISTS `book_sn`;
CREATE TABLE `book_sn` (
`bookSnId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`bookId`  bigint(20) NOT NULL ,
`bookSN`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`status`  smallint(6) NOT NULL COMMENT '借阅状态\r\n            0=未借出\r\n            1=借出\r\n            2=预订\r\n            3=注销' ,
PRIMARY KEY (`bookSnId`),
FOREIGN KEY (`bookId`) REFERENCES `book` (`bookId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of book_sn
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `book_type`
-- ----------------------------
DROP TABLE IF EXISTS `book_type`;
CREATE TABLE `book_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='图书类别'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of book_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cal_file`
-- ----------------------------
DROP TABLE IF EXISTS `cal_file`;
CREATE TABLE `cal_file` (
`fileId`  bigint(20) NOT NULL ,
`planId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `planId`),
FOREIGN KEY (`planId`) REFERENCES `calendar_plan` (`planId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of cal_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `calendar_plan`
-- ----------------------------
DROP TABLE IF EXISTS `calendar_plan`;
CREATE TABLE `calendar_plan` (
`planId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`startTime`  datetime NULL DEFAULT NULL COMMENT '开始时间' ,
`endTime`  datetime NULL DEFAULT NULL COMMENT '结束时间' ,
`urgent`  smallint(6) NOT NULL COMMENT '紧急程度\r\n            0=一般\r\n            1=重要\r\n            2=紧急' ,
`content`  varchar(1200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' ,
`status`  smallint(6) NOT NULL COMMENT '状态\r\n            0=未完成\r\n            1=完成' ,
`userId`  bigint(20) NOT NULL COMMENT '员工ID' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '员工名' ,
`assignerId`  bigint(20) NOT NULL COMMENT '分配人' ,
`assignerName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分配人名' ,
`feedback`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '反馈意见' ,
`showStyle`  smallint(6) NOT NULL COMMENT '显示方式\r\n            1=仅在任务中显示\r\n            2=在日程与任务中显示' ,
`taskType`  smallint(6) NOT NULL COMMENT '任务类型\r\n            1=限期任务\r\n            2=非限期任务' ,
PRIMARY KEY (`planId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`assignerId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='日程安排'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of calendar_plan
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `car`
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
`carId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`carNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`carType`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '轿车\r\n            货车\r\n            商务车\r\n            ' ,
`engineNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`buyInsureTime`  datetime NULL DEFAULT NULL COMMENT '购买保险时间' ,
`auditTime`  datetime NULL DEFAULT NULL COMMENT '年审时间' ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`factoryModel`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`driver`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`buyDate`  date NOT NULL COMMENT '购置日期' ,
`status`  smallint(6) NOT NULL COMMENT '当前状态\r\n            1=可用\r\n            2=维修中\r\n            0=报废' ,
`cartImage`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`carId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='车辆信息'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of car
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `car_apply`
-- ----------------------------
DROP TABLE IF EXISTS `car_apply`;
CREATE TABLE `car_apply` (
`applyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`carId`  bigint(20) NOT NULL ,
`department`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`userFullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`applyDate`  date NOT NULL ,
`reason`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`startTime`  datetime NOT NULL ,
`endTime`  datetime NULL DEFAULT NULL ,
`userId`  bigint(20) NOT NULL ,
`proposer`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`mileage`  decimal(18,2) NULL DEFAULT NULL ,
`oilUse`  decimal(18,2) NULL DEFAULT NULL ,
`notes`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`approvalStatus`  smallint(6) NOT NULL ,
PRIMARY KEY (`applyId`),
FOREIGN KEY (`carId`) REFERENCES `car` (`carId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='车辆申请'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of car_apply
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cart_repair`
-- ----------------------------
DROP TABLE IF EXISTS `cart_repair`;
CREATE TABLE `cart_repair` (
`repairId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`carId`  bigint(20) NULL DEFAULT NULL ,
`repairDate`  datetime NOT NULL COMMENT '维护日期' ,
`reason`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '维护原因' ,
`executant`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '经办人' ,
`notes`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`repairType`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '维修类型\r\n            保养\r\n            维修' ,
`fee`  decimal(18,2) NULL DEFAULT NULL COMMENT '费用' ,
PRIMARY KEY (`repairId`),
FOREIGN KEY (`carId`) REFERENCES `car` (`carId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of cart_repair
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `company`
-- ----------------------------
DROP TABLE IF EXISTS `company`;
CREATE TABLE `company` (
`companyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`companyNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`companyName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`companyDesc`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`legalPerson`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`setup`  datetime NULL DEFAULT NULL ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`site`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`logo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`companyId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公司信息'
AUTO_INCREMENT=2

;

-- ----------------------------
-- Records of company
-- ----------------------------
BEGIN;
INSERT INTO company VALUES ('1', '', '阳泉市工商行政管理局', '<BR>​', '', null, '', '', '', '');
COMMIT;

-- ----------------------------
-- Table structure for `contract`
-- ----------------------------
DROP TABLE IF EXISTS `contract`;
CREATE TABLE `contract` (
`contractId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`contractNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合同编号' ,
`subject`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '合同标题' ,
`contractAmount`  decimal(10,0) NOT NULL COMMENT '合同金额' ,
`mainItem`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主要条款' ,
`salesAfterItem`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '售后条款' ,
`validDate`  datetime NOT NULL COMMENT '生效日期' ,
`expireDate`  datetime NOT NULL COMMENT '有效期' ,
`serviceDep`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修部门' ,
`serviceMan`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '维修负责人' ,
`signupUser`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '签约人' ,
`signupTime`  datetime NOT NULL COMMENT '签约时间' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '录入人' ,
`createtime`  datetime NOT NULL COMMENT '录入时间' ,
`projectId`  bigint(20) NULL DEFAULT NULL COMMENT '所属项目' ,
`consignAddress`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址' ,
`consignee`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人' ,
PRIMARY KEY (`contractId`),
FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of contract
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `contract_config`
-- ----------------------------
DROP TABLE IF EXISTS `contract_config`;
CREATE TABLE `contract_config` (
`configId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`itemName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设备名称' ,
`contractId`  bigint(20) NULL DEFAULT NULL ,
`itemSpec`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '设置规格' ,
`amount`  decimal(18,2) NOT NULL COMMENT '数量' ,
`notes`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
PRIMARY KEY (`configId`),
FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='合同配置单'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of contract_config
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `contract_file`
-- ----------------------------
DROP TABLE IF EXISTS `contract_file`;
CREATE TABLE `contract_file` (
`fileId`  bigint(20) NOT NULL ,
`contractId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `contractId`),
FOREIGN KEY (`contractId`) REFERENCES `contract` (`contractId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='合同附件'

;

-- ----------------------------
-- Records of contract_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cus_connection`
-- ----------------------------
DROP TABLE IF EXISTS `cus_connection`;
CREATE TABLE `cus_connection` (
`connId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`customerId`  bigint(20) NOT NULL ,
`contactor`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`startDate`  datetime NOT NULL ,
`endDate`  datetime NOT NULL ,
`content`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`notes`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`connId`),
FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='business connection '
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of cus_connection
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `cus_linkman`
-- ----------------------------
DROP TABLE IF EXISTS `cus_linkman`;
CREATE TABLE `cus_linkman` (
`linkmanId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`customerId`  bigint(20) NOT NULL COMMENT '所属客户' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名' ,
`sex`  smallint(6) NOT NULL COMMENT '性别' ,
`position`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位' ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话' ,
`mobile`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机' ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'Email' ,
`msn`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'MSN' ,
`qq`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ' ,
`birthday`  date NULL DEFAULT NULL COMMENT '生日' ,
`homeAddress`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭住址' ,
`homeZip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编' ,
`homePhone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭电话' ,
`hobby`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '爱好' ,
`isPrimary`  smallint(6) NOT NULL COMMENT '是否为主要联系人' ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
PRIMARY KEY (`linkmanId`),
FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='客户联系人'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of cus_linkman
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `customer`
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
`customerId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`customerNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户号\r\n            自动生成' ,
`industryType`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属行业\r\n            有缺省的选择，也可以输入' ,
`customerSource`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户来源\r\n            可编辑，可添加\r\n            \r\n            电话访问\r\n            网络\r\n            客户或朋友介绍' ,
`customerType`  smallint(6) NOT NULL COMMENT '1=正式客户\r\n            2=重要客户\r\n            3＝潜在客户\r\n            4＝无效客户' ,
`companyScale`  int(11) NULL DEFAULT NULL COMMENT '1=1-20人\r\n            2=20-50人\r\n            3=50-100人\r\n            4=100-200人\r\n            5=200-500人\r\n            6=500-1000 人\r\n            7=1000人以上\r\n            ' ,
`customerName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户名称\r\n            一般为公司名称' ,
`customerManager`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '负责该客户的经理' ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话' ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`site`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`city`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`address`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`registerFun`  decimal(10,0) NULL DEFAULT NULL ,
`turnOver`  decimal(10,0) NULL DEFAULT NULL ,
`currencyUnit`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册资金及年营业额的货币单位\r\n            可选可编辑\r\n            人民币（默认）\r\n            美元\r\n            ' ,
`otherDesc`  varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`principal`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`openBank`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`accountsNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`taxNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`rights`  smallint(6) NOT NULL COMMENT '1=客户经理及上级经理有权查看\r\n            2=公开\r\n            3=共享人员有权查看' ,
PRIMARY KEY (`customerId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='客户信息'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `department`
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
`depId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`depName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称' ,
`depDesc`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门描述' ,
`depLevel`  int(11) NOT NULL COMMENT '层次' ,
`parentId`  bigint(20) NULL DEFAULT NULL ,
`path`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径' ,
`phone`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fax`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`depId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=26

;

-- ----------------------------
-- Records of department
-- ----------------------------
BEGIN;
INSERT INTO department VALUES ('1', '信息部门', '维护系统', '2', '0', '0.1.', null, null), ('2', '办公室', '办公室', '2', '0', '0.2.', null, null), ('3', '人教科', '人教科', '2', '0', '0.3.', null, null), ('4', '财务科', '财务科', '2', '0', '0.4.', null, null), ('5', '法制科', '法制科', '2', '0', '0.5.', null, null), ('6', '注册科', '注册科', '2', '0', '0.6.', null, null), ('7', '监督科', '监督科', '2', '0', '0.7.', null, null), ('8', '经检科', '经检科', '2', '0', '0.8.', null, null), ('9', '经检支队', '经检支队', '2', '0', '0.9.', null, null), ('10', '市场科', '市场科', '2', '0', '0.10.', null, null), ('11', '合同科', '合同科', '2', '0', '0.11.', null, null), ('12', '消保科', '消保科', '2', '0', '0.12.', null, null), ('13', '商标科', '商标科', '2', '0', '0.13.', null, null), ('14', '广告科', '广告科', '2', '0', '0.14.', null, null), ('15', '党办', '党办', '2', '0', '0.15.', null, null), ('16', '监察室', '监察室', '2', '0', '0.16.', null, null), ('17', '老干部离退科', '老干部离退科', '2', '0', '0.17.', null, null), ('18', '12315指挥中心', '12315指挥中心', '2', '0', '0.18.', null, null), ('19', '新闻中心', '新闻中心', '2', '0', '0.19.', null, null), ('20', '信息中心', '信息中心', '2', '0', '0.20.', null, null), ('21', '后勤中心', '后勤中心', '2', '0', '0.21.', null, null), ('22', '消协', '消协', '2', '0', '0.22.', null, null), ('23', '私协', '私协', '2', '0', '0.23.', null, null), ('24', '个协', '个协', '2', '0', '0.24.', null, null), ('25', '局领导', '局领导', '2', '0', '0.25.', null, null);
COMMIT;

-- ----------------------------
-- Table structure for `depre_record`
-- ----------------------------
DROP TABLE IF EXISTS `depre_record`;
CREATE TABLE `depre_record` (
`recordId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`assetsId`  bigint(20) NOT NULL ,
`workCapacity`  decimal(18,2) NULL DEFAULT NULL ,
`workGrossUnit`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depreAmount`  decimal(18,4) NOT NULL ,
`calTime`  datetime NOT NULL ,
PRIMARY KEY (`recordId`),
FOREIGN KEY (`assetsId`) REFERENCES `fixed_assets` (`assetsId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of depre_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `depre_type`
-- ----------------------------
DROP TABLE IF EXISTS `depre_type`;
CREATE TABLE `depre_type` (
`depreTypeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`deprePeriod`  int(11) NOT NULL COMMENT '单位为月' ,
`typeDesc`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`calMethod`  smallint(6) NOT NULL COMMENT '1=平均年限法\r\n            2=工作量法\r\n            加速折旧法\r\n            3=双倍余额递减法\r\n            4=年数总和法' ,
PRIMARY KEY (`depreTypeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='depreciation type'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of depre_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `diary`
-- ----------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary` (
`diaryId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`dayTime`  date NOT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`diaryType`  smallint(6) NOT NULL COMMENT '1=工作日志\r\n            0=个人日志' ,
PRIMARY KEY (`diaryId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of diary
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
`dicId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`itemName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`itemValue`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`descp`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`dicId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='数据字典'
AUTO_INCREMENT=300

;

-- ----------------------------
-- Records of dictionary
-- ----------------------------
BEGIN;
INSERT INTO dictionary VALUES ('1', '宗教信仰', '佛教', null), ('2', '宗教信仰', '道教', null), ('3', '宗教信仰', '基督宗教', null), ('4', '宗教信仰', '天主教', null), ('5', '宗教信仰', '伊斯兰教', null), ('6', '宗教信仰', '犹太教', null), ('7', '宗教信仰', '孔教', null), ('8', '宗教信仰', '神道教', null), ('9', '宗教信仰', '耆那教', null), ('10', '宗教信仰', '印度教', null), ('11', '宗教信仰', '东正教', null), ('12', '宗教信仰', '新教', null), ('13', '宗教信仰', '锡克教', null), ('14', '宗教信仰', '琐罗亚斯德教', null), ('15', '宗教信仰', '巴哈伊教', null), ('16', '宗教信仰', '其它', null), ('17', '民族', '汉族', null), ('18', '民族', '阿昌族', null), ('19', '民族', '白族', null), ('20', '民族', '保安族', null), ('21', '民族', '布朗族', null), ('22', '民族', '布依族', null), ('23', '民族', '朝鲜族', null), ('24', '民族', '达斡族', null), ('25', '民族', '傣族', null), ('26', '民族', '德昂族', null), ('27', '民族', '侗族', null), ('28', '民族', '东乡族', null), ('29', '民族', '独龙族', null), ('30', '民族', '鄂伦春族', null), ('31', '民族', '俄罗斯族', null), ('32', '民族', '鄂温克族', null), ('33', '民族', '高山族', null), ('34', '民族', '仡佬族', null), ('35', '民族', '哈尼族', null), ('36', '民族', '啥萨克族', null), ('37', '民族', '赫哲族', null), ('38', '民族', '回族', null), ('39', '民族', '基诺族', null), ('40', '民族', '京族', null), ('41', '民族', '景颇族', null), ('42', '民族', '柯尔克孜族', null), ('43', '民族', '拉祜族', null), ('44', '民族', '黎族', null), ('45', '民族', '僳僳族', null), ('46', '民族', '珞巴族', null), ('47', '民族', '满族', null), ('48', '民族', '毛南族', null), ('49', '民族', '门巴族', null), ('50', '民族', '蒙古族', null), ('51', '民族', '苗族', null), ('52', '民族', '仫佬族', null), ('53', '民族', '纳西族', null), ('54', '民族', '怒族', null), ('55', '民族', '普米族', null), ('56', '民族', '羌族', null), ('57', '民族', '撒拉族', null), ('58', '民族', '畲族', null), ('59', '民族', '水族', null), ('60', '民族', '塔吉克族', null), ('61', '民族', '塔塔尔族', null), ('62', '民族', '土族', null), ('63', '民族', '土家族', null), ('64', '民族', '佤族', null), ('65', '民族', '维吾尔族', null), ('66', '民族', '乌孜别克族', null), ('67', '民族', '锡伯族', null), ('68', '民族', '瑶族', null), ('69', '民族', '彝族', null), ('70', '民族', '藏族', null), ('71', '民族', '壮族', null), ('72', '学历', '博士', null), ('73', '学历', '研究生', null), ('74', '学历', '本科', null), ('75', '学历', '大专', null), ('76', '学历', '中专', null), ('77', '学历', '初中', null), ('78', '学历', '小学', null), ('79', '学历', '其它', null), ('80', '政治面貌', '群众', null), ('81', '政治面貌', '共青团员', null), ('82', '政治面貌', '中共党员', null), ('83', '国籍', '中华人民共和国', null), ('84', '国籍', '美国', null), ('85', '国籍', '俄罗斯', null), ('86', '国籍', '日本', null), ('87', '国籍', '韩国', null), ('88', '国籍', '新加波', null), ('89', '国籍', '马来西亚', null), ('90', '国籍', '英国', null), ('91', '国籍', '德国', null), ('92', '国籍', '意大利', null), ('93', '国籍', '澳大利亚', null), ('94', '国籍', '巴西', null), ('95', '专业', '管理科学', null), ('96', '专业', '信息管理和信息系统', null), ('97', '专业', '工业工程', null), ('98', '专业', '工程管理', null), ('99', '专业', '农业经理管理', null), ('100', '专业', '工商管理', null), ('101', '专业', '市场营销', null);
INSERT INTO dictionary VALUES ('102', '专业', '会计学', null), ('103', '专业', '涉外会计', null), ('104', '专业', '会计电算化', null), ('105', '专业', '财政金融', null), ('106', '专业', '财务管理', null), ('107', '专业', '技术经济', null), ('108', '专业', '文秘', null), ('109', '专业', '国际商务', null), ('110', '专业', '物流管理', null), ('111', '专业', '行政管理', null), ('112', '专业', '公共事业管理', null), ('113', '专业', '旅游管理', null), ('114', '专业', '宾馆/酒店管理', null), ('115', '专业', '人力资源管理', null), ('116', '专业', '公共关系学', null), ('117', '专业', '物业管理', null), ('118', '专业', '房地产经营管理', null), ('119', '专业', '劳动与社会保障', null), ('120', '专业', '土地资源管理', null), ('121', '专业', '图书档案学', null), ('122', '专业', '计算机科学与技术', null), ('123', '专业', '计算机应用', null), ('124', '专业', '计算机信息管理', null), ('125', '专业', '计算机网络', null), ('126', '专业', '电子商务', null), ('127', '专业', '通信工程', null), ('128', '专业', '电气工程及其自动化', null), ('129', '专业', '软件工程', null), ('130', '专业', '自动化', null), ('131', '专业', '电子信息工程', null), ('132', '专业', '电子科学与技术', null), ('133', '专业', '电子信息科学与技术', null), ('134', '专业', '微电子学', null), ('135', '专业', '光信息科学与技术', null), ('136', '专业', '机械设计制造及其自动化', null), ('137', '专业', '材料成型及控制工程', null), ('138', '专业', '工业设计', null), ('139', '专业', '过程装备与控制工程', null), ('140', '专业', '机械电子工程/机电一体化', null), ('141', '专业', '模具设计与制造', null), ('142', '专业', '机械制造工艺与设备', null), ('143', '专业', '测控技术与仪器', null), ('144', '专业', '热能与动力工程', null), ('145', '专业', '核工程与核技术', null), ('146', '专业', '电力系统及自动化', null), ('147', '专业', '制冷与低温技术', null), ('148', '专业', '冶金工程', null), ('149', '专业', '金属材料工程', null), ('150', '专业', '无机非金属料工程', null), ('151', '专业', '高分子材料与工程', null), ('152', '专业', '材料物理', null), ('153', '专业', '材料化学', null), ('154', '专业', '材料科学与工程', null), ('155', '专业', '食品科学与工程', null), ('156', '专业', '轻化工程', null), ('157', '专业', '包装工程', null), ('158', '专业', '印刷工程', null), ('159', '专业', '纺织工程', null), ('160', '专业', '服装设计与工程', null), ('161', '专业', '建筑学', null), ('162', '专业', '城市规划', null), ('163', '专业', '园林规划与设计', null), ('164', '专业', '土木工程', null), ('165', '专业', '道路与桥梁', null), ('166', '专业', '建设环境与设备工程', null), ('167', '专业', '给水排水工程', null), ('168', '专业', '供热通风与空调工程', null), ('169', '专业', '工业与民用建筑', null), ('170', '专业', '室内装潢设计', null), ('171', '专业', '建筑工程', null), ('172', '专业', '工程造价管理', null), ('173', '专业', '力学', null), ('174', '专业', '应用力学', null), ('175', '专业', '环境科学', null), ('176', '专业', '生态学', null), ('177', '专业', '环境工程', null), ('178', '专业', '安全工程', null), ('179', '专业', '制药工程', null), ('180', '专业', '交通运输', null), ('181', '专业', '交通工程', null), ('182', '专业', '油气储运工程', null), ('183', '专业', '飞行技术', null), ('184', '专业', '航海技术', null), ('185', '专业', '轮机工程', null), ('186', '专业', '汽车工程', null), ('187', '专业', '飞行器设计与工程', null), ('188', '专业', '飞行器动力工程', null), ('189', '专业', '飞行器制造工程', null), ('190', '专业', '飞行器环境与生命保障工程', null), ('191', '专业', '船舶与海洋工程', null), ('192', '专业', '水利水电工程', null), ('193', '专业', '水文与水资源工程', null), ('194', '专业', '港口航道与海岸工程', null), ('195', '专业', '测绘工程', null), ('196', '专业', '公安技术', null), ('197', '专业', '武器系统与发射工程', null), ('198', '专业', '探测制导与控制技术', null), ('199', '专业', '弹药工程与爆炸技术', null), ('200', '专业', '数学与应用数学', null), ('201', '专业', '信息与计算科学', null), ('202', '专业', '物理学', null);
INSERT INTO dictionary VALUES ('203', '专业', '应用物理学', null), ('204', '专业', '化学', null), ('205', '专业', '应用化学', null), ('206', '专业', '化学工程与工艺', null), ('207', '专业', '精细化工', null), ('208', '专业', '化工设备与机械', null), ('209', '专业', '生物工程', null), ('210', '专业', '生物医学工程', null), ('211', '专业', '生物科学,技术', null), ('212', '专业', '天文学', null), ('213', '专业', '地质学', null), ('214', '专业', '宝石鉴定与加工', null), ('215', '专业', '地理科学', null), ('216', '专业', '地球物理学', null), ('217', '专业', '大气科学', null), ('218', '专业', '海洋科学', null), ('219', '专业', '地矿', null), ('220', '专业', '石油工程', null), ('221', '专业', '经济学', null), ('222', '专业', '国际经济与贸易', null), ('223', '专业', '财政学', null), ('224', '专业', '金融学', null), ('225', '专业', '经济管理', null), ('226', '专业', '经济信息管理', null), ('227', '专业', '工业外贸', null), ('228', '专业', '国际金融', null), ('229', '专业', '投资经济管理', null), ('230', '专业', '统计学', null), ('231', '专业', '审计学', null), ('232', '专业', '中国语言文学', null), ('233', '专业', '英语', null), ('234', '专业', '俄语', null), ('235', '专业', '德语', null), ('236', '专业', '法语', null), ('237', '专业', '日语', null), ('238', '专业', '西班牙语', null), ('239', '专业', '阿拉伯语', null), ('240', '专业', '朝鲜语', null), ('241', '专业', '其它外语', null), ('242', '专业', '新闻学', null), ('243', '专业', '广播电视新闻', null), ('244', '专业', '广告学', null), ('245', '专业', '编辑出版学', null), ('246', '专业', '外贸英语', null), ('247', '专业', '商务英语', null), ('248', '专业', '音乐,舞蹈,作曲', null), ('249', '专业', '绘画,艺术设计', null), ('250', '专业', '戏剧,表演', null), ('251', '专业', '导演,广播电视编导', null), ('252', '专业', '戏剧影视文学', null), ('253', '专业', '戏剧影视美术设计', null), ('254', '专业', '摄影,动画', null), ('255', '专业', '播音,主持,录音', null), ('256', '专业', '服装设计', null), ('257', '专业', '法学', null), ('258', '专业', '马克思主义理论', null), ('259', '专业', '社会学', null), ('260', '专业', '政治学与行政学', null), ('261', '专业', '国际政治', null), ('262', '专业', '外交学', null), ('263', '专业', '思想政治教育', null), ('264', '专业', '公安学', null), ('265', '专业', '经济法', null), ('266', '专业', '国际经济法', null), ('267', '专业', '哲学', null), ('268', '专业', '逻辑学', null), ('269', '专业', '宗教学', null), ('270', '专业', '教育学', null), ('271', '专业', '学前教育', null), ('272', '专业', '体育学', null), ('273', '专业', '基础医学', null), ('274', '专业', '预防医学', null), ('275', '专业', '临床医学与医学技术', null), ('276', '专业', '口腔医学', null), ('277', '专业', '中医学', null), ('278', '专业', '法医学', null), ('279', '专业', '护理学', null), ('280', '专业', '药学', null), ('281', '专业', '心理学', null), ('282', '专业', '医学检验', null), ('283', '专业', '植物生产', null), ('284', '专业', '农学', null), ('285', '专业', '园艺', null), ('286', '专业', '植物保护学', null), ('287', '专业', '茶学', null), ('288', '专业', '草业科学', null), ('289', '专业', '森林资源', null), ('290', '专业', '环境生态', null), ('291', '专业', '园林', null), ('292', '专业', '动物生产', null), ('293', '专业', '动物医学', null), ('294', '专业', '水产类', null), ('295', '专业', '农业工程', null), ('296', '专业', '林业工程', null), ('297', '专业', '历史学', null), ('298', '专业', '考古学', null), ('299', '专业', '博物馆学', null);
COMMIT;

-- ----------------------------
-- Table structure for `doc_file`
-- ----------------------------
DROP TABLE IF EXISTS `doc_file`;
CREATE TABLE `doc_file` (
`fileId`  bigint(20) NOT NULL ,
`docId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `docId`),
FOREIGN KEY (`docId`) REFERENCES `document` (`docId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of doc_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `doc_folder`
-- ----------------------------
DROP TABLE IF EXISTS `doc_folder`;
CREATE TABLE `doc_folder` (
`folderId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`folderName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '目录名称' ,
`parentId`  bigint(20) NULL DEFAULT NULL COMMENT '父目录' ,
`path`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路径\r\n            为当前路径的＋上级路径\r\n            如当前ID为3，上级目录的路径为1.2，\r\n            则当前的路径为1.2.3.' ,
`isShared`  smallint(6) NOT NULL ,
PRIMARY KEY (`folderId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of doc_folder
-- ----------------------------
BEGIN;
INSERT INTO doc_folder VALUES ('1', '1', '用户操作管理', '0', '1.', '0'), ('2', '1', '管理及限制', '0', '2.', '0'), ('3', '2', '成文档', '0', '3.', '0'), ('4', '2', '强类型', '0', '4.', '0');
COMMIT;

-- ----------------------------
-- Table structure for `doc_history`
-- ----------------------------
DROP TABLE IF EXISTS `doc_history`;
CREATE TABLE `doc_history` (
`historyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`docId`  bigint(20) NOT NULL ,
`fileId`  bigint(20) NOT NULL COMMENT '附件ID' ,
`docName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文档名称' ,
`path`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '路径' ,
`version`  int(11) NOT NULL COMMENT '版本' ,
`updatetime`  datetime NOT NULL COMMENT '更新时间' ,
`mender`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '修改人' ,
PRIMARY KEY (`historyId`),
FOREIGN KEY (`docId`) REFERENCES `archives_doc` (`docId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of doc_history
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `doc_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `doc_privilege`;
CREATE TABLE `doc_privilege` (
`privilegeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`folderId`  bigint(20) NULL DEFAULT NULL ,
`docId`  bigint(20) NULL DEFAULT NULL ,
`rights`  int(11) NOT NULL COMMENT '权限\r\n            文档或目录的读写修改权限\r\n            1=读\r\n            2=修改\r\n            4=删除\r\n            \r\n            权限值可以为上面的值之和\r\n            如：3则代表进行读，修改的操作\r\n            \r\n            \r\n            ' ,
`udrId`  int(11) NULL DEFAULT NULL ,
`udrName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`flag`  smallint(6) NOT NULL COMMENT '1=user\r\n            2=deparment\r\n            3=role' ,
`fdFlag`  smallint(6) NOT NULL COMMENT '缺省为文件夹权限\r\n            1=文档权限\r\n            0=文件夹权限' ,
PRIMARY KEY (`privilegeId`),
FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`docId`) REFERENCES `document` (`docId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='文档或目录的权限，只要是针对公共目录下的'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of doc_privilege
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `document`
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
`docId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`docName`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '内容' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
`updatetime`  datetime NULL DEFAULT NULL ,
`folderId`  bigint(20) NULL DEFAULT NULL ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`haveAttach`  smallint(6) NULL DEFAULT NULL ,
`sharedUserIds`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '共享员工ID' ,
`sharedUserNames`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sharedDepIds`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '共享部门ID' ,
`sharedDepNames`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sharedRoleIds`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '共享角色ID' ,
`sharedRoleNames`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isShared`  smallint(6) NOT NULL COMMENT '是否共享' ,
PRIMARY KEY (`docId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`folderId`) REFERENCES `doc_folder` (`folderId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='文档'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of document
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `duty`
-- ----------------------------
DROP TABLE IF EXISTS `duty`;
CREATE TABLE `duty` (
`dutyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NOT NULL COMMENT '员工ID' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工姓名' ,
`systemId`  bigint(20) NOT NULL COMMENT '班制ID' ,
`startTime`  datetime NOT NULL COMMENT '开始时间' ,
`endTime`  datetime NULL DEFAULT NULL COMMENT '结束时间' ,
PRIMARY KEY (`dutyId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`systemId`) REFERENCES `duty_system` (`systemId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='排班'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of duty
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `duty_register`
-- ----------------------------
DROP TABLE IF EXISTS `duty_register`;
CREATE TABLE `duty_register` (
`registerId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`registerDate`  datetime NOT NULL COMMENT '登记时间' ,
`userId`  bigint(20) NOT NULL COMMENT '登记人' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`regFlag`  smallint(6) NOT NULL COMMENT '登记标识\r\n            1=正常登记（上班，下班）\r\n            2＝迟到\r\n            3=早退\r\n            4＝休息\r\n            5＝旷工\r\n            6=放假\r\n            \r\n            ' ,
`regMins`  int(11) NOT NULL COMMENT '迟到或早退分钟\r\n            正常上班时为0' ,
`reason`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '迟到原因' ,
`dayOfWeek`  int(11) NOT NULL COMMENT '周几\r\n            1=星期日\r\n            ..\r\n            7=日期六' ,
`inOffFlag`  smallint(6) NOT NULL COMMENT '上下班标识\r\n            1=签到\r\n            2=签退' ,
`sectionId`  bigint(20) NOT NULL ,
PRIMARY KEY (`registerId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`sectionId`) REFERENCES `duty_section` (`sectionId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of duty_register
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `duty_section`
-- ----------------------------
DROP TABLE IF EXISTS `duty_section`;
CREATE TABLE `duty_section` (
`sectionId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`sectionName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`startSignin`  datetime NOT NULL ,
`dutyStartTime`  datetime NOT NULL ,
`endSignin`  datetime NOT NULL ,
`earlyOffTime`  datetime NOT NULL ,
`dutyEndTime`  datetime NOT NULL ,
`signOutTime`  datetime NOT NULL COMMENT '' ,
PRIMARY KEY (`sectionId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of duty_section
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `duty_system`
-- ----------------------------
DROP TABLE IF EXISTS `duty_system`;
CREATE TABLE `duty_system` (
`systemId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`systemName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`systemSetting`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`systemDesc`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`isDefault`  smallint(6) NOT NULL COMMENT '' ,
PRIMARY KEY (`systemId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT=''
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of duty_system
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `emp_profile`
-- ----------------------------
DROP TABLE IF EXISTS `emp_profile`;
CREATE TABLE `emp_profile` (
`profileId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`profileNo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '档案编号' ,
`userId`  bigint(20) NOT NULL ,
`fullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工姓名' ,
`address`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭地址' ,
`birthday`  datetime NULL DEFAULT NULL COMMENT '出生日期' ,
`homeZip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '家庭邮编' ,
`sex`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`marriage`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '婚姻状况\r\n            已婚\r\n            未婚' ,
`designation`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`jobId`  bigint(20) NOT NULL ,
`position`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`phone`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码' ,
`mobile`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码' ,
`openBank`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户银行' ,
`bankNo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行账号' ,
`qq`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ号码' ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱' ,
`hobby`  varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '爱好' ,
`religion`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '宗教信仰' ,
`party`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '政治面貌' ,
`nationality`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '国籍' ,
`race`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族' ,
`birthPlace`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出生地' ,
`eduDegree`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学历' ,
`eduMajor`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '专业' ,
`eduCollege`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '毕业院校' ,
`startWorkDate`  datetime NULL DEFAULT NULL COMMENT '参加工作时间' ,
`eduCase`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教育背景' ,
`awardPunishCase`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖惩情况' ,
`trainingCase`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '培训情况' ,
`workCase`  varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作经历' ,
`idCard`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号' ,
`photo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '照片' ,
`standardMiNo`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '薪酬标准编号' ,
`standardMoney`  decimal(18,2) NULL DEFAULT NULL COMMENT '薪酬标准金额' ,
`standardName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '薪酬标准单名称' ,
`standardId`  bigint(20) NULL DEFAULT NULL COMMENT '薪酬标准单编号' ,
`creator`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '建档人' ,
`createtime`  datetime NULL DEFAULT NULL COMMENT '建档时间' ,
`checkName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人' ,
`checktime`  datetime NULL DEFAULT NULL COMMENT '审核时间' ,
`opprovalOpinion`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`approvalStatus`  smallint(6) NULL DEFAULT 0 COMMENT '核审状态\r\n            0=未审批\r\n            1=通过审核\r\n            2=未通过审核' ,
`memo`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`depName`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所属部门或公司' ,
`depId`  bigint(20) NULL DEFAULT NULL COMMENT '所属部门Id' ,
`delFlag`  smallint(6) NOT NULL DEFAULT 0 COMMENT '删除状态\r\n            0=未删除\r\n            1=删除' ,
PRIMARY KEY (`profileId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`jobId`) REFERENCES `job` (`jobId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='员工档案'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of emp_profile
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `errands_register`
-- ----------------------------
DROP TABLE IF EXISTS `errands_register`;
CREATE TABLE `errands_register` (
`dateId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`userId`  bigint(20) NOT NULL COMMENT '外出/加班人员' ,
`descp`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述' ,
`startTime`  datetime NOT NULL COMMENT '开始日期' ,
`endTime`  datetime NOT NULL COMMENT '结束日期' ,
`approvalId`  bigint(20) NOT NULL COMMENT '审批人ID' ,
`status`  smallint(6) NOT NULL COMMENT '审批状态\r\n            0=未审批\r\n            1=通过审批\r\n            2=不通过审批' ,
`approvalOption`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批意见' ,
`approvalName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '审批人' ,
`flag`  smallint(6) NULL DEFAULT NULL COMMENT '标识\r\n            0=加班\r\n            1=请假\r\n            2=外出' ,
PRIMARY KEY (`dateId`),
FOREIGN KEY (`approvalId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='请假、加班、外出登记'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of errands_register
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `file_attach`
-- ----------------------------
DROP TABLE IF EXISTS `file_attach`;
CREATE TABLE `file_attach` (
`fileId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`fileName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件名' ,
`filePath`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件路径' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
`ext`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展名' ,
`fileType`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '附件类型\r\n            如：邮件附件' ,
`note`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明' ,
`creator`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上传者' ,
PRIMARY KEY (`fileId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='附件'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of file_attach
-- ----------------------------
BEGIN;
INSERT INTO file_attach VALUES ('1', 'webQQ截图.docx', 'document/201005/2f1094cdfd6843b39f3e3e9ae3e18d7e.docx', '2010-05-22 14:10:04', 'docx', 'document', '137923 bytes', '超级管理员'), ('2', 'webQQ截图.docx', 'document/201005/185bc295267b45e7a6d467b44ab72ebe.docx', '2010-05-22 14:15:24', 'docx', 'document', '137923 bytes', '超级管理员'), ('3', 'webQQ截图.docx', 'document/201005/acb51b7f7fd6417384ddc694abef3881.docx', '2010-05-22 14:19:05', 'docx', 'document', '137923 bytes', '超级管理员');
COMMIT;

-- ----------------------------
-- Table structure for `fixed_assets`
-- ----------------------------
DROP TABLE IF EXISTS `fixed_assets`;
CREATE TABLE `fixed_assets` (
`assetsId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`assetsNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`assetsName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`model`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`assetsTypeId`  bigint(20) NOT NULL ,
`manufacturer`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`manuDate`  datetime NULL DEFAULT NULL ,
`buyDate`  datetime NOT NULL ,
`beDep`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`custodian`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remainValRate`  decimal(18,6) NOT NULL ,
`depreTypeId`  bigint(20) NOT NULL ,
`startDepre`  datetime NULL DEFAULT NULL ,
`intendTerm`  decimal(18,2) NULL DEFAULT NULL ,
`intendWorkGross`  decimal(18,2) NULL DEFAULT NULL COMMENT '当折旧的方法选择用工作量法进行计算时，才需要填写' ,
`workGrossUnit`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`assetValue`  decimal(18,4) NOT NULL ,
`assetCurValue`  decimal(18,4) NOT NULL ,
`depreRate`  decimal(18,2) NULL DEFAULT NULL ,
`defPerWorkGross`  decimal(18,2) NULL DEFAULT NULL ,
PRIMARY KEY (`assetsId`),
FOREIGN KEY (`assetsTypeId`) REFERENCES `assets_type` (`assetsTypeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`depreTypeId`) REFERENCES `depre_type` (`depreTypeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of fixed_assets
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `form_data`
-- ----------------------------
DROP TABLE IF EXISTS `form_data`;
CREATE TABLE `form_data` (
`dataId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`formId`  bigint(20) NOT NULL COMMENT '所属表单' ,
`fieldLabel`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段标签' ,
`fieldName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字段名称' ,
`intValue`  int(11) NULL DEFAULT NULL COMMENT '整数值' ,
`longValue`  bigint(20) NULL DEFAULT NULL COMMENT '长整值' ,
`decValue`  decimal(18,4) NULL DEFAULT NULL COMMENT '精度值' ,
`dateValue`  datetime NULL DEFAULT NULL COMMENT '日期值' ,
`strValue`  varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '字符值' ,
`boolValue`  smallint(6) NULL DEFAULT NULL COMMENT '布尔值' ,
`blobValue`  blob NULL DEFAULT NULL COMMENT '对象值' ,
`isShowed`  smallint(6) NOT NULL COMMENT '是否显示\r\n            1=显示\r\n            0=不显示' ,
`textValue`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`fieldType`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`dataId`),
FOREIGN KEY (`formId`) REFERENCES `process_form` (`formId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of form_data
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `form_def`
-- ----------------------------
DROP TABLE IF EXISTS `form_def`;
CREATE TABLE `form_def` (
`formDefId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`formName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表单名称' ,
`columns`  int(11) NOT NULL COMMENT '总列数' ,
`isEnabled`  smallint(6) NOT NULL DEFAULT 1 COMMENT '是否可用' ,
`activityName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '节点名称' ,
`deployId`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Jbpm流程发布ID' ,
`extDef`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '表单定义' ,
`formView`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义View' ,
PRIMARY KEY (`formDefId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=20

;

-- ----------------------------
-- Records of form_def
-- ----------------------------
BEGIN;
INSERT INTO form_def VALUES ('1', '申请-表单', '1', '1', '申请', '7', null, null), ('2', '部门领导审阅-表单', '1', '1', '部门领导审阅', '7', null, null), ('3', '分管领导盖章-表单', '1', '1', '分管领导盖章', '7', null, null), ('4', '打印-表单', '1', '1', '打印', '7', null, null), ('5', '财务处-表单', '1', '1', '财务处', '7', null, null), ('6', '开始-表单', '1', '1', '开始', '8', null, null), ('7', '上级审批-表单', '1', '1', '上级审批', '8', null, null), ('8', '开始-表单', '1', '1', '开始', '5', null, null), ('9', '登记拟办-表单', '1', '1', '登记拟办', '5', null, null), ('10', '领导批示-表单', '1', '1', '领导批示', '5', null, null), ('11', '公文分发-表单', '1', '1', '公文分发', '5', null, null), ('12', '承办传阅-表单', '1', '1', '承办传阅', '5', null, null), ('13', '开始-表单', '1', '1', '开始', '4', null, null), ('14', '发文核稿-表单', '1', '1', '发文核稿', '4', null, null), ('15', '科室审核-表单', '1', '1', '科室审核', '4', null, null), ('16', '主管领导审批-表单', '1', '1', '主管领导审批', '4', null, null), ('17', '发文分发-表单', '1', '1', '发文分发', '4', null, null), ('18', '发文校对-表单', '1', '1', '发文校对', '4', null, null), ('19', '分管领导签发-表单', '1', '1', '分管领导签发', '4', null, null);
COMMIT;

-- ----------------------------
-- Table structure for `fun_url`
-- ----------------------------
DROP TABLE IF EXISTS `fun_url`;
CREATE TABLE `fun_url` (
`urlId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`functionId`  bigint(20) NOT NULL ,
`urlPath`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`urlId`),
FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=386

;

-- ----------------------------
-- Records of fun_url
-- ----------------------------
BEGIN;
INSERT INTO fun_url VALUES ('1', '1', '/system/listAppUser.do'), ('2', '2', '/system/listAppUser.do'), ('3', '2', '/system/chooseRolesAppUser.do'), ('4', '2', '/system/selectedRolesAppUser.do'), ('5', '2', '/system/listDepartment.do'), ('6', '3', '/system/listAppUser.do'), ('7', '3', '/system/chooseRolesAppUser.do'), ('8', '3', '/system/selectedRolesAppUser.do'), ('9', '3', '/system/listDepartment.do'), ('10', '4', '/system/listAppUser.do'), ('11', '4', '/system/multiDelAppUser.do'), ('12', '5', '/system/listAppRole.do'), ('13', '6', '/system/listAppRole.do'), ('14', '6', '/system/saveAppRole.do'), ('15', '7', '/system/listAppRole.do'), ('16', '7', '/system/saveAppRole.do'), ('17', '7', '/system/getAppRole.do'), ('18', '8', '/system/listAppRole.do'), ('19', '8', '/system/mulDelAppRole.do'), ('20', '9', '/system/listAppRole.do'), ('21', '9', '/system/grantXmlAppRole.do'), ('22', '9', '/system/getAppRole.do'), ('23', '9', '/system/grantAppRole.do'), ('24', '10', '/system/listDepartment.do'), ('25', '10', '/system/selectAppUser.do'), ('26', '11', '/system/listDepartment.do'), ('27', '11', '/system/addDepartment.do'), ('28', '11', '/system/selectAppUser.do'), ('29', '11', '/system/saveAppUser.do'), ('30', '12', '/system/listDepartment.do'), ('31', '12', '/system/addDepartment.do'), ('32', '12', '/system/detailDepartment.do'), ('33', '12', '/system/selectAppUser.do'), ('34', '12', '/system/saveAppUser.do'), ('35', '13', '/system/listDepartment.do'), ('36', '13', '/system/removeDepartment.do'), ('37', '13', '/system/selectAppUser.do'), ('38', '13', '/system/saveAppUser.do'), ('39', '14', '/system/listFileAttach.do'), ('40', '15', '/system/saveFileAttach.do'), ('41', '15', '/system/listFileAttach.do'), ('42', '15', '/system/getFileAttach.do'), ('43', '16', '/system/listFileAttach.do'), ('44', '16', '/system/multiDelFileAttach.do'), ('45', '17', '/system/listCompany.do'), ('46', '17', '/system/addCompany.do'), ('47', '18', '/flow/rootProType.do'), ('48', '18', '/flow/listProDefinition.do'), ('49', '18', '/flow/processDetail.do'), ('50', '19', '/flow/rootProType.do'), ('51', '19', '/flow/saveProType.do'), ('52', '19', '/flow/removeProType.do'), ('53', '19', '/flow/getProType.do'), ('54', '20', '/flow/rootProType.do'), ('55', '20', '/flow/listProDefinition.do'), ('56', '20', '/flow/saveProDefinition.do'), ('57', '20', '/flow/listProType.do'), ('58', '20', '/flow/getProDefinition.do'), ('59', '21', '/flow/rootProType.do'), ('60', '21', '/flow/listProDefinition.do'), ('61', '21', '/flow/saveProDefinition.do'), ('62', '21', '/flow/listProType.do'), ('63', '21', '/flow/getProDefinition.do'), ('64', '22', '/flow/rootProType.do'), ('65', '22', '/flow/listProDefinition.do'), ('66', '22', '/flow/multiDelProDefinition.do'), ('67', '23', '/flow/processDetail.do'), ('68', '24', '/flow/saveProUserAssign.do'), ('69', '24', '/flow/listProUserAssign.do'), ('70', '25', '/document/saveDocFolder.do'), ('71', '25', '/document/getDocFolder.do'), ('72', '25', '/document/removeDocFolder.do'), ('73', '26', '/document/listDocPrivilege.do'), ('74', '27', '/document/listDocPrivilege.do'), ('75', '27', '/document/addDocPrivilege.do'), ('76', '28', '/document/listDocPrivilege.do'), ('77', '28', '/document/changeDocPrivilege.do'), ('78', '29', '/document/listDocPrivilege.do'), ('79', '29', '/document/multiDelDocPrivilege.do'), ('80', '30', '/task/listPlanType.do'), ('81', '31', '/task/listPlanType.do'), ('82', '31', '/task/savePlanType.do'), ('83', '32', '/task/listPlanType.do'), ('84', '32', '/task/savePlanType.do'), ('85', '32', '/task/getPlanType.do'), ('86', '33', '/task/listPlanType.do'), ('87', '33', '/task/multiDelPlanType.do'), ('88', '35', '/info/categoryNews.do'), ('89', '35', '/info/treeNewsType.do'), ('90', '36', '/info/categoryNews.do'), ('91', '36', '/info/treeNewsType.do'), ('92', '36', '/info/saveNews.do'), ('93', '37', '/info/categoryNews.do'), ('94', '37', '/info/treeNewsType.do'), ('95', '37', '/info/saveNews.do'), ('96', '38', '/info/categoryNews.do'), ('97', '38', '/info/treeNewsType.do'), ('98', '38', '/info/multiDelNews.do'), ('99', '40', '/info/multiDelNewsComment.do'), ('100', '41', '/info/listNewsType.do'), ('101', '42', '/info/listNewsType.do');
INSERT INTO fun_url VALUES ('102', '42', '/info/addNewsType.do'), ('103', '43', '/info/listNewsType.do'), ('104', '43', '/info/addNewsType.do'), ('105', '43', '/info/detailNewsType.do'), ('106', '43', '/info/sortNewsType.do'), ('107', '44', '/info/listNewsType.do'), ('108', '44', '/info/removeNewsType.do'), ('109', '46', '/info/saveNotice.do'), ('110', '47', '/info/saveNotice.do'), ('111', '48', '/info/multiDelNotice.do'), ('112', '49', '/personal/listHolidayRecord.do'), ('113', '50', '/personal/listHolidayRecord.do'), ('114', '50', '/personal/saveHolidayRecord.do'), ('115', '51', '/personal/listHolidayRecord.do'), ('116', '51', '/personal/getHolidayRecord.do'), ('117', '51', '/personal/saveHolidayRecord.do'), ('118', '52', '/personal/listHolidayRecord.do'), ('119', '52', '/personal/multiDelHolidayRecord.do'), ('120', '53', '/personal/listDutySection.do'), ('121', '54', '/personal/listDutySection.do'), ('122', '54', '/personal/saveDutySection.do'), ('123', '55', '/personal/listDutySection.do'), ('124', '55', '/personal/saveDutySection.do'), ('125', '55', '/personal/getDutySection.do'), ('126', '56', '/personal/listDutySection.do'), ('127', '56', '/personal/multiDelDutySection.do'), ('128', '57', '/personal/listDutySystem.do'), ('129', '58', '/personal/listDutySystem.do'), ('130', '58', '/personal/settingDutySystem.do'), ('131', '58', '/personal/saveDutySystem.do'), ('132', '59', '/personal/listDutySystem.do'), ('133', '59', '/personal/getDutySystem.do'), ('134', '59', '/personal/saveDutySystem.do'), ('135', '60', '/personal/listDutySystem.do'), ('136', '60', '/personal/multiDelDutySystem.do'), ('137', '61', '/personal/listDuty.do'), ('138', '62', '/personal/listDuty.do'), ('139', '62', '/personal/saveDuty.do'), ('140', '62', '/personal/comboDutySystem.do'), ('141', '63', '/personal/listDuty.do'), ('142', '63', '/personal/saveDuty.do'), ('143', '63', '/personal/comboDutySystem.do'), ('144', '63', '/personal/getDuty.do'), ('145', '64', '/personal/listDuty.do'), ('146', '64', '/personal/multiDelDuty.do'), ('147', '65', '/personal/listDutyRegister.do'), ('148', '66', '/personal/listDutyRegister.do'), ('149', '66', '/personal/saveDutyRegister.do'), ('150', '66', '/personal/comboDutySection.do'), ('151', '67', '/personal/listDutyRegister.do'), ('152', '67', '/personal/multiDelDutyRegister.do'), ('153', '68', '/customer/listCustomer.do'), ('154', '69', '/customer/listCustomer.do'), ('155', '69', '/customer/saveCustomer.do'), ('156', '69', '/customer/checkCustomer.do'), ('157', '69', '/customer/numberCustomer.do'), ('158', '69', '/system/listRegion.do'), ('159', '70', '/customer/listCustomer.do'), ('160', '70', '/customer/saveCustomer.do'), ('161', '70', '/customer/checkCustomer.do'), ('162', '70', '/customer/numberCustomer.do'), ('163', '70', '/system/listRegion.do'), ('164', '70', '/customer/getCustomer.do'), ('165', '71', '/customer/listCustomer.do'), ('166', '71', '/customer/multiDelCustomer.do'), ('167', '72', '/customer/listCusLinkman.do'), ('168', '73', '/customer/listCusLinkman.do'), ('169', '73', '/customer/saveCusLinkman.do'), ('170', '74', '/customer/listCusLinkman.do'), ('171', '74', '/customer/saveCusLinkman.do'), ('172', '74', '/customer/getCusLinkman.do'), ('173', '75', '/customer/listCusLinkman.do'), ('174', '75', '/customer/multiDelCusLinkman.do'), ('175', '76', '/customer/listCusConnection.do'), ('176', '77', '/customer/listCusConnection.do'), ('177', '77', '/customer/saveCusConnection.do'), ('178', '77', '/customer/findCusLinkman.do'), ('179', '78', '/customer/listCusConnection.do'), ('180', '78', '/customer/saveCusConnection.do'), ('181', '78', '/customer/findCusLinkman.do'), ('182', '78', '/customer/getCusConnection.do'), ('183', '79', '/customer/listCusConnection.do'), ('184', '79', '/customer/multiDelCusConnection.do'), ('185', '80', '/customer/listProject.do'), ('186', '81', '/customer/listProject.do'), ('187', '81', '/customer/saveProject.do'), ('188', '81', '/customer/numberProject.do'), ('189', '81', '/customer/findCusLinkman.do'), ('190', '82', '/customer/listProject.do'), ('191', '82', '/customer/saveProject.do'), ('192', '82', '/customer/numberProject.do'), ('193', '82', '/customer/findCusLinkman.do'), ('194', '82', '/customer/getProject.do'), ('195', '83', '/customer/listProject.do'), ('196', '83', '/customer/multiDelProject.do'), ('197', '84', '/customer/listContract.do'), ('198', '85', '/customer/listContract.do'), ('199', '85', '/customer/saveContract.do'), ('200', '85', '/customer/getProject.do'), ('201', '85', '/customer/removeFileContract.do'), ('202', '85', '/customer/listContractConfig.do');
INSERT INTO fun_url VALUES ('203', '85', '/customer/multiDelContractConfig.do'), ('204', '86', '/customer/listContract.do'), ('205', '86', '/customer/saveContract.do'), ('206', '86', '/customer/getProject.do'), ('207', '86', '/customer/removeFileContract.do'), ('208', '86', '/customer/listContractConfig.do'), ('209', '86', '/customer/multiDelContractConfig.do'), ('210', '86', '/customer/getContract.do'), ('211', '87', '/customer/listContract.do'), ('212', '87', '/customer/multiDelContract.do'), ('213', '88', '/customer/listProduct.do'), ('214', '89', '/customer/saveProduct.do'), ('215', '89', '/customer/listProvider.do'), ('216', '90', '/customer/listProduct.do'), ('217', '90', '/customer/getProduct.do'), ('218', '90', '/customer/saveProduct.do'), ('219', '91', '/customer/listProduct.do'), ('220', '91', '/customer/multiDelProduct.do'), ('221', '92', '/customer/listProvider.do'), ('222', '93', '/customer/saveProvider.do'), ('223', '94', '/customer/listProvider.do'), ('224', '94', '/customer/getProvider.do'), ('225', '94', '/customer/saveProvider.do'), ('226', '95', '/customer/listProvider.do'), ('227', '95', '/customer/multiDelProvider.do'), ('228', '96', '/admin/listOfficeGoods.do'), ('229', '96', '/admin/treeOfficeGoodsType.do'), ('230', '97', '/admin/listOfficeGoods.do'), ('231', '97', '/admin/treeOfficeGoodsType.do'), ('232', '97', '/admin/multiDelOfficeGoodsType.do'), ('233', '97', '/admin/saveOfficeGoodsType.do'), ('234', '97', '/admin/getOfficeGoodsType.do'), ('235', '98', '/admin/listOfficeGoods.do'), ('236', '98', '/admin/saveOfficeGoods.do'), ('237', '98', '/admin/treeOfficeGoodsType.do'), ('238', '99', '/admin/listOfficeGoods.do'), ('239', '99', '/admin/saveOfficeGoods.do'), ('240', '99', '/admin/treeOfficeGoodsType.do'), ('241', '99', '/admin/getOfficeGoods.do'), ('242', '100', '/admin/listOfficeGoods.do'), ('243', '100', '/admin/multiDelOfficeGoods.do'), ('244', '101', '/admin/listInStock.do'), ('245', '102', '/admin/listInStock.do'), ('246', '102', '/admin/listOfficeGoods.do'), ('247', '102', '/admin/saveInStock.do'), ('248', '102', '/admin/treeOfficeGoodsType.do'), ('249', '103', '/admin/listInStock.do'), ('250', '103', '/admin/listOfficeGoods.do'), ('251', '103', '/admin/saveInStock.do'), ('252', '103', '/admin/treeOfficeGoodsType.do'), ('253', '103', '/admin/getInStock.do'), ('254', '104', '/admin/listInStock.do'), ('255', '104', '/admin/multiDelInStock.do'), ('256', '105', '/admin/listGoodsApply.do'), ('257', '106', '/admin/listGoodsApply.do'), ('258', '106', '/admin/saveGoodsApply.do'), ('259', '106', '/admin/listOfficeGoods.do'), ('260', '106', '/admin/treeOfficeGoodsType.do'), ('261', '107', '/admin/listGoodsApply.do'), ('262', '107', '/admin/saveGoodsApply.do'), ('263', '107', '/admin/listOfficeGoods.do'), ('264', '107', '/admin/treeOfficeGoodsType.do'), ('265', '107', '/admin/getGoodsApply.do'), ('266', '108', '/admin/listGoodsApply.do'), ('267', '108', '/admin/multiDelGoodsApply.do'), ('268', '109', '/admin/listCar.do'), ('269', '110', '/admin/listCar.do'), ('270', '110', '/admin/saveCar.do'), ('271', '111', '/admin/listCar.do'), ('272', '111', '/admin/saveCar.do'), ('273', '111', '/admin/getCar.do'), ('274', '112', '/admin/listCar.do'), ('275', '112', '/admin/multiDelCar.do'), ('276', '113', '/admin/listCartRepair.do'), ('277', '114', '/admin/listCartRepair.do'), ('278', '114', '/admin/saveCartRepair.do'), ('279', '115', '/admin/listCartRepair.do'), ('280', '115', '/admin/saveCartRepair.do'), ('281', '115', '/admin/getCartRepair.do'), ('282', '116', '/admin/listCartRepair.do'), ('283', '116', '/admin/multiDelCartRepair.do'), ('284', '117', '/admin/listCarApply.do'), ('285', '118', '/admin/listCarApply.do'), ('286', '118', '/admin/saveCarApply.do'), ('287', '119', '/admin/listCarApply.do'), ('288', '119', '/admin/saveCarApply.do'), ('289', '119', '/admin/getCarApply.do'), ('290', '120', '/admin/listCarApply.do'), ('291', '120', '/admin/multiDelCarApply.do'), ('292', '121', '/admin/listDepreType.do'), ('293', '122', '/admin/listDepreType.do'), ('294', '122', '/admin/saveDepreType.do'), ('295', '123', '/admin/listDepreType.do'), ('296', '123', '/admin/saveDepreType.do'), ('297', '123', '/admin/getDepreType.do'), ('298', '124', '/admin/listDepreType.do'), ('299', '124', '/admin/multiDelDepreType.do'), ('300', '125', '/admin/listFixedAssets.do'), ('301', '125', '/admin/treeAssetsType.do'), ('302', '126', '/admin/treeAssetsType.do'), ('303', '126', '/admin/multiDelAssetsType.do');
INSERT INTO fun_url VALUES ('304', '126', '/admin/saveAssetsType.do'), ('305', '126', '/admin/getAssetsType.do'), ('306', '127', '/admin/listFixedAssets.do'), ('307', '127', '/system/selectDepartment.do'), ('308', '127', '/admin/treeAssetsType.do'), ('309', '127', '/admin/saveFixedAssets.do'), ('310', '127', '/admin/comboxAssetsType.do'), ('311', '127', '/admin/comboxDepreType.do'), ('312', '128', '/admin/listFixedAssets.do'), ('313', '128', '/admin/treeAssetsType.do'), ('314', '128', '/system/selectDepartment.do'), ('315', '128', '/admin/saveFixedAssets.do'), ('316', '128', '/admin/comboxAssetsType.do'), ('317', '128', '/admin/comboxDepreType.do'), ('318', '128', '/admin/getFixedAssets.do'), ('319', '129', '/admin/listFixedAssets.do'), ('320', '129', '/admin/treeAssetsType.do'), ('321', '129', '/admin/multiDelFixedAssets.do'), ('322', '130', '/admin/depreciateDepreRecord.do'), ('323', '130', '/admin/workDepreRecord.do'), ('324', '131', '/admin/listDepreRecord.do'), ('325', '132', '/admin/listBookType.do'), ('326', '133', '/admin/listBookType.do'), ('327', '133', '/admin/saveBookType.do'), ('328', '134', '/admin/listBookType.do'), ('329', '134', '/admin/saveBookType.do'), ('330', '134', '/admin/getBookType.do'), ('331', '135', '/admin/listBookType.do'), ('332', '135', '/admin/multiDelBookType.do'), ('333', '136', '/admin/listBook.do'), ('334', '136', '/admin/treeBookType.do'), ('335', '137', '/admin/listBook.do'), ('336', '137', '/admin/treeBookType.do'), ('337', '137', '/admin/saveBook.do'), ('338', '137', '/admin/treeBook.do'), ('339', '138', '/admin/listBookType.do'), ('340', '138', '/admin/treeBookType.do'), ('341', '138', '/admin/saveBookType.do'), ('342', '138', '/admin/treeBook.do'), ('343', '138', '/admin/getBook.do'), ('344', '139', '/admin/listBook.do'), ('345', '139', '/admin/treeBookType.do'), ('346', '139', '/admin/multiDelBook.do'), ('347', '140', '/admin/listBorrowBookBorRet.do'), ('348', '141', '/admin/listBorrowBookBorRet.do'), ('349', '141', '/admin/saveBorrowBookBorRet.do'), ('350', '141', '/admin/listBook.do'), ('351', '141', '/admin/treeBookType.do'), ('352', '141', '/admin/getBook.do'), ('353', '141', '/admin/getBorrowSnBookSn.do'), ('354', '142', '/admin/listBorrowBookBorRet.do'), ('355', '142', '/admin/saveBorrowBookBorRet.do'), ('356', '142', '/admin/listBook.do'), ('357', '142', '/admin/treeBookType.do'), ('358', '142', '/admin/getBook.do'), ('359', '142', '/admin/getBorrowSnBookSn.do'), ('360', '142', '/admin/getBookBorRet.do'), ('361', '143', '/admin/listBook.do'), ('362', '143', '/admin/treeBookType.do'), ('363', '143', '/admin/getReturnSnBookSn.do'), ('364', '143', '/admin/getBorRetTimeBookBorRet.do'), ('365', '143', '/admin/listReturnBookBorRet.do'), ('366', '143', '/admin/saveReturnBookBorRet.do'), ('367', '143', '/admin/getBookBorRet.do'), ('368', '144', '/admin/listBorrowBookBorRet.do'), ('369', '144', '/admin/multiDelBookBorRet.do'), ('370', '145', '/admin/listReturnBookBorRet.do'), ('371', '146', '/admin/listBook.do'), ('372', '146', '/admin/treeBookType.do'), ('373', '146', '/admin/getReturnSnBookSn.do'), ('374', '146', '/admin/getBorRetTimeBookBorRet.do'), ('375', '146', '/admin/listReturnBookBorRet.do'), ('376', '146', '/admin/saveReturnBookBorRet.do'), ('377', '147', '/admin/listBook.do'), ('378', '147', '/admin/treeBookType.do'), ('379', '147', '/admin/getReturnSnBookSn.do'), ('380', '147', '/admin/getBorRetTimeBookBorRet.do'), ('381', '147', '/admin/listReturnBookBorRet.do'), ('382', '147', '/admin/saveReturnBookBorRet.do'), ('383', '147', '/admin/getBookBorRet.do'), ('384', '148', '/admin/listReturnBookBorRet.do'), ('385', '148', '/admin/multiDelBookBorRet.do');
COMMIT;

-- ----------------------------
-- Table structure for `goods_apply`
-- ----------------------------
DROP TABLE IF EXISTS `goods_apply`;
CREATE TABLE `goods_apply` (
`applyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`goodsId`  bigint(20) NOT NULL ,
`applyDate`  datetime NOT NULL ,
`applyNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '申请号,按系统时间产生，如GA20091002-0001' ,
`useCounts`  int(11) NOT NULL ,
`userId`  bigint(20) NOT NULL ,
`proposer`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`approvalStatus`  smallint(6) NOT NULL COMMENT '审批状态\r\n            1=通过审批\r\n            0=未审批\r\n            ' ,
PRIMARY KEY (`applyId`),
FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='办公用品出库'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of goods_apply
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `hire_issue`
-- ----------------------------
DROP TABLE IF EXISTS `hire_issue`;
CREATE TABLE `hire_issue` (
`hireId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '招聘信息标题' ,
`startDate`  datetime NOT NULL COMMENT '开始时间' ,
`endDate`  datetime NOT NULL COMMENT '结束时间' ,
`hireCount`  int(11) NOT NULL COMMENT '招聘人数' ,
`jobName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称' ,
`jobCondition`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '招聘要求(条件)' ,
`regFullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登记人姓名' ,
`regDate`  datetime NOT NULL COMMENT '登记时间' ,
`modifyFullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变更人姓名' ,
`modifyDate`  datetime NULL DEFAULT NULL COMMENT '变更时间' ,
`checkFullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核人姓名' ,
`checkOpinion`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核意见' ,
`checkDate`  datetime NULL DEFAULT NULL COMMENT '审批时间' ,
`status`  smallint(6) NOT NULL COMMENT '状态\r\n            1=通过审核\r\n            0=未审核\r\n            2=审核不通过' ,
`memo`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
PRIMARY KEY (`hireId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='招聘发布'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of hire_issue
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `holiday_record`
-- ----------------------------
DROP TABLE IF EXISTS `holiday_record`;
CREATE TABLE `holiday_record` (
`recordId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`startTime`  datetime NOT NULL COMMENT '开始日期' ,
`endTime`  datetime NOT NULL COMMENT '结束日期' ,
`descp`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '假期描述' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名' ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '所属用户\r\n            若为某员工的假期，则为员工自己的id' ,
`isAll`  smallint(6) NOT NULL COMMENT '是否为全公司假期\r\n            1=是\r\n            0=否' ,
PRIMARY KEY (`recordId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='放假记录'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of holiday_record
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `in_message`
-- ----------------------------
DROP TABLE IF EXISTS `in_message`;
CREATE TABLE `in_message` (
`receiveId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`messageId`  bigint(20) NULL DEFAULT NULL ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`readFlag`  smallint(6) NOT NULL COMMENT '1=has red\r\n            0=unread' ,
`delFlag`  smallint(6) NOT NULL ,
`userFullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`receiveId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`messageId`) REFERENCES `short_message` (`messageId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of in_message
-- ----------------------------
BEGIN;
INSERT INTO in_message VALUES ('1', '1', '2', '1', '0', 'cwx'), ('2', '2', '1', '1', '1', '超级管理员'), ('3', '3', '2', '1', '0', 'cwx'), ('4', '4', '2', '1', '0', 'cwx');
COMMIT;

-- ----------------------------
-- Table structure for `in_stock`
-- ----------------------------
DROP TABLE IF EXISTS `in_stock`;
CREATE TABLE `in_stock` (
`buyId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`goodsId`  bigint(20) NOT NULL ,
`providerName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`stockNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`price`  decimal(18,2) NULL DEFAULT NULL ,
`inCounts`  int(11) NULL DEFAULT NULL ,
`amount`  decimal(18,2) NOT NULL ,
`inDate`  datetime NOT NULL ,
`buyer`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`buyId`),
FOREIGN KEY (`goodsId`) REFERENCES `office_goods` (`goodsId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='办公用品入库需要同时更新办公用品表的库存'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of in_stock
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `index_display`
-- ----------------------------
DROP TABLE IF EXISTS `index_display`;
CREATE TABLE `index_display` (
`indexId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`portalId`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Portal ID' ,
`userId`  bigint(20) NOT NULL COMMENT '用户ID' ,
`colNum`  int(11) NOT NULL COMMENT '列号' ,
`rowNum`  int(11) NOT NULL COMMENT '行号' ,
PRIMARY KEY (`indexId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='每个员工可以设置自己的显示方式'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of index_display
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_deployment`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployment`;
CREATE TABLE `jbpm4_deployment` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`NAME_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TIMESTAMP_`  bigint(20) NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of jbpm4_deployment
-- ----------------------------
BEGIN;
INSERT INTO jbpm4_deployment VALUES ('4', null, '0', 'active'), ('5', null, '0', 'active'), ('6', null, '0', 'active'), ('7', null, '0', 'active'), ('8', null, '0', 'active'), ('9', null, '0', 'active'), ('10', null, '0', 'active');
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_deployprop`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_deployprop`;
CREATE TABLE `jbpm4_deployprop` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DEPLOYMENT_`  bigint(20) NULL DEFAULT NULL ,
`OBJNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`STRINGVAL_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LONGVAL_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=31

;

-- ----------------------------
-- Records of jbpm4_deployprop
-- ----------------------------
BEGIN;
INSERT INTO jbpm4_deployprop VALUES ('10', '4', 'pd6426584402782899404', 'pdkey', 'pd6426584402782899404', null), ('11', '4', 'pd6426584402782899404', 'pdid', 'pd6426584402782899404-1', null), ('12', '4', 'pd6426584402782899404', 'pdversion', null, '1'), ('13', '5', 'pd4993365972583614546', 'pdkey', 'pd4993365972583614546', null), ('14', '5', 'pd4993365972583614546', 'pdversion', null, '1'), ('15', '5', 'pd4993365972583614546', 'pdid', 'pd4993365972583614546-1', null), ('16', '6', 'pd6578157620277996804', 'pdid', 'pd6578157620277996804-1', null), ('17', '6', 'pd6578157620277996804', 'pdversion', null, '1'), ('18', '6', 'pd6578157620277996804', 'pdkey', 'pd6578157620277996804', null), ('19', '7', 'pd8252642282251937144', 'pdversion', null, '1'), ('20', '7', 'pd8252642282251937144', 'pdkey', 'pd8252642282251937144', null), ('21', '7', 'pd8252642282251937144', 'pdid', 'pd8252642282251937144-1', null), ('22', '8', 'pd6212082814169152003', 'pdid', 'pd6212082814169152003-1', null), ('23', '8', 'pd6212082814169152003', 'pdversion', null, '1'), ('24', '8', 'pd6212082814169152003', 'pdkey', 'pd6212082814169152003', null), ('25', '9', 'pd8498713057619136655', 'pdid', 'pd8498713057619136655-1', null), ('26', '9', 'pd8498713057619136655', 'pdversion', null, '1'), ('27', '9', 'pd8498713057619136655', 'pdkey', 'pd8498713057619136655', null), ('28', '10', 'pd7096695693128483739', 'pdversion', null, '1'), ('29', '10', 'pd7096695693128483739', 'pdkey', 'pd7096695693128483739', null), ('30', '10', 'pd7096695693128483739', 'pdid', 'pd7096695693128483739-1', null);
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_execution`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_execution`;
CREATE TABLE `jbpm4_execution` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`ACTIVITYNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PROCDEFID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HASVARS_`  bit(1) NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`SUSPHISTSTATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PRIORITY_`  int(11) NULL DEFAULT NULL ,
`HISACTINST_`  bigint(20) NULL DEFAULT NULL ,
`PARENT_`  bigint(20) NULL DEFAULT NULL ,
`INSTANCE_`  bigint(20) NULL DEFAULT NULL ,
`SUPEREXEC_`  bigint(20) NULL DEFAULT NULL ,
`SUBPROCINST_`  bigint(20) NULL DEFAULT NULL ,
`PARENT_IDX_`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`INSTANCE_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`SUBPROCINST_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`SUPEREXEC_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_execution
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_hist_actinst`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_actinst`;
CREATE TABLE `jbpm4_hist_actinst` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`HPROCI_`  bigint(20) NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EXECUTION_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ACTIVITY_NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`START_`  datetime NULL DEFAULT NULL ,
`END_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`TRANSITION_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`NEXTIDX_`  int(11) NULL DEFAULT NULL ,
`HTASK_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_hist_actinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_hist_detail`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_detail`;
CREATE TABLE `jbpm4_hist_detail` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`USERID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TIME_`  datetime NULL DEFAULT NULL ,
`HPROCI_`  bigint(20) NULL DEFAULT NULL ,
`HPROCIIDX_`  int(11) NULL DEFAULT NULL ,
`HACTI_`  bigint(20) NULL DEFAULT NULL ,
`HACTIIDX_`  int(11) NULL DEFAULT NULL ,
`HTASK_`  bigint(20) NULL DEFAULT NULL ,
`HTASKIDX_`  int(11) NULL DEFAULT NULL ,
`HVAR_`  bigint(20) NULL DEFAULT NULL ,
`HVARIDX_`  int(11) NULL DEFAULT NULL ,
`MESSAGE_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`OLD_INT_`  int(11) NULL DEFAULT NULL ,
`NEW_INT_`  int(11) NULL DEFAULT NULL ,
`OLD_STR_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`NEW_STR_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`OLD_TIME_`  datetime NULL DEFAULT NULL ,
`NEW_TIME_`  datetime NULL DEFAULT NULL ,
`PARENT_`  bigint(20) NULL DEFAULT NULL ,
`PARENT_IDX_`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`HACTI_`) REFERENCES `jbpm4_hist_actinst` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`HVAR_`) REFERENCES `jbpm4_hist_var` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_hist_detail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_hist_procinst`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_procinst`;
CREATE TABLE `jbpm4_hist_procinst` (
`DBID_`  bigint(20) NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PROCDEFID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`START_`  datetime NULL DEFAULT NULL ,
`END_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ENDACTIVITY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`NEXTIDX_`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of jbpm4_hist_procinst
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_hist_task`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_task`;
CREATE TABLE `jbpm4_hist_task` (
`DBID_`  bigint(20) NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`EXECUTION_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`OUTCOME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PRIORITY_`  int(11) NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CREATE_`  datetime NULL DEFAULT NULL ,
`END_`  datetime NULL DEFAULT NULL ,
`DURATION_`  bigint(20) NULL DEFAULT NULL ,
`NEXTIDX_`  int(11) NULL DEFAULT NULL ,
`SUPERTASK_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of jbpm4_hist_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_hist_var`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_hist_var`;
CREATE TABLE `jbpm4_hist_var` (
`DBID_`  bigint(20) NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`PROCINSTID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EXECUTIONID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`VARNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`VALUE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HPROCI_`  bigint(20) NULL DEFAULT NULL ,
`HTASK_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`HPROCI_`) REFERENCES `jbpm4_hist_procinst` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`HTASK_`) REFERENCES `jbpm4_hist_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of jbpm4_hist_var
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_id_group`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_group`;
CREATE TABLE `jbpm4_id_group` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PARENT_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`PARENT_`) REFERENCES `jbpm4_id_group` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_id_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_id_membership`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_membership`;
CREATE TABLE `jbpm4_id_membership` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`USER_`  bigint(20) NULL DEFAULT NULL ,
`GROUP_`  bigint(20) NULL DEFAULT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`GROUP_`) REFERENCES `jbpm4_id_group` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`USER_`) REFERENCES `jbpm4_id_user` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_id_membership
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_id_user`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_id_user`;
CREATE TABLE `jbpm4_id_user` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PASSWORD_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`GIVENNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`FAMILYNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`BUSINESSEMAIL_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_id_user
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_job`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_job`;
CREATE TABLE `jbpm4_job` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`DUEDATE_`  datetime NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ISEXCLUSIVE_`  bit(1) NULL DEFAULT NULL ,
`LOCKOWNER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LOCKEXPTIME_`  datetime NULL DEFAULT NULL ,
`EXCEPTION_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`RETRIES_`  int(11) NULL DEFAULT NULL ,
`PROCESSINSTANCE_`  bigint(20) NULL DEFAULT NULL ,
`EXECUTION_`  bigint(20) NULL DEFAULT NULL ,
`CFG_`  bigint(20) NULL DEFAULT NULL ,
`SIGNAL_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EVENT_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`REPEAT_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`CFG_`) REFERENCES `jbpm4_lob` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_job
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_lob`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_lob`;
CREATE TABLE `jbpm4_lob` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`BLOB_VALUE_`  longblob NULL DEFAULT NULL ,
`DEPLOYMENT_`  bigint(20) NULL DEFAULT NULL ,
`NAME_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`DEPLOYMENT_`) REFERENCES `jbpm4_deployment` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=16

;

-- ----------------------------
-- Records of jbpm4_lob
-- ----------------------------
BEGIN;
INSERT INTO jbpm4_lob VALUES ('9', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436343236353834343032373832383939343034223E3C7374617274206E616D653D22BFAACABC2220673D223235312C342C35382C3538223E3C7472616E736974696F6E206E616D653D22C1F7B3CCC6F4B6AF2220746F3D22B7A2CEC4BACBB8E52220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B7A2CEC4BACBB8E52220673D223233382C3130352C38392C3336223E3C7472616E736974696F6E206E616D653D22BACBB8E52220746F3D22BFC6CAD2C9F3BACB2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22BFC6CAD2C9F3BACB2220673D223234302C3137352C38392C3336223E3C7472616E736974696F6E206E616D653D22C9F3BACB2220746F3D22D6F7B9DCC1ECB5BCC9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22D6F7B9DCC1ECB5BCC9F3C5FA2220673D223234302C3233352C39332C3336223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22B7D6B9DCC1ECB5BCC7A9B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7A2CEC4B7D6B7A22220673D223233392C3434312C38392C3336223E3C7472616E736974696F6E206E616D653D22B7D6B7A22220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7A2CEC4D0A3B6D42220673D223234312C3336382C38392C3336223E3C7472616E736974696F6E206E616D653D22D0A3B6D42220746F3D22B7A2CEC4B7D6B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCC1ECB5BCC7A9B7A22220673D223233392C3330332C39362C3336223E3C7472616E736974696F6E206E616D653D22C7A9B7A22220746F3D22B7A2CEC4D0A3B6D42220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223235342C3530352C35382C3538222F3E3C2F70726F636573733E, '4', 'process.jpdl.xml'), ('10', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706434393933333635393732353833363134353436223E3C7374617274206E616D653D22BFAACABC2220673D223236312C31342C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC4E2B0EC2220746F3D22B5C7BCC7C4E2B0EC2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B5C7BCC7C4E2B0EC2220673D223233392C3130332C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC1ECB5BCC5FACABE2220746F3D22C1ECB5BCC5FACABE2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22C1ECB5BCC5FACABE2220673D223233392C3138312C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB9ABCEC4B7D6B7A22220746F3D22B9ABCEC4B7D6B7A22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B9ABCEC4B7D6B7A22220673D223234302C3235332C3130362C3433223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB3D0B0ECB4ABD4C42220746F3D22B3D0B0ECB4ABD4C42220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B3D0B0ECB4ABD4C42220673D223234322C3333392C3130362C3433223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223236362C3432372C35382C3538222F3E3C2F70726F636573733E, '5', 'process.jpdl.xml'), ('11', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436353738313537363230323737393936383034223E3C7374617274206E616D653D22BFAACABC2220673D223139312C32392C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBBC9F3C5FA2220746F3D22BEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22BEADC0EDC9F3C5FA2220673D223135352C3135382C3133372C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223139362C3237382C35382C3538222F3E3C2F70726F636573733E, '6', 'process.jpdl.xml'), ('12', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706438323532363432323832323531393337313434223E3C7374617274206E616D653D22BFAACABC2220673D223232322C392C35382C3538223E3C7472616E736974696F6E206E616D653D22C9EAC7EB2220746F3D22B2BFC3C5C1ECB5BCC9F3D4C42220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B2BFC3C5C1ECB5BCC9F3D4C42220673D223230332C39382C39382C3436223E3C7472616E736974696F6E206E616D653D22CCE1BDBBB7D6B9DCC1ECB5BC2220746F3D22B7D6B9DCC1ECB5BCB8C7D5C22220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B7D6B9DCC1ECB5BCB8C7D5C22220673D223230332C3137322C39392C3437223E3C7472616E736974696F6E206E616D653D22B4F0D3A62220746F3D22B4F2D3A12220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B4F2D3A12220673D223230342C3234382C39382C3435223E3C7472616E736974696F6E206E616D653D22B2C6CEF1B4A62220746F3D22B2C6CEF1B4A62220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22B2C6CEF1B4A62220673D223230332C3332372C3130352C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223232362C3431362C35382C3538222F3E3C2F70726F636573733E, '7', 'process.jpdl.xml'), ('13', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706436323132303832383134313639313532303033223E3C7374617274206E616D653D22BFAACABC2220673D223135382C35392C35382C3538223E3C7472616E736974696F6E206E616D653D22BFAACABC2220746F3D22C9CFBCB6C9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22C9CFBCB6C9F3C5FA2220673D223134312C3136392C39372C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8312220673D223136312C3237372C35382C3538222F3E3C2F70726F636573733E, '8', 'process.jpdl.xml'), ('14', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706438343938373133303537363139313336363535223E3C7374617274206E616D653D22BFAACABC2220673D223139382C36322C35382C3538223E3C7472616E736974696F6E206E616D653D22C9EAC7EB2220746F3D22B1EDB5A52220673D222D32342C2D3136222F3E3C2F73746172743E3C656E64206E616D653D22BDE1CAF8312220673D223230342C3239382C35382C3538222F3E3C7461736B206E616D653D22B1EDB5A52220673D223138322C3138302C39372C3530223E3C7472616E736974696F6E206E616D653D22BDE1CAF82220746F3D22BDE1CAF8312220673D222D32342C2D3136222F3E3C2F7461736B3E3C2F70726F636573733E, '9', 'process.jpdl.xml'), ('15', '0', 0x3C3F786D6C2076657273696F6E3D22312E302220656E636F64696E673D2247424B223F3E0A3C70726F6365737320786D6C6E733D22687474703A2F2F6A62706D2E6F72672F342E302F6A70646C22206E616D653D22706437303936363935363933313238343833373339223E3C7374617274206E616D653D22BFAACABC2220673D223136392C34352C35382C3538223E3C7472616E736974696F6E206E616D653D22CCE1BDBB2220746F3D22B8B1BEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F73746172743E3C7461736B206E616D653D22B8B1BEADC0EDC9F3C5FA2220673D223133322C3133372C3133352C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22D7DCBEADC0EDC9F3C5FA2220673D222D32342C2D3136222F3E3C2F7461736B3E3C7461736B206E616D653D22D7DCBEADC0EDC9F3C5FA2220673D223133322C3232372C3133392C3530223E3C7472616E736974696F6E206E616D653D22C9F3C5FA2220746F3D22BDE1CAF8322220673D222D32342C2D3136222F3E3C2F7461736B3E3C656E64206E616D653D22BDE1CAF8322220673D223137362C3332392C35382C3538222F3E3C2F70726F636573733E, '10', 'process.jpdl.xml');
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_participation`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_participation`;
CREATE TABLE `jbpm4_participation` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`GROUPID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`USERID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TYPE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TASK_`  bigint(20) NULL DEFAULT NULL ,
`SWIMLANE_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_participation
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_swimlane`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_swimlane`;
CREATE TABLE `jbpm4_swimlane` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`DBVERSION_`  int(11) NOT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`EXECUTION_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_swimlane
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_task`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_task`;
CREATE TABLE `jbpm4_task` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`DESCR_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`STATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`SUSPHISTSTATE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ASSIGNEE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`FORM_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`PRIORITY_`  int(11) NULL DEFAULT NULL ,
`CREATE_`  datetime NULL DEFAULT NULL ,
`DUEDATE_`  datetime NULL DEFAULT NULL ,
`PROGRESS_`  int(11) NULL DEFAULT NULL ,
`SIGNALLING_`  bit(1) NULL DEFAULT NULL ,
`EXECUTION_ID_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ACTIVITY_NAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HASVARS_`  bit(1) NULL DEFAULT NULL ,
`SUPERTASK_`  bigint(20) NULL DEFAULT NULL ,
`EXECUTION_`  bigint(20) NULL DEFAULT NULL ,
`PROCINST_`  bigint(20) NULL DEFAULT NULL ,
`SWIMLANE_`  bigint(20) NULL DEFAULT NULL ,
`TASKDEFNAME_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`SUPERTASK_`) REFERENCES `jbpm4_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`SWIMLANE_`) REFERENCES `jbpm4_swimlane` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_task
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `jbpm4_variable`
-- ----------------------------
DROP TABLE IF EXISTS `jbpm4_variable`;
CREATE TABLE `jbpm4_variable` (
`DBID_`  bigint(20) NOT NULL AUTO_INCREMENT ,
`CLASS_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`DBVERSION_`  int(11) NOT NULL ,
`KEY_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`CONVERTER_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`HIST_`  bit(1) NULL DEFAULT NULL ,
`EXECUTION_`  bigint(20) NULL DEFAULT NULL ,
`TASK_`  bigint(20) NULL DEFAULT NULL ,
`DATE_VALUE_`  datetime NULL DEFAULT NULL ,
`DOUBLE_VALUE_`  double NULL DEFAULT NULL ,
`LONG_VALUE_`  bigint(20) NULL DEFAULT NULL ,
`STRING_VALUE_`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`TEXT_VALUE_`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`LOB_`  bigint(20) NULL DEFAULT NULL ,
`EXESYS_`  bigint(20) NULL DEFAULT NULL ,
PRIMARY KEY (`DBID_`),
FOREIGN KEY (`EXECUTION_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`EXESYS_`) REFERENCES `jbpm4_execution` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`LOB_`) REFERENCES `jbpm4_lob` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`TASK_`) REFERENCES `jbpm4_task` (`DBID_`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of jbpm4_variable
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `job`
-- ----------------------------
DROP TABLE IF EXISTS `job`;
CREATE TABLE `job` (
`jobId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`jobName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '职位名称' ,
`depId`  bigint(20) NOT NULL COMMENT '所属部门' ,
`memo`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`delFlag`  smallint(6) NOT NULL DEFAULT 0 COMMENT '0=未删除\r\n            1=删除' ,
PRIMARY KEY (`jobId`),
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=82

;

-- ----------------------------
-- Records of job
-- ----------------------------
BEGIN;
INSERT INTO job VALUES ('1', '局长', '25', '阳泉市工商行政管理局/局领导', '0'), ('2', '副局长', '25', '阳泉市工商行政管理局/局领导', '0'), ('3', '党组成员', '25', '', '0'), ('4', '纪检组长', '25', '', '0'), ('5', '办公室主任', '2', '', '0'), ('6', '科长', '3', '', '0'), ('7', '副科长', '3', '', '0'), ('8', '科员', '3', '', '0'), ('9', '科长', '4', '', '0'), ('10', '副科长', '4', '', '0'), ('11', '科员', '4', '', '0'), ('12', '科长', '5', '', '0'), ('13', '副科长', '5', '', '0'), ('14', '科员', '5', '', '0'), ('15', '科长', '6', '', '0'), ('16', '副科长', '6', '', '0'), ('17', '科员', '6', '', '0'), ('18', '科长', '7', '', '0'), ('19', '副科长', '7', '', '0'), ('20', '科员', '7', '', '0'), ('21', '科长', '8', '', '0'), ('22', '副科长', '8', '', '0'), ('23', '科员', '8', '', '0'), ('24', '科长', '9', '', '0'), ('25', '副科长', '9', '', '0'), ('26', '科员', '9', '', '0'), ('27', '科长', '10', '', '0'), ('28', '副科长', '10', '', '0'), ('29', '科员', '10', '', '0'), ('30', '科长', '11', '', '0'), ('31', '副科长', '11', '', '0'), ('32', '科员', '11', '', '0'), ('33', '科长', '12', '', '0'), ('34', '副科长', '12', '', '0'), ('35', '科员', '12', '', '0'), ('36', '科长', '13', '', '0'), ('37', '副科长', '13', '', '0'), ('38', '科员', '13', '', '0'), ('39', '科长', '14', '', '0'), ('40', '副科长', '14', '', '0'), ('41', '科员', '14', '', '0'), ('42', '主任', '15', '', '0'), ('43', '副主任', '15', '', '0'), ('44', '科员', '15', '', '0'), ('45', '主任', '16', '', '0'), ('46', '副主任', '16', '', '0'), ('47', '科员', '16', '', '0'), ('48', '主任', '18', '', '0'), ('49', '副主任', '18', '', '0'), ('50', '科员', '18', '', '0'), ('51', '主任', '19', '', '0'), ('52', '副主任', '19', '', '0'), ('53', '科员', '19', '', '0'), ('54', '副主任科员', '19', '', '0'), ('55', '主任', '20', '', '0'), ('56', '副主任', '20', '', '0'), ('57', '科员', '20', '', '0'), ('58', '副主任科员', '20', '', '0'), ('59', '主任', '21', '', '0'), ('60', '副主任', '21', '', '0'), ('61', '科员', '21', '', '0'), ('62', '主任科员', '21', '', '0'), ('63', '副主任科员', '21', '', '0'), ('64', '秘书长', '22', '', '0'), ('65', '副秘书长', '22', '', '0'), ('66', '科员', '22', '', '0'), ('67', '主任科员', '22', '', '0'), ('68', '副主任科员', '22', '', '0'), ('69', '秘书长', '23', '', '0'), ('70', '副秘书长', '23', '', '0'), ('71', '科员', '23', '', '0'), ('72', '主任科员', '23', '', '0'), ('73', '副主任科员', '23', '', '0'), ('74', '秘书长', '24', '', '0'), ('75', '副秘书长', '24', '', '0'), ('76', '科员', '24', '', '0'), ('77', '主任科员', '24', '', '0'), ('78', '副主任科员', '24', '', '0'), ('79', '科长', '17', '', '0'), ('80', '副科长', '17', '', '0'), ('81', '科员', '17', '', '0');
COMMIT;

-- ----------------------------
-- Table structure for `job_change`
-- ----------------------------
DROP TABLE IF EXISTS `job_change`;
CREATE TABLE `job_change` (
`changeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`profileId`  bigint(20) NOT NULL ,
`profileNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`userName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`orgJobId`  bigint(20) NOT NULL ,
`orgJobName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`newJobId`  bigint(20) NOT NULL ,
`newJobName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`orgStandardId`  bigint(20) NULL DEFAULT NULL COMMENT '原薪酬标准单ID' ,
`orgStandardNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`orgStandardName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`orgDepId`  bigint(20) NULL DEFAULT NULL ,
`orgDepName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`orgTotalMoney`  decimal(18,2) NULL DEFAULT NULL ,
`newStandardId`  bigint(20) NULL DEFAULT NULL ,
`newStandardNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`newStandardName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`newDepId`  bigint(20) NULL DEFAULT NULL ,
`newDepName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`newTotalMoney`  decimal(18,2) NULL DEFAULT NULL ,
`changeReason`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regTime`  datetime NULL DEFAULT NULL ,
`regName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`checkName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`checkTime`  datetime NULL DEFAULT NULL ,
`checkOpinion`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NULL DEFAULT NULL COMMENT '状态\r\n            \r\n            -1=草稿\r\n            0=提交审批\r\n            1=通过审批\r\n            2=未通过审批\r\n            ' ,
`memo`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`changeId`),
FOREIGN KEY (`newJobId`) REFERENCES `job` (`jobId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`orgJobId`) REFERENCES `job` (`jobId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of job_change
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `leader_read`
-- ----------------------------
DROP TABLE IF EXISTS `leader_read`;
CREATE TABLE `leader_read` (
`readId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`leaderName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`userId`  bigint(20) NOT NULL ,
`leaderOpinion`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createtime`  datetime NOT NULL ,
`archivesId`  bigint(20) NULL DEFAULT NULL ,
`depLevel`  int(11) NULL DEFAULT NULL ,
`depName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isPass`  smallint(6) NOT NULL COMMENT '0=尚未批\r\n            1=审批通过\r\n            2=审批未通过' ,
`checkName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`readId`),
FOREIGN KEY (`archivesId`) REFERENCES `archives` (`archivesId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='领导批示'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of leader_read
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `mail`
-- ----------------------------
DROP TABLE IF EXISTS `mail`;
CREATE TABLE `mail` (
`mailId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`sender`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`senderId`  bigint(20) NOT NULL ,
`importantFlag`  smallint(6) NOT NULL COMMENT '1=一般\r\n            2=重要\r\n            3=非常重要' ,
`sendTime`  datetime NOT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件内容' ,
`subject`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮件标题' ,
`copyToNames`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送人姓名列表' ,
`copyToIDs`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抄送人ID列表\r\n            用\',\'分开' ,
`recipientNames`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收件人姓名列表' ,
`recipientIDs`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '收件人ID列表\r\n            用\',\'分隔' ,
`mailStatus`  smallint(6) NOT NULL COMMENT '邮件状态\r\n            1=正式邮件\r\n            0=草稿邮件' ,
`fileIds`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件Ids，多个附件的ID，通过,分割' ,
`filenames`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附件名称列表，通过,进行分割' ,
PRIMARY KEY (`mailId`),
FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='邮件'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of mail
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `mail_attach`
-- ----------------------------
DROP TABLE IF EXISTS `mail_attach`;
CREATE TABLE `mail_attach` (
`mailId`  bigint(20) NOT NULL ,
`fileId`  bigint(20) NOT NULL ,
PRIMARY KEY (`mailId`, `fileId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of mail_attach
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `mail_box`
-- ----------------------------
DROP TABLE IF EXISTS `mail_box`;
CREATE TABLE `mail_box` (
`boxId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`mailId`  bigint(20) NOT NULL ,
`folderId`  bigint(20) NOT NULL ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`sendTime`  datetime NOT NULL ,
`delFlag`  smallint(6) NOT NULL COMMENT 'del=1则代表删除' ,
`readFlag`  smallint(6) NOT NULL ,
`replyFlag`  smallint(6) NOT NULL ,
`note`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'note' ,
PRIMARY KEY (`boxId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`folderId`) REFERENCES `mail_folder` (`folderId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`mailId`) REFERENCES `mail` (`mailId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='收件箱'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of mail_box
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `mail_folder`
-- ----------------------------
DROP TABLE IF EXISTS `mail_folder`;
CREATE TABLE `mail_folder` (
`folderId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件夹编号' ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`folderName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件夹名称' ,
`parentId`  bigint(20) NULL DEFAULT NULL COMMENT '父目录' ,
`depLevel`  int(11) NOT NULL COMMENT '目录层' ,
`path`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isPublic`  smallint(6) NOT NULL COMMENT '1=表示共享，则所有的员工均可以使用该文件夹\r\n            0=私人文件夹' ,
`folderType`  smallint(6) NOT NULL COMMENT '文件夹类型\r\n            1=收信箱\r\n            2=发信箱\r\n            3=草稿箱\r\n            4=删除箱\r\n            10=其他' ,
PRIMARY KEY (`folderId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of mail_folder
-- ----------------------------
BEGIN;
INSERT INTO mail_folder VALUES ('1', null, '收件箱', '0', '1', '0.1.', '1', '1'), ('2', null, '发件箱', '0', '1', '0.2.', '1', '2'), ('3', null, '草稿箱', '0', '1', '0.3.', '1', '3'), ('4', null, '垃圾箱', '0', '1', '0.4.', '1', '4');
COMMIT;

-- ----------------------------
-- Table structure for `meeting`
-- ----------------------------
DROP TABLE IF EXISTS `meeting`;
CREATE TABLE `meeting` (
`mettingId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`holdTime`  datetime NULL DEFAULT NULL ,
`holdLocation`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`meetingName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`attendUsers`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`holdDep`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`holdDepId`  bigint(20) NULL DEFAULT NULL ,
`shortDesc`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isFeedback`  smallint(6) NOT NULL ,
`summary`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`mettingId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of meeting
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `meeting_attend`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_attend`;
CREATE TABLE `meeting_attend` (
`attendId`  int(11) NOT NULL AUTO_INCREMENT ,
`mettingId`  bigint(20) NOT NULL ,
`userName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userId`  bigint(20) NULL DEFAULT NULL ,
`depName`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`depId`  bigint(20) NULL DEFAULT NULL ,
`attendType`  smallint(6) NOT NULL DEFAULT 0 COMMENT '参与类型\r\n            0=user\r\n            1=department' ,
`feedback`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`signTime`  datetime NULL DEFAULT NULL ,
`signName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`attendId`),
FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='会议参与部门或人员'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of meeting_attend
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `meeting_file`
-- ----------------------------
DROP TABLE IF EXISTS `meeting_file`;
CREATE TABLE `meeting_file` (
`mettingId`  bigint(20) NOT NULL ,
`fileId`  bigint(20) NOT NULL ,
PRIMARY KEY (`mettingId`, `fileId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`mettingId`) REFERENCES `meeting` (`mettingId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of meeting_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
`newsId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID' ,
`typeId`  bigint(20) NOT NULL ,
`subjectIcon`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`subject`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '新闻标题' ,
`author`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
`replyCounts`  int(11) NULL DEFAULT NULL ,
`viewCounts`  int(11) NULL DEFAULT NULL COMMENT '浏览数' ,
`issuer`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`content`  text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容' ,
`updateTime`  datetime NOT NULL ,
`status`  smallint(6) NOT NULL COMMENT '\r\n            0=待审核\r\n            1=审核通过' ,
`isDeskImage`  smallint(6) NULL DEFAULT NULL COMMENT '是否为图片新闻' ,
PRIMARY KEY (`newsId`),
FOREIGN KEY (`typeId`) REFERENCES `news_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='新闻'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of news
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `news_comment`
-- ----------------------------
DROP TABLE IF EXISTS `news_comment`;
CREATE TABLE `news_comment` (
`commentId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`newsId`  bigint(20) NOT NULL ,
`content`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`createtime`  datetime NOT NULL ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`userId`  bigint(20) NOT NULL ,
PRIMARY KEY (`commentId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`newsId`) REFERENCES `news` (`newsId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of news_comment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `news_type`
-- ----------------------------
DROP TABLE IF EXISTS `news_type`;
CREATE TABLE `news_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`sn`  int(11) NOT NULL ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='新闻类型'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of news_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `notice`
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
`noticeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`postName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`noticeTitle`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`noticeContent`  varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`effectiveDate`  date NULL DEFAULT NULL ,
`expirationDate`  date NULL DEFAULT NULL ,
`state`  smallint(6) NOT NULL ,
PRIMARY KEY (`noticeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='公告'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of notice
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `office_goods`
-- ----------------------------
DROP TABLE IF EXISTS `office_goods`;
CREATE TABLE `office_goods` (
`goodsId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeId`  bigint(20) NOT NULL COMMENT '所属分类' ,
`goodsName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品名称' ,
`goodsNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编号' ,
`specifications`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格' ,
`unit`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '计量单位' ,
`isWarning`  smallint(6) NOT NULL COMMENT '是否启用库存警示' ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`stockCounts`  int(11) NOT NULL COMMENT '库存总数' ,
`warnCounts`  int(11) NOT NULL COMMENT '最低库存数' ,
PRIMARY KEY (`goodsId`),
FOREIGN KEY (`typeId`) REFERENCES `office_goods_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='办公用品'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of office_goods
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `office_goods_type`
-- ----------------------------
DROP TABLE IF EXISTS `office_goods_type`;
CREATE TABLE `office_goods_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称' ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='办公用品类型'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of office_goods_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `phone_book`
-- ----------------------------
DROP TABLE IF EXISTS `phone_book`;
CREATE TABLE `phone_book` (
`phoneId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`fullname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`title`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '先生\r\n            女士\r\n            小姐' ,
`birthday`  datetime NULL DEFAULT NULL ,
`nickName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`duty`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`spouse`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`childs`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`companyName`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`companyAddress`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`companyPhone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`companyFax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`homeAddress`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`homeZip`  varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`QQ`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`MSN`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`note`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userId`  bigint(20) NOT NULL ,
`groupId`  bigint(20) NULL DEFAULT NULL ,
`isShared`  smallint(6) NOT NULL ,
PRIMARY KEY (`phoneId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`groupId`) REFERENCES `phone_group` (`groupId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='通讯簿'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of phone_book
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `phone_group`
-- ----------------------------
DROP TABLE IF EXISTS `phone_group`;
CREATE TABLE `phone_group` (
`groupId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`groupName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分组名称' ,
`isShared`  smallint(6) NOT NULL COMMENT '1=共享\r\n            0=私有' ,
`SN`  int(11) NOT NULL ,
`userId`  bigint(20) NOT NULL ,
PRIMARY KEY (`groupId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of phone_group
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `plan_attend`
-- ----------------------------
DROP TABLE IF EXISTS `plan_attend`;
CREATE TABLE `plan_attend` (
`attendId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`depId`  bigint(20) NULL DEFAULT NULL ,
`userId`  bigint(20) NULL DEFAULT NULL ,
`planId`  bigint(20) NOT NULL ,
`isDep`  smallint(6) NOT NULL COMMENT '是否为部门' ,
`isPrimary`  smallint(6) NULL DEFAULT NULL COMMENT '是否负责人' ,
PRIMARY KEY (`attendId`),
FOREIGN KEY (`depId`) REFERENCES `department` (`depId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of plan_attend
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `plan_file`
-- ----------------------------
DROP TABLE IF EXISTS `plan_file`;
CREATE TABLE `plan_file` (
`fileId`  bigint(20) NOT NULL ,
`planId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `planId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`planId`) REFERENCES `work_plan` (`planId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of plan_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `plan_type`
-- ----------------------------
DROP TABLE IF EXISTS `plan_type`;
CREATE TABLE `plan_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类别名称' ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='计划类型'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of plan_type
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `pro_definition`
-- ----------------------------
DROP TABLE IF EXISTS `pro_definition`;
CREATE TABLE `pro_definition` (
`defId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`typeId`  bigint(20) NULL DEFAULT NULL COMMENT '分类ID' ,
`name`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程的名称' ,
`description`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述' ,
`createtime`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`deployId`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Jbpm 工作流id' ,
`defXml`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程定义XML' ,
`drawDefXml`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isDefault`  smallint(6) NOT NULL DEFAULT 0 COMMENT '是否缺省\r\n            1=是\r\n            0=否' ,
PRIMARY KEY (`defId`),
FOREIGN KEY (`typeId`) REFERENCES `pro_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='流程定义'
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of pro_definition
-- ----------------------------
BEGIN;
INSERT INTO pro_definition VALUES ('4', '1', '发文流程', '发文流程', '2011-07-14 10:06:44', '4', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6426584402782899404\"><start name=\"开始\" g=\"251,4,58,58\"><transition name=\"流程启动\" to=\"发文核稿\" g=\"-24,-16\"/></start><task name=\"发文核稿\" g=\"238,105,89,36\"><transition name=\"核稿\" to=\"科室审核\" g=\"-24,-16\"/></task><task name=\"科室审核\" g=\"240,175,89,36\"><transition name=\"审核\" to=\"主管领导审批\" g=\"-24,-16\"/></task><task name=\"主管领导审批\" g=\"240,235,93,36\"><transition name=\"审批\" to=\"分管领导签发\" g=\"-24,-16\"/></task><task name=\"发文分发\" g=\"239,441,89,36\"><transition name=\"分发\" to=\"结束1\" g=\"-24,-16\"/></task><task name=\"发文校对\" g=\"241,368,89,36\"><transition name=\"校对\" to=\"发文分发\" g=\"-24,-16\"/></task><task name=\"分管领导签发\" g=\"239,303,96,36\"><transition name=\"签发\" to=\"发文校对\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"254,505,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"251\" y=\"4\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x53d1;&#x6587;&#x6838;&#x7a3f;\" x=\"238\" y=\"105\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x6838;&#x7a3f;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x79d1;&#x5ba4;&#x5ba1;&#x6838;\" x=\"240\" y=\"175\" w=\"79\" h=\"26\"><a><text><string>&#x79d1;&#x5ba4;&#x5ba1;&#x6838;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x5ba1;&#x6279;\" x=\"240\" y=\"235\" w=\"83\" h=\"26\"><a><text><string>&#x4e3b;&#x7ba1;&#x9886;&#x5bfc;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x53d1;&#x6587;&#x5206;&#x53d1;\" x=\"239\" y=\"441\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x5206;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"6\" name=\"&#x53d1;&#x6587;&#x6821;&#x5bf9;\" x=\"241\" y=\"368\" w=\"79\" h=\"26\"><a><text><string>&#x53d1;&#x6587;&#x6821;&#x5bf9;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"7\" name=\"&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;\" x=\"239\" y=\"303\" w=\"86\" h=\"26\"><a><text><string>&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x7b7e;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"8\" x=\"254\" y=\"505\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"9\" g=\"-24,-16\" name=\"&#x6d41;&#x7a0b;&#x542f;&#x52a8;\"><points><p colinear=\"true\" x=\"275.68333333333334\" y=\"52.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"277.1222222222222\" y=\"104.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"a\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"b\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"c\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"d\" g=\"-24,-16\" name=\"&#x6838;&#x7a3f;\"><points><p colinear=\"true\" x=\"277.8885714285714\" y=\"131.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"279.1114285714286\" y=\"174.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"e\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"10\" g=\"-24,-16\" name=\"&#x5ba1;&#x6838;\"><points><p colinear=\"true\" x=\"279.9533333333333\" y=\"201.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"281.0466666666667\" y=\"234.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"11\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"13\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"281.6\" y=\"261.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"281.9\" y=\"302.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"14\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"15\"><Owner><task ref=\"7\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"16\" g=\"-24,-16\" name=\"&#x7b7e;&#x53d1;\"><points><p colinear=\"true\" x=\"281.68615384615384\" y=\"329.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"280.81384615384616\" y=\"367.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"17\"><Owner><task ref=\"7\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"18\"><Owner><task ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"19\" g=\"-24,-16\" name=\"&#x6821;&#x5bf9;\"><points><p colinear=\"true\" x=\"280.127397260274\" y=\"394.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"278.87260273972606\" y=\"440.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"1a\"><Owner><task ref=\"6\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1b\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"1c\" g=\"-24,-16\" name=\"&#x5206;&#x53d1;\"><points><p colinear=\"true\" x=\"278.40933333333334\" y=\"467.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"278.164\" y=\"504.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"1d\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"1e\"><Owner><end ref=\"8\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"c\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('5', '1', '收文流程', '收文流程', '2011-07-14 10:15:22', '5', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd4993365972583614546\"><start name=\"开始\" g=\"261,14,58,58\"><transition name=\"提交拟办\" to=\"登记拟办\" g=\"-24,-16\"/></start><task name=\"登记拟办\" g=\"239,103,106,43\"><transition name=\"提交领导批示\" to=\"领导批示\" g=\"-24,-16\"/></task><task name=\"领导批示\" g=\"239,181,106,43\"><transition name=\"提交公文分发\" to=\"公文分发\" g=\"-24,-16\"/></task><task name=\"公文分发\" g=\"240,253,106,43\"><transition name=\"提交承办传阅\" to=\"承办传阅\" g=\"-24,-16\"/></task><task name=\"承办传阅\" g=\"242,339,106,43\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"266,427,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"261\" y=\"14\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x767b;&#x8bb0;&#x62df;&#x529e;\" x=\"239\" y=\"103\" w=\"96\" h=\"33\"><a><text><string>&#x767b;&#x8bb0;&#x62df;&#x529e;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x9886;&#x5bfc;&#x6279;&#x793a;\" x=\"239\" y=\"181\" w=\"96\" h=\"33\"><a><text><string>&#x9886;&#x5bfc;&#x6279;&#x793a;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x516c;&#x6587;&#x5206;&#x53d1;\" x=\"240\" y=\"253\" w=\"96\" h=\"33\"><a><text><string>&#x516c;&#x6587;&#x5206;&#x53d1;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x627f;&#x529e;&#x4f20;&#x9605;\" x=\"242\" y=\"339\" w=\"96\" h=\"33\"><a><text><string>&#x627f;&#x529e;&#x4f20;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"6\" x=\"266\" y=\"427\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"7\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x62df;&#x529e;\"><points><p colinear=\"true\" x=\"285.60368098159506\" y=\"62.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"286.5803680981595\" y=\"102.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"8\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"a\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"b\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x9886;&#x5bfc;&#x6279;&#x793a;\"><points><p colinear=\"true\" x=\"287\" y=\"136.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"287\" y=\"180.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"c\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"e\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x516c;&#x6587;&#x5206;&#x53d1;\"><points><p colinear=\"true\" x=\"287.2375\" y=\"214.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"287.7625\" y=\"252.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"10\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"11\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x627f;&#x529e;&#x4f20;&#x9605;\"><points><p colinear=\"true\" x=\"288.39767441860465\" y=\"286.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"289.60232558139535\" y=\"338.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"13\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"14\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"290\" y=\"372.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"290\" y=\"426.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('6', '3', '出差申请', '出差申请', '2011-07-14 12:41:12', '6', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6578157620277996804\"><start name=\"开始\" g=\"191,29,58,58\"><transition name=\"提交审批\" to=\"经理审批\" g=\"-24,-16\"/></start><task name=\"经理审批\" g=\"155,158,137,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"196,278,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"191\" y=\"29\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"155\" y=\"158\" w=\"127\" h=\"40\"><a><text><string>&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"3\" x=\"196\" y=\"278\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"4\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"215.68880000000001\" y=\"77.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"217.92319999999998\" y=\"157.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"218.7491935483871\" y=\"198.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"219.7024193548387\" y=\"277.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('7', '3', '网上报账流程', '网上报账流程', '2011-07-14 13:32:31', '7', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd8252642282251937144\"><start name=\"申请\" g=\"222,9,58,58\"><transition name=\"申请\" to=\"部门领导审阅\" g=\"-24,-16\"/></start><task name=\"部门领导审阅\" g=\"203,98,98,46\"><transition name=\"提交分管领导\" to=\"分管领导盖章\" g=\"-24,-16\"/></task><task name=\"分管领导盖章\" g=\"203,172,99,47\"><transition name=\"答应\" to=\"打印\" g=\"-24,-16\"/></task><task name=\"打印\" g=\"204,248,98,45\"><transition name=\"财务处\" to=\"财务处\" g=\"-24,-16\"/></task><task name=\"财务处\" g=\"203,327,105,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"226,416,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"222\" y=\"9\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x90e8;&#x95e8;&#x9886;&#x5bfc;&#x5ba1;&#x9605;\" x=\"203\" y=\"98\" w=\"88\" h=\"36\"><a><text><string>&#x90e8;&#x95e8;&#x9886;&#x5bfc;&#x5ba1;&#x9605;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x76d6;&#x7ae0;\" x=\"203\" y=\"172\" w=\"89\" h=\"37\"><a><text><string>&#x5206;&#x7ba1;&#x9886;&#x5bfc;&#x76d6;&#x7ae0;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"4\" name=\"&#x6253;&#x5370;\" x=\"204\" y=\"248\" w=\"88\" h=\"35\"><a><text><string>&#x6253;&#x5370;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"5\" name=\"&#x8d22;&#x52a1;&#x5904;\" x=\"203\" y=\"327\" w=\"95\" h=\"40\"><a><text><string>&#x8d22;&#x52a1;&#x5904;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"6\" x=\"226\" y=\"416\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"7\" g=\"-24,-16\" name=\"&#x7533;&#x8bf7;\"><points><p colinear=\"true\" x=\"246.29638554216868\" y=\"57.6\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"246.77590361445783\" y=\"97.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"8\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"a\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"b\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;&#x5206;&#x7ba1;&#x9886;&#x5bfc;\"><points><p colinear=\"true\" x=\"247.12483221476512\" y=\"134.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"247.37181208053693\" y=\"171.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"c\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"e\" g=\"-24,-16\" name=\"&#x7b54;&#x5e94;\"><points><p colinear=\"true\" x=\"247.62733333333333\" y=\"209.60000000000002\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"247.87933333333334\" y=\"247.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"f\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"10\"><Owner><task ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"11\" g=\"-24,-16\" name=\"&#x8d22;&#x52a1;&#x5904;\"><points><p colinear=\"true\" x=\"248.55521472392638\" y=\"283.6\" c1x=\"-0.8680981595092021\" c1y=\"-0.39999999999997726\" c2x=\"-0.8680981595092021\" c2y=\"-0.39999999999997726\"/><p colinear=\"true\" x=\"249.8680981595092\" y=\"326.4\" c1x=\"-0.8680981595092021\" c1y=\"-0.39999999999997726\" c2x=\"-0.8680981595092021\" c2y=\"-0.39999999999997726\"/></points><startConnector><rConnector id=\"12\"><Owner><task ref=\"4\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"13\"><Owner><task ref=\"5\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"14\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"250.38924731182794\" y=\"367.59999999999997\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/><p colinear=\"true\" x=\"250.13225806451612\" y=\"415.4\" c1x=\"0\" c1y=\"0\" c2x=\"0\" c2y=\"0\"/></points><startConnector><rConnector id=\"15\"><Owner><task ref=\"5\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"16\"><Owner><end ref=\"6\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"a\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('8', '3', '请假', '请假', '2011-07-14 13:48:06', '8', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd6212082814169152003\"><start name=\"开始\" g=\"158,59,58,58\"><transition name=\"开始\" to=\"上级审批\" g=\"-24,-16\"/></start><task name=\"上级审批\" g=\"141,169,97,50\"><transition name=\"审批\" to=\"结束1\" g=\"-24,-16\"/></task><end name=\"结束1\" g=\"161,277,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"158\" y=\"59\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x4e0a;&#x7ea7;&#x5ba1;&#x6279;\" x=\"141\" y=\"169\" w=\"87\" h=\"40\"><a><text><string>&#x4e0a;&#x7ea7;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"3\" x=\"161\" y=\"277\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"4\" g=\"-24,-16\" name=\"&#x5f00;&#x59cb;\"><points><p colinear=\"true\" x=\"182.58018867924528\" y=\"107.6\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/><p colinear=\"true\" x=\"184.01415094339623\" y=\"168.4\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"184.5919642857143\" y=\"209.60000000000002\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/><p colinear=\"true\" x=\"184.89017857142858\" y=\"276.4\" c1x=\"-73\" c1y=\"15\" c2x=\"-73\" c2y=\"15\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('9', '3', '通用', '通用', '2011-07-14 14:08:48', '9', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd8498713057619136655\"><start name=\"开始\" g=\"198,62,58,58\"><transition name=\"申请\" to=\"表单\" g=\"-24,-16\"/></start><end name=\"结束1\" g=\"204,298,58,58\"/><task name=\"表单\" g=\"182,180,97,50\"><transition name=\"结束\" to=\"结束1\" g=\"-24,-16\"/></task></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"198\" y=\"62\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><end id=\"2\" x=\"204\" y=\"298\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;1\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><task id=\"3\" name=\"&#x8868;&#x5355;\" x=\"182\" y=\"180\" w=\"87\" h=\"40\"><a><text><string>&#x8868;&#x5355;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><transition id=\"4\" g=\"-24,-16\" name=\"&#x7533;&#x8bf7;\"><points><p colinear=\"true\" x=\"222.75526315789475\" y=\"110.6\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/><p colinear=\"true\" x=\"224.86754385964912\" y=\"179.4\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/></points><startConnector><rConnector id=\"5\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"6\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"7\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"8\" g=\"-24,-16\" name=\"&#x7ed3;&#x675f;\"><points><p colinear=\"true\" x=\"225.922131147541\" y=\"220.60000000000002\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/><p colinear=\"true\" x=\"227.49590163934425\" y=\"297.4\" c1x=\"-70\" c1y=\"24\" c2x=\"-70\" c2y=\"24\"/></points><startConnector><rConnector id=\"9\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"a\"><Owner><end ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"7\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0'), ('10', '2', '购车请款', '购车请款', '2011-07-14 14:18:50', '10', '<?xml version=\"1.0\" encoding=\"GBK\"?>\n<process xmlns=\"http://jbpm.org/4.0/jpdl\" name=\"pd7096695693128483739\"><start name=\"输入购车预算金额\" g=\"169,45,58,58\"><transition name=\"提交\" to=\"副经理审批\" g=\"-24,-16\"/></start><task name=\"副经理审批\" g=\"132,137,135,50\"><transition name=\"审批\" to=\"总经理审批\" g=\"-24,-16\"/></task><task name=\"总经理审批\" g=\"132,227,139,50\"><transition name=\"审批\" to=\"结束2\" g=\"-24,-16\"/></task><end name=\"结束2\" g=\"176,329,58,58\"/></process>', '<drawing id=\"0\"><figures><start id=\"1\" x=\"169\" y=\"45\" w=\"48\" h=\"48\" name=\"&#x5f00;&#x59cb;\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></start><task id=\"2\" name=\"&#x526f;&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"132\" y=\"137\" w=\"125\" h=\"40\"><a><text><string>&#x526f;&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><task id=\"3\" name=\"&#x603b;&#x7ecf;&#x7406;&#x5ba1;&#x6279;\" x=\"132\" y=\"227\" w=\"129\" h=\"40\"><a><text><string>&#x603b;&#x7ecf;&#x7406;&#x5ba1;&#x6279;</string></text><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></task><end id=\"4\" x=\"176\" y=\"329\" w=\"48\" h=\"48\" name=\"&#x7ed3;&#x675f;2\"><a><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></end><transition id=\"5\" g=\"-24,-16\" name=\"&#x63d0;&#x4ea4;\"><points><p colinear=\"true\" x=\"193.4193181818182\" y=\"93.6\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"194.14886363636364\" y=\"136.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"6\"><Owner><start ref=\"1\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"7\"><Owner><task ref=\"2\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip id=\"8\" angle=\"0.35\" innerRadius=\"11.3\" outerRadius=\"12\" isFilled=\"true\" isStroked=\"false\" isSolid=\"true\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"9\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"194.95777777777778\" y=\"177.60000000000002\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"196.04222222222222\" y=\"226.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"a\"><Owner><task ref=\"2\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"b\"><Owner><task ref=\"3\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition><transition id=\"c\" g=\"-24,-16\" name=\"&#x5ba1;&#x6279;\"><points><p colinear=\"true\" x=\"197.1801886792453\" y=\"267.6\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/><p colinear=\"true\" x=\"199.1877358490566\" y=\"328.4\" c1x=\"-96\" c1y=\"20\" c2x=\"-96\" c2y=\"20\"/></points><startConnector><rConnector id=\"d\"><Owner><task ref=\"3\"/></Owner></rConnector></startConnector><endConnector><rConnector id=\"e\"><Owner><end ref=\"4\"/></Owner></rConnector></endConnector><a><endDecoration><arrowTip ref=\"8\"/></endDecoration><strokeColor><color rgba=\"#ff000000\"/></strokeColor></a></transition></figures></drawing>', '0');
COMMIT;

-- ----------------------------
-- Table structure for `pro_type`
-- ----------------------------
DROP TABLE IF EXISTS `pro_type`;
CREATE TABLE `pro_type` (
`typeId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类别ID' ,
`typeName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称' ,
PRIMARY KEY (`typeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='流程分类'
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of pro_type
-- ----------------------------
BEGIN;
INSERT INTO pro_type VALUES ('1', '公文'), ('2', '行政'), ('3', '日常');
COMMIT;

-- ----------------------------
-- Table structure for `pro_user_assign`
-- ----------------------------
DROP TABLE IF EXISTS `pro_user_assign`;
CREATE TABLE `pro_user_assign` (
`assignId`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '授权ID' ,
`deployId`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'jbpm流程定义的id' ,
`activityName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '流程节点名称' ,
`roleId`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色Id' ,
`roleName`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userId`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户ID' ,
`username`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isSigned`  smallint(6) NULL DEFAULT 0 COMMENT '1=是会签任务\r\n            0=非会签任务\r\n            \r\n            若为会签任务，则需要为该会签添加会签的决策方式的设置\r\n            ' ,
PRIMARY KEY (`assignId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='流程过程中各个任务节点及启动流程时的角色'
AUTO_INCREMENT=11

;

-- ----------------------------
-- Records of pro_user_assign
-- ----------------------------
BEGIN;
INSERT INTO pro_user_assign VALUES ('1', '4', '发文核稿', '', '', '1', '超级管理员', null), ('2', '4', '科室审核', '', '', '__super', '[上级]', null), ('3', '4', '主管领导审批', '8', '副局长', '', '', null), ('4', '4', '发文分发', '', '', '1', '超级管理员', null), ('5', '4', '发文校对', '', '', '1', '超级管理员', null), ('6', '4', '分管领导签发', '7', '局长', '', '', null), ('7', '5', '登记拟办', '', '', '1', '超级管理员', null), ('8', '5', '领导批示', '', '', '1', '超级管理员', null), ('9', '5', '公文分发', '', '', '1', '超级管理员', null), ('10', '5', '承办传阅', '', '', '1', '超级管理员', null);
COMMIT;

-- ----------------------------
-- Table structure for `process_form`
-- ----------------------------
DROP TABLE IF EXISTS `process_form`;
CREATE TABLE `process_form` (
`formId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`runId`  bigint(20) NOT NULL COMMENT '所属运行流程' ,
`activityName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动或任务名称' ,
`sn`  int(11) NOT NULL DEFAULT 1 COMMENT '表单序号代表该流程任务执行经过的次数,如第一次经过时为1,第二次再次经过时变为2,\r\n            主要用于标识某一任务在流程中可能被不断回退.' ,
`createtime`  datetime NOT NULL ,
`creatorId`  bigint(20) NOT NULL ,
`creatorName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
PRIMARY KEY (`formId`),
FOREIGN KEY (`runId`) REFERENCES `process_run` (`runId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='流程表单\r\n存储保存在运行中的流程表单数据'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of process_form
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `process_run`
-- ----------------------------
DROP TABLE IF EXISTS `process_run`;
CREATE TABLE `process_run` (
`runId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`subject`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题\r\n            一般为流程名称＋格式化的时间' ,
`creator`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建人' ,
`userId`  bigint(20) NOT NULL COMMENT '所属用户' ,
`defId`  bigint(20) NOT NULL COMMENT '所属流程定义' ,
`piId`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '流程实例ID' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
`runStatus`  smallint(6) NOT NULL COMMENT '0=尚未启动\r\n            1=已经启动流程\r\n            2=运行结束' ,
PRIMARY KEY (`runId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`defId`) REFERENCES `pro_definition` (`defId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='运行中的流程'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of process_run
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
`productId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`productName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '产品名称' ,
`productModel`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品型号' ,
`unit`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计量单位' ,
`costPrice`  decimal(18,2) NULL DEFAULT NULL COMMENT '成本价' ,
`salesPrice`  decimal(18,2) NULL DEFAULT NULL COMMENT '出售价' ,
`productDesc`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品描述' ,
`providerId`  bigint(20) NOT NULL COMMENT '所属供应商' ,
`createtime`  datetime NOT NULL COMMENT '收录时间' ,
`updatetime`  datetime NOT NULL ,
PRIMARY KEY (`productId`),
FOREIGN KEY (`providerId`) REFERENCES `provider` (`providerId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='供应商产品'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `project`
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
`projectId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`projectName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目名称' ,
`projectNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目编号' ,
`reqDesc`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需求描述' ,
`isContract`  smallint(6) NOT NULL COMMENT '是否签订合同' ,
`fullname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人姓名' ,
`mobile`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机' ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话' ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真' ,
`otherContacts`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '其他联系方式' ,
`customerId`  bigint(20) NOT NULL COMMENT '所属客户' ,
`userId`  bigint(20) NOT NULL COMMENT '业务人员' ,
PRIMARY KEY (`projectId`),
FOREIGN KEY (`customerId`) REFERENCES `customer` (`customerId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='项目信息'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of project
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `project_file`
-- ----------------------------
DROP TABLE IF EXISTS `project_file`;
CREATE TABLE `project_file` (
`fileId`  bigint(20) NOT NULL ,
`projectId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `projectId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='项目附件'

;

-- ----------------------------
-- Records of project_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `provider`
-- ----------------------------
DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
`providerId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`providerName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商名称' ,
`contactor`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系人' ,
`phone`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '电话' ,
`fax`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '传真' ,
`site`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网址' ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮件' ,
`address`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址' ,
`zip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮编' ,
`openBank`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开户行' ,
`account`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '帐号' ,
`notes`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`rank`  int(11) NULL DEFAULT NULL COMMENT '供应商等级\r\n            1=一级供应商\r\n            2＝二级供应商\r\n            3＝三级供应商\r\n            4＝四级供应商\r\n            ' ,
PRIMARY KEY (`providerId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='供应商'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of provider
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
`regionId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`regionName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地区名称' ,
`regionType`  smallint(6) NOT NULL COMMENT '地区类型\r\n            1=省份\r\n            2=市' ,
`parentId`  bigint(20) NULL DEFAULT NULL COMMENT '上级地区' ,
PRIMARY KEY (`regionId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='地区管理'
AUTO_INCREMENT=393

;

-- ----------------------------
-- Records of region
-- ----------------------------
BEGIN;
INSERT INTO region VALUES ('1', '北京', '2', '0'), ('2', '上海', '2', '0'), ('3', '天津', '2', '0'), ('4', '重庆', '2', '0'), ('5', '河北', '1', '0'), ('6', '山西', '1', '0'), ('7', '内蒙古', '1', '0'), ('8', '辽宁', '1', '0'), ('9', '吉林', '1', '0'), ('10', '黑龙江', '1', '0'), ('11', '江苏', '1', '0'), ('12', '浙江', '1', '0'), ('13', '安徽', '1', '0'), ('14', '福建', '1', '0'), ('15', '江西', '1', '0'), ('16', '山东', '1', '0'), ('17', '河南', '1', '0'), ('18', '湖北', '1', '0'), ('19', '湖南', '1', '0'), ('20', '广东', '1', '0'), ('21', '广西', '1', '0'), ('22', '海南', '1', '0'), ('23', '四川', '1', '0'), ('24', '贵州', '1', '0'), ('25', '云南', '1', '0'), ('26', '西藏', '1', '0'), ('27', '陕西', '1', '0'), ('28', '甘肃', '1', '0'), ('29', '青海', '1', '0'), ('30', '宁夏', '1', '0'), ('31', '新疆', '1', '0'), ('32', '台湾', '1', '0'), ('33', '港澳', '1', '0'), ('34', '石家庄', '2', '5'), ('35', '唐山', '2', '5'), ('36', '秦皇岛', '2', '5'), ('37', '邯郸', '2', '5'), ('38', '邢台', '2', '5'), ('39', '保定', '2', '5'), ('40', '张家口', '2', '5'), ('41', '承德', '2', '5'), ('42', '沧州', '2', '5'), ('43', '廊坊', '2', '5'), ('44', '衡水', '2', '5'), ('45', '太原', '2', '6'), ('46', '大同', '2', '6'), ('47', '阳泉', '2', '6'), ('48', '长治', '2', '6'), ('49', '晋城', '2', '6'), ('50', '朔州', '2', '6'), ('51', '晋中', '2', '6'), ('52', '运城', '2', '6'), ('53', '忻州', '2', '6'), ('54', '临汾', '2', '6'), ('55', '吕梁', '2', '6'), ('56', '呼和浩特', '2', '7'), ('57', '包头', '2', '7'), ('58', '乌海', '2', '7'), ('59', '赤峰', '2', '7'), ('60', '通辽', '2', '7'), ('61', '鄂尔多斯', '2', '7'), ('62', '呼伦贝尔', '2', '7'), ('63', '巴彦淖尔', '2', '7'), ('64', '乌兰察布', '2', '7'), ('65', '兴安', '2', '7'), ('66', '锡林郭勒', '2', '7'), ('67', '阿拉善', '2', '7'), ('68', '沈阳', '2', '8'), ('69', '大连', '2', '8'), ('70', '鞍山', '2', '8'), ('71', '抚顺', '2', '8'), ('72', '本溪', '2', '8'), ('73', '丹东', '2', '8'), ('74', '锦州', '2', '8'), ('75', '营口', '2', '8'), ('76', '阜新', '2', '8'), ('77', '辽阳', '2', '8'), ('78', '盘锦', '2', '8'), ('79', '铁岭', '2', '8'), ('80', '朝阳', '2', '8'), ('81', '葫芦岛', '2', '8'), ('82', '长春', '2', '9'), ('83', '吉林', '2', '9'), ('84', '四平', '2', '9'), ('85', '辽源', '2', '9'), ('86', '通化', '2', '9'), ('87', '白山', '2', '9'), ('88', '松原', '2', '9'), ('89', '白城', '2', '9'), ('90', '延边', '2', '9'), ('91', '哈尔滨', '2', '10'), ('92', '齐齐哈尔', '2', '10'), ('93', '鸡西', '2', '10'), ('94', '鹤岗', '2', '10'), ('95', '双鸭山', '2', '10'), ('96', '大庆', '2', '10'), ('97', '伊春', '2', '10'), ('98', '佳木斯', '2', '10'), ('99', '七台河', '2', '10'), ('100', '牡丹江', '2', '10'), ('101', '黑河', '2', '10');
INSERT INTO region VALUES ('102', '绥化', '2', '10'), ('103', '大兴安岭', '2', '10'), ('104', '南京', '2', '11'), ('105', '无锡', '2', '11'), ('106', '徐州', '2', '11'), ('107', '常州', '2', '11'), ('108', '苏州', '2', '11'), ('109', '南通', '2', '11'), ('110', '连云港', '2', '11'), ('111', '淮安', '2', '11'), ('112', '盐城', '2', '11'), ('113', '扬州', '2', '11'), ('114', '镇江', '2', '11'), ('115', '泰州', '2', '11'), ('116', '宿迁', '2', '11'), ('117', '杭州', '2', '12'), ('118', '宁波', '2', '12'), ('119', '温州', '2', '12'), ('120', '嘉兴', '2', '12'), ('121', '湖州', '2', '12'), ('122', '绍兴', '2', '12'), ('123', '金华', '2', '12'), ('124', '衢州', '2', '12'), ('125', '舟山', '2', '12'), ('126', '台州', '2', '12'), ('127', '丽水', '2', '12'), ('128', '合肥', '2', '13'), ('129', '芜湖', '2', '13'), ('130', '蚌埠', '2', '13'), ('131', '淮南', '2', '13'), ('132', '马鞍山', '2', '13'), ('133', '淮北', '2', '13'), ('134', '铜陵', '2', '13'), ('135', '安庆', '2', '13'), ('136', '黄山', '2', '13'), ('137', '滁州', '2', '13'), ('138', '阜阳', '2', '13'), ('139', '宿州', '2', '13'), ('140', '巢湖', '2', '13'), ('141', '六安', '2', '13'), ('142', '亳州', '2', '13'), ('143', '池州', '2', '13'), ('144', '宣城', '2', '13'), ('145', '福州', '2', '14'), ('146', '厦门', '2', '14'), ('147', '莆田', '2', '14'), ('148', '三明', '2', '14'), ('149', '泉州', '2', '14'), ('150', '漳州', '2', '14'), ('151', '南平', '2', '14'), ('152', '龙岩', '2', '14'), ('153', '宁德', '2', '14'), ('154', '南昌', '2', '15'), ('155', '景德镇', '2', '15'), ('156', '萍乡', '2', '15'), ('157', '九江', '2', '15'), ('158', '新余', '2', '15'), ('159', '鹰潭', '2', '15'), ('160', '赣州', '2', '15'), ('161', '吉安', '2', '15'), ('162', '宜春', '2', '15'), ('163', '抚州', '2', '15'), ('164', '上饶', '2', '15'), ('165', '济南', '2', '16'), ('166', '青岛', '2', '16'), ('167', '淄博', '2', '16'), ('168', '枣庄', '2', '16'), ('169', '东营', '2', '16'), ('170', '烟台', '2', '16'), ('171', '潍坊', '2', '16'), ('172', '济宁', '2', '16'), ('173', '泰安', '2', '16'), ('174', '日照', '2', '16'), ('175', '莱芜', '2', '16'), ('176', '临沂', '2', '16'), ('177', '德州', '2', '16'), ('178', '聊城', '2', '16'), ('179', '滨州', '2', '16'), ('180', '菏泽', '2', '16'), ('181', '郑州', '2', '17'), ('182', '开封', '2', '17'), ('183', '洛阳', '2', '17'), ('184', '平顶山', '2', '17'), ('185', '焦作', '2', '17'), ('186', '鹤壁', '2', '17'), ('187', '新乡', '2', '17'), ('188', '安阳', '2', '17'), ('189', '濮阳', '2', '17'), ('190', '许昌', '2', '17'), ('191', '渭河', '2', '17'), ('192', '三门峡', '2', '17'), ('193', '南阳', '2', '17'), ('194', '商丘', '2', '17'), ('195', '信阳', '2', '17'), ('196', '周口', '2', '17'), ('197', '驻马店', '2', '17'), ('198', '武汉', '2', '18'), ('199', '黄石', '2', '18'), ('200', '襄樊', '2', '18'), ('201', '十堰', '2', '18'), ('202', '荆州', '2', '18');
INSERT INTO region VALUES ('203', '宜昌', '2', '18'), ('204', '荆门', '2', '18'), ('205', '鄂州', '2', '18'), ('206', '孝感', '2', '18'), ('207', '黄冈', '2', '18'), ('208', '咸宁', '2', '18'), ('209', '随州', '2', '18'), ('210', '恩施', '2', '18'), ('211', '长沙', '2', '19'), ('212', '株洲', '2', '19'), ('213', '湘潭', '2', '19'), ('214', '衡阳', '2', '19'), ('215', '邵阳', '2', '19'), ('216', '岳阳', '2', '19'), ('217', '常德', '2', '19'), ('218', '张家界', '2', '19'), ('219', '溢阳', '2', '19'), ('220', '郴州', '2', '19'), ('221', '永州', '2', '19'), ('222', '怀化', '2', '19'), ('223', '娄底', '2', '19'), ('224', '湘西', '2', '19'), ('225', '广州', '2', '20'), ('226', '深圳', '2', '20'), ('227', '珠海', '2', '20'), ('228', '汕头', '2', '20'), ('229', '韶关', '2', '20'), ('230', '佛山', '2', '20'), ('231', '江门', '2', '20'), ('232', '湛江', '2', '20'), ('233', '茂名', '2', '20'), ('234', '肇庆', '2', '20'), ('235', '惠州', '2', '20'), ('236', '梅州', '2', '20'), ('237', '汕尾', '2', '20'), ('238', '河源', '2', '20'), ('239', '阳江', '2', '20'), ('240', '清远', '2', '20'), ('241', '东莞', '2', '20'), ('242', '中山', '2', '20'), ('243', '潮州', '2', '20'), ('244', '揭阳', '2', '20'), ('245', '云浮', '2', '20'), ('246', '南宁', '2', '21'), ('247', '柳州', '2', '21'), ('248', '桂林', '2', '21'), ('249', '梧州', '2', '21'), ('250', '北海', '2', '21'), ('251', '防城港', '2', '21'), ('252', '钦州', '2', '21'), ('253', '贵港', '2', '21'), ('254', '玉林', '2', '21'), ('255', '百色', '2', '21'), ('256', '贺州', '2', '21'), ('257', '河池', '2', '21'), ('258', '来宾', '2', '21'), ('259', '崇左', '2', '21'), ('260', '白沙黎族自治县', '2', '22'), ('261', '西沙群岛', '2', '22'), ('262', '儋州', '2', '22'), ('263', '屯昌县', '2', '22'), ('264', '安定县', '2', '22'), ('265', '琼中黎族苗族自治县', '2', '22'), ('266', '昌江黎族自治县', '2', '22'), ('267', '东方', '2', '22'), ('268', '三亚', '2', '22'), ('269', '中沙群岛的岛礁及其海域', '2', '22'), ('270', '琼海', '2', '22'), ('271', '澄迈县', '2', '22'), ('272', '五指山', '2', '22'), ('273', '海口', '2', '22'), ('274', '文昌', '2', '22'), ('275', '陵水黎族自治县', '2', '22'), ('276', '保亭黎族苗族自治县', '2', '22'), ('277', '南沙群岛', '2', '22'), ('278', '乐东黎族自治县', '2', '22'), ('279', '临高县', '2', '22'), ('280', '万宁', '2', '22'), ('281', '成都', '2', '23'), ('282', '自贡', '2', '23'), ('283', '攀枝花', '2', '23'), ('284', '泸州', '2', '23'), ('285', '德阳', '2', '23'), ('286', '绵阳', '2', '23'), ('287', '广元', '2', '23'), ('288', '遂宁', '2', '23'), ('289', '内江', '2', '23'), ('290', '乐山', '2', '23'), ('291', '南充', '2', '23'), ('292', '宜宾', '2', '23'), ('293', '广安', '2', '23'), ('294', '达州', '2', '23'), ('295', '眉山', '2', '23'), ('296', '雅安', '2', '23'), ('297', '巴中', '2', '23'), ('298', '资阳', '2', '23'), ('299', '阿坝', '2', '23'), ('300', '甘孜', '2', '23'), ('301', '凉山', '2', '23'), ('302', '贵阳', '2', '24'), ('303', '六盘水', '2', '24');
INSERT INTO region VALUES ('304', '遵义', '2', '24'), ('305', '安顺', '2', '24'), ('306', '铜仁', '2', '24'), ('307', '毕节', '2', '24'), ('308', '黔西南', '2', '24'), ('309', '黔东南', '2', '24'), ('310', '黔南', '2', '24'), ('311', '昆明', '2', '25'), ('312', '曲靖', '2', '25'), ('313', '玉溪', '2', '25'), ('314', '保山', '2', '25'), ('315', '昭通', '2', '25'), ('316', '丽江', '2', '25'), ('317', '普洱', '2', '25'), ('318', '临沧', '2', '25'), ('319', '文山', '2', '25'), ('320', '红河', '2', '25'), ('321', '西双版纳', '2', '25'), ('322', '楚雄', '2', '25'), ('323', '大理', '2', '25'), ('324', '德宏', '2', '25'), ('325', '怒江', '2', '25'), ('326', '迪庆', '2', '25'), ('327', '拉萨', '2', '26'), ('328', '昌都', '2', '26'), ('329', '山南', '2', '26'), ('330', '日喀则', '2', '26'), ('331', '那曲', '2', '26'), ('332', '阿里', '2', '26'), ('333', '林芝', '2', '26'), ('334', '西安', '2', '27'), ('335', '铜川', '2', '27'), ('336', '宝鸡', '2', '27'), ('337', '咸阳', '2', '27'), ('338', '渭南', '2', '27'), ('339', '延安', '2', '27'), ('340', '汉中', '2', '27'), ('341', '榆林', '2', '27'), ('342', '安康', '2', '27'), ('343', '商洛', '2', '27'), ('344', '兰州', '2', '28'), ('345', '嘉峪关', '2', '28'), ('346', '金昌', '2', '28'), ('347', '白银', '2', '28'), ('348', '天水', '2', '28'), ('349', '武威', '2', '28'), ('350', '张掖', '2', '28'), ('351', '平凉', '2', '28'), ('352', '酒泉', '2', '28'), ('353', '庆阳', '2', '28'), ('354', '定西', '2', '28'), ('355', '陇南', '2', '28'), ('356', '临夏', '2', '28'), ('357', '甘南', '2', '28'), ('358', '西宁', '2', '29'), ('359', '海东', '2', '29'), ('360', '海北', '2', '29'), ('361', '黄南', '2', '29'), ('362', '海南', '2', '29'), ('363', '果洛', '2', '29'), ('364', '玉树', '2', '29'), ('365', '海西', '2', '29'), ('366', '银川', '2', '30'), ('367', '石嘴山', '2', '30'), ('368', '吴忠', '2', '30'), ('369', '固原', '2', '30'), ('370', '中卫', '2', '30'), ('371', '乌鲁木齐', '2', '31'), ('372', '克拉玛依', '2', '31'), ('373', '吐鲁番', '2', '31'), ('374', '哈密', '2', '31'), ('375', '和田', '2', '31'), ('376', '阿克苏', '2', '31'), ('377', '喀什', '2', '31'), ('378', '克孜勒苏柯尔克孜', '2', '31'), ('379', '巴音郭楞蒙古', '2', '31'), ('380', '昌吉', '2', '31'), ('381', '博尔塔拉蒙古', '2', '31'), ('382', '伊犁哈萨克', '2', '31'), ('383', '塔城', '2', '31'), ('384', '阿勒泰', '2', '31'), ('385', '台北', '2', '32'), ('386', '高雄', '2', '32'), ('387', '基隆', '2', '32'), ('388', '台中', '2', '32'), ('389', '台南', '2', '32'), ('390', '新竹', '2', '32'), ('391', '香港', '2', '33'), ('392', '澳门', '2', '33');
COMMIT;

-- ----------------------------
-- Table structure for `report_param`
-- ----------------------------
DROP TABLE IF EXISTS `report_param`;
CREATE TABLE `report_param` (
`paramId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`reportId`  bigint(20) NOT NULL COMMENT '所属报表' ,
`paramName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数名称' ,
`paramKey`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '参数Key' ,
`defaultVal`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '缺省值' ,
`paramType`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型\r\n            字符类型--varchar\r\n            整型--int\r\n            精度型--decimal\r\n            日期型--date\r\n            日期时间型--datetime\r\n            ' ,
`sn`  int(11) NOT NULL COMMENT '系列号' ,
PRIMARY KEY (`paramId`),
FOREIGN KEY (`reportId`) REFERENCES `report_template` (`reportId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='报表参数'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of report_param
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `report_template`
-- ----------------------------
DROP TABLE IF EXISTS `report_template`;
CREATE TABLE `report_template` (
`reportId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`title`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题' ,
`descp`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述' ,
`reportLocation`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报表模块的jasper文件的路径' ,
`createtime`  datetime NOT NULL COMMENT '创建时间' ,
`updatetime`  datetime NOT NULL COMMENT '修改时间' ,
PRIMARY KEY (`reportId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='报表模板\r\nreport_template'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of report_template
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `resume`
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume` (
`resumeId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`fullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`age`  int(11) NULL DEFAULT NULL ,
`birthday`  datetime NULL DEFAULT NULL ,
`address`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`zip`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sex`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`position`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`phone`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`mobile`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`hobby`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`religion`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`party`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`nationality`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`race`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`birthPlace`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`eduCollege`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`eduDegree`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`eduMajor`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`startWorkDate`  datetime NULL DEFAULT NULL ,
`idNo`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`photo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态\r\n            \r\n            通过\r\n            未通过\r\n            准备安排面试\r\n            面试通过\r\n            \r\n            ' ,
`memo`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`registor`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`regTime`  datetime NULL DEFAULT NULL ,
`workCase`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`trainCase`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`projectCase`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`resumeId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='简历管理'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of resume
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `resume_file`
-- ----------------------------
DROP TABLE IF EXISTS `resume_file`;
CREATE TABLE `resume_file` (
`fileId`  bigint(20) NOT NULL ,
`resumeId`  bigint(20) NOT NULL ,
PRIMARY KEY (`fileId`, `resumeId`),
FOREIGN KEY (`fileId`) REFERENCES `file_attach` (`fileId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`resumeId`) REFERENCES `resume` (`resumeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of resume_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `role_fun`
-- ----------------------------
DROP TABLE IF EXISTS `role_fun`;
CREATE TABLE `role_fun` (
`roleId`  bigint(20) NOT NULL ,
`functionId`  bigint(20) NOT NULL ,
PRIMARY KEY (`roleId`, `functionId`),
FOREIGN KEY (`functionId`) REFERENCES `app_function` (`functionId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of role_fun
-- ----------------------------
BEGIN;
INSERT INTO role_fun VALUES ('1', '1'), ('6', '1'), ('1', '2'), ('6', '2'), ('1', '3'), ('6', '3'), ('1', '4'), ('6', '4'), ('6', '5'), ('6', '6'), ('6', '7'), ('6', '8'), ('6', '9'), ('1', '10'), ('6', '10'), ('1', '11'), ('6', '11'), ('1', '12'), ('6', '12'), ('1', '13'), ('6', '13'), ('6', '14'), ('6', '15'), ('6', '16'), ('6', '17'), ('6', '18'), ('6', '19'), ('6', '20'), ('6', '21'), ('6', '22'), ('6', '23'), ('7', '23'), ('8', '23'), ('6', '24'), ('3', '25'), ('6', '25'), ('3', '26'), ('6', '26'), ('3', '27'), ('6', '27'), ('3', '28'), ('6', '28'), ('3', '29'), ('6', '29'), ('4', '30'), ('6', '30'), ('4', '31'), ('6', '31'), ('4', '32'), ('6', '32'), ('4', '33'), ('6', '33'), ('1', '34'), ('2', '34'), ('4', '34'), ('5', '34'), ('6', '34'), ('4', '35'), ('6', '35'), ('7', '35'), ('4', '36'), ('6', '36'), ('4', '37'), ('6', '37'), ('4', '38'), ('6', '38'), ('4', '39'), ('6', '39'), ('7', '39'), ('4', '40'), ('6', '40'), ('4', '41'), ('6', '41'), ('4', '42'), ('6', '42'), ('4', '43'), ('6', '43'), ('4', '44'), ('6', '44'), ('4', '45'), ('6', '45'), ('7', '45'), ('4', '46'), ('6', '46'), ('4', '47'), ('6', '47'), ('4', '48'), ('6', '48'), ('1', '49'), ('6', '49'), ('1', '50'), ('6', '50'), ('1', '51'), ('6', '51'), ('1', '52'), ('6', '52'), ('1', '53'), ('6', '53'), ('1', '54'), ('6', '54'), ('1', '55');
INSERT INTO role_fun VALUES ('6', '55'), ('1', '56'), ('6', '56'), ('1', '57'), ('6', '57'), ('1', '58'), ('6', '58'), ('1', '59'), ('6', '59'), ('1', '60'), ('6', '60'), ('1', '61'), ('6', '61'), ('1', '62'), ('6', '62'), ('1', '63'), ('6', '63'), ('1', '64'), ('6', '64'), ('1', '65'), ('6', '65'), ('1', '66'), ('6', '66'), ('1', '67'), ('6', '67'), ('5', '68'), ('6', '68'), ('5', '69'), ('6', '69'), ('5', '70'), ('6', '70'), ('5', '71'), ('6', '71'), ('5', '72'), ('6', '72'), ('5', '73'), ('6', '73'), ('5', '74'), ('6', '74'), ('5', '75'), ('6', '75'), ('5', '76'), ('6', '76'), ('5', '77'), ('6', '77'), ('5', '78'), ('6', '78'), ('5', '79'), ('6', '79'), ('5', '80'), ('6', '80'), ('5', '81'), ('6', '81'), ('5', '82'), ('6', '82'), ('5', '83'), ('6', '83'), ('5', '84'), ('6', '84'), ('5', '85'), ('6', '85'), ('5', '86'), ('6', '86'), ('5', '87'), ('6', '87'), ('5', '88'), ('6', '88'), ('5', '89'), ('6', '89'), ('5', '90'), ('6', '90'), ('5', '91'), ('6', '91'), ('5', '92'), ('6', '92'), ('5', '93'), ('6', '93'), ('5', '94'), ('6', '94'), ('5', '95'), ('6', '95'), ('2', '96'), ('6', '96'), ('2', '97'), ('6', '97'), ('2', '98'), ('6', '98'), ('2', '99'), ('6', '99'), ('2', '100'), ('6', '100'), ('2', '101'), ('6', '101'), ('2', '102'), ('6', '102'), ('2', '103'), ('6', '103'), ('2', '104'), ('6', '104'), ('2', '105'), ('6', '105');
INSERT INTO role_fun VALUES ('2', '106'), ('6', '106'), ('2', '107'), ('6', '107'), ('2', '108'), ('6', '108'), ('2', '109'), ('6', '109'), ('2', '110'), ('6', '110'), ('2', '111'), ('6', '111'), ('2', '112'), ('6', '112'), ('2', '113'), ('6', '113'), ('2', '114'), ('6', '114'), ('2', '115'), ('6', '115'), ('2', '116'), ('6', '116'), ('2', '117'), ('6', '117'), ('2', '118'), ('6', '118'), ('2', '119'), ('6', '119'), ('2', '120'), ('6', '120'), ('2', '121'), ('6', '121'), ('2', '122'), ('6', '122'), ('2', '123'), ('6', '123'), ('2', '124'), ('6', '124'), ('2', '125'), ('6', '125'), ('2', '126'), ('6', '126'), ('2', '127'), ('6', '127'), ('2', '128'), ('6', '128'), ('2', '129'), ('6', '129'), ('2', '130'), ('6', '130'), ('2', '131'), ('6', '131'), ('2', '132'), ('6', '132'), ('2', '133'), ('6', '133'), ('2', '134'), ('6', '134'), ('2', '135'), ('6', '135'), ('2', '136'), ('6', '136'), ('2', '137'), ('6', '137'), ('2', '138'), ('6', '138'), ('2', '139'), ('6', '139'), ('2', '140'), ('6', '140'), ('2', '141'), ('6', '141'), ('2', '142'), ('6', '142'), ('2', '143'), ('6', '143'), ('2', '144'), ('6', '144'), ('2', '145'), ('6', '145'), ('2', '146'), ('6', '146'), ('2', '147'), ('6', '147'), ('2', '148'), ('6', '148');
COMMIT;

-- ----------------------------
-- Table structure for `salary_item`
-- ----------------------------
DROP TABLE IF EXISTS `salary_item`;
CREATE TABLE `salary_item` (
`salaryItemId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`itemName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '项目名称' ,
`defaultVal`  decimal(18,2) NOT NULL COMMENT '缺省值' ,
PRIMARY KEY (`salaryItemId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='薪酬组成项目'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of salary_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `salary_payoff`
-- ----------------------------
DROP TABLE IF EXISTS `salary_payoff`;
CREATE TABLE `salary_payoff` (
`recordId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`fullname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '员工姓名' ,
`userId`  bigint(20) NOT NULL COMMENT '所属员工' ,
`profileNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '档案编号' ,
`standardId`  bigint(20) NOT NULL ,
`idNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号' ,
`standAmount`  decimal(18,2) NOT NULL DEFAULT 0.00 COMMENT '薪标金额' ,
`encourageAmount`  decimal(18,2) NOT NULL DEFAULT 0.00 COMMENT '奖励金额' ,
`deductAmount`  decimal(18,2) NOT NULL DEFAULT 0.00 COMMENT '扣除工资' ,
`achieveAmount`  decimal(18,2) NULL DEFAULT 0.00 COMMENT '效绩工资' ,
`encourageDesc`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '奖励描述' ,
`deductDesc`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扣除描述' ,
`memo`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注描述' ,
`acutalAmount`  decimal(18,2) NULL DEFAULT NULL COMMENT '实发金额' ,
`regTime`  datetime NOT NULL COMMENT '登记时间' ,
`register`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登记人' ,
`checkOpinion`  varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`checkName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审批人' ,
`checkTime`  datetime NULL DEFAULT NULL COMMENT '审批时间' ,
`checkStatus`  smallint(6) NULL DEFAULT NULL COMMENT '审批状态\r\n            0=草稿\r\n            1=通过审批\r\n            2=未通过审批\r\n            ' ,
`startTime`  datetime NOT NULL ,
`endTime`  datetime NOT NULL ,
PRIMARY KEY (`recordId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of salary_payoff
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `short_message`
-- ----------------------------
DROP TABLE IF EXISTS `short_message`;
CREATE TABLE `short_message` (
`messageId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`senderId`  bigint(20) NULL DEFAULT NULL COMMENT '主键' ,
`content`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`sender`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`msgType`  smallint(6) NOT NULL COMMENT '1=个人信息\r\n            2=日程安排\r\n            3=计划任务\r\n            ' ,
`sendTime`  datetime NOT NULL ,
PRIMARY KEY (`messageId`),
FOREIGN KEY (`senderId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='短信消息'
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of short_message
-- ----------------------------
BEGIN;
INSERT INTO short_message VALUES ('1', '-1', 'cwx', '系统', '4', '2010-05-15 11:37:15'), ('2', '-1', 'tesw', '系统', '4', '2010-05-15 11:38:37'), ('3', '-1', 'admin', '系统', '4', '2010-05-15 12:06:56'), ('4', '-1', '测试及开发', '系统', '4', '2010-05-15 12:11:34');
COMMIT;

-- ----------------------------
-- Table structure for `stand_salary`
-- ----------------------------
DROP TABLE IF EXISTS `stand_salary`;
CREATE TABLE `stand_salary` (
`standardId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`standardNo`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '薪酬标准编号\r\n            惟一' ,
`standardName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标准名称' ,
`totalMoney`  decimal(18,2) NOT NULL DEFAULT 0.00 ,
`framer`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`setdownTime`  datetime NULL DEFAULT NULL ,
`checkName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`checkTime`  datetime NULL DEFAULT NULL ,
`modifyName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`modifyTime`  datetime NULL DEFAULT NULL ,
`checkOpinion`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status`  smallint(6) NOT NULL COMMENT '0=草稿\r\n            1=审批\r\n            2=未通过审批' ,
`memo`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`standardId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of stand_salary
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `stand_salary_item`
-- ----------------------------
DROP TABLE IF EXISTS `stand_salary_item`;
CREATE TABLE `stand_salary_item` (
`itemId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`standardId`  bigint(20) NOT NULL ,
`itemName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`amount`  decimal(18,2) NOT NULL ,
`salaryItemId`  bigint(20) NULL DEFAULT NULL COMMENT '所属工资组成ID\r\n            外键，但不需要在数据库层建立外键' ,
PRIMARY KEY (`itemId`),
FOREIGN KEY (`standardId`) REFERENCES `stand_salary` (`standardId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='薪酬标准明细'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of stand_salary_item
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `sys_config`
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
`configId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`configKey`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Key' ,
`configName`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '配置名称' ,
`configDesc`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置描述' ,
`typeName`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属分类名称' ,
`dataType`  smallint(6) NOT NULL COMMENT '数据类型\r\n            1=varchar\r\n            2=intger\r\n            3=decimal\r\n            4=datetime\r\n            5=time\r\n            ' ,
`dataValue`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`configId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='系统配置\r\n\r\n用于系统的全局配置\r\n如邮件服务'
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
BEGIN;
INSERT INTO sys_config VALUES ('1', 'host', '主机Host', '主机IP', '系统邮件配置', '1', '192.168.1.1'), ('2', 'username', '用户名', '邮件发送的邮箱用户名', '系统邮件配置', '1', 'admin'), ('3', 'password', '密码', '邮件发送的邮箱密码', '系统邮件配置', '1', 'admin'), ('4', 'from', '来自', '发送人的邮箱或用户名', '系统邮件配置', '1', 'admin'), ('5', 'goodsStockUser', '用户帐号', '当库存产生警报时，接收消息的人员', '行政管理配置', '1', 'admin'), ('6', 'codeConfig', '验证码', '登录时是否需要验证码', '验证码配置', '2', '1');
COMMIT;

-- ----------------------------
-- Table structure for `system_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log` (
`logId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`username`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名' ,
`userId`  bigint(20) NOT NULL COMMENT '用户ID' ,
`createtime`  datetime NOT NULL COMMENT '执行时间' ,
`exeOperation`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '执行操作' ,
PRIMARY KEY (`logId`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of system_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `task_sign`
-- ----------------------------
DROP TABLE IF EXISTS `task_sign`;
CREATE TABLE `task_sign` (
`signId`  bigint(20) NOT NULL ,
`assignId`  bigint(20) NOT NULL COMMENT '所属流程设置' ,
`voteCounts`  int(11) NULL DEFAULT NULL COMMENT '绝对票数' ,
`votePercents`  int(11) NULL DEFAULT NULL COMMENT '百分比票数' ,
`decideType`  smallint(6) NOT NULL COMMENT '1=pass 通过\r\n            2=reject 拒绝' ,
PRIMARY KEY (`signId`),
FOREIGN KEY (`assignId`) REFERENCES `pro_user_assign` (`assignId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of task_sign
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
`userId`  bigint(20) NOT NULL COMMENT '主键' ,
`roleId`  bigint(20) NOT NULL ,
PRIMARY KEY (`userId`, `roleId`),
FOREIGN KEY (`roleId`) REFERENCES `app_role` (`roleId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO user_role VALUES ('1', '-1'), ('2', '-1'), ('3', '7'), ('4', '8'), ('5', '8'), ('6', '8'), ('8', '9'), ('9', '10'), ('10', '11'), ('24', '11'), ('25', '11'), ('12', '13'), ('13', '13'), ('14', '13'), ('15', '13'), ('16', '13'), ('17', '13'), ('18', '13'), ('19', '13'), ('22', '13'), ('23', '13'), ('26', '13'), ('11', '14'), ('20', '14'), ('21', '14'), ('30', '15'), ('27', '16'), ('28', '16'), ('29', '16'), ('31', '18'), ('32', '18');
COMMIT;

-- ----------------------------
-- Table structure for `user_sub`
-- ----------------------------
DROP TABLE IF EXISTS `user_sub`;
CREATE TABLE `user_sub` (
`subId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`subUserId`  bigint(20) NOT NULL ,
`userId`  bigint(20) NOT NULL ,
PRIMARY KEY (`subId`),
FOREIGN KEY (`subUserId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='subordinate'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of user_sub
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for `work_plan`
-- ----------------------------
DROP TABLE IF EXISTS `work_plan`;
CREATE TABLE `work_plan` (
`planId`  bigint(20) NOT NULL AUTO_INCREMENT ,
`planName`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '计划名称' ,
`planContent`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '计划内容' ,
`startTime`  datetime NOT NULL COMMENT '开始日期' ,
`endTime`  datetime NOT NULL COMMENT '结束日期' ,
`typeId`  bigint(20) NOT NULL COMMENT '计划类型' ,
`userId`  bigint(20) NULL DEFAULT NULL COMMENT '员工ID' ,
`issueScope`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发布范围\r\n            0则代表全部部门\r\n            存放所有的参与部门ID\r\n            ' ,
`participants`  varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参与人\r\n            0则代表全部参与\r\n            参与人,即员工ID列表' ,
`principal`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '负责人' ,
`note`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注' ,
`status`  smallint(6) NOT NULL COMMENT '状态\r\n            1=激活\r\n            0=禁用' ,
`isPersonal`  smallint(6) NOT NULL COMMENT '是否为个人计划\r\n            1=则为个人工作计划，这时发布范围，参与人均为空，负责人为当前用户\r\n            0=则代表为其他任务' ,
`icon`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标' ,
PRIMARY KEY (`planId`),
FOREIGN KEY (`userId`) REFERENCES `app_user` (`userId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
FOREIGN KEY (`typeId`) REFERENCES `plan_type` (`typeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='工作计划'
AUTO_INCREMENT=1

;

-- ----------------------------
-- Records of work_plan
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Indexes structure for table `app_function`
-- ----------------------------
CREATE UNIQUE INDEX `AK_UQ_RSKEY` ON `app_function`(`funKey`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `app_function`
-- ----------------------------
ALTER TABLE `app_function` AUTO_INCREMENT=149;

-- ----------------------------
-- Auto increment value for `app_role`
-- ----------------------------
ALTER TABLE `app_role` AUTO_INCREMENT=20;

-- ----------------------------
-- Indexes structure for table `app_tips`
-- ----------------------------
CREATE INDEX `FK_AT_R_AP` ON `app_tips`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `app_tips`
-- ----------------------------
ALTER TABLE `app_tips` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `app_user`
-- ----------------------------
CREATE INDEX `FK_AU_R_DPT` ON `app_user`(`depId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `app_user`
-- ----------------------------
ALTER TABLE `app_user` AUTO_INCREMENT=33;

-- ----------------------------
-- Indexes structure for table `appointment`
-- ----------------------------
CREATE INDEX `FK_AP_R_AU` ON `appointment`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `appointment`
-- ----------------------------
ALTER TABLE `appointment` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `arch_dispatch`
-- ----------------------------
CREATE INDEX `FK_AVDH_R_ARV` ON `arch_dispatch`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `arch_dispatch`
-- ----------------------------
ALTER TABLE `arch_dispatch` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `arch_flow_conf`
-- ----------------------------
CREATE INDEX `FK_AFC_R_PD` ON `arch_flow_conf`(`processDefId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `arch_flow_conf`
-- ----------------------------
ALTER TABLE `arch_flow_conf` AUTO_INCREMENT=3;

-- ----------------------------
-- Indexes structure for table `arch_hasten`
-- ----------------------------
CREATE INDEX `FK_ARHN_R_ARV` ON `arch_hasten`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `arch_hasten`
-- ----------------------------
ALTER TABLE `arch_hasten` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `arch_rec_type`
-- ----------------------------
CREATE INDEX `FK_ART_R_DPT` ON `arch_rec_type`(`depId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `arch_rec_type`
-- ----------------------------
ALTER TABLE `arch_rec_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `arch_rec_user`
-- ----------------------------
ALTER TABLE `arch_rec_user` AUTO_INCREMENT=25;

-- ----------------------------
-- Indexes structure for table `arch_template`
-- ----------------------------
CREATE INDEX `FK_AHT_R_FA` ON `arch_template`(`fileId`) USING BTREE ;
CREATE INDEX `FK_ART_R_ARVT` ON `arch_template`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `arch_template`
-- ----------------------------
ALTER TABLE `arch_template` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `archives`
-- ----------------------------
CREATE INDEX `FK_ARV_R_ART` ON `archives`(`arc_typeId`) USING BTREE ;
CREATE INDEX `FK_ARV_R_ARVT` ON `archives`(`typeId`) USING BTREE ;
CREATE INDEX `FK_ARV_R_DPT` ON `archives`(`depId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `archives`
-- ----------------------------
ALTER TABLE `archives` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `archives_attend`
-- ----------------------------
CREATE INDEX `FK_ARVA_R_ARV` ON `archives_attend`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `archives_attend`
-- ----------------------------
ALTER TABLE `archives_attend` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `archives_dep`
-- ----------------------------
CREATE INDEX `FK_AVD_R_ARV` ON `archives_dep`(`archivesId`) USING BTREE ;
CREATE INDEX `FK_AVD_R_DPT` ON `archives_dep`(`depId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `archives_dep`
-- ----------------------------
ALTER TABLE `archives_dep` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `archives_doc`
-- ----------------------------
CREATE INDEX `FK_ARVC_R_FA` ON `archives_doc`(`fileId`) USING BTREE ;
CREATE INDEX `FK_ARVD_R_ARV` ON `archives_doc`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `archives_doc`
-- ----------------------------
ALTER TABLE `archives_doc` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `archives_handle`
-- ----------------------------
CREATE INDEX `FK_AVHD_R_ARV` ON `archives_handle`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `archives_handle`
-- ----------------------------
ALTER TABLE `archives_handle` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `archives_type`
-- ----------------------------
ALTER TABLE `archives_type` AUTO_INCREMENT=3;

-- ----------------------------
-- Auto increment value for `assets_type`
-- ----------------------------
ALTER TABLE `assets_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `book`
-- ----------------------------
CREATE INDEX `FK_BK_R_BT` ON `book`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `book`
-- ----------------------------
ALTER TABLE `book` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `book_bor_ret`
-- ----------------------------
CREATE INDEX `FK_BBR_R_BS` ON `book_bor_ret`(`bookSnId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `book_bor_ret`
-- ----------------------------
ALTER TABLE `book_bor_ret` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `book_sn`
-- ----------------------------
CREATE INDEX `FK_BS_R_BK` ON `book_sn`(`bookId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `book_sn`
-- ----------------------------
ALTER TABLE `book_sn` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `book_type`
-- ----------------------------
ALTER TABLE `book_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `cal_file`
-- ----------------------------
CREATE INDEX `FK_CF_R_CP` ON `cal_file`(`planId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `calendar_plan`
-- ----------------------------
CREATE INDEX `FK_CA_R_AU` ON `calendar_plan`(`userId`) USING BTREE ;
CREATE INDEX `FK_CP_R_AUAS` ON `calendar_plan`(`assignerId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `calendar_plan`
-- ----------------------------
ALTER TABLE `calendar_plan` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `car`
-- ----------------------------
ALTER TABLE `car` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `car_apply`
-- ----------------------------
CREATE INDEX `FK_CRA_R_CR` ON `car_apply`(`carId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `car_apply`
-- ----------------------------
ALTER TABLE `car_apply` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `cart_repair`
-- ----------------------------
CREATE INDEX `FK_CRR_R_CR` ON `cart_repair`(`carId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `cart_repair`
-- ----------------------------
ALTER TABLE `cart_repair` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `company`
-- ----------------------------
ALTER TABLE `company` AUTO_INCREMENT=2;

-- ----------------------------
-- Indexes structure for table `contract`
-- ----------------------------
CREATE INDEX `FK_CT_R_PT` ON `contract`(`projectId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `contract`
-- ----------------------------
ALTER TABLE `contract` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `contract_config`
-- ----------------------------
CREATE INDEX `FK_CC_R_CT` ON `contract_config`(`contractId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `contract_config`
-- ----------------------------
ALTER TABLE `contract_config` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `contract_file`
-- ----------------------------
CREATE INDEX `FK_CTF_R_CT` ON `contract_file`(`contractId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `cus_connection`
-- ----------------------------
CREATE INDEX `FK_CC_R_CS` ON `cus_connection`(`customerId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `cus_connection`
-- ----------------------------
ALTER TABLE `cus_connection` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `cus_linkman`
-- ----------------------------
CREATE INDEX `FK_CLM_R_CS` ON `cus_linkman`(`customerId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `cus_linkman`
-- ----------------------------
ALTER TABLE `cus_linkman` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `customer`
-- ----------------------------
ALTER TABLE `customer` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `department`
-- ----------------------------
ALTER TABLE `department` AUTO_INCREMENT=26;

-- ----------------------------
-- Indexes structure for table `depre_record`
-- ----------------------------
CREATE INDEX `FK_DR_R_FA` ON `depre_record`(`assetsId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `depre_record`
-- ----------------------------
ALTER TABLE `depre_record` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `depre_type`
-- ----------------------------
ALTER TABLE `depre_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `diary`
-- ----------------------------
CREATE INDEX `FK_DY_R_AU` ON `diary`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `diary`
-- ----------------------------
ALTER TABLE `diary` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `dictionary`
-- ----------------------------
ALTER TABLE `dictionary` AUTO_INCREMENT=300;

-- ----------------------------
-- Indexes structure for table `doc_file`
-- ----------------------------
CREATE INDEX `FK_DF_F_DT` ON `doc_file`(`docId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `doc_folder`
-- ----------------------------
CREATE INDEX `FK_DF_R_AU` ON `doc_folder`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `doc_folder`
-- ----------------------------
ALTER TABLE `doc_folder` AUTO_INCREMENT=5;

-- ----------------------------
-- Indexes structure for table `doc_history`
-- ----------------------------
CREATE INDEX `FK_DHY_R_ARVD` ON `doc_history`(`docId`) USING BTREE ;
CREATE INDEX `FK_DH_R_FA` ON `doc_history`(`fileId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `doc_history`
-- ----------------------------
ALTER TABLE `doc_history` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `doc_privilege`
-- ----------------------------
CREATE INDEX `FK_DP_R_DF` ON `doc_privilege`(`folderId`) USING BTREE ;
CREATE INDEX `FK_DP_R_DT` ON `doc_privilege`(`docId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `doc_privilege`
-- ----------------------------
ALTER TABLE `doc_privilege` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `document`
-- ----------------------------
CREATE INDEX `FK_DT_R_AU` ON `document`(`userId`) USING BTREE ;
CREATE INDEX `FK_DT_R_DF` ON `document`(`folderId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `document`
-- ----------------------------
ALTER TABLE `document` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `duty`
-- ----------------------------
CREATE INDEX `FK_DUY_R_AU` ON `duty`(`userId`) USING BTREE ;
CREATE INDEX `FK_DUY_R_DS` ON `duty`(`systemId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `duty`
-- ----------------------------
ALTER TABLE `duty` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `duty_register`
-- ----------------------------
CREATE INDEX `FK_DR_R_AU` ON `duty_register`(`userId`) USING BTREE ;
CREATE INDEX `FK_DR_R_DS` ON `duty_register`(`sectionId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `duty_register`
-- ----------------------------
ALTER TABLE `duty_register` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `duty_section`
-- ----------------------------
ALTER TABLE `duty_section` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `duty_system`
-- ----------------------------
ALTER TABLE `duty_system` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `emp_profile`
-- ----------------------------
CREATE INDEX `FK_EPF_R_AU` ON `emp_profile`(`userId`) USING BTREE ;
CREATE INDEX `FK_EP_R_JB` ON `emp_profile`(`jobId`) USING BTREE ;
CREATE INDEX `FK_SD_R_SY` ON `emp_profile`(`standardId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `emp_profile`
-- ----------------------------
ALTER TABLE `emp_profile` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `errands_register`
-- ----------------------------
CREATE INDEX `FK_ERP_R_AU` ON `errands_register`(`approvalId`) USING BTREE ;
CREATE INDEX `FK_ER_R_AU` ON `errands_register`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `errands_register`
-- ----------------------------
ALTER TABLE `errands_register` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `file_attach`
-- ----------------------------
ALTER TABLE `file_attach` AUTO_INCREMENT=4;

-- ----------------------------
-- Indexes structure for table `fixed_assets`
-- ----------------------------
CREATE INDEX `FK_FA_R_AT` ON `fixed_assets`(`assetsTypeId`) USING BTREE ;
CREATE INDEX `FK_FA_R_DT` ON `fixed_assets`(`depreTypeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `fixed_assets`
-- ----------------------------
ALTER TABLE `fixed_assets` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `form_data`
-- ----------------------------
CREATE INDEX `FK_FD_R_PF` ON `form_data`(`formId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `form_data`
-- ----------------------------
ALTER TABLE `form_data` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `form_def`
-- ----------------------------
ALTER TABLE `form_def` AUTO_INCREMENT=20;

-- ----------------------------
-- Indexes structure for table `fun_url`
-- ----------------------------
CREATE INDEX `FK_FU_R_AFN` ON `fun_url`(`functionId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `fun_url`
-- ----------------------------
ALTER TABLE `fun_url` AUTO_INCREMENT=386;

-- ----------------------------
-- Indexes structure for table `goods_apply`
-- ----------------------------
CREATE INDEX `FK_GA_R_OG` ON `goods_apply`(`goodsId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `goods_apply`
-- ----------------------------
ALTER TABLE `goods_apply` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `hire_issue`
-- ----------------------------
ALTER TABLE `hire_issue` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `holiday_record`
-- ----------------------------
ALTER TABLE `holiday_record` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `in_message`
-- ----------------------------
CREATE INDEX `FK_IM_R_AU` ON `in_message`(`userId`) USING BTREE ;
CREATE INDEX `FK_IM_R_SM` ON `in_message`(`messageId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `in_message`
-- ----------------------------
ALTER TABLE `in_message` AUTO_INCREMENT=5;

-- ----------------------------
-- Indexes structure for table `in_stock`
-- ----------------------------
CREATE INDEX `FK_IS_R_OG` ON `in_stock`(`goodsId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `in_stock`
-- ----------------------------
ALTER TABLE `in_stock` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `index_display`
-- ----------------------------
CREATE INDEX `FK_ID_R_AU` ON `index_display`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `index_display`
-- ----------------------------
ALTER TABLE `index_display` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `jbpm4_deployment`
-- ----------------------------
ALTER TABLE `jbpm4_deployment` AUTO_INCREMENT=11;

-- ----------------------------
-- Indexes structure for table `jbpm4_deployprop`
-- ----------------------------
CREATE INDEX `IDX_DEPLPROP_DEPL` ON `jbpm4_deployprop`(`DEPLOYMENT_`) USING BTREE ;
CREATE INDEX `FK_DEPLPROP_DEPL` ON `jbpm4_deployprop`(`DEPLOYMENT_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_deployprop`
-- ----------------------------
ALTER TABLE `jbpm4_deployprop` AUTO_INCREMENT=31;

-- ----------------------------
-- Indexes structure for table `jbpm4_execution`
-- ----------------------------
CREATE UNIQUE INDEX `ID_` ON `jbpm4_execution`(`ID_`) USING BTREE ;
CREATE INDEX `IDX_EXEC_SUPEREXEC` ON `jbpm4_execution`(`SUPEREXEC_`) USING BTREE ;
CREATE INDEX `IDX_EXEC_INSTANCE` ON `jbpm4_execution`(`INSTANCE_`) USING BTREE ;
CREATE INDEX `IDX_EXEC_SUBPI` ON `jbpm4_execution`(`SUBPROCINST_`) USING BTREE ;
CREATE INDEX `IDX_EXEC_PARENT` ON `jbpm4_execution`(`PARENT_`) USING BTREE ;
CREATE INDEX `FK_EXEC_PARENT` ON `jbpm4_execution`(`PARENT_`) USING BTREE ;
CREATE INDEX `FK_EXEC_SUBPI` ON `jbpm4_execution`(`SUBPROCINST_`) USING BTREE ;
CREATE INDEX `FK_EXEC_INSTANCE` ON `jbpm4_execution`(`INSTANCE_`) USING BTREE ;
CREATE INDEX `FK_EXEC_SUPEREXEC` ON `jbpm4_execution`(`SUPEREXEC_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_execution`
-- ----------------------------
ALTER TABLE `jbpm4_execution` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_hist_actinst`
-- ----------------------------
CREATE INDEX `IDX_HACTI_HPROCI` ON `jbpm4_hist_actinst`(`HPROCI_`) USING BTREE ;
CREATE INDEX `IDX_HTI_HTASK` ON `jbpm4_hist_actinst`(`HTASK_`) USING BTREE ;
CREATE INDEX `FK_HACTI_HPROCI` ON `jbpm4_hist_actinst`(`HPROCI_`) USING BTREE ;
CREATE INDEX `FK_HTI_HTASK` ON `jbpm4_hist_actinst`(`HTASK_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_hist_actinst`
-- ----------------------------
ALTER TABLE `jbpm4_hist_actinst` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_hist_detail`
-- ----------------------------
CREATE INDEX `IDX_HDET_HACTI` ON `jbpm4_hist_detail`(`HACTI_`) USING BTREE ;
CREATE INDEX `IDX_HDET_HPROCI` ON `jbpm4_hist_detail`(`HPROCI_`) USING BTREE ;
CREATE INDEX `IDX_HDETAIL_HACTI` ON `jbpm4_hist_detail`(`HACTI_`) USING BTREE ;
CREATE INDEX `IDX_HDETAIL_HVAR` ON `jbpm4_hist_detail`(`HVAR_`) USING BTREE ;
CREATE INDEX `IDX_HDETAIL_HTASK` ON `jbpm4_hist_detail`(`HTASK_`) USING BTREE ;
CREATE INDEX `IDX_HDETAIL_HPROCI` ON `jbpm4_hist_detail`(`HPROCI_`) USING BTREE ;
CREATE INDEX `IDX_HDET_HVAR` ON `jbpm4_hist_detail`(`HVAR_`) USING BTREE ;
CREATE INDEX `IDX_HDET_HTASK` ON `jbpm4_hist_detail`(`HTASK_`) USING BTREE ;
CREATE INDEX `FK_HDETAIL_HPROCI` ON `jbpm4_hist_detail`(`HPROCI_`) USING BTREE ;
CREATE INDEX `FK_HDETAIL_HACTI` ON `jbpm4_hist_detail`(`HACTI_`) USING BTREE ;
CREATE INDEX `FK_HDETAIL_HTASK` ON `jbpm4_hist_detail`(`HTASK_`) USING BTREE ;
CREATE INDEX `FK_HDETAIL_HVAR` ON `jbpm4_hist_detail`(`HVAR_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_hist_detail`
-- ----------------------------
ALTER TABLE `jbpm4_hist_detail` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_hist_task`
-- ----------------------------
CREATE INDEX `FK_HSUPERT_SUB` ON `jbpm4_hist_task`(`SUPERTASK_`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `jbpm4_hist_var`
-- ----------------------------
CREATE INDEX `IDX_HVAR_HPROCI` ON `jbpm4_hist_var`(`HPROCI_`) USING BTREE ;
CREATE INDEX `IDX_HVAR_HTASK` ON `jbpm4_hist_var`(`HTASK_`) USING BTREE ;
CREATE INDEX `FK_HVAR_HPROCI` ON `jbpm4_hist_var`(`HPROCI_`) USING BTREE ;
CREATE INDEX `FK_HVAR_HTASK` ON `jbpm4_hist_var`(`HTASK_`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `jbpm4_id_group`
-- ----------------------------
CREATE INDEX `IDX_GROUP_PARENT` ON `jbpm4_id_group`(`PARENT_`) USING BTREE ;
CREATE INDEX `FK_GROUP_PARENT` ON `jbpm4_id_group`(`PARENT_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_id_group`
-- ----------------------------
ALTER TABLE `jbpm4_id_group` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_id_membership`
-- ----------------------------
CREATE INDEX `IDX_MEM_USER` ON `jbpm4_id_membership`(`USER_`) USING BTREE ;
CREATE INDEX `IDX_MEM_GROUP` ON `jbpm4_id_membership`(`GROUP_`) USING BTREE ;
CREATE INDEX `FK_MEM_GROUP` ON `jbpm4_id_membership`(`GROUP_`) USING BTREE ;
CREATE INDEX `FK_MEM_USER` ON `jbpm4_id_membership`(`USER_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_id_membership`
-- ----------------------------
ALTER TABLE `jbpm4_id_membership` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `jbpm4_id_user`
-- ----------------------------
ALTER TABLE `jbpm4_id_user` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_job`
-- ----------------------------
CREATE INDEX `IDX_JOBRETRIES` ON `jbpm4_job`(`RETRIES_`) USING BTREE ;
CREATE INDEX `IDX_JOB_CFG` ON `jbpm4_job`(`CFG_`) USING BTREE ;
CREATE INDEX `IDX_JOB_PRINST` ON `jbpm4_job`(`PROCESSINSTANCE_`) USING BTREE ;
CREATE INDEX `IDX_JOB_EXE` ON `jbpm4_job`(`EXECUTION_`) USING BTREE ;
CREATE INDEX `IDX_JOBLOCKEXP` ON `jbpm4_job`(`LOCKEXPTIME_`) USING BTREE ;
CREATE INDEX `IDX_JOBDUEDATE` ON `jbpm4_job`(`DUEDATE_`) USING BTREE ;
CREATE INDEX `FK_JOB_CFG` ON `jbpm4_job`(`CFG_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_job`
-- ----------------------------
ALTER TABLE `jbpm4_job` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_lob`
-- ----------------------------
CREATE INDEX `IDX_LOB_DEPLOYMENT` ON `jbpm4_lob`(`DEPLOYMENT_`) USING BTREE ;
CREATE INDEX `FK_LOB_DEPLOYMENT` ON `jbpm4_lob`(`DEPLOYMENT_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_lob`
-- ----------------------------
ALTER TABLE `jbpm4_lob` AUTO_INCREMENT=16;

-- ----------------------------
-- Indexes structure for table `jbpm4_participation`
-- ----------------------------
CREATE INDEX `IDX_PART_TASK` ON `jbpm4_participation`(`TASK_`) USING BTREE ;
CREATE INDEX `FK_PART_SWIMLANE` ON `jbpm4_participation`(`SWIMLANE_`) USING BTREE ;
CREATE INDEX `FK_PART_TASK` ON `jbpm4_participation`(`TASK_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_participation`
-- ----------------------------
ALTER TABLE `jbpm4_participation` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_swimlane`
-- ----------------------------
CREATE INDEX `IDX_SWIMLANE_EXEC` ON `jbpm4_swimlane`(`EXECUTION_`) USING BTREE ;
CREATE INDEX `FK_SWIMLANE_EXEC` ON `jbpm4_swimlane`(`EXECUTION_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_swimlane`
-- ----------------------------
ALTER TABLE `jbpm4_swimlane` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_task`
-- ----------------------------
CREATE INDEX `IDX_TASK_SUPERTASK` ON `jbpm4_task`(`SUPERTASK_`) USING BTREE ;
CREATE INDEX `FK_TASK_SWIML` ON `jbpm4_task`(`SWIMLANE_`) USING BTREE ;
CREATE INDEX `FK_TASK_SUPERTASK` ON `jbpm4_task`(`SUPERTASK_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_task`
-- ----------------------------
ALTER TABLE `jbpm4_task` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `jbpm4_variable`
-- ----------------------------
CREATE INDEX `IDX_VAR_EXESYS` ON `jbpm4_variable`(`EXESYS_`) USING BTREE ;
CREATE INDEX `IDX_VAR_TASK` ON `jbpm4_variable`(`TASK_`) USING BTREE ;
CREATE INDEX `IDX_VAR_EXECUTION` ON `jbpm4_variable`(`EXECUTION_`) USING BTREE ;
CREATE INDEX `IDX_VAR_LOB` ON `jbpm4_variable`(`LOB_`) USING BTREE ;
CREATE INDEX `FK_VAR_LOB` ON `jbpm4_variable`(`LOB_`) USING BTREE ;
CREATE INDEX `FK_VAR_EXECUTION` ON `jbpm4_variable`(`EXECUTION_`) USING BTREE ;
CREATE INDEX `FK_VAR_EXESYS` ON `jbpm4_variable`(`EXESYS_`) USING BTREE ;
CREATE INDEX `FK_VAR_TASK` ON `jbpm4_variable`(`TASK_`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `jbpm4_variable`
-- ----------------------------
ALTER TABLE `jbpm4_variable` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `job`
-- ----------------------------
CREATE INDEX `FK_JB_R_DPT` ON `job`(`depId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `job`
-- ----------------------------
ALTER TABLE `job` AUTO_INCREMENT=82;

-- ----------------------------
-- Indexes structure for table `job_change`
-- ----------------------------
CREATE INDEX `FK_JBC_R_JBN` ON `job_change`(`newJobId`) USING BTREE ;
CREATE INDEX `FK_JBC_R_JBO` ON `job_change`(`orgJobId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `job_change`
-- ----------------------------
ALTER TABLE `job_change` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `leader_read`
-- ----------------------------
CREATE INDEX `FK_LDR_R_ARV` ON `leader_read`(`archivesId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `leader_read`
-- ----------------------------
ALTER TABLE `leader_read` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `mail`
-- ----------------------------
CREATE INDEX `FK_ML_R_AU` ON `mail`(`senderId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `mail`
-- ----------------------------
ALTER TABLE `mail` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `mail_attach`
-- ----------------------------
CREATE INDEX `FK_MA_R_FA` ON `mail_attach`(`fileId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `mail_box`
-- ----------------------------
CREATE INDEX `FK_MB_R_AU` ON `mail_box`(`userId`) USING BTREE ;
CREATE INDEX `FK_MB_R_FD` ON `mail_box`(`folderId`) USING BTREE ;
CREATE INDEX `FK_MB_R_ML` ON `mail_box`(`mailId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `mail_box`
-- ----------------------------
ALTER TABLE `mail_box` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `mail_folder`
-- ----------------------------
CREATE INDEX `FK_FD_R_AU` ON `mail_folder`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `mail_folder`
-- ----------------------------
ALTER TABLE `mail_folder` AUTO_INCREMENT=5;

-- ----------------------------
-- Auto increment value for `meeting`
-- ----------------------------
ALTER TABLE `meeting` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `meeting_attend`
-- ----------------------------
CREATE INDEX `FK_MTA_R_MT` ON `meeting_attend`(`mettingId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `meeting_attend`
-- ----------------------------
ALTER TABLE `meeting_attend` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `meeting_file`
-- ----------------------------
CREATE INDEX `FK_MF_R_FA` ON `meeting_file`(`fileId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `news`
-- ----------------------------
CREATE INDEX `FK_NS_R_NT` ON `news`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `news`
-- ----------------------------
ALTER TABLE `news` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `news_comment`
-- ----------------------------
CREATE INDEX `FK_NC_R_AU` ON `news_comment`(`userId`) USING BTREE ;
CREATE INDEX `FK_NC_R_NS` ON `news_comment`(`newsId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `news_comment`
-- ----------------------------
ALTER TABLE `news_comment` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `news_type`
-- ----------------------------
ALTER TABLE `news_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `notice`
-- ----------------------------
ALTER TABLE `notice` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `office_goods`
-- ----------------------------
CREATE INDEX `FK_OG_R_OGT` ON `office_goods`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `office_goods`
-- ----------------------------
ALTER TABLE `office_goods` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `office_goods_type`
-- ----------------------------
ALTER TABLE `office_goods_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `phone_book`
-- ----------------------------
CREATE INDEX `FK_PB_R_AU` ON `phone_book`(`userId`) USING BTREE ;
CREATE INDEX `FK_PB_R_PG` ON `phone_book`(`groupId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `phone_book`
-- ----------------------------
ALTER TABLE `phone_book` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `phone_group`
-- ----------------------------
CREATE INDEX `FK_PG_R_AU` ON `phone_group`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `phone_group`
-- ----------------------------
ALTER TABLE `phone_group` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `plan_attend`
-- ----------------------------
CREATE INDEX `FK_PAD_R_DT` ON `plan_attend`(`depId`) USING BTREE ;
CREATE INDEX `FK_PAD_R_UA` ON `plan_attend`(`userId`) USING BTREE ;
CREATE INDEX `FK_PAD_R_WP` ON `plan_attend`(`planId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `plan_attend`
-- ----------------------------
ALTER TABLE `plan_attend` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `plan_file`
-- ----------------------------
CREATE INDEX `FK_PA_R_WP` ON `plan_file`(`planId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `plan_type`
-- ----------------------------
ALTER TABLE `plan_type` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `pro_definition`
-- ----------------------------
CREATE INDEX `FK_PD_R_PT` ON `pro_definition`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `pro_definition`
-- ----------------------------
ALTER TABLE `pro_definition` AUTO_INCREMENT=11;

-- ----------------------------
-- Auto increment value for `pro_type`
-- ----------------------------
ALTER TABLE `pro_type` AUTO_INCREMENT=4;

-- ----------------------------
-- Auto increment value for `pro_user_assign`
-- ----------------------------
ALTER TABLE `pro_user_assign` AUTO_INCREMENT=11;

-- ----------------------------
-- Indexes structure for table `process_form`
-- ----------------------------
CREATE INDEX `FK_PF_R_PR` ON `process_form`(`runId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `process_form`
-- ----------------------------
ALTER TABLE `process_form` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `process_run`
-- ----------------------------
CREATE INDEX `FK_PR_R_AU` ON `process_run`(`userId`) USING BTREE ;
CREATE INDEX `FK_PR_R_PD` ON `process_run`(`defId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `process_run`
-- ----------------------------
ALTER TABLE `process_run` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `product`
-- ----------------------------
CREATE INDEX `FK_PD_R_PUT` ON `product`(`providerId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `product`
-- ----------------------------
ALTER TABLE `product` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `project`
-- ----------------------------
CREATE INDEX `FK_PR_R_CS` ON `project`(`customerId`) USING BTREE ;
CREATE INDEX `FK_PT_R_AU` ON `project`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `project`
-- ----------------------------
ALTER TABLE `project` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `project_file`
-- ----------------------------
CREATE INDEX `FK_PF_R_PT` ON `project_file`(`projectId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `provider`
-- ----------------------------
ALTER TABLE `provider` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `region`
-- ----------------------------
ALTER TABLE `region` AUTO_INCREMENT=393;

-- ----------------------------
-- Indexes structure for table `report_param`
-- ----------------------------
CREATE INDEX `FK_RP_R_RPT` ON `report_param`(`reportId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `report_param`
-- ----------------------------
ALTER TABLE `report_param` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `report_template`
-- ----------------------------
ALTER TABLE `report_template` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `resume`
-- ----------------------------
ALTER TABLE `resume` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `resume_file`
-- ----------------------------
CREATE INDEX `FK_RMF_R_RM` ON `resume_file`(`resumeId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `role_fun`
-- ----------------------------
CREATE INDEX `FK_RF_R_AFN` ON `role_fun`(`functionId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `salary_item`
-- ----------------------------
ALTER TABLE `salary_item` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `salary_payoff`
-- ----------------------------
ALTER TABLE `salary_payoff` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `short_message`
-- ----------------------------
CREATE INDEX `FK_SM_R_AU` ON `short_message`(`senderId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `short_message`
-- ----------------------------
ALTER TABLE `short_message` AUTO_INCREMENT=5;

-- ----------------------------
-- Auto increment value for `stand_salary`
-- ----------------------------
ALTER TABLE `stand_salary` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `stand_salary_item`
-- ----------------------------
CREATE INDEX `FK_SSI_R_SSY` ON `stand_salary_item`(`standardId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `stand_salary_item`
-- ----------------------------
ALTER TABLE `stand_salary_item` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for `sys_config`
-- ----------------------------
ALTER TABLE `sys_config` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for `system_log`
-- ----------------------------
ALTER TABLE `system_log` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `task_sign`
-- ----------------------------
CREATE INDEX `FK_TS_R_PUA` ON `task_sign`(`assignId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `user_role`
-- ----------------------------
CREATE INDEX `FK_UR_R_AR` ON `user_role`(`roleId`) USING BTREE ;

-- ----------------------------
-- Indexes structure for table `user_sub`
-- ----------------------------
CREATE INDEX `FK_USB_R_AU` ON `user_sub`(`subUserId`) USING BTREE ;
CREATE INDEX `FK_US_R_AU` ON `user_sub`(`userId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `user_sub`
-- ----------------------------
ALTER TABLE `user_sub` AUTO_INCREMENT=1;

-- ----------------------------
-- Indexes structure for table `work_plan`
-- ----------------------------
CREATE INDEX `FK_WP_R_AU` ON `work_plan`(`userId`) USING BTREE ;
CREATE INDEX `FK_WP_R_PT` ON `work_plan`(`typeId`) USING BTREE ;

-- ----------------------------
-- Auto increment value for `work_plan`
-- ----------------------------
ALTER TABLE `work_plan` AUTO_INCREMENT=1;
