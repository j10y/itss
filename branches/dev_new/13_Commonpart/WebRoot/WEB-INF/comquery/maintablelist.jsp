<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/adminmanage/CssAdmin.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript">
	function querylist(){
		var xform = document.queryform;
		xform.submit();
	}
	
	function removes(){
		if (confirm("您确实要删除此表及表的字段信息吗？")){
		}else{
		return false;
		}
	}
	</script>
  </head> 
  <body topMargin='20'>
  <form name="queryform" action="${pageContext.request.contextPath}/manage_findMainTableListByPars.action" method="post">
 <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  	<tr>
   <td colspan="5" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">
   表管理
   </td>
   </tr>
  	<tr>
   <td bgcolor="#FFFFFF" align="center">字段名:</td>
   <td bgcolor="#FFFFFF" align="center"><input type="text" name="tableName"></td>
   <td bgcolor="#FFFFFF" align="center">字段中文名:</td>
   <td bgcolor="#FFFFFF" align="center"><input type="text" name="tableCName"></td>
   <td bgcolor="#FFFFFF" align="center"><input type="submit" name="button"value="查询" ></td>
   </tr>
   </table>
  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
  <td colspan="5" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">
  表信息列表
  </td>
  </tr>
  	<tr>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">表编号</td>
	   <td bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">表名</td>
	   <td bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">表中文名</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">格式类型</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">操作</td>
   </tr>
   <c:forEach var="map" items="${list}">
   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
   <td noWrap>
   <a href="${pageContext.request.contextPath}/manage_mainTableColumnList.action?maintableid=${map.tableid}">${map.tableid}(点击进入修改表字段)</a>
   </td>
   <td >
   ${map.tableName}
   </td>
   <td >
   ${map.tableCName}
   </td>
   <td >
   <c:if test="${map.formatType eq 0 }">表</c:if>
   <c:if test="${map.formatType eq 1 }">图</c:if>
   <c:if test="${map.formatType eq 2 }">图表</c:if>
   </td>
   <td noWrap>
   <a onclick="return removes();" href="${pageContext.request.contextPath}/manage_removeMainTable.action?maintableid=${map.tableid}">删除</a>|
   <a href="${pageContext.request.contextPath}/manage_modifyMainTable.action?maintableid=${map.tableid}">修改</a>
   </td>
   </tr>
   </c:forEach>
   <tr>
   <td colspan="4" bgcolor="#EFF3FF">
   </td>
   <td bgcolor="#EFF3FF">
   <a href="${pageContext.request.contextPath}/manage_modifyMainTable.action">新增</a>
   </td>
   </tr>
   </table>
    </form>
  </body>
</html>
