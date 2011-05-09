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

function initSelectNull(fselect){
  var j=1;
  for(var i=fselect.options.length-1;i>=0;--i){ 
	fselect.options[i]=null;  
  }
}

function findColumnsByTable(){ 
   var serverPath = "${pageContext.request.contextPath}";
   var ftable = Ext.getDom("foreignTable");
   var ftablekc = Ext.getDom("foreignTableKeyColumn");
   var ftablevc = Ext.getDom("foreignTableValueColumn");
   
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
 		
  		
      }//end func
  });
}

function saveColumn(){
  var xform = document.editForm;
  xform.submit();
}
function returnTableDetail(){
  window.location.href="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=toForm&id=${param.tableId}";
} 
function display(){ 
	    var sysExtTables = document.getElementById("sysExtTables"); 
        var mtable=Ext.getDom("foreignTable");
		var ftablekc = Ext.getDom("foreignTableKeyColumn");
        var ftablevc = Ext.getDom("foreignTableValueColumn");
		var extSelectType = document.getElementById("extSelectType");	
		
	    var selectIndex = sysExtTables.selectedIndex; 
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
	    } else {
	    	if(extSelectType.style.display=="block"){
	      		extSelectType.style.display="none";	      		
	   	 	}
			
	   	 	  initSelectNull(ftablevc);
	   		  initSelectNull(mtable);
	   		  initSelectNull(ftablekc);
	    }
	    
	    if(sysExtTables.options[sysExtTables.selectedIndex].text=="自定义下拉列表"){
	    	//openSubWindow("index.jsp","dd");
	    }    
	}
	function displaySysMainTable(){ 
		var serverPath = "${pageContext.request.contextPath}";
	    var extSelectType = document.getElementById("extSelectType"); 
		var mtable=Ext.getDom("foreignTable");
		var ftablekc = Ext.getDom("foreignTableKeyColumn");
        var ftablevc = Ext.getDom("foreignTableValueColumn");
	    var selectIndex = extSelectType.selectedIndex; 
	    if(selectIndex==1){	//源于主表	
	    alert("源于主表");
	   	 	 Ext.Ajax.request({
			      url: serverPath+"/infoAdmin/sysExtTableColumn.do?methodCall=findSysMainTableByTable", 
			      method:'POST',
			      success:function(response){
			        var data = response.responseText;
			        var result = Ext.decode(data);
			        var obj = result.data;
			        initSelect(obj, mtable);	
			      }//end func
			  });
	   		 } else{
	   		  initSelectNull(ftablevc);
	   		  initSelectNull(mtable);
	   		  initSelectNull(ftablekc);
	   		  
	   		 } 
	    }  
	function eaddOnePurview() 
	{ 
		//获得select 
		var select1=document.editForm.extOptionValue; 
		//获得文本框的值 
		var text=editForm.addValue.value; 
		//创建option 
		var option = new Option(text,text); 
		
		//添加值 
		select1.options.add(option); 
		editForm.addValue.value='';
	}
	
	function delOnePurview() 
	{ 	var select1=document.editForm.extOptionValue;
		var select2=document.getElementById("extOptionValue");
		 var selectIndex = select2.selectedIndex;
		select1.options[selectIndex]=null;
	}
	function selectAll(){
	 var List1 = document.editForm.extOptionValue;//所有extOptionValue的select
	 alert(List1.length);
	  if (List1.length && List1.options[0].value == 'temp') return;
	  for (j=0;j<List1.length;j++)
	  {		
	  	alert(List1.options[j].value);
	     List1.options[j].selected = true;
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
      border=0>&nbsp;<STRONG>系统主表扩展字段管理：查看、修改主表字段信息</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    系统主表名称：${smt.tableCnName }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=saveExtendColumn" method=post  name="editForm" id="editForm" 
 onSubmit="selectAll();return checkForm('editForm');"
 >
字段属性
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

 <!-- 
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">属性名称</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${detail.id}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${detail.systemMainTable.id}">
     <input name="tableName"  id="tableName"   type="hidden" value="${detail.tableName}">
    <input name="propertyName" id="propertyName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.propertyName}">
    <input name="id"  id="id4"   type="hidden" value="$"></td>
    <td width="10%" bgcolor="#EFF3FF">属性类型</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="propertyType" id="propertyType" style="width:100px;" class="textfield">
      <option value="">-请选择-</option>
      <c:forEach var="item" items="${requestScope.propertyTypes}" varStatus="status">
      <option value="${item.id}" ${detail.propertyType.id eq item.id?' selected':''}>${item.propertyTypeCnName}</option>
     </c:forEach>
    </select>
    </td>
  </tr>
   -->
    
  <tr>
    <input name="id"  id="id"   type="hidden" value="${id}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${tableId}">
    <input name="extendTableColumnNum"  id="extendTableColumnNum"   type="hidden" value="${detail.extendTableColumnNum}">
    <td align="center" bgcolor="#EFF3FF">字段英文名称</td>
    <td bgcolor="#EFF3FF"><input name="extTableColumnName" id="extTableColumnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.extTableColumnName}"></td>
    <td bgcolor="#EFF3FF">字段中文名称</td>
    <td bgcolor="#EFF3FF"><input name="extTableColumnDispName" id="extTableColumnDispName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.extTableColumnDispName}"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">扩展字段类型</td>
    <td bgcolor="#EFF3FF">

      <select name="systemMainTableColumnType" id="sysExtTables"  onChange="display()" >
						    <option value=""></option>
							 <c:forEach  var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
							 	<option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id?' selected':''}>${item.columnTypeCnName}</option>
							 </c:forEach>
	   </select>
	  <select id="extSelectType" name="extSelectType" style="display:${detail.systemMainTableColumnType.id==3 ?' block':'none'};" onChange="displaySysMainTable()">
						    <option value="">请选择</option>
						    <option value="0" ${detail.extSelectType == 0?' selected':''}> 源于主表</option>
						    <!-- <option value="1">源于扩展表</option>  -->
							<option value="2" ${detail.extSelectType == 2?' selected':''}>自定义列表</option>
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
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">页面长度</td>
    <td bgcolor="#EFF3FF">
    <input name="lengthForPage" id="lengthForPage"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.lengthForPage}">px;</td>
    <td bgcolor="#EFF3FF">字段对齐方式</td>
    <td bgcolor="#EFF3FF">
    <select name="columnAlign" id="columnAlign" style="width:100px;" class="textfield">
      <option value="left"></option>
      <option value="left" ${detail.columnAlign eq 'left'?' selected':''}>left</option>
      <option value="center" ${detail.columnAlign eq 'center'?' selected':''}>center</option>
      <option value="right" ${detail.columnAlign eq 'right'?' selected':''}>right</option>
     </select>
  </td>
  </tr>
  <tr>
    <td bgcolor="#EFF3FF">是否独占整行</td> 
    <td bgcolor="#EFF3FF">
      <select name="bigFlag" id="bigFlag" style="width:100px;" class="textfield">
          <option value="0" ${detail.bigFlag eq 0?' selected':''}>否</option>
          <option value="1" ${detail.bigFlag eq 1?' selected':''}>是</option>
      </select>
  </td>
  <td align="center" bgcolor="#EFF3FF">组件显示列数</td>
  <td bgcolor="#EFF3FF">
     <input name="columnSum" id="columnSum"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnSum}">
    		
  </td>
  </tr>
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
<!-- 
<c:if test="${detail.extSelectType == 2 or empty detail.id }">
自定义列表字段<br />
<table  width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td  width="7%"bgcolor="#EFF3FF">
      输入字段名
      <input size="10"type="text" name="addValue">
      </td>
      <td width="5%" bgcolor="#EFF3FF"> 
      <input name="addselect" type="button" value="添加" onclick="eaddOnePurview()"> 
      <input name="delselect" type="button" value="删除" onclick="delOnePurview()"> 
      </td>
      <td bgcolor="#EFF3FF"> 
      <select style="width:150px" name="extOptionValue" id="extOptionValue" multiple size=2 value="">
      <c:forEach var="item" items="${extOptions}">
      <option  value="item.extOptionValue"> ${item.extOptionValue }</option>
      </c:forEach>
      </select>
      </td> 
    </tr>
    <tr>
      <td></td>
    </tr>
</table>
<br/>
</c:if>
 -->
字段关联字段<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">关联主表</td>
      <td width="40%" bgcolor="#EFF3FF"> 
      <select name="foreignTable" id="foreignTable" style="width:100px;" class="textfield" onchange="findColumnsByTable();">
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
      <select name="order" id=order style="width:100px;" class="textfield">
     	 <option value="1"></option>
          <option value="1" ${empty detail.order or detail.order eq 1?' selected':''}>升序</option>
          <option value="0" ${detail.order eq 0?' selected':''}>降序</option>
      </select>
      关联数据列表排序方式，如按ITCODE升序显示用户</td>
    </tr>
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="3" bgcolor="#EFF3FF">&nbsp; </td>
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
          <option value="0" ${empty detail or detail.isMustInput eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>是</option>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">是否是隐藏项</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isHiddenItem" id="isHiddenItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isHiddenItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isHiddenItem eq 1?' selected':'' }>是</option>
      </select></td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">是否可修改项</td>
      <td bgcolor="#EFF3FF">
      <select name="isUpdateItem" id="isUpdateItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isUpdateItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isUpdateItem eq 1?' selected':'' }>是</option>
      </select></td>
      <td bgcolor="#EFF3FF">是否是IME项</td>
      <td bgcolor="#EFF3FF">
      <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>是</option>
      </select></td>
    </tr>
    <tr>
     <td align="center" bgcolor="#EFF3FF">是否是查询字项</td>
      <td bgcolor="#EFF3FF">
      <select name="isSearchItem" id="isSearchItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isSearchItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isSearchItem eq 1?' selected':'' }>是</option>
      </select>
      </td>
      
      <td align="center" bgcolor="#EFF3FF">是否是导出项</td>
      <td bgcolor="#EFF3FF">
      <select name="isOutputItem" id="isOutputItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isOutputItem eq 0?' selected':'' }>否</option>
          <option value="1" ${detail.isOutputItem eq 1?' selected':'' }>是</option>
      </select></td>
      <!-- 
      <td align="center" bgcolor="#EFF3FF"></td>
      <td bgcolor="#EFF3FF"></td>
       -->
    </tr>
    <tr>
      <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
      <td colspan="3" bgcolor="#EFF3FF">&nbsp; </td>
    </tr>
    <tr>
      <td></td>
    </tr>

</table>
<br/>
<input class=button type="submit" value="保存字段">&nbsp;
<input class=button type="button" value="返回" onclick="returnTableDetail();">
</form> 	
<BR>
</body>
</html>
