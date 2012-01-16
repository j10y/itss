<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
  %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String stationIp = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>${sysconfigmap['title']}</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link  rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/login.css">
	<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/default/easyui.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/JS/Jquery/themes/icon.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/PageGrid/pagegrid.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/CSS/TreePanel/TreePanel.css"/>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/jquery.easyui.min.js"></script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/JS/Jquery/common.js"></script>
	<script>
	function login(){
		document.location.href = "http://172.16.103.165:8090/cas3/logout?service=http://www.shopin.net"
	}
	function initpage(){
		document.getElementById("login_username").focus();
	}
	function BindEnter(obj)
 	{     //使用document.getElementById获取到按钮对象   
	  	var button = document.getElementById('loginbutton'); 
	 	if(obj.keyCode == 13) 
	 	{             
			button.click();  
	 		obj.returnValue = false; 
	 	} 
	} 
	</script>
  </head>
  
  <body  align="center"  class="loginMain" onkeydown="BindEnter(event)" onload="initpage()">
   <table align="center" class="loginMain">
	<tr>
		<td class="login">
			<div align="center">
			<table class="loginBox">
			<!-- CRM客户管理系统-->
			 	<tr><td class="top"><img src="${sysconfigmap['loginimg']}" border="0" height="98" width="424">
					
				</td>
				</tr> 
				<tr>
					<td class="bottom">
						<div align="center">
						<table class="loginTable">
							<tr>
								<td class="loginForm">
									<form id="form1" action="login_toLogin.action" method="post">
									<div align="center">
									<table class="lf">
										<tr>
											<td colspan="2"> 
											<font color="#ff0000">${error }</font> 
											</br>
											管理员邮箱：<a href="mailto:xinxi-yunwei@shopin.cn">xinxi-yunwei@shopin.cn</a>
											</td>
										</tr>
										
										<tr>
											<td align="center">
											<input type="button" class="loginButton" id="loginbutton" name="loginbutton"onclick="return login()"value="关闭">
											</td>
										</tr>
									</table>
									<span class="errortext"></span>
									</div>
									</form>
								</td>
							</tr>
							<tr>
								<td class="loginBottom">${sysconfigmap['foot']} 
								<br></td>
							</tr>
						</table>
						</div>			
					</td>
				</tr>
			</table>
			</div>
		</td>
	</tr>
	<tr><td class="fill"></td></tr>
</table>
  </body>
</html>