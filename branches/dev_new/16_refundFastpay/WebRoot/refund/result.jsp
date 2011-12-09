<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>即时到帐批量退款结果页面</title>
	</head>
	<body>
		<br>
		<center>
		<h1>
		<c:if test="${result.success }">
			即时到帐批量退款导入成功！
		</c:if>
		<c:if test="${not result.success }">
			即时到帐批量退款导入失败！
		</c:if>
		
		</h1>
		<br/>
		<c:if test="${not empty result.errorCode }">
			错误码：
		</c:if>
			${result.errorCode }
		<br/>
		<c:if test="${not empty result.exception }">
			异常信息（操作人员可以给开发人员快速解决）：
		</c:if>
			${result.exception }
		<br/>
		<br/>
			${result.msg }
		<br/>			
		<br/>			
		<br/>			
			<input type="button" onclick="window.close();" value="关闭">
		</center>
		<br>
	</body>
</html>