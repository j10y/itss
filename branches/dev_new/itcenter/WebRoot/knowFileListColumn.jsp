<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@include file="/includefiles.jsp"%>
<%@include file="/header.jsp" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>服务管理</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
  </head>
  
  <body>
  <table width="940"  align="center" cellpadding="0" cellspacing="0" border="0" >
  <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
     <tr>
	   	 	<td colspan="3" style="font-family:宋体;font-size: 14px;font-weight:bold;color:#000000;padding-left: 10px;" height="30">	 	
	   	 	<c:if test="${param.columnType==1}">服务管理</c:if>
	   	 	<c:if test="${param.columnType==2}">业务支持</c:if>
	   	 	</td>
   	 </tr>
  <tr>
    <td width="50" height="350">&nbsp;</td>
    
    <td width="800" style="vertical-align: top" align="left">
   	 <c:if test="${param.columnType==1}"><table width="100%" border="0"    cellpadding="0" cellspacing="0" style="line-height: 25px;">  	
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=1" target="_blank" class="listLink">IT管理制度</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=2" target="_blank" class="listLink">IT管理工作体系</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=3" target="_blank" class="listLink">IT服务报告</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=5" target="_blank" class="listLink">IT违规通报</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		 	
   	 </table>
    </c:if>
    
    
    
       	 <c:if test="${param.columnType==2}"><table width="100%" border="0"    cellpadding="0" cellspacing="0" style="line-height: 25px;">  	
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=4" target="_blank" class="listLink">IT实战</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=6" target="_blank" class="listLink">他山之石</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=7" target="_blank" class="listLink">所获奖项</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/knowFileAction_getListInfoAction.action?infoLength=30&pageSize=10&columnType=8" target="_blank" class="listLink">外部专访</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   		 	
   	 </table>
    </c:if>
    
    
    
    
    </td>      
    <td >&nbsp;</td>
  </tr>  
    <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
  
</table>
<%@include file="/footer.jsp"%>
  </body>
</html>
