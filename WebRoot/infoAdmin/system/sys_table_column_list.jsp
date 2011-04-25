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
function openOptionWindow(url,title){
		window.open(url,title,
					"menubar=no,scrollbars=yes,resizable=yes,height=400,width=350,status=no,toolbar=no,location=no,fullscreen=no,left=5,top=5",
					true);
	}

function saveSystemMainTable(){ 
    var serverPath = "${pageContext.request.contextPath}";

    var id = Ext.getDom("id").value;
    var tableName = Ext.getDom("tableName").value;
    var tableCnName = Ext.getDom("tableCnName").value;
    var className = Ext.getDom("className").value;
    var primaryKeyColumn = Ext.getDom("primaryKeyColumn");
    var module = Ext.getDom("module");
    var pkselectid = primaryKeyColumn.selectedIndex;
    var pkId=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].value;
    var pkName=primaryKeyColumn.options[primaryKeyColumn.selectedIndex].text;
    var moduleId = module.options[module.selectedIndex].value;

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/sysMainTable.do?methodCall=save", 
       params:{
           id: id, 
           tableName: tableName,
           tableCnName: unicode(tableCnName),
           className: className,
           primaryKeyColumn: pkId,
           module: moduleId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         Ext.getDom("tableName").value=smt.data.tableName;
         Ext.getDom("tableCnName").value=smt.data.tableCnName;
         Ext.getDom("className").value=smt.data.className
         Ext.getDom("primaryKeyColumn").selectedIndex=pkselectid;
         
         alert("ϵͳ�������Ա���ɹ�!");
         
       }
  });
}

function loadNewColumn(mainTableId){ 
    var serverPath = "${pageContext.request.contextPath}";

    Ext.Ajax.request({
       url: serverPath+"/infoAdmin/sysMainTableColumn.do?methodCall=loadNewColumns", 
       params:{
           mainTableId: mainTableId
           
       },
       method:'POST',
       success:function(response){
        
         var smt = Ext.decode(response.responseText);
         window.location.href = window.location.href;
         
         
       }
  });
}

function addNewColumn(mainTableId){ 
    document.location.href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId="+mainTableId+"&id=";
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
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳ��������������޸��������ԡ�ӳ����Ϣ�����������ֶ�</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableSetting.do?methodCall=list&smtId=${smt.id}&settingType=1">ϵͳ�ɼ��ֶ�����</a>
    &nbsp;
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableQuery.do?methodCall=list&smtId=${smt.id}">ϵͳ�����ѯ����</a>
    &nbsp;
	     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableRole.do?methodCall=list&smtId=${smt.id}">ϵͳ��ɫ�ɼ��ֶ�����</a> 
    </TD>
   
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
   		
   		
    <input name="tableName" id="tableName"  type="text" class="textfield" style="WIDTH:250px;" value="${smt.tableName}">
    <br>(���ݿ��б�����ƣ��������������)
    </td>
    <td width="10%" bgcolor="#EFF3FF">������</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:150px;" value="${smt.tableCnName}">
    <br>(����д�������ƣ������������������)
    </td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">����ӳ��ʵ����</td>
    <td bgcolor="#EFF3FF">
    <input name="className" id="className" type="text" class="textfield" style="WIDTH: 400px;" value="${smt.className}">
    <br>(����д����·����ʵ����ȫ������ʵ���ȫ�޶���)
    </td>
    <td bgcolor="#EFF3FF">�����ֶ�</td>
    <td bgcolor="#EFF3FF">
    <select name="primaryKeyColumn" id="primaryKeyColumn" style="width:100px;" class="textfield">
       <option value=""></option>
        <c:forEach var="item" items="${requestScope.columns}" varStatus="status">
         <c:if test="${not empty item.columnCnName}">
          <option value="${item.id}" ${smt.primaryKeyColumn.id eq item.id?' selected':''}>${item.columnCnName}</option>
         </c:if>
       </c:forEach>
    </select><br>
    (�����ֶα���ѡ��)
    </td>
     <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">����ģ��</td>
    <td width="40%" bgcolor="#EFF3FF">
    <select name="module" id="module" style="width:100px;" class="textfield">
       <option value=""></option>
        <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${smt.module.id eq item.id?' selected':''}>${item.name}</option>
         </c:if>
       </c:forEach>
    </select>
    </td>
    <td width="10%" bgcolor="#EFF3FF"></td>
    <td width="40%" bgcolor="#EFF3FF"></td>
  </tr>
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="3" bgcolor="#EFF3FF">
   
    	<input class=button type="submit" name="Submit" value="�������������޸�"><!--  onclick="saveSystemMainTable();" -->
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<BR>

  ϵͳ��̨���� -&gt; ϵͳ���� -&gt; ϵͳ�ֶ����� 
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do" method="post">
    <input type="hidden" name="methodCall" value="remove">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="smtId" value="${smt.id}">
    <TBODY>
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
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>�ֶ�����</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>����</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.columns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }
        <input type="hidden" name="id" value="${item.id }"></TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
          href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}">
        ${item.propertyName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.propertyType.propertyTypeName }&nbsp; </TD>
        <TD noWrap>${item.columnName }</TD>
        <TD noWrap>${item.columnCnName }
        <c:if test="${item.systemMainTableColumnType.id==3 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','�����б�');">(�����޸������б�)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==4 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸ĵ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==5 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ��');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==10 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ�б�');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==12 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','Ext�б�');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        <c:if test="${item.systemMainTableColumnType.id==15 and item.extSelectType==2}">
        <A href="javascript:openOptionWindow('${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=addOrUpdateColumns&id=${item.id}','��ѡ���');">(�����޸Ķ�ѡ��)</A>
        </c:if>
        </TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
        <TD noWrap>
        <c:if test="${item.isExtColumn eq 0}">�����ֶ�</c:if>
        <c:if test="${item.isExtColumn eq 1}">��չ���ֶ�</c:if>
        &nbsp;</TD>
		<TD noWrap> 
		<input name="order${item.id}" type="text" class="textfield" id="order" style="WIDTH:20px;" value="${item.order}"></TD>
        <TD <%--width=48--%> align="center"> 
		<A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
           href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}"> 
           	<FONT color="#330099">�޸�</FONT>
		</A>
		<!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px"> -->
		<A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
           href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=remove&id=${item.id}&smtId=${smt.id}"> 
           ɾ��
        </A>
        </TD>
      </TR>
      </c:forEach>
      <TR>
        
        <TD noWrap bgColor=#ebf2f9 colSpan=10 align="right">&nbsp;
        <INPUT class=button  onclick="loadNewColumn(${smt.id});" type=button value="�������ֶ�" >&nbsp;
         <INPUT class=button  onclick="addNewColumn(${smt.id});" type=button value="�������ֶ�" >&nbsp;
        <INPUT class=button id=sort onclick="saveSort();" type=button value=�������� name=submitDelSelect>
        </TD>
        <TD colSpan=2 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('ɾ���ֶν�����ɾ�����û��ɼ��ֶκͲ�ѯ�ֶΣ���ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
      
   </TBODY>
  </form>
  
</TABLE>
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
          //window.location.href="${pageContext.request.contextPath}/infoAdmin/sysExtTableColumn.do?methodCall=toExtForm&tableId=${smt.id}";
          window.location.href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=";
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
<p>&nbsp;</p>
 -->
</body>
</html>
