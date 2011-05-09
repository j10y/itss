<%@ page language="java" pageEncoding="gbk"%>
<html>
<head>
  <title>欢迎使用IT服务系统</title>
    <%@include file="/includefiles.jsp"%>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/user/require/specialRequireForDev/confirmByTechnicManager.js"></script> 
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

	Ext.onReady(function(){	
	   Ext.QuickTips.init();
	   Ext.state.Manager.setProvider(new Ext.state.CookieProvider()); 
	   if("${param.dataId}"==""){
	     var pageTemplates = new PageTemplates({dataId:"0"});
	   }
	   else{
	   var pageTemplates = new PageTemplates({dataId:"${param.dataId}",taskId:"${param.taskId}",readOnly:"${param.readOnly}"});
	   }
	   pageTemplates.render("configItemEnv");
	   new Ext.Viewport({
	   	 layout:'fit',
	   	 items:[pageTemplates]
	   });
	});
	
	</script>
</head>
<body>
<div id="configItemEnv" style="height:100%"></div>
</body>
</html>
