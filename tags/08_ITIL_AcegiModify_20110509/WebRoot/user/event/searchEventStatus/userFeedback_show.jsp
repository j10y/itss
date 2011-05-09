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
  </head>
  <body bgcolor="#808080" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
  <table border="0px" style="line-height:30px;">
  <tr>
  <td width="400" align="center"  valign="top" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
    </td>
	<td width="650" valign="top" >
    <h3 align='center'>${survey.surveyName }</h3>
    <br>
    <form   name="form2" action=""  method=post style="font-size:12px">
    <input type="hidden" name="objId" value="${eventId}">
    <input type="hidden" name="surveyId" value="${surveyId}">
    <input type="hidden" name="taskId" value="${taskId}">
  	<table align="center" border="0px" width="480">    
  	<%  
  		int i = 1; 
	%>
    <c:forEach var="quest" items="${questOptions}" >
     <c:forEach var="result" items="${results}" >
     <c:if test="${quest.key.questName==result.quest.questName}">
     <c:if test="${quest.key.questType.id=='1'}">
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		    <input type="hidden" name="quest_id" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}" >
				<c:choose>
				<c:when test="${result.questOption.content==quest1.content}">
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.key.id}_radio" value="${quest1.id}" checked> ${quest1.answerNo}.${quest1.content}
				</c:when>
				<c:otherwise>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.key.id}_radio" value="${quest1.id}"> ${quest1.answerNo}.${quest1.content}
				</c:otherwise>
				    </c:choose>
		    	</c:forEach>	 
	    	<%  i++; %>
	    	</c:if>

    	 <c:if test="${quest.key.questType.id=='2'}">
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		     <input type="hidden" name="quest_id2" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}"  varStatus="status">
				    <!-- &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest1.id}" > ${quest1.answerNo}.${quest1.content} -->
		    	
		    	<c:choose>
				<c:when test="${result.questOption.content==quest1.content}">
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest1.id}" checked> ${quest1.answerNo}.${quest1.content}
				</c:when>
				<c:otherwise>
					&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest1.id}"> ${quest1.answerNo}.${quest1.content}
				</c:otherwise>
				</c:choose>
		    	
		    	<c:if test="${status.count%3==0}"><br></c:if>
		    	</c:forEach>
	    		 <br>
	    	<%  i++; %>
    	</c:if>
    	<c:if test="${quest.key.questType.id=='3'}">
		     <input type="hidden" name="quest_id3" value="${quest.key.id}">  
		     <c:set var="id" value="${quest.key.id}"></c:set> 
			    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}<input type="text" style="border:none;width:150px;border-bottom:1px solid #000" name="${quest.key.id}_text" value="${sessionScope.listresult['id'].value}"></tr>   
			    <br>	 
		    	<%  i++; %>
		    	</c:if>
    	<c:if test="${quest.key.questType.id=='4'}">
		    <input type="hidden" name="quest_id4" value="${quest.key.id}">   
			    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
				<c:choose>
				<c:when test="${result.quest.id==quest.key.id}">
				    &nbsp;&nbsp;&nbsp;&nbsp;<textarea cols="60" rows="5" id="${quest.key.id}_textArea" name="${quest.key.id}_textArea">${result.answer}</textarea>
				</c:when>
				<c:otherwise>
					&nbsp;&nbsp;&nbsp;&nbsp;<textarea cols="60" rows="5" id="${quest.key.id}_textArea" name="${quest.key.id}_textArea"></textarea>
				</c:otherwise>
				</c:choose>
				 <!-- <tr>&nbsp;&nbsp;&nbsp;&nbsp;
				 <textarea cols="60" rows="5" id="${quest.key.id}_text" name="${quest.key.id}_text"></textarea> -->
		    	<%  i++; %>
    	</c:if>
    	</c:if>
    	</c:forEach>
    </c:forEach>
 	<br>
    </table>
   
   	</form>
   	</td>
	<td width="250" align="center">
    </td>
    </tr>
    </table>
  </body>
</html>
