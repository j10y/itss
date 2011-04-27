<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
	</HEAD>
<body>
<c:if test="${param.isSpType eq 1}">
<jsp:forward page="/user/account/accountSummaryTable/personAccountSummarys.jsp?userId=${param.userId}"></jsp:forward>
</c:if>
<c:if test="${param.isSpType eq 2}">
<jsp:forward page="/user/account/accountSummaryTable/specailAccountSummarys.jsp?userId=${param.userId}"></jsp:forward>
</c:if>
<c:if test="${param.isSpType eq 3}">
<jsp:forward page="/user/account/accountSummaryTable/hrsAccountSummarys.jsp?userId=${param.userId}"></jsp:forward>
</c:if>
</body>	
</HTML>
