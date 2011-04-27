<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>ϵͳ����ָ���б�</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META content="MSHTML 6.00.2900.5583" name=GENERATOR>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
</HEAD>
<script language="javascript">
function saveSort(){ 
  var xform = document.formDel;
  xform.methodCall.value="saveItemOrder";
  xform.submit();
}

</script>

<script language="javascript">
 function query(pageNo){ 
   var xform = document.formSearch;
   var moduleSelect = xform.definiName;
  
   var selectModuleId = moduleSelect.options[moduleSelect.selectedIndex].value;

   xform.pageNo.value = pageNo;
   xform.methodCall.value="list";
   xform.submit();
 }
 
function addTaskPreAssign(){
	var definition = document.getElementById("definitionList");
	var def = definition.value;
	if(def==""){
		alert("��ѡ��һ�����̶���");
		return;
	}
	location = "${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=toAdd&def="+def;
}

function delTaskPreAssign(){
	if(confirm("ȷ��Ҫɾ���𣿴˲������ɻָ���")){
		var definition = document.getElementById("definitionList");
		var def = definition.value;
		if(def==""){
			alert("��ѡ��һ�����̶���");
			return;
		}
		location = "${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=deleteDefPreassign&def="+def;
	}
}

</script>
<body>

<TABLE cellSpacing=1 cellPadding=6 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳ����ָ���б�</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=center  valign="buttom" bgColor=#ebf2f9 height=10>
    <br/>
    <FORM name="formSearch" action="${pageContext.request.contextPath}/workflow/taskPreAssign.do" method="post">
     	ѡ�����̣�
     	<select name="definiName"  id="definitionList" class="textfield">
           <option value=""></option>
            <c:forEach var="item" items="${requestScope.definitions}" varStatus="status">
             <option value="${item.name}"}>${item.description}</option>
           </c:forEach>
        </select>
    	<INPUT name="pageNo" type="hidden" value="${param.pageNo}">
         <INPUT name="methodCall" type="hidden" value="list">
      [<A href="javascript:query(1);">����</A>]
      [<A href="javascript:addTaskPreAssign()">����</A>]
      [<A href="javascript:delTaskPreAssign()">ɾ��</A>]
  </FORM></TD>
      </TR>
   </TBODY>
 </TABLE>
        
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=10></TD></TR></TBODY></TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/workflow/taskPreAssign.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">

  <TBODY>
  <TR height="30">
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
     <TD width="25%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>��������</FONT></STRONG>
    </TD>
	<td nowrap width="25%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>���̶�������</STRONG></font>
	</td>
	   <td nowrap  width="25%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>��������</STRONG></font>
	</td>
	</td>
	   <td nowrap  width="15%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>���̽�ɫ</STRONG></font>
	</td>
   <TD  width= bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>����</FONT></STRONG> 
   </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.page.data}" varStatus="status">   
 
  <TR height="30" onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <TD  width="20">${item.id }</TD>
    </td>
    
    <td nowrap valign="middle">
			${item.departmentName}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.definitionDesc}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.nodeName}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.workflowRole.name}&nbsp;
		</td>
    <TD width=150 align="center">
    <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
       href="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=toForm&id=${item.id}&pageNo=${param.pageNo}">
      <FONT color="#330099">�޸�</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px">-->
      </TD>
   
    </TR>
  
 </c:forEach> 
  

<TR>
    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY>
        <TR>
          <TD>���ƣ�<FONT color=#ff6600>${requestScope.page.totalCount}</FONT>����¼&nbsp;ҳ�Σ�<FONT 
            color=#ff6600>${param.pageNo}</FONT></STRONG>/${requestScope.page.totalPageCount}&nbsp;ÿҳ��<FONT 
            color=#ff6600>${requestScope.page.pageSize}</FONT>��</TD>
          <TD align=right>&nbsp;
          <c:set var="actionPath" value="${pageContext.request.contextPath}/admin/itemManage.do?methodCall=${param.methodCall}" scope="page"/>
          <c:set var="pageno" value="${param.pageNo}" scope="page"/>
          <c:set var="pagenc" value="2" scope="page"/><!-- ÿҳ��ʾ�ķ�ҳҳ������=pagenc*2+1 -->
          <c:set var="pagenmin" value="${pageno-pagenc}" scope="page"/>
          <c:set var="pagenmax" value="${pageno+pagenc}" scope="page"/>
          <c:set var="pagec" value="${requestScope.page.totalPageCount}" scope="page"/>
          
          <c:if test="${pagenmin<1}">
            <c:set var="pagenmin" value="1" scope="page"/>
          </c:if>
          <c:if test="${pageno>1}"><!-- ���ҳ�����1����ʾ(��һҳ) -->
            <a href="javascript:query(1);"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>9</font></a>
          </c:if>
          <c:if test="${pagenmin>1}"><!-- ���ҳ�뿪ʼֵ����1����ʾ(��ǰ) -->
            <a href="javascript:query(${(pageno-(pagenc*2-1))});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>7</font></a>
          </c:if>
          <c:if test="${pagenmax>pagec}"> <!-- '���ҳ�����ֵ������ҳ��,��=��ҳ�� -->
            <c:set var="pagenmax" value="${pagec}" scope="page"/>
          </c:if>
		  <c:forEach var="index" begin="${pagenmin}" end="${pagenmax}"  varStatus="status">
		   <c:if test="${index eq pageno}" var="current">
		    <font color='#ff6600'>${index}</font>
		   </c:if>
		   <c:if test="${not current}">
		    [<a href="javascript:query(${index});">${index}</a>]
		   </c:if>
		  </c:forEach>
		  <c:if test="${pagenmax<pagec}"> <!-- '���ҳ�����ֵС����ҳ������ʾ(����) -->
		   <a href="javascript:query(${pageno+(pagenc*2+1)});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>8</font></a>&nbsp;
		  </c:if>
		  <c:if test="${pageno<pagec}"> <!-- ���ҳ��С����ҳ������ʾ(���ҳ)	 -->
		   <a href="javascript:query(${pagec});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>:</font></a>&nbsp;
		  </c:if>
		  
          &nbsp;��������&nbsp;<INPUT class=textfield 
            onkeydown=if(event.keyCode==13)event.returnValue=false 
            style="WIDTH: 40px; HEIGHT: 18px" 
            onchange="if(/\D/.test(this.value)){alert('ֻ������תĿ��ҳ��������������');this.value='1';}" 
            value=1 name="skipPage">&nbsp;ҳ
             <INPUT class=button style="WIDTH: 20px; HEIGHT: 18px" 
             onclick="javascript:getPage();" type=button value=GO name=submitSkip> 
          </TD></TR>
          </TBODY>
       </TABLE>
       <script>
         function getPage(){ 
           var pageNo = document.formDel.skipPage.value;
           query(pageNo);
         }
       </script>
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>
</BODY></HTML>
