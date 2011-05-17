<%@ page language="java" import="java.util.*,net.shopin.Constants" pageEncoding="utf-8"%>
<%-- first destroy the web application's session --%>
<%
	System.out.println("退出当前应用");
	request.getSession().invalidate();
	System.out.println(request.getUserPrincipal().getName());
	response.sendRedirect(Constants.CAS_LOGOUT_URL);
%>
