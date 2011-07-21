
/*==============================================================*/
/* Table: app_function                                          */
/*==============================================================*/
create table app_function  (
   functionId           NUMBER(18)                      not null,
   funKey               VARCHAR2(64)                    not null,
   funName              VARCHAR2(128)                   not null,
   constraint PK_APP_FUNCTION primary key (functionId),
   constraint AK_UQ_RSKEY_APP_FUNC unique (funKey)
);

comment on column app_function.funKey is
'权限Key';

comment on column app_function.funName is
'权限名称';

/*==============================================================*/
/* Table: app_role                                              */
/*==============================================================*/
create table app_role  (
   roleId               NUMBER(18)                      not null,
   roleName             VARCHAR2(128)                   not null,
   roleDesc             VARCHAR2(128),
   status               SMALLINT                        not null,
   rights               CLOB,
   isDefaultIn          SMALLINT                        not null,
   constraint PK_APP_ROLE primary key (roleId)
);

comment on table app_role is
'角色表';

comment on column app_role.roleName is
'角色名称';

comment on column app_role.roleDesc is
'角色描述';

comment on column app_role.status is
'状态';

/*==============================================================*/
/* Table: app_tips                                              */
/*==============================================================*/
create table app_tips  (
   tipsId               NUMBER(18)                      not null,
   userId               INTEGER                         not null,
   tipsName             VARCHAR2(128),
   content              VARCHAR2(2048),
   disheight            INTEGER,
   diswidth             INTEGER,
   disleft              INTEGER,
   distop               INTEGER,
   dislevel             INTEGER,
   createtime           DATE                            not null,
   constraint PK_APP_TIPS primary key (tipsId)
);

comment on table app_tips is
'用户便签';

/*==============================================================*/
/* Table: app_user                                              */
/*==============================================================*/
create table app_user  (
   userId               NUMBER(18)                      not null,
   username             VARCHAR2(128)                   not null,
   title                SMALLINT                        not null,
   password             VARCHAR2(128)                   not null,
   email                VARCHAR2(128)                   not null,
   depId                INTEGER,
   position             VARCHAR2(32),
   phone                VARCHAR2(32),
   mobile               VARCHAR2(32),
   fax                  VARCHAR2(32),
   address              VARCHAR2(64),
   zip                  VARCHAR2(32),
   photo                VARCHAR2(128),
   accessionTime        DATE                            not null,
   status               SMALLINT                        not null,
   education            VARCHAR2(64),
   fullname             VARCHAR2(50)                    not null,
   delFlag              SMALLINT                        not null,
   constraint PK_APP_USER primary key (userId)
);

comment on table app_user is
'app_user
用户表';

comment on column app_user.userId is
'主键';

comment on column app_user.username is
'用户名';

comment on column app_user.title is
'1=先生
0=女士
小姐';

comment on column app_user.password is
'密码';

comment on column app_user.email is
'邮件';

comment on column app_user.depId is
'所属部门';

comment on column app_user.position is
'职位';

comment on column app_user.phone is
'电话';

comment on column app_user.mobile is
'手机';

comment on column app_user.fax is
'传真';

comment on column app_user.address is
'地址';

comment on column app_user.zip is
'邮编';

comment on column app_user.photo is
'相片';

comment on column app_user.accessionTime is
'入职时间';

comment on column app_user.status is
'状态
1=激活
0=禁用
2=离职
';

comment on column app_user.delFlag is
'0=未删除
1=删除';

/*==============================================================*/
/* Table: appointment                                           */
/*==============================================================*/
create table appointment  (
   appointId            NUMBER(18)                      not null,
   userId               INTEGER,
   subject              VARCHAR2(128)                   not null,
   startTime            DATE                            not null,
   endTime              DATE                            not null,
   content              CLOB                            not null,
   notes                VARCHAR2(1000),
   location             VARCHAR2(150)                   not null,
   inviteEmails         VARCHAR2(1000),
   constraint PK_APPOINTMENT primary key (appointId)
);

comment on table appointment is
'约会管理';

comment on column appointment.userId is
'主键';

comment on column appointment.subject is
'主题';

comment on column appointment.startTime is
'开始时间';

comment on column appointment.endTime is
'结束时间';

comment on column appointment.content is
'约会内容';

comment on column appointment.notes is
'备注';

comment on column appointment.location is
'地点';

/*==============================================================*/
/* Table: arch_dispatch                                         */
/*==============================================================*/
create table arch_dispatch  (
   dispatchId           NUMBER(18)                      not null,
   archivesId           INTEGER,
   dispatchTime         DATE                            not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(128),
   isRead               SMALLINT,
   subject              VARCHAR2(256),
   readFeedback         VARCHAR2(1024),
   archUserType         SMALLINT                       default 0 not null,
   disRoleId            INTEGER,
   disRoleName          VARCHAR2(64),
   constraint PK_ARCH_DISPATCH primary key (dispatchId)
);

comment on column arch_dispatch.archUserType is
'0=阅读人员
1=承办人
2=分发负责人
';

/*==============================================================*/
/* Table: arch_flow_conf                                        */
/*==============================================================*/
create table arch_flow_conf  (
   configId             NUMBER(18)                      not null,
   processName          VARCHAR2(128)                   not null,
   processDefId         INTEGER                         not null,
   archType             SMALLINT                        not null,
   constraint PK_ARCH_FLOW_CONF primary key (configId)
);

comment on table arch_flow_conf is
'公文流程设置';

comment on column arch_flow_conf.archType is
'0=发文
1=收文';

/*==============================================================*/
/* Table: arch_hasten                                           */
/*==============================================================*/
create table arch_hasten  (
   recordId             NUMBER(18)                      not null,
   archivesId           INTEGER,
   content              VARCHAR2(1024),
   createtime           DATE,
   hastenFullname       VARCHAR2(64),
   handlerFullname      VARCHAR2(64),
   handlerUserId        INTEGER,
   constraint PK_ARCH_HASTEN primary key (recordId)
);

comment on column arch_hasten.content is
'催办内容';

comment on column arch_hasten.createtime is
'催办时间';

comment on column arch_hasten.hastenFullname is
'催办人';

comment on column arch_hasten.handlerFullname is
'承办人';

comment on column arch_hasten.handlerUserId is
'承办人ID';

/*==============================================================*/
/* Table: arch_rec_type                                         */
/*==============================================================*/
create table arch_rec_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   depId                INTEGER,
   constraint PK_ARCH_REC_TYPE primary key (typeId)
);

comment on column arch_rec_type.typeName is
'分类名称';

/*==============================================================*/
/* Table: arch_rec_user                                         */
/*==============================================================*/
create table arch_rec_user  (
   archRecId            NUMBER(18)                      not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(128)                   not null,
   depId                INTEGER                         not null,
   depName              VARCHAR2(128)                   not null,
   constraint PK_ARCH_REC_USER primary key (archRecId)
);

comment on column arch_rec_user.fullname is
'用户名';

comment on column arch_rec_user.depId is
'部门ID ';

comment on column arch_rec_user.depName is
'部门名称';

/*==============================================================*/
/* Table: arch_template                                         */
/*==============================================================*/
create table arch_template  (
   templateId           NUMBER(18)                      not null,
   typeId               INTEGER,
   tempName             VARCHAR2(128)                   not null,
   tempPath             VARCHAR2(256)                   not null,
   fileId               INTEGER                         not null,
   constraint PK_ARCH_TEMPLATE primary key (templateId)
);

comment on table arch_template is
'公文模板';

comment on column arch_template.typeId is
'所属类型';

comment on column arch_template.tempName is
'模板名称';

comment on column arch_template.tempPath is
'路径';

comment on column arch_template.fileId is
'文件ID';

/*==============================================================*/
/* Table: archives                                              */
/*==============================================================*/
create table archives  (
   archivesId           NUMBER(18)                      not null,
   typeId               INTEGER,
   typeName             VARCHAR2(128),
   archivesNo           VARCHAR2(100)                   not null,
   issueDep             VARCHAR2(128),
   depId                INTEGER,
   arc_typeId           INTEGER,
   subject              VARCHAR2(256)                   not null,
   createtime           DATE                            not null,
   issueDate            DATE                            not null,
   status               SMALLINT                        not null,
   shortContent         VARCHAR2(1024),
   fileCounts           INTEGER                        default 0 not null,
   privacyLevel         VARCHAR2(50)                   default '普通',
   urgentLevel          VARCHAR2(50)                   default '普通',
   issuer               VARCHAR2(50),
   issuerId             INTEGER,
   keywords             VARCHAR2(256),
   sources              VARCHAR2(50),
   archType             SMALLINT                       default 0 not null,
   recDepIds            VARCHAR2(2000),
   recDepNames          VARCHAR2(2000),
   handlerUids          VARCHAR2(256),
   handlerUnames        VARCHAR2(256),
   orgArchivesId        INTEGER,
   depSignNo            VARCHAR2(100),
   constraint PK_ARCHIVES primary key (archivesId)
);

comment on table archives is
'收发公文';

comment on column archives.typeId is
'公文类型';

comment on column archives.typeName is
'公文类型名称';

comment on column archives.archivesNo is
'发文字号';

comment on column archives.issueDep is
'发文机关或部门';

comment on column archives.depId is
'发文部门ID';

comment on column archives.subject is
'文件标题';

comment on column archives.issueDate is
'发布日期';

comment on column archives.status is
'公文状态
0=拟稿、修改状态
1=发文状态
2=归档状态';

comment on column archives.shortContent is
'内容简介';

comment on column archives.fileCounts is
'文件数';

comment on column archives.privacyLevel is
'秘密等级
普通
秘密
机密
绝密';

comment on column archives.urgentLevel is
'紧急程度
普通
紧急
特急
特提';

comment on column archives.issuer is
'发文人';

comment on column archives.issuerId is
'发文人ID';

comment on column archives.keywords is
'主题词';

comment on column archives.sources is
'公文来源
仅在收文中指定，发公文不需要指定
上级公文
下级公文';

comment on column archives.archType is
'0=发文
1=收文';

comment on column archives.recDepIds is
'用于存储接收公文的部门ID,使用,进行分开';

comment on column archives.recDepNames is
'用于存储接收公文的部门的名称，使用,进行分开';

comment on column archives.handlerUids is
'在收文中使用，多个用户ID用'',''分割';

comment on column archives.handlerUnames is
'用于收文，存储多个拟办用户名，用‘，’分割';

comment on column archives.orgArchivesId is
'用于收文时使用，指向原公文ID';

comment on column archives.depSignNo is
'用于收文时，部门对自身的公文自编号';

/*==============================================================*/
/* Table: archives_attend                                       */
/*==============================================================*/
create table archives_attend  (
   attendId             NUMBER(18)                      not null,
   archivesId           INTEGER                         not null,
   userID               INTEGER                         not null,
   fullname             VARCHAR2(128)                   not null,
   attendType           VARCHAR2(64)                    not null,
   executeTime          DATE,
   memo                 VARCHAR2(1024),
   constraint PK_ARCHIVES_ATTEND primary key (attendId)
);

comment on table archives_attend is
'发文拟稿参与人';

comment on column archives_attend.userID is
'用户ID';

comment on column archives_attend.fullname is
'姓名';

comment on column archives_attend.attendType is
'参与类型
1=校对人
2=审核人
3=缮印人
4=用印人';

comment on column archives_attend.executeTime is
'执行时间';

comment on column archives_attend.memo is
'备注';

/*==============================================================*/
/* Table: archives_dep                                          */
/*==============================================================*/
create table archives_dep  (
   archDepId            NUMBER(18)                      not null,
   signNo               VARCHAR2(128),
   depId                INTEGER                         not null,
   archivesId           INTEGER                         not null,
   subject              VARCHAR2(256)                   not null,
   status               SMALLINT                        not null,
   signTime             DATE,
   signFullname         VARCHAR2(64),
   signUserID           INTEGER,
   handleFeedback       VARCHAR2(1024),
   isMain               SMALLINT                       default 1 not null,
   constraint PK_ARCHIVES_DEP primary key (archDepId)
);

comment on column archives_dep.archDepId is
'主键';

comment on column archives_dep.signNo is
'自编号';

comment on column archives_dep.depId is
'收文部门';

comment on column archives_dep.archivesId is
'所属公文';

comment on column archives_dep.subject is
'公文标题';

comment on column archives_dep.status is
'签收状态
0=未签收
1=已签收';

comment on column archives_dep.signTime is
'签收日期';

comment on column archives_dep.signFullname is
'签收人';

comment on column archives_dep.handleFeedback is
'办理结果反馈';

comment on column archives_dep.isMain is
'主送、抄送
1=主送
0=抄送';

/*==============================================================*/
/* Table: archives_doc                                          */
/*==============================================================*/
create table archives_doc  (
   docId                NUMBER(18)                      not null,
   fileId               INTEGER,
   archivesId           INTEGER,
   creator              VARCHAR2(64),
   creatorId            INTEGER,
   menderId             INTEGER,
   mender               VARCHAR2(64),
   docName              VARCHAR2(128)                   not null,
   docStatus            SMALLINT                        not null,
   curVersion           INTEGER                         not null,
   docPath              VARCHAR2(128)                   not null,
   updatetime           DATE                            not null,
   createtime           DATE                            not null,
   constraint PK_ARCHIVES_DOC primary key (docId)
);

comment on column archives_doc.creator is
'拟稿人';

comment on column archives_doc.creatorId is
'拟稿人ID';

comment on column archives_doc.mender is
'修改人';

comment on column archives_doc.docName is
'文档名称';

comment on column archives_doc.docStatus is
'文档状态
0=修改中
1=修改完成';

comment on column archives_doc.curVersion is
'当前版本
取当前最新的版本';

comment on column archives_doc.docPath is
'文档路径';

comment on column archives_doc.updatetime is
'更新时间';

comment on column archives_doc.createtime is
'创建时间';

/*==============================================================*/
/* Table: archives_handle                                       */
/*==============================================================*/
create table archives_handle  (
   handleId             NUMBER(18)                      not null,
   archivesId           INTEGER                         not null,
   handleOpinion        VARCHAR2(1024),
   userId               INTEGER                         not null,
   userFullname         VARCHAR2(128)                   not null,
   createtime           DATE                            not null,
   fillTime             DATE,
   isPass               SMALLINT                       default 1 not null,
   constraint PK_ARCHIVES_HANDLE primary key (handleId)
);

comment on table archives_handle is
'公文拟办人';

comment on column archives_handle.isPass is
'0=尚未审批
1=通过审批
２=未通过审批';

/*==============================================================*/
/* Table: archives_type                                         */
/*==============================================================*/
create table archives_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   typeDesc             VARCHAR2(256),
   constraint PK_ARCHIVES_TYPE primary key (typeId)
);

comment on table archives_type is
'公文类型';

comment on column archives_type.typeName is
'类型名称';

comment on column archives_type.typeDesc is
'类型描述';

/*==============================================================*/
/* Table: assets_type                                           */
/*==============================================================*/
create table assets_type  (
   assetsTypeId         NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_ASSETS_TYPE primary key (assetsTypeId)
);

comment on column assets_type.typeName is
'分类名称';

/*==============================================================*/
/* Table: book                                                  */
/*==============================================================*/
create table book  (
   bookId               NUMBER(18)                      not null,
   typeId               INTEGER,
   bookName             VARCHAR2(128)                   not null,
   author               VARCHAR2(128)                   not null,
   isbn                 VARCHAR2(64)                    not null,
   publisher            VARCHAR2(128),
   price                NUMBER                          not null,
   location             VARCHAR2(128)                   not null,
   department           VARCHAR2(64)                    not null,
   amount               INTEGER                         not null,
   leftAmount           INTEGER                         not null,
   constraint PK_BOOK primary key (bookId)
);

comment on table book is
'图书';

/*==============================================================*/
/* Table: book_bor_ret                                          */
/*==============================================================*/
create table book_bor_ret  (
   recordId             NUMBER(18)                      not null,
   bookSnId             INTEGER,
   borrowTime           DATE                            not null,
   returnTime           DATE                            not null,
   lastReturnTime       DATE,
   borrowIsbn           VARCHAR2(128)                   not null,
   bookName             VARCHAR2(128)                   not null,
   registerName         VARCHAR2(32)                    not null,
   fullname             VARCHAR2(32)                    not null,
   constraint PK_BOOK_BOR_RET primary key (recordId)
);

comment on table book_bor_ret is
'图书借还表';

/*==============================================================*/
/* Table: book_sn                                               */
/*==============================================================*/
create table book_sn  (
   bookSnId             NUMBER(18)                      not null,
   bookId               INTEGER                         not null,
   bookSN               VARCHAR2(128)                   not null,
   status               SMALLINT                        not null,
   constraint PK_BOOK_SN primary key (bookSnId)
);

comment on column book_sn.status is
'借阅状态
0=未借出
1=借出
2=预订
3=注销';

/*==============================================================*/
/* Table: book_type                                             */
/*==============================================================*/
create table book_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_BOOK_TYPE primary key (typeId)
);

comment on table book_type is
'图书类别';

/*==============================================================*/
/* Table: cal_file                                              */
/*==============================================================*/
create table cal_file  (
   fileId               INTEGER                         not null,
   planId               INTEGER                         not null,
   constraint PK_CAL_FILE primary key (fileId, planId)
);

/*==============================================================*/
/* Table: calendar_plan                                         */
/*==============================================================*/
create table calendar_plan  (
   planId               NUMBER(18)                      not null,
   startTime            DATE,
   endTime              DATE,
   urgent               SMALLINT                        not null,
   content              VARCHAR2(1200)                  not null,
   status               SMALLINT                        not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(32),
   assignerId           INTEGER                         not null,
   assignerName         VARCHAR2(32),
   feedback             VARCHAR2(500),
   showStyle            SMALLINT                        not null,
   taskType             SMALLINT                        not null,
   constraint PK_CALENDAR_PLAN primary key (planId)
);

comment on table calendar_plan is
'日程安排';

comment on column calendar_plan.startTime is
'开始时间';

comment on column calendar_plan.endTime is
'结束时间';

comment on column calendar_plan.urgent is
'紧急程度
0=一般
1=重要
2=紧急';

comment on column calendar_plan.content is
'内容';

comment on column calendar_plan.status is
'状态
0=未完成
1=完成';

comment on column calendar_plan.userId is
'员工ID';

comment on column calendar_plan.fullname is
'员工名';

comment on column calendar_plan.assignerId is
'分配人';

comment on column calendar_plan.assignerName is
'分配人名';

comment on column calendar_plan.feedback is
'反馈意见';

comment on column calendar_plan.showStyle is
'显示方式
1=仅在任务中显示
2=在日程与任务中显示';

comment on column calendar_plan.taskType is
'任务类型
1=限期任务
2=非限期任务';

/*==============================================================*/
/* Table: car                                                   */
/*==============================================================*/
create table car  (
   carId                NUMBER(18)                      not null,
   carNo                VARCHAR2(128)                   not null,
   carType              VARCHAR2(64)                    not null,
   engineNo             VARCHAR2(128),
   buyInsureTime        DATE,
   auditTime            DATE,
   notes                VARCHAR2(500),
   factoryModel         VARCHAR2(64)                    not null,
   driver               VARCHAR2(32)                    not null,
   buyDate              DATE                            not null,
   status               SMALLINT                        not null,
   cartImage            VARCHAR2(128),
   constraint PK_CAR primary key (carId)
);

comment on table car is
'车辆信息';

comment on column car.carType is
'轿车
货车
商务车
';

comment on column car.buyInsureTime is
'购买保险时间';

comment on column car.auditTime is
'年审时间';

comment on column car.buyDate is
'购置日期';

comment on column car.status is
'当前状态
1=可用
2=维修中
0=报废';

/*==============================================================*/
/* Table: car_apply                                             */
/*==============================================================*/
create table car_apply  (
   applyId              NUMBER(18)                      not null,
   carId                INTEGER                         not null,
   department           VARCHAR2(64)                    not null,
   userFullname         VARCHAR2(32)                    not null,
   applyDate            DATE                            not null,
   reason               VARCHAR2(512)                   not null,
   startTime            DATE                            not null,
   endTime              DATE,
   userId               INTEGER                         not null,
   proposer             VARCHAR2(32)                    not null,
   mileage              NUMBER(18,2),
   oilUse               NUMBER(18,2),
   notes                VARCHAR2(128),
   approvalStatus       SMALLINT                        not null,
   constraint PK_CAR_APPLY primary key (applyId)
);

comment on table car_apply is
'车辆申请';

/*==============================================================*/
/* Table: cart_repair                                           */
/*==============================================================*/
create table cart_repair  (
   repairId             NUMBER(18)                      not null,
   carId                INTEGER,
   repairDate           DATE                            not null,
   reason               VARCHAR2(128)                   not null,
   executant            VARCHAR2(128)                   not null,
   notes                VARCHAR2(128),
   repairType           VARCHAR2(128)                   not null,
   fee                  NUMBER(18,2),
   constraint PK_CART_REPAIR primary key (repairId)
);

comment on column cart_repair.repairDate is
'维护日期';

comment on column cart_repair.reason is
'维护原因';

comment on column cart_repair.executant is
'经办人';

comment on column cart_repair.notes is
'备注';

comment on column cart_repair.repairType is
'维修类型
保养
维修';

comment on column cart_repair.fee is
'费用';

/*==============================================================*/
/* Table: company                                               */
/*==============================================================*/
create table company  (
   companyId            NUMBER(18)                      not null,
   companyNo            VARCHAR2(128),
   companyName          VARCHAR2(128)                   not null,
   companyDesc          VARCHAR2(4000),
   legalPerson          VARCHAR2(32),
   setup                DATE,
   phone                VARCHAR2(32),
   fax                  VARCHAR2(32),
   site                 VARCHAR2(128),
   logo                 VARCHAR2(128),
   constraint PK_COMPANY primary key (companyId)
);

comment on table company is
'公司信息';

/*==============================================================*/
/* Table: contract                                              */
/*==============================================================*/
create table contract  (
   contractId           NUMBER(18)                      not null,
   contractNo           VARCHAR2(64)                    not null,
   subject              VARCHAR2(128)                   not null,
   contractAmount       NUMBER                          not null,
   mainItem             VARCHAR2(4000),
   salesAfterItem       VARCHAR2(4000),
   validDate            DATE                            not null,
   expireDate           DATE                            not null,
   serviceDep           VARCHAR2(64),
   serviceMan           VARCHAR2(64),
   signupUser           VARCHAR2(64)                    not null,
   signupTime           DATE                            not null,
   creator              VARCHAR2(32)                    not null,
   createtime           DATE                            not null,
   projectId            INTEGER,
   consignAddress       VARCHAR2(128),
   consignee            VARCHAR2(32),
   constraint PK_CONTRACT primary key (contractId)
);

comment on column contract.contractNo is
'合同编号';

comment on column contract.subject is
'合同标题';

comment on column contract.contractAmount is
'合同金额';

comment on column contract.mainItem is
'主要条款';

comment on column contract.salesAfterItem is
'售后条款';

comment on column contract.validDate is
'生效日期';

comment on column contract.expireDate is
'有效期';

comment on column contract.serviceDep is
'维修部门';

comment on column contract.serviceMan is
'维修负责人';

comment on column contract.signupUser is
'签约人';

comment on column contract.signupTime is
'签约时间';

comment on column contract.creator is
'录入人';

comment on column contract.createtime is
'录入时间';

comment on column contract.projectId is
'所属项目';

comment on column contract.consignAddress is
'收货地址';

comment on column contract.consignee is
'收货人';

/*==============================================================*/
/* Table: contract_config                                       */
/*==============================================================*/
create table contract_config  (
   configId             NUMBER(18)                      not null,
   itemName             VARCHAR2(128)                   not null,
   contractId           INTEGER,
   itemSpec             VARCHAR2(128)                   not null,
   amount               NUMBER(18,2)                    not null,
   notes                VARCHAR2(200),
   constraint PK_CONTRACT_CONFIG primary key (configId)
);

comment on table contract_config is
'合同配置单';

comment on column contract_config.itemName is
'设备名称';

comment on column contract_config.itemSpec is
'设置规格';

comment on column contract_config.amount is
'数量';

comment on column contract_config.notes is
'备注';

/*==============================================================*/
/* Table: contract_file                                         */
/*==============================================================*/
create table contract_file  (
   fileId               INTEGER                         not null,
   contractId           INTEGER                         not null,
   constraint PK_CONTRACT_FILE primary key (fileId, contractId)
);

comment on table contract_file is
'合同附件';

/*==============================================================*/
/* Table: cus_connection                                        */
/*==============================================================*/
create table cus_connection  (
   connId               NUMBER(18)                      not null,
   customerId           INTEGER                         not null,
   contactor            VARCHAR2(32)                    not null,
   startDate            DATE                            not null,
   endDate              DATE                            not null,
   content              VARCHAR2(512)                   not null,
   notes                VARCHAR2(1000),
   creator              VARCHAR2(32),
   constraint PK_CUS_CONNECTION primary key (connId)
);

comment on table cus_connection is
'business connection ';

/*==============================================================*/
/* Table: cus_linkman                                           */
/*==============================================================*/
create table cus_linkman  (
   linkmanId            NUMBER(18)                      not null,
   customerId           INTEGER                         not null,
   fullname             VARCHAR2(32)                    not null,
   sex                  SMALLINT                        not null,
   position             VARCHAR2(32),
   phone                VARCHAR2(32),
   mobile               VARCHAR2(32)                    not null,
   fax                  VARCHAR2(32),
   email                VARCHAR2(100),
   msn                  VARCHAR2(100),
   qq                   VARCHAR2(64),
   birthday             DATE,
   homeAddress          VARCHAR2(128),
   homeZip              VARCHAR2(32),
   homePhone            VARCHAR2(32),
   hobby                VARCHAR2(100),
   isPrimary            SMALLINT                        not null,
   notes                VARCHAR2(500),
   constraint PK_CUS_LINKMAN primary key (linkmanId)
);

comment on table cus_linkman is
'客户联系人';

comment on column cus_linkman.customerId is
'所属客户';

comment on column cus_linkman.fullname is
'姓名';

comment on column cus_linkman.sex is
'性别';

comment on column cus_linkman.position is
'职位';

comment on column cus_linkman.phone is
'电话';

comment on column cus_linkman.mobile is
'手机';

comment on column cus_linkman.email is
'Email';

comment on column cus_linkman.msn is
'MSN';

comment on column cus_linkman.qq is
'QQ';

comment on column cus_linkman.birthday is
'生日';

comment on column cus_linkman.homeAddress is
'家庭住址';

comment on column cus_linkman.homeZip is
'邮编';

comment on column cus_linkman.homePhone is
'家庭电话';

comment on column cus_linkman.hobby is
'爱好';

comment on column cus_linkman.isPrimary is
'是否为主要联系人';

comment on column cus_linkman.notes is
'备注';

/*==============================================================*/
/* Table: customer                                              */
/*==============================================================*/
create table customer  (
   customerId           NUMBER(18)                      not null,
   customerNo           VARCHAR2(64)                    not null,
   industryType         VARCHAR2(64)                    not null,
   customerSource       VARCHAR2(64),
   customerType         SMALLINT                        not null,
   companyScale         INTEGER,
   customerName         VARCHAR2(128)                   not null,
   customerManager      VARCHAR2(32)                    not null,
   phone                VARCHAR2(32)                    not null,
   fax                  VARCHAR2(32),
   site                 VARCHAR2(128),
   email                VARCHAR2(128),
   state                VARCHAR2(32),
   city                 VARCHAR2(32),
   zip                  VARCHAR2(32),
   address              VARCHAR2(100),
   registerFun          NUMBER,
   turnOver             NUMBER,
   currencyUnit         VARCHAR2(32),
   otherDesc            VARCHAR2(800),
   principal            VARCHAR2(32),
   openBank             VARCHAR2(64),
   accountsNo           VARCHAR2(64),
   taxNo                VARCHAR2(64),
   notes                VARCHAR2(500),
   rights               SMALLINT                        not null,
   constraint PK_CUSTOMER primary key (customerId)
);

comment on table customer is
'客户信息';

comment on column customer.customerNo is
'客户号
自动生成';

comment on column customer.industryType is
'所属行业
有缺省的选择，也可以输入';

comment on column customer.customerSource is
'客户来源
可编辑，可添加

电话访问
网络
客户或朋友介绍';

comment on column customer.customerType is
'1=正式客户
2=重要客户
3＝潜在客户
4＝无效客户';

comment on column customer.companyScale is
'1=1-20人
2=20-50人
3=50-100人
4=100-200人
5=200-500人
6=500-1000 人
7=1000人以上
';

comment on column customer.customerName is
'客户名称
一般为公司名称';

comment on column customer.customerManager is
'负责该客户的经理';

comment on column customer.phone is
'电话';

comment on column customer.currencyUnit is
'注册资金及年营业额的货币单位
可选可编辑
人民币（默认）
美元
';

comment on column customer.rights is
'1=客户经理及上级经理有权查看
2=公开
3=共享人员有权查看';

/*==============================================================*/
/* Table: department                                            */
/*==============================================================*/
create table department  (
   depId                NUMBER(18)                      not null,
   depName              VARCHAR2(128)                   not null,
   depDesc              VARCHAR2(256),
   depLevel             INTEGER                         not null,
   parentId             INTEGER,
   path                 VARCHAR2(128),
   phone                VARCHAR2(64),
   fax                  VARCHAR2(64),
   constraint PK_DEPARTMENT primary key (depId)
);

comment on column department.depName is
'部门名称';

comment on column department.depDesc is
'部门描述';

comment on column department.depLevel is
'层次';

comment on column department.path is
'路径';

/*==============================================================*/
/* Table: depre_record                                          */
/*==============================================================*/
create table depre_record  (
   recordId             NUMBER(18)                      not null,
   assetsId             INTEGER                         not null,
   workCapacity         NUMBER(18,2),
   workGrossUnit        VARCHAR2(128),
   depreAmount          NUMBER(18,4)                    not null,
   calTime              DATE                            not null,
   constraint PK_DEPRE_RECORD primary key (recordId)
);

/*==============================================================*/
/* Table: depre_type                                            */
/*==============================================================*/
create table depre_type  (
   depreTypeId          NUMBER(18)                      not null,
   typeName             VARCHAR2(64)                    not null,
   deprePeriod          INTEGER                         not null,
   typeDesc             VARCHAR2(500),
   calMethod            SMALLINT                        not null,
   constraint PK_DEPRE_TYPE primary key (depreTypeId)
);

comment on table depre_type is
'depreciation type';

comment on column depre_type.deprePeriod is
'单位为月';

comment on column depre_type.calMethod is
'1=平均年限法
2=工作量法
加速折旧法
3=双倍余额递减法
4=年数总和法';

/*==============================================================*/
/* Table: diary                                                 */
/*==============================================================*/
create table diary  (
   diaryId              NUMBER(18)                      not null,
   userId               INTEGER,
   dayTime              DATE                            not null,
   content              CLOB                            not null,
   diaryType            SMALLINT                        not null,
   constraint PK_DIARY primary key (diaryId)
);

comment on column diary.userId is
'主键';

comment on column diary.diaryType is
'1=工作日志
0=个人日志';

/*==============================================================*/
/* Table: dictionary                                            */
/*==============================================================*/
create table dictionary  (
   dicId                NUMBER(18)                      not null,
   itemName             VARCHAR2(64)                    not null,
   itemValue            VARCHAR2(128)                   not null,
   descp                VARCHAR2(256),
   constraint PK_DICTIONARY primary key (dicId)
);

comment on table dictionary is
'数据字典';

/*==============================================================*/
/* Table: doc_file                                              */
/*==============================================================*/
create table doc_file  (
   fileId               INTEGER                         not null,
   docId                INTEGER                         not null,
   constraint PK_DOC_FILE primary key (fileId, docId)
);

/*==============================================================*/
/* Table: doc_folder                                            */
/*==============================================================*/
create table doc_folder  (
   folderId             NUMBER(18)                      not null,
   userId               INTEGER,
   folderName           VARCHAR2(128)                   not null,
   parentId             INTEGER,
   path                 VARCHAR2(128),
   isShared             SMALLINT                        not null,
   constraint PK_DOC_FOLDER primary key (folderId)
);

comment on column doc_folder.userId is
'主键';

comment on column doc_folder.folderName is
'目录名称';

comment on column doc_folder.parentId is
'父目录';

comment on column doc_folder.path is
'路径
为当前路径的＋上级路径
如当前ID为3，上级目录的路径为1.2，
则当前的路径为1.2.3.';

/*==============================================================*/
/* Table: doc_history                                           */
/*==============================================================*/
create table doc_history  (
   historyId            NUMBER(18)                      not null,
   docId                INTEGER                         not null,
   fileId               INTEGER                         not null,
   docName              VARCHAR2(128)                   not null,
   path                 VARCHAR2(128)                   not null,
   version              INTEGER                         not null,
   updatetime           DATE                            not null,
   mender               VARCHAR2(64)                    not null,
   constraint PK_DOC_HISTORY primary key (historyId)
);

comment on column doc_history.fileId is
'附件ID';

comment on column doc_history.docName is
'文档名称';

comment on column doc_history.path is
'路径';

comment on column doc_history.version is
'版本';

comment on column doc_history.updatetime is
'更新时间';

comment on column doc_history.mender is
'修改人';

/*==============================================================*/
/* Table: doc_privilege                                         */
/*==============================================================*/
create table doc_privilege  (
   privilegeId          NUMBER(18)                      not null,
   folderId             INTEGER,
   docId                INTEGER,
   rights               INTEGER                         not null,
   udrId                INTEGER,
   udrName              VARCHAR2(128),
   flag                 SMALLINT                        not null,
   fdFlag               SMALLINT                        not null,
   constraint PK_DOC_PRIVILEGE primary key (privilegeId)
);

comment on table doc_privilege is
'文档或目录的权限，只要是针对公共目录下的文档，个人的文档则不需要在这里进行权限的设置

某个目录或文档若没有指定某部门或某个人，即在本表中没有记录，
则表示可以进行所有的操作';

comment on column doc_privilege.rights is
'权限
文档或目录的读写修改权限
1=读
2=修改
4=删除

权限值可以为上面的值之和
如：3则代表进行读，修改的操作


';

comment on column doc_privilege.flag is
'1=user
2=deparment
3=role';

comment on column doc_privilege.fdFlag is
'缺省为文件夹权限
1=文档权限
0=文件夹权限';

/*==============================================================*/
/* Table: document                                              */
/*==============================================================*/
create table document  (
   docId                NUMBER(18)                      not null,
   docName              VARCHAR2(100)                   not null,
   content              CLOB,
   createtime           DATE                            not null,
   updatetime           DATE,
   folderId             INTEGER,
   userId               INTEGER,
   fullname             VARCHAR2(32)                    not null,
   haveAttach           SMALLINT,
   sharedUserIds        VARCHAR2(1000),
   sharedUserNames      VARCHAR2(1000),
   sharedDepIds         VARCHAR2(1000),
   sharedDepNames       VARCHAR2(1000),
   sharedRoleIds        VARCHAR2(1000),
   sharedRoleNames      VARCHAR2(1000),
   isShared             SMALLINT                        not null,
   constraint PK_DOCUMENT primary key (docId)
);

comment on table document is
'文档';

comment on column document.content is
'内容';

comment on column document.createtime is
'创建时间';

comment on column document.userId is
'主键';

comment on column document.sharedUserIds is
'共享员工ID';

comment on column document.sharedDepIds is
'共享部门ID';

comment on column document.sharedRoleIds is
'共享角色ID';

comment on column document.isShared is
'是否共享';

/*==============================================================*/
/* Table: duty                                                  */
/*==============================================================*/
create table duty  (
   dutyId               NUMBER(18)                      not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(32)                    not null,
   systemId             INTEGER                         not null,
   startTime            DATE                            not null,
   endTime              DATE,
   constraint PK_DUTY primary key (dutyId)
);

comment on table duty is
'排班';

comment on column duty.userId is
'员工ID';

comment on column duty.fullname is
'员工姓名';

comment on column duty.systemId is
'班制ID';

comment on column duty.startTime is
'开始时间';

comment on column duty.endTime is
'结束时间';

/*==============================================================*/
/* Table: duty_register                                         */
/*==============================================================*/
create table duty_register  (
   registerId           NUMBER(18)                      not null,
   registerDate         DATE                            not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(32)                    not null,
   regFlag              SMALLINT                        not null,
   regMins              INTEGER                         not null,
   reason               VARCHAR2(128),
   dayOfWeek            INTEGER                         not null,
   inOffFlag            SMALLINT                        not null,
   sectionId            INTEGER                         not null,
   constraint PK_DUTY_REGISTER primary key (registerId)
);

comment on column duty_register.registerDate is
'登记时间';

comment on column duty_register.userId is
'登记人';

comment on column duty_register.regFlag is
'登记标识
1=正常登记（上班，下班）
2＝迟到
3=早退
4＝休息
5＝旷工
6=放假

';

comment on column duty_register.regMins is
'迟到或早退分钟
正常上班时为0';

comment on column duty_register.reason is
'迟到原因';

comment on column duty_register.dayOfWeek is
'周几
1=星期日
..
7=日期六';

comment on column duty_register.inOffFlag is
'上下班标识
1=签到
2=签退';

/*==============================================================*/
/* Table: duty_section                                          */
/*==============================================================*/
create table duty_section  (
   sectionId            NUMBER(18)                      not null,
   sectionName          VARCHAR2(64)                    not null,
   startSignin          DATE                            not null,
   dutyStartTime        DATE                            not null,
   endSignin            DATE                            not null,
   earlyOffTime         DATE                            not null,
   dutyEndTime          DATE                            not null,
   signOutTime          DATE                            not null,
   constraint PK_DUTY_SECTION primary key (sectionId)
);

comment on table duty_section is
'班次
也称为班段，一天可以分为两个或三个班段
';

comment on column duty_section.startSignin is
'开始签到';

comment on column duty_section.dutyStartTime is
'上班时间';

comment on column duty_section.endSignin is
'签到结束时间';

comment on column duty_section.earlyOffTime is
'早退计时';

comment on column duty_section.dutyEndTime is
'下班时间';

comment on column duty_section.signOutTime is
'签退结束';

/*==============================================================*/
/* Table: duty_system                                           */
/*==============================================================*/
create table duty_system  (
   systemId             NUMBER(18)                      not null,
   systemName           VARCHAR2(128)                   not null,
   systemSetting        VARCHAR2(128)                   not null,
   systemDesc           VARCHAR2(256)                   not null,
   isDefault            SMALLINT                        not null,
   constraint PK_DUTY_SYSTEM primary key (systemId)
);

comment on table duty_system is
'轮班制
为公司设置上班的时间规定
如规定周一至周五上班，周六周日休息';

comment on column duty_system.systemName is
'班制名称';

comment on column duty_system.systemSetting is
'班次
班次的数据结构为：
如一员工周一至周五上班，上午9：00- 12：00 ，下午 13：30 -18:00

基数据结构为：
1|2,1|2,1|2,1|2,1|2,-,-
-代表正常休息日
1|2代表为一天上两个班次，1代表为班次表1的记录
';

comment on column duty_system.systemDesc is
'班次描述';

comment on column duty_system.isDefault is
'是否缺省
1＝缺省
0＝非缺省';

/*==============================================================*/
/* Table: emp_profile                                           */
/*==============================================================*/
create table emp_profile  (
   profileId            NUMBER(18)                      not null,
   profileNo            VARCHAR2(100)                   not null,
   userId               INTEGER                         not null,
   fullname             VARCHAR2(64)                    not null,
   address              VARCHAR2(128),
   birthday             DATE,
   homeZip              VARCHAR2(32),
   sex                  VARCHAR2(32),
   marriage             VARCHAR2(32),
   designation          VARCHAR2(64),
   jobId                INTEGER                         not null,
   position             VARCHAR2(128)                   not null,
   phone                VARCHAR2(64),
   mobile               VARCHAR2(64),
   openBank             VARCHAR2(100),
   bankNo               VARCHAR2(100),
   qq                   VARCHAR2(64),
   email                VARCHAR2(128),
   hobby                VARCHAR2(300),
   religion             VARCHAR2(100),
   party                VARCHAR2(100),
   nationality          VARCHAR2(100),
   race                 VARCHAR2(100),
   birthPlace           VARCHAR2(128),
   eduDegree            VARCHAR2(100),
   eduMajor             VARCHAR2(100),
   eduCollege           VARCHAR2(128),
   startWorkDate        DATE,
   eduCase              VARCHAR2(2048),
   awardPunishCase      VARCHAR2(2048),
   trainingCase         VARCHAR2(2048),
   workCase             VARCHAR2(2048),
   idCard               VARCHAR2(64),
   photo                VARCHAR2(128),
   standardMiNo         VARCHAR2(100),
   standardMoney        NUMBER(18,2),
   standardName         VARCHAR2(128),
   standardId           INTEGER,
   creator              VARCHAR2(64),
   createtime           DATE,
   checkName            VARCHAR2(128),
   checktime            DATE,
   opprovalOpinion      VARCHAR2(1024),
   approvalStatus       SMALLINT                       default 0,
   memo                 VARCHAR2(1024),
   depName              VARCHAR2(100),
   depId                INTEGER,
   delFlag              SMALLINT                       default 0 not null,
   constraint PK_EMP_PROFILE primary key (profileId)
);

comment on table emp_profile is
'员工档案';

comment on column emp_profile.profileNo is
'档案编号';

comment on column emp_profile.fullname is
'员工姓名';

comment on column emp_profile.address is
'家庭地址';

comment on column emp_profile.birthday is
'出生日期';

comment on column emp_profile.homeZip is
'家庭邮编';

comment on column emp_profile.marriage is
'婚姻状况
已婚
未婚';

comment on column emp_profile.phone is
'电话号码';

comment on column emp_profile.mobile is
'手机号码';

comment on column emp_profile.openBank is
'开户银行';

comment on column emp_profile.bankNo is
'银行账号';

comment on column emp_profile.qq is
'QQ号码';

comment on column emp_profile.email is
'电子邮箱';

comment on column emp_profile.hobby is
'爱好';

comment on column emp_profile.religion is
'宗教信仰';

comment on column emp_profile.party is
'政治面貌';

comment on column emp_profile.nationality is
'国籍';

comment on column emp_profile.race is
'民族';

comment on column emp_profile.birthPlace is
'出生地';

comment on column emp_profile.eduDegree is
'学历';

comment on column emp_profile.eduMajor is
'专业';

comment on column emp_profile.eduCollege is
'毕业院校';

comment on column emp_profile.startWorkDate is
'参加工作时间';

comment on column emp_profile.eduCase is
'教育背景';

comment on column emp_profile.awardPunishCase is
'奖惩情况';

comment on column emp_profile.trainingCase is
'培训情况';

comment on column emp_profile.workCase is
'工作经历';

comment on column emp_profile.idCard is
'身份证号';

comment on column emp_profile.photo is
'照片';

comment on column emp_profile.standardMiNo is
'薪酬标准编号';

comment on column emp_profile.standardMoney is
'薪酬标准金额';

comment on column emp_profile.standardName is
'薪酬标准单名称';

comment on column emp_profile.standardId is
'薪酬标准单编号';

comment on column emp_profile.creator is
'建档人';

comment on column emp_profile.createtime is
'建档时间';

comment on column emp_profile.checkName is
'审核人';

comment on column emp_profile.checktime is
'审核时间';

comment on column emp_profile.approvalStatus is
'核审状态
0=未审批
1=通过审核
2=未通过审核';

comment on column emp_profile.memo is
'备注';

comment on column emp_profile.depName is
'所属部门或公司';

comment on column emp_profile.depId is
'所属部门Id';

comment on column emp_profile.delFlag is
'删除状态
0=未删除
1=删除';

/*==============================================================*/
/* Table: errands_register                                      */
/*==============================================================*/
create table errands_register  (
   dateId               NUMBER(18)                      not null,
   userId               INTEGER                         not null,
   descp                VARCHAR2(500)                   not null,
   startTime            DATE                            not null,
   endTime              DATE                            not null,
   approvalId           INTEGER                         not null,
   status               SMALLINT                        not null,
   approvalOption       VARCHAR2(500),
   approvalName         VARCHAR2(128)                   not null,
   flag                 SMALLINT,
   constraint PK_ERRANDS_REGISTER primary key (dateId)
);

comment on table errands_register is
'请假、加班、外出登记';

comment on column errands_register.userId is
'外出/加班人员';

comment on column errands_register.descp is
'描述';

comment on column errands_register.startTime is
'开始日期';

comment on column errands_register.endTime is
'结束日期';

comment on column errands_register.approvalId is
'审批人ID';

comment on column errands_register.status is
'审批状态
0=未审批
1=通过审批
2=不通过审批';

comment on column errands_register.approvalOption is
'审批意见';

comment on column errands_register.approvalName is
'审批人';

comment on column errands_register.flag is
'标识
0=加班
1=请假
2=外出';

/*==============================================================*/
/* Table: file_attach                                           */
/*==============================================================*/
create table file_attach  (
   fileId               NUMBER(18)                      not null,
   fileName             VARCHAR2(128)                   not null,
   filePath             VARCHAR2(128)                   not null,
   createtime           DATE                            not null,
   ext                  VARCHAR2(32),
   fileType             VARCHAR2(32)                    not null,
   note                 VARCHAR2(1024),
   creator              VARCHAR2(32)                    not null,
   constraint PK_FILE_ATTACH primary key (fileId)
);

comment on table file_attach is
'附件';

comment on column file_attach.fileName is
'文件名';

comment on column file_attach.filePath is
'文件路径';

comment on column file_attach.createtime is
'创建时间';

comment on column file_attach.ext is
'扩展名';

comment on column file_attach.fileType is
'附件类型
如：邮件附件';

comment on column file_attach.note is
'说明';

comment on column file_attach.creator is
'上传者';

/*==============================================================*/
/* Table: fixed_assets                                          */
/*==============================================================*/
create table fixed_assets  (
   assetsId             NUMBER(18)                      not null,
   assetsNo             VARCHAR2(128),
   assetsName           VARCHAR2(128)                   not null,
   model                VARCHAR2(64),
   assetsTypeId         INTEGER                         not null,
   manufacturer         VARCHAR2(64),
   manuDate             DATE,
   buyDate              DATE                            not null,
   beDep                VARCHAR2(64)                    not null,
   custodian            VARCHAR2(32),
   notes                VARCHAR2(500),
   remainValRate        NUMBER(18,6)                    not null,
   depreTypeId          INTEGER                         not null,
   startDepre           DATE,
   intendTerm           NUMBER(18,2),
   intendWorkGross      NUMBER(18,2),
   workGrossUnit        VARCHAR2(128),
   assetValue           NUMBER(18,4)                    not null,
   assetCurValue        NUMBER(18,4)                    not null,
   depreRate            NUMBER(18,2),
   defPerWorkGross      NUMBER(18,2),
   constraint PK_FIXED_ASSETS primary key (assetsId)
);

comment on column fixed_assets.intendWorkGross is
'当折旧的方法选择用工作量法进行计算时，才需要填写';

/*==============================================================*/
/* Table: form_data                                             */
/*==============================================================*/
create table form_data  (
   dataId               NUMBER(18)                      not null,
   formId               INTEGER                         not null,
   fieldLabel           VARCHAR2(128)                   not null,
   fieldName            VARCHAR2(64)                    not null,
   intValue             INTEGER,
   longValue            INTEGER,
   decValue             NUMBER(18,4),
   dateValue            DATE,
   strValue             VARCHAR2(4000),
   boolValue            SMALLINT,
   blobValue            BLOB,
   isShowed             SMALLINT                        not null,
   textValue            CLOB,
   fieldType            VARCHAR2(32)                    not null,
   constraint PK_FORM_DATA primary key (dataId)
);

comment on column form_data.formId is
'所属表单';

comment on column form_data.fieldLabel is
'字段标签';

comment on column form_data.fieldName is
'字段名称';

comment on column form_data.intValue is
'整数值';

comment on column form_data.longValue is
'长整值';

comment on column form_data.decValue is
'精度值';

comment on column form_data.dateValue is
'日期值';

comment on column form_data.strValue is
'字符值';

comment on column form_data.boolValue is
'布尔值';

comment on column form_data.blobValue is
'对象值';

comment on column form_data.isShowed is
'是否显示
1=显示
0=不显示';

/*==============================================================*/
/* Table: form_def                                              */
/*==============================================================*/
create table form_def  (
   formDefId            NUMBER(18)                      not null,
   formName             VARCHAR2(128)                   not null,
   columns              INTEGER                         not null,
   isEnabled            SMALLINT                       default 1 not null,
   activityName         VARCHAR2(128)                   not null,
   deployId             VARCHAR2(128)                   not null,
   extDef               CLOB,
   formView             VARCHAR2(128),
   constraint PK_FORM_DEF primary key (formDefId)
);

comment on column form_def.formName is
'表单名称';

comment on column form_def.columns is
'总列数';

comment on column form_def.isEnabled is
'是否可用';

comment on column form_def.activityName is
'节点名称';

comment on column form_def.deployId is
'Jbpm流程发布ID';

comment on column form_def.extDef is
'表单定义';

comment on column form_def.formView is
'流程定义View';

/*==============================================================*/
/* Table: fun_url                                               */
/*==============================================================*/
create table fun_url  (
   urlId                NUMBER(18)                      not null,
   functionId           INTEGER                         not null,
   urlPath              VARCHAR2(128)                   not null,
   constraint PK_FUN_URL primary key (urlId)
);

/*==============================================================*/
/* Table: goods_apply                                           */
/*==============================================================*/
create table goods_apply  (
   applyId              NUMBER(18)                      not null,
   goodsId              INTEGER                         not null,
   applyDate            DATE                            not null,
   applyNo              VARCHAR2(128)                   not null,
   useCounts            INTEGER                         not null,
   userId               INTEGER                         not null,
   proposer             VARCHAR2(32)                    not null,
   notes                VARCHAR2(500),
   approvalStatus       SMALLINT                        not null,
   constraint PK_GOODS_APPLY primary key (applyId)
);

comment on table goods_apply is
'办公用品出库';

comment on column goods_apply.applyNo is
'申请号,按系统时间产生，如GA20091002-0001';

comment on column goods_apply.approvalStatus is
'审批状态
1=通过审批
0=未审批
';

/*==============================================================*/
/* Table: hire_issue                                            */
/*==============================================================*/
create table hire_issue  (
   hireId               NUMBER(18)                      not null,
   title                VARCHAR2(128)                   not null,
   startDate            DATE                            not null,
   endDate              DATE                            not null,
   hireCount            INTEGER                         not null,
   jobName              VARCHAR2(128)                   not null,
   jobCondition         VARCHAR2(1024),
   regFullname          VARCHAR2(128)                   not null,
   regDate              DATE                            not null,
   modifyFullname       VARCHAR2(32),
   modifyDate           DATE,
   checkFullname        VARCHAR2(32),
   checkOpinion         VARCHAR2(512),
   checkDate            DATE,
   status               SMALLINT                        not null,
   memo                 VARCHAR2(1024),
   constraint PK_HIRE_ISSUE primary key (hireId)
);

comment on table hire_issue is
'招聘发布';

comment on column hire_issue.title is
'招聘信息标题';

comment on column hire_issue.startDate is
'开始时间';

comment on column hire_issue.endDate is
'结束时间';

comment on column hire_issue.hireCount is
'招聘人数';

comment on column hire_issue.jobName is
'职位名称';

comment on column hire_issue.jobCondition is
'招聘要求(条件)';

comment on column hire_issue.regFullname is
'登记人姓名';

comment on column hire_issue.regDate is
'登记时间';

comment on column hire_issue.modifyFullname is
'变更人姓名';

comment on column hire_issue.modifyDate is
'变更时间';

comment on column hire_issue.checkFullname is
'审核人姓名';

comment on column hire_issue.checkOpinion is
'审核意见';

comment on column hire_issue.checkDate is
'审批时间';

comment on column hire_issue.status is
'状态
1=通过审核
0=未审核
2=审核不通过';

comment on column hire_issue.memo is
'备注';

/*==============================================================*/
/* Table: holiday_record                                        */
/*==============================================================*/
create table holiday_record  (
   recordId             NUMBER(18)                      not null,
   startTime            DATE                            not null,
   endTime              DATE                            not null,
   descp                VARCHAR2(512),
   fullname             VARCHAR2(32),
   userId               INTEGER,
   isAll                SMALLINT                        not null,
   constraint PK_HOLIDAY_RECORD primary key (recordId)
);

comment on table holiday_record is
'放假记录';

comment on column holiday_record.startTime is
'开始日期';

comment on column holiday_record.endTime is
'结束日期';

comment on column holiday_record.descp is
'假期描述';

comment on column holiday_record.fullname is
'用户名';

comment on column holiday_record.userId is
'所属用户
若为某员工的假期，则为员工自己的id';

comment on column holiday_record.isAll is
'是否为全公司假期
1=是
0=否';

/*==============================================================*/
/* Table: in_message                                            */
/*==============================================================*/
create table in_message  (
   receiveId            NUMBER(18)                      not null,
   messageId            INTEGER,
   userId               INTEGER,
   readFlag             SMALLINT                        not null,
   delFlag              SMALLINT                        not null,
   userFullname         VARCHAR2(32)                    not null,
   constraint PK_IN_MESSAGE primary key (receiveId)
);

comment on column in_message.userId is
'主键';

comment on column in_message.readFlag is
'1=has red
0=unread';

/*==============================================================*/
/* Table: in_stock                                              */
/*==============================================================*/
create table in_stock  (
   buyId                NUMBER(18)                      not null,
   goodsId              INTEGER                         not null,
   providerName         VARCHAR2(128),
   stockNo              VARCHAR2(128)                   not null,
   price                NUMBER(18,2),
   inCounts             INTEGER,
   amount               NUMBER(18,2)                    not null,
   inDate               DATE                            not null,
   buyer                VARCHAR2(128),
   constraint PK_IN_STOCK primary key (buyId)
);

comment on table in_stock is
'办公用品入库需要同时更新办公用品表的库存';

/*==============================================================*/
/* Table: index_display                                         */
/*==============================================================*/
create table index_display  (
   indexId              NUMBER(18)                      not null,
   portalId             VARCHAR2(64)                    not null,
   userId               INTEGER                         not null,
   colNum               INTEGER                         not null,
   rowsNum              INTEGER                         not null,
   constraint PK_INDEX_DISPLAY primary key (indexId)
);

comment on table index_display is
'每个员工可以设置自己的显示方式';

comment on column index_display.portalId is
'Portal ID';

comment on column index_display.userId is
'用户ID';

comment on column index_display.colNum is
'列号';

comment on column index_display.rowsNum is
'行号';

/*==============================================================*/
/* Table: job                                                   */
/*==============================================================*/
create table job  (
   jobId                NUMBER(18)                      not null,
   jobName              VARCHAR2(128)                   not null,
   depId                INTEGER                         not null,
   memo                 VARCHAR2(512),
   delFlag              SMALLINT                       default 0 not null,
   constraint PK_JOB primary key (jobId)
);

comment on column job.jobName is
'职位名称';

comment on column job.depId is
'所属部门';

comment on column job.memo is
'备注';

comment on column job.delFlag is
'0=未删除
1=删除';

/*==============================================================*/
/* Table: job_change                                            */
/*==============================================================*/
create table job_change  (
   changeId             NUMBER(18)                      not null,
   profileId            INTEGER                         not null,
   profileNo            VARCHAR2(64)                    not null,
   userName             VARCHAR2(64),
   orgJobId             INTEGER                         not null,
   orgJobName           VARCHAR2(64)                    not null,
   newJobId             INTEGER                         not null,
   newJobName           VARCHAR2(64)                    not null,
   orgStandardId        INTEGER,
   orgStandardNo        VARCHAR2(64),
   orgStandardName      VARCHAR2(64),
   orgDepId             INTEGER,
   orgDepName           VARCHAR2(128),
   orgTotalMoney        NUMBER(18,2),
   newStandardId        INTEGER,
   newStandardNo        VARCHAR2(64),
   newStandardName      VARCHAR2(64),
   newDepId             INTEGER,
   newDepName           VARCHAR2(128),
   newTotalMoney        NUMBER(18,2),
   changeReason         VARCHAR2(1024),
   regTime              DATE,
   regName              VARCHAR2(64),
   checkName            VARCHAR2(64),
   checkTime            DATE,
   checkOpinion         VARCHAR2(512),
   status               SMALLINT,
   memo                 VARCHAR2(1024),
   constraint PK_JOB_CHANGE primary key (changeId)
);

comment on column job_change.orgStandardId is
'原薪酬标准单ID';

comment on column job_change.status is
'状态

-1=草稿
0=提交审批
1=通过审批
2=未通过审批
';

/*==============================================================*/
/* Table: leader_read                                           */
/*==============================================================*/
create table leader_read  (
   readId               NUMBER(18)                      not null,
   leaderName           VARCHAR2(64)                    not null,
   userId               INTEGER                         not null,
   leaderOpinion        VARCHAR2(128),
   createtime           DATE                            not null,
   archivesId           INTEGER,
   depLevel             INTEGER,
   depName              VARCHAR2(128),
   isPass               SMALLINT                        not null,
   checkName            VARCHAR2(128),
   constraint PK_LEADER_READ primary key (readId)
);

comment on table leader_read is
'领导批示';

comment on column leader_read.isPass is
'0=尚未批
1=审批通过
2=审批未通过';

/*==============================================================*/
/* Table: mail                                                  */
/*==============================================================*/
create table mail  (
   mailId               NUMBER(18)                      not null,
   sender               VARCHAR2(32)                    not null,
   senderId             INTEGER                         not null,
   importantFlag        SMALLINT                        not null,
   sendTime             DATE                            not null,
   content              CLOB                            not null,
   subject              VARCHAR2(256)                   not null,
   copyToNames          VARCHAR2(1000),
   copyToIDs            VARCHAR2(1000),
   recipientNames       VARCHAR2(1000)                  not null,
   recipientIDs         VARCHAR2(1000)                  not null,
   mailStatus           SMALLINT                        not null,
   fileIds              VARCHAR2(500),
   filenames            VARCHAR2(500),
   constraint PK_MAIL primary key (mailId)
);

comment on table mail is
'邮件';

comment on column mail.importantFlag is
'1=一般
2=重要
3=非常重要';

comment on column mail.content is
'邮件内容';

comment on column mail.subject is
'邮件标题';

comment on column mail.copyToNames is
'抄送人姓名列表';

comment on column mail.copyToIDs is
'抄送人ID列表
用'',''分开';

comment on column mail.recipientNames is
'收件人姓名列表';

comment on column mail.recipientIDs is
'收件人ID列表
用'',''分隔';

comment on column mail.mailStatus is
'邮件状态
1=正式邮件
0=草稿邮件';

comment on column mail.fileIds is
'附件Ids，多个附件的ID，通过,分割';

comment on column mail.filenames is
'附件名称列表，通过,进行分割';

/*==============================================================*/
/* Table: mail_attach                                           */
/*==============================================================*/
create table mail_attach  (
   mailId               INTEGER                         not null,
   fileId               INTEGER                         not null,
   constraint PK_MAIL_ATTACH primary key (mailId, fileId)
);

/*==============================================================*/
/* Table: mail_box                                              */
/*==============================================================*/
create table mail_box  (
   boxId                NUMBER(18)                      not null,
   mailId               INTEGER                         not null,
   folderId             INTEGER                         not null,
   userId               INTEGER,
   sendTime             DATE                            not null,
   delFlag              SMALLINT                        not null,
   readFlag             SMALLINT                        not null,
   replyFlag            SMALLINT                        not null,
   note                 VARCHAR2(256),
   constraint PK_MAIL_BOX primary key (boxId)
);

comment on table mail_box is
'收件箱';

comment on column mail_box.userId is
'主键';

comment on column mail_box.delFlag is
'del=1则代表删除';

comment on column mail_box.note is
'note';

/*==============================================================*/
/* Table: mail_folder                                           */
/*==============================================================*/
create table mail_folder  (
   folderId             NUMBER(18)                      not null,
   userId               INTEGER,
   folderName           VARCHAR2(128)                   not null,
   parentId             INTEGER,
   depLevel             INTEGER                         not null,
   path                 VARCHAR2(256),
   isPublic             SMALLINT                        not null,
   folderType           SMALLINT                        not null,
   constraint PK_MAIL_FOLDER primary key (folderId)
);

comment on column mail_folder.folderId is
'文件夹编号';

comment on column mail_folder.userId is
'主键';

comment on column mail_folder.folderName is
'文件夹名称';

comment on column mail_folder.parentId is
'父目录';

comment on column mail_folder.depLevel is
'目录层';

comment on column mail_folder.isPublic is
'1=表示共享，则所有的员工均可以使用该文件夹
0=私人文件夹';

comment on column mail_folder.folderType is
'文件夹类型
1=收信箱
2=发信箱
3=草稿箱
4=删除箱
10=其他';

/*==============================================================*/
/* Table: meeting                                               */
/*==============================================================*/
create table meeting  (
   mettingId            NUMBER(18)                      not null,
   holdTime             DATE,
   holdLocation         VARCHAR2(128),
   meetingName          VARCHAR2(128),
   attendUsers          VARCHAR2(128),
   holdDep              VARCHAR2(128),
   holdDepId            INTEGER,
   shortDesc            VARCHAR2(256),
   isFeedback           SMALLINT                        not null,
   summary              CLOB,
   constraint PK_MEETING primary key (mettingId)
);

/*==============================================================*/
/* Table: meeting_attend                                        */
/*==============================================================*/
create table meeting_attend  (
   attendId             NUMBER(18)                      not null,
   mettingId            INTEGER                         not null,
   userName             VARCHAR2(64),
   userId               INTEGER,
   depName              VARCHAR2(100),
   depId                INTEGER,
   attendType           SMALLINT                       default 0 not null,
   feedback             VARCHAR2(1024),
   signTime             DATE,
   signName             VARCHAR2(32)                    not null,
   constraint PK_MEETING_ATTEND primary key (attendId)
);

comment on table meeting_attend is
'会议参与部门或人员';

comment on column meeting_attend.attendType is
'参与类型
0=user
1=department';

/*==============================================================*/
/* Table: meeting_file                                          */
/*==============================================================*/
create table meeting_file  (
   mettingId            INTEGER                         not null,
   fileId               INTEGER                         not null,
   constraint PK_MEETING_FILE primary key (mettingId, fileId)
);

/*==============================================================*/
/* Table: news                                                  */
/*==============================================================*/
create table news  (
   newsId               NUMBER(18)                      not null,
   typeId               INTEGER                         not null,
   subjectIcon          VARCHAR2(128),
   subject              VARCHAR2(128)                   not null,
   author               VARCHAR2(32)                    not null,
   createtime           DATE                            not null,
   replyCounts          INTEGER,
   viewCounts           INTEGER,
   issuer               VARCHAR2(32)                    not null,
   content              CLOB                            not null,
   updateTime           DATE                            not null,
   status               SMALLINT                        not null,
   isDeskImage          SMALLINT,
   constraint PK_NEWS primary key (newsId)
);

comment on table news is
'新闻';

comment on column news.newsId is
'ID';

comment on column news.subject is
'新闻标题';

comment on column news.author is
'作者';

comment on column news.createtime is
'创建时间';

comment on column news.viewCounts is
'浏览数';

comment on column news.content is
'内容';

comment on column news.status is
'
0=待审核
1=审核通过';

comment on column news.isDeskImage is
'是否为桌面新闻';

/*==============================================================*/
/* Table: news_comment                                          */
/*==============================================================*/
create table news_comment  (
   commentId            NUMBER(18)                      not null,
   newsId               INTEGER                         not null,
   content              VARCHAR2(500)                   not null,
   createtime           DATE                            not null,
   fullname             VARCHAR2(32)                    not null,
   userId               INTEGER                         not null,
   constraint PK_NEWS_COMMENT primary key (commentId)
);

/*==============================================================*/
/* Table: news_type                                             */
/*==============================================================*/
create table news_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   sn                   INTEGER                         not null,
   constraint PK_NEWS_TYPE primary key (typeId)
);

comment on table news_type is
'新闻类型';

/*==============================================================*/
/* Table: notice                                                */
/*==============================================================*/
create table notice  (
   noticeId             NUMBER(18)                      not null,
   postName             VARCHAR2(128)                   not null,
   noticeTitle          VARCHAR2(128)                   not null,
   noticeContent        VARCHAR2(3000),
   effectiveDate        DATE,
   expirationDate       DATE,
   state                SMALLINT                        not null,
   constraint PK_NOTICE primary key (noticeId)
);

comment on table notice is
'公告';

/*==============================================================*/
/* Table: office_goods                                          */
/*==============================================================*/
create table office_goods  (
   goodsId              NUMBER(18)                      not null,
   typeId               INTEGER                         not null,
   goodsName            VARCHAR2(128)                   not null,
   goodsNo              VARCHAR2(128)                   not null,
   specifications       VARCHAR2(256)                   not null,
   unit                 VARCHAR2(64)                    not null,
   isWarning            SMALLINT                        not null,
   notes                VARCHAR2(500),
   stockCounts          INTEGER                         not null,
   warnCounts           INTEGER                         not null,
   constraint PK_OFFICE_GOODS primary key (goodsId)
);

comment on table office_goods is
'办公用品';

comment on column office_goods.typeId is
'所属分类';

comment on column office_goods.goodsName is
'物品名称';

comment on column office_goods.goodsNo is
'编号';

comment on column office_goods.specifications is
'规格';

comment on column office_goods.unit is
'计量单位';

comment on column office_goods.isWarning is
'是否启用库存警示';

comment on column office_goods.notes is
'备注';

comment on column office_goods.stockCounts is
'库存总数';

comment on column office_goods.warnCounts is
'最低库存数';

/*==============================================================*/
/* Table: office_goods_type                                     */
/*==============================================================*/
create table office_goods_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_OFFICE_GOODS_TYPE primary key (typeId)
);

comment on table office_goods_type is
'办公用品类型';

comment on column office_goods_type.typeName is
'分类名称';

/*==============================================================*/
/* Table: phone_book                                            */
/*==============================================================*/
create table phone_book  (
   phoneId              NUMBER(18)                      not null,
   fullname             VARCHAR2(128)                   not null,
   title                VARCHAR2(32)                    not null,
   birthday             DATE,
   nickName             VARCHAR2(32),
   duty                 VARCHAR2(50),
   spouse               VARCHAR2(32),
   childs               VARCHAR2(40),
   companyName          VARCHAR2(100),
   companyAddress       VARCHAR2(128),
   companyPhone         VARCHAR2(32),
   companyFax           VARCHAR2(32),
   homeAddress          VARCHAR2(128),
   homeZip              VARCHAR2(12),
   mobile               VARCHAR2(32),
   phone                VARCHAR2(32),
   email                VARCHAR2(32),
   QQ                   VARCHAR2(64),
   MSN                  VARCHAR2(128),
   note                 VARCHAR2(500),
   userId               INTEGER                         not null,
   groupId              INTEGER,
   isShared             SMALLINT                        not null,
   constraint PK_PHONE_BOOK primary key (phoneId)
);

comment on table phone_book is
'通讯簿';

comment on column phone_book.title is
'先生
女士
小姐';

/*==============================================================*/
/* Table: phone_group                                           */
/*==============================================================*/
create table phone_group  (
   groupId              NUMBER(18)                      not null,
   groupName            VARCHAR2(128)                   not null,
   isShared             SMALLINT                        not null,
   SN                   INTEGER                         not null,
   userId               INTEGER                         not null,
   constraint PK_PHONE_GROUP primary key (groupId)
);

comment on column phone_group.groupName is
'分组名称';

comment on column phone_group.isShared is
'1=共享
0=私有';

/*==============================================================*/
/* Table: plan_attend                                           */
/*==============================================================*/
create table plan_attend  (
   attendId             NUMBER(18)                      not null,
   depId                INTEGER,
   userId               INTEGER,
   planId               INTEGER                         not null,
   isDep                SMALLINT                        not null,
   isPrimary            SMALLINT,
   constraint PK_PLAN_ATTEND primary key (attendId)
);

comment on column plan_attend.isDep is
'是否为部门';

comment on column plan_attend.isPrimary is
'是否负责人';

/*==============================================================*/
/* Table: plan_file                                             */
/*==============================================================*/
create table plan_file  (
   fileId               INTEGER                         not null,
   planId               INTEGER                         not null,
   constraint PK_PLAN_FILE primary key (fileId, planId)
);

/*==============================================================*/
/* Table: plan_type                                             */
/*==============================================================*/
create table plan_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_PLAN_TYPE primary key (typeId)
);

comment on table plan_type is
'计划类型';

comment on column plan_type.typeName is
'类别名称';

/*==============================================================*/
/* Table: pro_definition                                        */
/*==============================================================*/
create table pro_definition  (
   defId                NUMBER(18)                      not null,
   typeId               INTEGER,
   name                 VARCHAR2(256)                   not null,
   description          VARCHAR2(1024),
   createtime           DATE,
   deployId             VARCHAR2(64)                    not null,
   defXml               CLOB,
   drawDefXml           CLOB,
   isDefault            SMALLINT                       default 0 not null,
   constraint PK_PRO_DEFINITION primary key (defId)
);

comment on table pro_definition is
'流程定义';

comment on column pro_definition.typeId is
'分类ID';

comment on column pro_definition.name is
'流程的名称';

comment on column pro_definition.description is
'描述';

comment on column pro_definition.createtime is
'创建时间';

comment on column pro_definition.deployId is
'Jbpm 工作流id';

comment on column pro_definition.defXml is
'流程定义XML';

comment on column pro_definition.isDefault is
'是否缺省
1=是
0=否';

/*==============================================================*/
/* Table: pro_type                                              */
/*==============================================================*/
create table pro_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_PRO_TYPE primary key (typeId)
);

comment on table pro_type is
'流程分类';

comment on column pro_type.typeId is
'类别ID';

comment on column pro_type.typeName is
'分类名称';

/*==============================================================*/
/* Table: pro_user_assign                                       */
/*==============================================================*/
create table pro_user_assign  (
   assignId             NUMBER(18)                      not null,
   deployId             VARCHAR2(128)                   not null,
   activityName         VARCHAR2(128)                   not null,
   roleId               VARCHAR2(128),
   roleName             VARCHAR2(256),
   userId               VARCHAR2(128),
   username             VARCHAR2(256),
   isSigned             SMALLINT                       default 0,
   constraint PK_PRO_USER_ASSIGN primary key (assignId)
);

comment on table pro_user_assign is
'流程过程中各个任务节点及启动流程时的角色及用户';

comment on column pro_user_assign.assignId is
'授权ID';

comment on column pro_user_assign.deployId is
'jbpm流程定义的id';

comment on column pro_user_assign.activityName is
'流程节点名称';

comment on column pro_user_assign.roleId is
'角色Id';

comment on column pro_user_assign.userId is
'用户ID';

comment on column pro_user_assign.isSigned is
'1=是会签任务
0=非会签任务

若为会签任务，则需要为该会签添加会签的决策方式的设置
';

/*==============================================================*/
/* Table: process_form                                          */
/*==============================================================*/
create table process_form  (
   formId               NUMBER(18)                      not null,
   runId                INTEGER                         not null,
   activityName         VARCHAR2(128)                   not null,
   sn                   INTEGER                        default 1 not null,
   createtime           DATE                            not null,
   creatorId            INTEGER                         not null,
   creatorName          VARCHAR2(64)                    not null,
   constraint PK_PROCESS_FORM primary key (formId)
);

comment on table process_form is
'流程表单
存储保存在运行中的流程表单数据';

comment on column process_form.runId is
'所属运行流程';

comment on column process_form.activityName is
'活动或任务名称';

comment on column process_form.sn is
'表单序号代表该流程任务执行经过的次数,如第一次经过时为1,第二次再次经过时变为2,
主要用于标识某一任务在流程中可能被不断回退.';

/*==============================================================*/
/* Table: process_run                                           */
/*==============================================================*/
create table process_run  (
   runId                NUMBER(18)                      not null,
   subject              VARCHAR2(256)                   not null,
   creator              VARCHAR2(128),
   userId               INTEGER                         not null,
   defId                INTEGER                         not null,
   piId                 VARCHAR2(64),
   createtime           DATE                            not null,
   runStatus            SMALLINT                        not null,
   constraint PK_PROCESS_RUN primary key (runId)
);

comment on table process_run is
'运行中的流程';

comment on column process_run.subject is
'标题
一般为流程名称＋格式化的时间';

comment on column process_run.creator is
'创建人';

comment on column process_run.userId is
'所属用户';

comment on column process_run.defId is
'所属流程定义';

comment on column process_run.piId is
'流程实例ID';

comment on column process_run.createtime is
'创建时间';

comment on column process_run.runStatus is
'0=尚未启动
1=已经启动流程
2=运行结束';

/*==============================================================*/
/* Table: product                                               */
/*==============================================================*/
create table product  (
   productId            NUMBER(18)                      not null,
   productName          VARCHAR2(128)                   not null,
   productModel         VARCHAR2(128),
   unit                 VARCHAR2(128),
   costPrice            NUMBER(18,2),
   salesPrice           NUMBER(18,2),
   productDesc          VARCHAR2(512),
   providerId           INTEGER                         not null,
   createtime           DATE                            not null,
   updatetime           DATE                            not null,
   constraint PK_PRODUCT primary key (productId)
);

comment on table product is
'供应商产品';

comment on column product.productName is
'产品名称';

comment on column product.productModel is
'产品型号';

comment on column product.unit is
'计量单位';

comment on column product.costPrice is
'成本价';

comment on column product.salesPrice is
'出售价';

comment on column product.productDesc is
'产品描述';

comment on column product.providerId is
'所属供应商';

comment on column product.createtime is
'收录时间';

/*==============================================================*/
/* Table: project                                               */
/*==============================================================*/
create table project  (
   projectId            NUMBER(18)                      not null,
   projectName          VARCHAR2(128)                   not null,
   projectNo            VARCHAR2(64)                    not null,
   reqDesc              CLOB,
   isContract           SMALLINT                        not null,
   fullname             VARCHAR2(32)                    not null,
   mobile               VARCHAR2(32),
   phone                VARCHAR2(32),
   fax                  VARCHAR2(32),
   otherContacts        VARCHAR2(128),
   customerId           INTEGER                         not null,
   userId               INTEGER                         not null,
   constraint PK_PROJECT primary key (projectId)
);

comment on table project is
'项目信息';

comment on column project.projectName is
'项目名称';

comment on column project.projectNo is
'项目编号';

comment on column project.reqDesc is
'需求描述';

comment on column project.isContract is
'是否签订合同';

comment on column project.fullname is
'联系人姓名';

comment on column project.mobile is
'手机';

comment on column project.phone is
'电话';

comment on column project.fax is
'传真';

comment on column project.otherContacts is
'其他联系方式';

comment on column project.customerId is
'所属客户';

comment on column project.userId is
'业务人员';

/*==============================================================*/
/* Table: project_file                                          */
/*==============================================================*/
create table project_file  (
   fileId               INTEGER                         not null,
   projectId            INTEGER                         not null,
   constraint PK_PROJECT_FILE primary key (fileId, projectId)
);

comment on table project_file is
'项目附件';

/*==============================================================*/
/* Table: provider                                              */
/*==============================================================*/
create table provider  (
   providerId           NUMBER(18)                      not null,
   providerName         VARCHAR2(128)                   not null,
   contactor            VARCHAR2(128)                   not null,
   phone                VARCHAR2(32)                    not null,
   fax                  VARCHAR2(32),
   site                 VARCHAR2(128),
   email                VARCHAR2(128),
   address              VARCHAR2(128)                   not null,
   zip                  VARCHAR2(32),
   openBank             VARCHAR2(128),
   account              VARCHAR2(64),
   notes                VARCHAR2(500),
   rank                 INTEGER,
   constraint PK_PROVIDER primary key (providerId)
);

comment on table provider is
'供应商';

comment on column provider.providerName is
'供应商名称';

comment on column provider.contactor is
'联系人';

comment on column provider.phone is
'电话';

comment on column provider.fax is
'传真';

comment on column provider.site is
'网址';

comment on column provider.email is
'邮件';

comment on column provider.address is
'地址';

comment on column provider.zip is
'邮编';

comment on column provider.openBank is
'开户行';

comment on column provider.account is
'帐号';

comment on column provider.notes is
'备注';

comment on column provider.rank is
'供应商等级
1=一级供应商
2＝二级供应商
3＝三级供应商
4＝四级供应商
';

/*==============================================================*/
/* Table: region                                                */
/*==============================================================*/
create table region  (
   regionId             NUMBER(18)                      not null,
   regionName           VARCHAR2(128)                   not null,
   regionType           SMALLINT                        not null,
   parentId             INTEGER,
   constraint PK_REGION primary key (regionId)
);

comment on table region is
'地区管理';

comment on column region.regionName is
'地区名称';

comment on column region.regionType is
'地区类型
1=省份
2=市';

comment on column region.parentId is
'上级地区';

/*==============================================================*/
/* Table: report_param                                          */
/*==============================================================*/
create table report_param  (
   paramId              NUMBER(18)                      not null,
   reportId             INTEGER                         not null,
   paramName            VARCHAR2(64)                    not null,
   paramKey             VARCHAR2(64)                    not null,
   defaultVal           VARCHAR2(128)                   not null,
   paramType            VARCHAR2(32)                    not null,
   sn                   INTEGER                         not null,
   constraint PK_REPORT_PARAM primary key (paramId)
);

comment on table report_param is
'报表参数';

comment on column report_param.reportId is
'所属报表';

comment on column report_param.paramName is
'参数名称';

comment on column report_param.paramKey is
'参数Key';

comment on column report_param.defaultVal is
'缺省值';

comment on column report_param.paramType is
'类型
字符类型--varchar
整型--int
精度型--decimal
日期型--date
日期时间型--datetime
';

comment on column report_param.sn is
'系列号';

/*==============================================================*/
/* Table: report_template                                       */
/*==============================================================*/
create table report_template  (
   reportId             NUMBER(18)                      not null,
   title                VARCHAR2(128)                   not null,
   descp                VARCHAR2(500)                   not null,
   reportLocation       VARCHAR2(128)                   not null,
   createtime           DATE                            not null,
   updatetime           DATE                            not null,
   constraint PK_REPORT_TEMPLATE primary key (reportId)
);

comment on table report_template is
'报表模板
report_template';

comment on column report_template.title is
'标题';

comment on column report_template.descp is
'描述';

comment on column report_template.reportLocation is
'报表模块的jasper文件的路径';

comment on column report_template.createtime is
'创建时间';

comment on column report_template.updatetime is
'修改时间';

/*==============================================================*/
/* Table: resume                                                */
/*==============================================================*/
create table resume  (
   resumeId             NUMBER(18)                      not null,
   fullname             VARCHAR2(64)                    not null,
   age                  INTEGER,
   birthday             DATE,
   address              VARCHAR2(128),
   zip                  VARCHAR2(32),
   sex                  VARCHAR2(32),
   position             VARCHAR2(64),
   phone                VARCHAR2(64),
   mobile               VARCHAR2(64),
   email                VARCHAR2(128),
   hobby                VARCHAR2(256),
   religion             VARCHAR2(128),
   party                VARCHAR2(128),
   nationality          VARCHAR2(32),
   race                 VARCHAR2(32),
   birthPlace           VARCHAR2(128),
   eduCollege           VARCHAR2(128),
   eduDegree            VARCHAR2(128),
   eduMajor             VARCHAR2(128),
   startWorkDate        DATE,
   idNo                 VARCHAR2(64),
   photo                VARCHAR2(128),
   status               VARCHAR2(64),
   memo                 VARCHAR2(1024),
   registor             VARCHAR2(64),
   regTime              DATE,
   workCase             CLOB,
   trainCase            CLOB,
   projectCase          CLOB,
   constraint PK_RESUME primary key (resumeId)
);

comment on table resume is
'简历管理';

comment on column resume.status is
'状态

通过
未通过
准备安排面试
面试通过

';

/*==============================================================*/
/* Table: resume_file                                           */
/*==============================================================*/
create table resume_file  (
   fileId               INTEGER                         not null,
   resumeId             INTEGER                         not null,
   constraint PK_RESUME_FILE primary key (fileId, resumeId)
);

/*==============================================================*/
/* Table: role_fun                                              */
/*==============================================================*/
create table role_fun  (
   roleId               INTEGER                         not null,
   functionId           INTEGER                         not null,
   constraint PK_ROLE_FUN primary key (roleId, functionId)
);

/*==============================================================*/
/* Table: salary_item                                           */
/*==============================================================*/
create table salary_item  (
   salaryItemId         NUMBER(18)                      not null,
   itemName             VARCHAR2(128)                   not null,
   defaultVal           NUMBER(18,2)                    not null,
   constraint PK_SALARY_ITEM primary key (salaryItemId)
);

comment on table salary_item is
'薪酬组成项目';

comment on column salary_item.itemName is
'项目名称';

comment on column salary_item.defaultVal is
'缺省值';

/*==============================================================*/
/* Table: salary_payoff                                         */
/*==============================================================*/
create table salary_payoff  (
   recordId             NUMBER(18)                      not null,
   fullname             VARCHAR2(64)                    not null,
   userId               INTEGER                         not null,
   profileNo            VARCHAR2(128),
   standardId           INTEGER                         not null,
   idNo                 VARCHAR2(128),
   standAmount          NUMBER(18,2)                   default 0 not null,
   encourageAmount      NUMBER(18,2)                   default 0 not null,
   deductAmount         NUMBER(18,2)                   default 0 not null,
   achieveAmount        NUMBER(18,2)                   default 0,
   encourageDesc        VARCHAR2(512),
   deductDesc           VARCHAR2(512),
   memo                 VARCHAR2(512),
   acutalAmount         NUMBER(18,2),
   regTime              DATE                            not null,
   register             VARCHAR2(64),
   checkOpinion         VARCHAR2(1024),
   checkName            VARCHAR2(64),
   checkTime            DATE,
   checkStatus          SMALLINT,
   startTime            DATE                            not null,
   endTime              DATE                            not null,
   constraint PK_SALARY_PAYOFF primary key (recordId)
);

comment on column salary_payoff.fullname is
'员工姓名';

comment on column salary_payoff.userId is
'所属员工';

comment on column salary_payoff.profileNo is
'档案编号';

comment on column salary_payoff.idNo is
'身份证号';

comment on column salary_payoff.standAmount is
'薪标金额';

comment on column salary_payoff.encourageAmount is
'奖励金额';

comment on column salary_payoff.deductAmount is
'扣除工资';

comment on column salary_payoff.achieveAmount is
'效绩工资';

comment on column salary_payoff.encourageDesc is
'奖励描述';

comment on column salary_payoff.deductDesc is
'扣除描述';

comment on column salary_payoff.memo is
'备注描述';

comment on column salary_payoff.acutalAmount is
'实发金额';

comment on column salary_payoff.regTime is
'登记时间';

comment on column salary_payoff.register is
'登记人';

comment on column salary_payoff.checkName is
'审批人';

comment on column salary_payoff.checkTime is
'审批时间';

comment on column salary_payoff.checkStatus is
'审批状态
0=草稿
1=通过审批
2=未通过审批
';

/*==============================================================*/
/* Table: short_message                                         */
/*==============================================================*/
create table short_message  (
   messageId            NUMBER(18)                      not null,
   senderId             INTEGER,
   content              VARCHAR2(256)                   not null,
   sender               VARCHAR2(64)                    not null,
   msgType              SMALLINT                        not null,
   sendTime             DATE                            not null,
   constraint PK_SHORT_MESSAGE primary key (messageId)
);

comment on table short_message is
'短信消息';

comment on column short_message.senderId is
'主键';

comment on column short_message.msgType is
'1=个人信息
2=日程安排
3=计划任务
';

/*==============================================================*/
/* Table: stand_salary                                          */
/*==============================================================*/
create table stand_salary  (
   standardId           NUMBER(18)                      not null,
   standardNo           VARCHAR2(128)                   not null,
   standardName         VARCHAR2(128)                   not null,
   totalMoney           NUMBER(18,2)                   default 0.00 not null,
   framer               VARCHAR2(64),
   setdownTime          DATE,
   checkName            VARCHAR2(64),
   checkTime            DATE,
   modifyName           VARCHAR2(64),
   modifyTime           DATE,
   checkOpinion         VARCHAR2(512),
   status               SMALLINT                        not null,
   memo                 VARCHAR2(512),
   constraint PK_STAND_SALARY primary key (standardId)
);

comment on column stand_salary.standardNo is
'薪酬标准编号
惟一';

comment on column stand_salary.standardName is
'标准名称';

comment on column stand_salary.status is
'0=草稿
1=审批
2=未通过审批';

/*==============================================================*/
/* Table: stand_salary_item                                     */
/*==============================================================*/
create table stand_salary_item  (
   itemId               NUMBER(18)                      not null,
   standardId           INTEGER                         not null,
   itemName             VARCHAR2(64)                    not null,
   amount               NUMBER(18,2)                    not null,
   salaryItemId         INTEGER,
   constraint PK_STAND_SALARY_ITEM primary key (itemId)
);

comment on table stand_salary_item is
'薪酬标准明细';

comment on column stand_salary_item.salaryItemId is
'所属工资组成ID
外键，但不需要在数据库层建立外键';

/*==============================================================*/
/* Table: sys_config                                            */
/*==============================================================*/
create table sys_config  (
   configId             NUMBER(18)                      not null,
   configKey            VARCHAR2(64)                    not null,
   configName           VARCHAR2(64)                    not null,
   configDesc           VARCHAR2(256),
   typeName             VARCHAR2(32)                    not null,
   dataType             SMALLINT                        not null,
   dataValue            VARCHAR2(64),
   constraint PK_SYS_CONFIG primary key (configId)
);

comment on table sys_config is
'系统配置

用于系统的全局配置
如邮件服务器的配置';

comment on column sys_config.configKey is
'Key';

comment on column sys_config.configName is
'配置名称';

comment on column sys_config.configDesc is
'配置描述';

comment on column sys_config.typeName is
'所属分类名称';

comment on column sys_config.dataType is
'数据类型
1=varchar
2=intger
3=decimal
4=datetime
5=time
';

/*==============================================================*/
/* Table: system_log                                            */
/*==============================================================*/
create table system_log  (
   logId                NUMBER(18)                      not null,
   username             VARCHAR2(128)                   not null,
   userId               INTEGER                         not null,
   createtime           DATE                            not null,
   exeOperation         VARCHAR2(512)                   not null,
   constraint PK_SYSTEM_LOG primary key (logId)
);

/*==============================================================*/
/* Table: task_sign                                             */
/*==============================================================*/
create table task_sign  (
   signId               INTEGER                         not null,
   assignId             INTEGER                         not null,
   voteCounts           INTEGER,
   votePercents         INTEGER,
   decideType           SMALLINT                        not null,
   constraint PK_TASK_SIGN primary key (signId)
);

comment on column task_sign.assignId is
'所属流程设置';

comment on column task_sign.voteCounts is
'绝对票数';

comment on column task_sign.votePercents is
'百分比票数';

comment on column task_sign.decideType is
'1=pass 通过
2=reject 拒绝';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role  (
   userId               INTEGER                         not null,
   roleId               INTEGER                         not null,
   constraint PK_USER_ROLE primary key (userId, roleId)
);

comment on column user_role.userId is
'主键';

/*==============================================================*/
/* Table: user_sub                                              */
/*==============================================================*/
create table user_sub  (
   subId                NUMBER(18)                      not null,
   subUserId            INTEGER                         not null,
   userId               INTEGER                         not null,
   constraint PK_USER_SUB primary key (subId)
);

comment on table user_sub is
'subordinate';

/*==============================================================*/
/* Table: work_plan                                             */
/*==============================================================*/
create table work_plan  (
   planId               NUMBER(18)                      not null,
   planName             VARCHAR2(128)                   not null,
   planContent          CLOB,
   startTime            DATE                            not null,
   endTime              DATE                            not null,
   typeId               INTEGER                         not null,
   userId               INTEGER,
   issueScope           VARCHAR2(2000),
   participants         VARCHAR2(2000),
   principal            VARCHAR2(256)                   not null,
   note                 VARCHAR2(500),
   status               SMALLINT                        not null,
   isPersonal           SMALLINT                        not null,
   icon                 VARCHAR2(128),
   constraint PK_WORK_PLAN primary key (planId)
);

comment on table work_plan is
'工作计划';

comment on column work_plan.planName is
'计划名称';

comment on column work_plan.planContent is
'计划内容';

comment on column work_plan.startTime is
'开始日期';

comment on column work_plan.endTime is
'结束日期';

comment on column work_plan.typeId is
'计划类型';

comment on column work_plan.userId is
'员工ID';

comment on column work_plan.issueScope is
'发布范围
0则代表全部部门
存放所有的参与部门ID
';

comment on column work_plan.participants is
'参与人
0则代表全部参与
参与人,即员工ID列表';

comment on column work_plan.principal is
'负责人';

comment on column work_plan.note is
'备注';

comment on column work_plan.status is
'状态
1=激活
0=禁用';

comment on column work_plan.isPersonal is
'是否为个人计划
1=则为个人工作计划，这时发布范围，参与人均为空，负责人为当前用户
0=则代表为其他任务';

comment on column work_plan.icon is
'图标';

alter table app_tips
   add constraint FK_APP_TIPS_AT_R_AP_APP_USER foreign key (userId)
      references app_user (userId);

alter table app_user
   add constraint FK_AU_R_DPT foreign key (depId)
      references department (depId);

alter table appointment
   add constraint FK_APPOINTM_AP_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table arch_dispatch
   add constraint FK_AVDH_R_ARV foreign key (archivesId)
      references archives (archivesId)
      on delete cascade;

alter table arch_flow_conf
   add constraint FK_ARCH_FLO_AFC_R_PD_PRO_DEFI foreign key (processDefId)
      references pro_definition (defId)
      on delete cascade;

alter table arch_hasten
   add constraint FK_ARHN_R_ARV foreign key (archivesId)
      references archives (archivesId);

alter table arch_rec_type
   add constraint FK_ARCH_REC_ART_R_DPT_DEPARTME foreign key (depId)
      references department (depId)
      on delete set null;

alter table arch_template
   add constraint FK_ARCH_TEM_AHT_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table arch_template
   add constraint FK_ARCH_TEM_ART_R_ARV_ARCHIVES foreign key (typeId)
      references archives_type (typeId);

alter table archives
   add constraint FK_ARCHIVES_ARV_R_ART_ARCH_REC foreign key (arc_typeId)
      references arch_rec_type (typeId);

alter table archives
   add constraint FK_ARCHIVES_ARV_R_ARV_ARCHIVES foreign key (typeId)
      references archives_type (typeId);

alter table archives
   add constraint FK_ARCHIVES_ARV_R_DPT_DEPARTME foreign key (depId)
      references department (depId)
      on delete set null;

alter table archives_attend
   add constraint FK_ARCHIVES_ARVA_R_AR_ARCHIVES foreign key (archivesId)
      references archives (archivesId)
      on delete cascade;

alter table archives_dep
   add constraint FK_ARCHIVES_AVD_R_ARV_ARCHIVES foreign key (archivesId)
      references archives (archivesId)
      on delete cascade;

alter table archives_dep
   add constraint FK_ARCHIVES_AVD_R_DPT_DEPARTME foreign key (depId)
      references department (depId)
      on delete cascade;

alter table archives_doc
   add constraint FK_ARCHIVES_ARVC_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table archives_doc
   add constraint FK_ARCHIVES_ARVD_R_AR_ARCHIVES foreign key (archivesId)
      references archives (archivesId);

alter table archives_handle
   add constraint FK_AVHD_R_ARV foreign key (archivesId)
      references archives (archivesId)
      on delete cascade;

alter table book
   add constraint FK_BOOK_BK_R_BT_BOOK_TYP foreign key (typeId)
      references book_type (typeId);

alter table book_bor_ret
   add constraint FK_BOOK_BOR_BBR_R_BS_BOOK_SN foreign key (bookSnId)
      references book_sn (bookSnId);

alter table book_sn
   add constraint FK_BOOK_SN_BS_R_BK_BOOK foreign key (bookId)
      references book (bookId);

alter table cal_file
   add constraint FK_CAL_FILE_CF_R_CP_CALENDAR foreign key (planId)
      references calendar_plan (planId);

alter table cal_file
   add constraint FK_CAL_FILE_CF_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table calendar_plan
   add constraint FK_CALENDAR_CA_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table calendar_plan
   add constraint FK_CALENDAR_CP_R_AUAS_APP_USER foreign key (assignerId)
      references app_user (userId);

alter table car_apply
   add constraint FK_CAR_APPL_CRA_R_CR_CAR foreign key (carId)
      references car (carId);

alter table cart_repair
   add constraint FK_CART_REP_CRR_R_CR_CAR foreign key (carId)
      references car (carId);

alter table contract
   add constraint FK_CONTRACT_CT_R_PT_PROJECT foreign key (projectId)
      references project (projectId);

alter table contract_config
   add constraint FK_CONTRACT_CC_R_CT_CONTRACT foreign key (contractId)
      references contract (contractId);

alter table contract_file
   add constraint FK_CONTRACT_CTF_R_CT_CONTRACT foreign key (contractId)
      references contract (contractId);

alter table contract_file
   add constraint FK_CONTRACT_CTF_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table cus_connection
   add constraint FK_CUS_CONN_CC_R_CS_CUSTOMER foreign key (customerId)
      references customer (customerId);

alter table cus_linkman
   add constraint FK_CUS_LINK_CLM_R_CS_CUSTOMER foreign key (customerId)
      references customer (customerId);

alter table depre_record
   add constraint FK_DEPRE_RE_DR_R_FA_FIXED_AS foreign key (assetsId)
      references fixed_assets (assetsId);

alter table diary
   add constraint FK_DIARY_DY_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table doc_file
   add constraint FK_DOC_FILE_DF_F_DT_DOCUMENT foreign key (docId)
      references document (docId);

alter table doc_file
   add constraint FK_DF_R_FA foreign key (fileId)
      references file_attach (fileId);

alter table doc_folder
   add constraint FK_DOC_FOLD_DF_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table doc_history
   add constraint FK_DOC_HIST_DHY_R_ARV_ARCHIVES foreign key (docId)
      references archives_doc (docId);

alter table doc_history
   add constraint FK_DOC_HIST_DH_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table doc_privilege
   add constraint FK_DOC_PRIV_DP_R_DF_DOC_FOLD foreign key (folderId)
      references doc_folder (folderId);

alter table doc_privilege
   add constraint FK_DOC_PRIV_DP_R_DT_DOCUMENT foreign key (docId)
      references document (docId);

alter table document
   add constraint FK_DOCUMENT_DT_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table document
   add constraint FK_DOCUMENT_DT_R_DF_DOC_FOLD foreign key (folderId)
      references doc_folder (folderId);

alter table duty
   add constraint FK_DUTY_DUY_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table duty
   add constraint FK_DUTY_DUY_R_DS_DUTY_SYS foreign key (systemId)
      references duty_system (systemId);

alter table duty_register
   add constraint FK_DUTY_REG_DR_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table duty_register
   add constraint FK_DUTY_REG_DR_R_DS_DUTY_SEC foreign key (sectionId)
      references duty_section (sectionId);

alter table emp_profile
   add constraint FK_EMP_PROF_EPF_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table emp_profile
   add constraint FK_EMP_PROF_EP_R_JB_JOB foreign key (jobId)
      references job (jobId);

alter table emp_profile
   add constraint FK_EMP_PROF_SD_R_SY_STAND_SA foreign key (standardId)
      references stand_salary (standardId);

alter table errands_register
   add constraint FK_ERRANDS__ERP_R_AU_APP_USER foreign key (approvalId)
      references app_user (userId);

alter table errands_register
   add constraint FK_ERRANDS__ER_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table fixed_assets
   add constraint FK_FIXED_AS_FA_R_AT_ASSETS_T foreign key (assetsTypeId)
      references assets_type (assetsTypeId);

alter table fixed_assets
   add constraint FK_FIXED_AS_FA_R_DT_DEPRE_TY foreign key (depreTypeId)
      references depre_type (depreTypeId);

alter table form_data
   add constraint FK_FORM_DAT_FD_R_PF_PROCESS_ foreign key (formId)
      references process_form (formId);

alter table fun_url
   add constraint FK_FUN_URL_FU_R_AFN_APP_FUNC foreign key (functionId)
      references app_function (functionId);

alter table goods_apply
   add constraint FK_GOODS_AP_GA_R_OG_OFFICE_G foreign key (goodsId)
      references office_goods (goodsId);

alter table in_message
   add constraint FK_IN_MESSA_IM_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table in_message
   add constraint FK_IN_MESSA_IM_R_SM_SHORT_ME foreign key (messageId)
      references short_message (messageId);

alter table in_stock
   add constraint FK_IN_STOCK_IS_R_OG_OFFICE_G foreign key (goodsId)
      references office_goods (goodsId);

alter table index_display
   add constraint FK_INDEX_DI_ID_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table job
   add constraint FK_JOB_JB_R_DPT_DEPARTME foreign key (depId)
      references department (depId);

alter table job_change
   add constraint FK_JOB_CHAN_JBC_R_JBN_JOB foreign key (newJobId)
      references job (jobId);

alter table job_change
   add constraint FK_JOB_CHAN_JBC_R_JBO_JOB foreign key (orgJobId)
      references job (jobId);

alter table leader_read
   add constraint FK_LEADER_R_LDR_R_ARV_ARCHIVES foreign key (archivesId)
      references archives (archivesId)
      on delete cascade;

alter table mail
   add constraint FK_MAIL_ML_R_AU_APP_USER foreign key (senderId)
      references app_user (userId);

alter table mail_attach
   add constraint FK_MAIL_ATT_MA_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table mail_attach
   add constraint FK_MAIL_ATT_MA_R_ML_MAIL foreign key (mailId)
      references mail (mailId);

alter table mail_box
   add constraint FK_MAIL_BOX_MB_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table mail_box
   add constraint FK_MAIL_BOX_MB_R_FD_MAIL_FOL foreign key (folderId)
      references mail_folder (folderId);

alter table mail_box
   add constraint FK_MAIL_BOX_MB_R_ML_MAIL foreign key (mailId)
      references mail (mailId);

alter table mail_folder
   add constraint FK_MAIL_FOL_FD_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table meeting_attend
   add constraint FK_MEETING__MTA_R_MT_MEETING foreign key (mettingId)
      references meeting (mettingId);

alter table meeting_file
   add constraint FK_MEETING__MF_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table meeting_file
   add constraint FK_MEETING__MF_R_MT_MEETING foreign key (mettingId)
      references meeting (mettingId);

alter table news
   add constraint FK_NEWS_NS_R_NT_NEWS_TYP foreign key (typeId)
      references news_type (typeId);

alter table news_comment
   add constraint FK_NEWS_COM_NC_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table news_comment
   add constraint FK_NEWS_COM_NC_R_NS_NEWS foreign key (newsId)
      references news (newsId);

alter table office_goods
   add constraint FK_OFFICE_G_OG_R_OGT_OFFICE_G foreign key (typeId)
      references office_goods_type (typeId);

alter table phone_book
   add constraint FK_PHONE_BO_PB_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table phone_book
   add constraint FK_PHONE_BO_PB_R_PG_PHONE_GR foreign key (groupId)
      references phone_group (groupId);

alter table phone_group
   add constraint FK_PHONE_GR_PG_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table plan_attend
   add constraint FK_PLAN_ATT_PAD_R_DT_DEPARTME foreign key (depId)
      references department (depId);

alter table plan_attend
   add constraint FK_PLAN_ATT_PAD_R_UA_APP_USER foreign key (userId)
      references app_user (userId);

alter table plan_attend
   add constraint FK_PLAN_ATT_PAD_R_WP_WORK_PLA foreign key (planId)
      references work_plan (planId);

alter table plan_file
   add constraint FK_PLAN_FIL_PA_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table plan_file
   add constraint FK_PLAN_FIL_PA_R_WP_WORK_PLA foreign key (planId)
      references work_plan (planId);

alter table pro_definition
   add constraint FK_PRO_DEFI_PD_R_PT_PRO_TYPE foreign key (typeId)
      references pro_type (typeId);

alter table process_form
   add constraint FK_PROCESS__PF_R_PR_PROCESS_ foreign key (runId)
      references process_run (runId);

alter table process_run
   add constraint FK_PROCESS__PR_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table process_run
   add constraint FK_PROCESS__PR_R_PD_PRO_DEFI foreign key (defId)
      references pro_definition (defId);

alter table product
   add constraint FK_PRODUCT_PD_R_PUT_PROVIDER foreign key (providerId)
      references provider (providerId);

alter table project
   add constraint FK_PROJECT_PR_R_CS_CUSTOMER foreign key (customerId)
      references customer (customerId);

alter table project
   add constraint FK_PROJECT_PT_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table project_file
   add constraint FK_PROJECT__PF_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table project_file
   add constraint FK_PROJECT__PF_R_PT_PROJECT foreign key (projectId)
      references project (projectId);

alter table report_param
   add constraint FK_REPORT_P_RP_R_RPT_REPORT_T foreign key (reportId)
      references report_template (reportId);

alter table resume_file
   add constraint FK_RESUME_F_RMF_R_FA_FILE_ATT foreign key (fileId)
      references file_attach (fileId);

alter table resume_file
   add constraint FK_RESUME_F_RMF_R_RM_RESUME foreign key (resumeId)
      references resume (resumeId);

alter table role_fun
   add constraint FK_ROLE_FUN_RF_R_AFN_APP_FUNC foreign key (functionId)
      references app_function (functionId);

alter table role_fun
   add constraint FK_ROLE_FUN_RF_R_AR_APP_ROLE foreign key (roleId)
      references app_role (roleId);

alter table short_message
   add constraint FK_SHORT_ME_SM_R_AU_APP_USER foreign key (senderId)
      references app_user (userId);

alter table stand_salary_item
   add constraint FK_STAND_SA_SSI_R_SSY_STAND_SA foreign key (standardId)
      references stand_salary (standardId);

alter table task_sign
   add constraint FK_TASK_SIG_TS_R_PUA_PRO_USER foreign key (assignId)
      references pro_user_assign (assignId);

alter table user_role
   add constraint FK_UR_R_AR foreign key (roleId)
      references app_role (roleId);

alter table user_role
   add constraint FK_UR_R_AU foreign key (userId)
      references app_user (userId);

alter table user_sub
   add constraint FK_USER_SUB_USB_R_AU_APP_USER foreign key (subUserId)
      references app_user (userId);

alter table user_sub
   add constraint FK_USER_SUB_US_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table work_plan
   add constraint FK_WORK_PLA_WP_R_AU_APP_USER foreign key (userId)
      references app_user (userId);

alter table work_plan
   add constraint FK_WORK_PLA_WP_R_PT_PLAN_TYP foreign key (typeId)
      references plan_type (typeId);
