<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<title>config</title>
<%@include file="/includefiles.jsp"%>
   <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/column-tree.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/b2b-grid.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/ConfigItemRelationManager.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/ConfigUnitPanelAddRecordPanel.js"></script>
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
	    
	   var itemName = '${item.name}';
	   var itemBegin = '${item.beginDate}';
	    var itemEnd = '${item.endDate}';
	    var atechnoInfo = '${item.atechnoInfo}';
	    var btechnoInfo = '${item.btechnoInfo}';
	    var otherInfo = '${item.otherInfo}';
	    var itemId = '${item.id}';
	    var configItem = '${configItem.id}';	  
	   //alert(configItem);
	</script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        var templateId = '${templateId}';
        
    	Ext.onReady(function(){
    					
			var configUnitAddRecordPanel = new ConfigUnitAddRecordPanel();
			configUnitAddRecordPanel.render("configUnitAddRecordPanel");
			new Ext.Viewport({
		          layout:'fit',
		          items:[configUnitAddRecordPanel]
       		});
			
        });
     </script>   
    
</head>
<body>
<div id="configUnitAddRecordPanel"></div>
</body>
</html>
