<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title>
    <%@include file="/includefiles.jsp"%>  
	
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/account/requireAuditHis.js"></script>
  	
 	<script type="text/javascript" src="${pageContext.request.contextPath}/user/account/QQAccount/qqAccountDelete.js"></script> 
  	
  	
  	
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
	   var citId="";
	     if("${param.dataId}"==""){
	     	var pageTemplates = new PageTemplates({
	     						dataId:"0",
	     						serviceItemId:'${serviceItemId}',
	     						processType:'${processType}',
	   							status : '${status}',
	   							serviceItemProcess:'${serviceItemProcessId}',
	   					        processName:'${processName}',
	     						description:'${description}'});
	    new Ext.Viewport({
				   	 layout:'fit',
				   	 items:[pageTemplates]
				});
	   }
	   else{
	   var pname='${processName}';
	   if(pname==''){
			Ext.Ajax.request({
						url : webContext+'/accountAction_getApplyInfo.action',
						params : {
							dataId:'${param.dataId}'
						},
						success : function(response, options) {
						var result=Ext.decode(response.responseText);
				 			var pageTemplates = new PageTemplates({
	  							dataId:result.dataId,
	  							serviceItemId:result.serviceItemId,
	  							processType:result.processType,
	  							status :result.status,
	  							serviceItemProcess:result.serviceItemProcessId,
	  							processName:result.processName,
	    						description:result.description});
	    					new Ext.Viewport({
						   	 layout:'fit',
						   	 items:[pageTemplates]
						   });
		
					    },
						failure : function(response, options) {
								
						}
					}, this);
				}else{
					var pageTemplates = new PageTemplates({
	   							dataId:'${param.dataId}',
	   							serviceItemId:'${serviceItemId}',
	   							processType:'${processType}',
	   							status : '${status}',
	   							readOnly:'${readOnly}',
	   							serviceItemProcess:'${serviceItemProcessId}',
	   							processName:'${processName}',
	     						description:'${description}'});
	     						new Ext.Viewport({
						   	 layout:'fit',
						   	 items:[pageTemplates]
						   });
				}
	   }
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
