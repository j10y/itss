<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hibernate_jquery</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript">
		function getpagegrid(){
		$('#pagegrid').datagrid({
				title:'jquerygrid',
				iconCls:'icon-save',
				width:'auto',
				height:'auto',
				nowrap: false,
				striped: false,
				pageSize:20,
				url:'sample_toPage3.action',
				remoteSort:false,//是否全局排序,即后台数据库排序
				columns:[[
					{field:'ck',checkbox:true},
					{field:'id',title:'id',width:120,sortable:true},
					{field:'operateUserName',title:'operateUserName',width:120,sortable:true},
					{field:'operateDate',title:'operateDate',width:200,sortable:true},
					{field:'operateIP',title:'operateIP',width:150,sortable:true}
				]],
				pagination:true,
				rownumbers:true,
				toolbar:[
				{
					id:'btnadd',
					text:'Add',
					iconCls:'icon-add',
					handler:function(){
						$(btnadd).linkbutton('enable');
						alert('add');
					}
				},'-',{
					id:'btncut',
					text:'Cut',
					iconCls:'icon-cut',
					handler:function(){
						$(btncut).linkbutton('disable');
						alert('Cut');
					}
				}
				]
		});
		}
	</script>
</head>
<body onload="getpagegrid();">
<div id="pagegrid">
</div>
</body>
</html>