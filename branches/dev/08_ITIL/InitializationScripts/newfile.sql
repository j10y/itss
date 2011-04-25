--先清楚被删除的服务目录
delete from dbo.ServiceCatalogue where status=-1;

--使用SQL语句获取所有

delete from dbo.SCIRelationShip
where rootServiceCatalogue in(161,227,231,232,242,260,261,262,263);

---=======================================
delete from dbo.SCIRelationShip
where rootServiceCatalogue
not in( select id from ServiceCatalogue);
--========================================
delete from dbo.SCIRelationShip
where rootServiceCatalogue is null;

--=======================================
select * from ServiceCatalogue
where id not in(
   select serviceCatalogue 
   from SCIRelationShip
);

delete from dbo.ServiceCatalogue where id in(161,227,231,232,242,260,261,262,263,264,265,287);
delete from ServiceCatalogueAuditHis;

delete from dbo.ServiceCatalogueContract
where serviceCatalogue
not in( select id from ServiceCatalogue);

delete from dbo.ServiceItemSLA
where serviceCatalogue
not in( select id from ServiceCatalogue);
-----==================================================
delete  from dbo.SCIRelationShip
where rootServiceCatalogue
not in( select id from ServiceCatalogue);

delete  from dbo.SCIRelationShip
where serviceCatalogue
not in( select id from ServiceCatalogue);

delete from dbo.SCIRelationShip
where serviceItem
not in( select id from serviceItem);
--==========================================================================================

delete from CCCallInfo;
delete from Event;
delete from EventAssign;
delete from EventAuditHis;
delete from EventHandleLog;
delete from EventProblem;
delete from EventRelation;
delete from EventSulotion;
delete from Problem;
delete from ProblemAssign;
delete from ProblemHandleLog;
delete from ProblemRelation;
delete from KnowContract;
delete from KnowFile;

--==================
delete from NoticeAuditHis;
delete from Notice;
delete from NewNotice;
--==========================================================================================



delete from CIRelationShip;
delete from CIRelationShipItem;
delete from CIRelationShipPic;
delete from CIRelationShipPicNode;

delete from ConfigItemAuditHis
where configItem not in
(select id from ConfigItem);


delete from ConfigItemEvent;

delete from ConfigItemExtendInfo
where configItem not in(
  select id from ConfigItem);

delete from ConfigItemFinanceInfo
where configItem not in( select id from ConfigItem);

delete from ConfigItemFinanceInfo
where configItem in( 
	select id from ConfigItem 
	where status !=1
);

delete from ConfigItemFinanceInfo
where configItem in( 
	select id from ConfigItem 
	where status !=1
);

delete from ConfigItemExtendInfo
where configItem in( 
	select id from ConfigItem 
	where status !=1
);

delete from ConfigItemExtendInfo
where configItem in( 
	select id from ConfigItem 
	where status !=1
);


delete from supportGroup where deleteFlag = 1;


----++++++++++++=======================================================
delete from ConfigItemStatus;
go

INSERT INTO ConfigItemStatus(id, name, enname) VALUES (1, '已注册', 'Registered');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (2, '已下单', 'ordered');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (3, '在库', 'in stock');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (4, '已安装', 'installed');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (5, '在测', 'test');


INSERT INTO ConfigItemStatus(id, name, enname) VALUES (6, '开发中', 'development');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (7, '使用中', 'production');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (8, '日常维护', 'maintenance');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (9, '维修中', 'Repair');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (10, '备用', 'standby');

INSERT INTO ConfigItemStatus(id, name, enname) VALUES (11, '禁用', 'Disabled');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (12, '保留', 'Reserved');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (13, '已归档', 'Archived');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (14, '租出', 'loan');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (15, '其它', 'Unknown');
go


insert into tSystemTableColumnCondition(systemMainTable, mainTableColumn, operator, value_, logicType, tableDefaultFlag)
values(419, 3276, 2, 1, 1, 1);
go

--itcode带出 员工编号，部门编号，成本中心，部门名称等等。
--申请人换了，部门，成本中心，联系电话自动带出来或者随着编号。
--描述字段分大框也分小框。
--自动带出的字段自动用文本框就行了。
--带出并且自读的字段可以不用一个实际的字段。
--申请人电话需要真实字段可以修改。电话信息必须记入需求表里面。

--需求表不设置这些字段就存在一个问题，字段必须后台管理。看是否考虑
--获取面板可见字段时在用户类型的自动后面字段生成3个字段，显示到前端。


--==========================================================

delete from itil_sci_ERP_NormalNeed
delete from itil_sci_ErpEngineerFeedback
delete from itil_sci_ErpEngineerFeedback
delete from itil_sci_ProjectPlan
delete from itil_sci_ProjectRequireAnalyse
delete from itil_sci_ProjectTestReport
delete from itil_sci_ProjectTransmissionEngineer
delete from itil_sci_ProjectWorkReport
delete from itil_sci_RequirementCIRelationShip
delete from itil_sci_RequirementCIRelationShipPic
delete from itil_sci_RequirementContract
delete from itil_sci_RequirementEngineer
delete from itil_sci_RequirementGroupFinanceInfo
delete from itil_sci_RequirementServiceItem
delete from itil_sci_RequirementServiceProvider
delete from itil_sci_SpecialRequirement
delete from itil_sci_SpecialRequirementEvent
delete from ServiceItemApplyAuditHis

--==========================================================
--后台error

SELECT *, ForeignTableID AS Expr1, ForeignTableValueColumnID AS Expr2
FROM tSystemMainTableColumn
WHERE (ForeignTableID = 29) AND (ForeignTableValueColumnID = 18)
go

UPDATE tSystemMainTableColumn 
SET ForeignTableValueColumnID=1278
WHERE (ForeignTableID = 29) AND (ForeignTableValueColumnID = 18)
GO

--==========================================================



delete from ServiceItem 
where id in(175,177,173,154,153,137,136,112,111,110)

--IndividuationRequire不要了，后台删除这个表
--手动删除a开头的垃圾主表

--手动删除垃圾配置项，即工程师下面的，然后再执行。留着“演示用配置项”
--保留演示用服务面具路

--给服务合同补一个编号
--B2B服务，事件或需求审批过程中加一个服务器配置项

delete from supportGroup where deleteFlag = 1;
delete from SupportGroupServiceItem
where supportGroup not in(
 select id from SupportGroup
);

delete from SupportGroupEngineer
where supportGroup not in(
 select id from SupportGroup
);


delete from SupportGroupServiceItem
where serviceItem not in(
 select id from ServiceItem
);



delete from SupportGroupServiceItem
where supportGroup not in(
 select id from supportGroup
);


