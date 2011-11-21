<%@ page language="java" import="java.util.*,net.shopin.util.PropertiesUtil" pageEncoding="utf-8"%>
<%-- first destroy the web application's session --%>
<%
	System.out.println("退出当前应用");
	request.getSession().invalidate();
	//System.out.println(request.getUserPrincipal().getName());
	response.sendRedirect(PropertiesUtil.getProperties("casLogoutUrl"));
%>
