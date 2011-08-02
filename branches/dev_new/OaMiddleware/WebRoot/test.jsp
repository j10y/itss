 <%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s"  uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<c:set var="webpath" value="${pageContext.request.contextPath}"></c:set>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'upload.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  
   <form action="${webpath}/oa/downFileNetOaAction.action" method="post" >
      	姓名：<input type="text" name="name">
      	<input type="submit" value="提交">
    </form>
  
  
  
  
  
  
  
    <form action="${webpath}/ssh2/testForexAction.action" method="post" >
      	姓名：<input type="text" name="name">
      	<input type="submit" value="提交">
    </form>
  	<form action="${webpath}/ssh2/testForexAction!delete.action" method="post" >
      	编号：<input type="text" name="id">
      	<input type="submit" value="删除">
    </form>
    	<form action="${webpath}/ssh2/testForexAction!modify.action" method="post" >
      	编号：<input type="text" name="id"> 姓名修改为：<input type="text" name="name"> 
      	<input type="submit" value="修改">
    </form>
  </body>
</html>
