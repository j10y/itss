<%@ page pageEncoding="gbk" %>
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
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/pageModel/pageGroupPanelManage.do?methodCall=save" method=post>
 
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
    <td width="11%" align="center" bgcolor="#EFF3FF"></td>
    <td width="41%" colspan="3"  bgcolor="#EFF3FF">
      
   </td>
  </tr>
  
   	<input type="hidden" name="groupFlag" value="1">
  
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="6" bgcolor="#EFF3FF">
   <input class=button type="submit" name="Submit" value="保存分组面板" >
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>  
<br>


  
  </FORM>

</TABLE>

<%@ include file="groupPanel-main.jsp"%> 

<p>&nbsp;</p>
</body>
</html>
