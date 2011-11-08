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
	function removes(){
		if (confirm("您确实要删除此表的字段信息吗？")){
		}else{
		return false;
		}
	}
	</script>
  </head> 
  <body>
  <form action="">
  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  	<tr>
   <td colspan="9" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">${maintable.tableCName }(${maintable.tableName } )</td>
   </tr>
  	<tr>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段编号</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">统计图名称</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">统计图对应URL</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">统计图类型</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">X轴名字</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">Y轴名字</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">itemName</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">lableTickName</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">操作</td>
   </tr>
   <c:forEach var="map" items="${list}">
   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
   <td >
   ${map.id}
   </td>
   <td >
   ${map.tjPictureName}
   </td>
   <td >
   ${map.tjPictureURL}
   </td>
   <td >
   ${map.tjPictureType}
   </td>
   <td >
   ${map.XName}
   </td>
   <td >
   ${map.YName}
   </td>
   <td >
   ${map.itemName.columnCName}
   </td>
   <td >
   ${map.lableTickName.columnCName}
   </td>
   <td >
   <a onclick="return removes();" href="${pageContext.request.contextPath}/manage_removeChartColumn.action?tableid=${maintable.tableid}&id=${map.id}">删除</a>|
   <a href="${pageContext.request.contextPath}/manage_toChartDetail.action?tableid=${maintable.tableid}&id=${map.id}">修改</a>
   </td>
   </tr>
   </c:forEach>
   <tr>
   <td bgcolor="#EFF3FF" colspan="8">
   </td>
   <td bgcolor="#EFF3FF">
   <a href="${pageContext.request.contextPath}/manage_toChartDetail.action?tableid=${maintable.tableid }">新增</a>|
   <a href="${pageContext.request.contextPath}/manage_mainTableColumnList.action?maintableid=${maintable.tableid}">返回</a>
   </td>
   </tr>
   </table>
   </form>
  </body>
</html>
