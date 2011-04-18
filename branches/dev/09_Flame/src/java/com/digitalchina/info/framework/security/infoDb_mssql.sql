if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_ROLE_AUTHOR_AUTHORIZATIONS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sRoleAuthoriz] DROP CONSTRAINT FK_ROLE_AUTHOR_AUTHORIZATIONS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_AUTHORIZATIONS_RESOURCES]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sAuthorizations] DROP CONSTRAINT FK_AUTHORIZATIONS_RESOURCES
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_AUTHORIZATIONS_RIGHTS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sAuthorizations] DROP CONSTRAINT FK_AUTHORIZATIONS_RIGHTS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_ROLE_AUTHOR_ROLES]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sRoleAuthoriz] DROP CONSTRAINT FK_ROLE_AUTHOR_ROLES
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_USER_ROLE_ROLES]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sUserRole] DROP CONSTRAINT FK_USER_ROLE_ROLES
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_USER_ROLE_USERS]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[sUserRole] DROP CONSTRAINT FK_USER_ROLE_USERS
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_tExtendTable_tSystemExtTable]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[tExtendTable] DROP CONSTRAINT FK_tExtendTable_tSystemExtTable
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_tExtendTable_tSystemMainTable]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[tExtendTable] DROP CONSTRAINT FK_tExtendTable_tSystemMainTable
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sAuthorizations]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sAuthorizations]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sModules]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sModules]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sResources]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sResources]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRights]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRights]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRoleAuthoriz]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRoleAuthoriz]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sRoles]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sRoles]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sUserInfos]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sUserInfos]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[sUserRole]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[sUserRole]
GO

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

CREATE TABLE [dbo].[sAuthorizations] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[NAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[RESOURCE_ID] [bigint] NULL ,
	[RIGHT_ID] [bigint] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sModules] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[PARENT_MODULE_ID] [bigint] NULL ,
	[NAME] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[SERVICE_KEY_NAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[URL] [varchar] (150) COLLATE Chinese_PRC_CI_AS NULL ,
	[DESCN] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sResources] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[NAME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[TYPE] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[CLASS_NAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[METHOD_NAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[MODULE_ID] [bigint] NULL ,
	[DESCN] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sRights] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[NAME] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[KEY_NAME] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL ,
	[DESCN] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sRoleAuthoriz] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[ROLE_ID] [bigint] NULL ,
	[AUTHOR_ID] [bigint] NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sRoles] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[NAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[DESCN] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sUserInfos] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[USERNAME] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[PASSWORD] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,
	[REALNAME] [varchar] (80) COLLATE Chinese_PRC_CI_AS NULL ,
	[ENABLED] [int] NULL ,
	[EMAIL] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[sUserRole] (
	[ID] [bigint] IDENTITY (1, 1) NOT NULL ,
	[USER_ID] [bigint] NULL ,
	[ROLE_ID] [bigint] NULL 
) ON [PRIMARY]
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

