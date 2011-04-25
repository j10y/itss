<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<%@ include file="/includefiles.jsp"%>
<html>
	<head>
		<title>List Compontent</title>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk"> 

	    <script type="text/javascript" src="<c:url value="/js/util/UtilE.js"/>"></script>    
		<script type="text/javascript" src="<c:url value="/js/portal/Portlet.js"/>"></script>
		
		<link rel="stylesheet" type="text/css" href="<c:url value="/css/Common.css"/>" />

		
		<link rel="stylesheet" type="text/css"
			href="<c:url value="/css/portal.css"/>" />
		<link rel="stylesheet" type="text/css"
			href="<c:url value="/css/portlet.css"/>" />
	<style type="text/css">
        body .x-panel {
            margin-bottom:20px;
        }
        .icon-grid {
            background-image:url(../shared/icons/fam/grid.png) !important;
        }
        #button-grid .x-panel-body {
            border:1px solid #99bbe8;
            border-top:0 none;
        }
       
    </style>
	</head>
	<script type="text/javascript">
	var BP = '${pageContext.request.contextPath}/';
	Ext.onReady(com.faceye.portal.Portlet.init,com.faceye.portal.Portlet);
	function changeSkin(value){
	   Ext.util.CSS.swapStyleSheet('window', BP+'extEngine/resources/css/' + value + '.css');
	}
	</script>
	<body  onload="changeSkin('${userInfo.userViewStyle}');">
		<div id="topic-grid"></div>
	
		<div id="sub-tree"></div>
	</body>
</html>
