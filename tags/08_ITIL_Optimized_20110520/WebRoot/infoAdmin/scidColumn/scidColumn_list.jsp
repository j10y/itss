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


//�ж�������ַ��������ģ��ɰ��������ַ���
function isChinese(s){
 // �������ʽ����
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
 // �������ʽ����
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
  if(xform.name.value==""){
  	 alert("�����������������Ʊ�����д");
  	 xform.name.focus();
  	 return false;
  }
}

function saveOrder(){ 
	alert("zxc");
	var xform = document.formDel;
   	xform.action.value="${pageContext.request.contextPath}/serviceItem_saveOrder.action";
	xform.submit();
}

function returnTableDetail(){
  window.location.href="${pageContext.request.contextPath}/infoAdmin/serviceItem/serviceItem_info.jsp?dataId=${serviceItem.id}";
}
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>�������������͹������������޸��ֶ�</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    �������ƣ�${serviceItem.name }
    <!-- 
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableSetting.do?methodCall=list&smtId=${smt.id}&settingType=1">ϵͳ�ɼ��ֶ�����</a>
    &nbsp;
     <a href="${pageContext.request.contextPath}/infoAdmin/sysTableQuery.do?methodCall=list&smtId=${smt.id}">ϵͳ������ѯ����</a>
    &nbsp;
	 <a href="${pageContext.request.contextPath}/infoAdmin/sysTableRole.do?methodCall=list&smtId=${smt.id}">ϵͳ��ɫ�ɼ��ֶ�����</a> 
    -->
    </TD>
   
 </TR>
</TBODY>
</TABLE>
<BR>
  ϵͳ��̨���� -&gt; �������������� -&gt; ���������� 
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/serviceItem_removeScidColumn.action" method="post">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="serviceItem" value="${serviceItem.id}">
    <TBODY>
      <TR>
        <TD  width="20" bgColor=#8db5e9> <FONT color=#ffffff><STRONG>ID</STRONG></FONT> </TD>		
        <TD  noWrap bgColor=#8db5e9 > <STRONG><FONT color=#ffffff>�ֶ�</FONT></STRONG> </TD>
        <TD noWrap  bgColor=#8db5e9> <FONT color=#ffffff><STRONG>�ֶ���������</STRONG></FONT> </TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������</strong></FONT></TD>
        
        <TD noWrap  bgColor=#8db5e9><FONT color=#ffffff><strong>������</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>��������ʾ�ֶ�</strong></FONT></TD>
        <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>������֤����</strong></FONT></TD>
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>����</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.sCIDColumns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
          href="${pageContext.request.contextPath}/serviceItem_scidColumnDetail.action?id=${item.id}&serviceItem=${serviceItem.id }">
        ${item.columnName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.columnCnName }</TD>
        <TD noWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		<TD noWrap> 
		<input name="order${item.id}" type="text" class="textfield" id="order" style="WIDTH:20px;" value="${item.order}"></TD>
        <TD width=48 align="center"> 
		<A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
           href="${pageContext.request.contextPath}/serviceItem_scidColumnDetail.action?id=${item.id}&serviceItem=${serviceItem.id }"> <FONT color="#330099">�޸�</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      <TR>        
        <TD noWrap bgColor=#ebf2f9 colSpan=8 align="right">&nbsp;
        <input class=button type="button" value="����" onclick="returnTableDetail();">
         <INPUT class=button  onclick="javascript:location.href='${pageContext.request.contextPath}/serviceItem_scidColumnDetail.action?serviceItem=${serviceItem.id }'" type=button value="�����ֶ�" >&nbsp;
        <!--  <INPUT class=button id=sort onclick="saveOrder()"  type=button value=�������� >
        -->
        </TD>
        <TD colSpan=2 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('��ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
      
   </TBODY>
  </form>
</TABLE>
<p>&nbsp;</p>
</body>
</html>