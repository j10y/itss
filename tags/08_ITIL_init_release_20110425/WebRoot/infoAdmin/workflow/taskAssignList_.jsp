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

<body>

<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>ϵͳ����ָ���б�</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     	<select name="definiName"  class="textfield">
           <option value=""></option>
            <c:forEach var="item" items="${requestScope.definations}" varStatus="status">
             <option value="${item.id}"}>${item.description}</option>
           </c:forEach>
        </select>
     <A href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers">��������ָ��</A>
      <INPUT class="button" type="button" onclick="query(1);" value="����" > 
       </TD>
      </TR>
   </TBODY>
 </TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=10>&nbsp;
  </TD>
  </TR>
  </TBODY>
</TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/admin/userRoleManage.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
  
  <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
   <TD width="20%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>���̶�������</FONT></STRONG>
    </TD>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>��������</STRONG></font>
	</td>
	   <td nowrap  width="20%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>Ĭ��������</STRONG></font>
	</td>
	<td nowrap width="50%"  bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>��������</STRONG></font>
	</td>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>����ʼʱ��</STRONG></font>
	</td>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>������ֹʱ��</STRONG></font>
	</td>
    <TD colspan="2" width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>����</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=ȫ name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=�� name=buttonOtherSelect> 
    -->
    </TD>
    
    
    
  </TR>
  
 <c:forEach  var="item" items="${requestScope.assignTaskes}" varStatus="status">

     <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
      <td nowrap valign="middle">${item.id}</td>
     
      <td nowrap valign="middle">
		<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">${item.definitionName}
     </a>
		</td>
		<td nowrap valign="middle">
			${item.taskName}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.actorId}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.proxyId}
		</td>
		<td nowrap valign="middle">
			${item.proxyBegin}
		</td>
      	<td nowrap valign="middle">
			${item.proxyEnd}
		</td>
      <td nowrap height="25" align="center" valign="middle">
          <a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">�޸�</a>
                </td>
                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=removeUsers&id=${item.id}">ɾ��</a>
		</td>
   </tr>

 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditUsers' && param.id eq item.id }">      
					
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
      <td nowrap   valign="middle">
      <input name="id" type="hidden" style="width:100px;" value="${resource.id}" >           
       <input name="name" style="width:100%;" value="${resource.name}" >             
      </td>
      <td  nowrap height="25" valign="middle"> 
       <input name="type" style="width:100%;" value="${resource.type}" >              
      </td>
      <td nowrap  height="25" valign="middle"> 
       <input name="className" style="width:100%;" value="${resource.className}">              
      </td>
       <td nowrap  height="25" valign="middle"> 
       <input name="methodName" style="width:100%;" value="${resource.methodName}">              
        </td>
       <td nowrap height="25" align="center" valign="middle">
       <input type="button" name="Submit" value="�ύ" class="button" onclick="save();">
	<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">ȡ��</a></td>
                <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=removeUsers&id=${resource.id}">ɾ��</a>
	</td>  
          
	
      </tr>
                    
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddUsers'  }">      
		<form action="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=saveResource" method="post">
              <tr bgcolor="#ebf2f9">
              <td nowrap  height="25" valign="middle"> 
                 ${item.id}          
                </td>
				<td nowrap  height="25" valign="middle"> 
                 <input name="name" style="width:100%;" value="" >             
                </td>
                <td nowrap  height="25" valign="middle"> 
                 <input name="type" style="width:100%;" value="" >              
                </td>
                <td nowrap  height="25" valign="middle"> 
                 <input name="className" style="width:100%;" value="">              
                </td>
                 <td nowrap  height="25" valign="middle"> 
                 <input name="methodName" style="width:100%;" value="">              
                </td>
                
              
                <td height="25" align="center" colspan="2" valign="middle">
                
                <input type="submit" name="Submit" class="button" value="����" onclick="save();">
 			<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">ȡ��</a>
		</td>
		</tr>
		</form>
</c:if>                  
	<script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="saveResource";
	    xform.submit();
	  }
	  
	</script> 
   
  <script>
    function add(){
      window.location.href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=6 align="right">&nbsp;
   
   </TD>
    <TD colSpan=3 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="add();" type=button value=��������ָ��>&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
