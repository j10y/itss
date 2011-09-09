<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<c:set var="webpath" value="${pageContext.request.contextPath}"></c:set>
<html>
	<head>
	<script type="text/javascript">
	</script>
		<title>欢迎</title>
	</head>
	<body>
	<form action="${webpath}/uploadPic?target=1.2.5" method="post" enctype="multipart/form-data">
		<input name="pic" type="file"/>
		<input type="submit"/>
	</form>
	</body>
</html>
