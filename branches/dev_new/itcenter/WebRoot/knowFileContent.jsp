<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="/includefiles.jsp"%>
<%@include file="/header.jsp" %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>${knowFile.name}</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
  </head>
  
  <body>
  <table width="940" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
     <tr>
	   	 	<td colspan="3" align="center" style="font-family:宋体;font-size: 16px;font-weight:bold;color:#000000;" height="60">${knowFile.name}</td>
   	 </tr> 
      <tr>
	   	 	<td colspan="3" align="center" style="font-family:宋体;font-size: 12px; color:#666666";>发布日期：<fmt:formatDate value="${knowFile.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> 　　　 该文档已被阅读：${knowFile.readTimes} 次</td>
   	 </tr>   	 
   	 <tr><td colspan="3" style="border-top: 1px solid red" >&nbsp;</td></tr>
    <tr>
    <td width="50" height="300">&nbsp;</td>
    <td style="font-family:宋体;font-size: 12px;color:#333333;line-height: 25px; vertical-align: top" align="left">
    	${knowFile.descn}
    </td>
    <td width="50">&nbsp;</td>
  </tr>
    <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
  
</table>
<%@include file="/footer.jsp"%>
 
  </body>
</html>
