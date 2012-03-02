<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<title>即时到帐批量退款结果页面</title>
	</head>
	<body oncontextmenu="return false" >
		<br>
		<center>
		<h1>		
		操作成功！		
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
			<input type="button" onclick="window.close();" value="关闭"><input type="button" onclick="javascript:history.go(-1);" value="返回">
		</center>
		<br>
	</body>
</html>