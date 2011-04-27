<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>系统主表字段设置</title>
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
      url: serverPath+"/infoAdmin/sysMainTableColumn.do?methodCall=findColumnsByTable", 
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
  var systemMainTableColumnType = document.editForm.systemMainTableColumnType;
  var systemMainTableColumnType = systemMainTableColumnType[systemMainTableColumnType.selectedIndex].value;
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
  }
  if(!isChinese(xform.columnCnName.value)){
     alert("字段中文名称必须填写中文");
  	 xform.columnCnName.focus();
  	 return false;
  }
  
  xform.submit();
 
 
}
function returnTableDetail(){
  window.location.href="${pageContext.request.contextPath}/infoAdmin/ServiceItemTypeAction.do?methodCall=add&id=${serviceItemType.id}";
} 

</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>服务项数据字段管理：查看、修改字段信息</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    服务项类型名：${serviceItemType.name }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/SCIColumnAction.do?methodCall=save" method=post  name="editForm" id="editForm">

字段属性
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
    <td width="10%"  align="center" bgcolor="#EFF3FF">字段名称</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="serviceItemType" id="serviceItemType"  type="hidden" value="${serviceItemType.id}">
    <input name="id" id="id"  type="hidden" value="${detail.id}">
    <input name="order" id="order"  type="hidden" value="${detail.order}">
    <input name="isExtColumn"  id="isExtColumn"   type="hidden" value="0">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    
    </td>
    <td width="10%" bgcolor="#EFF3FF">字段中文名称</td>
    <td bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
   
	  </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">字段类型</td>
    <td bgcolor="#EFF3FF">
    <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:100px;" class="textfield">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
        <option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id?' selected':''}>${item.columnTypeCnName}</option>
       </c:forEach>
         
    </select>
    
    </td>
    <td bgcolor="#EFF3FF">字段验证类型</td>
    <td bgcolor="#EFF3FF">
    <select name="validateType" id="validateType" style="width:100px;" class="textfield">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    
    </td>
  </tr> <tr><td></td></tr>

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
      </td>
    </tr>
     <tr><td></td></tr>
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
      <td bgcolor="#EFF3FF">是否为虚拟字段</td>
      <td bgcolor="#EFF3FF"><select name="isNullForeignColumn" id="isNullForeignColumn" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.isNullForeignColumn eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isNullForeignColumn eq 1?' selected':'' }>是</option>
      </select>
     </td>
    </tr>
    
     <tr>
      <td align="center" bgcolor="#EFF3FF">上传文件路径：</td>
      <td bgcolor="#EFF3FF">
      <input name="uploadUrl" id="uploadUrl"  type="text" class="textfield" style="WIDTH:180px;" value="${detail.uploadUrl}">
      逻辑文件名前缀
      <input name="fileNamePrefix" id="fileNamePrefix"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.fileNamePrefix}">
      
      </td>
      <td bgcolor="#EFF3FF">逻辑文件名字段</td>
      <td bgcolor="#EFF3FF">
      
      <select name="fileNameColumn" id="fileNameColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.fileNameColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      系统文件名字段
      <select name="systemFileNameColumn" id="systemFileNameColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.systemFileNameColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
     </td>
    
    </tr>
     -->
   
 
</table>
<!-- 
<br>
区分字段设置<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">引用区分字段</td>
      <td width="18%" bgcolor="#EFF3FF">
     	<select name="abstractFlag" id="abstractFlag" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.abstractFlag eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.abstractFlag eq 1?' selected':'' }>是</option>
     	</select>
      </td>
      <td width="9%" bgcolor="#EFF3FF">引用的区分字段</td>
      <td width="13%" bgcolor="#EFF3FF">
      	<select name="discColumn" id="discColumn" style="width:100px;" class="textfield">
        <option value=""></option>
        <c:forEach var="item" items="${requestScope.columns}" varStatus="status">
        <option value="${item.id}"  ${detail.discColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
        </c:forEach>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">区分字段引用表</td>
      <td width="40%" bgcolor="#EFF3FF">
		<select name="foreignDiscTable" id="foreignDiscTable" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.sysMainTables}" varStatus="status">
           <option value="${item.id}"  ${detail.foreignDiscTable.id eq item.id?' selected':''}>${item.tableCnName}</option>
         </c:forEach>
      </select>
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
   -->    
<br>
字段选项<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 
    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">是否是必输项</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isMustInput eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>是</option>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">是否是隐藏项</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isHiddenItem" id="isHiddenItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isHiddenItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isHiddenItem eq 1?' selected':'' }>是</option>
      </select>
     
      </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">是否可修改项</td>
      <td bgcolor="#EFF3FF">
      <select name="isUpdateItem" id="isUpdateItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isUpdateItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isUpdateItem eq 1?' selected':'' }>是</option>
      </select>
  
      </td>
      <td bgcolor="#EFF3FF">是否是IME项</td>
      <td bgcolor="#EFF3FF">
      <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>是</option>
      </select>
      
      </td>
    </tr>
    <!-- 
    <tr>
      <td align="center" bgcolor="#EFF3FF">是否是查询字项</td>
      <td bgcolor="#EFF3FF">
      <select name="isSearchItem" id="isSearchItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isSearchItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isSearchItem eq 1?' selected':'' }>是</option>
      </select>
      (如需要在列表页面显示此字段作为查询条件，请选择是)
      </td>
      <td bgcolor="#EFF3FF">是否是导出项</td>
      <td bgcolor="#EFF3FF">
      <select name="isOutputItem" id="isOutputItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isOutputItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isOutputItem eq 1?' selected':'' }>是</option>
      </select>
      (如需要在列表页面可以导出此字段的数据，请选择是)
      </td>
    </tr>
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="3" bgcolor="#EFF3FF">&nbsp; </td>
    </tr>
    <tr>
      <td></td>
    </tr>
   --> <tr><td></td></tr>
</table>

</form> 	

<input class=button type="button" value="保存字段修改" onclick="saveColumn();">&nbsp;
<input class=button type="button" value="返回" onclick="returnTableDetail();">
<BR>
</body>
</html>
