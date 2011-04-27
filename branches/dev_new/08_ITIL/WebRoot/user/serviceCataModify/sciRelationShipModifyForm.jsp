<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">

<script>
var dataId='${dataId}';
 var modifyDataId = '${dataId}';
var oldModifyDataId = '${oldCataId}';
 var rootId = '${rootId}';
var alterFlag ='${alterFlag}';
 var rootText = '${rootText}';
var dispFlag = '${dispFlag}';
</script>
<title>config</title>
<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/SCIRelationShipManager.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/serviceCataModify/service_tree.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/serviceCataModify/service_grid.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/serviceCataModify/serviceComponent.js"></script>
	
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
    	Ext.onReady(function(){		
			var panel = new PageModelComponentPanel();
			panel.render(Ext.getBody());
			
        });
     </script>   
    
</head>
<body>
<div id="panel"></div>
</body>
</html>
