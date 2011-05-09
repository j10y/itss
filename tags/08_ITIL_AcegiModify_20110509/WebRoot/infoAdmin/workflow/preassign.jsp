<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>
	<title>‘§÷∏≈…</title>
	<%@include file="/includefiles.jsp"%>
  </head>
  
  <body>
  <br/><br/><br/>
    <table align="center" width="300" border="0"><tr><td>
    	input actorid for the task:
    	<br/><br/>
	  	<form action="${pageContext.request.contextPath}/workflow/start.do">
	  		<input type="hidden" name="methodCall" value="start">
		  	<c:forEach var="tn" items="${taskNodes}">
		  	${tn.nodeName}:<input name="${tn.name}"><br/><br/>
		  	</c:forEach>
			<input type="hidden" name="pdn" value="${pdn }">
			<input type="submit">
		</form>
		<a href="${pageContext.request.contextPath}/test/jbpm/index.jsp">back</a>
	</td></tr></table>
  </body>
</html>
