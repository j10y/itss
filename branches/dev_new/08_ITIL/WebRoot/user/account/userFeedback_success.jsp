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
    
    <title>��ӭʹ��IT����ϵͳ</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="my.css">

  </head>
  
  <body>
  <tr>
  <td width="300" align="center" style="background-color: #808080;" valign="top" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
    </td>
	<td width="659" valign="top" style="background-color: #FFFFFF;">
	<br>
    	<br><br>
    	<br><br>
    	<br><br>
    	<br><br>
    	<br>
    <h3 align='center'>��Ʒ��˾�����ʺ��û�ȷ��</h3>
    <br>
    	<br>
    	<br>
    	<center>
    		<font color="red" size="4">���������ʺ���ȷ�ϳɹ���лл��</font>
    	</center>
   	</td>
	<td width="250" align="center" style="background-color: #808080;">
    </td>
    </tr>
  </body>
</html>

