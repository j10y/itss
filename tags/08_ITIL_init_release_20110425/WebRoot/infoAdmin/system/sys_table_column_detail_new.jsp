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


function findRefColumnsByTable(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var ftable = Ext.getDom("referencedTable");
   var ftablekc = Ext.getDom("referencedTableKeyColumn");
   var ftablevc = Ext.getDom("referencedTableValueColumn");
   var ftablepc = Ext.getDom("referencedTableParentColumn");
   
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
  var propertyType = document.editForm.propertyType;
  var typeSelectIndex = propertyType[propertyType.selectedIndex].value;
  var typeSelectName = propertyType[propertyType.selectedIndex].text;
  var foreignTable = document.editForm.foreignTable;
  var foreignTableId = foreignTable[foreignTable.selectedIndex].value;
  var foreignTableKeyColumn = document.editForm.foreignTableKeyColumn;
  var foreignTableKeyColumnId = foreignTableKeyColumn[foreignTableKeyColumn.selectedIndex].value;
  var foreignTableValueColumn = document.editForm.foreignTableValueColumn;
  var foreignTableValueColumnId = foreignTableValueColumn[foreignTableValueColumn.selectedIndex].value;
  
  if(xform.propertyName.value==""){
  	 alert("属性名称必须填写");
  	 xform.propertyName.focus();
  	 return false;
  }else if(xform.columnName.value==""){
  	 alert("字段名称（英文）必须填写");
  	 xform.columnName.focus();
  	 return false;
  }else if(xform.columnCnName.value==""){
 	 alert("字段中文名称必须填写");
  	 xform.columnCnName.focus();
  	 return false;
  }else if(typeSelectIndex==""&&xform.isExtColumn.value==0){
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
function returnTableDetail(){
  window.location.href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toForm&id=${param.tableId}";
} 

function disColumn(){
var f=document.forms[0];
	var isec=document.forms[0].elements("isExtColumn").value;
	if(isec==0){
		//f.elements("propertyName").disabled=false;
		f.elements("propertyType").disabled=false;
		f.elements("foreignTableParentColumn").disabled=false;
		f.elements("isNullForeignColumn").disabled=false;
		f.elements("uploadUrl").disabled=false;
		f.elements("fileNamePrefix").disabled=false;
		f.elements("fileNameColumn").disabled=false;
		f.elements("systemFileNameColumn").disabled=false;
		f.elements("referencedTable").disabled=false;
		f.elements("referencedTableKeyColumn").disabled=false;
		f.elements("referencedTableValueColumn").disabled=false;
		f.elements("referencedTableParentColumn").disabled=false;
		f.elements("abstractFlag").disabled=false;
		f.elements("discColumn").disabled=false;
		f.elements("foreignDiscTable").disabled=false;
		f.elements("identityFlag").disabled=false;
		f.elements("seed").disabled=false;
		f.elements("increment").disabled=false;
		f.elements("identityMode").disabled=false;
	}else if(isec==1){
		//f.elements("propertyName").value="";
		f.elements("propertyType").value="";
		
		f.elements("foreignTableParentColumn").value="";
		f.elements("isNullForeignColumn").value="";
		f.elements("uploadUrl").value="";
		f.elements("fileNamePrefix").value="";
		f.elements("fileNameColumn").value="";
		f.elements("systemFileNameColumn").value="";
		f.elements("referencedTable").value="";
		f.elements("referencedTableKeyColumn").value="";
		f.elements("referencedTableValueColumn").value="";
		f.elements("referencedTableParentColumn").value="";
		f.elements("abstractFlag").value="";
		f.elements("discColumn").value="";
		f.elements("foreignDiscTable").value="";
		f.elements("identityFlag").value="";
		f.elements("seed").value="";
		f.elements("increment").value="";
		f.elements("identityMode").value="";
		
		//f.elements("propertyName").disabled=true;
		f.elements("propertyType").disabled=true;
		
		f.elements("foreignTableParentColumn").disabled=true;
		f.elements("isNullForeignColumn").disabled=true;
		f.elements("uploadUrl").disabled=true;
		f.elements("fileNamePrefix").disabled=true;
		f.elements("fileNameColumn").disabled=true;
		f.elements("systemFileNameColumn").disabled=true;
		f.elements("referencedTable").disabled=true;
		f.elements("referencedTableKeyColumn").disabled=true;
		f.elements("referencedTableValueColumn").disabled=true;
		f.elements("referencedTableParentColumn").disabled=true;
		f.elements("abstractFlag").disabled=true;
		f.elements("discColumn").disabled=true;
		f.elements("foreignDiscTable").disabled=true;
		f.elements("identityFlag").disabled=true;
		f.elements("seed").disabled=true;
		f.elements("increment").disabled=true;
		f.elements("identityMode").disabled=true;
	}
}

function display(){ 
	    var sysExtTables = document.getElementById("systemMainTableColumnType"); 
        var mtable=Ext.getDom("foreignTable");
		var ftablekc = Ext.getDom("foreignTableKeyColumn");
        var ftablevc = Ext.getDom("foreignTableValueColumn");
		var extSelectType = document.getElementById("extSelectType");	
		
	    var selectIndex = sysExtTables.selectedIndex; 
	    var isec=document.forms[0].elements("isExtColumn").value;
	    if(isec==1){
		    if(selectIndex==3){	//下拉列表		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==4){	//单选框		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==5){	//多选框		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==10){	//复选列表	
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==12){	//Ext列表
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==15){	//Ext列表
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else {
		    	if(extSelectType.style.display=="block"){
		      		extSelectType.style.display="none";	      		
		   	 	}
		    }

	    }else {
		    	if(extSelectType.style.display=="block"){
		      		extSelectType.style.display="none";	      		
		   	 	}
		    }
	      
	}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>系统主表字段管理：查看、修改主表字段信息</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    系统主表名称：${smt.tableCnName }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=save" method=post  name="editForm" id="editForm">
字段属性
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

 <tr> 
    <td align="center" bgcolor="#EFF3FF">是否是主表</td>
    <td colspan="3" bgcolor="#EFF3FF">
	<select name="isExtColumn" id="isExtColumn" style="width:100px;" class="textfield" onchange="disColumn()">
      <option value="">-请选择-</option>
      <option value="0" ${detail.isExtColumn eq 0?' selected':''}>主表字段</option>
      <option value="1" ${detail.isExtColumn eq 1?' selected':''}>扩展表字段</option>
    </select>
	</td>
  </tr>
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">属性名称</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${detail.id}">
     <input name="tableName"  id="tableName"   type="hidden" value="${detail.tableName}">
     <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${detail.systemMainTable.id}">
     <input name="smtForAdd"  id="smtForAdd"   type="hidden" value="${param.tableId}">
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
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">字段名称</td>
    <td bgcolor="#EFF3FF">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    (请填写数据表中的字段名称，而不是属性名称)
    </td>
    <td bgcolor="#EFF3FF">字段中文名称</td>
    <td bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
    (必须填写中文)		
	  </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">字段类型</td>
    <td bgcolor="#EFF3FF">
    <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:100px;" class="textfield" onChange="display()">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
        <option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id?' selected':''}>${item.columnTypeCnName}</option>
       </c:forEach>
         
    </select>
     <select id="extSelectType" name="extSelectType" style="display:
     <c:choose>
     <c:when test="${detail.systemMainTableColumnType.id==3 and detail.isExtColumn==1}">block</c:when>
     <c:when test="${detail.systemMainTableColumnType.id==4 and detail.isExtColumn==1}">block</c:when>
     <c:when test="${detail.systemMainTableColumnType.id==5 and detail.isExtColumn==1}">block</c:when>
     <c:when test="${detail.systemMainTableColumnType.id==12 and detail.isExtColumn==1}">block</c:when>
     <c:when test="${detail.systemMainTableColumnType.id==15 and detail.isExtColumn==1}">block</c:when>
     <c:when test="${detail.systemMainTableColumnType.id==10 and detail.isExtColumn==1}">block</c:when>
     <c:otherwise>none</c:otherwise></c:choose>;" >
						    <option value="">请选择</option>
						    <option value="0" ${detail.extSelectType == 0?' selected':''}> 源于主表</option>
						    <!-- <option value="1">源于扩展表</option>  -->
							<option value="2" ${detail.extSelectType == 2?' selected':''}>自定义列表</option>
	  </select>
    (表单组件的类型，对于主键请选择隐藏域，对于外键关联如不显示可选择隐藏域，否则选择下拉列表)
    </td>
    <td bgcolor="#EFF3FF">字段验证类型</td>
    <td bgcolor="#EFF3FF">
    <select name="validateType" id="validateType" style="width:100px;" class="textfield">
       <option value="">-请选择-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    (表单组件的验证方式，如货币类型不可以输入中文标点)
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">唯一标识</td>
    <td bgcolor="#EFF3FF">
      <select name="uniqueFlag" id="uniqueFlag" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.uniqueFlag eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.uniqueFlag eq 1?' selected':'' }>是</option>
      </select>
    </td>
    <td bgcolor="#EFF3FF">是否独占整行</td> 
    <td bgcolor="#EFF3FF">
      <select name="bigFlag" id="bigFlag" style="width:100px;" class="textfield">
          <option value="0" ${detail.bigFlag eq 0?' selected':''}>否</option>
          <option value="1" ${detail.bigFlag eq 1?' selected':''}>是</option>
      </select>
      <!-- 
    <select name="columnAlign" id="columnAlign" style="width:100px;" class="textfield">
      <option value="left"></option>
      <option value="left" ${detail.columnAlign eq 'left'?' selected':''}>left</option>
      <option value="center" ${detail.columnAlign eq 'center'?' selected':''}>center</option>
      <option value="right" ${detail.columnAlign eq 'right'?' selected':''}>right</option>
     </select>-->
  </td>
  </tr>
  
  <tr>
    <td align="center" bgcolor="#EFF3FF">组件宽度</td>
    <td bgcolor="#EFF3FF">
      <input name="lengthForPage" id="lengthForPage"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.lengthForPage}">px
    </td>
    <td bgcolor="#EFF3FF">组件高度</td> 
    <td bgcolor="#EFF3FF">
      <input name="heightForPage" id="heightForPage"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.heightForPage}">px;
  </td>
  </tr>
  <tr>
      <td align="center" bgcolor="#EFF3FF">显示列数</td>
      <td bgcolor="#EFF3FF">
	     <input name="columnSum" id="columnSum"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnSum}">
	    		
	  </td>
      <td bgcolor="#EFF3FF"></td>
      <td bgcolor="#EFF3FF">
     </td>
    </tr>
  <!-- 
  <tr>
    <td align="center" bgcolor="#EFF3FF">列表链接</td>
    <td colspan="3" bgcolor="#EFF3FF">
    <input name="columnLink" id="columnLink"  type="hidden" class="textfield" style="WIDTH:500px;" value="${detail.columnLink}"></td>
    </tr>-->
  <tr> 
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
      当前属性关联的主表</td>
      <td width="10%" bgcolor="#EFF3FF">关联外键字段</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="foreignTableKeyColumn" id="foreignTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-请选择关联主表-</option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      注意选择关联表的主键字段，系统将默认选中</td>
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
	注意选择关联表的“名字”字段</td>
      <td bgcolor="#EFF3FF">关联显示字段排序</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumnOrder" id="foreignTableValueColumnOrder" style="width:100px;" class="textfield">
     	 <option value="1"></option>
          <option value="1" ${empty detail.foreignTableValueColumnOrder or detail.foreignTableValueColumnOrder eq 1?' selected':''}>升序</option>
          <option value="0" ${detail.foreignTableValueColumnOrder eq 0?' selected':''}>降序</option>
      </select>
      关联数据列表排序方式，如按ITCODE升序显示用户</td>
    </tr>
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
      </select>
      </td>
      <td width="10%" bgcolor="#EFF3FF">关联外键字段</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="referencedTableKeyColumn" id="referencedTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-请选择关联主表-</option>
           <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
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
      </select>
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
    
</table>
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
      <td width="10%" align="center" bgcolor="#EFF3FF">是否查询字项</td>
      <td width="40%" bgcolor="#EFF3FF">
	      <select name="isSearchItem" id="isSearchItem" style="width:100px;" class="textfield">
	          <option value=""></option>
	          <option value="0" ${empty detail or detail.isSearchItem eq 0?' selected':'' }>否</option>
	          <option value="1" ${detail.isSearchItem eq 1?' selected':'' }>是</option>
	      </select>
	      (如需要在列表页面显示此字段作为查询条件，请选择是)
     </td>
      <td width="10%" bgcolor="#EFF3FF">是否是必输项</td>
      <td width="40%" bgcolor="#EFF3FF">
	      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
	          <option value=""></option>
	          <option value="0" ${empty detail or detail.isMustInput eq 0?' selected':'' }>否</option>
	          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>是</option>
	      </select>
      
      <input name="isHiddenItem" id="isHiddenItem"  type="hidden" style="width:40px;" class="textfield"  value="${detail.isHiddenItem}">
      
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
      (不可修改，即只读字段)
      </td>
      <td bgcolor="#EFF3FF">是否是IME项</td>
      <td bgcolor="#EFF3FF">
      <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>是</option>
      </select>
      (限制数字类型组件不可以输入中文标点等符号)
      </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">是否是导出项</td>
      <td bgcolor="#EFF3FF">
        <select name="isOutputItem" id="isOutputItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isOutputItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isOutputItem eq 1?' selected':'' }>是</option>
        </select>
      (如需要在列表页面可以导出此字段的数据，请选择是)
      </td>
      <td bgcolor="#EFF3FF"></td>
      <td bgcolor="#EFF3FF">
     
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
