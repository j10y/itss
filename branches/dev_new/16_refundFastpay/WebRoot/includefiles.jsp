<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String username = principal.getName();
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0"> 
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.6.1.min.js"/>
		<script type="text/javascript">
			function getCurrentUser(){
				return '${currentUser}';
			}
		</script>
	</head>
	<body> 
	</body>
</html>