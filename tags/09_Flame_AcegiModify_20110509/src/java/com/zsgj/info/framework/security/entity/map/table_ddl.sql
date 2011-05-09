-- create manually by peixf --

--ģ���
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[MODULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[MODULE]
GO

CREATE TABLE MODULE(
 ID BIGINT IDENTITY PRIMARY KEY,
 PARENT_MODULE_ID BIGINT NULL,
 NAME VARCHAR(30) NULL,
 SERVICE_KEY_NAME VARCHAR(80) NULL, --ģ�����ؼ��֣���service��ǰ׺
 URL VARCHAR(150) NULL,
 DESCN VARCHAR(255) NULL
);
GO
INSERT INTO MODULE(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('�ͻ�����ģ��',null,'Customer','customerMgr.do');
INSERT INTO MODULE(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('�ͻ���Ϣ',1,'Customer','customerMgr/customer.do');
INSERT INTO MODULE(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('��ϵ����Ϣ',1,'Customer','customerMgr/relationPerson.do');
INSERT INTO MODULE(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('��Ŀ����ģ��',null,'Project','projectMgr.do');

--��Դ������������
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[RESOURCES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[RESOURCES]
GO

CREATE TABLE RESOURCES(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(20) NULL,
 TYPE VARCHAR(20) NULL,
 CLASS_NAME VARCHAR(80) NULL,
 METHOD_NAME VARCHAR(80) NULL,
 MODULE_ID BIGINT NOT NULL,
 DESCN VARCHAR(255) NULL
);
GO
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ��鿴����','com.digitalchina.demo.customer.service.CustomerService','find*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ����溯��','com.digitalchina.demo.customer.service.CustomerService','save*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ�ɾ������','com.digitalchina.demo.customer.service.CustomerService','remove*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ����뺯��','com.digitalchina.demo.customer.service.CustomerService','import*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ��ύ����','com.digitalchina.demo.customer.service.CustomerService','submit*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ���������','com.digitalchina.demo.customer.service.CustomerService','audit*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','�ͻ�����ͨ������','com.digitalchina.demo.customer.service.CustomerService','pass*',1,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','��Ŀ�鿴����','com.digitalchina.demo.project.service.ProjectService','find*',4,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','��Ŀ�޸ĺ���','com.digitalchina.demo.project.service.ProjectService','modify*',4,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','��Ŀɾ������','com.digitalchina.demo.project.service.ProjectService','remove*',4,NULL);
INSERT INTO RESOURCES(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','��Ŀ���溯��','com.digitalchina.demo.project.service.ProjectService','save*',4,NULL);



--Ȩ�ޱ���acegi�õ���ROLE_��ͷ������ 
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[RIGHTS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[RIGHTS]
GO

CREATE TABLE RIGHTS(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(30) NULL,
 KEY_NAME VARCHAR(30) NULL,
 DESCN VARCHAR(255) NULL
 );
GO
INSERT INTO RIGHTS(NAME,KEY_NAME,DESCN) VALUES('�ͻ�ģ���Ȩ��','ROLE_CUST_VIEW',NULL);
INSERT INTO RIGHTS(NAME,KEY_NAME,DESCN) VALUES('�ͻ�ģ��дȨ��','ROLE_CUST_ADMIN',NULL);
INSERT INTO RIGHTS(NAME,KEY_NAME,DESCN) VALUES('�ͻ�ģ������Ȩ��', 'ROLE_CUST_AUDIT', NULL);

GO 

--��Ȩ����Դ��Ȩ��ֱ�ӵĹ����� 
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[AUTHORIZATIONS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[AUTHORIZATIONS]
GO
CREATE TABLE [AUTHORIZATIONS](
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(80) NULL,  --Ȩ������
 RESOURCE_ID BIGINT NULL REFERENCES RESOURCE(ID), 
 RIGHT_ID BIGINT NULL REFERENCES RIGHT(ID)
);
GO
INSERT INTO AUTHORIZATIONS(NAME, RESOURCE_ID, RIGHT_ID) 
VALUES('�ͻ���Ϣ�鿴��', 1, 1);
INSERT INTO AUTHORIZATIONS(NAME, RESOURCE_ID, RIGHT_ID) 
VALUES('�ͻ���Ϣд������', 2, 2);

--------------------------
--��ɫ���
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROLES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROLES]
GO
CREATE TABLE ROLES(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(80),
 DESCN VARCHAR(255)
);
GO
INSERT INTO ROLES(NAME, DESCN) 
VALUES('�ͻ���Ϣ����Ա', NULL);
GO


--��ɫ��-��Ȩӳ���
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[ROLE_AUTHOR]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[ROLE_AUTHOR]
GO
CREATE TABLE ROLE_AUTHOR(
 ID BIGINT IDENTITY PRIMARY KEY,
 ROLE_ID BIGINT NULL,  --��ɫid
 AUTHOR_ID BIGINT NULL  --��Ȩid
);
GO
INSERT INTO ROLE_AUTHOR(ROLE_ID, AUTHOR_ID) 
VALUES(1,1);
INSERT INTO ROLE_AUTHOR(ROLE_ID, AUTHOR_ID) 
VALUES(2,1);


---�û���
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[USERS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[USERS]
GO
CREATE TABLE USERS(
 ID BIGINT IDENTITY PRIMARY KEY,
 USERNAME VARCHAR(20),
 PASSWORD VARCHAR(255),
 REALNAME VARCHAR(80),
 ENABLED INT,
 EMAIL  VARCHAR(255)
);
GO
INSERT INTO USERS(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('admin', 'admin', 'adminr', 1);
go
INSERT INTO USERS(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('readonly', 'readonly', 'readonly', 1);
go
INSERT INTO USERS(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('cust_write', 'cust_write', 'cust_write', 1);
go

--�û���ɫ
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[USER_ROLE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[USER_ROLE]
GO
CREATE TABLE USER_ROLE(
 ID BIGINT IDENTITY PRIMARY KEY,
 USER_ID BIGINT,
 ROLE_ID BIGINT
);
GO
INSERT INTO USER_ROLE(USER_ID, ROLE_ID) 
VALUES(1,1);
GO
--------------------------------------------------------------------------------------------
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tExtendTable]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tExtendTable]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tSystemExtTable]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tSystemExtTable]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tSystemMainTable]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tSystemMainTable]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tUserExtTableSetting]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tUserExtTableSetting]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tUserMenuSetting]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tUserMenuSetting]
GO

CREATE TABLE [dbo].[tExtendTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[SystemMainTableID] [bigint] NULL ,
	[SystemExtTableID] [bigint] NULL ,
	[ExtTableColumnSum] [int] NULL ,
	[ExtTableColumnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ExtTableColumnDispName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[tSystemExtTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[TableName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[TableCnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[CountLeft] [int] NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[PageValue] [varchar] (400) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO


CREATE TABLE [dbo].[tSystemMainTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[TableName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[TableCnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ModuleID] [bigint] NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO
INSERT INTO tSystemMainTable(TableName,TableCnName,ModuleID,Descn) 
VALUES('Customer','�ͻ���', 1, null);


CREATE TABLE [dbo].[tUserExtTableSetting] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[UserID] [bigint] NULL ,
	[ExtTableID] [bigint] NULL ,
	[IsDisplay] [int] NULL ,
	[Order] [int] NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[tUserMenuSetting] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[UserID] [bigint] NULL ,
	[ModuleID] [bigint] NULL ,
	[Enabled] [int] NULL ,
	[Order] [int] NULL 
) ON [PRIMARY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[SYS_USERACTIONLOG]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[SYS_USERACTIONLOG]
GO

CREATE TABLE [dbo].[SYS_USERACTIONLOG] ( 
  ID            [bigint] IDENTITY (1, 1) NOT NULL ,
  USERNAME       [VARCHAR] (40), 
  TARGETURL       [VARCHAR] (300), 
  CLIENTIP       [VARCHAR] (100) , 
  ACTIONDATE        [timestamp] , --Oracle : timestamp
  REMARK          [VARCHAR] (500)
)ON [PRIMARY]
GO

--Oracleʹ��
CREATE SEQUENCE log_sequence  
     INCREMENT BY 1   -- ÿ�μӼ���  
     START WITH 1     -- ��1��ʼ����  
     NOMAXVALUE       -- ���������ֵ  
     NOCYCLE          -- һֱ�ۼӣ���ѭ��  
     CACHE 20; 
      
DROP TABLE SYS_USERACTIONLOG CASCADE CONSTRAINTS ; 

CREATE TABLE SYS_USERACTIONLOG ( 
  ID             NUMBER (10)   NOT NULL, 
  USERNAME       VARCHAR2 (40), 
  TARGETURL       VARCHAR2 (300), 
  CLIENTIP       VARCHAR2 (100) , 
  ACTIONDATE        TIMESTAMP, 
  REMARK          VARCHAR2 (500), 
  CONSTRAINT PK_SYS_USERACTIONLOG
  PRIMARY KEY ( ID ) 
    USING INDEX 
     TABLESPACE USERS PCTFREE 10
     STORAGE ( INITIAL 65536 NEXT 1048576 ))
   TABLESPACE USERS
   PCTFREE 10
   INITRANS 1
   MAXTRANS 255
  STORAGE ( 
   INITIAL 65536
   NEXT 1048576
   MINEXTENTS 1
   MAXEXTENTS 2147483645
 )
   NOCACHE; 
 ---oracle  ʹ��    