<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ModLoader.js"></script>   
<script>
	//window.location = webContext+"/trainPlan_findUserCourse.action";
</script>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script>  
		  function   choosedWhat(radio)   {  
		          for   (var   i=0;i<radio.length;i++)   {  
					  if   (radio[i].checked)   {  
					  window.location = webContext+"/trainPlan_findQuest2.action?dataId="+radio[i].value;
					 
		  }  
		  }  
		  }  
		  
		  function nochoose() {
		  	for   (var   i=0;i<radio.length;i++)   {  
					  if   (radio[i].checked=='null'||radio[i].checked=="")   {  
					alert("aaaaaaaaaaaaaaaaaa");
					 
		  }  
		  }  
		  
		  }
  </script> 
</head>
<body bgcolor="#808080">
<br>
<center>
	<h3 align='center'>ÇëÑ¡ÔñÒª·´À¡µÄ¿Î³Ì</h3>
	<br>
	<table align="center"  border="5" bgcolor="#F3F3F2">
	<tr >
	<td >
	<table align="center" width="300" height="auto" border="0" bgcolor="#F3F3F2">
	<p>ÒÔÏÂÎªÄã²Î¼ÓÅàÑµµÄ¿Î³Ì(Ö»ÓÐÅàÑµ¹ýµÄ¿Î³Ì²ÅÄÜ²Î¼Ó·´À¡)</p>
	<form   name="form1" action="${pageContext.request.contextPath}/trainPlan_findQuest2.action"  method=post >
				 <c:forEach var="course2" items="${course}" >
				 	<tr> <td height="30"><input type="radio" name="radio" checked="true" value="${course2.id}"> ${course2.courseName}</td></tr> 
				 </c:forEach>
				 <tr> <td></td></tr>
				   <tr>
				   <td height="20">
				   <center>
				   <input type="submit" name="Submit" value="È·¶¨">	
				    <input type="button" name="Submit2" value="·µ»Ø" onclick="window.history.back(-1)" >	 
				    </center>
				   </td>
				   </tr>
				   <tr> <td height="20"></td></tr> 
	</form>
	</table>
	</td>
	</tr>
	
	</table>
	</center>
</body>
</html>