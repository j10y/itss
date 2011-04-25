<%@ page contentType="text/html; charset=utf-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML xmlns="http://www.w3.org/1999/xhtml"><HEAD>
<TITLE>后台管理登录</TITLE>
<%@include file="/includefiles.jsp"%>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="Copyright 2006-2010 - Ameav.com" name=copyright>
<META content=ʲϢϵͳ>
<META content="" name=Keywords>
<META content="" name=Description>
<STYLE type=text/css>
BODY {
	BACKGROUND-COLOR: #0666b3;
}

body,td,th {
	font-size: 12px;
}
</STYLE>
<META content="MSHTML 6.00.2900.3354" name=GENERATOR></HEAD>
<BODY style="MARGIN-TOP: 100px; FONT-SIZE: 12px">
<form action="${pageContext.request.contextPath}/j_login.do" method="POST">
<table align="center" width="680" height="305" class="text">
  <tr><td background="${pageContext.request.contextPath}/images/login_bg.jpg"><table width="100%" height="215" border="0" cellpadding="0" cellspacing="0">
    <tr>
      <td colspan="3"><div align="center"></div></td>
    </tr>
    <tr>
      <td height="19" colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td width="44%" height="121">&nbsp;</td>
      <td width="49%">
	  <%
		  String error = request.getParameter("login_error");
		  if(error!=null) {
			out.println("<p><font color=\"red\">");
			out.println(error);
			out.println("</font></p>"); 
		  }
		%>
		<br><font color="#FFFFFF">用户名
          <input name="j_username" type="text" value="admin" style="width:150px;"/>
          <br><br>
           &nbsp;&nbsp;密码
          <input name="j_password" type="password" value="admin" style="width:150px;"><br>
     	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <!-- <input type="checkbox" id="remember" name="j_remember_me"> ס -->
      	</font><br>
		 <div align="center" ><input name="submit" type="submit" value="登录">
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <input name="ddd" type="reset" value="重置">
		 </div>
		 </td>
      <td width="7%"></td>
    </tr>
    
  </table></td>
</tr></table></form>
</BODY></HTML>
