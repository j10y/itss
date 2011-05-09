<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<%@ include file="/includefiles.jsp"%>
<html>
	<head>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk"> 
		<title>List Compontent</title>
			<script type="text/javascript"
			src="<c:url value="/js/util/UtilE.js"/>"></script>
		<script type="text/javascript"
			src="<c:url value="/js/portal/PortalStyle.js"/>"></script>
		<link rel="stylesheet" type="text/css"
			href="<c:url value="/css/Common.css"/>" />
		
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
        .add {
            url(../../extEngine/resources/images/other/add.gif) !important;
        }
        .option {
            background-image:url(/faceye/scripts/ext/examples/shared/icons/fam/plugin.gif) !important;
        }
        .remove {
            url(../../extEngine/resources/images/other/delete.gif) !important;
        }
        .save {
            background-image:url(../../extEngine/resources/images/other/save.gif) !important;
        }
    </style>
	</head>
	<script type="text/javascript">
	var BP = '${pageContext.request.contextPath}/';
	Ext.onReady(com.faceye.portal.PortalStyle.init,com.faceye.portal.PortalStyle);
	function changeSkin(value){
	   Ext.util.CSS.swapStyleSheet('window', BP+'extEngine/resources/css/' + value + '.css');
	}
	</script>
	<body  onload="changeSkin('${userInfo.userViewStyle}');">
		<div id="topic-grid"></div>
	
		<div id="sub-tree"></div>
	</body>
</html>
