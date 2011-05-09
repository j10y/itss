<%@ page contentType="text/html;charset=gbk" %>
<%@ include file="/menu/in.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/NavigateMenuManager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
	<style type="text/css">
	html, body {
        font:normal 12px verdana;
        margin:0;
        padding:0;
        border:0 none;
        overflow:hidden;
        height:100%;
    }
	p {
	    margin:5px;
	}
    .nav {
        background-image:url(${pageContext.request.contextPath}/extEngine/resources/images/other/folder_go.png);
    }
    .cls {
    	font-size:9pt;
    }
    .common-text {
    	font-size:9pt;
    }
    </style>
	<script type="text/javascript">
		 var glbRootPath = "${pageContext.request.contextPath}";
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/menu/menu-index.js"></script>
</head>
<body>
</body>
</html>