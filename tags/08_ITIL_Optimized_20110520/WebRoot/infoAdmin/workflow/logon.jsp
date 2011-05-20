<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
  <head>
    <title>用户登录</title>
    <%@include file="/includefiles.jsp"%>
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
    
  </head>
  
  <body>
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>用户任务</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=left bgColor=#ebf2f9 height=36>
    <form action="${pageContext.request.contextPath}/workflow/listTask.do">
     	<input type="hidden" value="list" name="methodCall">
    	用户名:<input name="actor" value="ernie">
    	<input type="submit" value="任务列表">
    </form>
    </TD>
  </TR>
  </TBODY>
  </TABLE>
  </body>
</html>
