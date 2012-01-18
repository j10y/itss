<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
		<title>欢迎使用统一用户管理系统</title>
		<%@include file="../../includefiles.jsp"%>
		<%
			net.shopin.ldap.dao.UserDao userDao = (net.shopin.ldap.dao.UserDao)net.shopin.util.SpringContextUtils.getBean("userDao");
			net.shopin.ldap.entity.User user = userDao.findByPrimaryKey(username);
			request.setAttribute("user", user);
		 %>
		<script type="text/javascript">
			__ctxPath = "${pageContext.request.contextPath}";
		</script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/TreeSelector.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/user/userManage/UserModifyForm.js"></script>
		<script type="text/javascript">
			Ext.onReady(function() {
				var user = new UserModifyForm({
					uid : '<%=username%>',
					isModify : true,
					userDN : '${user.dn}'
				});
				user.show();
			});
		</script>
	</head>
	<body>
		<div id="mainDiv"></div>
	</body>
</html>
