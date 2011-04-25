<%@ page contentType="text/html;charset=gbk" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
	<title>菜单定制</title>
	<%@include file="/includefiles.jsp"%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/infoAdmin/pageModel/basePagePanel/grid-examples.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/PanelManager.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/basePagePanel/PanelComponent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/basePagePanel/PanelDataPanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/basePagePanel/ButtonDataPanel.js"></script>
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
        var pagePanelId = '${param.pagePanelId}';
    	Ext.onReady(function(){			
			var panel = new PanelComponent();
			panel.render(Ext.getBody());
        });
     </script> 
</head>
<body>
</body>
</html>