<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/tabs.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editable-column-tree.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/feed-viewer.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/examples.css" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/b2b-grid.css" />  
    <script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/examples.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/RowExpander.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingToolbarExt.js"></script>	 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/req/reqAccPanal.js"></script>
	<style type="text/css"> 
		.x-head{
			background:url(images/titlelog.png) no-repeat left;
			height:65px;
			background-color: 'blank'
		}
		html, body {
	        font:normal 12px verdana;
	        margin:0;
	        padding:0;
	        border:0 none;
	        overflow:hidden;
	        height:100%;
	    }
		p {
		    margin:5px;
		}
	    .nav {
	        background-image:url(images/other/folder_go.png);
	    }
	    .cls {
	    	font-size:9pt;
	    }
	    .common-text {
	    	font-size:9pt;
	    }
    </style>
	<script type="text/javascript">
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/extEngine/resources/css/' + value + '.css');
	}
	function updateTab(id,title, url) {
    	var tab = mainPanel.getItem(id);
  		if(tab){
   			mainPanel.remove(tab);
   		}
    	tab = addTab(id,title,url);
   		mainPanel.setActiveTab(tab);
    }
	
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var reqAccPanel = new ReqAccPanel();
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 autoScroll:true,
	   	 items:[reqAccPanel]
	   });
	});
	</script>
</head>
<body onload="changeSkin('${userInfo.userViewStyle}');">
</body>
</html>

