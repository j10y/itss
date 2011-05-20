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
<title>服务项流程设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
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
function saveProcess(){
   var xform = document.getElementById("editForm");
   xform.methodCall.value="save";
   xform.submit();
  }
</script>
</head>
<body>
用户后台管理 -&gt; 服务项流程
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/userMainTable.do" 
 	   method=post>
  <tr>
    <td width="12%" align="center" bgcolor="#EFF3FF">服务项名称</td>
    <td width="38%" bgcolor="#EFF3FF">${serviceItem.name}
    </td>
    </tr>
    <tr>
    <td  width="12%" align="center" bgcolor="#EFF3FF">流程名称</td>
    <td width="38%" bgcolor="#EFF3FF">
     <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:250px;" value="${process.definitionName}">
     <input name="id" id="id" type="hidden" class="textfield" value="${process.id}">    
    <br>
    </td>
  </tr>

  <tr>  
    <td width="10%" align="center" bgcolor="#EFF3FF">按钮名称</td>
    <td width="40%" bgcolor="#EFF3FF">
     <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:250px;" value="${process.definitionName}">
    </td>
  </tr>
 
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td bgcolor="#EFF3FF">
   		<input class=button type="button"  onclick="saveProcess();" value="保存">&nbsp;
    </td>
  </tr>
  </form>
</table>
<BR>
<br>
</body>
</html>
