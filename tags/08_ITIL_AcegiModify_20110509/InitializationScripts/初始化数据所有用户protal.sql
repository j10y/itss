----------��ձ�����----------
delete from sys_portal_container
delete from sys_portal
delete from sys_portal_column
delete from sys_portletsubscribe

---��ʼ��sys_portal_container------
insert into sys_portal_container(name,userInfo,createTime) (SELECT userName + '_��ҳ', ID, GETDATE() FROM sUserInfos)
---��ʼ��into sys_portal Ĭ�ϲ���id 146------
insert into sys_portal(name,portalContainer_id,portalColumnTemplate_id,createTime) (select '��ҳ',id,146, GETDATE() from sys_portal_container)
---��ʼ��sys_portal_column �ȳ�ʼ�� ��ҳ_0 ����------
insert into sys_portal_column(name,icon,iconCls,portal_id,singleColumnScale,createTime)(select '��ҳ_0',null,null,id,50,GETDATE() from sys_portal)

---��ʼ��sys_portletsubscribe �ȳ�ʼ�� ��ҳ_0 ���� ָ��ĳ��portal���͵�id��˳��------
insert into sys_portletsubscribe( name,portlet_id,portalColumn_id,createTime,orderindex)(select '�������(������)',170,id,getdate(),0 from sys_portal_column where name='��ҳ_0')

---��ʼ��sys_portal_column �ȳ�ʼ�� ��ҳ_1 ����------
insert into sys_portal_column(name,icon,iconCls,portal_id,singleColumnScale,createTime)(select '��ҳ_1',null,null,id,50,GETDATE() from sys_portal)

---��ʼ��sys_portletsubscribe ��ʼ�� ��ҳ_1 ���� ָ��ĳ��portal���͵�id��˳��------
insert into sys_portletsubscribe( name,portlet_id,portalColumn_id,createTime,orderindex)(select '��������(������)',164,id,getdate(),0 from sys_portal_column where name='��ҳ_1')

