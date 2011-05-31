<%@ page contentType="text/html; charset=GBK" %>
<HTML>
<HEAD>
<TITLE>InfoDB数据定制框架后台管理系统</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META NAME="Author" CONTENT="InfoDB数据定制框架后台管理系统" />
<META NAME="Keywords" CONTENT="" />
<META NAME="Description" CONTENT="" /> 
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script>
function closewin() {
   if (opener!=null && !opener.closed) {
      opener.window.newwin=null;
      opener.openbutton.disabled=false;
      opener.closebutton.disabled=true;
   }
}

var count=0;//做计数器
var limit=new Array();//用于记录当前显示的哪几个菜单
var countlimit=1;//同时打开菜单数目，可自定义

function expandIt(el) {
   obj = eval("sub" + el);
   if (obj.style.display == "none") {
      obj.style.display = "block";//显示子菜单
      if (count<countlimit) {//限制2个
         limit[count]=el;//录入数组
         count++;
      }
      else {
         eval("sub" + limit[0]).style.display = "none";
         for (i=0;i<limit.length-1;i++) {limit[i]=limit[i+1];}//数组去掉头一位，后面的往前挪一位
         limit[limit.length-1]=el;
      }
   }
   else {
      obj.style.display = "none";
      var j;
      for (i=0;i<limit.length;i++) {if (limit[i]==el) j=i;}//获取当前点击的菜单在limit数组中的位置
      for (i=j;i<limit.length-1;i++) {limit[i]=limit[i+1];}//j以后的数组全部往前挪一位
      limit[limit.length-1]=null;//删除数组最后一位
      count--;
   }
}
</script>
</HEAD>

<BODY background="${pageContext.request.contextPath}/images/SysLeft_bg.gif" onmouseover="self.status='全心全意为您打造!';return true">
<TABLE height=26 cellSpacing=0 cellPadding=0 width=167 
background="${pageContext.request.contextPath}/images/SysLeft_bg_click.gif" border=0>
  <TBODY>
  <TR style="CURSOR: hand">
    <TD><A onclick='changeAdminFlag("返回后台主页")' 
      href="${pageContext.request.contextPath}/infoAdmin/sysCome.jsp" 
      target=mainFrame><IMG src="${pageContext.request.contextPath}/images/title.gif" width="170" height="40" 
  border=0></A></TD>
  </TR></TBODY></TABLE> 
  

<div id="main9" onclick=expandIt(9)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">数据模型管理</td>
    </tr>
  </table>
</div>
<div id="sub9" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("数据模型维护")'>数据模型维护</a>
   </td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据列表")'>新增数据模型</a></td>
  </tr>
 
 <tr> 
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toAdd" 
    	target="mainFrame" onClick='changeAdminFlag("增加映射信息")'>增加映射信息</a></td>
  </tr>

 <tr> 
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/idgen/tableIdGenList.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("增加映射信息")'>编号生成器</a></td>
  </tr>
 
</table>
</div>


<div id="main10" onclick=expandIt(10)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">配置项模型管理</td>
    </tr>
  </table>
</div>
<div id="sub10" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">

  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据模型")'>配置项数据模型管理</a></td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据列表")'>新增配置项模型</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userExpand/user_table_cir_data.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据列表")'>配置项关系导入</a></td>
  </tr>

</table>
</div>


<div id="main11" onclick=expandIt(11)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">服务项模型管理</td>
    </tr>
  </table>
</div>
<div id="sub11" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
    <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/ServiceItemTypeAction.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("服务项类型管理")'>服务项类型管理</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <!-- <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_listForIssue.action?pageNo=1"  -->
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/sci_list.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("服务项数据管理")'>服务项数据发布管理</a></td>
  </tr>

  <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_list.action?pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("服务项数据管理")'>服务项数据管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_listForIssue.action?pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("服务项数据管理")'>服务项发布管理</a></td>
  </tr> -->
  <!--<tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/req_table_list.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据列表")'>新增服务项需求主表</a></td>
  </tr>-->
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("新增数据列表")'>新增服务项模型</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/requireApplyDefaultAudit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("ERP服务审批人管理")'>需求审批人管理</a></td>
  </tr>
   <tr>
   	<td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/requireAppSystem.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("需求应用系统管理")'>需求应用系统管理</a></td>
  </tr>
    <tr>
   	<td width="40" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/systemAppAdmin.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("系统及应用负责人管理")'>系统及应用负责人管理</a></td>
  </tr>
 
</table>
</div>  
  <!--  
<div id="main28" onclick=expandIt(28)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">用户主表管理</td>
    </tr>
  </table>
</div>
<div id="sub28" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("用户主表列")'>用户主表列表</a></td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/userMainTablePanel.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("用户主表面")'>用户主表面板</a></td>
  </tr>
 
</table>
</div>


<div id="main22" onclick=expandIt(22)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">页面面板管理</td>
    </tr>
  </table>
</div>
<div id="sub22" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>基本面板管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>分组面板管理</a></td>
  </tr>

 
</table>
</div>-->
 

<div id="main21" onclick=expandIt(21)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">页面模型管理</td>
    </tr>
  </table>
</div>
<div id="sub21" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>基本面板管理</a></td>
  </tr>
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>分组面板管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>页面模型管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelExpandManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>页面模型管理(展开)</a></td>
  </tr>
 <!--  
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=toPageModelEditForm"  
    	target="mainFrame" onClick='changeAdminFlag("增加系统主表")'>增加页面模型</a></td>
  </tr>-->
 
</table>
</div>
 
<div id="main24" onclick=expandIt(24)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">流程模型管理</td>
    </tr>
  </table>
</div>


<div id="sub24" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">

   <!--<tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("流程类型管理")'>流程类型管理</a></td>
  </tr> -->
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("发布工作流定义")'>发布工作流定义</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/listProcess.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("流程配置")'>工作流程配置</a></td>
  </tr>

  <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("流程发布")'>流程发布</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/processConfig.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("流程配置")'>流程配置</a></td>
  </tr>
  -->  

 <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listdefinition.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("启动工作流定义")'>启动工作流定义</a></td>
  </tr>
 -->
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listprocess.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("流程监控")'>工作流程监控</a></td>
  </tr>
  
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listTask.do?methodCall=listAll" 
    	target="mainFrame" onClick='changeAdminFlag("所有任务监控")'>所有任务监控</a></td>
  </tr>
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listTask.do?methodCall=logon" 
    	target="mainFrame" onClick='changeAdminFlag("用户任务")'>用户任务委派</a></td>
  </tr>
  
  <!--
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/deployProcess.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("发布工作流定义")'>发布工作流定义</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listtask.do?methodCall=logon" 
    	target="mainFrame" onClick='changeAdminFlag("用户任务")'>用户任务</a></td>
  </tr>
 
 
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("流程任务指派")'>任务预指派</a></td>
  </tr>
  
  <!-- 
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/workflowRole.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("流程角色管理")'>流程角色管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=listPageModeNodes" 
    	target="mainFrame" onClick='changeAdminFlag("流程角色管理")'>工作流页面</a></td>
  </tr>
   -->
    <tr> 
    <td width="36" height="22"></td>
     
     
     <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("配置单元管理")'>流程组件管理</a></td>
     <!--
     <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnitRole.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("配置单元管理")'>配置单元角色管理</a></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("配置单元管理")'>配置单元管理</a></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeType.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("配置单元管理")'>配置单元管理</a></td>-->
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeType.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("配置模型管理")'>节点类型管理</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/modleToProcess/modletoprocess.jsp"
    	target="mainFrame" onClick='changeAdminFlag("流程模块设定")'>流程模块设定</a></td>
  </tr>
</table>
</div>
 
 
<div id="main23" onclick=expandIt(23)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">

    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">系统安全管理</td>
    </tr>
  </table>
</div>
<div id="sub23" style="display:none">
  <table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
   <tr>
      <td width="36" height="22"></td>
      <td class="SystemLeft"><a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=listModules" 
      	target="mainFrame" onClick='changeAdminFlag("模块列表")'>模块列表</a></td>
    </tr>
    
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("部门查询")' 
      href="${pageContext.request.contextPath}/infoAdmin/security/DepartmentQuery.jsp" 
      target=mainFrame>部门查询</A></TD>
   </TR>
      
     <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("配置资源")' 
      href="${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=listResources" 
      target=mainFrame>资源管理</A></TD></TR>
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("权限列表")' 
      href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights" 
      target=mainFrame>权限列表</A></TD></TR>
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("资源授权")' 
      href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations" 
      target=mainFrame>资源授权</A></TD></TR>
  <!-- 
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("角色管理")' 
      href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=listRoles" 
      target=mainFrame>角色管理</A></TD>
    </TR>-->
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("角色管理(新)")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/roleManager.jsp" 
      target=mainFrame>角色管理</A></TD>
    </TR>
  <!-- <TR>
    <TD width=36 height=22>
   </TD>
    <TD class=SystemLeft>
    <A onclick='changeAdminFlag("用户授权")' 
      href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers" 
      target=mainFrame>用户管理</A>
      
     
      </TD>
  </TR>-->
  
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("用户管理（新）")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userManager.jsp" 
      target=mainFrame>用户管理</A></TD>
    </TR>
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("锁定用户管理")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userLockManager.jsp" 
      target=mainFrame>锁定用户管理</A></TD>
    </TR>
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("外部用户管理")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userOuterManager.jsp" 
      target=mainFrame>外部用户管理</A></TD>
    </TR>
  </table>
</div>

<!--  
<div id="main18" onclick=expandIt(18)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_8.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">系统模板管理</td>
    </tr>
  </table>
</div
-->
<div id="sub18" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/template/templateManage.do?methodCall=listTemplate" 
    	target="mainFrame" onClick='changeAdminFlag("系统模板")'>系统模板</a></td>
  </tr>
  
</table>
</div>

<div id="main8" onclick=expandIt(8)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_8.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">系统菜单管理</td>
    </tr>
  </table>
</div>
<div id="sub8" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/template_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("用户菜单模板")'>模板菜单（新）</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/dept_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("用户菜单模板")'>部门菜单（新）</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/user_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("用户菜单模板")'>用户额外菜单（新）</a></td>
  </tr>
 
</table>
</div>



<!--
<div id="main29" onclick=expandIt(29)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">系统报表管理</td>
    </tr>
  </table>
</div>
<div id="sub29" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=tjList&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>报表维护</a></td>
    
  </tr>
</table>
</div> 

-->
 <div id="main31" onclick=expandIt(31)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">用户登录日志管理</td>
    </tr>
  </table>
</div>
<div id="sub31" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/userLog.jsp"
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>用户访问记录</a></td>
    
  </tr>
</table>
</div> 
<!-- 
 <div id="main30" onclick=expandIt(30)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">拖动构建页面管理</td>
    </tr>
  </table>
</div>
<div id="sub30" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/dragBuildTemplate/dragBuildTemplate.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("系统主表列表")'>生成页面维护</a></td>
    
  </tr>
</table>
</div> 
-->
  


<%--<table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
  <tr style="cursor: hand;">
    <td width="26"></td>
    <td class="SystemLeft"><a onclick="window.parent.location='${pageContext.request.contextPath}/login.jsp'" 
    >重新登录</a></td>
  </tr>
</table>

<table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
  <tr style="cursor: hand;">
    <td width="26"></td>
    <td class="SystemLeft"><a onclick="window.parent.location='${pageContext.request.contextPath}/j_logout.do'"  >退出登录</a></td>
  </tr>
</table>

--%></BODY>
</HTML>