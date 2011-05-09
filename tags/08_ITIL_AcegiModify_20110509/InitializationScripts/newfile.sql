--�������ɾ���ķ���Ŀ¼
delete from dbo.ServiceCatalogue where status=-1;

--ʹ��SQL����ȡ����

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

INSERT INTO ConfigItemStatus(id, name, enname) VALUES (1, '��ע��', 'Registered');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (2, '���µ�', 'ordered');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (3, '�ڿ�', 'in stock');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (4, '�Ѱ�װ', 'installed');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (5, '�ڲ�', 'test');


INSERT INTO ConfigItemStatus(id, name, enname) VALUES (6, '������', 'development');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (7, 'ʹ����', 'production');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (8, '�ճ�ά��', 'maintenance');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (9, 'ά����', 'Repair');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (10, '����', 'standby');

INSERT INTO ConfigItemStatus(id, name, enname) VALUES (11, '����', 'Disabled');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (12, '����', 'Reserved');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (13, '�ѹ鵵', 'Archived');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (14, '���', 'loan');
INSERT INTO ConfigItemStatus(id, name, enname) VALUES (15, '����', 'Unknown');
go


insert into tSystemTableColumnCondition(systemMainTable, mainTableColumn, operator, value_, logicType, tableDefaultFlag)
values(419, 3276, 2, 1, 1, 1);
go

--itcode���� Ա����ţ����ű�ţ��ɱ����ģ��������Ƶȵȡ�
--�����˻��ˣ����ţ��ɱ����ģ���ϵ�绰�Զ��������������ű�š�
--�����ֶηִ��Ҳ��С��
--�Զ��������ֶ��Զ����ı�������ˡ�
--���������Զ����ֶο��Բ���һ��ʵ�ʵ��ֶΡ�
--�����˵绰��Ҫ��ʵ�ֶο����޸ġ��绰��Ϣ���������������档

--�����������Щ�ֶξʹ���һ�����⣬�ֶα����̨�������Ƿ���
--��ȡ���ɼ��ֶ�ʱ���û����͵��Զ������ֶ�����3���ֶΣ���ʾ��ǰ�ˡ�


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
--��̨error

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

--IndividuationRequire��Ҫ�ˣ���̨ɾ�������
--�ֶ�ɾ��a��ͷ����������

--�ֶ�ɾ�����������������ʦ����ģ�Ȼ����ִ�С����š���ʾ�������
--������ʾ�÷������·

--�������ͬ��һ�����
--B2B�����¼����������������м�һ��������������

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


