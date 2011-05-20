<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>欢迎使用IT服务系统</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="my.css">
	<script>
		var hiddeBtn = "${hiddeBtn}";
		function result(){
				var xform = document.form2;
				xform.action="${pageContext.request.contextPath}/SRAction_saveQuestResult.action";
				xform.submit();
		}
		function reset(){
			document.form2.reset();
		}
	</script>
  </head>
  
  <body bgcolor="#808080" onload="javascript : history.back()" >
  </body>
</html>