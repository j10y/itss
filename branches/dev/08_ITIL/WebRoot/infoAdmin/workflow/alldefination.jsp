<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>   
    <title>流程定义列表</title>
    <%@include file="/includefiles.jsp"%>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
    
  </head>
    <body>
    <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>流程定义列表</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
    </TD>
  </TR>
  </TBODY>
  </TABLE>
  <br />
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap  bgColor=#8db5e9>
    <FONT color=#ffffff><STRONG>启动流程实例</STRONG></FONT>
    </TD>
  </TR>

 <c:forEach  var="def" items="${definations}" varStatus="status">   
  <TR onmouseover="this.style.backgroundColor = '#FFFFFF'" style="CURSOR: hand" 
      onmouseout="this.style.backgroundColor = ''" bgColor=#ebf2f9>
   <TD >
    name: ${def.description}[${def.name}.${def.version}] --> 
    <a href="${pageContext.request.contextPath}/workflow/preassign.do?pdn=${def.name}&methodCall=preassign">
    开始流程. </a>
    <br><br>
   </TD>
  </TR>
 </c:forEach>
  </TBODY>
 </TABLE> 
  </body>
</html>
