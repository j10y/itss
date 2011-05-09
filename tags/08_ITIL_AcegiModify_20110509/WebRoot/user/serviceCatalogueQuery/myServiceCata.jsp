<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<%@include file="/includefiles.jsp"%>  
	<script type="text/javascript" src="${pageContext.request.contextPath}/user/serviceCatalogueQuery/myServiceCata.js"></script>
	
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
			var panel = new MyServiceCataPanel();
			panel.render(Ext.getBody());
        });
     </script>   
    
</head>
<body>
<div id="panel"></div>
</body>
</html>
