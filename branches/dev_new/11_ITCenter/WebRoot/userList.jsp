<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="/includefiles.jsp"%>
<%@include file="/header.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>员工查询结果</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script type="text/javascript">
			function getDetail(ahref){
				alert(ahref.innerText);
			}
		</script>
	</head>

	<body>
		<table width="940" align="center" cellpadding="0" cellspacing="0"
			border="0">
			<tr>
				<td colspan="3">
					&nbsp;
				</td>
			</tr>
			<tr>
				
			</tr>
			<tr>
				<td width="50" height="350">
					&nbsp;
				</td>

				<td width="800" style="vertical-align: top" align="left">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						style="line-height: 25px;">
						<!-- 
						<c:forEach var="knowFile" items="${pagination.data}">

							<tr>
								<td width="2%">
									<img src="images/pot_12.gif"></img>
								</td>
								<td width="2%"></td>
								<td align="left">
									<a
										href="${pageContext.request.contextPath}/knowFileAction_getContentInfoAction.action?id=${knowFile.id}"
										target="_blank" class="listLink">${knowFile.name}</a>
								</td>
								<td width="10%" class="bannertxt">
									<fmt:formatDate value="${knowFile.createDate}"
										pattern="yyyy-MM-dd" />
								</td>
							</tr>
						</c:forEach>
						 -->
						 <tr>
						 	<td>账号</td>
						 	<td>姓名</td>
						 	<td>职位</td>
						 	<td>EMAIL</td>
						 </tr>
						 <c:if test="${not empty data }">
						 <c:forEach var="user" items="${data}">

							<tr>
								<td><a href="#" onclick="getDetail(this)">${user.uid }</a></td>
								<td>${user.cn }</td>
								<td>${user.title }</td>								
								<td>${user.mail }</td>
							</tr>
						</c:forEach>
						</c:if>
						<c:if test="${empty data }">
						 <c:forEach var="user" items="${data}">

							<tr>
								<td></td>
								<td></td>
								<td></td>								
								<td></td>
							</tr>
						</c:forEach>
						</c:if>
					</table>

				</td>
				<td>
					&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<span style="font-family: 宋体; font-size: 14px; color: #666666;">当前页数：${currentPage}
						总页数：${totalPage}</span>
						
					<c:if test="${currentPage!=1}">
						<c:url var="first"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="1" />
						</c:url>
						<c:url var="previous"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${currentPage-1}" />
						</c:url>
						<a href="${first}" class='nomalLink'>第一页</a>
						<a href="${previous}" class='nomalLink'>上一页</a>
					</c:if>

					<c:if test="${currentPage<totalPage}">
						<c:url var="next"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${currentPage+1}" />
						</c:url>
						<c:url var="last"
							value="${pageContext.request.contextPath}/userAction_getUserListByParam.action?firstFlag=0">
							<c:param name="currentPage" value="${totalPage}" />
						</c:url>
						<a href="${next}" class='nomalLink'>下一页</a>
						<a href="${last}" class='nomalLink'>最后一页</a>
					</c:if>
					
				</td>
			</tr>
			<tr>
				<td colspan="3">
					&nbsp;
				</td>
			</tr>

		</table>
		<%@include file="/footer.jsp"%>
	</body>
</html>
