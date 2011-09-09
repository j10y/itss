<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>后台配置</title>
	<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/TreePanel/TreePanel.css"/>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/TreePanel.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/commontree.js"></script>
			
		<script type="text/javascript">
		function jumpToUrl(url){
			document.getElementById("manageright").src=url;
		}
		</script>	
</head>
<body class="easyui-layout">
		<div region="north" title="banner" border="false" style="height:100px;padding:10px;" >
			
		</div>
		<div region="south" title="" style="height:10px;padding:10px;background:#efefef;">
			
		</div>
		<div region="west" icon="icon-reload" title="后台配置" split="true" style="width:200px;">
			<div style="margin-left: 0px; padding-top: 0px">
				<div icon="icon-punc-1"id="wlllfx" class="easyui-panel" title="后台配置 " collapsible="true"
					minimizable="false" maximizable=false closable="false"
					style="width: 193px; height: auto; background: #dfe8f7;">
					<table>
						<tr height="30px">
							<td>
								<a href="javascript:void(0)"
									onclick="jumpToUrl('manage_findMainTableList.action');">表管理</a>
							</td>
						</tr>
						<tr height="30px">
							<td>
								<a href="javascript:void(0)"
									onclick="jumpToUrl('comquery_getPage.action?tableName=sys_sec_operate');">权限操作管理</a>
							</td>
						</tr>
						<tr height="30px">
							<td>
								<a href="javascript:void(0)"
									onclick="jumpToUrl('comquery_getPage.action?tableName=sys_sec_pagconfig');">系统设置</a>
							</td>
						</tr>
						<tr height="30px">
							<td>
								<a href="javascript:void(0)"
									onclick="jumpToUrl('comquery_getPage.action?tableName=test_report');">商品销售测试</a>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
		<div region="center" title="" style="overflow:hidden;">
			<iframe id="manageright"name="manageright" scrolling="yes" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
		</div>
</body>
</html>