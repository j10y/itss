<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/TreePanel/TreePanel.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/TreePanel.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/commontree.js"></script>
	<script type="text/javascript">
	function optionClickTree(par){
		alert(par);
	}
	function optionClickCheck(treetype){
		var a=getCheckedId(treetype);
		alert(a);
	}
	function addMyTreeNode(){
		var newNode = new TreeNode({id:'20101117',text:'<html><b>节点</b></html>',icon:'mnode'});
		addTreeNode('10109',newNode);
		//var newNode = new TreeNode({text:'<b>节点</b>',icon:'mnode'});
		//insertBeforeTreeNode('10109',newNode,'1010902')
	}
	</script>
</head>
<body onload="queryListByNodes('1','node','节点树','1')">
<a href="#" onclick="optionClickCheck('node');">确定</a>
<a href="#" onclick="addMyTreeNode()">add</a>
<div id='tt' align="left">
	<div id='tree-div'></div>
</div>

</body>
</html>