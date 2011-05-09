<%@ page contentType="text/html;charset=gbk" %>
<%@ include file="/user/userMenu/in.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/UserMenuTemplateManager.js"></script>
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
		function changeSkin(value){
		   		 Ext.util.CSS.swapStyleSheet('window', '${pageContext.request.contextPath}/extEngine/resources/css/' + value + '.css');
		}
	     Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
		 var glbRootPath = "${pageContext.request.contextPath}";
		 var userId = '${userInfo.id}';
	</script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/user/userMenu/userMenu-index.js"></script>
</head>
<body onload="changeSkin('${userInfo.userViewStyle}');">
</body>
</html>