<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用该系统</title>
    <%@include file="/includefiles.jsp"%> 
  	<script type="text/javascript" src="${r"${pageContext.request.contextPath}"}/${jf.filePath}.js"></script>
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
<body onload="changeSkin('${r"${userInfo.userViewStyle}"}');">
</body>
</html>

