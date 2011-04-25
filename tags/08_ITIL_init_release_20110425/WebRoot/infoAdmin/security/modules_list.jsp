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
      border=0>&nbsp;<STRONG>系统权限管理：查询，添加、修改模块</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
     <a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=listModules">模块列表</a>&nbsp;
     <A href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=toAddModule">增加模块</A>
       </TD>
      </TR>
   </TBODY>
 </TABLE>
 
 
<TABLE cellSpacing=1 cellPadding=0 width="100%" border=0>
 <TBODY>
 <TR>
 <TD height=30>系统后台管理&nbsp;-&gt;&nbsp;系统权限管理&nbsp;-&gt;&nbsp;模块列表
  </TD>
  </TR>
  </TBODY>
</TABLE>


<TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <FORM name="formDel" action="${pageContext.request.contextPath}/admin/moduleManage.do" method="post">
  <input type="hidden" name="methodCall" value="remove">
  <input type="hidden" name="pageNo" value="${param.pageNo}">
 <TBODY>
  <TR>
    <TD noWrap width="20" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>ID</STRONG></FONT>
    </TD>
    <TD width="20%" bgColor=#8db5e9 >
    <STRONG><FONT color=#ffffff>模块名称</FONT></STRONG>
    </TD>
    <TD noWrap width="20%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>上级模块名称</STRONG></FONT>
    </TD>
    <TD noWrap width="50%" bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>模块关键字</STRONG></FONT>
    </TD><TD colspan="2" width=76 bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
    <!-- <INPUT class=button id=submitAllSearch style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckAll(this.form) type=button value=全 name=buttonAllSelect> 
	<INPUT class=button id=submitOtherSelect style="WIDTH: 16px; HEIGHT: 18px" onclick=CheckOthers(this.form) type=button value=反 name=buttonOtherSelect> 
    -->
    </TD>
  
  </TR>
  
 <c:forEach  var="item" items="${requestScope.modules}" varStatus="status">   
 <c:if test="${empty param.methodCall ||param.methodCall eq 'listModules' || param.methodCall eq 'toEditModule' &&param.id ne item.id ||param.methodCall eq 'toAddModule'}">
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   
                      
    <TD  width="20">${item.id }</TD>
    <TD noWrap>${item.name }</TD>
    
    <TD noWrap>${item.parentModule.name }</TD>
    <TD noWrap>${item.serviceKeyName }</TD>
    
    <TD width=55 align="center">
    <A onclick='changeAdminFlag("修改模块")' 
       href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=toEditModule&id=${item.id}">
      <FONT color="#330099">修改</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
   <TD width=55 align="center">
    <A onclick='changeAdminFlag("删除模块")' 
       href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=removeModule&id=${item.id}">
      <FONT color="#330099">删除</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px"> -->
      </TD>
    </TR>
 </c:if> 
 
 <c:if test="${!empty param.methodCall && param.methodCall eq 'toEditModule' && param.id eq item.id }">      
					
      <tr bgColor=#ebf2f9>
     <TD  width="20">${item.id }</TD>
       
        <td nowrap   valign="middle">
        <input name="id" type="hidden" value="${module.id}" class="textfield">           
         <input name="name" style="width:100%;" value="${module.name}" class="textfield">             
        </td>
        <td nowrap height="25" valign="middle" >
         <select name="parentModule" style="width:100%;" class="textfield">
         <option value=""></option>
         <c:forEach  var="item" items="${parentModules}">
             <option value="${item.id}" 
             <c:if test="${module.parentModule.id eq item.id}"> selected</c:if>
             >${item.name}</option>
         </c:forEach>
         </select>
        </td>
        <td  nowrap height="25" valign="middle"> 
         <input name="serviceKeyName" style="width:100%;" value="${module.serviceKeyName}" class="textfield">              
          </td>
         
          <td nowrap height="25" align="center" valign="middle">
          <input type="button" name="Submit" value="提交" class="button" onclick="save();">
		<a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=listModules">取消</a></td>
		                <td nowrap height="25" align="center" valign="middle">
		<a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=removeModule&id=${module.id}">删除</a>
		</td>
      </tr>
                    
</c:if>
</c:forEach> 

 <c:if test="${!empty param.methodCall && param.methodCall eq 'toAddModule'  }">      
 <form action="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=saveModule" method="post">
         <tr bgColor=#ebf2f9>
        <TD  width="20">${item.id }</TD>
          
           <td  valign="middle">      
            <input name="name" style="width:100%;" value="" class="textfield">             
           </td>
           <td nowrap height="25" valign="middle" >
            <select name="parentModule" style="width:100%;" class="textfield">
            <option value=""></option>
            <c:forEach  var="item" items="${parentModules}">
                <option value="${item.id}" 
                <c:if test="${module.parentModule.id eq item.id}"> selected</c:if>
                >${item.name}</option>
            </c:forEach>
            </select>
           </td>
           <td  nowrap height="25" valign="middle"> 
            <input name="serviceKeyName" style="width:100%;" value="" class="textfield">              
           </td>
           
           <td nowrap height="25" align="center" valign="middle">
           <input type="button" name="Submit" value="提交" class="button" onclick="save();">
		</td>
           <td nowrap height="25" align="center" valign="middle">
	<a href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=listModules">取消</a>
 </td>
   </tr>
  </form>
</c:if>    
   <script>
	  function save(){
	    var xform = document.formDel;
	    xform.methodCall.value="saveModule";
	    xform.submit();
	  }
	  
	</script> 
  <script>
    function addModule(){
      window.location.href="${pageContext.request.contextPath}/admin/moduleManage.do?methodCall=toAddModule";
    }
  </script>
  <TR>
    <TD noWrap bgColor=#ebf2f9 colSpan=4 align="right">&nbsp;
   
   </TD>
    <TD colSpan=2 noWrap bgColor=#ebf2f9>
    <INPUT class=button  onclick="addModule();" type=button value=增加模块 >&nbsp;
    </TD></TR>

    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
       
    </TD>
   </TR>

</TBODY></TABLE></TD></TR></FORM></TBODY></TABLE>

 
</body>
</html>
