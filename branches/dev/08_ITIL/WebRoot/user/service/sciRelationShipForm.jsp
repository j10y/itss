<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<script>
 var dataId = '${param.dataId}';
 var modifyDataId = '${dataId}';
 var rootId = '${rootId}';
 var rootText = '${rootText}';
</script>
<title>config</title>
	<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/SCIRelationShipManager.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/service/service_tree.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/service/service_grid.js"></script>
   <script type="text/javascript" src="${pageContext.request.contextPath}/user/service/serviceComponent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingToolbarExt.js"></script> 
	
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
        var dataId = '${param.dataId}';
		//alert(dataId);
    	Ext.onReady(function(){		
			var panel = new PageModelComponentPanel({dataId:'${param.rootCataId}'});
			panel.render(Ext.getBody());
			
        });
     </script>   
    
</head>
<body>
<div id="panel"></div>
</body>
</html>
