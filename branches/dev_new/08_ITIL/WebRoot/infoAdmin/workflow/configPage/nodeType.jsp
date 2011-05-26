<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head> 
  <title>欢迎使用IT服务系统</title>
  	<%@include file="/includefiles.jsp"%>   
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/extendTextArea.js"></script>
	<script type="text/javascript" src="<c:url value="/js/TabCloseMenu.js"/>"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeTypePanel.js"></script>
	  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/workflow/configPage/nodeTypeAndConfigUnitPanel.js"></script>
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
	
	function showUrl(value){
		//return "<a href='http://"+value+"'target='_blank'>"+value+"</a>";
		return "<a href='${pageContext.request.contextPath}/infoAdmin/workflow/configPage/"+value+"'target='_blank'>"+value+"</a>";
	}
	Ext.onReady(function(){	
	   Ext.QuickTips.init();

	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var workflowListPanel = new WorkflowListPanel();
	   
	   workflowListPanel.render("workflowListPanel");
	   
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[workflowListPanel]
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
<div id="workflowListPanel" style="height:100%"></div>
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
尚待实现
</body>
</html>
