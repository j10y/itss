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
	function gobacks(){
	  	window.location.href="${pageContext.request.contextPath}/manage_mainTableColumnList.action?maintableid=${maintable.tableid }";
	} 
	</script>
  </head> 
  <body>
  <form action="${pageContext.request.contextPath}/manage_saveMainTableColumnList.action" method="post">
  <input type="hidden" name="maintableid" value="${maintable.tableid }">
  <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  	<tr>
   <td colspan="8" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">${maintable.tableCName }(${maintable.tableName } )</td>
   </tr>
  	<tr>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段编号</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段名</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段中文名</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">字段类型</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">列表是否显示</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">是否查询字段</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">列表可导出</td>
	   <td noWrap bgcolor="#EFF3FF" style="background-image:url(${pageContext.request.contextPath}/Contents/image/td-gray.jpg); border:0px">是否是插入字段</td>
   </tr>
   <c:forEach var="map" items="${list}">
   <tr onmouseover="this.style.backgroundColor = '#F5F5F5'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#FFFFFF >
   <td noWrap>
   ${map.columnid}
   </td>
   <td noWrap>
   ${map.columnName}
   </td>
   <td noWrap>
   ${map.columnCName}
   </td>
   <td noWrap>
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
   <td noWrap>
   <input name="isList" type="checkbox" value="${map.columnid}"<c:if test="${map.isList eq 1}">checked</c:if>>
   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isListOrder}';}" 
            value="${map.isListOrder}" name="isListOrder_${map.columnid}">
   </td>
   <td noWrap>
   <input name="isQuery" type="checkbox" value="${map.columnid}"<c:if test="${map.isQuery eq 1}">checked</c:if>>
   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isQueryOrder}';}" 
            value="${map.isQueryOrder}" name="isQueryOrder_${map.columnid}">
   </td>
   <td noWrap>
   <input name="isExport" type="checkbox" value="${map.columnid}"<c:if test="${map.isExport eq 1}">checked</c:if>>
   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isExportOrder}';}" 
            value="${map.isExportOrder}" name="isExportOrder_${map.columnid}">
   </td>
   <td noWrap>
   <input name="isInsert" type="checkbox" value="${map.columnid}"<c:if test="${map.isInsert eq 1}">checked</c:if>>
   <input type="text" style="WIDTH: 40px; HEIGHT: 18px" onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='${map.isInsertOrder}';}" 
            value="${map.isInsertOrder}" name="isInsertOrder_${map.columnid}">
   </td>
   </tr>
   </c:forEach>
   <tr>
   <td bgcolor="#EFF3FF" colspan="7"></td>
   <td bgcolor="#EFF3FF"><input type="submit" name="save" value="保存"/>
   <input type="button" name="goback" value="返回" onclick="gobacks();"/>
   </td>
   </tr>
   </table>
   </form>
  </body>
</html>
