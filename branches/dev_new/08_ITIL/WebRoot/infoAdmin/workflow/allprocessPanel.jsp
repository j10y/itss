<%@ page language="java" pageEncoding="gbk"%>
<html>
	<head>
		<title>欢迎使用IT服务系统</title>		 
		<%@include file="/includefiles.jsp"%>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
		<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/infoAdmin/workflow/allprocessPanel.js"></script>

		<style type="text/css">
.x-head {
	background: url(images/titlelog.png) no-repeat left;
	height: 65px;
	background-color: 'blank'
}

html,body {
	font: normal 12px verdana;
	margin: 0;
	padding: 0;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}

p {
	margin: 5px;
}

.nav {
	background-image: url(images/other/folder_go.png);
}

.cls {
	font-size: 9pt;
}

.common-text {
	font-size: 9pt;
}
</style>
		<script type="text/javascript">
	function getProcessHistory(id){
		window.location.href = webContext + "/workflow/history.do?procid="+id+"&methodCall=list";
	}
	function endProcess(id){
		Ext.Msg.confirm("确认", "您确定要终止流程？ ", function(bool){
			if(bool=="yes"){
				var url = "${pageContext.request.contextPath}/workflow/endprocess.do?id="+id;
			    url += "&methodCall=endProcess";
				Ext.Ajax.request({  
			        url: url,
			        async: false,
			        method: 'POST',
			        success: function(response, opts) {	
			        	obj = Ext.util.JSON.decode(response.responseText);
			        	if(obj.success){
			        		Ext.getCmp("ListProcessPanel").listAll();
						}			         	
			        },
			        failure: function(response, opts) {
			         	Ext.Msg.alert("提示","操作失败");
			        }   
			    });
			}
		});
		
    }
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
	   var listProcessPanel = new ListProcessPanel();
	   
	   listProcessPanel.render("process");
	   
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[listProcessPanel]
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
		<div id="process" style="height: 100%"></div>
		<div id="hello-win" class="x-hidden">
			<div class="x-window-header">
				Hello Dialog
			</div>
			<div id="hello-tabs">
				<!-- Auto create tab 1 -->
				<div class="x-tab" title="Hello World 1">
					<p>
						Hello...
					</p>
				</div>
				<!-- Auto create tab 2 -->
				<div class="x-tab" title="Hello World 2">
					<p>
						... World!
					</p>
				</div>
			</div>
		</div>

	</body>
</html>
