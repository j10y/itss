<%@ page language="java" pageEncoding="UTF-8"%>

<html>
	<head>
		<title>欢迎使用IT服务系统</title>
		<%@ include file="/includefiles.jsp"%>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/infoAdmin/organization/roleManager.js"></script>

		<style type="text/css">
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
	background-image: url(../extEngine/resources/images/other/folder_go.png)
		;
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
	   		 Ext.util.CSS.swapStyleSheet('window', webContext+'/extEngine/resources/css/' + value + '.css');
	}
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	   var rolePanel = new RoleManagePanel();
	   rolePanel.render("role");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[rolePanel]
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
	<body>
		<div id="role" style="height: 100%">
		</div>
	</body>
</html>
