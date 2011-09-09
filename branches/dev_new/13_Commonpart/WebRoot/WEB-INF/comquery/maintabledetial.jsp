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
	function returnTableDetail(){
	  window.location.href="${pageContext.request.contextPath}/manage_findMainTableList.action";
	} 
	
	function saveMainTable(){
	  var xform = document.maintableform;
	  var isMulti=document.getElementById("isMulti").value;
	  var multiSql=document.getElementById("multiSql").value;
	  if(isMulti==1&&(multiSql==null||multiSql.length==0)){
	  	alert("当为拼接表是，获取表名的sql不能为空！");
	  	return false;
	  }
	 // alert(isMulti);
	  //alert(multiSql);
	  xform.submit();
	} 
	</script>
  </head>
  
  <body>
  <form name="maintableform"action="${pageContext.request.contextPath}/manage_saveMainTable.action" method="post"><div align="center"> 
  <input type="hidden" name="tableid" value="${maintable.tableid }"> 
  </div><table width="90%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <tr>
   <td colspan="4" bgcolor="#EFF3FF" align="center" style="background-image:url(${pageContext.request.contextPath}/Contents/image/grid-blue-hd.gif); border:0px">
   表详细信息
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">表名：</td><td bgcolor="#FFFFFF"><input type="text" name="tableName"value="${maintable.tableName }"/></td>
   <td bgcolor="#FFFFFF">表中文名:</td><td bgcolor="#FFFFFF"><input type="text" name="tableCName" value="${maintable.tableCName }"/></td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">格式类型</td>
   <td bgcolor="#FFFFFF">
     <select name="formatType" id="formatType" >
         <option value="0" <c:if test="${maintable.formatType eq 0 }">selected</c:if> >表</option>
         <option value="1" <c:if test="${maintable.formatType eq 1 }">selected</c:if> >图</option>
         <option value="2" <c:if test="${maintable.formatType eq 2 }">selected</c:if> >图表</option>
      </select> 
   </td>
   <td bgcolor="#FFFFFF">是否多表拼接</td>
   <td bgcolor="#FFFFFF">
     <select name="isMulti" id="isMulti" >
         <option value="0" <c:if test="${maintable.isMulti eq 0 }">selected</c:if> >否</option>
         <option value="1" <c:if test="${maintable.isMulti eq 1 }">selected</c:if> >是</option>         
      </select> 
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">唯一标识的字段</td>
   <td bgcolor="#FFFFFF">
     <select name="keyColumnName" id="keyColumnName" >
     <option value="" >请选择</option>
     <c:forEach var="column" items="${columnlist}">
         <option value="${column.columnName }" <c:if test="${column.columnName eq maintable.keyColumnName }">selected</c:if> >${column.columnCName }</option>
     </c:forEach>  
     </select> 
     
   </td>
    <td bgcolor="#FFFFFF">真正表名：</td><td bgcolor="#FFFFFF"><input type="text" name="tableRealName"value="${maintable.tableRealName }"/></td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">是否进入临时表</td>
   <td bgcolor="#FFFFFF" colspan=3>
     <select name="isPutTemp" id="isPutTemp" >
     <option value="" >请选择</option>
     <option value="0" <c:if test="${maintable.isPutTemp eq 0 }">selected</c:if> >否</option>
     <option value="1" <c:if test="${maintable.isPutTemp eq 1 }">selected</c:if> >是</option>       
   </select> 
     
   </td>
    
   </tr>
   
   <tr>
   <td bgcolor="#FFFFFF">
   	所需字段的sql：
   </td>
   <td colspan="3" bgcolor="#FFFFFF">
   <textarea id="sql"name="sql" rows="5" cols="70">${maintable.sql }</textarea>
   </td>
   </tr>
   <tr>
   <td bgcolor="#FFFFFF">
   	获取表名的sql：
   </td>
   <td colspan="3" bgcolor="#FFFFFF">
   <textarea id="multiSql" name="multiSql" rows="5" cols="70">${maintable.multiSql }</textarea>
   </td>
   </tr>
   
   <tr>
   <td colspan="3" bgcolor="#FFFFFF">
   </td>
   <td bgcolor="#FFFFFF"><input type="button" name="save" value="保存" onclick="return saveMainTable();"/>
      <input type="button" value="返回" onclick="returnTableDetail();">
   </td>
   </tr>
   <!-- <td>表编号</td><td><input type="text" name="tableid" value="${maintable.tableid }"/></td>
   <tr><td>类名</td><td><input type="text" name="className"/></td>
   <td>类路径</td><td colspan="2"><input type="text" name="classPath"  style="WIDTH:200px;"/></td>
   </tr>
    -->
  </table>
  </form>
  </body>
</html>
