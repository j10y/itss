----------清空表数据----------
delete from sys_portal_container
delete from sys_portal
delete from sys_portal_column
delete from sys_portletsubscribe

---初始化sys_portal_container------
insert into sys_portal_container(name,userInfo,createTime) (SELECT userName + '_首页', ID, GETDATE() FROM sUserInfos)
---初始化into sys_portal 默认布局id 146------
insert into sys_portal(name,portalContainer_id,portalColumnTemplate_id,createTime) (select '首页',id,146, GETDATE() from sys_portal_container)
---初始化sys_portal_column 先初始化 首页_0 部分------
insert into sys_portal_column(name,icon,iconCls,portal_id,singleColumnScale,createTime)(select '首页_0',null,null,id,50,GETDATE() from sys_portal)

---初始化sys_portletsubscribe 先初始化 首页_0 部分 指定某个portal类型的id，顺序------
insert into sys_portletsubscribe( name,portlet_id,portalColumn_id,createTime,orderindex)(select '我申请的(处理中)',170,id,getdate(),0 from sys_portal_column where name='首页_0')

---初始化sys_portal_column 先初始化 首页_1 部分------
insert into sys_portal_column(name,icon,iconCls,portal_id,singleColumnScale,createTime)(select '首页_1',null,null,id,50,GETDATE() from sys_portal)

---初始化sys_portletsubscribe 初始化 首页_1 部分 指定某个portal类型的id，顺序------
insert into sys_portletsubscribe( name,portlet_id,portalColumn_id,createTime,orderindex)(select '我审批的(处理中)',164,id,getdate(),0 from sys_portal_column where name='首页_1')

