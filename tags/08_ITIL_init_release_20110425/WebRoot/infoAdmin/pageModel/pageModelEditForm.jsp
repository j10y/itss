<%@ page pageEncoding="gbk" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>ϵͳ�����������ֶ�����</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>

<script type="text/javascript">
   //ģ��Id 
   var modId = '${detail.id}';
   var pageModuleName = '${requestScope.detail.title}'; 
   
</script>
<script type="text/javascript">
	function findSystemMainTableByModule(){ 	
	   var webContext = "${pageContext.request.contextPath}";
	   var fmodul = Ext.getDom("module");	   
	   var fsystemtable = Ext.getDom("SystemMainTableBelongModule");	   
	   var fmodulSelectedId = fmodul.options[fmodul.selectedIndex].value;
	   
	   Ext.Ajax.request({
	      url: webContext+'/pageModel/pageModelManage.do?methodCall=findSystemMainTableByModule', 
	      params:{
	          fmodulId: fmodulSelectedId
	      },
	      method:'POST',
	      success:function(response){
	        var data = response.responseText;
	        var result = Ext.decode(data);	        
	        var obj = result.data;
	        initSelect(obj, fsystemtable);	     		
	      }
	  });
	}
	
	function genModelCode(){
	   var xform = document.editForm; 
	   xform.methodCall.value="genPageModelCode"; 
	   xform.submit();
	   
	}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ҳ��ģ�͹���</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
     <a href="${pageContext.request.contextPath}/pageModel/pageModelManage.do?methodCall=list&pageNo=1">ҳ��ģ���б�</a>
    </TD>
 </TR>
</TBODY>
</TABLE>
<BR>

ϵͳ��̨���� -&gt; ҳ��ģ����Ϣ
<table width="98%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/pageModel/pageModelManage.do" method=post>
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">�ؼ���</td>
    <td width="36%" bgcolor="#EFF3FF">
    
    <input name="methodCall"  id="methodCall"   type="hidden" value="save" >
    
    <input name="name"  id="name"   type="text" value="${detail.name}" style="width:200px;">
    
    <input name="id"  id="id"   type="hidden" value="${detail.id}" ></td>
    <td width="11%" bgcolor="#EFF3FF">ҳ�����</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
      <input name="title"  id="title"   type="text" value="${detail.title}" style="width:200px;">
   </td>
  </tr>
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">�ļ�·��</td>
    <td width="36%" bgcolor="#EFF3FF">
    <input name="pagePath"  id="pagePath"  type="text" value="${detail.pagePath}" class="textfield" style="width:330px;">
    </td>
    <td width="11%" bgcolor="#EFF3FF" >ҳ����ʾ����</td>
    <td  bgcolor="#EFF3FF" colspan="3">
     <select name="settingType" id="settingType" class="textfield" style="width:200px;">
      <option value=""></option>
      <c:forEach var="item" items="${requestScope.settingTypes}" varStatus="status">
      <option value="${item.id}" ${detail.settingType eq item.id?' selected':''}>${item.name}</option>
     </c:forEach>
    </select>
   </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">����ģ��</td>
    <td bgcolor="#EFF3FF">
	    <select name="module" id="module" class="textfield"  style="width:200px;" onchange="findSystemMainTableByModule();">
	      <option value=""></option>
	      <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
	      <option value="${item.id}" ${detail.module.id eq item.id?' selected':''}>${item.name}</option>
	      </c:forEach>
	    </select>
	</td>
	<td width="10%" bgcolor="#EFF3FF">�����</td>
      <td width="40%" bgcolor="#EFF3FF">  
      <select name="mainPagePanel" id="mainPagePanel" style="width:220px;" class="textfield">
          <option value=""></option>
           <c:forEach var="item" items="${requestScope.pagePanels}" varStatus="status">
           <option value="${item.id}" ${detail.mainPagePanel.id eq item.id?' selected':''}>${item.name}</option>
         </c:forEach> 
      </select>
      </td>   
    </tr> 
    <tr>
    <td align="center" bgcolor="#EFF3FF">Ĭ���������</td>
    <td bgcolor="#EFF3FF">
	    <select name="pagePanelType" id="pagePanelType" class="textfield"  style="width:200px;" >
	      <option value=""></option>
	      <c:forEach var="item" items="${requestScope.pagePanelTypes}" varStatus="status">
	      <option value="${item.id}" ${detail.pagePanelType.id eq item.id?' selected':''}>${item.cnName}</option>
	      </c:forEach>
	    </select></td>
	     
	<td width="10%" bgcolor="#EFF3FF">
		<input class=button type="submit" name="Submit" value="����ҳ��ģ��" >
	</td>
      <td width="40%" bgcolor="#EFF3FF">&nbsp;
      <input class=button type="button" name="Submit" onclick="genModelCode();" value="����ҳ�����" >
      </td>   
    </tr>
  </form>
</table>  
<%@ include file="pageModelTemplate/pageModel_detail.jsp"%> 


<p>&nbsp;</p>
</body>
</html>
