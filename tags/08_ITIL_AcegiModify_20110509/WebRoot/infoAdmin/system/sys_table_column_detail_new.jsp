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
  
  if(xform.propertyName.value==""){
  	 alert("�������Ʊ�����д");
  	 xform.propertyName.focus();
  	 return false;
  }else if(xform.columnName.value==""){
  	 alert("�ֶ����ƣ�Ӣ�ģ�������д");
  	 xform.columnName.focus();
  	 return false;
  }else if(xform.columnCnName.value==""){
 	 alert("�ֶ��������Ʊ�����д");
  	 xform.columnCnName.focus();
  	 return false;
  }else if(typeSelectIndex==""&&xform.isExtColumn.value==0){
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
		    if(selectIndex==3){	//�����б�		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==4){	//��ѡ��		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==5){	//��ѡ��		
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==10){	//��ѡ�б�	
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==12){	//Ext�б�
		   	 	if(extSelectType.style.display=="none"){
		      		extSelectType.style.display="block";
		   	 	}
		    } else if(selectIndex==15){	//Ext�б�
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
      border=0>&nbsp;<STRONG>ϵͳ�����ֶι����鿴���޸������ֶ���Ϣ</STRONG></FONT></TD>
  </TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    ϵͳ�������ƣ�${smt.tableCnName }
    </TD></TR></TBODY></TABLE><BR>
 <form action="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=save" method=post  name="editForm" id="editForm">
�ֶ�����
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">

 <tr> 
    <td align="center" bgcolor="#EFF3FF">�Ƿ�������</td>
    <td colspan="3" bgcolor="#EFF3FF">
	<select name="isExtColumn" id="isExtColumn" style="width:100px;" class="textfield" onchange="disColumn()">
      <option value="">-��ѡ��-</option>
      <option value="0" ${detail.isExtColumn eq 0?' selected':''}>�����ֶ�</option>
      <option value="1" ${detail.isExtColumn eq 1?' selected':''}>��չ���ֶ�</option>
    </select>
	</td>
  </tr>
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">��������</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${detail.id}">
     <input name="tableName"  id="tableName"   type="hidden" value="${detail.tableName}">
     <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${detail.systemMainTable.id}">
     <input name="smtForAdd"  id="smtForAdd"   type="hidden" value="${param.tableId}">
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
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td bgcolor="#EFF3FF">
    <input name="columnName" id="columnName"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnName}">
    (����д���ݱ��е��ֶ����ƣ���������������)
    </td>
    <td bgcolor="#EFF3FF">�ֶ���������</td>
    <td bgcolor="#EFF3FF">
    <input name="columnCnName" id="columnCnName"  type="text"
    		class="textfield" style="WIDTH:100px;" value="${detail.columnCnName}">
    (������д����)		
	  </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">�ֶ�����</td>
    <td bgcolor="#EFF3FF">
    <select name="systemMainTableColumnType" id="systemMainTableColumnType" style="width:100px;" class="textfield" onChange="display()">
       <option value="">-��ѡ��-</option>
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
						    <option value="">��ѡ��</option>
						    <option value="0" ${detail.extSelectType == 0?' selected':''}> Դ������</option>
						    <!-- <option value="1">Դ����չ��</option>  -->
							<option value="2" ${detail.extSelectType == 2?' selected':''}>�Զ����б�</option>
	  </select>
    (����������ͣ�����������ѡ�������򣬶�����������粻��ʾ��ѡ�������򣬷���ѡ�������б�)
    </td>
    <td bgcolor="#EFF3FF">�ֶ���֤����</td>
    <td bgcolor="#EFF3FF">
    <select name="validateType" id="validateType" style="width:100px;" class="textfield">
       <option value="">-��ѡ��-</option>
       <c:forEach var="item" items="${requestScope.validateTypes}" varStatus="status">
        <option value="${item.id}" ${detail.validateType.id eq item.id?' selected':''}>${item.validateTypeCnName}</option>
       </c:forEach>
         
    </select>
    (���������֤��ʽ����������Ͳ������������ı��)
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">Ψһ��ʶ</td>
    <td bgcolor="#EFF3FF">
      <select name="uniqueFlag" id="uniqueFlag" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.uniqueFlag eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.uniqueFlag eq 1?' selected':'' }>��</option>
      </select>
    </td>
    <td bgcolor="#EFF3FF">�Ƿ��ռ����</td> 
    <td bgcolor="#EFF3FF">
      <select name="bigFlag" id="bigFlag" style="width:100px;" class="textfield">
          <option value="0" ${detail.bigFlag eq 0?' selected':''}>��</option>
          <option value="1" ${detail.bigFlag eq 1?' selected':''}>��</option>
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
    <td align="center" bgcolor="#EFF3FF">������</td>
    <td bgcolor="#EFF3FF">
      <input name="lengthForPage" id="lengthForPage"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.lengthForPage}">px
    </td>
    <td bgcolor="#EFF3FF">����߶�</td> 
    <td bgcolor="#EFF3FF">
      <input name="heightForPage" id="heightForPage"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.heightForPage}">px;
  </td>
  </tr>
  <tr>
      <td align="center" bgcolor="#EFF3FF">��ʾ����</td>
      <td bgcolor="#EFF3FF">
	     <input name="columnSum" id="columnSum"  type="text" class="textfield" style="WIDTH:100px;" value="${detail.columnSum}">
	    		
	  </td>
      <td bgcolor="#EFF3FF"></td>
      <td bgcolor="#EFF3FF">
     </td>
    </tr>
  <!-- 
  <tr>
    <td align="center" bgcolor="#EFF3FF">�б�����</td>
    <td colspan="3" bgcolor="#EFF3FF">
    <input name="columnLink" id="columnLink"  type="hidden" class="textfield" style="WIDTH:500px;" value="${detail.columnLink}"></td>
    </tr>-->
  <tr> 
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
      ��ǰ���Թ���������</td>
      <td width="10%" bgcolor="#EFF3FF">��������ֶ�</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="foreignTableKeyColumn" id="foreignTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-��ѡ���������-</option>
           <c:forEach var="item" items="${requestScope.fcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.foreignTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
      ע��ѡ�������������ֶΣ�ϵͳ��Ĭ��ѡ��</td>
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
	ע��ѡ�������ġ����֡��ֶ�</td>
      <td bgcolor="#EFF3FF">������ʾ�ֶ�����</td>
      <td bgcolor="#EFF3FF">
      <select name="foreignTableValueColumnOrder" id="foreignTableValueColumnOrder" style="width:100px;" class="textfield">
     	 <option value="1"></option>
          <option value="1" ${empty detail.foreignTableValueColumnOrder or detail.foreignTableValueColumnOrder eq 1?' selected':''}>����</option>
          <option value="0" ${detail.foreignTableValueColumnOrder eq 0?' selected':''}>����</option>
      </select>
      ���������б�����ʽ���簴ITCODE������ʾ�û�</td>
    </tr>
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
      </select>
      </td>
      <td width="10%" bgcolor="#EFF3FF">��������ֶ�</td>
      <td width="40%" bgcolor="#EFF3FF">
      <select name="referencedTableKeyColumn" id="referencedTableKeyColumn" style="width:120px;" class="textfield">
          <option value="">-��ѡ���������-</option>
           <c:forEach var="item" items="${requestScope.refcolumns}" varStatus="status">
           <option value="${item.id}" ${detail.referencedTableKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:forEach>
      </select>
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
      </select>
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
    
</table>
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
      <td width="10%" align="center" bgcolor="#EFF3FF">�Ƿ��ѯ����</td>
      <td width="40%" bgcolor="#EFF3FF">
	      <select name="isSearchItem" id="isSearchItem" style="width:100px;" class="textfield">
	          <option value=""></option>
	          <option value="0" ${empty detail or detail.isSearchItem eq 0?' selected':'' }>��</option>
	          <option value="1" ${detail.isSearchItem eq 1?' selected':'' }>��</option>
	      </select>
	      (����Ҫ���б�ҳ����ʾ���ֶ���Ϊ��ѯ��������ѡ����)
     </td>
      <td width="10%" bgcolor="#EFF3FF">�Ƿ��Ǳ�����</td>
      <td width="40%" bgcolor="#EFF3FF">
	      <select name="isMustInput" id="isMustInput" style="width:100px;" class="textfield">
	          <option value=""></option>
	          <option value="0" ${empty detail or detail.isMustInput eq 0?' selected':'' }>��</option>
	          <option value="1" ${detail.isMustInput eq 1?' selected':'' }>��</option>
	      </select>
      
      <input name="isHiddenItem" id="isHiddenItem"  type="hidden" style="width:40px;" class="textfield"  value="${detail.isHiddenItem}">
      
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
      (�����޸ģ���ֻ���ֶ�)
      </td>
      <td bgcolor="#EFF3FF">�Ƿ���IME��</td>
      <td bgcolor="#EFF3FF">
      <select name="isImeItem" id="isImeItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or empty detail.isImeItem or detail.isImeItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isImeItem eq 1?' selected':'' }>��</option>
      </select>
      (����������������������������ı��ȷ���)
      </td>
    </tr>
    <tr>
      <td align="center" bgcolor="#EFF3FF">�Ƿ��ǵ�����</td>
      <td bgcolor="#EFF3FF">
        <select name="isOutputItem" id="isOutputItem" style="width:100px;" class="textfield">
          <option value=""></option>
          <option value="0" ${empty detail or detail.isOutputItem eq 0?' selected':'' }>��</option>
          <option value="1" ${detail.isOutputItem eq 1?' selected':'' }>��</option>
        </select>
      (����Ҫ���б�ҳ����Ե������ֶε����ݣ���ѡ����)
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

<input class=button type="button" value="�����ֶ��޸�" onclick="saveColumn();">&nbsp;
<input class=button type="button" value="����" onclick="returnTableDetail();">
<BR>
</body>
</html>
