<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/jbpm.tld" prefix="jbpm" %>
<html>
	<head>
		<title>流程历史状态</title>
		<%@include file="/includefiles.jsp"%>
		<link href="${pageContext.request.contextPath}/css/olstyle.css" rel="stylesheet" type="text/css">
	</head>
	
	<body class="BODY">	
		<br><br>	
		<table width="300" align="center"><tr><td align="center">
		流程执行示意图
		<br>		
		<br>
		<jbpm:processimage task="${id}"/> 
		<a href="${pageContext.request.contextPath}/test/jbpm/index.jsp"></a> 
		</td></tr>
		</table>
		<br>
		<table width="300" align="center">
		<tr>
			<td align="center">			
		<a href="javascript:history.go(-1)">点击返回</a>
			</td>
		</tr>
		</table>
		

	</body>
</html>
 


         