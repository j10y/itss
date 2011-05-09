<%@ page language="java" pageEncoding="UTF-8"%>

<% 
	String username=(String)request.getAttribute("username");
	String password=(String)request.getAttribute("password");
	System.out.println("登录前SESSION【"+request.getSession().getId()+"】");
%>
<form name="form1" method="post" action="/j_login.do">
			<input type="hidden" name="j_username" value="<%=username%>">
			<input type="hidden" name='j_password' value="<%=password%>">
</form>
<script>
	document.form1.submit();
</script>