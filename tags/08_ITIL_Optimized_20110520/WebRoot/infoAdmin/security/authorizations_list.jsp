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
      border=0>&nbsp;<STRONG>系统资源授权管理：查询，添加、修改资源授权</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations">资源授权列表</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=toAddAuthorization">增加资源授权</A>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;系统资源授权管理&nbsp;-&gt;&nbsp;资源授权列表
  </TD>
  </TR>
  </TBODY>
</TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/admin/authorizationManage.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="20%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>资源授权名称</FONT></STRONG>
    </TD>
    <TD noWrap width="20%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>上级资源授权名称</STRONG></FONT>
    </TD>
    <TD noWrap width="50%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>资源授权关键字</STRONG></FONT>
    </TD><TD colspan="2" width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
    -->
    </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.authorizations}" varStatus="status">   
 <c:if test="${empty param.methodCall ||param.methodCall eq 'listAuthorizations' || param.methodCall eq 'toEditAuthorization' &&param.id ne item.id ||param.methodCall eq 'toAddAuthorization'}">
  
   <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor="#ebf2f9">
      
   <TD  width="20">${item.id }</TD> 
    <td nowrap valign="middle">
     ${item.name}
     </td>
     <td nowrap valign="middle">${item.right.keyName}</td>
     <td nowrap valign="middle">${item.resource.name}(${item.resource.shortClassMethodName})</td>
     
    <TD width=55 align="center">
    <A onclick='changeAdminFlag("修改资源授权")' 
       href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=toEditAuthorization&id=${item.id}">
      <FONT color="#330099">修改</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
   <TD width=55 align="center">
    <A onclick='changeAdminFlag("删除资源授权")' 
       href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=removeAuthorization&id=${item.id}">
      <FONT color="#330099">删除</FONT></A>
     
      </TD>
    </TR>
 </c:if> 
 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditAuthorization' && param.id eq item.id }">      
			
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
       
         <td width="20%"    valign="middle">
                      <input name="id" type="hidden" style="width:100px;" value="${authorization.id}" >           
                       <input name="name" style="width:100%;" value="${authorization.name}" class="textfield">             
                      </td>
                      <td width="20%"  height="25" valign="middle">
	                      <select name="rightId" style="width:100%;" >
	                      <option value=""></option>
	                     	 <c:forEach  var="item" items="${rights}">
	                          <option value="${item.id}" 
	                          <c:if test="${authorization.right.id eq item.id}"> selected</c:if>
	                          >${item.keyName}
	                      	</option>
                      		</c:forEach>
	                      </select>
                      </td>
                       <td nowrap height="25" valign="middle">
	                      <select name="resourceId" style="width:100%;" >
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${resources}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.resource.id eq item.id}"> selected</c:if>
		                          >${item.name}(${item.shortClassMethodName})
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
          
         
          <td nowrap height="25" align="center" valign="middle"> 
          <input type="button" name="Submit" value="提交" class="button" onclick="save();">
		<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations">取消</a></td>
		                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=removeAuthorization&id=${authorization.id}">删除</a>
		</td>
      </tr>
            
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddAuthorization'  }">      
 <form action="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=saveAuthorization" method="post">
         <tr bgColor=#ebf2f9>
       <td nowrap  height="25" valign="middle"> 
                        <td nowrap  height="25" valign="middle"> 
                         <input name="name" style="width:100%;" value="" >             
                         </td>
                      
                     <td nowrap height="25" valign="middle">
	                      <select name="rightId" style="width:100%;">
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${rights}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.right.id eq item.id}"> selected</c:if>
		                          >${item.keyName}
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
                       <td nowrap height="25" valign="middle">
	                      <select name="resourceId" style="width:100%;">
	                      <option value=""></option>
		                     	 <c:forEach  var="item" items="${resources}">
		                          <option value="${item.id}" 
		                          <c:if test="${authorization.resource.id eq item.id}"> selected</c:if>
		                          >${item.name}(${item.shortClassMethodName})
		                      </option>
	                      </c:forEach>
	                      </select>
                      </td>
         <td nowrap height="25" align="center" valign="middle">
           <input type="button" name="Submit" value="提交" class="button" onclick="save();">
		</td>
           <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=listAuthorizations">取消</a>
 </td>
   </tr>
  </form>
</c:if>    
   <script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="saveAuthorization";
	    xform.submit();
	  }
	  
	</script> 
  <script>
    function addModule(){
      window.location.href="${pageContext.request.contextPath}/admin/authorizationManage.do?methodCall=toAddAuthorization";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=4 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="addModule();" type=button value=增加资源授权 >&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
