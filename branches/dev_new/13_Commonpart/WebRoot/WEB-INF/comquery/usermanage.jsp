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
			document.getElementById("usermanageright").src=url;
		}
		function queryListSel(operid){
		 var url="${pageContext.request.contextPath}/comquery_getMenuTreeData.action";
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
							var userid=$("#userid").val();
							document.getElementById("usermanageright").src=onurl+"&operid="+opid+"&realtableid="+userid;
						}
					})
				});
			
		 }
		</script>	
</head>
<body class="easyui-layout" onload="queryListSel('${operid}')">
<input type="hidden" id="userid" name="userid" value="${usermap['id']}">
		<div region="west" icon="icon-reload" title="菜单" split="true" style="width:200px; background: #dfe8f7;">
			<div style="margin-left: 0px; padding-top: 0px">
				<div icon="icon-punc-1"id="wlllfx" class="easyui-panel" title="" collapsible="true"
					minimizable="false" maximizable=false closable="false"
					style="width: 193px; height:auto; background: #dfe8f7;">
					<div id='tt'>
						<div id='tree-div'></div>
					</div>
					<!-- 
					<table>
						<c:forEach var="item" items="${list}">
							<tr height="30px">
								<td>
									<a href="javascript:void(0)"
										onclick="jumpToUrl('${item['url']}&operid=${item['id']}&realtableid=${usermap['id']}');">${item['operatename']}</a>
								</td>
							</tr>
						</c:forEach>
					</table>
					 -->
				</div>
			</div>
		</div>
		<div region="center" title="" style="overflow:hidden;">
		<c:forEach var="item" items="${list}" begin="0"  end="0" step="1">
			<iframe id="usermanageright"name="usermanageright" scrolling="yes" frameborder="0"  src="${item['url']}&operid=${item['id']}" style="width:100%;height:100%;"></iframe>
		</c:forEach>
		</div>
</body>
</html>