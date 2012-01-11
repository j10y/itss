<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>欢迎使用统一用户管理系统</title>
		<%@include file="../../includefiles.jsp"%>
		<script type="text/javascript">
			__ctxPath = "${pageContext.request.contextPath}";
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/TreeSelector.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/systemManage/SystemView.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/systemManage/SystemForm.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/systemManage/SystemRelateRoleForm.js"></script>
		<script type="text/javascript">
			Ext.onReady(function() {
				var system = new SystemView();
			});
		</script>
	</head>
	<body>
		<div id="mainDiv"></div>
	</body>
</html>
