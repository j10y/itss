<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hibernate</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
</head>
<body>
<form id="queryform" name="queryform" action="${pageContext.request.contextPath}/sample_getPage2.action">
<input id="page" type="hidden" name="page" value="${page.page}" ispost="true">
<input id="pageSize" type="hidden" name="pageSize" value="${page.pageSize}" ispost="true">
<table class='MzTreeViewRow' style="width:100%; text-align:right;font-size:12px"> 
<tr style="background-image:url(${pageContext.request.contextPath}/Images/Page/datagrid_header_bg.gif); border:0px;background-repeat:repeat; text-align:center; height:27px;font-size:12px">
<td class='MzTreeViewCell0'></td>
<th class='MzTreeViewCell1'>
操作人员
</th>
<th class='MzTreeViewCell1'>
操作时间
</th>
<th class='MzTreeViewCell1'>
操作IP
</th>
</tr>
<c:forEach var="item" items="${page.data}"varStatus="status">
        <tr class="trRow" onmouseover="this.style.backgroundColor = '#D0E5F5'" style="CURSOR: hand" 
		      onmouseout="this.style.backgroundColor = ''" bgcolor="#FFFFFF"> 
		      <td class='MzTreeViewCell0'>${(page.page-1)*page.pageSize+status.index+1  }</td>
		      <td class='MzTreeViewCell1'>${item.operateUserName }</td>
		      <td class='MzTreeViewCell1'>${item.operateDate }</td>
		      <td class='MzTreeViewCell1'>${item.operateIP }</td>
		</tr>
</c:forEach>
</table>
 <%@include file="/pagefoot.jsp"%>
</form>
</body>
</html>