<%@ page language="java" pageEncoding="gbk"%>
<html>
	<head>
		<title></title>
    	<%@include file="/includefiles.jsp"%>
    	<script type="text/javascript"
			src="${pageContext.request.contextPath}/user/configItem/configItemBatchModify/configItemBatchModifyListForExterior.js"></script>
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
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/ext-3.2.1/resources/css/' + value + '.css');
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
		  Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		  Ext.BLANK_IMAGE_URL = webContext+'/ext-3.2.1/resources/images/default/s.gif';
		  var type="${param.type}";
		  var typeId="${param.typeId}";
		  var backUrl="${param.backUrl}";
		  var pagePanel = new PagePanel({type:type,typeId:typeId,backUrl:escape(backUrl)});		 
		  new Ext.Viewport({
		   	 layout:'fit',
		   	 items:[pagePanel]
		  });
	});
	</script>
	</head>
	<body onload="changeSkin('${userInfo.userViewStyle}');">
		
	</body>
</html>
