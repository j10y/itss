--itil本地对应cc那边oracle同样结构的表；

CREATE TABLE [dbo].[CC_LOG1_AGENTSKILLGCHANGE] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[AGENTID] [varchar] (8) COLLATE Chinese_PRC_CI_AS NULL ,
	[DN] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[LOGINTIME] [datetime] NULL ,
	[SKILLG] [varchar] (255) COLLATE Chinese_PRC_CI_AS NULL ,
	[MILLISEC] [varchar] (9) COLLATE Chinese_PRC_CI_AS NULL ,
	[LOGTAG] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CC_LOG1_AGENTSTATUSCHANGE] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[AGENTID] [varchar] (8) COLLATE Chinese_PRC_CI_AS NULL ,
	[OPERTYPE] [varchar] (2) COLLATE Chinese_PRC_CI_AS NULL ,
	[STARTTIME] [datetime] NULL ,
	[ENDTIME] [datetime] NULL ,
	[TIMESPAN] [numeric](18, 0) NULL ,
	[MILLISEC] [varchar] (50) COLLATE Chinese_PRC_CI_AS NULL ,
	[LOGTAG] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CC_LOG1_AGENTWORKSUM] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[AGENTID] [varchar] (8) COLLATE Chinese_PRC_CI_AS NULL ,
	[SKILLG] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[GLEVEL] [numeric](18, 0) NULL ,
	[ACTIONTYPE] [varchar] (2) COLLATE Chinese_PRC_CI_AS NULL ,
	[ORIGID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLERID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLEDID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[RINGTIME] [datetime] NULL ,
	[CONNECTTIME] [datetime] NULL ,
	[ENDTIME] [datetime] NULL ,
	[MILLISEC] [varchar] (9) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLID] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CC_LOG1_IVRWORKSUM] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[DN] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[ACTIONIO] [varchar] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[ORIGID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLERID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLEDID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[STARTTIME] [datetime] NULL ,
	[ENDTIME] [datetime] NULL ,
	[TIMESPAN] [numeric](18, 0) NULL ,
	[TAG] [numeric](18, 0) NULL ,
	[MILLISEC] [varchar] (9) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLID] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CC_LOG1_SKILLGWORKSUM] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[SKILLG] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[GLEVEL] [numeric](18, 0) NULL ,
	[DN] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[RESULT] [varchar] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[ROUTEVALUE] [varchar] (6) COLLATE Chinese_PRC_CI_AS NULL ,
	[ORIGID] [varchar] (15) COLLATE Chinese_PRC_CI_AS NULL ,
	[ENTERTIME] [datetime] NULL ,
	[ASSIGNTIME] [datetime] NULL ,
	[LEAVETIME] [datetime] NULL ,
	[TAG] [numeric](18, 0) NULL ,
	[MILLISEC] [varchar] (9) COLLATE Chinese_PRC_CI_AS NULL ,
	[CALLID] [varchar] (64) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[CC_TBL_IVR_SATISFY] (
	[id] [bigint] IDENTITY (1, 1) NOT NULL ,
	[SERVICE_ID] [varchar] (40) COLLATE Chinese_PRC_CI_AS NULL ,
	[HANDLE] [varchar] (40) COLLATE Chinese_PRC_CI_AS NULL ,
	[AGENTCODE] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[AGENTDEVICE] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[CODE] [varchar] (1) COLLATE Chinese_PRC_CI_AS NULL ,
	[ANI] [varchar] (20) COLLATE Chinese_PRC_CI_AS NULL ,
	[TIME] [varchar] (30) COLLATE Chinese_PRC_CI_AS NULL 
) ON [PRIMARY]
GO




---建立数据库链接，链接名称是demo-------------

EXEC sp_addlinkedserver 
  @server ='demo',
  @srvproduct='Oracle',
  @provider='MSDAORA', 
  @datasrc='B2B108'  
--SQLOLEDB

EXEC sp_addlinkedsrvlogin 
     'demo', 
     'false', 
     NULL, 
     'its_cc', 
     'its_cc' 
go



--------------------
create view itil_v_CCAgentSkillgChange
as 
select * FROM demo..ITS_CC.LOG1_AGENTSKILLGCHANGE
GO

create view itil_v_CCAgentStatusChange
as 
select * FROM demo..ITS_CC.LOG1_AGENTSTATUSCHANGE
GO


create view itil_v_CCIVRWorkSum
as 
select * FROM demo..ITS_CC.LOG1_IVRWORKSUM
GO



---SQLServer读取Oracle数据库-------------


create view v_brand
as 
select * FROM B2B108..BTBTEST2.BRAND;
GO

create  proc pro_brand 
as 
declare @id int 
declare @brandname varchar(100) 

DECLARE cur_sql CURSOR FOR 
  select id,brandname from v_brand
OPEN cur_sql

FETCH NEXT FROM cur_sql into @id,@brandname
WHILE @@FETCH_STATUS = 0
begin  
 SET IDENTITY_INSERT brand2 on;
 insert into brand2(id,brandname)
 values (@id,@brandname);

 SET IDENTITY_INSERT brand2 off;
end

CLOSE cur_sql
deallocate pro_brand 
go

delete from brand2;
go

EXEC pro_brand;