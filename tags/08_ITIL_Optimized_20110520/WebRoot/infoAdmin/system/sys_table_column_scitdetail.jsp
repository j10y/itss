<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>ϵͳ�����ֶ�����</title>
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
  var systemMainTableColumnType = document.editForm.systemMainTableColumnType;
  var systemMainTableColumnType = systemMainTableColumnType[systemMainTableColumnType.selectedIndex].value;
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
  }
  if(!isChinese(xform.columnCnName.value)){
     alert("�ֶ��������Ʊ�����д����");
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
      border=0>&nbsp;<STRONG>�����������ֶι����鿴���޸��ֶ���Ϣ</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    ��������������${serviceItemType.name }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/SCIColumnAction.do?methodCall=save" method=post  name="editForm" id="editForm">

�ֶ�����
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
  <tr>
    <td width="10%"  align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="serviceItemType" id="serviceItemType"  type="hidden" value="${serviceItemType.id}">
    <input name="id" id="id"  type="hidden" value="${detail.id}">
    <input name="order" id="order"  type="hidden" value="${detail.order}">
    <input name="isExtColumn"  id="isExtColumn"   type="hidden" value="0">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    
    </td>
    <td width="10%" bgcolor="#EFF3FF">�ֶ���������</td>
    <td bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
   
	  </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td bgcolor="#EFF3FF">
    <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:100px;" class="textfield">
       <option value="">-��ѡ��-</option>
       <c:forEach var="item" items="${requestScope.systemMainTableColumnTypes}" varStatus="status">
        <option value="${item.id}" ${detail.systemMainTableColumnType.id eq item.id?' selected':''}>${item.columnTypeCnName}</option>
       </c:forEach>
         
    </select>
    
    </td>
    <td bgcolor="#EFF3FF">�ֶ���֤����</td>
    <td bgcolor="#EFF3FF">
    <select name="validateType" id="validateType" style="width:100px;" class="textfield">
       <option value="">-��ѡ��-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    
    </td>
  </tr> <tr><td></td></tr>

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
      </td>
    </tr>
     <tr><td></td></tr>
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
      <td bgcolor="#EFF3FF">�Ƿ�Ϊ�����ֶ�</td>
      <td bgcolor="#EFF3FF"><select name="isNullForeignColumn" id="isNullForeignColumn" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.isNullForeignColumn eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isNullForeignColumn eq 1?' selected':'' }>��</option>
      </select>
     </td>
    </tr>
    
     <tr>
      <td align="center" bgcolor="#EFF3FF">�ϴ��ļ�·����</td>
      <td bgcolor="#EFF3FF">
      <input name="uploadUrl" id="uploadUrl"  type="text" class="textfield" style="WIDTH:180px;" value="${detail.uploadUrl}">
      �߼��ļ���ǰ׺
      <input name="fileNamePrefix" id="fileNamePrefix"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.fileNamePrefix}">
      
      </td>
      <td bgcolor="#EFF3FF">�߼��ļ����ֶ�</td>
      <td bgcolor="#EFF3FF">
      
      <select name="fileNameColumn" id="fileNameColumn" style="width:100px;" class="textfield">
          <option value=""></option>
            <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}"  ${detail.fileNameColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      ϵͳ�ļ����ֶ�
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
�����ֶ�����<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">���������ֶ�</td>
      <td width="18%" bgcolor="#EFF3FF">
     	<select name="abstractFlag" id="abstractFlag" style="width:100px;" class="textfield">
          <option value="" ${empty detail?' selected':'' }></option>
          <option value="0" ${empty detail or detail.abstractFlag eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.abstractFlag eq 1?' selected':'' }>��</option>
     	</select>
      </td>
      <td width="9%" bgcolor="#EFF3FF">���õ������ֶ�</td>
      <td width="13%" bgcolor="#EFF3FF">
      	<select name="discColumn" id="discColumn" style="width:100px;" class="textfield">
        <option value=""></option>
        <c:forEach var="item" items="${requestScope.columns}" varStatus="status">
        <option value="${item.id}"  ${detail.discColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
        </c:forEach>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">�����ֶ����ñ�</td>
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
�ֶ�ѡ��<br>
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 
    <tr>
      <td width="10%" align="center" bgcolor="#EFF3FF">�Ƿ��Ǳ�����</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isMustInput eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>��</option>
      </select></td>
      <td width="10%" bgcolor="#EFF3FF">�Ƿ���������</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="isHiddenItem" id="isHiddenItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isHiddenItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isHiddenItem eq 1?' selected':'' }>��</option>
      </select>
     
      </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">�Ƿ���޸���</td>
      <td bgcolor="#EFF3FF">
      <select name="isUpdateItem" id="isUpdateItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isUpdateItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isUpdateItem eq 1?' selected':'' }>��</option>
      </select>
  
      </td>
      <td bgcolor="#EFF3FF">�Ƿ���IME��</td>
      <td bgcolor="#EFF3FF">
      <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>��</option>
      </select>
      
      </td>
    </tr>
    <!-- 
    <tr>
      <td align="center" bgcolor="#EFF3FF">�Ƿ��ǲ�ѯ����</td>
      <td bgcolor="#EFF3FF">
      <select name="isSearchItem" id="isSearchItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isSearchItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isSearchItem eq 1?' selected':'' }>��</option>
      </select>
      (����Ҫ���б�ҳ����ʾ���ֶ���Ϊ��ѯ��������ѡ����)
      </td>
      <td bgcolor="#EFF3FF">�Ƿ��ǵ�����</td>
      <td bgcolor="#EFF3FF">
      <select name="isOutputItem" id="isOutputItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isOutputItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isOutputItem eq 1?' selected':'' }>��</option>
      </select>
      (����Ҫ���б�ҳ����Ե������ֶε����ݣ���ѡ����)
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

<input class=button type="button" value="�����ֶ��޸�" onclick="saveColumn();">&nbsp;
<input class=button type="button" value="����" onclick="returnTableDetail();">
<BR>
</body>
</html>
