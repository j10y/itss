<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>打分成功</title>
	</head>

	<body>
		<c:if test="${flag == 1}">
			<div style="height:200px;line-height:200px;text-align:center;font-size:18px">打分成功，请关闭窗口！</div>
		</c:if>
		<c:if test="${flag == 0}">
			<div style="height:200px;line-height:200px;text-align:center;font-size:18px;color:red">打分失败，请联系管理员！</div>
		</c:if>
	</body>
</html>
