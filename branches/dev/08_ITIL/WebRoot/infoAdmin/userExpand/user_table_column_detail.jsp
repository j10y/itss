<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<title>用户主表字段设置</title>
<%@include file="/includefiles.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<script type="text/javascript">

function unicode(s) {
	var len = s.length;
	var rs = "";
	for (var i = 0; i < len; i++) {
		var k = s.substring(i, i + 1);
		rs += "&#" + s.charCodeAt(i) + ";";
	}
	return rs;
}

function initSelect(cobj, fselect){
  var j=1;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
  for(var i=0 ;i< cobj.length ;i++){
	fselect.options[j] = new Option(cobj[i].columnCnName,cobj[i].id); 
	j = j +1;
  }
}

function findColumnsByTable(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var ftable = Ext.getDom("foreignTable");
   var ftablekc = Ext.getDom("foreignTableKeyColumn");
   var ftablevc = Ext.getDom("foreignTableValueColumn");
   var ftablepc = Ext.getDom("foreignTableParentColumn");
   
   var ftableSelectedId = ftable.options[ftable.selectedIndex].value;
   
   Ext.Ajax.request({
      url: serverPath+"/infoAdmin/userMainTableColumn.do?methodCall=findColumnsByTable", 
      params:{
          ftableId: ftableSelectedId
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        
        var obj = result.data;
        initSelect(obj, ftablekc);
        initSelect(obj, ftablevc);
        initSelect(obj, ftablepc);
 		
  		
      }//end func
  });
}
//判断输入的字符串是中文（可包含其它字符）
function isChinese(s){
 // 正则表达式对象
 var re = new RegExp("[\\u4e00-\\u9fa5]", "");
 // 验证是否刚好匹配
 var yesorno = re.test(s);
 if(yesorno){
        return true;
 }
 else{
        return false;
 }
}

//判断输入的字符串是否是中文（只能使中文不能包含其它字符）
function isOnlyChinese(s){
 // 正则表达式对象
 var re = new RegExp("^[\\u4e00-\\u9fa5]+$", "");
 // 验证是否刚好匹配
 var yesorno = re.test(s);
 if(yesorno){
  return true;
 }
 else{
  return false;
 }
}


function saveColumn(){
  var xform = document.editForm;
  var propertyType = document.editForm.propertyType;
  var typeSelectIndex = propertyType[propertyType.selectedIndex].value;
  var typeSelectName = propertyType[propertyType.selectedIndex].text;
  var foreignTable = document.editForm.foreignTable;
  var foreignTableId = foreignTable[foreignTable.selectedIndex].value;
  var foreignTableKeyColumn = document.editForm.foreignTableKeyColumn;
  var foreignTableKeyColumnId = foreignTableKeyColumn[foreignTableKeyColumn.selectedIndex].value;
  var foreignTableValueColumn = document.editForm.foreignTableValueColumn;
  var foreignTableValueColumnId = foreignTableValueColumn[foreignTableValueColumn.selectedIndex].value;
  
  if(xform.columnName.value==""){
  	 alert("字段名称（英文）必须填写");
  	 xform.columnName.focus();
  	 return false;
  }else if(xform.columnCnName.value==""){
 	 alert("字段中文名称必须填写");
  	 xform.columnCnName.focus();
  	 return false;
  }else if(typeSelectIndex==""){
     alert("属性类型必须选择，且选择BaseObject后必须选择关联主表");
  	 xform.propertyType.focus();
  	 return false;
  }
  
  if(typeSelectName=="BaseObject"){
     if(foreignTableId==""){
       alert("属性类型为BaseObject时关联主表必须选择");
       foreignTable.focus();
       return false;
     }else if(foreignTableKeyColumnId==""){
       alert("属性类型为BaseObject时关联外键字段必须选择");
       foreignTableKeyColumn.focus();
       return false;
     }else if(foreignTableValueColumnId==""){
       alert("属性类型为BaseObject时关联显示字段必须选择");
       foreignTableValueColumn.focus();
       return false;
     }
  }
  if(!isChinese(xform.columnCnName.value)){
     alert("字段中文名称必须填写中文");
  	 xform.columnCnName.focus();
  	 return false;
  }
  
  xform.submit();
 
 
}

function findRefColumnsByTable(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var ftable = Ext.getDom("referencedTable");
   var ftablekc = Ext.getDom("referencedTableKeyColumn");
   var ftablevc = Ext.getDom("referencedTableValueColumn");
   var ftablepc = Ext.getDom("referencedTableParentColumn");
   
   var ftableSelectedId = ftable.options[ftable.selectedIndex].value;
   
   Ext.Ajax.request({
      url: serverPath+"/infoAdmin/userMainTableColumn.do?methodCall=findColumnsByTable", 
      params:{
          ftableId: ftableSelectedId
      },
      method:'POST',
      success:function(response){
        var data = response.responseText;
        var result = Ext.decode(data);
        
        var obj = result.data;
        initSelect(obj, ftablekc);
        initSelect(obj, ftablevc);
        initSelect(obj, ftablepc);
 		
  		
      }//end func
  });
}

function returnTableDetail(){
  window.location.href="${pageContext.request.contextPath}/infoAdmin/userMainTable.do?methodCall=toForm&id=${param.tableId}";
} 

</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>配置项类型及其对应主表管理</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    配置项类型主表名称：${smt.tableCnName }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/userMainTableColumn.do?methodCall=save" method=post  name="editForm" id="editForm">
字段属性
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

 
 <!--<tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">属性名称</td>
    <td width="40%" bgcolor="#EFF3FF">
  
    <input name="propertyName" id="propertyName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.propertyName}">
    (请填写实体的属性名称，而不是字段名称)
    <input name="id"  id="id4"   type="hidden" value="$"></td>
    <td width="10%" bgcolor="#EFF3FF">属性类型</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="propertyType" id="propertyType" style="width:100px;" class="textfield" >
      <option value="">-请选择-</option>
      <c:forEach var="item" items="${requestScope.propertyTypes}" varStatus="status">
      <option value="${item.id}" ${detail.propertyType.id eq item.id?' selected':''}>${item.propertyTypeCnName}</option>
     </c:forEach>
    </select>
    <input class=button type="button" value="快速保存字段修改" onclick="saveColumn();">
    </td>
  </tr>-->
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">字段名称</td>
    <td width="40%" bgcolor="#EFF3FF">
      <input name="id"  id="id"   type="hidden" value="${detail.id}">
     <input name="smtForAdd"  id="smtForAdd"   type="hidden" value="${param.mainTableId}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${detail.systemMainTable.id}">
     <input name="tableName"  id="tableName"   type="hidden" value="${detail.tableName}">
    <input name="isExtColumn"  id="isExtColumn"   type="hidden" value="0">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    (<font color=red>注意首字母必须小写，而且头2个字母不允许连续为大写，如IOS，或IPAddress请使用ios, ipAddress</font>)
    </td>
    <td width="10%" bgcolor="#EFF3FF">字段中文名称</td> 
    <td width="40%" bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
    (必须填写中文)		
    <input class=button type="button" value="快速保存字段修改" onclick="saveColumn();">
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">字段类型</td>
    <td bgcolor="#EFF3FF">
     <select name="propertyType" id="propertyType" style="width:110px;" class="textfield" >
      <option value="">-请选择-</option>
      <c:forEach var="item" items="${requestScope.propertyTypes}" varStatus="status">
      <option value="${item.id}" ${detail.propertyType.id eq item.id or empty detail.id and item.id eq 3 ?' selected':''}>${item.sqlColumnType}</option>
     </c:forEach>
    </select>
    对应JAVA类的属性类型
    </td>
    <td bgcolor="#EFF3FF">表单组件类型</td>
    <td bgcolor="#EFF3FF">
   <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:110px;" class="textfield">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
        <option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id or empty detail.id and item.id eq 1 ?' selected':''}>${item.columnTypeCnName}</option>
       </c:forEach>
         
    </select>(表单组件的类型)
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">字符长度</td>
    <td bgcolor="#EFF3FF">
    <input name="length" id="length"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.length}">
    选择了文本框或者文本域时，需要填写 
    </td>
    <td bgcolor="#EFF3FF">字段验证类型</td>
    <td bgcolor="#EFF3FF"> 
     <select name="validateType" id="validateType" style="width:110px;" class="textfield">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    (表单组件的验证方式)
    <%--字段对齐方式
    <select name="columnAlign" id="columnAlign" style="width:100px;" class="textfield">
      <option value="left"></option>
      <option value="left" ${detail.columnAlign eq 'left'?' selected':''}>left</option>
      <option value="center" ${detail.columnAlign eq 'center'?' selected':''}>center</option>
      <option value="right" ${detail.columnAlign eq 'right'?' selected':''}>right</option>
     </select>
  --%></td>
  </tr>
  <%--<tr>
    <td align="center" bgcolor="#EFF3FF">列表链接</td>
    <td colspan="3" bgcolor="#EFF3FF">
    <input name="columnLink" id="columnLink"  type="text" class="textfield" style="WIDTH:500px;" value="${detail.columnLink}"></td>
    </tr>
  --%><tr>
    <td align="center" bgcolor="#EFF3FF">描述信息</td>
    <td colspan="3" bgcolor="#EFF3FF"><textarea name="descn" class="textfield" cols="83" rows="3">${detail.descn}</textarea></td>
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="3" bgcolor="#EFF3FF">&nbsp;
    </td>
  </tr>
  <tr><td></td></tr>
  
</table>

<br>
字段关联字段<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">关联主表</td>
      <td width="40%" bgcolor="#EFF3FF"> 
      <select name="foreignTable" id="foreignTable" class="textfield" onchange="findColumnsByTable();">
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.sysMainTables}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTable.id eq item.id?' selected':'' }>${item.tableCnName}</option>
         </c:forEach>
      </select>
      </td>
      <td width="10%" bgcolor="#EFF3FF">关联外键字段</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="foreignTableKeyColumn" id="foreignTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-请选择关联主表-</option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
    </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">关联显示字段</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumn" id="foreignTableValueColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.foreignTableValueColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
	</td>
      <td bgcolor="#EFF3FF">关联显示字段排序</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumnOrder" id="foreignTableValueColumnOrder" style="width:100px;" class="textfield">
     	 <option value="1"></option>
          <option value="1" ${empty detail.foreignTableValueColumnOrder or detail.foreignTableValueColumnOrder eq 1?' selected':''}>升序</option>
          <option value="0" ${detail.foreignTableValueColumnOrder eq 0?' selected':''}>降序</option>
      </select>
      
       <input name="foreignTableParentColumn" id="foreignTableParentColumn" type="hidden" class="textfield" value="${detail.foreignTableParentColumn.id}">    
       
     </td>
    </tr>
    <!-- 
    <tr>
      <td align="center" bgcolor="#EFF3FF">关联表父字段</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableParentColumn" id="foreignTableParentColumn" style="width:100px;" class="textfield">
          <option value=""></option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.foreignTableParentColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      关联表涉及二级父子关系时的“父字段”</td>
      <td bgcolor="#EFF3FF" colspan="2"></td>
      <td bgcolor="#EFF3FF"><input name="length" id="length"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.lengthForPage}">
     </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="3" bgcolor="#EFF3FF">&nbsp; </td>
    </tr>
    <tr>
      <td></td>
    </tr> -->
 
</table>

<br>
复选组件关联表<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">关联主表</td>
      <td width="40%" bgcolor="#EFF3FF"> 
      <select name="referencedTable" id="referencedTable" class="textfield" onchange="findRefColumnsByTable();">
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.sysMainTables}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTable.id eq item.id?' selected':'' }>${item.tableCnName}</option>
         </c:forEach>
      </select>如UserRole
      </td>
      <td width="10%" bgcolor="#EFF3FF">关联外键字段</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="referencedTableKeyColumn" id="referencedTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-请选择关联主表-</option>
           <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>如UserRole.id
    </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">关联显示字段</td>
      <td bgcolor="#EFF3FF">
      <select name="referencedTableValueColumn" id="referencedTableValueColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.referencedTableValueColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>如UserRole.role_id
	</td>
      <td bgcolor="#EFF3FF">关联根对象字段</td>
      <td bgcolor="#EFF3FF">
      <select name="referencedTableParentColumn" id="referencedTableParentColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.referencedTableParentColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select> 如UserRole.user_id
      <input name="referencedTableValueColumnOrder" id="referencedTableValueColumnOrder" type="hidden" class="textfield" value="${detail.referencedTableValueColumnOrder}">    
     </td>
    </tr>
   <tr>
      <td align="center" bgcolor="#EFF3FF">显示列数</td>
      <td bgcolor="#EFF3FF">
	     <input name="columnSum" id="columnSum"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnSum}">
	    		
	  </td>
      <td bgcolor="#EFF3FF">是否独占整行</td>
      <td bgcolor="#EFF3FF">
      <select name="bigFlag" id="bigFlag" style="width:100px;" class="textfield">
          <option value="0" ${detail.bigFlag eq 0?' selected':''}>否</option>
          <option value="1" ${detail.bigFlag eq 1?' selected':''}>是</option>
      </select>是，则该字段在一行显示
      <input name="referencedTableValueColumnOrder" id="referencedTableValueColumnOrder" type="hidden" class="textfield" value="${detail.referencedTableValueColumnOrder}">    
     </td>
    </tr>
    
</table>
<br>

标识字段设置<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">是否标识字段</td>
      <td width="18%" bgcolor="#EFF3FF">
     	<select name="identityFlag" id="identityFlag" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.identityFlag eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.identityFlag eq 1?' selected':'' }>是</option>
     	</select>
      </td>
      <td width="9%" bgcolor="#EFF3FF">起始值</td>
      <td width="13%" bgcolor="#EFF3FF">
        <input name="seed" id="seed"  type="text" class="textfield"  value="${detail.seed}">
       </td>
      <td width="10%" bgcolor="#EFF3FF">递增量</td>
      <td width="40%" bgcolor="#EFF3FF">
		<input name="increment" id="increment"  type="text" style="width:40px;" class="textfield"  value="${detail.increment}">
		&nbsp;前缀
		<input name="identityMode" id="identityMode"  type="text" class="textfield"  value="${detail.identityMode}">
		如C01-表示生成的编号以C01-开头
     </td>
     
    </tr>
    
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="5" bgcolor="#EFF3FF">&nbsp; </td>
    </tr>
    <tr>
      <td></td>
    </tr>
    
</table>  

<br>
字段选项<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 
    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">是否是必输项</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail.id or detail.isMustInput eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>是</option>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">是否是IME项</td>
      <td width="40%" bgcolor="#EFF3FF">
     <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail.id or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>是</option>
      </select>
      
        <input name="isSearchItem" id="isSearchItem" type="hidden" class="textfield" value="${detail.isSearchItem}">    
      <input name="isOutputItem" id="isOutputItem" type="hidden" class="textfield" value="${detail.isOutputItem}">  
      <input name="isHiddenItem" id="isHiddenItem" type="hidden" class="textfield" value="${detail.isHiddenItem}">   
      
      </td>
    </tr>
    
    
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="3" bgcolor="#EFF3FF">&nbsp; </td>
    </tr>
    <tr>
      <td></td>
    </tr>

</table>
</form> 	

<input class=button type="button" value="保存字段修改" onclick="saveColumn();">&nbsp;
<input class=button type="button" value="返回" onclick="returnTableDetail();">
<BR>
</body>
</html>
