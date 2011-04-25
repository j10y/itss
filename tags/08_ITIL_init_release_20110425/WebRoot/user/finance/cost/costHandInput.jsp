<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>成本手工录入</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/plain; charset=gbk">
		<%@include file="/includefiles.jsp"%>     
		<script type="text/javascript"	src="${pageContext.request.contextPath}/user/finance/cost/costHandInput.js"/></script>
 		<style type="text/css"> 
		.x-head{
			background:url(images/titlelog.png) no-repeat left;
			height:65px;
			background-color: 'blank'
		}
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
	        background-image:url(images/other/folder_go.png);
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
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/ext-3.2.1/resources/css/' + value + '.css');
	}
	Ext.BLANK_IMAGE_URL = webContext+'/ext-3.2.1/resources/images/default/s.gif';
	</script>
</head>
	<body onload="changeSkin('${userInfo.userViewStyle}');">
		<div id="costHandInputDiv"></div>
	</body>
</html>