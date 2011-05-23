<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@include file="/includefiles.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>  
   <title>
   			   	 	<c:if test="${columnType==1}">IT公告</c:if>
			   	 	<c:if test="${columnType==5}">IT小贴士</c:if>
			   	 	<c:if test="${columnType==3}">IT培训</c:if>   	 	
			   	 	<c:if test="${columnType==4}">操作手册及常见问题</c:if>   	 	
   </title>    
  </head>
  <body>
  <%@include file="/header.jsp" %>
  <table width="940"  align="center" cellpadding="0" cellspacing="0" border="0" >
  <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
  <tr>
  	<td colspan="3">  	
  		  <table width="100%"  cellpadding="0" cellspacing="0" border="0" >
     		<tr>
		   	 	<td   style="font-family:宋体;font-size: 14px;font-weight:bold;color:#000000;padding-left: 10px;" height="30">
			   	 	<c:if test="${columnType==1}">IT公告</c:if>
			   	 	<c:if test="${columnType==5}">IT小贴士</c:if>
			   	 	<c:if test="${columnType==3}">IT培训</c:if>   	 	
			   	 	<c:if test="${columnType==4}">操作手册及常见问题</c:if>   	 	
		   	 	</td>
		   	 	<td align="right" >
		   	 	<form action="noticeAction_getSearchInfoAction.action" method="post">			   	 		
			   	 		<input  type="hidden" name="infoLength" value="30" />	   	 		
			   	 		<input type="hidden" name="pageSize" value="10" />	   	 		
			   	 		<input type="hidden" name="pageNum" value="1" />	   	 		
			   	 		<input type="hidden" name="columnType" value="${columnType}" />	   	 		
				   	 	<input type="text" class="ipt-t ipt-t-dft"  name="keyValue"  style="color:#999" title="标题搜索"/>
						<input type="submit" value="搜索"  class="button" />				
				</form>
				</td>	   	 	
	   	 </tr>  
	  </table>
  	
  	</td>
  </tr>

  <tr>
    <td width="50" height="350">&nbsp;</td>
    
    <td width="800" style="vertical-align: top" align="left">
   	 <table width="100%" border="0"    cellpadding="0" cellspacing="0" style="line-height: 25px;">
   	 <c:forEach var="notice" items="${pagination.data}">
   	
   	 	<tr>
	   	 	<td width="2%" ><img src="images/pot_12.gif"></img></td>
	   	 	<td width="2%" ></td>
	   	 	<td align="left"><a href="${pageContext.request.contextPath}/noticeAction_getContentInfoAction.action?id=${notice.id}" target="_blank" class="listLink"><%request.setAttribute("redKeyValue", "<font color=red>"+request.getAttribute("keyValue").toString()+"</font>");%>${fn:replace(notice.title,keyValue,redKeyValue)}</a>　</td>
	   	 	<td width="10%" class="bannertxt">${notice.createDate}</td>
   	 	</tr>
   	 	 </c:forEach>
   		 	
   	 </table>
    
    </td>      
    <td >&nbsp;</td>
  </tr>
    <tr>
  <td colspan="3" align="center"><span style="font-family:宋体;font-size: 14px;color:#666666;" >当前页数：${pagination.pageNum}　总页数：${pagination.pageCount}</span>　　
  		<c:if test="${pagination.pageNum!=1}">
			<c:url var="first" value="/noticeAction_getSearchInfoAction.action?infoLength=${infoLength}&pageSize=${pageSize}&columnType=${pagination.data[0].newNoticeType}&keyValue=${keyValue}">
				<c:param name="pageNum" value="1" />
			</c:url>
			<c:url var="previous" value="/noticeAction_getSearchInfoAction.action?infoLength=${infoLength}&pageSize=${pageSize}&columnType=${pagination.data[0].newNoticeType}&keyValue=${keyValue}">
				<c:param name="pageNum" value="${pagination.pageNum-1}" />
			</c:url>
			<a href="${first}" class='nomalLink'>第一页</a>　
   	   		<a href="${previous}" class='nomalLink'>上一页</a>　
  		</c:if>
		
		<c:if test="${pagination.pageNum<pagination.pageCount}">
			<c:url var="next" value="/noticeAction_getSearchInfoAction.action?infoLength=${infoLength}&pageSize=${pageSize}&columnType=${pagination.data[0].newNoticeType}&keyValue=${keyValue}">
				<c:param name="pageNum" value="${pagination.pageNum+1}" />
			</c:url>
			<c:url var="last" value="/noticeAction_getSearchInfoAction.action?infoLength=${infoLength}&pageSize=${pageSize}&columnType=${pagination.data[0].newNoticeType}&keyValue=${keyValue}">
				<c:param name="pageNum" value="${pagination.pageCount}" />
			</c:url>
			<a href="${next}" class='nomalLink'>下一页</a>　
  		    <a href="${last}"class='nomalLink'>最后一页</a>
		</c:if>
		</td>  	
  </tr>
    <tr>
  	<td colspan="3">&nbsp;</td>  	
  </tr>
  
</table>
<%@include file="/footer.jsp"%>
  </body>
</html>