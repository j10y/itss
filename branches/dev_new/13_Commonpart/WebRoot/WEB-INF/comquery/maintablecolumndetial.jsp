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
	function returnTableDetail(){
	  window.location.href="${pageContext.request.contextPath}/manage_mainTableColumnList.action?maintableid=${maintable.tableid }";
	} 
	</script>
  </head>
  
  <body>
  <form action="${pageContext.request.contextPath}/manage_saveMainTableColumn.action" method="post"><div align="center"> 
  <input type="hidden" name="maintableid" value="${maintable.tableid }"> 
  <input type="hidden" name="columnid" value="${maintablecolumn.columnid }"> 
  <input type="hidden" name="isQueryOrder" value="${maintablecolumn.isQueryOrder }">
  <input type="hidden" name="isExportOrder" value="${maintablecolumn.isExportOrder }">
  <input type="hidden" name="isListOrder" value="${maintablecolumn.isListOrder }">
  <input type="hidden" name="isInsertOrder" value="${maintablecolumn.isInsertOrder }">
  </div><table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
   <td colspan="4" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">
   ${maintable.tableCName }表字段描述信息
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">字段名</td><td bgcolor="#FFFFFF"><input type="text" name="columnName"value="${maintablecolumn.columnName }"/></td>
   <td bgcolor="#FFFFFF">字段中文名</td><td bgcolor="#FFFFFF"><input type="text" name="columnCName" value="${maintablecolumn.columnCName }"/></td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">是否可查询</td>
   <td bgcolor="#FFFFFF">
     <select name="isQuery" id="isQuery" >
         <option value="0" <c:if test="${maintablecolumn.isQuery eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isQuery eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   <td bgcolor="#FFFFFF">是否不可修改</td>
   <td bgcolor="#FFFFFF">
     <select name="isUpdate" id="isUpdate" >
         <option value="0" <c:if test="${maintablecolumn.isUpdate eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isUpdate eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">是否可导出</td>
   <td bgcolor="#FFFFFF">
     <select name="isExport" id="isExport" >
         <option value="0" <c:if test="${maintablecolumn.isExport eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isExport eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   <td bgcolor="#FFFFFF">是否可插入</td>
   <td bgcolor="#FFFFFF">
     <select name="isInsert" id="isInsert" >
         <option value="0" <c:if test="${maintablecolumn.isInsert eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isInsert eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">是否在列表中显示</td>
   <td bgcolor="#FFFFFF">
     <select name="isList" id="isList" >
         <option value="0" <c:if test="${maintablecolumn.isList eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isList eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   <td bgcolor="#FFFFFF">字段类型</td>
   <td bgcolor="#FFFFFF">
     <select name="propertyType" id="propertyType" >
         <option value="1" <c:if test="${maintablecolumn.propertyType eq 1 }">selected</c:if> >文本框</option>
         <option value="2" <c:if test="${maintablecolumn.propertyType eq 2 }">selected</c:if> >下拉列表</option>
         <option value="3" <c:if test="${maintablecolumn.propertyType eq 3 }">selected</c:if> >复选框</option>
         <option value="4" <c:if test="${maintablecolumn.propertyType eq 4 }">selected</c:if> >单选框</option>
         <option value="5" <c:if test="${maintablecolumn.propertyType eq 5 }">selected</c:if> >时间</option>
         <option value="10" <c:if test="${maintablecolumn.propertyType eq 10 }">selected</c:if> >日期</option>
         <option value="6" <c:if test="${maintablecolumn.propertyType eq 6 }">selected</c:if> >隐藏域</option>
         <option value="7" <c:if test="${maintablecolumn.propertyType eq 7 }">selected</c:if> >扩展字段</option>
         <option value="8" <c:if test="${maintablecolumn.propertyType eq 8 }">selected</c:if> >弹出选择</option>
         <option value="9" <c:if test="${maintablecolumn.propertyType eq 9 }">selected</c:if> >文本域</option>
         <option value="11" <c:if test="${maintablecolumn.propertyType eq 11 }">selected</c:if> >密码</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">数据类型</td>
   <td bgcolor="#FFFFFF">
     <select name="dataType" id="dataType" >
         <option value="1" <c:if test="${maintablecolumn.dataType eq 1 }">selected</c:if> >字符串</option>
         <option value="2" <c:if test="${maintablecolumn.dataType eq 2 }">selected</c:if> >数字</option>
         <option value="3" <c:if test="${maintablecolumn.dataType eq 3 }">selected</c:if> >日期</option>
      	 </select> 
   </td>
  <td bgcolor="#FFFFFF">是否必填</td>
   <td bgcolor="#FFFFFF">
      <select name="isMust" id="isMust" >
         <option value="0" <c:if test="${maintablecolumn.isMust eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isMust eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">下拉列表sql/url链接</td>
   <td colspan="3" bgcolor="#FFFFFF">
   <textarea id="typeSql"name="typeSql" rows="5" cols="50">${maintablecolumn.typeSql }</textarea>
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">默认值：</td><td bgcolor="#FFFFFF"><input type="text" name="tolerant"value="${maintablecolumn.tolerant }"/></td>
   <td bgcolor="#FFFFFF">当时弹出树是否多选</td>
   <td bgcolor="#FFFFFF">
      <select name="isChecked" id="isChecked" >
         <option value="0" <c:if test="${maintablecolumn.isChecked eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isChecked eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">根据父ID生成子ID</td>
   <td bgcolor="#FFFFFF">
   		<select name="upColumnName" id="upColumnName" >
		     	 <option value="" >请选择</option>
		     <c:forEach var="column" items="${columnlist}">
		         <option value="${column.columnName }" <c:if test="${column.columnName eq maintablecolumn.upColumnName }">selected</c:if> >${column.columnName }</option>
		     </c:forEach>  
     	</select> 
   </td>
   <td bgcolor="#FFFFFF">是否验证唯一性</td>
   <td bgcolor="#FFFFFF">
      <select name="isUnique" id="isUnique" >
         <option value="0" <c:if test="${maintablecolumn.isUnique eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintablecolumn.isUnique eq 1 }">selected</c:if> >是</option>
      	 </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">模糊查询范围</td>
   <td bgcolor="#FFFFFF">
     <select name="likescope" id="likescope" >
     	<option value=""  >无</option>
         <option value="1" <c:if test="${maintablecolumn.likescope eq 1 }">selected</c:if> >1:%data</option>
         <option value="2" <c:if test="${maintablecolumn.likescope eq 2 }">selected</c:if> >2:data%</option>
      	 <option value="3" <c:if test="${maintablecolumn.likescope eq 3 }">selected</c:if> >3:%data%</option>
      	 <option value="4" <c:if test="${maintablecolumn.likescope eq 4 }">selected</c:if> >4:data</option>
      	 </select> 
   </td>
   <td colspan="1" bgcolor="#FFFFFF">
   验证类型
   </td>
   <td bgcolor="#FFFFFF">
   		<select name="validDataType" id="validDataType" >
         <option value="0" <c:if test="${maintablecolumn.validDataType eq 0 }">selected</c:if> >无</option>
         <option value="1" <c:if test="${maintablecolumn.validDataType eq 1 }">selected</c:if> >中文</option>
         <option value="2" <c:if test="${maintablecolumn.validDataType eq 2 }">selected</c:if> >数字</option>
         <option value="3" <c:if test="${maintablecolumn.validDataType eq 3 }">selected</c:if> >英文</option>
         <option value="4" <c:if test="${maintablecolumn.validDataType eq 4 }">selected</c:if> >邮箱</option>
         <option value="5" <c:if test="${maintablecolumn.validDataType eq 5 }">selected</c:if> >IP地址</option>
         <option value="6" <c:if test="${maintablecolumn.validDataType eq 6 }">selected</c:if> >电话号码</option>
         <option value="7" <c:if test="${maintablecolumn.validDataType eq 7 }">selected</c:if> >MAC地址</option>
         
      	</select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">列表宽度</td><td bgcolor="#FFFFFF"><input type="text" id="columnLength"name="columnLength"value="${maintablecolumn.columnLength}"/></td>
  <td bgcolor="#FFFFFF">弹出树是否子以上的都选择</td>
  <td bgcolor="#FFFFFF">
	  <select name="allTreeStatus" id="allTreeStatus" >
	  	<option value="0" <c:if test="${maintablecolumn.allTreeStatus eq 0 }">selected</c:if> >否</option>
	  	<option value="1" <c:if test="${maintablecolumn.allTreeStatus eq 1 }">selected</c:if> >是</option>
	  </select> 
   </td>
   </tr>
   <tr>
    <td colspan="4" bgcolor="#FFFFFF">
	    <input type="submit" name="save" value="保存"/>
	   <input type="button" value="返回" onclick="returnTableDetail();">
   </td>
   </tr>
  </table>
  </form>
  </body>
</html>