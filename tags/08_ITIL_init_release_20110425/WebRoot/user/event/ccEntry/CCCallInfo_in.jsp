<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title></title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/ext-3.2.1/resources/css/ext-all.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext-3.2.1/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/ext-3.2.1/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-basex.js"></script>
	<script type="text/javascript"> var webContext="${pageContext.request.contextPath}";</script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common_ext3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/FCKeditor/fckeditor.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Ext.form.FCKeditor.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/RowExpander.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Ext.ux.UploadDialog.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Ext.su.UpDownLoadFile.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/event/ccEntry/CCCallInfo_in.js"></script>	    
  	
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/treegrid/TreeGrid.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/TabCloseMenu.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/multisel/Multiselect.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/multisel/DDView.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/PageButton.js"></script>
  		<script type="text/javascript" src="${pageContext.request.contextPath}/js/CheckboxGroup.js"></script>
  	
  	
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

    Ext.onReady(function(){	
	    Ext.QuickTips.init();
	    Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
		  Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
          var pagePanel = new PagePanel();		 
		  pagePanel.render("Customer"); 
		  new Ext.Viewport({
		   	 layout:'fit',
		   	 items:[pagePanel]
		  });
	});
    
	Ext.override(Ext.grid.GridView, {
    templates: {
        cell: new Ext.Template(
               '<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>',
               '<div class="x-grid3-cell-inner x-grid3-col-{id}" {attr}>{value}</div>',
               "</td>"
           )
    }
	});
	</script>
</head>
<body onload="changeSkin('${userInfo.userViewStyle}');">
<div id="Customer" style="height:100%"></div>
</body>
</html>
