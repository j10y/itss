<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="fmt"%>
<%@ include file="/includefiles.jsp"%>
<html>
	<head>
	
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk"> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/TabCloseMenu.js"></script>
	<script type="text/javascript" src="<c:url value="/js/util/UtilE.js"/>"></script>    
	 	
		<link rel="stylesheet" type="text/css"
			href="<c:url value="/js/ext-ui/Grid-Row-Action/Grid-Row-Action.css"/>" />
		<script type="text/javascript"
			src="<c:url value="/js/ext-ui/Grid-Row-Action/Grid-RowAction.js"/>"></script>
		<script type="text/javascript"
			src="<c:url value="/js/portal/PortletSubscribe.js"/>"></script>
		<script type="text/javascript">
		  var BP = '${pageContext.request.contextPath}/';
		  var portalId = '${portalId}';
		  Ext.onReady(function(){
		  		 com.faceye.portal.UserSubscribePortlet.init('${portalId}');
		  });
	     
	      function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/extEngine/resources/css/' + value + '.css');
		}
        </script>
		<style type="text/css">
		table#portlet-subscribe-view-template-table {
			font-size: 12px;
			width:95%;
		}
		table#portlet-subscribe-view-template-table table {
			font-size: 12px;
			width:95%;
		}
		div#portlet-subscribe-view-template-div{
		
		}
		.add-user-portlet {
			background-image:
				url(/ext/resources/images/default/tree/drop-add.gif)
				!important;
			)
		}
		table#user-subscribe-porltet-table {
		   margins:5px 5px 0 5px;
		}
		</style>
	</head>
	<body onload="changeSkin('${userInfo.userViewStyle}');">
		<div align="right">
			<br>
		</div>
		<div id="portlet-grid"></div>
	</body>
</html>
