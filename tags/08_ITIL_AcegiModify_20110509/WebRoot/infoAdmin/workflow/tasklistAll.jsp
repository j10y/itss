<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>
    <title>任务列表</title>
    <%@include file="/includefiles.jsp"%>
  </head>
  
  <body>
 	<br/><br/><br/>
 	
    <table align="center" width="700" border="0">
    <tr><td>
    select a task.<br/><br/>
    </td></tr>
    <tr><td>
    <c:forEach var="task" items="${tasks}">
    definitionName:${task.definitionName }|processId:${task.processId }|taskId:${task.id }|taskName:${task.name }|actor:${task.actorId }| 
    <!-- <a href="javascript:cancel(${task.id});">cancel</a>&nbsp; -->
    <a href="javascript: excute(${task.id})">excute</a>&nbsp;
    <a href="javascript: history(${task.id})">history</a>
    <br><br>
    </c:forEach>
    <c:if test="${length==0}">
    NO task is open.<br/><br/>
    </c:if>
    <a href="${pageContext.request.contextPath}/test/jbpm/index.jsp">back</a>
    </td></tr></table>
  </body>
  
  <Script language="javaScript">
  
  function cancel(id){
    var url = "${pageContext.request.contextPath}/workflow/listtask.do?id="+id;
    url += "&methodCall=cancel";
  	location = url;
  }
  
  function excute(id){
    var url = "${pageContext.request.contextPath}/wftask?id="+id+"&system";
    window.open(url); 
  	
  }
  
  function history(id){
    var url = "${pageContext.request.contextPath}/workflow/history.do?taskid="+id;
    url += "&methodCall=list";
  	location = url;
  }
  </Script>
</html>
