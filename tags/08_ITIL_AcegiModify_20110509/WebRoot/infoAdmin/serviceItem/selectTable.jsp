<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<META content="" name=Description>
<title>用户主表属性与字段设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}

function saveExistentTable(serviceItemId){
    var serverPath = "${pageContext.request.contextPath}";
    var tableId = Ext.getDom("tableId").value;
    var tableName = Ext.getDom("tableName").value;
    var sourceServiceItem = Ext.getDom("serviceItem");
    var sourceServiceItemId = sourceServiceItem.options[sourceServiceItem.selectedIndex].value;
    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/serviceItemUserTableAction.do?methodCall=saveExistentTable", 
       params:{
           tableId: tableId,
           serviceItemId: serviceItemId,
           sourceServiceItem:sourceServiceItemId
       },
       method:'POST',
       success:function(response){
         alert("保存成功!");
         document.location.href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do?methodCall=addTable&serviceItemId="+serviceItemId;
       }
  });
}

function initSelect(cobj, fselect){
  var j=1;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].name,cobj[i].id); 
	j = j +1;
  }
}

function findServiceItemBySCIT(){
	var serverPath = "${pageContext.request.contextPath}";
	var serviceItemType=document.getElementById("serviceItemType").value;//Ext.getDom("serviceItemType");
	var serviceItem=Ext.getDom("serviceItem");
	Ext.Ajax.request({
      url: serverPath+"/infoAdmin/serviceItemUserTableAction.do?methodCall=findServiceItemBySCIT", 
      params:{
          serviceItemType: serviceItemType
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        var obj = result.data;
        initSelect(obj, serviceItem);
      }//end func
  });
}

function findTableBySCID(){
	var serverPath = "${pageContext.request.contextPath}";
	var serviceItem=document.getElementById("serviceItem").value;//Ext.getDom("serviceItemType");
	var tableId=Ext.getDom("tableId");
	var tableName=Ext.getDom("tableName");
	var tableCnName=Ext.getDom("tableCnName");
	Ext.Ajax.request({
      url: serverPath+"/infoAdmin/serviceItemUserTableAction.do?methodCall=findTableBySCID", 
      params:{
          serviceItem: serviceItem
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        tableId.value=result.tableId; 
        tableName.value=result.tableName;
        tableCnName.value=result.tableCnName;
      }//end func
  });
}
</script>
</head>
<body>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do" method=post>
 	   
  

  <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">服务项类型</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="serviceItemType" id="serviceItemType"  class="textfield" onchange="findServiceItemBySCIT()">
        <option value="">无</option>
        <c:forEach var="item" items="${requestScope.serviceItemTypes}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${siut.serviceItem.serviceItemType.id eq item.id? ' selected':'' }>${item.name}</option>
         </c:if>
       </c:forEach>
      </select>
      
    </td>
    <td width="10%" bgcolor="#EFF3FF">所属服务项</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="serviceItem" id="serviceItem" style="width:300px;" class="textfield" onchange="findTableBySCID()">
     	<option value="">无</option>
       <c:forEach var="item" items="${requestScope.serviceItems}" varStatus="status">
         <option value="${item.id}" ${siut.serviceItem.id eq item.id? ' selected':'' }>${item.name}</option>
        </c:forEach>
      </select>
 
    </td>
  </tr>
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">用户主表英文名</td>
    <td width="38%" bgcolor="#EFF3FF">
   	<input name="tableId" id="tableId"  type="hidden" value="">
      <input name="tableName" id="tableName"  type="text" class="textfield" style="WIDTH:250px;" value="">
    
    <br>
    </td>
    <td  width="12%" bgcolor="#EFF3FF">用户主表中文名</td>
    <td width="38%" bgcolor="#EFF3FF">
     <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:250px;" value="${siut.systemMainTable.tableCnName}">
   
    <br>
    </td>
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td bgcolor="#EFF3FF">
   		<input class=button type="button"  onclick="saveExistentTable(${targetServiceItem.id});" value="保存">&nbsp;
    </td>
     <td  colspan="2" bgcolor="#EFF3FF">
   		<div style="float:left" id="file"></div>
    	
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>
<br>
</body>
</html>
