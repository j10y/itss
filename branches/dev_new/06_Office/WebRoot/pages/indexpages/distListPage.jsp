<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="contentDiv">
<table class="distList" cellpadding="0" cellspacing="0" rules="rows">
	<c:forEach var="dist" items="${distList}">
		<tr>
			<td width="26"><img
				src="<%=request.getContextPath()%>/images/menus/flow/task.png" /></td>
			<td><a href="#"
				onclick="App.MyDesktopClickWin('ArchivesDetailWin', {archivesId:${dist.archives.archivesId}, isDist:true})">${dist.archives.archivesNo}</a></td>
			<td nowrap="nowrap">
				<c:choose>
					<c:when test="${not empty dist.subject}">${dist.subject}</c:when>					
					<c:otherwise><font color='red'>${dist.subject}</font></c:otherwise>
				</c:choose>
			</td>
			<td width="80" nowrap="nowrap"><a><fmt:formatDate
				value="${dist.signTime}" pattern="yyyy-MM-dd" /></a></td>
		</tr>
	</c:forEach>
</table>
</div>
<div class="moreDiv"><span><a href="#"
	onclick="App.clickTopTab('ArchivesDistView')">更多...</a></span></div>