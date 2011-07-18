<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<c:set var="webpath" value="${pageContext.request.contextPath}"></c:set>
<html>
	<head>
	<script type="text/javascript">
	var c = "${pageContext.request.contextPath}";
	 function a() {
	 	alert("${webpath}");
	 	alert("${pageContext.request.contextPath}");
	 }
	</script>
		<title>User</title>
	</head>
	<body onload="a()">
		<form action="${webpath}/test/testAction!add.action" method="post" >
      	姓名：<input type="text" name="name">
      	<input type="submit" value="提交">
    </form>
	</body>
</html>
