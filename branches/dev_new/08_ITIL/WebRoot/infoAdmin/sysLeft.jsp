<%@ page contentType="text/html; charset=GBK" %>
<HTML>
<HEAD>
<TITLE>InfoDB���ݶ��ƿ�ܺ�̨����ϵͳ</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META NAME="Author" CONTENT="InfoDB���ݶ��ƿ�ܺ�̨����ϵͳ" />
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

var count=0;//��������
var limit=new Array();//���ڼ�¼��ǰ��ʾ���ļ����˵�
var countlimit=1;//ͬʱ�򿪲˵���Ŀ�����Զ���

function expandIt(el) {
   obj = eval("sub" + el);
   if (obj.style.display == "none") {
      obj.style.display = "block";//��ʾ�Ӳ˵�
      if (count<countlimit) {//����2��
         limit[count]=el;//¼������
         count++;
      }
      else {
         eval("sub" + limit[0]).style.display = "none";
         for (i=0;i<limit.length-1;i++) {limit[i]=limit[i+1];}//����ȥ��ͷһλ���������ǰŲһλ
         limit[limit.length-1]=el;
      }
   }
   else {
      obj.style.display = "none";
      var j;
      for (i=0;i<limit.length;i++) {if (limit[i]==el) j=i;}//��ȡ��ǰ����Ĳ˵���limit�����е�λ��
      for (i=j;i<limit.length-1;i++) {limit[i]=limit[i+1];}//j�Ժ������ȫ����ǰŲһλ
      limit[limit.length-1]=null;//ɾ���������һλ
      count--;
   }
}
</script>
</HEAD>

<BODY background="${pageContext.request.contextPath}/images/SysLeft_bg.gif" onmouseover="self.status='ȫ��ȫ��Ϊ������!';return true">
<TABLE height=26 cellSpacing=0 cellPadding=0 width=167 
background="${pageContext.request.contextPath}/images/SysLeft_bg_click.gif" border=0>
  <TBODY>
  <TR style="CURSOR: hand">
    <TD><A onclick='changeAdminFlag("���غ�̨��ҳ")' 
      href="${pageContext.request.contextPath}/infoAdmin/sysCome.jsp" 
      target=mainFrame><IMG src="${pageContext.request.contextPath}/images/title.gif" width="170" height="40" 
  border=0></A></TD>
  </TR></TBODY></TABLE> 
  

<div id="main9" onclick=expandIt(9)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">����ģ�͹���</td>
    </tr>
  </table>
</div>
<div id="sub9" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("����ģ��ά��")'>����ģ��ά��</a>
   </td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������б�")'>��������ģ��</a></td>
  </tr>
 
 <tr> 
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toAdd" 
    	target="mainFrame" onClick='changeAdminFlag("����ӳ����Ϣ")'>����ӳ����Ϣ</a></td>
  </tr>

 <tr> 
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/idgen/tableIdGenList.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("����ӳ����Ϣ")'>���������</a></td>
  </tr>
 
</table>
</div>


<div id="main10" onclick=expandIt(10)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">������ģ�͹���</td>
    </tr>
  </table>
</div>
<div id="sub10" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">

  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("��������ģ��")'>����������ģ�͹���</a></td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������б�")'>����������ģ��</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userExpand/user_table_cir_data.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���������б�")'>�������ϵ����</a></td>
  </tr>

</table>
</div>


<div id="main11" onclick=expandIt(11)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">������ģ�͹���</td>
    </tr>
  </table>
</div>
<div id="sub11" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
    <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/ServiceItemTypeAction.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������͹���")'>���������͹���</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <!-- <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_listForIssue.action?pageNo=1"  -->
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/sci_list.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���������ݹ���")'>���������ݷ�������</a></td>
  </tr>

  <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_list.action?pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������ݹ���")'>���������ݹ���</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/serviceItem_listForIssue.action?pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������ݹ���")'>�����������</a></td>
  </tr> -->
  <!--<tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/req_table_list.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���������б�")'>������������������</a></td>
  </tr>-->
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userListMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("���������б�")'>����������ģ��</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/requireApplyDefaultAudit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("ERP���������˹���")'>���������˹���</a></td>
  </tr>
   <tr>
   	<td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/requireAppSystem.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("����Ӧ��ϵͳ����")'>����Ӧ��ϵͳ����</a></td>
  </tr>
    <tr>
   	<td width="40" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/serviceItem/systemAppAdmin.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ��Ӧ�ø����˹���")'>ϵͳ��Ӧ�ø����˹���</a></td>
  </tr>
 
</table>
</div>  
  <!--  
<div id="main28" onclick=expandIt(28)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">�û��������</td>
    </tr>
  </table>
</div>
<div id="sub28" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("�û�������")'>�û������б�</a></td>
  </tr>
 
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/userMainTablePanel.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("�û�������")'>�û��������</a></td>
  </tr>
 
</table>
</div>


<div id="main22" onclick=expandIt(22)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ҳ��������</td>
    </tr>
  </table>
</div>
<div id="sub22" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����������</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����������</a></td>
  </tr>

 
</table>
</div>-->
 

<div id="main21" onclick=expandIt(21)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ҳ��ģ�͹���</td>
    </tr>
  </table>
</div>
<div id="sub21" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����������</a></td>
  </tr>
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����������</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>ҳ��ģ�͹���</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelExpandManage.do?methodCall=list&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>ҳ��ģ�͹���(չ��)</a></td>
  </tr>
 <!--  
 <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=toPageModelEditForm"  
    	target="mainFrame" onClick='changeAdminFlag("����ϵͳ����")'>����ҳ��ģ��</a></td>
  </tr>-->
 
</table>
</div>
 
<div id="main24" onclick=expandIt(24)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">����ģ�͹���</td>
    </tr>
  </table>
</div>


<div id="sub24" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">

   <!--<tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("�������͹���")'>�������͹���</a></td>
  </tr> -->
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("��������������")'>��������������</a></td>
  </tr>
  
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/listProcess.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("��������")'>������������</a></td>
  </tr>

  <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("���̷���")'>���̷���</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/processConfig.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("��������")'>��������</a></td>
  </tr>
  -->  

 <!-- <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listdefinition.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("��������������")'>��������������</a></td>
  </tr>
 -->
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listprocess.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("���̼��")'>�������̼��</a></td>
  </tr>
  
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listTask.do?methodCall=listAll" 
    	target="mainFrame" onClick='changeAdminFlag("����������")'>����������</a></td>
  </tr>
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listTask.do?methodCall=logon" 
    	target="mainFrame" onClick='changeAdminFlag("�û�����")'>�û�����ί��</a></td>
  </tr>
  
  <!--
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/deployProcess.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("��������������")'>��������������</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/listtask.do?methodCall=logon" 
    	target="mainFrame" onClick='changeAdminFlag("�û�����")'>�û�����</a></td>
  </tr>
 
 
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=list" 
    	target="mainFrame" onClick='changeAdminFlag("��������ָ��")'>����Ԥָ��</a></td>
  </tr>
  
  <!-- 
   <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/workflowRole.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���̽�ɫ����")'>���̽�ɫ����</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=listPageModeNodes" 
    	target="mainFrame" onClick='changeAdminFlag("���̽�ɫ����")'>������ҳ��</a></td>
  </tr>
   -->
    <tr> 
    <td width="36" height="22"></td>
     
     
     <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���õ�Ԫ����")'>�����������</a></td>
     <!--
     <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnitRole.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���õ�Ԫ����")'>���õ�Ԫ��ɫ����</a></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/configUnit.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���õ�Ԫ����")'>���õ�Ԫ����</a></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeType.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("���õ�Ԫ����")'>���õ�Ԫ����</a></td>-->
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeType.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("����ģ�͹���")'>�ڵ����͹���</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/modleToProcess/modletoprocess.jsp"
    	target="mainFrame" onClick='changeAdminFlag("����ģ���趨")'>����ģ���趨</a></td>
  </tr>
</table>
</div>
 
 
<div id="main23" onclick=expandIt(23)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">

    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ϵͳ��ȫ����</td>
    </tr>
  </table>
</div>
<div id="sub23" style="display:none">
  <table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
   <tr>
      <td width="36" height="22"></td>
      <td class="SystemLeft"><a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=listModules" 
      	target="mainFrame" onClick='changeAdminFlag("ģ���б�")'>ģ���б�</a></td>
    </tr>
    
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("���Ų�ѯ")' 
      href="${pageContext.request.contextPath}/infoAdmin/security/DepartmentQuery.jsp" 
      target=mainFrame>���Ų�ѯ</A></TD>
   </TR>
      
     <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("������Դ")' 
      href="${pageContext.request.contextPath}/admin/resourceManage.do?methodCall=listResources" 
      target=mainFrame>��Դ����</A></TD></TR>
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("Ȩ���б�")' 
      href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights" 
      target=mainFrame>Ȩ���б�</A></TD></TR>
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("��Դ��Ȩ")' 
      href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations" 
      target=mainFrame>��Դ��Ȩ</A></TD></TR>
  <!-- 
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("��ɫ����")' 
      href="${pageContext.request.contextPath}/admin/roleManage.do?methodCall=listRoles" 
      target=mainFrame>��ɫ����</A></TD>
    </TR>-->
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("��ɫ����(��)")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/roleManager.jsp" 
      target=mainFrame>��ɫ����</A></TD>
    </TR>
  <!-- <TR>
    <TD width=36 height=22>
   </TD>
    <TD class=SystemLeft>
    <A onclick='changeAdminFlag("�û���Ȩ")' 
      href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers" 
      target=mainFrame>�û�����</A>
      
     
      </TD>
  </TR>-->
  
  <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("�û������£�")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userManager.jsp" 
      target=mainFrame>�û�����</A></TD>
    </TR>
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("�����û�����")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userLockManager.jsp" 
      target=mainFrame>�����û�����</A></TD>
    </TR>
   <TR>
    <TD width=36 height=22></TD>
    <TD class=SystemLeft><A onclick='changeAdminFlag("�ⲿ�û�����")' 
      href="${pageContext.request.contextPath}/infoAdmin/organization/userOuterManager.jsp" 
      target=mainFrame>�ⲿ�û�����</A></TD>
    </TR>
  </table>
</div>

<!--  
<div id="main18" onclick=expandIt(18)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_8.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ϵͳģ�����</td>
    </tr>
  </table>
</div
-->
<div id="sub18" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/template/templateManage.do?methodCall=listTemplate" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳģ��")'>ϵͳģ��</a></td>
  </tr>
  
</table>
</div>

<div id="main8" onclick=expandIt(8)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_8.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ϵͳ�˵�����</td>
    </tr>
  </table>
</div>
<div id="sub8" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/template_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("�û��˵�ģ��")'>ģ��˵����£�</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/dept_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("�û��˵�ģ��")'>���Ų˵����£�</a></td>
  </tr>
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/menu/user_menu.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("�û��˵�ģ��")'>�û�����˵����£�</a></td>
  </tr>
 
</table>
</div>



<!--
<div id="main29" onclick=expandIt(29)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">ϵͳ�������</td>
    </tr>
  </table>
</div>
<div id="sub29" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=tjList&pageNo=1" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����ά��</a></td>
    
  </tr>
</table>
</div> 

-->
 <div id="main31" onclick=expandIt(31)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">�û���¼��־����</td>
    </tr>
  </table>
</div>
<div id="sub31" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/userLog.jsp"
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>�û����ʼ�¼</a></td>
    
  </tr>
</table>
</div> 
<!-- 
 <div id="main30" onclick=expandIt(30)>
  <table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_9.gif">
    <tr style="cursor: hand;">
      <td width="26" ></td>
      <td class="SystemLeft">�϶�����ҳ�����</td>
    </tr>
  </table>
</div>
<div id="sub30" style="display:none">
<table width="160" border="0" cellspacing="0" cellpadding="0" background="${pageContext.request.contextPath}/images/SysLeft_bg_link.gif">
  <tr>
    <td width="36" height="22"></td>
    <td class="SystemLeft"><a href="${pageContext.request.contextPath}/infoAdmin/dragBuildTemplate/dragBuildTemplate.jsp" 
    	target="mainFrame" onClick='changeAdminFlag("ϵͳ�����б�")'>����ҳ��ά��</a></td>
    
  </tr>
</table>
</div> 
-->
  


<%--<table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
  <tr style="cursor: hand;">
    <td width="26"></td>
    <td class="SystemLeft"><a onclick="window.parent.location='${pageContext.request.contextPath}/login.jsp'" 
    >���µ�¼</a></td>
  </tr>
</table>

<table width="167" height="26" border="0" cellpadding="0" cellspacing="0" background="${pageContext.request.contextPath}/images/Admin_left_4.gif">
  <tr style="cursor: hand;">
    <td width="26"></td>
    <td class="SystemLeft"><a onclick="window.parent.location='${pageContext.request.contextPath}/j_logout.do'"  >�˳���¼</a></td>
  </tr>
</table>

--%></BODY>
</HTML>