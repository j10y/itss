<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>�û������������ֶ�����</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">
	var success="${success}";
	if(success){
		alert("����ɹ���");
	}

	function importFile(){ 
	   var xform = document.getElementById("fileForm");
	  xform.submit();
	}
	function importConfigGUL(){
		 var xform = document.getElementById("gulForm");
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
      border=0>&nbsp;<STRONG>�������ϵ���ݵ���</STRONG></FONT></TD></TR>
 
</TBODY>
</TABLE>
<BR>



<FORM id="fileForm" action="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=importCIRDataExcel" method="post"
  		enctype="multipart/form-data"  >
    <input name="systemMainTableIds"  id="systemMainTableIds"   type="hidden" value="${smt.id}">
    <input name="fromFile" type="file"  id="fromFile" size="41">
    <input class=button type="button" onclick="importFile();" value="�ϴ�EXCEL����">
</FORM>
<!--
<br>

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>�������ϵͼ�͹��������ݵ���</STRONG></FONT></TD></TR>
 
</TBODY>
</TABLE>
<BR>


<FORM id="gulForm" action="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=importCIRGUIDataExcel" method="post"
  		enctype="multipart/form-data"  >
    <input name="systemMainTableIds"  id="systemMainTableIds"   type="hidden" value="${smt.id}">
    2.
    <input class=button type="button" onclick="importConfigGUL();" value="�����������ϵͼ�͹���������"> ${json} 
</FORM>
<br>
  -->
</body>
</html>
