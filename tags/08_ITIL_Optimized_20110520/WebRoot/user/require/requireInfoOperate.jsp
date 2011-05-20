<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title> 
   <%@include file="/includefiles.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/require/requireInfoOperate.js"></script> 
  	  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/require/requireAuditHis.js"></script> 
  	
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
   	var dataId = '${dataId}';
   	var pagePanel = '${pagePanel}';
   	var status = '${status}';
	</script>
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
	   var operatePanel = new OperatePanel({
	   		serviceItemId:'${serviceItemId}',
	   		pagePanel:'${pagePanel}',
	   		reqClass:'${reqClass}',
	   		dataId:'${dataId}',
	   		oldId:'${oldId}',
	   		status:'${status}',
	   		processType:'${processType}',
	   		processId:'${processId}',
	   		processName:'${processName}',
	   		description:'${description}',
	   		readOnly:'${readOnly}'});
	   operatePanel.render("configItemEnv");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[operatePanel]
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
<div id="configItemEnv" style="height:100%"></div>
<div id="hello-win" class="x-hidden">
    <div class="x-window-header">Hello Dialog</div>
    <div id="hello-tabs">
        <!-- Auto create tab 1 -->
        <div class="x-tab" title="Hello World 1">
            <p>Hello...</p>
        </div>
        <!-- Auto create tab 2 -->
        <div class="x-tab" title="Hello World 2">
            <p>... World!</p>
        </div>
    </div>
</div>
</body>
</html>
