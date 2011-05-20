<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/>
<META content="" name=Description>
<title>ϵͳ�����������ֶ�����</title>
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


function checkForm(){
  var xform = document.editForm;
  var primaryKeyColumn = document.editForm.primaryKeyColumn;
  var pkSelectIndex = primaryKeyColumn[primaryKeyColumn.selectedIndex].value;
  
  
  if(xform.tableName.value==""){
  	 alert("ϵͳ������������д����ΪӢ��");
  	 xform.tableName.focus();
  	 return false;
  }else if(xform.tableCnName.value==""){
  	 alert("�������ƣ���������������д");
  	 xform.tableCnName.focus();
  	 return false;
  }else if(xform.className.value==""){
 	 alert("����ӳ��ʵ���������д");
  	 xform.className.focus();
  	 return false;
  }else if(pkSelectIndex==""){
     //alert("�����ֶα���ѡ����δȷ���ֶ����ƣ����ڼ����ֶΣ������������ֶ��������ƺ���ѡ��");
  	 xform.propertyType.focus();
  	 //return false;
  }
  
  if(!isChinese(xform.tableCnName.value)){
     alert("������������д����");
  	 xform.tableCnName.focus();
  	 return false;
  }
  
  xform.submit();
 
 
}

function save(){
	  var xform = document.formAdd;
	  xform.method.value="save";
	  xform.submit();
	}
	
</script>
<script LANGUAGE="javascript"> 
function openwin(url) { 
	window.open (url, "newwindow", "height=300, width=700,toolbar= no, menubar=no,top=200 ,left=60, scrollbars=no, resizable=no, location=no, status=no") 
//д��һ�� 
} 
</script>
<script language="javascript">
function setText(obj)
{
var valsel = obj.value;
var XName = document.getElementById("XName");
var YName = document.getElementById("YName");
var lableTickName = document.getElementById("lableTickName");

if(valsel == "1"){
	 XName.disabled = true;
	 YName.disabled = true;
	 lableTickName.disabled = true;
 }
else{
	 XName.disabled = false;
	 YName.disabled = false;
	 lableTickName.disabled = false;
 }
}
</script>

</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
 </TR>
</TBODY>
</TABLE>
<BR>

ϵͳ��̨���� -&gt; ϵͳ���� -&gt; ����
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="${pageContext.request.contextPath}/infoAdmin/sysMainTable.do?methodCall=saveTable" 
 	   method=post onsubmit="return checkForm();">
 
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">ϵͳ������</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${smt.id}">
    <input name="systemMainTable"  id="systemMainTable"   type="hidden" value="${smt}">
    
    <input name="userExtFlag"  id="userExtFlag"   type="hidden" value="${smt.userExtFlag}">
   		<input name="deployFlag"  id="deployFlag"   type="hidden" value="${smt.deployFlag}">
   		<input name="templateFlag"  id="templateFlag"   type="hidden" value="${smt.deployFlag}">
   		
   		
    <input name="tableName" id="tableName"  type="hidden" class="textfield" style="WIDTH:250px;" value="${smt.tableName}">${smt.tableName}
    
    </td>
    <td width="10%" bgcolor="#EFF3FF">������</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="tableCnName"  id="tableCnName" type="hidden" class="textfield" style="WIDTH:150px;" value="${smt.tableCnName}">${smt.tableCnName}
    
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">����ӳ��ʵ����</td>
    <td bgcolor="#EFF3FF">
    <input name="className" id="className" type="hidden" class="textfield" style="WIDTH: 400px;" value="${smt.className}">${smt.className}
    </td>
    <td width="10%" bgcolor="#EFF3FF"></td>
    <td width="40%" bgcolor="#EFF3FF"></td>
  </tr>
  </form>
</table>
<BR>

  ϵͳ��̨���� -&gt; ϵͳ���� -&gt; ϵͳͳ��ͼ������
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formAdd" action="${pageContext.request.contextPath}/infoAdmin/jfreeChartAction.do" method="post">
    <input type="hidden" name="method" value="save">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${smt.id}">
    <input type="hidden" name="smtTableName" value="${smt.tableName}">
    <TBODY>
    <tr bgColor=#ebf2f9>
     <td bgColor=#ebf2f9 colSpan=10>
    <table>
    <tr>
    <td ></td>
    <td>ͳ��ͼ����:</td>
    <td ><input name="tjPictureName"  id="tjPictureName"  type="text" value=""></td>
    <td>ͳ��ͼURL:</td>
    <td ><input name="tjPictureURL"  id="tjPictureURL"  type="text" value=""></td>
     <td>ͳ��ͼ����:</td>
    <td >
    <select name="tjPictureType" id="tjPictureType" style="width:120px;" class="textfield" onchange="setText(this);">	       
	       <option value=""></option>
	       <option value="1">��״ͼ</option>
	       <option value="2">��״-����</option>
	       <option value="3">��״-����</option>
	       <option value="4">����</option>
	</select>
    </td>
     
    </tr>
    </table>
    </td>
    </tr>
    <tr bgColor=#ebf2f9>
     <td bgColor=#ebf2f9 colSpan=10>
    <table>
    <tr>
    <td ></td>
    <td>X������:</td>
     <td ><input name="XName"  id="XName"  style="width:100px;" type="text" value=""></td>
     <td>Y������:</td>
     <td ><input name="YName"  id="YName"   style="width:100px;" type="text" value=""></td>
     <td>itemName:</td>
    <td >
    <select name="itemName" id="itemName" style="width:100px;" class="textfield">	       
	       <option value=""></option>
	       <c:forEach  var="item" items="${requestScope.mainColumns}" varStatus="status">
	       <option value="${item.id }">${item.columnCnName }</option>
	       </c:forEach>
	</select>
    </td>
    <td ></td>
     <td>lableTickName:</td>
    <td >
    <select name="lableTickName" id="lableTickName" style="width:100px;" class="textfield">	       
	       <option value=""></option>
	       <c:forEach  var="item" items="${requestScope.mainColumns}" varStatus="status">
	       <option value="${item.id }">${item.columnCnName }</option>
	       </c:forEach>
	</select>
    </td>
    </tr>
    </table>
    </td>
    </tr>
      <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>
		<TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>����</strong></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������</strong></FONT> </TD>
        <TD  noWrap bgColor=#8db5e9 > <STRONG><FONT color=#ffffff>�ֶ�</FONT></STRONG> </TD>
        <TD noWrap  bgColor=#8db5e9> <FONT color=#ffffff><STRONG>�ֶ���������</STRONG></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>������</strong></FONT></TD>
        
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>������</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������ʾ�ֶ�</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>������֤����</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.mainColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        ${item.propertyName }
        </TD>
        <TD noWrap>${item.propertyType.propertyTypeName }&nbsp; </TD>
        <TD noWrap>${item.columnName }</TD>
        <TD noWrap>${item.columnCnName }</TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
	    <TD width=60>
	    <table> 
	    <tr>
	    <td>
	    <INPUT name="columnName_${item.id}" type="hidden" value="${item.columnName }">
	    <INPUT name="foreignTable_${item.id}" type="hidden" value="${item.foreignTable.tableName }">
	    <INPUT name="foreignTableValueColumn_${item.id}" type="hidden" value="${item.foreignTableValueColumn.columnName }">
	    <INPUT name="foreignTableKeyColumn_${item.id}" type="hidden" value="${item.foreignTableKeyColumn.columnName }">
		<INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
       </td>
       <td>
        <select name="sqlType_${item.id}" id="sqlType_${item.id}" style="width:55px;" class="textfield">	       
	       <option value=""></option>
	       <option value="1">MAX</option>
	       <option value="2">COUNT</option>
	       <!-- <option value="3">ORDER BY</option> -->
	       <option value="4">GROUP BY</option>
	    </select>
	    </td>
	    </tr>
	    </table>
        </TD>
      </TR>
      </c:forEach>
      <TR>
        <TD noWrap bgColor=#ebf2f9 colSpan=9 align="right">&nbsp;
        </TD>
         <TD noWrap bgColor=#ebf2f9 colSpan=1 align="right">&nbsp;
       		<input class=button onclick="save()" type=button  value="����">   
        </TD>
      </TR>
      
   </TBODY>
  </form>
  
</TABLE>
<br>
  ϵͳ��̨���� -&gt; ϵͳͳ��ͼ�� 
   <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/jfreeChartAction.do" method="post">
     <input type="hidden" name="method" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${smt.id}">
   <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>
		<TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>ͳ��ͼ����</strong></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>ͳ��ͼ��ӦURL</strong></FONT> </TD>
        <TD  noWrap bgColor=#8db5e9 > <STRONG><FONT color=#ffffff>ͳ��ͼ����</FONT></STRONG> </TD> 
      <!--   <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>����sql���</strong></FONT></TD> -->
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>X������</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>Y������</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>itemName</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>lableTickName</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
       <c:forEach  var="item" items="${requestScope.statisPicture}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
       ${item.tjPictureName}
        </TD>
        <TD noWrap>${item.tjPictureURL}&nbsp; </TD>
        <TD noWrap>
        <c:if test="${item.tjPictureType eq 1}">
        	��״ͼ
        </c:if>
        <c:if test="${item.tjPictureType eq 2}">
        	��״-����
        </c:if>
        <c:if test="${item.tjPictureType eq 3}">
        	��״-����
        </c:if>
        <c:if test="${item.tjPictureType eq 4}">
        	����
        </c:if>
        </TD>
      <!--   <TD noWrap>${item.sqlString}&nbsp;</TD> -->
        <TD noWrap>${item.XName }&nbsp;</TD>
        <TD noWrap>${item.YName }&nbsp;</TD>
        <TD noWrap>${item.itemName.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.lableTickName.columnCnName }&nbsp;</TD>
        <TD width=48 align="center"> 
		<INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
       <!-- <a href="${pageContext.request.contextPath}${item.tjPictureURL}"  target="blank">�޸�</a>  -->
       <a href="${pageContext.request.contextPath}/infoAdmin/jfreeChartAction.do?method=makepicture&smtTableId=${smt.id }&dataId=${item.id }&tjPictureURL=${item.tjPictureURL}">����</a>
       <a href="#" onclick="openwin('${pageContext.request.contextPath}${item.tjPictureURL}')">Ԥ��</a> 
        </TD>
      </TR>
      </c:forEach>
      <TR>
        <TD noWrap bgColor=#ebf2f9 colSpan=8 align="right">&nbsp;
        </TD>
        <TD colSpan=1 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('��ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
      </FORM>
   </table>
<!-- 
<br>
  ϵͳ��̨���� -&gt; ϵͳ���� -&gt; ��չ�ֶ����� 
   <table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
   <FORM name="formDelExt" action="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do" method="post">
     <input type="hidden" name="methodCall" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${smt.id}">
   <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>
		<TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>�ֶ�Ӣ����</strong></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>�ֶ���������</strong></FONT> </TD>
        <TD  noWrap bgColor=#8db5e9 > <STRONG><FONT color=#ffffff>��չ�ֶ�����</FONT></STRONG> </TD> 
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>������</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������ʾ�ֶ�</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>������֤����</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>����</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
       <c:forEach  var="item" items="${requestScope.extColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A  
          href="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=toExtForm&tableId=${smt.id}&id=${item.id}">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.columnCnName}&nbsp; </TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }
        <c:if test="${item.systemMainTableColumnType.id==3 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','�����б�');">(�����޸������б�)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==4 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸ĵ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==5 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        </TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		<TD noWrap> 
		<input name="order${item.id}" type="text" class="textfield" id="order" style="WIDTH:20px;" value="${item.order}"></TD>
        <TD width=48 align="center"> 
		<A 
            href="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=toExtForm&tableId=${smt.id}&id=${item.id}"> <FONT color="#330099">�޸�</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      <TR>
      <script>
        function loadNewExtColumn(){
          window.location.href="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=toExtForm&tableId=${smt.id}";
        } 
      </script>
        <TD noWrap bgColor=#ebf2f9 colSpan=8 align="right">&nbsp;
         
           <INPUT class=button  onclick="loadNewExtColumn();" type=button value="������չ�ֶ�" >&nbsp;
        
        </TD>
        <TD colSpan=1 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('ɾ���ֶν�����ɾ�����û��ɼ��ֶκͲ�ѯ�ֶΣ���ȷ��ɾ����');" 
    		type=submit value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
      </FORM>
   </table>
    -->
<p>&nbsp;</p>
</body>
</html>
