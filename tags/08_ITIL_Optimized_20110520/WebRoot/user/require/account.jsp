<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.zsgj.itil.account.action.*" %>
<HTML>
	<HEAD>
	</HEAD>
<body>
<jsp:forward page="/account/myAccount.do?methodCall=query&flag=DCIT"></jsp:forward>
</body>	
</HTML>