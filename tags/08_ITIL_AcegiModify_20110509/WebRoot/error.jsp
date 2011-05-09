<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>IT服务管理系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<meta http-equiv="Content-Language" content="zh=cn" />
		<meta http-equiv="imagetoolbar" content="no" />
		<meta name="MSSmartTagsPreventParsing" content="true" />
		<meta name="description" content="LGBlue Free Css Template" />
		<meta name="keywords" content="free,css,template,business" />
		<meta name="author"
			content="David Herreman (http://www.free-css-templates.com)" />
		<style type="text/css" media="all">
		@import "css/style_3c.css";
		</style>
		<link rel="alternate" type="application/rss+xml" title="RSS 2.0"
			href="rss/" />

		<script language="javascript">
			function goBack(){
				window.history.back(-1);
			}
		</script>
	<body >
		<c:choose>
			<c:when test="${errorMessage == null or errorMessage == ''}">
				<c:set var="message" value="${errorMsg}"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="message" value="${errorMessage}"></c:set>
			</c:otherwise>
		</c:choose>

		<c:if test="${message == null or message == ''}">
			<c:set var="message" value="操作失败，请联系管理员 ！"></c:set>
		</c:if>
		<div
			style="background: url(${pageContext.request.contextPath}/images/error.jpg) no-repeat center center; height:300px; "
			align="center">
			<div class="text" align="center" style="padding-top:120px;">
				<font style="font-size:14px;">
					${message}
				</font>
				<div class="text" align="center" style="padding-top:50px;">
					<input type="button" name="Submit" value="返回" style="width: 100px;"
						onclick="goBack()" />
				</div>
			</div>
		</div>
	</body>
</html>

