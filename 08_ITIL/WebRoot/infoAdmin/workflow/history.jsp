<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>
    <title>������ʷ״̬</title>
    <%@include file="/includefiles.jsp"%>
  </head>
  
  <body>
  	<br/><br/><br/>
    <table align="center" width="400" border="0"><tr><td>
    
    <c:forEach var="hinfo" items="${history}">
    ${hinfo.definitionName}|${hinfo.processId}|${hinfo.nodeName}|${hinfo.actorId}|${hinfo.taskName}<br/>
    ${hinfo.comments.values['result']} ${hinfo.comments.values['comment']}<br/><br/>
    </c:forEach> 
    <br/>
    <c:if test="${not empty taskId}">
        Task Id��${taskId}
        <a href="${pageContext.request.contextPath}/workflow/history.do?id=${taskId }&methodCall=view">�鿴����ͼ</a>
    </c:if>
    <br> <br>
    <a href="javascript:history.go(-1)">�������</a>
    </td></tr></table>
  </body>
</html>
