<%@ page contentType="text/html;charset=gbk" %>
<%@ include file="/user/deptMenu/in.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/DeptMenuTemplateManager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
	<script type="text/javascript">
	     Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
		 var glbRootPath = "${pageContext.request.contextPath}";
		 var userId = '${userInfo.id}';
		 var smtId = '${smtId}';
		 var dmtId = '${dmtId}';
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/user/deptMenu/deptMenu-index.js"></script>
</head>
<body>
</body>
</html>