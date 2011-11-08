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
	function loadColumn(){
		var sql="${fn:substring(maintable.sql,0,5) }";
		if(sql.length==0){
			alert("您没有写sql语句指明要哪些字段，因此无法自动加载！");
		}else
	  	window.location.href="${pageContext.request.contextPath}/manage_loadMainTableColumn.action?tableid=${maintable.tableid }";
	} 
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
  <input type="hidden" id="sql"name="sql" value="${maintable.sql }"> 
   
  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  	<tr>
   <td colspan="5" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">${maintable.tableCName }(${maintable.tableName } )</td>
   </tr>
  	<tr>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段编号</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段名</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段中文名</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段类型</td>
	   <td bgcolor="#FFFFFF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">操作</td>
   </tr>
   <c:forEach var="map" items="${list}">
   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
   <td >
   ${map.columnid}
   </td>
   <td >
   ${map.columnName}
   </td>
   <td >
   ${map.columnCName}
   </td>
   <td >
   <c:if test="${map.propertyType eq 1 }">文本框</c:if>
   <c:if test="${map.propertyType eq 2 }">下拉列表</c:if>
   <c:if test="${map.propertyType eq 3 }">复选框</c:if>
   <c:if test="${map.propertyType eq 4 }">单选框</c:if>
   <c:if test="${map.propertyType eq 5 }">时间</c:if>
   <c:if test="${map.propertyType eq 10 }">日期</c:if>
   <c:if test="${map.propertyType eq 6 }">隐藏域</c:if>
   <c:if test="${map.propertyType eq 7 }">扩展字段</c:if>
   <c:if test="${map.propertyType eq 8 }">弹出选择</c:if>
   <c:if test="${map.propertyType eq 9 }">文本域</c:if>
    <c:if test="${map.propertyType eq 11 }">密码</c:if>
   </td>
   <td >
   <a onclick="return removes();" href="${pageContext.request.contextPath}/manage_removeMainTableColumn.action?columnid=${map.columnid}&maintableid=${maintable.tableid }">删除</a>|
   <a href="${pageContext.request.contextPath}/manage_modifyMainTableColumn.action?columnid=${map.columnid}">修改</a>
   </td>
   </tr>
   </c:forEach>
   <tr>
   <td bgcolor="#EFF3FF" colspan="4">
   <a href="${pageContext.request.contextPath}/manage_mainTableColumnListSet.action?maintableid=${maintable.tableid}">设置字段的状态</a>
   |<a href="${pageContext.request.contextPath}/manage_mainTableChartList.action?maintableid=${maintable.tableid}">设置图形报表</a>
   </td>
   <td bgcolor="#EFF3FF">
   <a href="javascript:loadColumn()">加载字段信息</a>|
   <a href="${pageContext.request.contextPath}/manage_addMainTableColumn.action?tableid=${maintable.tableid }">新增</a>|
   <a href="${pageContext.request.contextPath}/manage_findMainTableList.action">返回</a>
   </td>
   </tr>
   </table>
   </form>
  </body>
</html>
