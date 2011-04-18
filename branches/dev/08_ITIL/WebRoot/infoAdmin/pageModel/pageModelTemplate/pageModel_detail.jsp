<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<title>config</title>
<%@include file="/includefiles.jsp"%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/column-tree.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/b2b-grid.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<!--panel.loadData(); -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/PageModelTemplateManager.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/pageModel_Button.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/pageModel_tree.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/pageModel_grid.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/pageModelComponent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/GridModifyPanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/pageModel/pageModelTemplate/buttonComponent.js"></script>
	
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
        var templateId = '${templateId}';
        
    	Ext.onReady(function(){
    		
    		var modelName = pageModuleName;
    					
			var panel = new PageModelComponentPanel();
			panel.render(Ext.getBody());
			
			if(modelName){
				
				panel.loadData();
			}		
			
        });
     </script>   
    
</head>
<body>
<div id="panel"></div>
</body>
</html>
