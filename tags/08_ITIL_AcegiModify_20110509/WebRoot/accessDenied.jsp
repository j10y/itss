<%@ page contentType="text/html; charset=GBK" %>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title>IT服务系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<meta http-equiv="Content-Language" content="zh=cn" />
		<meta http-equiv="imagetoolbar" content="no" />
		<meta name="MSSmartTagsPreventParsing" content="true" />
		<meta name="keywords" content="free,css,template,business" />
		<meta name="author"
			content="David Herreman (http://www.free-css-templates.com)" />
		<style type="text/css" media="all"> @import "${pageContext.request.contextPath}/css/style_3c.css";</style>
		<link rel="alternate" type="application/rss+xml" title="RSS 2.0"
			href="rss/" />
		<script type="text/javascript">
			function goBack(){
				window.history.back(-1);
			}
		</script>
	</head>
	<body>
		<div class="text" align="center">
			<img src="${pageContext.request.contextPath}/images/003.png"/>
				<h2>对不起${userInfo.realName }，您没有权限，访问被决拒绝 ！</h2>
		</div>
		
	</body>
</html>