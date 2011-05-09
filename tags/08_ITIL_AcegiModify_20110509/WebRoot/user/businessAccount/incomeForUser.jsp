<%@ page language="java" pageEncoding="gbk"%> 
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<title>config</title>
<%@include file="/includefiles.jsp"%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/user/require/SRAction.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PageButton.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DWRTreeLoader.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/SmartCheckboxSelectionModel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingToolbarExt.js"></script>	
    <script type="text/javascript" src="${pageContext.request.contextPath}/user/businessAccount/incomeForUser.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/user/businessAccount/businessAccountAuditHis.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/user/businessAccount/BaAction.js"></script>
	
	
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
        
    </script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
        this.dataId = "${dataId}";
        var ba = new BaAction();
        var data = ba.getIncomeBa(this.dataId);
        this.requireId = data.reqId;
        this.applyNum = data.applyNum;
        this.userId = data.userId;
        this.descn = data.descn;
    	Ext.onReady(function(){
    		Ext.QuickTips.init();
			var panel = new Ext.Panel({
				width : "auto",
				height : "auto",
				autoScroll : true,
				plain : true,
				labelAlign : "right",
				frame : true,
				items : [this.getTabPanel()]
			});
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
<div id="gridWidth" style="width:100%,height:100%"></div>
</body>
</html>
