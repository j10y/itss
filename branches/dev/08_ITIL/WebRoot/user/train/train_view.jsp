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
	
	<link rel="stylesheet" type="text/css" href="my.css">
<script>
	 var dataId = '${param.dataId}';
	function query(pageNo){
	var pn=pageNo;
		var xform = document.form2; 
		xform.action="${pageContext.request.contextPath}/trainPlan_findQuest2.action";
		xform.pageNo.value=pn;
		xform.submit();
	 }
	function result(pageNo){
	var pn=pageNo;
			var xform = document.form2;
			xform.action="${pageContext.request.contextPath}/trainPlan_saveQuestResult.action";
			xform.pageNo.value=pn;
			xform.submit();
	 }
	 
	 	  function   choosedWhat()   {  
 
					  window.location = "${pageContext.request.contextPath}/trainPlan_findUserCourse.action";

		  }  
</script>
  </head>
  
  <body bgcolor="#808080" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
  <table>
  <tr>
  <td width="300" align="center" style="background-color: #808080;" valign="top" background="${pageContext.request.contextPath}/user/js/images/globe2.gif">
    </td>
	<td width="659" valign="top" style="background-color: #FFFFFF;">
    <h3 align='center'>用户培训调查问卷</h3>
    <form   name="form2" action=""  method=post >
    <INPUT name="radio" type="hidden" value="${dataId}">
    <INPUT name="pageNo" type="hidden" value="">
    <c:set var="listresult" value="listresult${pageNo}" ></c:set>
    <table align="left" width="660" border="0" cellspacing="11">
    <%   int i=0; 
    	 String num=(String)request.getAttribute("num");
		 i=(Integer)Integer.parseInt(num);
	%>
    <c:forEach var="quest" items="${questOptions}" >
    		<c:if test="${quest.key.questType.id=='1'}">
    	
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		    <input type="hidden" name="quest_id" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}" >
				    <tr> &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="${quest.key.id}_radio" value="${quest1.id}"> ${quest1.answerNo}.${quest1.content}</tr>
		    	</c:forEach><br>	 
	    	<%  i++; %>
	    	</c:if>
    	 <c:if test="${quest.key.questType.id=='2'}">
		    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr>
		    
		     <input type="hidden" name="quest_id2" value="${quest.key.id}">   
				<c:forEach var="quest1" items="${quest.value}"  varStatus="status">
				    &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="${quest.key.id}_checkbox" value="${quest1.id}" > ${quest1.answerNo}.${quest1.content}
		    	<c:if test="${status.count%3==0}"><br></c:if>
		    	</c:forEach>
	    		 <br><br>
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
			    <tr>&nbsp;&nbsp;<%=i%>. ${quest.key.questName}</tr><br>
				 <tr>&nbsp;&nbsp;&nbsp;&nbsp;
				 <textarea cols="60" rows="5" id="${quest.key.id}_text" name="${quest.key.id}_text">
				</textarea>
		    	<%  i++; %>
    	</c:if>
    </c:forEach>
 	 <br><br>&nbsp;&nbsp;&nbsp;&nbsp;
 	 <c:if test="${pageNo >1}">
 	 <input type="button" name="Submit" value="上一步" onclick="window.history.back()" >	
 	 </c:if>
 	 <c:if test="${pageNo < page.totalPageCount}">
	<input type="button" name="Submit" value="下一步" onclick="query(${pageNo+1})" >	
	</c:if>
	 <c:if test="${pageNo eq page.totalPageCount}">
	<input type="button" name="Submit" value="完成" onclick="result(${pageNo})">	
	</c:if>
    <input type="button" name="Submit2" value="取消" onclick="choosedWhat()""">	  
    </table>
   	 </form>
   	 </td>
	 <td width="250" align="center" style="background-color: #808080;">
    </td>
    </tr>
    </table>
    
  </body>
</html>
