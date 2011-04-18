<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<title>config</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/column-tree.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/b2b-grid.css" />
	<script src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
	<script src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/TemplateManager.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/SmartCheckboxSelectionModel.js"></script>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingToolbarExt.js"></script>	
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/test/modifyForm.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/test/borrowBookForm.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/test/GridModifyPanel.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/test/GridModifyPanel2.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/test/ModifyPanel.js"></script>
	
	
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
        var  id = ${param.id};
        
    </script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        //var templateId = '${templateId}';
        
    	Ext.onReady(function(){
    		alert("aaaaaaaaaaaaaaaaa"+id);			
			var panel = new ModifyPanel();
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
