--------------------
SET NOCOUNT ON

DECLARE 
@userMenuId bigint

PRINT '--------begin--------'

DECLARE myCur CURSOR FOR 
 select id from tUserMenu

OPEN myCur

FETCH NEXT FROM myCur 
INTO @userMenuId
 
WHILE @@FETCH_STATUS = 0
BEGIN
  
 IF(select count(*) from tUserMenuItem where userMenu = @userMenuId )=0 
 
 BEGIN
    INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
   VALUES (@userMenuId,'IT需求受理与跟踪',NULL,65,NULL,0,NULL,1,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
   VALUES (@userMenuId,'快速通道','/user/require/fastReqServiceList.jsp?fastSiIds=301,318,322,324',66,@userMenuId*12-11,1,NULL,0,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'IT基础类网络/通讯/邮件等帐号及资源申请','/user/require/reqServiceList.jsp?serviceType=2',67,@userMenuId*12-11,1,NULL,1,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'IT业务应用系统类帐号申请','/user/require/reqServiceList.jsp?serviceType=3',68,@userMenuId*12-11,1,NULL,2,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'IT需求申请（ERP类）','/user/require/reqServiceList.jsp?serviceType=4',69,@userMenuId*12-11,1,NULL,3,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'IT需求申请','/user/require/reqServiceList.jsp?serviceType=5',70,@userMenuId*12-11,1,NULL,4,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'流程需求申请','/user/require/reqServiceList.jsp?serviceType=6',71,@userMenuId*12-11,1,NULL,5,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'业务支援申请','/user/require/reqServiceList.jsp?serviceType=7',72,@userMenuId*12-11,1,NULL,6,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'我的需求查询','/user/require/requireSearchList.jsp',73,@userMenuId*12-11,1,NULL,7,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'我的帐号查询','/account/myAccount.do?methodCall=query',74,@userMenuId*12-11,1,NULL,8,1);
    INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
   VALUES (@userMenuId,'出差授权',NULL,77,NULL,0,NULL,4,1);
   INSERT INTO tUserMenuItem(userMenu,menuName,menuUrl,deptMenuTemplateItem,parentMenu,leafFlag,menuLevel,menuOrder,enabled)
    VALUES (@userMenuId,'我的出差授权','/infoAdmin/workflow/configPage/workflowProxyAuditPage.jsp',78,@userMenuId*12-1,1,NULL,0,1);
   PRINT @userMenuId
 END
 PRINT 'END'
 
   -- Get the next author.
   FETCH NEXT FROM myCur 
   INTO @userMenuId

END
PRINT '-------end --------'

CLOSE myCur
DEALLOCATE myCur