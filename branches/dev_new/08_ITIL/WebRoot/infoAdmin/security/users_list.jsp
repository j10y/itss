<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>系统主表</TITLE>
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
      border=0>&nbsp;<STRONG>系统用户管理：查询，添加、修改用户，为用户授予角色</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">系统用户列表</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toAddUsers">增加系统用户</A>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;系统权限管理&nbsp;-&gt;&nbsp;系统用户列表
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
    <STRONG><FONT color=#ffffff>姓名</FONT></STRONG>
    </TD>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>用户名</STRONG></font>
	</td>
	   <td nowrap  width="20%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>密码</STRONG></font>
	</td>
	<td nowrap width="50%"  bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>电子邮件</STRONG></font>
	</td>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>是否可用</STRONG></font>
	</td>
	<td nowrap bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>是否特殊用户</STRONG></font>
	</td>
    <TD colspan="2" width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
    -->
    </TD>
    
    
    
  </TR>
  
 <c:forEach  var="item" items="${requestScope.users}" varStatus="status">
<c:if test="${empty param.methodCall ||param.methodCall eq 'listUsers' || param.methodCall eq 'toEditUsers' &&param.id ne item.id ||param.methodCall eq 'toAddUsers'}">

     <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
      <td nowrap valign="middle">${item.id}</td>
     
      <td nowrap valign="middle">
		<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">${item.realName}
     </a>
		</td>
		<td nowrap valign="middle">
			${item.userName}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.password}&nbsp;
		</td>
		<td nowrap valign="middle">
			${item.email}
		</td>
		<td nowrap valign="middle">
			
			<c:if test="${item.enabled eq 1}">是</c:if>
			<c:if test="${item.enabled eq 0}">否</c:if>
			
		</td>
		<td nowrap valign="middle">
			
			<c:if test="${item.specialUser eq 1}">是</c:if>
			<c:if test="${item.specialUser eq 0}">否</c:if>
			
		</td>
		
      
      <td nowrap height="25" align="center" valign="middle">
          <a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=toEditUsers&id=${item.id}">修改</a>
                </td>
                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=removeUsers&id=${item.id}">删除</a>
		</td>
   </tr>
</c:if>
 
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
       <input type="button" name="Submit" value="提交" class="button" onclick="save();">
	<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">取消</a></td>
                <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=removeUsers&id=${resource.id}">删除</a>
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
                
                <input type="submit" name="Submit" class="button" value="保存" onclick="save();">
 			<a href="${pageContext.request.contextPath}/admin/userRoleManage.do?methodCall=listUsers">取消</a>
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
    <TD noWrap bgColor=#ebf2f9 colSpan=7 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="add();" type=button value=增加系统用户>&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
