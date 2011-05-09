<%@ page contentType="text/html;charset=gbk" %>
<%@ include file="/infoAdmin/template/userMenu/in.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/grid-examples.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/PanelManager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/PanelComponent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/PanelDataPanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/ButtonDataPanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pagePanelDemo3/GridModifyPanel.js"></script>
	<style type="text/css">
	#tree1, #tree2 {   
    	float:left;
    	margin:20px;
    	border:1px solid #c3daf9;
    	width:250px;
    	height:300px;
    }
    .folder .x-tree-node-icon{
		background:transparent url(../tree-image/folder.gif);
	}
	.x-tree-node-expanded .x-tree-node-icon{
		background:transparent url(../tree-image/folder-open.gif);
	}
    .common-text {
    	font-size:9pt;
    }
    </style>
	<script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        var pagePanelId = '${pagePanelId}';
    	Ext.onReady(function(){			
    		var grid = new GridModifyPanel();	
			var panel = new PanelComponent();
			panel.render(Ext.getBody());
			
			
				
        });
     </script> 
</head>
<body>
</body>
</html>