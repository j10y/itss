<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<title>config</title>
    <%@include file="/includefiles.jsp"%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/column-tree.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/TemplateManager.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/configTree/TreeDataPanel.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/configTree/configSearch.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/configTree/ConfigSourceDataPanel.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/configTree/ConfigItemComponent.js"></script>
	
	
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
    					
			var panel = new ConfigItemComponentPanel();
			panel.render("panel");
			new Ext.Viewport({
		          layout:'fit',
		          items:[panel]
       		});
			
        });
     </script>   
    
</head>
<body>
<div id="panel"></div>
</body>
</html>
