<%@ page language="java" pageEncoding="gbk"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>合同管理系统</title>
  <%@include file="/includefiles.jsp"%>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gbk">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">    
  	<script type="text/javascript" src="${pageContext.request.contextPath}/infoAdmin/userExpand/userListPanel.js"></script>
  	<script language="javascript">
		var className = '${param.className}';			
	</script>
  	<script type="text/javascript">
	
		Ext.onReady(function(){
		   Ext.QuickTips.init();
		   Ext.state.Manager.setProvider(new Ext.state.CookieProvider());		  
		   var userPanel = new userListPanel();
		   userPanel.render("user");
		   
		   new Ext.Viewport({
		   	 layout:'fit',
		   	 items:[userPanel]
		   });		  
		});
	</script>
</head>
<body>
  <div id="user" style="height:100%"></div>
</body>
</html>
