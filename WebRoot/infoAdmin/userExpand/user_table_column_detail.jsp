<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<title>�û������ֶ�����</title>
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
//�ж�������ַ��������ģ��ɰ��������ַ���
function isChinese(s){
 // ������ʽ����
 var re = new RegExp("[\\u4e00-\\u9fa5]", "");
 // ��֤�Ƿ�պ�ƥ��
 var yesorno = re.test(s);
 if(yesorno){
        return true;
 }
 else{
        return false;
 }
}

//�ж�������ַ����Ƿ������ģ�ֻ��ʹ���Ĳ��ܰ��������ַ���
function isOnlyChinese(s){
 // ������ʽ����
 var re = new RegExp("^[\\u4e00-\\u9fa5]+$", "");
 // ��֤�Ƿ�պ�ƥ��
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
  	 alert("�ֶ����ƣ�Ӣ�ģ�������д");
  	 xform.columnName.focus();
  	 return false;
  }else if(xform.columnCnName.value==""){
 	 alert("�ֶ��������Ʊ�����д");
  	 xform.columnCnName.focus();
  	 return false;
  }else if(typeSelectIndex==""){
     alert("�������ͱ���ѡ����ѡ��BaseObject�����ѡ���������");
  	 xform.propertyType.focus();
  	 return false;
  }
  
  if(typeSelectName=="BaseObject"){
     if(foreignTableId==""){
       alert("��������ΪBaseObjectʱ�����������ѡ��");
       foreignTable.focus();
       return false;
     }else if(foreignTableKeyColumnId==""){
       alert("��������ΪBaseObjectʱ��������ֶα���ѡ��");
       foreignTableKeyColumn.focus();
       return false;
     }else if(foreignTableValueColumnId==""){
       alert("��������ΪBaseObjectʱ������ʾ�ֶα���ѡ��");
       foreignTableValueColumn.focus();
       return false;
     }
  }
  if(!isChinese(xform.columnCnName.value)){
     alert("�ֶ��������Ʊ�����д����");
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
      border=0>&nbsp;<STRONG>���������ͼ����Ӧ�������</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    �����������������ƣ�${smt.tableCnName }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/userMainTableColumn.do?methodCall=save" method=post  name="editForm" id="editForm">
�ֶ�����
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

 
 <!--<tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">��������</td>
    <td width="40%" bgcolor="#EFF3FF">
  
    <input name="propertyName" id="propertyName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.propertyName}">
    (����дʵ����������ƣ��������ֶ�����)
    <input name="id"  id="id4"   type="hidden" value="$"></td>
    <td width="10%" bgcolor="#EFF3FF">��������</td>
    <td width="40%" bgcolor="#EFF3FF">
     <select name="propertyType" id="propertyType" style="width:100px;" class="textfield" >
      <option value="">-��ѡ��-</option>
      <c:forEach var="item" items="${requestScope.propertyTypes}" varStatus="status">
      <option value="${item.id}" ${detail.propertyType.id eq item.id?' selected':''}>${item.propertyTypeCnName}</option>
     </c:forEach>
    </select>
    <input class=button type="button" value="���ٱ����ֶ��޸�" onclick="saveColumn();">
    </td>
  </tr>-->
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td width="40%" bgcolor="#EFF3FF">
      <input name="id"  id="id"   type="hidden" value="${detail.id}">
     <input name="smtForAdd"  id="smtForAdd"   type="hidden" value="${param.mainTableId}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${detail.systemMainTable.id}">
     <input name="tableName"  id="tableName"   type="hidden" value="${detail.tableName}">
    <input name="isExtColumn"  id="isExtColumn"   type="hidden" value="0">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    (<font color=red>ע������ĸ����Сд������ͷ2����ĸ����������Ϊ��д����IOS����IPAddress��ʹ��ios, ipAddress</font>)
    </td>
    <td width="10%" bgcolor="#EFF3FF">�ֶ���������</td> 
    <td width="40%" bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
    (������д����)		
    <input class=button type="button" value="���ٱ����ֶ��޸�" onclick="saveColumn();">
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td bgcolor="#EFF3FF">
     <select name="propertyType" id="propertyType" style="width:110px;" class="textfield" >
      <option value="">-��ѡ��-</option>
      <c:forEach var="item" items="${requestScope.propertyTypes}" varStatus="status">
      <option value="${item.id}" ${detail.propertyType.id eq item.id or empty detail.id and item.id eq 3 ?' selected':''}>${item.sqlColumnType}</option>
     </c:forEach>
    </select>
    ��ӦJAVA�����������
    </td>
    <td bgcolor="#EFF3FF">���������</td>
    <td bgcolor="#EFF3FF">
   <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:110px;" class="textfield">
       <option value="">-��ѡ��-</option>
       <c:forEach var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
        <option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id or empty detail.id and item.id eq 1 ?' selected':''}>${item.columnTypeCnName}</option>
       </c:forEach>
         
    </select>(�����������)
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">�ַ�����</td>
    <td bgcolor="#EFF3FF">
    <input name="length" id="length"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.length}">
    ѡ�����ı�������ı���ʱ����Ҫ��д 
    </td>
    <td bgcolor="#EFF3FF">�ֶ���֤����</td>
    <td bgcolor="#EFF3FF"> 
     <select name="validateType" id="validateType" style="width:110px;" class="textfield">
       <option value="">-��ѡ��-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    (���������֤��ʽ)
    <%--�ֶζ��뷽ʽ
    <select name="columnAlign" id="columnAlign" style="width:100px;" class="textfield">
      <option value="left"></option>
      <option value="left" ${detail.columnAlign eq 'left'?' selected':''}>left</option>
      <option value="center" ${detail.columnAlign eq 'center'?' selected':''}>center</option>
      <option value="right" ${detail.columnAlign eq 'right'?' selected':''}>right</option>
     </select>
  --%></td>
  </tr>
  <%--<tr>
    <td align="center" bgcolor="#EFF3FF">�б�����</td>
    <td colspan="3" bgcolor="#EFF3FF">
    <input name="columnLink" id="columnLink"  type="text" class="textfield" style="WIDTH:500px;" value="${detail.columnLink}"></td>
    </tr>
  --%><tr>
    <td align="center" bgcolor="#EFF3FF">������Ϣ</td>
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
�ֶι����ֶ�<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">��������</td>
      <td width="40%" bgcolor="#EFF3FF"> 
      <select name="foreignTable" id="foreignTable" class="textfield" onchange="findColumnsByTable();">
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.sysMainTables}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTable.id eq item.id?' selected':'' }>${item.tableCnName}</option>
         </c:forEach>
      </select>
      </td>
      <td width="10%" bgcolor="#EFF3FF">��������ֶ�</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="foreignTableKeyColumn" id="foreignTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-��ѡ���������-</option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
    </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">������ʾ�ֶ�</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumn" id="foreignTableValueColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.foreignTableValueColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
	</td>
      <td bgcolor="#EFF3FF">������ʾ�ֶ�����</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumnOrder" id="foreignTableValueColumnOrder" style="width:100px;" class="textfield">
     	 <option value="1"></option>
          <option value="1" ${empty detail.foreignTableValueColumnOrder or detail.foreignTableValueColumnOrder eq 1?' selected':''}>����</option>
          <option value="0" ${detail.foreignTableValueColumnOrder eq 0?' selected':''}>����</option>
      </select>
      
       <input name="foreignTableParentColumn" id="foreignTableParentColumn" type="hidden" class="textfield" value="${detail.foreignTableParentColumn.id}">    
       
     </td>
    </tr>
    <!-- 
    <tr>
      <td align="center" bgcolor="#EFF3FF">�������ֶ�</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableParentColumn" id="foreignTableParentColumn" style="width:100px;" class="textfield">
          <option value=""></option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.foreignTableParentColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      �������漰�������ӹ�ϵʱ�ġ����ֶΡ�</td>
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
��ѡ���������<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">��������</td>
      <td width="40%" bgcolor="#EFF3FF"> 
      <select name="referencedTable" id="referencedTable" class="textfield" onchange="findRefColumnsByTable();">
         <option value=""></option>
         <c:forEach var="item" items="${requestScope.sysMainTables}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTable.id eq item.id?' selected':'' }>${item.tableCnName}</option>
         </c:forEach>
      </select>��UserRole
      </td>
      <td width="10%" bgcolor="#EFF3FF">��������ֶ�</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="referencedTableKeyColumn" id="referencedTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-��ѡ���������-</option>
           <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>��UserRole.id
    </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">������ʾ�ֶ�</td>
      <td bgcolor="#EFF3FF">
      <select name="referencedTableValueColumn" id="referencedTableValueColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.referencedTableValueColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>��UserRole.role_id
	</td>
      <td bgcolor="#EFF3FF">�����������ֶ�</td>
      <td bgcolor="#EFF3FF">
      <select name="referencedTableParentColumn" id="referencedTableParentColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.referencedTableParentColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select> ��UserRole.user_id
      <input name="referencedTableValueColumnOrder" id="referencedTableValueColumnOrder" type="hidden" class="textfield" value="${detail.referencedTableValueColumnOrder}">    
     </td>
    </tr>
   <tr>
      <td align="center" bgcolor="#EFF3FF">��ʾ����</td>
      <td bgcolor="#EFF3FF">
	     <input name="columnSum" id="columnSum"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnSum}">
	    		
	  </td>
      <td bgcolor="#EFF3FF">�Ƿ��ռ����</td>
      <td bgcolor="#EFF3FF">
      <select name="bigFlag" id="bigFlag" style="width:100px;" class="textfield">
          <option value="0" ${detail.bigFlag eq 0?' selected':''}>��</option>
          <option value="1" ${detail.bigFlag eq 1?' selected':''}>��</option>
      </select>�ǣ�����ֶ���һ����ʾ
      <input name="referencedTableValueColumnOrder" id="referencedTableValueColumnOrder" type="hidden" class="textfield" value="${detail.referencedTableValueColumnOrder}">    
     </td>
    </tr>
    
</table>
<br>

��ʶ�ֶ�����<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">�Ƿ��ʶ�ֶ�</td>
      <td width="18%" bgcolor="#EFF3FF">
     	<select name="identityFlag" id="identityFlag" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.identityFlag eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.identityFlag eq 1?' selected':'' }>��</option>
     	</select>
      </td>
      <td width="9%" bgcolor="#EFF3FF">��ʼֵ</td>
      <td width="13%" bgcolor="#EFF3FF">
        <input name="seed" id="seed"  type="text" class="textfield"  value="${detail.seed}">
       </td>
      <td width="10%" bgcolor="#EFF3FF">������</td>
      <td width="40%" bgcolor="#EFF3FF">
		<input name="increment" id="increment"  type="text" style="width:40px;" class="textfield"  value="${detail.increment}">
		&nbsp;ǰ׺
		<input name="identityMode" id="identityMode"  type="text" class="textfield"  value="${detail.identityMode}">
		��C01-��ʾ���ɵı����C01-��ͷ
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
�ֶ�ѡ��<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 
    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">�Ƿ��Ǳ�����</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail.id or detail.isMustInput eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>��</option>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">�Ƿ���IME��</td>
      <td width="40%" bgcolor="#EFF3FF">
     <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail.id or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>��</option>
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

<input class=button type="button" value="�����ֶ��޸�" onclick="saveColumn();">&nbsp;
<input class=button type="button" value="����" onclick="returnTableDetail();">
<BR>
</body>
</html>
