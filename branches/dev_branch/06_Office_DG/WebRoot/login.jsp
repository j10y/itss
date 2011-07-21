<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.xpsoft.core.util.AppUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 
<title>网上办公系统</title>
 -->
<title>欢迎登录<%=AppUtil.getCompanyName()%>协同办公系统</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css" />
		<%
		response.addHeader("__timeout","true");
		String codeEnabled=(String)AppUtil.getSysConfig().get("codeConfig");
		if(StringUtils.isEmpty(codeEnabled)){//若当前数据库没有配置验证码参数
			codeEnabled="1";//代表需要输入
		}
		request.setAttribute("codeEnabled", new Integer(codeEnabled));
		%>
		<script type="text/javascript">
			var __ctxPath="<%=request.getContextPath() %>";
			var __loginImage=__ctxPath+"<%=AppUtil.getCompanyLogo()%>";
		</script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/adapter/ext/ext-base.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-all.gzjs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/ext3/ext-lang-zh_CN.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
<script type="text/javascript">
		function checkForm(){
				var username = document.getElementById("username").value;
				var password = document.getElementById("password").value;
				var checkCode = "";
				if(username==""){
						Ext.Msg.alert("提示", "用户名不能为空！");
						return;
				}
				if(password==""){
					Ext.Msg.alert("提示", "密码不能为空！");
						return;
				}
				<c:if test="${codeEnabled eq 1}">
					checkCode = document.getElementById("checkCode").value;
					if(checkCode==""){
							Ext.Msg.alert("提示", "验证码不能为空！");
							return;
					}
				</c:if>
				Ext.Ajax.request({
					url : __ctxPath + "/login.do",
					method :"Post",
					params : {username:username,
						password:password,
						checkCode:checkCode
						},
					success : function(d, g) {
						var f = Ext.util.JSON.decode(d.responseText);
						handleLoginResult(f);
					}
				});
		}
		function handleLoginResult(a) {
			if (a.success) {
				var b = new Ext.ProgressBar({
					text : "正在登录..."
				});
				b.show();
				window.location.href = __ctxPath + "/index.jsp";
			} else {
				Ext.MessageBox.show({
					title : "操作信息",
					msg : a.msg,
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		}
		function refeshCode() {
			var a = document.getElementById("loginCode");
			a.innerHTML='<img border="0" height="30" width="150" src="' + __ctxPath
					+ '/CaptchaImg?rand=' + Math.random() + '"/>';
		}
		document.onkeydown = function(e){
			if(!e) e = window.event;//火狐中是window.event
			if((e.keyCode || e.which) == 13){
					checkForm();
			}
		}
</script>
<link href="<%=request.getContextPath() %>/css/css.css" rel="stylesheet" type="text/css" />
</head>

<body>
<form id="loginForm" method="post" onsubmit="checkForm()">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="27%" background="images/login/left_bg1.jpg">&nbsp;</td>
    <td width="49%" background="images/login/left_bg1.jpg"><img src="images/login/1.jpg" width="498" height="345" /></td>
    <td width="24%" background="images/login/left_bg1.jpg">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td width="4%"><img src="images/login/left_1.jpg" width="21" height="75" /></td>
        <td width="95%"><div align="center"><img src="images/login/logo.jpg" width="182" height="33" /></div></td>
        <td width="1%"><img src="images/login/right_1.jpg" width="17" height="75" /></td>
      </tr>
      <tr>
        <td background="images/login/left_b1.jpg">&nbsp;</td>
        <td><table width="100%" border="0" align="center" cellpadding="0" cellspacing="5" bgcolor="e2f0f9">
            <tr>
              <td class="blue">&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td width="28%" class="blue"><div align="right">用户名：</div></td>
              <td width="59%"><input id="username" name="username" type="text" class="black" value="" size="27" /></td>
              <td width="13%">&nbsp;</td>
            </tr>
            <tr>
              <td class="blue"><div align="right">密　码：</div></td>
              <td><input id="password" name="password" type="password" class="black" value="" size="27" /></td>
              <td>&nbsp;</td>
            </tr>
            <c:if test="${codeEnabled eq 1}">
            <tr>
              <td class="blue"><div align="right">验证码 ：</div></td>
              <td><input id="checkCode" name="checkCode" type="text" class="black" value="" size="27" /></td>
              <td>&nbsp;</td>
            </tr>
            <tr>
              <td class="blue"></td>
              <td id="loginCode"><img border="0" height="30" width="150" src="<%=request.getContextPath() %>/CaptchaImg"/><a href="javascript:refeshCode()">换一张</a></td>
              <td>&nbsp;</td>
            </tr>
          	</c:if>
            <tr>
              <td>&nbsp;</td>
              <td><table width="100%" height="32" border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td width="12%"><label>
                      <input type="checkbox" name="_spring_security_remember_me" value="checkbox" />
                    </label></td>
                    <td width="88%" class="black">记住密码</td>
                  </tr>
              </table></td>
              <td>&nbsp;</td>
            </tr>
        </table></td>
        <td background="images/login/right_b1.jpg">&nbsp;</td>
      </tr>
      <tr>
        <td background="images/login/left_b1.jpg">&nbsp;</td>
        <td><div align="right">
            <table width="100%" border="0" cellpadding="0" cellspacing="8">
              <tr>
                <td><div align="center"><a href="#"><img onclick="checkForm();" src="images/login/button.jpg" width="67" height="28" border="0" /></a></div></td>
              </tr>
            </table>
        </div></td>
        <td background="images/login/right_b1.jpg">&nbsp;</td>
      </tr>
      <tr>
        <td valign="top" background="images/login/left_bg2.jpg"><img src="images/login/left_2.jpg" width="21" height="14" /></td>
        <td background="images/login/left_bg2.jpg">&nbsp;</td>
        <td valign="top" background="images/login/left_bg2.jpg"><img src="images/login/right_2.jpg" width="17" height="14" /></td>
      </tr>
    </table></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="3"><br />
      <br />
      <br />
      <br />
      <br />
   <br />
<br /></td>
  </tr>
  <tr>
    <td height="147" colspan="3" background="images/login/bot_bg.jpg">&nbsp;</td>
  </tr>
</table>
</form>
</body>
</html>
