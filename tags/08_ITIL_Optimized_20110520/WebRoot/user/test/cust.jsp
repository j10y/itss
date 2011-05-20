<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>录入图书</title>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/extEngine/resources/css/ext-all.css" />	
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css" />  
    <script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/ext-lang-zh_CN-GBK.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/extEngine/examples.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/js/SmartCheckboxSelectionModel.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/test/borrowBookForm.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/test/borrowBookList.js"></script>
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
	//改变皮肤
	function changeSkin(value){
	   		 Ext.util.CSS.swapStyleSheet('window', '../extEngine/resources/css/' + value + '.css');
	}
	Ext.BLANK_IMAGE_URL = '../extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	   
	   //新建一个查询面板,这个类定义在borrowBookList.js里
	   var panel = new BookSearchPanel();
	   
//	   form.render("user");
	   
	   //新建一个Viewport ,把查询面板放进来
	   new Ext.Viewport({
	      //布局使用自动适应
	   	  layout:'fit',
	   	  items:[panel]
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
