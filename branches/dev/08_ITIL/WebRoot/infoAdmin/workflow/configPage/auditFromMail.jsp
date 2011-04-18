<%@ page language="java" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/includefiles.jsp"%>
<html>
	<head>
		<title>欢迎使用IT服务系统</title>
		<%@include file="/includefiles.jsp"%>
		<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
		<script type="text/javascript" src="${pageContext.request.contextPath}/auditForms.js"></script>

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
	Ext.BLANK_IMAGE_URL = webContext+'/extEngine/resources/images/default/s.gif';
	Ext.onReady(function(){	
	   	Ext.QuickTips.init();

	   	Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	   	//2种类型：下单岗或者审批
	   	//传入参数说明
	   	//必须有：
	   	//applyType：申请类别
	   	//applyId:申请编号
	   	//可选：taskName，当打回申请人时为:backToCreator
	    <c:set var="taskId" value="${param.taskId}"/>
	    <c:set var="taskName" value="${param.taskName}"/>
	    <c:set var="applyType" value="${param.applyType}"/>
	    <c:set var="reqClass" value="${param.reqClass}"/>
	    <c:set var="goStartState" value="${param.goStartState}"/>
	    <c:set var="dataId" value="${param.dataId}"/>
	    <c:set var="virtualDefinintionId" value="${param.virtualDefinintionId}"/>
	    
	    var taskName = "${taskName}";
	    var virtualDefinintionId = "${virtualDefinintionId}";
	    var applyType = "${applyType}";
	    var taskId= "${taskId}";
		var applyId="${dataId}";
		var reqClass="${reqClass}";
		var goStartState="${goStartState}";
	    var pageUrl="/servlet/getPageModel?taskId="+taskId+"****virtualDefinintionId="+'${param.virtualDefinintionId}';
	   	var applyContentForm = null;
	   	//注入窗口
	   	this.auditContentWin = null;	
	   	//显示窗口
	   
	   	showAuditWindow(taskId,taskName,applyType,applyId,pageUrl,reqClass,goStartState); 
		auditContentWin.maximizable = true;
		auditContentWin.maximize();
	   	//关闭窗口   	
	   	
	   	<!--showAuditWindow(taskId, taskName, applyType, applyId); -->
	    
	   	new Ext.Viewport({
	   	 	layout:'fit',
	   	 	autoScroll:true
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
	</body>
</html>
