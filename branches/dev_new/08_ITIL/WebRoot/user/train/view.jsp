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
    
    <title>My JSP 'view.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body bgcolor="#808080">
  <table>
  <tr>
  <td width="300" align="center" style="background-color: #808080;" valign="top">
    </td>
	<td width="659" valign="top" style="background-color: #FFFFFF;">
    <h3 align='center'>用户培训调查问卷</h3>
    <table align="left" width="660" border="0">
    <%   int i=1; %>
    <c:forEach var="quest" items="${questOptions}" >
    <c:if test="${quest.key.questType.id=='1'}">
	    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		<c:forEach var="quest1" items="${quest.value}" >
		    <tr> &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.value}"> ${quest1.answerNo}.${quest1.content}</tr>
    	</c:forEach><br>	 
    	<%  i++; %>
    	</c:if>
    </c:forEach>	
    <c:forEach var="quest" items="${questOptions}" >
    <c:if test="${quest.key.questType.id=='2'}">
	    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		<c:forEach var="quest1" items="${quest.value}" >
		    <tr> &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.value}"> ${quest1.answerNo}.${quest1.content}</tr>
    	</c:forEach><br>	 
    	<%  i++; %>
    	</c:if>
    </c:forEach>	 
    <c:forEach var="quest" items="${questOptions}" >
    <c:if test="${quest.key.questType.id=='3'}">
	    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}<input type="text" style="border:none;width:150px;border-bottom:1px solid #000" name="" value=""></tr>   
	    <br>	 
    	<%  i++; %>
    	</c:if>
    </c:forEach>	 
    <c:forEach var="quest" items="${questOptions}" >
    <c:if test="${quest.key.questType.id=='4'}">
	    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr><br>
		 <tr>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" style="border:none;width:550px;border-bottom:1px solid #000" name="" value=""></tr>
		 
    	<%  i++; %>
    	</c:if>
    </c:forEach>
    
     <input type="submit" name="Submit" value="返回" onclick="window.history.back()" >	
    </table>
    

     
     </td>
	 <td width="250" align="center" style="background-color: #808080">
    </td>
    </tr>
    </table>
    
  </body>
</html>
