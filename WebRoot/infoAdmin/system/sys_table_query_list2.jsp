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
</script>
</head>
<body>
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0 height="56">
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳ��������鿴���޸��������ԣ��������������ֶ�</STRONG></FONT></TD></TR>
  <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=24>
    
    </TD></TR></TBODY></TABLE><BR>

<BR>
ϵͳ��̨���� -&gt; ϵͳ�����ѯ����
<table width="100%" border="0" cellpadding="3" cellspacing="1" bgcolor="#6298E1">
 <form name="editForm" id="editForm" action="#" method=post>
 
  <tr>
    <td width="10%" align="center" bgcolor="#EFF3FF">��ѯ����</td>
    <td width="40%" bgcolor="#EFF3FF">
    <input name="id"  id="id"   type="hidden" value="${smt.id}">
    
    <input name="tableName" id="tableName"  type="text" class="textfield" style="WIDTH:250px;" value="${smt.tableName}"></td>
    <td width="10%" bgcolor="#EFF3FF">��ѯ������</td>
    <td width="40%" bgcolor="#EFF3FF"><input name="tableCnName"  id="tableCnName" type="text" class="textfield" style="WIDTH:150px;" value="${smt.tableCnName}"></td>
  </tr>
  <tr>
    <td align="center" bgcolor="#EFF3FF">��ѯ����</td>
    <td bgcolor="#EFF3FF">
	 <select name="module" id="module" style="width:100px;" class="textfield">
       <option value=""></option>
        <c:forEach var="item" items="${requestScope.modules}" varStatus="status">
         <c:if test="${not empty item.name}">
          <option value="${item.id}" ${smt.module.id eq item.id?' selected':''}>${item.name}</option>
         </c:if>
       </c:forEach>
    </select>
	</td>
    <td bgcolor="#EFF3FF"></td>
    <td bgcolor="#EFF3FF">
    
    
    </td>
   
  </tr>
  <tr>
    <td align="right" bgcolor="#EFF3FF">&nbsp;</td>
    <td colspan="3" bgcolor="#EFF3FF">
   
    	<input class=button type="button" name="Submit" value="�������������޸�" onclick="saveSystemMainTable();">
    </td>
  </tr>
  <tr><td></td></tr>
  </form>
</table>
<br>
  ϵͳ��̨���� -&gt; ϵͳ���� -&gt; ϵͳ�����ֶ����� 
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/infoAdmin/sysmtable.do" method="post">
    <input type="hidden" name="methodCall" value="removeItem">
    <input type="hidden" name="pageNo" value="${param.pageNo}">
    <input type="hidden" name="level" value="${param.level}">
    <input type="hidden" name="flag" value="${param.flag}">
    <input type="hidden" name="topItems" value="${param.topItems}">
    <input type="hidden" name="secItems" value="${param.secItems}">
    <input type="hidden" name="thirdItems" value="${param.thirdItems}">
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
		 <TD noWrap bgColor=#8db5e9><FONT color=#ffffff><strong>����</strong></FONT></TD>
        <TD  width=76 bgColor=#8db5e9 align="center"><STRONG> <FONT color=#ffffff>����</FONT></STRONG>
            <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect>
            <INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect>
        </TD>
      </TR>
      <c:forEach  var="item" items="${requestScope.columns}" varStatus="status">
      <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
        <TD  noWrap>
        <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
          href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}">
        ${item.propertyName }&nbsp; 
        </A>
        </TD>
        <TD noWrap>${item.propertyType.propertyTypeName }&nbsp; </TD>
        <TD noWrap>${item.columnName }</TD>
        <TD noWrap>${item.columnCnName }</TD>
        <TD wnoWrap>${item.systemMainTableColumnType.columnTypeCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTable.tableCnName }&nbsp;</TD>
        <TD noWrap>${item.foreignTableValueColumn.columnCnName }&nbsp;</TD>
        <TD noWrap>${item.validateType.validateTypeCnName }&nbsp;</TD>
		<TD noWrap> 
		<input name="order${item.id}" type="text" class="textfield" id="order" style="WIDTH:20px;" value="${item.order}"></TD>
        <TD width=48 align="center"> 
		<A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
           href="${pageContext.request.contextPath}/infoAdmin/sysMainTableColumn.do?methodCall=toForm&tableId=${smt.id}&id=${item.id}"> <FONT color="#330099">�޸�</FONT>
		</A><INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH:13px;HEIGHT:13px">
        </TD>
      </TR>
      </c:forEach>
      <TR>
        
        <TD noWrap bgColor=#ebf2f9 colSpan=9 align="right">&nbsp;
        <INPUT class=button  onclick="loadNewColumn(${smt.id});" type=button value="�������ֶ�" >&nbsp;
         <INPUT class=button  onclick="loadNewColumn(${smt.id});" type=button value="�������ֶ�" >&nbsp;
         <INPUT class=button  onclick="loadNewColumn(${smt.id});" type=button value="�û��ֶ�����" >&nbsp;
        <INPUT class=button id=sort onclick="saveSort();" type=button value=�������� name=submitDelSelect>
        </TD>
        <TD colSpan=2 noWrap bgColor=#ebf2f9 align="left">
          <INPUT class=button  onclick="ConfirmDel('ɾ�������ֶν�����ɾ���������ֶκ��û�������Ϣ����ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
        </TD>
      </TR>
    <TD noWrap bgColor=#d7e4f7 colSpan=13>
      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY>
          <TR>
            <TD>���ƣ�<FONT color=#ff6600>${requestScope.page.totalCount}</FONT>����¼&nbsp;ҳ�Σ�<FONT 
            color=#ff6600>${param.pageNo}</FONT>/${requestScope.page.totalPageCount}&nbsp;ÿҳ��<FONT 
            color=#ff6600>${requestScope.page.pageSize}</FONT>��</TD>
            <TD align=right>&nbsp; <c:set var="actionPath" value="${pageContext.request.contextPath}/admin/itemManage.do?methodCall=${param.methodCall}" scope="page"/> <c:set var="pageno" value="${param.pageNo}" scope="page"/> <c:set var="pagenc" value="2" scope="page"/>
                <!-- ÿҳ��ʾ�ķ�ҳҳ������=pagenc*2+1 -->
                <c:set var="pagenmin" value="${pageno-pagenc}" scope="page"/> <c:set var="pagenmax" value="${pageno+pagenc}" scope="page"/> <c:set var="pagec" value="${requestScope.page.totalPageCount}" scope="page"/> <c:if test="${pagenmin<1}"> <c:set var="pagenmin" value="1" scope="page"/> </c:if> <c:if test="${pageno>1}">
                <!-- ���ҳ�����1����ʾ(��һҳ) -->
                <a href="javascript:query(${param.level},1);"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>9</font></a> </c:if> <c:if test="${pagenmin>1}">
                <!-- ���ҳ�뿪ʼֵ����1����ʾ(��ǰ) -->
                <a href="javascript:query(${param.level},${(pageno-(pagenc*2-1))});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>7</font></a> </c:if> <c:if test="${pagenmax>pagec}">
                <!-- '���ҳ�����ֵ������ҳ��,��=��ҳ�� -->
                <c:set var="pagenmax" value="${pagec}" scope="page"/> </c:if> <c:forEach var="index" begin="${pagenmin}" end="${pagenmax}"  varStatus="status"> <c:if test="${index eq pageno}" var="current"> <font color='#ff6600'>${index}</font> </c:if> <c:if test="${not current}"> [<a href="javascript:query(${param.level},${index});">${index}</a>] </c:if> </c:forEach> <c:if test="${pagenmax<pagec}">
                <!-- '���ҳ�����ֵС����ҳ������ʾ(����) -->
                <a href="javascript:query(${param.level},${pageno+(pagenc*2+1)});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>8</font></a>&nbsp; </c:if> <c:if test="${pageno<pagec}">
                <!-- ���ҳ��С����ҳ������ʾ(���ҳ)	 -->
                <a href="javascript:query(${param.level},${pagec});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>:</font></a>&nbsp; </c:if> &nbsp;��������&nbsp;
                <INPUT class=textfield 
            onkeydown=if(event.keyCode==13)event.returnValue=false 
            style="WIDTH: 40px; HEIGHT: 18px" 
            onchange="if(/\D/.test(this.value)){alert('ֻ������תĿ��ҳ��������������');this.value='1';}" 
            value=1 name="skipPage">&nbsp;ҳ
  
             <INPUT class=button style="WIDTH: 20px; HEIGHT: 18px" 
             		onclick="javascript:getPage();" type=button value=GO name=submitSkip>
            </TD>
          </TR>
        </TBODY>
      </TABLE>
      <script>
         function getPage(){ 
           var pageNo = document.formDel.skipPage.value;
          // query(${param.level}, pageNo);
         }
       </script>
    </TD>
  </form>
</TABLE>
<p>&nbsp;</p>
</body>
</html>
