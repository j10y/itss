<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
  <head>
    <title>��������</title> 
    <%@include file="/includefiles.jsp"%>  
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/CssAdmin.css">
    
  </head>  
  <body>
  <TABLE cellSpacing=1 cellPadding=3 width="100%" bgColor=#6298e1 border=0>
  <TBODY>
  <TR>
    <TD noWrap height=24><FONT color=#ffffff><IMG height=18 
      src="${pageContext.request.contextPath}/images/Explain.gif" width=18 align=absMiddle 
      border=0>&nbsp;<STRONG>��������</STRONG></FONT></TD></TR>
   <TR>
    <TD noWrap align=middle bgColor=#ebf2f9 height=36>
      <TABLE cellSpacing=0 width="100%" border=0>
        <TBODY>
        <TR>
        <td>
         <form name="UploadForm" enctype="multipart/form-data" method="post" action="${pageContext.request.contextPath}/upload">  
		    ���ش���ļ���<input type="file" name="File1" size="20" maxlength="20">  
		 <input type="submit"value="�ϴ�">  
        </form> 
        </td>
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  </TR>
  </TBODY>
  </TABLE>
  </body>
</html>
