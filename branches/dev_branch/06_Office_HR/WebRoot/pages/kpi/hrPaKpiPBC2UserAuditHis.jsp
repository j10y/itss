<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String basePath=request.getContextPath();
%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
	</head>

	<body>
		<div style="padding:5px 5px 5px 62px;">
			<table class="table-info" cellpadding="0" cellspacing="1" width="98%" align="center">
				<tr>
					<td>被考核人姓名</td>
					<td>${user}</td>
				</tr>
				<tr>
					<td>考核模板名称</td>
					<td>${pbcName}</td>
				</tr>
				<tr>
					<td>总分</td>
					<td>${totalScore}</td>
				</tr>
			</table>
		</div>
		<div style="padding:5px 5px 5px 62px;">${content}</div>
		
		<div style="padding:5px 5px 5px 62px;">${listHis}</div>
	</body>
</html>
