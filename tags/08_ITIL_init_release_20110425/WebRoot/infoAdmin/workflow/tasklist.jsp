<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>
    <title>任务列表</title>
    <%@include file="/includefiles.jsp"%>
  </head>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
  
  <body>
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>所有任务监控</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
    </TD>
  </TR>
  </TBODY>
  </TABLE>
  </br>
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>任务列表</STRONG></FONT>
    </TD>
  </TR>
  
 <c:forEach  var="task" items="${tasks}" varStatus="status">   
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   <TD >
     ${task.id }|${task.name }| <a href="javascript:audit(${task.id});">execute</a>&nbsp;
    <input id="${task.id }" style="width:80px"> <a href="javascript: reassign(${task.id})">reasign</a>&nbsp;
    <a href="javascript: history(${task.id})">history</a>
    <br><br>
   </TD>
  </TR>
 </c:forEach> 
  <c:if test="${length==0}">
  <tr onmouseover="this.style.backgroundColor = '#FFFFFF'" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
  <td>
    现在没有任务.<br/><br/>
  </td>
  </tr>
    </c:if>
  </TBODY>
 </TABLE>
  </body>
  
  <Script language="javaScript">
  
  function audit(id){
    var url = "${pageContext.request.contextPath}/wftask?id="+id;
    url += "&methodCall=audit&system";
  	window.open(url); 
  }
  
  function reassign(id){
    var url = "${pageContext.request.contextPath}/workflow/reassign.do?id="+id;
    url += "&actor="+document.getElementById(id).value;
    url += "&methodCall=reassign";
  	location = url;
  }
  
  function history(id){
    var url = "${pageContext.request.contextPath}/workflow/history.do?taskid="+id;
    url += "&methodCall=list";
  	location = url;
  }
  </Script>
</html>
