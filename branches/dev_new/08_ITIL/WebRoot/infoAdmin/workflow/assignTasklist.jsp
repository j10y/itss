<%@ page contentType="text/html; charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD><TITLE>系统任务指派列表</TITLE>
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
		alert("请选择一个流程定义");
		return;
	}
	location = "${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=toAdd&def="+def;
}

function delTaskPreAssign(){
	if(confirm("确定要删除吗？此操作不可恢复！")){
		var definition = document.getElementById("definitionList");
		var def = definition.value;
		if(def==""){
			alert("请选择一个流程定义");
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
      border=0>&nbsp;<STRONG>系统任务指派列表</STRONG></FONT></TD></TR>
    <TR>
    <TD noWrap align=center  valign="buttom" bgColor=#ebf2f9 height=10>
    <br/>
    <FORM name="formSearch" action="${pageContext.request.contextPath}/workflow/taskPreAssign.do" method="post">
     	选择流程：
     	<select name="definiName"  id="definitionList" class="textfield">
           <option value=""></option>
            <c:forEach var="item" items="${requestScope.definitions}" varStatus="status">
             <option value="${item.name}"}>${item.description}</option>
           </c:forEach>
        </select>
    	<INPUT name="pageNo" type="hidden" value="${param.pageNo}">
         <INPUT name="methodCall" type="hidden" value="list">
      [<A href="javascript:query(1);">检索</A>]
      [<A href="javascript:addTaskPreAssign()">增加</A>]
      [<A href="javascript:delTaskPreAssign()">删除</A>]
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
    <STRONG><FONT color=#ffffff>部门名称</FONT></STRONG>
    </TD>
	<td nowrap width="25%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>流程定义名称</STRONG></font>
	</td>
	   <td nowrap  width="25%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>任务名称</STRONG></font>
	</td>
	</td>
	   <td nowrap  width="15%" bgColor=#8db5e9>
		<font color="#FFFFFF"><STRONG>流程角色</STRONG></font>
	</td>
   <TD  width= bgColor=#8db5e9 align="center"><STRONG>
   <FONT color=#ffffff>操作</FONT></STRONG> 
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
    <A onclick='changeAdminFlag("修改导航栏目")' 
       href="${pageContext.request.contextPath}/workflow/taskPreAssign.do?methodCall=toForm&id=${item.id}&pageNo=${param.pageNo}">
      <FONT color="#330099">修改</FONT></A>
      <!-- <INPUT name="id" type="checkbox" value="${item.id}" style="WIDTH: 13px; HEIGHT: 13px">-->
      </TD>
   
    </TR>
  
 </c:forEach> 
  

<TR>
    <TD noWrap bgColor=#d7e4f7 colSpan=10>
    
      <TABLE cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
        <TBODY>
        <TR>
          <TD>共计：<FONT color=#ff6600>${requestScope.page.totalCount}</FONT>条记录&nbsp;页次：<FONT 
            color=#ff6600>${param.pageNo}</FONT></STRONG>/${requestScope.page.totalPageCount}&nbsp;每页：<FONT 
            color=#ff6600>${requestScope.page.pageSize}</FONT>条</TD>
          <TD align=right>&nbsp;
          <c:set var="actionPath" value="${pageContext.request.contextPath}/admin/itemManage.do?methodCall=${param.methodCall}" scope="page"/>
          <c:set var="pageno" value="${param.pageNo}" scope="page"/>
          <c:set var="pagenc" value="2" scope="page"/><!-- 每页显示的分页页码数量=pagenc*2+1 -->
          <c:set var="pagenmin" value="${pageno-pagenc}" scope="page"/>
          <c:set var="pagenmax" value="${pageno+pagenc}" scope="page"/>
          <c:set var="pagec" value="${requestScope.page.totalPageCount}" scope="page"/>
          
          <c:if test="${pagenmin<1}">
            <c:set var="pagenmin" value="1" scope="page"/>
          </c:if>
          <c:if test="${pageno>1}"><!-- 如果页码大于1则显示(第一页) -->
            <a href="javascript:query(1);"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>9</font></a>
          </c:if>
          <c:if test="${pagenmin>1}"><!-- 如果页码开始值大于1则显示(更前) -->
            <a href="javascript:query(${(pageno-(pagenc*2-1))});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>7</font></a>
          </c:if>
          <c:if test="${pagenmax>pagec}"> <!-- '如果页码结束值大于总页数,则=总页数 -->
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
		  <c:if test="${pagenmax<pagec}"> <!-- '如果页码结束值小于总页数则显示(更后) -->
		   <a href="javascript:query(${pageno+(pagenc*2+1)});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>8</font></a>&nbsp;
		  </c:if>
		  <c:if test="${pageno<pagec}"> <!-- 如果页码小于总页数则显示(最后页)	 -->
		   <a href="javascript:query(${pagec});"><font style='FONT-SIZE: 14px; FONT-FAMILY: Webdings'>:</font></a>&nbsp;
		  </c:if>
		  
          &nbsp;跳到：第&nbsp;<INPUT class=textfield 
            onkeydown=if(event.keyCode==13)event.returnValue=false 
            style="WIDTH: 40px; HEIGHT: 18px" 
            onchange="if(/\D/.test(this.value)){alert('只能在跳转目标页框内输入整数！');this.value='1';}" 
            value=1 name="skipPage">&nbsp;页
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
