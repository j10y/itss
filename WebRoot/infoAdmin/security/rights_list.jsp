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

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳȨ�޹�����ѯ����ӡ��޸�Ȩ��</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights">Ȩ���б�</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=toAddRight">����Ȩ��</A>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>ϵͳ��̨����&nbsp;-&gt;&nbsp;ϵͳȨ�޹���&nbsp;-&gt;&nbsp;Ȩ���б�
  </TD>
  </TR>
  </TBODY>
</TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/admin/rightManage.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="20%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>Ȩ������</FONT></STRONG>
    </TD>
    <TD noWrap width="20%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>�ϼ�Ȩ������</STRONG></FONT>
    </TD>
    <TD noWrap width="50%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>Ȩ�޹ؼ���</STRONG></FONT>
    </TD><TD colspan="2" width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>����</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect> 
    -->
    </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.rights}" varStatus="status">   
 <c:if test="${empty param.methodCall ||param.methodCall eq 'listRights' || param.methodCall eq 'toEditRight' &&param.id ne item.id ||param.methodCall eq 'toAddRight'}">
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   
                      
    <TD  width="20">${item.id }</TD>
    <td nowrap valign="middle">
     ${item.name}
     </td>
     <td nowrap valign="middle">${item.keyName}</td>
     <td nowrap valign="middle">${item.descn}</td>
    <TD width=55 align="center">
    <A onclick='changeAdminFlag("�޸�Ȩ��")' 
       href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=toEditRight&id=${item.id}">
      <FONT color="#330099">�޸�</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
   <TD width=55 align="center">
    <A onclick='changeAdminFlag("ɾ��Ȩ��")' 
       href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=removeRight&id=${item.id}">
      <FONT color="#330099">ɾ��</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
    </TR>
 </c:if> 
 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditRight' && param.id eq item.id }">      
			
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
       
        <td nowrap   valign="middle">
          <input name="id" type="hidden" style="width:100px;" value="${right.id}" >           
           <input name="name" style="width:100%;" value="${right.name}" class="textfield">             
          </td>
          <td  nowrap height="25" valign="middle"> 
           <input name="keyName" style="width:100%;" value="${right.keyName}" class="textfield">              
          </td>
          <td nowrap  height="25" valign="middle"> 
           <input name="descn" style="width:100%;" value="${right.descn}" class="textfield">              
          </td>
          
         
          <td nowrap height="25" align="center" valign="middle"> 
          <input type="button" name="Submit" value="�ύ" class="button" onclick="save();">
		<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights">ȡ��</a></td>
		                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=removeRight&id=${right.id}">ɾ��</a>
		</td>
      </tr>
            
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddRight'  }">      
 <form action="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=saveModule" method="post">
         <tr bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
          
           <td nowrap  height="25" valign="middle"> 
            <input name="name" style="width:100%;"  class="textfield">             
           </td>
           <td nowrap  height="25" valign="middle"> 
            <input name="keyName" style="width:100%;" class="textfield" >              
           </td>
           <td nowrap  height="25" valign="middle"> 
            <input name="descn" style="width:100%;" value="" class="textfield">              
           </td>
           
           <td nowrap height="25" align="center" valign="middle">
           <input type="button" name="Submit" value="�ύ" class="button" onclick="save();">
		</td>
           <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=listRights">ȡ��</a>
 </td>
   </tr>
  </form>
</c:if>    
   <script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="saveRight";
	    xform.submit();
	  }
	  
	</script> 
  <script>
    function addModule(){
      window.location.href="${pageContext.request.contextPath}/admin/rightManage.do?methodCall=toAddRight";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=4 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="addModule();" type=button value=����Ȩ�� >&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
