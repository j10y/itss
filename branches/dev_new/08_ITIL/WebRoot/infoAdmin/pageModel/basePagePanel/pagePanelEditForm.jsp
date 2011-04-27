<%@ page pageEncoding="gbk" contentType="text/html;charset=gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>页面面板与字段设置</title>
<title>系统主表字段设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">
   var ppId = '${pp.id}';
   var ppTitle='${pp.title}';
</script>
<script type="text/javascript">
function addColumnsToPanel(){
	var xform = document.formDel;
	xform.methodCall.value="addForeignTable";
	xform.submit();
	window.location.href=server2Path;
}

function initModuleTable(cobj, fselect){
  var j=0;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].tableCnName,cobj[i].id); 
	j = j +1;
  }
}

function findTableByModule(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var module = Ext.getDom("module");
   var moduleSelectedId = module.options[module.selectedIndex].value;
   if(moduleSelectedId!=""){
       var panelSystemMainTable = Ext.getDom("panelSystemMainTable");

	   Ext.Ajax.request({
	      url: serverPath+"/pageModel/pagePanelManage.do?methodCall=findTableByModule", 
	      params:{
	          module: moduleSelectedId
	      },
	      method:'POST',
	      success:function(response){
	        var data = response.responseText;
	        var result = Ext.decode(data);
	        
	        var obj = result.data;
	    
	        initModuleTable(obj, panelSystemMainTable);
	 		
	  		
	      }//end func
	  });
   }//endif
   
}


</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>页面面板管理</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
     <a href="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=list&pageNo=1">页面面板列表</a>
    </TD>
 </TR>
</TBODY>
</TABLE>
<BR>
 
系统后台管理 -&gt; 页面面板
<table width="98%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/pageModel/pagePanelManage.do?methodCall=save" method=post>
 
 <tr >
    <td width="12%" align="center" bgcolor="#EFF3FF">面板关键字</td>
    <td width="36%" bgcolor="#EFF3FF">
    <input name="name"  id="name" class="textfield" style="width:200px;" 
    		type="text" value="${empty detail.name? (pp.name):(detail.name)}">
    <input name="id"  id="id"   type="hidden" value="${pp.id}">
    </td>
    <td width="11%" align="center" bgcolor="#EFF3FF">面板标题</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
   <input name="title"  id="title" class="textfield" style="width:200px;" type="text" value="${empty detail.title? (pp.title):(detail.title)}">
   </td>
  </tr>
  
  <tr> 
    <td width="12%" align="center" bgcolor="#EFF3FF">页面表现形式</td>
    <td width="36%" bgcolor="#EFF3FF">
     <select name="settingType" id="settingType" class="textfield" style="width:100px;">
      <option value="1"></option>
      <c:forEach var="item" items="${requestScope.settingTypes}" varStatus="status">
          <option value="${item.id}" ${pp.settingType eq item.id?' selected':''}>${item.name}</option>
     </c:forEach>
    </select>  
    </td>
    <td width="11%" align="center" bgcolor="#EFF3FF">EXT组件类型</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
    <select name="xtype" id="xtype" class="textfield" style="width:100px;">
      <option value="1"></option>
      <c:forEach var="item" items="${requestScope.xtypes}" varStatus="status">
      <c:if test="${not empty item.cnName}">
          <option value="${item.id}" ${pp.xtype.id eq item.id?' selected':''}>${item.cnName}</option>
      </c:if>
     </c:forEach>
    </select>  
   </td>
  </tr>
    
  <tr>
    <td width="11%" align="center" bgcolor="#EFF3FF">所属的模块</td>
    <td width="36%" bgcolor="#EFF3FF">
	    <select name="module" id="module" class="textfield" onchange="findTableByModule();">
	      <option value=""></option>
	      <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
	      <option value="${item.id}" ${pp.module.id eq item.id?' selected':''}>${item.name}</option>
	      </c:forEach>
	    </select>
    	
   </td>
    <td width="11%" align="center" bgcolor="#EFF3FF">面板涉及的主表</td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
       <select name="panelSystemMainTable" id="panelSystemMainTable" class="textfield" >
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.foreignTables}" varStatus="status">
           <option value="${item.id}" ${pp.systemMainTable.id eq item.id?' selected':''}>${item.tableCnName}(${item.id})</option>
         </c:forEach>
      </select>
      
       <input name="groupFlag"  id="groupFlag"   type="hidden" value="${empty pp.id ? 0 : pp.groupFlag}">
       
   </td>
  </tr>
  
    
  
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="6" bgcolor="#EFF3FF">
   <input class=button type="submit" name="Submit" value="保存PagePanel" >
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>  
<br>
<c:if test="${not empty requestScope.pp}">
面板主表
<table width="98%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
<FORM id="formDel" name="formDel" action="${pageContext.request.contextPath}/pageModel/pagePanelManage.do" method="post">
  <input type="hidden" name="methodCall" value="removePagePanelTable">
  <input type="hidden" name="ppId" value="${pp.id}">
 
  <TR>
    <TD width="20px;"  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="250px;" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>主表名称</STRONG></FONT>
    </TD>
   
    <TD  width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
   </TD>
  </TR>
  
 <c:forEach  var="pptItem" items="${requestScope.pagePanelTables}" varStatus="status">   
 
  <TR height="22px;"  style="CURSOR: hand" bgColor=#ebf2f9>
    <TD noWrap>${pptItem.id}</TD>
    <TD noWrap>
     <a target="blank" href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toForm&id=${pptItem.systemMainTable.id}">
     ${pptItem.systemMainTable.tableCnName}(${pptItem.systemMainTable.id})
    </a>
   
    
    </TD>
    <TD width=48 align="center">
      <INPUT name="id" type="checkbox" value="${pptItem.id}" style="WIDTH: 13px; HEIGHT: 13px">
    </TD>
  </TR>
  
 </c:forEach> 
 
 <TR height="24px;" >
    <TD noWrap bgColor=#ebf2f9 colSpan=2>
   <STRONG>&nbsp;向面板中添加系统主表:</STRONG>
    <select name="systemMainTable" id="systemMainTable" class="textfield" onchange="addColumnsToPanel();">
      <option value="1"></option>
      <c:forEach var="item" items="${requestScope.systemMainTables}" varStatus="status">
          <option value="${item.id}">${item.tableCnName}(${item.id})</option>
     </c:forEach>
    </select>  
    </TD>
   
   <TD noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="ConfirmDel('删除主表将级联删除其所有字段和用户定制信息，您确认删除吗？');" 
    		type=button value="删除所选" name="submitDelSelect">
    </TD>
  </TR>
  
  </FORM>

</TABLE>
</c:if>
<br>
<c:if test="${not empty requestScope.pp}">
面板主表关联关系
<table width="98%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
<FORM name="formDelTabRel" action="${pageContext.request.contextPath}/pageModel/pagePanelManage.do" method="post">
  <input type="hidden" name="methodCall" value="removePagePanelTable">
  <input type="hidden" name="ppId" value="${pp.id}">
 
  <TR>
    <TD width="20px;"  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="250px;" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>主表名称</STRONG></FONT>
    </TD>
    
      <TD width="250px;"  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>外键</STRONG></FONT>
    </TD>
    
     <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>外键表</STRONG></FONT>
    </TD>
   
    <TD  width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
   </TD>
  </TR>
  
 <c:forEach  var="pptItem" items="${requestScope.pagePanelTableRelations}" varStatus="status">   
 
  <TR height="22px;"  style="CURSOR: hand" bgColor=#ebf2f9>
    <TD noWrap>${pptItem.id}</TD>
    <TD noWrap>${pptItem.systemMainTable.tableCnName}(${pptItem.systemMainTable.id})</TD>
     <TD noWrap>${pptItem.foreignTableColumn.columnCnName}(${pptItem.foreignTableColumn.id})</TD>
     
    <TD noWrap>
     <select name="foreignTable" id="foreignTable" class="textfield" onchange="findColumnsByTable();" style="width:230px;">
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.foreignTables}" varStatus="status">
           <option value="${item.id}" ${pptItem.foreignTable.id eq item.id?' selected':''}>${item.tableCnName}(${item.id})</option>
         </c:forEach>
      </select>
    </TD>
   
    <TD width=48 align="center">
      <INPUT name="id" type="checkbox" value="${pptItem.id}" style="WIDTH: 13px; HEIGHT: 13px">
    </TD>
      
  </TR>
  
 </c:forEach> 

  
  </FORM>

</TABLE>
</c:if>
<jsp:include page="panel-main.jsp">
	<jsp:param name="pagePanelId" value='${param.id}'></jsp:param>
</jsp:include>
<p>&nbsp;</p>
</body>
</html>
