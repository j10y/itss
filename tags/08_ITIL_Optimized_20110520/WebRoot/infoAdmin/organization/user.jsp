<%@ page language="java" pageEncoding="gbk"%>

<html>
<head>
  <title>欢迎使用IT服务系统</title>
  <%@ include file="/includefiles.jsp" %>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/SmartCheckboxSelectionModel.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/organization/userDetail.js"></script> 
  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/organization/user.js"></script>
	<style type="text/css">
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
        background-image:url(../extEngine/resources/images/other/folder_go.png);
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
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var roleGrid;
	   var roleListGrid;
	   var roleEditGrid;
	   var userPanel = new UserManagePanel();
	   userPanel.render("user");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[userPanel]
	   });
	   userPanel.initData();
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
  <div id="user" style="height:100%">
  </div>
  <select id="skins" onchange="changeSkin(this.value)" style="display:none">
		<option value="ext-all">默认风格</option>
		<option value="xtheme-gray">银白风格</option>
		<option value="xtheme-purple">紫色风格</option>
		<option value="xtheme-olive">绿色风格</option>
		<option value="xtheme-darkgray">灰色风格</option>
		<option value="xtheme-black">黑色风格</option>
		<option value="xtheme-slate">深蓝风格</option>
  </select>
</body>
</html>
