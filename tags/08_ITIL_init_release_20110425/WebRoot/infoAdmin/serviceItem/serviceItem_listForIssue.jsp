<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>������</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">  
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
<META content="MSHTML 6.00.2900.5583" name=GENERATOR></HEAD>

<script language="javascript">
 
 function query(pageNo){ 
   var xform = document.formSearch;
   xform.pageNo.value = pageNo;
   xform.methodCall.value="listServiceItem";
   xform.submit();
 }

function createNewServiceItem(){
  	window.location.href ="${pageContext.request.contextPath}/infoAdmin/serviceItem/serviceItem_info.jsp";
}
</script>
  
  
<BODY >
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>���������ݹ�����ѯ����ӡ��޸ķ���������</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
      <TABLE cellSpacing=0 width="100%" border=0>
        <TBODY>
        <TR>

  <FORM name="formSearch" action="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do" method="post">
         
          <TD align="center"> 
        ���������ͣ�
          <select name="serviceItemType" style="width:100px;" class="textfield">
            <option value=""></option>
             <c:forEach var="item" items="${requestScope.serviceItemTypes}" varStatus="status">
              <option value="${item.id}" ${param.serviceItemType eq item.id?' selected':''}>${item.name}</option>
            </c:forEach>
         </select>&nbsp;
        ������״̬��
          <select name="serviceItemState" style="width:100px;" class="textfield">
            <option value=""></option>
             <c:forEach var="item" items="${requestScope.serviceStates}" varStatus="status">
              <option value="${item.id}" ${param.serviceState eq item.id?' selected':''}>${item.name}</option>
            </c:forEach>
         </select>&nbsp;
         ���������ƣ�<INPUT name="serviceItemName" type="text" value="${param.seviceItemName}">
         <INPUT name="pageNo" type="hidden" value="${param.pageNo}">
         <INPUT name="methodCall" type="hidden" value="listServiceItem">
         <INPUT class="button" type="button" onclick="query(1);" value="����" > 
         </TD>
  </FORM>
 
          <TD noWrap align=right>
            <A href="javascript:query(1);">ȫ��������</A>
          
           </TD>
          </TR>
          </TBODY>
          </TABLE>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
        
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>ϵͳ��̨����&nbsp;-&gt;&nbsp;
  ${not empty requestScope.serviceItemType? requestScope.serviceItemType.name : 'ȫ��'}����
  </TD></TR></TBODY></TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/serviceItem_remove.action" method="post">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
  <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>��������</FONT></STRONG>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>����������</STRONG></FONT>
    </TD>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>����״̬</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>������������</STRONG></FONT>
    </TD>
    <TD noWrap bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>������������</STRONG></FONT>
    </TD>
	<TD  width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>����</FONT></STRONG> 
    <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect> 
   </TD>
  </TR>
  
 <c:forEach  var="item" items="${requestScope.page.data}" varStatus="status">   
 
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
    <TD  width="20">${item.id }</TD>
    <TD noWrap  title="${item.name}" >
    <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
      href="${pageContext.request.contextPath}/infoAdmin/serviceItem/serviceItem_info.jsp?dataId=${item.id}">
      ${fn:substring(item.name, 0, 30)}
    </A> 
    </TD>
    
    <TD noWrap>${item.serviceItemType.name }</TD>
    <TD noWrap>${item.serviceStatus.name }</TD>
    
    <TD width=100 align="center">
    <A href="${pageContext.request.contextPath}/infoAdmin/serviceItemUserTableAction.do?methodCall=addTable&serviceItemId=${item.id}">
      <FONT color="#330099">������������</FONT></A>
      </TD>
     <TD width=100 align="center"> 
    <A href="${pageContext.request.contextPath}/infoAdmin/serviceItemProcessAction.do?methodCall=list&serviceItemId=${item.id}">
      <FONT color="#330099">������������</FONT></A>
      </TD>
    <TD width=48 align="center">
    <A onclick='changeAdminFlag("�޸ĵ�����Ŀ")' 
       href="${pageContext.request.contextPath}/infoAdmin/serviceItem/serviceItem_info.jsp?dataId=${item.id}">
      <FONT color="#330099">�޸�</FONT></A>
      <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px">
      </TD>
    </TR>
    </TR>
  
 </c:forEach> 
	<TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=6 align="right">&nbsp;
    <INPUT class=button  onclick="createNewServiceItem();" type=button value=����·����� >&nbsp;
    </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="ConfirmDel('ɾ����������ɾ���÷������������ֶμ����ݣ���ȷ��ɾ����');" 
    		type=button value="ɾ����ѡ" name="submitDelSelect">
    </TD></TR>
    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY>
        <TR>
          <TD>���ƣ�<FONT color=#ff6600>${requestScope.page.totalCount}</FONT>����¼&nbsp;ҳ�Σ�
          <FONT color=#ff6600>${param.pageNo}</FONT>/${requestScope.page.totalPageCount}&nbsp;ÿҳ��
          <FONT color=#ff6600>${requestScope.page.pageSize}</FONT>��</TD>
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
