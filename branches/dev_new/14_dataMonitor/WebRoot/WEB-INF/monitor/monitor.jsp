<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>系统管理</title>
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
			document.getElementById("monitorright").src=url;
		}
		function queryListSel(operid){
		 var url="${pageContext.request.contextPath}/monitor_getMenuTreeData.action";
		 var params={operid:operid};
		 var tree;
		 $.post(
				url, //服务器要接受的url
				params, //传递的参数 
				function(json){ 
				var dataObj=eval("("+json+")");//
					root = dataObj;
					$("#tree-div").remove();
					$("#tt").append("<div id='tree-div'></div>");
					tree = new TreePanel({
						renderTo:'tree-div',
						'root' : root
					});
					tree.render();
					tree.expandAll();
					tree.on(function(node){
						if(node.attributes.id!="-1"&&node.attributes.id!=operid){
							var onurl=node.attributes.url;
							var opid=node.attributes.id;
							alert(opid);
							//document.getElementById("monitorright").src="monitor_getPerformance.action?id="+opid;
						}
					})
				});
			
		 }
		</script>	
</head>
<body class="easyui-layout" onload="queryListSel('0')">
		<div region="west" icon="icon-reload" title="服务器地址" split="true" style="width:300px; background: #dfe8f7;">
			<div style="margin-left: 0px; padding-top: 0px">
				<div icon="icon-punc-1"id="wlllfx" class="easyui-panel" title="" collapsible="true"
					minimizable="false" maximizable=false closable="false"
					style="width: 293px; height:auto; background: #dfe8f7;">
					<div id='tt'>
						<div id='tree-div'></div>
					</div>
				</div>
			</div>
		</div>
		<div region="center" title="" style="overflow:hidden;">
			<iframe id="monitorright"name="monitorright" scrolling="yes" frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
		</div>
</body>
</html>