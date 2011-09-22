<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<HTML>
	<HEAD>
	</HEAD>
<body >
<iframe frameborder="0" scrolling="yes"width="99%" height="49%"src="${pageContext.request.contextPath}/reportJsp/showReport.jsp?raq=/customerserviceCheck.raq&typeid=19&authMonth=${param.curMonth}"></iframe>
<iframe frameborder="0" scrolling="yes"width="99%" height="49%"src="${pageContext.request.contextPath}/reportJsp/showReport.jsp?raq=/customerserviceCheck_s.raq&typeid=19&authMonth=${param.curMonth}"></iframe>
</body>	
</HTML>