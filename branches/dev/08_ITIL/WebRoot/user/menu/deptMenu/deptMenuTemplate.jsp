<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>���Ų˵�����</title>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
	<%@include file="/includefiles.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/engine.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/util.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/dwr/interface/DeptMenuTemplateManager.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/deptMenu/deptMenuTemplateForm.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/deptMenu/deptMenuTemplatePanel.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingToolbarExt.js"></script>	
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
        background-image:url(extEngine/resources/images/other/folder_go.png);
    }
    .cls {
    	font-size:9pt;
    }
    .common-text {
    	font-size:9pt;
    }
    </style>
	<script type="text/javascript">
	var style = '${userInfo.userViewStyle}';
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', '${pageContext.request.contextPath}/extEngine/resources/css/' + value + '.css');
	}
	Ext.BLANK_IMAGE_URL = '${pageContext.request.contextPath}/extEngine/resources/images/default/s.gif';
	var glbRootPath = "${pageContext.request.contextPath}";
	var basePath = "http://localhost:8080"+webContext;
	if(typeof(glbRootPath) != "undefined"){
		basePath = glbRootPath;
	}
	
	Ext.onReady(function(){
	
	   Ext.QuickTips.init();    	      
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   var panel = new DeptMenuTemplatePanel();
	  
	   panel.render("panel");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[panel]
	   });
	   panel.initData();
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
  <div  id="panel" style="height:100%">
  </div>
  <select id="skins" onchange="changeSkin(this.value)" style="display:none">
		<option value="ext-all">Ĭ�Ϸ��</option>
		<option value="xtheme-gray">���׷��</option>
		<option value="xtheme-purple">��ɫ���</option>
		<option value="xtheme-olive">��ɫ���</option>
		<option value="xtheme-darkgray">��ɫ���</option>
		<option value="xtheme-black">��ɫ���</option>
		<option value="xtheme-slate">�������</option>
		<option value="xtheme-chocolate">�ɿ������</option>
  </select>
</body>
</html>