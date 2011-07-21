
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
'Ȩ��Key';

comment on column app_function.funName is
'Ȩ������';

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
'��ɫ��';

comment on column app_role.roleName is
'��ɫ����';

comment on column app_role.roleDesc is
'��ɫ����';

comment on column app_role.status is
'״̬';

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
'�û���ǩ';

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
�û���';

comment on column app_user.userId is
'����';

comment on column app_user.username is
'�û���';

comment on column app_user.title is
'1=����
0=Ůʿ
С��';

comment on column app_user.password is
'����';

comment on column app_user.email is
'�ʼ�';

comment on column app_user.depId is
'��������';

comment on column app_user.position is
'ְλ';

comment on column app_user.phone is
'�绰';

comment on column app_user.mobile is
'�ֻ�';

comment on column app_user.fax is
'����';

comment on column app_user.address is
'��ַ';

comment on column app_user.zip is
'�ʱ�';

comment on column app_user.photo is
'��Ƭ';

comment on column app_user.accessionTime is
'��ְʱ��';

comment on column app_user.status is
'״̬
1=����
0=����
2=��ְ
';

comment on column app_user.delFlag is
'0=δɾ��
1=ɾ��';

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
'Լ�����';

comment on column appointment.userId is
'����';

comment on column appointment.subject is
'����';

comment on column appointment.startTime is
'��ʼʱ��';

comment on column appointment.endTime is
'����ʱ��';

comment on column appointment.content is
'Լ������';

comment on column appointment.notes is
'��ע';

comment on column appointment.location is
'�ص�';

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
'0=�Ķ���Ա
1=�а���
2=�ַ�������
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
'������������';

comment on column arch_flow_conf.archType is
'0=����
1=����';

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
'�߰�����';

comment on column arch_hasten.createtime is
'�߰�ʱ��';

comment on column arch_hasten.hastenFullname is
'�߰���';

comment on column arch_hasten.handlerFullname is
'�а���';

comment on column arch_hasten.handlerUserId is
'�а���ID';

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
'��������';

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
'�û���';

comment on column arch_rec_user.depId is
'����ID ';

comment on column arch_rec_user.depName is
'��������';

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
'����ģ��';

comment on column arch_template.typeId is
'��������';

comment on column arch_template.tempName is
'ģ������';

comment on column arch_template.tempPath is
'·��';

comment on column arch_template.fileId is
'�ļ�ID';

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
   privacyLevel         VARCHAR2(50)                   default '��ͨ',
   urgentLevel          VARCHAR2(50)                   default '��ͨ',
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
'�շ�����';

comment on column archives.typeId is
'��������';

comment on column archives.typeName is
'������������';

comment on column archives.archivesNo is
'�����ֺ�';

comment on column archives.issueDep is
'���Ļ��ػ���';

comment on column archives.depId is
'���Ĳ���ID';

comment on column archives.subject is
'�ļ�����';

comment on column archives.issueDate is
'��������';

comment on column archives.status is
'����״̬
0=��塢�޸�״̬
1=����״̬
2=�鵵״̬';

comment on column archives.shortContent is
'���ݼ��';

comment on column archives.fileCounts is
'�ļ���';

comment on column archives.privacyLevel is
'���ܵȼ�
��ͨ
����
����
����';

comment on column archives.urgentLevel is
'�����̶�
��ͨ
����
�ؼ�
����';

comment on column archives.issuer is
'������';

comment on column archives.issuerId is
'������ID';

comment on column archives.keywords is
'�����';

comment on column archives.sources is
'������Դ
����������ָ���������Ĳ���Ҫָ��
�ϼ�����
�¼�����';

comment on column archives.archType is
'0=����
1=����';

comment on column archives.recDepIds is
'���ڴ洢���չ��ĵĲ���ID,ʹ��,���зֿ�';

comment on column archives.recDepNames is
'���ڴ洢���չ��ĵĲ��ŵ����ƣ�ʹ��,���зֿ�';

comment on column archives.handlerUids is
'��������ʹ�ã�����û�ID��'',''�ָ�';

comment on column archives.handlerUnames is
'�������ģ��洢�������û������á������ָ�';

comment on column archives.orgArchivesId is
'��������ʱʹ�ã�ָ��ԭ����ID';

comment on column archives.depSignNo is
'��������ʱ�����Ŷ�����Ĺ����Ա��';

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
'������������';

comment on column archives_attend.userID is
'�û�ID';

comment on column archives_attend.fullname is
'����';

comment on column archives_attend.attendType is
'��������
1=У����
2=�����
3=��ӡ��
4=��ӡ��';

comment on column archives_attend.executeTime is
'ִ��ʱ��';

comment on column archives_attend.memo is
'��ע';

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
'����';

comment on column archives_dep.signNo is
'�Ա��';

comment on column archives_dep.depId is
'���Ĳ���';

comment on column archives_dep.archivesId is
'��������';

comment on column archives_dep.subject is
'���ı���';

comment on column archives_dep.status is
'ǩ��״̬
0=δǩ��
1=��ǩ��';

comment on column archives_dep.signTime is
'ǩ������';

comment on column archives_dep.signFullname is
'ǩ����';

comment on column archives_dep.handleFeedback is
'����������';

comment on column archives_dep.isMain is
'���͡�����
1=����
0=����';

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
'�����';

comment on column archives_doc.creatorId is
'�����ID';

comment on column archives_doc.mender is
'�޸���';

comment on column archives_doc.docName is
'�ĵ�����';

comment on column archives_doc.docStatus is
'�ĵ�״̬
0=�޸���
1=�޸����';

comment on column archives_doc.curVersion is
'��ǰ�汾
ȡ��ǰ���µİ汾';

comment on column archives_doc.docPath is
'�ĵ�·��';

comment on column archives_doc.updatetime is
'����ʱ��';

comment on column archives_doc.createtime is
'����ʱ��';

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
'���������';

comment on column archives_handle.isPass is
'0=��δ����
1=ͨ������
��=δͨ������';

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
'��������';

comment on column archives_type.typeName is
'��������';

comment on column archives_type.typeDesc is
'��������';

/*==============================================================*/
/* Table: assets_type                                           */
/*==============================================================*/
create table assets_type  (
   assetsTypeId         NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_ASSETS_TYPE primary key (assetsTypeId)
);

comment on column assets_type.typeName is
'��������';

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
'ͼ��';

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
'ͼ��軹��';

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
'����״̬
0=δ���
1=���
2=Ԥ��
3=ע��';

/*==============================================================*/
/* Table: book_type                                             */
/*==============================================================*/
create table book_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_BOOK_TYPE primary key (typeId)
);

comment on table book_type is
'ͼ�����';

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
'�ճ̰���';

comment on column calendar_plan.startTime is
'��ʼʱ��';

comment on column calendar_plan.endTime is
'����ʱ��';

comment on column calendar_plan.urgent is
'�����̶�
0=һ��
1=��Ҫ
2=����';

comment on column calendar_plan.content is
'����';

comment on column calendar_plan.status is
'״̬
0=δ���
1=���';

comment on column calendar_plan.userId is
'Ա��ID';

comment on column calendar_plan.fullname is
'Ա����';

comment on column calendar_plan.assignerId is
'������';

comment on column calendar_plan.assignerName is
'��������';

comment on column calendar_plan.feedback is
'�������';

comment on column calendar_plan.showStyle is
'��ʾ��ʽ
1=������������ʾ
2=���ճ�����������ʾ';

comment on column calendar_plan.taskType is
'��������
1=��������
2=����������';

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
'������Ϣ';

comment on column car.carType is
'�γ�
����
����
';

comment on column car.buyInsureTime is
'������ʱ��';

comment on column car.auditTime is
'����ʱ��';

comment on column car.buyDate is
'��������';

comment on column car.status is
'��ǰ״̬
1=����
2=ά����
0=����';

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
'��������';

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
'ά������';

comment on column cart_repair.reason is
'ά��ԭ��';

comment on column cart_repair.executant is
'������';

comment on column cart_repair.notes is
'��ע';

comment on column cart_repair.repairType is
'ά������
����
ά��';

comment on column cart_repair.fee is
'����';

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
'��˾��Ϣ';

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
'��ͬ���';

comment on column contract.subject is
'��ͬ����';

comment on column contract.contractAmount is
'��ͬ���';

comment on column contract.mainItem is
'��Ҫ����';

comment on column contract.salesAfterItem is
'�ۺ�����';

comment on column contract.validDate is
'��Ч����';

comment on column contract.expireDate is
'��Ч��';

comment on column contract.serviceDep is
'ά�޲���';

comment on column contract.serviceMan is
'ά�޸�����';

comment on column contract.signupUser is
'ǩԼ��';

comment on column contract.signupTime is
'ǩԼʱ��';

comment on column contract.creator is
'¼����';

comment on column contract.createtime is
'¼��ʱ��';

comment on column contract.projectId is
'������Ŀ';

comment on column contract.consignAddress is
'�ջ���ַ';

comment on column contract.consignee is
'�ջ���';

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
'��ͬ���õ�';

comment on column contract_config.itemName is
'�豸����';

comment on column contract_config.itemSpec is
'���ù��';

comment on column contract_config.amount is
'����';

comment on column contract_config.notes is
'��ע';

/*==============================================================*/
/* Table: contract_file                                         */
/*==============================================================*/
create table contract_file  (
   fileId               INTEGER                         not null,
   contractId           INTEGER                         not null,
   constraint PK_CONTRACT_FILE primary key (fileId, contractId)
);

comment on table contract_file is
'��ͬ����';

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
'�ͻ���ϵ��';

comment on column cus_linkman.customerId is
'�����ͻ�';

comment on column cus_linkman.fullname is
'����';

comment on column cus_linkman.sex is
'�Ա�';

comment on column cus_linkman.position is
'ְλ';

comment on column cus_linkman.phone is
'�绰';

comment on column cus_linkman.mobile is
'�ֻ�';

comment on column cus_linkman.email is
'Email';

comment on column cus_linkman.msn is
'MSN';

comment on column cus_linkman.qq is
'QQ';

comment on column cus_linkman.birthday is
'����';

comment on column cus_linkman.homeAddress is
'��ͥסַ';

comment on column cus_linkman.homeZip is
'�ʱ�';

comment on column cus_linkman.homePhone is
'��ͥ�绰';

comment on column cus_linkman.hobby is
'����';

comment on column cus_linkman.isPrimary is
'�Ƿ�Ϊ��Ҫ��ϵ��';

comment on column cus_linkman.notes is
'��ע';

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
'�ͻ���Ϣ';

comment on column customer.customerNo is
'�ͻ���
�Զ�����';

comment on column customer.industryType is
'������ҵ
��ȱʡ��ѡ��Ҳ��������';

comment on column customer.customerSource is
'�ͻ���Դ
�ɱ༭�������

�绰����
����
�ͻ������ѽ���';

comment on column customer.customerType is
'1=��ʽ�ͻ�
2=��Ҫ�ͻ�
3��Ǳ�ڿͻ�
4����Ч�ͻ�';

comment on column customer.companyScale is
'1=1-20��
2=20-50��
3=50-100��
4=100-200��
5=200-500��
6=500-1000 ��
7=1000������
';

comment on column customer.customerName is
'�ͻ�����
һ��Ϊ��˾����';

comment on column customer.customerManager is
'����ÿͻ��ľ���';

comment on column customer.phone is
'�绰';

comment on column customer.currencyUnit is
'ע���ʽ���Ӫҵ��Ļ��ҵ�λ
��ѡ�ɱ༭
����ң�Ĭ�ϣ�
��Ԫ
';

comment on column customer.rights is
'1=�ͻ������ϼ�������Ȩ�鿴
2=����
3=������Ա��Ȩ�鿴';

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
'��������';

comment on column department.depDesc is
'��������';

comment on column department.depLevel is
'���';

comment on column department.path is
'·��';

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
'��λΪ��';

comment on column depre_type.calMethod is
'1=ƽ�����޷�
2=��������
�����۾ɷ�
3=˫�����ݼ���
4=�����ܺͷ�';

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
'����';

comment on column diary.diaryType is
'1=������־
0=������־';

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
'�����ֵ�';

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
'����';

comment on column doc_folder.folderName is
'Ŀ¼����';

comment on column doc_folder.parentId is
'��Ŀ¼';

comment on column doc_folder.path is
'·��
Ϊ��ǰ·���ģ��ϼ�·��
�統ǰIDΪ3���ϼ�Ŀ¼��·��Ϊ1.2��
��ǰ��·��Ϊ1.2.3.';

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
'����ID';

comment on column doc_history.docName is
'�ĵ�����';

comment on column doc_history.path is
'·��';

comment on column doc_history.version is
'�汾';

comment on column doc_history.updatetime is
'����ʱ��';

comment on column doc_history.mender is
'�޸���';

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
'�ĵ���Ŀ¼��Ȩ�ޣ�ֻҪ����Թ���Ŀ¼�µ��ĵ������˵��ĵ�����Ҫ���������Ȩ�޵�����

ĳ��Ŀ¼���ĵ���û��ָ��ĳ���Ż�ĳ���ˣ����ڱ�����û�м�¼��
���ʾ���Խ������еĲ���';

comment on column doc_privilege.rights is
'Ȩ��
�ĵ���Ŀ¼�Ķ�д�޸�Ȩ��
1=��
2=�޸�
4=ɾ��

Ȩ��ֵ����Ϊ�����ֵ֮��
�磺3�������ж����޸ĵĲ���


';

comment on column doc_privilege.flag is
'1=user
2=deparment
3=role';

comment on column doc_privilege.fdFlag is
'ȱʡΪ�ļ���Ȩ��
1=�ĵ�Ȩ��
0=�ļ���Ȩ��';

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
'�ĵ�';

comment on column document.content is
'����';

comment on column document.createtime is
'����ʱ��';

comment on column document.userId is
'����';

comment on column document.sharedUserIds is
'����Ա��ID';

comment on column document.sharedDepIds is
'������ID';

comment on column document.sharedRoleIds is
'�����ɫID';

comment on column document.isShared is
'�Ƿ���';

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
'�Ű�';

comment on column duty.userId is
'Ա��ID';

comment on column duty.fullname is
'Ա������';

comment on column duty.systemId is
'����ID';

comment on column duty.startTime is
'��ʼʱ��';

comment on column duty.endTime is
'����ʱ��';

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
'�Ǽ�ʱ��';

comment on column duty_register.userId is
'�Ǽ���';

comment on column duty_register.regFlag is
'�ǼǱ�ʶ
1=�����Ǽǣ��ϰ࣬�°ࣩ
2���ٵ�
3=����
4����Ϣ
5������
6=�ż�

';

comment on column duty_register.regMins is
'�ٵ������˷���
�����ϰ�ʱΪ0';

comment on column duty_register.reason is
'�ٵ�ԭ��';

comment on column duty_register.dayOfWeek is
'�ܼ�
1=������
..
7=������';

comment on column duty_register.inOffFlag is
'���°��ʶ
1=ǩ��
2=ǩ��';

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
'���
Ҳ��Ϊ��Σ�һ����Է�Ϊ�������������
';

comment on column duty_section.startSignin is
'��ʼǩ��';

comment on column duty_section.dutyStartTime is
'�ϰ�ʱ��';

comment on column duty_section.endSignin is
'ǩ������ʱ��';

comment on column duty_section.earlyOffTime is
'���˼�ʱ';

comment on column duty_section.dutyEndTime is
'�°�ʱ��';

comment on column duty_section.signOutTime is
'ǩ�˽���';

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
'�ְ���
Ϊ��˾�����ϰ��ʱ��涨
��涨��һ�������ϰ࣬����������Ϣ';

comment on column duty_system.systemName is
'��������';

comment on column duty_system.systemSetting is
'���
��ε����ݽṹΪ��
��һԱ����һ�������ϰ࣬����9��00- 12��00 ������ 13��30 -18:00

�����ݽṹΪ��
1|2,1|2,1|2,1|2,1|2,-,-
-����������Ϣ��
1|2����Ϊһ����������Σ�1����Ϊ��α�1�ļ�¼
';

comment on column duty_system.systemDesc is
'�������';

comment on column duty_system.isDefault is
'�Ƿ�ȱʡ
1��ȱʡ
0����ȱʡ';

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
'Ա������';

comment on column emp_profile.profileNo is
'�������';

comment on column emp_profile.fullname is
'Ա������';

comment on column emp_profile.address is
'��ͥ��ַ';

comment on column emp_profile.birthday is
'��������';

comment on column emp_profile.homeZip is
'��ͥ�ʱ�';

comment on column emp_profile.marriage is
'����״��
�ѻ�
δ��';

comment on column emp_profile.phone is
'�绰����';

comment on column emp_profile.mobile is
'�ֻ�����';

comment on column emp_profile.openBank is
'��������';

comment on column emp_profile.bankNo is
'�����˺�';

comment on column emp_profile.qq is
'QQ����';

comment on column emp_profile.email is
'��������';

comment on column emp_profile.hobby is
'����';

comment on column emp_profile.religion is
'�ڽ�����';

comment on column emp_profile.party is
'������ò';

comment on column emp_profile.nationality is
'����';

comment on column emp_profile.race is
'����';

comment on column emp_profile.birthPlace is
'������';

comment on column emp_profile.eduDegree is
'ѧ��';

comment on column emp_profile.eduMajor is
'רҵ';

comment on column emp_profile.eduCollege is
'��ҵԺУ';

comment on column emp_profile.startWorkDate is
'�μӹ���ʱ��';

comment on column emp_profile.eduCase is
'��������';

comment on column emp_profile.awardPunishCase is
'�������';

comment on column emp_profile.trainingCase is
'��ѵ���';

comment on column emp_profile.workCase is
'��������';

comment on column emp_profile.idCard is
'���֤��';

comment on column emp_profile.photo is
'��Ƭ';

comment on column emp_profile.standardMiNo is
'н���׼���';

comment on column emp_profile.standardMoney is
'н���׼���';

comment on column emp_profile.standardName is
'н���׼������';

comment on column emp_profile.standardId is
'н���׼�����';

comment on column emp_profile.creator is
'������';

comment on column emp_profile.createtime is
'����ʱ��';

comment on column emp_profile.checkName is
'�����';

comment on column emp_profile.checktime is
'���ʱ��';

comment on column emp_profile.approvalStatus is
'����״̬
0=δ����
1=ͨ�����
2=δͨ�����';

comment on column emp_profile.memo is
'��ע';

comment on column emp_profile.depName is
'�������Ż�˾';

comment on column emp_profile.depId is
'��������Id';

comment on column emp_profile.delFlag is
'ɾ��״̬
0=δɾ��
1=ɾ��';

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
'��١��Ӱࡢ����Ǽ�';

comment on column errands_register.userId is
'���/�Ӱ���Ա';

comment on column errands_register.descp is
'����';

comment on column errands_register.startTime is
'��ʼ����';

comment on column errands_register.endTime is
'��������';

comment on column errands_register.approvalId is
'������ID';

comment on column errands_register.status is
'����״̬
0=δ����
1=ͨ������
2=��ͨ������';

comment on column errands_register.approvalOption is
'�������';

comment on column errands_register.approvalName is
'������';

comment on column errands_register.flag is
'��ʶ
0=�Ӱ�
1=���
2=���';

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
'����';

comment on column file_attach.fileName is
'�ļ���';

comment on column file_attach.filePath is
'�ļ�·��';

comment on column file_attach.createtime is
'����ʱ��';

comment on column file_attach.ext is
'��չ��';

comment on column file_attach.fileType is
'��������
�磺�ʼ�����';

comment on column file_attach.note is
'˵��';

comment on column file_attach.creator is
'�ϴ���';

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
'���۾ɵķ���ѡ���ù����������м���ʱ������Ҫ��д';

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
'������';

comment on column form_data.fieldLabel is
'�ֶα�ǩ';

comment on column form_data.fieldName is
'�ֶ�����';

comment on column form_data.intValue is
'����ֵ';

comment on column form_data.longValue is
'����ֵ';

comment on column form_data.decValue is
'����ֵ';

comment on column form_data.dateValue is
'����ֵ';

comment on column form_data.strValue is
'�ַ�ֵ';

comment on column form_data.boolValue is
'����ֵ';

comment on column form_data.blobValue is
'����ֵ';

comment on column form_data.isShowed is
'�Ƿ���ʾ
1=��ʾ
0=����ʾ';

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
'������';

comment on column form_def.columns is
'������';

comment on column form_def.isEnabled is
'�Ƿ����';

comment on column form_def.activityName is
'�ڵ�����';

comment on column form_def.deployId is
'Jbpm���̷���ID';

comment on column form_def.extDef is
'������';

comment on column form_def.formView is
'���̶���View';

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
'�칫��Ʒ����';

comment on column goods_apply.applyNo is
'�����,��ϵͳʱ���������GA20091002-0001';

comment on column goods_apply.approvalStatus is
'����״̬
1=ͨ������
0=δ����
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
'��Ƹ����';

comment on column hire_issue.title is
'��Ƹ��Ϣ����';

comment on column hire_issue.startDate is
'��ʼʱ��';

comment on column hire_issue.endDate is
'����ʱ��';

comment on column hire_issue.hireCount is
'��Ƹ����';

comment on column hire_issue.jobName is
'ְλ����';

comment on column hire_issue.jobCondition is
'��ƸҪ��(����)';

comment on column hire_issue.regFullname is
'�Ǽ�������';

comment on column hire_issue.regDate is
'�Ǽ�ʱ��';

comment on column hire_issue.modifyFullname is
'���������';

comment on column hire_issue.modifyDate is
'���ʱ��';

comment on column hire_issue.checkFullname is
'���������';

comment on column hire_issue.checkOpinion is
'������';

comment on column hire_issue.checkDate is
'����ʱ��';

comment on column hire_issue.status is
'״̬
1=ͨ�����
0=δ���
2=��˲�ͨ��';

comment on column hire_issue.memo is
'��ע';

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
'�żټ�¼';

comment on column holiday_record.startTime is
'��ʼ����';

comment on column holiday_record.endTime is
'��������';

comment on column holiday_record.descp is
'��������';

comment on column holiday_record.fullname is
'�û���';

comment on column holiday_record.userId is
'�����û�
��ΪĳԱ���ļ��ڣ���ΪԱ���Լ���id';

comment on column holiday_record.isAll is
'�Ƿ�Ϊȫ��˾����
1=��
0=��';

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
'����';

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
'�칫��Ʒ�����Ҫͬʱ���°칫��Ʒ��Ŀ��';

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
'ÿ��Ա�����������Լ�����ʾ��ʽ';

comment on column index_display.portalId is
'Portal ID';

comment on column index_display.userId is
'�û�ID';

comment on column index_display.colNum is
'�к�';

comment on column index_display.rowsNum is
'�к�';

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
'ְλ����';

comment on column job.depId is
'��������';

comment on column job.memo is
'��ע';

comment on column job.delFlag is
'0=δɾ��
1=ɾ��';

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
'ԭн���׼��ID';

comment on column job_change.status is
'״̬

-1=�ݸ�
0=�ύ����
1=ͨ������
2=δͨ������
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
'�쵼��ʾ';

comment on column leader_read.isPass is
'0=��δ��
1=����ͨ��
2=����δͨ��';

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
'�ʼ�';

comment on column mail.importantFlag is
'1=һ��
2=��Ҫ
3=�ǳ���Ҫ';

comment on column mail.content is
'�ʼ�����';

comment on column mail.subject is
'�ʼ�����';

comment on column mail.copyToNames is
'�����������б�';

comment on column mail.copyToIDs is
'������ID�б�
��'',''�ֿ�';

comment on column mail.recipientNames is
'�ռ��������б�';

comment on column mail.recipientIDs is
'�ռ���ID�б�
��'',''�ָ�';

comment on column mail.mailStatus is
'�ʼ�״̬
1=��ʽ�ʼ�
0=�ݸ��ʼ�';

comment on column mail.fileIds is
'����Ids�����������ID��ͨ��,�ָ�';

comment on column mail.filenames is
'���������б�ͨ��,���зָ�';

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
'�ռ���';

comment on column mail_box.userId is
'����';

comment on column mail_box.delFlag is
'del=1�����ɾ��';

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
'�ļ��б��';

comment on column mail_folder.userId is
'����';

comment on column mail_folder.folderName is
'�ļ�������';

comment on column mail_folder.parentId is
'��Ŀ¼';

comment on column mail_folder.depLevel is
'Ŀ¼��';

comment on column mail_folder.isPublic is
'1=��ʾ���������е�Ա��������ʹ�ø��ļ���
0=˽���ļ���';

comment on column mail_folder.folderType is
'�ļ�������
1=������
2=������
3=�ݸ���
4=ɾ����
10=����';

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
'������벿�Ż���Ա';

comment on column meeting_attend.attendType is
'��������
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
'����';

comment on column news.newsId is
'ID';

comment on column news.subject is
'���ű���';

comment on column news.author is
'����';

comment on column news.createtime is
'����ʱ��';

comment on column news.viewCounts is
'�����';

comment on column news.content is
'����';

comment on column news.status is
'
0=�����
1=���ͨ��';

comment on column news.isDeskImage is
'�Ƿ�Ϊ��������';

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
'��������';

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
'����';

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
'�칫��Ʒ';

comment on column office_goods.typeId is
'��������';

comment on column office_goods.goodsName is
'��Ʒ����';

comment on column office_goods.goodsNo is
'���';

comment on column office_goods.specifications is
'���';

comment on column office_goods.unit is
'������λ';

comment on column office_goods.isWarning is
'�Ƿ����ÿ�澯ʾ';

comment on column office_goods.notes is
'��ע';

comment on column office_goods.stockCounts is
'�������';

comment on column office_goods.warnCounts is
'��Ϳ����';

/*==============================================================*/
/* Table: office_goods_type                                     */
/*==============================================================*/
create table office_goods_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_OFFICE_GOODS_TYPE primary key (typeId)
);

comment on table office_goods_type is
'�칫��Ʒ����';

comment on column office_goods_type.typeName is
'��������';

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
'ͨѶ��';

comment on column phone_book.title is
'����
Ůʿ
С��';

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
'��������';

comment on column phone_group.isShared is
'1=����
0=˽��';

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
'�Ƿ�Ϊ����';

comment on column plan_attend.isPrimary is
'�Ƿ�����';

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
'�ƻ�����';

comment on column plan_type.typeName is
'�������';

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
'���̶���';

comment on column pro_definition.typeId is
'����ID';

comment on column pro_definition.name is
'���̵�����';

comment on column pro_definition.description is
'����';

comment on column pro_definition.createtime is
'����ʱ��';

comment on column pro_definition.deployId is
'Jbpm ������id';

comment on column pro_definition.defXml is
'���̶���XML';

comment on column pro_definition.isDefault is
'�Ƿ�ȱʡ
1=��
0=��';

/*==============================================================*/
/* Table: pro_type                                              */
/*==============================================================*/
create table pro_type  (
   typeId               NUMBER(18)                      not null,
   typeName             VARCHAR2(128)                   not null,
   constraint PK_PRO_TYPE primary key (typeId)
);

comment on table pro_type is
'���̷���';

comment on column pro_type.typeId is
'���ID';

comment on column pro_type.typeName is
'��������';

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
'���̹����и�������ڵ㼰��������ʱ�Ľ�ɫ���û�';

comment on column pro_user_assign.assignId is
'��ȨID';

comment on column pro_user_assign.deployId is
'jbpm���̶����id';

comment on column pro_user_assign.activityName is
'���̽ڵ�����';

comment on column pro_user_assign.roleId is
'��ɫId';

comment on column pro_user_assign.userId is
'�û�ID';

comment on column pro_user_assign.isSigned is
'1=�ǻ�ǩ����
0=�ǻ�ǩ����

��Ϊ��ǩ��������ҪΪ�û�ǩ��ӻ�ǩ�ľ��߷�ʽ������
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
'���̱�
�洢�����������е����̱�����';

comment on column process_form.runId is
'������������';

comment on column process_form.activityName is
'�����������';

comment on column process_form.sn is
'����Ŵ������������ִ�о����Ĵ���,���һ�ξ���ʱΪ1,�ڶ����ٴξ���ʱ��Ϊ2,
��Ҫ���ڱ�ʶĳһ�����������п��ܱ����ϻ���.';

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
'�����е�����';

comment on column process_run.subject is
'����
һ��Ϊ�������ƣ���ʽ����ʱ��';

comment on column process_run.creator is
'������';

comment on column process_run.userId is
'�����û�';

comment on column process_run.defId is
'�������̶���';

comment on column process_run.piId is
'����ʵ��ID';

comment on column process_run.createtime is
'����ʱ��';

comment on column process_run.runStatus is
'0=��δ����
1=�Ѿ���������
2=���н���';

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
'��Ӧ�̲�Ʒ';

comment on column product.productName is
'��Ʒ����';

comment on column product.productModel is
'��Ʒ�ͺ�';

comment on column product.unit is
'������λ';

comment on column product.costPrice is
'�ɱ���';

comment on column product.salesPrice is
'���ۼ�';

comment on column product.productDesc is
'��Ʒ����';

comment on column product.providerId is
'������Ӧ��';

comment on column product.createtime is
'��¼ʱ��';

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
'��Ŀ��Ϣ';

comment on column project.projectName is
'��Ŀ����';

comment on column project.projectNo is
'��Ŀ���';

comment on column project.reqDesc is
'��������';

comment on column project.isContract is
'�Ƿ�ǩ����ͬ';

comment on column project.fullname is
'��ϵ������';

comment on column project.mobile is
'�ֻ�';

comment on column project.phone is
'�绰';

comment on column project.fax is
'����';

comment on column project.otherContacts is
'������ϵ��ʽ';

comment on column project.customerId is
'�����ͻ�';

comment on column project.userId is
'ҵ����Ա';

/*==============================================================*/
/* Table: project_file                                          */
/*==============================================================*/
create table project_file  (
   fileId               INTEGER                         not null,
   projectId            INTEGER                         not null,
   constraint PK_PROJECT_FILE primary key (fileId, projectId)
);

comment on table project_file is
'��Ŀ����';

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
'��Ӧ��';

comment on column provider.providerName is
'��Ӧ������';

comment on column provider.contactor is
'��ϵ��';

comment on column provider.phone is
'�绰';

comment on column provider.fax is
'����';

comment on column provider.site is
'��ַ';

comment on column provider.email is
'�ʼ�';

comment on column provider.address is
'��ַ';

comment on column provider.zip is
'�ʱ�';

comment on column provider.openBank is
'������';

comment on column provider.account is
'�ʺ�';

comment on column provider.notes is
'��ע';

comment on column provider.rank is
'��Ӧ�̵ȼ�
1=һ����Ӧ��
2��������Ӧ��
3��������Ӧ��
4���ļ���Ӧ��
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
'��������';

comment on column region.regionName is
'��������';

comment on column region.regionType is
'��������
1=ʡ��
2=��';

comment on column region.parentId is
'�ϼ�����';

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
'�������';

comment on column report_param.reportId is
'��������';

comment on column report_param.paramName is
'��������';

comment on column report_param.paramKey is
'����Key';

comment on column report_param.defaultVal is
'ȱʡֵ';

comment on column report_param.paramType is
'����
�ַ�����--varchar
����--int
������--decimal
������--date
����ʱ����--datetime
';

comment on column report_param.sn is
'ϵ�к�';

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
'����ģ��
report_template';

comment on column report_template.title is
'����';

comment on column report_template.descp is
'����';

comment on column report_template.reportLocation is
'����ģ���jasper�ļ���·��';

comment on column report_template.createtime is
'����ʱ��';

comment on column report_template.updatetime is
'�޸�ʱ��';

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
'��������';

comment on column resume.status is
'״̬

ͨ��
δͨ��
׼����������
����ͨ��

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
'н�������Ŀ';

comment on column salary_item.itemName is
'��Ŀ����';

comment on column salary_item.defaultVal is
'ȱʡֵ';

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
'Ա������';

comment on column salary_payoff.userId is
'����Ա��';

comment on column salary_payoff.profileNo is
'�������';

comment on column salary_payoff.idNo is
'���֤��';

comment on column salary_payoff.standAmount is
'н����';

comment on column salary_payoff.encourageAmount is
'�������';

comment on column salary_payoff.deductAmount is
'�۳�����';

comment on column salary_payoff.achieveAmount is
'Ч������';

comment on column salary_payoff.encourageDesc is
'��������';

comment on column salary_payoff.deductDesc is
'�۳�����';

comment on column salary_payoff.memo is
'��ע����';

comment on column salary_payoff.acutalAmount is
'ʵ�����';

comment on column salary_payoff.regTime is
'�Ǽ�ʱ��';

comment on column salary_payoff.register is
'�Ǽ���';

comment on column salary_payoff.checkName is
'������';

comment on column salary_payoff.checkTime is
'����ʱ��';

comment on column salary_payoff.checkStatus is
'����״̬
0=�ݸ�
1=ͨ������
2=δͨ������
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
'������Ϣ';

comment on column short_message.senderId is
'����';

comment on column short_message.msgType is
'1=������Ϣ
2=�ճ̰���
3=�ƻ�����
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
'н���׼���
Ωһ';

comment on column stand_salary.standardName is
'��׼����';

comment on column stand_salary.status is
'0=�ݸ�
1=����
2=δͨ������';

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
'н���׼��ϸ';

comment on column stand_salary_item.salaryItemId is
'�����������ID
�����������Ҫ�����ݿ�㽨�����';

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
'ϵͳ����

����ϵͳ��ȫ������
���ʼ�������������';

comment on column sys_config.configKey is
'Key';

comment on column sys_config.configName is
'��������';

comment on column sys_config.configDesc is
'��������';

comment on column sys_config.typeName is
'������������';

comment on column sys_config.dataType is
'��������
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
'������������';

comment on column task_sign.voteCounts is
'����Ʊ��';

comment on column task_sign.votePercents is
'�ٷֱ�Ʊ��';

comment on column task_sign.decideType is
'1=pass ͨ��
2=reject �ܾ�';

/*==============================================================*/
/* Table: user_role                                             */
/*==============================================================*/
create table user_role  (
   userId               INTEGER                         not null,
   roleId               INTEGER                         not null,
   constraint PK_USER_ROLE primary key (userId, roleId)
);

comment on column user_role.userId is
'����';

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
'�����ƻ�';

comment on column work_plan.planName is
'�ƻ�����';

comment on column work_plan.planContent is
'�ƻ�����';

comment on column work_plan.startTime is
'��ʼ����';

comment on column work_plan.endTime is
'��������';

comment on column work_plan.typeId is
'�ƻ�����';

comment on column work_plan.userId is
'Ա��ID';

comment on column work_plan.issueScope is
'������Χ
0�����ȫ������
������еĲ��벿��ID
';

comment on column work_plan.participants is
'������
0�����ȫ������
������,��Ա��ID�б�';

comment on column work_plan.principal is
'������';

comment on column work_plan.note is
'��ע';

comment on column work_plan.status is
'״̬
1=����
0=����';

comment on column work_plan.isPersonal is
'�Ƿ�Ϊ���˼ƻ�
1=��Ϊ���˹����ƻ�����ʱ������Χ�������˾�Ϊ�գ�������Ϊ��ǰ�û�
0=�����Ϊ��������';

comment on column work_plan.icon is
'ͼ��';

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
