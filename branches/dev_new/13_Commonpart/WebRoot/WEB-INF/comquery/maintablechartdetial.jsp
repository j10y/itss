<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*" errorPage=""%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
  <head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8" />
   <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/adminmanage/CssAdmin.css">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	
	-->
	<script type="text/javascript">
	function saveChart(){
	
	}
	function returnTableDetail(){
	  window.location.href="${pageContext.request.contextPath}/manage_mainTableChartList.action?maintableid=${maintable.tableid}";
	} 
	</script>
  </head>
  
  <body>
  <form action="${pageContext.request.contextPath}/manage_saveChartDetail.action" method="post"><div align="center"> 
  <input type="hidden" id="maintableid"name="maintableid" value="${maintable.tableid }"> 
  <input type="hidden" id="id" name="id" value="${sp.id}">
  </div><table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
   <td colspan="4" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">
   ${maintable.tableCName }表的图形报表
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">统计图名称</td><td bgcolor="#FFFFFF"><input name="tjPictureName"  id="tjPictureName"  type="text" value="${sp.tjPictureName}"></td>
   <td bgcolor="#FFFFFF">统计图对应URL</td><td bgcolor="#FFFFFF"><input name="tjPictureURL"  id="tjPictureURL"  type="text" value="${sp.tjPictureURL}"></td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">统计图类型</td>
   <td bgcolor="#FFFFFF">
     <select name="tjPictureType" id="tjPictureType" style="width:120px;" class="textfield" onchange="setText(this);">	       
	       <option value=""></option>
	       <option value="1" <c:if test="${sp.tjPictureType eq 1}"> selected</c:if>>饼图</option>
	       <option value="2" <c:if test="${sp.tjPictureType eq 2}">  selected</c:if>>柱状-横向</option>
	       <option value="3" <c:if test="${sp.tjPictureType eq 3}">  selected</c:if>>柱状-纵向</option>
	       <option value="4" <c:if test="${sp.tjPictureType eq 4}">  selected</c:if>>曲线</option>
	</select>
   </td>
   <td bgcolor="#FFFFFF">X轴名字</td>
   <td bgcolor="#FFFFFF">
    <input name="XName"  id="XName"  style="width:100px;" type="text" value="${sp.XName}">
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">Y轴名字</td>
   <td bgcolor="#FFFFFF">
     <input name="YName"  id="YName"   style="width:100px;" type="text" value="${sp.YName}">
   </td>
   <td bgcolor="#FFFFFF">itemName</td>
   <td bgcolor="#FFFFFF">
     <select name="itemName" id="itemName" style="width:100px;" class="textfield">	       
	       <option value=""></option>
	       <c:forEach  var="item" items="${columnlist}" varStatus="status">
	       <option value="${item.columnid}" <c:if test="${sp.itemName.columnid eq item.columnid}"> selected</c:if>>${item.columnCName}</option>
	       </c:forEach>
	</select>
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">lableTickName</td>
   <td bgcolor="#FFFFFF">
   <select name="lableTickName" id="lableTickName" style="width:100px;" class="textfield">	       
	       <option value=""></option>
	       <c:forEach  var="item" items="${columnlist}" varStatus="status">
	       <option value="${item.columnid}" <c:if test="${sp.lableTickName.columnid eq item.columnid}"> selected</c:if>>${item.columnCName}</option>
	       </c:forEach>
	</select>
   </td>
   <td bgcolor="#FFFFFF">Y数量</td>
   <td bgcolor="#FFFFFF">
   <select name="numberName" id="numberName" style="width:100px;" class="textfield">	       
	       <option value=""></option>
	       <c:forEach  var="item" items="${columnlist}" varStatus="status">
	       <option value="${item.columnid}" <c:if test="${sp.numberName.columnid eq item.columnid}"> selected</c:if>>${item.columnCName}</option>
	       </c:forEach>
	</select>
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF" colspan=4 ><input type="submit" name="save" value="保存"/>
      <input type="button" value="返回" onclick="returnTableDetail();">
   </td>
   </tr>
  </table>
  </form>
  </body>
</html>
