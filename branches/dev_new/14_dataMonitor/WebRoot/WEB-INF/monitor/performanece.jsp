<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title></title>
	<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css"/>
		<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css"/>
		<script type="text/javascript"src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
		<script type="text/javascript"src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>		
		<script type="text/javascript">
		function queryDataList(){
		 var url="${pageContext.request.contextPath}/monitor_getSqlPerformance.action";
		 var id=$("#dataid").val();
		 var params={id:id};
		 $.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){ 
				$("#pitablelist").html(json);
				//var dataObj=eval("("+json+")");//
					
				});
		 }
		</script>	
</head>
<body class="easyui-layout" onload="queryDataList()">
		<input type="hidden" name="dataid" id="dataid" value="${id}">
		<div id="pitablelist" style="overflow:auto;height:470px;width:auto"><div>
</body>
</html>