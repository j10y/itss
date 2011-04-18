<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>

<html>
  <head>    
    <title>启动流程</title>
    <%@include file="/includefiles.jsp"%>
  </head>

  <body>
    <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>流程启动</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
    </TD>
  </TR>
  </TBODY>
  </TABLE>
    <table align="center" width="400" border="0"><tr><td>
    <br><br>
    proccess started, instance id is: ${id }<br><br>
    <a href="${pageContext.request.contextPath}/test/jbpm/index.jsp">back</a>
    </td></tr></table>
  </body>
</html>
