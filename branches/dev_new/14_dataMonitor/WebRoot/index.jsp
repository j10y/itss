<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>
<%
	AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal(); 
	String username = principal.getName(); 
	//业务系统处理逻辑
	if(username==null || username.equals("")){ 
		response.sendRedirect(request.getContextPath()+ "/index.jsp");
	}else{
		response.sendRedirect(request.getContextPath()+ "/login_toLoginForUserName.action?login_username="+username);
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

