-- create manually by peixf --

--模块表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sModules]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sModules]
GO

CREATE TABLE sModules(
 ID BIGINT IDENTITY PRIMARY KEY,
 PARENT_MODULE_ID BIGINT NULL,
 NAME VARCHAR(30) NULL,
 SERVICE_KEY_NAME VARCHAR(80) NULL, --模块服务关键字，即service的前缀
 URL VARCHAR(150) NULL,
 DESCN VARCHAR(255) NULL
);
GO
INSERT INTO sModules(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('客户管理模块',null,'Customer','customerMgr.do');
INSERT INTO sModules(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('客户信息',1,'Customer','customerMgr/customer.do');
INSERT INTO sModules(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('联系人信息',1,'Customer','customerMgr/relationPerson.do');
INSERT INTO sModules(NAME,PARENT_MODULE_ID,SERVICE_KEY_NAME,URL) 
VALUES('项目管理模块',null,'Project','projectMgr.do');

--资源表，即方法名称
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sResources]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sResources]
GO

CREATE TABLE sResources(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(20) NULL,
 TYPE VARCHAR(20) NULL,
 CLASS_NAME VARCHAR(80) NULL,
 METHOD_NAME VARCHAR(80) NULL,
 MODULE_ID BIGINT  NULL,
 DESCN VARCHAR(255) NULL
);
GO
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户查看函数','com.digitalchina.demo.customer.service.CustomerService','find*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户保存函数','com.digitalchina.demo.customer.service.CustomerService','save*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户删除函数','com.digitalchina.demo.customer.service.CustomerService','remove*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户导入函数','com.digitalchina.demo.customer.service.CustomerService','import*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户提交函数','com.digitalchina.demo.customer.service.CustomerService','submit*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户审批函数','com.digitalchina.demo.customer.service.CustomerService','audit*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','客户审批通过函数','com.digitalchina.demo.customer.service.CustomerService','pass*',1,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','项目查看函数','com.digitalchina.demo.project.service.ProjectService','find*',4,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','项目修改函数','com.digitalchina.demo.project.service.ProjectService','modify*',4,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','项目删除函数','com.digitalchina.demo.project.service.ProjectService','remove*',4,NULL);
INSERT INTO sResources(TYPE,NAME,CLASS_NAME,METHOD_NAME,MODULE_ID,DESCN) 
VALUES('FUNCTION','项目保存函数','com.digitalchina.demo.project.service.ProjectService','save*',4,NULL);



--权限表，给acegi用的以ROLE_开头的数据 
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRights]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRights]
GO

CREATE TABLE sRights(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(30) NULL,
 KEY_NAME VARCHAR(30) NULL,
 DESCN VARCHAR(255) NULL
 );
GO
INSERT INTO sRights(NAME,KEY_NAME,DESCN) VALUES('客户模块读权限','ROLE_CUST_VIEW',NULL);
INSERT INTO sRights(NAME,KEY_NAME,DESCN) VALUES('客户模块写权限','ROLE_CUST_ADMIN',NULL);
INSERT INTO sRights(NAME,KEY_NAME,DESCN) VALUES('客户模块审批权限', 'ROLE_CUST_AUDIT', NULL);

GO 

--授权表，资源与权限直接的关联表 
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sAuthorizations]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sAuthorizations]
GO
CREATE TABLE [sAuthorizations](
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(80) NULL,  --权限名称
 RESOURCE_ID BIGINT NULL , 
 RIGHT_ID BIGINT NULL 
);
GO
INSERT INTO sAuthorizations(NAME, RESOURCE_ID, RIGHT_ID) 
VALUES('客户信息查看人', 1, 1);
INSERT INTO sAuthorizations(NAME, RESOURCE_ID, RIGHT_ID) 
VALUES('客户信息写操作人', 2, 2);

--------------------------
--角色组表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRoles]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRoles]
GO
CREATE TABLE sRoles(
 ID BIGINT IDENTITY PRIMARY KEY,
 NAME VARCHAR(80),
 DESCN VARCHAR(255)
);
GO
INSERT INTO sRoles(NAME, DESCN) 
VALUES('客户信息管理员', NULL);
GO


--角色组-授权映射表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRoleAuthoriz]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRoleAuthoriz]
GO
CREATE TABLE sRoleAuthoriz(
 ID BIGINT IDENTITY PRIMARY KEY,
 ROLE_ID BIGINT NULL,  --角色id
 AUTHOR_ID BIGINT NULL  --授权id
);
GO
INSERT INTO sRoleAuthoriz(ROLE_ID, AUTHOR_ID) 
VALUES(1,1);
INSERT INTO sRoleAuthoriz(ROLE_ID, AUTHOR_ID) 
VALUES(2,1);


---用户表
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sUserInfos]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sUserInfos]
GO
CREATE TABLE sUserInfos(
 ID BIGINT IDENTITY PRIMARY KEY,
 USERNAME VARCHAR(20),
 PASSWORD VARCHAR(255),
 REALNAME VARCHAR(80),
 ENABLED INT,
 EMAIL  VARCHAR(255)
);
GO
INSERT INTO sUserInfos(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('admin', 'admin', 'adminr', 1);
go
INSERT INTO sUserInfos(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('readonly', 'readonly', 'readonly', 1);
go
INSERT INTO sUserInfos(USERNAME,[PASSWORD],REALNAME,ENABLED) 
VALUES('cust_write', 'cust_write', 'cust_write', 1);
go

--用户角色
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sUserRole]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sUserRole]
GO
CREATE TABLE sUserRole(
 ID BIGINT IDENTITY PRIMARY KEY,
 USER_ID BIGINT,
 ROLE_ID BIGINT
);
GO
INSERT INTO sUserRole(USER_ID, ROLE_ID) 
VALUES(1,1);
GO
---=====================


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
 
------------------------------------------------------------

CREATE TABLE [dbo].[tSystemMainTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL  PRIMARY KEY ,
	[TableName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[TableCnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ColumnSum] int NULL,
	[ModuleID] [bigint] NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO
insert into tSystemMainTable(TableName,TableCnName,ColumnSum, ModuleID,Descn) 
values('Customer','客户表', 7, 1, NULL);
insert into tSystemMainTable(TableName,TableCnName,ColumnSum, ModuleID,Descn) 
values('RelationUser','联系人表', 6, 1, NULL);

------------------------------------------------------------
CREATE TABLE [dbo].[tSystemExtTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL  PRIMARY KEY ,
	[TableName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[TableCnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[CountLeft] [int] NULL ,
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL ,
	[PageValue] [varchar] (400) COLLATE Chinese_PRC_CI_AS NULL
) ON [PRIMARY]
GO
insert into tSystemExtTable(TableName,TableCnName,CountLeft,Descn,PageValue) 
values('Ext_Text','文本框', 25, NULL, '010-23456789'); --无关联主表
insert into tSystemExtTable(TableName,TableCnName,CountLeft,Descn,PageValue) 
values('Ext_Select','下拉列表', 25, NULL, '2');--关联主表relationUser

------------------------------------------------------------
 
CREATE TABLE [dbo].[tExtendTable] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL PRIMARY KEY ,
	[SystemMainTableID] [bigint] NULL ,
	[SystemExtTableID] [bigint] NULL ,
	[ExtTableColumnNum] [int] NULL ,
	[ExtTableColumnName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ExtTableColumnDispName] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[ForeignTableID] BIGINT NULL ,  --外键关联表id
	[ForeignTableKeyColumn] varchar(10) NULL, --外键关联表的主键名称，如果所有表的主键都命名为id可省略
	[ForeignTableValueColumn] varchar(10) NULL, --外键关联表存值的字段名称，注意使用类JAVA的属性命名规范，如realName, id，便于页面直接通过EL显示列表
	[Descn] [varchar] (100) COLLATE Chinese_PRC_CI_AS NULL
	
) ON [PRIMARY]
GO
insert into tExtendTable (SystemMainTableID,SystemExtTableID,
    ExtTableColumnNum,ExtTableColumnName,ExtTableColumnDispName,
    ForeignTableID,ForeignTableKeyColumn,ForeignTableValueColumn, Descn) 
values(1, 1, 9, 'telBackup', '备用联系电话', NULL, NULL, NULL, NULL);
insert into tExtendTable (SystemMainTableID,SystemExtTableID,
    ExtTableColumnNum,ExtTableColumnName,ExtTableColumnDispName,
    ForeignTableID,ForeignTableKeyColumn,ForeignTableValueColumn, Descn) 
values(1, 2, 10, 'relationUser', '备用人', 2, 'id', 'realName', NULL);--新拓展的字段通过外键引用到联系人表
GO

-------------------------------------------------------------
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tExt_Text]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tExt_Text]
GO

CREATE TABLE tExt_Text(
    ID BIGINT IDENTITY PRIMARY KEY,
    MainTableRowID BIGINT NOT NULL,
    Ext_column1 VARCHAR(50) NULL,
    Ext_column2 VARCHAR(50) NULL,
    Ext_column3 VARCHAR(50) NULL,
    Ext_column4 VARCHAR(50) NULL,
    Ext_column5 VARCHAR(50) NULL,
    Ext_column6 VARCHAR(50) NULL,
    Ext_column7 VARCHAR(50) NULL,
    Ext_column8 VARCHAR(50) NULL,
    Ext_column9 VARCHAR(50) NULL,
    Ext_column10 VARCHAR(50) NULL,
    Ext_column11 VARCHAR(50) NULL,
    Ext_column12 VARCHAR(50) NULL,
    Ext_column13 VARCHAR(50) NULL,
    Ext_column14 VARCHAR(50) NULL,
    Ext_column15 VARCHAR(50) NULL,   
    Ext_column16 VARCHAR(50) NULL,
    Ext_column17 VARCHAR(50) NULL,
    Ext_column18 VARCHAR(50) NULL,
    Ext_column19 VARCHAR(50) NULL,
    Ext_column20 VARCHAR(50) NULL,
    Ext_column21 VARCHAR(50) NULL,
    Ext_column22 VARCHAR(50) NULL,
    Ext_column23 VARCHAR(50) NULL,
    Ext_column24 VARCHAR(50) NULL,
    Ext_column25 VARCHAR(50) NULL,
    Ext_column26 VARCHAR(50) NULL,
    Ext_column27 VARCHAR(50) NULL,
    Ext_column28 VARCHAR(50) NULL,
    Ext_column29 VARCHAR(50) NULL,
    Ext_column30 VARCHAR(50) NULL,   
    Ext_column31 VARCHAR(50) NULL,
    Ext_column32 VARCHAR(50) NULL,
    Ext_column33 VARCHAR(50) NULL,
    Ext_column34 VARCHAR(50) NULL,
    Ext_column35 VARCHAR(50) NULL,
    Ext_column36 VARCHAR(50) NULL,
    Ext_column37 VARCHAR(50) NULL,
    Ext_column38 VARCHAR(50) NULL,
    Ext_column39 VARCHAR(50) NULL,
    Ext_column40 VARCHAR(50) NULL,
    Ext_column41 VARCHAR(50) NULL,
    Ext_column42 VARCHAR(50) NULL,
    Ext_column43 VARCHAR(50) NULL,
    Ext_column44 VARCHAR(50) NULL,
    Ext_column45 VARCHAR(50) NULL,   
    Ext_column46 VARCHAR(50) NULL,
    Ext_column47 VARCHAR(50) NULL,
    Ext_column48 VARCHAR(50) NULL,
    Ext_column49 VARCHAR(50) NULL,
    Ext_column50 VARCHAR(50) NULL,
    Ext_column51 VARCHAR(50) NULL,
    Ext_column52 VARCHAR(50) NULL,
    Ext_column53 VARCHAR(50) NULL,
    Ext_column54 VARCHAR(50) NULL,
    Ext_column55 VARCHAR(50) NULL,
    Ext_column56 VARCHAR(50) NULL,
    Ext_column57 VARCHAR(50) NULL,
    Ext_column58 VARCHAR(50) NULL,
    Ext_column59 VARCHAR(50) NULL,
    Ext_column60 VARCHAR(50) NULL,   
    Ext_column61 VARCHAR(50) NULL,
    Ext_column62 VARCHAR(50) NULL,
    Ext_column63 VARCHAR(50) NULL,
    Ext_column64 VARCHAR(50) NULL,
    Ext_column65 VARCHAR(50) NULL,
    Ext_column66 VARCHAR(50) NULL,
    Ext_column67 VARCHAR(50) NULL,
    Ext_column68 VARCHAR(50) NULL,
    Ext_column69 VARCHAR(50) NULL,
    Ext_column70 VARCHAR(50) NULL,
    Ext_column71 VARCHAR(50) NULL,
    Ext_column72 VARCHAR(50) NULL,
    Ext_column73 VARCHAR(50) NULL,
    Ext_column74 VARCHAR(50) NULL,
    Ext_column75 VARCHAR(50) NULL,   
    Ext_column76 VARCHAR(50) NULL,
    Ext_column77 VARCHAR(50) NULL,
    Ext_column78 VARCHAR(50) NULL,
    Ext_column79 VARCHAR(50) NULL,
    Ext_column80 VARCHAR(50) NULL,
    Ext_column81 VARCHAR(50) NULL,
    Ext_column82 VARCHAR(50) NULL,
    Ext_column83 VARCHAR(50) NULL,
    Ext_column84 VARCHAR(50) NULL,
    Ext_column85 VARCHAR(50) NULL,   
    Ext_column86 VARCHAR(50) NULL,
    Ext_column87 VARCHAR(50) NULL,
    Ext_column88 VARCHAR(50) NULL,
    Ext_column89 VARCHAR(50) NULL,
    Ext_column90 VARCHAR(50) NULL,
    Ext_column91 VARCHAR(50) NULL,
    Ext_column92 VARCHAR(50) NULL,
    Ext_column93 VARCHAR(50) NULL,
    Ext_column94 VARCHAR(50) NULL,
    Ext_column95 VARCHAR(50) NULL,   
    Ext_column96 VARCHAR(50) NULL,
    Ext_column97 VARCHAR(50) NULL,
    Ext_column98 VARCHAR(50) NULL,
    Ext_column99 VARCHAR(50) NULL,
    Ext_column100 VARCHAR(50) NULL
);
GO
--------------------------------------------------------
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tExt_Select]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tExt_Select]
GO

CREATE TABLE tExt_Select(
    ID BIGINT IDENTITY PRIMARY KEY,
    MainTableRowID BIGINT NOT NULL,
    Ext_column1 VARCHAR(50) NULL,
    Ext_column2 VARCHAR(50) NULL,
    Ext_column3 VARCHAR(50) NULL,
    Ext_column4 VARCHAR(50) NULL,
    Ext_column5 VARCHAR(50) NULL,
    Ext_column6 VARCHAR(50) NULL,
    Ext_column7 VARCHAR(50) NULL,
    Ext_column8 VARCHAR(50) NULL,
    Ext_column9 VARCHAR(50) NULL,
    Ext_column10 VARCHAR(50) NULL,
    Ext_column11 VARCHAR(50) NULL,
    Ext_column12 VARCHAR(50) NULL,
    Ext_column13 VARCHAR(50) NULL,
    Ext_column14 VARCHAR(50) NULL,
    Ext_column15 VARCHAR(50) NULL,   
    Ext_column16 VARCHAR(50) NULL,
    Ext_column17 VARCHAR(50) NULL,
    Ext_column18 VARCHAR(50) NULL,
    Ext_column19 VARCHAR(50) NULL,
    Ext_column20 VARCHAR(50) NULL,
    Ext_column21 VARCHAR(50) NULL,
    Ext_column22 VARCHAR(50) NULL,
    Ext_column23 VARCHAR(50) NULL,
    Ext_column24 VARCHAR(50) NULL,
    Ext_column25 VARCHAR(50) NULL,
    Ext_column26 VARCHAR(50) NULL,
    Ext_column27 VARCHAR(50) NULL,
    Ext_column28 VARCHAR(50) NULL,
    Ext_column29 VARCHAR(50) NULL,
    Ext_column30 VARCHAR(50) NULL,   
    Ext_column31 VARCHAR(50) NULL,
    Ext_column32 VARCHAR(50) NULL,
    Ext_column33 VARCHAR(50) NULL,
    Ext_column34 VARCHAR(50) NULL,
    Ext_column35 VARCHAR(50) NULL,
    Ext_column36 VARCHAR(50) NULL,
    Ext_column37 VARCHAR(50) NULL,
    Ext_column38 VARCHAR(50) NULL,
    Ext_column39 VARCHAR(50) NULL,
    Ext_column40 VARCHAR(50) NULL,
    Ext_column41 VARCHAR(50) NULL,
    Ext_column42 VARCHAR(50) NULL,
    Ext_column43 VARCHAR(50) NULL,
    Ext_column44 VARCHAR(50) NULL,
    Ext_column45 VARCHAR(50) NULL,   
    Ext_column46 VARCHAR(50) NULL,
    Ext_column47 VARCHAR(50) NULL,
    Ext_column48 VARCHAR(50) NULL,
    Ext_column49 VARCHAR(50) NULL,
    Ext_column50 VARCHAR(50) NULL,
    Ext_column51 VARCHAR(50) NULL,
    Ext_column52 VARCHAR(50) NULL,
    Ext_column53 VARCHAR(50) NULL,
    Ext_column54 VARCHAR(50) NULL,
    Ext_column55 VARCHAR(50) NULL,
    Ext_column56 VARCHAR(50) NULL,
    Ext_column57 VARCHAR(50) NULL,
    Ext_column58 VARCHAR(50) NULL,
    Ext_column59 VARCHAR(50) NULL,
    Ext_column60 VARCHAR(50) NULL,   
    Ext_column61 VARCHAR(50) NULL,
    Ext_column62 VARCHAR(50) NULL,
    Ext_column63 VARCHAR(50) NULL,
    Ext_column64 VARCHAR(50) NULL,
    Ext_column65 VARCHAR(50) NULL,
    Ext_column66 VARCHAR(50) NULL,
    Ext_column67 VARCHAR(50) NULL,
    Ext_column68 VARCHAR(50) NULL,
    Ext_column69 VARCHAR(50) NULL,
    Ext_column70 VARCHAR(50) NULL,
    Ext_column71 VARCHAR(50) NULL,
    Ext_column72 VARCHAR(50) NULL,
    Ext_column73 VARCHAR(50) NULL,
    Ext_column74 VARCHAR(50) NULL,
    Ext_column75 VARCHAR(50) NULL,   
    Ext_column76 VARCHAR(50) NULL,
    Ext_column77 VARCHAR(50) NULL,
    Ext_column78 VARCHAR(50) NULL,
    Ext_column79 VARCHAR(50) NULL,
    Ext_column80 VARCHAR(50) NULL,
    Ext_column81 VARCHAR(50) NULL,
    Ext_column82 VARCHAR(50) NULL,
    Ext_column83 VARCHAR(50) NULL,
    Ext_column84 VARCHAR(50) NULL,
    Ext_column85 VARCHAR(50) NULL,   
    Ext_column86 VARCHAR(50) NULL,
    Ext_column87 VARCHAR(50) NULL,
    Ext_column88 VARCHAR(50) NULL,
    Ext_column89 VARCHAR(50) NULL,
    Ext_column90 VARCHAR(50) NULL,
    Ext_column91 VARCHAR(50) NULL,
    Ext_column92 VARCHAR(50) NULL,
    Ext_column93 VARCHAR(50) NULL,
    Ext_column94 VARCHAR(50) NULL,
    Ext_column95 VARCHAR(50) NULL,   
    Ext_column96 VARCHAR(50) NULL,
    Ext_column97 VARCHAR(50) NULL,
    Ext_column98 VARCHAR(50) NULL,
    Ext_column99 VARCHAR(50) NULL,
    Ext_column100 VARCHAR(50) NULL
);
GO

--------------------------------------------------------
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[tExt_TextArea]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[tExt_TextArea]
GO

CREATE TABLE tExt_TextArea(
    ID BIGINT IDENTITY PRIMARY KEY,
    MainTableRowID BIGINT NOT NULL,
    Ext_column1 VARCHAR(50) NULL,
    Ext_column2 VARCHAR(50) NULL,
    Ext_column3 VARCHAR(50) NULL,
    Ext_column4 VARCHAR(50) NULL,
    Ext_column5 VARCHAR(50) NULL,
    Ext_column6 VARCHAR(50) NULL,
    Ext_column7 VARCHAR(50) NULL,
    Ext_column8 VARCHAR(50) NULL,
    Ext_column9 VARCHAR(50) NULL,
    Ext_column10 VARCHAR(50) NULL,
    Ext_column11 VARCHAR(50) NULL,
    Ext_column12 VARCHAR(50) NULL,
    Ext_column13 VARCHAR(50) NULL,
    Ext_column14 VARCHAR(50) NULL,
    Ext_column15 VARCHAR(50) NULL,   
    Ext_column16 VARCHAR(50) NULL,
    Ext_column17 VARCHAR(50) NULL,
    Ext_column18 VARCHAR(50) NULL,
    Ext_column19 VARCHAR(50) NULL,
    Ext_column20 VARCHAR(50) NULL,
    Ext_column21 VARCHAR(50) NULL,
    Ext_column22 VARCHAR(50) NULL,
    Ext_column23 VARCHAR(50) NULL,
    Ext_column24 VARCHAR(50) NULL,
    Ext_column25 VARCHAR(50) NULL,
    Ext_column26 VARCHAR(50) NULL,
    Ext_column27 VARCHAR(50) NULL,
    Ext_column28 VARCHAR(50) NULL,
    Ext_column29 VARCHAR(50) NULL,
    Ext_column30 VARCHAR(50) NULL,   
    Ext_column31 VARCHAR(50) NULL,
    Ext_column32 VARCHAR(50) NULL,
    Ext_column33 VARCHAR(50) NULL,
    Ext_column34 VARCHAR(50) NULL,
    Ext_column35 VARCHAR(50) NULL,
    Ext_column36 VARCHAR(50) NULL,
    Ext_column37 VARCHAR(50) NULL,
    Ext_column38 VARCHAR(50) NULL,
    Ext_column39 VARCHAR(50) NULL,
    Ext_column40 VARCHAR(50) NULL,
    Ext_column41 VARCHAR(50) NULL,
    Ext_column42 VARCHAR(50) NULL,
    Ext_column43 VARCHAR(50) NULL,
    Ext_column44 VARCHAR(50) NULL,
    Ext_column45 VARCHAR(50) NULL,   
    Ext_column46 VARCHAR(50) NULL,
    Ext_column47 VARCHAR(50) NULL,
    Ext_column48 VARCHAR(50) NULL,
    Ext_column49 VARCHAR(50) NULL,
    Ext_column50 VARCHAR(50) NULL,
    Ext_column51 VARCHAR(50) NULL,
    Ext_column52 VARCHAR(50) NULL,
    Ext_column53 VARCHAR(50) NULL,
    Ext_column54 VARCHAR(50) NULL,
    Ext_column55 VARCHAR(50) NULL,
    Ext_column56 VARCHAR(50) NULL,
    Ext_column57 VARCHAR(50) NULL,
    Ext_column58 VARCHAR(50) NULL,
    Ext_column59 VARCHAR(50) NULL,
    Ext_column60 VARCHAR(50) NULL,   
    Ext_column61 VARCHAR(50) NULL,
    Ext_column62 VARCHAR(50) NULL,
    Ext_column63 VARCHAR(50) NULL,
    Ext_column64 VARCHAR(50) NULL,
    Ext_column65 VARCHAR(50) NULL,
    Ext_column66 VARCHAR(50) NULL,
    Ext_column67 VARCHAR(50) NULL,
    Ext_column68 VARCHAR(50) NULL,
    Ext_column69 VARCHAR(50) NULL,
    Ext_column70 VARCHAR(50) NULL,
    Ext_column71 VARCHAR(50) NULL,
    Ext_column72 VARCHAR(50) NULL,
    Ext_column73 VARCHAR(50) NULL,
    Ext_column74 VARCHAR(50) NULL,
    Ext_column75 VARCHAR(50) NULL,   
    Ext_column76 VARCHAR(50) NULL,
    Ext_column77 VARCHAR(50) NULL,
    Ext_column78 VARCHAR(50) NULL,
    Ext_column79 VARCHAR(50) NULL,
    Ext_column80 VARCHAR(50) NULL,
    Ext_column81 VARCHAR(50) NULL,
    Ext_column82 VARCHAR(50) NULL,
    Ext_column83 VARCHAR(50) NULL,
    Ext_column84 VARCHAR(50) NULL,
    Ext_column85 VARCHAR(50) NULL,   
    Ext_column86 VARCHAR(50) NULL,
    Ext_column87 VARCHAR(50) NULL,
    Ext_column88 VARCHAR(50) NULL,
    Ext_column89 VARCHAR(50) NULL,
    Ext_column90 VARCHAR(50) NULL,
    Ext_column91 VARCHAR(50) NULL,
    Ext_column92 VARCHAR(50) NULL,
    Ext_column93 VARCHAR(50) NULL,
    Ext_column94 VARCHAR(50) NULL,
    Ext_column95 VARCHAR(50) NULL,   
    Ext_column96 VARCHAR(50) NULL,
    Ext_column97 VARCHAR(50) NULL,
    Ext_column98 VARCHAR(50) NULL,
    Ext_column99 VARCHAR(50) NULL,
    Ext_column100 VARCHAR(50) NULL
);
GO


-------------------------------------------------------------
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
------------------------------------------------------------
if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TradeWay]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TradeWay]
GO
CREATE TABLE TradeWay (
	TradeWayId bigint NOT NULL ,
	TradeWayName varchar (80) NULL ,
	ParentTradeWayId bigint NULL 
) 
GO

if exists (select * from sysobjects where id = object_id('RelationUser') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table RelationUser
GO

create table RelationUser(
    [Id] bigint IDENTITY (1, 1) NOT NULL primary key,
    [RealName] [varchar] (100) NULL ,
    [CustomerID] bigint null,
    [Address] [varchar] (100) NULL ,
    [Tel] [varchar] (40) NULL ,
    [Fax] [varchar] (40) NULL ,
    [ZipCode]  [varchar] (40) NULL
)


if exists (select * from sysobjects where id = object_id('Customer') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table Customer
GO

create table Customer(
    [Id] bigint IDENTITY (1, 1) NOT NULL  primary key,
    [IsBigCustomer] int NULL,
    [Name] [varchar] (100) NULL ,
    [ShortName] [varchar] (50) NULL ,
    [Address] [varchar] (100) NULL ,
    --[Tel] [varchar] (40) NULL ,
    --[Fax] [varchar] (40) NULL ,
    [ZipCode]  [varchar] (40) NULL,
    [TradeWayID] BIGINT NULL,
    oldBigCustomerId bigint null
)
GO

----------------------------------------------------------
declare @t table(表名 varchar(100),字段名 varchar(100),数据类型 varchar(100),长度 varchar(100),描述 sql_variant )
declare @table varchar(100),@counter int,@tablecount int,@talben varchar(100),@a varchar(100),@b varchar(100),@c varchar(100),@d varchar(100),@e varchar(100)
select @tablecount=count(name) from sysobjects where xtype='u'
declare tablen cursor for
select name from sysobjects where xtype ='U'
open tablen
set @counter=1
while @counter<@tablecount
begin
  fetch next from tablen
  into @talben
  insert @t SELECT  表名=case when a.colorder=1 then d.name else '' end,
字段名=a.name,
类型=b.name,
长度=COLUMNPROPERTY(a.id,a.name,'PRECISION'),
字段描述=isnull(g.[value],'')
FROM syscolumns a
left join systypes b on a.xtype=b.xusertype
inner join sysobjects d on a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties'
left join syscomments e on a.cdefault=e.id
  --insert @t select @talben

set @counter=@counter+1
end
close tablen
deallocate tablen
left join sysproperties g on a.id=g.id and a.colid=g.smallid  
left join sysproperties f on d.id=f.id and f.smallid=0
  where d.id=object_id(@talben) 
  order by a.id,a.colorder
select * from @t
----------------------------------------------------------
SELECT *, USER_ROLE.USER_ID AS Expr1, ROLE_AUTHOR.ROLE_ID AS Expr2, 
      AUTHORIZATIONS.RESOURCE_ID AS Expr3, AUTHORIZATIONS.RIGHT_ID AS Expr4, 
      USERS.USERNAME AS Expr5
FROM USERS INNER JOIN
      USER_ROLE ON USERS.ID = USER_ROLE.USER_ID INNER JOIN
      ROLES ON USER_ROLE.ROLE_ID = ROLES.ID INNER JOIN
      ROLE_AUTHOR ON ROLES.ID = ROLE_AUTHOR.ROLE_ID INNER JOIN
      AUTHORIZATIONS ON 
      ROLE_AUTHOR.AUTHOR_ID = AUTHORIZATIONS.ID INNER JOIN
      RESOURCES ON AUTHORIZATIONS.RESOURCE_ID = RESOURCES.ID INNER JOIN
      RIGHTS ON AUTHORIZATIONS.RIGHT_ID = RIGHTS.ID
WHERE (USERS.USERNAME = 'user')

--------------系统表结构定义表-------------------------------------------------------------

CREATE TABLE tSystemTableDefinition(
   ID BIGINT IDENTITY PRIMARY KEY,
   TableID BIGINT NULL,
   TableName VARCHAR(20) NULL,
   ColumnName VARCHAR(50)  NULL,
   ColumnCnName VARCHAR(50)  NULL,
   DataType VARCHAR(50) NULL,
   Length VARCHAR(5) null,
   Descn VARCHAR(50) NULL
);
GO

-----------初始化表结构定义数据---------------------------------------------------
insert into tSystemTableDefinition
(TableName,ColumnName,ColumnCnName,DataType,Length)

select o.name tablename, c.name fieldname, null,t.name fieldtype, 
  columnproperty(c.id,c.name,'PRECISION') fieldlen

from sysobjects o, syscolumns c, systypes t, syscomments m
where o.xtype='U'
  and o.id=c.id 
  and c.xtype=t.xtype
  and c.cdefault*=m.id
  and o.name not like 't%' 
  and o.name not like 'd%'
  and o.name not like 's%'
order by o.name, c.colid

go
---------------

------------全面的表格定义脚本---------------------------------------------
------------http://www.cnblogs.com/jacker1979/archive/2006/11/07/553387.html

select o.name tablename, c.name fieldname, t.name fieldtype, 
  columnproperty(c.id,c.name,'PRECISION') fieldlen, c.Scale,c.length,
  c.colid fieldorder, c.isnullable, 
  case when c.colid in(select ik.colid
    from sysindexes i, Sysindexkeys ik, sysobjects oo
    where i.id=ik.id and i.indid=ik.indid
      and i.name=oo.name and oo.xtype='PK' --主键
      and o.id=i.id 
  ) then 1 else 0 end isPrimaryKey,
  case when c.colid in(select ik.colid
    from sysindexes i, Sysindexkeys ik
    where i.id=ik.id and i.indid=ik.indid
      and o.id=i.id and i.indid=1 --聚类索引
  ) then 1 else 0 end isClusterKey,
  columnproperty( c.id, c.name,'IsIdentity') IsIdentity,
  isnull(m.text,'') defaultvalue

from sysobjects o, syscolumns c, systypes t, syscomments m
where o.xtype='U'
  and o.id=c.id 
  and c.xtype=t.xtype
  and c.cdefault*=m.id
  and o.name not like 't%' 
  and o.name not like 'd%'
  and o.name not like 's%'
order by o.name, c.colid

go
---------------





