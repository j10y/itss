<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>ϵͳ����</TITLE>
<%@include file="/includefiles.jsp"%>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GBK"/> 
<META content="MSHTML 6.00.2900.5583" name=GENERATOR>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
<script language="javascript" src="${pageContext.request.contextPath}/js/Admin.js"></script>
</HEAD>

<body>
<!--  
<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳȨ�޹�����ѯ����ӡ��޸�ģ��</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=listPageModeNodes">ģ���б�</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=toAddPageModeNode">����ģ��</A>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
--> 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>ϵͳ��̨����&nbsp;-&gt;&nbsp;ϵͳ����������&nbsp;-&gt;&nbsp;������ҳ��
  </TD>
  </TR>
  </TBODY>
</TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/admin/pageModelNode.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="40%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>�ڵ�����</FONT></STRONG>
    </TD>
    <TD noWrap width="40%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ҳ��ģ��</STRONG></FONT>
    </TD>
    <TD noWrap colspan="2"  bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>����</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect> 
    -->
    </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.pageModelNodes}" varStatus="status">   
 <c:if test="${empty param.methodCall ||param.methodCall eq 'listPageModeNodes' || param.methodCall eq 'toEditPageModeNode' &&param.id ne item.id ||param.methodCall eq 'toAddPageModeNode'}">
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   
                      
    <TD  width="20">${item.id }</TD>
    <TD noWrap>${item.nodeName }</TD>
    
    <TD noWrap>${item.pageModel.title }&nbsp;${item.pageModel.name }</TD>
    <TD noWrap align="center">
    <A onclick='changeAdminFlag("�޸�ģ��")' 
       href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=toEditPageModeNode&id=${item.id}">
      <FONT color="#330099">�޸�</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
   <TD noWrap align="center">
    <A onclick='changeAdminFlag("ɾ��ģ��")' 
       href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=removePageModeNode&id=${item.id}">
      <FONT color="#330099">ɾ��</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
    </TR>
 </c:if> 
 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditPageModeNode' && param.id eq item.id }">      
					
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
       
        <td nowrap   valign="middle">
        <input name="id" type="hidden" value="${pageModelNode.id}" class="textfield">           
         <input name="nodeName" style="width:100%;" value="${pageModelNode.nodeName}" class="textfield">             
        </td>
        <td nowrap height="25" valign="middle" >
         <select name="pageModel" style="width:100%;" class="textfield">
         <option value=""></option>
         <c:forEach  var="item" items="${pageModels}">
             <option value="${item.id}" 
             <c:if test="${pageModelNode.pageModel.id eq item.id}"> selected</c:if>>
             ${item.title }&nbsp;${item.name}</option>
         </c:forEach>
         </select>
        </td>
          <td nowrap height="25" align="center" valign="middle">
          <input type="button" name="Submit" value="�ύ" class="button" onclick="save();">
		<a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=listPageModeNodes">ȡ��</a></td>
		                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=removePageModeNode&id=${module.id}">ɾ��</a>
		</td>
      </tr>
                    
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddPageModeNode'  }">      
 <form action="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=savePageModeNode" method="post">
         <tr bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
          
           <td  valign="middle">      
            <input name="nodeName" style="width:100%;" value="" class="textfield">             
           </td>
           <td nowrap height="25" valign="middle" >
            <select name="pageModel" style="width:100%;" class="textfield">
            <option value=""></option>
            <c:forEach  var="item" items="${pageModels}">
                <option value="${item.id}" 
                <c:if test="${pageModelNode.pageModel.id eq item.id}"> selected</c:if>
                >${item.title }&nbsp;${item.name}</option>
            </c:forEach>
            </select>
           </td>
           <td nowrap height="25" align="center" valign="middle">
           <input type="button" name="Submit" value="�ύ" class="button" onclick="save();">
		</td>
           <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=listPageModeNodes">ȡ��</a>
 </td>
   </tr>
  </form>
</c:if>    
   <script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="savePageModeNode";
	    xform.submit();
	  }
	  
	</script> 
  <script>
    function addModule(){
      window.location.href="${pageContext.request.contextPath}/admin/pageModelNode.do?methodCall=toAddPageModeNode";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=3 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="addModule();" type=button value=���ӹ�����ҳ�� >&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
