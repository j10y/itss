<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head> 
    <title>工作流演示</title>
    <%@include file="/includefiles.jsp"%>
  </head>
  
  <body>
    <br/><br/><br/>
    <table align="center" width="300" border="0"><tr><td>
     workflow functions demo: <br/><br/>
    <li><a href="${pageContext.request.contextPath}/workflow/deploy.do?methodCall=list">deploy a definition.</a>
    </li>
    <br/><br/>
    <li><a href="${pageContext.request.contextPath}/workflow/listdefinition.do?methodCall=list">select a definition.</a>
    </li>
    <br/><br/>
    <li><a href="${pageContext.request.contextPath}/workflow/listprocess.do?methodCall=list">select a process.</a>
    </li>
    <br/><br/>
    <li><a href="${pageContext.request.contextPath}/workflow/listtask.do?methodCall=listAll">select a task.</a>
    </li>
    <br/><br/>
    <li><a href="${pageContext.request.contextPath}/workflow/listtask.do?methodCall=logon">execute a task as an actor.</a>
    </li>
    </td></tr></table>
  </body>
</html>
