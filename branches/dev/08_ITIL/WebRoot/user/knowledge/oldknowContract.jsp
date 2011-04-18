<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title>
    <%@include file="/includefiles.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/knowledge/knowledgeChangeApproval.js"></script> 	
  	<script type="text/javascript">
	function audit(){
		window.parent.auditContentWin.audit();
   	}
   	function winClose(){
      	window.parent.auditContentWin.close();
   	}
  	</script>
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
	function getPanel(oldKnowContractId){
		var da = new DataAction();
		var	data = da.getSingleFormPanelElementsForEdit("KnowLedgeContract_pagepanel", oldKnowContractId);// 这是要随时变得
			for(var i=0;i<data.length;i++){
				data[i].readOnly=true;
                data[i].hideTrigger=true;
                data[i].emptyText="";
			}
		var	biddata = da.splitForReadOnly(data);
		var oldKnowFormPanel= new Ext.form.FormPanel({
		id : 'KnowLedgeContract_pagepanel',
		layout : 'table',
		height : 300,
		width : 300,
		frame : true,
		defaults : {
			bodyStyle : 'padding:5px'
		},
		layoutConfig : {
			columns : 4
		},
		items : biddata
		});
		return oldKnowFormPanel;
	};
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var panels=getPanel("${param.dataId}");
	    panels.render("configItemEnv");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[panels]
	   });
	   //orderApplyPanel.initData();
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
