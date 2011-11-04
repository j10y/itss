<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>上传结果页面</title>
		<style type="text/css">
			* { margin:0 auto;}
			table { margin-top:20px; margin-bottom:20px; background:#9acfd0; border:4px solid #dbf2f2; width:90%;}
			table td { background:#fff; text-align:center; font-size:14px; color:#333;}
			table thead td { background:#dbf2f2; font-weight:bold; font-size:16px; height:25px; line-height:25px;}
		</style>
	</head>

	<body>
		<c:choose>
			<c:when test="${uploadFileType == 1}">
				<table cellpadding="4" cellspacing="1" border="0">
					<thead>
						<td>编号</td>
						<td>所属部门</td>
						<td>品类</td>
						<td>考核标准</td>
						<td>目标值</td>
					</thead>
					<c:forEach items="${resultList}" var="result" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${result['depName']}</td>
							<td>${result['categoryName']}</td>
							<td>${result['acName']}</td>
							<td>${result['target']}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
			<c:when test="${uploadFileType == 2}">
				<table cellpadding="4" cellspacing="1" border="0">
					<thead>
						<td>编号</td>
						<td>姓名</td>
						<td>考核标准</td>
						<td>目标值</td>
					</thead>
					<c:forEach items="${resultList}" var="result" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${result['fullname']}</td>
							<td>${result['acName']}</td>
							<td>${result['target']}</td>
						</tr>
					</c:forEach>
				</table>
			</c:when>
		</c:choose>
	</body>
</html>
